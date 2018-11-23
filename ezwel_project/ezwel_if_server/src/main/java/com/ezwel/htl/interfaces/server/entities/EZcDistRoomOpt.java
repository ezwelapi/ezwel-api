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
 * 0.0.1      CodeSkeleton         2018-11-23 18:35:57                신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:35:57
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
@Data
@Alias("ezcdistroomopt")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="직영/숙박 객실 옵션", description="직영/숙박 객실 옵션 ( EZC_DIST_ROOM_OPT )", modelTypes="TABLE")
public class EZcDistRoomOpt extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "객실 코드", maxLength=10, required=true, constraints = "EZC_DIST_ROOM_OPT_PK(P),SYS_C0011430(C) EZC_DIST_ROOM_OPT_PK(UNIQUE),EZC_DIST_ROOM_OPT_IF01(NONUNIQUE)")
	private BigDecimal roomCd;

	@APIFields(description = "객실 옵션코드", maxLength=100, required=true, constraints = "EZC_DIST_ROOM_OPT_PK(P),SYS_C0011431(C) EZC_DIST_ROOM_OPT_PK(UNIQUE)")
	private String roomOptcd;

	@APIFields(description = "옵션 이름", maxLength=50)
	private String optNm;

	@APIFields(description = "옵션 금액", maxLength=8, required=true, constraints = "SYS_C0011432(C)")
	private BigDecimal optAmt;

	@APIFields(description = "최대 가능 수량", maxLength=4)
	private BigDecimal limitQty;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints = "SYS_C0011433(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, required=true, constraints = "SYS_C0011434(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true)
	private String modiDt;


	
	public BigDecimal getRoomCd() {
		return roomCd;
	}

	public void setRoomCd(BigDecimal roomCd) {
		this.roomCd = roomCd;
	}

	public String getRoomOptcd() {
		return roomOptcd;
	}

	public void setRoomOptcd(String roomOptcd) {
		this.roomOptcd = roomOptcd;
	}

	public String getOptNm() {
		return optNm;
	}

	public void setOptNm(String optNm) {
		this.optNm = optNm;
	}

	public BigDecimal getOptAmt() {
		return optAmt;
	}

	public void setOptAmt(BigDecimal optAmt) {
		this.optAmt = optAmt;
	}

	public BigDecimal getLimitQty() {
		return limitQty;
	}

	public void setLimitQty(BigDecimal limitQty) {
		this.limitQty = limitQty;
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
