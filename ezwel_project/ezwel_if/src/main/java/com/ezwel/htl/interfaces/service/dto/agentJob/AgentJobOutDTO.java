package com.ezwel.htl.interfaces.service.dto.agentJob;

import java.util.List;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
public class AgentJobOutDTO extends AbstractEntity {

	private String code;
	private String message;
	private List<AgentJobReservesOutDTO> reserves = null;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<AgentJobReservesOutDTO> getReserves() {
		return reserves;
	}

	public void setReserves(List<AgentJobReservesOutDTO> reserves) {
		this.reserves = reserves;
	}

}
