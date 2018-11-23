package com.ezwel.htl.interfaces.server.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;
import java.math.BigDecimal;


/**
 * <b>History : Generated Code Skeleton Made by KSW</b>
 * <pre>
 * 버전              성명                                일자                                       변경내용
 * -------    ----------------     -------------------		-----------------
 * 0.0.1      CodeSkeleton         2018-11-23 18:46:12                신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:46:12
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
@Data
@Alias("ezcReservBase")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="예약 기본 정보", description="예약 기본 정보 ( EZC_RESERV_BASE )", modelTypes="TABLE")
public class EzcReservBase extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "예약 번호", maxLength=10, required=true, constraints="EZC_RESERV_BASE_PK(P),SYS_C0011599(C) EZC_RESERV_BASE_PK(UNIQUE)")
	private BigDecimal reservNum;

	@APIFields(description = "시설 코드", maxLength=10, required=true, constraints="FK_EZC_FACL_EZC_RESERV_BASE(R),SYS_C0011600(C) EZC_RESERV_BASE_IF06(NONUNIQUE)")
	private BigDecimal faclCd;

	@APIFields(description = "오프라인 여부", maxLength=1, required=true, constraints="SYS_C0011601(C)")
	private String offYn;

	@APIFields(description = "이지웰 주문 번호", maxLength=10)
	private BigDecimal ezwelOrderNum;

	@APIFields(description = "제휴사 주문 번호", maxLength=100)
	private String partnerOrderNum;

	@APIFields(description = "시설 예약 번호", maxLength=100)
	private String faclReservNum;

	@APIFields(description = "디바이스 구분", maxLength=8, required=true, constraints="SYS_C0011602(C)")
	private String deviceDiv;

	@APIFields(description = "제휴사 코드", maxLength=20, required=true, constraints="FK_EZC_PARTNER_EZC_RESERV_BASE(R),SYS_C0011603(C) EZC_RESERV_BASE_IF03(NONUNIQUE)")
	private String partnerCd;

	@APIFields(description = "채널 코드", maxLength=10, required=true, constraints="FK_EZC_CHANNEL_MNG_EZC_RESERV_(R),SYS_C0011604(C) EZC_RESERV_BASE_IF01(NONUNIQUE)")
	private String channelCd;

	@APIFields(description = "USER_KEY", maxLength=10)
	private BigDecimal userKey;

	@APIFields(description = "시설 명", maxLength=100, required=true, constraints="SYS_C0011605(C)")
	private String faclNm;

	@APIFields(description = "총 판매 금액", maxLength=8, required=true, constraints="SYS_C0011606(C)")
	private BigDecimal totSaleAmt;

	@APIFields(description = "체크인 일자", maxLength=8, required=true, constraints="SYS_C0011607(C)")
	private String checkInDd;

	@APIFields(description = "체크아웃 일자", maxLength=8, required=true, constraints="SYS_C0011608(C)")
	private String checkOutDd;

	@APIFields(description = "총 성인 수", maxLength=4)
	private BigDecimal totAdtCnt;

	@APIFields(description = "총 소아 수", maxLength=4)
	private BigDecimal totChildCnt;

	@APIFields(description = "투숙자 명", maxLength=20)
	private String guestNm;

	@APIFields(description = "투숙자 휴대전화", maxLength=20, required=true, constraints="SYS_C0011609(C)")
	private String guestMobile;

	@APIFields(description = "투숙자 이메일", maxLength=100)
	private String guestEmail;

	@APIFields(description = "예약 팩스 발송 일시", maxLength=14, isDate=true)
	private String reservFaxSendDt;

	@APIFields(description = "예약 팩스 수신 일시", maxLength=14, isDate=true)
	private String reservFaxRecvDt;

	@APIFields(description = "예약 일시", maxLength=14, isDate=true, required=true, constraints="SYS_C0011610(C)")
	private String reservDt;

	@APIFields(description = "예약 취소 일시", maxLength=14, isDate=true)
	private String reservCancelDt;

	@APIFields(description = "예약 상태", maxLength=8, required=true, constraints="SYS_C0011611(C)")
	private String reservStatus;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011612(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, required=true, constraints="SYS_C0011613(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true)
	private String modiDt;

	@APIFields(description = "회원 ID", maxLength=20, constraints="EZC_RESERV_BASE_IF07(NONUNIQUE)")
	private String userId;


	
	public BigDecimal getReservNum() {
		return reservNum;
	}

	public void setReservNum(BigDecimal reservNum) {
		this.reservNum = reservNum;
	}

	public BigDecimal getFaclCd() {
		return faclCd;
	}

	public void setFaclCd(BigDecimal faclCd) {
		this.faclCd = faclCd;
	}

	public String getOffYn() {
		return offYn;
	}

	public void setOffYn(String offYn) {
		this.offYn = offYn;
	}

	public BigDecimal getEzwelOrderNum() {
		return ezwelOrderNum;
	}

	public void setEzwelOrderNum(BigDecimal ezwelOrderNum) {
		this.ezwelOrderNum = ezwelOrderNum;
	}

	public String getPartnerOrderNum() {
		return partnerOrderNum;
	}

	public void setPartnerOrderNum(String partnerOrderNum) {
		this.partnerOrderNum = partnerOrderNum;
	}

	public String getFaclReservNum() {
		return faclReservNum;
	}

	public void setFaclReservNum(String faclReservNum) {
		this.faclReservNum = faclReservNum;
	}

	public String getDeviceDiv() {
		return deviceDiv;
	}

	public void setDeviceDiv(String deviceDiv) {
		this.deviceDiv = deviceDiv;
	}

	public String getPartnerCd() {
		return partnerCd;
	}

	public void setPartnerCd(String partnerCd) {
		this.partnerCd = partnerCd;
	}

	public String getChannelCd() {
		return channelCd;
	}

	public void setChannelCd(String channelCd) {
		this.channelCd = channelCd;
	}

	public BigDecimal getUserKey() {
		return userKey;
	}

	public void setUserKey(BigDecimal userKey) {
		this.userKey = userKey;
	}

	public String getFaclNm() {
		return faclNm;
	}

	public void setFaclNm(String faclNm) {
		this.faclNm = faclNm;
	}

	public BigDecimal getTotSaleAmt() {
		return totSaleAmt;
	}

	public void setTotSaleAmt(BigDecimal totSaleAmt) {
		this.totSaleAmt = totSaleAmt;
	}

	public String getCheckInDd() {
		return checkInDd;
	}

	public void setCheckInDd(String checkInDd) {
		this.checkInDd = checkInDd;
	}

	public String getCheckOutDd() {
		return checkOutDd;
	}

	public void setCheckOutDd(String checkOutDd) {
		this.checkOutDd = checkOutDd;
	}

	public BigDecimal getTotAdtCnt() {
		return totAdtCnt;
	}

	public void setTotAdtCnt(BigDecimal totAdtCnt) {
		this.totAdtCnt = totAdtCnt;
	}

	public BigDecimal getTotChildCnt() {
		return totChildCnt;
	}

	public void setTotChildCnt(BigDecimal totChildCnt) {
		this.totChildCnt = totChildCnt;
	}

	public String getGuestNm() {
		return guestNm;
	}

	public void setGuestNm(String guestNm) {
		this.guestNm = guestNm;
	}

	public String getGuestMobile() {
		return guestMobile;
	}

	public void setGuestMobile(String guestMobile) {
		this.guestMobile = guestMobile;
	}

	public String getGuestEmail() {
		return guestEmail;
	}

	public void setGuestEmail(String guestEmail) {
		this.guestEmail = guestEmail;
	}

	public String getReservFaxSendDt() {
		return reservFaxSendDt;
	}

	public void setReservFaxSendDt(String reservFaxSendDt) {
		this.reservFaxSendDt = reservFaxSendDt;
	}

	public String getReservFaxRecvDt() {
		return reservFaxRecvDt;
	}

	public void setReservFaxRecvDt(String reservFaxRecvDt) {
		this.reservFaxRecvDt = reservFaxRecvDt;
	}

	public String getReservDt() {
		return reservDt;
	}

	public void setReservDt(String reservDt) {
		this.reservDt = reservDt;
	}

	public String getReservCancelDt() {
		return reservCancelDt;
	}

	public void setReservCancelDt(String reservCancelDt) {
		this.reservCancelDt = reservCancelDt;
	}

	public String getReservStatus() {
		return reservStatus;
	}

	public void setReservStatus(String reservStatus) {
		this.reservStatus = reservStatus;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getModiId() {
		return modiId;
	}

	public void setModiId(String modiId) {
		this.modiId = modiId;
	}

	public String getModiDt() {
		return modiDt;
	}

	public void setModiDt(String modiDt) {
		this.modiDt = modiDt;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


}	
