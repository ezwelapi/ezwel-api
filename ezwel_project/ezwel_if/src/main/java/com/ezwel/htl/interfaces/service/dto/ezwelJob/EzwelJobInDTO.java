package com.ezwel.htl.interfaces.service.dto.ezwelJob;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;

/**
 * <pre>
 * 
 * </pre>
 * @author ypjeon@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
public class EzwelJobInDTO extends AbstractEntity {

	private String rsvNo;
	private String rsvDateStart;
	private String rsvDateEnd;

	public String getRsvNo() {
		return rsvNo;
	}

	public void setRsvNo(String rsvNo) {
		this.rsvNo = rsvNo;
	}

	public String getRsvDateStart() {
		return rsvDateStart;
	}

	public void setRsvDateStart(String rsvDateStart) {
		this.rsvDateStart = rsvDateStart;
	}

	public String getRsvDateEnd() {
		return rsvDateEnd;
	}

	public void setRsvDateEnd(String rsvDateEnd) {
		this.rsvDateEnd = rsvDateEnd;
	}

}
