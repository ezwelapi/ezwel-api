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
 * 
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 13.
 */

@APIModel
@Data
@EqualsAndHashCode(callSuper=true)
public class CancelFeeAmtOutSDO extends AbstractSDO {



	
	@APIFields(description = "취소수수료계산 output code", required=true, maxLength=4)
	private String code;
	
	@APIFields(description = "취소수수료계산 output message", maxLength=100)
	private String message;
	
	@APIFields(description = "취소수수료계산 output data")
	private CancelFeeAmtDataOutSDO data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public CancelFeeAmtDataOutSDO getData() {
		return data;
	}

	public void setData(CancelFeeAmtDataOutSDO data) {
		this.data = data;
	}

}
