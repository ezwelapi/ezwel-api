package com.ezwel.htl.interfaces.server.commons.send;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.server.commons.intercepter.HandlerInterceptor;

@Component
@APIType(description="메일발송")
public class MailSenderService {
	
	private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);
	
	@Autowired
	MailSender mailSender;
	
	@Async
	public void asyncSimpleSend(final String from, final String fromName, final String recipient, final String subject, final String body){
		try {
			mailSender.callMailSender(from, fromName, recipient, subject, body);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
}
