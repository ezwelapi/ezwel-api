package sample;

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
		
		userAgentDTO.setHttpAgentId("10000496"); //펜션라이프
		userAgentDTO.setHttpAgentType("AP02PO");
		userAgentDTO.setHttpChannelCd("1");
		userAgentDTO.setHttpClientId("ez1");
		userAgentDTO.setHttpRequestId("test");
		
		//Input parameter
		RoomReadInSDO roomReadSDO = new RoomReadInSDO();
		
		roomReadSDO.setOtaId("10000496");
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
		
		userAgentDTO.setHttpAgentId("10000496"); //펜션라이프
		userAgentDTO.setHttpAgentType("AP02PO");
		userAgentDTO.setHttpChannelCd("1");
		userAgentDTO.setHttpClientId("ez1");
		userAgentDTO.setHttpRequestId("test");
		
		//Input parameter
		CancelFeePsrcInSDO sdo = new CancelFeePsrcInSDO();
		
		sdo.setOtaId("10000496");
		sdo.setPdtNo("1");
		sdo.setCheckInDate("20190101");
		sdo.setCheckOutDate("20190102");
		sdo.setRoomNo("1");
		sdo.setRoomCnt(1);
		
		//interface api call
		CancelFeePsrcOutSDO out = outIfService.callCancelFeePsrc(userAgentDTO, sdo);
		
		logger.debug("Code : {}", out.getCode());
		logger.debug("Message : {}", out.getMessage());
		logger.debug("Data : {}", out.getData());

		logger.debug("[END] callCancelFeePsrc");
	}
	
	// 결제완료내역전송
	public void testRsvHistSend()  throws Exception {		
		logger.debug("[START] callRsvHistSend");
		
		UserAgentSDO userAgentDTO = new UserAgentSDO();
		
		userAgentDTO.setHttpAgentId("10000496"); //펜션라이프
		userAgentDTO.setHttpAgentType("AP02PO");
		userAgentDTO.setHttpChannelCd("1");
		userAgentDTO.setHttpClientId("ez1");
		userAgentDTO.setHttpRequestId("test");
		
		//Input parameter
		RsvHistSendInSDO rsvHistSendInSDO = new RsvHistSendInSDO();
		
		RsvHistSendDataInSDO data = new RsvHistSendDataInSDO();
		
		data.setRsvNo("123456789");		
		data.setRsvDatetime("20181113152332");
		data.setRsvPrice(200000);
		data.setRsvStat("r02");
		data.setRsvPdtName("");
		data.setRsvPdtNo("");
		data.setPdtNo("1");
		data.setPdtName("서울 프라자 호텔");
		data.setRoomNo("1");
		data.setRoomName("디럭스");
		data.setRoomCnt(1);
		data.setCheckInDate("20181201");
		data.setCheckOutDate("20181202");
		data.setMemKey("EZ0001");
		data.setMemName("홍길동");
		data.setMemPhone("01012341234");
		data.setMemEmail("test@test.com");
		data.setUserName("홍길동");
		data.setUserMobile("01012341234");
		data.setUserEmail("test@test.com");
		data.setUserCmt("전망 좋은 방으로 요청 드립니다");
		data.setAdultCnt(2);
		data.setChildCnt(1);
		
		rsvHistSendInSDO.setData(data);
		
		//interface api call
		RsvHistSendOutSDO out = outIfService.callRsvHistSend(userAgentDTO, rsvHistSendInSDO);
		
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
		
		userAgentDTO.setHttpAgentId("10000496"); //펜션라이프
		userAgentDTO.setHttpAgentType("AP02PO");
		userAgentDTO.setHttpChannelCd("1");
		userAgentDTO.setHttpClientId("ez1");
		userAgentDTO.setHttpRequestId("test");
		
		//Input parameter
		CancelFeeAmtInSDO sdo = new CancelFeeAmtInSDO();
		
		sdo.setOtaId("10000496");
		sdo.setRsvNo("12345678");
		
		//interface api call
		CancelFeeAmtOutSDO out = outIfService.callCancelFeeAmt(userAgentDTO, sdo);
		
		logger.debug("Code : {}", out.getCode());
		logger.debug("Message : {}", out.getMessage());
		logger.debug("Data : {}", out.getData());
		logger.debug("[END] callCancelFeeAmt");
	}
	
	// 주문취소요청
	public void testOrderCancelReq()  throws Exception {		
		logger.debug("[START] callOrderCancelReq");
		
		UserAgentSDO userAgentDTO = new UserAgentSDO();
		
		userAgentDTO.setHttpAgentId("10000496"); //펜션라이프
		userAgentDTO.setHttpAgentType("AP02PO");
		userAgentDTO.setHttpChannelCd("1");
		userAgentDTO.setHttpClientId("ez1");
		userAgentDTO.setHttpRequestId("test");
		
		//Input parameter
		OrderCancelReqInSDO orderCancelReqInSDO = new OrderCancelReqInSDO();
		
		orderCancelReqInSDO.setOtaId("10000496");
		orderCancelReqInSDO.setRsvNo("12345678");
		orderCancelReqInSDO.setOtaRsvNo("12345678");
		orderCancelReqInSDO.setRsvPrice(100000);
		orderCancelReqInSDO.setCancelCharge(0);
		
		//interface api call
		OrderCancelReqOutSDO out = outIfService.callOrderCancelReq(userAgentDTO, orderCancelReqInSDO);
		
		logger.debug("Code : {}", out.getCode());
		logger.debug("Message : {}", out.getMessage());
		logger.debug("[END] callOrderCancelReq");
	}
	
	// 누락건확인
	public void testOmiNumIdn()  throws Exception {		
		logger.debug("[START] callOmiNumIdn");
		
		UserAgentSDO userAgentDTO = new UserAgentSDO();
		
		userAgentDTO.setHttpAgentId("10000496"); //펜션라이프
		userAgentDTO.setHttpAgentType("AP02PO");
		userAgentDTO.setHttpChannelCd("1");
		userAgentDTO.setHttpClientId("ez1");
		userAgentDTO.setHttpRequestId("test");
		
		//Input parameter
		OmiNumIdnInSDO omiNumIdnInSDO = new OmiNumIdnInSDO();
		
		omiNumIdnInSDO.setOtaId("10000496");
		omiNumIdnInSDO.setRsvNo("12345678");
		omiNumIdnInSDO.setRsvStat("Y");
		
		//interface api call
		OmiNumIdnOutSDO out = outIfService.callOmiNumIdn(userAgentDTO, omiNumIdnInSDO);
		
		logger.debug("Code : {}", out.getCode());
		logger.debug("Message : {}", out.getMessage());
		logger.debug("Reserves : {}", out.getReserves());
		logger.debug("[END] callOmiNumIdn");
	}
	


	// 주문대사(이지웰)
	public void testEzwelJob()  throws Exception {		
		logger.debug("[START] callEzwelJob");
		
		UserAgentSDO userAgentDTO = new UserAgentSDO();
		
		userAgentDTO.setHttpAgentId("10000496"); //펜션라이프
		userAgentDTO.setHttpAgentType("AP02PO");
		userAgentDTO.setHttpChannelCd("1");
		userAgentDTO.setHttpClientId("ez1");
		userAgentDTO.setHttpRequestId("test");
		
		//Input parameter
		EzwelJobInSDO ezwelJobInSDO = new EzwelJobInSDO();
		
		ezwelJobInSDO.setOtaId("10000496");
		ezwelJobInSDO.setRsvNo("111111-1111111-1111");
		ezwelJobInSDO.setRsvDateStart("20181201");
		ezwelJobInSDO.setRsvDateEnd("20181211");
		
		//interface api call
		EzwelJobOutSDO out = outIfService.callEzwelJob(userAgentDTO, ezwelJobInSDO);
		
		logger.debug("Code : {}", out.getCode());
		logger.debug("Message : {}", out.getMessage());
		logger.debug("Reserves : {}", out.getReserves());
		
		logger.debug("[END] callEzwelJob");
	}
	
}
