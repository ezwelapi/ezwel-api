package com.ezwel.htl.interfaces.commons.http.dto;

import java.util.Properties;

import javax.xml.bind.annotation.XmlElement;

import com.ezwel.htl.interfaces.commons.abstracts.APIObject;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.constants.IOperateCode;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;

/**
 * <pre>
 * Http 인터페이스 API용 DTO  
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */
@APIModel
public class HttpConfigDTO extends APIObject {

	@APIFields(description = "HTTP 체널 아이디", required=true)
	private String chanId;
	
	@APIFields(description = "HTTP 요청 파라메터 송신 여부")
	private boolean isDoOutput;
	
	@APIFields(description = "HTTP 응답 결과 수신 여부")
	private boolean isDoInput;
	
	@APIFields(description = "http interface restfufl api uri", required=true)
	private String restURI;
	
	@APIFields(description = "http connection timeout")
	private int connTimeout;
	
	@APIFields(description = "http read timeout")
	private int readTimeout;
	
	@APIFields(description = "HTTP HEADER PROPERTY")
	private Properties requestProperty;
	
	@APIFields(description = "HTTP 인터페이스 설명")
	private String description;
	
	@APIFields(description = "API 키(ezwel발급)", required=true, httpHeader=true, headerName="http-api-key")
	private String httpApiKey; 
	
	@APIFields(description = "에이전트 아이디", required=true, httpHeader=true, headerName="http-agent-id")
	private String httpAgentId;

	@APIFields(description = "API 시그니처", required=true, httpHeader=true, headerName="http-api-signature")
	private String httpApiSignature; 

	@APIFields(description = "요정시간(타임스탬프)", required=true, httpHeader=true, headerName="http-api-timestamp")
	private String httpApiTimestamp;
	
	@APIFields(description = "I/O 인코딩 (default:UTF-8)")
	private String encoding;
	
	@APIFields(description = "Output(request) 인코딩 (default:UTF-8)")
	private String writeEncoding;
	
	@APIFields(description = "Input(response) 인코딩 (default:UTF-8)")
	private String readEncoding;
	
	@APIFields(description = "HTTP URL 응답 코드")
	private Integer responseCode;
	
	@APIFields(description = "HTTP URL 응답 에러 메시지")
	private String responseException;
	
	@APIFields(description = "HTTP 인터페이스 API 총 실행시간")
	private Long lapTimeMillis;
	
	public HttpConfigDTO() {
		this.reset();
	}
	
	private void reset() {
		chanId = null;
		isDoOutput = true;
		isDoInput = true;
		restURI = null;
		connTimeout = 3000;
		readTimeout = 3000;
		requestProperty = null;
		httpApiKey = null;
		httpApiSignature = null;
		httpApiTimestamp = (Long.toString(APIUtil.currentTimeMillis() / 100));
		httpAgentId = null;
		encoding = IOperateCode.DEFAULT_ENCODING;
		writeEncoding = IOperateCode.DEFAULT_ENCODING;
		readEncoding = IOperateCode.DEFAULT_ENCODING;
		responseCode = -1;
		responseException = null;
		description = null;
		lapTimeMillis = IOperateCode.LONG_ZERO_VALUE;
	}

	public String getChanId() {
		return chanId;
	}

	public void setChanId(String chanId) {
		this.chanId = chanId;
	}

	public boolean isDoOutput() {
		return isDoOutput;
	}

	public void setDoOutput(boolean isDoOutput) {
		this.isDoOutput = isDoOutput;
	}

	public boolean isDoInput() {
		return isDoInput;
	}

	public void setDoInput(boolean isDoInput) {
		this.isDoInput = isDoInput;
	}


	public String getRestURI() {
		return restURI;
	}

	@XmlElement
	public void setRestURI(String restURI) {
		this.restURI = restURI;
	}
	
	public int getConnTimeout() {
		return connTimeout;
	}

	@XmlElement
	public void setConnTimeout(int connTimeout) {
		this.connTimeout = connTimeout;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	@XmlElement
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public Properties getRequestProperty() {
		return requestProperty;
	}

	public void setRequestProperty(Properties requestProperty) {
		this.requestProperty = requestProperty;
	}

	public void addRequestProperty(String key, String value) {
		if(this.requestProperty == null) {
			this.requestProperty = new Properties();
		}
		this.requestProperty.put(key, value);
	}
	
	public String getHttpApiKey() {
		return httpApiKey;
	}

	@XmlElement
	public void setHttpApiKey(String httpApiKey) {
		this.httpApiKey = httpApiKey;
	}

	public String getHttpApiSignature() {
		return httpApiSignature;
	}

	public void setHttpApiSignature(String httpApiSignature) {
		this.httpApiSignature = httpApiSignature;
	}

	public String getHttpApiTimestamp() {
		return httpApiTimestamp;
	}

	public void setHttpApiTimestamp(String httpApiTimestamp) {
		this.httpApiTimestamp = httpApiTimestamp;
	}

	public String getHttpAgentId() {
		return httpAgentId;
	}

	@XmlElement
	public void setHttpAgentId(String httpAgentId) {
		this.httpAgentId = httpAgentId;
	}

	public String getEncoding() {
		return encoding;
	}

	@XmlElement
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseException() {
		return responseException;
	}

	public void setResponseException(String responseException) {
		this.responseException = responseException;
	}

	public Long getLapTimeMillis() {
		return lapTimeMillis;
	}

	public void setLapTimeMillis(Long lapTimeMillis) {
		this.lapTimeMillis = lapTimeMillis;
	}

	public String getWriteEncoding() {
		return writeEncoding;
	}

	@XmlElement
	public void setWriteEncoding(String writeEncoding) {
		this.writeEncoding = writeEncoding;
	}

	public String getReadEncoding() {
		return readEncoding;
	}

	@XmlElement
	public void setReadEncoding(String readEncoding) {
		this.readEncoding = readEncoding;
	}

	public String getDescription() {
		return description;
	}

	@XmlElement
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
