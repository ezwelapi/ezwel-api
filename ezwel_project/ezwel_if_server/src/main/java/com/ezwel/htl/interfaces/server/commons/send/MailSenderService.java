package com.ezwel.htl.interfaces.server.commons.send;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ezwel.htl.interfaces.server.commons.intercepter.HandlerInterceptor;

@Service
public class MailSenderService {
	
	private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);
		
	private MailSender mailSender;
	
	public MailSenderService() {
		mailSender = new MailSender();
	}
	
	/**
	 * 메일전송
	 * @param from 발신자메일
	 * @param fromName 발신자명
	 * @param recipient 수신자
	 * @param subject 제목
	 * @param body 내용
	 */
	@Async
	public void asyncSimpleSend(final String from, final String fromName, final String recipient, final String subject, final String body){
		try {			
			mailSender.callMailSender(from, fromName, recipient, subject, body);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
}
