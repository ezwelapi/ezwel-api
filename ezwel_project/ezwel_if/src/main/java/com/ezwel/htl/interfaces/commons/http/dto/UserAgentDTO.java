package com.ezwel.htl.interfaces.commons.http.dto;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractDTO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.constants.OperateCode;

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

	@APIFields(description = "http connection timeout")
	private Integer connTimeout;
	
	@APIFields(description = "http read timeout")
	private Integer readTimeout;
	
	
	public UserAgentDTO() {
		this.reset();
	}
	
	private void reset() {
		httpAgentId = null;
		httpAgentGroupId = null;
		connTimeout = OperateCode.INTEGER_MINUS_ONE;
		readTimeout = OperateCode.INTEGER_MINUS_ONE;
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

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}
	
	
}
