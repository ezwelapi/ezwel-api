package com.ezwel.htl.interfaces.commons.configure.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.annotation.XmlElement;

import com.ezwel.htl.interfaces.commons.abstracts.APIObject;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel(description="시설 매핑 설정")
public class FaclMappingConfig extends APIObject {
	
	@APIFields(description = "시설 형태소 매핑 확정율")
	private BigDecimal faclMorpMappingPersent;

	@APIFields(description = "펜션 좌표 매치 M 범위")
	private BigDecimal pensionCordMatchCriteria;
	
	@APIFields(description = "호텔 좌표 매치 M 범위")
	private BigDecimal hotelCordMatchCriteria;
	
	@APIFields(description = "콘도 좌표 매치 M 범위")
	private BigDecimal condoCordMatchCriteria;
	
	@APIFields(description = "리조트 좌표 매치 M 범위")
	private BigDecimal resortCordMatchCriteria;

	@APIFields(description = "모텔 좌표 매치 M 범위")
	private BigDecimal motelCordMatchCriteria;
	
	@APIFields(description = "게스트하우스 좌표 매치 M 범위")
	private BigDecimal guestCordMatchCriteria;
	
	@APIFields(description = "레지던스 좌표 매치 M 범위")
	private BigDecimal residenceCordMatchCriteria;
	
	@APIFields(description = "기타 좌표 매치 M 범위")
	private BigDecimal etcCordMatchCriteria;
	
	@APIFields(description = "유형별 좌표 매치 범위 데이터")
	private static Map<List<String>, BigDecimal> typeCordMatchCriteriaData;
	
	static {
		typeCordMatchCriteriaData = new HashMap<List<String>, BigDecimal>();
		typeCordMatchCriteriaData.put(new ArrayList<String>(Arrays.asList(new String[] {"G002T03", "팬션", "펜션", "pension"})), null);
		typeCordMatchCriteriaData.put(new ArrayList<String>(Arrays.asList(new String[] {"G002T01", "호탤", "호텔", "hotel"})), null);
		typeCordMatchCriteriaData.put(new ArrayList<String>(Arrays.asList(new String[] {"G002T02", "리조트", "콘도", "resort", "condo"})), null);
		typeCordMatchCriteriaData.put(new ArrayList<String>(Arrays.asList(new String[] {"G002T06", "레지던스", "래지던스", "residence"})), null);
		typeCordMatchCriteriaData.put(new ArrayList<String>(Arrays.asList(new String[] {"G002T05", "게스트하우스", "개스트하우스", "게스트", "개스트", "guest house",  "guesthouse", "guest"})), null);
		typeCordMatchCriteriaData.put(new ArrayList<String>(Arrays.asList(new String[] {"G002T04", "모텔", "모탤", "motel"})), null);
		typeCordMatchCriteriaData.put(new ArrayList<String>(Arrays.asList(new String[] {"기타", "etc"})), null);
	}
	
	public FaclMappingConfig() {
		this.reset();
	}
	
	private void reset() {
		//default
		faclMorpMappingPersent = new BigDecimal(70);
		pensionCordMatchCriteria = new BigDecimal(20);
		hotelCordMatchCriteria = new BigDecimal(50);
		condoCordMatchCriteria = new BigDecimal(100);
		resortCordMatchCriteria = new BigDecimal(100);
		motelCordMatchCriteria = new BigDecimal(20);
		guestCordMatchCriteria = new BigDecimal(20);
		residenceCordMatchCriteria = new BigDecimal(30);
		etcCordMatchCriteria = new BigDecimal(40);
	}
	
	public BigDecimal getTypeCordMatchCriteriaData(String roomType, String[] faclMorp) {
		
		BigDecimal out = null;
		
		if(faclMorp != null && faclMorp.length > 0) {
			
			for(Entry<List<String>, BigDecimal> entry : typeCordMatchCriteriaData.entrySet()) {
				
				for(String morp : faclMorp) {
					if(entry.getKey().contains(morp)) {
						out = entry.getValue();
						break;
					}					
				}
				
				if(out != null) {
					break;
				}
			}
		}
		
		if(out == null) {
			out = typeCordMatchCriteriaData.get(new ArrayList<String>(Arrays.asList(new String[] {"기타", "etc"})));
		}
		
		return out;
	}
	
	private void setTypeCordMatchCriteriaData(String faclType, BigDecimal value) {
		
		for(Entry<List<String>, BigDecimal> entry : typeCordMatchCriteriaData.entrySet()) {
			if(entry.getKey().contains(faclType)) {
				entry.setValue(value);
				break;
			}					
		}
	}
	
	public BigDecimal getFaclMorpMappingPersent() {
		return faclMorpMappingPersent;
	}

	@XmlElement
	public void setFaclMorpMappingPersent(BigDecimal faclMorpMappingPersent) {
		this.faclMorpMappingPersent = faclMorpMappingPersent;
	}

	public BigDecimal getPensionCordMatchCriteria() {
		return pensionCordMatchCriteria;
	}

	@XmlElement
	public void setPensionCordMatchCriteria(BigDecimal pensionCordMatchCriteria) {
		this.pensionCordMatchCriteria = pensionCordMatchCriteria;
		this.setTypeCordMatchCriteriaData("pension", pensionCordMatchCriteria);
	}

	public BigDecimal getHotelCordMatchCriteria() {
		return hotelCordMatchCriteria;
	}

	@XmlElement
	public void setHotelCordMatchCriteria(BigDecimal hotelCordMatchCriteria) {
		this.hotelCordMatchCriteria = hotelCordMatchCriteria;
		this.setTypeCordMatchCriteriaData("hotel", hotelCordMatchCriteria);
	}

	public BigDecimal getCondoCordMatchCriteria() {
		return condoCordMatchCriteria;
	}

	@XmlElement
	public void setCondoCordMatchCriteria(BigDecimal condoCordMatchCriteria) {
		this.condoCordMatchCriteria = condoCordMatchCriteria;
		this.setTypeCordMatchCriteriaData("condo", condoCordMatchCriteria);
	}

	public BigDecimal getResortCordMatchCriteria() {
		return resortCordMatchCriteria;
	}

	@XmlElement
	public void setResortCordMatchCriteria(BigDecimal resortCordMatchCriteria) {
		this.resortCordMatchCriteria = resortCordMatchCriteria;
		this.setTypeCordMatchCriteriaData("resort", resortCordMatchCriteria);
	}

	public BigDecimal getEtcCordMatchCriteria() {
		return etcCordMatchCriteria;
	}

	@XmlElement
	public void setMotelCordMatchCriteria(BigDecimal motelCordMatchCriteria) {
		this.motelCordMatchCriteria = motelCordMatchCriteria;
		this.setTypeCordMatchCriteriaData("motel", motelCordMatchCriteria);
	}

	public BigDecimal getGuestCordMatchCriteria() {
		return guestCordMatchCriteria;
	}

	@XmlElement
	public void setGuestCordMatchCriteria(BigDecimal guestCordMatchCriteria) {
		this.guestCordMatchCriteria = guestCordMatchCriteria;
		this.setTypeCordMatchCriteriaData("guest", guestCordMatchCriteria);
	}

	public BigDecimal getResidenceCordMatchCriteria() {
		return residenceCordMatchCriteria;
	}

	@XmlElement
	public void setResidenceCordMatchCriteria(BigDecimal residenceCordMatchCriteria) {
		this.residenceCordMatchCriteria = residenceCordMatchCriteria;
		this.setTypeCordMatchCriteriaData("residence", residenceCordMatchCriteria);
	}

	@XmlElement
	public void setEtcCordMatchCriteria(BigDecimal etcCordMatchCriteria) {
		this.etcCordMatchCriteria = etcCordMatchCriteria;
		this.setTypeCordMatchCriteriaData("etc", etcCordMatchCriteria);
	}

	public BigDecimal getMotelCordMatchCriteria() {
		return motelCordMatchCriteria;
	}

}
