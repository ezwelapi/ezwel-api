package com.ezwel.htl.interfaces.service.dto.orderCancelReq;


import com.ezwel.htl.interfaces.commons.abstracts.AbstractDTO;



import com.ezwel.htl.interfaces.commons.annotation.APIFields;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author ypjeon@ebsolution.co.kr
 * @date 2018. 11. 13.
 */
public class OrderCancelReqOutDTO extends AbstractDTO {

	@APIFields(description = "주문취소요청 output code", required=true, maxLength=4)
	private String code;
	
	@APIFields(description = "주문취소요청 output message", maxLength=100)
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
