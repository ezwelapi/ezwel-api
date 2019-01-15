package com.ezwel.htl.interfaces.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractComponent;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.component.FaclMappingComponent;
import com.ezwel.htl.interfaces.server.entities.EzcFacl;
import com.ezwel.htl.interfaces.server.sdo.TransactionOutSDO;


@APIType(description="시설 매핑 멀티쓰레드 서비스")
public class FaclMappingMultiService extends AbstractComponent implements Callable<List<EzcFacl>> {

	private static final Logger logger = LoggerFactory.getLogger(FaclMappingMultiService.class);
	
	private static final boolean IS_LOGGING = false;
	
	private FaclMappingComponent faclMappingComponent;
	
	private EzcFacl faclCode;
	
	private OutsideService outsideService;
	
	private Integer count;
	
	private TransactionOutSDO transactionOutSDO;
	
	/**
	 * Constructor
	 * @param inImageParam
	 * @param count
	 */
	public FaclMappingMultiService(EzcFacl faclCode, Integer count, TransactionOutSDO transactionOutSDO) {
		//ThreadLocal 초기화
		Local.commonHeader();
		
		logger.debug("- FaclMappingMultiService Initialized : {}", count);
		/** 필요 한 지역변수 세팅 */
		this.faclCode = faclCode;
		this.count = count;
		this.transactionOutSDO = transactionOutSDO;
	}
	
	/**
	 * 멀티쓰레드에서 수행할 작업 (call)
	 */
	@Override
	public List<EzcFacl> call() {
		
		List<EzcFacl> out = null;
		List<EzcFacl> faclMorpSearchList = null;
		
		try {
			
			faclMappingComponent = (FaclMappingComponent) LApplicationContext.getBean(faclMappingComponent, FaclMappingComponent.class);
			outsideService = (OutsideService) LApplicationContext.getBean(outsideService, OutsideService.class);
			
			if(IS_LOGGING) {
				logger.debug("[START-FaclMappingMulti({})] 다중 시설 매핑 : {}", count, faclCode);
			}
			//::DB 도시,지역,숙소유형,숙소등급 파라매터의 시설코드별 형태소 목록  ( FACL_MORP_PAGE_SIZE 개수 만큼 끊어 읽음 속도 및 타임아웃 이슈 )
			faclMorpSearchList = outsideService.getFaclMappingMorpDataList(new ArrayList<EzcFacl>(), faclCode, 1, OutsideService.FACL_MORP_PAGE_SIZE);
			logger.debug("# 도시,지역,숙소유형,숙소등급별 시설 개수 : {}", (faclMorpSearchList != null ? faclMorpSearchList.size() : 0));
			
			Integer cnt = (Integer) transactionOutSDO.getProfileMap("도시,지역,숙소유형,숙소등급별 시설 개수");
			transactionOutSDO.addProfileMap("도시,지역,숙소유형,숙소등급별 시설 개수", (cnt != null ? cnt : 0) + (faclMorpSearchList != null ? faclMorpSearchList.size() : 0));
			//transactionOutSDO.addProfileMap("도시,지역,숙소유형,숙소등급별 시설 목록(" + count + ")", faclMorpSearchList);
			
			if(faclMorpSearchList != null && faclMorpSearchList.size() > 0) {
				//시설 정보 분석 매핑
				out = faclMappingComponent.getSearchForSameFacility(faclMorpSearchList, true);
				
				if(faclMorpSearchList.size() != out.size()) {
					throw new APIException(MessageConstants.RESPONSE_CODE_9600, "그룹조건별 시설갯수와 시설 분석 매핑 갯수가 다릅니다. 그룹조건별 시설 개수 : {} / 매핑시설 개수 : {}", faclMorpSearchList.size(), out.size());
				}
				
				Integer cntMorp = (Integer) transactionOutSDO.getProfileMap("매핑 결과 목록 갯수");
				transactionOutSDO.addProfileMap("매핑 결과 목록 갯수", (cntMorp != null ? cntMorp : 0) + (out!= null ? out.size() : 0));
			}
			
			if(IS_LOGGING) {
				logger.debug("[END-FaclMappingMulti({})] 다중 시설 매핑 : {}", count, (out != null ? out.size() : 0));
			}
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9600, "그룹별 시설 데이터 조회 및  시설 데이터 매핑 중 장애 발생", e);
		}
		finally {
			//시도/지역/숙소유형/시설유형 그룹별  시설 목록 clear 
			if(faclMorpSearchList != null) {
				faclMorpSearchList.clear();
			}
			
			//ThreadLocal 종료
			Local.remove();
		}
		
		return out;
	}
	

}
