package com.ezwel.htl.interfaces.commons.configure.data;

import javax.xml.bind.annotation.XmlElement;

import com.ezwel.htl.interfaces.commons.abstracts.APIObject;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel(description="숙박 건물 이미지 저장 경로")
public class BuildImageConfig extends APIObject {

	private static final long serialVersionUID = 1L;
	
	@APIFields(description="로컬(PC) 경로 루트")
	private String localRootPath;

	@APIFields(description="개발서버 경로 루트")
	private String devRootPath;
	
	@APIFields(description="운영서버 경로 루트")
	private String prodRootPath;
	
	public BuildImageConfig() {
		this.reset();
	}
	
	private void reset() {
		localRootPath = null;
		devRootPath = null;
		prodRootPath = null;
	}

	public String getLocalRootPath() {
		return localRootPath;
	}

	@XmlElement
	public void setLocalRootPath(String localRootPath) {
		this.localRootPath = localRootPath;
	}

	public String getDevRootPath() {
		return devRootPath;
	}

	@XmlElement
	public void setDevRootPath(String devRootPath) {
		this.devRootPath = devRootPath;
	}

	public String getProdRootPath() {
		return prodRootPath;
	}

	@XmlElement
	public void setProdRootPath(String prodRootPath) {
		this.prodRootPath = prodRootPath;
	}
	
	
}
