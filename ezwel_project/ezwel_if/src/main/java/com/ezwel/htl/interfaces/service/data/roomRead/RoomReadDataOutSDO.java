package com.ezwel.htl.interfaces.service.data.roomRead;

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
@APIModel
@Data
@EqualsAndHashCode(callSuper=true)
public class RoomReadDataOutSDO extends AbstractSDO {

	@APIFields(description = "객실정보조회 output 객실상품코드", required=true, maxLength=500)
	private String roomNo;
	
	@APIFields(description = "객실정보조회 output 객실상품명", required=true, maxLength=200)
	private String roomName;
	
	@APIFields(description = "객실정보조회 output 정상가", required=true)
	private Integer priceForList;
	
	@APIFields(description = "객실정보조회 output 판매가", required=true)
	private Integer priceForSale;
	
	@APIFields(description = "객실정보조회 output 기준인원", required=true)
	private Integer adtCntMin;
	
	@APIFields(description = "객실정보조회 output 최대인원", required=true)
	private Integer adtCntMax;
	
	@APIFields(description = "객실정보조회 output 객실정보", required=true, maxLength=1000)
	private String roomInfo;
	
	@APIFields(description = "객실정보조회 output 잔여객실수량", required=true)
	private Integer roomAvailCount;
	
	@APIFields(description = "객실정보조회 output 확정예약여부코드", required=true, maxLength=1)
	private String rsvTypeCode;
	
	@APIFields(description = "객실정보조회 output 조식포함여부", required=true, maxLength=1)
	private String breakfast;
	
	@APIFields(description = "객실정보조회 output 특가상품여부", required=true, maxLength=1)
	private String spcType;
	
	@APIFields(description = "객실정보조회 output 특가상품종료일시", maxLength=12, isDate=true)
	private String spcTypeTime;
	
	@APIFields(description = "객실정보조회 output options")
	private List<RoomReadOptionsOutSDO> options = null;
	
	@APIFields(description = "객실정보조회 output penalty")
	private List<RoomReadPenaltyOutSDO> penalty = null;

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	
	public String getRoomName() {
		return roomName;
	}
	
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Integer getPriceForList() {
		return priceForList;
	}

	public void setPriceForList(Integer priceForList) {
		this.priceForList = priceForList;
	}

	public Integer getPriceForSale() {
		return priceForSale;
	}
	
	public void setPriceForSale(Integer priceForSale) {
		this.priceForSale = priceForSale;
	}

	public Integer getAdtCntMin() {
		return adtCntMin;
	}

	public void setAdtCntMin(Integer adtCntMin) {
		this.adtCntMin = adtCntMin;
	}

	public Integer getAdtCntMax() {
		return adtCntMax;
	}

	public void setAdtCntMax(Integer adtCntMax) {
		this.adtCntMax = adtCntMax;
	}

	public String getRoomInfo() {
		return roomInfo;
	}

	public void setRoomInfo(String roomInfo) {
		this.roomInfo = roomInfo;
	}

	public Integer getRoomAvailCount() {
		return roomAvailCount;
	}

	public void setRoomAvailCount(Integer roomAvailCount) {
		this.roomAvailCount = roomAvailCount;
	}

	public String getRsvTypeCode() {
		return rsvTypeCode;
	}

	public void setRsvTypeCode(String rsvTypeCode) {
		this.rsvTypeCode = rsvTypeCode;
	}

	public String getBreakfast() {
		return breakfast;
	}

	public void setBreakfast(String breakfast) {
		this.breakfast = breakfast;
	}

	public String getSpcType() {
		return spcType;
	}

	public void setSpcType(String spcType) {
		this.spcType = spcType;
	}

	public String getSpcTypeTime() {
		return spcTypeTime;
	}

	public void setSpcTypeTime(String spcTypeTime) {
		this.spcTypeTime = spcTypeTime;
	}
	
	public List<RoomReadOptionsOutSDO> getOptions() {
		return options;
	}

	public void setOptions(List<RoomReadOptionsOutSDO> options) {
		this.options = options;
	}
	
	public List<RoomReadPenaltyOutSDO> getPenalty() {
		return penalty;
	}

	public void setPenalty(List<RoomReadPenaltyOutSDO> penalty) {
		this.penalty = penalty;
	}
	
}
