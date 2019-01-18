package com.ezwel.htl.interfaces.commons.http.data;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.annotation.XmlElement;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
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
public class HttpConfigSDO extends AbstractSDO {

	private static final long serialVersionUID = 1L;

	@APIFields(description = "캐쉬 아이디")
	private String cacheId;

	@APIFields(description = "에이전트 이름")
	private String agentName;
	
	@APIFields(description = "체널 아이디", required=false)
	private String chanId;

	@APIFields(description = "HTTP 체널 그룹 아이디")
	private String httpAgentGroupId;
	
	@APIFields(description = "에이전트 아이디", required=true, httpHeader=true, headerName="http-agent-id")
	private String httpAgentId;
	
	@APIFields(description = "고객사(파트너) 코드 유형", required=false)
	private String patnCdType;

	@APIFields(description = "API 키(ezwel발급)", required=true, httpHeader=true, headerName="http-api-key")
	private String httpApiKey; 

	@APIFields(description = "API 시그니처", required=true, httpHeader=true, headerName="http-api-signature")
	private String httpApiSignature; 

	@APIFields(description = "요정시간(타임스탬프)", required=true, httpHeader=true, headerName="http-api-timestamp")
	private String httpApiTimestamp;
	
	@APIFields(description = "에이전트유형", required=false, httpHeader=true, headerName="http-agent-type")
	private String httpAgentType;	
	
	@APIFields(description = "체널코드", required=false, httpHeader=true, headerName="http-channel-cd")
	private String httpChannelCd;	
	
	@APIFields(description = "클라이언트ID", required=false, httpHeader=true, headerName="http-client-id")
	private String httpClientId;	
	
	@APIFields(description = "요청자ID", required=false, httpHeader=true, headerName="http-request-id")
	private String httpRequestId;	
	
	@APIFields(description = "http interface restfufl api uri", required=false)
	private String restURI;

	@APIFields(description = "interface receiver restfufl api uri", required=false)
	private String receiverRestURI;
	
	@APIFields(description = "HTTP 요청 파라메터 송신 여부")
	private boolean isDoOutput;
	
	@APIFields(description = "HTTP 응답 결과 수신 여부")
	private boolean isDoInput;
	
	@APIFields(description = "http connection timeout")
	private Integer connTimeout;
	
	@APIFields(description = "http read timeout")
	private Integer readTimeout;
	
	@APIFields(description = "HTTP HEADER PROPERTY")
	private Properties requestProperty;
	
	@APIFields(description = "HTTP 인터페이스 설명")
	private String description;
	
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

	@APIFields(description = "HTTP 인터페이스 호출 회수")
	private Integer callCount;
	
	@APIFields(description = "내부 인터페이스 여부(Adapter)")
	private boolean isEzwelInsideInterface;
	
	@APIFields(description = "접속 확인 여부")
	private boolean isConfirmConnect;
	
	//이하 로그기록에 의하여 추가된 필드
	@APIFields(description = "인터페이스 방향")
	private String ifReqtDirt;
	
	@APIFields(description = "입력(요청) 전문")
	private String inptTelg;
	
	@APIFields(description = "출력(결과) 전문")
	private String outpTelg;	

	@APIFields(description = "인터페이스 성공 여부")
	private String succYn;
	
	@APIFields(description = "인터페이스 에러 유형")
	private String errType;
	
	@APIFields(description = "인터페이스 에러 내용")
	private String errCont;
	
	@APIFields(description = "Request Header Properties")
	private Map<String, String> requestProperties;
	
	public HttpConfigSDO() {
		this.reset();
	}
	
	private void reset() {
		cacheId = null;
		agentName = null;
		chanId = null;
		httpAgentGroupId = null;
		isDoOutput = true;
		isDoInput = true;
		restURI = null;
		receiverRestURI = null;
		connTimeout = 3000;
		readTimeout = 3000;
		requestProperty = null;
		httpApiKey = null;
		httpApiSignature = null;
		httpApiTimestamp = APIUtil.getTimeStamp();
		httpAgentType = null;	
		httpChannelCd = null;	
		httpClientId = null;	
		httpRequestId = OperateConstants.GUEST_ID;			
		httpAgentId = null;
		patnCdType = null;
		encoding = OperateConstants.DEFAULT_ENCODING;
		writeEncoding = OperateConstants.DEFAULT_ENCODING;
		readEncoding = OperateConstants.DEFAULT_ENCODING;
		responseCode = -1;
		responseException = null;
		description = null;
		lapTimeMillis = OperateConstants.LONG_ZERO_VALUE;
		callCount = OperateConstants.INTEGER_ZERO_VALUE;
		isEzwelInsideInterface = false;
		isConfirmConnect = true;
		//이하 로그기록에 의하여 추가된 필드
		ifReqtDirt = null;
		inptTelg = null;
		outpTelg = null;
		succYn = null;
		errType = null;
		errCont = null;		
		requestProperties = null;
	}
	
	public String getPatnCdType() {
		return patnCdType;
	}

	@XmlElement
	public void setPatnCdType(String patnCdType) {
		this.patnCdType = patnCdType;
	}

	public String getCacheId() {
		return cacheId;
	}

	public void setCacheId(String cacheId) {
		this.cacheId = cacheId;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getChanId() {
		return chanId;
	}

	@XmlElement
	public void setChanId(String chanId) {
		this.chanId = chanId;
	}

	public String getHttpAgentGroupId() {
		return httpAgentGroupId;
	}

	@XmlElement
	public void setHttpAgentGroupId(String httpAgentGroupId) {
		this.httpAgentGroupId = httpAgentGroupId;
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
	
	public String getReceiverRestURI() {
		return receiverRestURI;
	}

	@XmlElement
	public void setReceiverRestURI(String receiverRestURI) {
		this.receiverRestURI = receiverRestURI;
	}
	
	public Integer getConnTimeout() {
		return connTimeout;
	}

	@XmlElement
	public void setConnTimeout(Integer connTimeout) {
		this.connTimeout = connTimeout;
	}

	public Integer getReadTimeout() {
		return readTimeout;
	}

	@XmlElement
	public void setReadTimeout(Integer readTimeout) {
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

	public String getHttpAgentType() {
		return httpAgentType;
	}

	public void setHttpAgentType(String httpAgentType) {
		this.httpAgentType = httpAgentType;
	}

	public String getHttpChannelCd() {
		return httpChannelCd;
	}

	public void setHttpChannelCd(String httpChannelCd) {
		this.httpChannelCd = httpChannelCd;
	}

	public String getHttpClientId() {
		return httpClientId;
	}

	public void setHttpClientId(String httpClientId) {
		this.httpClientId = httpClientId;
	}

	public String getHttpRequestId() {
		return httpRequestId;
	}

	public void setHttpRequestId(String httpRequestId) {
		this.httpRequestId = httpRequestId;
	}

	public Integer getCallCount() {
		return callCount;
	}

	public void setCallCount(Integer callCount) {
		this.callCount = callCount;
	}

	public boolean isEzwelInsideInterface() {
		return isEzwelInsideInterface;
	}

	public void setEzwelInsideInterface(boolean isEzwelInsideInterface) {
		this.isEzwelInsideInterface = isEzwelInsideInterface;
	}

	public boolean isConfirmConnect() {
		return isConfirmConnect;
	}

	public void setConfirmConnect(boolean isConfirmConnect) {
		this.isConfirmConnect = isConfirmConnect;
	}

	public String getIfReqtDirt() {
		return ifReqtDirt;
	}

	public void setIfReqtDirt(String ifReqtDirt) {
		this.ifReqtDirt = ifReqtDirt;
	}

	public String getInptTelg() {
		return inptTelg;
	}

	public void setInptTelg(String inptTelg) {
		this.inptTelg = inptTelg;
	}

	public String getOutpTelg() {
		return outpTelg;
	}

	public void setOutpTelg(String outpTelg) {
		this.outpTelg = outpTelg;
	}

	public String getSuccYn() {
		return succYn;
	}

	public void setSuccYn(String succYn) {
		this.succYn = succYn;
	}

	public String getErrType() {
		return errType;
	}

	public void setErrType(String errType) {
		this.errType = errType;
	}

	public String getErrCont() {
		return errCont;
	}

	public void setErrCont(String errCont) {
		this.errCont = errCont;
	}

	public Map<String, String> getRequestProperties() {
		return requestProperties;
	}

	public void setRequestProperties(Map<String, String> requestProperties) {
		this.requestProperties = requestProperties;
	}

	public void addRequestProperties(String key, String value) {
		if(this.requestProperties == null) {
			this.requestProperties = new LinkedHashMap<String, String>();
		}
		
		this.requestProperties.put(key, value);
	}

}

