package com.ezwel.htl.interfaces.server.entities;

import java.math.BigDecimal;

//import lombok.Data;
//import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;

import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractEntity;


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
@Alias("ezcApiReq")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="API 요청", description="API 요청 ( EZC_API_REQ )", modelTypes="TABLE")
public class EzcApiReq extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "API 요청 번호", maxLength=10, required=true, constraints="EZC_API_REQ_PK(P),SYS_C0011184(C) EZC_API_REQ_PK(UNIQUE)")
	private BigDecimal apiReqNum;

	@APIFields(description = "API 요청 명", maxLength=100, required=true, constraints="SYS_C0011185(C)")
	private String apiReqNm;

	@APIFields(description = "API 구분 코드", maxLength=8, required=true, constraints="SYS_C0011186(C)")
	private String apiDivCd;

	@APIFields(description = "제휴사 코드", maxLength=20)
	private String partnerCd;

	@APIFields(description = "라이브 URL", maxLength=200)
	private String liveUrl;

	@APIFields(description = "테스트 URL", maxLength=200)
	private String testUrl;

	@APIFields(description = "테스트 기본 데이터", maxLength=200)
	private String testBaseData;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011187(C)")
	private String regId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyyMMddHHmmss", required=true, constraints="SYS_C0011188(C)")
	private String regDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyyMMddHHmmss")
	private String modiDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());


	
	public BigDecimal getApiReqNum() {
		return apiReqNum;
	}

	public void setApiReqNum(BigDecimal apiReqNum) {
		this.apiReqNum = apiReqNum;
	}

	public String getApiReqNm() {
		return apiReqNm;
	}

	public void setApiReqNm(String apiReqNm) {
		this.apiReqNm = apiReqNm;
	}

	public String getApiDivCd() {
		return apiDivCd;
	}

	public void setApiDivCd(String apiDivCd) {
		this.apiDivCd = apiDivCd;
	}

	public String getPartnerCd() {
		return partnerCd;
	}

	public void setPartnerCd(String partnerCd) {
		this.partnerCd = partnerCd;
	}

	public String getLiveUrl() {
		return liveUrl;
	}

	public void setLiveUrl(String liveUrl) {
		this.liveUrl = liveUrl;
	}

	public String getTestUrl() {
		return testUrl;
	}

	public void setTestUrl(String testUrl) {
		this.testUrl = testUrl;
	}

	public String getTestBaseData() {
		return testBaseData;
	}

	public void setTestBaseData(String testBaseData) {
		this.testBaseData = testBaseData;
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
