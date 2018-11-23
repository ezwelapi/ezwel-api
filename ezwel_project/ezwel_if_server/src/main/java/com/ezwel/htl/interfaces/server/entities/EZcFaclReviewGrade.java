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
@Alias("ezcfaclreviewgrade")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="시설 후기 평점", description="시설 후기 평점 ( EZC_FACL_REVIEW_GRADE )", modelTypes="TABLE")
public class EZcFaclReviewGrade extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "시설 후기 유형", maxLength=8, required=true, constraints = "EZC_FACL_REVIEW_GRADE_PK(P),SYS_C0011442(C) EZC_FACL_REVIEW_GRADE_PK(UNIQUE)")
	private String faclReviewType;

	@APIFields(description = "시설 후기 일련번호", maxLength=10, required=true, constraints = "EZC_FACL_REVIEW_GRADE_PK(P),SYS_C0011443(C) EZC_FACL_REVIEW_GRADE_IF01(NONUNIQUE),EZC_FACL_REVIEW_GRADE_PK(UNIQUE)")
	private BigDecimal faclReviewSeq;

	@APIFields(description = "평점", maxLength=4)
	private BigDecimal grade;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints = "SYS_C0011444(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, required=true, constraints = "SYS_C0011445(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true)
	private String modiDt;


	
	public String getFaclReviewType() {
		return faclReviewType;
	}

	public void setFaclReviewType(String faclReviewType) {
		this.faclReviewType = faclReviewType;
	}

	public BigDecimal getFaclReviewSeq() {
		return faclReviewSeq;
	}

	public void setFaclReviewSeq(BigDecimal faclReviewSeq) {
		this.faclReviewSeq = faclReviewSeq;
	}

	public BigDecimal getGrade() {
		return grade;
	}

	public void setGrade(BigDecimal grade) {
		this.grade = grade;
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
