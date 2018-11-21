package com.ezwel.interfaces.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.http.data.UserAgentDTO;
import com.ezwel.htl.interfaces.service.OutsideInterfaceService;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadInDTO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadOutDTO;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 19.
 */
public class InterfaceDemoService {

	private static final Logger logger = LoggerFactory.getLogger(InterfaceDemoService.class);
	
	private OutsideInterfaceService service; 
	
	public InterfaceDemoService() {
		service = new OutsideInterfaceService();
	}
	
	public RoomReadOutDTO callRoomRead() {
		logger.debug("[START] callRoomRead");
		
		UserAgentDTO userAgent = new UserAgentDTO();
		//펜션라이프_플레이스엠
		userAgent.setHttpAgentId("10000496");
		userAgent.setHttpAgentType("httpAgentType-sample");
		userAgent.setHttpChannelCd("httpChannelCd-sample");
		userAgent.setHttpClientId("httpClientId-sample");
		userAgent.setHttpRequestId("httpRequestId-sample");
		
		//Input parameter
		RoomReadInDTO roomReadDTO = new RoomReadInDTO();
		
		roomReadDTO.setOtaId("ota-Id");
		roomReadDTO.setPdtNo("pdt-No");
		roomReadDTO.setCheckInDate("20181201");
		roomReadDTO.setCheckOutDate("20181202");
		roomReadDTO.setRoomNo("1");
		roomReadDTO.setRoomCnt(1);
		roomReadDTO.setAdultCnt(1);
		roomReadDTO.setChildCnt(1);
		
		//interface api call
		RoomReadOutDTO out = service.callRoomRead(userAgent, roomReadDTO);
		
		logger.debug("[END] callRoomRead");
		return out;
	}
	
}
