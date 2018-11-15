package com.ezwel.htl.interfaces.commons.configure.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.http.dto.HttpConfigDTO;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 14.
 */
@XmlRootElement( name = "interfaceChannel" )
public class InterfaceChannel {


	@APIFields(description = "나가는 인터페이스 체널 목록")
	private List<HttpConfigDTO> outsideChans;
	
	@APIFields(description = "들어오는 인터페이스 체널 목록")
	private List<HttpConfigDTO> insideChans;
	
	public InterfaceChannel() {
		this.reset();
	}
	
	private void reset() {
		outsideChans = new ArrayList<HttpConfigDTO>();
		insideChans = new ArrayList<HttpConfigDTO>();
	}

	public List<HttpConfigDTO> getOutsideChans() {
		return outsideChans;
	}

	@XmlElementWrapper(name = "outsideChans") 
	@XmlElement(name = "httpChan")
	public void setOutsideChans(List<HttpConfigDTO> outsideChans) {
		this.outsideChans = outsideChans;
	}

	public List<HttpConfigDTO> getInsideChans() {
		return insideChans;
	}

	@XmlElementWrapper(name = "insideChans")
	@XmlElement(name = "httpChan")
	public void setInsideChans(List<HttpConfigDTO> insideChans) {
		this.insideChans = insideChans;
	}
	
	
}
