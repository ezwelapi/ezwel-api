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

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
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
import com.ezwel.htl.interfaces.commons.http.HttpInterfaceExecutor;
import com.ezwel.htl.interfaces.commons.http.data.AgentInfoSDO;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigSDO;
import com.ezwel.htl.interfaces.commons.http.data.MultiHttpConfigSDO;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
import com.ezwel.htl.interfaces.commons.sdo.ImageSDO;
import com.ezwel.htl.interfaces.commons.thread.CallableExecutor;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.commons.validation.ParamValidate;
import com.ezwel.htl.interfaces.commons.validation.data.ParamValidateSDO;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractServiceObject;
import com.ezwel.htl.interfaces.server.commons.constants.CodeDataConstants;
import com.ezwel.htl.interfaces.server.commons.morpheme.ko.KoreanAnalyzer;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.commons.utils.CommonUtil;
import com.ezwel.htl.interfaces.server.entities.EzcDetailCd;
import com.ezwel.htl.interfaces.server.entities.EzcFacl;
import com.ezwel.htl.interfaces.server.entities.EzcFaclImg;
import com.ezwel.htl.interfaces.server.repository.CommonRepository;
import com.ezwel.htl.interfaces.server.repository.OutsideRepository;
import com.ezwel.htl.interfaces.service.data.allReg.AllRegDataOutSDO;
import com.ezwel.htl.interfaces.service.data.allReg.AllRegDataRealtimeImageOutSDO;
import com.ezwel.htl.interfaces.service.data.allReg.AllRegFaclImgOutSDO;
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

	private HttpInterfaceExecutor inteface;
	
	private ConfigureHelper configureHelper;
	
	private OutsideRepository outsideRepository;
	
	private PropertyUtil propertyUtil;
	
	private KoreanAnalyzer koreanAnalyzer;
	
	/** 제휴사 별 시설 정보 transaction commit 건수 */
	private static final Integer FACL_REG_DATA_TX_COUNT = 50;
	
	/** 시설 이미지 전체 조회 페이징 개수 */
	private static final Integer FACL_IMG_PAGE_SIZE = 10000;
	
	private static final String ALL_REG_CHANNEL = "allReg";
	
	/**
	 * 맵핑 시설 : EZC_FACL, EZC_FACL_IMG, EZC_FACL_AMENT ( 1 : N : N ), 데이터 적제 
	 * 요청(입력) 파라메터 없음
	 */
	@APIOperation(description="전체시설일괄등록 인터페이스")
	public AllRegOutSDO callAllReg(UserAgentSDO userAgentDTO) {
		
		inteface = (HttpInterfaceExecutor) LApplicationContext.getBean(inteface, HttpInterfaceExecutor.class);
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
	public AllRegOutSDO insertAllFacl(List<AllRegOutSDO> assets, AllRegOutSDO out, List<EzcDetailCd> detailCdList, Integer faclIndex) {
		logger.debug("[START] insertAllFacl assets.size : {}, allFacl : {}, detailCdList : {}, faclIndex : {}", (assets != null ? assets.size() : 0), out, (detailCdList != null ? detailCdList.size() : 0), faclIndex);
		if(assets == null) {
			throw new APIException("시설 목록이 존재하지 않거나 잘못되었습니다.");
		}
		
		commonUtil = (CommonUtil) LApplicationContext.getBean(commonUtil, CommonUtil.class);
		koreanAnalyzer = (KoreanAnalyzer) LApplicationContext.getBean(koreanAnalyzer, KoreanAnalyzer.class);
		
		AllRegOutSDO allReg = null;
		List<AllRegDataOutSDO> faclDataList = null;	// 제휴사 및에 시설 목록 ( 인터페이스 전문 SDO )
		//시설 정보 DB 엔티티
		EzcFacl ezcFacl = null;
		List<EzcFacl> ezcFaclList = null;	// 제휴사 및에 시설 목록 ( DB 엔티티 )
		//시설 이미지 DB 엔티티
		EzcFaclImg ezcFaclImg = null;
		List<EzcFaclImg> ezcFaclImgList = null;
		List<String> imgUrlStringList = null;
		Integer nextIndex = null;
		List<String> ezcFaclAmentArrays = null;
		List<ImageSDO> imageList = null;
		//형태소 분석기 관련 변수 선언
		StringBuilder actual = null;
		TokenStream tokens = null;
		CharTermAttribute termAtt = null;
		
		try {
			/**
			 * 1. 제휴사 별 TX 실행
			 * 2. 제휴사 내 100개씩  commit 되도록 operation 구성
			 */
			allReg = assets.get(faclIndex);// 제휴사 (단건 / 인터페이스 전문 SDO )
			out.addMultiExecCodeList(allReg.getCode());
			out.addMultiExecMessageList(allReg.getMessage());
			logger.debug("- 멀티쓰레드 별 인터페이스 실행결과 : {}", allReg.getCode());
			
			nextIndex = faclIndex + 1;
			if(!allReg.getCode().equals(Integer.toString(MessageConstants.RESPONSE_CODE_1000))) {
				if(assets != null && assets.size() > nextIndex) {
					//현재 인터페이스 결과가 장애 상황인경우 다음 인터페이스로 넘어감 
					insertAllFacl(assets, out, detailCdList, nextIndex);
				}
				else {
					return out;
				}
			}
			else {
				
				String partnerGoodsCd = null;
				
				/** 제휴사 1건 별 이하 프로세스 실행 */
				if(allReg != null && allReg.getData() != null && allReg.getData().size() > 0) {
					/** 제휴사 별 시설 목록 */
					faclDataList = allReg.getData();
					/** ezwel 시설 정보 목록 */
					ezcFaclList = new ArrayList<EzcFacl>();
					/** 한글형태소분석기 설정 */
					koreanAnalyzer.setQueryMode(false);

					
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
						ezcFacl.setMainImgUrl(faclData.getMainImage() != null ? faclData.getMainImage().trim() : "");		//대표 이미지 URL
						ezcFacl.setImgChangeYn(faclData.getChangeImage());	//이미지 변경 여부
						ezcFacl.setApiSyncDt(APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis(), OperateConstants.DEF_DATE_FORMAT)); //API 동기화 일시(API 동작일시)
						ezcFacl.setUseYn(CodeDataConstants.CD_Y);	//사용 여부 새로등록되는 시설에 대하여 기본 Y로 등록함
						ezcFacl.setFaclStatus(CodeDataConstants.CD_FACL_STATUS_G0040003); //시설 상태
						ezcFacl.setConfirmStatus(CodeDataConstants.CD_CONFIRM_STATUS_G0060003); //확정 상태

						// 국문 형태소
						if (APIUtil.isNotEmpty(ezcFacl.getFaclNmKor())) {
							actual = new StringBuilder();

							tokens = koreanAnalyzer.tokenStream("bogus", ezcFacl.getFaclNmKor());
							termAtt = tokens.addAttribute(CharTermAttribute.class);
							tokens.reset();

							while (tokens.incrementToken()) {
								if (termAtt.toString().equals(OperateConstants.STR_SPEC_COMA)) {
									continue;
								}
								actual.append(termAtt.toString());
								actual.append(OperateConstants.STR_SPEC_COMA);
							}

							ezcFacl.setFaclKorMorp(actual.toString());
						}

						// 영문 형태소
						if (APIUtil.isNotEmpty(ezcFacl.getFaclNmEng())) {
							actual = new StringBuilder();

							tokens = koreanAnalyzer.tokenStream("bogus", ezcFacl.getFaclNmEng());
							termAtt = tokens.addAttribute(CharTermAttribute.class);
							tokens.reset();

							while (tokens.incrementToken()) {
								if (termAtt.toString().equals(OperateConstants.STR_SPEC_COMA)) {
									continue;
								}
								actual.append(termAtt.toString());
								actual.append(OperateConstants.STR_SPEC_COMA);
							}

							ezcFacl.setFaclEngMorp(actual.toString());
						}
						
						//서브 이미지 세팅
						if(faclData.getSubImages() != null) {
							
							//시설 이미지 DB 엔티티
							ezcFaclImgList = new ArrayList<EzcFaclImg>();
							imgUrlStringList = new ArrayList<String>();
							imageList = new ArrayList<ImageSDO>();
							
							for(AllRegSubImagesOutSDO subImages : faclData.getSubImages()) {
								ezcFaclImg = new EzcFaclImg();
								//ezcFaclImg.setFaclCd(faclCd); sequnce
								//ezcFaclImg.setFaclImgSeq(faclImgSeq); sequnce
								ezcFaclImg.setFaclImgType(CodeDataConstants.CD_FACL_IMG_TYPE_G0080001);
								//+ PARTNER_IMG_URL 컬럼 추가할 당시 PARTNER_IMG_URL 컬럼에 이미지 URL을 저장하고  IMG_URL 컬럼에 다운로드 경로 저장하라고 함 (from 전용필차장) 
								ezcFaclImg.setPartnerImgUrl(subImages.getImage() != null ? subImages.getImage().trim() : OperateConstants.STR_BLANK); // 이미지 URL 
								ezcFaclImg.setImgDesc(subImages.getDesc()); // 이미지 설명
								
								//이미지 URL이 empty이면 패스 (저장할 의미가 없음)
								if(!ezcFaclImg.getPartnerImgUrl().isEmpty() && imgUrlStringList.indexOf(ezcFaclImg.getPartnerImgUrl()) == -1) {
									
									if(!ezcFacl.getMainImgUrl().isEmpty() && ezcFacl.getMainImgUrl().equals(ezcFaclImg.getPartnerImgUrl())) {
										ezcFaclImg.setMainImgYn(CodeDataConstants.CD_Y); // 메인 이미지 여부
									}
									else {
										ezcFaclImg.setMainImgYn(CodeDataConstants.CD_N); // 메인 이미지 여부
									}
									//동일한 이미지URL 중복 체크를 위한 리스트 ADD 
									imgUrlStringList.add(ezcFaclImg.getPartnerImgUrl());
									//DB 저장대상 이미지 URL정보 ADD
									ezcFaclImgList.add(ezcFaclImg);
								}
							}
							
							if(imgUrlStringList != null) {
								imgUrlStringList.clear();
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
					out = insertFaclRegData(out, ezcFaclList, 0);
					/** 저장 개수가 존재하면 */
					if(out.getTxCount() > 0) {
						if(out.getData() == null) {
							out.setData(new ArrayList<AllRegDataOutSDO>());
						}
						/** 트렌젝션 성공 개수 */
						//out.setTxCount(out.getTxCount() + out.getSubTxCount());
						logger.debug("# TxCount : {}", out.getTxCount());
					}
				}
				
				if(assets != null && assets.size() > nextIndex) {
					//다음 인터페이스 데이터 저장 실행
					insertAllFacl(assets, out, detailCdList, nextIndex);
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
		return out;
	}
	
	
	
	@APIOperation(description="제휴사 별 시설 데이터 입력")
	public AllRegOutSDO insertFaclRegData(AllRegOutSDO out, List<EzcFacl> ezcFacls/* 제휴사 별 시설 목록 */, Integer fromIndex) {
		logger.debug("[START] insertFaclRegData ezcFacls.size : {}, fromIndex : {}, txCount : {}", (ezcFacls != null ? ezcFacls.size() : 0), fromIndex);
		
		outsideRepository = (OutsideRepository) LApplicationContext.getBean(outsideRepository, OutsideRepository.class);
		
		/**
		 * 시설 50개씩 connection 끊어서 실행
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
				out = outsideRepository.insertAllReg(out, saveFaclRegDatas, fromIndex, toIndex, true);
			}
			
			if(ezcFacls != null && ezcFacls.size() > toIndex) {
				insertFaclRegData(out, ezcFacls/* 제휴사 별 시설 목록 */, toIndex);
			}
		}
		catch(Exception e) {
			logger.error(APIUtil.formatMessage("제휴사 별 시설 데이터 입력 장애발생 (입력 구간 from/to : {} ~ {})", new Object[]{fromIndex, toIndex}), e);
		}

		logger.debug("[END] insertFaclRegData subTxCount : {}", out.getTxCount());
		return out;
	}
	
	@APIOperation(description="Http URL Image Download Multi Communication API", isExecTest=true)
	public AllRegFaclImgOutSDO downloadMultiImage() {
		return downloadMultiImage(null);
	}
	
	@APIOperation(description="Http URL Image Download Multi Communication API", isExecTest=true)
	public AllRegFaclImgOutSDO downloadMultiImage(AllRegOutSDO inRealtimePart) {
		logger.debug("[START] downloadMultiImage");
		
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		
		AllRegFaclImgOutSDO out = null;
		Integer txCount = OperateConstants.INTEGER_ZERO_VALUE;
		Integer downSuceCount = OperateConstants.INTEGER_ZERO_VALUE;
		Integer downFailCount = OperateConstants.INTEGER_ZERO_VALUE;
		List<Future<?>> futures = null;
		EzcFaclImg ezcFaclImg = null;
		List<EzcFaclImg> ezcFaclImgList = null;
		List<EzcFaclImg> finalFaclImgList = null;
		ImageSDO inImageSDO = null;
		ImageSDO outImageSDO = null;
		EzcFaclImg outEzcFaclImg = null;
		CallableExecutor executor = null;
		Callable<ImageSDO> callable = null;
		File deleteFile = null;
		boolean isDelete = false;

		try {
			
			if(inRealtimePart != null) {
				//파일 삭제
				if(inRealtimePart.getDeleteDownloadFilePathList() != null) {
					for(String deletePath : inRealtimePart.getDeleteDownloadFilePathList()) {
						deleteFile = new File(deletePath);
						isDelete = false;
						if(deleteFile.exists()) {
							isDelete = deleteFile.delete();
						}
						logger.debug("# deleteFile : {}, isDelete : {}", deletePath, isDelete);
					}
				}
			}
			
			out = new AllRegFaclImgOutSDO();
			/**
			 * 1. 이미지 테이블 데이터 조회
			 * 2. 이미지 url의 이미지 파일 다운로드
			 * 3. 결과 리턴
			 */
			if(inRealtimePart != null) {
				
				if(inRealtimePart.getCreateDownloadFileUrlList() != null) {
					ezcFaclImgList = new ArrayList<EzcFaclImg>();
					for(AllRegDataRealtimeImageOutSDO realtimePartFile : inRealtimePart.getCreateDownloadFileUrlList()) {
						
						ezcFaclImg = (EzcFaclImg) propertyUtil.copySameProperty(realtimePartFile, EzcFaclImg.class);
						ezcFaclImgList.add(ezcFaclImg);
					}
				}
			}
			else {
				ezcFaclImg = new EzcFaclImg();
				propertyUtil.removeFieldData(ezcFaclImg, "regId","regDt","modiId","modiDt");
				//조회 조건 현제 없음
				ezcFaclImgList = getBuildImageList(new ArrayList<EzcFaclImg>(), ezcFaclImg, 1, FACL_IMG_PAGE_SIZE);
				logger.debug("# ezcFaclImgList.size : {}", ezcFaclImgList.size());
			}
			
			if(ezcFaclImgList != null && ezcFaclImgList.size() > 0) {
				
				executor = new CallableExecutor();
				executor.initThreadPool(40);
				int count = 1;
				for(EzcFaclImg imageParam : ezcFaclImgList) {
					
					inImageSDO = new ImageSDO();
					inImageSDO.setImageURL(imageParam.getPartnerImgUrl());
					inImageSDO.setChildPath(new StringBuffer().append(imageParam.getPartnerCd()).append(File.separator)
							.append(imageParam.getCityCd()).append(File.separator)
							.append(imageParam.getAreaCd()).toString());
					
					inImageSDO.setDummy(imageParam);
					//다운로드 쓰레드 세팅
					callable = new DownloadMultiService(inImageSDO, count);
					//설정된 멀티쓰레드 개수만큼 반복 실행 
					executor.addCall(callable);
					count++;
				}
				
				futures = executor.getResult();
				if(futures != null) {
					logger.debug("- DownloadMultiService futures.size() : {}", futures.size());
					
					finalFaclImgList = new ArrayList<EzcFaclImg>();
					
					for(Future<?> future : futures) {
						if(future.get() != null) {
							outImageSDO = (ImageSDO) future.get();
							//logger.debug("[IMAGE-DOWNLOAD-OUTPUT] {}", outImageSDO);
							if(outImageSDO != null) {
								
								if(outImageSDO.getDummy() != null && EzcFaclImg.class.isAssignableFrom(outImageSDO.getDummy().getClass())) {
									
									outEzcFaclImg = (EzcFaclImg) outImageSDO.getDummy();
									
									if(outImageSDO.isSave()) {
										//ok
										downSuceCount++;
										outEzcFaclImg.setImgUrl(outImageSDO.getRelativePath());
										outEzcFaclImg.setImgCletYn(CodeDataConstants.CD_Y);
										outEzcFaclImg.setImgCletMsg(MessageConstants.getMessage(MessageConstants.RESPONSE_CODE_1000));
										outEzcFaclImg.setImgFileSize(new BigDecimal(outImageSDO.getFileSize()));
										logger.debug("[DOWNLOAD-SUCCESS-INFO] ImgUrl : {}", outEzcFaclImg.getImgUrl());
									}
									else {
										//fail
										downFailCount++;
										outEzcFaclImg.setImgUrl(null);
										outEzcFaclImg.setImgCletYn(CodeDataConstants.CD_N);
										outEzcFaclImg.setImgCletMsg(outImageSDO.getDescription());
										outEzcFaclImg.setImgFileSize(OperateConstants.BIGDECIMAL_ZERO_VALUE);
										logger.debug("[DOWNLOAD-FAIL-INFO] ImgUrl : {}", outEzcFaclImg.getImgUrl());
									}
									
									finalFaclImgList.add(outEzcFaclImg);
								}
								else {
									logger.warn("[LOOP-PROC] DownloadMultiService에 잘못된 Dummy 객체가 전달되었습니다.");
								}
							}
						}
					}
					
					//시설 이미지 데이터 UPDATE
					txCount = updateBuildImage(finalFaclImgList, 0, 0);
				}
				
				out.setDownSuceCount(downSuceCount);
				out.setDownFailCount(downFailCount);
				out.setTxCount(txCount);
			}
		} catch (APIException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9401, "■ 이미지 다운로드 다중 인터페이스 장애 발생", e);
		} catch (Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9401, "■ 이미지 다운로드 다중 인터페이스 장애 발생", e);
		} finally {
			if(ezcFaclImgList != null) {
				ezcFaclImgList.clear();
			}
			if(futures != null) {
				futures.clear();
			}
			if(executor != null) {
				executor.clear();
			}
		}
		
		logger.debug("[END] downloadMultiImage");		
		return out;
	}
	
	private Integer updateBuildImage(List<EzcFaclImg> finalFaclImgList, Integer fromIndex, Integer txCount) {

		outsideRepository = (OutsideRepository) LApplicationContext.getBean(outsideRepository, OutsideRepository.class);
		
		/**
		 * EZC_FACL_IMG update를 3000개씩 commit 처리 
		 */
		Integer toIndex = fromIndex + 3000;
		List<EzcFaclImg> subFaclImgList = null;
		if(toIndex > finalFaclImgList.size()) {
			toIndex = finalFaclImgList.size();
		}
		
		try {
			
			logger.debug("* updateBuildImage subList 'fromIndex : {} ~ toIndex : {}'", fromIndex, toIndex);
			subFaclImgList = finalFaclImgList.subList(fromIndex, toIndex);
			logger.debug("* updateBuildImage subFaclImgList.size {}", (subFaclImgList != null ? subFaclImgList.size() : 0));
			
			if(subFaclImgList != null && subFaclImgList.size() > 0) {
				//시설 이미지 데이터 UPDATE
				txCount += outsideRepository.updateBuildImage(subFaclImgList, true);				
			}
			
			if(finalFaclImgList != null && finalFaclImgList.size() > toIndex) {
				updateBuildImage(finalFaclImgList, toIndex, txCount);
			}
		}
		catch(Exception e) {
			logger.error(APIUtil.formatMessage("전체 시설 이미지 다운로드 정보 업데이트 (갱신 목록 구간 from/to : {} ~ {})", new Object[]{fromIndex, toIndex}), e);
		}
		
		
		return txCount;
	}
	
	
	@APIOperation(description="시설 이미지 전체 조회(페이징조회)")
	private List<EzcFaclImg> getBuildImageList(List<EzcFaclImg> ezcFaclImgList, EzcFaclImg ezcFaclImg, Integer pageNum, Integer pageSize) {
		logger.debug("[START] getBuildImageList pageNum : {}, pageSize : {}", pageNum, pageSize);
		
		outsideRepository = (OutsideRepository) LApplicationContext.getBean(outsideRepository, OutsideRepository.class);
		
		ezcFaclImg.setPageNum(pageNum);
		ezcFaclImg.setPageCount(pageSize);
		
		List<EzcFaclImg> list = outsideRepository.selectListBuildImage(ezcFaclImg);
		
		if(list != null && list.size() > 0) {
			ezcFaclImgList.addAll(list);
			getBuildImageList(ezcFaclImgList, ezcFaclImg, (pageNum + 1), pageSize);
		}
		
		logger.debug("[END] getBuildImageList");
		return ezcFaclImgList;
	}
	
	
	/**
	 * EZC_CACHE_DAY_PRICE, EZC_CACHE_DAY_PRICE_LOG 데이터 적제  ( 맵핑시설 테이블에 존재하는 시설의 당일특가만 적제 가능 )
	 * @param userAgentDTO
	 * @return 
	 */
	@APIOperation(description="당일특가검색 인터페이스")
	public SddSearchOutSDO callSddSearch(UserAgentSDO userAgentDTO) {
		
		inteface = (HttpInterfaceExecutor) LApplicationContext.getBean(inteface, HttpInterfaceExecutor.class);
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
		
		inteface = (HttpInterfaceExecutor) LApplicationContext.getBean(inteface, HttpInterfaceExecutor.class);
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
