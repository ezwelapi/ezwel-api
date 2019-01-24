package com.ezwel.htl.interfaces.commons.configure.data;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import com.ezwel.htl.interfaces.commons.abstracts.APIObject;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel(description="API 인터페이스/배치 레포트")
public class ApiReportConfig extends APIObject {
	
	@APIFields(description = "레포트 수신자 목록")
	private List<ApiReportRecevConfig> receiverList;

	public ApiReportConfig() {
		this.reset();
	}
	
	private void reset() {
		receiverList = null;
	}

	public List<ApiReportRecevConfig> getReceiverList() {
		return receiverList;
	}

	@XmlElementWrapper(name = "receiverList")
	@XmlElement(name = "receiver")
	public void setReceiverList(List<ApiReportRecevConfig> receiverList) {
		this.receiverList = receiverList;
	}

	
}
