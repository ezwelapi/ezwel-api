package com.ezwel.htl.interfaces.service.data.orderCancelReq;


import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import lombok.Data;
import lombok.EqualsAndHashCode;



import com.ezwel.htl.interfaces.commons.annotation.APIFields;

/**
 * <pre>
 * 
 * </pre>
 * @author ypjeon@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
@APIModel
@Data
@EqualsAndHashCode(callSuper=true)
public class OrderCancelReqInSDO extends AbstractSDO {

	@APIFields(description = "주문취소요청 Input 제휴사아이디", required=true, maxLength=100)
	private String otaId;
	
	@APIFields(description = "주문취소요청 Input 주문번호(이지웰)", required=true, maxLength=100)
	private String rsvNo;
	
	@APIFields(description = "주문취소요청 Input 주문번호(제휴사)", required=true, maxLength=100)
	private String otaRsvNo;
	
	@APIFields(description = "주문취소요청 Input 결제금액", required=true)
	private Integer rsvPrice;
	
	@APIFields(description = "주문취소요청 Input 취소수수료", required=true)
	private Integer cancelCharge;

	public String getOtaId() {
		return otaId;
	}

	public void setOtaId(String otaId) {
		this.otaId = otaId;
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
