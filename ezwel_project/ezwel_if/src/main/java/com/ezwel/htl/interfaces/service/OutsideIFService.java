package com.ezwel.htl.interfaces.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.configure.ConfigureHelper;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
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
import com.ezwel.htl.interfaces.service.data.mock.MocKUpOutSDO;
import com.ezwel.htl.interfaces.service.data.omiNumIdn.OmiNumIdnInSDO;
import com.ezwel.htl.interfaces.service.data.omiNumIdn.OmiNumIdnOutSDO;
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

	@Autowired
	private HttpInterfaceExecutor inteface;
	
	@Autowired
	private ConfigureHelper configureHelper; 
	
	@Autowired
	private PropertyUtil propertyUtil;
	
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
			httpConfigSDO.setEzwelInsideInterface(isEzwelInsideInterface);
			configureHelper.setupUserAgentInfo(userAgentSDO, httpConfigSDO);
			
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
			httpConfigSDO.setEzwelInsideInterface(isEzwelInsideInterface);
			configureHelper.setupUserAgentInfo(userAgentSDO, httpConfigSDO);
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
		
		try {
			
			HttpConfigSDO httpConfigSDO = InterfaceFactory.getChannel("rsvHistSend", userAgentSDO.getHttpAgentId());
			httpConfigSDO.setEzwelInsideInterface(isEzwelInsideInterface);
			configureHelper.setupUserAgentInfo(userAgentSDO, httpConfigSDO);
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
			httpConfigSDO.setEzwelInsideInterface(isEzwelInsideInterface);
			configureHelper.setupUserAgentInfo(userAgentSDO, httpConfigSDO);
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
			httpConfigSDO.setEzwelInsideInterface(isEzwelInsideInterface);
			configureHelper.setupUserAgentInfo(userAgentSDO, httpConfigSDO);
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
			
			HttpConfigSDO httpConfigSDO = InterfaceFactory.getChannel("omiNumIdn", userAgentSDO.getHttpAgentId());
			httpConfigSDO.setEzwelInsideInterface(isEzwelInsideInterface);
			configureHelper.setupUserAgentInfo(userAgentSDO, httpConfigSDO);
			/** execute interface */
			if(inteface.isHttpConnect(httpConfigSDO)) {
				out = (OmiNumIdnOutSDO) inteface.sendJSON(httpConfigSDO, omiNumIdnSDO, OmiNumIdnOutSDO.class);
			}
			else {
				out = MocKUpOutSDO.getOmiNumIdnOut();
			}
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
			httpConfigSDO.setEzwelInsideInterface(isEzwelInsideInterface);
			configureHelper.setupUserAgentInfo(userAgentSDO, httpConfigSDO);
			/** execute interface */
			if(inteface.isHttpConnect(httpConfigSDO)) {
				out = (EzwelJobOutSDO) inteface.sendJSON(httpConfigSDO, ezwelJobSDO, EzwelJobOutSDO.class);
			}
			else {
				out = MocKUpOutSDO.getEzwelJobOut();
			}
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "주문대사(이지웰) 인터페이스 장애발생.", e);
		}
		
		return out;
	}
	

}
