package com.ezwel.interfaces.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.http.data.UserAgentDTO;
import com.ezwel.htl.interfaces.service.OutsideInterfaceService;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadInSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadOutSDO;

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
	
	public RoomReadOutSDO callRoomRead() {
		logger.debug("[START] callRoomRead");
		
		UserAgentDTO userAgent = new UserAgentDTO();
		//펜션라이프_플레이스엠
		userAgent.setHttpAgentId("10000496");
		userAgent.setHttpAgentType("httpAgentType-sample");
		userAgent.setHttpChannelCd("httpChannelCd-sample");
		userAgent.setHttpClientId("httpClientId-sample");
		userAgent.setHttpRequestId("httpRequestId-sample");
		
		//Input parameter
		RoomReadInSDO roomReadSDO = new RoomReadInSDO();
		
		roomReadSDO.setOtaId("ota-Id");
		roomReadSDO.setPdtNo("pdt-No");
		roomReadSDO.setCheckInDate("20181201");
		roomReadSDO.setCheckOutDate("20181202");
		roomReadSDO.setRoomNo("1");
		roomReadSDO.setRoomCnt(1);
		roomReadSDO.setAdultCnt(1);
		roomReadSDO.setChildCnt(1);
		
		//interface api call
		RoomReadOutSDO out = service.callRoomRead(userAgent, roomReadSDO);
		
		logger.debug("[END] callRoomRead");
		return out;
	}
	
}
