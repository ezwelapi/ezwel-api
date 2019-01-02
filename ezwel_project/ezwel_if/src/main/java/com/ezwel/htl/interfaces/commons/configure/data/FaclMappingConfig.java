package com.ezwel.htl.interfaces.commons.configure.data;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;

import com.ezwel.htl.interfaces.commons.abstracts.APIObject;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel(description="시설 매핑 설정")
public class FaclMappingConfig extends APIObject {
	
	@APIFields(description = "시설 형태소 매핑 확정율")
	private BigDecimal faclMorpMappingPersent;

	@APIFields(description = "좌표 매치 M 범위")
	private BigDecimal cordMatchCriteria;
	
	public FaclMappingConfig() {
		this.reset();
	}
	
	private void reset() {
		faclMorpMappingPersent = new BigDecimal(60);
		cordMatchCriteria = new BigDecimal(50);
	}

	public BigDecimal getFaclMorpMappingPersent() {
		return faclMorpMappingPersent;
	}

	@XmlElement
	public void setFaclMorpMappingPersent(BigDecimal faclMorpMappingPersent) {
		this.faclMorpMappingPersent = faclMorpMappingPersent;
	}

	public BigDecimal getCordMatchCriteria() {
		return cordMatchCriteria;
	}

	@XmlElement
	public void setCordMatchCriteria(BigDecimal cordMatchCriteria) {
		this.cordMatchCriteria = cordMatchCriteria;
	}

	
}
