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
@Alias("ezcDepositIssueHist")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="예치금 발생 내역", description="예치금 발생 내역 ( EZC_DEPOSIT_ISSUE_HIST )", modelTypes="TABLE")
public class EzcDepositIssueHist extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "예치금 발생 일련번호", maxLength=10, required=true, constraints="EZC_DEPOSIT_ISSUE_HIST_PK(P),SYS_C0011364(C) EZC_DEPOSIT_ISSUE_HIST_PK(UNIQUE)")
	private BigDecimal depositIssueSeq;

	@APIFields(description = "제휴사 코드", maxLength=20, required=true, constraints="FK_EZC_DEPOSIT_EZC_DEPOSIT_ISS(R),SYS_C0011365(C) EZC_DEPOSIT_ISSUE_HIST_IF02(NONUNIQUE)")
	private String partnerCd;

	@APIFields(description = "예약 번호", maxLength=10, required=true, constraints="FK_EZC_RESERV_BASE_EZC_DEPOSIT(R),SYS_C0011366(C) EZC_DEPOSIT_ISSUE_HIST_IF01(NONUNIQUE)")
	private BigDecimal reservNum;

	@APIFields(description = "발생 일자", maxLength=8, required=true, constraints="SYS_C0011367(C)")
	private String issueDd;

	@APIFields(description = "발생 예치 금액", maxLength=8, required=true, constraints="SYS_C0011368(C)")
	private BigDecimal issueDepositAmt;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011369(C)")
	private String regId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011370(C)")
	private String regDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss")
	private String modiDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());


	
	public BigDecimal getDepositIssueSeq() {
		return depositIssueSeq;
	}

	public void setDepositIssueSeq(BigDecimal depositIssueSeq) {
		this.depositIssueSeq = depositIssueSeq;
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

	public String getIssueDd() {
		return issueDd;
	}

	public void setIssueDd(String issueDd) {
		this.issueDd = issueDd;
	}

	public BigDecimal getIssueDepositAmt() {
		return issueDepositAmt;
	}

	public void setIssueDepositAmt(BigDecimal issueDepositAmt) {
		this.issueDepositAmt = issueDepositAmt;
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
