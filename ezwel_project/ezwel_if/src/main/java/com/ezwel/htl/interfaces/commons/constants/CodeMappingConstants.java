package com.ezwel.htl.interfaces.commons.constants;

import java.util.HashMap;
import java.util.Map;

public class CodeMappingConstants {

	public final static Map<String, String> TEMP_IF_CODE_MAPPER;
	
	public final static Map<String, String> TEMP_IF_NAME_MAPPER; 
	
	static {

		/**
		 * 남은 코드 매핑
		 * CompareStat
		 */
		
		TEMP_IF_CODE_MAPPER = new HashMap<String, String>();
		//이곳에 전문코드 : 이지웰코드 추가
		TEMP_IF_CODE_MAPPER.put("r01","R001RW");
		TEMP_IF_CODE_MAPPER.put("r02","R001OK");
		TEMP_IF_CODE_MAPPER.put("r03","R001OK");
		TEMP_IF_CODE_MAPPER.put("r04","R001XX");

		TEMP_IF_NAME_MAPPER = new HashMap<String, String>();
		//이곳에 전문코드 : 이지웰코드 추가
		TEMP_IF_NAME_MAPPER.put("r01","예약대기");
		TEMP_IF_NAME_MAPPER.put("r02","예약완료");
		TEMP_IF_NAME_MAPPER.put("r03","취소대기");
		TEMP_IF_NAME_MAPPER.put("r04","취소완료");
	}
}
