package com.ezwel.htl.interfaces.service.data.roomRead;


import java.math.BigDecimal;
import java.util.List;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author ypjeon@ebsolution.co.kr
 * @date 2018. 11. 13.
 */

@APIModel(description="객실정보 조회 입력SDO")
@Data
@EqualsAndHashCode(callSuper=true)
public class RoomReadInSDO extends AbstractSDO {
	
	@APIFields(description = "객실정보조회 Input 상품코드", maxLength=100)
	private String pdtNo;
	
	@APIFields(description = "객실정보조회 Input 체크인", isDate=true, dateFormat="yyyyMMdd", required=true, maxLength=8)
	private String checkInDate;
	
	@APIFields(description = "객실정보조회 Input 체크아웃", isDate=true, dateFormat="yyyyMMdd", required=true, maxLength=8)
	private String checkOutDate;
	
	@APIFields(description = "객실정보조회 Input 객실상품코드", maxLength=500)
	private String roomNo;
	
	@APIFields(description = "객실정보조회 Input 객실수", required=true)
	private Integer roomCnt;
	
	@APIFields(description = "객실정보조회 Input 성인투숙자수")
	private Integer adultCnt;
	
	@APIFields(description = "객실정보조회 Input 소아투숙자수")
	private Integer childCnt;
	
	@APIFields(description = "그룹 시설 코드")
	private BigDecimal grpFaclCd;
	
	@APIFields(description = "시설 코드", maxLength=10)
	private BigDecimal faclCd;
	
	@APIFields(description = "최저가 객실 조회 상품정보")
	private List<RoomReadSeachMinInSDO> seachMinRoomInList;
	
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

	public BigDecimal getGrpFaclCd() {
		return grpFaclCd;
	}

	public void setGrpFaclCd(BigDecimal grpFaclCd) {
		this.grpFaclCd = grpFaclCd;
	}

	public List<RoomReadSeachMinInSDO> getSeachMinRoomInList() {
		return seachMinRoomInList;
	}

	public void setSeachMinRoomInList(List<RoomReadSeachMinInSDO> seachMinRoomInList) {
		this.seachMinRoomInList = seachMinRoomInList;
	}

	public BigDecimal getFaclCd() {
		return faclCd;
	}

	public void setFaclCd(BigDecimal faclCd) {
		this.faclCd = faclCd;
	}

	
}
