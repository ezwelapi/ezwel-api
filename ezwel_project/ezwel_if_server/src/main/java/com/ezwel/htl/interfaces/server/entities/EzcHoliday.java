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
@Alias("ezcHoliday")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="공휴일 정보", description="공휴일 정보 ( EZC_HOLIDAY )", modelTypes="TABLE")
public class EzcHoliday extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "공휴일 일련번호", maxLength=10, required=true, constraints="EZC_HOLIDAY_PK(P),SYS_C0011460(C) EZC_HOLIDAY_PK(UNIQUE)")
	private BigDecimal hdaySeq;

	@APIFields(description = "공휴일 유형 GB", maxLength=8, required=true, constraints="SYS_C0011461(C)")
	private String hdayTypeGb;

	@APIFields(description = "대체휴일 년도", maxLength=4)
	private String sholidayYy;

	@APIFields(description = "공휴일 월일", maxLength=4, required=true, constraints="SYS_C0011462(C)")
	private String hdayMmdd;

	@APIFields(description = "음력 여부", maxLength=1, required=true, constraints="SYS_C0011463(C)")
	private String lunarYn;

	@APIFields(description = "공휴일 명", maxLength=50)
	private String hdayNm;

	@APIFields(description = "공휴일 설명", maxLength=1000)
	private String hdayDesc;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011464(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011465(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss")
	private String modiDt;


	
	public BigDecimal getHdaySeq() {
		return hdaySeq;
	}

	public void setHdaySeq(BigDecimal hdaySeq) {
		this.hdaySeq = hdaySeq;
	}

	public String getHdayTypeGb() {
		return hdayTypeGb;
	}

	public void setHdayTypeGb(String hdayTypeGb) {
		this.hdayTypeGb = hdayTypeGb;
	}

	public String getSholidayYy() {
		return sholidayYy;
	}

	public void setSholidayYy(String sholidayYy) {
		this.sholidayYy = sholidayYy;
	}

	public String getHdayMmdd() {
		return hdayMmdd;
	}

	public void setHdayMmdd(String hdayMmdd) {
		this.hdayMmdd = hdayMmdd;
	}

	public String getLunarYn() {
		return lunarYn;
	}

	public void setLunarYn(String lunarYn) {
		this.lunarYn = lunarYn;
	}

	public String getHdayNm() {
		return hdayNm;
	}

	public void setHdayNm(String hdayNm) {
		this.hdayNm = hdayNm;
	}

	public String getHdayDesc() {
		return hdayDesc;
	}

	public void setHdayDesc(String hdayDesc) {
		this.hdayDesc = hdayDesc;
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
