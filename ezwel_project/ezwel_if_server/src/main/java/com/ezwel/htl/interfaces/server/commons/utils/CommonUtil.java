package com.ezwel.htl.interfaces.server.commons.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.HttpInterfaceExecutor;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigSDO;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.commons.utils.RegexUtil;
import com.ezwel.htl.interfaces.commons.utils.StackTraceUtil;
import com.ezwel.htl.interfaces.server.commons.morpheme.cm.MorphemeUtil;
import com.ezwel.htl.interfaces.server.commons.morpheme.en.EnglishAnalyzers;
import com.ezwel.htl.interfaces.server.commons.morpheme.ko.KoreanAnalyzers;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.entities.EzcDetailCd;
import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;
/**
 * <pre>
 * if server 공통 유틸
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 21.
 */
@APIType(description="IF 서버 공통 유틸")
@Component
public class CommonUtil {

	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	
	private KoreanAnalyzers koreanAnalyzers;
	
	private EnglishAnalyzers englishAnalyzers;
	
	private RegexUtil regexUtil;
	
	private APIUtil apiUtil;
	
	public final static int DEFAULT_FLAGS; 
	
	static {
		DEFAULT_FLAGS = Pattern.CASE_INSENSITIVE | Pattern.DOTALL;
	}
	
	@APIOperation(description="요청 파라메터 바디를 BufferReader를 이용하여 읽고 내용을 문자열로 리턴합니다.", isExecTest=true)
	public String readReqeustBodyWithBufferedReader() {
		return readReqeustBodyWithBufferedReader(null);
	}
	
	@APIOperation(description="요청 파라메터 바디를 BufferReader를 이용하여 읽고 내용을 문자열로 리턴합니다.", isExecTest=true)
    public String readReqeustBodyWithBufferedReader(HttpServletRequest request) {
    	
		HttpServletRequest currentRequest = null;
		if(request == null) {
			currentRequest = LApplicationContext.getRequest();
		}
		else {
			currentRequest = request; 
		}
    	StringBuilder builder = null;
    	
    	
    	try {

    		if(currentRequest.getMethod().equals(OperateConstants.HTTP_METHOD_POST)) {
    			
    			logger.debug("[InputStream] : {}", currentRequest.getInputStream());
        		if(currentRequest.getInputStream() != null) {
        			
        			builder = new StringBuilder();
        			//BufferedReader input = currentRequest.getReader();
        			BufferedReader input = new BufferedReader(new InputStreamReader(currentRequest.getInputStream(), OperateConstants.DEFAULT_ENCODING));
        			String buffer = null;
        			while ((buffer = input.readLine()) != null) {
        				//if (builder.length() > 0) {
        				//	builder.append(OperateConstants.LINE_SEPARATOR);
        				//}
        				builder.append(buffer);
        			}
        		}	
        	}
    		else if(currentRequest.getMethod().equals(OperateConstants.HTTP_METHOD_GET)) {
    		    			
    		}
    		
		} catch (IOException e) {
			throw new APIException(e);
		}
		return (builder != null ? builder.toString().trim() : null);
    }
 
    
	@APIOperation(description="요청 파라메터 바디를 ByteStreams를 이용하여 읽고 내용을 문자열로 리턴합니다.", isExecTest=true)
    public String readReqestBodyWithByteStreams(HttpServletRequest request) {
    	String out = null;
    	try {
    		if(request.getInputStream() != null) {
    			out = ByteSource.wrap(ByteStreams.toByteArray(request.getInputStream())).asCharSource(Charsets.UTF_8).read();
    		}
		} catch (IOException e) {
			throw new APIException(e);
		}
    	return out;
    }
    	

	
	/**
	 * <pre>
	 * [메서드 설명]
	 * HttpServletRequest의 content-type을 리턴
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @param request
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 20.
	 */
	@APIOperation(description="HttpServletRequest의 content-type을 리턴합니다.", isExecTest=true)
	public String getRequestContentType(HttpServletRequest request) {
		
		String out = null;
		
		String contentType = APIUtil.NVL(request.getContentType(),"").toLowerCase();
		
		if(contentType.indexOf("multipart/form-data; boundary=") > -1) {
			out = contentType.substring(0, OperateConstants.CONTENT_TYPE_MULTIPART_FORM_DATA.length());
		}
		else {
			out = contentType;
		}
		return out;
	}    
    

	/**
	 * <pre>
	 * [메서드 설명]
	 * 	클라이언트 IP를 리턴합니다.
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @param request
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 14.
	 */
	@APIOperation(description="클라이언트 IP를 리턴합니다.", isExecTest=true)
	public String getClientAddress(HttpServletRequest request) {
		
		String clientAddress  = request.getHeader("X-FORWARDED-FOR"); 
		if(clientAddress == null) 
		{ 
			clientAddress = request.getRemoteAddr(); 
		} 
		
		return clientAddress;
	}
	

	/**
	 * <pre>
	 * [메서드 설명]
	 * 	바인드된 객체의 타입@메소드 문자열을 리턴합니다.
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @param handler
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 14.
	 */
	@APIOperation(description="바인드된 객체의 타입@메소드 문자열을 리턴합니다.", isExecTest=true)
	public String getMethodInfo(Object handler){
		String out = null;
		
		if(getControllerType(handler).equals(OperateConstants.SPRING_CONTROLLER)) {
			HandlerMethod method = (HandlerMethod) handler;
			out = method.getBeanType().getCanonicalName().concat("@").concat(method.getMethod().getName());
		}
		else {
			out = handler.getClass().getCanonicalName();
		}
		
		return out;
	}

	/**
	 * <pre>
	 * [메서드 설명]
	 * 	handler 타입을 리턴 (HandlerMethod 또는 DefaultServlet를 리턴)
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @param handler
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 14.
	 */
	@APIOperation(description="handler 타입을 리턴 (HandlerMethod 또는 DefaultServlet를 리턴)", isExecTest=true)
	public String getControllerType(Object handlerParam){
		String out = null;
		Object handler = handlerParam;
		if(handler instanceof HandlerMethod){ 
			out = OperateConstants.SPRING_CONTROLLER;
		}
		else {
			out = OperateConstants.DEFAULT_SERVLET;
		}
		
		return out;
	}
	

	
	/**
	 * <pre>
	 * [메서드 설명]
	 * 	바인드된 객체가 HandlerMethod일 경우 HandlerMethod로 캐스팅하여 리턴합니다.
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @param handler
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 14.
	 */
	@APIOperation(description="바인드된 객체가 HandlerMethod일 경우 HandlerMethod로 캐스팅하여 리턴합니다.", isExecTest=true)
	public HandlerMethod getHandlerMethod(Object handler){
		HandlerMethod method = null;
		if(handler instanceof HandlerMethod){ 
			method = (HandlerMethod) handler;
		}
		return method;
	}
	
	
	@APIOperation(description="문자열의 첫번째 문자를 소문자로 변환하여 리턴합니다.", isExecTest=true)
	public static String getFirstCharLowerCase(String strWord) {
		
		String out = null;
		
		String word = APIUtil.NVL(strWord, "").trim();
		if(word.length() > 1) {
			out = word.substring(0,1).toLowerCase() + word.substring(1);
		}
		else {
			out = word.toLowerCase();
		}
		
		return out;
	}
	
	
	/**
	 * 컨트롤러 응답결과 테스트용 유틸
	 * @param in
	 * @param response
	 */
	@APIOperation(description="컨트롤러 응답결과 테스트용 유틸 response header 정보 세팅", isExecTest=true)
	public void setResponseHeader(HttpConfigSDO in, HttpServletResponse response) {
		if(in == null) {
			throw new APIException("에이전트 정보가 존재하지 않습니다. ");
		}
		logger.debug("[컨트롤러 응답결과 테스트용 유틸] in {}", in);
		
		Properties certifications = null;
		
		try {
			
			//Certifications Property
			certifications = new HttpInterfaceExecutor().getCert(in);
			
			if(in.getRequestProperty() == null) in.setRequestProperty(new Properties());
			in.getRequestProperty().putAll(certifications);
			
			for(Entry<Object, Object> entry : in.getRequestProperty().entrySet()) {
				response.setHeader((String) entry.getKey(), (String) entry.getValue());
			}
			
			/** 강제 유지 프로퍼티 */
			response.setHeader("Content-Type", APIUtil.addString("application/json; charset=", in.getEncoding()));
			response.setHeader("Accept", "application/json");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Content-Length", "1" );
			
		} catch(APIException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9000, "■ 인터페이스 요청 헤더 작성중 장애발생.", e);
		} finally {
			if(certifications != null) {
				certifications.clear();
			}
			if(in.getRequestProperty() != null) {
				in.getRequestProperty().clear();
			}			
		}
	}
	
	
    @APIOperation(description="공통코드 목록에서 상세 코드에 해당하는 레코드를 리턴합니다.")
	public EzcDetailCd getEzcDetailCdForCodeList(List<EzcDetailCd> detailCdList, String detailCd) {
		EzcDetailCd out = null;
		
		if(detailCdList != null) {
			for(EzcDetailCd detailCdItem : detailCdList) {
				if(detailCdItem != null && detailCdItem.getDetailCd().equals(detailCd)) {
					out = detailCdItem;
					break;
				}
			}
		}
		
		return out;
	}
    
    @APIOperation(description="공통코드 목록에서 상세 코드에 해당하는 마스터 코드문자열을 리턴합니다.")
	public String getMasterCdForCodeList(List<EzcDetailCd> detailCdList, String detailCd) {
		String out = null;
		
		EzcDetailCd outEzcDetailCd = getEzcDetailCdForCodeList(detailCdList, detailCd);
		if(outEzcDetailCd != null) {
			out = outEzcDetailCd.getMasterCd();
		}
		return out;
	}

	
    @APIOperation
	public static boolean existsClass(String... classType) {
		boolean out = false;
		try {
			String[] clazzTypes = classType;
			if( clazzTypes != null ) {
				for(String clazzType : clazzTypes) {
					Class.forName(clazzType);
				}
				out = true;
			}
		} catch (ClassNotFoundException e) {
			if(logger.isDebugEnabled()) {
				logger.debug("ClassNotFound : ".concat(e.getMessage()));
			}
		}
		
		return out;
	}
	
    @APIOperation
    public static String byteSubstring(String str, int sPoint, String encoding) {
    	return byteSubstring(str, sPoint, -1, encoding);
    }
    
    @APIOperation
	public static String byteSubstring(String userSentence, int userStartPoint, int userEndPoint, String userEncoding) {
	    String out = null;

	    try {
	    	String sentence = userSentence; 
	    	int startPoint = userStartPoint;
	    	int endPoint = userEndPoint;
	    	String encoding = userEncoding;
	    	
			byte[] bytes = sentence.getBytes(encoding);

	    	if(endPoint < 0) {
	    		endPoint = bytes.length - startPoint;
	    	}
	    	
			byte[] value = new byte[endPoint];
			
			if(bytes.length < startPoint + endPoint){
				logger.warn("[VALIDATE] byteSubstring => Length of bytes is less. length : {}, startPoint : {}, endPoint : {}", bytes.length, startPoint, endPoint);
				return sentence;
			}
			
			for(int i = 0; i < endPoint; i++){
				value[i] = bytes[startPoint + i];
			}
			
			/* 
			    logger.debug("utf-8 -> euc-kr        : " + new String(word.getBytes("utf-8"), "euc-kr"));
			    logger.debug("utf-8 -> ksc5601       : " + new String(word.getBytes("utf-8"), "ksc5601"));
			    logger.debug("utf-8 -> x-windows-949 : " + new String(word.getBytes("utf-8"), "x-windows-949"));
			    logger.debug("utf-8 -> iso-8859-1    : " + new String(word.getBytes("utf-8"), "iso-8859-1"));
			    logger.debug("iso-8859-1 -> euc-kr        : " + new String(word.getBytes("iso-8859-1"), "euc-kr"));
			    logger.debug("iso-8859-1 -> ksc5601       : " + new String(word.getBytes("iso-8859-1"), "ksc5601"));
			    logger.debug("iso-8859-1 -> x-windows-949 : " + new String(word.getBytes("iso-8859-1"), "x-windows-949"));
			    logger.debug("iso-8859-1 -> utf-8         : " + new String(word.getBytes("iso-8859-1"), "utf-8"));
			    logger.debug("euc-kr -> utf-8         : " + new String(word.getBytes("euc-kr"), "utf-8"));
			    logger.debug("euc-kr -> ksc5601       : " + new String(word.getBytes("euc-kr"), "ksc5601"));
			    logger.debug("euc-kr -> x-windows-949 : " + new String(word.getBytes("euc-kr"), "x-windows-949"));
			    logger.debug("euc-kr -> iso-8859-1    : " + new String(word.getBytes("euc-kr"), "iso-8859-1"));
			    logger.debug("ksc5601 -> euc-kr        : " + new String(word.getBytes("ksc5601"), "euc-kr"));
			    logger.debug("ksc5601 -> utf-8         : " + new String(word.getBytes("ksc5601"), "utf-8"));
			    logger.debug("ksc5601 -> x-windows-949 : " + new String(word.getBytes("ksc5601"), "x-windows-949"));
			    logger.debug("ksc5601 -> iso-8859-1    : " + new String(word.getBytes("ksc5601"), "iso-8859-1"));
			    logger.debug("x-windows-949 -> euc-kr     : " + new String(word.getBytes("x-windows-949"), "euc-kr"));
			    logger.debug("x-windows-949 -> utf-8      : " + new String(word.getBytes("x-windows-949"), "utf-8"));
			    logger.debug("x-windows-949 -> ksc5601    : " + new String(word.getBytes("x-windows-949"), "ksc5601"));
			    logger.debug("x-windows-949 -> iso-8859-1 : " + new String(word.getBytes("x-windows-949"), "iso-8859-1"));
			*/
			
			out = new String(value, encoding).trim();
		}
	    catch (UnsupportedEncodingException e) {
			throw new APIException(e);
		}
		catch(Exception e) {
			throw new APIException(e);
		} 
	    
	    return out;
	}
	
    
    /**
     * 문자열에서 주어진 패턴이 존재하면 대상 문자열을 변경대상 문자열로 변경합니다.
     * @param word
     * @param patterns
     * @param targetWord
     * @param replaceWord
     * @return
     */
    @APIOperation(description="문자열에서 주어진 패턴이 존재하면 대상 문자열을 변경대상 문자열로 변경합니다.")
	public String englishMorphemePartternReplace(String word , String[] patterns , String[] targetWord , String[] replaceWord) {
    	
    	regexUtil = (RegexUtil) LApplicationContext.getBean(regexUtil, RegexUtil.class);
    	
		String result = APIUtil.NVL(word);
		if(!result.equals("") && (patterns.length == targetWord.length && patterns.length == replaceWord.length)) {
			for(int i = 0 ; i < patterns.length; i++ ){
				if(regexUtil.matcherPatternFind(word, patterns[i].toString())){
					result = word.replaceAll(targetWord[i], replaceWord[i]);
				}
			}
		}
		return result;
	}
	
    /**
     * 두 문자 간에 일치하는 케릭터의 갯수를 리턴합니다.
     * @param str1
     * @param str2
     * @return
     */
	@APIOperation(description="두 문자 간에 일치하는 케릭터의 갯수를 리턴합니다.")
    public int indexOfDifference(String str1, String str2) {

		str2 = englishMorphemePartternReplace(str2 , "([,0-9０-９])".split("\\|") , ",".split("\\|") , "".split("\\|"));

        if (str1 == null || str2 == null) {
            return 0;
        }
        //양방향 일치하는 문자일경우
    	if (str1.equals(str2)) {
            return -1;
        }

        int i;
        for (i = 0; i < str1.length() && i < str2.length(); ++i) {
            if (str1.charAt(i) != str2.charAt(i)) {
                break;
            }
        }
        if (i < str2.length() || i < str1.length()) {
            return i;
        }
        return -1;
    }

	@APIOperation(description="국문/영문 형태소 복합 분석")
	public String getMorphologicalAnalysis(String firstLang, String sentence) {
		return getMorphologicalAnalysis(firstLang, sentence, null);
	}
	
	@APIOperation(description="국문/영문 형태소 복합 분석")
	public String getMorphologicalAnalysis(String firstLang, String sentence, String division) {
		//logger.debug("[START] getKorEngMorphemes : \"{}\", division : \"{}\"", sentence, division);
		
		if(APIUtil.isEmpty(sentence)) {
			//logger.warn("형태소 분석 대상 문장/단어가 존재하지 않습니다.");
			return null; 
		}
		
		if(APIUtil.isEmpty(division)) {
			division = OperateConstants.STR_COMA;
		}
		
		//WARN : getBeanInstance is test getBeanInstance
		koreanAnalyzers = (KoreanAnalyzers) LApplicationContext.getBeanInstance(koreanAnalyzers, KoreanAnalyzers.class);
		englishAnalyzers = (EnglishAnalyzers) LApplicationContext.getBeanInstance(englishAnalyzers, EnglishAnalyzers.class);
		regexUtil = (RegexUtil) LApplicationContext.getBeanInstance(regexUtil, RegexUtil.class);
		apiUtil = (APIUtil) LApplicationContext.getBeanInstance(apiUtil, APIUtil.class);
		
		String out = null;
		StringBuffer finalBuffer = null;
		
		Set<String> finalSet = new LinkedHashSet<String>();
		List<String> kor = new ArrayList<String>();
		List<String> eng = new ArrayList<String>();
		
		String conversion = null;

		//문자열 내에 한글/한문이 존재하면 형태소 분석실행
		if(regexUtil.testPattern(sentence, MorphemeUtil.PATTERN_KOREAN_SENTENCE)) {
			//영문삭제 (한/중 분석만을 위함) & ( 주	) 삭제
			conversion = apiUtil.toHalfChar(sentence).replaceAll("(?i)".concat(MorphemeUtil.PATTERN_CORP_SIGN).concat("|").concat(MorphemeUtil.PATTERN_SPEC_CHAR).concat("|").concat(MorphemeUtil.PATTERN_ENGLISH_SENTENCE), OperateConstants.STR_BLANK).trim();			
			kor = new ArrayList<String>(koreanAnalyzers.getKoreanMorphologicalAnalysis(conversion));
		}
		
		//한/중/일 문자삭제 (영문 분석을 위함)
		if(regexUtil.testPattern(sentence, MorphemeUtil.PATTERN_ENGLISH_SENTENCE)) {
			//문자열 내에 영문이 존재하면 형태소 분석실행
			conversion = apiUtil.toHalfChar(sentence).replaceAll("(?i)".concat(MorphemeUtil.PATTERN_SPEC_CHAR).concat("|").concat(MorphemeUtil.PATTERN_KOREAN_SENTENCE), OperateConstants.STR_BLANK).trim();
			eng = new ArrayList<String>(englishAnalyzers.getEnglishMorphologicalAnalysis(conversion));
		}
		
		if(kor.size() > 0) {
			Collections.sort(kor);
		}

		if(eng.size() > 0) {
			Collections.sort(eng);
		}
		
		if(firstLang.equalsIgnoreCase(MorphemeUtil.MORP_LANG_KO)) {
			finalSet.addAll(kor);
			finalSet.addAll(eng);
		}
		else {
			finalSet.addAll(eng);			
			finalSet.addAll(kor);
		}
		
		//logger.debug("# finalSet : {}", finalSet);
		
		finalBuffer = new StringBuffer();
		for(String morpheme : finalSet) {
			if(division.equals(morpheme)) {
				continue;
			}
			finalBuffer.append(morpheme);
			finalBuffer.append(division);
		}
		
		//logger.debug("# finalBuffer : {}", finalBuffer);
		//logger.debug("# division : {}", division);
		
		if(finalBuffer.toString().length() > 0) {
			out = finalBuffer.toString().substring(0, finalBuffer.toString().length() - division.length());
		}
		//logger.debug("[END] getKorEngMorphemes : {}", out);
		return out;
	}


    @APIOperation(description="Byte를 Long으로 변환")
	public long byteToLong(byte[] byteParam) {
		ByteBuffer bb = ByteBuffer.allocate(byteParam.length);
		bb.put(byteParam);
		return bb.getLong();
	}
		
    @APIOperation(description="두개의 문자열 배열을 병합 합니다.")    
	public String[] mergeStringArray(String[] a, String[] b){
    	String[] result = null;
    	
    	if(a != null && b != null) {
	        int length = a.length + b.length;
	        result = new String[length];
	        System.arraycopy(a, 0, result, 0, a.length);
	        System.arraycopy(b, 0, result, a.length, b.length);
    	}
    	else if(a == null) {
    		result = b;
    	}
    	else if(b == null) {
    		result = a;
        }
        return result;
    }
	
}