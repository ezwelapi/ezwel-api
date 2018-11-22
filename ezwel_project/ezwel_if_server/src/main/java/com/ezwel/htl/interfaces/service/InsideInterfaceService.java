package com.ezwel.htl.interfaces.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.dao.InsideInterfaceDAO;
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
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 15.
 */
@Service
public class InsideInterfaceService {

	private static final Logger logger = LoggerFactory.getLogger(InsideInterfaceService.class);
	
	private InsideInterfaceDAO intefaceDAO = (InsideInterfaceDAO) LApplicationContext.getBean(InsideInterfaceDAO.class);
	
	@APIOperation(description="신규시설등록수정 인터페이스")
	public RecordOutSDO callRecord(RecordInSDO recordDTO) {
		logger.debug("[START] callRecord {}", recordDTO);
		
		RecordOutSDO out = null;
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
	public SaleStopOutSDO callSaleStop(SaleStopInSDO saleStopDTO) {
		logger.debug("[START] callSaleStop {}", saleStopDTO);
		
		SaleStopOutSDO out = null;
		
		logger.debug("[END] callSaleStop {}", out);
		return out;
	}
	
	@APIOperation(description="시설바우처번호등록 인터페이스")
	public VoucherRegOutSDO callVoucherReg(VoucherRegInSDO voucherRegDTO) {
		logger.debug("[START] callVoucherReg {}", voucherRegDTO);
		
		VoucherRegOutSDO out = null;
		
		logger.debug("[END] callVoucherReg {}", out);
		return out;
	}
	
	@APIOperation(description="예약내역조회 인터페이스")
	public ViewOutSDO callView(ViewInSDO viewDTO) {
		logger.debug("[START] callView {}", viewDTO);
		
		ViewOutSDO out = null;
		
		logger.debug("[END] callView {}", out);
		return out;
	}
	
	@APIOperation(description="주문대사(제휴사) 인터페이스")
	public AgentJobOutSDO callAgentJob(AgentJobInSDO agentJobDTO) {
		logger.debug("[START] callAgentJob {}", agentJobDTO);
		
		AgentJobOutSDO out = null;
		
		logger.debug("[END] callAgentJob {}", out);
		return out;
	}
	
}
