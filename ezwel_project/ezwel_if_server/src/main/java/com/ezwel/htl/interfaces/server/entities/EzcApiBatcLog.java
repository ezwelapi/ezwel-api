package com.ezwel.htl.interfaces.server.entities;

import java.math.BigDecimal;

import org.apache.ibatis.type.Alias;

import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractEntity;

/**
 * <b>History : Entity Code Generated By iCodeManager Made by API Team KSW</b>
 *
 * <pre>
 * 버전              성명                                일자                                       변경내용
 * -------    ----------------     -------------------		-----------------
 * 0.0.1      iCodeManager         2019-01-17 16:03:40      신규자동생성 
 * </pre>
 * 
 * @author developer
 * @since 2019-01-17 16:03:40
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
//@Data
@Alias("ezcApiBatcLog")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="EZC API 배치 로그", description="EZC API 배치 로그 ( EZC_API_BATC_LOG )", modelTypes="TABLE")
public class EzcApiBatcLog extends AbstractEntity {
	
	@APIFields(description = "배치 실행 코드", maxLength=32, required=true, constraints="EZC_API_BATC_LOG_PK(P) EZC_API_BATC_LOG_PK(UNIQUE)")
	private String batcExecCd;

	@APIFields(description = "쓰레드 GUID", maxLength=32, required=true, constraints="EZC_API_BATC_LOG_PK(P) EZC_API_BATC_LOG_PK(UNIQUE)")
	private String thedGuid;

	@APIFields(description = "배치 프로그램 타입", maxLength=1000)
	private String batcProgType;

	@APIFields(description = "배치 설명", maxLength=4000)
	private String batcDesc;

	@APIFields(description = "배치 로그 유형 ( TM : 시간기록, ER : 에러기록, IV : INVALIDATE 유효성검증실패 )", maxLength=4, required=true, constraints="SYS_C0013886(C)")
	private String batcLogType;

	@APIFields(description = "실행 시작 밀리 초", maxLength=19, required=true, constraints="SYS_C0013887(C)")
	private BigDecimal execStrtMlisSecd;

	@APIFields(description = "실행 종료 밀리 초", maxLength=19)
	private BigDecimal execEndMlisSecd;

	@APIFields(description = "전체 수행 밀리 초", maxLength=19)
	private BigDecimal totlLapMlisSecd;

	@APIFields(description = "오류 유형", maxLength=2000)
	private String errMsg;

	@APIFields(description = "오류 내용", maxLength=4000)
	private String errCont;

	@APIFields(description = "배치 요청자 ID", maxLength=20, required=true, constraints="SYS_C0013888(C)")
	private String batcReqtId;

	@APIFields(description = "배치 요청자 IP", maxLength=20, required=true, constraints="SYS_C0013889(C)")
	private String batcReqtIp;


	
	public String getBatcExecCd() {
		return batcExecCd;
	}

	public void setBatcExecCd(String batcExecCd) {
		this.batcExecCd = batcExecCd;
	}

	public String getThedGuid() {
		return thedGuid;
	}

	public void setThedGuid(String thedGuid) {
		this.thedGuid = thedGuid;
	}

	public String getBatcProgType() {
		return batcProgType;
	}

	public void setBatcProgType(String batcProgType) {
		this.batcProgType = batcProgType;
	}

	public String getBatcDesc() {
		return batcDesc;
	}

	public void setBatcDesc(String batcDesc) {
		this.batcDesc = batcDesc;
	}

	public String getBatcLogType() {
		return batcLogType;
	}

	public void setBatcLogType(String batcLogType) {
		this.batcLogType = batcLogType;
	}

	public BigDecimal getExecStrtMlisSecd() {
		return execStrtMlisSecd;
	}

	public void setExecStrtMlisSecd(BigDecimal execStrtMlisSecd) {
		this.execStrtMlisSecd = execStrtMlisSecd;
	}

	public BigDecimal getExecEndMlisSecd() {
		return execEndMlisSecd;
	}

	public void setExecEndMlisSecd(BigDecimal execEndMlisSecd) {
		this.execEndMlisSecd = execEndMlisSecd;
	}

	public BigDecimal getTotlLapMlisSecd() {
		return totlLapMlisSecd;
	}

	public void setTotlLapMlisSecd(BigDecimal totlLapMlisSecd) {
		this.totlLapMlisSecd = totlLapMlisSecd;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getErrCont() {
		return errCont;
	}

	public void setErrCont(String errCont) {
		this.errCont = errCont;
	}

	public String getBatcReqtId() {
		return batcReqtId;
	}

	public void setBatcReqtId(String batcReqtId) {
		this.batcReqtId = batcReqtId;
	}

	public String getBatcReqtIp() {
		return batcReqtIp;
	}

	public void setBatcReqtIp(String batcReqtIp) {
		this.batcReqtIp = batcReqtIp;
	}


}	