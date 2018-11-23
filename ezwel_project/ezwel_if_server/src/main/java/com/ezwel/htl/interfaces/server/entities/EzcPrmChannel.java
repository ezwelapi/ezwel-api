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
 * 0.0.1      CodeSkeleton         2018-11-23 18:55:43                신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:55:43
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
@Data
@Alias("ezcPrmChannel")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="프로모션 채널", description="프로모션 채널 ( EZC_PRM_CHANNEL )", modelTypes="TABLE")
public class EzcPrmChannel extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "프로모션 코드", maxLength=10, required=true, constraints="EZC_PRM_CHANNEL_PK(P),FK_EZC_PRM_MNG_EZC_PRM_CHANNEL(R),SYS_C0011534(C) EZC_PRM_CHANNEL_PK(UNIQUE),EZC_PRM_CHANNEL_IF01(NONUNIQUE)")
	private String prmCd;

	@APIFields(description = "채널 코드", maxLength=10, required=true, constraints="EZC_PRM_CHANNEL_PK(P),FK_EZC_CHANNEL_MNG_EZC_PRM_CHA(R),SYS_C0011535(C) EZC_PRM_CHANNEL_IF02(NONUNIQUE),EZC_PRM_CHANNEL_PK(UNIQUE)")
	private String channelCd;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011536(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011537(C)")
	private String regDt;


	
	public String getPrmCd() {
		return prmCd;
	}

	public void setPrmCd(String prmCd) {
		this.prmCd = prmCd;
	}

	public String getChannelCd() {
		return channelCd;
	}

	public void setChannelCd(String channelCd) {
		this.channelCd = channelCd;
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
