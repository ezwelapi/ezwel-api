package com.ezwel.htl.interfaces.service.dto.sddSearch;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;

/**
 * <pre>
 * 
 * </pre>
 * @author ypjeon@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
public class SddSearchDataOutDTO extends AbstractEntity {

	private String  pdtNo;
	private String  applyDate;
	private Integer spcTodayNorPrice;
	private Integer spcTodayPrice;
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
