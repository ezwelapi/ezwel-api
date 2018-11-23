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
@Alias("ezcdistroom")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="직영/숙박 객실", description="직영/숙박 객실 ( EZC_DIST_ROOM )", modelTypes="TABLE")
public class EZcDistRoom extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "객실 코드", maxLength=10, required=true, constraints = "EZC_DIST_ROOM_PK(P),SYS_C0011412(C) EZC_DIST_ROOM_PK(UNIQUE)")
	private BigDecimal roomCd;

	@APIFields(description = "시설 코드", maxLength=10, required=true, constraints = "FK_EZC_FACL_EZC_DIST_ROOM(R),SYS_C0011413(C) EZC_DIST_ROOM_IF01(NONUNIQUE)")
	private BigDecimal faclCd;

	@APIFields(description = "특가 여부", maxLength=1, required=true, constraints = "SYS_C0011414(C)")
	private String specYn;

	@APIFields(description = "당일특가 여부", maxLength=1, required=true, constraints = "SYS_C0011415(C)")
	private String dayPriceYn;

	@APIFields(description = "객실 명", maxLength=100, required=true, constraints = "SYS_C0011416(C)")
	private String roomNm;

	@APIFields(description = "정상 금액", maxLength=8, required=true, constraints = "SYS_C0011417(C)")
	private BigDecimal normalAmt;

	@APIFields(description = "기준 인원 수", maxLength=4)
	private BigDecimal stdUserCnt;

	@APIFields(description = "최대 인원 수", maxLength=4)
	private BigDecimal maxUserCnt;

	@APIFields(description = "조식 포함 여부", maxLength=1, required=true, constraints = "SYS_C0011418(C)")
	private String breakfastIncludeYn;

	@APIFields(description = "전망", maxLength=1000)
	private String viewer;

	@APIFields(description = "객실 크기", maxLength=100, required=true, constraints = "SYS_C0011419(C)")
	private String roomSize;

	@APIFields(description = "객실 서비스", maxLength=1000)
	private String roomSvc;

	@APIFields(description = "객실 구비 품목", maxLength=1000)
	private String roomOfferItem;

	@APIFields(description = "욕실 구비 품목", maxLength=1000)
	private String bathOfferItem;

	@APIFields(description = "욕실 수", maxLength=4)
	private BigDecimal bathCnt;

	@APIFields(description = "거실 수", maxLength=4)
	private BigDecimal livingCnt;

	@APIFields(description = "주방 수", maxLength=4)
	private BigDecimal kitchenCnt;

	@APIFields(description = "테라스 수", maxLength=4)
	private BigDecimal terraceCnt;

	@APIFields(description = "객실 정보", maxLength=1000)
	private String roomInfo;

	@APIFields(description = "취소 수수료 규정", maxLength=4000)
	private String cancelFeeRule;

	@APIFields(description = "숙박 수", maxLength=4)
	private BigDecimal lomtCnt;

	@APIFields(description = "컷오프 일수", maxLength=4)
	private BigDecimal cutoffDdcnt;

	@APIFields(description = "텀 일수", maxLength=4)
	private BigDecimal termDdcnt;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints = "SYS_C0011420(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, required=true, constraints = "SYS_C0011421(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true)
	private String modiDt;


	
	public BigDecimal getRoomCd() {
		return roomCd;
	}

	public void setRoomCd(BigDecimal roomCd) {
		this.roomCd = roomCd;
	}

	public BigDecimal getFaclCd() {
		return faclCd;
	}

	public void setFaclCd(BigDecimal faclCd) {
		this.faclCd = faclCd;
	}

	public String getSpecYn() {
		return specYn;
	}

	public void setSpecYn(String specYn) {
		this.specYn = specYn;
	}

	public String getDayPriceYn() {
		return dayPriceYn;
	}

	public void setDayPriceYn(String dayPriceYn) {
		this.dayPriceYn = dayPriceYn;
	}

	public String getRoomNm() {
		return roomNm;
	}

	public void setRoomNm(String roomNm) {
		this.roomNm = roomNm;
	}

	public BigDecimal getNormalAmt() {
		return normalAmt;
	}

	public void setNormalAmt(BigDecimal normalAmt) {
		this.normalAmt = normalAmt;
	}

	public BigDecimal getStdUserCnt() {
		return stdUserCnt;
	}

	public void setStdUserCnt(BigDecimal stdUserCnt) {
		this.stdUserCnt = stdUserCnt;
	}

	public BigDecimal getMaxUserCnt() {
		return maxUserCnt;
	}

	public void setMaxUserCnt(BigDecimal maxUserCnt) {
		this.maxUserCnt = maxUserCnt;
	}

	public String getBreakfastIncludeYn() {
		return breakfastIncludeYn;
	}

	public void setBreakfastIncludeYn(String breakfastIncludeYn) {
		this.breakfastIncludeYn = breakfastIncludeYn;
	}

	public String getViewer() {
		return viewer;
	}

	public void setViewer(String viewer) {
		this.viewer = viewer;
	}

	public String getRoomSize() {
		return roomSize;
	}

	public void setRoomSize(String roomSize) {
		this.roomSize = roomSize;
	}

	public String getRoomSvc() {
		return roomSvc;
	}

	public void setRoomSvc(String roomSvc) {
		this.roomSvc = roomSvc;
	}

	public String getRoomOfferItem() {
		return roomOfferItem;
	}

	public void setRoomOfferItem(String roomOfferItem) {
		this.roomOfferItem = roomOfferItem;
	}

	public String getBathOfferItem() {
		return bathOfferItem;
	}

	public void setBathOfferItem(String bathOfferItem) {
		this.bathOfferItem = bathOfferItem;
	}

	public BigDecimal getBathCnt() {
		return bathCnt;
	}

	public void setBathCnt(BigDecimal bathCnt) {
		this.bathCnt = bathCnt;
	}

	public BigDecimal getLivingCnt() {
		return livingCnt;
	}

	public void setLivingCnt(BigDecimal livingCnt) {
		this.livingCnt = livingCnt;
	}

	public BigDecimal getKitchenCnt() {
		return kitchenCnt;
	}

	public void setKitchenCnt(BigDecimal kitchenCnt) {
		this.kitchenCnt = kitchenCnt;
	}

	public BigDecimal getTerraceCnt() {
		return terraceCnt;
	}

	public void setTerraceCnt(BigDecimal terraceCnt) {
		this.terraceCnt = terraceCnt;
	}

	public String getRoomInfo() {
		return roomInfo;
	}

	public void setRoomInfo(String roomInfo) {
		this.roomInfo = roomInfo;
	}

	public String getCancelFeeRule() {
		return cancelFeeRule;
	}

	public void setCancelFeeRule(String cancelFeeRule) {
		this.cancelFeeRule = cancelFeeRule;
	}

	public BigDecimal getLomtCnt() {
		return lomtCnt;
	}

	public void setLomtCnt(BigDecimal lomtCnt) {
		this.lomtCnt = lomtCnt;
	}

	public BigDecimal getCutoffDdcnt() {
		return cutoffDdcnt;
	}

	public void setCutoffDdcnt(BigDecimal cutoffDdcnt) {
		this.cutoffDdcnt = cutoffDdcnt;
	}

	public BigDecimal getTermDdcnt() {
		return termDdcnt;
	}

	public void setTermDdcnt(BigDecimal termDdcnt) {
		this.termDdcnt = termDdcnt;
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
