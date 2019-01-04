package com.ezwel.htl.interfaces.server.commons.send;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.server.commons.intercepter.HandlerInterceptor;

@Component
public class MailSender {
	
	private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);
	
	public static void gmailSend() {
	
	    // SMTP 서버 정보를 설정한다.
	    Properties prop = new Properties();
	    prop.put("mail.smtp.host", "smtp.gmail.com"); 
	    prop.put("mail.smtp.port", 465); 
	    prop.put("mail.smtp.auth", "true"); 
	    prop.put("mail.smtp.ssl.enable", "true"); 
	    prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	    
	    Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication("jyp0698@gmail.com", "mkse1547**");
	        }
	    });
	
	    try {
	        MimeMessage message = new MimeMessage(session);
	        message.setFrom(new InternetAddress("jyp0698@gmail.com"));
	
	        //수신자메일주소
	        message.addRecipient(Message.RecipientType.TO, new InternetAddress("java124@naver.com")); 
	
	        // Subject
	        message.setSubject("제목을 입력하세요"); //메일 제목을 입력
	
	        // Text
	        message.setText("내용을 입력하세요");    //메일 내용을 입력
	
	        // send the message
	        Transport.send(message); ////전송
	        System.out.println("message sent successfully...");
	    } catch (AddressException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (MessagingException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	}
	
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
	protected void callMailSender(String from, String fromName, String recipient, String subject, String body) throws MessagingException, UnsupportedEncodingException {
		
		String host = InterfaceFactory.getOptionalApps().getEmailConfig().getHost();
		String port = InterfaceFactory.getOptionalApps().getEmailConfig().getPort();
		String auth = InterfaceFactory.getOptionalApps().getEmailConfig().getAuth();
		String username = InterfaceFactory.getOptionalApps().getEmailConfig().getUsername();
		String password = InterfaceFactory.getOptionalApps().getEmailConfig().getPassword();
		String connTimeout = InterfaceFactory.getOptionalApps().getEmailConfig().getConnTimeout();
		String readTimeout = InterfaceFactory.getOptionalApps().getEmailConfig().getReadTimeout();
		
		System.out.println("host : " + host);
		System.out.println("port : " + port);
		System.out.println("auth : " + auth);
		System.out.println("username : " + username);
		System.out.println("password : " + password);
		System.out.println("connTimeout : " + connTimeout);
		System.out.println("timeout : " + readTimeout);
		
		//properties 설정
        Properties props = System.getProperties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.username", username);
        props.put("mail.smtp.password", password);
		props.put("mail.smtp.connectiontimeout", connTimeout);
		props.put("mail.smtp.timeout", readTimeout);
		props.put("mail.transport.protocol","smtp");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust", host);
		
        // 메일 세션
        Session session = Session.getDefaultInstance(props);

        // 메일 관련
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from, fromName));
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        msg.setSubject(subject, "UTF-8");
        msg.setContent(body,"text/html; charset=utf-8");
        
        // 발송 처리
        Transport.send(msg);
	}
	
}
