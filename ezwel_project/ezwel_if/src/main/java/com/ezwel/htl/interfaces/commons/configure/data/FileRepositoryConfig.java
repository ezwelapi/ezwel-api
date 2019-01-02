package com.ezwel.htl.interfaces.commons.configure.data;

import javax.xml.bind.annotation.XmlElement;

import com.ezwel.htl.interfaces.commons.abstracts.APIObject;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel(description="파일 저장소 설정")
public class FileRepositoryConfig extends APIObject {
	
	@APIFields(description="숙박 건물 이미지 저장 경로")
	private BuildImageConfig buildImage;

	@APIFields(description="배치 수행중 발생하는 로그 적제 경로(인터페이스 배치 전용)")
	private ErrorLogPathConfig errorLog;
	
	public FileRepositoryConfig() {
		this.reset();
	}
	
	private void reset() {
		buildImage = null;
		errorLog = null;
	}

	public BuildImageConfig getBuildImage() {
		return buildImage;
	}

	@XmlElement
	public void setBuildImage(BuildImageConfig buildImage) {
		this.buildImage = buildImage;
	}

	public ErrorLogPathConfig getErrorLog() {
		return errorLog;
	}

	@XmlElement
	public void setErrorLog(ErrorLogPathConfig errorLog) {
		this.errorLog = errorLog;
	}
}
