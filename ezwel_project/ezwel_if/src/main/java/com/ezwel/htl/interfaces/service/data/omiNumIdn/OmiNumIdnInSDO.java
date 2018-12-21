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
public class OmiNumIdnInSDO extends AbstractSDO {
	
	@APIFields(description = "누락건확인 Input 주문번호", required=true, maxLength=100)
	private String rsvNo;
	
	@APIFields(description = "누락건확인 Input 주문상태코드", required=true, maxLength=5)
	private String rsvStat;
	
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

}
