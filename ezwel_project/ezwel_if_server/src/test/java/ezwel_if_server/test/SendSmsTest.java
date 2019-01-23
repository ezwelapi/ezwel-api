package ezwel_if_server.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.server.service.SendService;
import com.ezwel.htl.interfaces.service.data.send.SmsSenderInSDO;
import com.ezwel.htl.interfaces.service.data.send.SmsSenderOutSDO;

public class SendSmsTest {

	private static final Logger logger = LoggerFactory.getLogger(SendSmsTest.class);
	
	private SendService sendService;
	
	public SendSmsTest() {
		
		InterfaceFactory.initLocalTestInterfaceFactory();
		
		sendService = new SendService();

	}
	
	@Test
	public void callSmsSenderTest() throws Exception {
		
		logger.debug("[START] callSmsSenderTest");
		
		//Input value
		String callTo = "01037440698";
		String smsTxt = "대기예약 확정이 가능합니다. 예약 확정은 2019-01-15 18:00 시간 내에 홈페이지에서 해주셔야 하며, 이후 자동 취소 됩니다.  - 시설 : 부산파라다이스호텔 - 일시 : 2019-01-17 이지웰 복지몰 서비스를 이용해 주셔서 감사합니다.";
		String templateCode = "10052";
		
		//Input parameter
		SmsSenderInSDO smsSenderInSDO = new SmsSenderInSDO();
		smsSenderInSDO.setCallTo(callTo); 				// 필수
		smsSenderInSDO.setSmsTxt(smsTxt); 				// 필수
		smsSenderInSDO.setTemplateCode(templateCode); 	// 선택 ( 카카오톡메세지일경우 필수 )
		
		//interface api call		
		SmsSenderOutSDO out = (SmsSenderOutSDO) sendService.callSmsSender(smsSenderInSDO);
		
		logger.debug("[MSG] callSmsSenderTest {} {}", out);
		
		logger.debug("[END] callSmsSenderTest");
	}
	
}
