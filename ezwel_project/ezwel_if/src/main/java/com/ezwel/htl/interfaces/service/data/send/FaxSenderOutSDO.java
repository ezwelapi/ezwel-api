package com.ezwel.htl.interfaces.service.data.send;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author ypjeon@ebsolution.co.kr
 * @date 2019.01.07
 */

public class FaxSenderOutSDO extends AbstractSDO {
	
	@APIFields(description = "팩스발송 성공여부", required=false)
	private boolean success;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}