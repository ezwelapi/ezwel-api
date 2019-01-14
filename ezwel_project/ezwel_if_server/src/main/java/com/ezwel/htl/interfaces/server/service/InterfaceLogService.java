package com.ezwel.htl.interfaces.server.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.sdo.InterfaceLogSDO;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.entities.EzcIfLog;
import com.ezwel.htl.interfaces.server.repository.InterfaceLogRepository;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 15.
 */
@Service
@APIType(description="외부 > 내부 인터페이스 서비스")
public class InterfaceLogService {

	private static final Logger logger = LoggerFactory.getLogger(InterfaceLogService.class);
	
	private InterfaceLogRepository interfaceLogRepository;
	
	private PropertyUtil propertyUtil;

	@APIOperation(description="인터페이스 실행 로그 건수 조회")
	public InterfaceLogSDO countListEzcIfLog(InterfaceLogSDO interfaceLogSDO) throws APIException {
		logger.debug("[START] countListEzcIfLog");
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		
		InterfaceLogSDO out = new InterfaceLogSDO(); 
		EzcIfLog inEzcIfLog = (EzcIfLog) propertyUtil.copySameProperty(interfaceLogSDO, EzcIfLog.class);
		out.setTotalCount(interfaceLogRepository.countListEzcIfLog(inEzcIfLog));
		
		logger.debug("[END] countListEzcIfLog");
		return out;
	}
	
	
	@APIOperation(description="인터페이스 실행 로그 목록 조회")
	public List<InterfaceLogSDO> selectListEzcIfLog(InterfaceLogSDO interfaceLogSDO) throws APIException {
		logger.debug("[START] selectListEzcIfLog");
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		
		EzcIfLog inEzcIfLog = (EzcIfLog) propertyUtil.copySameProperty(interfaceLogSDO, EzcIfLog.class);
		List<EzcIfLog> outEzcIfLogList = interfaceLogRepository.selectListEzcIfLog(inEzcIfLog);
		List<InterfaceLogSDO> out = new ArrayList<InterfaceLogSDO>(); 
		InterfaceLogSDO outItem = null;
		
		for(EzcIfLog logItem : outEzcIfLogList) {
			outItem = (InterfaceLogSDO) propertyUtil.copySameProperty(logItem, InterfaceLogSDO.class);
			out.add(outItem);
		}
		
		logger.debug("[END] selectListEzcIfLog");
		return out;
	}
	
	@APIOperation(description="인터페이스 실행 로그 단건 조회")
	public InterfaceLogSDO selectEzcIfLog(InterfaceLogSDO interfaceLogSDO) throws APIException {
		logger.debug("[START] selectEzcIfLog");
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		
		EzcIfLog inEzcIfLog = (EzcIfLog) propertyUtil.copySameProperty(interfaceLogSDO, EzcIfLog.class);
		EzcIfLog outEzcIfLog = interfaceLogRepository.selectEzcIfLog(inEzcIfLog);
		InterfaceLogSDO out = (InterfaceLogSDO) propertyUtil.copySameProperty(outEzcIfLog, InterfaceLogSDO.class);
		
		logger.debug("[END] selectEzcIfLog");
		return out;
	}
}
