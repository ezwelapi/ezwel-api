package com.ezwel.htl.interfaces.commons.exception;

import com.ezwel.htl.interfaces.commons.annotation.APIModel;

/**
 * <pre>
 *  API Exception Message DTO
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */
@APIModel
public class ExceptionMessage {

	private String message;
	
	private String cause;
	
	private String stackTrace;
	
	private int resultCode;
	
	public ExceptionMessage(){
		this.reset();
	}
	
	private void reset(){
		message = "";
		cause = "";
		stackTrace = "";
		resultCode = -1;
	}

	public String getMessage() {
		return message;
	}

	public String getCause() {
		return cause;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	
	
}
