package com.ezwel.htl.interfaces.commons.constants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;

/**
 * <pre>
 * API Constants
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */
@APIType
public class OperateConstants {

	//SYSTEM EXECUTABLE CODE
	public static final String DELIM_STR;
	public static final String DELIM_IN_NUMBER;
	public static final String STR_BLANK;
	public static final String DEF_DAY_FORMAT;
	public static final String DEF_DATE_FORMAT;
	public static final String GENERAL_DATE_FORMAT;
	public static final String DEFAULT_ENCODING;
	public static final String STR_COLON;
	public static final String STR_WHITE_SPACE;
	public static final String STR_TAB;
	public static final String STR_SLASH;
	public static final String STR_MID_BRACKET_R;
	public static final String STR_MID_BRACKET_L;
	public static final String STR_MAX_BRACKET_R;
	public static final String STR_MAX_BRACKET_L;
	public static final long   LONG_ZERO_VALUE;
	public static final long   LONG_MINUS_ONE;
	public static final long   LONG_MAX_VALUE;
	public static final int	   INTEGER_ZERO_VALUE;
	public static final int    INTEGER_MINUS_ONE;
	public static final BigDecimal   BIGDECIMAL_ZERO_VALUE;
	public static final BigDecimal   BIGDECIMAL_MINUS_ONE;	
	public static final String MESSAGE_ISNULL;
	public static final String HTTP_METHOD_POST;
	public static final String HTTP_METHOD_GET;
	public static final String STR_UNDERBAR;
	public static final String STR_HYPHEN;
	public static final String STR_AT;
	public static final String STR_DOT;
	public static final String STR_COMA;
	public static final String STR_SPEC_COMA;
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

	public static final String USER_DIR;
	public static final String FILE_ENCODING;
	/** TypeUtil use exclude package starts array */
	public static final List<String> EXCLUDE_PACKAGE_STARTS;
	/** VALIDATION TYPE */
	public static final int VALIDATE_DTO_FULL_FIELD_ANNO;
	public static final int VALIDATE_DTO_SINGLE_FIELD_ANNO;
	public static final int VALIDATE_SINGLE_VALUE_PATTERN;
	public static final int VALIDATE_DTO_SINGLE_FIELD_PATTERN;
	//Request Contents Type
	public static final String CONTENT_TYPE_MULTIPART_FORM_DATA;
	public static final String CONTENT_TYPE_APPLICATION_JSON;
	public static final String CONTENT_TYPE_QUERY_STRING;
	
	//Interface Batch System Info
	public static final String SYSTEM_ID;
	
	public static final Map<String, String> IMAGE_EXT;
	public static final String DEF_IMAGE_EXTENSION;
	
	public static final String DATA_IMAGE_PREFIX;
	public static final String STR_BASE64;
	
	public static final String FIELD_HTTP_AGENT_ID;
	public static final String FIELD_PATN_CD_TYPE;
	
	public final static String RESOURCE_LASTMODIFIED_KEY;
	public final static String RESOURCE_PROPERTIES_KEY;
	
	public final static String STR_ASTERISK;
	public final static String SYSTEM_LOCALE;
	public final static String EXT_DELIMETER;
	public final static String STR_DOUBLE_QUOTATION;
	public final static String STR_ZERO;
	public final static String STR_QUESTION_MARK;
	
	
	public final static String CURRENT_DEV_SERVER;
	public final static String CURRENT_PROD_SERVER;
	public final static String CURRENT_PC_SERVER;
	
	public final static String STR_EMPTY;
	public final static String STR_RESERVE_IS_SAVED;
	/** Use annotation value */
	public static final String META_COMM_HEADER = "commonHeader";
	/** ORACLE, IBM JDK 존재함, HP JDK 에서 존재하지 않음 */
	public static final String USER_COUNTRY = APIUtil.getProperty("user.country");
	public static final String USER_LANGUAGE = APIUtil.getProperty("user.language");
	
	static {

		STR_RESERVE_IS_SAVED = "isSaved";
		STR_EMPTY = "EMPTY";
		CURRENT_DEV_SERVER = "DEV-SERVER";
		CURRENT_PROD_SERVER = "PROD-SERVER";
		CURRENT_PC_SERVER = "DEVELOPER-PC";
		
		STR_ZERO = "0";
		STR_QUESTION_MARK = "?";
		STR_DOUBLE_QUOTATION = "\"";
		EXT_DELIMETER = ".";
		SYSTEM_LOCALE = (USER_LANGUAGE != null && USER_COUNTRY != null) ? USER_LANGUAGE.concat("_").concat(USER_COUNTRY) : "ko_KR";
		STR_ASTERISK = "*";
		RESOURCE_LASTMODIFIED_KEY = "lastModified";
		RESOURCE_PROPERTIES_KEY = "properties";
		
		FIELD_HTTP_AGENT_ID = "httpAgentId";
		FIELD_PATN_CD_TYPE = "patnCdType";
		
		DATA_IMAGE_PREFIX = "data:image/";
		STR_BASE64 = "base64";
		
		IMAGE_EXT = new HashMap<String, String>();
		IMAGE_EXT.put("jpeg", "jpg");
		IMAGE_EXT.put("jpg", "jpg");
		IMAGE_EXT.put("gif", "gif");
		IMAGE_EXT.put("png", "png");
		
		DEF_IMAGE_EXTENSION = "png";
		
		SYSTEM_ID = "IF_SYS_USR";
		
		CONTENT_TYPE_MULTIPART_FORM_DATA = "multipart/form-data";
		CONTENT_TYPE_APPLICATION_JSON = "application/json";
		CONTENT_TYPE_QUERY_STRING = "multipart/form-data";
		
		EXCLUDE_PACKAGE_STARTS = new ArrayList<String>();
		EXCLUDE_PACKAGE_STARTS.add("java.");
		EXCLUDE_PACKAGE_STARTS.add("javax.");

		VALIDATE_DTO_FULL_FIELD_ANNO = 1;
		VALIDATE_DTO_SINGLE_FIELD_ANNO = 2;
		VALIDATE_SINGLE_VALUE_PATTERN = 3;
		VALIDATE_DTO_SINGLE_FIELD_PATTERN = 4;
		
		DELIM_STR = "{}";
		DELIM_IN_NUMBER = "(\\{[0-9]?+\\})";
		STR_BLANK = "";
		DEF_DAY_FORMAT = "yyyyMMdd";
		DEF_DATE_FORMAT = "yyyyMMddHHmmss";
		GENERAL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		DEFAULT_ENCODING = "UTF-8";
		STR_COLON = ":";
		STR_WHITE_SPACE = " ";
		STR_TAB = "	";
		STR_SLASH = "/";
		STR_MID_BRACKET_R = "{";
		STR_MID_BRACKET_L = "}";
		STR_MAX_BRACKET_R = "[";
		STR_MAX_BRACKET_L = "]";
	 	LONG_ZERO_VALUE = 0L;
	 	LONG_MINUS_ONE = -1L;
	 	LONG_MAX_VALUE = 9223372036854775807L;
		INTEGER_ZERO_VALUE = 0;
		INTEGER_MINUS_ONE = -1;
		BIGDECIMAL_ZERO_VALUE = BigDecimal.ZERO;
		BIGDECIMAL_MINUS_ONE = new BigDecimal(-1);		 	
		//META_COMM_HEADER = "commonHeader";
		MESSAGE_ISNULL = "message is null.";
		HTTP_METHOD_POST = "POST";
		HTTP_METHOD_GET = "GET";
		STR_UNDERBAR = "_";
		STR_HYPHEN = "-";
		STR_AT = "@";
		STR_DOT = ".";
		STR_COMA = ",";
		STR_SPEC_COMA = "，";
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
		USER_DIR = APIUtil.getProperty("user.dir");
		FILE_ENCODING = APIUtil.getProperty("file.encoding");
	}
}
