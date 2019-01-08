package com.ezwel.htl.interfaces.server.sdo;

import java.util.LinkedHashMap;
import java.util.Map;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;

@APIModel(modelNames="트랜젝션 결과")
public class TransactionOutSDO extends AbstractSDO {

	@APIFields(description = "타입 명")
	private String typeName;
	
	@APIFields(description = "타입 설명")
	private String typeDesc;
	
	@APIFields(description = "오퍼레이션 명")
	private String operationName;
	
	@APIFields(description = "오퍼레이션 설명")
	private String operationDesc;

	@APIFields(description = "트랜젝션 개수")
	private Integer txCount = OperateConstants.INTEGER_ZERO_VALUE;

	@APIFields(description = "결과 코드")
	private String code;

	@APIFields(description = "결과 메시지")
	private String message;
	
	@APIFields(description = "개발용 프로파일 맵")
	private Map<String, Object> profileMap;
	
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

	public Integer getTxCount() {
		return txCount;
	}

	public void setTxCount(Integer txCount) {
		this.txCount = txCount;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getOperationDesc() {
		return operationDesc;
	}

	public void setOperationDesc(String operationDesc) {
		this.operationDesc = operationDesc;
	}

	public Map<String, Object> getProfileMap() {
		return profileMap;
	}

	public Object getProfileMap(String key) {
		return profileMap.get(key);
	}
	
	public void setProfileMap(Map<String, Object> profileMap) {
		this.profileMap = profileMap;
	}

	public void addProfileMap(String key, Object value) {
		if(this.profileMap == null) {
			this.profileMap = new LinkedHashMap<String, Object>();
		}
		this.profileMap.put(key, value);
	}
}
