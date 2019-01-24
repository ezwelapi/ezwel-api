package com.ezwel.htl.interfaces.commons.configure.data;

import javax.xml.bind.annotation.XmlElement;

import com.ezwel.htl.interfaces.commons.abstracts.APIObject;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel(description="숙박 건물 이미지 저장 경로")
public class ApiReportRecevConfig extends APIObject {

	@APIFields(description="수신자")
	private String name;

	@APIFields(description="이메일 주소")
	private String emailAddr;
	
	
	public ApiReportRecevConfig() {
		this.reset();
	}
	
	private void reset() {
		name = null;
		emailAddr = null;
	}

	public String getName() {
		return name;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	@XmlElement
	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}
}
