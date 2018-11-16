package com.ezwel.htl.interfaces.service.dto.rsvHistSend;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author ypjeon@ebsolution.co.kr
 * @date 2018. 11. 13.
 */
public class RsvHistSendOptionsInDTO extends AbstractEntity {

	@APIFields(description = "결제완료내역전송 Input option 옵션주문번호(이지웰)", maxLength=100)
	private String rsvOptNo;
	
	@APIFields(description = "결제완료내역전송 Input option 옵션코드", maxLength=100)
	private String optNo;
	
	@APIFields(description = "결제완료내역전송 Input option 옵션명", maxLength=100)
	private String optName;
	
	@APIFields(description = "결제완료내역전송 Input option 옵션가격")
	private Integer optPrice;
	
	@APIFields(description = "결제완료내역전송 Input option 최대선택가능수량")
	private Integer optCountMax;

	public String getRsvOptNo() {
		return rsvOptNo;
	}
	
	public void setRsvOptNo(String rsvOptNo) {
		this.rsvOptNo = rsvOptNo;
	}

	public String getOptNo() {
		return optNo;
	}

	public void setOptNo(String optNo) {
		this.optNo = optNo;
	}

	public String getOptName() {
		return optName;
	}

	public void setOptName(String optName) {
		this.optName = optName;
	}

	public Integer getOptPrice() {
		return optPrice;
	}

	public void setOptPrice(Integer optPrice) {
		this.optPrice = optPrice;
	}

	public Integer getOptCountMax() {
		return optCountMax;
	}

	public void setOptCountMax(Integer optCountMax) {
		this.optCountMax = optCountMax;
	}
	
}
