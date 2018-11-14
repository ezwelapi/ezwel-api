package com.ezwel.htl.interfaces.http.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel
public class OutputDTO extends AbstractEntity {

	private List<OutputDTOSub01> menuList = null;

	public List<OutputDTOSub01> getMenuList() {
	return menuList;
	}

	public void setMenuList(List<OutputDTOSub01> menuList) {
	this.menuList = menuList;
	}
	
}
