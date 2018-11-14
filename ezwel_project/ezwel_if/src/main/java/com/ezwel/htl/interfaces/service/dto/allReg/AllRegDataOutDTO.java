package com.ezwel.htl.interfaces.service.dto.allReg;

import java.util.List;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
public class AllRegDataOutDTO extends AbstractEntity {

	private String pdtName;
	private String pdtNameEng;
	private String pdtNo;
	private String tripadvisorId;
	private String mainImage;
	private String changeImage;
	private List<AllRegSubImagesOutDTO> subImages = null;
	private String descHTML;
	private String descMobile;
	private String sellStartDate;
	private String sellEndDate;
	private String sellPrice;
	private String sido;
	private String sidoCode;
	private String gungu;
	private String gunguCode;
	private String address;
	private String addressType;
	private String zipCode;
	private String telephone;
	private String checkInTime;
	private String checkOutTime;
	private String typeCode;
	private String gradeCode;
	private String mapX;
	private String mapY;
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
