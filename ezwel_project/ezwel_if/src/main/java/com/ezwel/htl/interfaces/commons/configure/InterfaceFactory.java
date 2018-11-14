package com.ezwel.htl.interfaces.commons.configure;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 14.
 */
public class InterfaceFactory {

	private static final Logger logger = LoggerFactory.getLogger(InterfaceFactory.class);
	
	private static Map<String, Map<String, Object>> configureMap; 
	
	static {
		configureMap = new LinkedHashMap<String, Map<String, Object>>();
	}
	
	private String configXmlPath;

	public InterfaceFactory() {
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
		InterfaceFactory.configureMap = configureMap;
	}
	
	public void initFactory() {
		
		JAXBContext jaxbc = null;
		Unmarshaller unmarshaller = null;
		String classesRoot = null;
		File configureXml = null;
		URL configureXmlURL = null;
		try {
			jaxbc = JAXBContext.newInstance("");
			unmarshaller = jaxbc.createUnmarshaller();
			classesRoot = this.getClass().getClassLoader().getResource("/").getPath();
			logger.debug("classesRoot : {}", classesRoot);
			configureXml = new File(classesRoot.concat(File.pathSeparator).concat(getConfigXmlPath()));
			if(!configureXml.exists()) {
				throw new APIException("인터페이스 설정파일이 존재하지 않거나 경로가 잘못되었습니다.");
			}
			configureXmlURL = new URL(configureXml.getCanonicalPath());
			
			
		} catch (JAXBException e) {
			throw new APIException("InterfaceFactory 초기화중 장애발생.", e);
		} catch (MalformedURLException e) {
			throw new APIException("InterfaceFactory 초기화중 장애발생.", e);
		} catch (IOException e) {
			throw new APIException("InterfaceFactory 초기화중 장애발생.", e);
		}
		
	}
	
	public void destroyFactory() {
		
	}
}
