package com.ezwel.htl.interfaces.server.commons.sdo;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel
public class ExceptionSDO extends AbstractSDO {

	private static final long serialVersionUID = 1L;

	@APIFields(description = "결과 코드", required=true, maxLength=4)
	private String code;
	
	@APIFields(description = "결과 메시지", maxLength=100)
	private String message;

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
}
