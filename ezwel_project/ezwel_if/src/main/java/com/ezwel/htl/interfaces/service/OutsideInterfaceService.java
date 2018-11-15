package com.ezwel.htl.interfaces.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIService;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.HttpInterfaceExecutorService;
import com.ezwel.htl.interfaces.commons.http.dto.HttpConfigDTO;
import com.ezwel.htl.interfaces.commons.http.dto.MultiHttpConfigDTO;
import com.ezwel.htl.interfaces.commons.spring.ApplicationContext;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.service.dto.allReg.AllRegOutDTO;
import com.ezwel.htl.interfaces.service.dto.cancelFeeAmt.CancelFeeAmtInDTO;
import com.ezwel.htl.interfaces.service.dto.cancelFeeAmt.CancelFeeAmtOutDTO;
import com.ezwel.htl.interfaces.service.dto.cancelFeePsrc.CancelFeePsrcInDTO;
import com.ezwel.htl.interfaces.service.dto.cancelFeePsrc.CancelFeePsrcOutDTO;
import com.ezwel.htl.interfaces.service.dto.ezwelJob.EzwelJobInDTO;
import com.ezwel.htl.interfaces.service.dto.ezwelJob.EzwelJobOutDTO;
import com.ezwel.htl.interfaces.service.dto.faclSearch.FaclSearchInDTO;
import com.ezwel.htl.interfaces.service.dto.faclSearch.FaclSearchOutDTO;
import com.ezwel.htl.interfaces.service.dto.omiNumIdn.OmiNumIdnInDTO;
import com.ezwel.htl.interfaces.service.dto.omiNumIdn.OmiNumIdnOutDTO;
import com.ezwel.htl.interfaces.service.dto.orderCancelReq.OrderCancelReqInDTO;
import com.ezwel.htl.interfaces.service.dto.orderCancelReq.OrderCancelReqOutDTO;
import com.ezwel.htl.interfaces.service.dto.roomRead.RoomReadInDTO;
import com.ezwel.htl.interfaces.service.dto.roomRead.RoomReadOutDTO;
import com.ezwel.htl.interfaces.service.dto.rsvHistSend.RsvHistSendInDTO;
import com.ezwel.htl.interfaces.service.dto.rsvHistSend.RsvHistSendOutDTO;
import com.ezwel.htl.interfaces.service.dto.sddSearch.SddSearchOutDTO;

/**
 * <pre>
 * 인터페이스 서비스
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
@Service
@APIService
public class OutsideInterfaceService {

	private static final Logger logger = LoggerFactory.getLogger(OutsideInterfaceService.class);

	private HttpInterfaceExecutorService inteface = (HttpInterfaceExecutorService) ApplicationContext.getBean(HttpInterfaceExecutorService.class);
	
	@APIOperation(description="전체시설일괄등록 인터페이스")
	public AllRegOutDTO callAllReg() {
		
		AllRegOutDTO out = null;
		
		try {
			
			HttpConfigDTO config = InterfaceFactory.getChannel("chan-01");
			config.setHttpApiSignature(APIUtil.getSecretId(config.getHttpApiKey()));
			/** execute interface */
			out = (AllRegOutDTO) inteface.sendPostJSON(config, AllRegOutDTO.class);
		}
		catch(Exception e) {
			throw new APIException("전체시설일괄등록 인터페이스 장애발생.", e);
		}
		
		return out;
	}
	
	//멀티쓰레드
	@APIOperation(description="시설검색 인터페이스")
	public List<FaclSearchOutDTO> callFaclSearch(FaclSearchInDTO faclSearchDTO) {
			
		List<FaclSearchOutDTO> out = null;
		MultiHttpConfigDTO multi = null;
		List<MultiHttpConfigDTO> multiHttpConfigList = null;
		List<HttpConfigDTO> channelList = null;
		
		try {
			multiHttpConfigList = new ArrayList<MultiHttpConfigDTO>();
			
			channelList = InterfaceFactory.getChannelGroup("chanGroup-01");
			if(channelList != null) {
				for(HttpConfigDTO config : channelList) {
					multi = new MultiHttpConfigDTO();
					config.setHttpApiSignature(APIUtil.getSecretId(config.getHttpApiKey()));
					//config
					multi.setHttpConfigDTO(config);
					//input
					multi.setInputDTO(faclSearchDTO);
					//output
					multi.setOutputType(FaclSearchOutDTO.class);
					multiHttpConfigList.add(multi);
				}
			}
			
			/** execute interface */
			//멀티 쓰레드 인터페이스 실행
			out = inteface.sendMultiPostJSON(multiHttpConfigList);
		}
		catch(Exception e) {
			throw new APIException("시설검색 인터페이스 장애발생.", e);
		}
			
		return out;
	}
	
	//멀티쓰레드
	@APIOperation(description="당일특가검색 인터페이스")
	public List<SddSearchOutDTO> callSddSearch() {
		
		List<SddSearchOutDTO> out = null;
		MultiHttpConfigDTO multi = null;
		List<MultiHttpConfigDTO> multiHttpConfigList = null;
		List<HttpConfigDTO> channelList = null;
		
		try {
			multiHttpConfigList = new ArrayList<MultiHttpConfigDTO>();
			
			channelList = InterfaceFactory.getChannelGroup("chanGroup-02");
			if(channelList != null) {
				for(HttpConfigDTO config : channelList) {
					multi = new MultiHttpConfigDTO();
					config.setHttpApiSignature(APIUtil.getSecretId(config.getHttpApiKey()));
					//no input 
					config.setDoOutput(false);
					//config
					multi.setHttpConfigDTO(config);
					//output
					multi.setOutputType(SddSearchOutDTO.class);
					multiHttpConfigList.add(multi);
				}
			}
			
			/** execute interface */
			//멀티 쓰레드 인터페이스 실행
			out = inteface.sendMultiPostJSON(multiHttpConfigList);
		}
		catch(Exception e) {
			throw new APIException("시설검색 인터페이스 장애발생.", e);
		}
		
		return out;
	}
	
	@APIOperation(description="객실정보조회 인터페이스")
	public RoomReadOutDTO callRoomRead(RoomReadInDTO roomReadDTO) {
		
		RoomReadOutDTO out = null;
		
		try {
			
			HttpConfigDTO config = InterfaceFactory.getChannel("chan-04");
			config.setHttpApiSignature(APIUtil.getSecretId(config.getHttpApiKey()));
			/** execute interface */
			out = (RoomReadOutDTO) inteface.sendPostJSON(config, roomReadDTO, RoomReadOutDTO.class);
		}
		catch(Exception e) {
			throw new APIException("객실정보조회 인터페이스 장애발생.", e);
		}
			
		return out;		
	}
	
	@APIOperation(description="취소수수규정 인터페이스")
	public CancelFeePsrcOutDTO callCancelFeePsrc(CancelFeePsrcInDTO cancelFeePsrcDTO) {
		
		CancelFeePsrcOutDTO out = null;
		
		try {
			
			HttpConfigDTO config = InterfaceFactory.getChannel("chan-05");
			config.setHttpApiSignature(APIUtil.getSecretId(config.getHttpApiKey()));
			/** execute interface */
			out = (CancelFeePsrcOutDTO) inteface.sendPostJSON(config, cancelFeePsrcDTO, CancelFeePsrcOutDTO.class);
		}
		catch(Exception e) {
			throw new APIException("주문대사(제휴사) 인터페이스 장애발생.", e);
		}
		
		return out;		
	}
	
	@APIOperation(description="결재완료내역전송 인터페이스")
	public RsvHistSendOutDTO callRsvHistSend(RsvHistSendInDTO rsvHistSendDTO) {
		
		RsvHistSendOutDTO out = null;
		
		try {
			
			HttpConfigDTO config = InterfaceFactory.getChannel("chan-06");
			config.setHttpApiSignature(APIUtil.getSecretId(config.getHttpApiKey()));
			/** execute interface */
			out = (RsvHistSendOutDTO) inteface.sendPostJSON(config, rsvHistSendDTO, RsvHistSendOutDTO.class);
		}
		catch(Exception e) {
			throw new APIException("결재완료내역전송 인터페이스 장애발생.", e);
		}
		
		return out;
	}
	
	@APIOperation(description="취소수수료계산 인터페이스")
	public CancelFeeAmtOutDTO callCancelFeeAmt(CancelFeeAmtInDTO cancelFeeAmtDTO) {
		
		CancelFeeAmtOutDTO out = null;
		
		try {
			
			HttpConfigDTO config = InterfaceFactory.getChannel("chan-07");
			config.setHttpApiSignature(APIUtil.getSecretId(config.getHttpApiKey()));
			/** execute interface */
			out = (CancelFeeAmtOutDTO) inteface.sendPostJSON(config, cancelFeeAmtDTO, CancelFeeAmtOutDTO.class);
		}
		catch(Exception e) {
			throw new APIException("주문대사(제휴사) 인터페이스 장애발생.", e);
		}
		return out;
	}
	
	@APIOperation(description="주문취소요청 인터페이스")
	public OrderCancelReqOutDTO callOrderCancelReq(OrderCancelReqInDTO orderCancelReqDTO) {
		
		OrderCancelReqOutDTO out = null;
		
		try {
			
			HttpConfigDTO config = InterfaceFactory.getChannel("chan-08");
			config.setHttpApiSignature(APIUtil.getSecretId(config.getHttpApiKey()));
			/** execute interface */
			out = (OrderCancelReqOutDTO) inteface.sendPostJSON(config, orderCancelReqDTO, OrderCancelReqOutDTO.class);
		}
		catch(Exception e) {
			throw new APIException("주문취소요청 인터페이스 장애발생.", e);
		}
		
		return out;
	}

	@APIOperation(description="누락건확인 인터페이스")
	public OmiNumIdnOutDTO callOmiNumIdn(OmiNumIdnInDTO omiNumIdnDTO) {
		
		OmiNumIdnOutDTO out = null;
		
		try {
			
			HttpConfigDTO config = InterfaceFactory.getChannel("chan-09");
			config.setHttpApiSignature(APIUtil.getSecretId(config.getHttpApiKey()));
			/** execute interface */
			out = (OmiNumIdnOutDTO) inteface.sendPostJSON(config, omiNumIdnDTO, OmiNumIdnOutDTO.class);
		}
		catch(Exception e) {
			throw new APIException("누락건확인 인터페이스 장애발생.", e);
		}
		
		return out;
	}

	@APIOperation(description="주문대사(이지웰) 인터페이스")
	public EzwelJobOutDTO callEzwelJob(EzwelJobInDTO ezwelJobDTO) {
		
		EzwelJobOutDTO out = null;
		
		try {
			
			HttpConfigDTO config = InterfaceFactory.getChannel("chan-10");
			config.setHttpApiSignature(APIUtil.getSecretId(config.getHttpApiKey()));
			/** execute interface */
			out = (EzwelJobOutDTO) inteface.sendPostJSON(config, ezwelJobDTO, EzwelJobOutDTO.class);
		}
		catch(Exception e) {
			throw new APIException("주문대사(이지웰) 인터페이스 장애발생.", e);
		}
		
		return out;
	}
	
}
