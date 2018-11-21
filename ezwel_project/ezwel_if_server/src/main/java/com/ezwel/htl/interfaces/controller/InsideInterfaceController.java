package com.ezwel.htl.interfaces.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.spring.ApplicationContext;
import com.ezwel.htl.interfaces.service.InsideIfService;
import com.ezwel.htl.interfaces.service.data.agentJob.AgentJobInDTO;
import com.ezwel.htl.interfaces.service.data.agentJob.AgentJobOutDTO;
import com.ezwel.htl.interfaces.service.data.record.RecordInDTO;
import com.ezwel.htl.interfaces.service.data.record.RecordOutDTO;
import com.ezwel.htl.interfaces.service.data.saleStop.SaleStopInDTO;
import com.ezwel.htl.interfaces.service.data.saleStop.SaleStopOutDTO;
import com.ezwel.htl.interfaces.service.data.view.ViewInDTO;
import com.ezwel.htl.interfaces.service.data.view.ViewOutDTO;
import com.ezwel.htl.interfaces.service.data.voucherReg.VoucherRegInDTO;
import com.ezwel.htl.interfaces.service.data.voucherReg.VoucherRegOutDTO;

/**
 * <pre>
 *  http://ip/API1.0/inside-03/facl/record
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 15.
 */
@Controller
@RequestMapping(value="/API1.0")
public class InsideInterfaceController {

	private static final Logger logger = LoggerFactory.getLogger(InsideInterfaceController.class);
	
	private InsideIfService intefaceService = (InsideIfService) ApplicationContext.getBean(InsideIfService.class);
	
	/**
	 * <pre>
	 * [메서드 설명]
	 * URL : /API1.0/{httpAgentId}/facl/record
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
	@APIOperation(description="신규시설등록수정 인터페이스")
	@RequestMapping(value="/{httpAgentId}/facl/record")
	public ResponseEntity<RecordOutDTO> callRecord(@PathVariable("httpAgentId") String httpAgentId, RecordInDTO in, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("[START] callRecord {}", in);
		if(in == null) {
			throw new APIException("입력값이 존재하지 않습니다.");
		}
		
		ResponseEntity<RecordOutDTO> out = null;
		RecordOutDTO serviceOut = null;

		try {
			/**
			 * 1. 파라메터 및 헤더 유효성 검사
			 * 2. 인터 페이스 요청에 따른 DB핸들링 
			 * 3. 결과 응답(JSON) 
			 */
			
			serviceOut = intefaceService.callRecord(in);

			out = new ResponseEntity<RecordOutDTO>(serviceOut, HttpStatus.OK);
		}
		catch(Exception e) {
			serviceOut = new RecordOutDTO(); 
			/**
			 * 장애 발생시 code, message 세팅 
			 */
			out = new ResponseEntity<RecordOutDTO>(serviceOut, HttpStatus.OK);
			e.printStackTrace();
		}
		
		logger.debug("[END] callRecord {}", out);
		return out;
	}
	
	@ResponseBody
	@APIOperation(description="시설판매중지설정 인터페이스")
	@RequestMapping(value="/{httpAgentId}/facl/saleStop")
	public ResponseEntity<SaleStopOutDTO> callSaleStop(@PathVariable("httpAgentId") String httpAgentId, SaleStopInDTO in, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("[START] callSaleStop {}", in);
		if(in == null) {
			throw new APIException("입력값이 존재하지 않습니다.");
		}
		
		ResponseEntity<SaleStopOutDTO> out = null;
		SaleStopOutDTO serviceOut = null;

		try {
			serviceOut = intefaceService.callSaleStop(in);

			out = new ResponseEntity<SaleStopOutDTO>(serviceOut, HttpStatus.OK);
		}
		catch(Exception e) {
			serviceOut = new SaleStopOutDTO(); 
		
			out = new ResponseEntity<SaleStopOutDTO>(serviceOut, HttpStatus.OK);
			e.printStackTrace();
		}
		
		logger.debug("[END] callSaleStop {}", out);
		return out;
	}
	

	@ResponseBody
	@APIOperation(description="예약내역조회 인터페이스")
	@RequestMapping(value="/{httpAgentId}/rsv/view")
	public ResponseEntity<ViewOutDTO> callView(@PathVariable("httpAgentId") String httpAgentId, ViewInDTO in, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("[START] callView {}", in);
		if(in == null) {
			throw new APIException("입력값이 존재하지 않습니다.");
		}
		
		ResponseEntity<ViewOutDTO> out = null;
		ViewOutDTO serviceOut = null;

		try {
			serviceOut = intefaceService.callView(in);

			out = new ResponseEntity<ViewOutDTO>(serviceOut, HttpStatus.OK);
		}
		catch(Exception e) {
			serviceOut = new ViewOutDTO(); 
		
			out = new ResponseEntity<ViewOutDTO>(serviceOut, HttpStatus.OK);
			e.printStackTrace();
		}
		
		logger.debug("[END] callView {}", out);
		return out;
	}
	
	@ResponseBody
	@APIOperation(description="시설바우처번호등록 인터페이스")
	@RequestMapping(value="/{httpAgentId}/facl/voucherReg")
	public ResponseEntity<VoucherRegOutDTO> callVoucherReg(@PathVariable("httpAgentId") String httpAgentId, VoucherRegInDTO in, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("[START] callVoucherReg {}", in);
		if(in == null) {
			throw new APIException("입력값이 존재하지 않습니다.");
		}
		
		ResponseEntity<VoucherRegOutDTO> out = null;
		VoucherRegOutDTO serviceOut = null;

		try {
			serviceOut = intefaceService.callVoucherReg(in);

			out = new ResponseEntity<VoucherRegOutDTO>(serviceOut, HttpStatus.OK);
		}
		catch(Exception e) {
			serviceOut = new VoucherRegOutDTO(); 
		
			out = new ResponseEntity<VoucherRegOutDTO>(serviceOut, HttpStatus.OK);
			e.printStackTrace();
		}
		
		logger.debug("[END] callVoucherReg {}", out);
		return out;
	}
	
	
	@ResponseBody
	@APIOperation(description="주문대사(제휴사) 인터페이스")
	@RequestMapping(value="/{httpAgentId}/order/agentJob")
	public ResponseEntity<AgentJobOutDTO> callAgentJob(@PathVariable("httpAgentId") String httpAgentId, AgentJobInDTO in, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("[START] callAgentJob {}", in);
		if(in == null) {
			throw new APIException("입력값이 존재하지 않습니다.");
		}
		
		ResponseEntity<AgentJobOutDTO> out = null;
		AgentJobOutDTO serviceOut = null;

		try {
			serviceOut = intefaceService.callAgentJob(in);

			out = new ResponseEntity<AgentJobOutDTO>(serviceOut, HttpStatus.OK);
		}
		catch(Exception e) {
			serviceOut = new AgentJobOutDTO(); 
		
			out = new ResponseEntity<AgentJobOutDTO>(serviceOut, HttpStatus.OK);
			e.printStackTrace();
		}
		
		logger.debug("[END] callAgentJob {}", out);
		return out;
	}	
	
}
