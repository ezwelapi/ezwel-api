package com.ezwel.htl.interfaces.server.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.configure.ConfigureHelper;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.HttpInterfaceExecutorService;
import com.ezwel.htl.interfaces.commons.http.data.AgentInfoSDO;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigSDO;
import com.ezwel.htl.interfaces.commons.http.data.MultiHttpConfigSDO;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.server.commons.constants.CodeDataConstants;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.entities.EzcFacl;
import com.ezwel.htl.interfaces.server.entities.EzcFaclImg;
import com.ezwel.htl.interfaces.server.repository.InterfaceCommRepository;
import com.ezwel.htl.interfaces.server.repository.OutsideRepository;
import com.ezwel.htl.interfaces.service.data.allReg.AllRegDataOutSDO;
import com.ezwel.htl.interfaces.service.data.allReg.AllRegOutSDO;
import com.ezwel.htl.interfaces.service.data.allReg.AllRegSubImagesOutSDO;
import com.ezwel.htl.interfaces.service.data.faclSearch.FaclSearchInSDO;
import com.ezwel.htl.interfaces.service.data.faclSearch.FaclSearchOutSDO;
import com.ezwel.htl.interfaces.service.data.sddSearch.SddSearchOutSDO;

/**
 * <pre>
 * 인터페이스 서비스
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
@Service
@APIType(description="외부 인터페이스 데이터 적제 서비스")
public class OutsideService {

	private static final Logger logger = LoggerFactory.getLogger(OutsideService.class);

	private HttpInterfaceExecutorService inteface = (HttpInterfaceExecutorService) LApplicationContext.getBean(HttpInterfaceExecutorService.class);
	
	private ConfigureHelper configureHelper = (ConfigureHelper) LApplicationContext.getBean(ConfigureHelper.class);
	
	private PropertyUtil propertyUtil = (PropertyUtil) LApplicationContext.getBean(PropertyUtil.class);
	
	private OutsideRepository outsideRepository = (OutsideRepository) LApplicationContext.getBean(OutsideRepository.class);
	
	private InterfaceCommRepository interfaceCommRepository = (InterfaceCommRepository) LApplicationContext.getBean(InterfaceCommRepository.class);
	
	/** 제휴사 별 시설 정보 transaction commit 건수 */
	private static final Integer FACL_REG_DATA_TX_COUNT = 50;
	
	public OutsideService() {
		
		if(propertyUtil == null) {
			propertyUtil = new PropertyUtil();
		}
		if(inteface == null) {
			inteface = new HttpInterfaceExecutorService();
		}
		if(configureHelper == null) {
			configureHelper = new ConfigureHelper();
		}		
	}
	

	/**
	 * 맵핑 시설 : EZC_FACL, EZC_FACL_IMG, EZC_FACL_AMENT ( 1 : N : N ), 데이터 적제 
	 * 요청(입력) 파라메터 없음
	 */
	@APIOperation(description="전체시설일괄등록 인터페이스")
	public AllRegOutSDO callAllReg(UserAgentSDO userAgentDTO) {
		
		AllRegOutSDO out = null;
		MultiHttpConfigSDO multi = null;
		List<MultiHttpConfigSDO> multiHttpConfigList = null;
		
		try {
			multiHttpConfigList = new ArrayList<MultiHttpConfigSDO>();
			HttpConfigSDO httpConfigSDO = null;
			Map<String, AgentInfoSDO> interfaceAgents = InterfaceFactory.getInterfaceAgents();
			
			for(Entry<String, AgentInfoSDO> entry : interfaceAgents.entrySet()) {
				
				multi = new MultiHttpConfigSDO();
				httpConfigSDO = InterfaceFactory.getChannel("allReg".concat(OperateConstants.STR_HYPHEN).concat(entry.getValue().getHttpAgentId()), entry.getValue().getHttpAgentId());
				configureHelper.setupUserAgentInfo(httpConfigSDO, userAgentDTO);
				//no input 
				httpConfigSDO.setDoOutput(false);	
				//config
				multi.setHttpConfigDTO(httpConfigSDO);
				//output
				multi.setOutputType(AllRegOutSDO.class);
				multiHttpConfigList.add(multi);
			}
			
			/** execute multi thread interface */
			List<AllRegOutSDO> assets = inteface.sendMultiPostJSON(multiHttpConfigList);
			
			if(assets != null && assets.size() > 0) {
				/** execute transaction */ 
				out = insertAllFacl(assets, new AllRegOutSDO(), 0);
			}
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "시설검색 인터페이스 장애발생.", e);
		}
		
		
		return out;
	}	
	
	/**
	 * 맵핑 시설 : EZC_FACL, EZC_FACL_IMG, EZC_FACL_AMENT ( 1 : N : N ), 데이터 적제
	 * @param assets
	 * @param allFacl
	 * @param faclIndex
	 * @return
	 */
	@APIOperation(description="전체시설일괄등록 인터페이스")
	private AllRegOutSDO insertAllFacl(List<AllRegOutSDO> assets, AllRegOutSDO allFacl, Integer faclIndex) {
		if(assets == null) {
			throw new APIException("시설 목록이 존재하지 않거나 잘못되었습니다.");
		}
		
		Integer txCount = 0;

		/**
		 * 1. 제휴사 별 TX 실행
		 * 2. 제휴사 내 100개씩  commit 되도록 operation 구성
		 */
		AllRegOutSDO allReg = assets.get(faclIndex);// 제휴사 (단건 / 인터페이스 전문 SDO )
		List<AllRegDataOutSDO> faclDatas = null;	// 제휴사 및에 시설 목록 ( 인터페이스 전문 SDO )
		
		//시설 정보 DB 엔티티
		EzcFacl ezcFacl = null;
		List<EzcFacl> ezcFacls = null;	// 제휴사 및에 시설 목록 ( DB 엔티티 )
		//시설 이미지 DB 엔티티
		EzcFaclImg ezcFaclImg = null;
		List<EzcFaclImg> ezcFaclImgList = null;
		
		/** 제휴사 1건 별 이하 프로세스 실행 */
		if(allReg != null && allReg.getData() != null && allReg.getData().size() > 0) {
			/** 제휴사 별 시설 목록 */
			faclDatas = allReg.getData();
			
			ezcFacls = new ArrayList<EzcFacl>();
			
			for(AllRegDataOutSDO faclData : faclDatas) {
				/** 제휴사 별 시설 데이터 세팅 */
				ezcFacl = new EzcFacl(); 
				//ezcFacl.setFaclCd(faclCd); //sequnce
				ezcFacl.setPartnerCd(allReg.getHttpAgentId()); // 에이전트 ID
				ezcFacl.setPartnerCdType(allReg.getPatnCdType());
				ezcFacl.setFaclDiv(CodeDataConstants.CD_API_G0010001); //시설 구분 ( API or 직영숙박 )
				ezcFacl.setPartnerGoodsCd(faclData.getPdtNo());
				ezcFacl.setFaclNmKor(faclData.getPdtName()); //시설 한글 명
				ezcFacl.setFaclNmEng(faclData.getPdtNameEng());
				ezcFacl.setRoomType(faclData.getTypeCode()); // -> DB 공통코드 (테이블 : EZC_DETAIL_CD.DETAIL_CD  = '#{typeCode}' AND EZC_DETAIL_CD.CLASS_CD = 'G002' )
				ezcFacl.setRoomClass(faclData.getGradeCode()); // -> DB 공통코드 (테이블 : EZC_DETAIL_CD.DETAIL_CD  = '#{gradeCode}' AND EZC_DETAIL_CD.CLASS_CD = 'G003')
				ezcFacl.setSaleStartDd(faclData.getSellStartDate()); // 판매시작일
				ezcFacl.setSaleEndDd(faclData.getSellEndDate());	// 판매종료일
				ezcFacl.setCheckInTm(faclData.getCheckInTime());	//채크인시간
				ezcFacl.setCheckOutTm(faclData.getCheckOutTime());	//채크아웃시간
				ezcFacl.setAreaCd(faclData.getGunguCode());	//지역코드(군구코드)
				ezcFacl.setCityCd(faclData.getSidoCode());	//도시코드(시도코드)
				ezcFacl.setAddrType(faclData.getAddressType());  // -> DB 공통코드 (테이블 : EZC_DETAIL_CD)
				ezcFacl.setAddr(faclData.getAddress());	//주소
				ezcFacl.setPost(faclData.getZipCode());	//우편번호
				ezcFacl.setTelNum(faclData.getTelephone());	//전화 번호
				ezcFacl.setCoordY(faclData.getMapX());	//위도
				ezcFacl.setCoordX(faclData.getMapY());	//경도
				ezcFacl.setMinAmt((faclData.getSellPrice() != null ? new BigDecimal(faclData.getSellPrice()) : OperateConstants.BIGDECIMAL_ZERO_VALUE)); // 최저 금액
				ezcFacl.setDetailDescPc(faclData.getDescHTML());	//상세 설명 PC
				ezcFacl.setDetailDescM(faclData.getDescMobile());	//상세 설명 모바일
				ezcFacl.setTripPropId(faclData.getTripadvisorId());	//트립어드바이저 프로퍼티 ID
				ezcFacl.setMainImgUrl(faclData.getMainImage());		//대표 이미지 URL
				ezcFacl.setImgChangeYn(faclData.getChangeImage());	//이미지 변경 여부
				ezcFacl.setApiSyncDt(APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis(), "yyyyMMddHH24miss")); //API 동기화 일시(API 동작일시)
				ezcFacl.setUseYn(CodeDataConstants.CD_Y);	//사용 여부 새로등록되는 시설에 대하여 기본 Y로 등록함
				
				//서브 이미지 세팅
				if(faclData.getSubImages() != null) {
					
					//시설 이미지 DB 엔티티
					ezcFaclImgList = new ArrayList<EzcFaclImg>();					
					for(AllRegSubImagesOutSDO subImages : faclData.getSubImages()) {
						ezcFaclImg = new EzcFaclImg();
						//ezcFaclImg.setFaclCd(faclCd); sequnce
						//ezcFaclImg.setFaclImgSeq(faclImgSeq); sequnce
						ezcFaclImg.setFaclImgType(CodeDataConstants.CD_FACL_IMG_TYPE_G0080001);
						ezcFaclImg.setPartnerImgUrl(subImages.getImage()); // 이미지 URL 
						ezcFaclImg.setImgDesc(subImages.getDesc()); // 이미지 설명
						if(faclData.getMainImage().equals(subImages.getImage())) {
							ezcFaclImg.setMainImgYn(CodeDataConstants.CD_Y); // 메인 이미지 여부
						}
						else {
							ezcFaclImg.setMainImgYn(CodeDataConstants.CD_N); // 메인 이미지 여부
						}

						ezcFaclImgList.add(ezcFaclImg);
					}
				}
				ezcFacl.setEzcFaclImgList(ezcFaclImgList);
				
				//부대시설 세팅
				ezcFacl.setEzcFaclAments(faclData.getServiceCodes());
				
				//DB 엔티티에 전문 데이터 세팅
				ezcFacls.add(ezcFacl);
			}
			
			/** 제휴사 별 시설 데이터 입력 실행 */
			txCount = insertFaclRegData(ezcFacls, 0);
			/** 저장 개수가 존재하면 */
			if(txCount > 0) {
				if(allFacl.getData() == null) {
					allFacl.setData(new ArrayList<AllRegDataOutSDO>());
				}
				/** 제휴사의 시설 데이터를 output sdo 의 목록에 저장 */
				allFacl.getData().addAll(faclDatas);
				/** 트렌젝션 성공 개수 */
				allFacl.setTxCount(allFacl.getTxCount() + txCount);
			}
		}
		
		Integer nextIndex = faclIndex + 1;
		if(assets != null && assets.size() > nextIndex) {
			insertAllFacl(assets, allFacl, nextIndex);
		}
		
		return allFacl;
	}
	
	
	
	@APIOperation(description="전체시설일괄등록 인터페이스")
	private Integer insertFaclRegData(List<EzcFacl> ezcFacls/* 제휴사 별 시설 목록 */, Integer fromIndex) {
		/**
		 * 시설 50개씩 connection 끊어서 돌려야함.
		 */
		Integer txCount = 0;
		Integer toIndex = fromIndex + FACL_REG_DATA_TX_COUNT;
		List<EzcFacl> saveFaclRegDatas = null;
		if(toIndex >= ezcFacls.size()) {
			toIndex = ezcFacls.size() - 1;
		}

		saveFaclRegDatas = ezcFacls.subList(fromIndex, toIndex);
		
		txCount = outsideRepository.insertAllReg(saveFaclRegDatas);
		
		Integer nextIndex = toIndex + 1;
		if(ezcFacls != null && ezcFacls.size() > nextIndex) {
			insertFaclRegData(ezcFacls/* 제휴사 별 시설 목록 */, nextIndex);
		}
		
		return txCount;
	}
	
	
	
	/**
	 * EZC_CACHE_DAY_PRICE, EZC_CACHE_DAY_PRICE_LOG 데이터 적제  ( 맵핑시설 테이블에 존재하는 시설의 당일특가만 적제 가능 )
	 * @param userAgentDTO
	 * @return 
	 */
	@APIOperation(description="당일특가검색 인터페이스")
	public SddSearchOutSDO callSddSearch(UserAgentSDO userAgentDTO) {
		
		SddSearchOutSDO out = null;
		MultiHttpConfigSDO multi = null;
		List<MultiHttpConfigSDO> multiHttpConfigList = null;
		List<HttpConfigSDO> channelList = null;
		
		try {
			multiHttpConfigList = new ArrayList<MultiHttpConfigSDO>();
			
			channelList = InterfaceFactory.getChannelGroup("sddSearch", userAgentDTO.getHttpAgentGroupId());
			if(channelList != null) {
				for(HttpConfigSDO httpConfigSDO : channelList) {
					multi = new MultiHttpConfigSDO();
					configureHelper.setupUserAgentInfo(httpConfigSDO, userAgentDTO);
					//no input 
					httpConfigSDO.setDoOutput(false);
					//config
					multi.setHttpConfigDTO(httpConfigSDO);
					//output
					multi.setOutputType(SddSearchOutSDO.class);
					multiHttpConfigList.add(multi);
				}
			}
			
			/** execute interface */
			//멀티 쓰레드 인터페이스 실행
			List<SddSearchOutSDO> assets = inteface.sendMultiPostJSON(multiHttpConfigList);
			
			/** execute dbio */
			out = outsideRepository.callSddSearch(assets);			
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "시설검색 인터페이스 장애발생.", e);
		}
		
		return out;
	}
	

	/**
	 * 멀티쓰레드
	 * EZC_CACHE_MIN_AMT, EZC_CACHE_SEARCH_LOG 데이터 적제
	 * input = 
	 * @param userAgentDTO
	 * @param faclSearchDTO
	 * @return
	 */
	@APIOperation(description="시설검색 인터페이스")
	public FaclSearchOutSDO callFaclSearch(UserAgentSDO userAgentDTO, FaclSearchInSDO faclSearchDTO) {
			
		FaclSearchOutSDO out = null;
		MultiHttpConfigSDO multi = null;
		List<HttpConfigSDO> channelList = null;
		List<MultiHttpConfigSDO> multiHttpConfigList = null;
		
		try {
			multiHttpConfigList = new ArrayList<MultiHttpConfigSDO>();
			
			channelList = InterfaceFactory.getChannelGroup("faclSearch", userAgentDTO.getHttpAgentGroupId());
			if(channelList != null) {
				for(HttpConfigSDO httpConfigSDO : channelList) {
					multi = new MultiHttpConfigSDO();
					configureHelper.setupUserAgentInfo(httpConfigSDO, userAgentDTO);
					//config
					multi.setHttpConfigDTO(httpConfigSDO);
					//input
					multi.setInputDTO(faclSearchDTO);
					//output
					multi.setOutputType(FaclSearchOutSDO.class);
					multiHttpConfigList.add(multi);
				}
			}
			
			/** execute interface */
			//멀티 쓰레드 인터페이스 실행
			List<FaclSearchOutSDO> assets = inteface.sendMultiPostJSON(multiHttpConfigList);
			
			/** execute dbio */
			out = outsideRepository.callFaclSearch(assets);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "시설검색 인터페이스 장애발생.", e);
		}
			
		return out;
	}
}
