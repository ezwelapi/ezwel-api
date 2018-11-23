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
@Alias("ezcmem")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="회원 정보", description="회원 정보 ( EZC_MEM )", modelTypes="TABLE")
public class EZcMem extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "회원 ID", maxLength=20, required=true, constraints = "EZC_MEM_PK(P),SYS_C0011467(C) EZC_MEM_PK(UNIQUE)")
	private String userId;

	@APIFields(description = "고객사 코드", maxLength=20, required=true, constraints = "FK_EZC_CLIENT_INFO_EZC_MEM(R),SYS_C0011468(C) EZC_MEM_IF01(NONUNIQUE)")
	private String clientCd;

	@APIFields(description = "USER_KEY", maxLength=10)
	private BigDecimal userKey;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints = "SYS_C0011469(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, required=true, constraints = "SYS_C0011470(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true)
	private String modiDt;


	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getClientCd() {
		return clientCd;
	}

	public void setClientCd(String clientCd) {
		this.clientCd = clientCd;
	}

	public BigDecimal getUserKey() {
		return userKey;
	}

	public void setUserKey(BigDecimal userKey) {
		this.userKey = userKey;
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
