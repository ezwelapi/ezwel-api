package com.ezwel.htl.interfaces.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.server.commons.send.FaxSenderService;
import com.ezwel.htl.interfaces.server.commons.send.MailSenderService;
import com.ezwel.htl.interfaces.server.commons.send.SmsSender;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.service.OutsideIFService;
import com.ezwel.htl.interfaces.service.data.send.SmsSenderInSDO;
import com.ezwel.htl.interfaces.service.data.send.SmsSenderOutSDO;

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
	private SmsSender smsSender;
	
	@Autowired /** interface_if는 프론트 및 관리자단에서 ezwel 프레임워크 표준인  Autowired를 사용한다. (interface_if_server는 Autowired보다 빠른 스프링 컨텍스트의 getBean을 사용함) */
	private MailSenderService mailSenderService;
	
	@Autowired /** interface_if는 프론트 및 관리자단에서 ezwel 프레임워크 표준인  Autowired를 사용한다. (interface_if_server는 Autowired보다 빠른 스프링 컨텍스트의 getBean을 사용함) */
	private FaxSenderService faxSenderService;
	
	public SendService() {
		
		if(smsSender == null) {
			smsSender = new SmsSender();
		}
		
		if(mailSenderService == null) {
			mailSenderService = new MailSenderService();
		}
		
		if(faxSenderService == null) {
			faxSenderService = new FaxSenderService();
		}
	}
	
	@APIOperation(description="문자발송 인터페이스")
	public Object callSmsSender(SmsSenderInSDO smsSenderInSDO) {
		return callSmsSender(smsSenderInSDO, false);
	}
	
	@APIOperation(description="문자발송 인터페이스")
	public Object callSmsSender(SmsSenderInSDO smsSenderInSDO, boolean isEzwelInsideInterface) {
		
		smsSender = (SmsSender) LApplicationContext.getBean(smsSender, SmsSender.class);
		
		SmsSenderOutSDO out = new SmsSenderOutSDO();
		
		try {
			
			out = (SmsSenderOutSDO) smsSender.requestUrl(smsSenderInSDO);
			
		} 
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "문자발송 인터페이스 장애발생.", e);
		}
			
		return out;
	}
	
//	@APIOperation(description="메일발송 인터페이스")
//	public boolean callMailSender(MailSenderInSDO mailSenderInSDO) {
//		return callMailSender(mailSenderInSDO, false);
//	}
//	
//	@APIOperation(description="메일발송 인터페이스")
//	public boolean callMailSender(MailSenderInSDO mailSenderInSDO, boolean isEzwelInsideInterface) {
//		
//		boolean out = true;
//		
//		try {
//			
//			String recipient = mailSenderInSDO.getRecipient();
//			String subject = mailSenderInSDO.getSubject();
//			String body = mailSenderInSDO.getBody();
//			
//			mailSenderService.asyncMailSender(recipient, subject, body);
//			
//			logger.debug("MAIL START] : {}", out);
//		}
//		catch(Exception e) {
//			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "메일발송 인터페이스 장애발생.", e);
//		}
//			
//		return out;		
//	}
//	
//	@APIOperation(description="팩스발송 인터페이스")
//	public boolean callFaxSender(FaxSenderInSDO faxSenderInSDO) {
//		return callFaxSender(faxSenderInSDO, false);
//	}
//	
//	@APIOperation(description="팩스발송 인터페이스")
//	public boolean callFaxSender(FaxSenderInSDO faxSenderInSDO, boolean isEzwelInsideInterface) {
//		
//		boolean out = true;
//		
//		try {
//			
//			String trTitle = faxSenderInSDO.getTrTitle();
//			String trSendName = faxSenderInSDO.getTrSendName();
//			String trSendFaxNum = faxSenderInSDO.getTrSendFaxNum();
//			String trDocName = faxSenderInSDO.getTrDocName();
//			String trName = faxSenderInSDO.getTrName();
//			String trPhone = faxSenderInSDO.getTrPhone();
//			
//			faxSenderService.asyncFaxSender(trTitle, trSendName, trSendFaxNum, trDocName, trName, trPhone);
//			
//		}
//		catch(Exception e) {
//			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "팩스발송 인터페이스 장애발생.", e);
//		}
//			
//		return out;		
//	}

}
