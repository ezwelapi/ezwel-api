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
import com.ezwel.htl.interfaces.commons.constants.OperateCode;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.dto.AgentInfoDTO;
import com.ezwel.htl.interfaces.commons.http.dto.HttpConfigDTO;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 14.
 */
public class InterfaceFactory {

	private static final Logger logger = LoggerFactory.getLogger(InterfaceFactory.class);
	
	private static Map<String, List<HttpConfigDTO>> interfaceChannels;
	
	private PropertyUtil propertyUtil;
	
	static {
		interfaceChannels = new LinkedHashMap<String, List<HttpConfigDTO>>();
	}
	
	private String configXmlPath;

	public InterfaceFactory() {
		if(APIUtil.isEmpty(this.configXmlPath)) {
			this.configXmlPath = "interface-configure.xml";
		}
		if(propertyUtil == null) {
			propertyUtil = new PropertyUtil();
		}
		
	}
	
	public String getConfigXmlPath() {
		return configXmlPath;
	}

	public void setConfigXmlPath(String configXmlPath) {
		this.configXmlPath = configXmlPath;
	}
	
	public static HttpConfigDTO getChannel(String chanId, String httpAgentId) {
		/*
		StackTraceElement beforeStack = StackTraceUtil.getCurrentStack(InterfaceFactory.class);
		
		beforeStack.getClassName()
		beforeStack.getMethodName()
		*/
		HttpConfigDTO out = null;
		List<HttpConfigDTO> channels = null;
		String key = null;
		if(interfaceChannels != null && httpAgentId != null) {
			key = chanId.concat(OperateCode.STR_HYPHEN).concat(httpAgentId);
			channels = interfaceChannels.get(key);
			logger.debug("channel key : {}", key);
			if(channels != null) {
				out = channels.get(0); 
			}
		}
		return out;
	}
	
	
	public static List<HttpConfigDTO> getChannelGroup(String chanId, String httpAgentGroupId) {
		/*
		StackTraceElement beforeStack = StackTraceUtil.getCurrentStack(InterfaceFactory.class);
		
		beforeStack.getClassName()
		beforeStack.getMethodName()
		*/
		List<HttpConfigDTO> out = null;
		String key = null;
		if(interfaceChannels != null && httpAgentGroupId != null) {
			key = chanId.concat(OperateCode.STR_HYPHEN).concat(httpAgentGroupId);
			logger.debug("channelGroup key : {}", key);
			out = interfaceChannels.get(key); 
		}
		return out;
	}
	
	public static Map<String, List<HttpConfigDTO>> getInterfaceChannels() {
		return interfaceChannels;
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
			
			//logger.debug("- AgentSize : {}", ifc.getAgentList().size());
			//agent list (제휴사 목록)
			for(AgentInfoDTO agent : ifc.getAgentList()) {
				
				//방향 : inside interface list
				for(HttpConfigDTO inside : ifc.getInsideChans()) {
					
					inside.setAgentName(agent.getAgentName());
					inside.setHttpAgentId(agent.getHttpAgentId());
					inside.setHttpApiKey(agent.getInsideApiKey());
					//logger.debug("inside channel : {}", item);
					enter(inside);
				}
				
			}
			
			for(AgentInfoDTO agent : ifc.getAgentList()) {

				//방향 : outside interface list
				for(HttpConfigDTO outside : ifc.getOutsideChans()) {
					
					outside.setAgentName(agent.getAgentName());
					outside.setHttpAgentId(agent.getHttpAgentId());
					outside.setHttpApiKey(agent.getOutsideApiKey());					
					//logger.debug("outside channel : {}", item);
					enter(outside);
				}
			}
			
		} catch (JAXBException e) {
			throw new APIException("InterfaceFactory 초기화중 장애발생.", e);
		} 
	}
	
	private void enter(HttpConfigDTO item) {
		
		String channelId = null;
		List<HttpConfigDTO> channelList = null;
		
		if(APIUtil.isNotEmpty(item.getHttpAgentGroupId())) {
			channelId = item.getHttpAgentGroupId();
		}
		else if(APIUtil.isNotEmpty(item.getHttpAgentId())) {
			channelId = item.getHttpAgentId();
		}
		else {
			channelId = null;
		}
		
		if(channelId != null) {
			
			channelId = item.getChanId().concat(OperateCode.STR_HYPHEN).concat(channelId);
			//logger.debug("channelId : {}", channelId);
			
			channelList = interfaceChannels.get(channelId);
			if(channelList == null) {
				channelList = new ArrayList<HttpConfigDTO>();
			}
			
			//logger.debug("channelList.size() : {}", channelList.size() + 1);
			//logger.debug("cached class : {}", item.getClass());
			//logger.debug("cached data : {}", item);
			
			channelList.add(item);
			
			interfaceChannels.put(channelId, channelList);
		}
	}
	
	public void destroyFactory() {
		if(interfaceChannels != null) {
			interfaceChannels.clear();
		}
	}
}
