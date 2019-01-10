package interfaces.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.adapter.OutsideIFAdapter;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
import com.ezwel.htl.interfaces.service.OutsideIFService;
import com.ezwel.htl.interfaces.service.data.ezwelJob.EzwelJobInSDO;
import com.ezwel.htl.interfaces.service.data.ezwelJob.EzwelJobOutSDO;
import com.ezwel.htl.interfaces.service.data.rsvHistSend.RsvHistSendDataInSDO;
import com.ezwel.htl.interfaces.service.data.rsvHistSend.RsvHistSendInSDO;
import com.ezwel.htl.interfaces.service.data.rsvHistSend.RsvHistSendOutSDO;

import junit.framework.TestCase;

/**
 * <pre>
 * 테스트 도중 문제발생 케이스 디버깅용 테스트클래스
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 19.
 */
public class InterfaceMixTestService extends TestCase {

	private static final Logger logger = LoggerFactory.getLogger(InterfaceMixTestService.class);
	
	private OutsideIFAdapter outIfAdapter; // if Adapter
	
	private OutsideIFService outIfService; // if Service
	
	/**
	 * 아레 컨트스럭터는 로컬테스트 떄만 사용한다.
	 * InterfaceFactory는 스프링 어플리케이션 초기화시 스프링 빈으로 초기화된다.
	 */
	public InterfaceMixTestService()  throws Exception {
		InterfaceFactory.initLocalTestInterfaceFactory();
		
		outIfAdapter = new OutsideIFAdapter();
		outIfService = new OutsideIFService();
	}
/*
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
		
		data.setRsvNo("E190110001");		
		data.setRsvDatetime("20181226180000");
		data.setRsvPrice(48000);
		data.setRsvStat("R001OK"); //예약완료 (호텔패스 전달 에약상태코드 r02 로 변호나하여 전달 테스트 예약 진행 시 예약상태가 예약완료가 아닌 에약 정보는 예약처리 않함)
		data.setRsvPdtName("유니크 비즈니스 호텔");
		data.setRsvPdtNo("P190110001");
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
	*/
	
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
		
		//ezwelJobSDO.setRsvNo("E181226002");
		ezwelJobSDO.setRsvDateStart("20190101");
		ezwelJobSDO.setRsvDateEnd("20190110");
		
		//interface api call
		EzwelJobOutSDO out = outIfAdapter.callEzwelJob(userAgentDTO, ezwelJobSDO);
		
		logger.debug("Code : {}", out.getCode());
		logger.debug("Message : {}", out.getMessage());
		logger.debug("Reserves : {}", out.getReserves());
		
		logger.debug("[END] callEzwelJob");
	}
	
}
