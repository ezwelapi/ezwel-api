package ezwel_if_server.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.send.data.SmsSenderInSDO;
import com.ezwel.htl.interfaces.commons.send.data.SmsSenderOutSDO;
import com.ezwel.htl.interfaces.server.commons.sdo.MailSenderSDO;
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
	
	//문자
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
		SmsSenderOutSDO out = sendIFService.callSmsSender(smsSenderSDO);
		
		logger.debug("data : {}", out.getData());
		
		logger.debug("[END] smsSenderTest");
	}
	
	//메일
	@Test
	public void MailSenderTest() {
		
		logger.debug("[START] MailSenderTest");
		
		String from = "java124@naver.com";
		String fromName = "전용필";
		String recipient = "jyp0698@gmail.com"; 
		String subject = "메일 제목 테스트";
		String body = "메일 내용 테스트";
		
		MailSenderSDO mailSenderSDO = new MailSenderSDO();
		mailSenderSDO.setFrom(from);
		mailSenderSDO.setFromName(fromName);
		mailSenderSDO.setRecipient(recipient);
		mailSenderSDO.setSubject(subject);
		mailSenderSDO.setBody(body);
		
		sendService.callMailSender(mailSenderSDO);
		
		logger.debug("[END] MailSenderTest");
	}
}
