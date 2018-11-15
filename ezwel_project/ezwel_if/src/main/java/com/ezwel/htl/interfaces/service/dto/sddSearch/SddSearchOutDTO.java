package com.ezwel.htl.interfaces.service.dto.sddSearch;

import java.util.List;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author ypjeon@ebsolution.co.kr
 * @date 2018. 11. 13.
 */
public class SddSearchOutDTO extends AbstractEntity {

	private String code;
	private String message;
	private List<SddSearchDataOutDTO> data = null;

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

	public List<SddSearchDataOutDTO> getData() {
		return data;
	}

	public void setData(List<SddSearchDataOutDTO> data) {
		this.data = data;
	}

}