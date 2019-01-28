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
import com.ezwel.htl.interfaces.commons.configure.data.OptMailConfig;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.server.commons.intercepter.HandlerInterceptor;
import com.ezwel.htl.interfaces.service.data.send.MailSenderInSDO;
import com.ezwel.htl.interfaces.service.data.send.MailSenderOutSDO;

/**
 * <pre>
 * 메일발송
 * </pre>
 * @author ypjeon@ebsolution.co.kr
 * @date   2019. 01. 22.
 */
@Component
@APIType(description="메일발송")
public class MailSender {
	
	private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);
	
	static final String CONFIGSET = "Configset";
    
	/**
	 * 
	 * @param mailSenderInList
	 * @return
	 * @throws Exception
	 */
	@APIOperation(description="메일발송 목록 인터페이스")
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
	 * 
	 * @param mailSenderInSDO
	 * @return
	 * @throws Exception
	 */
	@APIOperation(description="메일발송 인터페이스")
	public MailSenderOutSDO callMailSender(MailSenderInSDO mailSenderInSDO) throws Exception {
		logger.debug("[START] callMailSender {}", mailSenderInSDO);
		
		OptMailConfig mailConfig = InterfaceFactory.getOptionalApps().getMailConfig();
		String host			= mailConfig.getHost();
		String port 		= mailConfig.getPort();
		String from 		= mailConfig.getFrom();
		String fromName 	= mailConfig.getFromName();
		String userName 	= mailConfig.getUserName();
		String passWord 	= mailConfig.getPassWord();
		String connTimeout 	= mailConfig.getConnTimeout();
		String readTimeout 	= mailConfig.getReadTimeout();
		
		//properties 설정
		Properties props = System.getProperties();
    	props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.port", port); 
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.connectiontimeout", connTimeout);
	    props.put("mail.smtp.timeout", readTimeout);
	    
        MailSenderOutSDO mailSenderOutSDO = null;
        Transport transport = null;
        Session session = null;
        MimeMessage msg = null;
        
	    try {
	    	
		    session = Session.getDefaultInstance(props);
		    
		    //MimeMessage 설정
	        msg = new MimeMessage(session);
	        msg.setFrom(new InternetAddress(from, fromName));
	        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(mailSenderInSDO.getRecipient()));
	        msg.setSubject(mailSenderInSDO.getSubject());
	        msg.setContent(mailSenderInSDO.getBody(),"text/html; charset=" + OperateConstants.DEFAULT_ENCODING);
	        msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);
	        
	        transport = session.getTransport();

	        logger.info("Mail Connecting... [host : {}, userName : {}, passWord : {}]", host, userName, passWord);
            transport.connect(host, userName, passWord);
            
            logger.info("Send Mail Sending...");
            transport.sendMessage(msg, msg.getAllRecipients());
            
            logger.info("Send Mail Complete...");
            mailSenderOutSDO = new MailSenderOutSDO();
            mailSenderOutSDO.setSuccess(true);
        }
        catch (Exception e) {
        	logger.error("Send Mail Error message", e);
        }
        finally
        {
            transport.close();
        }
        
	    logger.debug("[END] callMailSender {}", mailSenderOutSDO);
		return mailSenderOutSDO;
	}
	
}
