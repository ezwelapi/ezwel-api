package com.ezwel.htl.interfaces.commons.http.data;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractDTO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;

/**
 * <pre>
 * Http 인터페이스 API용 DTO  
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */
@APIModel
public class UserAgentDTO extends AbstractDTO {

	@APIFields(description = "에이전트 아이디", required=true, httpHeader=true, headerName="http-agent-id")
	private String httpAgentId;
	
	@APIFields(description = "에이전트 그룹 아이디")
	private String httpAgentGroupId;

	@APIFields(description = "에이전트유형", required=true, httpHeader=true)
	private String httpAgentType;	
	
	@APIFields(description = "체널코드", required=true, httpHeader=true)
	private String httpChannelCd;	
	
	@APIFields(description = "클라이언트ID", required=true, httpHeader=true)
	private String httpClientId;	
	
	@APIFields(description = "요청자ID", required=true, httpHeader=true)
	private String httpRequestId;	
	
	@APIFields(description = "http connection timeout")
	private int connTimeout;
	
	@APIFields(description = "http read timeout")
	private int readTimeout;
	
	
	public UserAgentDTO() {
		this.reset();
	}
	
	private void reset() {
		httpAgentId = null;
		httpAgentGroupId = null;
		connTimeout = OperateConstants.INTEGER_MINUS_ONE;
		readTimeout = OperateConstants.INTEGER_MINUS_ONE;
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

	public int getConnTimeout() {
		return connTimeout;
	}

	public void setConnTimeout(int connTimeout) {
		this.connTimeout = connTimeout;
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

	public void setConnTimeout(Integer connTimeout) {
		this.connTimeout = connTimeout;
	}

	public void setReadTimeout(Integer readTimeout) {
		this.readTimeout = readTimeout;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}
	
	
}
