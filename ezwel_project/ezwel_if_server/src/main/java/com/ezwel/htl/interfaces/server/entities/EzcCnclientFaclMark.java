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
 * 0.0.1      CodeSkeleton         2018-11-23 18:46:11                신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:46:11
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
@Data
@Alias("ezcCnclientFaclMark")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="채널고객사 시설 마크", description="채널고객사 시설 마크 ( EZC_CNCLIENT_FACL_MARK )", modelTypes="TABLE")
public class EzcCnclientFaclMark extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "고객사 코드", maxLength=20, required=true, constraints="EZC_CNCLIENT_FACL_MARK_PK(P),SYS_C0011295(C) EZC_CNCLIENT_FACL_MARK_PK(UNIQUE),EZC_CNCLIENT_FACL_MARK_IF01(NONUNIQUE)")
	private String clientCd;

	@APIFields(description = "그룹 시설 코드", maxLength=10, required=true, constraints="EZC_CNCLIENT_FACL_MARK_PK(P),SYS_C0011296(C) EZC_CNCLIENT_FACL_MARK_PK(UNIQUE)")
	private BigDecimal grpFaclCd;

	@APIFields(description = "채널 코드", maxLength=10, required=true, constraints="EZC_CNCLIENT_FACL_MARK_PK(P),SYS_C0011297(C) EZC_CNCLIENT_FACL_MARK_IF01(NONUNIQUE),EZC_CNCLIENT_FACL_MARK_PK(UNIQUE)")
	private String channelCd;

	@APIFields(description = "마크 유형", maxLength=8, required=true, constraints="SYS_C0011298(C)")
	private String markType;

	@APIFields(description = "마크 단위", maxLength=8, required=true, constraints="SYS_C0011299(C)")
	private String markUnit;

	@APIFields(description = "마크 금액", maxLength=8, required=true, constraints="SYS_C0011300(C)")
	private BigDecimal markAmt;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011301(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, required=true, constraints="SYS_C0011302(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true)
	private String modiDt;


	
	public String getClientCd() {
		return clientCd;
	}

	public void setClientCd(String clientCd) {
		this.clientCd = clientCd;
	}

	public BigDecimal getGrpFaclCd() {
		return grpFaclCd;
	}

	public void setGrpFaclCd(BigDecimal grpFaclCd) {
		this.grpFaclCd = grpFaclCd;
	}

	public String getChannelCd() {
		return channelCd;
	}

	public void setChannelCd(String channelCd) {
		this.channelCd = channelCd;
	}

	public String getMarkType() {
		return markType;
	}

	public void setMarkType(String markType) {
		this.markType = markType;
	}

	public String getMarkUnit() {
		return markUnit;
	}

	public void setMarkUnit(String markUnit) {
		this.markUnit = markUnit;
	}

	public BigDecimal getMarkAmt() {
		return markAmt;
	}

	public void setMarkAmt(BigDecimal markAmt) {
		this.markAmt = markAmt;
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