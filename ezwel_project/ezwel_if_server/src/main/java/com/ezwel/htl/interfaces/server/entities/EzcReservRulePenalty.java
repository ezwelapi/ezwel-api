package com.ezwel.htl.interfaces.server.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;
import java.math.BigDecimal;


/**
 * <b>History : Generated Code Skeleton iCodeManager Made by KSW</b>
 * <pre>
 * 버전              성명                                일자                                       변경내용
 * -------    ----------------     -------------------		-----------------
 * 0.0.1      iCodeManager         2018-11-23 18:58:46      신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:58:46
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
@Data
@Alias("ezcReservRulePenalty")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="예약 규정 패널티", description="예약 규정 패널티 ( EZC_RESERV_RULE_PENALTY )", modelTypes="TABLE")
public class EzcReservRulePenalty extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "예약 패널티 일련번호", maxLength=10, required=true, constraints="EZC_RESERV_RULE_PENALTY_PK(P),SYS_C0011665(C) EZC_RESERV_RULE_PENALTY_PK(UNIQUE)")
	private BigDecimal reservPenaltySeq;

	@APIFields(description = "예약 객실 번호", maxLength=10, required=true, constraints="SYS_C0011666(C) EZC_RESERV_RULE_PENALTY_IF01(NONUNIQUE)")
	private BigDecimal reservRoomNum;

	@APIFields(description = "패널티 적용 일자", maxLength=8, required=true, constraints="SYS_C0011667(C)")
	private String penaltyApplyDd;

	@APIFields(description = "수수료 율", maxLength=4)
	private BigDecimal feeRate;

	@APIFields(description = "수수료 금액", maxLength=8, required=true, constraints="SYS_C0011668(C)")
	private BigDecimal feeAmt;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011669(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011670(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss")
	private String modiDt;


	
	public BigDecimal getReservPenaltySeq() {
		return reservPenaltySeq;
	}

	public void setReservPenaltySeq(BigDecimal reservPenaltySeq) {
		this.reservPenaltySeq = reservPenaltySeq;
	}

	public BigDecimal getReservRoomNum() {
		return reservRoomNum;
	}

	public void setReservRoomNum(BigDecimal reservRoomNum) {
		this.reservRoomNum = reservRoomNum;
	}

	public String getPenaltyApplyDd() {
		return penaltyApplyDd;
	}

	public void setPenaltyApplyDd(String penaltyApplyDd) {
		this.penaltyApplyDd = penaltyApplyDd;
	}

	public BigDecimal getFeeRate() {
		return feeRate;
	}

	public void setFeeRate(BigDecimal feeRate) {
		this.feeRate = feeRate;
	}

	public BigDecimal getFeeAmt() {
		return feeAmt;
	}

	public void setFeeAmt(BigDecimal feeAmt) {
		this.feeAmt = feeAmt;
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
