package com.ezwel.htl.interfaces.server.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
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
 * Inside 인터페이스 서비스
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 15.
 */
@Repository
@APIType(description="외부 > 내부 인터페이스 데이터 핸들링 서비스")
public class InsideRepository {

	private static final Logger logger = LoggerFactory.getLogger(InsideRepository.class);

	@APIOperation(description="신규시설등록수정 인터페이스")
	public RecordOutSDO callRecord(RecordInSDO recordSDO) {
		logger.debug("[START] callRecord {}", recordSDO);
		
		RecordOutSDO out = null;

		
		logger.debug("[END] callRecord {}", out);
		return out;
	}
	
	@APIOperation(description="시설판매중지설정 인터페이스")
	public SaleStopOutSDO callSaleStop(SaleStopInSDO saleStopSDO) {
		logger.debug("[START] callSaleStop {}", saleStopSDO);
		
		SaleStopOutSDO out = null;
		
		logger.debug("[END] callSaleStop {}", out);
		return out;
	}
	
	@APIOperation(description="시설바우처번호등록 인터페이스")
	public VoucherRegOutSDO callVoucherReg(VoucherRegInSDO voucherRegSDO) {
		logger.debug("[START] callVoucherReg {}", voucherRegSDO);
		
		VoucherRegOutSDO out = null;
		
		logger.debug("[END] callVoucherReg {}", out);
		return out;
	}
	
	@APIOperation(description="예약내역조회 인터페이스")
	public ViewOutSDO callView(ViewInSDO viewSDO) {
		logger.debug("[START] callView {}", viewSDO);
		
		ViewOutSDO out = null;
		
		logger.debug("[END] callView {}", out);
		return out;
	}
	
	@APIOperation(description="주문대사(제휴사) 인터페이스")
	public AgentJobOutSDO callAgentJob(AgentJobInSDO agentJobSDO) {
		logger.debug("[START] callAgentJob {}", agentJobSDO);
		
		AgentJobOutSDO out = null;
		
		logger.debug("[END] callAgentJob {}", out);
		return out;
	}
	
	
}
