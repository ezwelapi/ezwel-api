package com.ezwel.htl.interfaces.commons.configure.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.http.data.AgentInfoSDO;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigSDO;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 14.
 */
@XmlRootElement( name = "interfaceChannel" )
@XmlType (propOrder={"agentList","insideChans","outsideChans"})
public class InterfaceChannel {


	@APIFields(description = "나가는 인터페이스 체널 목록")
	private List<HttpConfigSDO> outsideChans;
	
	@APIFields(description = "들어오는 인터페이스 체널 목록")
	private List<HttpConfigSDO> insideChans;
	
	@APIFields(description = "에이전트 별 agentId, insideKey, outsideKey")
	private List<AgentInfoSDO> agentList;
		
	public InterfaceChannel() {
		this.reset();
	}
	
	private void reset() {
		outsideChans = new ArrayList<HttpConfigSDO>();
		insideChans = new ArrayList<HttpConfigSDO>();
		agentList = new ArrayList<AgentInfoSDO>();
	}

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
	}
	
}
