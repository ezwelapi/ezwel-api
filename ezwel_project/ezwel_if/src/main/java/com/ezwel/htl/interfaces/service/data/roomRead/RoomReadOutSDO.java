package com.ezwel.htl.interfaces.service.data.roomRead;


import java.util.ArrayList;
import java.util.List;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class RoomReadOutSDO extends AbstractSDO {

	@APIFields(description = "객실정보조회 output code", required=true, maxLength=4)
	private String code;
	
	@APIFields(description = "객실정보조회 output message", maxLength=100)
	private String message;

	@APIFields(description = "RestAPI URI")
	private String restURI;

	@APIFields(description = "객실정보조회 output data")
	private List<RoomReadDataOutSDO> data;

	
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

	public List<RoomReadDataOutSDO> getData() {
		return data;
	}

	public void setData(List<RoomReadDataOutSDO> data) {
		this.data = data;
	}

	public void addData(RoomReadDataOutSDO data) {
		if(this.data == null) {
			this.data = new ArrayList<RoomReadDataOutSDO>();
		}
		this.data.add(data);
	}
}