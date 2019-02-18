package com.ezwel.htl.interfaces.server.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.sdo.ApiBatcLogSDO;
import com.ezwel.htl.interfaces.commons.sdo.IfLogSDO;
import com.ezwel.htl.interfaces.server.commons.interfaces.RequestNamespace;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.service.LogService;

/**
 * <pre>
 *  http://localhost:8282/API1.0/inside-03/facl/record
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 15.
 */
@Controller
@RequestMapping(value = RequestNamespace.NAME_SPACE)
@APIType(description = "인터페이스/배치 로그 조회")
public class LogController {

	private static final Logger logger = LoggerFactory.getLogger(LogController.class);
	
	private LogService logService;


	@RequestMapping(value = "/countListIfLogSDO")
	@APIOperation(description="인터페이스 실행 로그 건수 조회", isOutputJsonMarshall=true, returnType=IfLogSDO.class)
	public Object countListIfLogSDO(IfLogSDO interfaceLogSDO) throws APIException {
		logger.debug("[START] countListIfLogSDO");
		
		logService = (LogService) LApplicationContext.getBean(logService, LogService.class);
		
		IfLogSDO out = logService.countListEzcIfLog(interfaceLogSDO);
		
		logger.debug("[END] countListIfLogSDO");
		return out;
	}
	
	@RequestMapping(value = "/selectListIfLogSDO")
	@APIOperation(description="인터페이스 실행 로그 목록 조회", isOutputJsonMarshall=true, returnType=IfLogSDO.class)
	public Object selectListIfLogSDO(IfLogSDO interfaceLogSDO) throws APIException {
		logger.debug("[START] selectListIfLogSDO");
		
		logService = (LogService) LApplicationContext.getBean(logService, LogService.class);
		
		List<IfLogSDO> out = logService.selectListEzcIfLog(interfaceLogSDO);

		logger.debug("[END] selectListIfLogSDO");	
		return out;
	}
	
	@RequestMapping(value = "/selectIfLogSDO")
	@APIOperation(description="인터페이스 실행 로그 단건 조회", isOutputJsonMarshall=true, returnType=IfLogSDO.class)
	public Object selectIfLogSDO(IfLogSDO interfaceLogSDO) throws APIException {
		logger.debug("[START] selectIfLogSDO");

		logService = (LogService) LApplicationContext.getBean(logService, LogService.class);
		
		IfLogSDO out = logService.selectEzcIfLog(interfaceLogSDO);
			
		logger.debug("[END] selectIfLogSDO");
		return out;
	}
	

	@RequestMapping(value = "/countListApiBatcLog")
	@APIOperation(description="API 배치 실행 로그 건수 조회", isOutputJsonMarshall=true, returnType=ApiBatcLogSDO.class)
	public Object countListApiBatcLogSDO(ApiBatcLogSDO apiBatcLogSDO) throws APIException {
		logger.debug("[START] countListApiBatcLogSDO");
		
		logService = (LogService) LApplicationContext.getBean(logService, LogService.class);
		
		ApiBatcLogSDO out = logService.countListEzcApiBatcLog(apiBatcLogSDO);
		
		logger.debug("[END] countListApiBatcLogSDO");
		return out;
	}
	
	@RequestMapping(value = "/selectListApiBatcLog")
	@APIOperation(description="API 배치 실행 로그 목록 조회", isOutputJsonMarshall=true, returnType=ApiBatcLogSDO.class)
	public Object selectListApiBatcLogSDO(ApiBatcLogSDO apiBatcLogSDO) throws APIException {
		logger.debug("[START] selectListApiBatcLogSDO");
		
		logService = (LogService) LApplicationContext.getBean(logService, LogService.class);
		
		List<ApiBatcLogSDO> out = logService.selectListEzcApiBatcLog(apiBatcLogSDO);

		logger.debug("[END] selectListApiBatcLogSDO");	
		return out;
	}
	
	@RequestMapping(value = "/selectApiBatcLog")
	@APIOperation(description="API 배치 실행 로그 단건 조회", isOutputJsonMarshall=true, returnType=ApiBatcLogSDO.class)
	public Object selectApiBatcLogSDO(ApiBatcLogSDO apiBatcLogSDO) throws APIException {
		logger.debug("[START] selectApiBatcLogSDO");

		logService = (LogService) LApplicationContext.getBean(logService, LogService.class);
		
		ApiBatcLogSDO out = logService.selectEzcApiBatcLog(apiBatcLogSDO);
			
		logger.debug("[END] selectApiBatcLogSDO");
		return out;
	}

	
	@RequestMapping(value = "/deleteLogData")
	@APIOperation(description="오늘로부터 N일전 인터페이스 로그와 API 배치로그 삭제", isOutputJsonMarshall=true, returnType=ApiBatcLogSDO.class)
	public Object deleteLogData(ApiBatcLogSDO apiBatcLogSDO) throws APIException {
		logger.debug("[START] deleteLogData");

		logService = (LogService) LApplicationContext.getBean(logService, LogService.class);
		
		ApiBatcLogSDO out = new ApiBatcLogSDO();
		out.setTxCount(logService.deleteLogData(apiBatcLogSDO.getDeleteDay()));
		
		logger.debug("[END] deleteLogData");
		return out;
	}
	
}
