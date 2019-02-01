package com.ezwel.htl.interfaces.commons.configure.data;

import javax.xml.bind.annotation.XmlElement;

import com.ezwel.htl.interfaces.commons.abstracts.APIObject;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel(description="파일 저장소 설정")
public class FileRepositoryConfig extends APIObject {
	
	@APIFields(description="숙박 건물 이미지 저장 경로")
	private BuildImageConfig buildImage;

	
	public FileRepositoryConfig() {
		this.reset();
	}
	
	private void reset() {
		buildImage = null;
	}

	public BuildImageConfig getBuildImage() {
		return buildImage;
	}

	@XmlElement
	public void setBuildImage(BuildImageConfig buildImage) {
		this.buildImage = buildImage;
	}
}
