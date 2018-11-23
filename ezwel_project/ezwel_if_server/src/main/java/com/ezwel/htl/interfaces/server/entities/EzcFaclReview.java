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
 * 0.0.1      CodeSkeleton         2018-11-23 18:44:42                신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:44:42
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
@Data
@Alias("ezcFaclReview")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="시설 후기", description="시설 후기 ( EZC_FACL_REVIEW )", modelTypes="TABLE")
public class EzcFaclReview extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "시설 후기 일련번호", maxLength=10, required=true, constraints = "EZC_FACL_REVIEW_PK(P),SYS_C0011436(C) EZC_FACL_REVIEW_PK(UNIQUE)")
	private BigDecimal faclReviewSeq;

	@APIFields(description = "예약 번호", maxLength=10, required=true, constraints = "SYS_C0011437(C) EZC_FACL_REVIEW_IF02(NONUNIQUE)")
	private BigDecimal reservNum;

	@APIFields(description = "후기 내용", maxLength=1000)
	private String reviewContent;

	@APIFields(description = "이미지 여부", maxLength=1, required=true, constraints = "SYS_C0011438(C)")
	private String imgYn;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints = "SYS_C0011439(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, required=true, constraints = "SYS_C0011440(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true)
	private String modiDt;


	
	public BigDecimal getFaclReviewSeq() {
		return faclReviewSeq;
	}

	public void setFaclReviewSeq(BigDecimal faclReviewSeq) {
		this.faclReviewSeq = faclReviewSeq;
	}

	public BigDecimal getReservNum() {
		return reservNum;
	}

	public void setReservNum(BigDecimal reservNum) {
		this.reservNum = reservNum;
	}

	public String getReviewContent() {
		return reviewContent;
	}

	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}

	public String getImgYn() {
		return imgYn;
	}

	public void setImgYn(String imgYn) {
		this.imgYn = imgYn;
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
