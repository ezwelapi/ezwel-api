package com.ezwel.htl.interfaces.server.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

//import lombok.Data;
//import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractEntity;
import com.ezwel.htl.interfaces.server.commons.sdo.EzcFaclMappingSDO;


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
@Alias("ezcFacl")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="시설 정보", description="시설 정보 ( EZC_FACL )", modelTypes="TABLE")
public class EzcFacl extends AbstractEntity {

	@APIFields(description = "시설 코드", maxLength=10, required=true, constraints="EZC_FACL_PK(P),SYS_C0011053(C) EZC_FACL_PK(UNIQUE)")
	private BigDecimal faclCd;

	@APIFields(description = "제휴사 코드", maxLength=20, required=true, constraints="FK_EZC_PARTNER_EZC_FACL(R),SYS_C0011054(C) EZC_FACL_IF03(NONUNIQUE)")
	private String partnerCd;

	//AA쪽에서 컬럼삭제
	//@APIFields(description = "제휴사 코드 유형", maxLength=8, required=true, constraints="SYS_C0011055(C)")
	//private String partnerCdType;

	@APIFields(description = "시설 구분", maxLength=8, required=true, constraints="SYS_C0011056(C)")
	private String faclDiv;

	@APIFields(description = "제휴사 상품 코드", maxLength=100, required=true, constraints="SYS_C0011057(C)")
	private String partnerGoodsCd;

	@APIFields(description = "시설 명 한글", maxLength=100, required=true, constraints="SYS_C0011058(C)")
	private String faclNmKor;

	@APIFields(description = "시설 명 영문", maxLength=100, required=false, constraints="SYS_C0011059(C)")
	private String faclNmEng;

	@APIFields(description = "시설 명", maxLength=100)
	private String faclNm;
	
	@APIFields(description = "숙소 유형", maxLength=8, required=true, constraints="SYS_C0011060(C)")
	private String roomType;

	@APIFields(description = "숙소 등급", maxLength=8, required=true, constraints="SYS_C0011061(C)")
	private String roomClass;

	@APIFields(description = "판매 시작 일자", maxLength=8, required=true, isDate=true, dateFormat="yyyyMMdd", constraints="SYS_C0011062(C)")
	private String saleStartDd;

	@APIFields(description = "판매 종료 일자", maxLength=8, required=true, isDate=true, dateFormat="yyyyMMdd", constraints="SYS_C0011063(C)")
	private String saleEndDd;

	@APIFields(description = "체크인 시분", maxLength=4, required=false, isDate=true, dateFormat="HHmm", constraints="SYS_C0011064(C)")
	private String checkInTm;

	@APIFields(description = "체크아웃 시분", maxLength=4, required=false, isDate=true, dateFormat="HHmm", constraints="SYS_C0011065(C)")
	private String checkOutTm;

	@APIFields(description = "지역 코드", maxLength=10, required=true, constraints="FK_EZC_AREA_CD_EZC_FACL(R),SYS_C0011066(C) EZC_FACL_IF02(NONUNIQUE)")
	private String areaCd;

	@APIFields(description = "도시 코드", maxLength=10, required=true, constraints="FK_EZC_CITY_CD_EZC_FACL(R),SYS_C0011067(C) EZC_FACL_IF01(NONUNIQUE)")
	private String cityCd;

	@APIFields(description = "주소 유형", maxLength=8, required=true, constraints="SYS_C0011068(C)")
	private String addrType;

	@APIFields(description = "주소", maxLength=200, required=true, constraints="SYS_C0011069(C)")
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
	//maxLength가 0 이면 길이체크를 하지 않습니다.
	@APIFields(description = "상세 설명 PC", maxLength=0)
	private String detailDescPc;
	//maxLength가 0 이면 길이체크를 하지 않습니다.
	@APIFields(description = "상세 설명 모바일", maxLength=0)
	private String detailDescM;

	@APIFields(description = "트립어드바이저 프로퍼티 ID", maxLength=100)
	private String tripPropId;

	@APIFields(description = "대표 이미지 URL", maxLength=200)
	private String mainImgUrl;

	@APIFields(description = "이미지 변경 여부", maxLength=1, required=true, constraints="SYS_C0011070(C)")
	private String imgChangeYn;

	@APIFields(description = "판매 중지 일시", maxLength=14, isDate=true, dateFormat="yyyyMMddHHmmss")
	private String saleStopDt;

	@APIFields(description = "API 동기화 일시", maxLength=14, isDate=true, dateFormat="yyyyMMddHHmmss")
	private String apiSyncDt;

	@APIFields(description = "확정 상태", maxLength=8, required=true, constraints="SYS_C0011071(C)")
	private String confirmStatus;

	@APIFields(description = "시설 상태", maxLength=8, required=true, constraints="SYS_C0011072(C)")
	private String faclStatus;

	@APIFields(description = "반려 내용", maxLength=1000)
	private String rejectContent;

	@APIFields(description = "사용 여부", maxLength=1, required=true, constraints="SYS_C0011073(C)")
	private String useYn;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011074(C)")
	private String regId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyyMMddHHmmss", required=true, constraints="SYS_C0011075(C)")
	private String regDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyyMMddHHmmss")
	private String modiDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());

	@APIFields(description = "시설 이미지", required=false)
	private List<EzcFaclImg> ezcFaclImgList = null;
	
	@APIFields(description = "시설 부대시설 전문(,)구분", maxLength=500)
	private String ezcFaclAments;

	@APIFields(description = "시설 형태소 한글", maxLength=1600)
	private String faclKorMorp;
	
	@APIFields(description = "시설 형태소 영문", maxLength=1600)
	private String faclEngMorp;	
	
	@APIFields(description = "형태소 유형")
	private String morpType;
	
	@APIFields(description = "시설 이름 형태소")
	private String faclNmMorp;
	
	@APIFields(description = "시설 부대시설 목록")
	private List<String> ezcFaclAmentList;	

	@APIFields(description = "한글 형태소 목록")
	private String[] korMorpArray;	
	
	@APIFields(description = "영문 형태소 목록")
	private String[] engMorpArray;	
	
	@APIFields(description = "한글 형태소 일치 개수")
	private Integer korEqualsCount;	
	
	@APIFields(description = "영문 형태소 일치 개수")
	private Integer engEqualsCount;	
	
	@APIFields(description = "일치 형태소 시설 코드 목록")
	private Set<BigDecimal> matchMorpFaclCdList;	

	@APIFields(description = "시군/지역/숙소(유형/등급)별 형태소 매핑 정보")
	private List<EzcFaclMappingSDO> ezcFaclMapping;
	

	public List<EzcFaclMappingSDO> getEzcFaclMappingSDO() {
		return ezcFaclMapping;
	}

	public void setEzcFaclMappingSDO(List<EzcFaclMappingSDO> ezcFaclMapping) {
		this.ezcFaclMapping = ezcFaclMapping;
	}

	public void addEzcFaclMappingSDO(EzcFaclMappingSDO ezcFaclMapping) {
		if(this.ezcFaclMapping == null) {
			this.ezcFaclMapping = new ArrayList<EzcFaclMappingSDO>();
		}
		
		
	}
	
	public Set<BigDecimal> getMatchMorpFaclCdList() {
		return matchMorpFaclCdList;
	}

	public void setMatchMorpFaclCdList(Set<BigDecimal> matchMorpFaclCdList) {
		this.matchMorpFaclCdList = matchMorpFaclCdList;
	}

	public void addMatchMorpFaclCdList(BigDecimal matchMorpFaclCd) {
		if(this.matchMorpFaclCdList == null) {
			this.matchMorpFaclCdList = new LinkedHashSet<BigDecimal>();
		}
		this.matchMorpFaclCdList.add(matchMorpFaclCd);
	}
	
	public Integer getKorEqualsCount() {
		return korEqualsCount;
	}

	public void setKorEqualsCount(Integer korEqualsCount) {
		this.korEqualsCount = korEqualsCount;
	}

	public Integer getEngEqualsCount() {
		return engEqualsCount;
	}

	public void setEngEqualsCount(Integer engEqualsCount) {
		this.engEqualsCount = engEqualsCount;
	}

	public String[] getKorMorpArray() {
		if( APIUtil.isNotEmpty(this.faclKorMorp) ) {
			this.korMorpArray = this.faclKorMorp.split(OperateConstants.STR_COMA);
		}
		return korMorpArray;
	}

	public void setKorMorpArray(String[] engMorpArray) {
		this.engMorpArray = engMorpArray;
	}

	public String[] getEngMorpArray() {
		if( APIUtil.isNotEmpty(this.faclEngMorp) ) {
			this.engMorpArray = this.faclEngMorp.split(OperateConstants.STR_COMA);
		}		
		return engMorpArray;
	}

	public void setEngMorpArray(String[] engMorpArray) {
		this.engMorpArray = engMorpArray;
	}

	public String getFaclNm() {
		return faclNm;
	}

	public void setFaclNm(String faclNm) {
		this.faclNm = faclNm;
	}

	public String getMorpType() {
		return morpType;
	}

	public void setMorpType(String morpType) {
		this.morpType = morpType;
	}

	public String getFaclNmMorp() {
		return faclNmMorp;
	}

	public void setFaclNmMorp(String faclNmMorp) {
		this.faclNmMorp = faclNmMorp;
	}

	public String getEzcFaclAments() {
		return ezcFaclAments;
	}

	public void setEzcFaclAments(String ezcFaclAments) {
		this.ezcFaclAments = ezcFaclAments;
	}

	public BigDecimal getFaclCd() {
		return faclCd;
	}

	public void setFaclCd(BigDecimal faclCd) {
		this.faclCd = faclCd;
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

	public String getPartnerGoodsCd() {
		return partnerGoodsCd;
	}

	public void setPartnerGoodsCd(String partnerGoodsCd) {
		this.partnerGoodsCd = partnerGoodsCd;
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

	public List<EzcFaclImg> getEzcFaclImgList() {
		return ezcFaclImgList;
	}

	public void setEzcFaclImgList(List<EzcFaclImg> ezcFaclImgList) {
		this.ezcFaclImgList = ezcFaclImgList;
	}

	public List<String> getEzcFaclAmentList() {
		return ezcFaclAmentList;
	}

	public void setEzcFaclAmentList(List<String> ezcFaclAmentList) {
		this.ezcFaclAmentList = ezcFaclAmentList;
	}

	public void addEzcFaclAmentList(String ezcFaclAment) {
		if(this.ezcFaclAmentList == null) {
			this.ezcFaclAmentList = new ArrayList<String>();
		}
		this.ezcFaclAmentList.add(ezcFaclAment);
	}

	public String getFaclKorMorp() {
		return faclKorMorp;
	}

	public void setFaclKorMorp(String faclKorMorp) {
		this.faclKorMorp = faclKorMorp;
	}

	public String getFaclEngMorp() {
		return faclEngMorp;
	}

	public void setFaclEngMorp(String faclEngMorp) {
		this.faclEngMorp = faclEngMorp;
	}
	
	
}	
