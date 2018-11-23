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
 * 0.0.1      CodeSkeleton         2018-11-23 18:55:41                신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:55:41
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
@Data
@Alias("ezcCpnUseHist")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="쿠폰 사용 내역", description="쿠폰 사용 내역 ( EZC_CPN_USE_HIST )", modelTypes="TABLE")
public class EzcCpnUseHist extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "쿠폰 코드", maxLength=10, required=true, constraints="EZC_CPN_USE_HIST_PK(P),FK_EZC_CPN_EZC_CPN_USE_HIST(R),SYS_C0011352(C) EZC_CPN_USE_HIST_PK(UNIQUE),EZC_CPN_USE_HIST_IF05(NONUNIQUE)")
	private BigDecimal cpnCd;

	@APIFields(description = "회원 ID", maxLength=20, required=true, constraints="EZC_CPN_USE_HIST_PK(P),SYS_C0011353(C) EZC_CPN_USE_HIST_PK(UNIQUE)")
	private String userId;

	@APIFields(description = "채널 코드", maxLength=10, required=true, constraints="FK_EZC_CHANNEL_MNG_EZC_CPN_USE(R),SYS_C0011354(C) EZC_CPN_USE_HIST_IF06(NONUNIQUE)")
	private String channelCd;

	@APIFields(description = "예약 번호", maxLength=10, required=true, constraints="FK_EZC_RESERV_BASE_EZC_CPN_USE(R),SYS_C0011355(C) EZC_CPN_USE_HIST_IF04(NONUNIQUE)")
	private BigDecimal reservNum;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011356(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011357(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss")
	private String modiDt;


	
	public BigDecimal getCpnCd() {
		return cpnCd;
	}

	public void setCpnCd(BigDecimal cpnCd) {
		this.cpnCd = cpnCd;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getChannelCd() {
		return channelCd;
	}

	public void setChannelCd(String channelCd) {
		this.channelCd = channelCd;
	}

	public BigDecimal getReservNum() {
		return reservNum;
	}

	public void setReservNum(BigDecimal reservNum) {
		this.reservNum = reservNum;
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
