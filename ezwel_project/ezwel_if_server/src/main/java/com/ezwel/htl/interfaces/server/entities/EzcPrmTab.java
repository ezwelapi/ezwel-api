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
 * 0.0.1      iCodeManager         2018-11-23 18:58:46      신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:58:46
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
//@Data
@Alias("ezcPrmTab")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="프로모션 TAB", description="프로모션 TAB ( EZC_PRM_TAB )", modelTypes="TABLE")
public class EzcPrmTab extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "TAB 일련번호", maxLength=10, required=true, constraints="EZC_PRM_TAB_PK(P),SYS_C0011586(C) EZC_PRM_TAB_PK(UNIQUE)")
	private BigDecimal tabSeq;

	@APIFields(description = "프로모션 코드", maxLength=10, required=true, constraints="FK_EZC_PRM_MNG_EZC_PRM_TAB(R),SYS_C0011587(C) EZC_PRM_TAB_IF02(NONUNIQUE)")
	private String prmCd;

	@APIFields(description = "TAB 명", maxLength=100, required=true, constraints="SYS_C0011588(C)")
	private String tabNm;

	@APIFields(description = "상품 노출 배열", maxLength=4)
	private BigDecimal goodsDispArr;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011589(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011590(C)")
	private String regDt;


	
	public BigDecimal getTabSeq() {
		return tabSeq;
	}

	public void setTabSeq(BigDecimal tabSeq) {
		this.tabSeq = tabSeq;
	}

	public String getPrmCd() {
		return prmCd;
	}

	public void setPrmCd(String prmCd) {
		this.prmCd = prmCd;
	}

	public String getTabNm() {
		return tabNm;
	}

	public void setTabNm(String tabNm) {
		this.tabNm = tabNm;
	}

	public BigDecimal getGoodsDispArr() {
		return goodsDispArr;
	}

	public void setGoodsDispArr(BigDecimal goodsDispArr) {
		this.goodsDispArr = goodsDispArr;
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
