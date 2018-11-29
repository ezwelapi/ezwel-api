package com.ezwel.htl.interfaces.server.commons.utils.data;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel(description="파일 다운로드 데이터")
public class ImageSDO  extends AbstractSDO {
	
	@APIFields(description="파일 확장자")
	private String fileExt;
	
	@APIFields(description="변환 파일 명")
	private String chngFileName;
	
	@APIFields(description="원본 파일 명")
	private String orgFileName;
	
	@APIFields(description="저장된 파일 일반 경로")
	private String canonicalPath;

	@APIFields(description="이미지 URL")
	private String imageURL;
	
	@APIFields(description="저장 여부")
	private boolean isSave;
	
	public ImageSDO() {
		this.reset();	
	}
	
	private void reset() {
		fileExt = "";
		chngFileName = "";
		orgFileName = "";
		canonicalPath = "";	
		imageURL = "";
		isSave = false;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public String getChngFileName() {
		return chngFileName;
	}

	public void setChngFileName(String chngFileName) {
		this.chngFileName = chngFileName;
	}

	public String getOrgFileName() {
		return orgFileName;
	}

	public void setOrgFileName(String orgFileName) {
		this.orgFileName = orgFileName;
	}

	public String getCanonicalPath() {
		return canonicalPath;
	}

	public void setCanonicalPath(String canonicalPath) {
		this.canonicalPath = canonicalPath;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public boolean isSave() {
		return isSave;
	}

	public void setSave(boolean isSave) {
		this.isSave = isSave;
	}
	
	
}
