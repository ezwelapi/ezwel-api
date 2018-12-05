package com.ezwel.htl.interfaces.service.data.ezwelJob;

import java.util.List;


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
public class EzwelJobOutSDO extends AbstractSDO {

	@APIFields(description = "주문대사(이지웰) output code", required=true, maxLength=4)
	private String code;
	
	@APIFields(description = "주문대사(이지웰) output message", maxLength=100)
	private String message;

	@APIFields(description = "RestAPI URI")
	private String restURI;

	public String getRestURI() {
		return restURI;
	}

	public void setRestURI(String restURI) {
		this.restURI = restURI;
	}

	
	@APIFields(description = "주문대사(이지웰) output reserves")
	private List<EzwelJobReservesOutSDO> reserves = null;

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

	public List<EzwelJobReservesOutSDO> getReserves() {
		return reserves;
	}

	public void setReserves(List<EzwelJobReservesOutSDO> reserves) {
		this.reserves = reserves;
	}

}
