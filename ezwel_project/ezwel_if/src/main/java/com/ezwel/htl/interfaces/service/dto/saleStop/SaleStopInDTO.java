package com.ezwel.htl.interfaces.service.dto.saleStop;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;

/**
 * <pre>
 * 
 * </pre>
 * @author ypjeon@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
public class SaleStopInDTO extends AbstractEntity {

	private String pdtNo;
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
