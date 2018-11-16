package com.ezwel.htl.interfaces.service.dto.orderCancelReq;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;

/**
 * <pre>
 * 
 * </pre>
 * @author ypjeon@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
public class OrderCancelReqInDTO extends AbstractEntity {

	@APIFields(description = "주문취소요청 Input 주문번호(이지웰)", required=true, maxLength=100)
	private String rsvNo;
	
	@APIFields(description = "주문취소요청 Input 주문번호(제휴사)", required=true, maxLength=100)
	private String otaRsvNo;
	
	@APIFields(description = "주문취소요청 Input 결제금액", required=true)
	private Integer rsvPrice;
	
	@APIFields(description = "주문취소요청 Input 취소수수료", required=true)
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
