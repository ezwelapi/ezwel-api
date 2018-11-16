package com.ezwel.htl.interfaces.service.dto.rsvHistSend;


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

public class RsvHistSendOutDTO extends AbstractDTO {



	
	@APIFields(description = "결제완료내역전송 output code", required=true, maxLength=4)
	private String code;
	
	@APIFields(description = "결제완료내역전송 output message", maxLength=100)
	private String message;
	
	@APIFields(description = "결제완료내역전송 output 주문번호(이지웰)", required=true, maxLength=100)
	private String rsvNo;
	
	@APIFields(description = "결제완료내역전송 output 주문번호(제휴사)", required=true, maxLength=100)
	private String otaRsvNo;

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
	
	public String getRsvNo() {
		return rsvNo;
	}

	public void setRsvNo(String rsvNo) {
		this.rsvNo = rsvNo;
	}
	
	public String getOtaRsvNo() {
		return otaRsvNo;
	}

	public void setOtaRsvNo(String otaRsvNo) {
		this.otaRsvNo = otaRsvNo;
	}

}
