package ezwel_if;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
import com.ezwel.htl.interfaces.service.OutsideIFService;
import com.ezwel.htl.interfaces.service.data.cancelFeeAmt.CancelFeeAmtInSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeeAmt.CancelFeeAmtOutSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeePsrc.CancelFeePsrcInSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeePsrc.CancelFeePsrcOutSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadInSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadOutSDO;
import com.ezwel.htl.interfaces.service.data.rsvHistSend.RsvHistSendInSDO;
import com.ezwel.htl.interfaces.service.data.rsvHistSend.RsvHistSendOutSDO;

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
	
	public InterfaceDemoService() {
		outIfService = new OutsideIFService();
	}
	

	public CancelFeePsrcOutSDO callCancelFeePsrc() {
		logger.debug("[START] callCancelFeePsrc");
		
		UserAgentSDO userAgentSDO = new UserAgentSDO();
		//펜션라이프_플레이스엠
		userAgentSDO.setHttpAgentId("10000496");
		userAgentSDO.setHttpAgentType("httpAgentType-sample");
		userAgentSDO.setHttpChannelCd("httpChannelCd-sample");
		userAgentSDO.setHttpClientId("httpClientId-sample");
		userAgentSDO.setHttpRequestId("httpRequestId-sample");
		
		//Input parameter
		CancelFeePsrcInSDO sdo = new CancelFeePsrcInSDO();
		
		sdo.setPdtNo("pdt-No");
		sdo.setCheckInDate("20181201");
		sdo.setCheckOutDate("20181202");
		sdo.setRoomNo("1");
		sdo.setRoomCnt(1);
		
		//interface api call
		CancelFeePsrcOutSDO out = outIfService.callCancelFeePsrc(userAgentSDO, sdo);
		
		logger.debug("[END] callCancelFeePsrc");
		return out;
	}
	
	public CancelFeeAmtOutSDO callCancelFeeAmt() {
		logger.debug("[START] callCancelFeeAmt");
		
		UserAgentSDO userAgentSDO = new UserAgentSDO();
		//펜션라이프_플레이스엠
		userAgentSDO.setHttpAgentId("10000496");
		userAgentSDO.setHttpAgentType("httpAgentType-sample");
		userAgentSDO.setHttpChannelCd("httpChannelCd-sample");
		userAgentSDO.setHttpClientId("httpClientId-sample");
		userAgentSDO.setHttpRequestId("httpRequestId-sample");
		
		//Input parameter
		CancelFeeAmtInSDO sdo = new CancelFeeAmtInSDO();
		
		sdo.setRsvNo("rsv-no");
		
		//interface api call
		CancelFeeAmtOutSDO out = outIfService.callCancelFeeAmt(userAgentSDO, sdo);
		
		logger.debug("[END] callCancelFeeAmt");
		return out;
	}
	
	public RoomReadOutSDO callRoomRead() {		
		logger.debug("[START] callRoomRead");
		
		UserAgentSDO userAgentSDO = new UserAgentSDO();
		//펜션라이프_플레이스엠
		userAgentSDO.setHttpAgentId("10000496");
		userAgentSDO.setHttpAgentType("httpAgentType-sample");
		userAgentSDO.setHttpChannelCd("httpChannelCd-sample");
		userAgentSDO.setHttpClientId("httpClientId-sample");
		userAgentSDO.setHttpRequestId("httpRequestId-sample");
		
		//Input parameter
		RoomReadInSDO roomReadSDO = new RoomReadInSDO();
		
		roomReadSDO.setPdtNo("pdt-No");
		roomReadSDO.setCheckInDate("20181201");
		roomReadSDO.setCheckOutDate("20181202");
		roomReadSDO.setRoomNo("1");
		roomReadSDO.setRoomCnt(1);
		roomReadSDO.setAdultCnt(1);
		roomReadSDO.setChildCnt(1);
		
		//interface api call
		RoomReadOutSDO out = outIfService.callRoomRead(userAgentSDO, roomReadSDO);
		
		logger.debug("[END] callRoomRead");
		return out;
	}
	
	public RsvHistSendOutSDO callRsvHistSend() {		
		logger.debug("[START] callRsvHistSend");
		
		UserAgentSDO userAgentSDO = new UserAgentSDO();
		
		//
		userAgentSDO.setHttpAgentId("10000496");
		userAgentSDO.setHttpAgentType("httpAgentType-sample");
		userAgentSDO.setHttpChannelCd("httpChannelCd-sample");
		userAgentSDO.setHttpClientId("httpClientId-sample");
		userAgentSDO.setHttpRequestId("httpRequestId-sample");
		
		//Input parameter
		RsvHistSendInSDO sdo = new RsvHistSendInSDO();
		/*
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
		*/
		//interface api call
		RsvHistSendOutSDO out = outIfService.callRsvHistSend(userAgentSDO, sdo);
		
		logger.debug("[END] callRsvHistSend");
		return out;
	}
	
}
