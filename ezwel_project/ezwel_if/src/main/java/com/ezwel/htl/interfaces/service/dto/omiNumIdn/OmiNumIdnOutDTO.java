package com.ezwel.htl.interfaces.service.dto.omiNumIdn;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractDTO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;

/**
 * <pre>
 * 
 * </pre>
 * @author ypjeon@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
public class OmiNumIdnOutDTO extends AbstractDTO {

	@APIFields(description = "누락건확인 output code", required=true, maxLength=4)
	private String code;
	
	@APIFields(description = "누락건확인 output message", maxLength=100)
	private String message;
	
	@APIFields(description = "누락건확인 output reserves")
	private OmiNumIdnReservesOutDTO reserves;

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

	public OmiNumIdnReservesOutDTO getReserves() {
		return reserves;
	}

	public void setReserves(OmiNumIdnReservesOutDTO reserves) {
		this.reserves = reserves;
	}

}
