package com.ezwel.htl.interfaces.service.dto.roomRead;


import com.ezwel.htl.interfaces.commons.abstracts.AbstractDTO;



import com.ezwel.htl.interfaces.commons.annotation.APIFields;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author ypjeon@ebsolution.co.kr
 * @date 2018. 11. 13.
 */
public class RoomReadPenaltyOutDTO extends AbstractDTO {

	@APIFields(description = "객실정보조회 output penalty 적용시작일", required=true, maxLength=8)
	private String applyBgnDate;
	
	@APIFields(description = "객실정보조회 output penalty 수수료율", required=true)
	private Integer cancelFeeRate;
	
	@APIFields(description = "객실정보조회 output penalty 수수료금액", required=true)
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
