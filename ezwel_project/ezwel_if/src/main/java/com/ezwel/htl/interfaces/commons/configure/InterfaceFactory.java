package com.ezwel.htl.interfaces.commons.configure;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
	
	private static Map<String, HttpConfigDTO> channelMap;
	
	private static Map<String, List<HttpConfigDTO>> channelGroupMap;
	
	static {
		channelMap = new LinkedHashMap<String, HttpConfigDTO>();
		channelGroupMap = new LinkedHashMap<String, List<HttpConfigDTO>>();
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
		if(channelMap != null) {
			out = channelMap.get(chanId); 
		}
		return out;
	}
	
	public static List<HttpConfigDTO> getChannelGroup(String chanGroupId) {
		List<HttpConfigDTO> out = null;
		if(channelMap != null) {
			out = channelGroupMap.get(chanGroupId); 
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
		List<HttpConfigDTO> chanGroupList = null;
		
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
				channelMap.put(item.getChanId(), item);

				if(APIUtil.isNotEmpty(item.getChanGroupId())) {
					chanGroupList = channelGroupMap.get(item.getChanGroupId());
					if(chanGroupList == null) {
						chanGroupList = new ArrayList<HttpConfigDTO>();
					}
					chanGroupList.add(item);
					channelGroupMap.put(item.getChanGroupId(), chanGroupList);
				}
			}
			
			for(HttpConfigDTO item : ifc.getInsideChans()) {
				logger.debug("inside channel : {}", item);
				channelMap.put(item.getChanId(), item);		
				
				if(APIUtil.isNotEmpty(item.getChanGroupId())) {
					chanGroupList = channelGroupMap.get(item.getChanGroupId());
					if(chanGroupList == null) {
						chanGroupList = new ArrayList<HttpConfigDTO>();
					}
					chanGroupList.add(item);
					channelGroupMap.put(item.getChanGroupId(), chanGroupList);
				}				
			}
			
		} catch (JAXBException e) {
			throw new APIException("InterfaceFactory 초기화중 장애발생.", e);
		} 
	}
	
	public void destroyFactory() {
		if(channelMap != null) {
			channelMap.clear();
		}
	}
}
