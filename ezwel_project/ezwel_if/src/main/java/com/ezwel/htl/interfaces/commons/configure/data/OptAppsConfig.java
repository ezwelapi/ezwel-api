package com.ezwel.htl.interfaces.commons.configure.data;

import javax.xml.bind.annotation.XmlElement;

import com.ezwel.htl.interfaces.commons.abstracts.APIObject;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel(description="인터페이스 부가기능(fax/mail/sms)")
public class OptAppsConfig extends APIObject {
	
	@APIFields(description = "SMS 설정")
	private OptSmsConfig smsConfig;

	@APIFields(description = "EMAIL 설정")
	private OptEmailConfig emailConfig;
	
	@APIFields(description = "FAX 설정")
	private OptFaxConfig faxConfig;
	
	public OptAppsConfig() {
		this.reset();
	}
	
	private void reset() {
		smsConfig = null;
		emailConfig = null;
		faxConfig = null;
	}

	public OptSmsConfig getSmsConfig() {
		return smsConfig;
	}

	@XmlElement
	public void setSmsConfig(OptSmsConfig smsConfig) {
		this.smsConfig = smsConfig;
	}

	public OptEmailConfig getEmailConfig() {
		return emailConfig;
	}

	@XmlElement
	public void setEmailConfig(OptEmailConfig emailConfig) {
		this.emailConfig = emailConfig;
	}

	public OptFaxConfig getFaxConfig() {
		return faxConfig;
	}

	@XmlElement
	public void setFaxConfig(OptFaxConfig faxConfig) {
		this.faxConfig = faxConfig;
	}
	
	
}
