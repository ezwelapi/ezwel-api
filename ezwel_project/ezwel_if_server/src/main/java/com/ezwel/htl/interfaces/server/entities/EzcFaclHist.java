package com.ezwel.htl.interfaces.server.entities;

//import lombok.Data;
//import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
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
@Alias("ezcFaclHist")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="시설 정보 내역", description="시설 정보 내역 ( EZC_FACL_HIST )", modelTypes="TABLE")
public class EzcFaclHist extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "시설 내역 일련번호", maxLength=10, required=true, constraints="EZC_FACL_HIST_PK(P),SYS_C0011082(C) EZC_FACL_HIST_PK(UNIQUE)")
	private BigDecimal faclHistSeq;

	@APIFields(description = "제휴사 코드", maxLength=20, required=true, constraints="SYS_C0011083(C)")
	private String partnerCd;

	//@APIFields(description = "제휴사 코드 유형", maxLength=8, required=true, constraints="SYS_C0011084(C)")
	//private String partnerCdType;

	@APIFields(description = "시설 구분", maxLength=8, required=true, constraints="SYS_C0011085(C)")
	private String faclDiv;

	@APIFields(description = "상품 코드", maxLength=100, required=true, constraints="SYS_C0011086(C)")
	private String goodsCd;

	@APIFields(description = "시설 명 한글", maxLength=100, required=true, constraints="SYS_C0011087(C)")
	private String faclNmKor;

	@APIFields(description = "시설 명 영문", maxLength=100, required=true, constraints="SYS_C0011088(C)")
	private String faclNmEng;

	@APIFields(description = "숙소 유형", maxLength=8, required=true, constraints="SYS_C0011089(C)")
	private String roomType;

	@APIFields(description = "숙소 등급", maxLength=8, required=true, constraints="SYS_C0011090(C)")
	private String roomClass;

	@APIFields(description = "판매 시작 일자", maxLength=8, required=true, constraints="SYS_C0011091(C)")
	private String saleStartDd;

	@APIFields(description = "판매 종료 일자", maxLength=8, required=true, constraints="SYS_C0011092(C)")
	private String saleEndDd;

	@APIFields(description = "체크인 시분", maxLength=4, required=true, constraints="SYS_C0011093(C)")
	private String checkInTm;

	@APIFields(description = "체크아웃 시분", maxLength=4, required=true, constraints="SYS_C0011094(C)")
	private String checkOutTm;

	@APIFields(description = "지역 코드", maxLength=10, required=true, constraints="SYS_C0011095(C)")
	private String areaCd;

	@APIFields(description = "도시 코드", maxLength=10, required=true, constraints="SYS_C0011096(C)")
	private String cityCd;

	@APIFields(description = "주소 유형", maxLength=8, required=true, constraints="SYS_C0011097(C)")
	private String addrType;

	@APIFields(description = "주소", maxLength=200, required=true, constraints="SYS_C0011098(C)")
	private String addr;

	@APIFields(description = "우편번호", maxLength=7)
	private String post;

	@APIFields(description = "전화 번호", maxLength=20)
	private String telNum;

	@APIFields(description = "위도", maxLength=20)
	private String coordY;

	@APIFields(description = "경도", maxLength=20)
	private String coordX;

	@APIFields(description = "최저 금액", maxLength=8)
	private BigDecimal minAmt;

	@APIFields(description = "상세 설명 PC", maxLength=4000)
	private String detailDescPc;

	@APIFields(description = "상세 설명 모바일", maxLength=4000)
	private String detailDescM;

	@APIFields(description = "트립어드바이저 프로퍼티 ID", maxLength=100)
	private String tripPropId;

	@APIFields(description = "대표 이미지 URL", maxLength=200)
	private String mainImgUrl;

	@APIFields(description = "이미지 변경 여부", maxLength=1, required=true, constraints="SYS_C0011099(C)")
	private String imgChangeYn;

	@APIFields(description = "판매 중지 일시", maxLength=14, isDate=true, dateFormat="yyyyMMddHHmmss")
	private String saleStopDt;

	@APIFields(description = "API 동기화 일시", maxLength=14, isDate=true, dateFormat="yyyyMMddHHmmss")
	private String apiSyncDt;

	@APIFields(description = "확정 상태", maxLength=8, required=true, constraints="SYS_C0011100(C)")
	private String confirmStatus;

	@APIFields(description = "시설 상태", maxLength=8, required=true, constraints="SYS_C0011101(C)")
	private String faclStatus;

	@APIFields(description = "반려 내용", maxLength=1000)
	private String rejectContent;

	@APIFields(description = "사용 여부", maxLength=1, required=true, constraints="SYS_C0011102(C)")
	private String useYn;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011103(C)")
	private String regId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyyMMddHHmmss", required=true, constraints="SYS_C0011104(C)")
	private String regDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyyMMddHHmmss")
	private String modiDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());

	@APIFields(description = "시설 코드", maxLength=10, constraints="EZC_FACL_HIST_IF01(NONUNIQUE)")
	private BigDecimal faclCd;


	
	public BigDecimal getFaclHistSeq() {
		return faclHistSeq;
	}

	public void setFaclHistSeq(BigDecimal faclHistSeq) {
		this.faclHistSeq = faclHistSeq;
	}

	public String getPartnerCd() {
		return partnerCd;
	}

	public void setPartnerCd(String partnerCd) {
		this.partnerCd = partnerCd;
	}

	public String getFaclDiv() {
		return faclDiv;
	}

	public void setFaclDiv(String faclDiv) {
		this.faclDiv = faclDiv;
	}

	public String getGoodsCd() {
		return goodsCd;
	}

	public void setGoodsCd(String goodsCd) {
		this.goodsCd = goodsCd;
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

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getRoomClass() {
		return roomClass;
	}

	public void setRoomClass(String roomClass) {
		this.roomClass = roomClass;
	}

	public String getSaleStartDd() {
		return saleStartDd;
	}

	public void setSaleStartDd(String saleStartDd) {
		this.saleStartDd = saleStartDd;
	}

	public String getSaleEndDd() {
		return saleEndDd;
	}

	public void setSaleEndDd(String saleEndDd) {
		this.saleEndDd = saleEndDd;
	}

	public String getCheckInTm() {
		return checkInTm;
	}

	public void setCheckInTm(String checkInTm) {
		this.checkInTm = checkInTm;
	}

	public String getCheckOutTm() {
		return checkOutTm;
	}

	public void setCheckOutTm(String checkOutTm) {
		this.checkOutTm = checkOutTm;
	}

	public String getAreaCd() {
		return areaCd;
	}

	public void setAreaCd(String areaCd) {
		this.areaCd = areaCd;
	}

	public String getCityCd() {
		return cityCd;
	}

	public void setCityCd(String cityCd) {
		this.cityCd = cityCd;
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

	public BigDecimal getMinAmt() {
		return minAmt;
	}

	public void setMinAmt(BigDecimal minAmt) {
		this.minAmt = minAmt;
	}

	public String getDetailDescPc() {
		return detailDescPc;
	}

	public void setDetailDescPc(String detailDescPc) {
		this.detailDescPc = detailDescPc;
	}

	public String getDetailDescM() {
		return detailDescM;
	}

	public void setDetailDescM(String detailDescM) {
		this.detailDescM = detailDescM;
	}

	public String getTripPropId() {
		return tripPropId;
	}

	public void setTripPropId(String tripPropId) {
		this.tripPropId = tripPropId;
	}

	public String getMainImgUrl() {
		return mainImgUrl;
	}

	public void setMainImgUrl(String mainImgUrl) {
		this.mainImgUrl = mainImgUrl;
	}

	public String getImgChangeYn() {
		return imgChangeYn;
	}

	public void setImgChangeYn(String imgChangeYn) {
		this.imgChangeYn = imgChangeYn;
	}

	public String getSaleStopDt() {
		return saleStopDt;
	}

	public void setSaleStopDt(String saleStopDt) {
		this.saleStopDt = saleStopDt;
	}

	public String getApiSyncDt() {
		return apiSyncDt;
	}

	public void setApiSyncDt(String apiSyncDt) {
		this.apiSyncDt = apiSyncDt;
	}

	public String getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public String getFaclStatus() {
		return faclStatus;
	}

	public void setFaclStatus(String faclStatus) {
		this.faclStatus = faclStatus;
	}

	public String getRejectContent() {
		return rejectContent;
	}

	public void setRejectContent(String rejectContent) {
		this.rejectContent = rejectContent;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
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

	public BigDecimal getFaclCd() {
		return faclCd;
	}

	public void setFaclCd(BigDecimal faclCd) {
		this.faclCd = faclCd;
	}


}	
