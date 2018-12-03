package com.ezwel.htl.interfaces.server.entities;

//import lombok.Data;
//import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractEntity;
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
//@Data
@Alias("ezcCnclientRoomType")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="채널고객사 숙소 유형", description="채널고객사 숙소 유형 ( EZC_CNCLIENT_ROOM_TYPE )", modelTypes="TABLE")
public class EzcCnclientRoomType extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "숙소 유형", maxLength=8, required=true, constraints="EZC_CNCLIENT_ROOM_TYPE_PK(P),SYS_C0011304(C) EZC_CNCLIENT_ROOM_TYPE_PK(UNIQUE)")
	private String roomType;

	@APIFields(description = "고객사 코드", maxLength=20, required=true, constraints="EZC_CNCLIENT_ROOM_TYPE_PK(P),SYS_C0011305(C) EZC_CNCLIENT_ROOM_TYPE_IF01(NONUNIQUE),EZC_CNCLIENT_ROOM_TYPE_PK(UNIQUE)")
	private String clientCd;

	@APIFields(description = "채널 코드", maxLength=10, required=true, constraints="EZC_CNCLIENT_ROOM_TYPE_PK(P),SYS_C0011306(C) EZC_CNCLIENT_ROOM_TYPE_IF01(NONUNIQUE),EZC_CNCLIENT_ROOM_TYPE_PK(UNIQUE)")
	private String channelCd;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011307(C)")
	private String regId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011308(C)")
	private String regDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());


	
	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
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
