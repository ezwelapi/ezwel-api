package interfaces.example;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.service.SendIFService;
import com.ezwel.htl.interfaces.service.data.send.MailSenderInSDO;
import com.ezwel.htl.interfaces.service.data.send.MailSenderOutSDO;
import com.ezwel.htl.interfaces.service.data.send.SmsSenderInSDO;
import com.ezwel.htl.interfaces.service.data.send.SmsSenderOutSDO;

public class SendMailTest {

	private static final Logger logger = LoggerFactory.getLogger(SendMailTest.class);
	
	private SendIFService sendIFService;
	
	public SendMailTest() {
		
		InterfaceFactory.initLocalTestInterfaceFactory();
		
		sendIFService = new SendIFService();

	}
	
	@Test
	public void smsSenderTest()  throws Exception {
		
		logger.debug("[START] smsSenderTest");
		
		String recipient = "java124@naver.com"; 
		String subject = "메일 제목 테스트";
		String body = "메일 내용 테스트";
		
		MailSenderInSDO mailSenderInSDO = new MailSenderInSDO();
		mailSenderInSDO.setRecipient(recipient);
		mailSenderInSDO.setSubject(subject);
		mailSenderInSDO.setBody(body);
		
		//interface api call
		MailSenderOutSDO out = new MailSenderOutSDO();		
		out = sendIFService.callMailSender(mailSenderInSDO, false);
		
		logger.debug("[END] smsSenderTest");
	}
	
}
