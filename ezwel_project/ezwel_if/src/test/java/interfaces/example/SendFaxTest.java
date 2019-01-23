package interfaces.example;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.service.SendIFService;
import com.ezwel.htl.interfaces.service.data.send.SmsSenderInSDO;
import com.ezwel.htl.interfaces.service.data.send.SmsSenderOutSDO;

public class SendFaxTest {

	private static final Logger logger = LoggerFactory.getLogger(SendFaxTest.class);
	
	private SendIFService sendIFService;
	
	public SendFaxTest() {
		
		InterfaceFactory.initLocalTestInterfaceFactory();
		
		sendIFService = new SendIFService();

	}
	
	@Test
	public void smsSenderTest()  throws Exception {
		
		logger.debug("[START] smsSenderTest");
		
////	webfax 파일 경로
////	수신fax : /Volume/data/nas_image/webfax/receive/
////	송신fax : /Volume/data/nas_image/webfax/send/
//	  
//	String trTitle = "팩스발신 테스트"; 	//발송제목
//	String trSendName = "이지웰"; 		//발신자이름
//	String trSendFaxNum = "0269191002"; //발신자팩스번호
//	String trDocName = ""; 				//발송파일정보
//	String trName = "이지웰"; 			//수신자명
//	String trPhone = "0269191002"; 		//수신자팩스번호
//	
//	FaxSenderIOSDO faxSenderSDO = new FaxSenderIOSDO();
//	faxSenderSDO.setTrTitle(trTitle);
//	faxSenderSDO.setTrSendName(trSendName);
//	faxSenderSDO.setTrSendFaxNum(trSendFaxNum);
//	faxSenderSDO.setTrDocName(trDocName);
//	faxSenderSDO.setTrName(trName);
//	faxSenderSDO.setTrPhone(trPhone);
		
		//request value
		String callTo = "01037440698";
		String smsTxt = "대기예약 확정이 가능합니다. 예약 확정은 2019-01-15 18:00 시간 내에 홈페이지에서 해주셔야 하며, 이후 자동 취소 됩니다.  - 시설 : 부산파라다이스호텔 - 일시 : 2019-01-17 이지웰 복지몰 서비스를 이용해 주셔서 감사합니다.";
		String templateCode = "10052";
		
		//Input parameter
		SmsSenderInSDO smsSenderInSDO = new SmsSenderInSDO();
		smsSenderInSDO.setCallTo(callTo); 			// 필수
		smsSenderInSDO.setSmsTxt(smsTxt); 			// 필수
		smsSenderInSDO.setTemplateCode(templateCode); // 선택 ( 카카오톡메세지일경우 필수 )
		
		//interface api call
		SmsSenderOutSDO out = new SmsSenderOutSDO();		
		out = sendIFService.callSmsSender(smsSenderInSDO, false);
		
		logger.debug("[END] smsSenderTest");
	}
	
}
