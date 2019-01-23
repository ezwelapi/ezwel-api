package com.ezwel.htl.interfaces.commons.configure.data;

import com.ezwel.htl.interfaces.commons.abstracts.APIObject;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel(description="EMAIL")
public class OptMailConfig extends APIObject {
		
	@APIFields(description = "SMS 서버 REST API URI")
	private String restURI;
	
	@APIFields(description = "SMS 데이터 인코딩")
	private String encoding;
	
	@APIFields(description = "메일호스트")
	private String host;
	
	@APIFields(description = "메일포트")
	private String port;
	
	@APIFields(description = "발신자메일주소")
	private String from;
	
	@APIFields(description = "발신자명")
	private String fromName;
	
	@APIFields(description = "메일인증사용자명")
	private String userName;
	
	@APIFields(description = "메일인증패스워드")
	private String passWord;
	
	@APIFields(description = "connTimeout")
	private String connTimeout;
	
	@APIFields(description = "readTimeout")
	private String readTimeout;
	
	public OptMailConfig() {
		this.reset();
	}
	
	private void reset() {
		restURI = null;
		encoding = null;
		host = null;
		port = null;
		from = null;
		fromName = null;
		userName = null;
		passWord = null;
		connTimeout = null;
		readTimeout = null;
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

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getConnTimeout() {
		return connTimeout;
	}

	public void setConnTimeout(String connTimeout) {
		this.connTimeout = connTimeout;
	}

	public String getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(String readTimeout) {
		this.readTimeout = readTimeout;
	}
	
}
