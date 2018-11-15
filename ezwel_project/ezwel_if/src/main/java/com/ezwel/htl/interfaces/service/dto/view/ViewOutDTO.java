package com.ezwel.htl.interfaces.service.dto.view;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author ypjeon@ebsolution.co.kr
 * @date 2018. 11. 13.
 */
public class ViewOutDTO extends AbstractEntity {

	private String code;
	private String message;
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