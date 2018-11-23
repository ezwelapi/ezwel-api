package com.ezwel.htl.interfaces.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.repository.InsideInterfaceRepository;
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
	
	private InsideInterfaceRepository intefaceDAO = (InsideInterfaceRepository) LApplicationContext.getBean(InsideInterfaceRepository.class);
	
	@APIOperation(description="신규시설등록수정 인터페이스")
	public RecordOutSDO callRecord(RecordInSDO recordSDO) {
		logger.debug("[START] callRecord {}", recordSDO);
		
		//Advice & Interceptor 최적화후 작업 진행
		RecordOutSDO out = intefaceDAO.callRecord(recordSDO);
		
		logger.debug("[END] callRecord {}", out);
		return out;
	}
	
	@APIOperation(description="시설판매중지설정 인터페이스")
	public SaleStopOutSDO callSaleStop(SaleStopInSDO saleStopSDO) {
		logger.debug("[START] callSaleStop {}", saleStopSDO);
		
		//Advice & Interceptor 최적화후 작업 진행
		SaleStopOutSDO out = intefaceDAO.callSaleStop(saleStopSDO);
		
		logger.debug("[END] callSaleStop {}", out);
		return out;
	}
	
	@APIOperation(description="시설바우처번호등록 인터페이스")
	public VoucherRegOutSDO callVoucherReg(VoucherRegInSDO voucherRegSDO) {
		logger.debug("[START] callVoucherReg {}", voucherRegSDO);
		
		//Advice & Interceptor 최적화후 작업 진행
		VoucherRegOutSDO out = intefaceDAO.callVoucherReg(voucherRegSDO);
		
		logger.debug("[END] callVoucherReg {}", out);
		return out;
	}
	
	@APIOperation(description="예약내역조회 인터페이스")
	public ViewOutSDO callView(ViewInSDO viewSDO) {
		logger.debug("[START] callView {}", viewSDO);
		
		//Advice & Interceptor 최적화후 작업 진행
		ViewOutSDO out = intefaceDAO.callView(viewSDO);
		
		logger.debug("[END] callView {}", out);
		return out;
	}
	

	@APIOperation(description="주문대사(제휴사) 인터페이스")
	public AgentJobOutSDO callAgentJob(AgentJobInSDO agentJobSDO) {
		logger.debug("[START] callAgentJob {}", agentJobSDO);
		
		//Advice & Interceptor 최적화후 작업 진행
		AgentJobOutSDO out = intefaceDAO.callAgentJob(agentJobSDO);
		
		logger.debug("[END] callAgentJob {}", out);
		return out;
	}
	
}
