package com.ezwel.htl.interfaces.service.data.faclSearch;

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
public class FaclSearchOutSDO extends AbstractSDO {

	@APIFields(description = "시설검색 output code", required=true, maxLength=4)
	private String code;
	
	@APIFields(description = "시설검색 output message", maxLength=100)
	private String message;

	@APIFields(description = "RestAPI URI")
	private String restURI;

	@APIFields(description = "시설검색 output data")
	private List<FaclSearchDataOutSDO> data = null;

	@APIFields(description = "시설검색 output message", maxLength=100)
	private Integer txCount;
	
	public String getRestURI() {
		return restURI;
	}

	public void setRestURI(String restURI) {
		this.restURI = restURI;
	}
	
	public Integer getTxCount() {
		return txCount;
	}

	public void setTxCount(Integer txCount) {
		this.txCount = txCount;
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

	public List<FaclSearchDataOutSDO> getData() {
		return data;
	}

	public void setData(List<FaclSearchDataOutSDO> data) {
		this.data = data;
	}

	public void addData(FaclSearchDataOutSDO data) {
		if(this.data == null) {
			this.data = new ArrayList<FaclSearchDataOutSDO>();
		}
		this.data.add(data);
	}
	
	public void addAllData(List<FaclSearchDataOutSDO> datas) {
		if(this.data == null) {
			this.data = new ArrayList<FaclSearchDataOutSDO>();
		}
		this.data.addAll(datas);
	}
	
}