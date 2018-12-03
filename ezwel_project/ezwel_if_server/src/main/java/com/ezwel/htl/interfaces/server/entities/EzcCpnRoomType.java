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
@Alias("ezcCpnRoomType")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="쿠폰 숙소 유형", description="쿠폰 숙소 유형 ( EZC_CPN_ROOM_TYPE )", modelTypes="TABLE")
public class EzcCpnRoomType extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "쿠폰 코드", maxLength=10, required=true, constraints="EZC_CPN_ROOM_TYPE_PK(P),FK_EZC_CPN_EZC_CPN_ROOM_TYPE(R),SYS_C0011347(C) EZC_CPN_ROOM_TYPE_PK(UNIQUE),EZC_CPN_ROOM_TYPE_IF01(NONUNIQUE)")
	private BigDecimal cpnCd;

	@APIFields(description = "숙소 유형", maxLength=8, required=true, constraints="EZC_CPN_ROOM_TYPE_PK(P),SYS_C0011348(C) EZC_CPN_ROOM_TYPE_PK(UNIQUE)")
	private String roomType;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011349(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011350(C)")
	private String regDt;


	
	public BigDecimal getCpnCd() {
		return cpnCd;
	}

	public void setCpnCd(BigDecimal cpnCd) {
		this.cpnCd = cpnCd;
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
