package com.ezwel.htl.interfaces.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.constants.MessageCode;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.HttpInterfaceExecutorService;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigDTO;
import com.ezwel.htl.interfaces.commons.http.data.MultiHttpConfigDTO;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentDTO;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.service.data.allReg.AllRegOutDTO;
import com.ezwel.htl.interfaces.service.data.cancelFeeAmt.CancelFeeAmtInDTO;
import com.ezwel.htl.interfaces.service.data.cancelFeeAmt.CancelFeeAmtOutDTO;
import com.ezwel.htl.interfaces.service.data.cancelFeePsrc.CancelFeePsrcInDTO;
import com.ezwel.htl.interfaces.service.data.cancelFeePsrc.CancelFeePsrcOutDTO;
import com.ezwel.htl.interfaces.service.data.ezwelJob.EzwelJobInDTO;
import com.ezwel.htl.interfaces.service.data.ezwelJob.EzwelJobOutDTO;
import com.ezwel.htl.interfaces.service.data.faclSearch.FaclSearchInDTO;
import com.ezwel.htl.interfaces.service.data.faclSearch.FaclSearchOutDTO;
import com.ezwel.htl.interfaces.service.data.omiNumIdn.OmiNumIdnInDTO;
import com.ezwel.htl.interfaces.service.data.omiNumIdn.OmiNumIdnOutDTO;
import com.ezwel.htl.interfaces.service.data.orderCancelReq.OrderCancelReqInDTO;
import com.ezwel.htl.interfaces.service.data.orderCancelReq.OrderCancelReqOutDTO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadInDTO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadOutDTO;
import com.ezwel.htl.interfaces.service.data.rsvHistSend.RsvHistSendInDTO;
import com.ezwel.htl.interfaces.service.data.rsvHistSend.RsvHistSendOutDTO;
import com.ezwel.htl.interfaces.service.data.sddSearch.SddSearchOutDTO;

/**
 * <pre>
 * 인터페이스 서비스
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
@Service
@APIType
public class OutsideInterfaceService {

	private static final Logger logger = LoggerFactory.getLogger(OutsideInterfaceService.class);

	@Autowired
	private HttpInterfaceExecutorService inteface;
	
	private PropertyUtil propertyUtil;
	
	public OutsideInterfaceService() {
		
		if(propertyUtil == null) {
			propertyUtil = new PropertyUtil();
		}
		if(inteface == null) {
			inteface = new HttpInterfaceExecutorService();
		}
	}
	
	@APIOperation(description="전체시설일괄등록 인터페이스")
	public AllRegOutDTO callAllReg(UserAgentDTO agentInfoDTO) {
		
		AllRegOutDTO out = null;
		
		try {
			HttpConfigDTO httpConfigDTO = InterfaceFactory.getChannel("allReg", agentInfoDTO.getHttpAgentId());
			setupUserAgentInfo(httpConfigDTO, agentInfoDTO);
			/** execute interface */
			out = (AllRegOutDTO) inteface.sendPostJSON(httpConfigDTO, AllRegOutDTO.class);
		}
		catch(Exception e) {
			throw new APIException(MessageCode.RESPONSE_CODE_9100, "전체시설일괄등록 인터페이스 장애발생.", e);
		}
		
		return out;
	}
	
	//멀티쓰레드
	@APIOperation(description="시설검색 인터페이스")
	public List<FaclSearchOutDTO> callFaclSearch(UserAgentDTO agentInfoDTO, FaclSearchInDTO faclSearchDTO) {
			
		List<FaclSearchOutDTO> out = null;
		MultiHttpConfigDTO multi = null;
		List<HttpConfigDTO> channelList = null;
		List<MultiHttpConfigDTO> multiHttpConfigList = null;
		
		try {
			multiHttpConfigList = new ArrayList<MultiHttpConfigDTO>();
			
			channelList = InterfaceFactory.getChannelGroup("faclSearch", agentInfoDTO.getHttpAgentGroupId());
			if(channelList != null) {
				for(HttpConfigDTO httpConfigDTO : channelList) {
					multi = new MultiHttpConfigDTO();
					setupUserAgentInfo(httpConfigDTO, agentInfoDTO);
					//config
					multi.setHttpConfigDTO(httpConfigDTO);
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
			throw new APIException(MessageCode.RESPONSE_CODE_9100, "시설검색 인터페이스 장애발생.", e);
		}
			
		return out;
	}
	
	//멀티쓰레드
	@APIOperation(description="당일특가검색 인터페이스")
	public List<SddSearchOutDTO> callSddSearch(UserAgentDTO agentInfoDTO) {
		
		List<SddSearchOutDTO> out = null;
		MultiHttpConfigDTO multi = null;
		List<MultiHttpConfigDTO> multiHttpConfigList = null;
		List<HttpConfigDTO> channelList = null;
		
		try {
			multiHttpConfigList = new ArrayList<MultiHttpConfigDTO>();
			
			channelList = InterfaceFactory.getChannelGroup("sddSearch", agentInfoDTO.getHttpAgentGroupId());
			if(channelList != null) {
				for(HttpConfigDTO httpConfigDTO : channelList) {
					multi = new MultiHttpConfigDTO();
					setupUserAgentInfo(httpConfigDTO, agentInfoDTO);
					//no input 
					httpConfigDTO.setDoOutput(false);
					//config
					multi.setHttpConfigDTO(httpConfigDTO);
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
			throw new APIException(MessageCode.RESPONSE_CODE_9100, "시설검색 인터페이스 장애발생.", e);
		}
		
		return out;
	}
	
	@APIOperation(description="객실정보조회 인터페이스")
	public RoomReadOutDTO callRoomRead(UserAgentDTO agentInfoDTO, RoomReadInDTO roomReadDTO) {
		
		RoomReadOutDTO out = null;
		
		try {
			
			HttpConfigDTO httpConfigDTO = InterfaceFactory.getChannel("roomRead", agentInfoDTO.getHttpAgentId());
			setupUserAgentInfo(httpConfigDTO, agentInfoDTO);
			/** execute interface */
			out = (RoomReadOutDTO) inteface.sendPostJSON(httpConfigDTO, roomReadDTO, RoomReadOutDTO.class);
		}
		catch(Exception e) {
			throw new APIException(MessageCode.RESPONSE_CODE_9100, "객실정보조회 인터페이스 장애발생.", e);
		}
			
		return out;		
	}
	
	@APIOperation(description="취소수수규정 인터페이스")
	public CancelFeePsrcOutDTO callCancelFeePsrc(UserAgentDTO agentInfoDTO, CancelFeePsrcInDTO cancelFeePsrcDTO) {
		
		CancelFeePsrcOutDTO out = null;
		
		try {
			
			HttpConfigDTO httpConfigDTO = InterfaceFactory.getChannel("cancelFeePsrc", agentInfoDTO.getHttpAgentId());
			setupUserAgentInfo(httpConfigDTO, agentInfoDTO);
			/** execute interface */
			out = (CancelFeePsrcOutDTO) inteface.sendPostJSON(httpConfigDTO, cancelFeePsrcDTO, CancelFeePsrcOutDTO.class);
		}
		catch(Exception e) {
			throw new APIException(MessageCode.RESPONSE_CODE_9100, "주문대사(제휴사) 인터페이스 장애발생.", e);
		}
		
		return out;		
	}
	
	@APIOperation(description="결재완료내역전송 인터페이스")
	public RsvHistSendOutDTO callRsvHistSend(UserAgentDTO agentInfoDTO, RsvHistSendInDTO rsvHistSendDTO) {
		
		RsvHistSendOutDTO out = null;
		
		try {
			
			HttpConfigDTO httpConfigDTO = InterfaceFactory.getChannel("rsvHistSend", agentInfoDTO.getHttpAgentId());
			setupUserAgentInfo(httpConfigDTO, agentInfoDTO);
			/** execute interface */
			out = (RsvHistSendOutDTO) inteface.sendPostJSON(httpConfigDTO, rsvHistSendDTO, RsvHistSendOutDTO.class);
		}
		catch(Exception e) {
			throw new APIException(MessageCode.RESPONSE_CODE_9100, "결재완료내역전송 인터페이스 장애발생.", e);
		}
		
		return out;
	}
	
	@APIOperation(description="취소수수료계산 인터페이스")
	public CancelFeeAmtOutDTO callCancelFeeAmt(UserAgentDTO agentInfoDTO, CancelFeeAmtInDTO cancelFeeAmtDTO) {
		
		CancelFeeAmtOutDTO out = null;
		
		try {
			
			HttpConfigDTO httpConfigDTO = InterfaceFactory.getChannel("cancelFeeAmt", agentInfoDTO.getHttpAgentId());
			setupUserAgentInfo(httpConfigDTO, agentInfoDTO);
			/** execute interface */
			out = (CancelFeeAmtOutDTO) inteface.sendPostJSON(httpConfigDTO, cancelFeeAmtDTO, CancelFeeAmtOutDTO.class);
		}
		catch(Exception e) {
			throw new APIException(MessageCode.RESPONSE_CODE_9100, "주문대사(제휴사) 인터페이스 장애발생.", e);
		}
		return out;
	}
	
	@APIOperation(description="주문취소요청 인터페이스")
	public OrderCancelReqOutDTO callOrderCancelReq(UserAgentDTO agentInfoDTO, OrderCancelReqInDTO orderCancelReqDTO) {
		
		OrderCancelReqOutDTO out = null;
		
		try {
			
			HttpConfigDTO httpConfigDTO = InterfaceFactory.getChannel("orderCancelReq", agentInfoDTO.getHttpAgentId());
			setupUserAgentInfo(httpConfigDTO, agentInfoDTO);
			/** execute interface */
			out = (OrderCancelReqOutDTO) inteface.sendPostJSON(httpConfigDTO, orderCancelReqDTO, OrderCancelReqOutDTO.class);
		}
		catch(Exception e) {
			throw new APIException(MessageCode.RESPONSE_CODE_9100, "주문취소요청 인터페이스 장애발생.", e);
		}
		
		return out;
	}

	@APIOperation(description="누락건확인 인터페이스")
	public OmiNumIdnOutDTO callOmiNumIdn(UserAgentDTO agentInfoDTO, OmiNumIdnInDTO omiNumIdnDTO) {
		
		OmiNumIdnOutDTO out = null;
		
		try {
			
			HttpConfigDTO httpConfigDTO = InterfaceFactory.getChannel("omiNumIdn", agentInfoDTO.getHttpAgentId());
			setupUserAgentInfo(httpConfigDTO, agentInfoDTO);
			/** execute interface */
			out = (OmiNumIdnOutDTO) inteface.sendPostJSON(httpConfigDTO, omiNumIdnDTO, OmiNumIdnOutDTO.class);
		}
		catch(Exception e) {
			throw new APIException(MessageCode.RESPONSE_CODE_9100, "누락건확인 인터페이스 장애발생.", e);
		}
		
		return out;
	}

	@APIOperation(description="주문대사(이지웰) 인터페이스")
	public EzwelJobOutDTO callEzwelJob(UserAgentDTO agentInfoDTO, EzwelJobInDTO ezwelJobDTO) {
		
		EzwelJobOutDTO out = null;
		
		try {
			
			HttpConfigDTO httpConfigDTO = InterfaceFactory.getChannel("ezwelJob", agentInfoDTO.getHttpAgentId());
			setupUserAgentInfo(httpConfigDTO, agentInfoDTO);
			/** execute interface */
			out = (EzwelJobOutDTO) inteface.sendPostJSON(httpConfigDTO, ezwelJobDTO, EzwelJobOutDTO.class);
		}
		catch(Exception e) {
			throw new APIException(MessageCode.RESPONSE_CODE_9100, "주문대사(이지웰) 인터페이스 장애발생.", e);
		}
		
		return out;
	}
	
	@APIOperation(description="인터페이스 사용 유저 설정 정보 세팅")
	private void setupUserAgentInfo(HttpConfigDTO config, UserAgentDTO userAgentDTO) {
		/** conntime, readtime, httpAgentType, httpChannelCd, httpClientId, httpRequestId  */
		propertyUtil.copySameProperty(userAgentDTO, config);
		/** setup httpApiSignature */
		config.setHttpApiSignature(APIUtil.getHttpSignature(config.getHttpAgentId(), config.getHttpApiKey(), config.getHttpApiTimestamp()));
	}
}
