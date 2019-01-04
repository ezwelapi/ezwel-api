package com.ezwel.htl.interfaces.commons.configure.data;

import com.ezwel.htl.interfaces.commons.abstracts.APIObject;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel(description="EMAIL")
public class OptEmailConfig extends APIObject {
		
	@APIFields(description = "EMAIL host")
	private String host;
	
	@APIFields(description = "EMAIL port")
	private String port;
	
	@APIFields(description = "EMAIL auth")
	private String auth;
	
	@APIFields(description = "EMAIL username")
	private String username;
	
	@APIFields(description = "EMAIL password")
	private String password;
	
	@APIFields(description = "EMAIL connTimeout")
	private String connTimeout;
	
	@APIFields(description = "EMAIL readTimeout")
	private String readTimeout;
	
	public OptEmailConfig() {
		this.reset();
	}
	
	private void reset() {
		host = null;
		port = null;
		auth = null;
		username = null;
		password = null;
		connTimeout = null;
		readTimeout = null;
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

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
