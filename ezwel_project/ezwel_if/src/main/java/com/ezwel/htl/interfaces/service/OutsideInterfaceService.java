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
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.HttpInterfaceExecutorService;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigDTO;
import com.ezwel.htl.interfaces.commons.http.data.MultiHttpConfigDTO;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentDTO;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.service.data.allReg.AllRegOutSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeeAmt.CancelFeeAmtInSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeeAmt.CancelFeeAmtOutSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeePsrc.CancelFeePsrcInSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeePsrc.CancelFeePsrcOutSDO;
import com.ezwel.htl.interfaces.service.data.ezwelJob.EzwelJobInSDO;
import com.ezwel.htl.interfaces.service.data.ezwelJob.EzwelJobOutSDO;
import com.ezwel.htl.interfaces.service.data.faclSearch.FaclSearchInSDO;
import com.ezwel.htl.interfaces.service.data.faclSearch.FaclSearchOutSDO;
import com.ezwel.htl.interfaces.service.data.omiNumIdn.OmiNumIdnInSDO;
import com.ezwel.htl.interfaces.service.data.omiNumIdn.OmiNumIdnOutSDO;
import com.ezwel.htl.interfaces.service.data.orderCancelReq.OrderCancelReqInSDO;
import com.ezwel.htl.interfaces.service.data.orderCancelReq.OrderCancelReqOutSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadInSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadOutSDO;
import com.ezwel.htl.interfaces.service.data.rsvHistSend.RsvHistSendInSDO;
import com.ezwel.htl.interfaces.service.data.rsvHistSend.RsvHistSendOutSDO;
import com.ezwel.htl.interfaces.service.data.sddSearch.SddSearchOutSDO;

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
	public AllRegOutSDO callAllReg(UserAgentDTO agentInfoDTO) {
		
		AllRegOutSDO out = null;
		
		try {
			HttpConfigDTO httpConfigDTO = InterfaceFactory.getChannel("allReg", agentInfoDTO.getHttpAgentId());
			setupUserAgentInfo(httpConfigDTO, agentInfoDTO);
			/** execute interface */
			out = (AllRegOutSDO) inteface.sendPostJSON(httpConfigDTO, AllRegOutSDO.class);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "전체시설일괄등록 인터페이스 장애발생.", e);
		}
		
		return out;
	}
	
	//멀티쓰레드
	@APIOperation(description="시설검색 인터페이스")
	public List<FaclSearchOutSDO> callFaclSearch(UserAgentDTO agentInfoDTO, FaclSearchInSDO faclSearchDTO) {
			
		List<FaclSearchOutSDO> out = null;
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
					multi.setOutputType(FaclSearchOutSDO.class);
					multiHttpConfigList.add(multi);
				}
			}
			
			/** execute interface */
			//멀티 쓰레드 인터페이스 실행
			out = inteface.sendMultiPostJSON(multiHttpConfigList);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "시설검색 인터페이스 장애발생.", e);
		}
			
		return out;
	}
	
	//멀티쓰레드
	@APIOperation(description="당일특가검색 인터페이스")
	public List<SddSearchOutSDO> callSddSearch(UserAgentDTO agentInfoDTO) {
		
		List<SddSearchOutSDO> out = null;
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
					multi.setOutputType(SddSearchOutSDO.class);
					multiHttpConfigList.add(multi);
				}
			}
			
			/** execute interface */
			//멀티 쓰레드 인터페이스 실행
			out = inteface.sendMultiPostJSON(multiHttpConfigList);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "시설검색 인터페이스 장애발생.", e);
		}
		
		return out;
	}
	
	@APIOperation(description="객실정보조회 인터페이스")
	public RoomReadOutSDO callRoomRead(UserAgentDTO agentInfoDTO, RoomReadInSDO roomReadDTO) {
		
		RoomReadOutSDO out = null;
		
		try {
			
			HttpConfigDTO httpConfigDTO = InterfaceFactory.getChannel("roomRead", agentInfoDTO.getHttpAgentId());
			setupUserAgentInfo(httpConfigDTO, agentInfoDTO);
			/** execute interface */
			out = (RoomReadOutSDO) inteface.sendPostJSON(httpConfigDTO, roomReadDTO, RoomReadOutSDO.class);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "객실정보조회 인터페이스 장애발생.", e);
		}
			
		return out;		
	}
	
	@APIOperation(description="취소수수규정 인터페이스")
	public CancelFeePsrcOutSDO callCancelFeePsrc(UserAgentDTO agentInfoDTO, CancelFeePsrcInSDO cancelFeePsrcDTO) {
		
		CancelFeePsrcOutSDO out = null;
		
		try {
			
			HttpConfigDTO httpConfigDTO = InterfaceFactory.getChannel("cancelFeePsrc", agentInfoDTO.getHttpAgentId());
			setupUserAgentInfo(httpConfigDTO, agentInfoDTO);
			/** execute interface */
			out = (CancelFeePsrcOutSDO) inteface.sendPostJSON(httpConfigDTO, cancelFeePsrcDTO, CancelFeePsrcOutSDO.class);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "주문대사(제휴사) 인터페이스 장애발생.", e);
		}
		
		return out;		
	}
	
	@APIOperation(description="결재완료내역전송 인터페이스")
	public RsvHistSendOutSDO callRsvHistSend(UserAgentDTO agentInfoDTO, RsvHistSendInSDO rsvHistSendDTO) {
		
		RsvHistSendOutSDO out = null;
		
		try {
			
			HttpConfigDTO httpConfigDTO = InterfaceFactory.getChannel("rsvHistSend", agentInfoDTO.getHttpAgentId());
			setupUserAgentInfo(httpConfigDTO, agentInfoDTO);
			/** execute interface */
			out = (RsvHistSendOutSDO) inteface.sendPostJSON(httpConfigDTO, rsvHistSendDTO, RsvHistSendOutSDO.class);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "결재완료내역전송 인터페이스 장애발생.", e);
		}
		
		return out;
	}
	
	@APIOperation(description="취소수수료계산 인터페이스")
	public CancelFeeAmtOutSDO callCancelFeeAmt(UserAgentDTO agentInfoDTO, CancelFeeAmtInSDO cancelFeeAmtDTO) {
		
		CancelFeeAmtOutSDO out = null;
		
		try {
			
			HttpConfigDTO httpConfigDTO = InterfaceFactory.getChannel("cancelFeeAmt", agentInfoDTO.getHttpAgentId());
			setupUserAgentInfo(httpConfigDTO, agentInfoDTO);
			/** execute interface */
			out = (CancelFeeAmtOutSDO) inteface.sendPostJSON(httpConfigDTO, cancelFeeAmtDTO, CancelFeeAmtOutSDO.class);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "주문대사(제휴사) 인터페이스 장애발생.", e);
		}
		return out;
	}
	
	@APIOperation(description="주문취소요청 인터페이스")
	public OrderCancelReqOutSDO callOrderCancelReq(UserAgentDTO agentInfoDTO, OrderCancelReqInSDO orderCancelReqDTO) {
		
		OrderCancelReqOutSDO out = null;
		
		try {
			
			HttpConfigDTO httpConfigDTO = InterfaceFactory.getChannel("orderCancelReq", agentInfoDTO.getHttpAgentId());
			setupUserAgentInfo(httpConfigDTO, agentInfoDTO);
			/** execute interface */
			out = (OrderCancelReqOutSDO) inteface.sendPostJSON(httpConfigDTO, orderCancelReqDTO, OrderCancelReqOutSDO.class);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "주문취소요청 인터페이스 장애발생.", e);
		}
		
		return out;
	}

	@APIOperation(description="누락건확인 인터페이스")
	public OmiNumIdnOutSDO callOmiNumIdn(UserAgentDTO agentInfoDTO, OmiNumIdnInSDO omiNumIdnDTO) {
		
		OmiNumIdnOutSDO out = null;
		
		try {
			
			HttpConfigDTO httpConfigDTO = InterfaceFactory.getChannel("omiNumIdn", agentInfoDTO.getHttpAgentId());
			setupUserAgentInfo(httpConfigDTO, agentInfoDTO);
			/** execute interface */
			out = (OmiNumIdnOutSDO) inteface.sendPostJSON(httpConfigDTO, omiNumIdnDTO, OmiNumIdnOutSDO.class);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "누락건확인 인터페이스 장애발생.", e);
		}
		
		return out;
	}

	@APIOperation(description="주문대사(이지웰) 인터페이스")
	public EzwelJobOutSDO callEzwelJob(UserAgentDTO agentInfoDTO, EzwelJobInSDO ezwelJobDTO) {
		
		EzwelJobOutSDO out = null;
		
		try {
			
			HttpConfigDTO httpConfigDTO = InterfaceFactory.getChannel("ezwelJob", agentInfoDTO.getHttpAgentId());
			setupUserAgentInfo(httpConfigDTO, agentInfoDTO);
			/** execute interface */
			out = (EzwelJobOutSDO) inteface.sendPostJSON(httpConfigDTO, ezwelJobDTO, EzwelJobOutSDO.class);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "주문대사(이지웰) 인터페이스 장애발생.", e);
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
