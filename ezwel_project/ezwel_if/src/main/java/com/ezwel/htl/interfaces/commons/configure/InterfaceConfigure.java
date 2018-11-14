package com.ezwel.htl.interfaces.commons.configure;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.utils.APIUtil;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 14.
 */
public class InterfaceConfigure {

	private static final Logger logger = LoggerFactory.getLogger(InterfaceConfigure.class);
	
	private static Map<String, Map<String, Object>> configureMap; 
	
	static {
		configureMap = new LinkedHashMap<String, Map<String, Object>>();
	}
	
	private String configXmlPath;

	public InterfaceConfigure() {
		if(APIUtil.isEmpty(this.configXmlPath)) {
			this.configXmlPath = "interface-configure.xml";
		}
	}
	
	public String getConfigXmlPath() {
		return configXmlPath;
	}

	public void setConfigXmlPath(String configXmlPath) {
		this.configXmlPath = configXmlPath;
	}

	public static Map<String, Map<String, Object>> getConfigureMap() {
		return configureMap;
	}

	public static void setConfigureMap(Map<String, Map<String, Object>> configureMap) {
		InterfaceConfigure.configureMap = configureMap;
	}
	
	
}
