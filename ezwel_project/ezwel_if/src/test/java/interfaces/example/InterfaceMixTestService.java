package interfaces.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.adapter.OutsideIFAdapter;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
import com.ezwel.htl.interfaces.service.OutsideIFService;
import com.ezwel.htl.interfaces.service.SendIFService;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadInSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadOutSDO;

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
	
	private SendIFService sendIFService; // if Service
	
	/**
	 * 아레 컨트스럭터는 로컬테스트 떄만 사용한다.
	 * InterfaceFactory는 스프링 어플리케이션 초기화시 스프링 빈으로 초기화된다.
	 */
	public InterfaceMixTestService()  throws Exception {
		InterfaceFactory.initLocalTestInterfaceFactory();
		
		outIfAdapter = new OutsideIFAdapter();
		outIfService = new OutsideIFService();
		sendIFService = new SendIFService();
	}


	// 객실정보조회
	public void testRoomRead()  throws Exception {
		logger.debug("[START] callRoomRead");
		
		UserAgentSDO userAgentDTO = new UserAgentSDO();
		
		userAgentDTO.setHttpAgentId("99999999"); //호텔패스
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
		RoomReadOutSDO out = outIfService.callRoomRead(userAgentDTO, roomReadSDO);
		
		logger.debug("Code : {}", out.getCode());
		logger.debug("Message : {}", out.getMessage());
		logger.debug("Data : {}", out.getData());
		
		logger.debug("[END] callRoomRead");
	}
	
	
//	// 주문대사(이지웰)
//	public void testEzwelJob()  throws Exception {		
//		logger.debug("[START] callEzwelJob");
//		
//		UserAgentSDO userAgentDTO = new UserAgentSDO();
//		
//		userAgentDTO.setHttpAgentId("10055550"); //호텔패스
//		userAgentDTO.setHttpAgentType("AP02PO");
//		userAgentDTO.setHttpChannelCd("1");
//		userAgentDTO.setHttpClientId("ez1");
//		userAgentDTO.setHttpRequestId("test");
//		
//		//Input parameter
//		EzwelJobInSDO ezwelJobSDO = new EzwelJobInSDO();
//		
//		ezwelJobSDO.setRsvNo("E181226002");
//		ezwelJobSDO.setRsvDateStart("20181201");
//		ezwelJobSDO.setRsvDateEnd("20181226");
//		
//		//interface api call
//		EzwelJobOutSDO out = outIfAdapter.callEzwelJob(userAgentDTO, ezwelJobSDO);
//		
//		logger.debug("Code : {}", out.getCode());
//		logger.debug("Message : {}", out.getMessage());
//		logger.debug("Reserves : {}", out.getReserves());
//		
//		logger.debug("[END] callEzwelJob");
//	}
	
}
