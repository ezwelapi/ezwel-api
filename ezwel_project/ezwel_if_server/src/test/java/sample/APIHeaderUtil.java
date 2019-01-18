package sample;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.utils.APIUtil;

//TEST API (실사용 코드에서는 삭제하세요)
import junit.framework.TestCase;

/**
 * <pre>
 * 인터페이스 요청 헤더 발급 예제
 * 사용한 base64는 commons-codec-1.10.jar API를 이용하였습니다.
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2019. 01. 15.
 * @serviceType API
 */
public class APIHeaderUtil extends TestCase /* TEST API extends (실사용 코드에서는 삭제하세요) */ {

	private static final Logger logger = LoggerFactory.getLogger(APIHeaderUtil.class);

	private final static String STR_HYPHEN;
	private final static String STR_BLANK;
	
	static {
		STR_HYPHEN = "-";
		STR_BLANK = "";
	}
	
	/**
	 * 인터페이스 인증에 필요한 http header 데이터 생성 테스트 
	 */
	public void testMakeHeader() /* 요청 헤더 발급 예제입니다. */ {
		
		HttpHeaderDTO httpHeaderDTO = new HttpHeaderDTO();
		
		//제휴사와 공유된 http-agent-id
		httpHeaderDTO.setHttpAgentId("10055550");
		//제휴사와 공유된 http-api-key
		httpHeaderDTO.setHttpApiKey("f5831137b0aa322fc2af1a37d6ecf8cei");
		//current http-api-timestamp
		httpHeaderDTO.setHttpApiTimestamp(APIUtil.getTimeStamp());
		//make http-api-signature
		httpHeaderDTO.setHttpApiSignature(getHttpSignature(httpHeaderDTO.getHttpAgentId(), httpHeaderDTO.getHttpApiKey(), httpHeaderDTO.getHttpApiTimestamp()));
		
		// 20190118153140438
		// 20190118153235386
		// 201901181446102
		//생성된 해더 정보 확인
		logger.debug("[생성된 해더 정보 확인]");
		logger.debug("HttpAgentId : {}", httpHeaderDTO.getHttpAgentId());
		logger.debug("HttpApiKey : {}", httpHeaderDTO.getHttpApiKey());
		logger.debug("HttpApiTimestamp : {}", httpHeaderDTO.getHttpApiTimestamp());
		logger.debug("HttpApiSignature : {}", httpHeaderDTO.getHttpApiSignature());
		
		//생성한 시그니처 원본 데이터 확인
		logger.debug("[생성한 시그니처 원본 데이터 확인]");
		logger.debug("Original HttpApiSignature : {}", base64Decode(httpHeaderDTO.getHttpApiSignature()));
		
		
		String testCode = "NjNlMDkwODFmODljNGNmMjgzMmQzNWY0YzE3NzgyOTYtMTAwNTU1NTAtMDUxYWQ2NTk5MjExODM3YWU1NTdlZmJhZWNjNjViM2FvLTIwMTkwMTE4MTQ0NjEwMg";
		
		logger.debug("Decode HttpApiSignature : {}", base64Decode(testCode));
	}
	
	
	/**
	 * <pre>
	 * [메서드 설명]
	 * 	RandomUUID를 리턴합니다.
	 * [사용방법 설명]
	 *  getRandomUUID()
	 * </pre>
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 14.
	 */
	public static String getRandomUUID() {
		/** randomUUID 36 byte */
		return UUID.randomUUID().toString();
	}

	/**
	 * <pre>
	 * [메서드 설명]
	 *  현제 타임스템프를 리턴합니다.
	 * [사용방법 설명]
	 *  getTimeStamp()
	 * </pre>
	 * 
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 15.
	 */
	public static String getTimeStamp() {
		
		String out = null;
		try {
			
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			Date resultDate = new Date( timestamp.getTime( ) );
	    	out = FastDateFormat.getInstance("yyyyMMddHHmmssS", TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREA).format(resultDate);			
		}
		catch(Exception e) {
			throw new RuntimeException("타임스탬프가 존재하지 않습니다.");
		}

		return out;
	}

	/**
	 * <pre>
	 * [메서드 설명]
	 * 	SECRET ID를 생성하여 리턴합니다.
	 * [사용방법 설명]
	 *  getHttpSignature(정의된에이전트아이디, 발급(공유)된API-KEY, 헤더에세팅된 timestamp)
	 * </pre>
	 * @param apiKey
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 14.
	 */
	public String getHttpSignature(String httpAgentId, String httpApiKey, String timestamp) {
		
		if(httpAgentId == null || httpAgentId.isEmpty()) {
			throw new RuntimeException("HTTP에이전트ID가 존재하지 않습니다.");
		}

		if(httpApiKey == null || httpApiKey.isEmpty()) {
			throw new RuntimeException("API키가 존재하지 않습니다.");
		}
		
		if(timestamp == null || timestamp.isEmpty()) {
			throw new RuntimeException("타임스탬프가 존재하지 않습니다.");
		}
		
		String out = null;
		//API 키 : httpApiKey
		
		//공유 비밀 키 : UUID + STR_HYPEN + 에이전트 아이디
		String shardSecret = new StringBuffer().append(UUID.randomUUID().toString().replace(STR_HYPHEN, STR_BLANK))
				.append(STR_HYPHEN).append(httpAgentId).toString();
		
		logger.debug("shardSecret : {}", shardSecret);
		
		//타임 스탬프 : timestamp
		
		//API 싸인
		String httpApiSignature = (new StringBuffer().append(shardSecret)
				.append(STR_HYPHEN).append(httpApiKey)
				.append(STR_HYPHEN).append(timestamp).toString());
		
		logger.debug("httpApiSignature : {}", httpApiSignature);
		
		out = base64Encode(httpApiSignature);
		return out;
	}
	
	/**
	 * <pre>
	 * [메서드 설명]
	 * 	바인드된 일반 문자열을 Base64로 인코딩합니다.
	 * [사용방법 설명]
	 *  encode(문자열)
	 * </pre>
	 * 
	 * @param value
	 * @return
	 */
	public String base64Encode(String value) {
		
		if(value == null || value.isEmpty()) {
			throw new RuntimeException("인코딩대상 문자열이 존재하지 않습니다.");
		}
		
        String result = "";
        byte plainText[] = null;
        
        try {
        	
            plainText = value.getBytes();
            result = Base64.encodeBase64URLSafeString(plainText);
        } catch(Exception e) {
        	throw new RuntimeException("BASE64 인코딩 장애발생", e);
        }
        return result;
    }

	
	/**
	 * <pre>
	 * [메서드 설명]
	 * 	바인드된 일반 문자열을 Base64로 디코딩합니다.
	 * [사용방법 설명]
	 *  decode(base64 인코딩된 문자열)
	 * </pre>
	 * 
	 * @param value
	 * @return
	 */	
    public String base64Decode(String value) {
    	
		if(value == null || value.isEmpty()) {
			throw new RuntimeException("디코딩대상 문자열이 존재하지 않습니다.");
		}
		
        String result = "";
        Base64 decoder = null;
        byte plainText[] = null;
        
        try {
        	
        	decoder = new Base64();
            plainText = decoder.decode(value);
            result = new String(plainText);
        }
        catch(Exception e) {
        	throw new RuntimeException("BASE64 디코딩 장애발생", e);
        }
        return result;
    }

	
}