package com.ezwel.htl.interfaces.server.commons.sdo;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel
public class AgentApiKeySDO extends AbstractSDO {

	@APIFields(description = "졔휴사 명", required=true)
	private String agentName;
	
	@APIFields(description = "에이젼트 ID", required=true)
	private String httpAgentId;

	@APIFields(description = "인사이드 API KEY")
	private String insideKey;
	
	@APIFields(description = "아웃사이드 API KEY")
	private String outsideKey;
	
	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getHttpAgentId() {
		return httpAgentId;
	}

	public void setHttpAgentId(String httpAgentId) {
		this.httpAgentId = httpAgentId;
	}

	public String getInsideKey() {
		return insideKey;
	}

	public void setInsideKey(String insideKey) {
		this.insideKey = insideKey;
	}

	public String getOutsideKey() {
		return outsideKey;
	}

	public void setOutsideKey(String outsideKey) {
		this.outsideKey = outsideKey;
	}

	
	
}
