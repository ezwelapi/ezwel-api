package com.ezwel.htl.interfaces.server.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
import com.ezwel.htl.interfaces.commons.http.HttpInterfaceExecutor;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigSDO;
import com.ezwel.htl.interfaces.commons.http.data.MultiHttpConfigSDO;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
import com.ezwel.htl.interfaces.commons.sdo.ApiBatcLogSDO;
import com.ezwel.htl.interfaces.commons.sdo.IfLogSDO;
import com.ezwel.htl.interfaces.commons.sdo.ImageSDO;
import com.ezwel.htl.interfaces.commons.thread.CallableExecutor;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.commons.utils.StackTraceUtil;
import com.ezwel.htl.interfaces.commons.validation.ParamValidate;
import com.ezwel.htl.interfaces.commons.validation.data.ParamValidateSDO;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractServiceObject;
import com.ezwel.htl.interfaces.server.commons.constants.CodeDataConstants;
import com.ezwel.htl.interfaces.server.commons.morpheme.cm.MorphemeUtil;
import com.ezwel.htl.interfaces.server.commons.sdo.EzcFaclMappingSDO;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.commons.utils.CommonUtil;
import com.ezwel.htl.interfaces.server.commons.utils.CoordinateUtil;
import com.ezwel.htl.interfaces.server.commons.utils.FileUtil;
import com.ezwel.htl.interfaces.server.component.FaclMappingComponent;
import com.ezwel.htl.interfaces.server.entities.EzcCityCd;
import com.ezwel.htl.interfaces.server.entities.EzcDetailCd;
import com.ezwel.htl.interfaces.server.entities.EzcFacl;
import com.ezwel.htl.interfaces.server.entities.EzcFaclImg;
import com.ezwel.htl.interfaces.server.repository.CommonRepository;
import com.ezwel.htl.interfaces.server.repository.OutsideRepository;
import com.ezwel.htl.interfaces.server.sdo.FaclSDO;
import com.ezwel.htl.interfaces.server.sdo.TransactionOutSDO;
import com.ezwel.htl.interfaces.server.thread.DownloadMultiCallable;
import com.ezwel.htl.interfaces.server.thread.FaclMappingMultiCallable;
import com.ezwel.htl.interfaces.server.thread.RoomReadMultiCallable;
import com.ezwel.htl.interfaces.service.data.allReg.AllRegDataOutSDO;
import com.ezwel.htl.interfaces.service.data.allReg.AllRegDataRealtimeImageOutSDO;
import com.ezwel.htl.interfaces.service.data.allReg.AllRegFaclImgOutSDO;
import com.ezwel.htl.interfaces.service.data.allReg.AllRegOutSDO;
import com.ezwel.htl.interfaces.service.data.allReg.AllRegSubImagesOutSDO;
import com.ezwel.htl.interfaces.service.data.faclSearch.FaclSearchDataOutSDO;
import com.ezwel.htl.interfaces.service.data.faclSearch.FaclSearchInSDO;
import com.ezwel.htl.interfaces.service.data.faclSearch.FaclSearchOutSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadDataOutSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadInSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadOutSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadSeachMinInSDO;
import com.ezwel.htl.interfaces.service.data.sddSearch.SddSearchDataOutSDO;
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
	
	private CoordinateUtil coordinateUtil;
	
	private PropertyUtil propertyUtil;
	
	private FileUtil fileUtil;
	
	private FaclMappingComponent faclMappingComponent;
	
	private StackTraceUtil stackTraceUtil;
	
	/** 제휴사 별 시설 정보 transaction commit 건수 */
	private static final Integer FACL_REG_DATA_TX_COUNT;
	
	/** 이미지 다운로드 멀티쓰레드 개수 */
	private static final Integer IMG_DOWNLOAD_MULTI_COUNT;
	
	/** 시설정보 조회 및 매핑 멀티쓰레드 개수 */
	private static final Integer FACL_MAPPING_MULTI_COUNT;
	
	/** 시설 이미지 전체 조회 페이징 개수 */
	private static final Integer FACL_IMG_PAGE_SIZE;

	/** 시설 형태소 조회 페이징 개수 */
	public static final Integer FACL_MORP_PAGE_SIZE;
	
	/** 시설 이미지 변경 정보 1 커넥션당 업데이트 개수  */
	private static final Integer FACL_COMM_SAVE_COUNT;
	
	/** 시설정보 조회 및 매핑 멀티쓰레드 개수 */
	private static final Integer ROOM_READ_MULTI_COUNT;
	
	/** 전체시설일괄등록 실행 중 플래그 */
	private static boolean isCallAllRegRunning;

	/** 시설매핑 실행 중 플래그 */
	private static boolean isFaclMappingRunning;

	/** 시설매핑 실행 중 플래그 */
	private static boolean isFaclImageDownloadRunning;
	
	static {
		ROOM_READ_MULTI_COUNT = 20;
		FACL_REG_DATA_TX_COUNT = 50;
		IMG_DOWNLOAD_MULTI_COUNT = 20;
		FACL_MAPPING_MULTI_COUNT = 10;
		FACL_IMG_PAGE_SIZE = 10000;
		FACL_MORP_PAGE_SIZE = 5000;
		FACL_COMM_SAVE_COUNT = 3000;
		isCallAllRegRunning = false;
		isFaclMappingRunning = false;
		isFaclImageDownloadRunning = false;
	}

	public static boolean isCallAllRegRunning() {
		return isCallAllRegRunning;
	}
	
	public static boolean isFaclMappingRunning() {
		return isFaclMappingRunning;
	}

	public static boolean isFaclImageDownloadRunning() {
		return isFaclImageDownloadRunning;
	}

	/**
	 * 맵핑 시설 : EZC_FACL, EZC_FACL_IMG, EZC_FACL_AMENT ( 1 : N : N ), 데이터 적제 
	 * 요청(입력) 파라메터 없음
	 */
	@APIOperation(description="전체시설일괄등록 인터페이스")
	public synchronized AllRegOutSDO callAllReg(UserAgentSDO userAgentDTO) {
		
		if(isCallAllRegRunning()) {
			throw new APIException("전체시설일괄등록 인터페이스가 이미 실행중입니다.");
		}
		else {
			//실행중 변환
			isCallAllRegRunning = true;
		}
		
		AllRegOutSDO out = null;
		MultiHttpConfigSDO multi = null;
		List<MultiHttpConfigSDO> multiHttpConfigList = null;
		List<HttpConfigSDO> channelList = null;
		List<AllRegOutSDO> assets = null;
		
		try {
			
			inteface = (HttpInterfaceExecutor) LApplicationContext.getBean(inteface, HttpInterfaceExecutor.class);
			configureHelper = (ConfigureHelper) LApplicationContext.getBean(configureHelper, ConfigureHelper.class);
			commonRepository = (CommonRepository) LApplicationContext.getBean(commonRepository, CommonRepository.class);
			outsideRepository = (OutsideRepository) LApplicationContext.getBean(outsideRepository, OutsideRepository.class);
			commonUtil = (CommonUtil) LApplicationContext.getBean(commonUtil, CommonUtil.class);
			
			
			multiHttpConfigList = new ArrayList<MultiHttpConfigSDO>();
			
			channelList = InterfaceFactory.getChannelGroup(userAgentDTO.getHttpAgentGroupId());
			if(channelList != null) {
				
				for(HttpConfigSDO httpConfigSDO : channelList) {
					
					multi = new MultiHttpConfigSDO();
					configureHelper.setupUserAgentInfo(userAgentDTO, httpConfigSDO);
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
			assets = inteface.sendMultiJSON(multiHttpConfigList);
			
			out = saveAllReg(assets);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "전체시설일괄등록 인터페이스 장애발생.", e);
		}
		finally {
			//실행중 해지
			isCallAllRegRunning = false;
			
			if(assets != null) {
				assets.clear();
			}
		}
		
		return out;
	}	

	public AllRegOutSDO saveAllReg(List<AllRegOutSDO> assets) {
		logger.debug("[START] saveAllReg");
		
		/*****************************
		 * [START] DB LOG DATA SETTING
		 *****************************/
		ApiBatcLogSDO apiBatcLogSDO = new ApiBatcLogSDO();
		apiBatcLogSDO.setBatcProgType(this.getClass().getName().concat(OperateConstants.STR_AT).concat("saveAllReg"));
		apiBatcLogSDO.setBatcDesc("전체시설일괄등록");
		apiBatcLogSDO.setBatcLogType(MessageConstants.API_BATCH_LOG_TYPE_TM);
		apiBatcLogSDO.setExecStrtMlisSecd(APIUtil.currentTimeMillis());
		
		AllRegOutSDO out = null;
		try {
		
			if(assets != null && assets.size() > 0) {
				
				/** execute code select transaction */ 
				EzcDetailCd inEzcDetailCd = new EzcDetailCd();
				inEzcDetailCd.addClassCdList(CodeDataConstants.CD_CLASS_CD_G002, CodeDataConstants.CD_CLASS_CD_G003, CodeDataConstants.CD_CLASS_CD_C007, CodeDataConstants.CD_CLASS_CD_G005);
				/** execute save transaction */
				out = insertAllFacl(assets, new AllRegOutSDO(), commonRepository.selectListCommonCode(inEzcDetailCd), 0);
			
				// 등록/갱신 일시(yyyyMMddHHmmss)가 인터페이스 실행(StartTimeMillis)보다 이전 데이터는 전문에서 제외된 시설로서 사용안함 처리
				// 조건 : 시설 구분 컬럼 데이터가 API 인것만
				EzcFacl removeEzcFacl = new EzcFacl();
				removeEzcFacl.setFaclDiv(CodeDataConstants.CD_API_G0010001); // API
				removeEzcFacl.setUseYn(CodeDataConstants.CD_N);
				out.setTxCount(out.getTxCount() + outsideRepository.updateRemoveFacl(removeEzcFacl, true));
			}
			
			if(out != null) {
				logger.info("[SERVICE-FINAL] createDownloadFileUrlList size : {}", (out.getCreateDownloadFileUrlList() != null ? out.getCreateDownloadFileUrlList().size() : 0));
				logger.info("[SERVICE-FINAL] deleteDownloadFilePathList size : {}", (out.getDeleteDownloadFilePathList() != null ? out.getDeleteDownloadFilePathList().size() : 0));
				
				/** 데이터 저장이 모두 끝난후 변경사항이 존재하는 제휴사 별 멀티쓰레드 이미지 다운로드/삭제 실행 */
				downloadMultiImage(out);	
			}
		}
		finally {
			
			/*****************************
			 * [END] DB LOG DATA SETTING
			 *****************************/
			
			Local.commonHeader().addBatchExecLog(apiBatcLogSDO, true);
		}

		logger.debug("[END] saveAllReg");
		return out;
	}
	
	
	@APIOperation(description="시설 매핑")
	public synchronized TransactionOutSDO execFaclMapping(FaclSDO faclSDO) {
		logger.debug("[START] execFaclMapping");
		
		if(isFaclMappingRunning()) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9700, "전체시설일괄등록 인터페이스가 이미 실행중입니다.");
		}
		else {
			//실행중 변환
			isFaclMappingRunning = true;
		}
		
		TransactionOutSDO out = null;
		List<EzcFacl> faclCodeGroupList = null;
		List<EzcFacl> faclMorpSearchList = null;
		List<EzcFacl> morpCompareFinalList = null;
		EzcFacl ezcFacl = null;
		//멀티쓰레드 객체
		List<Future<?>> futures = null;
		CallableExecutor executor = null;
		Callable<List<EzcFacl>> callable = null;
		
		try {
			out = new TransactionOutSDO();
			
			propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
			outsideRepository = (OutsideRepository) LApplicationContext.getBean(outsideRepository, OutsideRepository.class);
			fileUtil = (FileUtil) LApplicationContext.getBean(fileUtil, FileUtil.class);
			coordinateUtil = (CoordinateUtil) LApplicationContext.getBean(coordinateUtil, CoordinateUtil.class);
			faclMappingComponent = (FaclMappingComponent) LApplicationContext.getBean(faclMappingComponent, FaclMappingComponent.class);
			
			ezcFacl = (EzcFacl) propertyUtil.copySameProperty(faclSDO, EzcFacl.class);
			//::DB 시도/지역/숙소유형/시설유형 그룹 목록
			faclCodeGroupList = outsideRepository.selectFaclCodeGroupList(ezcFacl);
			
			if(faclCodeGroupList == null || faclCodeGroupList.size() == 0) {
				return out;
			}
			
			executor = new CallableExecutor();
			executor.initThreadPool(FACL_MAPPING_MULTI_COUNT);
			
			logger.debug(" ■■ [PROC-START] 시도/지역/숙소유형/시설유형 그룹별 지역의 시설 매핑을 시작합니다.");
			
			out.addProfileMap("시도/지역/숙소유형/시설유형 그룹 목록 개수", faclCodeGroupList.size());
			
			int count = 1;
			for(EzcFacl faclCode : faclCodeGroupList) {
				
				faclCode.setMorpDelt(OperateConstants.STR_COMA);
				//쓰레드 세팅
				callable = new FaclMappingMultiCallable(faclCode, count, out);
				//설정된 멀티쓰레드 개수만큼 반복 실행 
				executor.addCall(callable);
				count++;	
			}
		
			logger.debug(" ■■ [PROC-END] 시도/지역/숙소유형/시설유형 그룹별 지역의 시설 매핑이 종료되었습니다.");
			
			//매핑 정보 취합 목록
			morpCompareFinalList = new ArrayList<EzcFacl>();
			futures = executor.getResult();
			if(futures != null) {
				
				logger.debug(" ■■ [PROC-START] 시설 매핑 결과를 수집합니다. ");
				for(Future<?> future : futures) {
					//매핑 정보 취합
					if(future.get() != null) {
						morpCompareFinalList.addAll((List<EzcFacl>) future.get());
					}
				}
				logger.debug(" ■■ [PROC-END] 시설 매핑 결과를 수집이 완료되었습니다.");
			}
			else {
				logger.debug(" ■■ [PROC-WARN] 시설 매핑 결과가 존재하지 않습니다.");
			}
			
			//시도/지역/숙소유형/시설유형 그룹 목록 clear
			if(faclCodeGroupList != null) {
				faclCodeGroupList.clear(); 
			}
			
			out.addProfileMap("매핑 목록 그룹 개수", morpCompareFinalList.size());
			
			if(morpCompareFinalList != null && morpCompareFinalList.size() > 0) {
				logger.debug(" ■■ [PROC-START] 시설 매핑 데이터 저장을 시작합니다.");
				//시설 매핑 데이터 저장
				out.setTxCount(mergeFaclMappingData(morpCompareFinalList, out));
				
				logger.debug(" ■■ [PROC-END] 시설 매핑 데이터 저장이 종료되었습니다.");
			}
		}
		catch(Exception e) {
			//매핑 시설 정보 추출 중 장애 발생
			throw new APIException(MessageConstants.RESPONSE_CODE_9600, "매핑 시설 정보 추출 중 장애 발생", e);
		}
		finally {
			
			if(faclCodeGroupList != null) {
				//try 절에서 clear전 Exception 발생시 finally에서 clear
				faclCodeGroupList.clear();
			}
			if(faclMorpSearchList != null) {
				//try 절에서 clear전 Exception 발생시 finally에서 clear
				faclMorpSearchList.clear();
			}
			if(executor != null) {
				executor.clear();
			}
			
			//시설 매핑 실행 종료 전환
			isFaclMappingRunning = false;
		} 
		
		logger.debug("[END] execFaclMapping");
		return out;
	}

	@APIOperation(description="시설 매핑 데이터 저장") 
	private Integer mergeFaclMappingData(List<EzcFacl> morpCompareFinalList, TransactionOutSDO txOut) {
		logger.debug("[START] 시설 매핑 데이터 저장 list size : {}", (morpCompareFinalList != null ? morpCompareFinalList.size() : 0));
		
		/*****************************
		 * [START] DB LOG DATA SETTING
		 *****************************/
		ApiBatcLogSDO apiBatcLogSDO = new ApiBatcLogSDO();
		apiBatcLogSDO.setBatcProgType(this.getClass().getName().concat(OperateConstants.STR_AT).concat("mergeFaclMappingData"));
		apiBatcLogSDO.setBatcDesc("시설 매핑 데이터 저장");
		apiBatcLogSDO.setBatcLogType(MessageConstants.API_BATCH_LOG_TYPE_TM);
		apiBatcLogSDO.setExecStrtMlisSecd(APIUtil.currentTimeMillis());
		
		Integer out = OperateConstants.INTEGER_ZERO_VALUE;
		List<EzcFaclMappingSDO> mappingFaclFinalList = null;
		try {
			//매핑 그룹 시설 데이터 저장
			out += mergeFaclMappingGrpData(morpCompareFinalList, out, 0, txOut);
			
			mappingFaclFinalList = new ArrayList<EzcFaclMappingSDO>();	
			for(EzcFacl groupFacl : morpCompareFinalList) {
				logger.debug("- Final grpFaclCd : {}, Prnt : {}", groupFacl.getGrpFaclCd(), groupFacl.getPrntFaclCd());
				
				if(groupFacl.getEzcFaclMappingList() != null) {
					for(EzcFaclMappingSDO mappingFinal : groupFacl.getEzcFaclMappingList()) {
						mappingFinal.setGrpFaclCd(groupFacl.getGrpFaclCd());
						logger.debug("# mappingFinal : {}", mappingFinal);
						mappingFaclFinalList.add(mappingFinal);
					}
				}
			}
			
			logger.debug("$mappingFaclFinalList size : {}", mappingFaclFinalList.size());
			//매핑 시설 데이터 저장
			out += mergeFaclMappingData(mappingFaclFinalList, out, 0, txOut);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9500, "시설 매핑 데이터 저장 장애발생", e);
		}
		finally {
			if(morpCompareFinalList != null) {
				morpCompareFinalList.clear();
			}
			
			/*****************************
			 * [END] DB LOG DATA SETTING
			 *****************************/
			
			Local.commonHeader().addBatchExecLog(apiBatcLogSDO, true);			
		}
		 
		logger.debug("[END] 시설 매핑 데이터 저장 txCount : {}", out);
		return out;
	}
	
	
	@APIOperation(description="시설 매핑 그룹 데이터 저장") 
	private Integer mergeFaclMappingGrpData(List<EzcFacl> morpCompareFinalList, Integer txCount, Integer fromIndex, TransactionOutSDO txOut) {
		logger.debug("[START] mergeFaclMappingGrpData size : {}, fromIndex : {}", (morpCompareFinalList != null ? morpCompareFinalList.size() : 0), fromIndex);

		/**
		 * 시설 매핑 3000 개씩 connection 끊어서 실행
		 */
		Integer toIndex = fromIndex + FACL_COMM_SAVE_COUNT;
		
		List<EzcFacl> saveFaclMappingList = null;
		if(toIndex > morpCompareFinalList.size()) {
			toIndex = morpCompareFinalList.size();
		}

		try {
			
			logger.debug("* mergeFaclMappingGrpData subList 'fromIndex : {} ~ toIndex : {}'", fromIndex, toIndex);
			saveFaclMappingList = morpCompareFinalList.subList(fromIndex, toIndex);
			logger.debug("* mergeFaclMappingGrpData saveFaclMappingList.size {}", (saveFaclMappingList != null ? saveFaclMappingList.size() : 0));
			
			if(saveFaclMappingList != null && saveFaclMappingList.size() > 0) {
				txCount += outsideRepository.mergeFaclMappingGrpData(saveFaclMappingList, fromIndex, toIndex, true, txOut);
			}
			
			if(morpCompareFinalList != null && morpCompareFinalList.size() > toIndex) {
				logger.debug("* Next Call MergeFaclMappingGrpData txCount : {}, toIndex : {}", txCount, toIndex);
				mergeFaclMappingGrpData(morpCompareFinalList, txCount, toIndex, txOut);
			}
		}
		catch(Exception e) {
			logger.error(APIUtil.formatMessage("시설 매핑 데이터 저장 장애발생 (입력 구간 from/to : {} ~ {})", new Object[]{fromIndex, toIndex}), e);
		}
		
		logger.debug("[END] mergeFaclMappingGrpData txCount : {}", txCount);
		return txCount;
	}
	

	@APIOperation(description="시설 매핑 데이터 저장") 
	private Integer mergeFaclMappingData(List<EzcFaclMappingSDO> mappingFaclFinalList, Integer txCount, Integer fromIndex, TransactionOutSDO txOut) {
		logger.debug("[START] mergeFaclMappingData size : {}, fromIndex : {}", (mappingFaclFinalList != null ? mappingFaclFinalList.size() : 0), fromIndex);
		Integer out = OperateConstants.INTEGER_ZERO_VALUE;

		/**
		 * 시설 매핑 3000 개씩 connection 끊어서 실행
		 */
		Integer toIndex = fromIndex + 1000 /*FACL_COMM_SAVE_COUNT*/;
		
		List<EzcFaclMappingSDO> saveFaclMappingList = null;
		if(toIndex > mappingFaclFinalList.size()) {
			toIndex = mappingFaclFinalList.size();
		}

		try {
			
			logger.debug("* mergeFaclMappingData subList 'fromIndex : {} ~ toIndex : {}'", fromIndex, toIndex);
			saveFaclMappingList = mappingFaclFinalList.subList(fromIndex, toIndex);
			logger.debug("* mergeFaclMappingData saveFaclMappingList.size {}", (saveFaclMappingList != null ? saveFaclMappingList.size() : 0));
			
			if(saveFaclMappingList != null && saveFaclMappingList.size() > 0) {
				txCount += outsideRepository.mergeFaclMappingData(saveFaclMappingList, fromIndex, toIndex, true, txOut);
			}
			
			if(mappingFaclFinalList != null && mappingFaclFinalList.size() > toIndex) {
				logger.debug("* Next Call MergeFaclMappingData txCount : {}, toIndex : {}", txCount, toIndex);
				mergeFaclMappingData(mappingFaclFinalList, txCount, toIndex, txOut);
			}
		}
		catch(Exception e) {
			logger.error(APIUtil.formatMessage("시설 매핑 데이터 저장 장애발생 (입력 구간 from/to : {} ~ {})", new Object[]{fromIndex, toIndex}), e);
		}
		
		logger.debug("[END] mergeFaclMappingData txCount : {}", out);
		return out;
	}
	
	
	@APIOperation(description="조건 별 시설 전체 조회(페이징조회)")
	public List<EzcFacl> getFaclMappingMorpDataList(List<EzcFacl> ezcFaclList, EzcFacl ezcFacl, Integer pageNum, Integer pageSize) {
		logger.debug("[START] getFaclMappingDataAll pageNum : {}, pageSize : {}", pageNum, pageSize);
		
		outsideRepository = (OutsideRepository) LApplicationContext.getBean(outsideRepository, OutsideRepository.class);
		
		ezcFacl.setPageNum(pageNum);
		ezcFacl.setPageCount(pageSize);
		
		List<EzcFacl> list = outsideRepository.selectFaclMappingMorpDataList(ezcFacl);
		
		if(list != null && list.size() > 0) {
			ezcFaclList.addAll(list);
			getFaclMappingMorpDataList(ezcFaclList, ezcFacl, (pageNum + 1), pageSize);
		}
		
		logger.debug("[END] getFaclMappingData2All");
		return ezcFaclList;
	}
	
	
	/**
	 * 맵핑 시설 : EZC_FACL, EZC_FACL_IMG, EZC_FACL_AMENT ( 1 : N : N ), 데이터 적제
	 * @param assets
	 * @param allFacl
	 * @param faclIndex
	 * @return
	 */
	@APIOperation(description="전체시설일괄등록 인터페이스")
	private AllRegOutSDO insertAllFacl(List<AllRegOutSDO> assets, AllRegOutSDO out, List<EzcDetailCd> detailCdList, Integer faclIndex) {
		logger.debug("[START] insertAllFacl assets.size : {}, allFacl : {}, detailCdList : {}, faclIndex : {}", (assets != null ? assets.size() : 0), out, (detailCdList != null ? detailCdList.size() : 0), faclIndex);
		if(assets == null) {
			throw new APIException("시설 목록이 존재하지 않거나 잘못되었습니다.");
		}
	
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

		try {
			/**
			 * 1. 제휴사 별 TX 실행
			 * 2. 제휴사 내 100개씩  commit 되도록 operation 구성
			 */
			allReg = assets.get(faclIndex);// 제휴사 (단건 / 인터페이스 전문 SDO )
			
			/**** 멀티쓰레드 인터페이스 결과 세팅 */
			out.addMultiThreadResults(allReg.getCode(), allReg.getMessage(), allReg.getRestURI());
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
				
				/** 제휴사 1건 별 이하 프로세스 실행 */
				if(allReg != null && allReg.getData() != null && allReg.getData().size() > 0) {
					/** 제휴사 별 시설 목록 */
					faclDataList = allReg.getData();
					/** ezwel 시설 정보 목록 */
					ezcFaclList = new ArrayList<EzcFacl>();
					/** 제휴사 별 시설 목록 loop */
					for(AllRegDataOutSDO faclData : faclDataList) {
						
						ezcFacl = new EzcFacl(); 
						//ezcFacl.setFaclCd(faclCd); //sequnce
						ezcFacl.setPartnerCd(allReg.getHttpAgentId()); // 에이전트 ID
						ezcFacl.setFaclDiv(CodeDataConstants.CD_API_G0010001); //시설 구분 ( API )
						ezcFacl.setPartnerGoodsCd( faclData.getPdtNo() ); //상품코드
						ezcFacl.setFaclNmKor( faclData.getPdtName() ); /* 시설 한글 명 => 호텔패스글로벌 전문에 데이터가 전달되어오지 않기때문에 임시로 EMPTY 처리함 */
						ezcFacl.setFaclNmEng( faclData.getPdtNameEng() );
						ezcFacl.setRoomType( APIUtil.NVL(commonUtil.getMasterCdForCodeList(detailCdList, faclData.getTypeCode()), "NA-G002") ); // -> DB 공통코드 (테이블 : EZC_DETAIL_CD.DETAIL_CD  = '#{typeCode}' AND EZC_DETAIL_CD.CLASS_CD = 'G002' )
						ezcFacl.setRoomClass( APIUtil.NVL(commonUtil.getMasterCdForCodeList(detailCdList, faclData.getGradeCode()), "NA-G003") ); // -> DB 공통코드 (테이블 : EZC_DETAIL_CD.DETAIL_CD  = '#{gradeCode}' AND EZC_DETAIL_CD.CLASS_CD = 'G003')
						ezcFacl.setSaleStartDd(  commonUtil.getNumberOnly(faclData.getSellStartDate(), 8)  ); // 판매시작일 => 8바이트가 넘게 오는 데이터는 에러 뱃도록 함 (전용필차장 요구사항)
						ezcFacl.setSaleEndDd( commonUtil.getNumberOnly(faclData.getSellEndDate(), 8) );	// 판매종료일 => 8바이트가 넘게 오는 데이터는 에러 뱃도록 함 (전용필차장 요구사항)
						ezcFacl.setCheckInTm( commonUtil.getNumberOnly(faclData.getCheckInTime(), 4) );	//채크인시간 => 10바이트로 변경
						ezcFacl.setCheckOutTm( commonUtil.getNumberOnly(faclData.getCheckOutTime(), 4) );	//채크아웃시간 => 10바이트로 변경 
						ezcFacl.setAreaCd( faclData.getGunguCode() );	//지역코드(군구코드) => 호텔패스글로벌 전문에 데이터가  전달되어오지 않기때문에 임시로 EMPTY 처리함 */
						ezcFacl.setCityCd( faclData.getSidoCode() );	//도시코드(시도코드)
						ezcFacl.setAddrType( APIUtil.NVL(commonUtil.getMasterCdForCodeList(detailCdList, faclData.getAddressType()), OperateConstants.STR_HYPHEN) );  //주소 유형 -> DB 공통코드 (테이블 : EZC_DETAIL_CD)
						ezcFacl.setAddr( APIUtil.NVL(faclData.getAddress(), OperateConstants.STR_EMPTY, true) );	//주소
						ezcFacl.setPost( faclData.getZipCode() );	//우편번호
						ezcFacl.setTelNum(faclData.getTelephone());	//전화 번호
						ezcFacl.setCoordY(faclData.getMapX());	//위도
						ezcFacl.setCoordX(faclData.getMapY());	//경도
						ezcFacl.setMinAmt((APIUtil.isNotEmpty(faclData.getSellPrice()) ? new BigDecimal(faclData.getSellPrice()) : null)); // 최저 금액
						ezcFacl.setDetailDescPc(faclData.getDescHTML());	//상세 설명 PC 		(제휴사 텍스트 OR HTML 설명 데이터)
						ezcFacl.setDetailDescM(faclData.getDescMobile());	//상세 설명 모바일	(제휴사 텍스트 OR HTML 설명 데이터)
						ezcFacl.setTripPropId(faclData.getTripadvisorId());	//트립어드바이저 프로퍼티 ID
						ezcFacl.setMainImgUrl(faclData.getMainImage() != null ? faclData.getMainImage().trim() : OperateConstants.STR_BLANK);		//대표 이미지 URL
						ezcFacl.setImgChangeYn(faclData.getChangeImage());	//이미지 변경 여부
						ezcFacl.setApiSyncDt(APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis(), OperateConstants.DEF_DATE_FORMAT)); //API 동기화 일시(API 동작일시)
						ezcFacl.setUseYn(CodeDataConstants.CD_Y);	//사용 여부 새로등록되는 시설에 대하여 기본 Y로 등록함
						ezcFacl.setFaclStatus(CodeDataConstants.CD_FACL_STATUS_G0040003); //시설 상태
						ezcFacl.setConfirmStatus(CodeDataConstants.CD_CONFIRM_STATUS_G0060003); //확정 상태

						// 국문 형태소 ( 한글명에 영문이 석여있는 시설들이 있음으로 한글과 영문 형태소 분석을 동시 실행한다.)
						ezcFacl.setFaclKorMorp(commonUtil.getMorphologicalAnalysis(MorphemeUtil.MORP_LANG_KO, ezcFacl.getFaclNmKor()));
						
						// 영문 형태소 ( 영문명에 한글이 석여있는 시설들이 있음으로 한글과 영문 형태소 분석을 동시 실행한다.)
						ezcFacl.setFaclEngMorp(commonUtil.getMorphologicalAnalysis(MorphemeUtil.MORP_LANG_EN, ezcFacl.getFaclNmEng()));

						//서브 이미지 세팅
						if(faclData.getSubImages() != null) {
							
							//시설 이미지 DB 엔티티
							ezcFaclImgList = new ArrayList<EzcFaclImg>();
							imgUrlStringList = new ArrayList<String>();
							imageList = new ArrayList<ImageSDO>();
							
							//전문 이미지 loop
							for(AllRegSubImagesOutSDO subImages : faclData.getSubImages()) {
								ezcFaclImg = new EzcFaclImg();
								//ezcFaclImg.setFaclCd(faclCd); sequnce
								//ezcFaclImg.setFaclImgSeq(faclImgSeq); sequnce
								ezcFaclImg.setFaclImgType(CodeDataConstants.CD_FACL_IMG_TYPE_G0080001);
								//+ PARTNER_IMG_URL 컬럼 추가할 당시 PARTNER_IMG_URL 컬럼에 이미지 URL을 저장하고  IMG_URL 컬럼에 다운로드 경로 저장하라고 함 (from 전용필차장) 
								ezcFaclImg.setPartnerImgUrl(APIUtil.NVL(subImages.getImage()).trim()); // 이미지 URL 
								ezcFaclImg.setImgDesc(subImages.getDesc()); // 이미지 설명
								
								//이미지 URL이 empty이이거나 이전에 존재하는 이미지이면 패스 (저장할 의미가 없음) 
								if(APIUtil.isNotEmpty(ezcFaclImg.getPartnerImgUrl()) && imgUrlStringList.indexOf(ezcFaclImg.getPartnerImgUrl()) == -1) {
									
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
							//동일한 이미지URL 중복 체크를 위한 리스트 clear
							imgUrlStringList.clear();
						}
						
						//DB 저장대상 이미지 정보
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
						
						try {
							//execute paramValidate
							new ParamValidate(new ParamValidateSDO(ezcFacl, new String[]{"faclCd"})).execute();
							
							//DB 엔티티에 전문 데이터 세팅
							ezcFaclList.add(ezcFacl);
						}
						catch(Exception e) {
							
							/*****************************
							 * [START] DB LOG DATA SETTING
							 *****************************/
							ApiBatcLogSDO apiBatcLogSDO = new ApiBatcLogSDO();
							apiBatcLogSDO.setBatcProgType(this.getClass().getName().concat(OperateConstants.STR_AT).concat("insertAllFacl"));
							apiBatcLogSDO.setBatcDesc("전체시설일괄등록 데이터 유효성검사");
							apiBatcLogSDO.setBatcLogType(MessageConstants.API_BATCH_LOG_TYPE_IV);
							apiBatcLogSDO.setErrCont(new StringBuffer()
									.append( "::시설 정보 유효성 검사 실패 발생시각 : " )
									.append( APIUtil.getFastDate(OperateConstants.GENERAL_DATE_FORMAT) )
									.append( OperateConstants.LINE_SEPARATOR )
									.append( "- 제휴사아이디 : " )
									.append( allReg.getHttpAgentId() )
									.append( ", 제휴사에이전트설명 : " )
									.append( allReg.getHttpAgentDesc() )
									.append( OperateConstants.LINE_SEPARATOR )
									.append( "- 유효성검사 실패 시설 : " )
									.append( ezcFacl )
									.append( OperateConstants.LINE_SEPARATOR )
									.toString());
							
							Local.commonHeader().addBatchExecLog(apiBatcLogSDO, e);
							/*****************************
							 * [END]   DB LOG DATA SETTING 
							 *****************************/

							logger.error( APIUtil.formatMessage("{} '{}({})'유효성 검사 실패", allReg.getHttpAgentDesc(), ezcFacl.getFaclNmKor(), ezcFacl.getPartnerGoodsCd()), e);
						}
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
			
			/*****************************
			 * [START] DB LOG DATA SETTING
			 *****************************/
			ApiBatcLogSDO apiBatcLogSDO = new ApiBatcLogSDO();
			apiBatcLogSDO.setBatcProgType(this.getClass().getName().concat(OperateConstants.STR_AT).concat("insertAllFacl"));
			apiBatcLogSDO.setBatcDesc("전체시설일괄등록 장애발생");
			apiBatcLogSDO.setBatcLogType(MessageConstants.API_BATCH_LOG_TYPE_IV);
			apiBatcLogSDO.setErrCont(new StringBuffer()
					.append( "::전체시설일괄등록 장애발생 발생시각 : " )
					.append( APIUtil.getFastDate(OperateConstants.GENERAL_DATE_FORMAT) )
					.append( OperateConstants.LINE_SEPARATOR )
					.append( "- 제휴사아이디 : " )
					.append( allReg.getHttpAgentId() )
					.append( ", 제휴사에이전트설명 : " )
					.append( allReg.getHttpAgentDesc() )
					.append( OperateConstants.LINE_SEPARATOR )
					.append( "- 전체시설일괄등록 실패 제휴사 : " )
					.append( allReg )
					.append( OperateConstants.LINE_SEPARATOR )
					.toString());
			
			Local.commonHeader().addBatchExecLog(apiBatcLogSDO, e);
			/*****************************
			 * [END]   DB LOG DATA SETTING 
			 *****************************/
			
			//logger.error( APIUtil.formatMessage("{} '{}({})'전체시설일괄등록 장애발생.", allReg.getHttpAgentDesc(), allReg.getHttpAgentId()), e);
			//채크 필요
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
	private AllRegOutSDO insertFaclRegData(AllRegOutSDO out, List<EzcFacl> ezcFacls/* 제휴사 별 시설 목록 */, Integer fromIndex) {
		logger.debug("[START] insertFaclRegData ezcFacls.size : {}, fromIndex : {}, txCount : {}", (ezcFacls != null ? ezcFacls.size() : 0), fromIndex);
		
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
				logger.debug("* Next Call InsertFaclRegData txCount : {}, toIndex : {}", out.getTxCount(), toIndex);
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
	public synchronized AllRegFaclImgOutSDO downloadMultiImage() {
		return downloadMultiImage(null);
	}
	
	@APIOperation(description="Http URL Image Download Multi Communication API", isExecTest=true)
	public synchronized AllRegFaclImgOutSDO downloadMultiImage(AllRegOutSDO inRealtimePart) {
		
		if(isFaclImageDownloadRunning()) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9700, "전체 시설 이미지 다운로드 프로세스가 이미 실행중입니다.");
		}
		else {
			//실행중 변환
			isFaclImageDownloadRunning = true;
		}
		
		logger.debug("[START] downloadMultiImage");
		
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

		/*****************************
		 * [START] DB LOG DATA SETTING
		 *****************************/
		ApiBatcLogSDO apiBatcLogSDO = new ApiBatcLogSDO();
		apiBatcLogSDO.setBatcProgType(this.getClass().getName().concat(OperateConstants.STR_AT).concat("downloadMultiImage"));
		apiBatcLogSDO.setBatcDesc("시설 이미지 변경정보 업데이트");
		apiBatcLogSDO.setBatcLogType(MessageConstants.API_BATCH_LOG_TYPE_TM);
		apiBatcLogSDO.setExecStrtMlisSecd(APIUtil.currentTimeMillis());
		
		try {
			
			propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
			
			if(inRealtimePart != null) {
				//파일 삭제
				if(inRealtimePart.getDeleteDownloadFilePathList() != null) {
					
					logger.info("# RealtimePart 삭제대상 파일 개수 : {}", inRealtimePart.getDeleteDownloadFilePathList().size());
					
					for(String deletePath : inRealtimePart.getDeleteDownloadFilePathList()) {
						deleteFile = new File(deletePath);
						isDelete = false;
						if(deleteFile.exists()) {
							//실제 파일 삭제
							isDelete = deleteFile.delete();
						}
						logger.info("# deleteFile : {}, isDelete : {}", deletePath, isDelete);
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
					
					logger.info("# RealtimePart 신규 다운로드 대상 파일 개수 : {}", inRealtimePart.getCreateDownloadFileUrlList().size());
					
					ezcFaclImgList = new ArrayList<EzcFaclImg>();
					for(AllRegDataRealtimeImageOutSDO realtimePartFile : inRealtimePart.getCreateDownloadFileUrlList()) {
						
						ezcFaclImg = (EzcFaclImg) propertyUtil.copySameProperty(realtimePartFile, EzcFaclImg.class);
						ezcFaclImgList.add(ezcFaclImg);
					}
				}
			}
			else {
				ezcFaclImg = new EzcFaclImg();
				// remove data => "regId", "regDt", "modiId", "modiDt" 
				propertyUtil.removeDefaulFieldData(ezcFaclImg);
				//조회 조건 현제 없음
				ezcFaclImgList = getBuildImageAllList(new ArrayList<EzcFaclImg>(), ezcFaclImg, 1, FACL_IMG_PAGE_SIZE);
				logger.debug("# ezcFaclImgList.size : {}", ezcFaclImgList.size());
			}
			
			if(ezcFaclImgList != null && ezcFaclImgList.size() > 0) {
				
				executor = new CallableExecutor();
				executor.initThreadPool(IMG_DOWNLOAD_MULTI_COUNT);
				int count = 1;
				for(EzcFaclImg imageParam : ezcFaclImgList) {
					
					inImageSDO = new ImageSDO();
					//이미지 URL
					inImageSDO.setImageURL(imageParam.getPartnerImgUrl());
					//파일 저장 경로 ( 환경설정의 이미지 저장 루트디렉토리로 부터 하위 )
					inImageSDO.setChildPath(new StringBuffer()
							.append(OperateConstants.API_NAS_ROOT_NAME_SPACE).append(File.separator)
							.append(OperateConstants.IMAGE_FILE_NAME_SPACE).append(File.separator)
							.append(imageParam.getPartnerCd()).append(File.separator)
							.append(imageParam.getCityCd()).append(File.separator)
							.append(imageParam.getFaclCd()).append(File.separator)
							.append(imageParam.getAreaCd()).toString());
					
					inImageSDO.setDummy(imageParam);
					//다운로드 쓰레드 세팅
					callable = new DownloadMultiCallable(inImageSDO, count);
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
										logger.debug("[DOWNLOAD-SUCCESS-INFO]\n[Ｏ] PartnerImgUrl : {}\n- ImgUrl : {}", outEzcFaclImg.getPartnerImgUrl(), outEzcFaclImg.getImgUrl());
									}
									else {
										//fail
										downFailCount++;
										outEzcFaclImg.setImgUrl(null);
										outEzcFaclImg.setImgCletYn(CodeDataConstants.CD_N);
										outEzcFaclImg.setImgCletMsg(outImageSDO.getDescription());
										outEzcFaclImg.setImgFileSize(OperateConstants.BIGDECIMAL_ZERO_VALUE);
										logger.debug("[DOWNLOAD-FAIL-INFO]\n[Ｘ] PartnerImgUrl : {}", outEzcFaclImg.getPartnerImgUrl());
									}
									
									finalFaclImgList.add(outEzcFaclImg);
								}
								else {
									logger.warn("[LOOP-PROC] DownloadMultiService에 잘못된 Dummy 객체가 전달되었습니다.");
								}
							}
						}
					}
					
					logger.info("[PROC] UPDATE BUILD IMAGE - START");
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
			if(inRealtimePart != null) {
				
				if(inRealtimePart.getCreateDownloadFileUrlList() != null) {
					inRealtimePart.getCreateDownloadFileUrlList().clear();
				}
				if(inRealtimePart.getDeleteDownloadFilePathList() != null) {
					inRealtimePart.getDeleteDownloadFilePathList().clear();
				}
 			}
			
			/*****************************
			 * [END] DB LOG DATA SETTING
			 *****************************/
			Local.commonHeader().addBatchExecLog(apiBatcLogSDO, true);
			
			//실행중 해제
			isFaclImageDownloadRunning = false;
		}
		
		logger.debug("[END] downloadMultiImage");		
		return out;
	}
	
	@APIOperation(description="시설 이미지 변경정보 업데이트")
	private Integer updateBuildImage(List<EzcFaclImg> finalFaclImgList, Integer fromIndex, Integer txCount) {

		outsideRepository = (OutsideRepository) LApplicationContext.getBean(outsideRepository, OutsideRepository.class);
		
		/**
		 * EZC_FACL_IMG update를 3000개씩 commit 처리 
		 */
		Integer toIndex = fromIndex + FACL_COMM_SAVE_COUNT;
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
				logger.debug("* Next Call UpdateBuildImage txCount : {}, toIndex : {}", txCount, toIndex);
				updateBuildImage(finalFaclImgList, toIndex, txCount);
			}
		}
		catch(Exception e) {
			logger.error(APIUtil.formatMessage("전체 시설 이미지 다운로드 정보 업데이트 (갱신 목록 구간 from/to : {} ~ {})", new Object[]{fromIndex, toIndex}), e);
		}
		
		return txCount;
	}
	
	
	@APIOperation(description="시설 이미지 전체 조회(페이징조회)")
	private List<EzcFaclImg> getBuildImageAllList(List<EzcFaclImg> ezcFaclImgList, EzcFaclImg ezcFaclImg, Integer pageNum, Integer pageSize) {
		logger.debug("[START] getBuildImageAllList pageNum : {}, pageSize : {}", pageNum, pageSize);
		
		outsideRepository = (OutsideRepository) LApplicationContext.getBean(outsideRepository, OutsideRepository.class);
		
		ezcFaclImg.setPageNum(pageNum);
		ezcFaclImg.setPageCount(pageSize);
		
		List<EzcFaclImg> list = outsideRepository.selectListBuildImage(ezcFaclImg);
		
		if(list != null && list.size() > 0) {
			ezcFaclImgList.addAll(list);
			getBuildImageAllList(ezcFaclImgList, ezcFaclImg, (pageNum + 1), pageSize);
		}
		
		logger.debug("[END] getBuildImageAllList");
		return ezcFaclImgList;
	}
	
	
	/**
	 * EZC_CACHE_DAY_PRICE 데이터 적제  ( 맵핑시설 테이블에 존재하는 시설의 당일특가만 적제 가능 )
	 * @param userAgentDTO
	 * @return 
	 */
	@APIOperation(description="당일특가검색 인터페이스")
	public SddSearchOutSDO callSddSearch(UserAgentSDO userAgentDTO) {
		
		inteface = (HttpInterfaceExecutor) LApplicationContext.getBean(inteface, HttpInterfaceExecutor.class);
		configureHelper = (ConfigureHelper) LApplicationContext.getBean(configureHelper, ConfigureHelper.class);
		outsideRepository = (OutsideRepository) LApplicationContext.getBean(outsideRepository, OutsideRepository.class);
		
		Integer txCount = OperateConstants.INTEGER_ZERO_VALUE;
		SddSearchOutSDO out = null;
		MultiHttpConfigSDO multi = null;
		List<HttpConfigSDO> channelList = null;
		List<MultiHttpConfigSDO> multiHttpConfigList = null;
		
		try {
			out = new SddSearchOutSDO();
			multiHttpConfigList = new ArrayList<MultiHttpConfigSDO>();
			
			channelList = InterfaceFactory.getChannelGroup(userAgentDTO.getHttpAgentGroupId());
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
			
			if(assets != null) {
				
				List<SddSearchDataOutSDO> validateDataList = null;
				
				/*****************************
				 * [START] DB LOG DATA SETTING
				 *****************************/
				ApiBatcLogSDO apiBatcLogSDO = new ApiBatcLogSDO();
				apiBatcLogSDO.setBatcProgType(this.getClass().getName().concat(OperateConstants.STR_AT).concat("callSddSearch"));
				apiBatcLogSDO.setBatcDesc("당일특가검색 저장");
				apiBatcLogSDO.setBatcLogType(MessageConstants.API_BATCH_LOG_TYPE_TM);
				apiBatcLogSDO.setExecStrtMlisSecd(APIUtil.currentTimeMillis());
				
				try {
					
					for(SddSearchOutSDO data : assets) {
						//정상 처리되었을경우 데이터 저장
						if(Integer.toString(MessageConstants.RESPONSE_CODE_1000).equals(data.getCode()) && data.getData() != null) {
							
							validateDataList = new ArrayList<SddSearchDataOutSDO>();
							
							for(SddSearchDataOutSDO subData : data.getData()) {
								
								try {
									//execute paramValidate
									new ParamValidate(new ParamValidateSDO(subData, new String[]{"faclCd"})).execute();
									
									validateDataList.add(subData);
								}
								catch(Exception e) {
									
									/*****************************
									 * [START] DB LOG DATA SETTING
									 *****************************/
									ApiBatcLogSDO apiBatcValidationLogSDO = new ApiBatcLogSDO();
									apiBatcValidationLogSDO.setBatcProgType(this.getClass().getName().concat(OperateConstants.STR_AT).concat("callFaclSearch"));
									apiBatcValidationLogSDO.setBatcDesc("당일특가검색 저장 데이터 유효성검사");
									apiBatcValidationLogSDO.setBatcLogType(MessageConstants.API_BATCH_LOG_TYPE_IV);
									apiBatcValidationLogSDO.setErrCont(new StringBuffer()
											.append( "::당일특가검색 정보 유효성 검사 실패 발생시각 : " )
											.append( APIUtil.getFastDate(OperateConstants.GENERAL_DATE_FORMAT) )
											.append( OperateConstants.LINE_SEPARATOR )
											.append( "- 제휴사아이디 : " )
											.append( data.getHttpAgentId() )
											.append( ", 제휴사에이전트설명 : " )
											.append( data.getHttpAgentDesc() )
											.append( OperateConstants.LINE_SEPARATOR )
											.append( "- 유효성검사 실패 상품 : " )
											.append( subData )
											.append( OperateConstants.LINE_SEPARATOR )
											.toString());
									
									Local.commonHeader().addBatchExecLog(apiBatcValidationLogSDO, e);
									/*****************************
									 * [END]   DB LOG DATA SETTING 
									 *****************************/

									logger.error( APIUtil.formatMessage("'{}({})'유효성 검사 실패({})", data.getHttpAgentDesc(), data.getHttpAgentId(), e.getMessage()), e);
								}
							}
							
							//유효성 검증된 목록 세팅
							data.setData(validateDataList);
							//제휴사 별 최저가 목록 데이터 저장
							/** execute dbio */
							txCount += outsideRepository.callSddSearch(data, true);
						}
						
						/**** 멀티쓰레드 인터페이스 결과 세팅 */
						out.addMultiThreadResults(data.getCode(), data.getMessage(), data.getRestURI());
					}
					
					out.setTxCount(txCount);
				}
				finally {
					
					/*****************************
					 * [END] DB LOG DATA SETTING
					 *****************************/
					
					Local.commonHeader().addBatchExecLog(apiBatcLogSDO, true);
				}
			}
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "당일특가검색 인터페이스 장애발생.", e);
		}
		
		return out;
	}
	
	
	@APIOperation(description="시설검색 인터페이스 (DB테이블의 시도코드 만큼 (callFaclSearch)반복실행)")
	public List<FaclSearchOutSDO> callFaclSearchWithSidoList(UserAgentSDO userAgentDTO, FaclSearchInSDO faclSearchDTO) {
		
		commonRepository = (CommonRepository) LApplicationContext.getBean(commonRepository, CommonRepository.class);
		
		List<FaclSearchOutSDO> out = null;
		List<EzcCityCd> ezcCityCdList = null;
		
		try {
			
			ezcCityCdList = commonRepository.selectListSidoCode(new EzcCityCd());
			
			if(ezcCityCdList != null) {
			
				out = new ArrayList<FaclSearchOutSDO>();
				for(EzcCityCd item : ezcCityCdList) {
					faclSearchDTO.setSidoCode(item.getCityCd());
					out.add(callFaclSearch(userAgentDTO, faclSearchDTO));
				}
			}
		}
		catch(Exception e) {
			logger.error("시설검색 인터페이스 (DB테이블의 시도코드 만큼 (callFaclSearch)반복실행)중 장애발생", e);
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
		
		Integer txCount = OperateConstants.INTEGER_ZERO_VALUE;
		FaclSearchOutSDO out = null;
		MultiHttpConfigSDO multi = null;
		List<HttpConfigSDO> channelList = null;
		List<MultiHttpConfigSDO> multiHttpConfigList = null;
		boolean isConfirm = false;
		
		try {
			out = new FaclSearchOutSDO();
			multiHttpConfigList = new ArrayList<MultiHttpConfigSDO>();
			
			channelList = InterfaceFactory.getChannelGroup(userAgentDTO.getHttpAgentGroupId());
			if(channelList != null) {
				for(HttpConfigSDO httpConfigSDO : channelList) {
					
					isConfirm = false;
					if(!userAgentDTO.isReadOnly()) /* ReadOnly False */ {
						//읽기전용이 아닐때 
						isConfirm = true;
					}
					else /* ReadOnly True */{
						//읽기전용일때
						if(APIUtil.isEmpty(userAgentDTO.getHttpAgentId()) || httpConfigSDO.getHttpAgentId().equals(userAgentDTO.getHttpAgentId())) {
							isConfirm = true;
						}
					}
					
					if(isConfirm) {
						
						multi = new MultiHttpConfigSDO();
						configureHelper.setupUserAgentInfo(userAgentDTO, httpConfigSDO);
						//config
						multi.setHttpConfigDTO(httpConfigSDO);
						//input
						multi.setInputDTO(faclSearchDTO);
						//output
						multi.setOutputType(FaclSearchOutSDO.class);
						//add
						multiHttpConfigList.add(multi);					
					}
				}
			}
			
			/** execute interface */
			//멀티 쓰레드 인터페이스 실행
			List<FaclSearchOutSDO> assets = inteface.sendMultiJSON(multiHttpConfigList);
			logger.debug("- FaclSearchOutSDO size : {}", (assets != null ? assets.size() : 0));
			
			if(assets != null) {
				
				List<FaclSearchDataOutSDO> validateDataList = null;
				
				/*****************************
				 * [START] DB LOG DATA SETTING
				 *****************************/
				ApiBatcLogSDO apiBatcLogSDO = new ApiBatcLogSDO();
				apiBatcLogSDO.setBatcProgType(this.getClass().getName().concat(OperateConstants.STR_AT).concat("callFaclSearch"));
				apiBatcLogSDO.setBatcDesc("시설검색 인터페이스(최저가 정보)저장");
				apiBatcLogSDO.setBatcLogType(MessageConstants.API_BATCH_LOG_TYPE_TM);
				apiBatcLogSDO.setExecStrtMlisSecd(APIUtil.currentTimeMillis());
				
				try {
				
					//읽기 전용이 아닐경우 배치실행
					for(FaclSearchOutSDO data : assets) {
						
						if(Integer.toString(MessageConstants.RESPONSE_CODE_1000).equals(data.getCode()) && data.getData() != null) {
						
							if(userAgentDTO.isReadOnly()) /* 읽기 전용 */ {
								out.addAllData(data.getData());
							}
							else /* 읽기 전용이 아닐경우 배치실행 */ {
								
								validateDataList = new ArrayList<FaclSearchDataOutSDO>();
								
								for(FaclSearchDataOutSDO subData : data.getData()) {
									
									try {
										//execute paramValidate
										new ParamValidate(new ParamValidateSDO(subData, new String[]{"faclCd"})).execute();
										
										validateDataList.add(subData);
									}
									catch(Exception e) {
										
										/*****************************
										 * [START] DB LOG DATA SETTING
										 *****************************/
										ApiBatcLogSDO apiBatcValidationLogSDO = new ApiBatcLogSDO();
										apiBatcValidationLogSDO.setBatcProgType(this.getClass().getName().concat(OperateConstants.STR_AT).concat("callFaclSearch"));
										apiBatcValidationLogSDO.setBatcDesc("시설검색 인터페이스(최저가 정보)저장 데이터 유효성검사");
										apiBatcValidationLogSDO.setBatcLogType(MessageConstants.API_BATCH_LOG_TYPE_IV);
										apiBatcValidationLogSDO.setErrCont(new StringBuffer()
												.append( "::시설검색 인터페이스(최저가 정보) 유효성 검사 실패 발생시각 : " )
												.append( APIUtil.getFastDate(OperateConstants.GENERAL_DATE_FORMAT) )
												.append( OperateConstants.LINE_SEPARATOR )
												.append( "- 제휴사아이디 : " )
												.append( data.getHttpAgentId() )
												.append( ", 제휴사에이전트설명 : " )
												.append( data.getHttpAgentDesc() )
												.append( OperateConstants.LINE_SEPARATOR )
												.append( "- 유효성검사 실패 상품 : " )
												.append( subData )
												.append( OperateConstants.LINE_SEPARATOR )
												.toString());
										
										Local.commonHeader().addBatchExecLog(apiBatcValidationLogSDO, e);
										/*****************************
										 * [END]   DB LOG DATA SETTING 
										 *****************************/

										logger.error( APIUtil.formatMessage("'{}({})'유효성 검사 실패({})", data.getHttpAgentDesc(), data.getHttpAgentId(), e.getMessage()), e);
									}
								}
								
								//유효성 검증된 목록 세팅
								data.setData(validateDataList);
								//정상 처리되었을경우 데이터 저장
								/** execute dbio */
								//제휴사 별 최저가 목록 데이터 저장
								txCount += outsideRepository.callFaclSearch(faclSearchDTO, data, true);
							}
						}
						
						/**** 멀티쓰레드 인터페이스 결과 세팅 */
						out.addMultiThreadResults(data.getCode(), data.getMessage(), data.getRestURI());
					}
					
					out.setTxCount(txCount);
				}
				finally {
					
					/*****************************
					 * [END] DB LOG DATA SETTING
					 *****************************/
					
					Local.commonHeader().addBatchExecLog(apiBatcLogSDO, true);
				}
			}
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "시설검색 인터페이스 장애발생.", e);
		}
			
		return out;
	}


	/**
	 * 현제 사용 보류 20181227
	 * @param faclSDO
	 * @return
	 */
	@APIOperation(description="시설 매핑")
	private FaclSDO execFaclMappingWithMorpRowData(FaclSDO faclSDO) {
		logger.debug("[START] execFaclMapping");
		
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		outsideRepository = (OutsideRepository) LApplicationContext.getBean(outsideRepository, OutsideRepository.class);
		
		FaclSDO out = null;
		List<EzcFacl> mappingStep1List = null;
		List<EzcFacl> mappingStep2List = null;
		List<EzcFacl> mappingStep2ListClone = null;
		EzcFacl ezcFacl = null;
		
		try {
			
			ezcFacl = (EzcFacl) propertyUtil.copySameProperty(faclSDO, EzcFacl.class);
			
			mappingStep1List = outsideRepository.selectFaclCodeGroupList(ezcFacl);
			
			for(EzcFacl faclMorp : mappingStep1List) {
				logger.debug("[LOOP] faclMorp : {}", faclMorp);
				//도시,지역,숙소유형,숙소등급 파라매터의 시설코드별 형태소 목록 
				mappingStep2List = outsideRepository.selectFaclMappingMorpRowData(faclMorp);
				
				if(mappingStep2List != null && mappingStep2List.size() > 0) {
					
					logger.debug("sort before : {}", mappingStep2List);
					
					mappingStep2List = sortFaclList(mappingStep2List, OperateConstants.STR_SENDING_ASC);
					
					logger.debug("sort after : {}", mappingStep2List);
					
					//비교 대조를 위한 목록 복제
					mappingStep2ListClone = new ArrayList<EzcFacl>();
					mappingStep2ListClone.addAll(mappingStep2List);
					//분석 비교 시작(추론검색)
				}
			}
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9600, MessageConstants.getMessage(MessageConstants.RESPONSE_CODE_9600), e);
		}
		finally {
			
			if(mappingStep1List != null) {
				mappingStep1List.clear();
			}
			if(mappingStep2List != null) {
				mappingStep2List.clear();
			}
			if(mappingStep2ListClone != null) {
				mappingStep2ListClone.clear();
			}
		}
		
		logger.debug("[END] execFaclMapping");
		return out;
	}
	

	/**
	 * 현제 사용 보류
	 * 리스트에 담긴 문자목록을 HashCode (아스키) 를 기준으로 sending 방향대로 정렬하여 줍니다.
	 * @param arrays
	 * @param sending
	 * @return
	 */
	private List<EzcFacl> sortFaclList(List<EzcFacl> ezcFaclList, String sending) {

		if(ezcFaclList == null || ezcFaclList.size() == 0) {
			return null;
		}

		List<EzcFacl> faclList = ezcFaclList;
		final String orderBy = sending;

        Collections.sort(faclList, new Comparator<EzcFacl>(){
        	int frontHashCode = OperateConstants.INTEGER_ZERO_VALUE;
        	int backendHashCode = OperateConstants.INTEGER_ZERO_VALUE;        	
	    	int position = OperateConstants.INTEGER_ZERO_VALUE;

			public int compare(final EzcFacl front, final EzcFacl backend ) {
				frontHashCode = front.getFaclCd().toPlainString().concat(front.getFaclNm()).hashCode(); 
				backendHashCode = backend.getFaclCd().toPlainString().concat(backend.getFaclNm()).hashCode();
				
        		if(OperateConstants.STR_SENDING_DESC.equalsIgnoreCase(orderBy)){
        			position = backendHashCode - frontHashCode;
        		}else{
        			position = frontHashCode - backendHashCode;
        		}

                return position;
            }
		});

        return faclList;
	}

	@APIOperation(description="그룹시설코드 기준 시설목록 조회")
	public List<EzcFacl> selectRoomReadFaclList(EzcFacl inEzcFacl) {
		
		outsideRepository = (OutsideRepository) LApplicationContext.getBean(outsideRepository, OutsideRepository.class);
		
		List<EzcFacl> out = null;
		
		try {
			out = outsideRepository.selectRoomReadFaclList(inEzcFacl);
		}
		catch(APIException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9500, "그룹시설코드 기준 시설목록검색 장애발생", e);
		}
		
		return out;
	}
	

	@APIOperation(description="객실 최저가 정보 조회 인터페이스")
	public RoomReadOutSDO callRoomRead(UserAgentSDO userAgentSDO, RoomReadInSDO roomReadSDO) {
		
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		
		RoomReadOutSDO out = null;
		//멀티쓰레드 객체
		List<Future<?>> futures = null;
		CallableExecutor executor = null;
		Callable<RoomReadOutSDO> callable = null;
		List<EzcFacl> searchRoomParams = null;
		EzcFacl searchMinRoomFacl = null;
		//최저가 객실 정보
		RoomReadDataOutSDO minAmtRoom = null;
		List<RoomReadOutSDO> roomReadList = null;
		RoomReadOutSDO roomReadFuture = null;
		IfLogSDO ifLogSDO = null;
		EzcFacl inEzcFacl = null;
		RoomReadInSDO inRoomReadSDO = null;
		UserAgentSDO inUserAgentSDO = null;
		
		try {
			
			out = new RoomReadOutSDO();

			//상품목록이 없고 그룹시설코드가 있으면 매핑기준으로 조회
			if((roomReadSDO.getSeachMinRoomInList() == null || roomReadSDO.getSeachMinRoomInList().size() == 0) && roomReadSDO.getGrpFaclCd() != null) {
				//그룹시설코드 기준 시설목록 조회
				inEzcFacl = new EzcFacl();
				inEzcFacl.setGrpFaclCd(roomReadSDO.getGrpFaclCd());
				searchRoomParams = selectRoomReadFaclList(inEzcFacl);
			}
			else {
				//상품목록이 있으면 상품목록기준으로 조회
				searchRoomParams = new ArrayList<EzcFacl>();
				
				for(RoomReadSeachMinInSDO minRoom : roomReadSDO.getSeachMinRoomInList()) {
					
					searchMinRoomFacl = (EzcFacl) propertyUtil.copySameProperty(minRoom, EzcFacl.class);
					searchMinRoomFacl.setPartnerGoodsCd(minRoom.getPdtNo());
					searchRoomParams.add(searchMinRoomFacl);
				}
			}
			
			//그룹시설코드에 대한 목록이 있으면...
			if(searchRoomParams != null && searchRoomParams.size() > 0) {
				
				//제휴사별 상품코드별 객실목록
				roomReadList = new ArrayList<RoomReadOutSDO>();
				//Callable 객체
				executor = new CallableExecutor();
				executor.initThreadPool(ROOM_READ_MULTI_COUNT);
				
				int count = 0;
				for(EzcFacl faclitem : searchRoomParams) {
					//logger.debug("# 인터페이스 실행전 : {}", faclitem);
					
					inUserAgentSDO = (UserAgentSDO) propertyUtil.copySameProperty(userAgentSDO, UserAgentSDO.class);			
					inUserAgentSDO.setHttpAgentId(faclitem.getPartnerCd()); //제휴사 코드 (에이젼트ID)
					
					inRoomReadSDO = (RoomReadInSDO) propertyUtil.copySameProperty(roomReadSDO, RoomReadInSDO.class, "seachMinRoomInList");
					inRoomReadSDO.setPdtNo(faclitem.getPartnerGoodsCd()); //제휴사 상품 코드
					inRoomReadSDO.setGrpFaclCd(faclitem.getGrpFaclCd()); //제휴사 상품 코드
					inRoomReadSDO.setFaclCd(faclitem.getFaclCd()); //제휴사 상품 코드
					
					//멀티쓰레드 세팅
					callable = new RoomReadMultiCallable(inUserAgentSDO, inRoomReadSDO, count);
					//설정된 멀티쓰레드 개수만큼 반복 실행 
					executor.addCall(callable);
					count++;
				}
				
				roomReadList = new ArrayList<RoomReadOutSDO>();
				futures = executor.getResult();
				if(futures != null) {
				
					logger.debug(" ■■ [PROC-START] 객실 정보 결과를 수집합니다. ({})", futures.size());
					for(Future<?> future : futures) {
						//매핑 정보 취합
						if(future.get() != null) {
							roomReadFuture = (RoomReadOutSDO) future.get();
							ifLogSDO = roomReadFuture.getIfLog();
							if(ifLogSDO != null) {
								Local.commonHeader().addMultiIfLogList(ifLogSDO);
							}
							//1개 상품에 대한 객실 목록
							roomReadList.add(roomReadFuture);
						}
					}
					logger.debug(" ■■ [PROC-END] 객실 정보 결과 수집이 완료되었습니다.");
				}
				
				logger.debug("# roomReadList size : {}", roomReadList.size());
				
				//각 제휴사 상품 객실 목록에서 최저가를 뽑는다.
				for(RoomReadOutSDO item : roomReadList) {
					
					//logger.debug("- RoomReadOutSDO : {}", item);
					
					//최저가 정보를 담을 객체
					// minAmtRoom = null; //+
					//인터페이스 정상 성공인경우
					if(item.getCode().equals(Integer.toString(MessageConstants.RESPONSE_CODE_1000)) && item.getData() != null) {
						
						for(RoomReadDataOutSDO roomItem : item.getData()) {
							
							//START 20190128 (전용필차장, PM요청으로 모든 객실정보를 리턴함)
							minAmtRoom = (RoomReadDataOutSDO) propertyUtil.copySameProperty(roomItem, new RoomReadDataOutSDO(), new String[] {"penalty","options"}, true);
							minAmtRoom.setPartnerCd(item.getPartnerCd());
							minAmtRoom.setFaclCd(item.getFaclCd());
							out.addData(minAmtRoom);
							//END 20190128 (전용필차장, PM요청으로 모든 객실정보를 리턴함)
							
							//최저가 정보를 담는다.
							//minAmtRoom.set ...
							/* <20190128 주석처리>
							if(minAmtRoom == null) {
								//logger.debug("# First roomItem : {}", roomItem);
								minAmtRoom = (RoomReadDataOutSDO) propertyUtil.copySameProperty(roomItem, new RoomReadDataOutSDO(), new String[] {"penalty","options"}, true);
								minAmtRoom.setPartnerCd(item.getPartnerCd());
								minAmtRoom.setFaclCd(item.getFaclCd());
							}
							else if(roomItem.getPriceForSale() <= minAmtRoom.getPriceForSale()) {
								//logger.debug("# Find MinRoom : {}", roomItem);
								minAmtRoom = (RoomReadDataOutSDO) propertyUtil.copySameProperty(roomItem, new RoomReadDataOutSDO(), new String[] {"penalty","options"}, true);
								minAmtRoom.setPartnerCd(item.getPartnerCd());
								minAmtRoom.setFaclCd(item.getFaclCd());
							}
							*/
						}
					}
					
					/**** 멀티쓰레드 인터페이스 결과 세팅 */
					out.addMultiThreadResults(item.getCode(), item.getMessage(), item.getRestURI());
					
					/* <20190128 주석처리>
					if(minAmtRoom != null) {
						out.addData(minAmtRoom);
					}
					*/
				}
			}
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "객실정보조회 인터페이스 장애발생.", e);
		}
		finally {
			
			if(searchRoomParams != null) {
				searchRoomParams.clear();
			}
			if(executor != null) {
				executor.clear();
			}
		}
		
		return out;		
	}
	
}
