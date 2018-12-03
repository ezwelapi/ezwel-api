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
 * 0.0.1      iCodeManager         2018-11-23 18:58:46      신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:58:46
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
//@Data
@Alias("ezcReservRoom")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="예약 객실 정보", description="예약 객실 정보 ( EZC_RESERV_ROOM )", modelTypes="TABLE")
public class EzcReservRoom extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "예약 객실 번호", maxLength=10, required=true, constraints="EZC_RESERV_ROOM_PK(P),SYS_C0011644(C) EZC_RESERV_ROOM_PK(UNIQUE)")
	private BigDecimal reservRoomNum;

	@APIFields(description = "예약 번호", maxLength=10, required=true, constraints="SYS_C0011645(C) EZC_RESERV_ROOM_IF01(NONUNIQUE)")
	private BigDecimal reservNum;

	@APIFields(description = "제휴사 객실 주문 번호", maxLength=100)
	private String partnerRoomOrderNum;

	@APIFields(description = "제휴사 객실 상품 코드", maxLength=100)
	private String partnerRoomGoodsCd;

	@APIFields(description = "객실 명", maxLength=100, required=true, constraints="SYS_C0011646(C)")
	private String roomNm;

	@APIFields(description = "체크인 일자", maxLength=8, required=true, constraints="SYS_C0011647(C)")
	private String checkInDd;

	@APIFields(description = "체크아웃 일자", maxLength=8, required=true, constraints="SYS_C0011648(C)")
	private String checkOutDd;

	@APIFields(description = "성인 수", maxLength=4)
	private BigDecimal adtCnt;

	@APIFields(description = "소아 수", maxLength=4)
	private BigDecimal childCnt;

	@APIFields(description = "객실 수", maxLength=4)
	private BigDecimal roomCnt;

	@APIFields(description = "정상 금액", maxLength=8, required=true, constraints="SYS_C0011649(C)")
	private BigDecimal normalAmt;

	@APIFields(description = "옵션 금액", maxLength=8, required=true, constraints="SYS_C0011650(C)")
	private BigDecimal optAmt;

	@APIFields(description = "판매 금액", maxLength=8, required=true, constraints="SYS_C0011651(C)")
	private BigDecimal saleAmt;

	@APIFields(description = "기준 인원", maxLength=4)
	private BigDecimal stdUser;

	@APIFields(description = "최대 인원", maxLength=4)
	private BigDecimal maxUser;

	@APIFields(description = "침대 유형", maxLength=8)
	private String bedType;

	@APIFields(description = "조식 포함 여부", maxLength=1, required=true, constraints="SYS_C0011652(C)")
	private String breakfastIncludeYn;

	@APIFields(description = "취소 수수료 규정", maxLength=4000)
	private String cancelFeeRule;

	@APIFields(description = "객실 상태", maxLength=8, required=true, constraints="SYS_C0011653(C)")
	private String roomStatus;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011654(C)")
	private String regId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011655(C)")
	private String regDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss")
	private String modiDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());

	@APIFields(description = "직영/숙박 객실 코드", maxLength=10)
	private BigDecimal distRoomCd;


	
	public BigDecimal getReservRoomNum() {
		return reservRoomNum;
	}

	public void setReservRoomNum(BigDecimal reservRoomNum) {
		this.reservRoomNum = reservRoomNum;
	}

	public BigDecimal getReservNum() {
		return reservNum;
	}

	public void setReservNum(BigDecimal reservNum) {
		this.reservNum = reservNum;
	}

	public String getPartnerRoomOrderNum() {
		return partnerRoomOrderNum;
	}

	public void setPartnerRoomOrderNum(String partnerRoomOrderNum) {
		this.partnerRoomOrderNum = partnerRoomOrderNum;
	}

	public String getPartnerRoomGoodsCd() {
		return partnerRoomGoodsCd;
	}

	public void setPartnerRoomGoodsCd(String partnerRoomGoodsCd) {
		this.partnerRoomGoodsCd = partnerRoomGoodsCd;
	}

	public String getRoomNm() {
		return roomNm;
	}

	public void setRoomNm(String roomNm) {
		this.roomNm = roomNm;
	}

	public String getCheckInDd() {
		return checkInDd;
	}

	public void setCheckInDd(String checkInDd) {
		this.checkInDd = checkInDd;
	}

	public String getCheckOutDd() {
		return checkOutDd;
	}

	public void setCheckOutDd(String checkOutDd) {
		this.checkOutDd = checkOutDd;
	}

	public BigDecimal getAdtCnt() {
		return adtCnt;
	}

	public void setAdtCnt(BigDecimal adtCnt) {
		this.adtCnt = adtCnt;
	}

	public BigDecimal getChildCnt() {
		return childCnt;
	}

	public void setChildCnt(BigDecimal childCnt) {
		this.childCnt = childCnt;
	}

	public BigDecimal getRoomCnt() {
		return roomCnt;
	}

	public void setRoomCnt(BigDecimal roomCnt) {
		this.roomCnt = roomCnt;
	}

	public BigDecimal getNormalAmt() {
		return normalAmt;
	}

	public void setNormalAmt(BigDecimal normalAmt) {
		this.normalAmt = normalAmt;
	}

	public BigDecimal getOptAmt() {
		return optAmt;
	}

	public void setOptAmt(BigDecimal optAmt) {
		this.optAmt = optAmt;
	}

	public BigDecimal getSaleAmt() {
		return saleAmt;
	}

	public void setSaleAmt(BigDecimal saleAmt) {
		this.saleAmt = saleAmt;
	}

	public BigDecimal getStdUser() {
		return stdUser;
	}

	public void setStdUser(BigDecimal stdUser) {
		this.stdUser = stdUser;
	}

	public BigDecimal getMaxUser() {
		return maxUser;
	}

	public void setMaxUser(BigDecimal maxUser) {
		this.maxUser = maxUser;
	}

	public String getBedType() {
		return bedType;
	}

	public void setBedType(String bedType) {
		this.bedType = bedType;
	}

	public String getBreakfastIncludeYn() {
		return breakfastIncludeYn;
	}

	public void setBreakfastIncludeYn(String breakfastIncludeYn) {
		this.breakfastIncludeYn = breakfastIncludeYn;
	}

	public String getCancelFeeRule() {
		return cancelFeeRule;
	}

	public void setCancelFeeRule(String cancelFeeRule) {
		this.cancelFeeRule = cancelFeeRule;
	}

	public String getRoomStatus() {
		return roomStatus;
	}

	public void setRoomStatus(String roomStatus) {
		this.roomStatus = roomStatus;
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

	public BigDecimal getDistRoomCd() {
		return distRoomCd;
	}

	public void setDistRoomCd(BigDecimal distRoomCd) {
		this.distRoomCd = distRoomCd;
	}


}	
