package com.ezwel.htl.interfaces.service.data.sddSearch;

import java.util.List;


import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import lombok.Data;
import lombok.EqualsAndHashCode;



import com.ezwel.htl.interfaces.commons.annotation.APIFields;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author ypjeon@ebsolution.co.kr
 * @date 2018. 11. 13.
 */
@APIModel
@Data
@EqualsAndHashCode(callSuper=true)
public class SddSearchOutSDO extends AbstractSDO {

	@APIFields(description = "당일특가검색 output code", required=true, maxLength=4)
	private String code;
	
	@APIFields(description = "당일특가검색 output message", maxLength=100)
	private String message;
	
	@APIFields(description = "당일특가검색 output data")
	private List<SddSearchDataOutSDO> data = null;

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

	public List<SddSearchDataOutSDO> getData() {
		return data;
	}

	public void setData(List<SddSearchDataOutSDO> data) {
		this.data = data;
	}

}