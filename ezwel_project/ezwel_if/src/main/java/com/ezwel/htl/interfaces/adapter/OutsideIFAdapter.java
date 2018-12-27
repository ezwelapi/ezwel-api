package com.ezwel.htl.interfaces.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.configure.ConfigureHelper;
import com.ezwel.htl.interfaces.commons.http.HttpInterfaceExecutor;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.service.OutsideIFService;
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

	@Autowired /** interface_if는 프론트 및 관리자단에서 ezwel 프레임워크 표준인  Autowired를 사용한다. (interface_if_server는 Autowired보다 빠른 스프링 컨텍스트의 getBean을 사용함) */
	private HttpInterfaceExecutor inteface;
	
	@Autowired /** interface_if는 프론트 및 관리자단에서 ezwel 프레임워크 표준인  Autowired를 사용한다. (interface_if_server는 Autowired보다 빠른 스프링 컨텍스트의 getBean을 사용함) */
	private ConfigureHelper configureHelper; 
	
	@Autowired /** interface_if는 프론트 및 관리자단에서 ezwel 프레임워크 표준인  Autowired를 사용한다. (interface_if_server는 Autowired보다 빠른 스프링 컨텍스트의 getBean을 사용함) */
	private PropertyUtil propertyUtil;
	
	@Autowired /** interface_if는 프론트 및 관리자단에서 ezwel 프레임워크 표준인  Autowired를 사용한다. (interface_if_server는 Autowired보다 빠른 스프링 컨텍스트의 getBean을 사용함) */
	private OutsideIFService outsideIFService;
	
	public OutsideIFAdapter() {
		
		if(propertyUtil == null) {
			propertyUtil = new PropertyUtil();
		}
		if(inteface == null) {
			inteface = new HttpInterfaceExecutor();
		}
		if(configureHelper == null) {
			configureHelper = new ConfigureHelper();
		}
		if(outsideIFService == null) {
			outsideIFService = new OutsideIFService();
		}
	}
	
	@APIOperation(description="객실정보조회 인터페이스")
	public RoomReadOutSDO callRoomRead(UserAgentSDO userAgentSDO, RoomReadInSDO roomReadSDO) {
		return outsideIFService.callRoomRead(userAgentSDO, roomReadSDO, true);
	}
	
	@APIOperation(description="취소수수규정 인터페이스")
	public CancelFeePsrcOutSDO callCancelFeePsrc(UserAgentSDO userAgentSDO, CancelFeePsrcInSDO cancelFeePsrcSDO) {
		return outsideIFService.callCancelFeePsrc(userAgentSDO, cancelFeePsrcSDO, true);
	}
	
	@APIOperation(description="결재완료내역전송 인터페이스")
	public RsvHistSendOutSDO callRsvHistSend(UserAgentSDO userAgentSDO, RsvHistSendInSDO rsvHistSendSDO) {
		return outsideIFService.callRsvHistSend(userAgentSDO, rsvHistSendSDO, true);
	}
	
	@APIOperation(description="취소수수료계산 인터페이스")
	public CancelFeeAmtOutSDO callCancelFeeAmt(UserAgentSDO userAgentSDO, CancelFeeAmtInSDO cancelFeeAmtSDO) {
		return outsideIFService.callCancelFeeAmt(userAgentSDO, cancelFeeAmtSDO, true);
	}
	
	@APIOperation(description="주문취소요청 인터페이스")
	public OrderCancelReqOutSDO callOrderCancelReq(UserAgentSDO userAgentSDO, OrderCancelReqInSDO orderCancelReqSDO) {
		return outsideIFService.callOrderCancelReq(userAgentSDO, orderCancelReqSDO, true);
	}

	@APIOperation(description="누락건확인 인터페이스")
	public OmiNumIdnOutSDO callOmiNumIdn(UserAgentSDO userAgentSDO, OmiNumIdnInSDO omiNumIdnSDO) {
		return outsideIFService.callOmiNumIdn(userAgentSDO, omiNumIdnSDO, true);
	}

	@APIOperation(description="주문대사(이지웰) 인터페이스")
	public EzwelJobOutSDO callEzwelJob(UserAgentSDO userAgentSDO, EzwelJobInSDO ezwelJobSDO) {
		return outsideIFService.callEzwelJob(userAgentSDO, ezwelJobSDO, true);
	}
	

}
