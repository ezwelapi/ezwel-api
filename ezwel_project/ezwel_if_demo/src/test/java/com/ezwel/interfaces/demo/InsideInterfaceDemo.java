package com.ezwel.interfaces.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.service.data.allReg.AllRegOutSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeeAmt.CancelFeeAmtOutSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeePsrc.CancelFeePsrcOutSDO;
import com.ezwel.htl.interfaces.service.data.ezwelJob.EzwelJobOutSDO;
import com.ezwel.htl.interfaces.service.data.omiNumIdn.OmiNumIdnOutSDO;
import com.ezwel.htl.interfaces.service.data.orderCancelReq.OrderCancelReqOutSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadOutSDO;
import com.ezwel.htl.interfaces.service.data.rsvHistSend.RsvHistSendOutSDO;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 19.
 */
public class InsideInterfaceDemo {

	private static final Logger logger = LoggerFactory.getLogger(InterfaceDemoService.class);

	public InsideInterfaceDemo() {
		InterfaceFactory factory = new InterfaceFactory();
		factory.setConfigXmlPath("/interfaces/interface-configure.xml");
		factory.initFactory();
	}
	
	//전체시설일괄등록
	//@Test
	public void callAllReg() {

		InterfaceDemoService service = new InterfaceDemoService(); 
		
		AllRegOutSDO out = service.callAllReg();		
		logger.debug("Code : {}", out.getCode());
		logger.debug("Message : {}", out.getMessage());
		logger.debug("Data : {}", out.getData());
		
	}
	
	//객실정보조회
	//@Test
	public void callRoomRead() {

		InterfaceDemoService service = new InterfaceDemoService(); 
		
		RoomReadOutSDO out = service.callRoomRead();
		logger.debug("Code : {}", out.getCode());
		logger.debug("Message : {}", out.getMessage());
		logger.debug("Data : {}", out.getData());
		
	}
		
	//취소수수료규정
	//@Test
	public void callCancelFeePsrc() {

		InterfaceDemoService service = new InterfaceDemoService(); 
		
		CancelFeePsrcOutSDO out = service.callCancelFeePsrc();
		logger.debug("Code : {}", out.getCode());
		logger.debug("Message : {}", out.getMessage());
		logger.debug("Data : {}", out.getData());
		
	}
	
	//결제완료내역전송
	//@Test
	public void callRsvHistSend() {

		InterfaceDemoService service = new InterfaceDemoService(); 
		
		RsvHistSendOutSDO out = service.callRsvHistSend();
		logger.debug("Code : {}", out.getCode());
		logger.debug("Message : {}", out.getMessage());
	
	}
	
	//취소수수료계산
	//@Test
	public void callCancelFeeAmt() {

		InterfaceDemoService service = new InterfaceDemoService(); 
		
		CancelFeeAmtOutSDO out = service.callCancelFeeAmt();
		logger.debug("Code : {}", out.getCode());
		logger.debug("Message : {}", out.getMessage());
		logger.debug("Data : {}", out.getData());
			
	}
	
	//주문취소요청
	//@Test
	public void callOrderCancelReq() {

		InterfaceDemoService service = new InterfaceDemoService(); 
			
		OrderCancelReqOutSDO out = service.callOrderCancelReq();
		logger.debug("Code : {}", out.getCode());
		logger.debug("Message : {}", out.getMessage());
		
	}
	
	//누락건확인
	//@Test
	public void callOmiNumIdn() {

		InterfaceDemoService service = new InterfaceDemoService(); 
			
		OmiNumIdnOutSDO out = service.callOmiNumIdn();
		logger.debug("Code : {}", out.getCode());
		logger.debug("Message : {}", out.getMessage());
		logger.debug("Reserves : {}", out.getReserves());
		
	}
	
	//주문대사(이지웰)
	//@Test
	public void callEzwelJob() {

		InterfaceDemoService service = new InterfaceDemoService(); 
			
		EzwelJobOutSDO out = service.callEzwelJob();
		logger.debug("Code : {}", out.getCode());
		logger.debug("Message : {}", out.getMessage());
		logger.debug("Reserves : {}", out.getReserves());
	
	}
	
}
