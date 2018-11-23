package com.ezwel.htl.interfaces.server.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
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
 * 0.0.1      iCodeManager         2018-11-23 18:58:45      신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:58:45
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
@Data
@Alias("ezcCpnChannel")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="쿠폰 채널", description="쿠폰 채널 ( EZC_CPN_CHANNEL )", modelTypes="TABLE")
public class EzcCpnChannel extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "쿠폰 코드", maxLength=10, required=true, constraints="EZC_CPN_CHANNEL_PK(P),FK_EZC_CPN_EZC_CPN_CHANNEL(R),SYS_C0011321(C) EZC_CPN_CHANNEL_PK(UNIQUE),EZC_CPN_CHANNEL_IF01(NONUNIQUE)")
	private BigDecimal cpnCd;

	@APIFields(description = "채널 코드", maxLength=10, required=true, constraints="EZC_CPN_CHANNEL_PK(P),FK_EZC_CHANNEL_MNG_EZC_CPN_CHA(R),SYS_C0011322(C) EZC_CPN_CHANNEL_IF02(NONUNIQUE),EZC_CPN_CHANNEL_PK(UNIQUE)")
	private String channelCd;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011323(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011324(C)")
	private String regDt;


	
	public BigDecimal getCpnCd() {
		return cpnCd;
	}

	public void setCpnCd(BigDecimal cpnCd) {
		this.cpnCd = cpnCd;
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
