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
@Alias("ezcCpnClient")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="쿠폰 고객사", description="쿠폰 고객사 ( EZC_CPN_CLIENT )", modelTypes="TABLE")
public class EzcCpnClient extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "쿠폰 코드", maxLength=10, required=true, constraints="EZC_CPN_CLIENT_PK(P),FK_EZC_CPN_CHANNEL_EZC_CPN_CLI(R),SYS_C0011331(C) EZC_CPN_CLIENT_PK(UNIQUE),EZC_CPN_CLIENT_IF01(NONUNIQUE)")
	private BigDecimal cpnCd;

	@APIFields(description = "채널 코드", maxLength=10, required=true, constraints="EZC_CPN_CLIENT_PK(P),FK_EZC_CPN_CHANNEL_EZC_CPN_CLI(R),SYS_C0011332(C) EZC_CPN_CLIENT_PK(UNIQUE),EZC_CPN_CLIENT_IF01(NONUNIQUE)")
	private String channelCd;

	@APIFields(description = "고객사 코드", maxLength=20, required=true, constraints="EZC_CPN_CLIENT_PK(P),SYS_C0011333(C) EZC_CPN_CLIENT_PK(UNIQUE)")
	private String clientCd;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011334(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011335(C)")
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

	public String getClientCd() {
		return clientCd;
	}

	public void setClientCd(String clientCd) {
		this.clientCd = clientCd;
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
