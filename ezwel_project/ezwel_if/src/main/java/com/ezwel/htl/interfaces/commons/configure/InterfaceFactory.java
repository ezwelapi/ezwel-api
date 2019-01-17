package com.ezwel.htl.interfaces.commons.configure;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.configure.data.FaclMappingConfig;
import com.ezwel.htl.interfaces.commons.configure.data.FileRepositoryConfig;
import com.ezwel.htl.interfaces.commons.configure.data.InterfaceRootConfig;
import com.ezwel.htl.interfaces.commons.configure.data.OptAppsConfig;
import com.ezwel.htl.interfaces.commons.configure.data.ServerManagedConfig;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.data.AgentInfoSDO;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigSDO;
import com.ezwel.htl.interfaces.commons.marshaller.BeanMarshaller;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.commons.utils.ResourceUtil;

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
	
	private final static boolean IS_LOGGING = false;

	@Autowired
	private PropertyUtil propertyUtil;

	@Autowired
	private ResourceUtil resourceUtil;
	
	@Autowired
	private BeanMarshaller beanMarshaller;
	
	@APIFields(description = "인터페이스 체널 목록")
	private static Map<String, List<HttpConfigSDO>> interfaceChannels;
	
	@APIFields(description = "인터페이스 에이전트 목록")
	private static Map<String, AgentInfoSDO> interfaceAgents;
	
	@APIFields(description = "파일 저장소 정보")
	private static FileRepositoryConfig fileRepository;
	
	@APIFields(description = "운영/개발 서버 설정 정보")
	private static ServerManagedConfig serverManaged;
	
	@APIFields(description = "그룹체널 캐쉬명 접미사")
	private final static String GROUP_CACHE_POSTFIX;
	
	@APIFields(description = "로컬 호스트 주소")
	public final static String LOCAL_HOST_ADDRESS; 
	
	@APIFields(description = "로컬 호스트 이름")
	public final static String LOCAL_HOST_NAME;
	
	@APIFields(description = "로컬 호스트 정규화 된 도메인명")
	public final static String LOCAL_CANONICAL_HOST_NAME;
	
	@APIFields(description = "인터페이스 부가기능 설정정보")
	public static OptAppsConfig optionalApps;
	
	@APIFields(description = "시설 매핑 설정정보")
	public static FaclMappingConfig faclMapping;
	
	@APIFields(description = "이미지 저장 루트 경로")
	private static String imageRootPath;
	
	@APIFields(description = "서버 도메인 URI")
	private static String serverHttpDomainURI;
	
	@APIFields(description = "인터페이스 배치 에러 로그 루트 경로")
	private static String interfaceBatchErrorLogPath;
	
	@APIFields(description = "인터페이스 초기화 에러 메시지")
	private final static String INIT_ERROR_MESSAGE;
	
	@APIFields(description = "웹앱 루트키")
	private static String webRootKey;
	
	@APIFields(description = "로딩된 인터페이스 환경 XML 파일 URL")
	private static URL configXmlFileURL;
	
	@APIFields(description = "인터페이스 마스터 서버 여부")
	private static boolean isMasterServer;
	
	@APIFields(description = "인터페이스 환경 XML 파일 경로 파라메터")
	private String configXmlPath;

	@APIFields(description = "로컬 테스트여부")
	private boolean isLocalTestInit;

	@APIFields(description = "인터페이스 서버환경 프로퍼티 파일명")
	private final static String MANAGED_PROPERTIES_FILE_NAME;

	@APIFields(description = "인터페이스 운영환경 XML 파일명")
	private final static String MANAGED_XML_FILE_NAME;
	
	static {
		
		MANAGED_PROPERTIES_FILE_NAME = "interface-managed.properties";
		
		MANAGED_XML_FILE_NAME = "interface-configure.xml";
		
		INIT_ERROR_MESSAGE = "InterfaceFactory 초기화중 장애발생.";
		
		interfaceChannels = new LinkedHashMap<String, List<HttpConfigSDO>>();
		interfaceAgents = new LinkedHashMap<String, AgentInfoSDO>();
		
		GROUP_CACHE_POSTFIX = "ChannelGroup";
		
		LOCAL_HOST_ADDRESS = APIUtil.getLocalHost().getHostAddress();
		LOCAL_HOST_NAME = APIUtil.getLocalHost().getHostName();
		LOCAL_CANONICAL_HOST_NAME = APIUtil.getLocalHost().getCanonicalHostName();
	}
	
	public InterfaceFactory() {
		this.init();
	}
	
	private void init() {
		//ThreadLocal 초기화
		Local.commonHeader();
		
		if(APIUtil.isEmpty(this.configXmlPath)) {
			this.configXmlPath = MANAGED_XML_FILE_NAME;
		}
	}
	
	public String getConfigXmlPath() {
		return configXmlPath;
	}

	public void setConfigXmlPath(String configXmlPath) {
		this.configXmlPath = configXmlPath;
	}
	
	public static HttpConfigSDO getChannel(String chanId, String httpAgentId) {
		if(IS_LOGGING) {
			logger.debug("[PROC] getChannel chanId : {}, httpAgentId : {}", chanId, httpAgentId);
		}
		
		HttpConfigSDO out = null;
		List<HttpConfigSDO> channels = null;
		if(interfaceChannels != null && httpAgentId != null) {
			channels = interfaceChannels.get(getCacheId(chanId, httpAgentId));
			if(channels != null) {
				out = channels.get(0); 
			}
		}
		
		if(out == null) {
			throw new APIException("인터페이스 체널정보가 존재하지 않습니다. 채널아이디 : {}, 에이전트아이디 : {}", chanId, httpAgentId);
		}
		
		return out;
	}
	
	public static List<HttpConfigSDO> getChannelGroup(String httpAgentGroupId) {
		if(IS_LOGGING) {
			logger.debug("[PROC] getChannelGroup httpAgentGroupId : {}", httpAgentGroupId);
		}
		
		List<HttpConfigSDO> out = null;
		if(interfaceChannels != null && httpAgentGroupId != null) {
			out = interfaceChannels.get(getGroupCacheId(httpAgentGroupId)); 
		}
		
		if(out == null || out.size() == 0) {
			throw new APIException("인터페이스 그룹 체널정보가 존재하지 않습니다. 에이전트그룹아이디 : {}", httpAgentGroupId); 
		}
		
		return out;
	}

	public static String getImageRootPath() {
		return imageRootPath;
	}

	public static String getServerHttpDomainURI() {
		return serverHttpDomainURI;
	}

	public static String getInterfaceBatchErrorLogPath() {
		return interfaceBatchErrorLogPath;
	}

	public static ServerManagedConfig getServerAddress() {
		return serverManaged;
	}

	public static FileRepositoryConfig getFileRepository() {
		return fileRepository;
	}
	
	public static Map<String, List<HttpConfigSDO>> getInterfaceChannels() {
		return interfaceChannels;
	}
	
	public static Map<String, AgentInfoSDO> getInterfaceAgents() {
		return interfaceAgents;
	}
	
	public static AgentInfoSDO getInterfaceAgents(String httpAgentId) {
		return interfaceAgents.get(httpAgentId);
	}
	
	public static FaclMappingConfig getFaclMapping() {
		return faclMapping;
	}

	public static OptAppsConfig getOptionalApps() {
		return optionalApps;
	}
	
	public static URL getConfigXmlFileURL() {
		return configXmlFileURL;
	}

	public static boolean isMasterServer() {
		return isMasterServer;
	}
	
	public static String getWebRootKey() {
		return webRootKey;
	}

	public void setLocalTestInit(boolean isLocalTestInit) {
		this.isLocalTestInit = isLocalTestInit;
	}
	
	private static String getCacheId(String chanId, String agentId) {
		
		if(APIUtil.isEmpty(chanId) || APIUtil.isEmpty(agentId)) {
			throw new APIException("[getCacheId] 채널 아이디 또는 에이전트 아이디가 존재하지 않습니다. 'chanId : {}, agentId : {}'", chanId, agentId); 
		}
		String cacheId = chanId.concat(OperateConstants.STR_HYPHEN).concat(agentId);
		
		return cacheId;
	}
	
	private static String getGroupCacheId(String chanGroupId) {
		
		if(APIUtil.isEmpty(chanGroupId)) {
			throw new APIException("[getGroupCacheId] 채널 아이디 또는 에이전트 아이디가 존재하지 않습니다. 'chanGroupId : {}'", chanGroupId); 
		}
		String cacheId = chanGroupId.concat(OperateConstants.STR_HYPHEN).concat(GROUP_CACHE_POSTFIX);
		
		return cacheId;
	}
	
	private void testBeanInit() {
			
		if(propertyUtil == null) {
			propertyUtil = new PropertyUtil();
		}
		
		if(resourceUtil == null) {
			resourceUtil = new ResourceUtil();	
		}
		
		if(beanMarshaller == null) {
			beanMarshaller = new BeanMarshaller();
		}
	}
	
	public void initFactory() {
		logger.debug("[INITIALIZE] INTERFACE FACTORY ... ");
		
		if( isLocalTestInit ) {
			testBeanInit();
		}
		
		JAXBContext jaxbc = null;
		Unmarshaller unmarshaller = null;
		String classesRoot = null;
		File configureXml = null;
		InterfaceRootConfig ifc = null;
		String xmlPath = null;
		ChannelListData cld = null;
		URL resourceURL = null;
		WebApplicationContext webContext = null;
		
		//logger.debug("# 인터페이스 팩토리 초기화 방식 : {}", (isLocalTestInit ? "로컬개발도구" : "스프링"));
		
		try {
			
			URL fileURL = getClass().getResource(MANAGED_PROPERTIES_FILE_NAME);
			logger.debug("# interface-managed URL : {}", fileURL);
			
			Properties managedConfig = resourceUtil.load(fileURL);
			//logger.debug("# managedConfig : {}", managedConfig);
			
			if(managedConfig == null) {
				logger.warn("★ 인터페이스  운영환경 프로퍼티를 찾을수 없습니다. 인터페이스 펙토리가 초기화 되지 않습니다.");
				return;
			} 
			
 			// 서버 IP대역 및 URI => 자바 상수 파일에서 관리하도록 변경 20190102 (XML환경파일에서 제거)
			InterfaceFactory.serverManaged = (ServerManagedConfig) beanMarshaller.mapToBean((Map) managedConfig, ServerManagedConfig.class);
			
			webContext = ContextLoader.getCurrentWebApplicationContext();
			if(webContext != null) {   
				webRootKey = webContext.getEnvironment().getProperty(InterfaceFactory.serverManaged.getWebRootKeyName());
				isMasterServer = (InterfaceFactory.serverManaged.getIfServerWebRootKey().equals(webRootKey));
				logger.debug("# WebRootKey : {}, Environment : {}", webRootKey, webContext.getEnvironment().toString());
				if(isMasterServer) {
					logger.debug("# INTERFACE MANAGED PROPERTIES : {}", InterfaceFactory.serverManaged);
				}
			}
			else {
				if(!isLocalTestInit) {
					logger.warn("☆★☆★☆★☆ 스프링 ContextLoader에 정상등록되지 않은 WAC입니다. ContextLoader.getCurrentWebApplicationContext() is null... ☆★☆★☆★☆");
				}
			}
			
			//logger.debug("- Direct Parse Xml Config : {}", InterfaceFactory.serverManaged.getDirectParseXmlYn());
			//logger.debug("- This server is interface master server ? {}, webAppRootKey : {}", isMasterServer, webRootKey);
			
			jaxbc = JAXBContext.newInstance(InterfaceRootConfig.class);
			unmarshaller = jaxbc.createUnmarshaller();
			
			if(OperateConstants.STR_Y.equals(InterfaceFactory.serverManaged.getDirectParseXmlYn())) {
				isMasterServer = true;
				logger.debug("- Direct Parse : {}", isMasterServer);
			}
			
			if(isMasterServer || isLocalTestInit) {
				
				// 1. xmlPath를 canonical로 채크
				configureXml = new File(getConfigXmlPath());
				logger.debug("# 1. config xml -> canonical path scan -> caninocalPath : {}", getConfigXmlPath());
				
				if(!configureXml.exists()) {
					
					// 2. xmlPath를 application classes class loader아래에서 채크
					classesRoot = getClass().getResource("/").getPath();
					xmlPath = classesRoot.concat(File.separator).concat(getConfigXmlPath());
					configureXml = new File(xmlPath);
					logger.debug("# 2. config xml -> classes scan -> classes xmlPath : {}", xmlPath);
					
					if(!configureXml.exists()) {
						// 3. xmlPath를 application class loader 이하 jar에서 채크
						resourceURL = getClass().getResource(getConfigXmlPath());
						logger.debug("# 3. config xml -> current jar scan -> resourceURL : {}", resourceURL);
						if(resourceURL == null) {
							
							if(isMasterServer) {
								throw new APIException("인터페이스 설정파일 이 존재하지 않거나 경로가 잘못되었습니다. 인터페이스 설정파일 경로를 확인하세요.");
							}
							else {
								logger.error("인터페이스 설정파일 이 존재하지 않거나 경로가 잘못되었습니다. 인터페이스 설정파일 경로를 확인하세요.");
								return;
							}
						}
						else {
							logger.debug("# find application jar");
						}
					}
					else {
						logger.debug("# find application classes");
					}
				}
				else {
					logger.debug("# find filesystem canonicalPath");
				}
				
				if(resourceURL == null) {
					
					if(!configureXml.exists()) {
						
						if(isMasterServer) {
							throw new APIException("인터페이스 설정파일 이 존재하지 않거나 경로가 잘못되었습니다. 인터페이스 설정파일 경로를 확인하세요.");
						}
						else {
							logger.error("인터페이스 설정파일 이 존재하지 않거나 경로가 잘못되었습니다. 인터페이스 설정파일 경로를 확인하세요.");
							return;
						}
					}
					
					if(!configureXml.canRead()) {
						if(isMasterServer) {
							throw new APIException("인터페이스 설정파일을 읽을 권한이 없습니다. 설정파일 권한을 확인하세요.");
						}
						else {
							logger.error("인터페이스 설정파일을 읽을 권한이 없습니다. 설정파일 권한을 확인하세요.");
							return;
						}
					}
					
					resourceURL = Paths.get(configureXml.getCanonicalPath()).toUri().toURL();
				}
				
				configXmlFileURL = resourceURL;
				logger.debug(" ==> CONFIG_XML_FILE_PATH : {}", configXmlFileURL);
				
			}
			else {
				
				// 인터페이스 마스터 서버에게 설정 정보를 가저온다.
				if(APIUtil.getServerAddress() == null) {
					if(isMasterServer) {
						throw new APIException("인터페이스 환경파일에 설정된 개발 또는 운영서버의 IP또는 IP대역과 현제 서버의 IP가 일치하지 않습니다.");
					}
					else {
						logger.error("인터페이스 환경파일에 설정된 개발 또는 운영서버의 IP또는 IP대역과 현제 서버의 IP가 일치하지 않습니다.");
						return;
					}
				}
				else if(APIUtil.getServerAddress().equals(OperateConstants.CURRENT_PROD_SERVER)) {
					// prod server  InterfaceFactory.serverManaged
					resourceURL = new URL(InterfaceFactory.serverManaged.getProdMasterServerName().concat(InterfaceFactory.serverManaged.getConfigXmlServerUri()));
				}
				else if(APIUtil.getServerAddress().equals(OperateConstants.CURRENT_DEV_SERVER)) {
					// dev server
					resourceURL = new URL(InterfaceFactory.serverManaged.getDevMasterServerName().concat(InterfaceFactory.serverManaged.getConfigXmlServerUri()));
				}
				else {
					// developer local pc server
					resourceURL = new URL(InterfaceFactory.serverManaged.getDevMasterServerName().concat(InterfaceFactory.serverManaged.getConfigXmlServerUri()));
				}
			}
			
			ifc = (InterfaceRootConfig) unmarshaller.unmarshal(resourceURL);
			
			if(ifc != null) {
				
				if(ifc.getAgentList() != null && ifc.getAgentList().size() > 0) {
					
					cld = new ChannelListData();
					cld.addAllAgentList(ifc.getAgentList());
					cld.addAllInsideConfigList(ifc.getInsideChans());
					cld.addAllOutsideConfigList(ifc.getOutsideChans());
					
					initInterfaceChannels(cld);

					InterfaceFactory.fileRepository = ifc.getFileRepository();
					
					/**
					 * 초기화 서버 별 이미지 경로 & 도메인 URI 세팅 
					 */
					String imageRootPath = null;
					String serverHttpDomainUri = null;
					String interfaceBatchErrorLogPath = null;
					
					if(APIUtil.getServerAddress() == null) {
						if(isMasterServer) {
							throw new APIException("인터페이스 환경파일에 설정된 개발 또는 운영서버의 IP또는 IP대역과 현제 서버의 IP가 일치하지 않습니다.");
						}
						else {
							logger.error("인터페이스 환경파일에 설정된 개발 또는 운영서버의 IP또는 IP대역과 현제 서버의 IP가 일치하지 않습니다.");
							return;
						}
					}
					else if(APIUtil.getServerAddress().equals(OperateConstants.CURRENT_PROD_SERVER)) {
						// prod server
						imageRootPath = InterfaceFactory.getFileRepository().getBuildImage().getProdRootPath();
						serverHttpDomainUri = InterfaceFactory.getServerAddress().getProdServerL4Domain();
						interfaceBatchErrorLogPath = InterfaceFactory.getFileRepository().getErrorLog().getProdRootPath();
					}
					else if(APIUtil.getServerAddress().equals(OperateConstants.CURRENT_DEV_SERVER)) {
						// dev server
						imageRootPath = InterfaceFactory.getFileRepository().getBuildImage().getDevRootPath();
						serverHttpDomainUri = InterfaceFactory.getServerAddress().getDevServerL4Domain();
						interfaceBatchErrorLogPath = InterfaceFactory.getFileRepository().getErrorLog().getDevRootPath();
					}
					else {
						// developer local pc server
						imageRootPath = InterfaceFactory.getFileRepository().getBuildImage().getLocalRootPath();
						serverHttpDomainUri = InterfaceFactory.getServerAddress().getDevServerL4Domain();
						interfaceBatchErrorLogPath = InterfaceFactory.getFileRepository().getErrorLog().getLocalRootPath();
					}
					
					InterfaceFactory.serverHttpDomainURI = serverHttpDomainUri;
					InterfaceFactory.imageRootPath = getRevisionPath(imageRootPath);
					InterfaceFactory.interfaceBatchErrorLogPath = getRevisionPath(interfaceBatchErrorLogPath);
					
					/** 시설정보 매핑 설정 */
					InterfaceFactory.faclMapping = ifc.getFaclMapping();
					
					/** 인터페이스 부가기능 설정 정보 */
					InterfaceFactory.optionalApps = ifc.getOptionalApps();
				}
				
				if(IS_LOGGING)  {
					logger.debug("# LOCAL_HOST_ADDRESS : {}", LOCAL_HOST_ADDRESS);
					logger.debug("# LOCAL_HOST_NAME : {}", LOCAL_HOST_NAME);
					logger.debug("# LOCAL_CANONICAL_HOST_NAME : {}", LOCAL_CANONICAL_HOST_NAME);
									
					logger.debug("# imageRootPath : {}", InterfaceFactory.imageRootPath);
					logger.debug("# serverHttpDomainURI : {}", InterfaceFactory.serverHttpDomainURI);
					logger.debug("# interfaceBatchErrorLogPath : {}", InterfaceFactory.interfaceBatchErrorLogPath);	
	
					logger.debug("# Agent Size : {}", ifc.getAgentList().size());
					logger.debug("# InsideChans Channel Size : {}", ifc.getInsideChans().size());
					logger.debug("# OutsideChans Channel Size : {}", ifc.getOutsideChans().size());
					
					logger.debug("# Real Cached Size : {}", interfaceChannels.size());
					//logger.debug("# interfaceChannels : {}", interfaceChannels);
					
					logger.debug("# fileRepository : {}", InterfaceFactory.fileRepository);
					logger.debug("# faclMapping : {}", InterfaceFactory.faclMapping);	
					logger.debug("# optionalApps : {}", InterfaceFactory.optionalApps);	
				}
			}
		} catch (JAXBException e) {
			
			if(isMasterServer) {
				throw new APIException(INIT_ERROR_MESSAGE, e);
			}
			else {
				logger.error(INIT_ERROR_MESSAGE, e);
			}
		} catch (IOException e) {
			
			if(isMasterServer) {
				throw new APIException(INIT_ERROR_MESSAGE, e);
			}
			else {
				logger.error(INIT_ERROR_MESSAGE, e);
			}
		} catch (Exception e) {
			
			if(isMasterServer) {
				throw new APIException(INIT_ERROR_MESSAGE, e);
			}
			else {
				logger.error(INIT_ERROR_MESSAGE, e);
			}
		}
		finally {
			
			if(ifc != null) {
				ifc.clear();
			}
			
			if(cld != null) {
				cld.clear();
			}
			
			//ThreadLocal 종료
			Local.remove();
		}
	}
	
	
	private String getRevisionPath(String path) {
		
		if(path != null) {
			if(path.endsWith(File.separator)) {
				path = path.substring(0, path.lastIndexOf(File.separator));
			}
			else if(path.endsWith(OperateConstants.STR_SLASH)) {
				// 동작 머신의 OS가 윈도우일때 /로 세팅된 경우
				path = path.substring(0, path.lastIndexOf(OperateConstants.STR_SLASH));
			}
		}
		return path;
	}
	
	private void initInterfaceChannels(ChannelListData channelListData) {
		if(IS_LOGGING)  {
			logger.debug("[START] initInterfaceChannels");
		}
		initInterfaceChannels(channelListData, 0);
	}
	
	private void initInterfaceChannels(ChannelListData channelListData, int index) {
		if(IS_LOGGING) {
			logger.debug("[PROC] agentItemList.size : {}, index : {}", channelListData.getAgentList().size(), index);
		}
		
		AgentInfoSDO agent = (AgentInfoSDO) propertyUtil.copySameProperty(channelListData.getAgentList().get(index), AgentInfoSDO.class);
		//에이전트(제휴사)캐쉬
		interfaceAgents.put(agent.getHttpAgentId(), agent);
		
		//방향 : inside interface list
		Iterator<HttpConfigSDO> insideIterator = channelListData.getInsideConfigList().iterator();
        while (insideIterator.hasNext()) {
        	HttpConfigSDO inside = (HttpConfigSDO) propertyUtil.copySameProperty(insideIterator.next(), HttpConfigSDO.class);
        	propertyUtil.copySameProperty(agent, inside);
        	
			inside.setHttpApiKey(agent.getInsideApiKey());
			inside.setIfReqtDirt(OperateConstants.STR_I);
			//logger.debug("inside channel : {}", item);
			enter(agent, inside, false);
		}
	
		//방향 : outside interface list
		Iterator<HttpConfigSDO> outsideIterator = channelListData.getOutsideConfigList().iterator();
        while (outsideIterator.hasNext()) {
	        HttpConfigSDO outside = (HttpConfigSDO) propertyUtil.copySameProperty(outsideIterator.next(), HttpConfigSDO.class);
	        propertyUtil.copySameProperty(agent, outside, "httpAgentId");
	        
			outside.setHttpApiKey(agent.getOutsideApiKey());
			outside.setIfReqtDirt(OperateConstants.STR_O);
			//logger.debug("outside channel : {}", item);
			enter(agent, outside, true);
		}
        
        index = index + 1;
        if(channelListData.getAgentList().size() > index) {
        	initInterfaceChannels(channelListData, index);
        }
        else {
        	if(IS_LOGGING) {
        		logger.debug("[END] initInterfaceChannels");
        	}
        }
	}
	
	private void enter(AgentInfoSDO agent, HttpConfigSDO item, boolean isOutside) {
		
		if(APIUtil.isEmpty(item.getHttpAgentId()) && APIUtil.isEmpty(item.getHttpAgentGroupId())) {
			logger.warn("인터페이스 설정파일에 httpAgentId, httpAgentGroupId가 모두 존재하지 않는 채널이 존재합니다. channel : {}", item);
			//pass
			return;
		}
	
		String cacheId = null;
		HttpConfigSDO agentGroup = null;
		//httpAgentId 별 무조건 캐쉬
		if(APIUtil.isNotEmpty(item.getHttpAgentId())) {
			
			//outside일때 HttpAgentId가 없으면 pass
			if(isOutside) {
				if(APIUtil.isEmpty(item.getHttpAgentId())) {
					//pass
					return;
				}
				else {
					if(!agent.getHttpAgentId().equals(item.getHttpAgentId())) {
						//pass
						return;
					}
				}
			}
			
			cacheId = getCacheId(item.getChanId(), item.getHttpAgentId());
			//local cache list
			List<HttpConfigSDO> channelList = getChannelList(cacheId);
			//set cache id
			item.setCacheId(cacheId);
			//add cacheId '{}' item -> channelList 
			channelList.add(item);
			//logger.debug("# Channel Cache Id : {}, channelList({})", cacheId, channelList.size());
			//set local cache 
			interfaceChannels.put(cacheId, channelList);
			
			if(isOutside) {
				// outside 의 경우만 그룹 캐쉬 허용 (inside는 필요없음) 캐쉬사이즈 줄임 20181221
				if(APIUtil.isNotEmpty(item.getHttpAgentGroupId())) {
					
					agentGroup = new HttpConfigSDO();
					//new instance property
					if(propertyUtil.copySameProperty(item, agentGroup)) {
						
						cacheId = getGroupCacheId(agentGroup.getHttpAgentGroupId());
						//set cache id
						agentGroup.setCacheId(cacheId);
						//local cache list
						List<HttpConfigSDO> groupChannelList = getChannelList(cacheId);
						
						if(!groupChannelList.contains(agentGroup)) {
							//add cacheId '{}' item -> channelList 
							groupChannelList.add(agentGroup);
							
							if(IS_LOGGING) {
								logger.debug("# Group Channel Cache Id : {}, groupChannelList({})", cacheId, groupChannelList.size());
							}
							//set local cache 
							interfaceChannels.put(cacheId, groupChannelList);					
						}
					}
				}			
			}
		}
	}
	
	private List<HttpConfigSDO> getChannelList(String cacheId) {
		
		List<HttpConfigSDO> channelList = interfaceChannels.get(cacheId);
		if(channelList == null) {
			channelList = new ArrayList<HttpConfigSDO>();
		}
		
		return channelList;
	}
	
	public void destroyFactory() {
		
		if(interfaceChannels != null) {
			interfaceChannels.clear();
		}
		if(interfaceAgents != null) {
			interfaceAgents.clear();
		}
		
		Local.remove();
	}

	private class ChannelListData {
		
		private List<HttpConfigSDO> insideConfigList;
		private List<HttpConfigSDO> outsideConfigList;
		private List<AgentInfoSDO> agentList;
		
		public List<HttpConfigSDO> getInsideConfigList() {
			return insideConfigList;
		}
		
		public void addAllInsideConfigList(List<HttpConfigSDO> insideConfigList) {
			if(this.insideConfigList == null) {
				this.insideConfigList = new ArrayList<HttpConfigSDO>();
			}
			this.insideConfigList.addAll(insideConfigList);
		}	
		
		public List<HttpConfigSDO> getOutsideConfigList() {
			return outsideConfigList;
		}
		
		public void addAllOutsideConfigList(List<HttpConfigSDO> outsideConfigList) {
			if(this.outsideConfigList == null) {
				this.outsideConfigList = new ArrayList<HttpConfigSDO>();
			}
			this.outsideConfigList.addAll(outsideConfigList);
		}
		
		public List<AgentInfoSDO> getAgentList() {
			return agentList;
		}
		
		public void addAllAgentList(List<AgentInfoSDO> agentList) {
			if(this.agentList == null) {
				this.agentList = new ArrayList<AgentInfoSDO>();
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

	/**
	 * 로컬 테스트용 인터페이스 팩토리 초기화
	 */
	public static void initLocalTestInterfaceFactory() {
		initLocalTestInterfaceFactory(null);
	}
	
	/**
	 * 로컬 테스트용 인터페이스 팩토리 초기화(설정 파일 경로 입력 가능)
	 * @param xmlConfigPath
	 */
	public static void initLocalTestInterfaceFactory(String xmlConfigPath) {
		InterfaceFactory factory = new InterfaceFactory();
		if(xmlConfigPath == null) {
			factory.setConfigXmlPath("/interfaces/interface-configure.xml");
		}
		else {
			factory.setConfigXmlPath(xmlConfigPath);
		}
		factory.setLocalTestInit(true); /* 로컬 테스트시 필수 세팅 */
		factory.initFactory();
	}
}
