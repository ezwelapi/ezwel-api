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

	@APIFields(description = "그룹 시설 코드", maxLength=10, required=true, constraints="EZC_MAPPING_FACL_PK(P),FK_EZC_MAPPING_GRP_FACL_EZC_MA(R),SYS_C0011114(C) EZC_MAPPING_FACL_PK(UNIQUE),EZC_MAPPING_FACL_IF01(NONUNIQUE)")
	private BigDecimal grpFaclCd;

	@APIFields(description = "시설 코드", maxLength=10, required=true, constraints="EZC_MAPPING_FACL_PK(P),FK_EZC_FACL_EZC_MAPPING_FACL(R),SYS_C0011115(C) EZC_MAPPING_FACL_IF02(NONUNIQUE),EZC_MAPPING_FACL_PK(UNIQUE)")
	private BigDecimal faclCd;

	@APIFields(description = "수동 맵핑 여부", maxLength=1, required=true, constraints="SYS_C0011116(C)")
	private String handMappingYn;

	@APIFields(description = "정렬 순서", maxLength=4)
	private Integer dispOrder;
	
	@APIFields(description = "국문 형태소 매칭 퍼센트", maxLength=9)
	private BigDecimal korMorpMatcPcnt;
	
	@APIFields(description = "영문 형태소 매칭 퍼센트", maxLength=9)
	private BigDecimal engMorpMatcPcnt;	
	
	@APIFields(description = "좌표 거리", maxLength=9)
	private BigDecimal cordDist;
	
	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011117(C)")
	private String regId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyyMMddHHmmss", required=true, constraints="SYS_C0011118(C)")
	private String regDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "수정일시", maxLength=14, isDate=true, dateFormat="yyyyMMddHHmmss")
	private String modidt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());


	
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

	public Integer getDispOrder() {
		return dispOrder;
	}

	public void setDispOrder(Integer dispOrder) {
		this.dispOrder = dispOrder;
	}
	
	public BigDecimal getKorMorpMatcPcnt() {
		return korMorpMatcPcnt;
	}

	public void setKorMorpMatcPcnt(BigDecimal korMorpMatcPcnt) {
		this.korMorpMatcPcnt = korMorpMatcPcnt;
	}

	public BigDecimal getEngMorpMatcPcnt() {
		return engMorpMatcPcnt;
	}

	public void setEngMorpMatcPcnt(BigDecimal engMorpMatcPcnt) {
		this.engMorpMatcPcnt = engMorpMatcPcnt;
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

	public String getModidt() {
		return modidt;
	}

	public void setModidt(String modidt) {
		this.modidt = modidt;
	}


}	
