package ezwel_if_server.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.server.service.SendService;
import com.ezwel.htl.interfaces.service.data.send.FaxSenderInSDO;
import com.ezwel.htl.interfaces.service.data.send.FaxSenderOutSDO;

public class SendFaxTest {

	private static final Logger logger = LoggerFactory.getLogger(SendFaxTest.class);
	
	private SendService sendService;
	
	public SendFaxTest() {
		
		InterfaceFactory.initLocalTestInterfaceFactory();
		
		sendService = new SendService();

	}
	
	@Test
	public void callFaxSenderTest()  throws Exception {
		
		logger.debug("[START] callFaxSenderTest");
		
		//Input value
		String trTitle = "팩스발신 테스트"; 	//발송제목
		String trSendFaxNum = "0269191002"; //발신자팩스번호
		String trDocName = ""; 				//발송파일정보
		
		//Input parameter
		FaxSenderInSDO faxSenderInSDO = new FaxSenderInSDO();
		faxSenderInSDO.setTrTitle(trTitle);
		faxSenderInSDO.setTrSendFaxNum(trSendFaxNum);
		faxSenderInSDO.setTrDocName(trDocName);
		
		//interface api call
		FaxSenderOutSDO out = (FaxSenderOutSDO) sendService.callFaxSender(faxSenderInSDO);
		
		logger.debug("[MSG] callFaxSenderTest {} {}", out);
		
		logger.debug("[END] callFaxSenderTest");
	}
	
}
