package com.ezwel.htl.interfaces.server.commons.morpheme.cm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MorphemeUtil {

	private static final Logger logger = LoggerFactory.getLogger(MorphemeUtil.class);
	
	public static final String PATTERN_ENGLISH_SENTENCE;

	public static final String PATTERN_KOREAN_SENTENCE;
	
	static {
		
		PATTERN_KOREAN_SENTENCE = "([\\p{S}\\p{P}ºㄱ-ㅎㅏ-ㅣ가-힣ァ-ンあ-ん一-齢𠮟\u2e80-\u2eff\u31c0-\u31ef\u3200-\u32ff\u3400-\u4dbf\u4e00-\u9fbf\uf900-\ufaff]+)";
		
		PATTERN_ENGLISH_SENTENCE = "([\\p{S}\\p{P}ºa-zA-Z]+)";
	}
	
}
