package com.ezwel.htl.interfaces.commons.exception;

import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.thread.Local;


public class RunTimeException extends RuntimeException {

	static final long serialVersionUID = 5930391908933291621L;

    private Object arguments[];
    
    private Integer resultCode;
    
    protected void init(){
    	resultCode = MessageConstants.RESPONSE_CODE_1000;
    }
    
    public RunTimeException(String message) {
        super(message);
    }

    public RunTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public RunTimeException(Throwable cause) {
        super(cause);
    }

	public Object[] getArguments() {
		return arguments;
	}

	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		Local.commonHeader().setResultCode(resultCode);
		this.resultCode = resultCode;
		
	}
}
