package com.ezwel.htl.interfaces.server.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
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
@Data
@Alias("ezcDistFaclDetail")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="직영/숙박 시설 상세", description="직영/숙박 시설 상세 ( EZC_DIST_FACL_DETAIL )", modelTypes="TABLE")
public class EzcDistFaclDetail extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "시설 코드", maxLength=10, required=true, constraints="EZC_DIST_FACL_DETAIL_PK(P),FK_EZC_FACL_EZC_DIST_FACL_DETA(R),SYS_C0011407(C) EZC_DIST_FACL_DETAIL_PK(UNIQUE)")
	private BigDecimal faclCd;

	@APIFields(description = "팩스 번호", maxLength=20, required=true, constraints="SYS_C0011408(C)")
	private String faxNum;

	@APIFields(description = "홈페이지", maxLength=200)
	private String homepage;

	@APIFields(description = "추가 조식 요금", maxLength=8)
	private BigDecimal addBreakfastAmt;

	@APIFields(description = "추가 인원 요금", maxLength=8)
	private BigDecimal addUserAmt;

	@APIFields(description = "추가 배드 요금", maxLength=8)
	private BigDecimal addBedAmt;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011409(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011410(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss")
	private String modiDt;


	
	public BigDecimal getFaclCd() {
		return faclCd;
	}

	public void setFaclCd(BigDecimal faclCd) {
		this.faclCd = faclCd;
	}

	public String getFaxNum() {
		return faxNum;
	}

	public void setFaxNum(String faxNum) {
		this.faxNum = faxNum;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public BigDecimal getAddBreakfastAmt() {
		return addBreakfastAmt;
	}

	public void setAddBreakfastAmt(BigDecimal addBreakfastAmt) {
		this.addBreakfastAmt = addBreakfastAmt;
	}

	public BigDecimal getAddUserAmt() {
		return addUserAmt;
	}

	public void setAddUserAmt(BigDecimal addUserAmt) {
		this.addUserAmt = addUserAmt;
	}

	public BigDecimal getAddBedAmt() {
		return addBedAmt;
	}

	public void setAddBedAmt(BigDecimal addBedAmt) {
		this.addBedAmt = addBedAmt;
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
