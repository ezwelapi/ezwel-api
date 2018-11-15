package com.ezwel.htl.interfaces.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.dto.HttpConfigDTO;
import com.ezwel.htl.interfaces.commons.spring.ApplicationContext;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.service.InsideInterfaceService;
import com.ezwel.htl.interfaces.service.dto.agentJob.AgentJobInDTO;
import com.ezwel.htl.interfaces.service.dto.agentJob.AgentJobOutDTO;
import com.ezwel.htl.interfaces.service.dto.record.RecordInDTO;
import com.ezwel.htl.interfaces.service.dto.record.RecordOutDTO;
import com.ezwel.htl.interfaces.service.dto.saleStop.SaleStopInDTO;
import com.ezwel.htl.interfaces.service.dto.saleStop.SaleStopOutDTO;
import com.ezwel.htl.interfaces.service.dto.view.ViewInDTO;
import com.ezwel.htl.interfaces.service.dto.view.ViewOutDTO;
import com.ezwel.htl.interfaces.service.dto.voucherReg.VoucherRegInDTO;
import com.ezwel.htl.interfaces.service.dto.voucherReg.VoucherRegOutDTO;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 15.
 */
@Controller
@RequestMapping(value="/interface/api/")
public class InsideInterfaceController {

	private static final Logger logger = LoggerFactory.getLogger(InsideInterfaceController.class);
	
	private InsideInterfaceService intefaceService = (InsideInterfaceService) ApplicationContext.getBean(InsideInterfaceService.class);
	
	@APIOperation(description="신규시설등록수정 인터페이스")
	@RequestMapping(value="callRecord.do")
	public RecordOutDTO callRecord(RecordInDTO recordDTO) {
		logger.debug("[START] callRecord {}", recordDTO);
		
		RecordOutDTO out = intefaceService.callRecord(recordDTO);
		
		logger.debug("[END] callRecord {}", out);
		return out;
	}
	
	@APIOperation(description="시설판매중지설정 인터페이스")
	@RequestMapping(value="callSaleStop.do")
	public SaleStopOutDTO callSaleStop(SaleStopInDTO saleStopDTO) {
		logger.debug("[START] callSaleStop {}", saleStopDTO);
		
		SaleStopOutDTO out = intefaceService.callSaleStop(saleStopDTO);
		
		logger.debug("[END] callSaleStop {}", out);
		return out;
	}
	
	@APIOperation(description="시설바우처번호등록 인터페이스")
	@RequestMapping(value="callVoucherReg.do")
	public VoucherRegOutDTO callVoucherReg(VoucherRegInDTO voucherRegDTO) {
		logger.debug("[START] callVoucherReg {}", voucherRegDTO);
		
		VoucherRegOutDTO out = intefaceService.callVoucherReg(voucherRegDTO);
		
		logger.debug("[END] callVoucherReg {}", out);
		return out;
	}
	
	@APIOperation(description="예약내역조회 인터페이스")
	@RequestMapping(value="callView.do")
	public ViewOutDTO callView(ViewInDTO viewDTO) {
		logger.debug("[START] callView {}", viewDTO);
		
		ViewOutDTO out = intefaceService.callView(viewDTO);
		
		logger.debug("[END] callView {}", out);
		return out;
	}
	
	@APIOperation(description="주문대사(제휴사) 인터페이스")
	@RequestMapping(value="callAgentJob.do")
	public AgentJobOutDTO callAgentJob(AgentJobInDTO agentJobDTO) {
		logger.debug("[START] callAgentJob {}", agentJobDTO);
		
		AgentJobOutDTO out = intefaceService.callAgentJob(agentJobDTO);
		
		logger.debug("[END] callAgentJob {}", out);
		return out;
	}	
	
}
