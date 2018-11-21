package com.ezwel.htl.interfaces.service.data.faclSearch;


import com.ezwel.htl.interfaces.commons.abstracts.AbstractDTO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;

/**
 * <pre>
 * 
 * </pre>
 * @author ypjeon@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
public class FaclSearchDataOutDTO extends AbstractDTO {

	@APIFields(description = "시설검색 output 상품코드", required=true, maxLength=100)
	private String  pdtNo;
	
	@APIFields(description = "시설검색 output 시설판매최저가(정상가)", required=true)
	private Integer sellNorPrice;
	
	@APIFields(description = "시설검색 output 시설판매최저가(판매가)", required=true)
	private Integer sellPrice;
	
	@APIFields(description = "시설검색 output 시설특가최저가(정상가)", required=true)
	private Integer spcNorPrice;
	
	@APIFields(description = "시설검색 output 시설특가최저가(판매가)", required=true)
	private Integer spcPrice;

	public String getPdtNo() {
		return pdtNo;
	}

	public void setPdtNo(String pdtNo) {
		this.pdtNo = pdtNo;
	}
	
	public Integer getSellNorPrice() {
		return sellNorPrice;
	}

	public void setSellNorPrice(Integer sellNorPrice) {
		this.sellNorPrice = sellNorPrice;
	}
	
	public Integer getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Integer sellPrice) {
		this.sellPrice = sellPrice;
	}
	
	public Integer getSpcNorPrice() {
		return spcNorPrice;
	}

	public void setSpcNorPrice(Integer spcNorPrice) {
		this.spcNorPrice = spcNorPrice;
	}
	
	public Integer getSpcPrice() {
		return spcPrice;
	}

	public void setSpcPrice(Integer spcPrice) {
		this.spcPrice = spcPrice;
	}

}
