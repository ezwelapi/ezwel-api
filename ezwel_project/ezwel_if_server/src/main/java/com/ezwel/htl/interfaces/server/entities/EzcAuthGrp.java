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
 * 0.0.1      CodeSkeleton         2018-11-23 18:55:41                신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:55:41
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
@Data
@Alias("ezcAuthGrp")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="권한 그룹", description="권한 그룹 ( EZC_AUTH_GRP )", modelTypes="TABLE")
public class EzcAuthGrp extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "권한 그룹 코드", maxLength=10, required=true, constraints="EZC_AUTH_GRP_PK(P),SYS_C0011214(C) EZC_AUTH_GRP_PK(UNIQUE)")
	private String authGrpCd;

	@APIFields(description = "시스템 유형", maxLength=8, required=true, constraints="SYS_C0011215(C)")
	private String sysType;

	@APIFields(description = "권한 그룹 명", maxLength=50)
	private String authGrpNm;

	@APIFields(description = "사용 여부", maxLength=1, required=true, constraints="SYS_C0011216(C)")
	private String useYn;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011217(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011218(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss")
	private String modiDt;


	
	public String getAuthGrpCd() {
		return authGrpCd;
	}

	public void setAuthGrpCd(String authGrpCd) {
		this.authGrpCd = authGrpCd;
	}

	public String getSysType() {
		return sysType;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}

	public String getAuthGrpNm() {
		return authGrpNm;
	}

	public void setAuthGrpNm(String authGrpNm) {
		this.authGrpNm = authGrpNm;
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
