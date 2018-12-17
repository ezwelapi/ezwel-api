package sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.adapter.OutsideIFAdapter;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
import com.ezwel.htl.interfaces.service.OutsideIFService;
import com.ezwel.htl.interfaces.service.data.ezwelJob.EzwelJobInSDO;
import com.ezwel.htl.interfaces.service.data.ezwelJob.EzwelJobOutSDO;
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
	
	/**
	 * 아레 컨트스럭터는 로컬테스트 떄만 사용한다.
	 * InterfaceFactory는 스프링 어플리케이션 초기화시 스프링 빈으로 초기화된다.
	 */
	public InterfaceMixTestService()  throws Exception {
		InterfaceFactory factory = new InterfaceFactory();
		factory.setConfigXmlPath("/interfaces/interface-configure.xml");
		factory.initFactory();
		
		outIfAdapter = new OutsideIFAdapter();
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
		
		userAgentDTO.setConnTimeout(10000);
		userAgentDTO.setReadTimeout(20000);
		
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
		RoomReadOutSDO out = outIfAdapter.callRoomRead(userAgentDTO, roomReadSDO);
		
		logger.debug("Code : {}", out.getCode());
		logger.debug("Message : {}", out.getMessage());
		logger.debug("Data : {}", out.getData());
		
		logger.debug("[END] callRoomRead");
	}
	
}
