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
 * 0.0.1      CodeSkeleton         2018-11-23 18:46:10                신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:46:10
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
@Data
@Alias("ezcAreaCd")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="지역 코드", description="지역 코드 ( EZC_AREA_CD )", modelTypes="TABLE")
public class EzcAreaCd extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "지역 코드", maxLength=10, required=true, constraints="EZC_AREA_CD_PK(P),SYS_C0011196(C) EZC_AREA_CD_PK(UNIQUE)")
	private String areaCd;

	@APIFields(description = "도시 코드", maxLength=10, required=true, constraints="FK_EZC_CITY_CD_EZC_AREA_CD(R),SYS_C0011197(C) EZC_AREA_CD_IF01(NONUNIQUE)")
	private String cityCd;

	@APIFields(description = "지역 명", maxLength=50)
	private String areaNm;

	@APIFields(description = "위도", maxLength=20)
	private String coordY;

	@APIFields(description = "경도", maxLength=20)
	private String coordX;

	@APIFields(description = "정렬순서", maxLength=4, required=true, constraints="SYS_C0011198(C)")
	private BigDecimal dispOrder;

	@APIFields(description = "사용 여부", maxLength=1, required=true, constraints="SYS_C0011199(C)")
	private String useYn;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011200(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, required=true, constraints="SYS_C0011201(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true)
	private String modiDt;


	
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

	public String getAreaNm() {
		return areaNm;
	}

	public void setAreaNm(String areaNm) {
		this.areaNm = areaNm;
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

	public BigDecimal getDispOrder() {
		return dispOrder;
	}

	public void setDispOrder(BigDecimal dispOrder) {
		this.dispOrder = dispOrder;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
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
