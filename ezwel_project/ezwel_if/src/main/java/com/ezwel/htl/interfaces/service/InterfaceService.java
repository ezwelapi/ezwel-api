package com.ezwel.htl.interfaces.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ezwel.htl.interfaces.annotation.APIOperation;
import com.ezwel.htl.interfaces.annotation.APIService;
import com.ezwel.htl.interfaces.exception.APIException;
import com.ezwel.htl.interfaces.http.HttpInterfaceService;
import com.ezwel.htl.interfaces.http.dto.HttpConfigDTO;
import com.ezwel.htl.interfaces.service.dto.AgentJobInDTO;
import com.ezwel.htl.interfaces.service.dto.AgentJobOutDTO;
import com.ezwel.htl.interfaces.service.dto.AllRegOutDTO;
import com.ezwel.htl.interfaces.service.dto.CancelFeeAmtInDTO;
import com.ezwel.htl.interfaces.service.dto.CancelFeeAmtOutDTO;
import com.ezwel.htl.interfaces.service.dto.CancelFeePsrcInDTO;
import com.ezwel.htl.interfaces.service.dto.CancelFeePsrcOutDTO;
import com.ezwel.htl.interfaces.spring.ApplicationContext;
import com.ezwel.htl.interfaces.utils.APIUtil;

/**
 * <pre>
 * 인터페이스 서비스
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
@APIService
@Service(value="InterfaceService")
public class InterfaceService {

	private static final Logger logger = LoggerFactory.getLogger(InterfaceService.class);

	private HttpInterfaceService inteface = (HttpInterfaceService) ApplicationContext.getBean("HttpInterfaceService");
	
	@APIOperation(description="주문대사(제휴사) 인터페이스 (방향:OUT)")
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

	
	@APIOperation(description="전체시설일괄등록 인터페이스 (방향:OUT)")
	public AllRegOutDTO callAllReg() {
		
		AllRegOutDTO out = null;
		
		try {
			
			HttpConfigDTO config = new HttpConfigDTO();
			/** reqeust header config */
			config.setHttpAgentId("제휴사ID-".concat(APIUtil.getId()));
			config.setHttpApiKey("apiKey-agentJob");
			config.setHttpApiSignature(APIUtil.getSecretId("apiKey-agentJob"));
			config.setHttpApiTimestamp(Long.toString(APIUtil.currentTimeMillis() / 100));
			/** interface target config */
			config.setRestURI("http://localhost:9123/ezwel_if_demo/service/allReg.jsp");
			/** execute interface */
			out = (AllRegOutDTO) inteface.sendPostJSON(config, AllRegOutDTO.class);
		}
		catch(Exception e) {
			throw new APIException("주문대사(제휴사) 인터페이스 장애발생.", e);
		}
		
		return out;
	}

	
	@APIOperation(description="취소수수료계산 인터페이스 (방향:OUT)")
	public CancelFeeAmtOutDTO callCancelFeeAmt(CancelFeeAmtInDTO cancelFeeAmtDTO) {
		
		CancelFeeAmtOutDTO out = null;
		
		try {
			
			HttpConfigDTO config = new HttpConfigDTO();
			/** reqeust header config */
			config.setHttpAgentId("제휴사ID-".concat(APIUtil.getId()));
			config.setHttpApiKey("apiKey-agentJob");
			config.setHttpApiSignature(APIUtil.getSecretId("apiKey-agentJob"));
			config.setHttpApiTimestamp(Long.toString(APIUtil.currentTimeMillis() / 100));
			/** interface target config */
			config.setRestURI("http://localhost:9123/ezwel_if_demo/service/cancelFeeAmt.jsp");
			/** execute interface */
			out = (CancelFeeAmtOutDTO) inteface.sendPostJSON(config, cancelFeeAmtDTO, CancelFeeAmtOutDTO.class);
		}
		catch(Exception e) {
			throw new APIException("주문대사(제휴사) 인터페이스 장애발생.", e);
		}
		return out;
	}

	@APIOperation(description="취소수수규정 인터페이스 (방향:OUT)")
	public CancelFeePsrcOutDTO callCancelFeePsrc(CancelFeePsrcInDTO cancelFeePsrcDTO) {
		
		
		
		return out;
	}

	public EzwelJobOutDTO callEzwelJob(EzwelJobInDTO ezwelJobDTO) {
		
		
		return out;
	}

	//멀티쓰레드
	public FaclSearchOutDTO callFaclSearch(FaclSearchInDTO faclSearchDTO) {
		
		
		
		return out;
	}

	public OmiNumIdnOutDTO callOmiNumIdn(OmiNumIdnInDTO omiNumIdnDTO) {
		
		
		
		return out;
	}

	public OrderCancelReqOutDTO callOrderCancelReq(OrderCancelReqInDTO orderCancelReqDTO) {
		
		
		
		return out;
	}

	public RecodeOutDTO callRecode(RecodeInDTO recodeDTO) {
		
		
		
		return out;
	}

	public RoomReadOutDTO callRoomRead(RoomReadInDTO roomReadDTO) {
		
		
		
		return out;
	}

	public RsvHistSendOutDTO callRsvHistSend(RsvHistSendInDTO rsvHistSendDTO) {
		
		
		
		return out;
	}

	public SaleStopOutDTO callSaleStop(SaleStopInDTO saleStopDTO) {
		
		
		
		return out;
	}

	//멀티쓰레드
	@APIOperation(description="당일특가검색 인터페이스")
	public SddSearchOutDTO callSddSearch() {
		
		
		
		return out;
	}

	public ViewOutDTO callView(ViewInDTO viewDTO) {
		
		
		
		return out;
	}

	public VoucherRegOutDTO callVoucherReg(VoucherRegInDTO voucherRegDTO) {
		
		
		
		return out;
	}
	
	
}
