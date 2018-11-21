package com.ezwel.htl.interfaces.service.data.voucherReg;


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
public class VoucherRegOutDTO extends AbstractDTO {

	@APIFields(description = "시설바우처번호등록 output code", required=true, maxLength=4)
	private String code;
	
	@APIFields(description = "시설바우처번호등록 output message", maxLength=100)
	private String message;

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

}
