package com.ezwel.htl.interfaces.commons.configure.data;

import com.ezwel.htl.interfaces.commons.abstracts.APIObject;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel(description="EMAIL")
public class OptEmailConfig extends APIObject {
		
	@APIFields(description = "EMAIL HOST")
	private String host;
	
	@APIFields(description = "EMAIL AUTH")
	private String auth;
	
	@APIFields(description = "EMAIL USERNAME")
	private String username;
	
	@APIFields(description = "EMAIL PASSWORD")
	private String password;
	
	@APIFields(description = "EMAIL connectiontimeout")
	private String connectiontimeout;
	
	@APIFields(description = "EMAIL timeout")
	private String timeout;
	
	public OptEmailConfig() {
		this.reset();
	}
	
	private void reset() {
		host = null;
		auth = null;
		username = null;
		password = null;
		connectiontimeout = null;
		timeout = null;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
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

	public String getConnectiontimeout() {
		return connectiontimeout;
	}

	public void setConnectiontimeout(String connectiontimeout) {
		this.connectiontimeout = connectiontimeout;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}
	
}
