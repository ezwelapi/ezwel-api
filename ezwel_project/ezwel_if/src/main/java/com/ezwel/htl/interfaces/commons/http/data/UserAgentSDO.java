package com.ezwel.htl.interfaces.commons.http.data;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

/**
 * <pre>
 * Http 인터페이스 API용 DTO  
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */
@APIModel
public class UserAgentSDO extends AbstractSDO {

	private static final long serialVersionUID = 1L;

	@APIFields(description = "에이전트 아이디", httpHeader=true, headerName="http-agent-id")
	private String httpAgentId;
	
	@APIFields(description = "에이전트 그룹 아이디")
	private String httpAgentGroupId;

	@APIFields(description = "에이전트유형", required=false, httpHeader=true)
	private String httpAgentType;	
	
	@APIFields(description = "체널코드", required=false, httpHeader=true)
	private String httpChannelCd;	
	
	@APIFields(description = "클라이언트ID", required=false, httpHeader=true)
	private String httpClientId;	
	
	@APIFields(description = "요청자ID", required=false, httpHeader=true)
	private String httpRequestId;	
	
	@APIFields(description = "http connection timeout")
	private Integer connTimeout;
	
	@APIFields(description = "http read timeout")
	private Integer readTimeout;
	
	
	public UserAgentSDO() {
		this.reset();
	}
	
	private void reset() {
		httpAgentId = null;
		httpAgentGroupId = null;
		connTimeout = null;
		readTimeout = null;
		httpAgentType = null;	
		httpChannelCd = null;	
		httpClientId = null;	
		httpRequestId = null;		
	}

	public String getHttpAgentGroupId() {
		return httpAgentGroupId;
	}

	public void setHttpAgentGroupId(String httpAgentGroupId) {
		this.httpAgentGroupId = httpAgentGroupId;
	}

	public String getHttpAgentId() {
		return httpAgentId;
	}

	public void setHttpAgentId(String httpAgentId) {
		this.httpAgentId = httpAgentId;
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

	public Integer getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(Integer readTimeout) {
		this.readTimeout = readTimeout;
	}

	public Integer getConnTimeout() {
		return connTimeout;
	}

	public void setConnTimeout(Integer connTimeout) {
		this.connTimeout = connTimeout;
	}

}
