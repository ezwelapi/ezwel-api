package com.ezwel.htl.interfaces.service.dto.cancelFeePsrc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 13.
 */
public class CancelFeePsrcOutDTO extends AbstractEntity {

	private String code;
	private String message;
	private CancelFeePsrcDataOutDTO data;

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

	public CancelFeePsrcDataOutDTO getData() {
		return data;
	}

	public void setData(CancelFeePsrcDataOutDTO data) {
		this.data = data;
	}

}