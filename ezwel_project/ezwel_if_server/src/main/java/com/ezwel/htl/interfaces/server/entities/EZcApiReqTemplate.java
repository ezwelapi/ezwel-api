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
 * 0.0.1      CodeSkeleton         2018-11-23 18:35:56                신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:35:56
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
@Data
@Alias("ezcapireqtemplate")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="API 요청 템플릿", description="API 요청 템플릿 ( EZC_API_REQ_TEMPLATE )", modelTypes="TABLE")
public class EZcApiReqTemplate extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "API 요청 ID", maxLength=5, required=true, constraints = "EZC_API_REQ_TEMPLATE_PK(P),SYS_C0011190(C) EZC_API_REQ_TEMPLATE_PK(UNIQUE)")
	private String apiReqId;

	@APIFields(description = "API 요청 번호", maxLength=10, required=true, constraints = "EZC_API_REQ_TEMPLATE_PK(P),FK_EZC_API_REQ_EZC_API_REQ_TEM(R),SYS_C0011191(C) EZC_API_REQ_TEMPLATE_IF01(NONUNIQUE),EZC_API_REQ_TEMPLATE_PK(UNIQUE)")
	private BigDecimal apiReqNum;

	@APIFields(description = "요청 내용", maxLength=4000)
	private String reqContent;

	@APIFields(description = "요청 샘플 데이타", maxLength=2000)
	private String reqSampleData;

	@APIFields(description = "접속 타임 아웃", maxLength=4)
	private BigDecimal connTimeOut;

	@APIFields(description = "리드 타임 아웃", maxLength=4)
	private BigDecimal readTimeOut;

	@APIFields(description = "사용 여부", maxLength=1, required=true, constraints = "SYS_C0011192(C)")
	private String useYn;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints = "SYS_C0011193(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, required=true, constraints = "SYS_C0011194(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true)
	private String modiDt;


	
	public String getApiReqId() {
		return apiReqId;
	}

	public void setApiReqId(String apiReqId) {
		this.apiReqId = apiReqId;
	}

	public BigDecimal getApiReqNum() {
		return apiReqNum;
	}

	public void setApiReqNum(BigDecimal apiReqNum) {
		this.apiReqNum = apiReqNum;
	}

	public String getReqContent() {
		return reqContent;
	}

	public void setReqContent(String reqContent) {
		this.reqContent = reqContent;
	}

	public String getReqSampleData() {
		return reqSampleData;
	}

	public void setReqSampleData(String reqSampleData) {
		this.reqSampleData = reqSampleData;
	}

	public BigDecimal getConnTimeOut() {
		return connTimeOut;
	}

	public void setConnTimeOut(BigDecimal connTimeOut) {
		this.connTimeOut = connTimeOut;
	}

	public BigDecimal getReadTimeOut() {
		return readTimeOut;
	}

	public void setReadTimeOut(BigDecimal readTimeOut) {
		this.readTimeOut = readTimeOut;
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
