package com.ezwel.htl.interfaces.http.dto;

import java.util.HashMap;
import java.util.Map;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractDTO;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel
public class OutputDTOSub01 extends AbstractDTO {

	private String menuCd;
	private String menuCdPrnt;
	private String menuNm;
	private String menuDesc;
	private String menuIcon;
	private String cdMenuType;
	private String cdEvntType;
	private Integer sortOrdr;
	private String moduUrl;
	private String moduParm;
	private Integer level;

	public String getMenuCd() {
		return menuCd;
	}

	public void setMenuCd(String menuCd) {
		this.menuCd = menuCd;
	}

	public String getMenuCdPrnt() {
		return menuCdPrnt;
	}

	public void setMenuCdPrnt(String menuCdPrnt) {
		this.menuCdPrnt = menuCdPrnt;
	}

	public String getMenuNm() {
		return menuNm;
	}

	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}

	public String getMenuDesc() {
		return menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public String getCdMenuType() {
		return cdMenuType;
	}

	public void setCdMenuType(String cdMenuType) {
		this.cdMenuType = cdMenuType;
	}

	public String getCdEvntType() {
		return cdEvntType;
	}

	public void setCdEvntType(String cdEvntType) {
		this.cdEvntType = cdEvntType;
	}

	public Integer getSortOrdr() {
		return sortOrdr;
	}

	public void setSortOrdr(Integer sortOrdr) {
		this.sortOrdr = sortOrdr;
	}

	public String getModuUrl() {
		return moduUrl;
	}

	public void setModuUrl(String moduUrl) {
		this.moduUrl = moduUrl;
	}

	public String getModuParm() {
		return moduParm;
	}

	public void setModuParm(String moduParm) {
		this.moduParm = moduParm;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

}
