package com.ezwel.htl.interfaces.service.data.agentJob;


import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import lombok.Data;
import lombok.EqualsAndHashCode;



import com.ezwel.htl.interfaces.commons.annotation.APIFields;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
@APIModel
@Data
@EqualsAndHashCode(callSuper=true)
public class AgentJobReservesOutSDO extends AbstractSDO {

	@APIFields(description = "주문대사(제휴사) output 주문번호(이지웰)", required=true, maxLength=100)
	private String rsvNo;
	
	@APIFields(description = "주문대사(제휴사) output 주문상품번호(이지웰)", required=true, maxLength=100)
	private String rsvPdtNo;
	
	@APIFields(description = "주문대사(제휴사) output 결제금액", required=true)
	private Integer rsvPrice;
	
	@APIFields(description = "주문대사(제휴사) output 상품번호(제휴사)", required=true, maxLength=100)
	private String pdtNo;
	
	@APIFields(description = "주문대사(제휴사) output 주문번호(제휴사)", required=true, maxLength=100)
	private String otaRsvNo;
	
	@APIFields(description = "주문대사(제휴사) output 주문상태코드", required=true, maxLength=5)
	private String rsvStat;

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

}
