package com.ezwel.htl.interfaces.server.sdo;

import java.util.List;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel(modelNames="XML 관리 I/O")
public class ManagerSDO extends AbstractSDO {

	@APIFields(description = "사용자 ID")
	private String userId;
	
	@APIFields(description = "사용자 IP")
	private String userIp;
	
	@APIFields(description = "XML 내용")
	private String xmlCont;
	
	@APIFields(description = "XPATH Query")
	private String xpathQuery;

	@APIFields(description = "XML Node 이름")
	private String nodeName;
	
	@APIFields(description = "XML Node 텍스트")
	private String nodeText;
	
	@APIFields(description = "XML 파일 목록")
	private List<String> fileList;

	@APIFields(description = "XML 설정 반영")
	private boolean isApplyXML;	
	
	@APIFields(description = "임시 XML 저장소 파일명")
	private String storeFileName;	

	@APIFields(description = "임시 XML 저장소 파일 디렉토리")
	private String storeFileDir;	
	
	@APIFields(description = "저장 여부")
	private boolean isSaved;	
	
	
	
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

	public String getXpathQuery() {
		return xpathQuery;
	}

	public void setXpathQuery(String xpathQuery) {
		this.xpathQuery = xpathQuery;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeText() {
		return nodeText;
	}

	public void setNodeText(String nodeText) {
		this.nodeText = nodeText;
	}

	public List<String> getFileList() {
		return fileList;
	}

	public void setFileList(List<String> fileList) {
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
	
	
}
