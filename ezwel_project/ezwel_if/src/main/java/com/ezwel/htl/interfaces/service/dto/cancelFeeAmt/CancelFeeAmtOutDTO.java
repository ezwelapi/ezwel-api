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
public class CancelFeeAmtOutDTO extends AbstractEntity {

	private String code;
	private String message;
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
