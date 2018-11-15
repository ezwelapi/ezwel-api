package com.ezwel.htl.interfaces.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIService;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.HttpInterfaceExecutorService;
import com.ezwel.htl.interfaces.commons.http.dto.HttpConfigDTO;
import com.ezwel.htl.interfaces.commons.spring.ApplicationContext;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
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
 * 인터페이스 서비스
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
@APIService
@Service(value="InsideInterfaceService")
public class InsideInterfaceService {

	private static final Logger logger = LoggerFactory.getLogger(InsideInterfaceService.class);

	private HttpInterfaceExecutorService inteface = (HttpInterfaceExecutorService) ApplicationContext.getBean("HttpInterfaceService");
	
	@APIOperation(description="신규시설등록수정 인터페이스")
	public RecordOutDTO callRecord(RecordInDTO recordDTO) {
		
		RecordOutDTO out = null;
		
		try {
			
			HttpConfigDTO config = new HttpConfigDTO();
			/** reqeust header config */
			config.setHttpAgentId("제휴사ID-".concat(APIUtil.getId()));
			config.setHttpApiKey("apiKey-agentJob");
			config.setHttpApiSignature(APIUtil.getSecretId("apiKey-agentJob"));
			config.setHttpApiTimestamp(Long.toString(APIUtil.currentTimeMillis() / 100));
			/** interface target config */
			config.setRestURI("http://localhost:9123/ezwel_if_demo/service/record.jsp");
			/** execute interface */
			out = (RecordOutDTO) inteface.sendPostJSON(config, recordDTO, RecordOutDTO.class);
		}
		catch(Exception e) {
			throw new APIException("시설판매중지설정 인터페이스 장애발생.", e);
		}
		
		return out;
	}
	
	@APIOperation(description="시설판매중지설정 인터페이스")
	public SaleStopOutDTO callSaleStop(SaleStopInDTO saleStopDTO) {
		
		SaleStopOutDTO out = null;
		
		try {
			
			HttpConfigDTO config = new HttpConfigDTO();
			/** reqeust header config */
			config.setHttpAgentId("제휴사ID-".concat(APIUtil.getId()));
			config.setHttpApiKey("apiKey-agentJob");
			config.setHttpApiSignature(APIUtil.getSecretId("apiKey-agentJob"));
			config.setHttpApiTimestamp(Long.toString(APIUtil.currentTimeMillis() / 100));
			/** interface target config */
			config.setRestURI("http://localhost:9123/ezwel_if_demo/service/saleStop.jsp");
			/** execute interface */
			out = (SaleStopOutDTO) inteface.sendPostJSON(config, saleStopDTO, SaleStopOutDTO.class);
		}
		catch(Exception e) {
			throw new APIException("시설판매중지설정 인터페이스 장애발생.", e);
		}
		
		return out;
	}
	
	@APIOperation(description="시설바우처번호등록 인터페이스")
	public VoucherRegOutDTO callVoucherReg(VoucherRegInDTO voucherRegDTO) {
		
		VoucherRegOutDTO out = null;
		
		try {
			
			HttpConfigDTO config = new HttpConfigDTO();
			/** reqeust header config */
			config.setHttpAgentId("제휴사ID-".concat(APIUtil.getId()));
			config.setHttpApiKey("apiKey-agentJob");
			config.setHttpApiSignature(APIUtil.getSecretId("apiKey-agentJob"));
			config.setHttpApiTimestamp(Long.toString(APIUtil.currentTimeMillis() / 100));
			/** interface target config */
			config.setRestURI("http://localhost:9123/ezwel_if_demo/service/voucherReg.jsp");
			/** execute interface */
			out = (VoucherRegOutDTO) inteface.sendPostJSON(config, voucherRegDTO, VoucherRegOutDTO.class);
		}
		catch(Exception e) {
			throw new APIException("시설바우처번호등록 인터페이스 장애발생.", e);
		}
		
		return out;
	}
	
	@APIOperation(description="예약내역조회 인터페이스")
	public ViewOutDTO callView(ViewInDTO viewDTO) {
		
		ViewOutDTO out = null;
		
		try {
			
			HttpConfigDTO config = new HttpConfigDTO();
			/** reqeust header config */
			config.setHttpAgentId("제휴사ID-".concat(APIUtil.getId()));
			config.setHttpApiKey("apiKey-agentJob");
			config.setHttpApiSignature(APIUtil.getSecretId("apiKey-agentJob"));
			config.setHttpApiTimestamp(Long.toString(APIUtil.currentTimeMillis() / 100));
			/** interface target config */
			config.setRestURI("http://localhost:9123/ezwel_if_demo/service/view.jsp");
			/** execute interface */
			out = (ViewOutDTO) inteface.sendPostJSON(config, viewDTO, ViewOutDTO.class);
		}
		catch(Exception e) {
			throw new APIException("예약내역조회 인터페이스 장애발생.", e);
		}
		
		return out;
	}
	
	@APIOperation(description="주문대사(제휴사) 인터페이스")
	public AgentJobOutDTO callAgentJob(AgentJobInDTO agentJobDTO) {
		
		AgentJobOutDTO out = null;
		
		try {
			
			HttpConfigDTO config = new HttpConfigDTO();
			/** reqeust header config */
			config.setHttpAgentId("제휴사ID-".concat(APIUtil.getId()));
			config.setHttpApiKey("apiKey-agentJob");
			config.setHttpApiSignature(APIUtil.getSecretId("apiKey-agentJob"));
			config.setHttpApiTimestamp(Long.toString(APIUtil.currentTimeMillis() / 100));
			/** interface target config */
			config.setRestURI("http://localhost:9123/ezwel_if_demo/service/agentJob.jsp");
			/** execute interface */
			out = (AgentJobOutDTO) inteface.sendPostJSON(config, agentJobDTO, AgentJobOutDTO.class);
		}
		catch(Exception e) {
			throw new APIException("주문대사(제휴사) 인터페이스 장애발생.", e);
		}
		
		return out;
	}	
	
}
