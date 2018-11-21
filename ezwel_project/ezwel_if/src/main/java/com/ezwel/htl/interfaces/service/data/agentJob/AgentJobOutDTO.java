package com.ezwel.htl.interfaces.service.data.agentJob;

import java.util.List;


import com.ezwel.htl.interfaces.commons.abstracts.AbstractDTO;



import com.ezwel.htl.interfaces.commons.annotation.APIFields;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
public class AgentJobOutDTO extends AbstractDTO {

	@APIFields(description = "주문대사(제휴사) output code", required=true, maxLength=4)
	private String code;
	
	@APIFields(description = "주문대사(제휴사) output message", maxLength=100)
	private String message;
	
	@APIFields(description = "주문대사(제휴사) output reserves")
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
