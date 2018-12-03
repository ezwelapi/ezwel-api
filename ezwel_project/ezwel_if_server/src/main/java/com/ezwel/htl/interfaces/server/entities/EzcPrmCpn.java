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
@Alias("ezcPrmCpn")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="프로모션 쿠폰", description="프로모션 쿠폰 ( EZC_PRM_CPN )", modelTypes="TABLE")
public class EzcPrmCpn extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "프로모션 코드", maxLength=10, required=true, constraints="EZC_PRM_CPN_PK(P),FK_EZC_PRM_MNG_EZC_PRM_CPN(R),SYS_C0011550(C) EZC_PRM_CPN_PK(UNIQUE),EZC_PRM_CPN_IF01(NONUNIQUE)")
	private String prmCd;

	@APIFields(description = "쿠폰 코드", maxLength=10, required=true, constraints="EZC_PRM_CPN_PK(P),FK_EZC_CPN_EZC_PRM_CPN(R),SYS_C0011551(C) EZC_PRM_CPN_IF02(NONUNIQUE),EZC_PRM_CPN_PK(UNIQUE)")
	private BigDecimal cpnCd;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011552(C)")
	private String regId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011553(C)")
	private String regDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());


	
	public String getPrmCd() {
		return prmCd;
	}

	public void setPrmCd(String prmCd) {
		this.prmCd = prmCd;
	}

	public BigDecimal getCpnCd() {
		return cpnCd;
	}

	public void setCpnCd(BigDecimal cpnCd) {
		this.cpnCd = cpnCd;
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
