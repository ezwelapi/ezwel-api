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
 * 0.0.1      CodeSkeleton         2018-11-23 18:44:41                신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:44:41
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
@Data
@Alias("ezcClassCd")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="공통 분류 코드", description="공통 분류 코드 ( EZC_CLASS_CD )", modelTypes="TABLE")
public class EzcClassCd extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "분류 코드", maxLength=4, required=true, constraints = "EZC_CLAS_CD_PK(P),SYS_C0011273(C) EZC_CLAS_CD_PK(UNIQUE)")
	private String classCd;

	@APIFields(description = "분류 명", maxLength=50)
	private String classNm;

	@APIFields(description = "분류 설명", maxLength=1000)
	private String classDesc;

	@APIFields(description = "사용 여부", maxLength=1, required=true, constraints = "SYS_C0011274(C)")
	private String useYn;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints = "SYS_C0011275(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, required=true, constraints = "SYS_C0011276(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true)
	private String modiDt;


	
	public String getClassCd() {
		return classCd;
	}

	public void setClassCd(String classCd) {
		this.classCd = classCd;
	}

	public String getClassNm() {
		return classNm;
	}

	public void setClassNm(String classNm) {
		this.classNm = classNm;
	}

	public String getClassDesc() {
		return classDesc;
	}

	public void setClassDesc(String classDesc) {
		this.classDesc = classDesc;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
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
