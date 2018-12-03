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
 * 0.0.1      iCodeManager         2018-11-23 18:58:45      신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:58:45
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
//@Data
@Alias("ezcCpn")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="쿠폰", description="쿠폰 ( EZC_CPN )", modelTypes="TABLE")
public class EzcCpn extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "쿠폰 코드", maxLength=10, required=true, constraints="EZC_CPN_PK(P),SYS_C0011310(C) EZC_CPN_PK(UNIQUE)")
	private BigDecimal cpnCd;

	@APIFields(description = "쿠폰 명", maxLength=100, required=true, constraints="SYS_C0011311(C)")
	private String cpnNm;

	@APIFields(description = "디바이스 구분", maxLength=8, required=true, constraints="SYS_C0011312(C)")
	private String deviceDiv;

	@APIFields(description = "할인 유형", maxLength=8, required=true, constraints="SYS_C0011313(C)")
	private String dcType;

	@APIFields(description = "할인 값", maxLength=8, required=true, constraints="SYS_C0011314(C)")
	private BigDecimal dcValue;

	@APIFields(description = "발행 시작 일자", maxLength=8, required=true, constraints="SYS_C0011315(C)")
	private String issueStartDd;

	@APIFields(description = "발행 만료 일자", maxLength=8, required=true, constraints="SYS_C0011316(C)")
	private String issueEndDd;

	@APIFields(description = "발행 시작 시간", maxLength=4)
	private String issueStartTm;

	@APIFields(description = "발행 만료 시간", maxLength=4)
	private String issueEndTm;

	@APIFields(description = "매수 제한", maxLength=4)
	private BigDecimal purchaseLimit;

	@APIFields(description = "최소 적용 금액", maxLength=8)
	private BigDecimal minApplyAmt;

	@APIFields(description = "쿠폰 이미지 경로", maxLength=100)
	private String cpnImgPath;

	@APIFields(description = "쿠폰 이미지 파일명", maxLength=100)
	private String cpnImgFilenm;

	@APIFields(description = "발행 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011317(C)")
	private String issueDt;

	@APIFields(description = "발생 수량", maxLength=4)
	private BigDecimal issueQty;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011318(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011319(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss")
	private String modiDt;


	
	public BigDecimal getCpnCd() {
		return cpnCd;
	}

	public void setCpnCd(BigDecimal cpnCd) {
		this.cpnCd = cpnCd;
	}

	public String getCpnNm() {
		return cpnNm;
	}

	public void setCpnNm(String cpnNm) {
		this.cpnNm = cpnNm;
	}

	public String getDeviceDiv() {
		return deviceDiv;
	}

	public void setDeviceDiv(String deviceDiv) {
		this.deviceDiv = deviceDiv;
	}

	public String getDcType() {
		return dcType;
	}

	public void setDcType(String dcType) {
		this.dcType = dcType;
	}

	public BigDecimal getDcValue() {
		return dcValue;
	}

	public void setDcValue(BigDecimal dcValue) {
		this.dcValue = dcValue;
	}

	public String getIssueStartDd() {
		return issueStartDd;
	}

	public void setIssueStartDd(String issueStartDd) {
		this.issueStartDd = issueStartDd;
	}

	public String getIssueEndDd() {
		return issueEndDd;
	}

	public void setIssueEndDd(String issueEndDd) {
		this.issueEndDd = issueEndDd;
	}

	public String getIssueStartTm() {
		return issueStartTm;
	}

	public void setIssueStartTm(String issueStartTm) {
		this.issueStartTm = issueStartTm;
	}

	public String getIssueEndTm() {
		return issueEndTm;
	}

	public void setIssueEndTm(String issueEndTm) {
		this.issueEndTm = issueEndTm;
	}

	public BigDecimal getPurchaseLimit() {
		return purchaseLimit;
	}

	public void setPurchaseLimit(BigDecimal purchaseLimit) {
		this.purchaseLimit = purchaseLimit;
	}

	public BigDecimal getMinApplyAmt() {
		return minApplyAmt;
	}

	public void setMinApplyAmt(BigDecimal minApplyAmt) {
		this.minApplyAmt = minApplyAmt;
	}

	public String getCpnImgPath() {
		return cpnImgPath;
	}

	public void setCpnImgPath(String cpnImgPath) {
		this.cpnImgPath = cpnImgPath;
	}

	public String getCpnImgFilenm() {
		return cpnImgFilenm;
	}

	public void setCpnImgFilenm(String cpnImgFilenm) {
		this.cpnImgFilenm = cpnImgFilenm;
	}

	public String getIssueDt() {
		return issueDt;
	}

	public void setIssueDt(String issueDt) {
		this.issueDt = issueDt;
	}

	public BigDecimal getIssueQty() {
		return issueQty;
	}

	public void setIssueQty(BigDecimal issueQty) {
		this.issueQty = issueQty;
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
