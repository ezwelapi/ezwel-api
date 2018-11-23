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
 * 0.0.1      CodeSkeleton         2018-11-23 18:35:56                신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:35:56
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
@Data
@Alias("ezcchannelmng")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="채널", description="채널 ( EZC_CHANNEL_MNG )", modelTypes="TABLE")
public class EZcChannelMng extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "채널 코드", maxLength=10, required=true, constraints = "EZC_CHANNEL_MNG_PK(P),SYS_C0011247(C) EZC_CHANNEL_MNG_PK(UNIQUE)")
	private String channelCd;

	@APIFields(description = "채널 코드 명", maxLength=50)
	private String channelCdNm;

	@APIFields(description = "숙소 설정 여부", maxLength=1, required=true, constraints = "SYS_C0011248(C)")
	private String roomSetYn;

	@APIFields(description = "제휴사 설정 여부", maxLength=1, required=true, constraints = "SYS_C0011249(C)")
	private String partnerSetYn;

	@APIFields(description = "시설 설정 여부", maxLength=1, required=true, constraints = "SYS_C0011250(C)")
	private String faclSetYn;

	@APIFields(description = "마크 유형", maxLength=8, required=true, constraints = "SYS_C0011251(C)")
	private String markType;

	@APIFields(description = "마크 단위", maxLength=8, required=true, constraints = "SYS_C0011252(C)")
	private String markUnit;

	@APIFields(description = "마크 금액", maxLength=8, required=true, constraints = "SYS_C0011253(C)")
	private BigDecimal markAmt;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints = "SYS_C0011254(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, required=true, constraints = "SYS_C0011255(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true)
	private String modiDt;


	
	public String getChannelCd() {
		return channelCd;
	}

	public void setChannelCd(String channelCd) {
		this.channelCd = channelCd;
	}

	public String getChannelCdNm() {
		return channelCdNm;
	}

	public void setChannelCdNm(String channelCdNm) {
		this.channelCdNm = channelCdNm;
	}

	public String getRoomSetYn() {
		return roomSetYn;
	}

	public void setRoomSetYn(String roomSetYn) {
		this.roomSetYn = roomSetYn;
	}

	public String getPartnerSetYn() {
		return partnerSetYn;
	}

	public void setPartnerSetYn(String partnerSetYn) {
		this.partnerSetYn = partnerSetYn;
	}

	public String getFaclSetYn() {
		return faclSetYn;
	}

	public void setFaclSetYn(String faclSetYn) {
		this.faclSetYn = faclSetYn;
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
