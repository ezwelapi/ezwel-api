package com.ezwel.htl.interfaces.service.dto.orderCancelReq;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;

/**
 * <pre>
 * 
 * </pre>
 * @author ypjeon@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
public class OrderCancelReqInDTO extends AbstractEntity {

	private String rsvNo;
	private String otaRsvNo;
	private Integer rsvPrice;
	private Integer cancelCharge;

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
	
	public Integer getRsvPrice() {
		return rsvPrice;
	}

	public void setRsvPrice(Integer rsvPrice) {
		this.rsvPrice = rsvPrice;
	}
	
	public Integer getCancelCharge() {
		return cancelCharge;
	}

	public void setCancelCharge(Integer cancelCharge) {
		this.cancelCharge = cancelCharge;
	}

}
