package com.ezwel.htl.interfaces.service.dto.cancelFeeAmt;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 13.
 */
public class CancelFeeAmtDataOutDTO extends AbstractEntity {

	private String rsvNo;
	private Integer cancelFeePrice;
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
