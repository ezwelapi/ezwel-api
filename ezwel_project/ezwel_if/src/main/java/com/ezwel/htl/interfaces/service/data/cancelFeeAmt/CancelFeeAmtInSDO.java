package com.ezwel.htl.interfaces.service.data.cancelFeeAmt;


import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import lombok.Data;
import lombok.EqualsAndHashCode;



import com.ezwel.htl.interfaces.commons.annotation.APIFields;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
@APIModel
@Data
@EqualsAndHashCode(callSuper=true)
public class CancelFeeAmtInSDO extends AbstractSDO {

	@APIFields(description = "취소수수료계산 Input 제휴사아이디", required=true, maxLength=100)
	private String otaId;
	
	@APIFields(description = "취소수수료계산 Input 주문번호", required=true, maxLength=100)
	private String rsvNo;
	
	public String getOtaId() {
		return otaId;
	}

	public void setOtaId(String otaId) {
		this.otaId = otaId;
	}
	
	public String getRsvNo() {
		return rsvNo;
	}

	public void setRsvNo(String rsvNo) {
		this.rsvNo = rsvNo;
	}

}
