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
 * 0.0.1      CodeSkeleton         2018-11-23 18:35:58                신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:35:58
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
@Data
@Alias("ezcspot")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="스팟", description="스팟 ( EZC_SPOT )", modelTypes="TABLE")
public class EZcSpot extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "스팟 코드", maxLength=10, required=true, constraints = "EZC_SPOT_PK(P),SYS_C0011672(C) EZC_SPOT_PK(UNIQUE)")
	private BigDecimal spotCd;

	@APIFields(description = "도시 코드", maxLength=10)
	private String cityCd;

	@APIFields(description = "지역 코드", maxLength=10)
	private String areaCd;

	@APIFields(description = "스팟 명", maxLength=100, required=true, constraints = "SYS_C0011673(C)")
	private String spotNm;

	@APIFields(description = "적용 시작 일자", maxLength=8, required=true, constraints = "SYS_C0011674(C)")
	private String applyStartDd;

	@APIFields(description = "적용 종료 일자", maxLength=8, required=true, constraints = "SYS_C0011675(C)")
	private String applyEndDd;

	@APIFields(description = "위도", maxLength=20)
	private String coordY;

	@APIFields(description = "경도", maxLength=20)
	private String coordX;

	@APIFields(description = "URL", maxLength=200)
	private String url;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints = "SYS_C0011676(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, required=true, constraints = "SYS_C0011677(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true)
	private String modiDt;


	
	public BigDecimal getSpotCd() {
		return spotCd;
	}

	public void setSpotCd(BigDecimal spotCd) {
		this.spotCd = spotCd;
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

	public String getSpotNm() {
		return spotNm;
	}

	public void setSpotNm(String spotNm) {
		this.spotNm = spotNm;
	}

	public String getApplyStartDd() {
		return applyStartDd;
	}

	public void setApplyStartDd(String applyStartDd) {
		this.applyStartDd = applyStartDd;
	}

	public String getApplyEndDd() {
		return applyEndDd;
	}

	public void setApplyEndDd(String applyEndDd) {
		this.applyEndDd = applyEndDd;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
