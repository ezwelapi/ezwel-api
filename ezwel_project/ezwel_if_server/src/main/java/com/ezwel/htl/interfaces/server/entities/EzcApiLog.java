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
 * 0.0.1      CodeSkeleton         2018-11-23 18:44:41                신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:44:41
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
@Data
@Alias("ezcApiLog")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="API 로그", description="API 로그 ( EZC_API_LOG )", modelTypes="TABLE")
public class EzcApiLog extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "API 로그 일련번호", maxLength=10, required=true, constraints = "EZC_API_LOG_PK(P),SYS_C0011151(C) EZC_API_LOG_PK(UNIQUE)")
	private BigDecimal apiLogSeq;

	@APIFields(description = "API 유형", maxLength=8, required=true, constraints = "SYS_C0011152(C)")
	private String apiType;

	@APIFields(description = "API 응답 구분", maxLength=8, required=true, constraints = "SYS_C0011153(C)")
	private String apiResponDiv;

	@APIFields(description = "에이전트 유형", maxLength=8, required=true, constraints = "SYS_C0011154(C)")
	private String agentType;

	@APIFields(description = "로그 일시", maxLength=14, isDate=true, required=true, constraints = "SYS_C0011155(C)")
	private String logDt;

	@APIFields(description = "오류 여부", maxLength=1, required=true, constraints = "SYS_C0011156(C)")
	private String errYn;

	@APIFields(description = "오류 메시지", maxLength=4000)
	private String errMsg;

	@APIFields(description = "REQ TXT", maxLength=4000)
	private String reqTxt;

	@APIFields(description = "RES TXT", maxLength=4000)
	private String resTxt;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints = "SYS_C0011157(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, required=true, constraints = "SYS_C0011158(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true)
	private String modiDt;


	
	public BigDecimal getApiLogSeq() {
		return apiLogSeq;
	}

	public void setApiLogSeq(BigDecimal apiLogSeq) {
		this.apiLogSeq = apiLogSeq;
	}

	public String getApiType() {
		return apiType;
	}

	public void setApiType(String apiType) {
		this.apiType = apiType;
	}

	public String getApiResponDiv() {
		return apiResponDiv;
	}

	public void setApiResponDiv(String apiResponDiv) {
		this.apiResponDiv = apiResponDiv;
	}

	public String getAgentType() {
		return agentType;
	}

	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}

	public String getLogDt() {
		return logDt;
	}

	public void setLogDt(String logDt) {
		this.logDt = logDt;
	}

	public String getErrYn() {
		return errYn;
	}

	public void setErrYn(String errYn) {
		this.errYn = errYn;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getReqTxt() {
		return reqTxt;
	}

	public void setReqTxt(String reqTxt) {
		this.reqTxt = reqTxt;
	}

	public String getResTxt() {
		return resTxt;
	}

	public void setResTxt(String resTxt) {
		this.resTxt = resTxt;
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
