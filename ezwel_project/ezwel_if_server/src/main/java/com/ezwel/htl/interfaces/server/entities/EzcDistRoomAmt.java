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
@Alias("ezcDistRoomAmt")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="직영/숙박 객실 금액", description="직영/숙박 객실 금액 ( EZC_DIST_ROOM_AMT )", modelTypes="TABLE")
public class EzcDistRoomAmt extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "객실 코드", maxLength=10, required=true, constraints="EZC_DIST_ROOM_AMT_PK(P),FK_EZC_DIST_ROOM_EZC_DIST_ROOM(R),SYS_C0011423(C) EZC_DIST_ROOM_AMT_PK(UNIQUE),EZC_DIST_ROOM_AMT_IF01(NONUNIQUE)")
	private BigDecimal roomCd;

	@APIFields(description = "적용 일자", maxLength=8, required=true, constraints="EZC_DIST_ROOM_AMT_PK(P),SYS_C0011424(C) EZC_DIST_ROOM_AMT_PK(UNIQUE)")
	private String applyDd;

	@APIFields(description = "총 객실 수", maxLength=4)
	private BigDecimal totRoomCnt;

	@APIFields(description = "판매 객실 수", maxLength=4)
	private BigDecimal saleRoomCnt;

	@APIFields(description = "판매 금액", maxLength=8, required=true, constraints="SYS_C0011425(C)")
	private BigDecimal saleAmt;

	@APIFields(description = "자동 마감 여부", maxLength=1, required=true, constraints="SYS_C0011426(C)")
	private String autoEndYn;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011427(C)")
	private String regId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyyMMddHHmmss", required=true, constraints="SYS_C0011428(C)")
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

	public String getApplyDd() {
		return applyDd;
	}

	public void setApplyDd(String applyDd) {
		this.applyDd = applyDd;
	}

	public BigDecimal getTotRoomCnt() {
		return totRoomCnt;
	}

	public void setTotRoomCnt(BigDecimal totRoomCnt) {
		this.totRoomCnt = totRoomCnt;
	}

	public BigDecimal getSaleRoomCnt() {
		return saleRoomCnt;
	}

	public void setSaleRoomCnt(BigDecimal saleRoomCnt) {
		this.saleRoomCnt = saleRoomCnt;
	}

	public BigDecimal getSaleAmt() {
		return saleAmt;
	}

	public void setSaleAmt(BigDecimal saleAmt) {
		this.saleAmt = saleAmt;
	}

	public String getAutoEndYn() {
		return autoEndYn;
	}

	public void setAutoEndYn(String autoEndYn) {
		this.autoEndYn = autoEndYn;
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
