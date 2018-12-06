package com.ezwel.htl.interfaces.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.service.OutsideService;
import com.ezwel.htl.interfaces.service.OutsideIFService;
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
 *  http://localhost:8282/ezwel_if_server/API1.0/inside-03/facl/record
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 15.
 */
@Controller
public class OutsideController {

	private static final Logger logger = LoggerFactory.getLogger(OutsideController.class);
	
	private OutsideService outsideService = (OutsideService) LApplicationContext.getBean(OutsideService.class);
	
	private OutsideIFService outsideIFService = (OutsideIFService) LApplicationContext.getBean(OutsideIFService.class);
	
	/**
	 * <pre>
	 * [메서드 설명]
	 * URL : /API1.0/test/facl/record
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @param httpAgentId
	 * @param in
	 * @param request
	 * @param response
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 21.
	 */
	@APIOperation(description="전체시설일괄등록 인터페이스", isOutputJsonMarshall=true, returnType=AllRegOutSDO.class)
	@RequestMapping(value="/service/allReg")
	public Object callAllReg(UserAgentSDO userAgentSDO) {
		
		AllRegOutSDO out = null;

		out = outsideService.callAllReg(userAgentSDO);

		return out;
	}
	
	@APIOperation(description="시설검색 인터페이스", isOutputJsonMarshall=true, returnType=FaclSearchOutSDO.class)
	@RequestMapping(value="/service/callFaclSearch")
	public Object callFaclSearch(UserAgentSDO userAgentDTO, FaclSearchInSDO faclSearchDTO) {
		
		FaclSearchOutSDO out = null;

		out = outsideService.callFaclSearch(userAgentDTO, faclSearchDTO);

		return out;
	}
	

	@APIOperation(description="당일특가검색 인터페이스", isOutputJsonMarshall=true, returnType=SddSearchOutSDO.class)
	@RequestMapping(value="/service/callSddSearch")
	public Object callSddSearch(UserAgentSDO userAgentDTO) {
		
		SddSearchOutSDO out = null;

		out = outsideService.callSddSearch(userAgentDTO);
		
		return out;
	}
	
	/**************************************
	 * interface_if API
	 **************************************/
	
	@RequestMapping(value="/service/callRoomRead")
	@APIOperation(description="객실정보조회 인터페이스", returnType=RoomReadOutSDO.class)
	public Object callRoomRead(UserAgentSDO userAgentDTO, RoomReadInSDO roomReadDTO) {
		
		RoomReadOutSDO out = null;

		out = outsideIFService.callRoomRead(userAgentDTO, roomReadDTO);
					
		return out;		
	}
	
	@RequestMapping(value="/service/callCancelFeePsrc")
	@APIOperation(description="취소수수규정 인터페이스", returnType=CancelFeePsrcOutSDO.class)
	public Object callCancelFeePsrc(UserAgentSDO userAgentDTO, CancelFeePsrcInSDO cancelFeePsrcDTO) {
		
		CancelFeePsrcOutSDO out = null;
		
		out = outsideIFService.callCancelFeePsrc(userAgentDTO, cancelFeePsrcDTO);
		
		return out;		
	}
	
	@RequestMapping(value="/service/callRsvHistSend")
	@APIOperation(description="결재완료내역전송 인터페이스", returnType=RsvHistSendOutSDO.class)
	public Object callRsvHistSend(UserAgentSDO userAgentDTO, RsvHistSendInSDO rsvHistSendDTO) {
		
		RsvHistSendOutSDO out = null;
		
		out = outsideIFService.callRsvHistSend(userAgentDTO, rsvHistSendDTO);
		
		return out;
	}
	
	@RequestMapping(value="/service/callCancelFeeAmt")
	@APIOperation(description="취소수수료계산 인터페이스", returnType=CancelFeeAmtOutSDO.class)
	public Object callCancelFeeAmt(UserAgentSDO userAgentDTO, CancelFeeAmtInSDO cancelFeeAmtDTO) {
		
		CancelFeeAmtOutSDO out = null;
		
		out = outsideIFService.callCancelFeeAmt(userAgentDTO, cancelFeeAmtDTO);
		
		return out;
	}
	
	@RequestMapping(value="/service/callOrderCancelReq")
	@APIOperation(description="주문취소요청 인터페이스", returnType=OrderCancelReqOutSDO.class)
	public Object callOrderCancelReq(UserAgentSDO userAgentDTO, OrderCancelReqInSDO orderCancelReqDTO) {
		
		OrderCancelReqOutSDO out = null;
		
		out = outsideIFService.callOrderCancelReq(userAgentDTO, orderCancelReqDTO);
		
		return out;
	}

	@RequestMapping(value="/service/callOmiNumIdn")
	@APIOperation(description="누락건확인 인터페이스", returnType=OmiNumIdnOutSDO.class)
	public Object callOmiNumIdn(UserAgentSDO userAgentDTO, OmiNumIdnInSDO omiNumIdnDTO) {
		
		OmiNumIdnOutSDO out = null;
		
		out = outsideIFService.callOmiNumIdn(userAgentDTO, omiNumIdnDTO);
		
		return out;
	}

	@RequestMapping(value="/service/callEzwelJob")
	@APIOperation(description="주문대사(이지웰) 인터페이스", returnType=EzwelJobOutSDO.class)
	public Object callEzwelJob(UserAgentSDO userAgentDTO, EzwelJobInSDO ezwelJobDTO) {
		
		EzwelJobOutSDO out = null;
		
		out = outsideIFService.callEzwelJob(userAgentDTO, ezwelJobDTO);
		
		return out;
	}	
	
}
