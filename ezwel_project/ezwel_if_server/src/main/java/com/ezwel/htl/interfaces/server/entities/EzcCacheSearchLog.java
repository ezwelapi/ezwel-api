package com.ezwel.htl.interfaces.server.entities;

//import lombok.Data;
//import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;
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
@Alias("ezcCacheSearchLog")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="CACHE 검색 로그", description="CACHE 검색 로그 ( EZC_CACHE_SEARCH_LOG )", modelTypes="TABLE")
public class EzcCacheSearchLog extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "체크인 일자", maxLength=8, required=true, constraints="EZC_CACHE_SEARCH_LOG_PK(P),SYS_C0011169(C) EZC_CACHE_SEARCH_LOG_PK(UNIQUE)")
	private String checkInDd;

	@APIFields(description = "체크아웃 일자", maxLength=8, required=true, constraints="EZC_CACHE_SEARCH_LOG_PK(P),SYS_C0011170(C) EZC_CACHE_SEARCH_LOG_PK(UNIQUE)")
	private String checkOutDd;

	@APIFields(description = "도시 코드", maxLength=10, required=true, constraints="EZC_CACHE_SEARCH_LOG_PK(P),SYS_C0011171(C) EZC_CACHE_SEARCH_LOG_PK(UNIQUE)")
	private String cityCd;

	@APIFields(description = "지역 코드", maxLength=10, required=true, constraints="EZC_CACHE_SEARCH_LOG_PK(P),SYS_C0011172(C) EZC_CACHE_SEARCH_LOG_PK(UNIQUE)")
	private String areaCd;

	@APIFields(description = "실행 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011173(C)")
	private String exeDt;

	@APIFields(description = "CACHE 만료 시간", maxLength=4, required=true, constraints="SYS_C0011174(C)")
	private String cacheEndTm;

	@APIFields(description = "실행 상태", maxLength=8, required=true, constraints="SYS_C0011175(C)")
	private String exeStatus;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011176(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011177(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss")
	private String modiDt;


	
	public String getCheckInDd() {
		return checkInDd;
	}

	public void setCheckInDd(String checkInDd) {
		this.checkInDd = checkInDd;
	}

	public String getCheckOutDd() {
		return checkOutDd;
	}

	public void setCheckOutDd(String checkOutDd) {
		this.checkOutDd = checkOutDd;
	}

	public String getCityCd() {
		return cityCd;
	}

	public void setCityCd(String cityCd) {
		this.cityCd = cityCd;
	}

	public String getAreaCd() {
		return areaCd;
	}

	public void setAreaCd(String areaCd) {
		this.areaCd = areaCd;
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
