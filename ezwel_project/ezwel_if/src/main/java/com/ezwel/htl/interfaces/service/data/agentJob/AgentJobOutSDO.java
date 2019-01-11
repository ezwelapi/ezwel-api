package com.ezwel.htl.interfaces.service.data.agentJob;

import java.util.ArrayList;
import java.util.List;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
@APIModel
@Data
@EqualsAndHashCode(callSuper=true)
public class AgentJobOutSDO extends AbstractSDO {

	@APIFields(description = "주문대사(제휴사) output code", required=true, maxLength=4)
	private String code;
	
	@APIFields(description = "주문대사(제휴사) output message", maxLength=100)
	private String message;

	@APIFields(description = "RestAPI URI")
	private String restURI;

	@APIFields(description = "트랜젝션 실행 개수")	
	private Integer txCount;
	
	@APIFields(description = "주문대사(제휴사) output reserves")
	private List<AgentJobReservesOutSDO> reserves = null;

	public String getRestURI() {
		return restURI;
	}

	public void setRestURI(String restURI) {
		this.restURI = restURI;
	}

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

	public List<AgentJobReservesOutSDO> getReserves() {
		return reserves;
	}

	public void setReserves(List<AgentJobReservesOutSDO> reserves) {
		this.reserves = reserves;
	}
	
	public void addReserves(AgentJobReservesOutSDO reserves) {
		if(this.reserves == null) {
			this.reserves = new ArrayList<AgentJobReservesOutSDO>();
		}
		this.reserves.add(reserves);
	}	

	public Integer getTxCount() {
		return txCount;
	}

	public void setTxCount(Integer txCount) {
		this.txCount = txCount;
	}

}
