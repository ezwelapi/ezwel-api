package com.ezwel.htl.interfaces.service.data.sddSearch;

import java.util.List;


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
public class SddSearchOutDTO extends AbstractDTO {

	@APIFields(description = "당일특가검색 output code", required=true, maxLength=4)
	private String code;
	
	@APIFields(description = "당일특가검색 output message", maxLength=100)
	private String message;
	
	@APIFields(description = "당일특가검색 output data")
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