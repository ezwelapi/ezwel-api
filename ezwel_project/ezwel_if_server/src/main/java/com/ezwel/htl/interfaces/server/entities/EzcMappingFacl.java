package com.ezwel.htl.interfaces.server.entities;

//import lombok.Data;
//import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;
import java.math.BigDecimal;


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
//@Data
@Alias("ezcMappingFacl")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="맵핑 시설", description="맵핑 시설 ( EZC_MAPPING_FACL )", modelTypes="TABLE")
public class EzcMappingFacl extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "그룹 시설 코드", maxLength=10, required=true, constraints="EZC_MAPPING_FACL_PK(P),FK_EZC_MAPPING_GRP_FACL_EZC_MA(R),SYS_C0011114(C) EZC_MAPPING_FACL_PK(UNIQUE),EZC_MAPPING_FACL_IF01(NONUNIQUE)")
	private BigDecimal grpFaclCd;

	@APIFields(description = "시설 코드", maxLength=10, required=true, constraints="EZC_MAPPING_FACL_PK(P),FK_EZC_FACL_EZC_MAPPING_FACL(R),SYS_C0011115(C) EZC_MAPPING_FACL_IF02(NONUNIQUE),EZC_MAPPING_FACL_PK(UNIQUE)")
	private BigDecimal faclCd;

	@APIFields(description = "수동 맵핑 여부", maxLength=1, required=true, constraints="SYS_C0011116(C)")
	private String handMappingYn;

	@APIFields(description = "정렬 순서", maxLength=4)
	private BigDecimal dispOrder;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011117(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011118(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정일시", maxLength=14)
	private String modidt;


	
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

	public String getModidt() {
		return modidt;
	}

	public void setModidt(String modidt) {
		this.modidt = modidt;
	}


}	
