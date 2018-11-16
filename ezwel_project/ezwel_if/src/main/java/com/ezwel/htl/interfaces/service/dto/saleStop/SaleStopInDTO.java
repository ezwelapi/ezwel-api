package com.ezwel.htl.interfaces.service.dto.saleStop;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;

/**
 * <pre>
 * 
 * </pre>
 * @author ypjeon@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
public class SaleStopInDTO extends AbstractEntity {

	@APIFields(description = "시설판매중지설정 Input 상품코드", required=true, maxLength=100)
	private String pdtNo;
	
	@APIFields(description = "시설판매중지설정 Input 판매중지여부코드", required=true, maxLength=1)
	private String sellFlag;

	public String getPdtNo() {
		return pdtNo;
	}

	public void setPdtNo(String pdtNo) {
		this.pdtNo = pdtNo;
	}
	
	public String getSellFlag() {
		return sellFlag;
	}

	public void setSellFlag(String sellFlag) {
		this.sellFlag = sellFlag;
	}

}
