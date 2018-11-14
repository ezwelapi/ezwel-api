package com.ezwel.htl.interfaces.service.dto.faclSearch;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;

/**
 * <pre>
 * 
 * </pre>
 * @author ypjeon@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
public class FaclSearchDataOutDTO extends AbstractEntity {

	private String  pdtNo;
	private Integer sellNorPrice;
	private Integer sellPrice;
	private Integer spcNorPrice;
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
