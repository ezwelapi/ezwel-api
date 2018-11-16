package com.ezwel.htl.interfaces.service.dto.faclSearch;

import java.util.List;

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
public class FaclSearchOutDTO extends AbstractEntity {

	@APIFields(description = "시설검색 output code", required=true, maxLength=4)
	private String code;
	
	@APIFields(description = "시설검색 output message", maxLength=100)
	private String message;
	
	@APIFields(description = "시설검색 output data")
	private List<FaclSearchDataOutDTO> data = null;

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

	public List<FaclSearchDataOutDTO> getData() {
		return data;
	}

	public void setData(List<FaclSearchDataOutDTO> data) {
		this.data = data;
	}

}