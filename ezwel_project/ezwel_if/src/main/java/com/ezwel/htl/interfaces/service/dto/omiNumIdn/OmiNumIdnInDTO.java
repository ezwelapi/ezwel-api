package com.ezwel.htl.interfaces.service.dto.omiNumIdn;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;

/**
 * <pre>
 * 
 * </pre>
 * @author ypjeon@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
public class OmiNumIdnInDTO extends AbstractEntity {

	private String rsvNo;
	private String rsvStat;

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

}
