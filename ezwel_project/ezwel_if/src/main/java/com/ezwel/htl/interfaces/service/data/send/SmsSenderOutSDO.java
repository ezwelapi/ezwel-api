package com.ezwel.htl.interfaces.service.data.send;


import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author ypjeon@ebsolution.co.kr
 * @date 2018. 12. 13.
 */

@APIModel(description="문자발송 정보")
public class SmsSenderOutSDO extends AbstractSDO {
	
	@APIFields(description = "문자발송 errorCode", required=false, maxLength=4)
	private String errorCode;
	
	@APIFields(description = "문자발송 errorMessage", required=false, maxLength=4)
	private String errorMessage;
	
	@APIFields(description = "문자발송 data", required=false, maxLength=4)
	private String data;
	
	@APIFields(description = "문자발송 성공여부", required=false)
	private boolean success;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
