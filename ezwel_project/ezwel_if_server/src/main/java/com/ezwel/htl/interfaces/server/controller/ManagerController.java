package com.ezwel.htl.interfaces.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.server.commons.interfaces.RequestNamespace;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.sdo.ManagerSDO;
import com.ezwel.htl.interfaces.server.service.ManagerService;

/**
 * <pre>
 *  http://localhost:8282/ezwel_if_server/API1.0/manager-03/facl/record
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 15.
 */
@Controller
@RequestMapping(value = RequestNamespace.NAME_SPACE)
@APIType(description = "인터페이스 환경XML파일 관리 Controller")
public class ManagerController {

	private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);
	
	private ManagerService managerService;

	/**
	 * <pre>
	 * [메서드 설명]
	 * 인터페이스 설정 XML내용 조회
	 * 
	 * [사용방법 설명]
	 * URL : /API1.0/service/manager/findXML 
	 * </pre>
	 * 
	 * @param inManagerSDO
	 * @return
	 * @throws APIException
	 * @throws Exception
	 * @author swkim@ebsolution.co.kr
	 * @since  2019. 02. 11. 
	 */
	@APIOperation(description="인터페이스 설정 XML내용 조회", isOutputJsonMarshall=true, returnType=ManagerSDO.class)
	@RequestMapping(value = "/manager/findXML")
	public Object findXML(ManagerSDO inManagerSDO) throws Exception {
		logger.debug("[START] findXML {}", inManagerSDO);
		
		ManagerSDO out = null;
		
		if(inManagerSDO == null) {
			throw new APIException(MessageConstants.RESPONSE_CODE_2000, "입력값이 존재하지 않습니다.");
		}
		
		managerService = (ManagerService) LApplicationContext.getBean(managerService, ManagerService.class);
		//실행
		out = managerService.findXML(inManagerSDO);

		logger.debug("[END] findXML {}", out);
		return out;
	}
	
	/**
	 * <pre>
	 * [메서드 설명]
	 * 인터페이스 설정 XML내용 저장
	 * 
	 * [사용방법 설명]
	 * URL : /API1.0/service/manager/saveXML 
	 * </pre>
	 * 
	 * @param inManagerSDO
	 * @return
	 * @throws APIException
	 * @throws Exception
	 * @author swkim@ebsolution.co.kr
	 * @since  2019. 02. 11. 
	 */
	@APIOperation(description="인터페이스 설정 XML내용 저장", isOutputJsonMarshall=true, returnType=ManagerSDO.class)
	@RequestMapping(value = "/manager/saveXML")
	public Object saveXML(ManagerSDO inManagerSDO) throws Exception {
		logger.debug("[START] saveXML {}", inManagerSDO);
		
		ManagerSDO out = null;
		
		if(inManagerSDO == null) {
			throw new APIException(MessageConstants.RESPONSE_CODE_2000, "입력값이 존재하지 않습니다.");
		}
		
		managerService = (ManagerService) LApplicationContext.getBean(managerService, ManagerService.class);
		//실행
		out = managerService.saveXML(inManagerSDO);

		logger.debug("[END] saveXML {}", out);
		return out;
	}

	
	
	/**
	 * <pre>
	 * [메서드 설명]
	 * 인터페이스 설정 XML내용 반영
	 * 
	 * [사용방법 설명]
	 * URL : /API1.0/service/manager/applyXML 
	 * </pre>
	 * 
	 * @param inManagerSDO
	 * @return
	 * @throws APIException
	 * @throws Exception
	 * @author swkim@ebsolution.co.kr
	 * @since  2019. 02. 11. 
	 */
	@APIOperation(description="인터페이스 설정 XML내용 반영", isOutputJsonMarshall=true, returnType=ManagerSDO.class)
	@RequestMapping(value = "/manager/applyXML")
	public Object applyXML(ManagerSDO inManagerSDO) throws Exception {
		logger.debug("[START] applyXML {}", inManagerSDO);
		
		ManagerSDO out = null;
		
		if(inManagerSDO == null) {
			throw new APIException(MessageConstants.RESPONSE_CODE_2000, "입력값이 존재하지 않습니다.");
		}
		
		managerService = (ManagerService) LApplicationContext.getBean(managerService, ManagerService.class);
		//실행
		out = managerService.applyXML(inManagerSDO);

		logger.debug("[END] applyXML {}", out);
		return out;
	}
	
	
	/**
	 * <pre>
	 * [메서드 설명]
	 * 인터페이스 설정 XML임시 저장내용 목록 조회
	 * 
	 * [사용방법 설명]
	 * URL : /API1.0/service/manager/storeList 
	 * </pre>
	 * 
	 * @param inManagerSDO
	 * @return
	 * @throws APIException
	 * @throws Exception
	 * @author swkim@ebsolution.co.kr
	 * @since  2019. 02. 11. 
	 */
	@APIOperation(description="인터페이스 설정 XML임시 저장내용 목록 조회", isOutputJsonMarshall=true, returnType=ManagerSDO.class)
	@RequestMapping(value = "/manager/storeList")
	public Object storeList(ManagerSDO inManagerSDO) throws Exception {
		logger.debug("[START] storeList {}", inManagerSDO);
		
		ManagerSDO out = null;
		
		if(inManagerSDO == null) {
			throw new APIException(MessageConstants.RESPONSE_CODE_2000, "입력값이 존재하지 않습니다.");
		}
		
		managerService = (ManagerService) LApplicationContext.getBean(managerService, ManagerService.class);
		//실행
		out = managerService.storeList(inManagerSDO);

		logger.debug("[END] storeList {}", out);
		return out;
	}
	
}
