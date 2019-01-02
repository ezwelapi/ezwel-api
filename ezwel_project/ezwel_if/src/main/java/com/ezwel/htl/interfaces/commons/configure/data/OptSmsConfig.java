package com.ezwel.htl.interfaces.commons.configure.data;

import javax.xml.bind.annotation.XmlElement;

import com.ezwel.htl.interfaces.commons.abstracts.APIObject;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel(description="SMS/MMS/KAKAO")
public class OptSmsConfig extends APIObject {
	
	@APIFields(description = "SMS 서버 REST API URI")
	private String restURI;
	
	@APIFields(description = "SMS 데이터 인코딩")
	private String encoding;
	
	public OptSmsConfig() {
		this.reset();
	}
	
	private void reset() {
		restURI = null;
		encoding = null;
	}

	public String getRestURI() {
		return restURI;
	}

	@XmlElement
	public void setRestURI(String restURI) {
		this.restURI = restURI;
	}

	
}
