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
	public void callMailSenderTest()  throws Exception {
		
		logger.debug("[START] callMailSenderTest");
		
		//Input value
		String recipient = "jyp0698@gmail.com"; 
		String subject = "메일 제목 테스트 2019 02 11";
		String body = "메일 내용 테스트";
		
		//Input parameter
		MailSenderInSDO mailSenderInSDO = new MailSenderInSDO();
		mailSenderInSDO.setRecipient(recipient);
		mailSenderInSDO.setSubject(subject);
		mailSenderInSDO.setBody(body);
		
		//interface api call
		MailSenderOutSDO out = (MailSenderOutSDO) sendService.callMailSender(mailSenderInSDO);
		
		logger.debug("[MSG] callMailSenderTest {} {}", out);
		
		logger.debug("[END] callMailSenderTest");
	}
	
}
