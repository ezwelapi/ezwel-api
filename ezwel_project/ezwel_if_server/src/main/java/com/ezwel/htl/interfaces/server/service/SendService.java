package com.ezwel.htl.interfaces.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.send.data.MailSenderSDO;
import com.ezwel.htl.interfaces.server.commons.send.MailSenderService;
import com.ezwel.htl.interfaces.service.OutsideIFService;

/**
 * <pre>
 * 인터페이스 서비스
 * </pre>
 * @author ypjeon@ebsolution.co.kr
 * @date   2018. 12. 26.
 */
@Service
@APIType(description="메일발송 인터페이스")
public class SendService {

	private static final Logger logger = LoggerFactory.getLogger(OutsideIFService.class);
	
	@Autowired /** interface_if는 프론트 및 관리자단에서 ezwel 프레임워크 표준인  Autowired를 사용한다. (interface_if_server는 Autowired보다 빠른 스프링 컨텍스트의 getBean을 사용함) */
	private MailSenderService mailSenderService;
	
	public SendService() {
		
		if(mailSenderService == null) {
			mailSenderService = new MailSenderService();
		}
	}	
	
	@APIOperation(description="메일발송 인터페이스")
	public boolean callMailSender(MailSenderSDO mailSenderSDO) {
		return callMailSender(mailSenderSDO, false);
	}
	
	@APIOperation(description="메일발송 인터페이스")
	public boolean callMailSender(MailSenderSDO mailSenderSDO, boolean isEzwelInsideInterface) {
		
		boolean out = true;
		
		try {
			
			String recipient = mailSenderSDO.getRecipient();
			String subject = mailSenderSDO.getSubject();
			String body = mailSenderSDO.getBody();
			
			mailSenderService.asyncSimpleSend(recipient, subject, body);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "메일발송 인터페이스 장애발생.", e);
		}
			
		return out;		
	}

}
