package com.ezwel.htl.interfaces.commons.constants;

import com.ezwel.htl.interfaces.commons.annotation.APIType;

/**
 * <pre>
 * API Constants
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */
@APIType(description="운영 상수 관리")
public class ManagedConstants {

	private final static String IF_SERVER_WEB_ROOT_KEY;
	private final static String WEB_ROOT_KEY_NAME;
	private final static String CONFIG_XML_SERVER_URI;
	
	private final static String PROD_SERVER_IP_RANGE;
	private final static String PROD_SERVER_DOMAIN;
	private final static String DEV_SERVER_IP_RANGE;
	private final static String DEV_SERVER_DOMAIN;
	
	static {
		
		IF_SERVER_WEB_ROOT_KEY = "ezwel_if_server";
		WEB_ROOT_KEY_NAME = "webAppRootKey";
		
		CONFIG_XML_SERVER_URI = "/server/configXML";
		
		PROD_SERVER_IP_RANGE = "xxx.xxx.xxx.*";
		PROD_SERVER_DOMAIN = "http://ezc-api.ezwel.com/API1.0";
		
		DEV_SERVER_IP_RANGE = "192.168.110.83";
		DEV_SERVER_DOMAIN = "http://ezc-api.dev.ezwel.com/API1.0";		
	}

	public static String getIfServerWebRootKey() {
		return IF_SERVER_WEB_ROOT_KEY;
	}

	public static String getWebRootKeyName() {
		return WEB_ROOT_KEY_NAME;
	}

	public static String getConfigXmlServerUri() {
		return CONFIG_XML_SERVER_URI;
	}

	public static String getProdServerIpRange() {
		return PROD_SERVER_IP_RANGE;
	}

	public static String getProdServerDomain() {
		return PROD_SERVER_DOMAIN;
	}

	public static String getDevServerIpRange() {
		return DEV_SERVER_IP_RANGE;
	}

	public static String getDevServerDomain() {
		return DEV_SERVER_DOMAIN;
	}
	
	
	
}
