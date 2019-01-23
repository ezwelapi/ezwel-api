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
		String trTitle = "팩스발신 테스트"; 	//발송제목
		String trSendName = "이지웰"; 		//발신자이름
		String trSendFaxNum = "0269191002"; //발신자팩스번호
		String trDocName = ""; 				//발송파일정보
		String trName = "이지웰"; 			//수신자명
		String trPhone = "0269191002"; 		//수신자팩스번호
		
		//Input parameter
		FaxSenderInSDO faxSenderInSDO = new FaxSenderInSDO();
		faxSenderInSDO.setTrTitle(trTitle);
		faxSenderInSDO.setTrSendName(trSendName);
		faxSenderInSDO.setTrSendFaxNum(trSendFaxNum);
		faxSenderInSDO.setTrDocName(trDocName);
		faxSenderInSDO.setTrName(trName);
		faxSenderInSDO.setTrPhone(trPhone);
		
		//interface api call
		FaxSenderOutSDO out = (FaxSenderOutSDO) sendIFService.callFaxSender(faxSenderInSDO);
		
		logger.debug("[MSG] callFaxSenderTest {} {}", out);
		
		logger.debug("[END] callFaxSenderTest");
	}
	
}
