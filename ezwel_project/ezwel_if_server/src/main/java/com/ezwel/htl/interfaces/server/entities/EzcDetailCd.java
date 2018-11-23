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
@Alias("ezcDetailCd")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="공통 코드 정보", description="공통 코드 정보 ( EZC_DETAIL_CD )", modelTypes="TABLE")
public class EzcDetailCd extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "공통 코드", maxLength=8, required=true, constraints = "EZC_DETAIL_CD_PK(P),SYS_C0011381(C) EZC_DETAIL_CD_PK(UNIQUE)")
	private String masterCd;

	@APIFields(description = "상세 코드", maxLength=4, required=true, constraints = "SYS_C0011382(C)")
	private String detailCd;

	@APIFields(description = "분류 코드", maxLength=4, required=true, constraints = "FK_EZC_CLASS_CD_EZC_DETAIL_CD(R),SYS_C0011383(C) EZC_DETAIL_CD_IF01(NONUNIQUE)")
	private String classCd;

	@APIFields(description = "상세 명", maxLength=50)
	private String detailNm;

	@APIFields(description = "정렬순서", maxLength=4)
	private BigDecimal dispOrder;

	@APIFields(description = "사용 여부", maxLength=1, required=true, constraints = "SYS_C0011384(C)")
	private String useYn;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints = "SYS_C0011385(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, required=true, constraints = "SYS_C0011386(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true)
	private String modiDt;


	
	public String getMasterCd() {
		return masterCd;
	}

	public void setMasterCd(String masterCd) {
		this.masterCd = masterCd;
	}

	public String getDetailCd() {
		return detailCd;
	}

	public void setDetailCd(String detailCd) {
		this.detailCd = detailCd;
	}

	public String getClassCd() {
		return classCd;
	}

	public void setClassCd(String classCd) {
		this.classCd = classCd;
	}

	public String getDetailNm() {
		return detailNm;
	}

	public void setDetailNm(String detailNm) {
		this.detailNm = detailNm;
	}

	public BigDecimal getDispOrder() {
		return dispOrder;
	}

	public void setDispOrder(BigDecimal dispOrder) {
		this.dispOrder = dispOrder;
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
