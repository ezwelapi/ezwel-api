package com.ezwel.htl.interfaces.service.dto.roomRead;

import java.util.List;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author ypjeon@ebsolution.co.kr
 * @date 2018. 11. 13.
 */
public class RoomReadDataOutDTO extends AbstractEntity {

	private String roomNo;
	private String roomName;
	private Integer priceForList;
	private Integer priceForSale;
	private String adtCntMin;
	private String adtCntMax;
	private String roomInfo;
	private Integer roomAvailCount;
	private String rsvTypeCode;
	private String breakfast;
	private String spcType;
	private String spcTypeTime;
	private List<RoomReadOptionsOutDTO> options = null;
	private List<RoomReadPenaltyOutDTO> penalty = null;

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

	public String getAdtCntMin() {
		return adtCntMin;
	}

	public void setAdtCntMin(String adtCntMin) {
		this.adtCntMin = adtCntMin;
	}

	public String getAdtCntMax() {
		return adtCntMax;
	}

	public void setAdtCntMax(String adtCntMax) {
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
	
	public List<RoomReadOptionsOutDTO> getOptions() {
		return options;
	}

	public void setOptions(List<RoomReadOptionsOutDTO> options) {
		this.options = options;
	}
	
	public List<RoomReadPenaltyOutDTO> getPenalty() {
		return penalty;
	}

	public void setPenalty(List<RoomReadPenaltyOutDTO> penalty) {
		this.penalty = penalty;
	}
	
}
