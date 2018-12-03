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
@Alias("ezcFaclReviewGrade")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="시설 후기 평점", description="시설 후기 평점 ( EZC_FACL_REVIEW_GRADE )", modelTypes="TABLE")
public class EzcFaclReviewGrade extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "시설 후기 유형", maxLength=8, required=true, constraints="EZC_FACL_REVIEW_GRADE_PK(P),SYS_C0011442(C) EZC_FACL_REVIEW_GRADE_PK(UNIQUE)")
	private String faclReviewType;

	@APIFields(description = "시설 후기 일련번호", maxLength=10, required=true, constraints="EZC_FACL_REVIEW_GRADE_PK(P),SYS_C0011443(C) EZC_FACL_REVIEW_GRADE_IF01(NONUNIQUE),EZC_FACL_REVIEW_GRADE_PK(UNIQUE)")
	private BigDecimal faclReviewSeq;

	@APIFields(description = "평점", maxLength=4)
	private BigDecimal grade;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011444(C)")
	private String regId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011445(C)")
	private String regDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss")
	private String modiDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());


	
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
