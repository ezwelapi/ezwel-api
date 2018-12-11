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
 * 0.0.1      iCodeManager         2018-11-23 18:58:45      신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:58:45
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
//@Data
@Alias("ezcFreqTxt")
//@EqualsAndHashCode(callSuper=true)
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
	private String regId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyyMMddHHmmss", required=true, constraints="SYS_C0011458(C)")
	private String regDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyyMMddHHmmss")
	private String modiDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());


	
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
