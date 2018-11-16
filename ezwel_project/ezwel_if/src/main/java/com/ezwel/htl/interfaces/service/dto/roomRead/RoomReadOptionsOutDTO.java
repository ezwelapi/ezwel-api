package com.ezwel.htl.interfaces.service.dto.roomRead;


import com.ezwel.htl.interfaces.commons.abstracts.AbstractDTO;



import com.ezwel.htl.interfaces.commons.annotation.APIFields;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author ypjeon@ebsolution.co.kr
 * @date 2018. 11. 13.
 */
public class RoomReadOptionsOutDTO extends AbstractDTO {

	@APIFields(description = "객실정보조회 output option 옵션코드", maxLength=100)
	private String optNo;
	
	@APIFields(description = "객실정보조회 output option 옵션명", maxLength=100)
	private String optName;
	
	@APIFields(description = "객실정보조회 output option 옵션가격")
	private Integer optPrice;
	
	@APIFields(description = "객실정보조회 output option 최대선택가능수량")
	private Integer optCountMax;

	public String getOptNo() {
		return optNo;
	}

	public void setOptNo(String optNo) {
		this.optNo = optNo;
	}
	
	public String getOptName() {
		return optName;
	}

	public void setOptName(String optName) {
		this.optName = optName;
	}

	public Integer getOptPrice() {
		return optPrice;
	}

	public void setOptPrice(Integer optPrice) {
		this.optPrice = optPrice;
	}

	public Integer getOptCountMax() {
		return optCountMax;
	}

	public void setOptCountMax(Integer optCountMax) {
		this.optCountMax = optCountMax;
	}

}
