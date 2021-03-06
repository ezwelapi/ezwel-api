package com.ezwel.htl.interfaces.service.data.omiNumIdn;


import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import lombok.Data;
import lombok.EqualsAndHashCode;



import com.ezwel.htl.interfaces.commons.annotation.APIFields;

/**
 * <pre>
 * 
 * </pre>
 * @author ypjeon@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
@APIModel
@Data
@EqualsAndHashCode(callSuper=true)
public class OmiNumIdnReservesOutSDO extends AbstractSDO {

	@APIFields(description = "누락건확인 output 주문번호(이지웰)", required=true, maxLength=100)
	private String rsvNo;
	
	@APIFields(description = "누락건확인 output 주문상태코드", required=true, maxLength=5)
	private String rsvStat;

	@APIFields(description = "누락건확인 output 주문상태코드 명")
	private String rsvStatName;
	
	@APIFields(description = "누락건확인 output 주문번호(제휴사)", maxLength=100)
	private String otaRsvNo;
	
	@APIFields(description = "누락건확인 output 주문상태비교결과코드", required=true, maxLength=3)
	private String compareStat;

	@APIFields(description = "누락건확인 output 주문상태비교결과코드 명")
	private String compareStatName;
	
	public String getRsvNo() {
		return rsvNo;
	}

	public void setRsvNo(String rsvNo) {
		this.rsvNo = rsvNo;
	}

	public String getRsvStat() {		
		return rsvStat;
	}

	public void setRsvStat(String rsvStat) {
		this.rsvStat = rsvStat;
	}

	
	public String getOtaRsvNo() {
		return otaRsvNo;
	}

	public void setOtaRsvNo(String otaRsvNo) {
		this.otaRsvNo = otaRsvNo;
	}

	public String getCompareStat() {
		return compareStat;
	}

	public void setCompareStat(String compareStat) {
		this.compareStat = compareStat;
	}

	public String getRsvStatName() {
		return rsvStatName;
	}

	public void setRsvStatName(String rsvStatName) {
		this.rsvStatName = rsvStatName;
	}

	public String getCompareStatName() {
		return compareStatName;
	}

	public void setCompareStatName(String compareStatName) {
		this.compareStatName = compareStatName;
	}

	
}
