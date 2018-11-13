package com.ezwel.htl.interfaces.service.dto;

import com.ezwel.htl.interfaces.abstracts.AbstractEntity;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
public class CancelFeeAmtInDTO extends AbstractEntity {

	private String rsvNo;

	public String getRsvNo() {
		return rsvNo;
	}

	public void setRsvNo(String rsvNo) {
		this.rsvNo = rsvNo;
	}

}
