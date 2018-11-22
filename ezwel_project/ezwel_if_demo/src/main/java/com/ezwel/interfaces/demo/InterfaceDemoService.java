package com.ezwel.interfaces.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.http.data.UserAgentDTO;
import com.ezwel.htl.interfaces.service.OutsideInterfaceService;
import com.ezwel.htl.interfaces.service.data.cancelFeePsrc.CancelFeePsrcInSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeePsrc.CancelFeePsrcOutSDO;
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
		
		UserAgentDTO userAgentDTO = new UserAgentDTO();
		//펜션라이프_플레이스엠
		userAgentDTO.setHttpAgentId("10000496");
		userAgentDTO.setHttpAgentType("httpAgentType-sample");
		userAgentDTO.setHttpChannelCd("httpChannelCd-sample");
		userAgentDTO.setHttpClientId("httpClientId-sample");
		userAgentDTO.setHttpRequestId("httpRequestId-sample");
		
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
		RoomReadOutSDO out = service.callRoomRead(userAgentDTO, roomReadSDO);
		
		logger.debug("[END] callRoomRead");
		return out;
	}
	

	public CancelFeePsrcOutSDO callCancelFeePsrc() {
		logger.debug("[START] callCancelFeePsrc");
		
		UserAgentDTO userAgentDTO = new UserAgentDTO();
		//펜션라이프_플레이스엠
		userAgentDTO.setHttpAgentId("10000496");
		userAgentDTO.setHttpAgentType("httpAgentType-sample");
		userAgentDTO.setHttpChannelCd("httpChannelCd-sample");
		userAgentDTO.setHttpClientId("httpClientId-sample");
		userAgentDTO.setHttpRequestId("httpRequestId-sample");
		
		//Input parameter
		CancelFeePsrcInSDO sdo = new CancelFeePsrcInSDO();
		
		sdo.setOtaId("ota-Id");
		sdo.setPdtNo("pdt-No");
		sdo.setCheckInDate("20181201");
		sdo.setCheckOutDate("20181202");
		sdo.setRoomNo("1");
		sdo.setRoomCnt(1);
		
		//interface api call
		CancelFeePsrcOutSDO out = service.callCancelFeePsrc(userAgentDTO, sdo);
		
		logger.debug("[END] callCancelFeePsrc");
		return out;
	}
	
}
