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
import com.ezwel.htl.interfaces.server.service.InsideService;
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
 *  http://localhost:8282/ezwel_if_server/API1.0/inside-03/facl/record
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 15.
 */
@Controller
@RequestMapping(value = RequestNamespace.NAME_SPACE)
@APIType(description = "Inside Callee Interface Controller")
public class InsideController {

	private static final Logger logger = LoggerFactory.getLogger(InsideController.class);
	
	private InsideService insideService;
	
	
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
	@APIOperation(description="신규시설등록수정 인터페이스", isOutputJsonMarshall=true, isRequestHeaderValidate=true, isInsideInterfaceAPI=true, returnType=RecordOutSDO.class)
	@RequestMapping(value = "/facl/record")
	public Object callRecord(RecordInSDO recordInSDO) throws APIException, Exception {
		logger.debug("[START] callRecord {}", recordInSDO);
		
		RecordOutSDO out = null;
		
		if(recordInSDO == null) {
			throw new APIException(MessageConstants.RESPONSE_CODE_2000, "입력값이 존재하지 않습니다.");
		}
		
		insideService = (InsideService) LApplicationContext.getBean(insideService, InsideService.class);
		//체널 정보 조회
		out = insideService.callRecord(recordInSDO);

		logger.debug("[END] callRecord {}", out);
		return out;
	}
	
	
	
	/**
	 * 시설판매중지설정 인터페이스
	 * @param saleStopInSDO
	 * @return
	 * @throws APIException
	 * @throws Exception
	 */
	@APIOperation(description="시설판매중지설정 인터페이스", isOutputJsonMarshall=true, isRequestHeaderValidate=true, isInsideInterfaceAPI=true, returnType=SaleStopOutSDO.class)
	@RequestMapping(value = "/facl/saleStop")
	public Object callSaleStop(SaleStopInSDO saleStopInSDO) throws APIException, Exception {
		logger.debug("[START] callSaleStop {}", saleStopInSDO);
		
		SaleStopOutSDO out = null;

		if(saleStopInSDO == null) {
			throw new APIException(MessageConstants.RESPONSE_CODE_2000, "입력값이 존재하지 않습니다.");
		}
		
		insideService = (InsideService) LApplicationContext.getBean(insideService, InsideService.class);
		//체널 정보 조회
		out = insideService.callSaleStop(saleStopInSDO);
		
		logger.debug("[END] callSaleStop {}", out);
		return out;
	}
	

	
	@APIOperation(description="예약완료내역조회(예약조회) 인터페이스", isOutputJsonMarshall=true, isRequestHeaderValidate=true, isInsideInterfaceAPI=true, returnType=ViewOutSDO.class)
	@RequestMapping(value = "/facl/view")
	public Object callView(ViewInSDO viewInSDO) throws APIException, Exception {
		logger.debug("[START] callView {}", viewInSDO);
		
		ViewOutSDO out = null;

		if(viewInSDO == null) {
			throw new APIException(MessageConstants.RESPONSE_CODE_2000, "입력값이 존재하지 않습니다.");
		}
		
		insideService = (InsideService) LApplicationContext.getBean(insideService, InsideService.class);
		//체널 정보 조회
		out = insideService.callView(viewInSDO);
		
		logger.debug("[END] callView {}", out);
		return out;
	}
	
	

	@APIOperation(description="시설바우처번호등록 인터페이스", isOutputJsonMarshall=true, isRequestHeaderValidate=true, isInsideInterfaceAPI=true, returnType=VoucherRegOutSDO.class)
	@RequestMapping(value = "/facl/voucherReg")
	public Object callVoucherReg(VoucherRegInSDO voucherRegInSDO) throws APIException, Exception {
		logger.debug("[START] callVoucherReg {}", voucherRegInSDO);
		
		VoucherRegOutSDO out = null;

		if(voucherRegInSDO == null) {
			throw new APIException(MessageConstants.RESPONSE_CODE_2000, "입력값이 존재하지 않습니다.");
		}
		
		insideService = (InsideService) LApplicationContext.getBean(insideService, InsideService.class);
		//체널 정보 조회
		out = insideService.callVoucherReg(voucherRegInSDO);

		logger.debug("[END] callVoucherReg {}", out);
		return out;
	}
	

	
	@APIOperation(description="주문대사(제휴사) 인터페이스", isOutputJsonMarshall=true, isRequestHeaderValidate=true, isInsideInterfaceAPI=true, returnType=AgentJobOutSDO.class)
	@RequestMapping(value = "/facl/agentJob")
	public Object callAgentJob(AgentJobInSDO agentJobInSDO) throws APIException, Exception {
		logger.debug("[START] callAgentJob {}", agentJobInSDO);
		
		AgentJobOutSDO out = null;

		if(agentJobInSDO == null) {
			throw new APIException(MessageConstants.RESPONSE_CODE_2000, "입력값이 존재하지 않습니다.");
		}
		
		insideService = (InsideService) LApplicationContext.getBean(insideService, InsideService.class);
		//체널 정보 조회
		out = insideService.callAgentJob(agentJobInSDO);
		
		logger.debug("[END] callAgentJob {}", out);
		return out;
	}
	
	
}
