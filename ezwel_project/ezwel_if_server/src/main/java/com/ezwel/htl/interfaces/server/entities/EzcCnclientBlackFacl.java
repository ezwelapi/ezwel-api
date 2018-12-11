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
@Alias("ezcCnclientBlackFacl")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="채널고객사 블랙 시설", description="채널고객사 블랙 시설 ( EZC_CNCLIENT_BLACK_FACL )", modelTypes="TABLE")
public class EzcCnclientBlackFacl extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "그룹 시설 여부", maxLength=1, required=true, constraints="EZC_CNCLIENT_BLACK_FACL_PK(P),SYS_C0011282(C) EZC_CNCLIENT_BLACK_FACL_PK(UNIQUE)")
	private String grpFaclYn;

	@APIFields(description = "연계 시설 코드", maxLength=10, required=true, constraints="EZC_CNCLIENT_BLACK_FACL_PK(P),SYS_C0011283(C) EZC_CNCLIENT_BLACK_FACL_PK(UNIQUE)")
	private BigDecimal conFaclCd;

	@APIFields(description = "고객사 코드", maxLength=20, required=true, constraints="EZC_CNCLIENT_BLACK_FACL_PK(P),FK_EZC_CHANNEL_CLIENT_EZC_CNCL(R),SYS_C0011284(C) EZC_CNCLIENT_BLACK_FACL_IF01(NONUNIQUE),EZC_CNCLIENT_BLACK_FACL_PK(UNIQUE)")
	private String clientCd;

	@APIFields(description = "채널 코드", maxLength=10, required=true, constraints="EZC_CNCLIENT_BLACK_FACL_PK(P),FK_EZC_CHANNEL_CLIENT_EZC_CNCL(R),SYS_C0011285(C) EZC_CNCLIENT_BLACK_FACL_IF01(NONUNIQUE),EZC_CNCLIENT_BLACK_FACL_PK(UNIQUE)")
	private String channelCd;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011286(C)")
	private String regId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyyMMddHHmmss", required=true, constraints="SYS_C0011287(C)")
	private String regDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());


	
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

	public String getClientCd() {
		return clientCd;
	}

	public void setClientCd(String clientCd) {
		this.clientCd = clientCd;
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
