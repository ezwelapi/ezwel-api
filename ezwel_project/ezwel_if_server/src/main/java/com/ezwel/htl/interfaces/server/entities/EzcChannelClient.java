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
@Alias("ezcChannelClient")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="채널 고객사", description="채널 고객사 ( EZC_CHANNEL_CLIENT )", modelTypes="TABLE")
public class EzcChannelClient extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "고객사 코드", maxLength=20, required=true, constraints="EZC_CHANNEL_CLIENT_PK(P),SYS_C0011236(C) EZC_CHANNEL_CLIENT_PK(UNIQUE)")
	private String clientCd;

	@APIFields(description = "채널 코드", maxLength=10, required=true, constraints="EZC_CHANNEL_CLIENT_PK(P),SYS_C0011237(C) EZC_CHANNEL_CLIENT_IF01(NONUNIQUE),EZC_CHANNEL_CLIENT_PK(UNIQUE)")
	private String channelCd;

	@APIFields(description = "마크 유형", maxLength=8)
	private String markType;

	@APIFields(description = "마크 단위", maxLength=8)
	private String markUnit;

	@APIFields(description = "마크 금액", maxLength=8)
	private BigDecimal markAmt;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011238(C)")
	private String regId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyyMMddHHmmss", required=true, constraints="SYS_C0011239(C)")
	private String regDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyyMMddHHmmss")
	private String modiDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());


	
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
