package interfaces.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.adapter.OutsideIFAdapter;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
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
public class BOInterfaceDemoService extends TestCase {

	private static final Logger logger = LoggerFactory.getLogger(BOInterfaceDemoService.class);
	
	private OutsideIFAdapter outIfAdapter; // if
	
	/**
	 * 아레 컨트스럭터는 로컬테스트 떄만 사용한다.
	 * InterfaceFactory는 스프링 어플리케이션 초기화시 스프링 빈으로 초기화된다.
	 */
	public BOInterfaceDemoService()  throws Exception {
		InterfaceFactory.initLocalTestInterfaceFactory();
		
		outIfAdapter = new OutsideIFAdapter();
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
		
		roomReadSDO.setPdtNo("KRSEL217");
		roomReadSDO.setCheckInDate("20190114");
		roomReadSDO.setCheckOutDate("20190115");
		roomReadSDO.setRoomCnt(1);
		roomReadSDO.setAdultCnt(2);
		roomReadSDO.setChildCnt(0);
		
		//interface api call
		RoomReadOutSDO out = outIfAdapter.callRoomRead(userAgentDTO, roomReadSDO);
		
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
		
		cancelFeePsrcSDO.setPdtNo("KRSEL217");
		cancelFeePsrcSDO.setCheckInDate("20190114");
		cancelFeePsrcSDO.setCheckOutDate("20190115");
		cancelFeePsrcSDO.setRoomNo("/YyrSCv5EXWAKqq6inTqsKPC3UdfZLeQ^jRHi04nUsZootJAddvlwgX5VcmUcrVrTNdxUL3SanpIteQGXSLtgqDan9^VFYmUVbzildelRxVQvda/Utk5N5kRhtrMvCgr0fX4foMqbmonu/9YiWjWAw9iPKxyPwzOQy/IHuFreyL3c6L9ARzXM^/^vPOaFRi4jgBWmg2kmYTojU2RkaW7VFJuipoJtxHncQB5qI7vq0s=");
		cancelFeePsrcSDO.setRoomCnt(1);
		
		//interface api call
		CancelFeePsrcOutSDO out = outIfAdapter.callCancelFeePsrc(userAgentDTO, cancelFeePsrcSDO);
		
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
		
		data.setRsvNo("E181226002");		
		data.setRsvDatetime("20181226180000");
		data.setRsvPrice(48000);
		data.setRsvStat("r02");
		data.setRsvPdtName("유니크 비즈니스 호텔");
		data.setRsvPdtNo("P181226002");
		data.setPdtNo("KRSEL217");
		data.setPdtName("유니크 비즈니스 호텔");
		data.setRoomNo("/YyrSCv5EXWAKqq6inTqsKPC3UdfZLeQ^jRHi04nUsZootJAddvlwgX5VcmUcrVrTNdxUL3SanpIteQGXSLtgqDan9^VFYmUVbzildelRxVQvda/Utk5N5kRhtrMvCgr0fX4foMqbmonu/9YiWjWAw9iPKxyPwzOQy/IHuFreyL3c6L9ARzXM^/^vPOaFRi4jgBWmg2kmYTojU2RkaW7VFJuipoJtxHncQB5qI7vq0s=");
		data.setRoomName("Twin Room(2 single beds request)");
		data.setRoomCnt(1);
		data.setCheckInDate("20190114");
		data.setCheckOutDate("20190115");
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
		RsvHistSendOutSDO out = outIfAdapter.callRsvHistSend(userAgentDTO, rsvHistSendSDO);
		
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
		
		cancelFeeAmtSDO.setRsvNo("E181226002");
		
		//interface api call
		CancelFeeAmtOutSDO out = outIfAdapter.callCancelFeeAmt(userAgentDTO, cancelFeeAmtSDO);
		
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
		
		orderCancelReqSDO.setRsvNo("E181226002");
		orderCancelReqSDO.setOtaRsvNo("1812260291-1");
		orderCancelReqSDO.setRsvPrice(58000);
		orderCancelReqSDO.setCancelCharge(0);
		
		//interface api call
		OrderCancelReqOutSDO out = outIfAdapter.callOrderCancelReq(userAgentDTO, orderCancelReqSDO);
		
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
		
		omiNumIdnSDO.setRsvNo("E181226002");
		omiNumIdnSDO.setRsvStat("r02");
		
		//interface api call
		OmiNumIdnOutSDO out = outIfAdapter.callOmiNumIdn(userAgentDTO, omiNumIdnSDO);
		
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
		
		ezwelJobSDO.setRsvNo("");
		ezwelJobSDO.setRsvDateStart("20190101");
		ezwelJobSDO.setRsvDateEnd("20190115");
		
		//interface api call
		EzwelJobOutSDO out = outIfAdapter.callEzwelJob(userAgentDTO, ezwelJobSDO);
		
		logger.debug("Code : {}", out.getCode());
		logger.debug("Message : {}", out.getMessage());
		logger.debug("Reserves : {}", out.getReserves());
		
		logger.debug("[END] callEzwelJob");
	}
	
}
