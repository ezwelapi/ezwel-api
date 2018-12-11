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
 * 0.0.1      iCodeManager         2018-11-23 18:58:46      신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:58:46
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
//@Data
@Alias("ezcReservPay")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="예약 결제 정보", description="예약 결제 정보 ( EZC_RESERV_PAY )", modelTypes="TABLE")
public class EzcReservPay extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "예약 번호", maxLength=10, required=true, constraints="EZC_RESERV_PAY_PK(P),SYS_C0011634(C) EZC_RESERV_PAY_PK(UNIQUE)")
	private BigDecimal reservNum;

	@APIFields(description = "카드 결제 여부", maxLength=1, required=true, constraints="SYS_C0011635(C)")
	private String cardPayYn;

	@APIFields(description = "카드 결제 금액", maxLength=8)
	private BigDecimal cardPayAmt;

	@APIFields(description = "현금 결제 여부", maxLength=1, required=true, constraints="SYS_C0011636(C)")
	private String cashPayYn;

	@APIFields(description = "현금 결제 금액", maxLength=8)
	private BigDecimal cashPayAmt;

	@APIFields(description = "쿠폰 사용 여부", maxLength=1, required=true, constraints="SYS_C0011637(C)")
	private String cpnUseYn;

	@APIFields(description = "할인 쿠폰 금액", maxLength=8)
	private BigDecimal dcCpnAmt;

	@APIFields(description = "포인트 사용 여부", maxLength=1, required=true, constraints="SYS_C0011638(C)")
	private String pointUseYn;

	@APIFields(description = "포인트 금액", maxLength=8)
	private BigDecimal pointAmt;

	@APIFields(description = "미납 금액", maxLength=8, required=true, constraints="SYS_C0011639(C)")
	private BigDecimal nonAmt;

	@APIFields(description = "결제 상태", maxLength=8, required=true, constraints="SYS_C0011640(C)")
	private String payStatus;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011641(C)")
	private String regId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyyMMddHHmmss", required=true, constraints="SYS_C0011642(C)")
	private String regDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyyMMddHHmmss")
	private String modiDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());


	
	public BigDecimal getReservNum() {
		return reservNum;
	}

	public void setReservNum(BigDecimal reservNum) {
		this.reservNum = reservNum;
	}

	public String getCardPayYn() {
		return cardPayYn;
	}

	public void setCardPayYn(String cardPayYn) {
		this.cardPayYn = cardPayYn;
	}

	public BigDecimal getCardPayAmt() {
		return cardPayAmt;
	}

	public void setCardPayAmt(BigDecimal cardPayAmt) {
		this.cardPayAmt = cardPayAmt;
	}

	public String getCashPayYn() {
		return cashPayYn;
	}

	public void setCashPayYn(String cashPayYn) {
		this.cashPayYn = cashPayYn;
	}

	public BigDecimal getCashPayAmt() {
		return cashPayAmt;
	}

	public void setCashPayAmt(BigDecimal cashPayAmt) {
		this.cashPayAmt = cashPayAmt;
	}

	public String getCpnUseYn() {
		return cpnUseYn;
	}

	public void setCpnUseYn(String cpnUseYn) {
		this.cpnUseYn = cpnUseYn;
	}

	public BigDecimal getDcCpnAmt() {
		return dcCpnAmt;
	}

	public void setDcCpnAmt(BigDecimal dcCpnAmt) {
		this.dcCpnAmt = dcCpnAmt;
	}

	public String getPointUseYn() {
		return pointUseYn;
	}

	public void setPointUseYn(String pointUseYn) {
		this.pointUseYn = pointUseYn;
	}

	public BigDecimal getPointAmt() {
		return pointAmt;
	}

	public void setPointAmt(BigDecimal pointAmt) {
		this.pointAmt = pointAmt;
	}

	public BigDecimal getNonAmt() {
		return nonAmt;
	}

	public void setNonAmt(BigDecimal nonAmt) {
		this.nonAmt = nonAmt;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
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
