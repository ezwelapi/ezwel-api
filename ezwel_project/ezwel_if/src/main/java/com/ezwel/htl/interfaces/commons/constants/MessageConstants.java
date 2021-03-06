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
	public static final int RESPONSE_CODE_9101;
	public static final int RESPONSE_CODE_9102;
	public static final int RESPONSE_CODE_9103;
	public static final int RESPONSE_CODE_9200;
	public static final int RESPONSE_CODE_9300;
	public static final int RESPONSE_CODE_9301;
	public static final int RESPONSE_CODE_9302;
	public static final int RESPONSE_CODE_9400;
	public static final int RESPONSE_CODE_9401;
	public static final int RESPONSE_CODE_9500;
	public static final int RESPONSE_CODE_9501;
	public static final int RESPONSE_CODE_9502;
	public static final int RESPONSE_CODE_9600;
	public static final int RESPONSE_CODE_9700;
	public static final int RESPONSE_CODE_9800;
	public static final int RESPONSE_CODE_9900;
	public static final int RESPONSE_CODE_9901;
	public static final int RESPONSE_CODE_9902;
	
	public static final Map<Integer, String> MESSAGE_MAP;

	public static final String RESPONSE_CODE_FIELD_NAME;
	public static final String IF_LOG_FIELD_NAME;
	public static final String RESPONSE_MESSAGE_FIELD_NAME;
	
	public static final String RESPONSE_TYPE_NAME_FIELD_NAME;
	public static final String RESPONSE_TYPE_DESC_FIELD_NAME;
	public static final String RESPONSE_OPERATION_NAME_FIELD_NAME;
	public static final String RESPONSE_OPERATION_DESC_FIELD_NAME;
	
	public static final String API_BATCH_LOG_TYPE_TM;
	public static final String API_BATCH_LOG_TYPE_ER;
	public static final String API_BATCH_LOG_TYPE_IV;
	
	static {
		MESSAGE_MAP = new HashMap<Integer, String>();
		
		RESPONSE_CODE_1000 = 1000;	// MessageConstants.RESPONSE_CODE_1000
		RESPONSE_CODE_2000 = 2000;	// MessageConstants.RESPONSE_CODE_2000
		RESPONSE_CODE_2001 = 2001;	// MessageConstants.RESPONSE_CODE_2001
		RESPONSE_CODE_3000 = 3000;	// MessageConstants.RESPONSE_CODE_3000
		RESPONSE_CODE_4000 = 4000;	// MessageConstants.RESPONSE_CODE_4000
		RESPONSE_CODE_5000 = 5000;	// MessageConstants.RESPONSE_CODE_5000
		RESPONSE_CODE_9000 = 9000;	// MessageConstants.RESPONSE_CODE_9000
		RESPONSE_CODE_9100 = 9100;	// MessageConstants.RESPONSE_CODE_9100
		RESPONSE_CODE_9101 = 9101;	// MessageConstants.RESPONSE_CODE_9101
		RESPONSE_CODE_9102 = 9102;	// MessageConstants.RESPONSE_CODE_9102
		RESPONSE_CODE_9103 = 9103;
		RESPONSE_CODE_9200 = 9200;	// MessageConstants.RESPONSE_CODE_9200
		RESPONSE_CODE_9300 = 9300;	// MessageConstants.RESPONSE_CODE_9300
		RESPONSE_CODE_9301 = 9301;	// MessageConstants.RESPONSE_CODE_9301
		RESPONSE_CODE_9302 = 9302;	// MessageConstants.RESPONSE_CODE_9302
		RESPONSE_CODE_9400 = 9400;	// MessageConstants.RESPONSE_CODE_9400
		RESPONSE_CODE_9401 = 9401;	// MessageConstants.RESPONSE_CODE_9401
		RESPONSE_CODE_9500 = 9500;	// MessageConstants.RESPONSE_CODE_9500
		RESPONSE_CODE_9501 = 9501;	// MessageConstants.RESPONSE_CODE_9501
		RESPONSE_CODE_9502 = 9502;	// MessageConstants.RESPONSE_CODE_9502
		RESPONSE_CODE_9600 = 9600;	// MessageConstants.RESPONSE_CODE_9600
		RESPONSE_CODE_9700 = 9700;	// MessageConstants.RESPONSE_CODE_9700
		RESPONSE_CODE_9800 = 9800;
		RESPONSE_CODE_9900 = 9900;
		RESPONSE_CODE_9901 = 9901;
		RESPONSE_CODE_9902 = 9902;
		
		MESSAGE_MAP.put(RESPONSE_CODE_1000, "정상적으로 처리되었습니다.");
		MESSAGE_MAP.put(RESPONSE_CODE_2000, "필수 파라메터 누락");
		MESSAGE_MAP.put(RESPONSE_CODE_2001, "파라메터 검증 실패(길이/포맷 등)");
		MESSAGE_MAP.put(RESPONSE_CODE_3000, "기타 오류");
		MESSAGE_MAP.put(RESPONSE_CODE_4000, "사용자 인증 실패");
		MESSAGE_MAP.put(RESPONSE_CODE_5000, "제휴사 시스템 에러");
		MESSAGE_MAP.put(RESPONSE_CODE_9000, "어플리케이션 장애 발생");
		MESSAGE_MAP.put(RESPONSE_CODE_9100, "인터페이스 장애 발생");
		MESSAGE_MAP.put(RESPONSE_CODE_9101, "일부 인터페이스 장애 발생");
		MESSAGE_MAP.put(RESPONSE_CODE_9102, "인터페이스 통신 장애 발생");
		MESSAGE_MAP.put(RESPONSE_CODE_9103, "인터페이스 체널정보가 존재하지 않습니다.");
		MESSAGE_MAP.put(RESPONSE_CODE_9200, "원격지 서버 에러 발생");
		MESSAGE_MAP.put(RESPONSE_CODE_9300, "인터페이스 초기화 실패");
		MESSAGE_MAP.put(RESPONSE_CODE_9301, "인터페이스 설정 파일 경로가 변경 되었거나 읽을 수 없습니다.");
		MESSAGE_MAP.put(RESPONSE_CODE_9302, "인터페이스 운영환경 파일핸들링 장애 발생");
		MESSAGE_MAP.put(RESPONSE_CODE_9400, "URL Image Not Found");
		MESSAGE_MAP.put(RESPONSE_CODE_9401, "시설 이미지 다운로드 실패!!");
		MESSAGE_MAP.put(RESPONSE_CODE_9500, "DB 핸들링 장애발생");
		MESSAGE_MAP.put(RESPONSE_CODE_9501, "DB 데이터 검증 실패!!");
		MESSAGE_MAP.put(RESPONSE_CODE_9502, "데이터가 존재하지 않음");
		MESSAGE_MAP.put(RESPONSE_CODE_9600, "숙박 시설 매핑 장애발생");
		MESSAGE_MAP.put(RESPONSE_CODE_9700, "프로세스 실행중");
		MESSAGE_MAP.put(RESPONSE_CODE_9800, "파라메터 인코딩 장애발생");
		MESSAGE_MAP.put(RESPONSE_CODE_9900, "메시지 발송 장애발생");
		MESSAGE_MAP.put(RESPONSE_CODE_9901, "메일 발송 장애발생");
		MESSAGE_MAP.put(RESPONSE_CODE_9902, "팩스 발송 장애발생");
		
		
		RESPONSE_CODE_FIELD_NAME = "code";
		IF_LOG_FIELD_NAME = "ifLog";
		RESPONSE_MESSAGE_FIELD_NAME = "message";
		RESPONSE_TYPE_NAME_FIELD_NAME = "typeName";
		RESPONSE_TYPE_DESC_FIELD_NAME = "typeDesc";
		RESPONSE_OPERATION_NAME_FIELD_NAME = "operationName";
		RESPONSE_OPERATION_DESC_FIELD_NAME = "operationDesc";
	
		API_BATCH_LOG_TYPE_TM = "TM";
		API_BATCH_LOG_TYPE_ER = "ER";
		API_BATCH_LOG_TYPE_IV = "IV";
	}
	
	public static String getMessage(Integer resultCode) {
		return (MESSAGE_MAP != null && resultCode != null ? MESSAGE_MAP.get(resultCode) : "");
	}
}
