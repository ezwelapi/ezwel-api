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
@Alias("ezcFreqTxt")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="상용구 관리", description="상용구 관리 ( EZC_FREQ_TXT )", modelTypes="TABLE")
public class EzcFreqTxt extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "상용구 코드", maxLength=10, required=true, constraints="EZC_FREQ_TXT_PK(P),SYS_C0011455(C) EZC_FREQ_TXT_PK(UNIQUE)")
	private BigDecimal freqTxtCd;

	@APIFields(description = "상용구 분류", maxLength=8, required=true, constraints="SYS_C0011456(C)")
	private String freqTxtClass;

	@APIFields(description = "상용구", maxLength=1000)
	private String freqTxt;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011457(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011458(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss")
	private String modiDt;


	
	public BigDecimal getFreqTxtCd() {
		return freqTxtCd;
	}

	public void setFreqTxtCd(BigDecimal freqTxtCd) {
		this.freqTxtCd = freqTxtCd;
	}

	public String getFreqTxtClass() {
		return freqTxtClass;
	}

	public void setFreqTxtClass(String freqTxtClass) {
		this.freqTxtClass = freqTxtClass;
	}

	public String getFreqTxt() {
		return freqTxt;
	}

	public void setFreqTxt(String freqTxt) {
		this.freqTxt = freqTxt;
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
