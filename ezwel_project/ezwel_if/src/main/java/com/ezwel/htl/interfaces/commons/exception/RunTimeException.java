package com.ezwel.htl.interfaces.commons.exception;

import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;

@APIType(description="런타임 익셉셥 클래스")
public class RunTimeException extends RuntimeException {

	static final long serialVersionUID = 5930391908933291621L;

    private Object arguments[];
    
    private Integer resultCode;
    
    protected void init(){
    	resultCode = MessageConstants.RESPONSE_CODE_1000;
    }
    
    public RunTimeException(String message) {
        super(message);
        if(APIUtil.isEmpty(Local.commonHeader().getMessage())) {
        	Local.commonHeader().setMessage(message);
        }
    }

    public RunTimeException(String message, Throwable cause) {
        super(message, cause);
        
        if(APIUtil.isEmpty(Local.commonHeader().getMessage())) {
        	Local.commonHeader().setMessage(message);
        }
        
        if(Local.commonHeader().getThrowable() == null) {
        	Local.commonHeader().setThrowable(cause);
        }
    }
    
    public RunTimeException(Throwable cause) {
        super(cause);
        
        if(Local.commonHeader().getThrowable() == null) {
        	Local.commonHeader().setThrowable(cause);
        }        
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
	
	public String getResultCodeString() {
		return (resultCode != null ? Integer.toString(resultCode) : "");
	}
	
	public String getMessages() {
		return APIUtil.NVL(Local.commonHeader().getMessage(), super.getMessage());
	}
	
    
}
