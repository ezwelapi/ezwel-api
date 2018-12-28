package com.ezwel.htl.interfaces.server.commons.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Controller;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.marshaller.BeanMarshaller;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.commons.utils.StackTraceUtil;
import com.ezwel.htl.interfaces.server.commons.sdo.ExceptionSDO;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.commons.utils.ResponseUtil;

@Aspect
public class MethodsAdvice implements MethodInterceptor, Ordered {

	private static final Logger logger = LoggerFactory.getLogger(MethodsAdvice.class);

	private final static boolean IS_LOGGING = false;

	private BeanMarshaller beanMarshaller;
	
	private PropertyUtil propertyUtil;
	
	private ResponseUtil responseUtil;
	
	private MethodsAdviceHelper methodsAdviceHelper;
	
	private int order = 1;

	@Override
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public Object intercept(Object arg0, Method arg1, Object[] arg2, MethodProxy arg3) throws Throwable {

		if(IS_LOGGING) {
			logger.debug("■■ [AOP] intercept\narg0 : {}\n■ arg1 : {}\n■ arg2 : {}\n■ arg3 : {}", arg0, arg1, arg2, arg3);
		}
		return null;
	}

	//order:1
	public void beforeTargetMethod(JoinPoint thisJoinPoint) {
		if (IS_LOGGING) {
			logger.debug("■■ [AOP] MethodsAdvice.beforeTargetMethod executed.\n■ thisJoinPoint : {}", thisJoinPoint);
		}
	}

	//order:2, methodend > order:1
	public void afterTargetMethod(JoinPoint thisJoinPoint) {
		if (IS_LOGGING) {
			logger.debug("■■ [AOP] MethodsAdvice.afterTargetMethod (finally) executed.\n■ thisJoinPoint : {}", thisJoinPoint);
		}
	}
	
	//order:3
	public void afterThrowingTargetMethod(JoinPoint thisJoinPoint, Exception exception) throws Exception {
		if (IS_LOGGING) {
			logger.debug("■■ [AOP] MethodsAdvice.afterThrowingTargetMethod executed.\n■ thisJoinPoint : {}\n■ exception : ", thisJoinPoint, exception);
		}
		// throw new ApplicationException("어플리케이션 장애발생", exception);
	}
	
	//methodend > order:2
	public void afterReturningTargetMethod(JoinPoint thisJoinPoint, Object retVal) {
		if (IS_LOGGING) {
			logger.debug("■■ [AOP] MethodsAdvice.afterReturningTargetMethod executed.\n■ thisJoinPoint : {}\n■ retVal : {}", thisJoinPoint, retVal);
		}
	}

	//method execute
	public Object aroundTargetMethod(ProceedingJoinPoint thisJoinPoint) throws Throwable {
		if (IS_LOGGING) {
			logger.debug("■■ [AOP] MethodsAdvice.aroundTargetMethod executed. thisJoinPoint : {}", thisJoinPoint);
		}
		
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		beanMarshaller = (BeanMarshaller) LApplicationContext.getBean(beanMarshaller, BeanMarshaller.class);
		responseUtil = (ResponseUtil) LApplicationContext.getBean(responseUtil, ResponseUtil.class);
		methodsAdviceHelper = (MethodsAdviceHelper) LApplicationContext.getBean(methodsAdviceHelper, MethodsAdviceHelper.class);
		
		Class<?> typeClass = thisJoinPoint.getTarget().getClass();
		String className = typeClass.getSimpleName();
		String methodName = thisJoinPoint.getSignature().getName();
		String typeMethodName = className.concat(OperateConstants.STR_DOT).concat(methodName);
		Method proccesMethod = ((MethodSignature) thisJoinPoint.getSignature()).getMethod();
		APIOperation apiOperAnno = proccesMethod.getAnnotation(APIOperation.class);
		Controller controlAnno = typeClass.getAnnotation(Controller.class);
		
		if (apiOperAnno == null) {
			throw new APIException("■■ 유효하지 않은 API 오퍼레이션 APIOperation어노테이션이 존재하지 않습니다. '{}'", typeMethodName);
		}
		
		// inputParam 변경가능
		Object[] inputParamObjects = thisJoinPoint.getArgs();
		
		/** method당 실행시간 세팅 및 쓰레드내 메소드의 고유 값 생성 */
		String methodGuid = Local.startOperation();
		/** method description */
		String description = APIUtil.NVL(apiOperAnno.description(), className.concat(OperateConstants.STR_DOT).concat(methodName));

		logger.debug("■■ [START APIOperation] {}, methodGuid : {} ", typeMethodName, methodGuid);

		Object retVal = null;
		ExceptionSDO output = null;
		long executeLapTimeMillis = 0L;
		
		try {
				
			if(controlAnno != null && !Local.commonHeader().isControlMarshalling()) {

				logger.debug("■■ [INPUTSTREAM]");
				methodsAdviceHelper.doMethodInputUnmarshall(proccesMethod.getParameterTypes(), inputParamObjects);
				Local.commonHeader().setControlMarshalling(true);
			}
			
			if(controlAnno != null || apiOperAnno.isInputBeanValidation()) {
				
				logger.debug("■■ [VALIDATE] {} {}", controlAnno, apiOperAnno.isInputBeanValidation());
				methodsAdviceHelper.doMethodInputValidation(proccesMethod.getParameterTypes(), inputParamObjects);
			}
			
			//Execute Operation
			retVal = thisJoinPoint.proceed(inputParamObjects);
			
			if(controlAnno != null && retVal != null && Local.commonHeader().isControlMarshalling() && apiOperAnno.isOutputJsonMarshall()) {
				
				if(APIUtil.isEmpty((String) propertyUtil.getProperty(retVal, MessageConstants.RESPONSE_CODE_FIELD_NAME))) {
					propertyUtil.setProperty(retVal, MessageConstants.RESPONSE_CODE_FIELD_NAME, Integer.toString(MessageConstants.RESPONSE_CODE_1000));
				}
				
				if(APIUtil.isEmpty((String) propertyUtil.getProperty(retVal, MessageConstants.RESPONSE_MESSAGE_FIELD_NAME))) {
					propertyUtil.setProperty(retVal, MessageConstants.RESPONSE_MESSAGE_FIELD_NAME, MessageConstants.getMessage(MessageConstants.RESPONSE_CODE_1000));						
				}
				
				retVal = responseUtil.getResponseEntity(beanMarshaller.toJSONString(retVal));
			}
			
			//logger.debug("■■ [OUTPUT] {} {}", typeMethodName, retVal);
		}
		catch(APIException e) {
			
			if(controlAnno != null && apiOperAnno != null && apiOperAnno.isOutputJsonMarshall()) {
				
				output = new ExceptionSDO();
				output.setCode(e.getResultCodeString());
				output.setMessage(e.getMessages());
				retVal = responseUtil.getResponseEntity(beanMarshaller.toJSONString(output));
				
				e.printStackTrace();
			}
			else {
				throw new APIException("■■ [AOP-APIException] {} ({}) 장애발생" , new Object[]{ typeMethodName, description }, e);
			}
		} 
		finally {
			
			executeLapTimeMillis = Local.endOperation(methodGuid).getLapTimeMillis();
			
			logger.debug("■■ [END APIOperation] {}, lapTime : {} sec, methodGuid : {}", typeMethodName, APIUtil.getTimeMillisToSecond(executeLapTimeMillis), methodGuid);
			if(Local.commonHeader().isHandlerInterceptorComplete()) {
				Local.remove();
			}
		}
		
		return retVal;
	}

}
