package com.ezwel.htl.interfaces.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ezwel.htl.interfaces.annotation.APIOperation;
import com.ezwel.htl.interfaces.annotation.APIService;
import com.ezwel.htl.interfaces.exception.APIException;
import com.ezwel.htl.interfaces.http.HttpInterfaceService;
import com.ezwel.htl.interfaces.http.dto.HttpConfigDTO;
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

	}

	public AllRegOutDTO callAllReg(AllRegInDTO allRegDTO) {
	}

	public CancelFeeAmtOutDTO callCancelFeeAmt(CancelFeeAmtInDTO cancelFeeAmtDTO) {
	}

	public CancelFeePsrcOutDTO callCancelFeePsrc(CancelFeePsrcInDTO cancelFeePsrcDTO) {
	}

	public EzwelJobOutDTO callEzwelJob(EzwelJobInDTO ezwelJobDTO) {
	}

	public FaclSearchOutDTO callFaclSearch(FaclSearchInDTO faclSearchDTO) {
	}

	public OmiNumIdnOutDTO callOmiNumIdn(OmiNumIdnInDTO omiNumIdnDTO) {
	}

	public OrderCancelReqOutDTO callOrderCancelReq(OrderCancelReqInDTO orderCancelReqDTO) {
	}

	public RecodeOutDTO callRecode(RecodeInDTO recodeDTO) {
	}

	public RoomReadOutDTO callRoomRead(RoomReadInDTO roomReadDTO) {
	}

	public RsvHistSendOutDTO callRsvHistSend(RsvHistSendInDTO rsvHistSendDTO) {
	}

	public SaleStopOutDTO callSaleStop(SaleStopInDTO saleStopDTO) {
	}

	public SddSearchOutDTO callSddSearch(SddSearchInDTO sddSearchDTO) {
	}

	public ViewOutDTO callView(ViewInDTO viewDTO) {
	}

	public VoucherRegOutDTO callVoucherReg(VoucherRegInDTO voucherRegDTO) {
	}
	
	
}
