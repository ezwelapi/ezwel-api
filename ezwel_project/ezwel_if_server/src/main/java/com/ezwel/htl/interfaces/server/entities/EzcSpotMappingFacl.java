package com.ezwel.htl.interfaces.server.entities;

//import lombok.Data;
//import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractEntity;
import java.math.BigDecimal;


/**
 * <b>History : Generated Code Skeleton iCodeManager Made by KSW</b>
 * <pre>
 * 버전              성명                                일자                                       변경내용
 * -------    ----------------     -------------------		-----------------
 * 0.0.1      iCodeManager         2018-11-23 18:58:46      신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:58:46
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
//@Data
@Alias("ezcSpotMappingFacl")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="스팟 맵핑 시설", description="스팟 맵핑 시설 ( EZC_SPOT_MAPPING_FACL )", modelTypes="TABLE")
public class EzcSpotMappingFacl extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "스팟 코드", maxLength=10, required=true, constraints="EZC_SPOT_MAPPING_FACL_PK(P),FK_EZC_SPOT_EZC_SPOT_MAPPING_F(R),SYS_C0011679(C) EZC_SPOT_MAPPING_FACL_PK(UNIQUE),EZC_SPOT_MAPPING_FACL_IF01(NONUNIQUE)")
	private BigDecimal spotCd;

	@APIFields(description = "그룹 시설 코드", maxLength=10, required=true, constraints="EZC_SPOT_MAPPING_FACL_PK(P),FK_EZC_MAPPING_GRP_FACL_EZC_SP(R),SYS_C0011680(C) EZC_SPOT_MAPPING_FACL_IF03(NONUNIQUE),EZC_SPOT_MAPPING_FACL_PK(UNIQUE)")
	private BigDecimal grpFaclCd;

	@APIFields(description = "정렬 순서", maxLength=4)
	private BigDecimal dispOrder;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011681(C)")
	private String regId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011682(C)")
	private String regDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());


	
	public BigDecimal getSpotCd() {
		return spotCd;
	}

	public void setSpotCd(BigDecimal spotCd) {
		this.spotCd = spotCd;
	}

	public BigDecimal getGrpFaclCd() {
		return grpFaclCd;
	}

	public void setGrpFaclCd(BigDecimal grpFaclCd) {
		this.grpFaclCd = grpFaclCd;
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


}	
