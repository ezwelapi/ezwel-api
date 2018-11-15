package com.ezwel.htl.interfaces.service.dto.roomRead;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author ypjeon@ebsolution.co.kr
 * @date 2018. 11. 13.
 */
public class RoomReadOptionsOutDTO extends AbstractEntity {

	private String optNo;
	private String optName;
	private Integer optPrice;
	private Integer optCountMax;

	public String getOptNo() {
		return optNo;
	}

	public void setOptNo(String optNo) {
		this.optNo = optNo;
	}
	
	public String getOptName() {
		return optName;
	}

	public void setOptName(String optName) {
		this.optName = optName;
	}

	public Integer getOptPrice() {
		return optPrice;
	}

	public void setOptPrice(Integer optPrice) {
		this.optPrice = optPrice;
	}

	public Integer getOptCountMax() {
		return optCountMax;
	}

	public void setOptCountMax(Integer optCountMax) {
		this.optCountMax = optCountMax;
	}

}
