package com.ezwel.htl.interfaces.service.dto.saleStop;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author ypjeon@ebsolution.co.kr
 * @date 2018. 11. 13.
 */
public class SaleStopOutDTO extends AbstractEntity {

	@APIFields(description = "시설판매중지설정 output code", required=true, maxLength=4)
	private String code;
	
	@APIFields(description = "시설판매중지설정 output message", maxLength=100)
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
