package com.ezwel.interfaces.demo;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.service.dto.roomRead.RoomReadOutDTO;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 19.
 */
public class InterfaceDemoTest {

	private static final Logger logger = LoggerFactory.getLogger(InterfaceDemoService.class);

	@Test
	public void callRoomRead() {
		
		InterfaceFactory factory = new InterfaceFactory();
		
		factory.setConfigXmlPath("/interfaces/interface-configure.xml");
		factory.initFactory();
		
		InterfaceDemoService service = new InterfaceDemoService(); 
		
		RoomReadOutDTO out = service.callRoomRead();
		
		logger.debug("Code : {}", out.getCode());
		logger.debug("Message : {}", out.getMessage());
		logger.debug("Data : {}", out.getData());
	}
	
}
