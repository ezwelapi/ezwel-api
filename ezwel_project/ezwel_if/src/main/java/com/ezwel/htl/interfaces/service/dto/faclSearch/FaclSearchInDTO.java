package com.ezwel.htl.interfaces.service.dto.faclSearch;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author ypjeon@ebsolution.co.kr
 * @date 2018. 11. 13.
 */
public class FaclSearchInDTO extends AbstractEntity {
	
	@APIFields(description = "시설검색 Input 체크인", required=true, maxLength=8)
	private String checkInDate;
	
	@APIFields(description = "시설검색 Input 체크아웃", required=true, maxLength=8)
	private String checkOutDate;
	
	@APIFields(description = "시설검색 Input 시도코드", required=true, maxLength=2)
	private String sidoCode;
	
	@APIFields(description = "시설검색 Input 군구코드", maxLength=5)
	private String gunguCode;

	public String getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}

	public String getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	
	public String getSidoCode() {
		return sidoCode;
	}

	public void setSidoCode(String sidoCode) {
		this.sidoCode = sidoCode;
	}

	public String getGunguCode() {
		return gunguCode;
	}

	public void setGunguCode(String gunguCode) {
		this.gunguCode = gunguCode;
	}
	
}
