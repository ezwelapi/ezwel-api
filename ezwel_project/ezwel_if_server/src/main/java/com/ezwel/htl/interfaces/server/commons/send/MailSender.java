package com.ezwel.htl.interfaces.server.commons.send;

import java.util.ArrayList;
import java.util.List;
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
import com.ezwel.htl.interfaces.service.data.send.MailSenderInSDO;
import com.ezwel.htl.interfaces.service.data.send.MailSenderOutSDO;

@Component
@APIType(description="메일발송")
public class MailSender {
	
	private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);
	
	static final String CONFIGSET = "Configset";
    
	public List<MailSenderOutSDO> callMailSender(List<MailSenderInSDO> mailSenderInList) throws Exception {
		List<MailSenderOutSDO> out = null;
		
		if(mailSenderInList != null) {
			out = new ArrayList<MailSenderOutSDO>();
			for(MailSenderInSDO mail : mailSenderInList) {
				out.add(callMailSender(mail));
			}
		}
		
		return out;
	}
	
	/**
	 * 메일전송
	 * @param recipient 수신자
	 * @param subject 제목
	 * @param body 내용
	 * @throws Exception
	 */
	@APIOperation(description="메일발송 인터페이스")
	public MailSenderOutSDO callMailSender(MailSenderInSDO mailSenderInSDO) throws Exception {
		
		String host			= InterfaceFactory.getOptionalApps().getMailConfig().getHost();
		String port 		= InterfaceFactory.getOptionalApps().getMailConfig().getPort();
		String from 		= InterfaceFactory.getOptionalApps().getMailConfig().getFrom();
		String fromName 	= InterfaceFactory.getOptionalApps().getMailConfig().getFromName();
		String userName 	= InterfaceFactory.getOptionalApps().getMailConfig().getUserName();
		String passWord 	= InterfaceFactory.getOptionalApps().getMailConfig().getPassWord();
		String connTimeout 	= InterfaceFactory.getOptionalApps().getMailConfig().getConnTimeout();
		String readTimeout 	= InterfaceFactory.getOptionalApps().getMailConfig().getReadTimeout();
		
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
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(mailSenderInSDO.getRecipient()));
        msg.setSubject(mailSenderInSDO.getSubject());
        msg.setContent(mailSenderInSDO.getBody(),"text/html");
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
        
        MailSenderOutSDO mailSenderOutSDO = new MailSenderOutSDO();
        mailSenderOutSDO.setSuccess(true);
		
		return mailSenderOutSDO;
	}
	
}
