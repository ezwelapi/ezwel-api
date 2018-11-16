package com.ezwel.htl.interfaces.service.dto.cancelFeeAmt;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 13.
 */
public class CancelFeeAmtDataOutDTO extends AbstractEntity {
	
	@APIFields(description = "취소수수료계산 output 주문번호", required=true, maxLength=100)
	private String rsvNo;
	
	@APIFields(description = "취소수수료계산 output 취소수수료(원)", required=true)
	private Integer cancelFeePrice;
	
	@APIFields(description = "취소수수료계산 output 대체텍스트", required=true, maxLength=2000)
	private String cancelFeeText;

	public String getRsvNo() {
		return rsvNo;
	}

	public void setRsvNo(String rsvNo) {
		this.rsvNo = rsvNo;
	}

	public Integer getCancelFeePrice() {
		return cancelFeePrice;
	}

	public void setCancelFeePrice(Integer cancelFeePrice) {
		this.cancelFeePrice = cancelFeePrice;
	}

	public String getCancelFeeText() {
		return cancelFeeText;
	}

	public void setCancelFeeText(String cancelFeeText) {
		this.cancelFeeText = cancelFeeText;
	}
}
