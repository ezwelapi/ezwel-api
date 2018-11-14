package com.ezwel.htl.interfaces.service.dto.ezwelJob;

import java.util.List;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;

/**
 * <pre>
 * 
 * </pre>
 * @author ypjeon@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
public class EzwelJobOutDTO extends AbstractEntity {

	private String code;
	private String message;
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
