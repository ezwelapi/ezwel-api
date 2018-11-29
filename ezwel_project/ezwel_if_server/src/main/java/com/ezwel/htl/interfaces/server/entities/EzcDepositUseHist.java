package com.ezwel.htl.interfaces.server.entities;

//import lombok.Data;
//import lombok.EqualsAndHashCode;
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
 * 0.0.1      iCodeManager         2018-11-23 18:58:45      신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:58:45
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
//@Data
@Alias("ezcDepositUseHist")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="예치금 사용 내역", description="예치금 사용 내역 ( EZC_DEPOSIT_USE_HIST )", modelTypes="TABLE")
public class EzcDepositUseHist extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "예치금 사용 일련번호", maxLength=10, required=true, constraints="EZC_DEPOSIT_USE_HIST_PK(P),SYS_C0011372(C) EZC_DEPOSIT_USE_HIST_PK(UNIQUE)")
	private BigDecimal depositUseSeq;

	@APIFields(description = "제휴사 코드", maxLength=20, required=true, constraints="FK_EZC_DEPOSIT_EZC_DEPOSIT_USE(R),SYS_C0011373(C) EZC_DEPOSIT_USE_HIST_IF01(NONUNIQUE)")
	private String partnerCd;

	@APIFields(description = "예약 번호", maxLength=10, required=true, constraints="SYS_C0011374(C) EZC_DEPOSIT_USE_HIST_IF02(NONUNIQUE)")
	private BigDecimal reservNum;

	@APIFields(description = "지불 구분", maxLength=8, required=true, constraints="SYS_C0011375(C)")
	private String payDiv;

	@APIFields(description = "송금 일자", maxLength=8, required=true, constraints="SYS_C0011376(C)")
	private String payTransDd;

	@APIFields(description = "사용 예치 금액", maxLength=8, required=true, constraints="SYS_C0011377(C)")
	private BigDecimal useDepositAmt;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011378(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011379(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss")
	private String modiDt;


	
	public BigDecimal getDepositUseSeq() {
		return depositUseSeq;
	}

	public void setDepositUseSeq(BigDecimal depositUseSeq) {
		this.depositUseSeq = depositUseSeq;
	}

	public String getPartnerCd() {
		return partnerCd;
	}

	public void setPartnerCd(String partnerCd) {
		this.partnerCd = partnerCd;
	}

	public BigDecimal getReservNum() {
		return reservNum;
	}

	public void setReservNum(BigDecimal reservNum) {
		this.reservNum = reservNum;
	}

	public String getPayDiv() {
		return payDiv;
	}

	public void setPayDiv(String payDiv) {
		this.payDiv = payDiv;
	}

	public String getPayTransDd() {
		return payTransDd;
	}

	public void setPayTransDd(String payTransDd) {
		this.payTransDd = payTransDd;
	}

	public BigDecimal getUseDepositAmt() {
		return useDepositAmt;
	}

	public void setUseDepositAmt(BigDecimal useDepositAmt) {
		this.useDepositAmt = useDepositAmt;
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
