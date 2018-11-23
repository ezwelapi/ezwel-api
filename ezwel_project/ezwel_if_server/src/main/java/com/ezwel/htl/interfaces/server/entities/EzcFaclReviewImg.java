package com.ezwel.htl.interfaces.server.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
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
@Data
@Alias("ezcFaclReviewImg")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="시설 후기 이미지", description="시설 후기 이미지 ( EZC_FACL_REVIEW_IMG )", modelTypes="TABLE")
public class EzcFaclReviewImg extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "후기 이미지 일련번호", maxLength=10, required=true, constraints="EZC_FACL_REVIEW_IMG_PK(P),SYS_C0011447(C) EZC_FACL_REVIEW_IMG_PK(UNIQUE)")
	private BigDecimal reviewImgSeq;

	@APIFields(description = "파일 경로", maxLength=100, required=true, constraints="SYS_C0011448(C)")
	private String filePath;

	@APIFields(description = "시스템 파일명", maxLength=100, required=true, constraints="SYS_C0011449(C)")
	private String sysFilenm;

	@APIFields(description = "파일명", maxLength=100, required=true, constraints="SYS_C0011450(C)")
	private String filenm;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011451(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011452(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss")
	private String modiDt;

	@APIFields(description = "시설 후기 일련번호", maxLength=10, required=true, constraints="SYS_C0011453(C) EZC_FACL_REVIEW_IMG_IF01(NONUNIQUE)")
	private BigDecimal faclReviewSeq;


	
	public BigDecimal getReviewImgSeq() {
		return reviewImgSeq;
	}

	public void setReviewImgSeq(BigDecimal reviewImgSeq) {
		this.reviewImgSeq = reviewImgSeq;
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

	public BigDecimal getFaclReviewSeq() {
		return faclReviewSeq;
	}

	public void setFaclReviewSeq(BigDecimal faclReviewSeq) {
		this.faclReviewSeq = faclReviewSeq;
	}


}	
