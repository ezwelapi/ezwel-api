package com.ezwel.htl.interfaces.commons.configure.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ezwel.htl.interfaces.commons.abstracts.APIObject;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.constants.CodeMappingConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;

@APIModel(description="서버 주소")
public class ServerManagedConfig extends APIObject {

	@APIFields(description="개발 서버 IP 범위(대역)")
	private String devServerIpRange;
	
	@APIFields(description="개발 서버 도메인")
	private String devServerL4Domain;

	@APIFields(description="마스터 서버 도메인")
	private String devMasterServerName;

	@APIFields(description="API TEAM 테스트 서버 IP 범위(대역)")
	private String testServerIpRange;
	
	@APIFields(description="API TEAM 테스트 서버 도메인")
	private String testServerL4Domain;

	@APIFields(description="API TEAM 테스트 마스터 서버 도메인")
	private String testMasterServerName;
	
	@APIFields(description="운영 서버 IP 범위(대역)")
	private String prodServerIpRange;
	
	@APIFields(description="운영 서버 도메인")
	private String prodServerL4Domain;
	
	@APIFields(description="운영 인터페이스 마스터 서버네임")
	private String prodMasterServerName;
	
	@APIFields(description="웹앱 루트 키 명")
	private String webRootKeyName;
	
	@APIFields(description="XML 설정 정보 서비스 URI")
	private String configXmlServerUri;
	
	@APIFields(description="인터페이스 서버 웹앱 루트 키")
	private String ifServerWebRootKey;
	
	@APIFields(description="슬레이브 서버가 인터페이스 환경파일을 직접파싱할것인지 여부")
	private String directParseXmlYn;
	
	@APIFields(description = "인터페이스 환경XML파일 임시 저장경로 루트")
	private String temporaryXmlStorageRoot; 
	
	@APIFields(description = "인터페이스 환경XML파일 경로")
	private String xmlConfigRealPath;
	
	@APIFields(description = "인터페이스 환경XML파일 경로 (if_server상대경로)")
	private String ifServerRelativePath;
	
	@APIFields(description = "인터페이스 환경파일 백업 루트경로")
	private String xmlConfigFileBackupRoot;
	
	@APIFields(description = "인터페이스 환경 변경 로그 루트 경로")
	private String applyConfigLogRoot;
	
	public ServerManagedConfig() {
		this.reset();
	}
	
	private void reset() {
		devServerL4Domain = null;
		devServerIpRange = null;
		devMasterServerName = null;
		testServerIpRange = null;
		testServerL4Domain = null;
		testMasterServerName = null;
		prodServerIpRange = null;
		prodServerL4Domain = null;
		prodMasterServerName = null;
		webRootKeyName = null;
		configXmlServerUri = null;
		ifServerWebRootKey = null;	
		directParseXmlYn = OperateConstants.STR_N;
		temporaryXmlStorageRoot = "/WEB-INF/config/interface/store";
		xmlConfigRealPath = "/WEB-INF/config/interface/interface-configure.xml";
		ifServerRelativePath = OperateConstants.STR_Y;
		xmlConfigFileBackupRoot = "/WEB-INF/config/interface/backup";
		applyConfigLogRoot = "/WEB-INF/config/interface/logs";
	}

	public String getDevServerIpRange() {
		return devServerIpRange;
	}

	public void setDevServerIpRange(String devServerIpRange) {
		this.devServerIpRange = devServerIpRange;
	}

	public String getProdServerIpRange() {
		return prodServerIpRange;
	}

	public void setProdServerIpRange(String prodServerIpRange) {
		this.prodServerIpRange = prodServerIpRange;
	}

	public String getDevServerL4Domain() {
		return devServerL4Domain;
	}

	public void setDevServerL4Domain(String devServerL4Domain) {
		this.devServerL4Domain = devServerL4Domain;
	}

	public String getProdServerL4Domain() {
		return prodServerL4Domain;
	}

	public void setProdServerL4Domain(String prodServerL4Domain) {
		this.prodServerL4Domain = prodServerL4Domain;
	}

	public String getDevMasterServerName() {
		return devMasterServerName;
	}

	public void setDevMasterServerName(String devMasterServerName) {
		this.devMasterServerName = devMasterServerName;
	}

	public String getTestServerIpRange() {
		return testServerIpRange;
	}

	public void setTestServerIpRange(String testServerIpRange) {
		this.testServerIpRange = testServerIpRange;
	}

	public String getTestServerL4Domain() {
		return testServerL4Domain;
	}

	public void setTestServerL4Domain(String testServerL4Domain) {
		this.testServerL4Domain = testServerL4Domain;
	}

	public String getTestMasterServerName() {
		return testMasterServerName;
	}

	public void setTestMasterServerName(String testMasterServerName) {
		this.testMasterServerName = testMasterServerName;
	}

	public String getProdMasterServerName() {
		return prodMasterServerName;
	}

	public void setProdMasterServerName(String prodMasterServerName) {
		this.prodMasterServerName = prodMasterServerName;
	}

	public String getWebRootKeyName() {
		return webRootKeyName;
	}

	public void setWebRootKeyName(String webRootKeyName) {
		this.webRootKeyName = webRootKeyName;
	}

	public String getConfigXmlServerUri() {
		return configXmlServerUri;
	}

	public void setConfigXmlServerUri(String configXmlServerUri) {
		this.configXmlServerUri = configXmlServerUri;
	}

	public String getIfServerWebRootKey() {
		return ifServerWebRootKey;
	}
	
	public List<String> getIfServerWebRootKeyList() {
		List<String> out = new ArrayList<String>();
		if(ifServerWebRootKey != null) {
			out = Arrays.asList(ifServerWebRootKey.split(OperateConstants.STR_COMA));
		}
		return out;
	}

	public void setIfServerWebRootKey(String ifServerWebRootKey) {
		this.ifServerWebRootKey = ifServerWebRootKey;
	}

	public String getDirectParseXmlYn() {
		return directParseXmlYn;
	}

	public void setDirectParseXmlYn(String directParseXmlYn) {
		this.directParseXmlYn = directParseXmlYn;
	}

	public String getTemporaryXmlStorageRoot() {
		return temporaryXmlStorageRoot;
	}

	public void setTemporaryXmlStorageRoot(String temporaryXmlStorageRoot) {
		this.temporaryXmlStorageRoot = temporaryXmlStorageRoot;
	}

	public String getXmlConfigRealPath() {
		return xmlConfigRealPath;
	}

	public void setXmlConfigRealPath(String xmlConfigRealPath) {
		this.xmlConfigRealPath = xmlConfigRealPath;
	}

	public boolean isServerRelativePath() {
		return (ifServerRelativePath != null ? ifServerRelativePath.toUpperCase().equals(OperateConstants.STR_Y) : false);
	}
	
	public String getIfServerRelativePath() {
		return ifServerRelativePath;
	}

	public void setIfServerRelativePath(String ifServerRelativePath) {
		this.ifServerRelativePath = ifServerRelativePath;
	}

	public String getXmlConfigFileBackupRoot() {
		return xmlConfigFileBackupRoot;
	}

	public void setXmlConfigFileBackupRoot(String xmlConfigFileBackupRoot) {
		this.xmlConfigFileBackupRoot = xmlConfigFileBackupRoot;
	}

	public String getApplyConfigLogRoot() {
		return applyConfigLogRoot;
	}

	public void setApplyConfigLogRoot(String applyConfigLogRoot) {
		this.applyConfigLogRoot = applyConfigLogRoot;
	}

	
}
