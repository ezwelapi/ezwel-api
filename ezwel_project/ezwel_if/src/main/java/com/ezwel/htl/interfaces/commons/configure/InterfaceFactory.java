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

import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.configure.data.InterfaceChannel;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.data.AgentInfoDTO;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigDTO;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;

/**
 * <pre>
 * 인터페이스 설정 XML로딩 및 캐쉬 데이터 핸들링 클래스
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 14.
 */
@APIType(description="인터페이스 설정 XML로딩 및 캐쉬 데이터 핸들링 클래스")
public class InterfaceFactory {

	private static final Logger logger = LoggerFactory.getLogger(InterfaceFactory.class);
	
	private static Map<String, List<HttpConfigDTO>> interfaceChannels;
	
	private PropertyUtil propertyUtil;
	
	static {
		interfaceChannels = new LinkedHashMap<String, List<HttpConfigDTO>>();
	}
	
	private String configXmlPath;

	public InterfaceFactory() {
		this.init();
	}
	
	private void init() {
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
		String cacheId = chanId.concat(OperateConstants.STR_HYPHEN).concat(agentId);
		logger.debug("- cacheId key : {}", cacheId);
		return cacheId;
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
					cld.addAllAgentList(ifc.getAgentList());
					cld.addAllInsideConfigList(ifc.getInsideChans());
					cld.addAllOutsideConfigList(ifc.getOutsideChans());
					
					logger.debug("# Agent Size : {}", ifc.getAgentList().size());
					logger.debug("# InsideChans Channel Size : {}", ifc.getInsideChans().size());
					logger.debug("# OutsideChans Channel Size : {}", ifc.getOutsideChans().size());
					
					initInterfaceChannels(cld);
					
					logger.debug("# Real Cached Size : {}", interfaceChannels.size());
					logger.debug("# interfaceChannels : {}", interfaceChannels);
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
	
	
	private void initInterfaceChannels(ChannelListData channelListData) {
		logger.debug("[START] initInterfaceChannels");
		initInterfaceChannels(channelListData, 0);
	}
	
	private void initInterfaceChannels(ChannelListData channelListData, int index) {
		logger.debug("[PROC] agentItemList.size : {}, index : {}", channelListData.getAgentList().size(), index);
		
		AgentInfoDTO agent = new AgentInfoDTO();
		propertyUtil.copySameProperty(channelListData.getAgentList().get(index), agent);
		
		//방향 : inside interface list
		Iterator<HttpConfigDTO> insideIterator = channelListData.getInsideConfigList().iterator();
        while (insideIterator.hasNext()) {
        	HttpConfigDTO inside = insideIterator.next();
			
			inside.setAgentName(agent.getAgentName());
			inside.setHttpAgentId(agent.getHttpAgentId());
			inside.setHttpApiKey(agent.getInsideApiKey());
			//logger.debug("inside channel : {}", item);
			enter(inside);
		}
	
		//방향 : outside interface list
		Iterator<HttpConfigDTO> outsideIterator = channelListData.getOutsideConfigList().iterator();
        while (outsideIterator.hasNext()) {
	        HttpConfigDTO outside = outsideIterator.next();

			outside.setAgentName(agent.getAgentName());
			outside.setHttpAgentId(agent.getHttpAgentId());
			outside.setHttpApiKey(agent.getOutsideApiKey());					
			//logger.debug("outside channel : {}", item);
			enter(outside);
		}
        
        index = index + 1;
        if(channelListData.getAgentList().size() > index) {
        	initInterfaceChannels(channelListData, index);
        }
        else {
        	logger.debug("[END] initInterfaceChannels");
        }
	}
	
	private void enter(HttpConfigDTO item) {
		
		if(APIUtil.isEmpty(item.getHttpAgentId()) && APIUtil.isEmpty(item.getHttpAgentGroupId())) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9300, "인터페이스 팩토리 초기화 실패. 인터페이스 설정파일에 httpAgentId, httpAgentGroupId가 모두 존재하지 않는 채널이 존재합니다. channel : {}", item);
		}
	
		String cacheId = null;
		HttpConfigDTO agentGroup = null;
		//httpAgentId 별 무조건 캐쉬
		if(APIUtil.isNotEmpty(item.getHttpAgentId())) {
			
			cacheId = getCacheId(item.getChanId(), item.getHttpAgentId());
			//local cache list
			List<HttpConfigDTO> channelList = getChannelList(cacheId);
			//set cache id
			item.setCacheId(cacheId);
			//add cacheId '{}' item -> channelList 
			channelList.add(item);
			logger.debug("# Channel Cache Id : {}, channelList({})", cacheId, channelList.size());
			//set local cache 
			interfaceChannels.put(cacheId, channelList);
			
			if(APIUtil.isNotEmpty(item.getHttpAgentGroupId())) {
				
				agentGroup = new HttpConfigDTO();
				//new instance property
				if(propertyUtil.copySameProperty(item, agentGroup)) {
					
					cacheId = getCacheId(item.getChanId(), agentGroup.getHttpAgentGroupId());
					//set cache id
					agentGroup.setCacheId(cacheId);
					//local cache list
					List<HttpConfigDTO> groupChannelList = getChannelList(cacheId);				
					//add cacheId '{}' item -> channelList 
					groupChannelList.add(agentGroup);
					logger.debug("# Group Channel Cache Id : {}, groupChannelList({})", cacheId, groupChannelList.size());
					//set local cache 
					interfaceChannels.put(cacheId, groupChannelList);					
				}
			}			
		}
	}
	
	private List<HttpConfigDTO> getChannelList(String cacheId) {
		
		List<HttpConfigDTO> channelList = interfaceChannels.get(cacheId);
		if(channelList == null) {
			channelList = new ArrayList<HttpConfigDTO>();
		}
		
		return channelList;
	}
	
	public void destroyFactory() {
		if(interfaceChannels != null) {
			interfaceChannels.clear();
		}
	}

	private class ChannelListData {
		
		private List<HttpConfigDTO> insideConfigList;
		private List<HttpConfigDTO> outsideConfigList;
		private List<AgentInfoDTO> agentList;
		
		public List<HttpConfigDTO> getInsideConfigList() {
			return insideConfigList;
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
		
		public void addAllOutsideConfigList(List<HttpConfigDTO> outsideConfigList) {
			if(this.outsideConfigList == null) {
				this.outsideConfigList = new ArrayList<HttpConfigDTO>();
			}
			this.outsideConfigList.addAll(outsideConfigList);
		}
		
		public List<AgentInfoDTO> getAgentList() {
			return agentList;
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
}
