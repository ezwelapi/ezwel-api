package com.ezwel.htl.interfaces.commons.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.abstracts.APIObject;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.constants.MessageCode;
import com.ezwel.htl.interfaces.commons.constants.OperateCode;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;

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
	
	@APIFields(description = "헤더 어노테이션 맵")
	private Map<String, Object> annotationMap;
	
	@APIFields(description = "헤더 오브젝트 맵")
	private Map<String, Object> objectMap;
	
	@APIFields(description = "헤더 프로퍼티")
	private Properties	properties;
	
	@APIFields(description = "거래코드", groupName = OperateCode.META_COMM_HEADER, isMarshalField = true)
	private String 	guid;
	
	@APIFields(description = "결과코드", groupName = OperateCode.META_COMM_HEADER, isMarshalField = true)
	private Integer	resultCode;
	
	@APIFields(description = "메시지", groupName = OperateCode.META_COMM_HEADER, isMarshalField = true)
	private String	message;

	@APIFields(description = "원인", groupName = OperateCode.META_COMM_HEADER, isMarshalField = true)
	private String	cause;
	
	@APIFields(description = "원인추적", groupName = OperateCode.META_COMM_HEADER, isMarshalField = true)
	private String	stackTrace;

	@APIFields(description = "실행시간", groupName = OperateCode.META_COMM_HEADER, isMarshalField = true)
	private long	lapTimeMillis;

	private String[]	arrayMessages;

	private List<String>	traceMessages;

	private long	startTimeMillis;
	
	private long	endTimeMillis;
	
	private String	receiveFormat;
	
	private String	encoding;
	
	private boolean	ruleValidate;
	
	private String elementRoot;
	
	private String controllerType;
	
	private String contentType;
	
	private String pagination;
	
	private String clientAddress;
	
	private boolean exec;
	
	private boolean isMultipartRequest;
	
	private Map<String, String[]> requestMap;
	
	public CommonHeader() {
		this.reset();
	}
	
	private void reset(){
		annotationMap = null;
		objectMap = null;
		properties = null;
		
		guid = "";
		startTimeMillis = OperateCode.LONG_ZERO_VALUE;
		endTimeMillis = OperateCode.LONG_MINUS_ONE;
		lapTimeMillis = OperateCode.LONG_MINUS_ONE;
		resultCode = MessageCode.RESPONSE_CODE_1000;
		message = "";
		cause = "";
		stackTrace = "";
		arrayMessages = null;
		traceMessages = null;
		receiveFormat = null;
		encoding = OperateCode.DEFAULT_ENCODING;
		ruleValidate = true;
		elementRoot = null;
		controllerType = null;
		contentType = null;
		pagination = null;
		clientAddress = null;
		exec = false;
		isMultipartRequest = false;
		requestMap = new LinkedHashMap<String, String[]>();
		runtimeHeader = null;
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
		if(this.resultCode != MessageCode.RESPONSE_CODE_1000 || endTimeMillis == OperateCode.LONG_MINUS_ONE) {
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
			logger.debug(" [SET COMMON HEADER MESSAGE] : " + message);
		}
		this.message = message;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public void addProperties(String key, String value) {
		if( properties == null ) properties = new Properties();
		this.properties.setProperty(key, (value == null ? OperateCode.MESSAGE_ISNULL : value));
	} 
	
	
	public void setLapTimeMillis(long lapTimeMillis) {
		this.lapTimeMillis = lapTimeMillis;
	}

	public long getLapTimeMillis() {
		if(this.lapTimeMillis == OperateCode.LONG_MINUS_ONE) this.lapTimeMillis = getEndTimeMillis() - getStartTimeMillis();
		return lapTimeMillis;
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
		if( this.traceMessages == null ) this.traceMessages = new ArrayList<String>();
		this.traceMessages.add(traceMessages);
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

	public String getReceiveFormat() {
		return receiveFormat;
	}

	public void setReceiveFormat(String receiveFormat) {
		this.receiveFormat = receiveFormat;
	}


	public boolean isRuleValidate() {
		return ruleValidate;
	}


	public void setRuleValidate(boolean ruleValidate) {
		this.ruleValidate = ruleValidate;
	}


	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		if(encoding != null && !encoding.isEmpty()) {
			this.encoding = encoding;
		}
	}

	public String getElementRoot() {
		return elementRoot;
	}


	public void setElementRoot(String elementRoot) {
		this.elementRoot = elementRoot;
	}

	public String getControllerType() {
		return controllerType;
	}
	
	public void setControllerType(String controllerType) {
		this.controllerType = controllerType;
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

	public String getPagination() {
		return pagination;
	}

	public void setPagination(String pagination) {
		this.pagination = pagination;
	}

	public boolean isExec() {
		return exec;
	}

	public void setExec(boolean exec) {
		this.exec = exec;
	}

	public boolean isMultipartRequest() {
		return isMultipartRequest;
	}

	public void setMultipartRequest(boolean isMultipartRequest) {
		this.isMultipartRequest = isMultipartRequest;
	}

	
}
