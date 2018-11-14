package com.ezwel.htl.interfaces.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIService;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.HttpInterfaceExecutor;
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
@Service(value="OutsideInterfaceService")
public class OutsideInterfaceService {

	private static final Logger logger = LoggerFactory.getLogger(OutsideInterfaceService.class);

	private HttpInterfaceExecutor inteface = (HttpInterfaceExecutor) ApplicationContext.getBean("HttpInterfaceService");
	
	@APIOperation(description="전체시설일괄등록 인터페이스")
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

	
	@APIOperation(description="취소수수료계산 인터페이스")
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

	@APIOperation(description="취소수수규정 인터페이스")
	public CancelFeePsrcOutDTO callCancelFeePsrc(CancelFeePsrcInDTO cancelFeePsrcDTO) {
		
		CancelFeePsrcOutDTO out = null;
		
		try {
			
			HttpConfigDTO config = new HttpConfigDTO();
			/** reqeust header config */
			config.setHttpAgentId("제휴사ID-".concat(APIUtil.getId()));
			config.setHttpApiKey("apiKey-agentJob");
			config.setHttpApiSignature(APIUtil.getSecretId("apiKey-agentJob"));
			/** interface target config */
			config.setRestURI("http://localhost:9123/ezwel_if_demo/service/cancelFeePsrc.jsp");
			/** execute interface */
			out = (CancelFeePsrcOutDTO) inteface.sendPostJSON(config, cancelFeePsrcDTO, CancelFeePsrcOutDTO.class);
		}
		catch(Exception e) {
			throw new APIException("주문대사(제휴사) 인터페이스 장애발생.", e);
		}
		return out;
	}

	@APIOperation(description="주문대사(이지웰) 인터페이스")
	public EzwelJobOutDTO callEzwelJob(EzwelJobInDTO ezwelJobDTO) {
		
		
		return out;
	}

	//멀티쓰레드
	@APIOperation(description="시설검색 인터페이스")
	public FaclSearchOutDTO callFaclSearch(FaclSearchInDTO faclSearchDTO) {
		
		
		
		return out;
	}

	@APIOperation(description="누락건확인 인터페이스")
	public OmiNumIdnOutDTO callOmiNumIdn(OmiNumIdnInDTO omiNumIdnDTO) {
		
		
		
		return out;
	}

	@APIOperation(description="주문취소요청 인터페이스")
	public OrderCancelReqOutDTO callOrderCancelReq(OrderCancelReqInDTO orderCancelReqDTO) {
		
		
		
		return out;
	}


	@APIOperation(description="객실정보조회 인터페이스")
	public RoomReadOutDTO callRoomRead(RoomReadInDTO roomReadDTO) {
		
		
		
		return out;
	}

	@APIOperation(description="결재완료내역전송 인터페이스")
	public RsvHistSendOutDTO callRsvHistSend(RsvHistSendInDTO rsvHistSendDTO) {
		
		
		
		return out;
	}

	//멀티쓰레드
	@APIOperation(description="당일특가검색 인터페이스")
	public SddSearchOutDTO callSddSearch() {
		
		
		
		return out;
	}
	
}
