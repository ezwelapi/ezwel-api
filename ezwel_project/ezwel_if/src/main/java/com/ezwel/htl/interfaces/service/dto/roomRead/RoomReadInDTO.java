package com.ezwel.htl.interfaces.service.dto.roomRead;

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
public class RoomReadInDTO extends AbstractEntity {
	
	@APIFields(description = "객실정보조회 Input 상품코드", required=true, maxLength=100)
	private String pdtNo;
	
	@APIFields(description = "객실정보조회 Input 체크인", required=true, maxLength=8)
	private String checkInDate;
	
	@APIFields(description = "객실정보조회 Input 체크아웃", required=true, maxLength=8)
	private String checkOutDate;
	
	@APIFields(description = "객실정보조회 Input 객실상품코드", maxLength=500)
	private String roomNo;
	
	@APIFields(description = "객실정보조회 Input 객실수", required=true)
	private Integer roomCnt;
	
	@APIFields(description = "객실정보조회 Input 성인투숙자수")
	private Integer adultCnt;
	
	@APIFields(description = "객실정보조회 Input 소아투숙자수")
	private Integer childCnt;

	public String getPdtNo() {
		return pdtNo;
	}

	public void setPdtNo(String pdtNo) {
		this.pdtNo = pdtNo;
	}

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

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public Integer getRoomCnt() {
		return roomCnt;
	}

	public void setRoomCnt(Integer roomCnt) {
		this.roomCnt = roomCnt;
	}

	public Integer getAdultCnt() {
		return adultCnt;
	}

	public void setAdultCnt(Integer adultCnt) {
		this.adultCnt = adultCnt;
	}

	public Integer getChildCnt() {
		return childCnt;
	}

	public void setChildCnt(Integer childCnt) {
		this.childCnt = childCnt;
	}

}
