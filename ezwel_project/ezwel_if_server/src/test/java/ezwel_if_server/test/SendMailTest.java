package ezwel_if_server.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.server.service.SendService;
import com.ezwel.htl.interfaces.service.data.send.MailSenderInSDO;
import com.ezwel.htl.interfaces.service.data.send.MailSenderOutSDO;

public class SendMailTest {

	private static final Logger logger = LoggerFactory.getLogger(SendMailTest.class);
	
	private SendService sendService;
	
	public SendMailTest() {
		
		InterfaceFactory.initLocalTestInterfaceFactory();
		
		sendService = new SendService();

	}
	
	@Test
	public void callMailSender()  throws Exception {
		
		logger.debug("[START] callMailSender");
		
		//Input value
		String recipient = "java124@naver.com"; 
		String subject = "메일 제목 테스트";
		String body = "메일 내용 테스트";
		
		//Input parameter
		MailSenderInSDO mailSenderInSDO = new MailSenderInSDO();
		mailSenderInSDO.setRecipient(recipient);
		mailSenderInSDO.setSubject(subject);
		mailSenderInSDO.setBody(body);
		
		//interface api call
		MailSenderOutSDO out = new MailSenderOutSDO();
		out = (MailSenderOutSDO) sendService.callMailSender(mailSenderInSDO);
		
		logger.debug("[END] callMailSender");
	}
	
}
