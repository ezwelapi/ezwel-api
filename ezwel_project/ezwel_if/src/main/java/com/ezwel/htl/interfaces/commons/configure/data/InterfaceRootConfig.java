package com.ezwel.htl.interfaces.commons.configure.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.ezwel.htl.interfaces.commons.abstracts.APIObject;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.http.data.AgentInfoSDO;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigSDO;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 14.
 */
@XmlRootElement( name = "interfaceConfigure" )
@XmlType (propOrder={"fileRepository", "faclMapping", "optionalApps", "agentList", "insideChans", "outsideChans"})
@APIModel(description="인터페이스 설정")
public class InterfaceRootConfig extends APIObject {

	@APIFields(description = "나가는 인터페이스 체널 목록")
	private List<HttpConfigSDO> outsideChans;
	
	@APIFields(description = "들어오는 인터페이스 체널 목록")
	private List<HttpConfigSDO> insideChans;
	
	@APIFields(description = "에이전트 별 agentId, insideKey, outsideKey")
	private List<AgentInfoSDO> agentList;
	
	@APIFields(description = "인터페이스 URL/FAX 이미지 다운로드 시스템 설정")
	private FileRepositoryConfig fileRepository;
	
	/*
	@APIFields(description = "서버 주소")
	private ServerAddressConfig serverAddress;*/
	
	@APIFields(description = "시설 매핑 설정")
	private FaclMappingConfig faclMapping;
	
	@APIFields(description = "인터페이스 부가기능(fax/mail/sms)")
	private OptAppsConfig optionalApps;	
	
	
	public InterfaceRootConfig() {
		this.reset();
	}
	
	private void reset() {
		outsideChans = new ArrayList<HttpConfigSDO>();
		insideChans = new ArrayList<HttpConfigSDO>();
		agentList = new ArrayList<AgentInfoSDO>();
		faclMapping = null;
		fileRepository = null;
		//serverAddress = null;
		optionalApps = null;
	}

	/*
	public ServerAddressConfig getServerAddress() {
		return serverAddress;
	}*/

	/*
	@XmlElement
	public void setServerAddress(ServerAddressConfig serverAddress) {
		this.serverAddress = serverAddress;
	}*/
	
	public List<HttpConfigSDO> getOutsideChans() {
		return outsideChans;
	}

	@XmlElementWrapper(name = "outsideChans") 
	@XmlElement(name = "httpChan")
	public void setOutsideChans(List<HttpConfigSDO> outsideChans) {
		this.outsideChans = outsideChans;
	}

	public List<HttpConfigSDO> getInsideChans() {
		return insideChans;
	}

	@XmlElementWrapper(name = "insideChans")
	@XmlElement(name = "httpChan")
	public void setInsideChans(List<HttpConfigSDO> insideChans) {
		this.insideChans = insideChans;
	}

	public List<AgentInfoSDO> getAgentList() {
		return agentList;
	}

	@XmlElementWrapper(name = "agentList")
	@XmlElement(name = "agentInfo")
	public void setAgentList(List<AgentInfoSDO> agentList) {
		this.agentList = agentList;
	}
	
	public FileRepositoryConfig getFileRepository() {
		return fileRepository;
	}

	@XmlElement
	public void setFileRepository(FileRepositoryConfig fileRepository) {
		this.fileRepository = fileRepository;
	}
	
	public FaclMappingConfig getFaclMapping() {
		return faclMapping;
	}

	@XmlElement
	public void setFaclMapping(FaclMappingConfig faclMapping) {
		this.faclMapping = faclMapping;
	}
	
	public OptAppsConfig getOptionalApps() {
		return optionalApps;
	}

	@XmlElement
	public void setOptionalApps(OptAppsConfig optionalApps) {
		this.optionalApps = optionalApps;
	}

	public void clear() {
		if(agentList != null) {
			agentList.clear();
		}
		if(insideChans != null) {
			insideChans.clear();
		}
		if(outsideChans != null) {
			outsideChans.clear();
		}
		//memory release
		fileRepository = null;
		//serverAddress = null;
		optionalApps = null;
	}
	
}
