package com.ezwel.htl.interfaces.server.commons.morpheme.en.data;

import com.ezwel.htl.interfaces.commons.abstracts.APIObject;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

/**
* <p>Title: EnglishAnalayzerSDO</p>
* <p>Description: 분석 검증 데이터 DTO
*
* </p>
* <p>Copyright: Copyright (c) 2012</p>
* <p>Company: </p>
* @since
* @author ksw
* @version 1.0
*/
@APIModel
public class EnglishAnalayzerSDO extends APIObject {

	@APIFields
    private String frontWord;
    
	@APIFields
	private String backEndWord;

	@APIFields
	private String originalFrontWord;

	@APIFields
	private int	 frontWordOrder;

	@APIFields
	private String originalBackEndWord;

	@APIFields
	private int	 backEndWordOrder;

	@APIFields
	private String confirmType; // similar/equals/failed

	@APIFields
	private String analyzer;

	@APIFields
	private String morpheme;

	@APIFields
	private String originalMorpheme;

	@APIFields
	private int  difference;

	@APIFields
	private String startTag;

	@APIFields
	private String endTag;

	@APIFields
	private int	percent;

	public EnglishAnalayzerSDO(){
    	this.reset();
    }

    private void reset(){
    	 frontWord = "";
    	 backEndWord = "";
    	 originalFrontWord = "";
    	 frontWordOrder = 0;
    	 originalBackEndWord = "";
    	 backEndWordOrder = 0;
    	 confirmType = ""; // similar/equals/failed
    	 analyzer = "";
    	 morpheme = "";
    	 originalMorpheme = "";
    	 difference = 0;
    	 startTag = "";
    	 endTag = "";
    	 percent=0;
    }
    public String getFrontWord() {
		return frontWord;
	}

	public void setFrontWord(String frontWord) {
		this.frontWord = frontWord;
	}

	public String getBackEndWord() {
		return backEndWord;
	}

	public void setBackEndWord(String backEndWord) {
		this.backEndWord = backEndWord;
	}

	public String getOriginalFrontWord() {
		return originalFrontWord;
	}

	public void setOriginalFrontWord(String originalFrontWord) {
		this.originalFrontWord = originalFrontWord;
	}

	public String getOriginalBackEndWord() {
		return originalBackEndWord;
	}

	public void setOriginalBackEndWord(String originalBackEndWord) {
		this.originalBackEndWord = originalBackEndWord;
	}

	public String getConfirmType() {
		return confirmType;
	}

	public void setConfirmType(String confirmType) {
		this.confirmType = confirmType;
	}

	public String getAnalyzer() {
		return analyzer;
	}

	public void setAnalyzer(String analyzer) {
		this.analyzer = analyzer;
	}

	public String getMorpheme() {
		return morpheme;
	}

	public void setMorpheme(String morpheme) {
		this.morpheme = morpheme;
	}

	public int getDifference() {
		return difference;
	}

	public void setDifference(int difference) {
		this.difference = difference;
	}

	public String getOriginalMorpheme() {
		return originalMorpheme;
	}

	public void setOriginalMorpheme(String originalMorpheme) {
		this.originalMorpheme = originalMorpheme;
	}

	public int getFrontWordOrder() {
		return frontWordOrder;
	}

	public void setFrontWordOrder(int frontWordOrder) {
		this.frontWordOrder = frontWordOrder;
	}

	public int getBackEndWordOrder() {
		return backEndWordOrder;
	}

	public void setBackEndWordOrder(int backEndWordOrder) {
		this.backEndWordOrder = backEndWordOrder;
	}

	public String getStartTag() {
		return startTag;
	}

	public void setStartTag(String startTag) {
		this.startTag = startTag;
	}

	public String getEndTag() {
		return endTag;
	}

	public void setEndTag(String endTag) {
		this.endTag = endTag;
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}



}

