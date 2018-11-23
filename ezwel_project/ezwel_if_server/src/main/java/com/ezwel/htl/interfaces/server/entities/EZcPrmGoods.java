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
@Alias("ezcprmgoods")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="프로모션 상품", description="프로모션 상품 ( EZC_PRM_GOODS )", modelTypes="TABLE")
public class EZcPrmGoods extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "TAB 일련번호", maxLength=10, required=true, constraints = "EZC_PRM_GOODS_PK(P),FK_EZC_PRM_TAB_EZC_PRM_GOODS(R),SYS_C0011555(C) EZC_PRM_GOODS_PK(UNIQUE),EZC_PRM_GOODS_IF01(NONUNIQUE)")
	private BigDecimal tabSeq;

	@APIFields(description = "그룹 시설 코드", maxLength=10, required=true, constraints = "EZC_PRM_GOODS_PK(P),FK_EZC_MAPPING_GRP_FACL_EZC_PR(R),SYS_C0011556(C) EZC_PRM_GOODS_IF02(NONUNIQUE),EZC_PRM_GOODS_PK(UNIQUE)")
	private BigDecimal grpFaclCd;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints = "SYS_C0011557(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, required=true, constraints = "SYS_C0011558(C)")
	private String regDt;


	
	public BigDecimal getTabSeq() {
		return tabSeq;
	}

	public void setTabSeq(BigDecimal tabSeq) {
		this.tabSeq = tabSeq;
	}

	public BigDecimal getGrpFaclCd() {
		return grpFaclCd;
	}

	public void setGrpFaclCd(BigDecimal grpFaclCd) {
		this.grpFaclCd = grpFaclCd;
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
