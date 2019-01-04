package ezwel_if_server.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.utils.CryptUtil;
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
	public void smsSenderTest()  throws Exception {
		
		logger.debug("[START] smsSenderTest");
		
		//request value
		String callTo = "01037440698";
		String callFrom = "0232820579";
		String msgType = "SMS";
		String mmsSubject = "테스트";
		String smsText = "API 발송 테스트";
		String svcType = "1008";
		String smsUseYn = "Y";
		String templateCode = "10013";
		String serviceSeqno = "1710002752";
		
		String ebcallTo = CryptUtil.getEncodeBase64(callTo);
		String ebcallFrom = CryptUtil.getEncodeBase64(callFrom);
		String ebmsgType = CryptUtil.getEncodeBase64(msgType);
		String ebmmsSubject = CryptUtil.getEncodeBase64(mmsSubject);
		String ebsmsText = CryptUtil.getEncodeBase64(smsText);
		String ebsvcType = CryptUtil.getEncodeBase64(svcType);
		String ebsmsUseYn = CryptUtil.getEncodeBase64(smsUseYn);
		String ebtemplateCode = CryptUtil.getEncodeBase64(templateCode);
		String ebserviceSeqno = CryptUtil.getEncodeBase64(serviceSeqno);
		
		//Input parameter
		SmsSenderInSDO smsSenderSDO = new SmsSenderInSDO();
		
		smsSenderSDO.setCallTo(ebcallTo);
		smsSenderSDO.setCallFrom(ebcallFrom);
		smsSenderSDO.setMsgType(ebmsgType);
		smsSenderSDO.setMmsSubject(ebmmsSubject);
		smsSenderSDO.setSmsText(ebsmsText);
		smsSenderSDO.setSvcType(ebsvcType);
		smsSenderSDO.setSmsUseYn(ebsmsUseYn);
		smsSenderSDO.setTemplateCode(ebtemplateCode);
		smsSenderSDO.setServiceSeqno(ebserviceSeqno);
		
		//interface api call
		SmsSenderOutSDO out = smsSender.callSmsSender(smsSenderSDO);
		
		logger.debug("ebcallTo getDecodeBase64 : {}", CryptUtil.getDecodeBase64(ebcallTo));
		logger.debug("ebcallFrom getDecodeBase64 : {}", CryptUtil.getDecodeBase64(ebcallFrom));
		logger.debug("ebmsgType getDecodeBase64 : {}", CryptUtil.getDecodeBase64(ebmsgType));
		logger.debug("ebmmsSubject getDecodeBase64 : {}", CryptUtil.getDecodeBase64(ebmmsSubject));
		logger.debug("ebsmsText getDecodeBase64 : {}", CryptUtil.getDecodeBase64(ebsmsText));
		logger.debug("ebsvcType getDecodeBase64 : {}", CryptUtil.getDecodeBase64(ebsvcType));
		logger.debug("ebsmsUseYn getDecodeBase64 : {}", CryptUtil.getDecodeBase64(ebsmsUseYn));
		logger.debug("ebtemplateCode getDecodeBase64 : {}", CryptUtil.getDecodeBase64(ebtemplateCode));
		logger.debug("ebserviceSeqno getDecodeBase64 : {}", CryptUtil.getDecodeBase64(ebserviceSeqno));
		
		logger.debug("errorCode : {}", out.getErrorCode());
		logger.debug("errorMessage : {}", out.getErrorMessage());
		logger.debug("data : {}", out.getData());
		
		logger.debug("[END] smsSenderTest");
	}
	
	//메일
	@Test
	public void MailSenderGmailTest() {
		
		logger.debug("[START] MailSenderGmailTest");
		
		mailSender.gmailSend();
		
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
