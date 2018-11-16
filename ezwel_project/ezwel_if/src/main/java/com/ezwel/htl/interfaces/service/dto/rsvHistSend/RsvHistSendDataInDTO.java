package com.ezwel.htl.interfaces.service.dto.rsvHistSend;

import java.util.List;

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
public class RsvHistSendDataInDTO extends AbstractEntity {

	@APIFields(description = "결제완료내역전송 Input 주문번호", required=true, maxLength=100)
	private String rsvNo;
	
	@APIFields(description = "결제완료내역전송 Input 주문일시", required=true, maxLength=16)
	private String rsvDatetime;
	
	@APIFields(description = "결제완료내역전송 Input 결제금액", required=true)
	private Integer rsvPrice;
	
	@APIFields(description = "결제완료내역전송 Input 주문상태코드", required=true, maxLength=5)
	private String rsvStat;
	
	@APIFields(description = "결제완료내역전송 Input 주문상품명", required=true, maxLength=100)
	private String rsvPdtName;
	
	@APIFields(description = "결제완료내역전송 Input 주문상품번호", required=true, maxLength=100)
	private String rsvPdtNo;
	
	@APIFields(description = "결제완료내역전송 Input 상품코드(제휴사)", required=true, maxLength=100)
	private String pdtNo;
	
	@APIFields(description = "결제완료내역전송 Input 상품명", required=true, maxLength=200)
	private String pdtName;
	
	@APIFields(description = "결제완료내역전송 Input 객실상품코드", required=true, maxLength=500)
	private String roomNo;
	
	@APIFields(description = "결제완료내역전송 Input 객실상품명", required=true, maxLength=200)
	private String roomName;
	
	@APIFields(description = "결제완료내역전송 Input 객실수", required=true)
	private Integer roomCnt;
	
	@APIFields(description = "결제완료내역전송 Input 체크인", required=true, maxLength=8)
	private String checkInDate;
	
	@APIFields(description = "결제완료내역전송 Input 체크아웃", required=true, maxLength=8)
	private String checkOutDate;
	
	@APIFields(description = "결제완료내역전송 Input 회원유저키", required=true, maxLength=20)
	private String memKey;
	
	@APIFields(description = "결제완료내역전송 Input 회원명", required=true, maxLength=20)
	private String memName;
	
	@APIFields(description = "결제완료내역전송 Input 회원전화번호", required=true, maxLength=20)
	private String memPhone;
	
	@APIFields(description = "결제완료내역전송 Input 회원이메일주소", required=true, maxLength=100)
	private String memEmail;
	
	@APIFields(description = "결제완료내역전송 Input 투숙자명", required=true, maxLength=20)
	private String userName;
	
	@APIFields(description = "결제완료내역전송 Input 투숙자전화번호", required=true, maxLength=20)
	private String userMobile;
	
	@APIFields(description = "결제완료내역전송 Input 투숙자이메일주소", required=true, maxLength=100)
	private String userEmail;
	
	@APIFields(description = "결제완료내역전송 Input 요청내용", maxLength=1000)
	private String userCmt;
	
	@APIFields(description = "결제완료내역전송 Input 성인투숙자수", required=true)
	private Integer adultCnt;
	
	@APIFields(description = "결제완료내역전송 Input 소아투숙자수", required=true)
	private Integer childCnt;
	
	@APIFields(description = "결제완료내역전송 Input options")
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
