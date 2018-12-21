package interfaces.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
import com.ezwel.htl.interfaces.service.OutsideIFService;
import com.ezwel.htl.interfaces.service.data.cancelFeeAmt.CancelFeeAmtInSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeeAmt.CancelFeeAmtOutSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeePsrc.CancelFeePsrcInSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeePsrc.CancelFeePsrcOutSDO;
import com.ezwel.htl.interfaces.service.data.ezwelJob.EzwelJobInSDO;
import com.ezwel.htl.interfaces.service.data.ezwelJob.EzwelJobOutSDO;
import com.ezwel.htl.interfaces.service.data.omiNumIdn.OmiNumIdnInSDO;
import com.ezwel.htl.interfaces.service.data.omiNumIdn.OmiNumIdnOutSDO;
import com.ezwel.htl.interfaces.service.data.orderCancelReq.OrderCancelReqInSDO;
import com.ezwel.htl.interfaces.service.data.orderCancelReq.OrderCancelReqOutSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadInSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadOutSDO;
import com.ezwel.htl.interfaces.service.data.rsvHistSend.RsvHistSendDataInSDO;
import com.ezwel.htl.interfaces.service.data.rsvHistSend.RsvHistSendInSDO;
import com.ezwel.htl.interfaces.service.data.rsvHistSend.RsvHistSendOutSDO;

import junit.framework.TestCase;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 19.
 */
public class FrontInterfaceDemoService extends TestCase {

	private static final Logger logger = LoggerFactory.getLogger(BOInterfaceDemoService.class);
	
	private OutsideIFService outIfService; // if
	
	/**
	 * 아레 컨트스럭터는 로컬테스트 떄만 사용한다.
	 * InterfaceFactory는 스프링 어플리케이션 초기화시 스프링 빈으로 초기화된다.
	 */
	public FrontInterfaceDemoService()  throws Exception {
		InterfaceFactory factory = new InterfaceFactory();
		factory.setConfigXmlPath("/interfaces/interface-configure.xml");
		factory.initFactory();
		
		outIfService = new OutsideIFService();
	}


	// 객실정보조회
	public void testRoomRead()  throws Exception {
		logger.debug("[START] callRoomRead");
		
		UserAgentSDO userAgentDTO = new UserAgentSDO();
		
		userAgentDTO.setHttpAgentId("10055550"); //호텔패스
		userAgentDTO.setHttpAgentType("AP02PO");
		userAgentDTO.setHttpChannelCd("1");
		userAgentDTO.setHttpClientId("ez1");
		userAgentDTO.setHttpRequestId("test");
		
		//Input parameter
		RoomReadInSDO roomReadSDO = new RoomReadInSDO();
		
		roomReadSDO.setOtaId("10055550");
		roomReadSDO.setPdtNo("KRSEL112");
		roomReadSDO.setCheckInDate("20190101");
		roomReadSDO.setCheckOutDate("20190102");
		roomReadSDO.setRoomCnt(1);
		roomReadSDO.setAdultCnt(2);
		roomReadSDO.setChildCnt(0);
		
		//interface api call
		RoomReadOutSDO out = outIfService.callRoomRead(userAgentDTO, roomReadSDO);
		
		logger.debug("Code : {}", out.getCode());
		logger.debug("Message : {}", out.getMessage());
		logger.debug("Data : {}", out.getData());
		
		logger.debug("[END] callRoomRead");
	}
	
	// 취소수수료규정
	public void testCancelFeePsrc()  throws Exception {
		logger.debug("[START] callCancelFeePsrc");
		
		UserAgentSDO userAgentDTO = new UserAgentSDO();
		
		userAgentDTO.setHttpAgentId("10055550"); //호텔패스
		userAgentDTO.setHttpAgentType("AP02PO");
		userAgentDTO.setHttpChannelCd("1");
		userAgentDTO.setHttpClientId("ez1");
		userAgentDTO.setHttpRequestId("test");
		
		//Input parameter
		CancelFeePsrcInSDO cancelFeePsrcSDO = new CancelFeePsrcInSDO();
		
		cancelFeePsrcSDO.setOtaId("10055550");
		cancelFeePsrcSDO.setPdtNo("KRSEL112");
		cancelFeePsrcSDO.setCheckInDate("20190101");
		cancelFeePsrcSDO.setCheckOutDate("20190102");
		cancelFeePsrcSDO.setRoomNo("Yob1daQcPbOlVncEHz/Q62kMjcnsyRrO0QH/U/qJzoi^JE^t1pCAhgNe80PkfWL7SQwNgwaDCDPX6r37ofNtXZuytTAdeJAo6WO8m3wHVREg5VuHdfOXK/v5oB/X1xkrdHjAJxAzF1vEiNFvpWFBXgstG1HUZrPvh^mfp97BEQ0NnGJJHoRFdtvpsLw3PbTWdNRjNMkeXuF0n4XmUgfzxNLMP3ThbqcvENb6JCLuIek=");
		cancelFeePsrcSDO.setRoomCnt(1);
		
		//interface api call
		CancelFeePsrcOutSDO out = outIfService.callCancelFeePsrc(userAgentDTO, cancelFeePsrcSDO);
		
		logger.debug("Code : {}", out.getCode());
		logger.debug("Message : {}", out.getMessage());
		logger.debug("Data : {}", out.getData());

		logger.debug("[END] callCancelFeePsrc");
	}
	
	// 결제완료내역전송
	public void testRsvHistSend()  throws Exception {		
		logger.debug("[START] callRsvHistSend");
		
		UserAgentSDO userAgentDTO = new UserAgentSDO();
		
		userAgentDTO.setHttpAgentId("10055550"); //호텔패스
		userAgentDTO.setHttpAgentType("AP02PO");
		userAgentDTO.setHttpChannelCd("1");
		userAgentDTO.setHttpClientId("ez1");
		userAgentDTO.setHttpRequestId("test");
		
		//Input parameter
		RsvHistSendInSDO rsvHistSendSDO = new RsvHistSendInSDO();
		
		RsvHistSendDataInSDO data = new RsvHistSendDataInSDO();
		
		data.setRsvNo("E123456789");		
		data.setRsvDatetime("20181219182534");
		data.setRsvPrice(58000);
		data.setRsvStat("r02");
		data.setRsvPdtName("뉴천지 호텔");
		data.setRsvPdtNo("P000000001");
		data.setPdtNo("KRSEL112");
		data.setPdtName("뉴천지 호텔");
		data.setRoomNo("Yob1daQcPbOlVncEHz/Q62kMjcnsyRrO0QH/U/qJzoi^JE^t1pCAhgNe80PkfWL7SQwNgwaDCDPX6r37ofNtXZuytTAdeJAo6WO8m3wHVREg5VuHdfOXK/v5oB/X1xkrdHjAJxAzF1vEiNFvpWFBXgstG1HUZrPvh^mfp97BEQ0NnGJJHoRFdtvpsLw3PbTWdNRjNMkeXuF0n4XmUgfzxNLMP3ThbqcvENb6JCLuIek=");
		data.setRoomName("Standard Double Room(1 double bed request)");
		data.setRoomCnt(1);
		data.setCheckInDate("20190101");
		data.setCheckOutDate("20190102");
		data.setMemKey("EZ0001");
		data.setMemName("홍길동");
		data.setMemPhone("01012341234");
		data.setMemEmail("test@test.com");
		data.setUserName("홍길동");
		data.setUserMobile("01012341234");
		data.setUserEmail("test@test.com");
		data.setUserCmt("전망 좋은 방으로 요청 드립니다");
		data.setAdultCnt(2);
		data.setChildCnt(0);
		
		rsvHistSendSDO.setData(data);
		
		//interface api call
		RsvHistSendOutSDO out = outIfService.callRsvHistSend(userAgentDTO, rsvHistSendSDO);
		
		logger.debug("Code : {}", out.getCode());
		logger.debug("Message : {}", out.getMessage());
		logger.debug("RsvNo : {}", out.getRsvNo());
		logger.debug("OtaRsvNo : {}", out.getOtaRsvNo());
		
		logger.debug("[END] callRsvHistSend");
	}
	
	// 취소수수료계산
	public void testCancelFeeAmt()  throws Exception {
		logger.debug("[START] callCancelFeeAmt");
		
		UserAgentSDO userAgentDTO = new UserAgentSDO();
		
		userAgentDTO.setHttpAgentId("10055550"); //호텔패스
		userAgentDTO.setHttpAgentType("AP02PO");
		userAgentDTO.setHttpChannelCd("1");
		userAgentDTO.setHttpClientId("ez1");
		userAgentDTO.setHttpRequestId("test");
		
		//Input parameter
		CancelFeeAmtInSDO cancelFeeAmtSDO = new CancelFeeAmtInSDO();
		
		cancelFeeAmtSDO.setOtaId("10055550");
		cancelFeeAmtSDO.setRsvNo("E123456789");
		
		//interface api call
		CancelFeeAmtOutSDO out = outIfService.callCancelFeeAmt(userAgentDTO, cancelFeeAmtSDO);
		
		logger.debug("Code : {}", out.getCode());
		logger.debug("Message : {}", out.getMessage());
		logger.debug("Data : {}", out.getData());
		logger.debug("[END] callCancelFeeAmt");
	}
	
	// 주문취소요청
	public void testOrderCancelReq()  throws Exception {		
		logger.debug("[START] callOrderCancelReq");
		
		UserAgentSDO userAgentDTO = new UserAgentSDO();
		
		userAgentDTO.setHttpAgentId("10055550"); //호텔패스
		userAgentDTO.setHttpAgentType("AP02PO");
		userAgentDTO.setHttpChannelCd("1");
		userAgentDTO.setHttpClientId("ez1");
		userAgentDTO.setHttpRequestId("test");
		
		//Input parameter
		OrderCancelReqInSDO orderCancelReqSDO = new OrderCancelReqInSDO();
		
		orderCancelReqSDO.setOtaId("10055550");
		orderCancelReqSDO.setRsvNo("E123456789");
		orderCancelReqSDO.setOtaRsvNo("O123456789");
		orderCancelReqSDO.setRsvPrice(58000);
		orderCancelReqSDO.setCancelCharge(0);
		
		//interface api call
		OrderCancelReqOutSDO out = outIfService.callOrderCancelReq(userAgentDTO, orderCancelReqSDO);
		
		logger.debug("Code : {}", out.getCode());
		logger.debug("Message : {}", out.getMessage());
		logger.debug("[END] callOrderCancelReq");
	}
	
	// 누락건확인
	public void testOmiNumIdn()  throws Exception {		
		logger.debug("[START] callOmiNumIdn");
		
		UserAgentSDO userAgentDTO = new UserAgentSDO();
		
		userAgentDTO.setHttpAgentId("10055550"); //호텔패스
		userAgentDTO.setHttpAgentType("AP02PO");
		userAgentDTO.setHttpChannelCd("1");
		userAgentDTO.setHttpClientId("ez1");
		userAgentDTO.setHttpRequestId("test");
		
		//Input parameter
		OmiNumIdnInSDO omiNumIdnSDO = new OmiNumIdnInSDO();
		
		omiNumIdnSDO.setOtaId("10055550");
		omiNumIdnSDO.setRsvNo("E123456789");
		omiNumIdnSDO.setRsvStat("r02");
		
		//interface api call
		OmiNumIdnOutSDO out = outIfService.callOmiNumIdn(userAgentDTO, omiNumIdnSDO);
		
		logger.debug("Code : {}", out.getCode());
		logger.debug("Message : {}", out.getMessage());
		logger.debug("Reserves : {}", out.getReserves());
		logger.debug("[END] callOmiNumIdn");
	}
	
	// 주문대사(이지웰)
	public void testEzwelJob()  throws Exception {		
		logger.debug("[START] callEzwelJob");
		
		UserAgentSDO userAgentDTO = new UserAgentSDO();
		
		userAgentDTO.setHttpAgentId("10055550"); //호텔패스
		userAgentDTO.setHttpAgentType("AP02PO");
		userAgentDTO.setHttpChannelCd("1");
		userAgentDTO.setHttpClientId("ez1");
		userAgentDTO.setHttpRequestId("test");
		
		//Input parameter
		EzwelJobInSDO ezwelJobSDO = new EzwelJobInSDO();
		
		ezwelJobSDO.setOtaId("10055550");
		ezwelJobSDO.setRsvNo("E123456789");
		ezwelJobSDO.setRsvDateStart("20190101");
		ezwelJobSDO.setRsvDateEnd("20190102");
		
		//interface api call
		EzwelJobOutSDO out = outIfService.callEzwelJob(userAgentDTO, ezwelJobSDO);
		
		logger.debug("Code : {}", out.getCode());
		logger.debug("Message : {}", out.getMessage());
		logger.debug("Reserves : {}", out.getReserves());
		
		logger.debug("[END] callEzwelJob");
	}
	
}
