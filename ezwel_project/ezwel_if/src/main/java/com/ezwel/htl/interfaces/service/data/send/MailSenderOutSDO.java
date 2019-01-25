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

@APIModel
public class MailSenderOutSDO extends AbstractSDO {
	

	@APIFields(description = "메일발송 성공여부", required=false)
	private boolean success;
	

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
