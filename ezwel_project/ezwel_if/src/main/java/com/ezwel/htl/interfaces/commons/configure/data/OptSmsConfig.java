package com.ezwel.htl.interfaces.commons.configure.data;

import javax.xml.bind.annotation.XmlElement;

import com.ezwel.htl.interfaces.commons.abstracts.APIObject;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel(description="SMS/MMS/KAKAO")
public class OptSmsConfig extends APIObject {
	
	@APIFields(description = "REST API URI")
	private String restURI;
	
	@APIFields(description = "데이터 인코딩")
	private String encoding;
	
	@APIFields(description = "SMS 서버 API URI")
	private String smsURI;
	
	public OptSmsConfig() {
		this.reset();
	}
	
	private void reset() {
		restURI = null;
		encoding = null;
		smsURI = null;
	}

	public String getRestURI() {
		return restURI;
	}

	@XmlElement
	public void setRestURI(String restURI) {
		this.restURI = restURI;
	}

	public String getEncoding() {
		return encoding;
	}

	@XmlElement
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getSmsURI() {
		return smsURI;
	}

	public void setSmsURI(String smsURI) {
		this.smsURI = smsURI;
	}
	
}
