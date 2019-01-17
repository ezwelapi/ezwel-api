package com.ezwel.htl.interfaces.server.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.sdo.IfLogSDO;
import com.ezwel.htl.interfaces.server.commons.interfaces.RequestNamespace;
import com.ezwel.htl.interfaces.server.service.InterfaceLogService;

/**
 * <pre>
 *  http://localhost:8282/API1.0/inside-03/facl/record
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 15.
 */
@Controller
@RequestMapping(value = RequestNamespace.NAME_SPACE)
@APIType(description = "인터페이스 로그 조회")
public class InterfaceLogController {

	private static final Logger logger = LoggerFactory.getLogger(InterfaceLogController.class);
	
	private InterfaceLogService interfaceLogService;


	@RequestMapping(value = "/countListInterfaceLogSDO")
	@APIOperation(description="인터페이스 실행 로그 건수 조회", isOutputJsonMarshall=true, returnType=IfLogSDO.class)
	public Object countListInterfaceLogSDO(IfLogSDO interfaceLogSDO) throws APIException {
		logger.debug("[START] countListInterfaceLogSDO");
		
		IfLogSDO out = interfaceLogService.countListEzcIfLog(interfaceLogSDO);
		
		logger.debug("[END] countListInterfaceLogSDO");
		return out;
	}
	
	@RequestMapping(value = "/selectListInterfaceLogSDO")
	@APIOperation(description="인터페이스 실행 로그 목록 조회", isOutputJsonMarshall=true, returnType=IfLogSDO.class)
	public Object selectListInterfaceLogSDO(IfLogSDO interfaceLogSDO) throws APIException {
		logger.debug("[START] selectListInterfaceLogSDO");
		
		List<IfLogSDO> out = interfaceLogService.selectListEzcIfLog(interfaceLogSDO);

		logger.debug("[END] selectListInterfaceLogSDO");	
		return out;
	}
	
	@RequestMapping(value = "/selectInterfaceLogSDO")
	@APIOperation(description="인터페이스 실행 로그 단건 조회", isOutputJsonMarshall=true, returnType=IfLogSDO.class)
	public Object selectInterfaceLogSDO(IfLogSDO interfaceLogSDO) throws APIException {
		logger.debug("[START] selectInterfaceLogSDO");

		IfLogSDO out = interfaceLogService.selectEzcIfLog(interfaceLogSDO);
			
		logger.debug("[END] selectInterfaceLogSDO");
		return out;
	}
}
