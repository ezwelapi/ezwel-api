package com.ezwel.htl.interfaces.service.dto.allReg;

import java.util.List;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractDTO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
public class AllRegOutDTO extends AbstractDTO {

	@APIFields(description = "전체시설일괄등록 output code", required=true, maxLength=4)
	private String code;
	
	@APIFields(description = "전체시설일괄등록 output message", maxLength=100)
	private String message;
	
	@APIFields(description = "전체시설일괄등록 output data")
	private List<AllRegDataOutDTO> data = null;

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

	public List<AllRegDataOutDTO> getData() {
		return data;
	}

	public void setData(List<AllRegDataOutDTO> data) {
		this.data = data;
	}

}
