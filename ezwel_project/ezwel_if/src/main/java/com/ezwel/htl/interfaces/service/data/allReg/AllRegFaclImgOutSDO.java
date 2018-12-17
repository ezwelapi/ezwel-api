package com.ezwel.htl.interfaces.service.data.allReg;

import java.math.BigDecimal;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;


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
@EqualsAndHashCode(callSuper=true)
@APIModel(description="시설 이미지")
public class AllRegFaclImgOutSDO extends AbstractSDO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@APIFields(description = "실행자 ID", maxLength=20)
	private String execId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "실행 일시", maxLength=14, isDate=true, dateFormat="yyyyMMddHHmmss")
	private String execDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());

	@APIFields(description = "다운로드 성공 개수", maxLength=9)
	private Integer downSuceCount;
	
	@APIFields(description = "다운로드 실패 개수", maxLength=9)
	private Integer downFailCount;

	@APIFields(description = "이미지 데이터 DB TX개수", maxLength=9)
	private Integer txCount;
	
	public String getExecId() {
		return execId;
	}

	public void setExecId(String execId) {
		this.execId = execId;
	}

	public String getExecDt() {
		return execDt;
	}

	public void setExecDt(String execDt) {
		this.execDt = execDt;
	}

	public Integer getDownSuceCount() {
		return downSuceCount;
	}

	public void setDownSuceCount(Integer downSuceCount) {
		this.downSuceCount = downSuceCount;
	}

	public Integer getDownFailCount() {
		return downFailCount;
	}

	public void setDownFailCount(Integer downFailCount) {
		this.downFailCount = downFailCount;
	}

	public Integer getTxCount() {
		return txCount;
	}

	public void setTxCount(Integer txCount) {
		this.txCount = txCount;
	}

	
	
}	
