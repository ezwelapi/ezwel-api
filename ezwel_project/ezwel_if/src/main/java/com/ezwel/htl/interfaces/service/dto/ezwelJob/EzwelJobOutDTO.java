package com.ezwel.htl.interfaces.service.dto.ezwelJob;

import java.util.List;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractDTO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;

/**
 * <pre>
 * 
 * </pre>
 * @author ypjeon@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
public class EzwelJobOutDTO extends AbstractDTO {

	@APIFields(description = "주문대사(이지웰) output code", required=true, maxLength=4)
	private String code;
	
	@APIFields(description = "주문대사(이지웰) output message", maxLength=100)
	private String message;
	
	@APIFields(description = "주문대사(이지웰) output reserves")
	private List<EzwelJobReservesOutDTO> reserves = null;

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

	public List<EzwelJobReservesOutDTO> getReserves() {
		return reserves;
	}

	public void setReserves(List<EzwelJobReservesOutDTO> reserves) {
		this.reserves = reserves;
	}

}
