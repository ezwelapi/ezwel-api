package com.ezwel.htl.interfaces.service.data.view;


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
public class ViewInSDO extends AbstractSDO {

	@APIFields(description = "예약내역조회 Input 주문번호", maxLength=100)
	private String rsvNo;
	
	@APIFields(description = "예약내역조회 Input 검색시작일", maxLength=8, isDate=true, dateFormat="yyyyMMdd")
	private String startDate;
	
	@APIFields(description = "예약내역조회 Input 검색종료일", maxLength=8, isDate=true, dateFormat="yyyyMMdd")
	private String endDate;
	
	@APIFields(description = "예약내역조회 Input 검색일구분", maxLength=1)
	private String dateType;

	public String getRsvNo() {
		return rsvNo;
	}

	public void setRsvNo(String rsvNo) {
		this.rsvNo = rsvNo;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}


}
