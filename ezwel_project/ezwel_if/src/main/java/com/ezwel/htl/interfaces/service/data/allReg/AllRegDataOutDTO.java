package com.ezwel.htl.interfaces.service.data.allReg;

import java.util.List;


import com.ezwel.htl.interfaces.commons.abstracts.AbstractDTO;



import com.ezwel.htl.interfaces.commons.annotation.APIFields;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 13.
 */

public class AllRegDataOutDTO extends AbstractDTO {



	
	@APIFields(description = "전체시설일괄등록 output 시설명칭", required=true, maxLength=200)
	private String pdtName;
	
	@APIFields(description = "전체시설일괄등록 output 시설명칭(영문)", maxLength=200)
	private String pdtNameEng;
	
	@APIFields(description = "전체시설일괄등록 output 상품코드", required=true, maxLength=100)
	private String pdtNo;
	
	@APIFields(description = "전체시설일괄등록 output 트립어드바이져아이디", maxLength=10)
	private String tripadvisorId;
	
	@APIFields(description = "전체시설일괄등록 output 대표이미지", required=true, maxLength=500)
	private String mainImage;
	
	@APIFields(description = "전체시설일괄등록 output 이미지변경여부", required=true, maxLength=1)
	private String changeImage;
	
	@APIFields(description = "전체시설일괄등록 output 서브이미지")
	private List<AllRegSubImagesOutDTO> subImages = null;
	
	@APIFields(description = "전체시설일괄등록 output 상품상세설명(HTML)", required=true, maxLength=50000)
	private String descHTML;
	
	@APIFields(description = "전체시설일괄등록 output 상품상세설명(MOBILE)", maxLength=50000)
	private String descMobile;
	
	@APIFields(description = "전체시설일괄등록 output 판매시작일", required=true, maxLength=8)
	private String sellStartDate;
	
	@APIFields(description = "전체시설일괄등록 output 판매종료일", required=true, maxLength=8)
	private String sellEndDate;
	
	@APIFields(description = "전체시설일괄등록 output 판매기간최저가", required=true)
	private String sellPrice;
	
	@APIFields(description = "전체시설일괄등록 output 시도명", required=true, maxLength=100)
	private String sido;
	
	@APIFields(description = "전체시설일괄등록 output 시도코드", required=true, maxLength=2)
	private String sidoCode;
	
	@APIFields(description = "전체시설일괄등록 output 군구명", required=true, maxLength=100)
	private String gungu;
	
	@APIFields(description = "전체시설일괄등록 output 군구코드", required=true, maxLength=5)
	private String gunguCode;
	
	@APIFields(description = "전체시설일괄등록 output 시설주소", required=true, maxLength=300)
	private String address;
	
	@APIFields(description = "전체시설일괄등록 output 주소타입", required=true, maxLength=1)
	private String addressType;
	
	@APIFields(description = "전체시설일괄등록 output 우편번호", required=true, maxLength=6)
	private String zipCode;
	
	@APIFields(description = "전체시설일괄등록 output 시설대표전화번호", required=true, maxLength=20)
	private String telephone;
	
	@APIFields(description = "전체시설일괄등록 output 채크인시간", maxLength=5)
	private String checkInTime;
	
	@APIFields(description = "전체시설일괄등록 output 체크아웃시간", maxLength=5)
	private String checkOutTime;
	
	@APIFields(description = "전체시설일괄등록 output 시설유형코드", required=true, maxLength=5)
	private String typeCode;
	
	@APIFields(description = "전체시설일괄등록 output 시설등급코드", required=true, maxLength=5)
	private String gradeCode;
	
	@APIFields(description = "전체시설일괄등록 output 위도", maxLength=20)
	private String mapX;
	
	@APIFields(description = "전체시설일괄등록 output 경도", maxLength=20)
	private String mapY;
	
	@APIFields(description = "전체시설일괄등록 output 부대시설", required=true, maxLength=500)
	private String serviceCodes;

	public String getPdtName() {
		return pdtName;
	}

	public void setPdtName(String pdtName) {
		this.pdtName = pdtName;
	}

	public String getPdtNameEng() {
		return pdtNameEng;
	}

	public void setPdtNameEng(String pdtNameEng) {
		this.pdtNameEng = pdtNameEng;
	}

	public String getPdtNo() {
		return pdtNo;
	}

	public void setPdtNo(String pdtNo) {
		this.pdtNo = pdtNo;
	}

	public String getTripadvisorId() {
		return tripadvisorId;
	}

	public void setTripadvisorId(String tripadvisorId) {
		this.tripadvisorId = tripadvisorId;
	}

	public String getMainImage() {
		return mainImage;
	}

	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}

	public String getChangeImage() {
		return changeImage;
	}

	public void setChangeImage(String changeImage) {
		this.changeImage = changeImage;
	}

	public List<AllRegSubImagesOutDTO> getAllRegSubImagesOutDTOs() {
		return subImages;
	}

	public void setAllRegSubImagesOutDTOs(List<AllRegSubImagesOutDTO> subImages) {
		this.subImages = subImages;
	}

	public String getDescHTML() {
		return descHTML;
	}

	public void setDescHTML(String descHTML) {
		this.descHTML = descHTML;
	}

	public String getDescMobile() {
		return descMobile;
	}

	public void setDescMobile(String descMobile) {
		this.descMobile = descMobile;
	}

	public String getSellStartDate() {
		return sellStartDate;
	}

	public void setSellStartDate(String sellStartDate) {
		this.sellStartDate = sellStartDate;
	}

	public String getSellEndDate() {
		return sellEndDate;
	}

	public void setSellEndDate(String sellEndDate) {
		this.sellEndDate = sellEndDate;
	}

	public String getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(String sellPrice) {
		this.sellPrice = sellPrice;
	}

	public String getSido() {
		return sido;
	}

	public void setSido(String sido) {
		this.sido = sido;
	}

	public String getSidoCode() {
		return sidoCode;
	}

	public void setSidoCode(String sidoCode) {
		this.sidoCode = sidoCode;
	}

	public String getGungu() {
		return gungu;
	}

	public void setGungu(String gungu) {
		this.gungu = gungu;
	}

	public String getGunguCode() {
		return gunguCode;
	}

	public void setGunguCode(String gunguCode) {
		this.gunguCode = gunguCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}

	public String getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(String checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	public String getMapX() {
		return mapX;
	}

	public void setMapX(String mapX) {
		this.mapX = mapX;
	}

	public String getMapY() {
		return mapY;
	}

	public void setMapY(String mapY) {
		this.mapY = mapY;
	}

	public String getServiceCodes() {
		return serviceCodes;
	}

	public void setServiceCodes(String serviceCodes) {
		this.serviceCodes = serviceCodes;
	}

}
