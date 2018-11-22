package com.ezwel.htl.interfaces.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.spring.ApplicationContext;
import com.ezwel.htl.interfaces.dao.InsideInterfaceDAO;
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
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 15.
 */
@Service
public class InsideInterfaceService {

	private static final Logger logger = LoggerFactory.getLogger(InsideInterfaceService.class);
	
	@Autowired
	private InsideInterfaceDAO intefaceDAO; // = (InsideInterfaceDAO) ApplicationContext.getBean(InsideInterfaceDAO.class);
	
	@APIOperation(description="신규시설등록수정 인터페이스")
	public RecordOutDTO callRecord(RecordInDTO recordDTO) {
		logger.debug("[START] callRecord {}", recordDTO);
		
		RecordOutDTO out = null;
		/**
		 * 
		 * 
		 * 
		 * 
		 */
		
		
		
		logger.debug("[END] callRecord {}", out);
		return out;
	}
	
	@APIOperation(description="시설판매중지설정 인터페이스")
	public SaleStopOutDTO callSaleStop(SaleStopInDTO saleStopDTO) {
		logger.debug("[START] callSaleStop {}", saleStopDTO);
		
		SaleStopOutDTO out = null;
		
		logger.debug("[END] callSaleStop {}", out);
		return out;
	}
	
	@APIOperation(description="시설바우처번호등록 인터페이스")
	public VoucherRegOutDTO callVoucherReg(VoucherRegInDTO voucherRegDTO) {
		logger.debug("[START] callVoucherReg {}", voucherRegDTO);
		
		VoucherRegOutDTO out = null;
		
		logger.debug("[END] callVoucherReg {}", out);
		return out;
	}
	
	@APIOperation(description="예약내역조회 인터페이스")
	public ViewOutDTO callView(ViewInDTO viewDTO) {
		logger.debug("[START] callView {}", viewDTO);
		
		ViewOutDTO out = null;
		
		logger.debug("[END] callView {}", out);
		return out;
	}
	
	@APIOperation(description="주문대사(제휴사) 인터페이스")
	public AgentJobOutDTO callAgentJob(AgentJobInDTO agentJobDTO) {
		logger.debug("[START] callAgentJob {}", agentJobDTO);
		
		AgentJobOutDTO out = null;
		
		logger.debug("[END] callAgentJob {}", out);
		return out;
	}
	
}
