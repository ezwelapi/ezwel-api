package com.ezwel.htl.interfaces.commons.configure;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.configure.dto.InterfaceChannel;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.dto.HttpConfigDTO;
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
	
	private static Map<String, HttpConfigDTO> configureMap; 
	
	static {
		configureMap = new LinkedHashMap<String, HttpConfigDTO>();
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
	
	public static HttpConfigDTO getChannel(String chanId) {
		HttpConfigDTO out = null;
		if(configureMap != null) {
			out = configureMap.get(chanId); 
		}
		return out;
	}
	
	public void initFactory() {
		
		JAXBContext jaxbc = null;
		Unmarshaller unmarshaller = null;
		String classesRoot = null;
		File configureXml = null;
		InterfaceChannel ifc = null;
		String xmlPath = null;
		
		try {
			
			jaxbc = JAXBContext.newInstance(InterfaceChannel.class);
			unmarshaller = jaxbc.createUnmarshaller();
			classesRoot = this.getClass().getClassLoader().getResource("").getPath();
			xmlPath = classesRoot.concat(File.separator).concat(getConfigXmlPath());
			logger.debug("\nclassesRoot : {}\nxmlPath : {}", classesRoot, xmlPath);

			configureXml = new File(xmlPath);
			
			if(!configureXml.exists()) {
				throw new APIException("인터페이스 설정파일이 존재하지 않거나 경로가 잘못되었습니다.");
			}
			
			ifc = (InterfaceChannel) unmarshaller.unmarshal(configureXml);
			
			for(HttpConfigDTO item : ifc.getOutsideChans()) {
				logger.debug("outside channel : {}", item);
				configureMap.put(item.getChanId(), item);
			}
			
			for(HttpConfigDTO item : ifc.getInsideChans()) {
				logger.debug("inside channel : {}", item);
				configureMap.put(item.getChanId(), item);				
			}
			
		} catch (JAXBException e) {
			throw new APIException("InterfaceFactory 초기화중 장애발생.", e);
		} 
	}
	
	public void destroyFactory() {
		if(configureMap != null) {
			configureMap.clear();
		}
	}
}
