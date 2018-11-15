package com.ezwel.htl.interfaces.service.dto.omiNumIdn;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;

/**
 * <pre>
 * 
 * </pre>
 * @author ypjeon@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
public class OmiNumIdnReservesOutDTO extends AbstractEntity {

	private String rsvNo;
	private String rsvStat;
	private String otaRsvNo;
	private String compareStat;

	public String getRsvNo() {
		return rsvNo;
	}

	public void setRsvNo(String rsvNo) {
		this.rsvNo = rsvNo;
	}

	public String getRsvStat() {
		return rsvStat;
	}

	public void setRsvStat(String rsvStat) {
		this.rsvStat = rsvStat;
	}

	public String getOtaRsvNo() {
		return otaRsvNo;
	}

	public void setOtaRsvNo(String otaRsvNo) {
		this.otaRsvNo = otaRsvNo;
	}

	public String getCompareStat() {
		return compareStat;
	}

	public void setCompareStat(String compareStat) {
		this.compareStat = compareStat;
	}

}
