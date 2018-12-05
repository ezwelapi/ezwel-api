package com.ezwel.htl.interfaces.server.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.service.OutsideService;
import com.ezwel.htl.interfaces.service.data.allReg.AllRegOutSDO;
import com.ezwel.htl.interfaces.service.data.faclSearch.FaclSearchInSDO;
import com.ezwel.htl.interfaces.service.data.faclSearch.FaclSearchOutSDO;
import com.ezwel.htl.interfaces.service.data.sddSearch.SddSearchOutSDO;

/**
 * <pre>
 *  http://localhost:8282/ezwel_if_server/API1.0/inside-03/facl/record
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 15.
 */
@Controller
public class OutsideController {

	private static final Logger logger = LoggerFactory.getLogger(OutsideController.class);
	
	private OutsideService outsideService = (OutsideService) LApplicationContext.getBean(OutsideService.class);
	
	/**
	 * <pre>
	 * [메서드 설명]
	 * URL : /API1.0/test/facl/record
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @param httpAgentId
	 * @param in
	 * @param request
	 * @param response
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 21.
	 */
	@APIOperation(description="전체시설일괄등록 인터페이스", isOutputJsonMarshall=true, returnType=AllRegOutSDO.class)
	@RequestMapping(value="/service/allReg")
	public Object callAllReg(UserAgentSDO userAgentSDO, HttpServletRequest request, HttpServletResponse response) {
		
		AllRegOutSDO out = null;

		if (userAgentSDO == null) {
			throw new APIException("입력값이 존재하지 않습니다.");
		}

		out = outsideService.callAllReg(userAgentSDO);

		return out;
	}
	
	@APIOperation(description="시설검색 인터페이스", isOutputJsonMarshall=true, returnType=FaclSearchOutSDO.class)
	@RequestMapping(value="/service/callFaclSearch")
	public Object callFaclSearch(UserAgentSDO userAgentDTO, FaclSearchInSDO faclSearchDTO, HttpServletRequest request, HttpServletResponse response) {
		
		FaclSearchOutSDO out = null;
		
		if (userAgentDTO == null || faclSearchDTO == null) {
			throw new APIException("입력값이 존재하지 않습니다.");
		}

		// Advice & Interceptor 최적화후 작업 추가 진행
		out = outsideService.callFaclSearch(userAgentDTO, faclSearchDTO);

		return out;
	}
	

	@APIOperation(description="당일특가검색 인터페이스", isOutputJsonMarshall=true, returnType=SddSearchOutSDO.class)
	@RequestMapping(value="/service/callSddSearch")
	public Object callSddSearch(UserAgentSDO userAgentDTO, HttpServletRequest request, HttpServletResponse response) {
		
		SddSearchOutSDO out = null;

		if (userAgentDTO == null) {
			throw new APIException("입력값이 존재하지 않습니다.");
		}

		// Advice & Interceptor 최적화후 작업 추가 진행
		out = outsideService.callSddSearch(userAgentDTO);
		
		return out;
	}
	
	
	
}
