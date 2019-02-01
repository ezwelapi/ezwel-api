package com.ezwel.htl.interfaces.server.schedule;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.commons.utils.CommonUtil;
import com.ezwel.htl.interfaces.server.controller.OutsideController;
import com.ezwel.htl.interfaces.server.sdo.FaclSDO;
import com.ezwel.htl.interfaces.server.sdo.TransactionOutSDO;
import com.ezwel.htl.interfaces.server.service.OutsideService;
import com.ezwel.htl.interfaces.service.data.allReg.AllRegOutSDO;
import com.ezwel.htl.interfaces.service.data.faclSearch.FaclSearchInSDO;
import com.ezwel.htl.interfaces.service.data.faclSearch.FaclSearchOutSDO;
import com.ezwel.htl.interfaces.service.data.sddSearch.SddSearchOutSDO;

@Component
@APIType(description="이지웰 배치 좝 스케쥴 실행기")
public class EzwelBatchJobExecutor {

	private static final Logger logger = LoggerFactory.getLogger(EzwelBatchJobExecutor.class);
	
	private OutsideService outsideService;
	
	private boolean isInitApplication = false;
	
	//@Scheduled(fixedDelay=999999999)
	@APIOperation(description="어플리케이션 초기화 직후 실행")
	public void initApplication() {
		
		if(!isInitApplication) {
			//TEST
			try {
				logger.debug("EZWEL Application Init Complete");
				isInitApplication = true;
			}
			catch(Exception e) {
				logger.error("[Scheduled-Exception] initApplication", e);
			}
		}
	}
	
	/***
	 * 1. 전체 시설 (부대시설, 이미지 다운로드) : 매일 01
	 * 
	 * 2. 전체 시설 매핑 : 전체 시설 배치 종료 1시간후 시작   
	 * 
	 * 3. 이미지 전채 : 1주 1회 월요일 새벽 :
	 * 
	 * 4. 특가 2시간마다 1회
	 */
	
	/**
	 * 1. 전체 시설 (부대시설, 이미지 다운로드) : 매일 01
	 */
	//초 분 시 일 월 주(년)
	@Scheduled(cron="0 0 01 * * ?")
	@APIOperation(description="전체 시설 (부대시설, 이미지 다운로드) : 매일 01")
	public void allFaclRegJob() {
		logger.debug("[START-Scheduled] allFaclRegJob start-time : {}", APIUtil.getFastDate(OperateConstants.GENERAL_DATE_FORMAT));
		
		if (OutsideService.isCallAllRegRunning()) {
			logger.warn("전체시설일괄등록 인터페이스가 이미 실행중입니다.");
			return;
		}
		
		AllRegOutSDO out = null;
		ExecutorService executorService = null;
		CommonUtil.initCommonHeader();
		
		try {
			
			UserAgentSDO userAgentSDO = new UserAgentSDO();
			//setting
			userAgentSDO.setHttpAgentGroupId("multiAllReg");
			userAgentSDO.setConnTimeout(1000);
			userAgentSDO.setReadTimeout(100000);
			
			outsideService = (OutsideService) LApplicationContext.getBean(outsideService, OutsideService.class);
			out = outsideService.callAllReg(userAgentSDO);
		}
		finally {

			try {
				
				executorService = Executors.newCachedThreadPool();
				Runnable runnable = new Runnable() {
					@Override
					public void run() {
						//별도쓰레드로 변경
						allFaclMappingJob();
					}
				};
				// 스레드풀에게 작업 처리 요청
				executorService.execute(runnable);
				
			} catch (Exception e) {
				logger.error("[Scheduled-Runnable-Exception] allFaclRegJob", e);
			}
			
			if(Local.commonHeader() != null) {
				Local.commonHeader().setHandlerInterceptorComplete(true);
			}
		}
		
		logger.debug("[END-Scheduled] allFaclRegJob end-time : {} {}", APIUtil.getFastDate(OperateConstants.GENERAL_DATE_FORMAT), out);
	}
	
	/**
	 * 2. 전체 시설 매핑 : 전체 시설 배치 종료 30분 후 시작
	 */
	@APIOperation(description="전체 시설 매핑 : 전체 시설 배치 종료 30분 후 시작")
	public void allFaclMappingJob() {
		
		// 2. 전체 시설 매핑 : 전체 시설 배치 종료 30분 후 시작
		Long sleepMinute = 30L;
		TransactionOutSDO out = null;
		CommonUtil.initCommonHeader();
		
		try {
			
			if(OutsideService.isFaclMappingRunning()) {
				logger.warn("시설 매핑 프로세스가 이미 실행중입니다.");
				return;
			}
			
			//sleep
			Thread.sleep(((sleepMinute * 60L) * 1000L));
			//start
			logger.debug("[START-Scheduled] allFaclMappingJob start-time : {}", APIUtil.getFastDate(OperateConstants.GENERAL_DATE_FORMAT));
			
			FaclSDO faclSDO = new FaclSDO();
			//setting
			
			outsideService = (OutsideService) LApplicationContext.getBean(outsideService, OutsideService.class);
			out = outsideService.execFaclMapping(faclSDO);	
			
		} catch (InterruptedException e) {
			logger.error("[Scheduled-InterruptedException] allFaclMappingJob", e);
		} catch (Exception e) {
			logger.error("[Scheduled-Exception] allFaclMappingJob", e);
		} finally {
			if(Local.commonHeader() != null) {
				Local.commonHeader().setHandlerInterceptorComplete(true);
			}
		}

		logger.debug("[END-Scheduled] allFaclMappingJob end-time : {} {}", APIUtil.getFastDate(OperateConstants.GENERAL_DATE_FORMAT), out);
	}
	
	
	/**
	 * 시설검색(최저가 정보) 인터페이스 : 2시간반 마다
	 */
	//초 분 시 일 월 주(년)  cron = "0 0 0/1 * * *"  1시간마다
	@Scheduled(cron="* 0/150 * * * *") // 0분 부터 2시간반 마다
	//@Scheduled(fixedDelay=((120L * 60L) * 1000L))
	//@Scheduled(fixedDelay=999999999)
	@APIOperation(description="시설검색(최저가 정보) 인터페이스 : 0시30분부터 2시간마다")
	public void allFaclSearchJob() {
		logger.debug("[START-Scheduled] allFaclSearchJob start-time : {}", APIUtil.getFastDate(OperateConstants.GENERAL_DATE_FORMAT));
		
		List<FaclSearchOutSDO> out = null;
		ExecutorService executorService = null;
		CommonUtil.initCommonHeader();
		
		try {
			
			UserAgentSDO userAgentSDO = new UserAgentSDO();
			//setting
			userAgentSDO.setHttpAgentGroupId("multiFaclSearch");
			userAgentSDO.setConnTimeout(1000);
			userAgentSDO.setReadTimeout(100000);
			
			FaclSearchInSDO faclSearchSDO = new FaclSearchInSDO();
			//setting
			//시작일 당일(기준일)
			faclSearchSDO.setStndDate(APIUtil.getFastDate(OperateConstants.DEF_DAY_FORMAT));
			//종료일 당일로 부터 90일 이후
			faclSearchSDO.setPlusDay(90);
			//시도 코드는 DB에서 조회 (EZC_CITY_CD)
			
			outsideService = (OutsideService) LApplicationContext.getBean(outsideService, OutsideService.class);
			out = outsideService.callFaclSearchWithSidoList(userAgentSDO, faclSearchSDO);
		} catch (Exception e) {
			logger.error("[Scheduled-Exception] allFaclSearchJob", e);
		} finally {
			
			try {
				
				executorService = Executors.newCachedThreadPool();
				Runnable runnable = new Runnable() {
					@Override
					public void run() {
						//당일특가검색 인터페이스
						allSddSearchJob();
					}
				};
				// 스레드풀에게 작업 처리 요청
				executorService.execute(runnable);
				
			} catch (Exception e) {
				logger.error("[Scheduled-Runnable-Exception] allFaclSearchJob", e);
			}
			
			if(Local.commonHeader() != null) {
				Local.commonHeader().setHandlerInterceptorComplete(true);
			}
			
		}
		
		logger.debug("[END-Scheduled] allFaclSearchJob end-time : {} {}", APIUtil.getFastDate(OperateConstants.GENERAL_DATE_FORMAT), out);
	}
	
	
	
	/**
	 * 당일특가검색 인터페이스 : 2시간마다
	 */
	//초 분 시 일 월 주(년) 0 0 01 * * ?
	//@Scheduled(cron="* 0/120 * * * *")
	//@Scheduled(fixedDelay=999999999)
	@APIOperation(description="당일특가검색 인터페이스 : 시설검색(최저가 정보) 인터페이스 종료 후 실행")
	public void allSddSearchJob() {
		
		// 시설검색(최저가 정보) 인터페이스 : 전체 시설 배치 종료 20분 후 시작
		Long sleepMinute = 20L;
		SddSearchOutSDO out = null;
		CommonUtil.initCommonHeader();
		
		try {
			
			Thread.sleep(((sleepMinute * 60L) * 1000L));
			
			logger.debug("[START-Scheduled] allSddSearchJob start-time : {}", APIUtil.getFastDate(OperateConstants.GENERAL_DATE_FORMAT));
			
			UserAgentSDO userAgentSDO = new UserAgentSDO();
			//setting
			userAgentSDO.setHttpAgentGroupId("multiSddSearch");
			userAgentSDO.setConnTimeout(1000);
			userAgentSDO.setReadTimeout(100000);
			
			outsideService = (OutsideService) LApplicationContext.getBean(outsideService, OutsideService.class);
			out = outsideService.callSddSearch(userAgentSDO);
			
		} catch (Exception e) {
			logger.error("[Scheduled-Exception] allSddSearchJob", e);
		} finally {
			if(Local.commonHeader() != null) {
				Local.commonHeader().setHandlerInterceptorComplete(true);
			}
		}
		
		logger.debug("[END-Scheduled] allSddSearchJob end-time : {} {}", APIUtil.getFastDate(OperateConstants.GENERAL_DATE_FORMAT), out);
	}
	
}
