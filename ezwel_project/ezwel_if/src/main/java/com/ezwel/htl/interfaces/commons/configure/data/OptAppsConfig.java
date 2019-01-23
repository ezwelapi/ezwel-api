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
	private OptMailConfig mailConfig;
	
	@APIFields(description = "FAX 설정")
	private OptFaxConfig faxConfig;
	
	public OptAppsConfig() {
		this.reset();
	}
	
	private void reset() {
		smsConfig = null;
		mailConfig = null;
		faxConfig = null;
	}

	public OptSmsConfig getSmsConfig() {
		return smsConfig;
	}

	@XmlElement(name = "smsConfig")
	public void setSmsConfig(OptSmsConfig smsConfig) {
		this.smsConfig = smsConfig;
	}
	
	public OptMailConfig getMailConfig() {
		return mailConfig;
	}

	@XmlElement
	public void setMailConfig(OptMailConfig mailConfig) {
		this.mailConfig = mailConfig;
	}

	public OptFaxConfig getFaxConfig() {
		return faxConfig;
	}

	@XmlElement
	public void setFaxConfig(OptFaxConfig faxConfig) {
		this.faxConfig = faxConfig;
	}
	
}
