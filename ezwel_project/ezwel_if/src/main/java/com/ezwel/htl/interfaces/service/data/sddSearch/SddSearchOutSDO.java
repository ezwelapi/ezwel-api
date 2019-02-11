package com.ezwel.htl.interfaces.service.data.sddSearch;

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
public class SddSearchOutSDO extends AbstractSDO {

	@APIFields(description = "당일특가검색 output code", required=true, maxLength=4)
	private String code;
	
	@APIFields(description = "당일특가검색 output message", maxLength=100)
	private String message;

	@APIFields(description = "RestAPI URI")
	private String restURI;

	@APIFields(description = "에이전트 아이디(제휴사 코드)")
	private String httpAgentId;

	@APIFields(description = "에이전트(제휴사 명)")
	private String httpAgentDesc;
	
	@APIFields(description = "트랜젝션 개수")
	private Integer txCount;
	
	@APIFields(description = "당일특가검색 output data")
	private List<SddSearchDataOutSDO> data = null;
	
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

	public List<SddSearchDataOutSDO> getData() {
		return data;
	}

	public void setData(List<SddSearchDataOutSDO> data) {
		this.data = data;
	}

	public void addData(SddSearchDataOutSDO data) {
		if(this.data == null) {
			this.data = new ArrayList<SddSearchDataOutSDO>();
		}
		
		this.data.add(data);
	}
	
	public Integer getTxCount() {
		return txCount;
	}

	public void setTxCount(Integer txCount) {
		this.txCount = txCount;
	}

	public String getHttpAgentId() {
		return httpAgentId;
	}

	public void setHttpAgentId(String httpAgentId) {
		this.httpAgentId = httpAgentId;
	}

	public String getHttpAgentDesc() {
		return httpAgentDesc;
	}

	public void setHttpAgentDesc(String httpAgentDesc) {
		this.httpAgentDesc = httpAgentDesc;
	}

}