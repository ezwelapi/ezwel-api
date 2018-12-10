package com.ezwel.htl.interfaces.adapter;

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
import com.ezwel.htl.interfaces.commons.http.HttpInterfaceExecutorService;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigSDO;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.service.data.cancelFeeAmt.CancelFeeAmtInSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeeAmt.CancelFeeAmtOutSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeePsrc.CancelFeePsrcInSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeePsrc.CancelFeePsrcOutSDO;
import com.ezwel.htl.interfaces.service.data.ezwelJob.EzwelJobInSDO;
import com.ezwel.htl.interfaces.service.data.ezwelJob.EzwelJobOutSDO;
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
public class OutsideIFAdapter {

	private static final Logger logger = LoggerFactory.getLogger(OutsideIFAdapter.class);

	@Autowired
	private HttpInterfaceExecutorService inteface;
	
	@Autowired
	private ConfigureHelper configureHelper; 
	
	@Autowired
	private PropertyUtil propertyUtil;
	
	
	public OutsideIFAdapter() {
		
		if(propertyUtil == null) {
			propertyUtil = new PropertyUtil();
		}
		if(inteface == null) {
			inteface = new HttpInterfaceExecutorService();
		}
		if(configureHelper == null) {
			configureHelper = new ConfigureHelper();
		}
	}
	
	@APIOperation(description="객실정보조회 인터페이스")
	public RoomReadOutSDO callRoomRead(UserAgentSDO userAgentSDO, RoomReadInSDO roomReadSDO) {
		
		RoomReadOutSDO out = null;
		
		try {
			
			HttpConfigSDO httpConfigSDO = InterfaceFactory.getChannel("roomRead", userAgentSDO.getHttpAgentId());
			httpConfigSDO.setEzwelInsideInterface(true);
			configureHelper.setupUserAgentInfo(userAgentSDO, httpConfigSDO);
			
			/** execute interface */
			out = (RoomReadOutSDO) inteface.sendJSON(httpConfigSDO, roomReadSDO, RoomReadOutSDO.class);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "객실정보조회 인터페이스 장애발생.", e);
		}
			
		return out;		
	}
	
	@APIOperation(description="취소수수규정 인터페이스")
	public CancelFeePsrcOutSDO callCancelFeePsrc(UserAgentSDO userAgentSDO, CancelFeePsrcInSDO cancelFeePsrcSDO) {
		
		CancelFeePsrcOutSDO out = null;
		
		try {
			
			HttpConfigSDO httpConfigSDO = InterfaceFactory.getChannel("cancelFeePsrc", userAgentSDO.getHttpAgentId());
			httpConfigSDO.setEzwelInsideInterface(true);
			configureHelper.setupUserAgentInfo(userAgentSDO, httpConfigSDO);
			
			/** execute interface */
			out = (CancelFeePsrcOutSDO) inteface.sendJSON(httpConfigSDO, cancelFeePsrcSDO, CancelFeePsrcOutSDO.class);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "주문대사(제휴사) 인터페이스 장애발생.", e);
		}
		
		return out;		
	}
	
	@APIOperation(description="결재완료내역전송 인터페이스")
	public RsvHistSendOutSDO callRsvHistSend(UserAgentSDO userAgentSDO, RsvHistSendInSDO rsvHistSendSDO) {
		
		RsvHistSendOutSDO out = null;
		
		try {
			
			HttpConfigSDO httpConfigSDO = InterfaceFactory.getChannel("rsvHistSend", userAgentSDO.getHttpAgentId());
			httpConfigSDO.setEzwelInsideInterface(true);
			configureHelper.setupUserAgentInfo(userAgentSDO, httpConfigSDO);
			
			/** execute interface */
			out = (RsvHistSendOutSDO) inteface.sendJSON(httpConfigSDO, rsvHistSendSDO, RsvHistSendOutSDO.class);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "결재완료내역전송 인터페이스 장애발생.", e);
		}
		
		return out;
	}
	
	@APIOperation(description="취소수수료계산 인터페이스")
	public CancelFeeAmtOutSDO callCancelFeeAmt(UserAgentSDO userAgentSDO, CancelFeeAmtInSDO cancelFeeAmtSDO) {
		
		CancelFeeAmtOutSDO out = null;
		
		try {
			
			HttpConfigSDO httpConfigSDO = InterfaceFactory.getChannel("cancelFeeAmt", userAgentSDO.getHttpAgentId());
			httpConfigSDO.setEzwelInsideInterface(true);
			configureHelper.setupUserAgentInfo(userAgentSDO, httpConfigSDO);
			
			/** execute interface */
			out = (CancelFeeAmtOutSDO) inteface.sendJSON(httpConfigSDO, cancelFeeAmtSDO, CancelFeeAmtOutSDO.class);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "주문대사(제휴사) 인터페이스 장애발생.", e);
		}
		return out;
	}
	
	@APIOperation(description="주문취소요청 인터페이스")
	public OrderCancelReqOutSDO callOrderCancelReq(UserAgentSDO userAgentSDO, OrderCancelReqInSDO orderCancelReqSDO) {
		
		OrderCancelReqOutSDO out = null;
		
		try {
			
			HttpConfigSDO httpConfigSDO = InterfaceFactory.getChannel("orderCancelReq", userAgentSDO.getHttpAgentId());
			httpConfigSDO.setEzwelInsideInterface(true);
			configureHelper.setupUserAgentInfo(userAgentSDO, httpConfigSDO);
			
			/** execute interface */
			out = (OrderCancelReqOutSDO) inteface.sendJSON(httpConfigSDO, orderCancelReqSDO, OrderCancelReqOutSDO.class);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "주문취소요청 인터페이스 장애발생.", e);
		}
		
		return out;
	}

	@APIOperation(description="누락건확인 인터페이스")
	public OmiNumIdnOutSDO callOmiNumIdn(UserAgentSDO userAgentSDO, OmiNumIdnInSDO omiNumIdnSDO) {
		
		OmiNumIdnOutSDO out = null;
		
		try {
			
			HttpConfigSDO httpConfigSDO = InterfaceFactory.getChannel("omiNumIdn", userAgentSDO.getHttpAgentId());
			httpConfigSDO.setEzwelInsideInterface(true);
			configureHelper.setupUserAgentInfo(userAgentSDO, httpConfigSDO);
			
			/** execute interface */
			out = (OmiNumIdnOutSDO) inteface.sendJSON(httpConfigSDO, omiNumIdnSDO, OmiNumIdnOutSDO.class);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "누락건확인 인터페이스 장애발생.", e);
		}
		
		return out;
	}

	@APIOperation(description="주문대사(이지웰) 인터페이스")
	public EzwelJobOutSDO callEzwelJob(UserAgentSDO userAgentSDO, EzwelJobInSDO ezwelJobSDO) {
		
		EzwelJobOutSDO out = null;
		
		try {
			
			HttpConfigSDO httpConfigSDO = InterfaceFactory.getChannel("ezwelJob", userAgentSDO.getHttpAgentId());
			httpConfigSDO.setEzwelInsideInterface(true);
			configureHelper.setupUserAgentInfo(userAgentSDO, httpConfigSDO);
			
			/** execute interface */
			out = (EzwelJobOutSDO) inteface.sendJSON(httpConfigSDO, ezwelJobSDO, EzwelJobOutSDO.class);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "주문대사(이지웰) 인터페이스 장애발생.", e);
		}
		
		return out;
	}
	

}
