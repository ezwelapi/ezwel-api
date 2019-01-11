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
public class VerificationSDO extends AbstractSDO {

	@APIFields(description = "에이전트 정보")
	private AgentInfoSDO agentInfoSDO;

	@APIFields(description = "체널 정보")
	private HttpConfigSDO httpConfigSDO;

	public AgentInfoSDO getAgentInfoSDO() {
		return agentInfoSDO;
	}

	public void setAgentInfoSDO(AgentInfoSDO agentInfoSDO) {
		this.agentInfoSDO = agentInfoSDO;
	}

	public HttpConfigSDO getHttpConfigSDO() {
		return httpConfigSDO;
	}

	public void setHttpConfigSDO(HttpConfigSDO httpConfigSDO) {
		this.httpConfigSDO = httpConfigSDO;
	}
	
	
}
