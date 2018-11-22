package com.ezwel.htl.interfaces.http.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel
public class OutputDTO extends AbstractSDO {

	private List<OutputDTOSub01> menuList = null;

	private String code;
	private String message;

	public List<OutputDTOSub01> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<OutputDTOSub01> menuList) {
		this.menuList = menuList;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
