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
 * 0.0.1      iCodeManager         2018-11-23 18:58:44      신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:58:44
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
//@Data
@Alias("ezcCacheDayPriceLog")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="CACHE 당일특가 로그", description="CACHE 당일특가 로그 ( EZC_CACHE_DAY_PRICE_LOG )", modelTypes="TABLE")
public class EzcCacheDayPriceLog extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "당일특가 로그 일련번호", maxLength=10, required=true, constraints="EZC_CACHE_DAY_PRICE_LOG_PK(P),SYS_C0011144(C) EZC_CACHE_DAY_PRICE_LOG_PK(UNIQUE)")
	private BigDecimal dayPriceLogSeq;

	@APIFields(description = "실행 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011145(C)")
	private String exeDt;

	@APIFields(description = "CACHE 만료 시간", maxLength=4, required=true, constraints="SYS_C0011146(C)")
	private String cacheEndTm;

	@APIFields(description = "실행 상태", maxLength=8, required=true, constraints="SYS_C0011147(C)")
	private String exeStatus;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011148(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011149(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss")
	private String modiDt;


	
	public BigDecimal getDayPriceLogSeq() {
		return dayPriceLogSeq;
	}

	public void setDayPriceLogSeq(BigDecimal dayPriceLogSeq) {
		this.dayPriceLogSeq = dayPriceLogSeq;
	}

	public String getExeDt() {
		return exeDt;
	}

	public void setExeDt(String exeDt) {
		this.exeDt = exeDt;
	}

	public String getCacheEndTm() {
		return cacheEndTm;
	}

	public void setCacheEndTm(String cacheEndTm) {
		this.cacheEndTm = cacheEndTm;
	}

	public String getExeStatus() {
		return exeStatus;
	}

	public void setExeStatus(String exeStatus) {
		this.exeStatus = exeStatus;
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
