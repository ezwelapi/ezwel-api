package com.ezwel.htl.interfaces.server.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.sdo.ApiBatcLogSDO;
import com.ezwel.htl.interfaces.commons.sdo.IfLogSDO;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.entities.EzcApiBatcLog;
import com.ezwel.htl.interfaces.server.entities.EzcIfLog;
import com.ezwel.htl.interfaces.server.repository.LogRepository;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 15.
 */
@Service
@APIType(description="인터페이스/배치 로그조회 서비스")
public class LogService {

	private static final Logger logger = LoggerFactory.getLogger(LogService.class);
	
	private LogRepository logRepository;
	
	private PropertyUtil propertyUtil;

	@APIOperation(description="인터페이스 실행 로그 건수 조회")
	public IfLogSDO countListEzcIfLog(IfLogSDO interfaceLogSDO) throws APIException {
		logger.debug("[START] countListEzcIfLog");
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		
		IfLogSDO out = new IfLogSDO(); 
		EzcIfLog inEzcIfLog = (EzcIfLog) propertyUtil.copySameProperty(interfaceLogSDO, EzcIfLog.class);
		out.setTotalCount(logRepository.countListEzcIfLog(inEzcIfLog));
		
		logger.debug("[END] countListEzcIfLog");
		return out;
	}
	
	
	@APIOperation(description="인터페이스 실행 로그 목록 조회")
	public List<IfLogSDO> selectListEzcIfLog(IfLogSDO interfaceLogSDO) throws APIException {
		logger.debug("[START] selectListEzcIfLog");
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		
		EzcIfLog inEzcIfLog = (EzcIfLog) propertyUtil.copySameProperty(interfaceLogSDO, EzcIfLog.class);
		List<EzcIfLog> outEzcIfLogList = logRepository.selectListEzcIfLog(inEzcIfLog);
		List<IfLogSDO> out = new ArrayList<IfLogSDO>(); 
		IfLogSDO outItem = null;
		
		for(EzcIfLog logItem : outEzcIfLogList) {
			outItem = (IfLogSDO) propertyUtil.copySameProperty(logItem, IfLogSDO.class);
			out.add(outItem);
		}
		
		logger.debug("[END] selectListEzcIfLog");
		return out;
	}
	
	@APIOperation(description="인터페이스 실행 로그 단건 조회")
	public IfLogSDO selectEzcIfLog(IfLogSDO interfaceLogSDO) throws APIException {
		logger.debug("[START] selectEzcIfLog");
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		
		EzcIfLog inEzcIfLog = (EzcIfLog) propertyUtil.copySameProperty(interfaceLogSDO, EzcIfLog.class);
		EzcIfLog outEzcIfLog = logRepository.selectEzcIfLog(inEzcIfLog);
		IfLogSDO out = (IfLogSDO) propertyUtil.copySameProperty(outEzcIfLog, IfLogSDO.class);
		
		logger.debug("[END] selectEzcIfLog");
		return out;
	}
	
	@APIOperation(description="API 배치 로그 건수 조회")
	public ApiBatcLogSDO countListEzcApiBatcLog(ApiBatcLogSDO apiBatcLogSDO) throws APIException {
		logger.debug("[START] countListEzcApiBatcLog");
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		
		ApiBatcLogSDO out = new ApiBatcLogSDO(); 
		EzcApiBatcLog inEzcApiBatcLog = (EzcApiBatcLog) propertyUtil.copySameProperty(apiBatcLogSDO, EzcApiBatcLog.class);
		out.setTotalCount(logRepository.countListEzcApiBatcLog(inEzcApiBatcLog));
		
		logger.debug("[END] countListEzcApiBatcLog");
		return out;
	}
	
	
	@APIOperation(description="API 배치 로그 목록 조회")
	public List<ApiBatcLogSDO> selectListEzcApiBatcLog(ApiBatcLogSDO apiBatcLogSDO) throws APIException {
		logger.debug("[START] selectListEzcApiBatcLog");
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		
		EzcApiBatcLog inEzcApiBatcLog = (EzcApiBatcLog) propertyUtil.copySameProperty(apiBatcLogSDO, EzcApiBatcLog.class);
		List<EzcApiBatcLog> outEzcApiBatcLogList = logRepository.selectListEzcApiBatcLog(inEzcApiBatcLog);
		List<ApiBatcLogSDO> out = new ArrayList<ApiBatcLogSDO>(); 
		ApiBatcLogSDO outItem = null;
		
		for(EzcApiBatcLog logItem : outEzcApiBatcLogList) {
			outItem = (ApiBatcLogSDO) propertyUtil.copySameProperty(logItem, ApiBatcLogSDO.class);
			out.add(outItem);
		}
		
		logger.debug("[END] selectListEzcApiBatcLog");
		return out;
	}
	
	@APIOperation(description="API 배치 로그 단건 조회")
	public ApiBatcLogSDO selectEzcApiBatcLog(ApiBatcLogSDO apiBatcLogSDO) throws APIException {
		logger.debug("[START] selectEzcApiBatcLog");
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		
		EzcApiBatcLog inEzcApiBatcLog = (EzcApiBatcLog) propertyUtil.copySameProperty(apiBatcLogSDO, EzcApiBatcLog.class);
		EzcApiBatcLog outEzcApiBatcLog = logRepository.selectEzcApiBatcLog(inEzcApiBatcLog);
		ApiBatcLogSDO out = (ApiBatcLogSDO) propertyUtil.copySameProperty(outEzcApiBatcLog, ApiBatcLogSDO.class);
		
		logger.debug("[END] selectEzcApiBatcLog");
		return out;
	}
	
}
