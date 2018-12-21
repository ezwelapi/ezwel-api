package com.ezwel.htl.interfaces.service.data.allReg;

import java.math.BigDecimal;
import java.util.List;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 13.
 */

@Data
@EqualsAndHashCode(callSuper=true)
@APIModel(description="시설 정보")
public class AllRegDataRealtimeImageOutSDO extends AbstractSDO {

	@APIFields(description = "이미지 URL", maxLength=500, required=true, constraints="SYS_C0011110(C)")
	private String partnerImgUrl;
	
	@APIFields(description = "도시 코드", maxLength=10, constraints="FK_EZC_CITY_CD_EZC_FACL(R),SYS_C0011067(C) EZC_FACL_IF01(NONUNIQUE)")
	private String cityCd;
	
	@APIFields(description = "지역 코드", maxLength=10, constraints="FK_EZC_AREA_CD_EZC_FACL(R),SYS_C0011066(C) EZC_FACL_IF02(NONUNIQUE)")
	private String areaCd;

	@APIFields(description = "제휴사 코드", maxLength=20, constraints="FK_EZC_PARTNER_EZC_FACL(R),SYS_C0011054(C) EZC_FACL_IF03(NONUNIQUE)")
	private String partnerCd;

	@APIFields(description = "시설 이미지 일련번호", maxLength=10, constraints="EZC_FACL_IMG_PK(P),SYS_C0011106(C) EZC_FACL_IMG_PK(UNIQUE)")
	private BigDecimal faclImgSeq;

	@APIFields(description = "시설 코드", maxLength=10, constraints="FK_EZC_FACL_EZC_FACL_IMG(R),SYS_C0011107(C) EZC_FACL_IMG_IF01(NONUNIQUE)")
	private BigDecimal faclCd;
	
	public String getPartnerImgUrl() {
		return partnerImgUrl;
	}

	public void setPartnerImgUrl(String partnerImgUrl) {
		this.partnerImgUrl = partnerImgUrl;
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

	public String getPartnerCd() {
		return partnerCd;
	}

	public void setPartnerCd(String partnerCd) {
		this.partnerCd = partnerCd;
	}

	public BigDecimal getFaclImgSeq() {
		return faclImgSeq;
	}

	public void setFaclImgSeq(BigDecimal faclImgSeq) { 
		this.faclImgSeq = faclImgSeq;
	}

	public BigDecimal getFaclCd() {
		return faclCd;
	}

	public void setFaclCd(BigDecimal faclCd) {
		this.faclCd = faclCd;
	}

	
}
