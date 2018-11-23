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
 * 0.0.1      CodeSkeleton         2018-11-23 18:35:57                신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:35:57
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
@Data
@Alias("ezcfaclament")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="시설 부대시설", description="시설 부대시설 ( EZC_FACL_AMENT )", modelTypes="TABLE")
public class EZcFaclAment extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "시설 코드", maxLength=10, required=true, constraints = "EZC_FACL_AMENT_PK(P),FK_EZC_FACL_EZC_FACL_AMENT(R),SYS_C0011077(C) EZC_FACL_AMENT_PK(UNIQUE),EZC_FACL_AMENT_IF01(NONUNIQUE)")
	private BigDecimal faclCd;

	@APIFields(description = "부대시설 유형", maxLength=8, required=true, constraints = "EZC_FACL_AMENT_PK(P),SYS_C0011078(C) EZC_FACL_AMENT_PK(UNIQUE)")
	private String amentType;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints = "SYS_C0011079(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, required=true, constraints = "SYS_C0011080(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true)
	private String modiDt;


	
	public BigDecimal getFaclCd() {
		return faclCd;
	}

	public void setFaclCd(BigDecimal faclCd) {
		this.faclCd = faclCd;
	}

	public String getAmentType() {
		return amentType;
	}

	public void setAmentType(String amentType) {
		this.amentType = amentType;
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
