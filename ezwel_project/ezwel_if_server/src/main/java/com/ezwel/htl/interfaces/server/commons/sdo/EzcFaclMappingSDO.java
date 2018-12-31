package com.ezwel.htl.interfaces.server.commons.sdo;

import java.math.BigDecimal;

import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractEntity;


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
@APIModel(modelNames="시설 매핑 정보")
public class EzcFaclMappingSDO extends AbstractEntity {

	@APIFields(description = "시설 코드", maxLength=10, required=true, constraints="EZC_FACL_PK(P),SYS_C0011053(C) EZC_FACL_PK(UNIQUE)")
	private BigDecimal faclCd;

	@APIFields(description = "제휴사 코드", maxLength=20, required=true, constraints="FK_EZC_PARTNER_EZC_FACL(R),SYS_C0011054(C) EZC_FACL_IF03(NONUNIQUE)")
	private String partnerCd;

	@APIFields(description = "시설 구분", maxLength=8, required=true, constraints="SYS_C0011056(C)")
	private String faclDiv;

	@APIFields(description = "제휴사 상품 코드", maxLength=100, required=true, constraints="SYS_C0011057(C)")
	private String partnerGoodsCd;

	@APIFields(description = "시설 명 한글", maxLength=100, required=true, constraints="SYS_C0011058(C)")
	private String faclNmKor;

	@APIFields(description = "시설 명 영문", maxLength=100, required=false, constraints="SYS_C0011059(C)")
	private String faclNmEng;

	@APIFields(description = "숙소 유형", maxLength=8, required=true, constraints="SYS_C0011060(C)")
	private String roomType;

	@APIFields(description = "숙소 등급", maxLength=8, required=true, constraints="SYS_C0011061(C)")
	private String roomClass;

	@APIFields(description = "지역 코드", maxLength=10, required=true, constraints="FK_EZC_AREA_CD_EZC_FACL(R),SYS_C0011066(C) EZC_FACL_IF02(NONUNIQUE)")
	private String areaCd;

	@APIFields(description = "도시 코드", maxLength=10, required=true, constraints="FK_EZC_CITY_CD_EZC_FACL(R),SYS_C0011067(C) EZC_FACL_IF01(NONUNIQUE)")
	private String cityCd;

	@APIFields(description = "주소", maxLength=200, required=true, constraints="SYS_C0011069(C)")
	private String addr;

	@APIFields(description = "우편번호", maxLength=7)
	private String post;

	@APIFields(description = "전화 번호", maxLength=20)
	private String telNum;

	@APIFields(description = "위도", maxLength=20)
	private String coordY;

	@APIFields(description = "경도", maxLength=20)
	private String coordX;

	@APIFields(description = "트립어드바이저 프로퍼티 ID", maxLength=100)
	private String tripPropId;

	@APIFields(description = "수동 맵핑 여부", maxLength=1, required=true, constraints="SYS_C0011116(C)")
	private String handMappingYn;

	@APIFields(description = "정렬 순서", maxLength=4)
	private BigDecimal dispOrder;
	
	@APIFields(description = "한글 형태소 일치 개수", maxLength=9)
	private Integer korMorpEqualsCount;
	
	@APIFields(description = "영문 형태소 일치 개수", maxLength=9)
	private Integer engMorpEqualsCount;
	
	@APIFields(description = "한글 형태소 일치 율", maxLength=9)
	private BigDecimal korMorpEqualsPer;
	
	@APIFields(description = "영문 형태소 일치 율", maxLength=9)
	private BigDecimal engMorpEqualsPer;
	
	@APIFields(description = "좌표 거리", maxLength=29)
	private BigDecimal cordDist;
	
	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011074(C)")
	private String regId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyyMMddHHmmss", required=true, constraints="SYS_C0011075(C)")
	private String regDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyyMMddHHmmss")
	private String modiDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());

	public BigDecimal getFaclCd() {
		return faclCd;
	}

	public void setFaclCd(BigDecimal faclCd) {
		this.faclCd = faclCd;
	}

	public String getPartnerCd() {
		return partnerCd;
	}

	public void setPartnerCd(String partnerCd) {
		this.partnerCd = partnerCd;
	}

	public String getFaclDiv() {
		return faclDiv;
	}

	public void setFaclDiv(String faclDiv) {
		this.faclDiv = faclDiv;
	}

	public String getPartnerGoodsCd() {
		return partnerGoodsCd;
	}

	public void setPartnerGoodsCd(String partnerGoodsCd) {
		this.partnerGoodsCd = partnerGoodsCd;
	}

	public String getFaclNmKor() {
		return faclNmKor;
	}

	public void setFaclNmKor(String faclNmKor) {
		this.faclNmKor = faclNmKor;
	}

	public String getFaclNmEng() {
		return faclNmEng;
	}

	public void setFaclNmEng(String faclNmEng) {
		this.faclNmEng = faclNmEng;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getRoomClass() {
		return roomClass;
	}

	public void setRoomClass(String roomClass) {
		this.roomClass = roomClass;
	}

	public String getAreaCd() {
		return areaCd;
	}

	public void setAreaCd(String areaCd) {
		this.areaCd = areaCd;
	}

	public String getCityCd() {
		return cityCd;
	}

	public void setCityCd(String cityCd) {
		this.cityCd = cityCd;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getTelNum() {
		return telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	public String getCoordY() {
		return coordY;
	}

	public void setCoordY(String coordY) {
		this.coordY = coordY;
	}

	public String getCoordX() {
		return coordX;
	}

	public void setCoordX(String coordX) {
		this.coordX = coordX;
	}

	public String getTripPropId() {
		return tripPropId;
	}

	public void setTripPropId(String tripPropId) {
		this.tripPropId = tripPropId;
	}

	public String getHandMappingYn() {
		return handMappingYn;
	}

	public void setHandMappingYn(String handMappingYn) {
		this.handMappingYn = handMappingYn;
	}

	public BigDecimal getDispOrder() {
		return dispOrder;
	}

	public void setDispOrder(BigDecimal dispOrder) {
		this.dispOrder = dispOrder;
	}

	public Integer getKorMorpEqualsCount() {
		return korMorpEqualsCount;
	}

	public void setKorMorpEqualsCount(Integer korMorpEqualsCount) {
		this.korMorpEqualsCount = korMorpEqualsCount;
	}

	public Integer getEngMorpEqualsCount() {
		return engMorpEqualsCount;
	}

	public void setEngMorpEqualsCount(Integer engMorpEqualsCount) {
		this.engMorpEqualsCount = engMorpEqualsCount;
	}


	public BigDecimal getKorMorpEqualsPer() {
		return korMorpEqualsPer;
	}

	public void setKorMorpEqualsPer(BigDecimal korMorpEqualsPer) {
		this.korMorpEqualsPer = korMorpEqualsPer;
	}

	public BigDecimal getEngMorpEqualsPer() {
		return engMorpEqualsPer;
	}

	public void setEngMorpEqualsPer(BigDecimal engMorpEqualsPer) {
		this.engMorpEqualsPer = engMorpEqualsPer;
	}

	public BigDecimal getCordDist() {
		return cordDist;
	}

	public void setCordDist(BigDecimal cordDist) {
		this.cordDist = cordDist;
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
