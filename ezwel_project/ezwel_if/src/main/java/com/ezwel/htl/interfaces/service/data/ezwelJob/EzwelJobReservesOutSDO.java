package com.ezwel.htl.interfaces.service.data.ezwelJob;


import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class EzwelJobReservesOutSDO extends AbstractSDO {

	@APIFields(description = "주문대사(이지웰) output 주문번호(이지웰)", required=true, maxLength=100)
	private String rsvNo;
	
	@APIFields(description = "주문대사(이지웰) output 주문상품번호(이지웰)", required=true, maxLength=100)
	private String rsvPdtNo;
	
	@APIFields(description = "주문대사(이지웰) output 결제금액", required=true)
	private Integer rsvPrice;
	
	@APIFields(description = "주문대사(이지웰) output 상품번호(제휴사)", required=true, maxLength=100)
	private String pdtNo;
	
	@APIFields(description = "주문대사(이지웰) output 주문번호(제휴사)", required=true, maxLength=100)
	private String otaRsvNo;
	
	@APIFields(description = "주문대사(이지웰) output 주문상태코드", required=true, maxLength=5)
	private String rsvStat;
	
	@APIFields(description = "주문대사(이지웰) output 주문상태명")
	private String rsvStatName;

	public String getRsvNo() {
		return rsvNo;
	}

	public void setRsvNo(String rsvNo) {
		this.rsvNo = rsvNo;
	}

	public String getRsvPdtNo() {
		return rsvPdtNo;
	}

	public void setRsvPdtNo(String rsvPdtNo) {
		this.rsvPdtNo = rsvPdtNo;
	}

	public Integer getRsvPrice() {
		return rsvPrice;
	}

	public void setRsvPrice(Integer rsvPrice) {
		this.rsvPrice = rsvPrice;
	}

	public String getPdtNo() {
		return pdtNo;
	}

	public void setPdtNo(String pdtNo) {
		this.pdtNo = pdtNo;
	}

	public String getOtaRsvNo() {
		return otaRsvNo;
	}

	public void setOtaRsvNo(String otaRsvNo) {
		this.otaRsvNo = otaRsvNo;
	}

	public String getRsvStat() {
		return rsvStat;
	}

	public void setRsvStat(String rsvStat) {
		this.rsvStat = rsvStat;
	}
	
	//전차장님 오타 get 을 set 이라 해두심 -_-
	public String getRsvStatName() {
		return rsvStatName;
	}

	public void setRsvStatName(String rsvStatName) {
		this.rsvStatName = rsvStatName;
	}

}
