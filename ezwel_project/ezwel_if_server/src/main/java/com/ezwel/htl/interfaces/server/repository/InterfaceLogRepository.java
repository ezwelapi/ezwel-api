package com.ezwel.htl.interfaces.server.repository;

import java.sql.SQLException;
import java.util.List;

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
import com.ezwel.htl.interfaces.commons.sdo.InterfaceLogSDO;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractDataAccessObject;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
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
public class InterfaceLogRepository extends AbstractDataAccessObject {

	private static final Logger logger = LoggerFactory.getLogger(InterfaceLogRepository.class);
	
	private PropertyUtil propertyUtil;
	
	@APIOperation(description="인터페이스 실행 로그 입력")
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class, SQLException.class, APIException.class})
	public void insertInterfaceLog(InterfaceLogSDO inInterfaceLogSDO) {
		logger.debug("[START] insertInterfaceLog [FINAL-LOG-DATA] {}", inInterfaceLogSDO);
		
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		Integer out = OperateConstants.INTEGER_ZERO_VALUE;
		EzcIfLog ezcIfLog = null;
		
		try {
			
			if(inInterfaceLogSDO != null) {
				
				ezcIfLog = (EzcIfLog) propertyUtil.copySameProperty(inInterfaceLogSDO, EzcIfLog.class);
				ezcIfLog.setIfExecCd(APIUtil.getId());
				logger.debug("# EzcIfLog : {}", ezcIfLog);
				out = sqlSession.insert(getNamespace("IF_LOG_MAPPER", "insertEzcIfLog"), ezcIfLog);
				logger.debug("[LOG-SAVED] txSuccess : {}", out);
			}
		}
		catch(Exception e) {
			
			//None API runtimeException
			logger.error(APIUtil.formatMessage("- 인터페이스 로그 입력 장애발생 {}", ezcIfLog), e);
		}
		
		logger.debug("[END] insertInterfaceLog");
	}

	@APIOperation(description="인터페이스 실행 로그 건수 조회")
	public Integer countListEzcIfLog(EzcIfLog ezcIfLog) {
			
		Integer out = null;
		
		try {
			out = sqlSession.selectOne(getNamespace("IF_LOG_MAPPER", "selectCountEzcIfLog"), ezcIfLog);
		}
		catch(APIException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "인터페이스 실행 로그 목록 조회 장애발생.", e);
		}
			
		return out;
	}
	
	
	@APIOperation(description="인터페이스 실행 로그 목록 조회")
	public List<EzcIfLog> selectListEzcIfLog(EzcIfLog ezcIfLog) {
			
		List<EzcIfLog> out = null;
		
		try {
			out = sqlSession.selectList(getNamespace("IF_LOG_MAPPER", "selectListEzcIfLog"), ezcIfLog);
		}
		catch(APIException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "인터페이스 실행 로그 목록 조회 장애발생.", e);
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
		catch(APIException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "인터페이스 실행 로그 목록 조회 장애발생.", e);
		}
			
		return out;
	}
}