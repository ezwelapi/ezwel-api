package com.ezwel.htl.interfaces.service.data.agentJob;


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
public class AgentJobInSDO extends AbstractSDO {

	@APIFields(description = "주문대사(제휴사) Input 주문번호", required=true, maxLength=100)
	private String rsvNo;
	
	@APIFields(description = "주문대사(제휴사) Input 주문시작일", required=true, maxLength=8)
	private String rsvDateStart;
	
	@APIFields(description = "주문대사(제휴사) Input 주문종료일", required=true, maxLength=8)
	private String rsvDateEnd;

	public String getRsvNo() {
		return rsvNo;
	}

	public void setRsvNo(String rsvNo) {
		this.rsvNo = rsvNo;
	}

	public String getRsvDateStart() {
		return rsvDateStart;
	}

	public void setRsvDateStart(String rsvDateStart) {
		this.rsvDateStart = rsvDateStart;
	}

	public String getRsvDateEnd() {
		return rsvDateEnd;
	}

	public void setRsvDateEnd(String rsvDateEnd) {
		this.rsvDateEnd = rsvDateEnd;
	}

}
