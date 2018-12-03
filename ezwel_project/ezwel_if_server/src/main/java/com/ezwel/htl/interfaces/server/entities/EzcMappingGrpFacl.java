package com.ezwel.htl.interfaces.server.entities;

//import lombok.Data;
//import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractEntity;
import java.math.BigDecimal;


/**
 * <b>History : Generated Code Skeleton iCodeManager Made by KSW</b>
 * <pre>
 * 버전              성명                                일자                                       변경내용
 * -------    ----------------     -------------------		-----------------
 * 0.0.1      iCodeManager         2018-11-23 18:58:45      신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:58:45
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
//@Data
@Alias("ezcMappingGrpFacl")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="맵핑 그룹 시설", description="맵핑 그룹 시설 ( EZC_MAPPING_GRP_FACL )", modelTypes="TABLE")
public class EzcMappingGrpFacl extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "그룹 시설 코드", maxLength=10, required=true, constraints="EZC_MAPPING_GRP_FACL_PK(P),SYS_C0011120(C) EZC_MAPPING_GRP_FACL_PK(UNIQUE)")
	private BigDecimal grpFaclCd;

	@APIFields(description = "도시 코드", maxLength=10, required=true, constraints="SYS_C0011121(C)")
	private String cityCd;

	@APIFields(description = "지역 코드", maxLength=10, required=true, constraints="SYS_C0011122(C)")
	private String areaCd;

	@APIFields(description = "시설 명 한글", maxLength=100, required=true, constraints="SYS_C0011123(C)")
	private String faclNmKor;

	@APIFields(description = "시설 명 영문", maxLength=100, required=true, constraints="SYS_C0011124(C)")
	private String faclNmEng;

	@APIFields(description = "숙소 등급", maxLength=8, required=true, constraints="SYS_C0011125(C)")
	private String roomClass;

	@APIFields(description = "숙소 유형", maxLength=8, required=true, constraints="SYS_C0011126(C)")
	private String roomType;

	@APIFields(description = "주소 유형", maxLength=8, required=true, constraints="SYS_C0011127(C)")
	private String addrType;

	@APIFields(description = "주소", maxLength=200, required=true, constraints="SYS_C0011128(C)")
	private String addr;

	@APIFields(description = "우편번호", maxLength=7)
	private String post;

	@APIFields(description = "전화 번호", maxLength=20, required=true, constraints="SYS_C0011129(C)")
	private String telNum;

	@APIFields(description = "위도", maxLength=20, required=true, constraints="SYS_C0011130(C)")
	private String coordY;

	@APIFields(description = "경도", maxLength=20, required=true, constraints="SYS_C0011131(C)")
	private String coordX;

	@APIFields(description = "트립어드바이저 프로퍼티 ID", maxLength=100)
	private String tripPropId;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011132(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011133(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss")
	private String modiDt;


	
	public BigDecimal getGrpFaclCd() {
		return grpFaclCd;
	}

	public void setGrpFaclCd(BigDecimal grpFaclCd) {
		this.grpFaclCd = grpFaclCd;
	}

	public String getCityCd() {
		return cityCd;
	}

	public void setCityCd(String cityCd) {
		this.cityCd = cityCd;
	}

	public String getAreaCd() {
		return areaCd;
	}

	public void setAreaCd(String areaCd) {
		this.areaCd = areaCd;
	}

	public String getFaclNmKor() {
		return faclNmKor;
	}

	public void setFaclNmKor(String faclNmKor) {
		this.faclNmKor = faclNmKor;
	}

	public String getFaclNmEng() {
		return faclNmEng;
	}

	public void setFaclNmEng(String faclNmEng) {
		this.faclNmEng = faclNmEng;
	}

	public String getRoomClass() {
		return roomClass;
	}

	public void setRoomClass(String roomClass) {
		this.roomClass = roomClass;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getAddrType() {
		return addrType;
	}

	public void setAddrType(String addrType) {
		this.addrType = addrType;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getTelNum() {
		return telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	public String getCoordY() {
		return coordY;
	}

	public void setCoordY(String coordY) {
		this.coordY = coordY;
	}

	public String getCoordX() {
		return coordX;
	}

	public void setCoordX(String coordX) {
		this.coordX = coordX;
	}

	public String getTripPropId() {
		return tripPropId;
	}

	public void setTripPropId(String tripPropId) {
		this.tripPropId = tripPropId;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getModiId() {
		return modiId;
	}

	public void setModiId(String modiId) {
		this.modiId = modiId;
	}

	public String getModiDt() {
		return modiDt;
	}

	public void setModiDt(String modiDt) {
		this.modiDt = modiDt;
	}


}	
