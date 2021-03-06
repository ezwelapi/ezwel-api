package com.ezwel.htl.interfaces.service;

import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.configure.ConfigureHelper;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.constants.CodeMappingConstants;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.HttpInterfaceExecutor;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigSDO;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.service.data.cancelFeeAmt.CancelFeeAmtInSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeeAmt.CancelFeeAmtOutSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeePsrc.CancelFeePsrcInSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeePsrc.CancelFeePsrcOutSDO;
import com.ezwel.htl.interfaces.service.data.ezwelJob.EzwelJobInSDO;
import com.ezwel.htl.interfaces.service.data.ezwelJob.EzwelJobOutSDO;
import com.ezwel.htl.interfaces.service.data.ezwelJob.EzwelJobReservesOutSDO;
import com.ezwel.htl.interfaces.service.data.mock.MocKUpOutSDO;
import com.ezwel.htl.interfaces.service.data.omiNumIdn.OmiNumIdnInSDO;
import com.ezwel.htl.interfaces.service.data.omiNumIdn.OmiNumIdnOutSDO;
import com.ezwel.htl.interfaces.service.data.omiNumIdn.OmiNumIdnReservesOutSDO;
import com.ezwel.htl.interfaces.service.data.orderCancelReq.OrderCancelReqInSDO;
import com.ezwel.htl.interfaces.service.data.orderCancelReq.OrderCancelReqOutSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadInSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadOutSDO;
import com.ezwel.htl.interfaces.service.data.rsvHistSend.RsvHistSendInSDO;
import com.ezwel.htl.interfaces.service.data.rsvHistSend.RsvHistSendOutSDO;

/**
 * <pre>
 * 인터페이스 서비스
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
@Service
@APIType
public class OutsideIFService {

	private static final Logger logger = LoggerFactory.getLogger(OutsideIFService.class);

	@Autowired /** interface_if는 프론트 및 관리자단에서 ezwel 프레임워크 표준인  Autowired를 사용한다. (interface_if_server는 Autowired보다 빠른 스프링 컨텍스트의 getBean을 사용함) */
	private HttpInterfaceExecutor inteface;
	
	@Autowired /** interface_if는 프론트 및 관리자단에서 ezwel 프레임워크 표준인  Autowired를 사용한다. (interface_if_server는 Autowired보다 빠른 스프링 컨텍스트의 getBean을 사용함) */
	private ConfigureHelper configureHelper; 
	
	@Autowired /** interface_if는 프론트 및 관리자단에서 ezwel 프레임워크 표준인  Autowired를 사용한다. (interface_if_server는 Autowired보다 빠른 스프링 컨텍스트의 getBean을 사용함) */
	private PropertyUtil propertyUtil;
	
	
	/**
	 * 제휴사에서 받은 전문중 코드데이터를 이지웰 코드데이터로 변환함
	 * @param telgCode
	 * @return
	 */
	private String getEzcOutputCode(String telgCode) {
		String out = CodeMappingConstants.TEMP_IF_CODE_MAPPER.get(telgCode);
		return (out != null ? out : telgCode);
	}
	
	/**
	 * 제휴사에서 받은 전문중 코드데이터에 해당하는 명칭을 리턴함
	 * @param telgCode
	 * @return
	 */
	private String getEzcOutputName(String telgCode) {
		String out = CodeMappingConstants.TEMP_IF_NAME_MAPPER.get(telgCode);
		return (out != null ? out : telgCode); 
	}
	
	
	/**
	 * 이지웰코드데이터중 코드데이터를 제휴사용 코드로 변환
	 * @param ezcCode
	 * @return
	 */
	private String getEzcInputCode(String ezcCode) {
		String out = ezcCode;
		for(Entry<String, String> entry : CodeMappingConstants.TEMP_IF_CODE_MAPPER.entrySet()) {
			if(entry.getValue().equals(ezcCode)) {
				out = entry.getKey(); 
				break;
			}
		}
		return out;
	}
	
	public OutsideIFService() {
		
		if(propertyUtil == null) {
			propertyUtil = new PropertyUtil();
		}
		if(inteface == null) {
			inteface = new HttpInterfaceExecutor();
		}
		if(configureHelper == null) {
			configureHelper = new ConfigureHelper();
		}
	}
	
	
	@APIOperation(description="객실정보조회 인터페이스 (외부로직접나감)")
	public RoomReadOutSDO callRoomRead(UserAgentSDO userAgentSDO, RoomReadInSDO roomReadSDO) {
		return callRoomRead(userAgentSDO, roomReadSDO, false);
	}
	
	@APIOperation(description="객실정보조회 인터페이스")
	public RoomReadOutSDO callRoomRead(UserAgentSDO userAgentSDO, RoomReadInSDO roomReadSDO, boolean isEzwelInsideInterface) {
		
		RoomReadOutSDO out = null;
		
		try {
			
			HttpConfigSDO httpConfigSDO = InterfaceFactory.getChannel("roomRead", userAgentSDO.getHttpAgentId());
			if(httpConfigSDO == null) {
				return configureHelper.getChannelNotFoundMessage(RoomReadOutSDO.class);
			}
			configureHelper.setupUserAgentInfo(userAgentSDO, httpConfigSDO, isEzwelInsideInterface);
			
			/** execute interface */
			if(inteface.isHttpConnect(httpConfigSDO)) {
				out = (RoomReadOutSDO) inteface.sendJSON(httpConfigSDO, roomReadSDO, RoomReadOutSDO.class);
			}
			else {
				out = MocKUpOutSDO.getRoomReadOut();
			}
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "객실정보조회 인터페이스 장애발생.", e);
		}
			
		return out;		
	}
	
	@APIOperation(description="취소수수규정 인터페이스 (외부로직접나감)")
	public CancelFeePsrcOutSDO callCancelFeePsrc(UserAgentSDO userAgentSDO, CancelFeePsrcInSDO cancelFeePsrcSDO) {
		return callCancelFeePsrc(userAgentSDO, cancelFeePsrcSDO, false);
	}
	
	@APIOperation(description="취소수수규정 인터페이스")
	public CancelFeePsrcOutSDO callCancelFeePsrc(UserAgentSDO userAgentSDO, CancelFeePsrcInSDO cancelFeePsrcSDO, boolean isEzwelInsideInterface) {
		
		CancelFeePsrcOutSDO out = null;
		
		try {
			
			HttpConfigSDO httpConfigSDO = InterfaceFactory.getChannel("cancelFeePsrc", userAgentSDO.getHttpAgentId());
			if(httpConfigSDO == null) {
				return configureHelper.getChannelNotFoundMessage(CancelFeePsrcOutSDO.class);
			}
			configureHelper.setupUserAgentInfo(userAgentSDO, httpConfigSDO, isEzwelInsideInterface);
			/** execute interface */
			if(inteface.isHttpConnect(httpConfigSDO)) {
				out = (CancelFeePsrcOutSDO) inteface.sendJSON(httpConfigSDO, cancelFeePsrcSDO, CancelFeePsrcOutSDO.class);
			}
			else {
				out = MocKUpOutSDO.getCancelFeePsrcOut();
			}
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "취소수수규정 인터페이스 장애발생.", e);
		}
		
		return out;		
	}
	
	@APIOperation(description="결재완료내역전송 인터페이스 (외부로직접나감)")
	public RsvHistSendOutSDO callRsvHistSend(UserAgentSDO userAgentSDO, RsvHistSendInSDO rsvHistSendSDO) {
		return callRsvHistSend(userAgentSDO, rsvHistSendSDO, false);
	}
	
	@APIOperation(description="결재완료내역전송 인터페이스")
	public RsvHistSendOutSDO callRsvHistSend(UserAgentSDO userAgentSDO, RsvHistSendInSDO rsvHistSendSDO, boolean isEzwelInsideInterface) {
		
		RsvHistSendOutSDO out = null;
		
		//입력 코드 변환
		rsvHistSendSDO.getData().setRsvStat(getEzcInputCode(rsvHistSendSDO.getData().getRsvStat()));

		try {
			
			HttpConfigSDO httpConfigSDO = InterfaceFactory.getChannel("rsvHistSend", userAgentSDO.getHttpAgentId());
			if(httpConfigSDO == null) {
				return configureHelper.getChannelNotFoundMessage(RsvHistSendOutSDO.class);
			}
			configureHelper.setupUserAgentInfo(userAgentSDO, httpConfigSDO, isEzwelInsideInterface);
			/** execute interface */
			if(inteface.isHttpConnect(httpConfigSDO)) {
				out = (RsvHistSendOutSDO) inteface.sendJSON(httpConfigSDO, rsvHistSendSDO, RsvHistSendOutSDO.class);
			}
			else {
				out = MocKUpOutSDO.getRsvHistSendOut();
			}
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "결재완료내역전송 인터페이스 장애발생.", e);
		}
		
		return out;
	}

	
	@APIOperation(description="취소수수료계산 인터페이스 (외부로직접나감)")
	public CancelFeeAmtOutSDO callCancelFeeAmt(UserAgentSDO userAgentSDO, CancelFeeAmtInSDO cancelFeeAmtSDO) {
		return callCancelFeeAmt(userAgentSDO, cancelFeeAmtSDO, false);
	}
	
	@APIOperation(description="취소수수료계산 인터페이스")
	public CancelFeeAmtOutSDO callCancelFeeAmt(UserAgentSDO userAgentSDO, CancelFeeAmtInSDO cancelFeeAmtSDO, boolean isEzwelInsideInterface) {
		
		CancelFeeAmtOutSDO out = null;
		
		try {
			
			HttpConfigSDO httpConfigSDO = InterfaceFactory.getChannel("cancelFeeAmt", userAgentSDO.getHttpAgentId());
			if(httpConfigSDO == null) {
				return configureHelper.getChannelNotFoundMessage(CancelFeeAmtOutSDO.class);
			}
			configureHelper.setupUserAgentInfo(userAgentSDO, httpConfigSDO, isEzwelInsideInterface);
			/** execute interface */
			if(inteface.isHttpConnect(httpConfigSDO)) {
				out = (CancelFeeAmtOutSDO) inteface.sendJSON(httpConfigSDO, cancelFeeAmtSDO, CancelFeeAmtOutSDO.class);
			}
			else {
				out = MocKUpOutSDO.getCancelFeeAmtOut();
			}
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "취소수수료계산 인터페이스 장애발생.", e);
		}
		return out;
	}
	
	
	@APIOperation(description="주문취소요청 인터페이스 (외부로직접나감)")
	public OrderCancelReqOutSDO callOrderCancelReq(UserAgentSDO userAgentSDO, OrderCancelReqInSDO orderCancelReqSDO) {
		return callOrderCancelReq(userAgentSDO, orderCancelReqSDO, false);
	}
	
	@APIOperation(description="주문취소요청 인터페이스")
	public OrderCancelReqOutSDO callOrderCancelReq(UserAgentSDO userAgentSDO, OrderCancelReqInSDO orderCancelReqSDO, boolean isEzwelInsideInterface) {
		
		OrderCancelReqOutSDO out = null;
		
		try {
			
			HttpConfigSDO httpConfigSDO = InterfaceFactory.getChannel("orderCancelReq", userAgentSDO.getHttpAgentId());
			if(httpConfigSDO == null) {
				return configureHelper.getChannelNotFoundMessage(OrderCancelReqOutSDO.class);
			}
			configureHelper.setupUserAgentInfo(userAgentSDO, httpConfigSDO, isEzwelInsideInterface);
			/** execute interface */
			if(inteface.isHttpConnect(httpConfigSDO)) {
				out = (OrderCancelReqOutSDO) inteface.sendJSON(httpConfigSDO, orderCancelReqSDO, OrderCancelReqOutSDO.class);
			}
			else {
				out = MocKUpOutSDO.getOrderCancelReqOut();
			}
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "주문취소요청 인터페이스 장애발생.", e);
		}
		
		return out;
	}

	
	@APIOperation(description="누락건확인 인터페이스 (외부로 직접나감)")
	public OmiNumIdnOutSDO callOmiNumIdn(UserAgentSDO userAgentSDO, OmiNumIdnInSDO omiNumIdnSDO) {
		return callOmiNumIdn(userAgentSDO, omiNumIdnSDO, false);
	}
	
	@APIOperation(description="누락건확인 인터페이스")
	public OmiNumIdnOutSDO callOmiNumIdn(UserAgentSDO userAgentSDO, OmiNumIdnInSDO omiNumIdnSDO, boolean isEzwelInsideInterface) {
		
		OmiNumIdnOutSDO out = null;
		
		try {
			
			//입력 코드 변환
			//이지웰코드를 제휴사 코드로 변환 예제.
			omiNumIdnSDO.setRsvStat(getEzcInputCode(omiNumIdnSDO.getRsvStat()));
			
			HttpConfigSDO httpConfigSDO = InterfaceFactory.getChannel("omiNumIdn", userAgentSDO.getHttpAgentId());
			if(httpConfigSDO == null) {
				return configureHelper.getChannelNotFoundMessage(OmiNumIdnOutSDO.class);
			}
			configureHelper.setupUserAgentInfo(userAgentSDO, httpConfigSDO, isEzwelInsideInterface);
			/** execute interface */
			if(inteface.isHttpConnect(httpConfigSDO)) {
				out = (OmiNumIdnOutSDO) inteface.sendJSON(httpConfigSDO, omiNumIdnSDO, OmiNumIdnOutSDO.class);
			}
			else {
				out = MocKUpOutSDO.getOmiNumIdnOut();
			}
			
			//전문코드를 이지웰코드로 변환 예제.
			OmiNumIdnReservesOutSDO data = out.getReserves();
			//코드 데이터 만큼 아래의 set/getEzcCode(전문코드) 를 실행한다.
			String rsvStat = data.getRsvStat();
			data.setRsvStat(getEzcOutputCode(rsvStat));
			data.setRsvStatName(getEzcOutputName(rsvStat));
			//주문상태비교결과코드
			String compareStat = data.getCompareStat();
			data.setCompareStat(getEzcOutputCode(compareStat));
			data.setCompareStatName(getEzcOutputName(compareStat));
			
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "누락건확인 인터페이스 장애발생.", e);
		}
		
		return out;
	}

	@APIOperation(description="주문대사(이지웰) 인터페이스 (외부로직접나감)")
	public EzwelJobOutSDO callEzwelJob(UserAgentSDO userAgentSDO, EzwelJobInSDO ezwelJobSDO) {
		return callEzwelJob(userAgentSDO, ezwelJobSDO, false);
	}
	
	@APIOperation(description="주문대사(이지웰) 인터페이스")
	public EzwelJobOutSDO callEzwelJob(UserAgentSDO userAgentSDO, EzwelJobInSDO ezwelJobSDO, boolean isEzwelInsideInterface) {
		
		EzwelJobOutSDO out = null;
		
		try {
			
			HttpConfigSDO httpConfigSDO = InterfaceFactory.getChannel("ezwelJob", userAgentSDO.getHttpAgentId());
			if(httpConfigSDO == null) {
				return configureHelper.getChannelNotFoundMessage(EzwelJobOutSDO.class);
			}
			configureHelper.setupUserAgentInfo(userAgentSDO, httpConfigSDO, isEzwelInsideInterface);
			/** execute interface */
			if(inteface.isHttpConnect(httpConfigSDO)) {
				out = (EzwelJobOutSDO) inteface.sendJSON(httpConfigSDO, ezwelJobSDO, EzwelJobOutSDO.class);
			}
			else {
				out = MocKUpOutSDO.getEzwelJobOut();
			}
			
			//전문코드를 이지웰코드로 변환 예제.(목록)
			String rsvStat = null;
			if(out.getReserves() != null) {
				
				for(EzwelJobReservesOutSDO data : out.getReserves()) {
					rsvStat = data.getRsvStat();
					logger.debug("##rsvStat : {}", rsvStat);
					//코드 데이터 만큼 아래의 set/getEzcCode(전문코드) 를 실행한다.
					data.setRsvStat(getEzcOutputCode(rsvStat));	//cd
					data.setRsvStatName(getEzcOutputName(rsvStat)); //nm
				}
			}
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "주문대사(이지웰) 인터페이스 장애발생.", e);
		}
		
		logger.debug("#주문대사(이지웰) : {}", out);
		
		return out;
	}



}
