package com.ezwel.htl.interfaces.commons.configure.data;

import javax.xml.bind.annotation.XmlElement;

import com.ezwel.htl.interfaces.commons.abstracts.APIObject;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel(description="서버 주소")
public class ServerAddressConfig extends APIObject {

	private static final long serialVersionUID = 1L;
	
	@APIFields(description="개발 서버 IP 범위(대역)")
	private String devServerIpRange;

	@APIFields(description="운영 서버 IP 범위(대역)")
	private String prodServerIpRange;

	@APIFields(description="개발 서버 도메인")
	private String devServerDomain;
	
	@APIFields(description="운영 서버 도메인")
	private String prodServerDomain;
	
	
	public ServerAddressConfig() {
		this.reset();
	}
	
	private void reset() {
		devServerDomain = null;
		devServerIpRange = null;
		prodServerIpRange = null;
		prodServerDomain = null;
	}

	public String getDevServerIpRange() {
		return devServerIpRange;
	}

	@XmlElement
	public void setDevServerIpRange(String devServerIpRange) {
		this.devServerIpRange = devServerIpRange;
	}

	public String getProdServerIpRange() {
		return prodServerIpRange;
	}

	@XmlElement
	public void setProdServerIpRange(String prodServerIpRange) {
		this.prodServerIpRange = prodServerIpRange;
	}

	public String getDevServerDomain() {
		return devServerDomain;
	}

	@XmlElement
	public void setDevServerDomain(String devServerDomain) {
		this.devServerDomain = devServerDomain;
	}

	public String getProdServerDomain() {
		return prodServerDomain;
	}

	@XmlElement
	public void setProdServerDomain(String prodServerDomain) {
		this.prodServerDomain = prodServerDomain;
	}
	
	
}
