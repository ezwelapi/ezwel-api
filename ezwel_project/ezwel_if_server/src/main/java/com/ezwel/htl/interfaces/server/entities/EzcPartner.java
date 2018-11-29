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
 * 0.0.1      iCodeManager         2018-11-23 18:58:46      신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:58:46
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
//@Data
@Alias("ezcPartner")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="제휴사 정보", description="제휴사 정보 ( EZC_PARTNER )", modelTypes="TABLE")
public class EzcPartner extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "제휴사 코드", maxLength=20, required=true, constraints="EZC_PARTNER_PK(P),SYS_C0011512(C) EZC_PARTNER_PK(UNIQUE)")
	private String partnerCd;

	@APIFields(description = "제휴사 유형", maxLength=8, required=true, constraints="SYS_C0011513(C)")
	private String partnerType;

	@APIFields(description = "제휴사 코드 유형", maxLength=8, required=true, constraints="SYS_C0011514(C)")
	private String partnerCdType;

	@APIFields(description = "지불 구분", maxLength=8, required=true, constraints="SYS_C0011515(C)")
	private String payDiv;

	@APIFields(description = "정산 기준 유형", maxLength=8, required=true, constraints="SYS_C0011516(C)")
	private String calStdType;

	@APIFields(description = "금액 기준 유형", maxLength=8, required=true, constraints="SYS_C0011517(C)")
	private String amtStdType;

	@APIFields(description = "제휴사 명", maxLength=100, required=true, constraints="SYS_C0011518(C)")
	private String partnerNm;

	@APIFields(description = "사업자 등록번호", maxLength=12)
	private String licRegnum;

	@APIFields(description = "대표자명", maxLength=20)
	private String ceonm;

	@APIFields(description = "전화 번호", maxLength=20)
	private String telNum;

	@APIFields(description = "팩스 번호", maxLength=100)
	private String faxNum;

	@APIFields(description = "주소", maxLength=100)
	private String addr;

	@APIFields(description = "상세 주소", maxLength=100)
	private String detailAddr;

	@APIFields(description = "이지웰 담당자 ID", maxLength=20)
	private String ezwelMgrId;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011519(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011520(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss")
	private String modiDt;


	
	public String getPartnerCd() {
		return partnerCd;
	}

	public void setPartnerCd(String partnerCd) {
		this.partnerCd = partnerCd;
	}

	public String getPartnerType() {
		return partnerType;
	}

	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}

	public String getPartnerCdType() {
		return partnerCdType;
	}

	public void setPartnerCdType(String partnerCdType) {
		this.partnerCdType = partnerCdType;
	}

	public String getPayDiv() {
		return payDiv;
	}

	public void setPayDiv(String payDiv) {
		this.payDiv = payDiv;
	}

	public String getCalStdType() {
		return calStdType;
	}

	public void setCalStdType(String calStdType) {
		this.calStdType = calStdType;
	}

	public String getAmtStdType() {
		return amtStdType;
	}

	public void setAmtStdType(String amtStdType) {
		this.amtStdType = amtStdType;
	}

	public String getPartnerNm() {
		return partnerNm;
	}

	public void setPartnerNm(String partnerNm) {
		this.partnerNm = partnerNm;
	}

	public String getLicRegnum() {
		return licRegnum;
	}

	public void setLicRegnum(String licRegnum) {
		this.licRegnum = licRegnum;
	}

	public String getCeonm() {
		return ceonm;
	}

	public void setCeonm(String ceonm) {
		this.ceonm = ceonm;
	}

	public String getTelNum() {
		return telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	public String getFaxNum() {
		return faxNum;
	}

	public void setFaxNum(String faxNum) {
		this.faxNum = faxNum;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getDetailAddr() {
		return detailAddr;
	}

	public void setDetailAddr(String detailAddr) {
		this.detailAddr = detailAddr;
	}

	public String getEzwelMgrId() {
		return ezwelMgrId;
	}

	public void setEzwelMgrId(String ezwelMgrId) {
		this.ezwelMgrId = ezwelMgrId;
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
