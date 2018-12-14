package com.ezwel.htl.interfaces.server.commons.aop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.marshaller.BeanMarshaller;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.validation.ParamValidate;
import com.ezwel.htl.interfaces.commons.validation.data.ParamValidateSDO;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.commons.utils.CommonUtil;

@Component
public class MethodsAdviceHelper {

	private static final Logger logger = LoggerFactory.getLogger(MethodsAdviceHelper.class);

	private final static boolean IS_LOGGING = false;

	private BeanMarshaller beanMarshaller;
	
	private ParamValidate paramValidate;
	
	private CommonUtil commonUtil;

	
	@APIOperation(description="APIOperation 입력 APIModel개수 조회")
	Integer getAPIModelCount(Class<?>[] inputParamTypes) {

		Integer out = OperateConstants.INTEGER_ZERO_VALUE;
		for(Class<?> clazz : inputParamTypes) {
			if(clazz.getAnnotation(APIModel.class) != null) {
				out++;
			}
		}
		return out;
	}
	
	/**
	 * 스프링 APIOperation 입력 DTO유효성 검증
	 * @param inputParamTypes
	 * @param inputParamObjects
	 */
	@APIOperation(description="APIOperation 입력 APIModel유효성 검증")
	void doMethodInputValidation(Class<?>[] inputParamTypes, Object[] inputParamObjects) {
		if(IS_LOGGING) {
			logger.debug("■■ [AOP] doMethodInputValidation");
		}
		
		paramValidate = (ParamValidate) LApplicationContext.getBean(paramValidate, ParamValidate.class);
		
		if(inputParamTypes != null && inputParamObjects != null) {
			
			if(inputParamTypes.length != inputParamObjects.length) {
				throw new APIException("컨트롤러 오퍼레이션의 입력 파라메터 타입 개수와 입력 오브젝트 개수가 다를 수 없습니다.");
			}
			
			Class<?> inputType = null;
			Object inputObject = null;
			APIModel modelAnno = null;
			List<String> inputModelValidate = null;
			
			try {
				inputModelValidate = new ArrayList<String>();
				
				for(int i = 0; i < inputParamTypes.length; i++) {
					inputType = inputParamTypes[i];
					
					if(Collection.class.isAssignableFrom(inputType)) {
						throw new APIException("컨트롤러 오퍼레이션의 입력 타입으로 Collection/List는 사용할 수 없습니다.(API표준)");
					}
					
					inputObject = inputParamObjects[i];
					modelAnno = inputType.getAnnotation(APIModel.class);
					if(modelAnno != null) {
						
						if(inputModelValidate.contains(inputType.getSimpleName())) {
							throw new APIException("컨트롤러 오퍼레이션의 입력 타입으로 동일한 클래스 이름의 APIModel을 중복 선언할 수 없습니다. (API표준)");
						}
						
						if(inputObject == null) {
							throw new APIException("필수 입력 APIModel Parameter {}이/가 입력되지 않았습니다. ", APIUtil.NVL(modelAnno.description(), inputType.getSimpleName()));
						}
						
						inputModelValidate.add(inputType.getSimpleName());
						//setup validation object    
						paramValidate.addParam(new ParamValidateSDO(inputObject));					
					}
				}
				
				if(paramValidate.getParams() != null && paramValidate.getParams().size() > 0) {
					//execute validator
					paramValidate.execute();
				}	
			}
			finally {
				if(inputModelValidate != null) {
					inputModelValidate.clear();
				}
			}
					
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@APIOperation(description="APIOperation InputJSON Marshalling")
	void doMethodInputUnmarshall(Class<?>[] inputParamTypes, Object[] inputParamObjects) {
		if(IS_LOGGING) {
			logger.debug("■■ [AOP] doMethodInputStream");
		}
		 
		beanMarshaller = (BeanMarshaller) LApplicationContext.getBean(beanMarshaller, BeanMarshaller.class);
		commonUtil = (CommonUtil) LApplicationContext.getBean(commonUtil, CommonUtil.class);
		
		String requestBody = null;
		if(inputParamTypes != null && inputParamObjects != null) {

			if(inputParamTypes.length != inputParamObjects.length) {
				throw new APIException("컨트롤러 오퍼레이션의 입력 파라메터 타입 개수와 입력 오브젝트 개수가 다를 수 없습니다.");
			}
			requestBody = commonUtil.readReqeustBodyWithBufferedReader();
			
			Map<String, Object> jsonMap = null;
			if(requestBody != null && APIUtil.isNotEmpty(requestBody)) {
				logger.debug("■ inputStreamData : \n{}", requestBody);
				jsonMap = (Map<String, Object>) beanMarshaller.fromJSONStringToMap(requestBody);
				
				Class<?> inputType = null;
				APIModel modelAnno = null;
				Integer modelCount = getAPIModelCount(inputParamTypes);
				
				for(int i = 0; i < inputParamTypes.length; i++) {
					inputType = inputParamTypes[i];
					modelAnno = inputType.getAnnotation(APIModel.class);
					
					if(modelAnno != null) {
						
						if(inputParamObjects[i] == null) {
							throw new APIException("필수 입력 APIModel Parameter {}이/가 입력되지 않았습니다. ", APIUtil.NVL(modelAnno.description(), inputType.getSimpleName()));
						}
						
						if(!inputType.isAssignableFrom(inputParamObjects[i].getClass())) {
							throw new APIException("오퍼레이션 입력 파라메터순서가 일치하지 않습니다. inputType : {}, inputObject : {}", inputType, inputParamObjects[i].getClass());
						}
						
						if(modelCount == 1) {
							inputParamObjects[i] = beanMarshaller.mapToBean(jsonMap, inputType);
						}
						else if(jsonMap.get(APIUtil.getFirstCharLowerCase(inputType.getSimpleName())) != null) {
							inputParamObjects[i] = beanMarshaller.mapToBean((Map<String, Object>) jsonMap.get(APIUtil.getFirstCharLowerCase(inputType.getSimpleName())), inputType);
						}
						
						logger.debug("[INPUT-APIModel({})] {}", i, inputParamObjects[i]);
					}
				}
			}
		}
	}
	
	
	
	@APIOperation(description="Getting Runtime APIOperation Infomation")
	String getTargetMethodInfomation(String typeMethodName, Object[] inputParam, String description, String methodGuid) {

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
