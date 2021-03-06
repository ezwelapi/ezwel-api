package com.ezwel.htl.interfaces.commons.http.data;

import javax.xml.bind.annotation.XmlElement;

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
public class AgentInfoSDO extends AbstractSDO {

	@APIFields(description = "에이전트 이름")
	private String agentName;

	@APIFields(description = "에이전트 아이디")
	private String httpAgentId;
	
	@APIFields(description = "고객사(파트너) 코드 유형")
	private String patnCdType;
	
	@APIFields(description = "insideApiKey")
	private String insideApiKey;

	@APIFields(description = "outsideApiKey")
	private String outsideApiKey;
	
	@APIFields(description = "제휴사 암복호화 16자리 대칭키")
	private String cryptKey;
	
	public AgentInfoSDO() {
		this.reset();
	}
	
	private void reset() {
		agentName = null;
		httpAgentId = null;
		patnCdType = null;
		insideApiKey = null;
		outsideApiKey = null;
		cryptKey = null;
	}

	public String getAgentName() {
		return agentName;
	}

	@XmlElement
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getHttpAgentId() {
		return httpAgentId;
	}

	@XmlElement
	public void setHttpAgentId(String httpAgentId) {
		this.httpAgentId = httpAgentId;
	}

	public String getInsideApiKey() {
		return insideApiKey;
	}

	@XmlElement
	public void setInsideApiKey(String insideApiKey) {
		this.insideApiKey = insideApiKey;
	}

	public String getOutsideApiKey() {
		return outsideApiKey;
	}

	@XmlElement
	public void setOutsideApiKey(String outsideApiKey) {
		this.outsideApiKey = outsideApiKey;
	}

	public String getPatnCdType() {
		return patnCdType;
	}

	@XmlElement
	public void setPatnCdType(String patnCdType) {
		this.patnCdType = patnCdType;
	}

	public String getCryptKey() {
		return cryptKey;
	}

	@XmlElement
	public void setCryptKey(String cryptKey) {
		this.cryptKey = cryptKey;
	}

	
}
