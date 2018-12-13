package com.ezwel.htl.interfaces.server.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

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
import com.ezwel.htl.interfaces.commons.sdo.ImageSDO;
import com.ezwel.htl.interfaces.commons.thread.CallableExecutor;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.validation.ParamValidate;
import com.ezwel.htl.interfaces.commons.validation.data.ParamValidateSDO;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractServiceObject;
import com.ezwel.htl.interfaces.server.commons.constants.CodeDataConstants;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.commons.utils.CommonUtil;
import com.ezwel.htl.interfaces.server.entities.EzcDetailCd;
import com.ezwel.htl.interfaces.server.entities.EzcFacl;
import com.ezwel.htl.interfaces.server.entities.EzcFaclImg;
import com.ezwel.htl.interfaces.server.repository.CommonRepository;
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
public class OutsideService extends AbstractServiceObject {

	private static final Logger logger = LoggerFactory.getLogger(OutsideService.class);

	private HttpInterfaceExecutorService inteface;
	
	private ConfigureHelper configureHelper;
	
	private OutsideRepository outsideRepository;
	
	/** 제휴사 별 시설 정보 transaction commit 건수 */
	private static final Integer FACL_REG_DATA_TX_COUNT = 50;
	
	private static final String ALL_REG_CHANNEL = "allReg";
	
	/**
	 * 맵핑 시설 : EZC_FACL, EZC_FACL_IMG, EZC_FACL_AMENT ( 1 : N : N ), 데이터 적제 
	 * 요청(입력) 파라메터 없음
	 */
	@APIOperation(description="전체시설일괄등록 인터페이스")
	public AllRegOutSDO callAllReg(UserAgentSDO userAgentDTO) {
		
		inteface = (HttpInterfaceExecutorService) LApplicationContext.getBean(inteface, HttpInterfaceExecutorService.class);
		configureHelper = (ConfigureHelper) LApplicationContext.getBean(configureHelper, ConfigureHelper.class);
		commonRepository = (CommonRepository) LApplicationContext.getBean(commonRepository, CommonRepository.class);
		
		AllRegOutSDO out = null;
		MultiHttpConfigSDO multi = null;
		List<MultiHttpConfigSDO> multiHttpConfigList = null;
		
		try {
			multiHttpConfigList = new ArrayList<MultiHttpConfigSDO>();
			HttpConfigSDO httpConfigSDO = null;
			Map<String, AgentInfoSDO> interfaceAgents = InterfaceFactory.getInterfaceAgents();
			
			for(Entry<String, AgentInfoSDO> entry : interfaceAgents.entrySet()) {
				
				multi = new MultiHttpConfigSDO();
				httpConfigSDO = InterfaceFactory.getChannel(ALL_REG_CHANNEL.concat(OperateConstants.STR_HYPHEN).concat(entry.getValue().getHttpAgentId()), entry.getValue().getHttpAgentId());

				if(httpConfigSDO != null) {
					
					
					httpConfigSDO = configureHelper.setupUserAgentInfo(userAgentDTO, httpConfigSDO);
					logger.debug(" After setupUserAgentInfo process : {}{}", userAgentDTO, httpConfigSDO);
					
					//no input 
					httpConfigSDO.setDoOutput(false);	
					//config
					multi.setHttpConfigDTO(httpConfigSDO);
					//output
					multi.setOutputType(AllRegOutSDO.class);
					multiHttpConfigList.add(multi);
				}
			}
			
			/** execute multi thread interface */
			List<AllRegOutSDO> assets = inteface.sendMultiJSON(multiHttpConfigList);
			
			if(assets != null && assets.size() > 0) {
				/** execute code select transaction */ 
				EzcDetailCd inEzcDetailCd = new EzcDetailCd();
				inEzcDetailCd.addClassCdList(CodeDataConstants.CD_CLASS_CD_G002, CodeDataConstants.CD_CLASS_CD_G003, CodeDataConstants.CD_CLASS_CD_C007, CodeDataConstants.CD_CLASS_CD_G005);
				/** execute save transaction */
				out = insertAllFacl(assets, new AllRegOutSDO(), commonRepository.selectListCommonCode(inEzcDetailCd), 0);
			}
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "전체시설일괄등록 인터페이스 장애발생.", e);
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
	public AllRegOutSDO insertAllFacl(List<AllRegOutSDO> assets, AllRegOutSDO allFacl, List<EzcDetailCd> detailCdList, Integer faclIndex) {
		logger.debug("[START] insertAllFacl assets.size : {}, allFacl : {}, detailCdList : {}, faclIndex : {}", (assets != null ? assets.size() : 0), allFacl, (detailCdList != null ? detailCdList.size() : 0), faclIndex);
		if(assets == null) {
			throw new APIException("시설 목록이 존재하지 않거나 잘못되었습니다.");
		}
		
		commonUtil = (CommonUtil) LApplicationContext.getBean(commonUtil, CommonUtil.class);
		
		Integer txCount = 0;
		AllRegOutSDO allReg = null;
		List<AllRegDataOutSDO> faclDataList = null;	// 제휴사 및에 시설 목록 ( 인터페이스 전문 SDO )
		//시설 정보 DB 엔티티
		EzcFacl ezcFacl = null;
		List<EzcFacl> ezcFaclList = null;	// 제휴사 및에 시설 목록 ( DB 엔티티 )
		//시설 이미지 DB 엔티티
		EzcFaclImg ezcFaclImg = null;
		List<EzcFaclImg> ezcFaclImgList = null;
		Integer nextIndex = null;
		ImageSDO imageSDO = null;
		List<String> ezcFaclAmentArrays = null;
		List<ImageSDO> imageList = null;
		
		try {
			/**
			 * 1. 제휴사 별 TX 실행
			 * 2. 제휴사 내 100개씩  commit 되도록 operation 구성
			 */
			allReg = assets.get(faclIndex);// 제휴사 (단건 / 인터페이스 전문 SDO )
			allFacl.addMultiExecCodeList(allReg.getCode());
			allFacl.addMultiExecMessageList(allReg.getMessage());
			
			logger.debug("- 멀티쓰레드 내 현제 쓰레드의 실행결과 : {}", allReg.getCode());
			nextIndex = faclIndex + 1;
			if(!allReg.getCode().equals(Integer.toString(MessageConstants.RESPONSE_CODE_1000))) {
				
				if(assets != null && assets.size() > nextIndex) {
					insertAllFacl(assets, allFacl, detailCdList, nextIndex);
				}
				else {
					return allFacl;
				}
			}
			else {
				
				String partnerGoodsCd = null;
				
				/** 제휴사 1건 별 이하 프로세스 실행 */
				if(allReg != null && allReg.getData() != null && allReg.getData().size() > 0) {
					/** 제휴사 별 시설 목록 */
					faclDataList = allReg.getData();
					
					ezcFaclList = new ArrayList<EzcFacl>();
					
					for(AllRegDataOutSDO faclData : faclDataList) {
						/** 제휴사 별 시설 데이터 세팅 */  /* => 이슈 : 제휴사 ID : 호텔패스글로벌 전문에 데이터가  전달되어오지 않기때문에 임시로 APIUtil.getId() 처리함 */
						partnerGoodsCd = APIUtil.NVL(faclData.getPdtNo(), APIUtil.getId());
						
						ezcFacl = new EzcFacl(); 
						//ezcFacl.setFaclCd(faclCd); //sequnce
						ezcFacl.setPartnerCd(allReg.getHttpAgentId()); // 에이전트 ID
						ezcFacl.setPartnerCdType(allReg.getPatnCdType());
						ezcFacl.setFaclDiv(CodeDataConstants.CD_API_G0010001); //시설 구분 ( API )
						ezcFacl.setPartnerGoodsCd( partnerGoodsCd );
						ezcFacl.setFaclNmKor( APIUtil.NVL(faclData.getPdtName(), OperateConstants.STR_EMPTY) ); /* 시설 한글 명 => 호텔패스글로벌 전문에 데이터가 전달되어오지 않기때문에 임시로 EMPTY 처리함 */
						ezcFacl.setFaclNmEng(faclData.getPdtNameEng());
						ezcFacl.setRoomType( APIUtil.NVL(commonUtil.getMasterCdForCodeList(detailCdList, faclData.getTypeCode()), "NA-G002") ); // -> DB 공통코드 (테이블 : EZC_DETAIL_CD.DETAIL_CD  = '#{typeCode}' AND EZC_DETAIL_CD.CLASS_CD = 'G002' )
						ezcFacl.setRoomClass( APIUtil.NVL(commonUtil.getMasterCdForCodeList(detailCdList, faclData.getGradeCode()), "NA-G003") ); // -> DB 공통코드 (테이블 : EZC_DETAIL_CD.DETAIL_CD  = '#{gradeCode}' AND EZC_DETAIL_CD.CLASS_CD = 'G003')
						ezcFacl.setSaleStartDd(faclData.getSellStartDate()); // 판매시작일
						ezcFacl.setSaleEndDd(faclData.getSellEndDate());	// 판매종료일
						ezcFacl.setCheckInTm( APIUtil.isNotEmpty(faclData.getCheckInTime()) ? faclData.getCheckInTime().replace(":", "") : null );	//채크인시간 ( 임시 필터 )
						ezcFacl.setCheckOutTm( APIUtil.isNotEmpty(faclData.getCheckOutTime()) ? faclData.getCheckOutTime().replace(":","") : null );	//채크아웃시간 ( 임시 필터 )
						ezcFacl.setAreaCd( APIUtil.NVL(faclData.getGunguCode(), OperateConstants.STR_EMPTY) );	//지역코드(군구코드) => 호텔패스글로벌 전문에 데이터가  전달되어오지 않기때문에 임시로 EMPTY 처리함 */
						ezcFacl.setCityCd( APIUtil.NVL(faclData.getSidoCode(), OperateConstants.STR_EMPTY) );	//도시코드(시도코드)
						ezcFacl.setAddrType( APIUtil.NVL(commonUtil.getMasterCdForCodeList(detailCdList, faclData.getAddressType()), "NA-C007") );  //주소 유형 -> DB 공통코드 (테이블 : EZC_DETAIL_CD)
						ezcFacl.setAddr( APIUtil.NVL(faclData.getAddress(), OperateConstants.STR_EMPTY) );	//주소
						ezcFacl.setPost(faclData.getZipCode());	//우편번호
						ezcFacl.setTelNum(faclData.getTelephone());	//전화 번호
						ezcFacl.setCoordY(faclData.getMapX());	//위도
						ezcFacl.setCoordX(faclData.getMapY());	//경도
						ezcFacl.setMinAmt((faclData.getSellPrice() != null ? new BigDecimal(faclData.getSellPrice()) : null)); // 최저 금액
						ezcFacl.setDetailDescPc(faclData.getDescHTML());	//상세 설명 PC 		(제휴사 텍스트 OR HTML 설명 데이터)
						ezcFacl.setDetailDescM(faclData.getDescMobile());	//상세 설명 모바일	(제휴사 텍스트 OR HTML 설명 데이터)
						ezcFacl.setTripPropId(faclData.getTripadvisorId());	//트립어드바이저 프로퍼티 ID
						ezcFacl.setMainImgUrl(faclData.getMainImage());		//대표 이미지 URL
						ezcFacl.setImgChangeYn(faclData.getChangeImage());	//이미지 변경 여부
						ezcFacl.setApiSyncDt(APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis(), OperateConstants.DEF_DATE_FORMAT)); //API 동기화 일시(API 동작일시)
						ezcFacl.setUseYn(CodeDataConstants.CD_Y);	//사용 여부 새로등록되는 시설에 대하여 기본 Y로 등록함
						ezcFacl.setFaclStatus(CodeDataConstants.CD_FACL_STATUS_G0040003); //시설 상태
						ezcFacl.setConfirmStatus(CodeDataConstants.CD_CONFIRM_STATUS_G0060003); //확정 상태
						
						//서브 이미지 세팅
						if(faclData.getSubImages() != null) {
							
							//시설 이미지 DB 엔티티
							ezcFaclImgList = new ArrayList<EzcFaclImg>();
							imageList = new ArrayList<ImageSDO>();
							
							for(AllRegSubImagesOutSDO subImages : faclData.getSubImages()) {
								ezcFaclImg = new EzcFaclImg();
								//ezcFaclImg.setFaclCd(faclCd); sequnce
								//ezcFaclImg.setFaclImgSeq(faclImgSeq); sequnce
								ezcFaclImg.setFaclImgType(CodeDataConstants.CD_FACL_IMG_TYPE_G0080001);
								ezcFaclImg.setPartnerImgUrl(subImages.getImage()); // 이미지 URL 
								ezcFaclImg.setImgDesc(subImages.getDesc()); // 이미지 설명
								if(faclData.getMainImage() != null && subImages.getImage() != null && faclData.getMainImage().equals(subImages.getImage())) {
									ezcFaclImg.setMainImgYn(CodeDataConstants.CD_Y); // 메인 이미지 여부
								}
								else {
									ezcFaclImg.setMainImgYn(CodeDataConstants.CD_N); // 메인 이미지 여부
								}
								
								if(APIUtil.isNotEmpty(subImages.getImage())) {
									//이미지 다운로드
									imageSDO = new ImageSDO();
									imageSDO.setPathPrefix(new StringBuffer().append(ezcFacl.getPartnerCd()).append(File.separator).append(ezcFacl.getCityCd()).toString());
									imageSDO.setImageURL(subImages.getImage());
									imageSDO = commonUtil.getSaveImagePath(imageSDO, false);
									
									ezcFaclImg.setImgUrl( APIUtil.NVL(imageSDO.getRelativePath(), MessageConstants.getMessage(MessageConstants.RESPONSE_CODE_9400)) );
									
									if(!ezcFaclImg.getImgUrl().equals(MessageConstants.getMessage(MessageConstants.RESPONSE_CODE_9400))) {
										allFacl.addImageList(imageSDO);
									}
								}
								
								ezcFaclImgList.add(ezcFaclImg);
							}
						}
						
						ezcFacl.setEzcFaclImgList(ezcFaclImgList);
						
						//부대시설 세팅
						ezcFacl.setEzcFaclAments(faclData.getServiceCodes());
						//부대시설 유형이 전문에 존재하면
						if(faclData.getServiceCodes() != null) {
							//부대시설 전문 코드 목록
							ezcFaclAmentArrays = Arrays.asList(faclData.getServiceCodes().split(OperateConstants.STR_COMA));
							//공통코드와 매핑 되는 부대시설 유형 코드
							for(String amentCd : ezcFaclAmentArrays) {
								ezcFacl.addEzcFaclAmentList( APIUtil.NVL(commonUtil.getMasterCdForCodeList(detailCdList, amentCd), "NA-G005") );
							}
						}
						
						//execute paramValidate
						new ParamValidate(new ParamValidateSDO(ezcFacl, new String[]{"faclCd"})).execute();
						
						//DB 엔티티에 전문 데이터 세팅
						ezcFaclList.add(ezcFacl);
					}
					
					/** 제휴사 별 시설 데이터 입력 실행 */
					txCount = insertFaclRegData(ezcFaclList, 0, 0);
					/** 저장 개수가 존재하면 */
					if(txCount > 0) {
						if(allFacl.getData() == null) {
							allFacl.setData(new ArrayList<AllRegDataOutSDO>());
						}
						/** 트렌젝션 성공 개수 */
						allFacl.setTxCount(allFacl.getTxCount() + txCount);
					}
				}
				
				if(assets != null && assets.size() > nextIndex) {
					insertAllFacl(assets, allFacl, detailCdList, nextIndex);
				}
			}
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "전체시설일괄등록 인터페이스 장애발생.", e);
		}
		finally { 
			
			if(allReg != null && allReg.getData() != null) {
				allReg.getData().clear();
			}
			if(imageList != null) {
				imageList.clear();
			}
		}
		
		logger.debug("[END] insertAllFacl " /* out : {}", allFacl*/);
		return allFacl;
	}
	
	
	
	@APIOperation(description="제휴사 별 시설 데이터 입력")
	public Integer insertFaclRegData(List<EzcFacl> ezcFacls/* 제휴사 별 시설 목록 */, Integer fromIndex, Integer txCount) {
		logger.debug("[START] insertFaclRegData ezcFacls.size : {}, fromIndex : {}, txCount : {}", (ezcFacls != null ? ezcFacls.size() : 0), fromIndex, txCount);
		
		outsideRepository = (OutsideRepository) LApplicationContext.getBean(outsideRepository, OutsideRepository.class);
		
		/**
		 * 시설 50개씩 connection 끊어서 돌려야함.
		 */
		Integer toIndex = fromIndex + FACL_REG_DATA_TX_COUNT;
		
		List<EzcFacl> saveFaclRegDatas = null;
		if(toIndex > ezcFacls.size()) {
			toIndex = ezcFacls.size();
		}

		try {
			logger.debug("* insertFaclRegData subList 'fromIndex : {} ~ toIndex : {}'", fromIndex, toIndex);
			saveFaclRegDatas = ezcFacls.subList(fromIndex, toIndex);
			logger.debug("* insertFaclRegData saveFaclRegDatas.size {}", (saveFaclRegDatas != null ? saveFaclRegDatas.size() : 0));
			if(saveFaclRegDatas != null && saveFaclRegDatas.size() > 0) {
				txCount += outsideRepository.insertAllReg(saveFaclRegDatas, fromIndex, txCount);
			}
			
			if(ezcFacls != null && ezcFacls.size() > toIndex) {
				insertFaclRegData(ezcFacls/* 제휴사 별 시설 목록 */, toIndex, txCount);
			}
		}
		catch(Exception e) {
			throw new APIException("제휴사 별 시설 데이터 입력 장애발생 (입력 구간 from/to : {} ~ {})", new Object[]{fromIndex, toIndex}, e);
		}

		logger.debug("[END] insertFaclRegData txCount : {}", txCount);
		return txCount;
	}
	
	@APIOperation(description="Http URL Image Download Multi Communication API", isExecTest=true)
	public void downloadMultiImage(List<ImageSDO> imageList) {
		logger.debug("[START] downloadMultiImage\nInput Signature : {}", imageList);
		
		CallableExecutor executor = null;
		
		try {
			
			if(imageList != null && imageList.size() > 0) {
				
				executor = new CallableExecutor();
				executor.initThreadPool(40);
				ImageSDO imageConfig = null;

				for(int i = 0; i < imageList.size(); i++) {
					imageConfig = imageList.get(i);
					//실존하는 이미지로 확인된 URL만 다운로드를 실행한다.
					if(APIUtil.isNotEmpty(imageConfig.getCanonicalPath())) {
						Callable<ImageSDO> callable = new DownloadMultiService(imageConfig, (i+1));
						//설정된 멀티쓰레드 개수만큼 반복 실행
						executor.addCall(callable);
					}
				}
				
				//현재(20181211)는 결과를 받아 사용하지 않음.
				/*
				List<Future<?>> futures = executor.getResult();
				if(futures != null) {
					logger.debug("- futures.size() : {}", futures.size());
					
					for(Future<?> future : futures) {
						if(future.get() != null) {
							logger.debug("[IMAGE-DOWNLOAD-OUTPUT] {}", future.get());
						}
					}
				}
				*/
			}
			
		} catch (APIException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9401, "■ 이미지 다운로드 다중 인터페이스 장애 발생", e);
		} catch (Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9401, "■ 이미지 다운로드 다중 인터페이스 장애 발생", e);
		} 
		finally {
			if(executor != null) {
				executor.clear();
			}
		}
		
		logger.debug("[END] downloadMultiImage");		
	}
	
	
	/**
	 * EZC_CACHE_DAY_PRICE, EZC_CACHE_DAY_PRICE_LOG 데이터 적제  ( 맵핑시설 테이블에 존재하는 시설의 당일특가만 적제 가능 )
	 * @param userAgentDTO
	 * @return 
	 */
	@APIOperation(description="당일특가검색 인터페이스")
	public SddSearchOutSDO callSddSearch(UserAgentSDO userAgentDTO) {
		
		inteface = (HttpInterfaceExecutorService) LApplicationContext.getBean(inteface, HttpInterfaceExecutorService.class);
		configureHelper = (ConfigureHelper) LApplicationContext.getBean(configureHelper, ConfigureHelper.class);
		outsideRepository = (OutsideRepository) LApplicationContext.getBean(outsideRepository, OutsideRepository.class);
		
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
					configureHelper.setupUserAgentInfo(userAgentDTO, httpConfigSDO);
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
			List<SddSearchOutSDO> assets = inteface.sendMultiJSON(multiHttpConfigList);
			
			/** execute dbio */
			out = outsideRepository.callSddSearch(assets);			
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "당일특가검색 인터페이스 장애발생.", e);
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
		
		inteface = (HttpInterfaceExecutorService) LApplicationContext.getBean(inteface, HttpInterfaceExecutorService.class);
		configureHelper = (ConfigureHelper) LApplicationContext.getBean(configureHelper, ConfigureHelper.class);
		outsideRepository = (OutsideRepository) LApplicationContext.getBean(outsideRepository, OutsideRepository.class);
		
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
					configureHelper.setupUserAgentInfo(userAgentDTO, httpConfigSDO);
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
			List<FaclSearchOutSDO> assets = inteface.sendMultiJSON(multiHttpConfigList);
			
			/** execute dbio */
			out = outsideRepository.callFaclSearch(assets);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "시설검색 인터페이스 장애발생.", e);
		}
			
		return out;
	}
}
