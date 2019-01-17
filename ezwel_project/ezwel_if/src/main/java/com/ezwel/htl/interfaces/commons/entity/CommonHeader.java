package com.ezwel.htl.interfaces.commons.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.abstracts.APIObject;
import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigSDO;
import com.ezwel.htl.interfaces.commons.sdo.InterfaceLogSDO;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.StackTraceUtil;

/**
 * <pre>
 *  API Runtime Info in ThreadLocal
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */
@APIModel
public class CommonHeader extends APIObject implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 144157635035030490L;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private Map<String, RuntimeHeader> runtimeHeader;

    private StackTraceUtil stackTraceUtil;
    
	@APIFields(description = "헤더 어노테이션 맵")
	private Map<String, Object> annotationMap;
	
	@APIFields(description = "헤더 오브젝트 맵")
	private Map<String, Object> objectMap;
	
	@APIFields(description = "헤더 프로퍼티")
	private Properties	properties;
	
	@APIFields(description = "거래코드", groupName = OperateConstants.META_COMM_HEADER, isMarshalField = true)
	private String 	guid;
	
	@APIFields(description = "결과코드", groupName = OperateConstants.META_COMM_HEADER, isMarshalField = true)
	private Integer	resultCode;
	
	@APIFields(description = "메시지", groupName = OperateConstants.META_COMM_HEADER, isMarshalField = true)
	private String	message;

	@APIFields(description = "원인", groupName = OperateConstants.META_COMM_HEADER, isMarshalField = true)
	private String	cause;
	
	@APIFields(description = "Throwable", groupName = OperateConstants.META_COMM_HEADER, isMarshalField = true)
	private Throwable throwable;
	
	@APIFields(description = "원인추적", groupName = OperateConstants.META_COMM_HEADER, isMarshalField = true)
	private String	stackTrace;

	@APIFields(description = "실행시간", groupName = OperateConstants.META_COMM_HEADER, isMarshalField = true)
	private long	lapTimeMillis;

	@APIFields(description = "HTTP 헤더정보를 담을 인터페이스 설정 DTO")
	private HttpConfigSDO httpConfigSDO;

	@APIFields(description = "인터페이스 로그")
	private InterfaceLogSDO interfaceLogSDO;
	
	private Integer interfaceLogInitCount;
	
	/*@APIFields(description = "인터페이스 로그 목록")
	private List<InterfaceLogSDO> interfaceLogSDOList;*/
	
	private String controllerType;
	
	private String[]	arrayMessages;

	private List<String>	traceMessages;

	private long	startTimeMillis;
	
	private long	endTimeMillis;
	
	private String	encoding;

	private String contentType;
	
	private String clientAddress;
	
	private String systemUserId;
	
	private Map<String, String[]> requestMap;
	
	private List<Object> errorItems;
	
	private boolean isHandlerInterceptorComplete;
	
	private boolean isControlMarshalling;
	
	public CommonHeader() {
		this.reset();
	}
	
	private void reset(){
		annotationMap = null;
		objectMap = null;
		properties = null;
		systemUserId = OperateConstants.SYSTEM_ID;
		guid = OperateConstants.STR_BLANK;
		startTimeMillis = OperateConstants.LONG_ZERO_VALUE;
		endTimeMillis = OperateConstants.LONG_MINUS_ONE;
		lapTimeMillis = OperateConstants.LONG_MINUS_ONE;
		resultCode = MessageConstants.RESPONSE_CODE_1000;
		message = OperateConstants.STR_BLANK;
		throwable = null;
		cause = OperateConstants.STR_BLANK;
		stackTrace = OperateConstants.STR_BLANK;
		controllerType = null;
		arrayMessages = null;
		traceMessages = null;
		encoding = OperateConstants.DEFAULT_ENCODING;
		contentType = null;
		clientAddress = null;
		requestMap = new LinkedHashMap<String, String[]>();
		runtimeHeader = null;
		errorItems = null;
		isHandlerInterceptorComplete = false;
		isControlMarshalling = false;
		httpConfigSDO = null;
		stackTraceUtil = new StackTraceUtil();
		interfaceLogSDO = null;
		interfaceLogInitCount = OperateConstants.INTEGER_ZERO_VALUE;
	}
	
	
	public String getSystemUserId() {
		return systemUserId;
	}

	public void setSystemUserId(String systemUserId) {
		this.systemUserId = systemUserId;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public RuntimeHeader getRuntimeHeader(String key) {
		return runtimeHeader.get(key);
	}
	
	
	public Map<String, RuntimeHeader> getRuntimeHeader() {
		return runtimeHeader;
	}

	public void setRuntimeHeader(LinkedHashMap<String, RuntimeHeader> runtimeHeader) {
		this.runtimeHeader = runtimeHeader;
	}

	public void addRuntimeHeader(String key, RuntimeHeader header) {
		if(runtimeHeader == null) {
			this.runtimeHeader = new LinkedHashMap<String, RuntimeHeader>();
		}
		runtimeHeader.put(key, header);
	}
	
	public Map<String, Object> getAnnotationMap() {
		return annotationMap;
	}

	public void setAnnotationMap(Map<String, Object> annotationMap) {
		this.annotationMap = annotationMap;
	}

	public void addAnnotation(String key, Object clazz) {
		if(annotationMap == null) {
			this.annotationMap = new HashMap<String, Object>();
		}
		annotationMap.put(key, clazz);
	}

	public Object getAnnotation(String key) {
		if(annotationMap != null) {
			return annotationMap.get(key);
		}
		return null;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
		this.setInterfaceErrorCont(stackTraceUtil.getStackTrace(throwable));
	}

	public void setObject(String key, Object clazz) {
		if(objectMap == null) {
			this.objectMap = new HashMap<String, Object>();
		}
		objectMap.put(key, clazz);
	}

	public Object getObject(String key) {
		if(objectMap != null) {
			return objectMap.get(key);
		}
		return null;
	}
	
	public Map<String, Object> getObjectMap() {
		return objectMap;
	}

	public void setObjectMap(Map<String, Object> objectMap) {
		this.objectMap = objectMap;
	}

	public void setProperties(String key, String value) {
		if(properties == null) {
			this.properties = new Properties();
		}
		properties.setProperty(key, value);
	}

	public String getProperties(String key) {
		return getProperties(key, null);
	}

	public String getProperties(String key, String defaultValue) {
		if(properties != null) {
			return properties.getProperty(key, defaultValue);
		}
		return null;
	}
	
	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public long getStartTimeMillis() {
		return startTimeMillis;
	}

	public void setStartTimeMillis(long startTimeMillis) {
		this.startTimeMillis = startTimeMillis;
	}

	public long getEndTimeMillis() {
		if(this.resultCode != MessageConstants.RESPONSE_CODE_1000 || endTimeMillis == OperateConstants.LONG_MINUS_ONE) {
			setEndTimeMillis(APIUtil.currentTimeMillis());
		}
		return endTimeMillis;
	}

	public void setEndTimeMillis(long endTimeMillis) {
		this.endTimeMillis = endTimeMillis;
	}

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		if(logger.isDebugEnabled()) {
			//logger.debug(" [SET COMMON HEADER MESSAGE] : {}", message);
		}
		this.message = message;
		this.setInterfaceErrorType(message);
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public void addProperties(String key, String value) {
		if( properties == null ) {
			properties = new Properties();
		}
		this.properties.setProperty(key, (value == null ? OperateConstants.MESSAGE_ISNULL : value));
	} 
	
	
	public void setLapTimeMillis(long lapTimeMillis) {
		this.lapTimeMillis = lapTimeMillis;
	}

	public long getLapTimeMillis() {
		if(this.lapTimeMillis == OperateConstants.LONG_MINUS_ONE) this.lapTimeMillis = getEndTimeMillis() - getStartTimeMillis();
		return lapTimeMillis;
	}

	public String getControllerType() {
		return controllerType;
	}

	public void setControllerType(String controllerType) {
		this.controllerType = controllerType;
	}

	public String[] getArrayMessages() {
		return arrayMessages;
	}

	public void setArrayMessages(String[] arrayMessages) {
		this.arrayMessages = arrayMessages;
	}

	public List<String> getTraceMessages() {
		return traceMessages;
	}

	public void setTraceMessages(List<String> traceMessages) {
		this.traceMessages = traceMessages;
	}

	public void addTraceMessages(String traceMessages) {
		if( this.traceMessages == null ) {
			this.traceMessages = new ArrayList<String>();
		}
		this.traceMessages.add(traceMessages);
	}
	
	public List<Object> getErrorItems() {
		return errorItems;
	}

	public void setErrorItems(List<Object> errorItems) {
		this.errorItems = errorItems;
	}

	public void addErrorItems(Object errorItem) {
		if( this.errorItems == null ) {
			this.errorItems = new ArrayList<Object>();
		}
		this.errorItems.add(errorItem);
	}
	
	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		if(encoding != null && !encoding.isEmpty()) {
			this.encoding = encoding;
		}
	}

	public String getClientAddress() {
		return clientAddress;
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	public Map<String, String[]> getRequestMap() {
		return requestMap;
	}

	public void setRequestMap(Map<String, String[]> requestMap) {
		this.requestMap = requestMap;
	}

	public boolean isHandlerInterceptorComplete() {
		return isHandlerInterceptorComplete;
	}

	public void setHandlerInterceptorComplete(boolean isHandlerInterceptorComplete) {
		this.isHandlerInterceptorComplete = isHandlerInterceptorComplete;
	}

	public boolean isControlMarshalling() {
		return isControlMarshalling;
	}

	public void setControlMarshalling(boolean isControlMarshalling) {
		this.isControlMarshalling = isControlMarshalling;
	}

	public HttpConfigSDO getHttpConfigSDO() {
		return httpConfigSDO;
	}

	public void setHttpConfigSDO(HttpConfigSDO httpConfigSDO) {
		this.httpConfigSDO = httpConfigSDO;
	}
	
	public InterfaceLogSDO getInterfaceLogSDO() {
		return interfaceLogSDO;
	}

	public void setInterfaceLogSDO(InterfaceLogSDO interfaceLogSDO) {
		this.interfaceLogSDO = interfaceLogSDO;
	}

	/*public List<InterfaceLogSDO> getInterfaceLogSDOList() {
		return interfaceLogSDOList;
	}

	public void setInterfaceLogSDOList(List<InterfaceLogSDO> interfaceLogSDOList) {
		this.interfaceLogSDOList = interfaceLogSDOList;
	}
	
	public void addInterfaceLogSDOList(InterfaceLogSDO interfaceLogSDO) {
		if(this.interfaceLogSDOList == null) {
			this.interfaceLogSDOList = new ArrayList<InterfaceLogSDO>();
		}
		this.interfaceLogSDOList.add(interfaceLogSDO);
	}*/

	@APIOperation(description="인터페이스 요청헤더 로그 데이터 세팅")
	public void initInterfaceReqeustLogData() {
		initInterfaceReqeustLogData(httpConfigSDO, null);
	}

	@APIOperation(description="인터페이스 요청헤더 로그 데이터 세팅")
	public void initInterfaceReqeustLogData(Long execStrtMlisSecd) {
		initInterfaceReqeustLogData(httpConfigSDO, execStrtMlisSecd);
	}
	
	@APIOperation(description="인터페이스 요청헤더 로그 데이터 세팅")
	public void initInterfaceReqeustLogData(HttpConfigSDO httpConfig, Long execStrtMlisSecd) {
		logger.debug("[INIT] initInterfaceReqeustLogData({}) {} : {}", interfaceLogInitCount, execStrtMlisSecd, interfaceLogSDO);
		logger.debug("[INPUT] httpConfig : {}", httpConfig);
		try {
			
			if(interfaceLogInitCount == 0 || interfaceLogSDO == null) {
				
				interfaceLogSDO = new InterfaceLogSDO();
				interfaceLogSDO.setThedGuid(this.guid);
				
				if(httpConfig != null) {
					
					interfaceLogSDO.setPartAgentId(httpConfig.getHttpAgentId());
					interfaceLogSDO.setIfChanId(httpConfig.getChanId());
					interfaceLogSDO.setIfChanGrpId(httpConfig.getHttpAgentGroupId());
					interfaceLogSDO.setIfDesc(httpConfig.getDescription());
					interfaceLogSDO.setIfApiUri(httpConfig.getRestURI());
					interfaceLogSDO.setIfApiKey(httpConfig.getHttpApiKey());
					interfaceLogSDO.setIfTimeStmp(httpConfig.getHttpApiTimestamp());
					interfaceLogSDO.setIfSign(httpConfig.getHttpApiSignature()); 
					interfaceLogSDO.setIfReqtId(httpConfig.getHttpRequestId()); 
					interfaceLogSDO.setEncoding(httpConfig.getEncoding());
					interfaceLogSDO.setIfReqtDirt(OperateConstants.STR_O);
					//시작시간
					if(execStrtMlisSecd != null && execStrtMlisSecd > OperateConstants.LONG_ZERO_VALUE) {
						logger.debug("# 시작시간 세팅 : InputSignature > execStrtMlisSecd");
						interfaceLogSDO.setExecStrtMlisSecd(execStrtMlisSecd);
					}
					else if(startTimeMillis > OperateConstants.LONG_ZERO_VALUE){
						logger.debug("# 시작시간 세팅 : Local > startTimeMillis");
						interfaceLogSDO.setExecStrtMlisSecd(startTimeMillis);
					}
					else {
						logger.debug("# 시작시간 세팅 : 신규 currentTimeMillis");
						interfaceLogSDO.setExecStrtMlisSecd(APIUtil.currentTimeMillis());
					}
				}
				
				interfaceLogInitCount++;
			}
			else {
				logger.debug("# 인터페이스 로그 SDO가 이미 초기화 되었습니다. input-httpConfig : {}", httpConfig);
			}
			
		} catch (Exception e) {
			logger.error("[InterfaceReqeustLogData] 요청(입력) 로그 데이터 세팅 장애발생", e);
		}

		logger.debug("[END] initInterfaceReqeustLogData({}) {}", interfaceLogInitCount, interfaceLogSDO);		
	}
	
	@APIOperation(description="인터페이스 요청헤더 로그 데이터 세팅")
	public void initInterfaceCertLogData(HttpConfigSDO httpConfig) {
		logger.debug("[START] initInterfaceCertLogData({}) {}", interfaceLogInitCount, httpConfig);

		try {
			
			if(interfaceLogSDO != null) {
				
				if(httpConfig != null) {
					
					interfaceLogSDO.setPartAgentId(httpConfig.getHttpAgentId());
					interfaceLogSDO.setIfChanId(httpConfig.getChanId());
					interfaceLogSDO.setIfChanGrpId(httpConfig.getHttpAgentGroupId());
					interfaceLogSDO.setIfDesc(httpConfig.getDescription());
					interfaceLogSDO.setIfApiUri(httpConfig.getRestURI());
					interfaceLogSDO.setIfApiKey(httpConfig.getHttpApiKey());
					interfaceLogSDO.setIfReqtDirt(OperateConstants.STR_I);
				}
				
				interfaceLogInitCount++;
			}
			else {
				logger.debug("# CertLogData::인터페이스 로그 SDO가 이미 초기화 되지 않았습니다. input-httpConfig : {}", httpConfig);
			}
			
		} catch (Exception e) {
			logger.error("[InterfaceReqeustLogData] 요청(입력) 로그 데이터 세팅 장애발생", e);
		}

		logger.debug("[END] initInterfaceCertLogData({}) {}", interfaceLogInitCount, interfaceLogSDO);		
	}
	
	@APIOperation(description="인터페이스 입력파라메터 로그 데이터 세팅")
	public void setInterfaceInputTelegram(String inJsonParam) {
		setInterfaceInputTelegram(httpConfigSDO, inJsonParam);
	}
	
	@APIOperation(description="인터페이스 입력파라메터 로그 데이터 세팅")
	public void setInterfaceInputTelegram(HttpConfigSDO httpConfig, String inJsonParam) {
		
		try {
			
			interfaceLogSDO.setInptTelg(inJsonParam);			
			if(inJsonParam != null) {
				
				if(httpConfig != null ) {
					interfaceLogSDO.setInptTelgSize(new BigDecimal(inJsonParam.getBytes(httpConfig.getEncoding()).length));
				}
			}
			else {
				interfaceLogSDO.setInptTelgSize(OperateConstants.BIGDECIMAL_ZERO_VALUE);
			}
		} catch (Exception e) {
			logger.error("[InterfaceReqeustLogData] 요청(입력) 로그 데이터 세팅 장애발생", e);
		}			
	}
	
	@APIOperation(description="인터페이스 출력(결과) 로그 데이터 세팅")
	public <T1 extends AbstractSDO> void setInterfaceResultLogData(Object code, String responseOrgin, Long execEndMlisSecd) {
		logger.debug("[START] setInterfaceResultLogData({}) {} : {}", code, execEndMlisSecd, responseOrgin);
		
		try {
			
			if(interfaceLogSDO != null) {
				
				//종료시간 : TotlLapMlisSecd은 세팅하지 않는다. Strt와 End만 세팅하고 TotlLapMlisSecd은 getter에서 계산한다.
				if(execEndMlisSecd != null && execEndMlisSecd > OperateConstants.LONG_ZERO_VALUE) {
					logger.debug("# 종료시간 세팅 : InputSignature > execEndMlisSecd");
					interfaceLogSDO.setExecEndMlisSecd(execEndMlisSecd);
				}
				else if(endTimeMillis > OperateConstants.LONG_ZERO_VALUE) {
					logger.debug("# 종료시간 세팅 : Local > endTimeMillis");
					interfaceLogSDO.setExecEndMlisSecd(endTimeMillis);
				}
				else {
					logger.debug("# 종료시간 세팅 : 신규 currentTimeMillis");
					interfaceLogSDO.setExecEndMlisSecd(APIUtil.currentTimeMillis());
				}				
				
				interfaceLogSDO.setOutpTelg(responseOrgin);
				
				if(responseOrgin != null) {
					interfaceLogSDO.setOutpTelgSize(new BigDecimal(responseOrgin.getBytes(interfaceLogSDO.getEncoding()).length));
				}
				else {
					interfaceLogSDO.setOutpTelgSize(OperateConstants.BIGDECIMAL_ZERO_VALUE);
				}
				
				if(code != null && String.class.isAssignableFrom(code.getClass()) && ((String) code).equals(Integer.toString(MessageConstants.RESPONSE_CODE_1000))) {
					interfaceLogSDO.setSuccYn(OperateConstants.STR_Y);
				}
				else {
					interfaceLogSDO.setSuccYn(OperateConstants.STR_N);
				}
			}
			else {
				logger.debug("# 인터페이스 로그 SDO가 초기화 되지 않았습니다. [code : {}, execEndMlisSecd : {}, responseOrgin : {}]", code, execEndMlisSecd, responseOrgin);
			}
			
		} catch (Exception e) {
			logger.error("[setInterfaceResultLogData] 응답(결과) 로그 데이터 세팅 장애발생", e);
		}
		
		logger.debug("[END] setInterfaceResultLogData({}) {}", code, interfaceLogSDO);
	}
	
	@APIOperation(description="인터페이스 에러유형 세팅")
	public void setInterfaceErrorType(String errType) {
    	
		if(interfaceLogSDO != null && errType != null) {
			interfaceLogSDO.setErrType(errType); 
			Local.commonHeader().getInterfaceLogSDO().setSuccYn(OperateConstants.STR_N);
		}	
    }
    
	@APIOperation(description="인터페이스 에러내용 세팅")
	public void setInterfaceErrorCont(String errCont) {
    	
		if(interfaceLogSDO != null && errCont != null) {
			interfaceLogSDO.setErrCont(errCont);
			Local.commonHeader().getInterfaceLogSDO().setSuccYn(OperateConstants.STR_N);
		}	
    }
	
}
