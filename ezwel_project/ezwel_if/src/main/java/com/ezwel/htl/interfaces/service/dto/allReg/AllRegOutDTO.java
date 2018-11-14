package com.ezwel.htl.interfaces.service.dto.allReg;

import java.util.List;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
public class AllRegOutDTO extends AbstractEntity {

	private String code;
	private String message;
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
