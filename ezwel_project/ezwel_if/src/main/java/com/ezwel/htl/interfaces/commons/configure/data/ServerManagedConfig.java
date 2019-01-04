package com.ezwel.htl.interfaces.commons.configure.data;

import com.ezwel.htl.interfaces.commons.abstracts.APIObject;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel(description="서버 주소")
public class ServerManagedConfig extends APIObject {

	@APIFields(description="개발 서버 IP 범위(대역)")
	private String devServerIpRange;
	
	@APIFields(description="개발 서버 도메인")
	private String devServerDomain;

	@APIFields(description="운영 서버 도메인")
	private String devMasterServerName;

	@APIFields(description="운영 서버 IP 범위(대역)")
	private String prodServerIpRange;
	
	@APIFields(description="운영 서버 도메인")
	private String prodServerDomain;
	
	@APIFields(description="운영 인터페이스 마스터 서버네임")
	private String prodMasterServerName;
	
	@APIFields(description="웹앱 루트 키 명")
	private String webRootKeyName;
	
	@APIFields(description="XML 설정 정보 서비스 URI")
	private String configXmlServerUri;
	
	@APIFields(description="인터페이스 서버 웹앱 루트 키")
	private String ifServerWebRootKey;
	
	
	public ServerManagedConfig() {
		this.reset();
	}
	
	private void reset() {
		devServerDomain = null;
		devServerIpRange = null;
		devMasterServerName = null;
		prodServerIpRange = null;
		prodServerDomain = null;
		prodMasterServerName = null;
		webRootKeyName = null;
		configXmlServerUri = null;
		ifServerWebRootKey = null;		
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

	public String getDevServerDomain() {
		return devServerDomain;
	}

	public void setDevServerDomain(String devServerDomain) {
		this.devServerDomain = devServerDomain;
	}

	public String getProdServerDomain() {
		return prodServerDomain;
	}

	public void setProdServerDomain(String prodServerDomain) {
		this.prodServerDomain = prodServerDomain;
	}

	public String getDevMasterServerName() {
		return devMasterServerName;
	}

	public void setDevMasterServerName(String devMasterServerName) {
		this.devMasterServerName = devMasterServerName;
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

	public void setIfServerWebRootKey(String ifServerWebRootKey) {
		this.ifServerWebRootKey = ifServerWebRootKey;
	}
	
	
}
