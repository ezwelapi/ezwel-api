package com.ezwel.htl.interfaces.server.commons.morpheme.cm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MorphemeUtil {

	private static final Logger logger = LoggerFactory.getLogger(MorphemeUtil.class);
	
	public static final String PATTERN_ENGLISH_SENTENCE;

	public static final String PATTERN_KOREAN_SENTENCE;
	
	public static final String PATTERN_CORP_SIGN;
	
	public static final String PATTERN_SPEC_CHAR;
	
	public static final String MORP_LANG_KO;
	
	public static final String MORP_LANG_EN;
	
	static {
		
		MORP_LANG_KO = "ko";
		
		MORP_LANG_EN = "en";
		
		// MorphemeUtil.PATTERN_CORP_SIGN
		PATTERN_CORP_SIGN = "\\(([	 주]+)\\)";
		
		// MorphemeUtil.PATTERN_KOREAN_SENTENCE
		PATTERN_KOREAN_SENTENCE = "([ㄱ-ㅎㅏ-ㅣ가-힣ァ-ンあ-ん一-齢𠮟\u2e80-\u2eff\u31c0-\u31ef\u3200-\u32ff\u3400-\u4dbf\u4e00-\u9fbf\uf900-\ufaff]+)";
		
		// MorphemeUtil.PATTERN_ENGLISH_SENTENCE
		PATTERN_ENGLISH_SENTENCE = "([a-zA-Z]+)";
		
		// MorphemeUtil.PATTERN_SPEC_CHAR
		PATTERN_SPEC_CHAR = "([\\p{S}\\p{P}º]+)";
	}

}
