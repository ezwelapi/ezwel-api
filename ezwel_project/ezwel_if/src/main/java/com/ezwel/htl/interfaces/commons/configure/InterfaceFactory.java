package com.ezwel.htl.interfaces.commons.configure;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.configure.dto.InterfaceChannel;
import com.ezwel.htl.interfaces.commons.constants.InterfaceCode;
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
		if(interfaceChannels != null && httpAgentId != null) {
			channels = interfaceChannels.get(getCacheId(chanId, httpAgentId));
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
		if(interfaceChannels != null && httpAgentGroupId != null) {
			out = interfaceChannels.get(getCacheId(chanId, httpAgentGroupId)); 
		}
		return out;
	}
	
	public static Map<String, List<HttpConfigDTO>> getInterfaceChannels() {
		return interfaceChannels;
	}
	
	private static String getCacheId(String chanId, String agentId) {
		if(APIUtil.isEmpty(chanId) || APIUtil.isEmpty(agentId)) {
			throw new APIException("[getCacheId] 채널 아이디 또는 에이전트 아이디가 존재하지 않습니다. 'chanId : {}, agentId : {}'", chanId, agentId); 
		}
		String cacheId = chanId.concat(OperateCode.STR_HYPHEN).concat(agentId);
		logger.debug("- cacheId key : {}", cacheId);
		return cacheId;
	}
	
	private class ChannelListData {
		
		private List<HttpConfigDTO> insideConfigList;
		private List<HttpConfigDTO> outsideConfigList;
		private List<AgentInfoDTO> agentList;
		
		public List<HttpConfigDTO> getInsideConfigList() {
			return insideConfigList;
		}
		
		public void setInsideConfigList(List<HttpConfigDTO> insideConfigList) {
			this.insideConfigList = insideConfigList;
		}
		
		public void addAllInsideConfigList(List<HttpConfigDTO> insideConfigList) {
			if(this.insideConfigList == null) {
				this.insideConfigList = new ArrayList<HttpConfigDTO>();
			}
			this.insideConfigList.addAll(insideConfigList);
		}	
		
		public List<HttpConfigDTO> getOutsideConfigList() {
			return outsideConfigList;
		}
		
		public void setOutsideConfigList(List<HttpConfigDTO> outsideConfigList) {
			this.outsideConfigList = outsideConfigList;
		}
		
		public void addAllOutsideConfigList(List<HttpConfigDTO> outsideConfigList) {
			if(this.outsideConfigList == null) {
				this.outsideConfigList = new ArrayList<HttpConfigDTO>();
			}
			this.outsideConfigList.addAll(outsideConfigList);
		}
		
		public List<AgentInfoDTO> getAgentList() {
			return agentList;
		}
		
		public void setAgentList(List<AgentInfoDTO> agentList) {
			this.agentList = agentList;
		}
		
		public void addAllAgentList(List<AgentInfoDTO> agentList) {
			if(this.agentList == null) {
				this.agentList = new ArrayList<AgentInfoDTO>();
			}
			this.agentList.addAll(agentList);
		}
		
		public void clear() {
			if(insideConfigList != null) {
				insideConfigList.clear();
			}
			if(outsideConfigList != null) {
				outsideConfigList.clear();
			}
			if(agentList != null) {
				agentList.clear();
			}			
		}
	}
	
	public void initFactory() {
		
		JAXBContext jaxbc = null;
		Unmarshaller unmarshaller = null;
		String classesRoot = null;
		File configureXml = null;
		InterfaceChannel ifc = null;
		String xmlPath = null;
		ChannelListData cld = null;
		
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
			
			if(ifc != null) {
				
				if(ifc.getAgentList() != null && ifc.getAgentList().size() > 0) {
					
					cld = new ChannelListData();
					cld.addAllInsideConfigList(ifc.getInsideChans());
					cld.addAllOutsideConfigList(ifc.getOutsideChans());
					
					logger.debug("# Agent Size : {}", ifc.getAgentList().size());
					logger.debug("# InsideChans Channel Size : {}", ifc.getInsideChans().size());
					logger.debug("# OutsideChans Channel Size : {}", ifc.getOutsideChans().size());
					logger.debug("# Total Cahced Size : {}", ifc.getAgentList().size() * (ifc.getInsideChans().size() + ifc.getOutsideChans().size()));
					initInterfaceChannels(cld, ifc.getAgentList());
					
					logger.debug("# Real Cached Size : {}", interfaceChannels.size());
				}
				
				
			}
		} catch (JAXBException e) {
			throw new APIException("InterfaceFactory 초기화중 장애발생.", e);
		}
		finally {
			if(ifc != null) {
				ifc.clear();
			}
			
			if(cld != null) {
				cld.clear();
			}
		}
	}
	
	
	private void initInterfaceChannels(ChannelListData channelListData, List<AgentInfoDTO> agentItemList) {
		logger.debug("[START] initInterfaceChannels");
		initInterfaceChannels(channelListData, agentItemList, 0);
	}
	
	private void initInterfaceChannels(ChannelListData channelListData, List<AgentInfoDTO> agentItemList, int index) {
		logger.debug("[START] agentItemList.size : {}, index : {}", agentItemList.size(), index);
		
		AgentInfoDTO agent = new AgentInfoDTO();
		propertyUtil.copySameProperty(agentItemList.get(index), agent, true);
		
		//방향 : inside interface list
		Iterator<HttpConfigDTO> insideIterator = channelListData.getInsideConfigList().iterator();
        while (insideIterator.hasNext()){
        	HttpConfigDTO inside = insideIterator.next();
			
			inside.setAgentName(agent.getAgentName());
			inside.setHttpAgentId(agent.getHttpAgentId());
			inside.setHttpApiKey(agent.getInsideApiKey());
			//logger.debug("inside channel : {}", item);
			enter(inside);
		}
	
		//방향 : outside interface list
		Iterator<HttpConfigDTO> outsideIterator = channelListData.getOutsideConfigList().iterator();
        while (outsideIterator.hasNext()){
	        HttpConfigDTO outside = outsideIterator.next();
	        	
			outside.setAgentName(agent.getAgentName());
			outside.setHttpAgentId(agent.getHttpAgentId());
			outside.setHttpApiKey(agent.getOutsideApiKey());					
			//logger.debug("outside channel : {}", item);
			enter(outside);
		}
        
        index = index + 1;
        if(agentItemList.size() > index) {
        	initInterfaceChannels(channelListData, agentItemList, index);
        }
        else {
        	logger.debug("[END] initInterfaceChannels \n{}", interfaceChannels);
        }
	}
	
	private void enter(HttpConfigDTO item) {
		
		if(APIUtil.isEmpty(item.getHttpAgentId()) && APIUtil.isEmpty(item.getHttpAgentGroupId())) {
			throw new APIException(InterfaceCode.RESPONSE_CODE_9300, "인터페이스 팩토리 초기화 실패. 인터페이스 설정파일에 httpAgentId, httpAgentGroupId가 모두 존재하지 않는 채널이 존재합니다. channel : {}", item);
		}
		
		//httpAgentGroupId가 존재할 경우 GroupId 묶음으로 캐쉬
		if(APIUtil.isNotEmpty(item.getHttpAgentGroupId())) {
			enterChannel(item, item.getHttpAgentGroupId(), true);
		}
		
		//httpAgentId 별 무조건 캐쉬
		if(APIUtil.isNotEmpty(item.getHttpAgentId())) {
			enterChannel(item, item.getHttpAgentId(), false);
		}
	}
	
	
	private void enterChannel(HttpConfigDTO item, String agentId, boolean isGroup) {
		
		String cacheId = getCacheId(item.getChanId(), agentId);
		item.setCacheId(cacheId);
		
		List<HttpConfigDTO> channelList = interfaceChannels.get(cacheId);
		if(channelList == null) {
			channelList = new ArrayList<HttpConfigDTO>();
		}
		
		//logger.debug("# {} isGroup : {}, channelList.size : {}", cacheId, isGroup, channelList.size());
		if(!isGroup && channelList.size() > 0) {
			//그룹 캐쉬가 아닐경우 1회만 캐쉬
			//logger.debug("■ single channel data {} is cached ", cacheId);
			return;
		}
		
		if(channelList.indexOf(item) == -1) {
			//logger.debug("# add cacheId '{}' item -> channelList ", cacheId);
			channelList.add(item);
		
			if(isGroup) {
				logger.debug("# Channel GroupCache Id : {}, channelList({})", cacheId, channelList.size());
			}
			else {
				logger.debug("# Channel Cache Id : {}, channelList({})", cacheId, channelList.size());
			}
			//logger.debug("# cached data : {}", item);
			
			interfaceChannels.put(cacheId, channelList);
		}
		//else {
		//	logger.debug("# Passed item is : {}", item);
		//}
	}
	
	public void destroyFactory() {
		if(interfaceChannels != null) {
			interfaceChannels.clear();
		}
	}
}
