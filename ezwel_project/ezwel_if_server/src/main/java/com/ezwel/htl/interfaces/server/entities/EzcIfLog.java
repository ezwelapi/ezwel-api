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
 * 0.0.1      iCodeManager         2019-01-14 15:14:47      신규자동생성 
 * </pre>
 * 
 * @author developer
 * @since 2019-01-14 15:14:47
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
//@Data
@Alias("ezcIfLog")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="EZC 인터페이스 로그", description="EZC 인터페이스 로그 ( EZC_IF_LOG )", modelTypes="TABLE")
public class EzcIfLog extends AbstractEntity {
	
	@APIFields(description = "인터페이스 실행 코드", maxLength=32, required=true, constraints="EZC_IF_LOG_PK(P),SYS_C0013738(C) EZC_IF_LOG_PK(UNIQUE)")
	private String ifExecCd; // Repository 에서 생성

	@APIFields(description = "제휴사 에이젼트 ID", maxLength=20, required=true, constraints="SYS_C0013739(C)")
	private String partAgentId;

	@APIFields(description = "인터페이스 체널 ID", maxLength=100, required=true, constraints="SYS_C0013740(C)")
	private String ifChanId;

	@APIFields(description = "인터페이스 체널 그룹 ID", maxLength=100)
	private String ifChanGrpId;

	@APIFields(description = "인터페이스 설명", maxLength=4000)
	private String ifDesc;

	@APIFields(description = "인터페이스 API URI", maxLength=200, required=true, constraints="SYS_C0013741(C)")
	private String ifApiUri;

	@APIFields(description = "인터페이스 API KEY", maxLength=40, required=true, constraints="SYS_C0013742(C)")
	private String ifApiKey;

	@APIFields(description = "인터페이스 타임 스탬프", maxLength=30)
	private String ifTimeStmp;

	@APIFields(description = "인터페이스 시그니처", maxLength=120)
	private String ifSign;

	@APIFields(description = "인터페이스 요청자 ID", maxLength=20)
	private String ifReqtId;

	@APIFields(description = "인터페이스 요청 방향", maxLength=1, required=true, constraints="SYS_C0013743(C)")
	private String ifReqtDirt;

	@APIFields(description = "실행 시작 밀리 초", maxLength=19, required=true, constraints="SYS_C0013744(C)")
	private BigDecimal execStrtMlisSecd;

	@APIFields(description = "입력 전문", maxLength=4000)
	private String inptTelg;

	@APIFields(description = "입력 전문 사이즈", maxLength=19)
	private BigDecimal inptTelgSize;

	@APIFields(description = "실행 종료 밀리 초", maxLength=19)
	private BigDecimal execEndMlisSecd;

	@APIFields(description = "출력 전문", maxLength=4000)
	private String outpTelg;

	@APIFields(description = "출력 전문 사이즈", maxLength=19)
	private BigDecimal outpTelgSize;

	@APIFields(description = "전체 수행 밀리 초", maxLength=19)
	private BigDecimal totlLapMlisSecd;

	@APIFields(description = "성공 여부", maxLength=1)
	private String succYn;

	@APIFields(description = "오류 유형", maxLength=8)
	private String errType;

	@APIFields(description = "오류 내용", maxLength=4000)
	private String errCont;

	
	public String getIfExecCd() {
		return ifExecCd;
	}

	public void setIfExecCd(String ifExecCd) {
		this.ifExecCd = ifExecCd;
	}

	public String getPartAgentId() {
		return partAgentId;
	}

	public void setPartAgentId(String partAgentId) {
		this.partAgentId = partAgentId;
	}

	public String getIfChanId() {
		return ifChanId;
	}

	public void setIfChanId(String ifChanId) {
		this.ifChanId = ifChanId;
	}

	public String getIfChanGrpId() {
		return ifChanGrpId;
	}

	public void setIfChanGrpId(String ifChanGrpId) {
		this.ifChanGrpId = ifChanGrpId;
	}

	public String getIfDesc() {
		return ifDesc;
	}

	public void setIfDesc(String ifDesc) {
		this.ifDesc = ifDesc;
	}

	public String getIfApiUri() {
		return ifApiUri;
	}

	public void setIfApiUri(String ifApiUri) {
		this.ifApiUri = ifApiUri;
	}

	public String getIfApiKey() {
		return ifApiKey;
	}

	public void setIfApiKey(String ifApiKey) {
		this.ifApiKey = ifApiKey;
	}

	public String getIfTimeStmp() {
		return ifTimeStmp;
	}

	public void setIfTimeStmp(String ifTimeStmp) {
		this.ifTimeStmp = ifTimeStmp;
	}

	public String getIfSign() {
		return ifSign;
	}

	public void setIfSign(String ifSign) {
		this.ifSign = ifSign;
	}

	public String getIfReqtId() {
		return ifReqtId;
	}

	public void setIfReqtId(String ifReqtId) {
		this.ifReqtId = ifReqtId;
	}

	public String getIfReqtDirt() {
		return ifReqtDirt;
	}

	public void setIfReqtDirt(String ifReqtDirt) {
		this.ifReqtDirt = ifReqtDirt;
	}

	public BigDecimal getExecStrtMlisSecd() {
		return execStrtMlisSecd;
	}

	public void setExecStrtMlisSecd(BigDecimal execStrtMlisSecd) {
		this.execStrtMlisSecd = execStrtMlisSecd;
	}

	public String getInptTelg() {
		return inptTelg;
	}

	public void setInptTelg(String inptTelg) {
		this.inptTelg = inptTelg;
	}

	public BigDecimal getInptTelgSize() {
		return inptTelgSize;
	}

	public void setInptTelgSize(BigDecimal inptTelgSize) {
		this.inptTelgSize = inptTelgSize;
	}

	public BigDecimal getExecEndMlisSecd() {
		return execEndMlisSecd;
	}

	public void setExecEndMlisSecd(BigDecimal execEndMlisSecd) {
		this.execEndMlisSecd = execEndMlisSecd;
	}

	public String getOutpTelg() {
		return outpTelg;
	}

	public void setOutpTelg(String outpTelg) {
		this.outpTelg = outpTelg;
	}

	public BigDecimal getOutpTelgSize() {
		return outpTelgSize;
	}

	public void setOutpTelgSize(BigDecimal outpTelgSize) {
		this.outpTelgSize = outpTelgSize;
	}

	public BigDecimal getTotlLapMlisSecd() {
		return totlLapMlisSecd;
	}

	public void setTotlLapMlisSecd(BigDecimal totlLapMlisSecd) {
		this.totlLapMlisSecd = totlLapMlisSecd;
	}

	public String getSuccYn() {
		return succYn;
	}

	public void setSuccYn(String succYn) {
		this.succYn = succYn;
	}

	public String getErrType() {
		return errType;
	}

	public void setErrType(String errType) {
		this.errType = errType;
	}

	public String getErrCont() {
		return errCont;
	}

	public void setErrCont(String errCont) {
		this.errCont = errCont;
	}


}	
