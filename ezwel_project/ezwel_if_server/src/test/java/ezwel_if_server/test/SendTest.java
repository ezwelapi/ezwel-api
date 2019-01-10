package ezwel_if_server.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.server.commons.send.MailSender;
import com.ezwel.htl.interfaces.server.commons.send.MailSenderService;
import com.ezwel.htl.interfaces.server.commons.send.SmsSender;
import com.ezwel.htl.interfaces.server.commons.send.data.SmsSenderInSDO;
import com.ezwel.htl.interfaces.server.commons.send.data.SmsSenderOutSDO;

public class SendTest {

	private static final Logger logger = LoggerFactory.getLogger(SendTest.class);
	
	private SmsSender smsSender;
	
	private MailSenderService mailSenderService;
	
	private MailSender mailSender;
	
	public SendTest() {
		
		InterfaceFactory.initLocalTestInterfaceFactory();
		
		smsSender = new SmsSender();
		
		mailSenderService = new MailSenderService();
		
		mailSender = new MailSender();
	}
	
	//문자
	@Test
	public void smsSenderTest()  throws Exception {
		
		logger.debug("[START] smsSenderTest");
		
		//request value
		String callTo = "01037440698";
		String callFrom = "0232820579";
		String mmsSubject = "테스트";
		String smsTxt = "대기예약 확정이 가능합니다. 예약 확정은 2019-01-15 18:00 시간 내에 홈페이지에서 해주셔야 하며, 이후 자동 취소 됩니다.  - 시설 : 부산파라다이스호텔 - 일시 : 2019-01-17 이지웰 복지몰 서비스를 이용해 주셔서 감사합니다.";
		String svcType = "1008";
		String smsUseYn = "N";
		String templateCode = "10052";
		
		//Input parameter
		SmsSenderInSDO smsSenderSDO = new SmsSenderInSDO();
		smsSenderSDO.setCallTo(callTo);
		smsSenderSDO.setCallFrom(callFrom);
		smsSenderSDO.setMmsSubject(mmsSubject);
		smsSenderSDO.setSmsText(smsTxt);
		smsSenderSDO.setSvcType(svcType);
		smsSenderSDO.setSmsUseYn(smsUseYn);
		smsSenderSDO.setTemplateCode(templateCode);
		
		//interface api call
		SmsSenderOutSDO out = smsSender.callSmsSender(smsSenderSDO);
		
		logger.debug("errorCode : {}", out.getErrorCode());
		logger.debug("errorMessage : {}", out.getErrorMessage());
		logger.debug("data : {}", out.getData());
		
		logger.debug("[END] smsSenderTest");
	}
	
	//메일	
	public void MailSenderNaverTest() {
		
		logger.debug("[START] MailSenderGmailTest");
		
		mailSender.naverSend();
		
		logger.debug("[END] MailSenderGmailTest");
	}
	
	public void MailSenderDevTest() {
		
		logger.debug("[START] MailSenderDevTest");
		
		String from = "java124@naver.com";
		String fromName = "전용필";
		String recipient = "jyp0698@gmail.com"; 
		String subject = "메일 제목 테스트";
		String body = "메일 내용 테스트";
		
		mailSenderService.asyncSimpleSend(from, fromName, recipient, subject, body);
		
		logger.debug("[END] MailSenderDevTest");
	}
}
