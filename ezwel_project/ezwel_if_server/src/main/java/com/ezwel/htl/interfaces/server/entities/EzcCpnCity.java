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
 * 0.0.1      CodeSkeleton         2018-11-23 18:46:11                신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:46:11
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
@Data
@Alias("ezcCpnCity")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="쿠폰 도시", description="쿠폰 도시 ( EZC_CPN_CITY )", modelTypes="TABLE")
public class EzcCpnCity extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "쿠폰 코드", maxLength=10, required=true, constraints="EZC_CPN_CITY_PK(P),FK_EZC_CPN_EZC_CPN_CITY(R),SYS_C0011326(C) EZC_CPN_CITY_PK(UNIQUE),EZC_CPN_CITY_IF01(NONUNIQUE)")
	private BigDecimal cpnCd;

	@APIFields(description = "도시 코드", maxLength=10, required=true, constraints="EZC_CPN_CITY_PK(P),SYS_C0011327(C) EZC_CPN_CITY_PK(UNIQUE)")
	private String cityCd;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011328(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, required=true, constraints="SYS_C0011329(C)")
	private String regDt;


	
	public BigDecimal getCpnCd() {
		return cpnCd;
	}

	public void setCpnCd(BigDecimal cpnCd) {
		this.cpnCd = cpnCd;
	}

	public String getCityCd() {
		return cityCd;
	}

	public void setCityCd(String cityCd) {
		this.cityCd = cityCd;
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