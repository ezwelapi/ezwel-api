package com.ezwel.htl.interfaces.commons.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 16.
 */
public class MessageConstants {

	public static final int RESPONSE_CODE_1000;
	public static final int RESPONSE_CODE_2000;
	public static final int RESPONSE_CODE_2001;
	public static final int RESPONSE_CODE_3000;
	public static final int RESPONSE_CODE_4000;
	public static final int RESPONSE_CODE_5000;
	public static final int RESPONSE_CODE_9000;
	public static final int RESPONSE_CODE_9100;
	public static final int RESPONSE_CODE_9200;
	public static final int RESPONSE_CODE_9300;
	
	public static final Map<Integer, String> MESSAGE_MAP;

	public static final String RESPONSE_CODE_FIELD_NAME;
	public static final String RESPONSE_MESSAGE_FIELD_NAME;
	
	static {
		MESSAGE_MAP = new HashMap<Integer, String>();
		
		RESPONSE_CODE_1000 = 1000;	// MessageCode.RESPONSE_CODE_1000
		RESPONSE_CODE_2000 = 2000;	// MessageCode.RESPONSE_CODE_2000
		RESPONSE_CODE_2001 = 2001;	// MessageCode.RESPONSE_CODE_2001
		RESPONSE_CODE_3000 = 3000;	// MessageCode.RESPONSE_CODE_3000
		RESPONSE_CODE_4000 = 4000;	// MessageCode.RESPONSE_CODE_4000
		RESPONSE_CODE_5000 = 5000;	// MessageCode.RESPONSE_CODE_5000
		RESPONSE_CODE_9000 = 9000;	// MessageCode.RESPONSE_CODE_9000
		RESPONSE_CODE_9100 = 9100;	// MessageCode.RESPONSE_CODE_9100
		RESPONSE_CODE_9200 = 9200;	// MessageCode.RESPONSE_CODE_9200
		RESPONSE_CODE_9300 = 9300;	// MessageCode.RESPONSE_CODE_9300
		
		MESSAGE_MAP.put(RESPONSE_CODE_1000, "정상적으로 처리되었습니다.");
		MESSAGE_MAP.put(RESPONSE_CODE_2000, "필수 파라메터 누락");
		MESSAGE_MAP.put(RESPONSE_CODE_2001, "파라메터 검증 실패(길이/포맷 등)");
		MESSAGE_MAP.put(RESPONSE_CODE_3000, "기타 오류");
		MESSAGE_MAP.put(RESPONSE_CODE_4000, "사용자 인증 실패");
		MESSAGE_MAP.put(RESPONSE_CODE_5000, "시스템 에러");
		MESSAGE_MAP.put(RESPONSE_CODE_9000, "어플리케이션 장애 발생");
		MESSAGE_MAP.put(RESPONSE_CODE_9100, "인터페이스 장애 발생");
		MESSAGE_MAP.put(RESPONSE_CODE_9200, "원격지 서버 에러 발생");
		MESSAGE_MAP.put(RESPONSE_CODE_9300, "인터페이스 초기화 실패");
		
		RESPONSE_CODE_FIELD_NAME = "code";
		RESPONSE_MESSAGE_FIELD_NAME = "message";
	}
	
	public static String getMessage(Integer resultCode) {
		return (MESSAGE_MAP != null && resultCode != null ? MESSAGE_MAP.get(resultCode) : "");
	}
}
