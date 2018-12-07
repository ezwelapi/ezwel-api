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

import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.marshaller.BeanMarshaller;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.commons.validation.ParamValidate;
import com.ezwel.htl.interfaces.commons.validation.data.ParamValidateSDO;
import com.ezwel.htl.interfaces.server.commons.sdo.ExceptionSDO;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.commons.utils.CommonUtil;
import com.ezwel.htl.interfaces.server.commons.utils.ResponseUtil;

@Aspect
public class MethodsAdvice implements MethodInterceptor, Ordered {

	private static final Logger logger = LoggerFactory.getLogger(MethodsAdvice.class);

	private final static boolean IS_LOGGING = false;

	private BeanMarshaller beanMarshaller;
	
	private PropertyUtil propertyUtil;
	
	private ResponseUtil responseUtil;
	
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
		Object[] inputParam = thisJoinPoint.getArgs();
		
		/** method당 실행시간 세팅 및 쓰레드내 메소드의 고유 값 생성 */
		String methodGuid = Local.startOperation();
		/** method description */
		String description = APIUtil.NVL(apiOperAnno.description(), className.concat(OperateConstants.STR_DOT).concat(methodName));

		logger.debug("■■ [START APIOperation] {}, methodGuid : {} ", typeMethodName, methodGuid);

		Object retVal = null;
		
		try {
			logger.debug("■■ [INPUT] {} {}", typeMethodName, inputParam);
			if(controlAnno != null || apiOperAnno.isInputBeanValidation()) {
				logger.debug("■■ [VALIDATE] {} {}", controlAnno, apiOperAnno.isInputBeanValidation());
				doMethodInputValidation(proccesMethod.getParameterTypes(), inputParam);
			}
			
			retVal = thisJoinPoint.proceed(inputParam);
			
			if(retVal != null && controlAnno != null && apiOperAnno.isOutputJsonMarshall()) {
				propertyUtil.setProperty(retVal, MessageConstants.RESPONSE_CODE_FIELD_NAME, MessageConstants.RESPONSE_CODE_1000);
				propertyUtil.setProperty(retVal, MessageConstants.RESPONSE_MESSAGE_FIELD_NAME, MessageConstants.getMessage(MessageConstants.RESPONSE_CODE_1000));
				retVal = responseUtil.getResponseEntity(beanMarshaller.toJSONString(retVal));
			}
			
			logger.debug("■■ [OUTPUT] {} {}", typeMethodName, retVal);
		}
		catch(APIException e) {
			
			if(controlAnno != null && apiOperAnno != null && apiOperAnno.isOutputJsonMarshall()) {
				
				ExceptionSDO output = new ExceptionSDO();
				output.setCode(e.getResultCodeString());
				output.setMessage(e.getMessages());
				retVal = responseUtil.getResponseEntity(beanMarshaller.toJSONString(output));
				
				e.printStackTrace();
			}
			else {
				throw new APIException("■■ [AOP-APIException] {} ({}) 장애발생" , new Object[]{ typeMethodName, description }, e);
			}
		} 

		long executeLapTimeMillis = Local.endOperation(methodGuid).getLapTimeMillis();
		
		logger.debug("■■ [END APIOperation] {}, methodGuid : {}, lapTime : {} sec", typeMethodName, methodGuid, APIUtil.getTimeMillisToSecond(executeLapTimeMillis));
		if(Local.commonHeader().isHandlerInterceptorComplete()) {
			Local.remove();
		}
		return retVal;
	}

	private void doMethodInputValidation(Class<?>[] inputParamTypes, Object[] inputParamObjects) {
		if(IS_LOGGING) {
			logger.debug("■■ [AOP] doMethodInputValidation");
		}
		
		if(inputParamTypes != null && inputParamObjects != null) {
			
			if(inputParamTypes.length != inputParamObjects.length) {
				throw new APIException("컨트롤러 오퍼레이션의 입력 파라메터 타입 개수와 입력 오브젝트 개수가 다를 수 없습니다.");
			}
			
			ParamValidate paramValidator = new ParamValidate();
			Class<?> inputType = null;
			Object inputObject = null;
			APIModel modelAnno = null;
			for(int i = 0; i < inputParamTypes.length; i++) {
				inputType = inputParamTypes[i];
				inputObject = inputParamObjects[i];
				modelAnno = inputType.getAnnotation(APIModel.class);
				if(modelAnno != null) {
					if(inputObject == null) {
						throw new APIException("필수 입력 APIModel Parameter {}이/가 입력되지 않았습니다. ", APIUtil.NVL(modelAnno.description(), inputType.getSimpleName()));
					}
					
					//setup validation object    
					paramValidator.addParam(new ParamValidateSDO(inputObject));					
				}
			}
			
			if(paramValidator.getParams() != null && paramValidator.getParams().size() > 0) {
				//execute validator
				paramValidator.execute();
			}			
		}
	}
	
	private String getTargetMethodInfomation(String typeMethodName, Object[] inputParam, String description, String methodGuid) {

		StringBuilder strb = new StringBuilder();
		strb.append("DESCRIPTION : ");
		strb.append(description);
		strb.append(OperateConstants.STR_COMA);
		strb.append(OperateConstants.STR_WHITE_SPACE);
		strb.append("METHOD GUID : ");
		strb.append(methodGuid);
		strb.append(OperateConstants.STR_COMA);
		strb.append(OperateConstants.STR_WHITE_SPACE);
		strb.append(typeMethodName);
		strb.append(OperateConstants.STR_PAREN_START);
		if (inputParam != null) {
			for (int i = 0; i < inputParam.length; i++) {

				if(inputParam[i] != null) {
				
					if (i > 0) {
						strb.append(", ");
					}
					strb.append(inputParam[i].getClass().getSimpleName());
				}
			}
		}
		strb.append(OperateConstants.STR_PAREN_END);
		
		return strb.toString();
	}
}
