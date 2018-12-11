package com.ezwel.htl.interfaces.server.entities;

//import lombok.Data;
//import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractEntity;
import java.math.BigDecimal;


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
@Alias("ezcChannelClientPartner")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="채널 고객사 제휴사", description="채널 고객사 제휴사 ( EZC_CHANNEL_CLIENT_PARTNER )", modelTypes="TABLE")
public class EzcChannelClientPartner extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "고객사 코드", maxLength=20, required=true, constraints="EZC_CHANNEL_CLIENT_PARTNER_PK(P),FK_EZC_CHANNEL_CLIENT_EZC_CHAN(R),SYS_C0011241(C) EZC_CHANNEL_CLIENT_PARTNER_PK(UNIQUE),EZC_CHANNEL_CLIENT_PARTNER_IF0(NONUNIQUE)")
	private String clientCd;

	@APIFields(description = "제휴사 코드 유형", maxLength=8, required=true, constraints="EZC_CHANNEL_CLIENT_PARTNER_PK(P),SYS_C0011242(C) EZC_CHANNEL_CLIENT_PARTNER_PK(UNIQUE)")
	private String partnerCdType;

	@APIFields(description = "채널 코드", maxLength=10, required=true, constraints="EZC_CHANNEL_CLIENT_PARTNER_PK(P),FK_EZC_CHANNEL_CLIENT_EZC_CHAN(R),SYS_C0011243(C) EZC_CHANNEL_CLIENT_PARTNER_IF0(NONUNIQUE),EZC_CHANNEL_CLIENT_PARTNER_PK(UNIQUE)")
	private String channelCd;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011244(C)")
	private String regId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyyMMddHHmmss", required=true, constraints="SYS_C0011245(C)")
	private String regDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());


	
	public String getClientCd() {
		return clientCd;
	}

	public void setClientCd(String clientCd) {
		this.clientCd = clientCd;
	}

	public String getPartnerCdType() {
		return partnerCdType;
	}

	public void setPartnerCdType(String partnerCdType) {
		this.partnerCdType = partnerCdType;
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
