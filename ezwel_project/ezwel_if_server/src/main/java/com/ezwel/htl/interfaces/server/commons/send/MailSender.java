package com.ezwel.htl.interfaces.server.commons.send;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
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
	
	static final String from = "admin@ezwel.com";
    static final String fromName = "이지웰";
    
	/**
	 * 메일전송
	 * @param from 발신자메일
	 * @param fromName 발신자명
	 * @param recipient 수신자
	 * @param subject 제목
	 * @param body 내용
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	@APIOperation(description="메일발송 인터페이스")
	protected void callMailSender(String recipient, String subject, String body) throws MessagingException, UnsupportedEncodingException {
		
		String host = InterfaceFactory.getOptionalApps().getEmailConfig().getHost();
		String port = InterfaceFactory.getOptionalApps().getEmailConfig().getPort();
		String auth = InterfaceFactory.getOptionalApps().getEmailConfig().getAuth();
		String username = InterfaceFactory.getOptionalApps().getEmailConfig().getUsername();
		String password = InterfaceFactory.getOptionalApps().getEmailConfig().getPassword();
		String connTimeout = InterfaceFactory.getOptionalApps().getEmailConfig().getConnTimeout();
		String readTimeout = InterfaceFactory.getOptionalApps().getEmailConfig().getReadTimeout();
		
		//properties 설정
        Properties props = System.getProperties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.username", username);
        props.put("mail.smtp.password", password);
		props.put("mail.smtp.connectiontimeout", connTimeout);
		props.put("mail.smtp.timeout", readTimeout);
		
        // 메일 세션
        Session session = Session.getDefaultInstance(props);
        
        javax.mail.internet.MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from, fromName));
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        msg.setSubject(subject, "UTF-8");
        msg.setContent(body,"text/html; charset=utf-8");
        
        logger.info(recipient + " - send : " + subject);
        
        // 발송 처리
        Transport.send(msg);
	}
	
}
