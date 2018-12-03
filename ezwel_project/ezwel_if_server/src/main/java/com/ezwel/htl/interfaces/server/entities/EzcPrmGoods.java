package com.ezwel.htl.interfaces.server.entities;

//import lombok.Data;
//import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
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
@Alias("ezcPrmGoods")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="프로모션 상품", description="프로모션 상품 ( EZC_PRM_GOODS )", modelTypes="TABLE")
public class EzcPrmGoods extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "TAB 일련번호", maxLength=10, required=true, constraints="EZC_PRM_GOODS_PK(P),FK_EZC_PRM_TAB_EZC_PRM_GOODS(R),SYS_C0011555(C) EZC_PRM_GOODS_PK(UNIQUE),EZC_PRM_GOODS_IF01(NONUNIQUE)")
	private BigDecimal tabSeq;

	@APIFields(description = "그룹 시설 코드", maxLength=10, required=true, constraints="EZC_PRM_GOODS_PK(P),FK_EZC_MAPPING_GRP_FACL_EZC_PR(R),SYS_C0011556(C) EZC_PRM_GOODS_IF02(NONUNIQUE),EZC_PRM_GOODS_PK(UNIQUE)")
	private BigDecimal grpFaclCd;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011557(C)")
	private String regId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011558(C)")
	private String regDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());


	
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
