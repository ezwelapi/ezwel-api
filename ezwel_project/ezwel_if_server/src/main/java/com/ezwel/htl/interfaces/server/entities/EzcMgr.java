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
 * 0.0.1      CodeSkeleton         2018-11-23 18:55:42                신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:55:42
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
@Data
@Alias("ezcMgr")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="운영자 정보", description="운영자 정보 ( EZC_MGR )", modelTypes="TABLE")
public class EzcMgr extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "운영자 ID", maxLength=20, required=true, constraints="EZC_MGR_PK(P),SYS_C0011479(C) EZC_MGR_PK(UNIQUE)")
	private String mgrId;

	@APIFields(description = "운영자 유형", maxLength=8, required=true, constraints="SYS_C0011480(C)")
	private String mgrType;

	@APIFields(description = "고객사 코드", maxLength=20, constraints="FK_EZC_CLIENT_INFO_EZC_MGR(R) EZC_MGR_IF01(NONUNIQUE)")
	private String clientCd;

	@APIFields(description = "제휴사 코드", maxLength=20, constraints="FK_EZC_PARTNER_EZC_MGR(R) EZC_MGR_IF02(NONUNIQUE)")
	private String partnerCd;

	@APIFields(description = "운영자 명", maxLength=20)
	private String mgrNm;

	@APIFields(description = "비밀번호", maxLength=100)
	private String passwd;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011481(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011482(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss")
	private String modiDt;


	
	public String getMgrId() {
		return mgrId;
	}

	public void setMgrId(String mgrId) {
		this.mgrId = mgrId;
	}

	public String getMgrType() {
		return mgrType;
	}

	public void setMgrType(String mgrType) {
		this.mgrType = mgrType;
	}

	public String getClientCd() {
		return clientCd;
	}

	public void setClientCd(String clientCd) {
		this.clientCd = clientCd;
	}

	public String getPartnerCd() {
		return partnerCd;
	}

	public void setPartnerCd(String partnerCd) {
		this.partnerCd = partnerCd;
	}

	public String getMgrNm() {
		return mgrNm;
	}

	public void setMgrNm(String mgrNm) {
		this.mgrNm = mgrNm;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
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
