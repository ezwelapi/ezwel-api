package com.ezwel.htl.interfaces.service.dto.cancelFeePsrc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractDTO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 13.
 */
public class CancelFeePsrcOutDTO extends AbstractDTO {

	@APIFields(description = "취소수수료규정 output code", required=true, maxLength=4)
	private String code;
	
	@APIFields(description = "취소수수료규정 output message", maxLength=100)
	private String message;
	
	@APIFields(description = "취소수수료규정 output data")
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