package com.ezwel.htl.interfaces.service.data.roomRead;


import java.math.BigDecimal;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author ypjeon@ebsolution.co.kr
 * @date 2018. 11. 13.
 */

@APIModel(description="객실정보 조회 입력SDO")
@Data
@EqualsAndHashCode(callSuper=true)
public class RoomReadSeachMinInSDO extends AbstractSDO {
	
	@APIFields(description = "최저가조회 상품코드", maxLength=100)
	private String pdtNo;
	
	@APIFields(description = "최저가조회 제휴사코드", maxLength=20)
	private String partnerCd;

	public String getPdtNo() {
		return pdtNo;
	}

	public void setPdtNo(String pdtNo) {
		this.pdtNo = pdtNo;
	}

	public String getPartnerCd() {
		return partnerCd;
	}

	public void setPartnerCd(String partnerCd) {
		this.partnerCd = partnerCd;
	}
	
	
}
