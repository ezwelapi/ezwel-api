package com.ezwel.htl.interfaces.service.data.cancelFeePsrc;


import com.ezwel.htl.interfaces.commons.abstracts.AbstractDTO;



import com.ezwel.htl.interfaces.commons.annotation.APIFields;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 13.
 */

public class CancelFeePsrcPenaltyOutDTO extends AbstractDTO {



	
	@APIFields(description = "취소수수료규정 output 적용시작일", required=true, maxLength=8)
	private String applyBgnDate;
	
	@APIFields(description = "취소수수료규정 output 수수료율")
	private Integer cancelFeeRate;
	
	@APIFields(description = "취소수수료규정 output 수수료금액")
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