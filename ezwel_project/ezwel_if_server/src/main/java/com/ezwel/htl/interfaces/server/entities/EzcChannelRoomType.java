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
 * 0.0.1      iCodeManager         2018-11-23 18:58:44      신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:58:44
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
//@Data
@Alias("ezcChannelRoomType")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="채널 숙소 유형", description="채널 숙소 유형 ( EZC_CHANNEL_ROOM_TYPE )", modelTypes="TABLE")
public class EzcChannelRoomType extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "채널 코드", maxLength=10, required=true, constraints="EZC_CHANNEL_ROOM_TYPE_PK(P),SYS_C0011262(C) EZC_CHANNEL_ROOM_TYPE_PK(UNIQUE),EZC_CHANNEL_ROOM_TYPE_IF01(NONUNIQUE)")
	private String channelCd;

	@APIFields(description = "숙소 유형", maxLength=8, required=true, constraints="EZC_CHANNEL_ROOM_TYPE_PK(P),SYS_C0011263(C) EZC_CHANNEL_ROOM_TYPE_PK(UNIQUE)")
	private String roomType;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011264(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011265(C)")
	private String regDt;


	
	public String getChannelCd() {
		return channelCd;
	}

	public void setChannelCd(String channelCd) {
		this.channelCd = channelCd;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
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
