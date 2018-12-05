package com.ezwel.htl.interfaces.service.data.allReg;

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
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
@APIModel
@Data
@EqualsAndHashCode(callSuper=true)
public class AllRegOutSDO extends AbstractSDO {

	@APIFields(description = "전체시설일괄등록 output code", required=true, maxLength=4)
	private String code;
	
	@APIFields(description = "전체시설일괄등록 output message", maxLength=100)
	private String message;

	@APIFields(description = "전체시설일괄등록 output code list", required=true, maxLength=4)
	private List<String> multiExecCodeList;
	
	@APIFields(description = "전체시설일괄등록 output message list", maxLength=100)
	private List<String> multiExecMessageList;
	
	@APIFields(description = "RestAPI URI")
	private String restURI;

	public String getRestURI() {
		return restURI;
	}

	public void setRestURI(String restURI) {
		this.restURI = restURI;
	}

	
	@APIFields(description = "고객사ID (제휴사 코드)", maxLength=20)
	private String httpAgentId;
	
	@APIFields(description = "고객사(파트너) 코드 유형", required=true)
	private String patnCdType;
	
	@APIFields(description = "전체시설일괄등록 output data")
	private List<AllRegDataOutSDO> data = null;
	
	private Integer txCount = 0;
	
	
	public String getPatnCdType() {
		return patnCdType;
	}

	public void setPatnCdType(String patnCdType) {
		this.patnCdType = patnCdType;
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

	public List<AllRegDataOutSDO> getData() {
		return data;
	}

	public void setData(List<AllRegDataOutSDO> data) {
		this.data = data;
	}

	public String getHttpAgentId() {
		return httpAgentId;
	}

	public void setHttpAgentId(String httpAgentId) {
		this.httpAgentId = httpAgentId;
	}

	public List<String> getMultiExecCodeList() {
		return multiExecCodeList;
	}

	public void setMultiExecCodeList(List<String> multiExecCodeList) {
		this.multiExecCodeList = multiExecCodeList;
	}

	public void addMultiExecCodeList(String multiExecCode) {
		if(this.multiExecCodeList == null) {
			this.multiExecCodeList = new ArrayList<String>();
		}
		this.multiExecCodeList.add(multiExecCode);
	}
	
	public List<String> getMultiExecMessageList() {
		return multiExecMessageList;
	}

	public void setMultiExecMessageList(List<String> multiExecMessageList) {
		this.multiExecMessageList = multiExecMessageList;
	}

	public void addMultiExecMessageList(String multiExecMessage) {
		if(this.multiExecMessageList == null) {
			this.multiExecMessageList = new ArrayList<String>();
		}
		this.multiExecMessageList.add(multiExecMessage);
	}
}
