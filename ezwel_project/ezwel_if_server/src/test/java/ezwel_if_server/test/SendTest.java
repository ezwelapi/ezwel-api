package ezwel_if_server.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.send.data.MailSenderSDO;
import com.ezwel.htl.interfaces.server.service.SendService;
import com.ezwel.htl.interfaces.service.SendIFService;

public class SendTest {

	private static final Logger logger = LoggerFactory.getLogger(SendTest.class);
	
	private SendIFService sendIFService;
	
	private SendService sendService;
	
	public SendTest() {
		
		InterfaceFactory.initLocalTestInterfaceFactory();
		
		sendIFService = new SendIFService();
		
		sendService = new SendService();
		
	}
	
	//메일
	@Test
	public void MailSenderTest() {
		
		logger.debug("[START] MailSenderTest");
		
		String recipient = "java124@naver.com"; 
		String subject = "메일 제목 테스트";
		String body = "메일 내용 테스트";
		
		MailSenderSDO mailSenderSDO = new MailSenderSDO();
		mailSenderSDO.setRecipient(recipient);
		mailSenderSDO.setSubject(subject);
		mailSenderSDO.setBody(body);
		
		sendService.callMailSender(mailSenderSDO);
		
		logger.debug("[END] MailSenderTest");
	}
}
