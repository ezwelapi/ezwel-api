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
 * 0.0.1      iCodeManager         2018-11-23 18:58:44      신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:58:44
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
//@Data
@Alias("ezcCacheDayPrice")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="CACHE 당일특가", description="CACHE 당일특가 ( EZC_CACHE_DAY_PRICE )", modelTypes="TABLE")
public class EzcCacheDayPrice extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "당일특가 일자", maxLength=8, required=true, constraints="EZC_CACHE_DAY_PRICE_PK(P),SYS_C0011135(C) EZC_CACHE_DAY_PRICE_PK(UNIQUE)")
	private String dayPriceDd;

	@APIFields(description = "시설 코드", maxLength=10, required=true, constraints="EZC_CACHE_DAY_PRICE_PK(P),FK_EZC_MAPPING_FACL_EZC_CACHE(R),SYS_C0011136(C) EZC_CACHE_DAY_PRICE_IF01(NONUNIQUE),EZC_CACHE_DAY_PRICE_PK(UNIQUE)")
	private BigDecimal faclCd;

	@APIFields(description = "그룹 시설 코드", maxLength=10, required=true, constraints="EZC_CACHE_DAY_PRICE_PK(P),FK_EZC_MAPPING_FACL_EZC_CACHE(R),SYS_C0011137(C) EZC_CACHE_DAY_PRICE_IF01(NONUNIQUE),EZC_CACHE_DAY_PRICE_PK(UNIQUE)")
	private BigDecimal grpFaclCd;

	@APIFields(description = "당일특가 로그 일련번호", maxLength=10, required=true, constraints="FK_EZC_CACHE_DAY_PRICE_LOG_EZC(R),SYS_C0011138(C) EZC_CACHE_DAY_PRICE_IF02(NONUNIQUE)")
	private BigDecimal dayPriceLogSeq;

	@APIFields(description = "당일특가 최저가", maxLength=8, required=true, constraints="SYS_C0011139(C)")
	private BigDecimal dayPriceMinPrice;

	@APIFields(description = "판매 종료 시분", maxLength=4, required=true, constraints="SYS_C0011140(C)")
	private String saleEndTm;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011141(C)")
	private String regId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011142(C)")
	private String regDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss")
	private String modiDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());


	
	public String getDayPriceDd() {
		return dayPriceDd;
	}

	public void setDayPriceDd(String dayPriceDd) {
		this.dayPriceDd = dayPriceDd;
	}

	public BigDecimal getFaclCd() {
		return faclCd;
	}

	public void setFaclCd(BigDecimal faclCd) {
		this.faclCd = faclCd;
	}

	public BigDecimal getGrpFaclCd() {
		return grpFaclCd;
	}

	public void setGrpFaclCd(BigDecimal grpFaclCd) {
		this.grpFaclCd = grpFaclCd;
	}

	public BigDecimal getDayPriceLogSeq() {
		return dayPriceLogSeq;
	}

	public void setDayPriceLogSeq(BigDecimal dayPriceLogSeq) {
		this.dayPriceLogSeq = dayPriceLogSeq;
	}

	public BigDecimal getDayPriceMinPrice() {
		return dayPriceMinPrice;
	}

	public void setDayPriceMinPrice(BigDecimal dayPriceMinPrice) {
		this.dayPriceMinPrice = dayPriceMinPrice;
	}

	public String getSaleEndTm() {
		return saleEndTm;
	}

	public void setSaleEndTm(String saleEndTm) {
		this.saleEndTm = saleEndTm;
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
