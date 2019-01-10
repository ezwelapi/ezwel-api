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
 * 0.0.1      iCodeManager         2018-11-23 18:58:44      신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:58:44
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
//@Data
@Alias("ezcCacheMinAmt")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="CACHE 최저가 정보", description="CACHE 최저가 정보 ( EZC_CACHE_MIN_AMT )", modelTypes="TABLE")
public class EzcCacheMinAmt extends AbstractEntity {

	@APIFields(description = "체크인 일자", maxLength=8, required=true, constraints="EZC_CACHE_MIN_AMT_PK(P),FK_EZC_CACHE_SEARCH_LOG_EZC_CA(R),SYS_C0011160(C) EZC_CACHE_MIN_AMT_PK(UNIQUE),EZC_CACHE_MIN_AMT_IF01(NONUNIQUE)")
	private String checkInDd;

	@APIFields(description = "체크아웃 일자", maxLength=8, required=true, constraints="EZC_CACHE_MIN_AMT_PK(P),FK_EZC_CACHE_SEARCH_LOG_EZC_CA(R),SYS_C0011161(C) EZC_CACHE_MIN_AMT_PK(UNIQUE),EZC_CACHE_MIN_AMT_IF01(NONUNIQUE)")
	private String checkOutDd;

	@APIFields(description = "그룹 시설 코드", maxLength=10, required=true, constraints="EZC_CACHE_MIN_AMT_PK(P),FK_EZC_MAPPING_FACL_EZC_CACHE_(R),SYS_C0011162(C) EZC_CACHE_MIN_AMT_IF02(NONUNIQUE),EZC_CACHE_MIN_AMT_PK(UNIQUE)")
	private BigDecimal grpFaclCd;

	@APIFields(description = "시설 코드", maxLength=10, required=true, constraints="EZC_CACHE_MIN_AMT_PK(P),FK_EZC_MAPPING_FACL_EZC_CACHE_(R),SYS_C0011163(C) EZC_CACHE_MIN_AMT_IF02(NONUNIQUE),EZC_CACHE_MIN_AMT_PK(UNIQUE)")
	private BigDecimal faclCd;

	@APIFields(description = "도시 코드", maxLength=10, required=true, constraints="EZC_CACHE_MIN_AMT_PK(P),FK_EZC_CACHE_SEARCH_LOG_EZC_CA(R),SYS_C0011164(C) EZC_CACHE_MIN_AMT_IF01(NONUNIQUE),EZC_CACHE_MIN_AMT_PK(UNIQUE)")
	private String cityCd;

	@APIFields(description = "지역 코드", maxLength=10, required=true, constraints="EZC_CACHE_MIN_AMT_PK(P),FK_EZC_CACHE_SEARCH_LOG_EZC_CA(R),SYS_C0011165(C) EZC_CACHE_MIN_AMT_IF01(NONUNIQUE),EZC_CACHE_MIN_AMT_PK(UNIQUE)")
	private String areaCd;

	//@APIFields(description = "제휴사 코드 유형", maxLength=8, required=true, constraints="SYS_C0011166(C)")
	//private String partnerCdType;
	
	@APIFields(description = "객실 최저가", maxLength=8, required=true, constraints="SYS_C0011167(C)")
	private BigDecimal roomMinPrice;

	@APIFields(description = "특가최저가", maxLength=8)
	private BigDecimal spRoomMinPrice;

	//20181227 추가
	@APIFields(description = "제휴사 코드", maxLength=20, required=true)
	private BigDecimal partnerCd;
	
	@APIFields(description = "객실 정상가", maxLength=22)
	private BigDecimal roomNetPrice;
	
	@APIFields(description = "특가 정상가", maxLength=22)
	private BigDecimal spRoomNetPrice;

	@APIFields(description = "제휴사 상품 코드", maxLength=100)
	private String partnerGoodsCd;
	
	
	public BigDecimal getPartnerCd() {
		return partnerCd;
	}

	public void setPartnerCd(BigDecimal partnerCd) {
		this.partnerCd = partnerCd;
	}

	public BigDecimal getRoomNetPrice() {
		return roomNetPrice;
	}

	public void setRoomNetPrice(BigDecimal roomNetPrice) {
		this.roomNetPrice = roomNetPrice;
	}

	public BigDecimal getSpRoomNetPrice() {
		return spRoomNetPrice;
	}

	public void setSpRoomNetPrice(BigDecimal spRoomNetPrice) {
		this.spRoomNetPrice = spRoomNetPrice;
	}

	public String getPartnerGoodsCd() {
		return partnerGoodsCd;
	}

	public void setPartnerGoodsCd(String partnerGoodsCd) {
		this.partnerGoodsCd = partnerGoodsCd;
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

	public BigDecimal getGrpFaclCd() {
		return grpFaclCd;
	}

	public void setGrpFaclCd(BigDecimal grpFaclCd) {
		this.grpFaclCd = grpFaclCd;
	}

	public BigDecimal getFaclCd() {
		return faclCd;
	}

	public void setFaclCd(BigDecimal faclCd) {
		this.faclCd = faclCd;
	}

	public String getCityCd() {
		return cityCd;
	}

	public void setCityCd(String cityCd) {
		this.cityCd = cityCd;
	}

	public String getAreaCd() {
		return areaCd;
	}

	public void setAreaCd(String areaCd) {
		this.areaCd = areaCd;
	}

	public BigDecimal getRoomMinPrice() {
		return roomMinPrice;
	}

	public void setRoomMinPrice(BigDecimal roomMinPrice) {
		this.roomMinPrice = roomMinPrice;
	}

	public BigDecimal getSpRoomMinPrice() {
		return spRoomMinPrice;
	}

	public void setSpRoomMinPrice(BigDecimal spRoomMinPrice) {
		this.spRoomMinPrice = spRoomMinPrice;
	}


}	
