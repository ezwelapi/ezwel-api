package com.ezwel.htl.interfaces.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

		/** 데이터 저장이 모두 끝난후 제휴사 별 별도 멀티쓰레드 이미지 다운로드 실행 */
		outsideService.downloadMultiImage(out);	
		
		return out;
	}
	
	@APIOperation(description="시설검색 인터페이스", isOutputJsonMarshall=true, returnType=FaclSearchOutSDO.class)
	@RequestMapping(value="/service/callFaclSearch")
	public Object callFaclSearch(UserAgentSDO userAgentSDO, FaclSearchInSDO faclSearchSDO) {
		
		FaclSearchOutSDO out = null;

		out = outsideService.callFaclSearch(userAgentSDO, faclSearchSDO);

		return out;
	}
	

	@APIOperation(description="당일특가검색 인터페이스", isOutputJsonMarshall=true, returnType=SddSearchOutSDO.class)
	@RequestMapping(value="/service/callSddSearch")
	public Object callSddSearch(UserAgentSDO userAgentSDO) {
		
		SddSearchOutSDO out = null;

		out = outsideService.callSddSearch(userAgentSDO);
		
		return out;
	}
	
	/**************************************
	 * interface_if API
	 **************************************/
	
	@RequestMapping(value="/service/callRoomRead")
	@APIOperation(description="객실정보조회 인터페이스", isOutputJsonMarshall=true, returnType=RoomReadOutSDO.class)
	public Object callRoomRead(UserAgentSDO userAgentSDO, RoomReadInSDO roomReadSDO) {
		logger.debug("[START] callRoomRead {} {}", userAgentSDO, roomReadSDO);
		RoomReadOutSDO out = outsideIFService.callRoomRead(userAgentSDO, roomReadSDO);
					
		return out;		
	}
	
	@RequestMapping(value="/service/callCancelFeePsrc")
	@APIOperation(description="취소수수규정 인터페이스", isOutputJsonMarshall=true, returnType=CancelFeePsrcOutSDO.class)
	public Object callCancelFeePsrc(UserAgentSDO userAgentSDO, CancelFeePsrcInSDO cancelFeePsrcSDO) {
		
		CancelFeePsrcOutSDO out = outsideIFService.callCancelFeePsrc(userAgentSDO, cancelFeePsrcSDO);
		
		return out;		
	}
	
	@RequestMapping(value="/service/callRsvHistSend")
	@APIOperation(description="결재완료내역전송 인터페이스", isOutputJsonMarshall=true, returnType=RsvHistSendOutSDO.class)
	public Object callRsvHistSend(UserAgentSDO userAgentSDO, RsvHistSendInSDO rsvHistSendSDO) {
		
		RsvHistSendOutSDO out = outsideIFService.callRsvHistSend(userAgentSDO, rsvHistSendSDO);
		
		return out;
	}
	
	@RequestMapping(value="/service/callCancelFeeAmt")
	@APIOperation(description="취소수수료계산 인터페이스", isOutputJsonMarshall=true, returnType=CancelFeeAmtOutSDO.class)
	public Object callCancelFeeAmt(UserAgentSDO userAgentSDO, CancelFeeAmtInSDO cancelFeeAmtSDO) {
		
		CancelFeeAmtOutSDO out = outsideIFService.callCancelFeeAmt(userAgentSDO, cancelFeeAmtSDO);
		
		return out;
	}
	
	@RequestMapping(value="/service/callOrderCancelReq")
	@APIOperation(description="주문취소요청 인터페이스", isOutputJsonMarshall=true, returnType=OrderCancelReqOutSDO.class)
	public Object callOrderCancelReq(UserAgentSDO userAgentSDO, OrderCancelReqInSDO orderCancelReqSDO) {
		
		OrderCancelReqOutSDO out = outsideIFService.callOrderCancelReq(userAgentSDO, orderCancelReqSDO);
		
		return out;
	}

	@RequestMapping(value="/service/callOmiNumIdn")
	@APIOperation(description="누락건확인 인터페이스", isOutputJsonMarshall=true, returnType=OmiNumIdnOutSDO.class)
	public Object callOmiNumIdn(UserAgentSDO userAgentSDO, OmiNumIdnInSDO omiNumIdnSDO) {
		
		OmiNumIdnOutSDO out = outsideIFService.callOmiNumIdn(userAgentSDO, omiNumIdnSDO);
		
		return out;
	}

	@RequestMapping(value="/service/callEzwelJob")
	@APIOperation(description="주문대사(이지웰) 인터페이스", isOutputJsonMarshall=true, returnType=EzwelJobOutSDO.class)
	public Object callEzwelJob(UserAgentSDO userAgentSDO, EzwelJobInSDO ezwelJobSDO) {
		
		EzwelJobOutSDO out = outsideIFService.callEzwelJob(userAgentSDO, ezwelJobSDO);
		
		return out;
	}	
	

	
}
