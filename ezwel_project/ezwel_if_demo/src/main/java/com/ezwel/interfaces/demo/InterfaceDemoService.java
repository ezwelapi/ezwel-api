package com.ezwel.interfaces.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
import com.ezwel.htl.interfaces.server.service.OutsideService;
import com.ezwel.htl.interfaces.service.OutsideIFService;
import com.ezwel.htl.interfaces.service.data.allReg.AllRegOutSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeeAmt.CancelFeeAmtInSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeeAmt.CancelFeeAmtOutSDO;
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
	
	private OutsideIFService outIfService; 
	private OutsideService outIfServerService; 
	
	public InterfaceDemoService() {
		outIfService = new OutsideIFService();
		outIfServerService = new OutsideService();
	}
	
	public AllRegOutSDO callAllReg() {		
		logger.debug("[START] callAllReg");
		
		UserAgentSDO userAgentDTO = new UserAgentSDO();
		
		//User agent set
		userAgentDTO.setHttpAgentType("httpAgentType-sample");
		userAgentDTO.setHttpChannelCd("httpChannelCd-sample");
		userAgentDTO.setHttpClientId("httpClientId-sample");
		userAgentDTO.setHttpRequestId("httpRequestId-sample");
		
		//interface api call
		AllRegOutSDO out = outIfServerService.callAllReg(userAgentDTO);
		
		logger.debug("[END] callAllReg");
		return out;
	}	

	public CancelFeePsrcOutSDO callCancelFeePsrc() {
		logger.debug("[START] callCancelFeePsrc");
		
		UserAgentSDO userAgentDTO = new UserAgentSDO();
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
		CancelFeePsrcOutSDO out = outIfService.callCancelFeePsrc(userAgentDTO, sdo);
		
		logger.debug("[END] callCancelFeePsrc");
		return out;
	}
	
	public CancelFeeAmtOutSDO callCancelFeeAmt() {
		logger.debug("[START] callCancelFeeAmt");
		
		UserAgentSDO userAgentDTO = new UserAgentSDO();
		//펜션라이프_플레이스엠
		userAgentDTO.setHttpAgentId("10000496");
		userAgentDTO.setHttpAgentType("httpAgentType-sample");
		userAgentDTO.setHttpChannelCd("httpChannelCd-sample");
		userAgentDTO.setHttpClientId("httpClientId-sample");
		userAgentDTO.setHttpRequestId("httpRequestId-sample");
		
		//Input parameter
		CancelFeeAmtInSDO sdo = new CancelFeeAmtInSDO();
		
		sdo.setOtaId("ota-Id");
		sdo.setRsvNo("rsv-no");
		
		//interface api call
		CancelFeeAmtOutSDO out = outIfService.callCancelFeeAmt(userAgentDTO, sdo);
		
		logger.debug("[END] callCancelFeeAmt");
		return out;
	}
	
	public RoomReadOutSDO callRoomRead() {		
		logger.debug("[START] callRoomRead");
		
		UserAgentSDO userAgentDTO = new UserAgentSDO();
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
		RoomReadOutSDO out = outIfService.callRoomRead(userAgentDTO, roomReadSDO);
		
		logger.debug("[END] callRoomRead");
		return out;
	}
	
	/*public RsvHistSendOutSDO callRsvHistSend() {		
		logger.debug("[START] callRsvHistSend");
		
		UserAgentDTO userAgentDTO = new UserAgentDTO();
		
		//
		userAgentDTO.setHttpAgentId("10000496");
		userAgentDTO.setHttpAgentType("httpAgentType-sample");
		userAgentDTO.setHttpChannelCd("httpChannelCd-sample");
		userAgentDTO.setHttpClientId("httpClientId-sample");
		userAgentDTO.setHttpRequestId("httpRequestId-sample");
		
		//Input parameter
		RsvHistSendInSDO sdo = new RsvHistSendInSDO();
		
		sdo.setRsvNo("123456789");		
		sdo.setRsvDatetime("20181113152332");
		sdo.setRsvPrice(200000);
		sdo.setRsvStat("r02");
		sdo.setRsvPdtName("");
		sdo.setRsvPdtNo("");
		sdo.setPdtNo("1");
		sdo.setPdtName("서울 프라자 호텔");
		sdo.setRoomNo("1");
		sdo.setRoomName("디럭스");
		sdo.setRoomCnt(1);
		sdo.setCheckInDate("20181201");
		sdo.setCheckOutDate("20181202");
		sdo.setMemKey("EZ0001");
		sdo.setMemName("홍길동");
		sdo.setMemPhone("01012341234");
		sdo.setMemEmail("test@test.com");
		sdo.setUserName("홍길동");
		sdo.setUserMobile("01012341234");
		sdo.UserEmail("test@test.com");
		sdo.setUserCmt("전망 좋은 방으로 요청 드립니다");
		sdo.setAdultCnt(2);
		sdo.setChildCnt(1);
		
		//interface api call
		RsvHistSendOutSDO out = service.callRsvHistSend(userAgentDTO, rsvHistSendSDO);
		
		logger.debug("[END] callRsvHistSend");
		return out;
	}*/
	
}
