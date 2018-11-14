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
import com.ezwel.htl.interfaces.service.dto.allReg.AllRegOutDTO;
import com.ezwel.htl.interfaces.service.dto.cancelFeeAmt.CancelFeeAmtInDTO;
import com.ezwel.htl.interfaces.service.dto.cancelFeeAmt.CancelFeeAmtOutDTO;
import com.ezwel.htl.interfaces.service.dto.cancelFeePsrc.CancelFeePsrcInDTO;
import com.ezwel.htl.interfaces.service.dto.cancelFeePsrc.CancelFeePsrcOutDTO;

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


	@APIOperation(description="신규시설등록수정 인터페이스")
	public RecordOutDTO callRecord(RecordInDTO recodeDTO) {
		
		
		
		return out;
	}


	@APIOperation(description="시설판매중지설정 인터페이스")
	public SaleStopOutDTO callSaleStop(SaleStopInDTO saleStopDTO) {
		
		
		
		return out;
	}


	@APIOperation(description="예약내역조회 인터페이스")
	public ViewOutDTO callView(ViewInDTO viewDTO) {
		
		
		
		return out;
	}

	@APIOperation(description="시설바우처번호등록 인터페이스")
	public VoucherRegOutDTO callVoucherReg(VoucherRegInDTO voucherRegDTO) {
		
		
		
		return out;
	}
	
	
}