package com.ezwel.htl.interfaces.http.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel
public class InputDTO extends AbstractSDO {

	private String key01;
	private List<InputDTOSub01> key02 = null;
	private String key03;
	private InputDTOSub02 key04;
	private String key05;

	public String getKey01() {
		return key01;
	}

	public void setKey01(String key01) {
		this.key01 = key01;
	}

	public List<InputDTOSub01> getKey02() {
		return key02;
	}

	public void setKey02(List<InputDTOSub01> key02) {
		this.key02 = key02;
	}

	public String getKey03() {
		return key03;
	}

	public void setKey03(String key03) {
		this.key03 = key03;
	}

	public InputDTOSub02 getKey04() {
		return key04;
	}

	public void setKey04(InputDTOSub02 key04) {
		this.key04 = key04;
	}

	public String getKey05() {
		return key05;
	}

	public void setKey05(String key05) {
		this.key05 = key05;
	}
}
