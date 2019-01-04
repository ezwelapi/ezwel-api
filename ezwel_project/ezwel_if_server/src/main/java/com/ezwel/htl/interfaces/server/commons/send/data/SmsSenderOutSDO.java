package com.ezwel.htl.interfaces.server.commons.send.data;


import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author ypjeon@ebsolution.co.kr
 * @date 2018. 11. 13.
 */
@APIModel
public class SmsSenderOutSDO extends AbstractSDO {

	@APIFields(description = "문자발송 output 결과코드", required=true)
	private Integer errorCode;
	
	@APIFields(description = "문자발송 output 에러 메세지")
	private String errorMessage;

	@APIFields(description = "문자발송 output 메세지 고유번호")
	private String data;

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
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

}