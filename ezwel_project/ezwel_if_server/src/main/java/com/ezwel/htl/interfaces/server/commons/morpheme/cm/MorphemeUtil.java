package com.ezwel.htl.interfaces.server.commons.morpheme.cm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public static final Map<String, String[]> PRONUN_SYMBOL_MAP;
	
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
		
		PRONUN_SYMBOL_MAP = new HashMap<String, String[]>();
		PRONUN_SYMBOL_MAP.put("A", new String[] {"에이"});
		PRONUN_SYMBOL_MAP.put("B", new String[] {"비이", "비"});
		PRONUN_SYMBOL_MAP.put("C", new String[] {"씨이", "씨"});
		PRONUN_SYMBOL_MAP.put("D", new String[] {"디이", "디"});
		PRONUN_SYMBOL_MAP.put("E", new String[] {"이이", "이"});
		PRONUN_SYMBOL_MAP.put("F", new String[] {"에프",});
		PRONUN_SYMBOL_MAP.put("G", new String[] {"쥐이", "쥐"});
		PRONUN_SYMBOL_MAP.put("H", new String[] {"에이취"});
		PRONUN_SYMBOL_MAP.put("I", new String[] {"아이"});
		PRONUN_SYMBOL_MAP.put("J", new String[] {"줴이", "제이"});
		PRONUN_SYMBOL_MAP.put("K", new String[] {"케이"});
		PRONUN_SYMBOL_MAP.put("L", new String[] {"엘"});
		PRONUN_SYMBOL_MAP.put("M", new String[] {"엠"});
		PRONUN_SYMBOL_MAP.put("N", new String[] {"엔"});
		PRONUN_SYMBOL_MAP.put("O", new String[] {"오우", "오"});
		PRONUN_SYMBOL_MAP.put("P", new String[] {"피이", "피"});
		PRONUN_SYMBOL_MAP.put("Q", new String[] {"큐우", "큐"});
		PRONUN_SYMBOL_MAP.put("R", new String[] {"알"});
		PRONUN_SYMBOL_MAP.put("S", new String[] {"에쓰", "에스"});
		PRONUN_SYMBOL_MAP.put("T", new String[] {"티이", "티"});
		PRONUN_SYMBOL_MAP.put("U", new String[] {"유우", "유"});
		PRONUN_SYMBOL_MAP.put("V", new String[] {"뷔이", "브이"});
		PRONUN_SYMBOL_MAP.put("W", new String[] {"더블유", "떠블유"});
		PRONUN_SYMBOL_MAP.put("X", new String[] {"엑쓰", "엑스"});
		PRONUN_SYMBOL_MAP.put("Y", new String[] {"와이"});
		PRONUN_SYMBOL_MAP.put("Z", new String[] {"즤", "제트"});
	}

}
