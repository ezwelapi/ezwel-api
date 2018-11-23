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
 * 0.0.1      CodeSkeleton         2018-11-23 18:44:43                신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:44:43
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
@Data
@Alias("ezcPrmSurvey")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="프로모션 설문", description="프로모션 설문 ( EZC_PRM_SURVEY )", modelTypes="TABLE")
public class EzcPrmSurvey extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "프로모션 코드", maxLength=10, required=true, constraints = "EZC_PRM_SURVEY_PK(P),FK_EZC_PRM_MNG_EZC_PRM_SURVEY(R),SYS_C0011572(C) EZC_PRM_SURVEY_PK(UNIQUE)")
	private String prmCd;

	@APIFields(description = "설문 제목", maxLength=100)
	private String surveyTitle;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints = "SYS_C0011573(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, required=true, constraints = "SYS_C0011574(C)")
	private String regDt;


	
	public String getPrmCd() {
		return prmCd;
	}

	public void setPrmCd(String prmCd) {
		this.prmCd = prmCd;
	}

	public String getSurveyTitle() {
		return surveyTitle;
	}

	public void setSurveyTitle(String surveyTitle) {
		this.surveyTitle = surveyTitle;
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
