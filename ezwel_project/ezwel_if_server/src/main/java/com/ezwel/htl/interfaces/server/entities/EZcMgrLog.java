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
@Alias("ezcmgrlog")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="운영자 로그", description="운영자 로그 ( EZC_MGR_LOG )", modelTypes="TABLE")
public class EZcMgrLog extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "로그 일련번호", maxLength=10, required=true, constraints = "EZC_MGR_LOG_PK(P),SYS_C0011484(C) EZC_MGR_LOG_PK(UNIQUE)")
	private BigDecimal logSeq;

	@APIFields(description = "운영자 ID", maxLength=20, required=true, constraints = "FK_EZC_MGR_EZC_MGR_LOG(R),SYS_C0011485(C) EZC_MGR_LOG_IF01(NONUNIQUE)")
	private String mgrId;

	@APIFields(description = "로그 유형", maxLength=8, required=true, constraints = "SYS_C0011486(C)")
	private String logType;

	@APIFields(description = "내용", maxLength=1000, required=true, constraints = "SYS_C0011487(C)")
	private String content;

	@APIFields(description = "접속 IP", maxLength=15, required=true, constraints = "SYS_C0011488(C)")
	private String connIp;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints = "SYS_C0011489(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, required=true, constraints = "SYS_C0011490(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true)
	private String modiDt;


	
	public BigDecimal getLogSeq() {
		return logSeq;
	}

	public void setLogSeq(BigDecimal logSeq) {
		this.logSeq = logSeq;
	}

	public String getMgrId() {
		return mgrId;
	}

	public void setMgrId(String mgrId) {
		this.mgrId = mgrId;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getConnIp() {
		return connIp;
	}

	public void setConnIp(String connIp) {
		this.connIp = connIp;
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
