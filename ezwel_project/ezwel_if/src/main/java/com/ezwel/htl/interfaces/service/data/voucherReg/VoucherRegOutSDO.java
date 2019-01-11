package com.ezwel.htl.interfaces.service.data.voucherReg;


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
 * @author ypjeon@ebsolution.co.kr
 * @date 2018. 11. 13.
 */
@APIModel
@Data
@EqualsAndHashCode(callSuper=true)
public class VoucherRegOutSDO extends AbstractSDO {

	@APIFields(description = "시설바우처번호등록 output code", required=true, maxLength=4)
	private String code;
	
	@APIFields(description = "시설바우처번호등록 output message", maxLength=100)
	private String message;

	@APIFields(description = "RestAPI URI")
	private String restURI;

	@APIFields(description = "트랜젝션 실행 개수")	
	private Integer txCount;
	
	public String getRestURI() {
		return restURI;
	}

	public void setRestURI(String restURI) {
		this.restURI = restURI;
	}


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

	public Integer getTxCount() {
		return txCount;
	}

	public void setTxCount(Integer txCount) {
		this.txCount = txCount;
	}

}
