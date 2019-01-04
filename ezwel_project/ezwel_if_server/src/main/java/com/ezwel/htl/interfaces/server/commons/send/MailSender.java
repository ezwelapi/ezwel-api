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

import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.server.commons.intercepter.HandlerInterceptor;

@Component
public class MailSender {
	
	private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);

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
	protected void callMailSender(String from, String fromName, String recipient, String subject, String body) throws MessagingException, UnsupportedEncodingException {
		
		String host = InterfaceFactory.getOptionalApps().getEmailConfig().getHost();
		String auth = InterfaceFactory.getOptionalApps().getEmailConfig().getAuth();
		String username = InterfaceFactory.getOptionalApps().getEmailConfig().getUsername();
		String password = InterfaceFactory.getOptionalApps().getEmailConfig().getPassword();
		String connectiontimeout = InterfaceFactory.getOptionalApps().getEmailConfig().getConnectiontimeout();
		String timeout = InterfaceFactory.getOptionalApps().getEmailConfig().getTimeout();
		
		System.out.println("host : " + host);
		System.out.println("auth : " + auth);
		System.out.println("username : " + username);
		System.out.println("password : " + password);
		System.out.println("connectiontimeout : " + connectiontimeout);
		System.out.println("timeout : " + timeout);
		
		//properties 설정
        Properties props = System.getProperties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.username", username);
        props.put("mail.smtp.password", password);
		props.put("mail.smtp.connectiontimeout", connectiontimeout);
		props.put("mail.smtp.timeout", timeout);
		
        // 메일 세션
        Session session = Session.getDefaultInstance(props);

        // 메일 관련
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from, fromName));
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        msg.setSubject(subject, "UTF-8");
        msg.setContent(body,"text/html; charset=utf-8");
        
        logger.info(recipient + " - send : " + subject);
        
        // 발송 처리
        Transport.send(msg);
	}
	
}
