package com.ezwel.htl.interfaces.server.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import lombok.Data;
//import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractEntity;


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
@Alias("ezcDetailCd")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="공통 코드 정보", description="공통 코드 정보 ( EZC_DETAIL_CD )", modelTypes="TABLE")
public class EzcDetailCd extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "공통 코드", maxLength=8, required=true, constraints="EZC_DETAIL_CD_PK(P),SYS_C0011381(C) EZC_DETAIL_CD_PK(UNIQUE)")
	private String masterCd;

	@APIFields(description = "상세 코드", maxLength=4, required=true, constraints="SYS_C0011382(C)")
	private String detailCd;

	@APIFields(description = "상세 코드 IN 목록", maxLength=0, required=false, constraints="SYS_C0011382(C)")
	private List<String> detailCdList;
	
	@APIFields(description = "분류 코드", maxLength=4, required=true, constraints="FK_EZC_CLASS_CD_EZC_DETAIL_CD(R),SYS_C0011383(C) EZC_DETAIL_CD_IF01(NONUNIQUE)")
	private String classCd;

	@APIFields(description = "분류 코드 IN 목록", maxLength=0, required=false, constraints="FK_EZC_CLASS_CD_EZC_DETAIL_CD(R),SYS_C0011383(C) EZC_DETAIL_CD_IF01(NONUNIQUE)")
	private List<String> classCdList;
	
	@APIFields(description = "상세 명", maxLength=50)
	private String detailNm;

	@APIFields(description = "정렬순서", maxLength=4)
	private BigDecimal dispOrder;

	@APIFields(description = "사용 여부", maxLength=1, required=true, constraints="SYS_C0011384(C)")
	private String useYn;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011385(C)")
	private String regId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011386(C)")
	private String regDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss")
	private String modiDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());


	
	public String getMasterCd() {
		return masterCd;
	}

	public void setMasterCd(String masterCd) {
		this.masterCd = masterCd;
	}

	public String getDetailCd() {
		return detailCd;
	}

	public void setDetailCd(String detailCd) {
		this.detailCd = detailCd;
	}

	public String getClassCd() {
		return classCd;
	}

	public void setClassCd(String classCd) {
		this.classCd = classCd;
	}

	public String getDetailNm() {
		return detailNm;
	}

	public void setDetailNm(String detailNm) {
		this.detailNm = detailNm;
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

	public List<String> getDetailCdList() {
		return detailCdList;
	}

	public void setDetailCdList(List<String> detailCdList) {
		this.detailCdList = detailCdList;
	}

	public void addDetailCdList(String... detailCd) {
		if(this.detailCdList == null) {
			this.detailCdList = new ArrayList<String>();
		}
		if(detailCd != null && detailCd.length > 0) {
			this.detailCdList.addAll(Arrays.asList(detailCd));
		}
	}
	
	public List<String> getClassCdList() {
		return classCdList;
	}

	public void setClassCdList(List<String> classCdList) {
		this.classCdList = classCdList;
	}

	public void addClassCdList(String... classCd) {
		if(this.classCdList == null) {
			this.classCdList = new ArrayList<String>();
		}
		if(classCd != null && classCd.length > 0) {
			this.classCdList.addAll(Arrays.asList(classCd));
		}
	}
}	
