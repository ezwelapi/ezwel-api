package com.ezwel.htl.interfaces.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.data.VerificationSDO;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.commons.utils.CommonUtil;
import com.ezwel.htl.interfaces.server.repository.InsideRepository;
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
@APIType(description="외부 > 내부 인터페이스 서비스")
public class InsideService {

	private static final Logger logger = LoggerFactory.getLogger(InsideService.class);
	
	private InsideRepository intefaceDAO;
	
	private OutsideService outsideService;
	
	private CommonUtil commonUtil;
	
	@APIOperation(description="신규시설등록수정 인터페이스")
	public RecordOutSDO callRecord(RecordInSDO recordSDO) throws APIException, Exception {
		logger.debug("[START] callRecord {}", recordSDO);
		
		RecordOutSDO out = null;
		
		intefaceDAO = (InsideRepository) LApplicationContext.getBean(intefaceDAO, InsideRepository.class);
		commonUtil = (CommonUtil) LApplicationContext.getBean(commonUtil, CommonUtil.class);
		outsideService = (OutsideService) LApplicationContext.getBean(outsideService, OutsideService.class);
		
		//시그니처 및 에이전트 체널 검증
		commonUtil.getConfirmHeaderSignature("record");
		
		// dataUrl 로 인터페이스 실행 하면 결과로 1개의 시설정보(부대/이미지 포함) 가 조회됨 해당 정보를 DB에 저장 
		// - 끄~~~~~읏
		
		// 이것을 써야 것는디..
		// outsideService.callAllReg(userAgentDTO);
		
		logger.debug("[END] callRecord {}", out);
		return out;
	}
	
	
	@APIOperation(description="시설판매중지설정 인터페이스")
	public SaleStopOutSDO callSaleStop(SaleStopInSDO saleStopSDO) throws APIException, Exception {
		logger.debug("[START] callSaleStop {}", saleStopSDO);
		
		intefaceDAO = (InsideRepository) LApplicationContext.getBean(intefaceDAO, InsideRepository.class);
		commonUtil = (CommonUtil) LApplicationContext.getBean(commonUtil, CommonUtil.class);
		
		//시그니처 및 에이전트 체널 검증
		commonUtil.getConfirmHeaderSignature("saleStop");
		
		SaleStopOutSDO out = intefaceDAO.callSaleStop(saleStopSDO);
		
		logger.debug("[END] callSaleStop {}", out);
		return out;
	}
	

	@APIOperation(description="예약내역조회 인터페이스")
	public ViewOutSDO callView(ViewInSDO viewSDO) throws APIException, Exception {
		logger.debug("[START] callView {}", viewSDO);
		
		intefaceDAO = (InsideRepository) LApplicationContext.getBean(intefaceDAO, InsideRepository.class);
		commonUtil = (CommonUtil) LApplicationContext.getBean(commonUtil, CommonUtil.class);

		//시그니처 및 에이전트 체널 검증
		commonUtil.getConfirmHeaderSignature("view");
		
		ViewOutSDO out = intefaceDAO.callView(viewSDO);
		
		logger.debug("[END] callView {}", out);
		return out;
	}
	
	
	@APIOperation(description="시설바우처번호등록 인터페이스")
	public VoucherRegOutSDO callVoucherReg(VoucherRegInSDO voucherRegSDO) throws APIException, Exception {
		logger.debug("[START] callVoucherReg {}", voucherRegSDO);
		
		intefaceDAO = (InsideRepository) LApplicationContext.getBean(intefaceDAO, InsideRepository.class);
		commonUtil = (CommonUtil) LApplicationContext.getBean(commonUtil, CommonUtil.class);
		
		//시그니처 및 에이전트 체널 검증
		commonUtil.getConfirmHeaderSignature("voucherReg");
		
		VoucherRegOutSDO out = intefaceDAO.callVoucherReg(voucherRegSDO);
		
		logger.debug("[END] callVoucherReg {}", out);
		return out;
	}
	
	

	@APIOperation(description="주문대사(제휴사) 인터페이스")
	public AgentJobOutSDO callAgentJob(AgentJobInSDO agentJobSDO) throws APIException, Exception {
		logger.debug("[START] callAgentJob {}", agentJobSDO);
		
		intefaceDAO = (InsideRepository) LApplicationContext.getBean(intefaceDAO, InsideRepository.class);
		commonUtil = (CommonUtil) LApplicationContext.getBean(commonUtil, CommonUtil.class);
		
		//시그니처 및 에이전트 체널 검증
		commonUtil.getConfirmHeaderSignature("agentJob");
		
		AgentJobOutSDO out = intefaceDAO.callAgentJob(agentJobSDO);
		
		logger.debug("[END] callAgentJob {}", out);
		return out;
	}
	
}
