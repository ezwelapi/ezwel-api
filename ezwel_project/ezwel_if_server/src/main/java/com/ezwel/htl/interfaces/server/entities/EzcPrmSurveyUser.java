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
 * 0.0.1      CodeSkeleton         2018-11-23 18:46:12                신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:46:12
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
@Data
@Alias("ezcPrmSurveyUser")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="프로모션 설문 참여자", description="프로모션 설문 참여자 ( EZC_PRM_SURVEY_USER )", modelTypes="TABLE")
public class EzcPrmSurveyUser extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "프로모션 코드", maxLength=10, required=true, constraints="EZC_PRM_SURVEY_USER_PK(P),SYS_C0011581(C) EZC_PRM_SURVEY_USER_PK(UNIQUE),EZC_PRM_SURVEY_USER_IF01(NONUNIQUE)")
	private String prmCd;

	@APIFields(description = "회원 ID", maxLength=20, required=true, constraints="EZC_PRM_SURVEY_USER_PK(P),SYS_C0011582(C) EZC_PRM_SURVEY_USER_PK(UNIQUE)")
	private String userId;

	@APIFields(description = "답변 번호", maxLength=4)
	private BigDecimal answerNum;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011583(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, required=true, constraints="SYS_C0011584(C)")
	private String regDt;


	
	public String getPrmCd() {
		return prmCd;
	}

	public void setPrmCd(String prmCd) {
		this.prmCd = prmCd;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getAnswerNum() {
		return answerNum;
	}

	public void setAnswerNum(BigDecimal answerNum) {
		this.answerNum = answerNum;
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


}	