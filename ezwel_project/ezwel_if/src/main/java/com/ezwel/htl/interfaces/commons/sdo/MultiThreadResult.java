package com.ezwel.htl.interfaces.commons.sdo;

import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel(modelNames="멀티쓰레드 인터페이스 결과 코드/메시지")
public class MultiThreadResult {

	@APIFields(description = "code", required=true, maxLength=4)
	private String code;
	
	@APIFields(description = "message", maxLength=100)
	private String message;

	@APIFields(description = "RestAPI URI")
	private String restURI;

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

	public String getRestURI() {
		return restURI;
	}

	public void setRestURI(String restURI) {
		this.restURI = restURI;
	}
	
	
}
