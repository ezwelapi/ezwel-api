package com.ezwel.htl.interfaces.server.commons.send;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.server.commons.intercepter.HandlerInterceptor;

@Component
@APIType(description="메일발송")
public class MailSender {
	
	private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);
	
	static final String CONFIGSET = "Configset";
    
	/**
	 * 메일전송
	 * @param recipient 수신자
	 * @param subject 제목
	 * @param body 내용
	 * @throws Exception
	 */
	@APIOperation(description="메일발송 인터페이스")
	protected void callMailSender(String recipient, String subject, String body) throws Exception {
		
		String host = InterfaceFactory.getOptionalApps().getEmailConfig().getHost();
		String port = InterfaceFactory.getOptionalApps().getEmailConfig().getPort();
		String from = InterfaceFactory.getOptionalApps().getEmailConfig().getFrom();
		String fromName = InterfaceFactory.getOptionalApps().getEmailConfig().getFromName();
		String userName = InterfaceFactory.getOptionalApps().getEmailConfig().getUserName();
		String passWord = InterfaceFactory.getOptionalApps().getEmailConfig().getPassWord();
		String connTimeout = InterfaceFactory.getOptionalApps().getEmailConfig().getConnTimeout();
		String readTimeout = InterfaceFactory.getOptionalApps().getEmailConfig().getReadTimeout();
		
		//properties 설정
		Properties props = System.getProperties();
    	props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.port", port); 
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.connectiontimeout", connTimeout);
	    props.put("mail.smtp.timeout", readTimeout);
		
	    Session session = Session.getDefaultInstance(props);
	    
	    //MimeMessage 설정
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from, fromName));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        msg.setSubject(subject);
        msg.setContent(body,"text/html");
        msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);
        
        Transport transport = session.getTransport();
        
        try
        {
        	logger.info("Send Mail Sending...");
            transport.connect(host, userName, passWord);
            transport.sendMessage(msg, msg.getAllRecipients());
            logger.info("Send Mail Sent");
        }
        catch (Exception ex) {
        	logger.info("Send Mail Error message" + ex.getMessage());
        }
        finally
        {
            transport.close();
        }

	}
	
}
