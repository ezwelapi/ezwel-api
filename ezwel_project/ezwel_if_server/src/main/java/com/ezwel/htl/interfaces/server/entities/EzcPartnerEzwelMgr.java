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
@Alias("ezcPartnerEzwelMgr")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="제휴사 이지웰 담당자", description="제휴사 이지웰 담당자 ( EZC_PARTNER_EZWEL_MGR )", modelTypes="TABLE")
public class EzcPartnerEzwelMgr extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "제휴사 이지웰 일련번호", maxLength=10, required=true, constraints="EZC_PARTNER_EZWEL_MGR_PK(P),SYS_C0011522(C) EZC_PARTNER_EZWEL_MGR_PK(UNIQUE)")
	private BigDecimal partnerEzwelSeq;

	@APIFields(description = "제휴사 코드", maxLength=20, required=true, constraints="FK_EZC_PARTNER_EZC_PARTNER_EZW(R),SYS_C0011523(C) EZC_PARTNER_EZWEL_MGR_IF01(NONUNIQUE)")
	private String partnerCd;

	@APIFields(description = "운영자 ID", maxLength=20, required=true, constraints="FK_EZC_MGR_EZC_PARTNER_EZWEL_M(R),SYS_C0011524(C) EZC_PARTNER_EZWEL_MGR_IF02(NONUNIQUE)")
	private String mgrId;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011525(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011526(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss")
	private String modiDt;


	
	public BigDecimal getPartnerEzwelSeq() {
		return partnerEzwelSeq;
	}

	public void setPartnerEzwelSeq(BigDecimal partnerEzwelSeq) {
		this.partnerEzwelSeq = partnerEzwelSeq;
	}

	public String getPartnerCd() {
		return partnerCd;
	}

	public void setPartnerCd(String partnerCd) {
		this.partnerCd = partnerCd;
	}

	public String getMgrId() {
		return mgrId;
	}

	public void setMgrId(String mgrId) {
		this.mgrId = mgrId;
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
