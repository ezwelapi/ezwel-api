package com.ezwel.htl.interfaces.service.dto.ezwelJob;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;

/**
 * <pre>
 * 
 * </pre>
 * @author ypjeon@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
public class EzwelJobReservesOutDTO extends AbstractEntity {

	private String rsvNo;
	private String rsvPdtNo;
	private Integer rsvPrice;
	private String pdtNo;
	private String otaRsvNo;
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
