package com.ezwel.htl.interfaces.service.data.faclSearch;

import java.util.List;


import com.ezwel.htl.interfaces.commons.abstracts.AbstractDTO;
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
public class FaclSearchOutSDO extends AbstractDTO {

	@APIFields(description = "시설검색 output code", required=true, maxLength=4)
	private String code;
	
	@APIFields(description = "시설검색 output message", maxLength=100)
	private String message;
	
	@APIFields(description = "시설검색 output data")
	private List<FaclSearchDataOutSDO> data = null;

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

	public List<FaclSearchDataOutSDO> getData() {
		return data;
	}

	public void setData(List<FaclSearchDataOutSDO> data) {
		this.data = data;
	}

}