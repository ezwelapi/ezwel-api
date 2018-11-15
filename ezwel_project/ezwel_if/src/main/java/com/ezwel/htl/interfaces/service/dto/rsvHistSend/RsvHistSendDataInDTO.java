package com.ezwel.htl.interfaces.service.dto.rsvHistSend;

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
public class RsvHistSendDataInDTO extends AbstractEntity {

	private String rsvNo;
	private String rsvDatetime;
	private Integer rsvPrice;
	private String rsvStat;
	private String rsvPdtName;
	private String rsvPdtNo;
	private String pdtNo;
	private String pdtName;
	private String roomNo;
	private String roomName;
	private Integer roomCnt;
	private String checkInDate;
	private String checkOutDate;
	private String memKey;
	private String memName;
	private String memPhone;
	private String memEmail;
	private String userName;
	private String userMobile;
	private String userEmail;
	private String userCmt;
	private Integer adultCnt;
	private Integer childCnt;
	private List<RsvHistSendOptionsInDTO> options = null;

	public String getRsvNo() {
		return rsvNo;
	}

	public void setRsvNo(String rsvNo) {
		this.rsvNo = rsvNo;
	}

	public String getRsvDatetime() {
		return rsvDatetime;
	}

	public void setRsvDatetime(String rsvDatetime) {
		this.rsvDatetime = rsvDatetime;
	}

	public Integer getRsvPrice() {
		return rsvPrice;
	}

	public void setRsvPrice(Integer rsvPrice) {
		this.rsvPrice = rsvPrice;
	}

	public String getRsvStat() {
		return rsvStat;
	}

	public void setRsvStat(String rsvStat) {
		this.rsvStat = rsvStat;
	}

	public String getRsvPdtName() {
		return rsvPdtName;
	}

	public void setRsvPdtName(String rsvPdtName) {
		this.rsvPdtName = rsvPdtName;
	}

	public String getRsvPdtNo() {
		return rsvPdtNo;
	}

	public void setRsvPdtNo(String rsvPdtNo) {
		this.rsvPdtNo = rsvPdtNo;
	}

	public String getPdtNo() {
		return pdtNo;
	}

	public void setPdtNo(String pdtNo) {
		this.pdtNo = pdtNo;
	}

	public String getPdtName() {
		return pdtName;
	}

	public void setPdtName(String pdtName) {
		this.pdtName = pdtName;
	}

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

	public Integer getRoomCnt() {
		return roomCnt;
	}

	public void setRoomCnt(Integer roomCnt) {
		this.roomCnt = roomCnt;
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

	public String getMemKey() {
		return memKey;
	}

	public void setMemKey(String memKey) {
		this.memKey = memKey;
	}

	public String getMemName() {
		return memName;
	}

	public void setMemName(String memName) {
		this.memName = memName;
	}

	public String getMemPhone() {
		return memPhone;
	}

	public void setMemPhone(String memPhone) {
		this.memPhone = memPhone;
	}

	public String getMemEmail() {
		return memEmail;
	}

	public void setMemEmail(String memEmail) {
		this.memEmail = memEmail;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserCmt() {
		return userCmt;
	}

	public void setUserCmt(String userCmt) {
		this.userCmt = userCmt;
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

	public List<RsvHistSendOptionsInDTO> getOptions() {
		return options;
	}

	public void setPenalty(List<RsvHistSendOptionsInDTO> options) {
		this.options = options;
	}
	
}
