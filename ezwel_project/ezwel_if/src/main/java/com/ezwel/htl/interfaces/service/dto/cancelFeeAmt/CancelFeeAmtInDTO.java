package com.ezwel.htl.interfaces.service.dto.cancelFeeAmt;


import com.ezwel.htl.interfaces.commons.abstracts.AbstractDTO;



import com.ezwel.htl.interfaces.commons.annotation.APIFields;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
public class CancelFeeAmtInDTO extends AbstractDTO {

	@APIFields(description = "취소수수료계산 Input 주문번호", required=true, maxLength=100)
	private String rsvNo;

	public String getRsvNo() {
		return rsvNo;
	}

	public void setRsvNo(String rsvNo) {
		this.rsvNo = rsvNo;
	}

}
