package com.ezwel.htl.interfaces.service.dto.view;

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
public class ViewOutDTO extends AbstractDTO {

	@APIFields(description = "예약내역조회 output code", required=true, maxLength=4)
	private String code;
	
	@APIFields(description = "예약내역조회 output message", maxLength=100)
	private String message;
	
	@APIFields(description = "예약내역조회 output data")
	private ViewDataOutDTO data;

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

	public ViewDataOutDTO getData() {
		return data;
	}

	public void setData(ViewDataOutDTO data) {
		this.data = data;
	}

}