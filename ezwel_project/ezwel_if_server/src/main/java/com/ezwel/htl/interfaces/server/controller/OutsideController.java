package com.ezwel.htl.interfaces.server.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
@RequestMapping(value="/API1.0")
public class OutsideController {

	private static final Logger logger = LoggerFactory.getLogger(OutsideController.class);
	
	private OutsideService outsideService = (OutsideService) LApplicationContext.getBean(OutsideService.class);
	

	@ResponseBody
	@APIOperation(description="시설검색 인터페이스")
	@RequestMapping(value="/service/callFaclSearch")
	public ResponseEntity<List<FaclSearchOutSDO>> callFaclSearch(UserAgentSDO userAgentDTO, FaclSearchInSDO faclSearchDTO, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("[START] callFaclSearch {} {}", userAgentDTO, faclSearchDTO);
		
		ResponseEntity<List<FaclSearchOutSDO>> out = null;
		List<FaclSearchOutSDO> serviceOut = null;
		FaclSearchOutSDO faclSearchOut = null; 
		try {
			if(userAgentDTO == null || faclSearchDTO == null) {
				throw new APIException("입력값이 존재하지 않습니다.");
			}
			
			//Advice & Interceptor 최적화후 작업 추가 진행
			serviceOut = outsideService.callFaclSearch(userAgentDTO, faclSearchDTO);

			out = new ResponseEntity<List<FaclSearchOutSDO>>(serviceOut, HttpStatus.CREATED);
		}
		catch(Exception e) {
			serviceOut = new ArrayList<FaclSearchOutSDO>(); 
			faclSearchOut = new FaclSearchOutSDO();
			
			serviceOut.add(faclSearchOut);
			out = new ResponseEntity<List<FaclSearchOutSDO>>(serviceOut, HttpStatus.CREATED);
			e.printStackTrace();
		}
		
		logger.debug("[END] callFaclSearch {}", out);
		return out;
	}
	

	@ResponseBody
	@APIOperation(description="당일특가검색 인터페이스")
	@RequestMapping(value="/service/callSddSearch")
	public ResponseEntity<List<SddSearchOutSDO>> callSddSearch(UserAgentSDO userAgentDTO, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("[START] callView {}", userAgentDTO);
		
		ResponseEntity<List<SddSearchOutSDO>> out = null;
		List<SddSearchOutSDO> serviceOut = null;
		SddSearchOutSDO sddSearchOut = null;
		try {
			if(userAgentDTO == null) {
				throw new APIException("입력값이 존재하지 않습니다.");
			}
			
			//Advice & Interceptor 최적화후 작업 추가 진행
			serviceOut = outsideService.callSddSearch(userAgentDTO);

			out = new ResponseEntity<List<SddSearchOutSDO>>(serviceOut, HttpStatus.CREATED);
		}
		catch(Exception e) {
			serviceOut = new ArrayList<SddSearchOutSDO>(); 
			sddSearchOut = new SddSearchOutSDO();
			
			serviceOut.add(sddSearchOut);
			
			out = new ResponseEntity<List<SddSearchOutSDO>>(serviceOut, HttpStatus.CREATED);
			e.printStackTrace();
		}
		
		logger.debug("[END] callView {}", out);
		return out;
	}
	
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
	@ResponseBody
	@APIOperation(description="전체시설일괄등록 인터페이스")
	@RequestMapping(value="/service/allReg")
	public ResponseEntity<AllRegOutSDO> callAllReg(UserAgentSDO userAgentSDO, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("[START] callAllReg {}", userAgentSDO);
		
		ResponseEntity<AllRegOutSDO> out = null;
		AllRegOutSDO serviceOut = null;

		try {
			if(userAgentSDO == null) {
				throw new APIException("입력값이 존재하지 않습니다.");
			}
			
			/**
			 * 1. 파라메터 및 헤더 유효성 검사
			 * 2. 인터 페이스 요청에 따른 DB핸들링 
			 * 3. 결과 응답(JSON) 
			 */
			
			//Advice & Interceptor 최적화후 작업 추가 진행
			serviceOut = outsideService.callAllReg(userAgentSDO);

			out = new ResponseEntity<AllRegOutSDO>(serviceOut, HttpStatus.CREATED);
		}
		catch(Exception e) {
			serviceOut = new AllRegOutSDO(); 
			/**
			 * 장애 발생시 code, message 세팅 
			 */
			out = new ResponseEntity<AllRegOutSDO>(serviceOut, HttpStatus.CREATED);
			e.printStackTrace();
		}
		
		logger.debug("[END] callAllReg {}", out);
		return out;
	}
	
}
