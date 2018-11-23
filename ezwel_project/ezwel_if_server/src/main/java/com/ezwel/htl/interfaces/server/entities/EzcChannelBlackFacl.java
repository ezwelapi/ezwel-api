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
 * 0.0.1      CodeSkeleton         2018-11-23 18:46:10                신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:46:10
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
@Data
@Alias("ezcChannelBlackFacl")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="채널 블랙 시설", description="채널 블랙 시설 ( EZC_CHANNEL_BLACK_FACL )", modelTypes="TABLE")
public class EzcChannelBlackFacl extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "채널 코드", maxLength=10, required=true, constraints="EZC_CHANNEL_BLACK_FACL_PK(P),FK_EZC_CHANNEL_MNG_EZC_CHANNEL(R),SYS_C0011225(C) EZC_CHANNEL_BLACK_FACL_PK(UNIQUE),EZC_CHANNEL_BLACK_FACL_IF01(NONUNIQUE)")
	private String channelCd;

	@APIFields(description = "그룹 시설 여부", maxLength=1, required=true, constraints="EZC_CHANNEL_BLACK_FACL_PK(P),SYS_C0011226(C) EZC_CHANNEL_BLACK_FACL_PK(UNIQUE)")
	private String grpFaclYn;

	@APIFields(description = "연계 시설 코드", maxLength=10, required=true, constraints="EZC_CHANNEL_BLACK_FACL_PK(P),SYS_C0011227(C) EZC_CHANNEL_BLACK_FACL_PK(UNIQUE)")
	private BigDecimal conFaclCd;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011228(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, required=true, constraints="SYS_C0011229(C)")
	private String regDt;


	
	public String getChannelCd() {
		return channelCd;
	}

	public void setChannelCd(String channelCd) {
		this.channelCd = channelCd;
	}

	public String getGrpFaclYn() {
		return grpFaclYn;
	}

	public void setGrpFaclYn(String grpFaclYn) {
		this.grpFaclYn = grpFaclYn;
	}

	public BigDecimal getConFaclCd() {
		return conFaclCd;
	}

	public void setConFaclCd(BigDecimal conFaclCd) {
		this.conFaclCd = conFaclCd;
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


}	
