package com.ezwel.htl.interfaces.server.sdo;

import java.math.BigDecimal;

//import lombok.Data;
//import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;


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

@APIModel(modelNames="시설 정보")
public class FaclSDO extends AbstractSDO {

	@APIFields(description = "시설 코드", maxLength=10, constraints="EZC_FACL_PK(P),SYS_C0011053(C) EZC_FACL_PK(UNIQUE)")
	private BigDecimal faclCd;

	@APIFields(description = "제휴사 코드", maxLength=20, constraints="FK_EZC_PARTNER_EZC_FACL(R),SYS_C0011054(C) EZC_FACL_IF03(NONUNIQUE)")
	private String partnerCd;

	//AA쪽에서 컬럼삭제
	//@APIFields(description = "제휴사 코드 유형", maxLength=8, constraints="SYS_C0011055(C)")
	//private String partnerCdType;

	@APIFields(description = "시설 구분", maxLength=8, constraints="SYS_C0011056(C)")
	private String faclDiv;

	@APIFields(description = "제휴사 상품 코드", maxLength=100, constraints="SYS_C0011057(C)")
	private String partnerGoodsCd;

	@APIFields(description = "시설 명 한글", maxLength=100, constraints="SYS_C0011058(C)")
	private String faclNmKor;

	@APIFields(description = "시설 명 영문", maxLength=100, constraints="SYS_C0011059(C)")
	private String faclNmEng;

	@APIFields(description = "시설 명", maxLength=100)
	private String faclNm;

	@APIFields(description = "지역 코드", required=false, maxLength=10, constraints="FK_EZC_AREA_CD_EZC_FACL(R),SYS_C0011066(C) EZC_FACL_IF02(NONUNIQUE)")
	private String areaCd;

	@APIFields(description = "도시 코드", required=false, maxLength=10, constraints="FK_EZC_CITY_CD_EZC_FACL(R),SYS_C0011067(C) EZC_FACL_IF01(NONUNIQUE)")
	private String cityCd;
	
	@APIFields(description = "숙소 유형", required=false, maxLength=8, constraints="SYS_C0011060(C)")
	private String roomType;

	@APIFields(description = "숙소 등급", required=false, maxLength=8, constraints="SYS_C0011061(C)")
	private String roomClass;

	@APIFields(description = "위도", maxLength=20)
	private String coordY;

	@APIFields(description = "경도", maxLength=20)
	private String coordX;

	@APIFields(description = "시설 형태소 한글", maxLength=1600)
	private String faclKorMorp;
	
	@APIFields(description = "시설 형태소 영문", maxLength=1600)
	private String faclEngMorp;	
	
	@APIFields(description = "형태소 유형")
	private String morpType;
	
	@APIFields(description = "시설 이름 형태소")
	private String faclNmMorp;
	
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

	public String getFaclNm() {
		return faclNm;
	}

	public void setFaclNm(String faclNm) {
		this.faclNm = faclNm;
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

	public String getFaclKorMorp() {
		return faclKorMorp;
	}

	public void setFaclKorMorp(String faclKorMorp) {
		this.faclKorMorp = faclKorMorp;
	}

	public String getFaclEngMorp() {
		return faclEngMorp;
	}

	public void setFaclEngMorp(String faclEngMorp) {
		this.faclEngMorp = faclEngMorp;
	}

	public String getMorpType() {
		return morpType;
	}

	public void setMorpType(String morpType) {
		this.morpType = morpType;
	}

	public String getFaclNmMorp() {
		return faclNmMorp;
	}

	public void setFaclNmMorp(String faclNmMorp) {
		this.faclNmMorp = faclNmMorp;
	}
	
}	
