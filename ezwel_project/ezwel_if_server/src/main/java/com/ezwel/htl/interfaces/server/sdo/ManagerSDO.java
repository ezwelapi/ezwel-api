package com.ezwel.htl.interfaces.server.sdo;

import java.util.List;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel(modelNames="XML 관리 I/O")
public class ManagerSDO extends AbstractSDO {

	@APIFields(description = "code")
	private String code;
	
	@APIFields(description = "message")
	private String message;
	
	@APIFields(description = "사용자 ID")
	private String userId;
	
	@APIFields(description = "사용자 IP")
	private String userIp;
	
	@APIFields(description = "XML 내용")
	private String xmlCont;
	
	@APIFields(description = "XML 파일 목록")
	private List<StoreInfoSDO> fileList;

	@APIFields(description = "XML 설정 반영")
	private boolean isApplyXML;	
	
	@APIFields(description = "임시 XML 저장소 파일명")
	private String storeFileName;	

	@APIFields(description = "임시 XML 저장소 파일 디렉토리")
	private String storeFileDir;	
	
	@APIFields(description = "저장 여부")
	private boolean isSaved;	
	
	@APIFields(description = "저장소 조회 유형")
	private String storeType;

	@APIFields(description = "운영 XML 여부")
	private boolean isManagedXml;
	

	
	
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getXmlCont() {
		return xmlCont;
	}

	public void setXmlCont(String xmlCont) {
		this.xmlCont = xmlCont;
	}

	public List<StoreInfoSDO> getFileList() {
		return fileList;
	}

	public void setFileList(List<StoreInfoSDO> fileList) {
		this.fileList = fileList;
	}

	public boolean isApplyXML() {
		return isApplyXML;
	}

	public void setApplyXML(boolean isApplyXML) {
		this.isApplyXML = isApplyXML;
	}

	public String getStoreFileName() {
		return storeFileName;
	}

	public void setStoreFileName(String storeFileName) {
		this.storeFileName = storeFileName;
	}

	public String getStoreFileDir() {
		return storeFileDir;
	}

	public void setStoreFileDir(String storeFileDir) {
		this.storeFileDir = storeFileDir;
	}

	public boolean isSaved() {
		return isSaved;
	}

	public void setSaved(boolean isSaved) {
		this.isSaved = isSaved;
	}

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	public boolean isManagedXml() {
		return isManagedXml;
	}

	public void setManagedXml(boolean isManagedXml) {
		this.isManagedXml = isManagedXml;
	}
	
	
}
