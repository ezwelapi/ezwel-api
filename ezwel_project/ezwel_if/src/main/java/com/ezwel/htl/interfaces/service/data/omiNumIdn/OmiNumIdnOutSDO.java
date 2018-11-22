package com.ezwel.htl.interfaces.service.data.omiNumIdn;


import com.ezwel.htl.interfaces.commons.abstracts.AbstractDTO;
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
public class OmiNumIdnOutSDO extends AbstractDTO {

	@APIFields(description = "누락건확인 output code", required=true, maxLength=4)
	private String code;
	
	@APIFields(description = "누락건확인 output message", maxLength=100)
	private String message;
	
	@APIFields(description = "누락건확인 output reserves")
	private OmiNumIdnReservesOutSDO reserves;

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

	public OmiNumIdnReservesOutSDO getReserves() {
		return reserves;
	}

	public void setReserves(OmiNumIdnReservesOutSDO reserves) {
		this.reserves = reserves;
	}

}
