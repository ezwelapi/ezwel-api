package com.ezwel.htl.interfaces.server.commons.sdo;

import java.util.List;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel
public class MorphemeSDO extends AbstractSDO {


	@APIFields(description = "단어/문장 목록", required=true)
	private List<String> sentenceList;
	
	@APIFields(description = "한글 형태소 분석 결과 목록")
	private List<List<String>> korMorphemeList;

	@APIFields(description = "영문 형태소 분석 결과 목록")
	private List<List<String>> engMorphemeList;
	
	public List<String> getSentenceList() {
		return sentenceList;
	}

	public void setSentenceList(List<String> sentenceList) {
		this.sentenceList = sentenceList;
	}

	public List<List<String>> getKorMorphemeList() {
		return korMorphemeList;
	}

	public void setKorMorphemeList(List<List<String>> korMorphemeList) {
		this.korMorphemeList = korMorphemeList;
	}

	public List<List<String>> getEngMorphemeList() {
		return engMorphemeList;
	}

	public void setEngMorphemeList(List<List<String>> engMorphemeList) {
		this.engMorphemeList = engMorphemeList;
	}



	
}
