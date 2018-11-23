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
 * 0.0.1      CodeSkeleton         2018-11-23 18:44:43                신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:44:43
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
@Data
@Alias("ezcPopulSearch")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="인기 검색어", description="인기 검색어 ( EZC_POPUL_SEARCH )", modelTypes="TABLE")
public class EzcPopulSearch extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "검색어 일련번호", maxLength=10, required=true, constraints = "EZC_POPUL_SEARCH_PK(P),SYS_C0011528(C) EZC_POPUL_SEARCH_PK(UNIQUE)")
	private BigDecimal searchSeq;

	@APIFields(description = "검색어", maxLength=100, required=true, constraints = "SYS_C0011529(C)")
	private String search;

	@APIFields(description = "검색 일자", maxLength=8, required=true, constraints = "SYS_C0011530(C)")
	private String searchDd;

	@APIFields(description = "실제 조회수", maxLength=4)
	private BigDecimal realViewCnt;

	@APIFields(description = "관리 조회수", maxLength=4)
	private BigDecimal mngViewCnt;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints = "SYS_C0011531(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, required=true, constraints = "SYS_C0011532(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true)
	private String modiDt;


	
	public BigDecimal getSearchSeq() {
		return searchSeq;
	}

	public void setSearchSeq(BigDecimal searchSeq) {
		this.searchSeq = searchSeq;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getSearchDd() {
		return searchDd;
	}

	public void setSearchDd(String searchDd) {
		this.searchDd = searchDd;
	}

	public BigDecimal getRealViewCnt() {
		return realViewCnt;
	}

	public void setRealViewCnt(BigDecimal realViewCnt) {
		this.realViewCnt = realViewCnt;
	}

	public BigDecimal getMngViewCnt() {
		return mngViewCnt;
	}

	public void setMngViewCnt(BigDecimal mngViewCnt) {
		this.mngViewCnt = mngViewCnt;
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
