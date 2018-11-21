package com.ezwel.htl.interfaces.service.data.roomRead;


import java.util.List;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractDTO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author ypjeon@ebsolution.co.kr
 * @date 2018. 11. 13.
 */
public class RoomReadOutDTO extends AbstractDTO {

	@APIFields(description = "객실정보조회 output code", required=true, maxLength=4)
	private String code;
	
	@APIFields(description = "객실정보조회 output message", maxLength=100)
	private String message;
	
	@APIFields(description = "객실정보조회 output data")
	private List<RoomReadDataOutDTO> data;

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

	public List<RoomReadDataOutDTO> getData() {
		return data;
	}

	public void setData(List<RoomReadDataOutDTO> data) {
		this.data = data;
	}

}