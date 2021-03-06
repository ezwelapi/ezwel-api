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
@Alias("ezcDistEvid")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="직영/숙박 증빙 파일", description="직영/숙박 증빙 파일 ( EZC_DIST_EVID )", modelTypes="TABLE")
public class EzcDistEvid extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "시설 증빙 일련번호", maxLength=10, required=true, constraints="EZC_DIST_EVID_PK(P),SYS_C0011398(C) EZC_DIST_EVID_PK(UNIQUE)")
	private BigDecimal faclEvidSeq;

	@APIFields(description = "시설 코드", maxLength=10, required=true, constraints="FK_EZC_FACL_EZC_DIST_EVID(R),SYS_C0011399(C) EZC_DIST_EVID_IF01(NONUNIQUE)")
	private BigDecimal faclCd;

	@APIFields(description = "시설 증빙 유형", maxLength=8, required=true, constraints="SYS_C0011400(C)")
	private String faclEvidType;

	@APIFields(description = "파일 경로", maxLength=100, required=true, constraints="SYS_C0011401(C)")
	private String filePath;

	@APIFields(description = "시스템 파일명", maxLength=100, required=true, constraints="SYS_C0011402(C)")
	private String sysFilenm;

	@APIFields(description = "파일명", maxLength=100, required=true, constraints="SYS_C0011403(C)")
	private String filenm;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011404(C)")
	private String regId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyyMMddHHmmss", required=true, constraints="SYS_C0011405(C)")
	private String regDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyyMMddHHmmss")
	private String modiDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());


	
	public BigDecimal getFaclEvidSeq() {
		return faclEvidSeq;
	}

	public void setFaclEvidSeq(BigDecimal faclEvidSeq) {
		this.faclEvidSeq = faclEvidSeq;
	}

	public BigDecimal getFaclCd() {
		return faclCd;
	}

	public void setFaclCd(BigDecimal faclCd) {
		this.faclCd = faclCd;
	}

	public String getFaclEvidType() {
		return faclEvidType;
	}

	public void setFaclEvidType(String faclEvidType) {
		this.faclEvidType = faclEvidType;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getSysFilenm() {
		return sysFilenm;
	}

	public void setSysFilenm(String sysFilenm) {
		this.sysFilenm = sysFilenm;
	}

	public String getFilenm() {
		return filenm;
	}

	public void setFilenm(String filenm) {
		this.filenm = filenm;
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
