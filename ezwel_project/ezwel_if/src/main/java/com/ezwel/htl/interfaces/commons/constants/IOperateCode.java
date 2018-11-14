package com.ezwel.htl.interfaces.commons.constants;

import com.ezwel.htl.interfaces.commons.annotation.APIService;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;

/**
 * <pre>
 * API Constants
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */
@APIService
public class IOperateCode {

	//SYSTEM EXECUTABLE CODE
	public static final int API_RESULT_CODE_ERROR;
	public static final int API_RESULT_CODE_SUCCESS;
	public static final int API_RESULT_CODE_INVALID;
	public static final String DELIM_STR;
	public static final String DELIM_IN_NUMBER;
	public static final String STR_BLANK;
	public static final String DEF_DAY_FORMAT;
	public static final String DEF_DATE_FORMAT;
	public static final String DEFAULT_ENCODING;
	public static final String STR_COLON;
	public static final String STR_WHITE_SPACE;
	public static final String STR_TAB;
	public static final String STR_MID_BRACKET_R;
	public static final String STR_MID_BRACKET_L;
	public static final String STR_MAX_BRACKET_R;
	public static final String STR_MAX_BRACKET_L;
	public static final long   LONG_ZERO_VALUE;
	public static final long   LONG_MINUS_ONE;
	public static final String MESSAGE_ISNULL;
	public static final String HTTP_METHOD_POST;
	public static final String HTTP_METHOD_GET;
	public static final String STR_UNDERBAR;
	public static final String STR_HYPHEN;
	public static final String STR_AT;
	public static final String STR_DOT;
	public static final String STR_COMA;
	public final static String STR_PAREN_START;
	public final static String STR_PAREN_END;	
	public static final String LINE_SEPARATOR;
	public static final String FILE_SEPARATOR;
	public static final String PATH_SEPARATOR;
	public static final String OS_NAME;
	public static final String JAVA_VM_VERSION;
	public static final String JAVA_CLASS_VERSION;
	public static final String USER_NAME;
	public static final String OS_VERSION;
	public static final String OS_ARCH;
	public static final String JAVA_VERSION;
	public static final String JAVA_RUNTIME_VERSION;
	public static final String JAVA_VM_SPECIFICATION_VERSION;
	public static final String JAVA_HOME;
	public static final String JAVA_VENDOR;
	public static final String JAVA_SPECIFICATION_VERSION;
	public final static String SPRING_CONTROLLER;
	public final static String DEFAULT_SERVLET;
	/** ORACLE, IBM JDK 존재함, HP JDK 에서 존재하지 않음 */
	public static final String USER_COUNTRY;
	public static final String USER_DIR;
	public static final String USER_LANGUAGE;
	public static final String FILE_ENCODING;
	
	/** Use annotation value */
	public static final String META_COMM_HEADER = "commonHeader";
	
	static {
		
		//SYSTEM EXECUTABLE CODE
		API_RESULT_CODE_ERROR = 0;
		API_RESULT_CODE_SUCCESS = 1;
		API_RESULT_CODE_INVALID = 2;
		DELIM_STR = "{}";
		DELIM_IN_NUMBER = "(\\{[0-9]?+\\})";
		STR_BLANK = "";
		DEF_DAY_FORMAT = "yyyy-MM-dd";
		DEF_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		DEFAULT_ENCODING = "UTF-8";
		STR_COLON = ":";
		STR_WHITE_SPACE = " ";
		STR_TAB = "	";
		STR_MID_BRACKET_R = "{";
		STR_MID_BRACKET_L = "}";
		STR_MAX_BRACKET_R = "[";
		STR_MAX_BRACKET_L = "]";
	 	LONG_ZERO_VALUE = 0L;
	 	LONG_MINUS_ONE = -1L;
		//META_COMM_HEADER = "commonHeader";
		MESSAGE_ISNULL = "message is null.";
		HTTP_METHOD_POST = "POST";
		HTTP_METHOD_GET = "GET";
		STR_UNDERBAR = "_";
		STR_HYPHEN = "-";
		STR_AT = "@";
		STR_DOT = ".";
		STR_COMA = ",";
		STR_PAREN_START = "(";
		STR_PAREN_END  = ")";		
		LINE_SEPARATOR = APIUtil.getProperty("line.separator");
		FILE_SEPARATOR = APIUtil.getProperty("file.separator");
		PATH_SEPARATOR = APIUtil.getProperty("path.separator");
		OS_NAME = APIUtil.getProperty("os.name");
		JAVA_VM_VERSION = APIUtil.getProperty("java.vm.version");
		JAVA_CLASS_VERSION = APIUtil.getProperty("java.class.version");
		USER_NAME = APIUtil.getProperty("user.name");
		OS_VERSION = APIUtil.getProperty("os.version");
		OS_ARCH = APIUtil.getProperty("os.arch");
		JAVA_VERSION = APIUtil.getProperty("java.version");
		JAVA_RUNTIME_VERSION = APIUtil.getProperty("java.runtime.version");
		JAVA_VM_SPECIFICATION_VERSION = APIUtil.getProperty("java.vm.specification.version");
		JAVA_HOME = APIUtil.getProperty("java.home");
		JAVA_VENDOR = APIUtil.getProperty("java.vendor");
		JAVA_SPECIFICATION_VERSION = APIUtil.getProperty("java.specification.version");
		SPRING_CONTROLLER = "SPRING";
		DEFAULT_SERVLET = "DEFAULT_SERVLET";
		/** ORACLE, IBM JDK 존재함, HP JDK 에서 존재하지 않음 */
		USER_COUNTRY = APIUtil.getProperty("user.country");
		USER_DIR = APIUtil.getProperty("user.dir");
		USER_LANGUAGE = APIUtil.getProperty("user.language");
		FILE_ENCODING = APIUtil.getProperty("file.encoding");
	}
}
