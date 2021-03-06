package com.ezwel.htl.interfaces.service.data.sddSearch;


import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author ypjeon@ebsolution.co.kr
 * @date 2018. 11. 13.
 */
@APIModel
@Data
@EqualsAndHashCode(callSuper=true)
public class SddSearchInSDO extends AbstractSDO {
	
	@APIFields(description = "당일특가검색 Input 체크인", required=true, maxLength=8)
	private String checkInDate;
	
	@APIFields(description = "당일특가검색 Input 체크아웃", required=true, maxLength=8)
	private String checkOutDate;

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
	
	
}
