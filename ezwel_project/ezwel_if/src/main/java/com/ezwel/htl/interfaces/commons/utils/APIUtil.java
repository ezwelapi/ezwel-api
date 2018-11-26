package com.ezwel.htl.interfaces.commons.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.rmi.dgc.VMID;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;

/**
 * <pre>
 * API Opertation에서 사용하는 유틸 클래스
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */

@APIType
@Component
public class APIUtil {

	private static final Logger logger = LoggerFactory.getLogger(APIUtil.class);
	
	public final static String DEF_DAY_FORMAT;
	public final static String DEF_DATE_FORMAT;
	
	static {
		DEF_DAY_FORMAT = "yyyyMMdd";
		DEF_DATE_FORMAT = "yyyyMMddHHmmss";
	}
	
	/**
	 * str의 isEmpty 여부를 체크하 고 false이면 ""을 반환합니다.
	 * @param str
	 * @return
	 */
	@APIOperation(description="문자열의 isEmpty 여부를 체크하 고 false이면 \"\"을 반환합니다.", isExecTest=true)
	public static String NVL(String str){
		return NVL(str, null);
	}
	
	/**
	 * str의 isEmpty 여부를 체크하 고 false이면 defaultStr을 반환합니다.
	 * @param str
	 * @param defaultStr
	 * @return
	 */
	@APIOperation(description="문자열의 isEmpty 여부를 체크하 고 false이면 defaultStr을 반환합니다.", isExecTest=true)
    public static String NVL(String str, String defs) {
		
    	String out = str;
    	String defaults = defs;
    	
        if (isEmpty(out)) {
        	
        	if(isEmpty(defaults)) {
        		out = "";
        	}
        	else {
        		out = defaults;
        	}
        }
        return out;
    }
    
	@APIOperation(description="문자열의 isEmpty 여부를 체크합니다.", isExecTest=true)
	public static boolean isEmpty(String str) {
		String sentence = str; 
		return StringUtils.isEmpty(sentence);
	}    
	
	@APIOperation(description="문자열의 isNotEmpty 여부를 체크합니다.", isExecTest=true)
	public static boolean isNotEmpty(String str) {
		String sentence = str; 
		return StringUtils.isNotEmpty(sentence);
	}	

	@APIOperation(description="바인드된 첫번째 문자열에 두번째 배열문자열의 값을 바인드하여 리턴합니다.", isExecTest=true)
    public static String formatMessage(String message, Object... arguments) {
    	
    	String out = null;
    	String pattern = message;
    	Object[] argument = arguments;
    	if(pattern != null) {

    		try {
		    	if(pattern.indexOf(OperateConstants.DELIM_STR) > -1) {
		    		out = MessageFormatter.arrayFormat(pattern, argument).getMessage().replaceAll(OperateConstants.DELIM_IN_NUMBER, OperateConstants.STR_BLANK);
		    	}
		    	else {
		    		out = MessageFormat.format(pattern, argument);
		    	}
    		}
    		catch(IllegalArgumentException e){
    			throw new APIException("Invalid message pattern : {}", new Object[]{pattern}, e);
    		}
    	}
        return out;
    }

	@APIOperation(description="바인드된 키에 해당하는 시스템 프로퍼티의 정보를 리턴합니다.", isExecTest=true)
	public static String getProperty(String key){
		
		String propsKey = key;
		String out = System.getProperty(propsKey);
		
		if(logger.isTraceEnabled()) {
			logger.trace("{} : {}", propsKey, out);
		}
		return out;
	}
	

	@APIOperation(description="현재 년월일 시분초(yyyy-MM-dd HH:mm:ss)를 리턴합니다.", isExecTest=true)
	public static String getFastDate(){
		return getFastDate(null);
	}
	
	@APIOperation(description="바인드된 포맷에 해당하는 현재 시간을 리턴합니다.", isExecTest=true)
	public static String getFastDate(String dateFormat){
		return getFastDate(null, dateFormat);
	}

	@APIOperation(description="바인드된 Date를 포맷에 마추어 리턴합니다.", isExecTest=true)
    public static String getFastDate(Date date, String dateFormat){
    	
    	String format = dateFormat;
    	Date datetime = (date != null ? date : new Date());
    	String dateformat = (format != null && !format.isEmpty()) ? format:OperateConstants.DEF_DATE_FORMAT;
    	return FastDateFormat.getInstance(dateformat, TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREA).format(datetime);
    }
	
	@APIOperation(description="현제 TimeMillis를 리턴합니다.", isExecTest=true)
	public static double getTimeMillisToSecond(long timeMillis){
		double out = timeMillis / 1000.;
		return out;
	}
	
	@APIOperation(description="현제 TimeMillis를 리턴합니다.", isExecTest=true)
	public static double getTimeMillisToMinute(long timeMillis){
		double out = timeMillis / (1000. * 60);
		return out;
	}
	
	@APIOperation(description="현제 TimeMillis를 리턴합니다.", isExecTest=true)
	public static long currentTimeMillis(){
		long out = System.currentTimeMillis();
    	return out; 
    }
	
	/**
	 * <pre>
	 * [메서드 설명]
	 * 	RandomUUID를 리턴합니다.
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 14.
	 */
	@APIOperation(description="RandomUUID를 리턴합니다.", isExecTest=true)
	public static String getRandomUUID(){
		/** randomUUID 36 byte */
		return UUID.randomUUID().toString();
	}
	
	/**
	 * <pre>
	 * [메서드 설명]
	 * 	VMID를 리턴합니다. (43byte)
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 14.
	 */
	@APIOperation(description="VMID를 리턴합니다.", isExecTest=true)
	public static String getVMID(){
		/** VMID 43 byte */
		return new VMID().toString();
	}
	
	/**
	 * <pre>
	 * [메서드 설명]
	 * randomUUID + VMID를 합하여 MD5로 암호화된 값을 리턴합니다. (32byte)
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 14.
	 */
	@APIOperation(description="randomUUID + VMID를 합하여 MD5로 암호화된 값을 리턴합니다.", isExecTest=true)
	public static String getId(){
		return getId("");
	}
	
	/**
	 * <pre>
	 * [메서드 설명]
	 * 	randomUUID + VMID + reference(파라메터)를 합하여 MD5로 암호화된 값(유일값)을 리턴합니다. (32byte)
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @param references
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 14.
	 */
	@APIOperation(description="randomUUID + VMID + reference(파라메터)를 합하여 MD5로 암호화된 값(유일값)을 리턴합니다.", isExecTest=true)
	public static String getId(String references){
		String reference = references;
		
		/** reference null check */
		if(reference == null || reference.isEmpty()) {
			reference = "";
		}
		/** getRandomUUID 36 byte + rmi.VMID 43 byte + reference */
		String uuidMD5 = MD5.getInstance().getHashString(getRandomUUID().concat(getVMID()).concat(reference));
		
		//logger.debug("uuidMD5 : {}, uuidMD5.length() : {} ", uuidMD5, uuidMD5.length());
		return uuidMD5;
	}
	
	
	/**
	 * <pre>
	 * [메서드 설명]
	 * 	과거일부터 현재 날자와의 차이를 리턴합니다.
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 14.
	 */
	@APIOperation(description="과거일부터 현재 날자와의 차이를 리턴합니다.", isExecTest=true)
    public static int getPrevDateDiff(int year, int month, int day){

    	/** 현재 날자 */
    	Calendar currentCal = Calendar.getInstance();
    	currentCal.setTime (new Date()); 

    	/** 기준일 */
    	Calendar benchmarkCal = Calendar.getInstance();
    	benchmarkCal.set ( year, (month-1), day); // (과거일자)기준일로 설정. month의 경우 해당월수-1.

    	/** 날자차이 채크 */
    	int difference = 0;
    	while ( benchmarkCal.before(currentCal) ) {
    		difference++;
    		benchmarkCal.add ( Calendar.DATE, 1 ); // 다음날로 바뀜
    	}

    	return difference;
    }
    
	/**
	 * <pre>
	 * [메서드 설명]
	 * 	바인드된 객체 배열을 문자열 버퍼로 연결한 문자열을 리턴합니다.
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @param arrays
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 14.
	 */
	@APIOperation(description="바인드된 객체 배열을 문자열 버퍼로 연결한 문자열을 리턴합니다.", isExecTest=true)
    public static String addString(Object... arrays) {
    	
    	StringBuilder out = new StringBuilder();
    	Object[] merges = arrays;
    	if( merges != null ) {
	    	for(Object sentence : merges) {
	    		out.append(sentence);
	    	}	
    	}
    	
    	return out.toString();
    }
    
    
    
    /**
     * <pre>
     * [메서드 설명]
     * 	문자열의 바이트를 리턴합니다.
     * [사용방법 설명]
     * 
     * </pre>
     * @param str
     * @param encoding
     * @return
     * @author swkim@ebsolution.co.kr
     * @since  2018. 11. 14.
     */
	@APIOperation(description="문자열의 바이트를 리턴합니다.", isExecTest=true)
    public int getBytesLength(String str, String encoding) {
    	
    	int out = 0;
    	byte[] byteString = null;
    	String strEncoding = null;
    	if( StringUtils.isNotEmpty(encoding) ) {
    		strEncoding = encoding;
    	}
    	else {
    		strEncoding = OperateConstants.FILE_ENCODING;
    	}
    	
    	try {
    		byteString = str.getBytes(strEncoding);
    		out = byteString.length;
    	} catch (UnsupportedEncodingException e) {
			throw new APIException("지원하지 않는 인코딩", e);
		}
    	
    	return out;
    }
	
	/**
	 * <pre>
	 * [메서드 설명]
	 * 	Array Object를 List Object로 변환합니다.
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @param array
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 12.
	 */
	@APIOperation(description="Array Object를 List Object로 변환합니다.", isExecTest=true)
    public static List<?> arrayToList(Object[] array){
    	if( array == null ) return null;
    	return Arrays.asList(array);
    }
    
	/**
	 * <pre>
	 * [메서드 설명]
	 * 	SECRET ID를 생성하여 리턴합니다.
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @param apiKey
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 14.
	 */
	@APIOperation(description="Http-Signature를 생성하여 리턴합니다.", isExecTest=true)
	public static String getHttpSignature(String httpAgentId, String httpApiKey, String timestamp) {
		if(isEmpty(httpApiKey)) {
			throw new APIException("apiKey가 존재하지 않습니다.");
		}
		
		String out = null;
		//API 키 : httpApiKey
		
		//공유 비밀 키 : UUID + OperateCode.STR_HYPEN + 에이전트 아이디
		String shardSecret = new StringBuffer().append(UUID.randomUUID().toString().replace(OperateConstants.STR_HYPHEN, OperateConstants.STR_BLANK))
				.append(OperateConstants.STR_HYPHEN).append(httpAgentId).toString();
		
		//타임 스탬프 : timestamp
		
		//API 싸인
		String httpApiSignature = (new StringBuffer().append(shardSecret)
				.append(OperateConstants.STR_HYPHEN).append(httpApiKey)
				.append(OperateConstants.STR_HYPHEN).append(timestamp).toString());
		
		logger.debug("httpSignature Original : {}", httpApiSignature);
		
		out = new Base64Codec().encode(httpApiSignature);
		logger.debug("[END] getSecretId : {}", out);
		return out;
	}
	
	
	
	
	/**
	 * <pre>
	 * [메서드 설명]
	 * 바인드된 URI에 해당하는 File을 리턴합니다.
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @param uriParam
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 20.
	 */
	@APIOperation(description="바인드된 URI에 해당하는 File을 리턴합니다.", isExecTest=true)
	public static File getFileFromURI(URI uriParam) {
		URI uri = uriParam;
		if(uri == null) {
			throw new APIException("URI가 존재하지 않습니다.");
		}
		return new File(uri);
	}
	
	/**
	 * <pre>
	 * [메서드 설명]
	 * 제휴사별 APIKey 생성 
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @param prevString
	 * @param agentName
	 * @param httpAgentId
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 20.
	 */
	@APIOperation(description="제휴사별APIKey 생성 ")
	public String createApiKey(String prevfix, String agentName, String httpAgentId) {
		String toDay = getFastDate("yyyyMMdd");
		String apiKey = null;
		if(prevfix.equalsIgnoreCase("i")) {
			// inside
			apiKey = (MD5.getInstance().getHashString(agentName.concat(prevfix).concat(httpAgentId).concat(toDay))).concat(prevfix).toLowerCase();
		}
		else {
			//outside
			apiKey = (MD5.getInstance().getHashString(httpAgentId.concat(prevfix).concat(agentName).concat(toDay))).concat(prevfix).toLowerCase();
		}
		return apiKey;
	}
	
	
	@APIOperation(description="문자밸열의 인덱스별 값의 존재 여부를 채크하여줍니다.")
	public static boolean isNotEmptyStringArray(String... arrayString) {
		
		boolean out = false;
		String[] strAry = arrayString;
	
		if(strAry != null && strAry.length > 0) {
			out = true;
			
			for(String str : strAry){
				if(StringUtils.isEmpty(str)) {
					out = false;
					break;
				}
			}
		}
		return out;
	}
	
	@APIOperation(description="현제 타임스템프를 리턴합니다.")
	public static String getTimeStamp() {
		Timestamp out = new Timestamp(System.currentTimeMillis());
		return out.toString();
	}
	
	@APIOperation(description="문자형식의 날자값이 유효한 날자인지 체크합니다.")
    public boolean isValidDate(String dateString) {
    	return isValidDate(dateString, null);
    }
    
	@APIOperation(description="문자형식의 날자값을 바인드된 데이터 포멧으로 변환하여 유효한 날자인지 체크합니다.")
	public boolean isValidDate(String dateString, String dateFormat) {
		
		if (dateString == null || dateString.isEmpty()) {
			return false;
		}
		String date = dateString;
		String format = dateFormat;
		SimpleDateFormat sdf = new SimpleDateFormat(((format == null || format.isEmpty()) ? DEF_DAY_FORMAT : format));
		String confirmFormat = null;
		try {
			confirmFormat = sdf.format(sdf.parse(date));
		} catch (ParseException e) {
			throw new APIException(e);
		}
		return date.equals(confirmFormat);
	}
	
	@APIOperation(description="바인드된 URL이 유효한 URL인지 체크합니다.")
	public static boolean isValidURL(final URL url) {
		boolean out = false;
	    try {
	        final URI uri = url.toURI();
	        if (!uri.isAbsolute()) {
	            throw new IllegalArgumentException("URI is not absolute: " + uri.toString());   //NOI18N
	        }
	        if (uri.isOpaque()) {
	            throw new IllegalArgumentException("URI is not hierarchical: " + uri.toString());   //NOI18N
	        }
	        if (!"file".equals(uri.getScheme())) {
	            throw new IllegalArgumentException("URI scheme is not \"file\": " + uri.toString());   //NOI18N
	        }
	        out = true;
	    } catch (URISyntaxException use) {
	        throw new IllegalArgumentException(use);
	    }
	    return out;
	}
	
	
	public static String getTimeMillisToDate(long userTimeMillis) {
		return getTimeMillisToDate(userTimeMillis, null);
	}
	
	public static String getTimeMillisToDate(long userTimeMillis, String dateFormat) {
		long timeMillis = userTimeMillis;
		String format = dateFormat;
		Date resultdate = new Date(timeMillis);
    	String dateformat = (format != null && !format.isEmpty()) ? format:OperateConstants.DEF_DATE_FORMAT;
    	return FastDateFormat.getInstance(dateformat, TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREA).format(resultdate);
	
	}
	
}
