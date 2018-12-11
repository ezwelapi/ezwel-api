package com.ezwel.htl.interfaces.server.commons.sdo;

import java.util.List;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel
public class MorphemeSDO extends AbstractSDO {

	private static final long serialVersionUID = 1L;

	@APIFields(description = "단어/문장 목록", required=true, maxLength=4)
	private List<String> sentenceList;
	
	@APIFields(description = "형태소 분석 결과 목록", maxLength=100)
	private List<String> morphemeList;

	public List<String> getSentenceList() {
		return sentenceList;
	}

	public void setSentenceList(List<String> sentenceList) {
		this.sentenceList = sentenceList;
	}

	public List<String> getMorphemeList() {
		return morphemeList;
	}

	public void setMorphemeList(List<String> morphemeList) {
		this.morphemeList = morphemeList;
	}


	
}
