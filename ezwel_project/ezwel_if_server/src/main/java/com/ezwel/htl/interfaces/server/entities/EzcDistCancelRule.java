package com.ezwel.htl.interfaces.server.entities;

//import lombok.Data;
//import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractEntity;
import java.math.BigDecimal;


/**
 * <b>History : Generated Code Skeleton iCodeManager Made by KSW</b>
 * <pre>
 * 버전              성명                                일자                                       변경내용
 * -------    ----------------     -------------------		-----------------
 * 0.0.1      iCodeManager         2018-11-23 18:58:45      신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:58:45
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
//@Data
@Alias("ezcDistCancelRule")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="직영/숙박 취소 규정", description="직영/숙박 취소 규정 ( EZC_DIST_CANCEL_RULE )", modelTypes="TABLE")
public class EzcDistCancelRule extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "객실 코드", maxLength=10, required=true, constraints="EZC_DIST_CANCEL_RULE_PK(P),FK_EZC_DIST_ROOM_EZC_DIST_CANC(R),SYS_C0011393(C) EZC_DIST_CANCEL_RULE_PK(UNIQUE),EZC_DIST_CANCEL_RULE_IF01(NONUNIQUE)")
	private BigDecimal roomCd;

	@APIFields(description = "패널티 발생일 수", maxLength=4, required=true, constraints="EZC_DIST_CANCEL_RULE_PK(P),SYS_C0011394(C) EZC_DIST_CANCEL_RULE_PK(UNIQUE)")
	private BigDecimal penaltyIssueddCnt;

	@APIFields(description = "수수료 율", maxLength=4)
	private BigDecimal feeRate;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011395(C)")
	private String regId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyyMMddHHmmss", required=true, constraints="SYS_C0011396(C)")
	private String regDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyyMMddHHmmss")
	private String modiDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());


	
	public BigDecimal getRoomCd() {
		return roomCd;
	}

	public void setRoomCd(BigDecimal roomCd) {
		this.roomCd = roomCd;
	}

	public BigDecimal getPenaltyIssueddCnt() {
		return penaltyIssueddCnt;
	}

	public void setPenaltyIssueddCnt(BigDecimal penaltyIssueddCnt) {
		this.penaltyIssueddCnt = penaltyIssueddCnt;
	}

	public BigDecimal getFeeRate() {
		return feeRate;
	}

	public void setFeeRate(BigDecimal feeRate) {
		this.feeRate = feeRate;
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
