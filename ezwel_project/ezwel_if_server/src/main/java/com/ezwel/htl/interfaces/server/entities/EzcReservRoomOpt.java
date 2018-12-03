package com.ezwel.htl.interfaces.server.entities;

//import lombok.Data;
//import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractEntity;
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
//@Data
@Alias("ezcReservRoomOpt")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="예약 객실 옵션", description="예약 객실 옵션 ( EZC_RESERV_ROOM_OPT )", modelTypes="TABLE")
public class EzcReservRoomOpt extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "예약 옵션 번호", maxLength=10, required=true, constraints="EZC_RESERV_ROOM_OPT_PK(P),SYS_C0011657(C) EZC_RESERV_ROOM_OPT_PK(UNIQUE)")
	private BigDecimal reservOptNum;

	@APIFields(description = "예약 객실 번호", maxLength=10, required=true, constraints="SYS_C0011658(C) EZC_RESERV_ROOM_OPT_IF01(NONUNIQUE)")
	private BigDecimal reservRoomNum;

	@APIFields(description = "제휴사 옵션 주문 번호", maxLength=100)
	private String partnerOptOrderNum;

	@APIFields(description = "제휴사 옵션 코드", maxLength=100, required=true, constraints="SYS_C0011659(C)")
	private String partnerOptCd;

	@APIFields(description = "옵션 명", maxLength=100, required=true, constraints="SYS_C0011660(C)")
	private String optNm;

	@APIFields(description = "옵션 금액", maxLength=8, required=true, constraints="SYS_C0011661(C)")
	private BigDecimal optAmt;

	@APIFields(description = "옵션 수량", maxLength=4)
	private BigDecimal optQty;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011662(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011663(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss")
	private String modiDt;

	@APIFields(description = "직영/숙박 옵션 코드", maxLength=10)
	private BigDecimal distOptCd;


	
	public BigDecimal getReservOptNum() {
		return reservOptNum;
	}

	public void setReservOptNum(BigDecimal reservOptNum) {
		this.reservOptNum = reservOptNum;
	}

	public BigDecimal getReservRoomNum() {
		return reservRoomNum;
	}

	public void setReservRoomNum(BigDecimal reservRoomNum) {
		this.reservRoomNum = reservRoomNum;
	}

	public String getPartnerOptOrderNum() {
		return partnerOptOrderNum;
	}

	public void setPartnerOptOrderNum(String partnerOptOrderNum) {
		this.partnerOptOrderNum = partnerOptOrderNum;
	}

	public String getPartnerOptCd() {
		return partnerOptCd;
	}

	public void setPartnerOptCd(String partnerOptCd) {
		this.partnerOptCd = partnerOptCd;
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

	public BigDecimal getOptQty() {
		return optQty;
	}

	public void setOptQty(BigDecimal optQty) {
		this.optQty = optQty;
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

	public BigDecimal getDistOptCd() {
		return distOptCd;
	}

	public void setDistOptCd(BigDecimal distOptCd) {
		this.distOptCd = distOptCd;
	}


}	
