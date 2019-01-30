package interfaces.example;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.service.SendIFService;
import com.ezwel.htl.interfaces.service.data.send.FaxSenderInSDO;
import com.ezwel.htl.interfaces.service.data.send.FaxSenderOutSDO;

public class SendFaxTest {

	private static final Logger logger = LoggerFactory.getLogger(SendFaxTest.class);
	
	private SendIFService sendIFService;
	
	public SendFaxTest() {
		
		InterfaceFactory.initLocalTestInterfaceFactory();
		
		sendIFService = new SendIFService();

	}
	
	@Test
	public void callFaxSenderTest()  throws Exception {
		
		logger.debug("[START] callFaxSenderTest");
		
//		webfax 파일 경로
//		수신fax : /Volume/data/nas_image/webfax/receive/
//		송신fax : /Volume/data/nas_image/webfax/send/
		
		//Input value
		String trTitle = "팩스발신 테스트"; 				//발송제목
		String trSendFaxNum = "0232828600"; 			//발신자팩스번호
		String trDocName = "send/ez_checkin_test.html"; //발송파일정보
		
		//Input parameter
		FaxSenderInSDO faxSenderInSDO = new FaxSenderInSDO();
		faxSenderInSDO.setTrTitle(trTitle);
		faxSenderInSDO.setTrSendFaxNum(trSendFaxNum);
		faxSenderInSDO.setTrDocName(trDocName);
		
		//interface api call
		FaxSenderOutSDO out = (FaxSenderOutSDO) sendIFService.callFaxSender(faxSenderInSDO,true);
		
		logger.debug("[MSG] callFaxSenderTest {} {}", out);
		
		logger.debug("[END] callFaxSenderTest");
	}
	
}
