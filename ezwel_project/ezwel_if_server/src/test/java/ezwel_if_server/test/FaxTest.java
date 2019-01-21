package ezwel_if_server.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.send.data.FaxSenderSDO;
import com.ezwel.htl.interfaces.server.service.SendService;

public class FaxTest {

	private static final Logger logger = LoggerFactory.getLogger(FaxTest.class);
	
	private SendService sendService;
	
	public FaxTest() {
		
		InterfaceFactory.initLocalTestInterfaceFactory();
		
		sendService = new SendService();
		
	}
	
	@Test
	public void FaxSenderTest() {
		
		logger.debug("[START] FaxSenderTest");
		
		String trTitle = "팩스발신 테스트"; 	//발송제목
		String trSendName = "이지웰"; 		//발신자이름
		String trSendFaxNum = "0269191002"; //발신자팩스번호
		String trDocName = ""; 				//발송파일정보
		String trName = "이지웰"; 			//수신자명
		String trPhone = "0269191002"; 		//수신자팩스번호
		
		FaxSenderSDO faxSenderSDO = new FaxSenderSDO();
		faxSenderSDO.setTrTitle(trTitle);
		faxSenderSDO.setTrSendName(trSendName);
		faxSenderSDO.setTrSendFaxNum(trSendFaxNum);
		faxSenderSDO.setTrDocName(trDocName);
		faxSenderSDO.setTrName(trName);
		faxSenderSDO.setTrPhone(trPhone);
		
		sendService.callFaxSender(faxSenderSDO);
		
		logger.debug("[END] FaxSenderTest");
	}
}
