package com.ezwel.htl.interfaces.service.dto.roomRead;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 13.
 */
public class RoomReadOutDTO extends AbstractEntity {

	private String code;
	private String message;
	private RoomReadDataOutDTO data;

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

	public RoomReadDataOutDTO getData() {
		return data;
	}

	public void setData(RoomReadDataOutDTO data) {
		this.data = data;
	}

}