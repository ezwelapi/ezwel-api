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
		RoomReadInSDO roomReadDTO = new RoomReadInSDO();
		
		roomReadDTO.setOtaId("ota-Id");
		roomReadDTO.setPdtNo("pdt-No");
		roomReadDTO.setCheckInDate("20181201");
		roomReadDTO.setCheckOutDate("20181202");
		roomReadDTO.setRoomNo("1");
		roomReadDTO.setRoomCnt(1);
		roomReadDTO.setAdultCnt(1);
		roomReadDTO.setChildCnt(1);
		
		//interface api call
		RoomReadOutSDO out = service.callRoomRead(userAgent, roomReadDTO);
		
		logger.debug("[END] callRoomRead");
		return out;
	}
	
}
