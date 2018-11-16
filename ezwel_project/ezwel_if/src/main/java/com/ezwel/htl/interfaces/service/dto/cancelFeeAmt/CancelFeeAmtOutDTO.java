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
public class CancelFeeAmtOutDTO extends AbstractEntity {
	
	@APIFields(description = "취소수수료계산 output code", required=true, maxLength=4)
	private String code;
	
	@APIFields(description = "취소수수료계산 output message", maxLength=100)
	private String message;
	
	@APIFields(description = "취소수수료계산 output data")
	private CancelFeeAmtDataOutDTO data;

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

	public CancelFeeAmtDataOutDTO getData() {
		return data;
	}

	public void setData(CancelFeeAmtDataOutDTO data) {
		this.data = data;
	}

}
