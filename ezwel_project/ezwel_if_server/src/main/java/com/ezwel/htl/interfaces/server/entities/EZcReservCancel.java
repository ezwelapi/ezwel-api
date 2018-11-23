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
 * 0.0.1      CodeSkeleton         2018-11-23 18:35:58                신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:35:58
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
@Data
@Alias("ezcreservcancel")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="예약 취소 정보", description="예약 취소 정보 ( EZC_RESERV_CANCEL )", modelTypes="TABLE")
public class EZcReservCancel extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "예약 취소 일련번호", maxLength=10, required=true, constraints = "EZC_RESERV_CANCEL_PK(P),SYS_C0011615(C) EZC_RESERV_CANCEL_PK(UNIQUE)")
	private BigDecimal reservCancelSeq;

	@APIFields(description = "예약 번호", maxLength=10, required=true, constraints = "FK_EZC_RESERV_BASE_EZC_RESERV_(R),SYS_C0011616(C) EZC_RESERV_CANCEL_IF01(NONUNIQUE)")
	private BigDecimal reservNum;

	@APIFields(description = "예약 객실 번호", maxLength=10, constraints = "FK_EZC_RESERV_ROOM_EZC_RESERV_(R) EZC_RESERV_CANCEL_IF02(NONUNIQUE)")
	private BigDecimal reservRoomNum;

	@APIFields(description = "전체 취소 여부", maxLength=1, required=true, constraints = "SYS_C0011617(C)")
	private String allCancelYn;

	@APIFields(description = "취소 요청 일시", maxLength=14, isDate=true, required=true, constraints = "SYS_C0011618(C)")
	private String cancelReqDt;

	@APIFields(description = "취소 수수료 금액", maxLength=8, required=true, constraints = "SYS_C0011619(C)")
	private BigDecimal cancelFeeAmt;

	@APIFields(description = "수수료 결제 여부", maxLength=1, required=true, constraints = "SYS_C0011620(C)")
	private String feePayYn;

	@APIFields(description = "취소 상태", maxLength=8, required=true, constraints = "SYS_C0011621(C)")
	private String cancelStatus;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints = "SYS_C0011622(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, required=true, constraints = "SYS_C0011623(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true)
	private String modiDt;


	
	public BigDecimal getReservCancelSeq() {
		return reservCancelSeq;
	}

	public void setReservCancelSeq(BigDecimal reservCancelSeq) {
		this.reservCancelSeq = reservCancelSeq;
	}

	public BigDecimal getReservNum() {
		return reservNum;
	}

	public void setReservNum(BigDecimal reservNum) {
		this.reservNum = reservNum;
	}

	public BigDecimal getReservRoomNum() {
		return reservRoomNum;
	}

	public void setReservRoomNum(BigDecimal reservRoomNum) {
		this.reservRoomNum = reservRoomNum;
	}

	public String getAllCancelYn() {
		return allCancelYn;
	}

	public void setAllCancelYn(String allCancelYn) {
		this.allCancelYn = allCancelYn;
	}

	public String getCancelReqDt() {
		return cancelReqDt;
	}

	public void setCancelReqDt(String cancelReqDt) {
		this.cancelReqDt = cancelReqDt;
	}

	public BigDecimal getCancelFeeAmt() {
		return cancelFeeAmt;
	}

	public void setCancelFeeAmt(BigDecimal cancelFeeAmt) {
		this.cancelFeeAmt = cancelFeeAmt;
	}

	public String getFeePayYn() {
		return feePayYn;
	}

	public void setFeePayYn(String feePayYn) {
		this.feePayYn = feePayYn;
	}

	public String getCancelStatus() {
		return cancelStatus;
	}

	public void setCancelStatus(String cancelStatus) {
		this.cancelStatus = cancelStatus;
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


}	
