package com.ezwel.htl.interfaces.server.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.HttpInterfaceExecutor;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigSDO;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.commons.utils.CommonUtil;
import com.ezwel.htl.interfaces.server.repository.InsideRepository;
import com.ezwel.htl.interfaces.service.data.agentJob.AgentJobInSDO;
import com.ezwel.htl.interfaces.service.data.agentJob.AgentJobOutSDO;
import com.ezwel.htl.interfaces.service.data.allReg.AllRegOutSDO;
import com.ezwel.htl.interfaces.service.data.record.RecordInSDO;
import com.ezwel.htl.interfaces.service.data.record.RecordOutSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadOutSDO;
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
	
	private InsideRepository insideRepository;
	
	private OutsideService outsideService;
	
	private CommonUtil commonUtil;
	
	private HttpInterfaceExecutor inteface;
	
	@APIOperation(description="신규시설등록수정 인터페이스")
	public RecordOutSDO callRecord(RecordInSDO recordSDO) throws APIException, Exception {
		logger.debug("[START] callRecord {}", recordSDO);
		
		RecordOutSDO out = null;
		
		insideRepository = (InsideRepository) LApplicationContext.getBean(insideRepository, InsideRepository.class);
		commonUtil = (CommonUtil) LApplicationContext.getBean(commonUtil, CommonUtil.class);
		outsideService = (OutsideService) LApplicationContext.getBean(outsideService, OutsideService.class);
		inteface = (HttpInterfaceExecutor) LApplicationContext.getBean(inteface, HttpInterfaceExecutor.class);
		
		//시그니처 및 에이전트 체널 검증
		commonUtil.isConfirmHeaderSignature("record");
		
		HttpConfigSDO httpConfigSDO = new HttpConfigSDO();
		httpConfigSDO.setRestURI(recordSDO.getDataUrl());
		httpConfigSDO.setEncoding(OperateConstants.DEFAULT_ENCODING);
		httpConfigSDO.setDoInput(true);
		
		
		// 인터페이스 결과 1개 시설에 대한 데이터 저장/삭제 실행
		List<AllRegOutSDO> assets = new ArrayList<AllRegOutSDO>();
		AllRegOutSDO recordData = (AllRegOutSDO) inteface.sendJSON(httpConfigSDO, AllRegOutSDO.class);
		assets.add(recordData);
		outsideService.saveAllReg(assets);
		
		logger.debug("[END] callRecord {}", out);
		return out;
	}
	
	
	@APIOperation(description="시설판매중지설정 인터페이스")
	public SaleStopOutSDO callSaleStop(SaleStopInSDO saleStopSDO) throws APIException, Exception {
		logger.debug("[START] callSaleStop {}", saleStopSDO);
		
		insideRepository = (InsideRepository) LApplicationContext.getBean(insideRepository, InsideRepository.class);
		commonUtil = (CommonUtil) LApplicationContext.getBean(commonUtil, CommonUtil.class);
		
		//시그니처 및 에이전트 체널 검증
		commonUtil.isConfirmHeaderSignature("saleStop");
		
		SaleStopOutSDO out = insideRepository.callSaleStop(saleStopSDO);
		
		logger.debug("[END] callSaleStop {}", out);
		return out;
	}
	

	@APIOperation(description="예약내역조회 인터페이스")
	public ViewOutSDO callView(ViewInSDO viewSDO) throws APIException, Exception {
		logger.debug("[START] callView {}", viewSDO);
		
		insideRepository = (InsideRepository) LApplicationContext.getBean(insideRepository, InsideRepository.class);
		commonUtil = (CommonUtil) LApplicationContext.getBean(commonUtil, CommonUtil.class);

		//시그니처 및 에이전트 체널 검증
		commonUtil.isConfirmHeaderSignature("view");
		
		ViewOutSDO out = insideRepository.callView(viewSDO);
		
		logger.debug("[END] callView {}", out);
		return out;
	}
	
	
	@APIOperation(description="시설바우처번호등록 인터페이스")
	public VoucherRegOutSDO callVoucherReg(VoucherRegInSDO voucherRegSDO) throws APIException, Exception {
		logger.debug("[START] callVoucherReg {}", voucherRegSDO);
		
		insideRepository = (InsideRepository) LApplicationContext.getBean(insideRepository, InsideRepository.class);
		commonUtil = (CommonUtil) LApplicationContext.getBean(commonUtil, CommonUtil.class);
		
		//시그니처 및 에이전트 체널 검증
		commonUtil.isConfirmHeaderSignature("voucherReg");
		
		VoucherRegOutSDO out = insideRepository.callVoucherReg(voucherRegSDO);
		
		logger.debug("[END] callVoucherReg {}", out);
		return out;
	}
	

	@APIOperation(description="주문대사(제휴사) 인터페이스")
	public AgentJobOutSDO callAgentJob(AgentJobInSDO agentJobSDO) throws APIException, Exception {
		logger.debug("[START] callAgentJob {}", agentJobSDO);
		
		insideRepository = (InsideRepository) LApplicationContext.getBean(insideRepository, InsideRepository.class);
		commonUtil = (CommonUtil) LApplicationContext.getBean(commonUtil, CommonUtil.class);
		
		//시그니처 및 에이전트 체널 검증
		commonUtil.isConfirmHeaderSignature("agentJob");
		
		AgentJobOutSDO out = insideRepository.callAgentJob(agentJobSDO);
		
		logger.debug("[END] callAgentJob {}", out);
		return out;
	}
	
}
