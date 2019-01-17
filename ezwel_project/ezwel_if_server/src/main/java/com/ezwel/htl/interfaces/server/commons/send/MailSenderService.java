package com.ezwel.htl.interfaces.server.commons.send;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.server.commons.intercepter.HandlerInterceptor;

@Component
@APIType(description="메일발송")
public class MailSenderService {
	
	private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);
	
	@Autowired /** interface_if는 프론트 및 관리자단에서 ezwel 프레임워크 표준인  Autowired를 사용한다. (interface_if_server는 Autowired보다 빠른 스프링 컨텍스트의 getBean을 사용함) */
	private MailSender mailSender;
	
	public MailSenderService() {
		
		if(mailSender == null) {
			mailSender = new MailSender();
		}
	}
	
	@APIOperation(description="메일발송 인터페이스")
	@Async
	public void asyncSimpleSend(final String recipient, final String subject, final String body){
		try {
			mailSender.callMailSender(recipient, subject, body);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
}
