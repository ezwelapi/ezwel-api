package com.ezwel.htl.interfaces.service.dto.roomRead;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author ypjeon@ebsolution.co.kr
 * @date 2018. 11. 13.
 */
public class RoomReadPenaltyOutDTO extends AbstractEntity {

	private String applyBgnDate;
	private Integer cancelFeeRate;
	private Integer cancelFeePrice;

	public String getApplyBgnDate() {
		return applyBgnDate;
	}

	public void setApplyBgnDate(String applyBgnDate) {
		this.applyBgnDate = applyBgnDate;
	}

	public Integer getCancelFeeRate() {
		return cancelFeeRate;
	}

	public void setCancelFeeRate(Integer cancelFeeRate) {
		this.cancelFeeRate = cancelFeeRate;
	}

	public Integer getCancelFeePrice() {
		return cancelFeePrice;
	}

	public void setCancelFeePrice(Integer cancelFeePrice) {
		this.cancelFeePrice = cancelFeePrice;
	}

}
