package com.ezwel.htl.interfaces.server.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.sdo.ApiBatcLogSDO;
import com.ezwel.htl.interfaces.commons.sdo.IfLogSDO;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractDataAccessObject;
import com.ezwel.htl.interfaces.server.commons.constants.CodeDataConstants;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.component.LogReportMailComponent;
import com.ezwel.htl.interfaces.server.entities.EzcApiBatcLog;
import com.ezwel.htl.interfaces.server.entities.EzcIfLog;

/**
 * <pre>
 * 인터페이스 로그 데이터 핸들링
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2019. 01. 14.
 */
@Repository
@APIType(description="인터페이스 로그 데이터 핸들링")
public class LogRepository extends AbstractDataAccessObject {

	private static final Logger logger = LoggerFactory.getLogger(LogRepository.class);
	
	private PropertyUtil propertyUtil;
	
	private LogReportMailComponent mailComponent;
	
	@APIOperation(description="인터페이스 실행 로그 목록 입력")
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class, SQLException.class, APIException.class})
	public void insertInterfaceLog(List<IfLogSDO> inInterfaceLogSDO) {
		if(inInterfaceLogSDO != null) {
			
			String inptDt = APIUtil.getFastDate();
			for(IfLogSDO item : inInterfaceLogSDO) {
				item.setInptDt(inptDt);
				insertInterfaceLog(item);
			}
		}
	}
	
	@APIOperation(description="오늘로부터 2일전 인터페이스 로그와 API 배치로그 삭제")
	@Transactional(rollbackFor={Exception.class, SQLException.class, APIException.class})
	public Integer deleteLogData(Integer deleteDay) {
		logger.debug("[START] deleteLogData ");
		
		Integer txIfLogCount = OperateConstants.INTEGER_ZERO_VALUE;
		Integer txApiBatcLogCount = OperateConstants.INTEGER_ZERO_VALUE;
		EzcApiBatcLog ezcApiBatcLog = new EzcApiBatcLog();
		EzcIfLog ezcIfLog = new EzcIfLog();
		try {
			ezcIfLog.setDeleteDay(deleteDay);
			ezcApiBatcLog.setDeleteDay(deleteDay);
			
			txIfLogCount = sqlSession.delete(getNamespace("IF_LOG_MAPPER", "deletePrevEzcIfLog"), ezcIfLog);
			txApiBatcLogCount = sqlSession.delete(getNamespace("API_BATC_LOG_MAPPER", "deletePrevEzcApiBatcLog"), ezcApiBatcLog);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9500, "오늘로부터 2일전 인터페이스 로그와 API 배치로그 삭제 장애발생.", e);
		}
		
		logger.debug("[END] deleteLogData delete txIfLogCount : {}, txApiBatcLogCount : {}", txIfLogCount, txApiBatcLogCount);
		return (txIfLogCount + txApiBatcLogCount);
	}
	
	
	
	@APIOperation(description="인터페이스 실행 로그 입력")
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class, SQLException.class, APIException.class})
	public void insertInterfaceLog(IfLogSDO inInterfaceLogSDO) {
		logger.debug("[START] insertInterfaceLog [FINAL-LOG-DATA] "/*, inInterfaceLogSDO*/);
		
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		
		Integer out = OperateConstants.INTEGER_ZERO_VALUE;
		EzcIfLog ezcIfLog = null;
		ExecutorService executorService = null;
		Runnable runnable = null;
		
		try {
			
			if(inInterfaceLogSDO != null) {
				
				ezcIfLog = (EzcIfLog) propertyUtil.copySameProperty(inInterfaceLogSDO, EzcIfLog.class);
				ezcIfLog.setIfExecCd(APIUtil.getId());
				if(APIUtil.isEmpty(ezcIfLog.getInptDt())) {
					ezcIfLog.setInptDt(APIUtil.getFastDate());
				}
				
				if(APIUtil.isEmpty(ezcIfLog.getPartAgentId())) {
					ezcIfLog.setPartAgentId(OperateConstants.STR_EMPTY);
				}
				
				//logger.debug("# EzcIfLog : {}", ezcIfLog);
				out = sqlSession.insert(getNamespace("IF_LOG_MAPPER", "insertEzcIfLog"), ezcIfLog);
				logger.debug("[LOG-SAVED] txSuccess : {}", out);
				
				if(out > 0 && APIUtil.NVL(ezcIfLog.getSuccYn(), CodeDataConstants.CD_N).equals(CodeDataConstants.CD_N)) { // 로그 정보 이메일 발송
					
					executorService = Executors.newCachedThreadPool();
					final EzcIfLog mailEzcIfLog = (EzcIfLog) propertyUtil.copySameProperty(ezcIfLog, EzcIfLog.class);
					runnable = new Runnable() {
						
						@Override
						public void run() {
							Local.commonHeader();
							
							mailComponent = (LogReportMailComponent) LApplicationContext.getBean(mailComponent, LogReportMailComponent.class);
							
							try {
								mailComponent.sendInterfaceLog(mailEzcIfLog);
							}
							finally {
								Local.remove();
							}
						}
					};
					// 스레드풀에게 작업 처리 요청
					executorService.execute(runnable);
				}
				
			}
		}
		catch(Exception e) {
			
			//None API runtimeException
			logger.error(APIUtil.formatMessage("- 인터페이스 로그 입력 장애발생 {}", ezcIfLog), e);
		}
		finally {
			
			if(inInterfaceLogSDO != null) {
				inInterfaceLogSDO = null;
			}
			if(ezcIfLog != null) {
				ezcIfLog = null;
			}
			if(executorService != null) {
				executorService.shutdown();
			}
		}
		logger.debug("[END] insertInterfaceLog");
	}

	@APIOperation(description="인터페이스 실행 로그 건수 조회")
	public Integer countListEzcIfLog(EzcIfLog ezcIfLog) {
			
		Integer out = null;
		
		try {
			out = sqlSession.selectOne(getNamespace("IF_LOG_MAPPER", "selectCountEzcIfLog"), ezcIfLog);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9500, "인터페이스 실행 로그 목록 조회 장애발생.", e);
		}
			
		return out;
	}
	
	
	@APIOperation(description="인터페이스 실행 로그 목록 조회")
	public List<EzcIfLog> selectListEzcIfLog(EzcIfLog ezcIfLog) {
			
		List<EzcIfLog> out = null;
		
		try {
			out = sqlSession.selectList(getNamespace("IF_LOG_MAPPER", "selectListEzcIfLog"), ezcIfLog);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9500, "인터페이스 실행 로그 목록 조회 장애발생.", e);
		}
			
		return out;
	}
	
	
	
	@APIOperation(description="인터페이스 실행 로그 단건 조회")
	public EzcIfLog selectEzcIfLog(EzcIfLog ezcIfLog) {
			
		EzcIfLog out = null;
		
		try {
			out = sqlSession.selectOne(getNamespace("IF_LOG_MAPPER", "selectListEzcIfLog"), ezcIfLog);
			if(out == null) {
				throw new APIException(MessageConstants.RESPONSE_CODE_9502, "인터페이스 실행 코드에 해당하는 로그 정보가 존재하지 않습니다.");
			}
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9500, "인터페이스 실행 로그 목록 조회 장애발생.", e);
		}
			
		return out;
	}
	
	

	@APIOperation(description="API 배치 로그 입력")
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class, SQLException.class, APIException.class})
	public void insertEzcApiBatcLog(List<ApiBatcLogSDO> inApiBatcLogList) {
		logger.debug("[START] insertEzcApiBatcLog [FINAL-LOG-DATA] size : {}", (inApiBatcLogList != null ? inApiBatcLogList.size() : 0));
		
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		mailComponent = (LogReportMailComponent) LApplicationContext.getBean(mailComponent, LogReportMailComponent.class);
		
		Integer out = OperateConstants.INTEGER_ZERO_VALUE;
		EzcApiBatcLog ezcApiBatcLog = null;
		ExecutorService executorService = null;
		Runnable runnable = null;
		
		try {
			
			if(inApiBatcLogList != null) {
				
				String inptDt = APIUtil.getFastDate();
				for(ApiBatcLogSDO inApiBatcLog : inApiBatcLogList) {
					
					ezcApiBatcLog = (EzcApiBatcLog) propertyUtil.copySameProperty(inApiBatcLog, EzcApiBatcLog.class);
					ezcApiBatcLog.setBatcExecCd(APIUtil.getId());
					ezcApiBatcLog.setInptDt(inptDt);
					//logger.debug("# EzcApiBatcLog : {}", ezcApiBatcLog);
					out += sqlSession.insert(getNamespace("API_BATC_LOG_MAPPER", "insertEzcApiBatcLog"), ezcApiBatcLog);

					// APIUtil.NVL(ezcApiBatcLog.getBatcLogType()).equals("IV")
					if(out > 0) { // 로그 정보 이메일 발송
						executorService = Executors.newCachedThreadPool();
						final EzcApiBatcLog mailApiBatcLog = (EzcApiBatcLog) propertyUtil.copySameProperty(ezcApiBatcLog, EzcApiBatcLog.class);
						runnable = new Runnable() {
							
							@Override
							public void run() {
								Local.commonHeader();
								
								mailComponent = (LogReportMailComponent) LApplicationContext.getBean(mailComponent, LogReportMailComponent.class);
								
								try {
									mailComponent.sendApiBatchLog(mailApiBatcLog);
								}
								finally {
									Local.remove();
								}
							}
						};
						
						// 스레드풀에게 작업 처리 요청
						executorService.execute(runnable);
					}
					
				}
				
				logger.debug("[LOG-SAVED] txSuccess : {}", out);
			}
		}
		catch(Exception e) {
			
			//None API runtimeException
			logger.error(APIUtil.formatMessage("- API 배치 로그 입력 장애발생 {}", ezcApiBatcLog), e);
		}
		finally {
			
			if(inApiBatcLogList != null) {
				inApiBatcLogList.clear();
			}
			if(ezcApiBatcLog != null) {
				ezcApiBatcLog = null;
			}
			if(executorService != null) {
				executorService.shutdown();
			}			
		}
		logger.debug("[END] insertEzcApiBatcLog");
	}

	@APIOperation(description="API 배치 로그 건수 조회")
	public Integer countListEzcApiBatcLog(EzcApiBatcLog ezcApiBatcLog) {
			
		Integer out = null;
		
		try {
			out = sqlSession.selectOne(getNamespace("API_BATC_LOG_MAPPER", "selectCountEzcApiBatcLog"), ezcApiBatcLog);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9500, "API 배치 로그 목록 조회 장애발생.", e);
		}
			
		return out;
	}
	
	
	@APIOperation(description="API 배치 로그 목록 조회")
	public List<EzcApiBatcLog> selectListEzcApiBatcLog(EzcApiBatcLog ezcApiBatcLog) {
			
		List<EzcApiBatcLog> out = null;
		
		try {
			out = sqlSession.selectList(getNamespace("API_BATC_LOG_MAPPER", "selectListEzcApiBatcLog"), ezcApiBatcLog);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9500, "API 배치 로그 목록 조회 장애발생.", e);
		}
			
		return out;
	}
	
	
	
	@APIOperation(description="API 배치 로그 단건 조회")
	public EzcApiBatcLog selectEzcApiBatcLog(EzcApiBatcLog ezcApiBatcLog) {
			
		EzcApiBatcLog out = null;
		
		try {
			out = sqlSession.selectOne(getNamespace("API_BATC_LOG_MAPPER", "selectListEzcApiBatcLog"), ezcApiBatcLog);
			if(out == null) {
				throw new APIException(MessageConstants.RESPONSE_CODE_9502, "배치 실행 코드에 해당하는 로그 정보가 존재하지 않습니다.");
			}
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9500, "API 배치 로그 목록 조회 장애발생.", e);
		}
			
		return out;
	}
	
	
	
}
