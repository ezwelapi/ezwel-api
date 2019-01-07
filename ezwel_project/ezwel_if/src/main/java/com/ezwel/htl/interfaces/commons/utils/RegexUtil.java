package com.ezwel.htl.interfaces.commons.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;



@Component
@APIType(description="정규식(Regex) 핸들링 유틸")
public class RegexUtil {

	private static final Logger logger = LoggerFactory.getLogger(RegexUtil.class);

	public final static int DEFAULT_FLAGS; 
	
	static {
		DEFAULT_FLAGS = Pattern.CASE_INSENSITIVE | Pattern.DOTALL;
	}
	
	/**
	 * 주어진 패턴컴파일, Matcher 실행 및 Matcher 리턴
	 * @param contents
	 * @param pattern
	 * @return
	 */
	@APIOperation(description="주어진 패턴컴파일, Matcher 실행 및 Matcher 리턴합니다.")
	public Matcher match(String contents , String pattern, int flags) {
		String contentStr = APIUtil.NVL(contents);
		String patternStr = APIUtil.NVL(pattern);
		Pattern regex = null; 
		
		if(flags == -1) {
			regex = Pattern.compile(patternStr);
		}
		else {
			regex = Pattern.compile(patternStr, flags);
		}
		
		return regex.matcher(contentStr);
	}

	@APIOperation(description="문자열이 주어진 패턴이 존재하는지 채크합니다.")
	public boolean matcherPatternFind(String input, String pattern) {
		boolean findMatcher = false;
		//log.info(" *- matcherPatternFind -* input :  " + input + ", pattern : " + pattern);
		if(APIUtil.isNotEmpty(input) && APIUtil.isNotEmpty(pattern)) {
			Pattern regex = Pattern.compile(pattern);
			Matcher matcher = regex.matcher(input);
			findMatcher = matcher.find();
		}
		return findMatcher;
	}
	
	
	
	@APIOperation(description="바인드된 대상문자열 내에 패턴에 해당하는 부분이 있는지 체크하여 줍니다.")
	public boolean testPattern(String sentence, String pattern) {
		return testPattern(sentence, pattern, DEFAULT_FLAGS);
	}
	
	/**
	 * <pre>
	 * [메서드 설명]
	 * 바인드된 대상문자열 내에 패턴에 해당하는 부분이 있는지 체크하여 줍니다.
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @param sentence
	 * @param pattern
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 21.
	 */
	@APIOperation(description="바인드된 대상문자열 내에 패턴에 해당하는 부분이 있는지 체크하여 줍니다.")
	public boolean testPattern(String sentence, String pattern, int flags) {
		
		boolean findMatcher = false;
		if(APIUtil.isNotEmptyStringArray(new String[]{sentence, pattern})){
			Matcher matcher = match(sentence, pattern, flags);
			findMatcher = matcher.find();
		}
		
		return findMatcher;
	}


	/**
	 * 대상문자열중 패턴에 해당하는 문자열중 "" 이 아닌 값을 리스트에 담아 리턴하여줍니다.
	 * @param targetString : 대상문자열
	 * @param patternString : 패턴
	 * @return
	 */
	@APIOperation(description="대상문자열중 패턴에 해당하는 문자열중 \"\" 이 아닌 값을 리스트에 담아 리턴하여줍니다.")
	public List<String> findPatternToList(String targetString, String patternString, int flags){

		List<String> patternSet = new ArrayList<String>();

        String targetStr = targetString;
        String patternStr  = patternString;

        if(!APIUtil.isNotEmptyStringArray(new String[]{targetStr, patternStr})){
        	return patternSet;
        }

  		Matcher matcher = match(targetStr, patternStr, flags);

  		int count = 0;
  		String matchStr = "";
  		while(matcher.find()){
  			matchStr = matcher.group().trim();
  			if(!matchStr.equals("")){
  				//if(logger.isDebugEnabled()) {
				//    logger.debug(CommonUtil.mergeObjectString(new Object[]{" Match Count [", (count++), "] ", matchStr}));
		    	//}
			    patternSet.add(matchStr);
  			}
  		}
  		return patternSet;
	}
	
	/**
	 * 대상문자열중 패턴에 해당하는 문자를 switchString 으로 모두 치환하여 리턴하여 줍니다.
	 * @param sentenceString
	 * @param patternString
	 * @param switchString
	 * @return
	 */
	@APIOperation(description="대상문자열중 패턴에 해당하는 문자를 switchString 으로 모두 치환하여 리턴하여 줍니다.")
	public String replaceAllPattern(String sentenceString, String patternString, String switchString, int flags){

    	String sentenceStr = sentenceString;
    	String patternStr = patternString;
    	String switchStr = APIUtil.NVL(switchString);

        if(!APIUtil.isNotEmptyStringArray(new String[]{sentenceStr, patternStr})){
        	return sentenceStr;
        }else{
        	patternStr = "("+patternStr+")";
    		Matcher matcher = match(sentenceStr, patternStr, flags);
    		if(matcher.find()) {
    			sentenceStr = matcher.replaceAll(switchStr);
    		}
    	}

		return sentenceStr;
	}

	/**
	 * 대상문자열중 패턴에 해당하는 문자의 앞과 뒤를 postfixStr {patternString} prefixStr 형태로 모두 치환하여 리턴하여줍니다.
	 * @param sentenceStr
	 * @param patternStr
	 * @param postfixStr
	 * @param prefixStr
	 * @return
	 */
	@APIOperation(description="대상문자열중 패턴에 해당하는 문자의 앞과 뒤를 postfixStr {patternString} prefixStr 형태로 모두 치환하여 리턴하여줍니다.")
	public String replaceAllPatternWrap(String sentenceString, String patternString, String postfixString, String prefixString, int flags){

    	String sentenceStr = sentenceString;
    	String patternStr  = patternString;
    	String postfixStr  = postfixString;
    	String prefixStr   = prefixString;

        if(!APIUtil.isNotEmptyStringArray(new String[]{sentenceStr, patternStr, postfixStr, prefixStr})){
        	return sentenceStr;
        }else{
        	patternStr = "("+patternStr+")";
    		Matcher matcher = match(sentenceStr, patternStr, flags);
    		if(matcher.find()) {
    			sentenceStr = matcher.replaceAll(postfixStr+"$1"+prefixStr);
    		}
    	}

		return sentenceStr;
	}

	
}
