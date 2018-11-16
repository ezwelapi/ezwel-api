package com.ezwel.htl.interfaces.service.dto.sddSearch;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;

/**
 * <pre>
 * 
 * </pre>
 * @author ypjeon@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
public class SddSearchDataOutDTO extends AbstractEntity {

	@APIFields(description = "당일특가검색 output 상품코드", required=true, maxLength=100)
	private String  pdtNo;
	
	@APIFields(description = "당일특가검색 output 적용일", required=true, maxLength=8)
	private String  applyDate;
	
	@APIFields(description = "당일특가검색 output 최저가(정상가)", required=true)
	private Integer spcTodayNorPrice;
	
	@APIFields(description = "당일특가검색 output 최저가(판매가)", required=true)
	private Integer spcTodayPrice;
	
	@APIFields(description = "당일특가검색 output 상품종료일시", maxLength=12)
	private String  spcTypeTime;


	public String getPdtNo() {
		return pdtNo;
	}

	public void setPdtNo(String pdtNo) {
		this.pdtNo = pdtNo;
	}
	
	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	
	public Integer getSellNorPrice() {
		return spcTodayNorPrice;
	}

	public void setSpcTodayNorPrice(Integer spcTodayNorPrice) {
		this.spcTodayNorPrice = spcTodayNorPrice;
	}
	
	public Integer getSpcTodayPrice() {
		return spcTodayPrice;
	}

	public void setSpcTodayPrice(Integer spcTodayPrice) {
		this.spcTodayPrice = spcTodayPrice;
	}
	
	public String getSpcTypeTime() {
		return spcTypeTime;
	}

	public void setSpcTypeTime(String spcTypeTime) {
		this.spcTypeTime = spcTypeTime;
	}

}
