package com.ezwel.htl.interfaces.server.sdo;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel(modelNames="XML 파일 임시 저장소 정보")
public class StoreInfoSDO extends AbstractSDO {

	@APIFields(description = "파일명")
	private String name;
	
	@APIFields(description = "요청경로")
	private String path;
	
	@APIFields(description = "파일설명")
	private String desc;
	
	@APIFields(description = "임시 저장소 파일 디렉토리")
	private String storeFileDir;
	
	@APIFields(description = "임시 저장소 파일 명")
	private String storeFileName;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getStoreFileDir() {
		return storeFileDir;
	}
	public void setStoreFileDir(String storeFileDir) {
		this.storeFileDir = storeFileDir;
	}
	public String getStoreFileName() {
		return storeFileName;
	}
	public void setStoreFileName(String storeFileName) {
		this.storeFileName = storeFileName;
	}
}
