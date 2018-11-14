package com.ezwel.htl.interfaces.http.dto;

import java.util.HashMap;
import java.util.Map;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel
public class InputDTOSub01 extends AbstractEntity {

	private String key02Sub01;
	private String key02Sub02;
	private String key02Sub03;

	public String getKey02Sub01() {
		return key02Sub01;
	}

	public void setKey02Sub01(String key02Sub01) {
		this.key02Sub01 = key02Sub01;
	}

	public String getKey02Sub02() {
		return key02Sub02;
	}

	public void setKey02Sub02(String key02Sub02) {
		this.key02Sub02 = key02Sub02;
	}

	public String getKey02Sub03() {
		return key02Sub03;
	}

	public void setKey02Sub03(String key02Sub03) {
		this.key02Sub03 = key02Sub03;
	}

}
