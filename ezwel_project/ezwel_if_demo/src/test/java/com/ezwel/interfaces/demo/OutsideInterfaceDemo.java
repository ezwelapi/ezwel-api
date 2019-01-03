package com.ezwel.interfaces.demo;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.service.data.cancelFeePsrc.CancelFeePsrcOutSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadOutSDO;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 19.
 */
public class OutsideInterfaceDemo {

	private static final Logger logger = LoggerFactory.getLogger(InterfaceDemoService.class);

	public OutsideInterfaceDemo() {
		InterfaceFactory.initLocalTestInterfaceFactory();
	}
	
	@Test
	public void callRoomRead() {

		InterfaceDemoService service = new InterfaceDemoService(); 
		
		RoomReadOutSDO out = service.callRoomRead();
		
		logger.debug("Code : {}", out.getCode());
		logger.debug("Message : {}", out.getMessage());
		logger.debug("Data : {}", out.getData());
		
		
		CancelFeePsrcOutSDO cancelFeePsrcOutSDO = service.callCancelFeePsrc();
		
		logger.debug("cancelFeePsrcOutSDO : {}", cancelFeePsrcOutSDO);
	}
	
}
