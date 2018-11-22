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
import com.ezwel.htl.interfaces.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.service.InsideInterfaceService;
import com.ezwel.htl.interfaces.service.data.agentJob.AgentJobInSDO;
import com.ezwel.htl.interfaces.service.data.agentJob.AgentJobOutSDO;
import com.ezwel.htl.interfaces.service.data.record.RecordInSDO;
import com.ezwel.htl.interfaces.service.data.record.RecordOutSDO;
import com.ezwel.htl.interfaces.service.data.saleStop.SaleStopInSDO;
import com.ezwel.htl.interfaces.service.data.saleStop.SaleStopOutSDO;
import com.ezwel.htl.interfaces.service.data.view.ViewInSDO;
import com.ezwel.htl.interfaces.service.data.view.ViewOutSDO;
import com.ezwel.htl.interfaces.service.data.voucherReg.VoucherRegInSDO;
import com.ezwel.htl.interfaces.service.data.voucherReg.VoucherRegOutSDO;

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
	
	private InsideInterfaceService intefaceService = (InsideInterfaceService) LApplicationContext.getBean(InsideInterfaceService.class);
	
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
	@APIOperation(description="신규시설등록수정 인터페이스")
	@RequestMapping(value="/{httpAgentId}/facl/record")
	public ResponseEntity<RecordOutSDO> callRecord(@PathVariable("httpAgentId") String httpAgentId, RecordInSDO recordInSDO, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("[START] callRecord {}", recordInSDO);
		
		ResponseEntity<RecordOutSDO> out = null;
		RecordOutSDO serviceOut = null;

		try {
			if(recordInSDO == null) {
				throw new APIException("입력값이 존재하지 않습니다.");
			}
			
			/**
			 * 1. 파라메터 및 헤더 유효성 검사
			 * 2. 인터 페이스 요청에 따른 DB핸들링 
			 * 3. 결과 응답(JSON) 
			 */
			
			//Advice & Interceptor 최적화후 작업 추가 진행
			serviceOut = intefaceService.callRecord(recordInSDO);

			out = new ResponseEntity<RecordOutSDO>(serviceOut, HttpStatus.OK);
		}
		catch(Exception e) {
			serviceOut = new RecordOutSDO(); 
			/**
			 * 장애 발생시 code, message 세팅 
			 */
			out = new ResponseEntity<RecordOutSDO>(serviceOut, HttpStatus.OK);
			e.printStackTrace();
		}
		
		logger.debug("[END] callRecord {}", out);
		return out;
	}
	
	@ResponseBody
	@APIOperation(description="시설판매중지설정 인터페이스")
	@RequestMapping(value="/{httpAgentId}/facl/saleStop")
	public ResponseEntity<SaleStopOutSDO> callSaleStop(@PathVariable("httpAgentId") String httpAgentId, SaleStopInSDO saleStopInSDO, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("[START] callSaleStop {}", saleStopInSDO);
		
		ResponseEntity<SaleStopOutSDO> out = null;
		SaleStopOutSDO serviceOut = null;

		try {
			if(saleStopInSDO == null) {
				throw new APIException("입력값이 존재하지 않습니다.");
			}
			
			//Advice & Interceptor 최적화후 작업 추가 진행
			serviceOut = intefaceService.callSaleStop(saleStopInSDO);

			out = new ResponseEntity<SaleStopOutSDO>(serviceOut, HttpStatus.OK);
		}
		catch(Exception e) {
			serviceOut = new SaleStopOutSDO(); 
		
			out = new ResponseEntity<SaleStopOutSDO>(serviceOut, HttpStatus.OK);
			e.printStackTrace();
		}
		
		logger.debug("[END] callSaleStop {}", out);
		return out;
	}
	

	@ResponseBody
	@APIOperation(description="예약내역조회 인터페이스")
	@RequestMapping(value="/{httpAgentId}/rsv/view")
	public ResponseEntity<ViewOutSDO> callView(@PathVariable("httpAgentId") String httpAgentId, ViewInSDO viewInSDO, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("[START] callView {}", viewInSDO);
		
		ResponseEntity<ViewOutSDO> out = null;
		ViewOutSDO serviceOut = null;

		try {
			if(viewInSDO == null) {
				throw new APIException("입력값이 존재하지 않습니다.");
			}
			
			//Advice & Interceptor 최적화후 작업 추가 진행
			serviceOut = intefaceService.callView(viewInSDO);

			out = new ResponseEntity<ViewOutSDO>(serviceOut, HttpStatus.OK);
		}
		catch(Exception e) {
			serviceOut = new ViewOutSDO(); 
		
			out = new ResponseEntity<ViewOutSDO>(serviceOut, HttpStatus.OK);
			e.printStackTrace();
		}
		
		logger.debug("[END] callView {}", out);
		return out;
	}
	
	@ResponseBody
	@APIOperation(description="시설바우처번호등록 인터페이스")
	@RequestMapping(value="/{httpAgentId}/facl/voucherReg")
	public ResponseEntity<VoucherRegOutSDO> callVoucherReg(@PathVariable("httpAgentId") String httpAgentId, VoucherRegInSDO voucherRegInSDO, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("[START] callVoucherReg {}", voucherRegInSDO);
		
		ResponseEntity<VoucherRegOutSDO> out = null;
		VoucherRegOutSDO serviceOut = null;

		try {
			if(voucherRegInSDO == null) {
				throw new APIException("입력값이 존재하지 않습니다.");
			}
			
			//Advice & Interceptor 최적화후 작업 추가 진행
			serviceOut = intefaceService.callVoucherReg(voucherRegInSDO);

			out = new ResponseEntity<VoucherRegOutSDO>(serviceOut, HttpStatus.OK);
		}
		catch(Exception e) {
			serviceOut = new VoucherRegOutSDO(); 
		
			out = new ResponseEntity<VoucherRegOutSDO>(serviceOut, HttpStatus.OK);
			e.printStackTrace();
		}
		
		logger.debug("[END] callVoucherReg {}", out);
		return out;
	}
	
	
	@ResponseBody
	@APIOperation(description="주문대사(제휴사) 인터페이스")
	@RequestMapping(value="/{httpAgentId}/order/agentJob")
	public ResponseEntity<AgentJobOutSDO> callAgentJob(@PathVariable("httpAgentId") String httpAgentId, AgentJobInSDO agentJobInSDO, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("[START] callAgentJob {}", agentJobInSDO);
		
		ResponseEntity<AgentJobOutSDO> out = null;
		AgentJobOutSDO serviceOut = null;

		try {
			if(agentJobInSDO == null) {
				throw new APIException("입력값이 존재하지 않습니다.");
			}
			
			//Advice & Interceptor 최적화후 작업 추가 진행
			serviceOut = intefaceService.callAgentJob(agentJobInSDO);

			out = new ResponseEntity<AgentJobOutSDO>(serviceOut, HttpStatus.OK);
		}
		catch(Exception e) {
			serviceOut = new AgentJobOutSDO(); 
		
			out = new ResponseEntity<AgentJobOutSDO>(serviceOut, HttpStatus.OK);
			e.printStackTrace();
		}
		
		logger.debug("[END] callAgentJob {}", out);
		return out;
	}	
	
}
