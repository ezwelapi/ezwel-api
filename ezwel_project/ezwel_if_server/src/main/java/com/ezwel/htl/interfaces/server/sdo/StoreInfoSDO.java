package com.ezwel.htl.interfaces.server.sdo;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel(modelNames="XML 파일 임시 저장소 정보")
public class StoreInfoSDO extends AbstractSDO {

	@APIFields(description = "파일명")
	private String name;
	
	@APIFields(description = "부모파일명(dir)")
	private String prntName;
	
	@APIFields(description = "전체경로")
	private String path;
	
	@APIFields(description = "파일설명")
	private String desc;
	
	@APIFields(description = "파일타입")
	private String fileType;
	
	@APIFields(description = "실제운영파일여부")
	private boolean isManagedXml;
	
	@APIFields(description = "소팅순서")
	private Integer order;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPrntName() {
		return prntName;
	}
	public void setPrntName(String prntName) {
		this.prntName = prntName;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public boolean isManagedXml() {
		return isManagedXml;
	}
	public void setManagedXml(boolean isManagedXml) {
		this.isManagedXml = isManagedXml;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	
}
