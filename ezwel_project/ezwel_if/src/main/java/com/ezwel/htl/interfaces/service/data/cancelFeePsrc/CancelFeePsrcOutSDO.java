package com.ezwel.htl.interfaces.service.data.cancelFeePsrc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 13.
 */
@APIModel
@Data
@EqualsAndHashCode(callSuper=true)
public class CancelFeePsrcOutSDO extends AbstractDTO {

	@APIFields(description = "취소수수료규정 output code", required=true, maxLength=4)
	private String code;
	
	@APIFields(description = "취소수수료규정 output message", maxLength=100)
	private String message;
	
	@APIFields(description = "취소수수료규정 output data")
	private CancelFeePsrcDataOutSDO data;

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

	public CancelFeePsrcDataOutSDO getData() {
		return data;
	}

	public void setData(CancelFeePsrcDataOutSDO data) {
		this.data = data;
	}

}