package com.ezwel.htl.interfaces.service.data.voucherReg;


import com.ezwel.htl.interfaces.commons.abstracts.AbstractDTO;



import com.ezwel.htl.interfaces.commons.annotation.APIFields;

/**
 * <pre>
 * 
 * </pre>
 * @author ypjeon@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
public class VoucherRegInDTO extends AbstractDTO {

	@APIFields(description = "시설바우처번호등록 Input 주문번호", required=true, maxLength=100)
	private String rsvNo;
	
	@APIFields(description = "시설바우처번호등록 Input 바우처번호", required=true, maxLength=100)
	private String voucherNo;

	public String getRsvNo() {
		return rsvNo;
	}

	public void setRsvNo(String rsvNo) {
		this.rsvNo = rsvNo;
	}
	
	public String getVoucherNo() {
		return voucherNo;
	}

	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}

}