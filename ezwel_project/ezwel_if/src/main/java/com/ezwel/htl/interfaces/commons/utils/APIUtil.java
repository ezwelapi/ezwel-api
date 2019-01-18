package com.ezwel.htl.interfaces.commons.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
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
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
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

@Component
@APIType(description="IF API 공통 유틸")
public class APIUtil {

	private static final Logger logger = LoggerFactory.getLogger(APIUtil.class);
	
	
	@APIOperation(description="오브젝트가 null이면 default 객체를 리턴합니다.", isExecTest=true)
	public static Object ONVL(Object inObject, Object defObject){
		return (inObject != null ? inObject : defObject);
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
	
	@APIOperation(description="문자열의 isEmpty 여부를 체크하 고 false이면 defaultStr을 반환합니다.", isExecTest=true)
	public static String NVL(String str, String defs) {
		return NVL(str, defs, false);
	}
	
	/**
	 * str의 isEmpty 여부를 체크하 고 false이면 defaultStr을 반환합니다.
	 * @param str
	 * @param defaultStr
	 * @return
	 */
	@APIOperation(description="isTrimCheck 여부에따라 문자열을 trim하고 isEmpty 여부를 체크한 후 false이면 defaultStr을 반환합니다.", isExecTest=true)
    public static String NVL(String str, String defs, boolean isTrimCheck) {
		
    	String out = str;
    	String defaults = defs;
    	if(isTrimCheck && out != null) {
    		out = out.trim();
    	}
    	
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
		return getId(Long.toString(currentTimeMillis()));
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
		String uuidMD5 = CryptUtil.getMD5HashString(getRandomUUID().concat(getVMID()).concat(reference));
		
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
    public int getBytesLength(String userSentence, String userEncoding) {
    	
    	int out = 0;
    	byte[] byteString = null;
    	String encoding = userEncoding;
    	String sentence = userSentence;
    	
    	if( APIUtil.isEmpty(encoding) ) {
    		encoding = OperateConstants.DEFAULT_ENCODING;
    	}
    	
    	try {
    		byteString = sentence.getBytes(encoding);
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
		
		out = CryptUtil.getEncodeBase64(httpApiSignature);
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
			apiKey = (CryptUtil.getMD5HashString(agentName.concat(prevfix).concat(httpAgentId).concat(toDay))).concat(prevfix).toLowerCase();
		}
		else {
			//outside
			apiKey = (CryptUtil.getMD5HashString(httpAgentId.concat(prevfix).concat(agentName).concat(toDay))).concat(prevfix).toLowerCase();
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
				if(APIUtil.isEmpty(str)) {
					out = false;
					break;
				}
			}
		}
		return out;
	}
	
	@APIOperation(description="현제 타임스템프를 리턴합니다.")
	public static String getTimeStamp() {
		
		Timestamp timestamp = null;
		Date resultDate = null;
		
		try {
			
			timestamp = new Timestamp(System.currentTimeMillis());
			resultDate = new Date( timestamp.getTime( ) );
			return FastDateFormat.getInstance(OperateConstants.DEF_DATE_MILLISECOND_FORMAT, TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREA).format(resultDate);			
		}
		catch(Exception e) {
			throw new RuntimeException("타임스탬프발급 장애 발생");
		}
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
		SimpleDateFormat sdf = new SimpleDateFormat(((format == null || format.isEmpty()) ? OperateConstants.DEF_DAY_FORMAT : format));
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
	
	
	@APIOperation(description="TimeMillis를 문자열Date로 변환합니다.")
	public static String getTimeMillisToDate(Long userTimeMillis) {
		return getTimeMillisToDate(userTimeMillis, null);
	}
	
	@APIOperation(description="TimeMillis를 주어진 날짜포멧의 문자열Date로 변환합니다.")
	public static String getTimeMillisToDate(Long userTimeMillis, String userDateFormat) {
		//logger.debug("[START] getTimeMillisToDate userTimeMillis : {}, userDateFormat : {}", userTimeMillis, userDateFormat);
		
		if(userTimeMillis == null) {
			logger.warn("[WARN] ThreadLocal is init");
			return "";
		}
		String out = null;
		long timeMillis = userTimeMillis;
		String format = userDateFormat;

		try {
			Date resultDate = new Date(timeMillis);
	    	String dateFormat = (format != null && !format.isEmpty()) ? format : OperateConstants.DEF_DATE_FORMAT;
	    	out = FastDateFormat.getInstance(dateFormat, TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREA).format(resultDate);
		}
		catch(APIException e) {
			logger.error("- TimeMillisToDate API Exception [inputParameter userTimeMillis : {}, userDateFormat : {}]", new Object[]{timeMillis, format});
			throw new APIException("- TimeMillisToDate API Exception [inputParameter userTimeMillis : {}, userDateFormat : {}]", new Object[]{timeMillis, format}, e);
		}

    	return out;
	
	}
	
	@APIOperation(description="로컬 호스트 InetAddress를 리턴합니다.")
	public static InetAddress getLocalHost() {
		
		InetAddress local = null;

		try {
			local = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} 

		return local;
	}

	
	@APIOperation(description="서버 IP 대역")
	public static String getServerAddress() {
		String out = null;
		String prodServerIpRange = InterfaceFactory.getServerAddress().getProdServerIpRange(); 
		String devServerIpRange = InterfaceFactory.getServerAddress().getDevServerIpRange();
		
		//운영서버인지 IP대역 확인
		if(APIUtil.isNotEmpty(prodServerIpRange)) {
			
			if(prodServerIpRange.endsWith(OperateConstants.STR_ASTERISK)) {
				prodServerIpRange = prodServerIpRange.substring(0, prodServerIpRange.indexOf(OperateConstants.STR_ASTERISK));
				
				if(InterfaceFactory.LOCAL_HOST_ADDRESS.startsWith(prodServerIpRange)) {
					out = OperateConstants.CURRENT_PROD_SERVER;
				}
			}
			else if(InterfaceFactory.LOCAL_HOST_ADDRESS.equals(prodServerIpRange)) {
				out = OperateConstants.CURRENT_PROD_SERVER;
			}
		}
		
		//운영서버가 아니면 개발서버 IP대역에서 확인
		if(out == null && APIUtil.isNotEmpty(devServerIpRange)) {
			
			if(devServerIpRange.endsWith(OperateConstants.STR_ASTERISK)) {
				devServerIpRange = devServerIpRange.substring(0, devServerIpRange.indexOf(OperateConstants.STR_ASTERISK));
				
				if(InterfaceFactory.LOCAL_HOST_ADDRESS.startsWith(devServerIpRange)) {
					out = OperateConstants.CURRENT_DEV_SERVER;
				}
			}
			else if(InterfaceFactory.LOCAL_HOST_ADDRESS.equals(devServerIpRange)) {
				out = OperateConstants.CURRENT_DEV_SERVER;
			}
		}
		
		//운영/개발 서버가 모두 아니면 개발자 PC
		if(out == null) {
			out = OperateConstants.CURRENT_PC_SERVER;
		}

		return out;
	}
	
	
	@APIOperation(description="서버별 이미지 저장루트 디렉토리를 바인드된 이미지 상대경로앞에 붙여 리턴하여줌")
	public static String getImageCanonicalPath(String userRelativePath) {
		String imageRootPath = InterfaceFactory.getImageRootPath();
		String relativePath = userRelativePath;
		return imageRootPath.concat(File.separator).concat(relativePath);
	}
	
	@APIOperation
	public static String getFirstCharLowerCase(String strWord) {
		
		String out = null;
		
		String word = NVL(strWord, "").trim();
		if(word.length() > 1) {
			out = word.substring(0,1).toLowerCase() + word.substring(1);
		}
		else {
			out = word.toLowerCase();
		}
		
		return out;
	}
	
	
	@APIOperation(description="핸들링 패스 필드인지 여부를 리턴합니다.", isExecTest=true)
	public boolean isPassField(Field field) {
		boolean out = false;

		if (Modifier.isTransient(field.getModifiers()) || Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
			/* if (logger.isDebugEnabled()) {
				logger.debug("[ field name : " + field.getName()+ " ] modifiers \"" + field.getModifiers()+ "\" is pass");
			} */
			out = true;
		}

		return out;
	}
	

	/**
	 * 반각문자로 변경한다
	 * 
	 * @param src 변경할값
	 * @return String 변경된값
	 */
    @APIOperation(description="반각문자로 변경한다.")
	public String toHalfChar(String str) {
		if (APIUtil.NVL(str).equals("")) {
			return "";
		}
		
		String target = str.trim();

		StringBuffer strBuf = new StringBuffer();

		char c = 0;
		int nSrcLength = target.length();
		for (int i = 0; i < nSrcLength; i++) {
			c = target.charAt(i);
			// 영문이거나 특수 문자 일경우.
			if (c >= '！' && c <= '～') {
				c -= 0xfee0;
			} else if (c == '　') {
				c = 0x20;
			}
			// 문자열 버퍼에 변환된 문자를 쌓는다
			strBuf.append(c);
		}
		return strBuf.toString();
	}

	/**
	 * 전각문자로 변경한다.
	 * 
	 * @param src 변경할값
	 * @return String 변경된값
	 */
	@APIOperation(description = "전각문자로 변경한다.")
	public String toFullChar(String str) {
		if (APIUtil.NVL(str).equals("")) {
			return "";
		}

		String target = str.trim();

		// 변환된 문자들을 쌓아놓을 StringBuffer 를 마련한다
		StringBuffer strBuf = new StringBuffer();
		char c = 0;
		int nSrcLength = target.length();
		for (int i = 0; i < nSrcLength; i++) {
			c = target.charAt(i);
			// 영문이거나 특수 문자 일경우.
			if (c >= 0x21 && c <= 0x7e) {
				c += 0xfee0;
			}
			// 공백일경우
			else if (c == 0x20) {
				c = 0x3000;
			}
			// 문자열 버퍼에 변환된 문자를 쌓는다
			strBuf.append(c);
		}
		return strBuf.toString();
	}

	/**
	 * 대상문자열(strTarget)이 전각문자로 구성되어 있는지 확인한다.
	 *
	 * @param strTarget 전각여부를 확인할 문자열
	 * @return 전각문자만으로 구성된 문자열일 경우 true반환. 아니면 false
	 */
    @APIOperation(description="대상문자열이 전각문자로 구성되어 있는지 확인한다.")
	public boolean isFullWord(String strTarget) {
    	if (APIUtil.NVL(strTarget).equals("")) {
			return false;
		}
    	
    	byte[] byteArray = strTarget.getBytes();
		for (int i = 0; i < byteArray.length; i++) {
			if ((byteArray[i] >= (byte) 0x81 && byteArray[i] <= (byte) 0x9f)
					|| (byteArray[i] >= (byte) 0xe0 && byteArray[i] <= (byte) 0xef)) {
				if ((byteArray[i + 1] >= (byte) 0x40 && byteArray[i + 1] <= (byte) 0x7e)
						|| (byteArray[i + 1] >= (byte) 0x80 && byteArray[i + 1] <= (byte) 0xfc)) {
					i++;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * 대상문자열(strTarget)이 반각문자로 구성되어 있는지 확인한다.
	 *
	 * @param strTarget 반각여부를 확인할 문자열
	 * @return 반각문자만으로 구성된 문자열일 경우 true반환. 아니면 false
	 */
    @APIOperation(description="대상문자열이 반각문자로 구성되어 있는지 확인한다.")
	public boolean isHalfWord(String strTarget) {
    	if (APIUtil.NVL(strTarget).equals("")) {
			return false;
		}
    	
		byte[] byteArray = strTarget.getBytes();
		for (int i = 0; i < byteArray.length; i++) {
			if ((byteArray[i] >= (byte) 0x81 && byteArray[i] <= (byte) 0x9f)
					|| (byteArray[i] >= (byte) 0xe0 && byteArray[i] <= (byte) 0xef)) {
				if ((byteArray[i + 1] >= (byte) 0x40 && byteArray[i + 1] <= (byte) 0x7e)
						|| (byteArray[i + 1] >= (byte) 0x80 && byteArray[i + 1] <= (byte) 0xfc)) {
					return false;
				}
			}
		}
		return true;
	}	
    
}
