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
 * 0.0.1      iCodeManager         2018-11-23 18:58:46      신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:58:46
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
//@Data
@Alias("ezcPrmClient")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="프로모션 고객사", description="프로모션 고객사 ( EZC_PRM_CLIENT )", modelTypes="TABLE")
public class EzcPrmClient extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "프로모션 코드", maxLength=10, required=true, constraints="EZC_PRM_CLIENT_PK(P),FK_EZC_PRM_CHANNEL_EZC_PRM_CLI(R),SYS_C0011539(C) EZC_PRM_CLIENT_PK(UNIQUE),EZC_PRM_CLIENT_IF01(NONUNIQUE)")
	private String prmCd;

	@APIFields(description = "채널 코드", maxLength=10, required=true, constraints="EZC_PRM_CLIENT_PK(P),FK_EZC_PRM_CHANNEL_EZC_PRM_CLI(R),SYS_C0011540(C) EZC_PRM_CLIENT_PK(UNIQUE),EZC_PRM_CLIENT_IF01(NONUNIQUE)")
	private String channelCd;

	@APIFields(description = "고객사 코드", maxLength=20, required=true, constraints="EZC_PRM_CLIENT_PK(P),SYS_C0011541(C) EZC_PRM_CLIENT_PK(UNIQUE)")
	private String clientCd;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011542(C)")
	private String regId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyyMMddHHmmss", required=true, constraints="SYS_C0011543(C)")
	private String regDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());


	
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
