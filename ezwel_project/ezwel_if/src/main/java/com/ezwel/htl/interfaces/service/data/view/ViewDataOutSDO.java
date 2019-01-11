package com.ezwel.htl.interfaces.service.data.view;

import java.math.BigDecimal;
import java.util.ArrayList;
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
public class ViewDataOutSDO extends AbstractSDO {

	@APIFields(description = "예약내역조회 주문번호(이지웰)", required=true, maxLength=100)
	private String rsvNo;
	
	@APIFields(description = "예약내역조회 주문일시", required=true, maxLength=16)
	private String rsvDatetime;
	
	@APIFields(description = "예약내역조회 결제금액", required=true)
	private BigDecimal rsvPrice;
	
	@APIFields(description = "예약내역조회 주문상태코드", required=true, maxLength=5)
	private String rsvStat;
	
	@APIFields(description = "예약내역조회 주문상품명", required=true, maxLength=100)
	private String rsvPdtName;
	
	@APIFields(description = "예약내역조회 주문상품번호(이지웰주문번호)", required=true, maxLength=100)
	private String rsvPdtNo;
	
	@APIFields(description = "예약내역조회 제휴사 상품코드", required=true, maxLength=100)
	private String pdtNo;
	
	@APIFields(description = "예약내역조회 상품명(시설명)", required=true, maxLength=200)
	private String pdtName;
	
	@APIFields(description = "예약내역조회 객실상품코드", required=true, maxLength=500)
	private String roomNo;
	
	@APIFields(description = "예약내역조회 객실상품명", required=true, maxLength=200)
	private String roomName;
	
	@APIFields(description = "예약내역조회 객실수", required=true)
	private Integer roomCnt;
	
	@APIFields(description = "예약내역조회 체크인", required=true, maxLength=8)
	private String checkInDate;
	
	@APIFields(description = "예약내역조회 체크아웃", required=true, maxLength=8)
	private String checkOutDate;
	
	@APIFields(description = "예약내역조회 주문번호(제휴사)", required=true, maxLength=100)
	private String otaRsvNo;
	
	@APIFields(description = "예약내역조회 시설예약번호(바우처번호)", maxLength=100)
	private String voucherNo;
	
	@APIFields(description = "예약내역조회 회원유저키", required=true, maxLength=20)
	private String memKey;
	
	@APIFields(description = "예약내역조회 회원명", required=true, maxLength=20)
	private String memName;
	
	@APIFields(description = "예약내역조회 회원전화번호", required=true, maxLength=20)
	private String memPhone;
	
	@APIFields(description = "예약내역조회 회원이메일주소", required=true, maxLength=100)
	private String memEmail;
	
	@APIFields(description = "예약내역조회 투숙자명", required=true, maxLength=20)
	private String userName;
	
	@APIFields(description = "예약내역조회 투숙자전화번호", required=true, maxLength=20)
	private String userMobile;
	
	@APIFields(description = "예약내역조회 투숙자이메일주소", required=true, maxLength=100)
	private String userEmail;
	
	@APIFields(description = "예약내역조회 요청내용", maxLength=1000)
	private String userCmt;
	
	@APIFields(description = "예약내역조회 성인투숙자수", required=true)
	private Integer adultCnt;
	
	@APIFields(description = "예약내역조회 소아투숙자수", required=true)
	private Integer childCnt;
	
	@APIFields(description = "예약내역조회 options")
	private List<ViewOptionsOutSDO> options = null;

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

	public BigDecimal getRsvPrice() {
		return rsvPrice;
	}

	public void setRsvPrice(BigDecimal rsvPrice) {
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

	public String getOtaRsvNo() {
		return otaRsvNo;
	}

	public void setOtaRsvNo(String otaRsvNo) {
		this.otaRsvNo = otaRsvNo;
	}

	public String getVoucherNo() {
		return voucherNo;
	}

	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
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

	public List<ViewOptionsOutSDO> getOptions() {
		return options;
	}

	public void setOptions(List<ViewOptionsOutSDO> options) {
		this.options = options;
	}
	
	public void addOptions(ViewOptionsOutSDO options) {
		if(this.options == null) {
			this.options = new ArrayList<ViewOptionsOutSDO>();
		}
		this.options.add(options);
	}	
}
