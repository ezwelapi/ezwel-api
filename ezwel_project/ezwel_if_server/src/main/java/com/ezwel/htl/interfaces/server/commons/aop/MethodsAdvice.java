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
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.entity.RuntimeHeader;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.marshaller.BeanMarshaller;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.commons.utils.StackTraceUtil;
import com.ezwel.htl.interfaces.server.commons.sdo.ExceptionSDO;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.commons.utils.ResponseUtil;
import com.ezwel.htl.interfaces.server.sdo.TransactionOutSDO;

@Aspect
public class MethodsAdvice implements MethodInterceptor, Ordered {

	private static final Logger logger = LoggerFactory.getLogger(MethodsAdvice.class);

	private final static boolean IS_LOGGING = false;
	
	private BeanMarshaller beanMarshaller;
	
	private PropertyUtil propertyUtil;
	
	private ResponseUtil responseUtil;
	
	private MethodsAdviceHelper methodsAdviceHelper;
	
	private StackTraceUtil stackTraceUtil;
	
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
		methodsAdviceHelper.processLogger("afterThrowingTargetMethod", thisJoinPoint);
	}
	
	//methodend > order:2
	public void afterReturningTargetMethod(JoinPoint thisJoinPoint, Object retVal) {
		if (IS_LOGGING) {
			logger.debug("■■ [AOP] MethodsAdvice.afterReturningTargetMethod executed.\n■ thisJoinPoint : {}\n■ retVal : {}", thisJoinPoint, retVal);
		}
		
		methodsAdviceHelper.processLogger("afterReturningTargetMethod", thisJoinPoint);
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
		APIType apiTypeAnno = typeClass.getAnnotation(APIType.class);
		RequestMapping requestAnno = null;
		String responseJSON = null;
		Object resultValue = null;
		
		if (apiTypeAnno == null) {
			throw new APIException("■■ 유효하지 않은 API타입(클래스) APIType어노테이션이 존재하지 않습니다. '{}'", className);
		}
		
		if (apiOperAnno == null) {
			throw new APIException("■■ 유효하지 않은 API 오퍼레이션 APIOperation어노테이션이 존재하지 않습니다. '{}'", typeMethodName);
		}
		
		// inputParam 변경가능
		Object[] inputParamObjects = thisJoinPoint.getArgs();
		
		/** method당 실행시간 세팅 및 쓰레드내 메소드의 고유 값 생성 */
		String methodGuid = Local.startOperation();
		/** method description */
		//String description = APIUtil.NVL(apiOperAnno.description(), className.concat(OperateConstants.STR_DOT).concat(methodName));

		if(controlAnno != null) {
			logger.debug("■■ [START APIOperation] {}, methodGuid : {} ", typeMethodName, methodGuid);
			requestAnno = (RequestMapping) proccesMethod.getAnnotation(RequestMapping.class);
		}

		Object retVal = null;
		ExceptionSDO output = null;
		long executeLapTimeMillis = 0L;
		double operationLapTime = 0D;
		String channelId = null;
		
		try {
				
			if(controlAnno != null && requestAnno != null && !Local.commonHeader().isControlMarshalling()) {
				
				if (IS_LOGGING) {
					logger.debug("■■ [PARAMETER-UNMARSHALL]");
				}
				methodsAdviceHelper.doMethodInputUnmarshall(proccesMethod.getParameterTypes(), inputParamObjects, apiOperAnno, methodGuid);
				Local.commonHeader().setControlMarshalling(true);
			}
			
			if( controlAnno != null && requestAnno != null && apiOperAnno.isRequestHeaderValidate() && requestAnno.value().length > 0) {

				channelId = requestAnno.value()[0];
				if(channelId.contains(OperateConstants.STR_SLASH)) {
					channelId = channelId.substring(channelId.lastIndexOf(OperateConstants.STR_SLASH) + OperateConstants.STR_SLASH.length());
				}
				
				//시그니처 및 에이전트 체널 검증
				methodsAdviceHelper.isConfirmHeaderSignature(channelId);
			}
			
			if(( controlAnno != null && requestAnno != null ) || apiOperAnno.isInputBeanValidation()) {
				
				if (IS_LOGGING) {
					logger.debug("■■ [INPUTBEAN-VALIDATE] {} {}", controlAnno, apiOperAnno.isInputBeanValidation());
				}
				
				logger.debug("# requestAnno : {}", requestAnno);
				
				methodsAdviceHelper.doMethodInputValidation(proccesMethod.getParameterTypes(), inputParamObjects);
			}
			
			//Execute Operation
			retVal = thisJoinPoint.proceed(inputParamObjects);
			
			if(controlAnno != null && retVal != null && Local.commonHeader().isControlMarshalling() && apiOperAnno.isOutputJsonMarshall()) {
				
				logger.debug(new StringBuffer()
	            		.append(OperateConstants.LINE_SEPARATOR )
	            		.append( " ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ " )
	            		.append( OperateConstants.LINE_SEPARATOR )
	            		.append( " ■■ [AOP-Reverse] Converts the result of method '")
	            		.append( className)
	            		.append( OperateConstants.STR_DOT)
	            		.append( methodName)
	            		.append( "' to a JSON ResponseEntity.")
	            		.append( OperateConstants.LINE_SEPARATOR )
	            		.append( " ■■ guid : " )
	            		.append( Local.getId() )
	            		.append( OperateConstants.LINE_SEPARATOR )
	            		.append( " ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ " )
	            		.append( OperateConstants.LINE_SEPARATOR )
	            		.toString());
				
				if(TransactionOutSDO.class.isAssignableFrom(retVal.getClass())) {
					propertyUtil.setProperty(retVal, MessageConstants.RESPONSE_TYPE_NAME_FIELD_NAME, typeClass.getCanonicalName());
					propertyUtil.setProperty(retVal, MessageConstants.RESPONSE_TYPE_DESC_FIELD_NAME, apiTypeAnno.description());
					propertyUtil.setProperty(retVal, MessageConstants.RESPONSE_OPERATION_NAME_FIELD_NAME, methodName);
					propertyUtil.setProperty(retVal, MessageConstants.RESPONSE_OPERATION_DESC_FIELD_NAME, apiOperAnno.description());
				}

				logger.debug("#CODE : {}", propertyUtil.getProperty(retVal, MessageConstants.RESPONSE_CODE_FIELD_NAME));
				logger.debug("#MESSAGE : {}", propertyUtil.getProperty(retVal, MessageConstants.RESPONSE_MESSAGE_FIELD_NAME));
				
				if(propertyUtil.getProperty(retVal, MessageConstants.RESPONSE_CODE_FIELD_NAME) == null) {
					propertyUtil.setProperty(retVal, MessageConstants.RESPONSE_CODE_FIELD_NAME, Integer.toString(MessageConstants.RESPONSE_CODE_1000));
				}
				if(propertyUtil.getProperty(retVal, MessageConstants.RESPONSE_MESSAGE_FIELD_NAME) == null) {
					propertyUtil.setProperty(retVal, MessageConstants.RESPONSE_MESSAGE_FIELD_NAME, MessageConstants.getMessage(MessageConstants.RESPONSE_CODE_1000));						
				}
				
				resultValue = retVal;
				responseJSON = beanMarshaller.toJSONString(retVal);
				retVal = responseUtil.getResponseEntity(responseJSON);
				
			}
			
			//logger.debug("■■ [OUTPUT] {} {}", typeMethodName, retVal);
		}
		catch(APIException e) {
			
			//logger.debug("[AOP-APIException-InterfaceLog] {}", Local.commonHeader().getInterfaceLogSDO());
			
			stackTraceUtil = (StackTraceUtil) LApplicationContext.getBean(stackTraceUtil, StackTraceUtil.class);
			
			if(controlAnno != null && apiOperAnno != null && apiOperAnno.isOutputJsonMarshall()) {
        		logger.error(new StringBuffer()
            		.append(OperateConstants.LINE_SEPARATOR )
            		.append( " ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ " )
            		.append( OperateConstants.LINE_SEPARATOR )
            		.append( " ■■ [AOP-APIException]")
            		.append( OperateConstants.LINE_SEPARATOR )
            		.append( " ■■ guid : " )
            		.append( Local.getId() )
            		.append( OperateConstants.LINE_SEPARATOR )
            		.append( " ■■ Message : " )
            		.append( stackTraceUtil.getStackTrace(e) )
            		.append( OperateConstants.LINE_SEPARATOR )
            		.append( " ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ " )
            		.append( OperateConstants.LINE_SEPARATOR )
            		.toString()
            	);
			
				output = new ExceptionSDO();
				output.setCode(e.getResultCodeString());
				output.setMessage(e.getMessages());
				output.setDetailMessage(stackTraceUtil.getStackTrace(e));
				
				resultValue = output;
				responseJSON = beanMarshaller.toJSONString(output);
				retVal = responseUtil.getResponseEntity(responseJSON);
				
			}
			else {
				throw new APIException("■■ [AOP-APIException] {} 장애발생" , new Object[]{ typeMethodName }, e);
			}
		}
		finally {
			
			executeLapTimeMillis = Local.endOperation(methodGuid).getLapTimeMillis();
			operationLapTime = APIUtil.getTimeMillisToSecond(executeLapTimeMillis);
			
			if(operationLapTime > 3D) {
				logger.debug("■■ [This APIOperation runs longer than 3 seconds] {}, lapTime : {} sec, methodGuid : {}", typeMethodName, operationLapTime, methodGuid);
			}
			
			if(controlAnno != null) {
				logger.debug("■■ [END APIOperation({})] {}, lapTime : {} sec, methodGuid : {}", Local.commonHeader().isHandlerInterceptorComplete(), typeMethodName, operationLapTime, methodGuid);
			}
			
			//logger.debug("[InterfaceResultLogData] output : {}", retVal);
			
			setResultLogData(controlAnno, requestAnno, apiOperAnno, methodGuid, resultValue, responseJSON);
			
			if(responseJSON != null) {
				responseJSON = null;
			}
			if(resultValue != null) {
				resultValue = null;
			}
			
			if(Local.commonHeader().isHandlerInterceptorComplete()) {
				Local.remove();
			}
		}
		
		return retVal;
	}

	
	private void setResultLogData(Controller controlAnno, RequestMapping requestAnno, APIOperation apiOperAnno, String methodGuid, Object resultValue, String responseJSON) {
		
		/***************************
		 * [START] LOG DATA SETTING 
		 ***************************/
		if(controlAnno != null && requestAnno != null && apiOperAnno != null && apiOperAnno.isInsideInterfaceAPI() && Local.commonHeader().isControlMarshalling()) {
			RuntimeHeader runtime = methodsAdviceHelper.getRuntimeHeader(methodGuid);
			logger.debug("■ EndOperation-RuntimeHeader[{}] {}", methodGuid, runtime);
			Long endTimeMillis = OperateConstants.LONG_ZERO_VALUE;
			if(runtime != null) {
				endTimeMillis = runtime.getEndTimeMillis(); 
			} 
			else {
				endTimeMillis = APIUtil.currentTimeMillis();
			}
			
			Local.commonHeader().setInterfaceResultLogData(propertyUtil.getProperty(resultValue, MessageConstants.RESPONSE_CODE_FIELD_NAME), responseJSON, endTimeMillis);
		}
		/***************************
		 * [END]   LOG DATA SETTING 
		 ***************************/
		
	}
	
	
}
