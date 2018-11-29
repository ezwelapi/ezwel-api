package com.ezwel.htl.interfaces.commons.configure.data;

import javax.xml.bind.annotation.XmlElement;

import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel(description="파일 저장소 설정")
public class FileRepository {
	
	@APIFields(description="숙박 건물 이미지 루트 경로")
	private String lodgeBuildingImageRootPath;
	
	public FileRepository() {
		this.reset();
	}
	
	private void reset() {
		lodgeBuildingImageRootPath = null;
	}

	public String getLodgeBuildingImageRootPath() {
		return lodgeBuildingImageRootPath;
	}

	@XmlElement
	public void setLodgeBuildingImageRootPath(String lodgeBuildingImageRootPath) {
		this.lodgeBuildingImageRootPath = lodgeBuildingImageRootPath;
	}
	
	
}
