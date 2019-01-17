package com.ezwel.htl.interfaces.server.commons.aop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.entity.RuntimeHeader;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.data.AgentInfoSDO;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigSDO;
import com.ezwel.htl.interfaces.commons.marshaller.BeanMarshaller;
import com.ezwel.htl.interfaces.commons.sdo.ApiBatcLogSDO;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.CryptUtil;
import com.ezwel.htl.interfaces.commons.validation.ParamValidate;
import com.ezwel.htl.interfaces.commons.validation.data.ParamValidateSDO;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.commons.utils.CommonUtil;
import com.ezwel.htl.interfaces.server.thread.BatchLoggingRunnable;
import com.ezwel.htl.interfaces.server.thread.IfLoggingRunnable;

@Component
public class MethodsAdviceHelper {

	private static final Logger logger = LoggerFactory.getLogger(MethodsAdviceHelper.class);

	private final static boolean IS_LOGGING = false;

	private final static Integer BATCH_LOG_QUEUE_SIZE = 5;
	
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
	void doMethodInputUnmarshall(Class<?>[] inputParamTypes, Object[] inputParamObjects, APIOperation apiOperAnno, String methodGuid) {
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
			
			/***************************
			 * [START] LOG DATA SETTING 
			 ***************************/
			if(apiOperAnno != null && apiOperAnno.isInsideInterfaceAPI()) {
				//인터페이스 로그정보 초기화
				RuntimeHeader runtime = getRuntimeHeader(methodGuid);
				logger.debug("■ StartOperation-RuntimeHeader[{}] {}", methodGuid, runtime);
				Long startTimeMillis = OperateConstants.LONG_ZERO_VALUE;
				if(runtime != null) {
					startTimeMillis = runtime.getStartTimeMillis(); 
				} 
				else {
					startTimeMillis = APIUtil.currentTimeMillis();
				}
				
				Local.commonHeader().initInterfaceReqeustLogData(startTimeMillis);
			}
			/***************************
			 * [END]   LOG DATA SETTING 
			 ***************************/
			
			requestBody = commonUtil.readReqeustBodyWithBufferedReader();
			
			Map<String, Object> jsonMap = null;
			if(requestBody != null && APIUtil.isNotEmpty(requestBody)) {
				logger.debug("■ inputStreamData : \n{}", requestBody);
				
				/*********************************************
				 * [START] LOG DATA SETTING THE INPUT TELEGRAM 
				 *********************************************/
				Local.commonHeader().setInterfaceInputTelegram(requestBody);
				/*********************************************
				 * [END]   LOG DATA SETTING THE INPUT TELEGRAM 
				 *********************************************/
				
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
	
	RuntimeHeader getRuntimeHeader(String methodGuid) {
		return Local.commonHeader().getRuntimeHeader(methodGuid);
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
						strb.append(OperateConstants.STR_COMA);
						strb.append(OperateConstants.STR_WHITE_SPACE);
					}
					strb.append(inputParam[i].getClass().getSimpleName());
				}
			}
		}
		strb.append(OperateConstants.STR_PAREN_END);
		
		return strb.toString();
	}
	
	

    @APIOperation(description="헤더 조회 및 시그니처 검증")
    void isConfirmHeaderSignature(String chanId) throws APIException, Exception {
    	logger.debug("[START] isConfirmHeaderSignature => chanId : {}", chanId);
    	
    	paramValidate = (ParamValidate) LApplicationContext.getBean(paramValidate, ParamValidate.class);
    	
    	HttpConfigSDO httpConfigSDO = Local.commonHeader().getHttpConfigSDO();
    	//헤더 파라메터 검증
    	paramValidate.addParam(new ParamValidateSDO(httpConfigSDO));
    	//execute validator
		paramValidate.execute();
		
    	//인터페이스 설정 파일 체널 정보
    	HttpConfigSDO httpConfigXML = InterfaceFactory.getChannel(chanId, httpConfigSDO.getHttpAgentId());
    	logger.debug("[PROC] ConfirmHeaderSignature httpConfigDTO : {}", httpConfigXML);
    	//제휴사 에이전트 정보
    	AgentInfoSDO agentInfoXML = InterfaceFactory.getInterfaceAgents(httpConfigSDO.getHttpAgentId());
		if(agentInfoXML == null) {
			throw new APIException("제휴사 에이전트 정보가 존재하지 않습니다. 에이전트아이디 : {}", httpConfigSDO.getHttpAgentId());
		}
    	
		/***************************************
		 * [START] CERTIFICATION LOG DATA SETTING 
		 ***************************************/
		//httpConfigXML.setHttpApiKey(agentInfoXML.getInsideApiKey());
    	Local.commonHeader().initInterfaceCertLogData(httpConfigXML);
		/***************************************
		 * [END]   CERTIFICATION LOG DATA SETTING 
		 ****************************************/
    	
    	String signature = httpConfigSDO.getHttpApiSignature();
    	logger.debug("[PROC] Signature : {}", signature);
    	
    	String decodeSignature = CryptUtil.getDecodeBase64(signature);
    	logger.debug("[PROC] DecodeSignature : {}", decodeSignature);
    	
    	if(decodeSignature.indexOf(OperateConstants.STR_HYPHEN) == -1) {
    		throw new APIException(MessageConstants.RESPONSE_CODE_4000, "시그니처 유형이 잘못되었습니다.");
    	}

    	String signatureItem = null;
    	List<String> signatureSplit = Arrays.asList(decodeSignature.split(OperateConstants.STR_HYPHEN));
    	for(int i = 0; i < signatureSplit.size(); i++) {
    		signatureItem = signatureSplit.get(i);
    		
    		switch(i) {
    	    	case 0: 
    	    		// ★ 0번째 인덱스는 시그니처를 유일값으로 만들기위한 UUID 입니다. 이 값은 로깅후 패스합니다.
    	    		logger.debug("* UUID data[{}] : {}", i, signatureItem);
    	        	break;
    	    	case 1: 
    	    		// ★ 1번째 인덱스는 httpAgentId 입니다. 요청 헤더에 세팅되어온 httpAgentId 값과 동일한지를 검증합니다.
    	    		logger.debug("* httpAgentId data[{}] : {}", i, signatureItem);
    	    		if(!signatureItem.equals(httpConfigSDO.getHttpAgentId())) {
    	    			throw new APIException(MessageConstants.RESPONSE_CODE_4000, "시그니처 검증실패! http-agent-id가 존재하지 않거나 잘못되었습니다.");
    	    		}
    	        	break;
    	    	case 2: 
    	    		// ★ 2번째 인덱스는 httpApiKey 입니다. 요청 헤더에 세팅되어온 httpApiKey 값과 동일한지를 검증합니다.
        			logger.debug("* httpApiKey data[{}] : {}", i, signatureItem);
        			if(!signatureItem.equals(agentInfoXML.getInsideApiKey())) {
        				throw new APIException(MessageConstants.RESPONSE_CODE_4000, "시그니처 검증실패! http-api-key가 존재하지 않거나 잘못되었습니다.");
        			}
    	        	break;
    	    	case 3: 
    	    		// ★ 3번째 인덱스는 httpApiTimestamp 입니다. 요청 헤더에 세팅되어온 httpApiTimestamp 값과 동일한지를 검증합니다.
        			logger.debug("* httpApiTimestamp data[{}] : {}", i, signatureItem);
        			if(!signatureItem.equals(httpConfigSDO.getHttpApiTimestamp())) {
        				throw new APIException(MessageConstants.RESPONSE_CODE_4000, "시그니처 검증실패! http-api-timestamp가 존재하지 않거나 잘못되었습니다.");
        			}
    	        	break;  	        	
    	    	default: logger.debug("* Ignore Index Data[{}] : {}", i, signatureItem);
    	    		break;
    		}
    	}
    	
    	logger.debug("[END] isConfirmHeaderSignature Confirm Complete!");
    }
    
    @APIOperation
	void processLogger(String pointcutType, JoinPoint thisJoinPoint) {
		logger.debug("[PROC] processLogger => pointcutType : {}, thisJoinPoint : {}, InterfaceLogSDO : {}", pointcutType, thisJoinPoint, Local.commonHeader().getInterfaceLogSDO());
		
		APIOperation apiOperAnno = ((MethodSignature) thisJoinPoint.getSignature()).getMethod().getAnnotation(APIOperation.class);
		if(apiOperAnno != null && Local.commonHeader().getInterfaceLogSDO() != null && APIUtil.isNotEmpty(Local.commonHeader().getInterfaceLogSDO().getSuccYn())) {
			//인터페이스 로그 저장
			logger.debug("■■ [Call] 인터페이스 로그 저장 IfLoggingRunnable => pointcutType : {} =>> thisJoinPoint : {}", pointcutType, thisJoinPoint);
			
			Local.commonHeader().getInterfaceLogSDO().setIfReqtIp(Local.commonHeader().getClientAddress());
			if(Local.commonHeader().getInterfaceLogSDO().getExecEndMlisSecd() == null) {
				Local.commonHeader().getInterfaceLogSDO().setExecEndMlisSecd(APIUtil.currentTimeMillis());
			}
			
			logger.debug("[Call] InterfaceLogSDO : {}", Local.commonHeader().getInterfaceLogSDO());
			
			Runnable ifLoggingRunnable = new IfLoggingRunnable(Local.commonHeader().getInterfaceLogSDO());
			Thread ifLoggingThread = new Thread(ifLoggingRunnable);
			ifLoggingThread.start();
			Local.commonHeader().setInterfaceLogSDO(null);
			Local.commonHeader().setInterfaceLogInitCount(OperateConstants.INTEGER_ZERO_VALUE);
		}
		
		if(Local.commonHeader().getApiBatcLogList() != null && (Local.commonHeader().getApiBatcLogList().size() >= BATCH_LOG_QUEUE_SIZE || Local.commonHeader().isForcedApiBatcLogSave())) {
			//API 배치 로그 저장
			logger.debug("■■ [Call] API 배치 로그 저장 BatchLoggingRunnable => pointcutType : {} =>> thisJoinPoint : {}", pointcutType, thisJoinPoint);
			
			List<ApiBatcLogSDO> apiBatcLogList = new ArrayList<ApiBatcLogSDO>();
			for(ApiBatcLogSDO apiBatcLog : Local.commonHeader().getApiBatcLogList()) {
				apiBatcLogList.add(apiBatcLog);
			}
			
			Runnable batcLoggingRunnable = new BatchLoggingRunnable(apiBatcLogList);
			logger.debug("[Call] ApiBatcLogList : {}", Local.commonHeader().getApiBatcLogList());
			
			Thread batcLoggingThread = new Thread(batcLoggingRunnable);
			batcLoggingThread.start();
			if(Local.commonHeader().isForcedApiBatcLogSave()) {
				Local.commonHeader().setForcedApiBatcLogSave(false);
			}
			Local.commonHeader().getApiBatcLogList().clear();
		}
	}
	
}
