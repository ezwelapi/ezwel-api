package com.ezwel.htl.interfaces.commons.configure.data;

import com.ezwel.htl.interfaces.commons.abstracts.APIObject;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel(description="FAX")
public class OptFaxConfig extends APIObject {
	
	@APIFields(description = "REST API URI")
	private String restURI;
	
	@APIFields(description = "데이터 인코딩")
	private String encoding;
	
	public OptFaxConfig() {
		this.reset();
	}
	
	private void reset() {
		restURI = null;
		encoding = null;
	}

	public String getRestURI() {
		return restURI;
	}

	public void setRestURI(String restURI) {
		this.restURI = restURI;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
}
