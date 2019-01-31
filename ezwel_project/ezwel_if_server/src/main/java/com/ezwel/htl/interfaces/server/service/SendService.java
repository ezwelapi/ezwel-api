package com.ezwel.htl.interfaces.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.server.commons.send.FaxReader;
import com.ezwel.htl.interfaces.server.commons.send.FaxSender;
import com.ezwel.htl.interfaces.server.commons.send.MailSender;
import com.ezwel.htl.interfaces.server.commons.send.SmsSender;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.service.data.send.FaxReaderOutSDO;
import com.ezwel.htl.interfaces.service.data.send.FaxSenderInSDO;
import com.ezwel.htl.interfaces.service.data.send.FaxSenderOutSDO;
import com.ezwel.htl.interfaces.service.data.send.MailSenderInSDO;
import com.ezwel.htl.interfaces.service.data.send.MailSenderOutSDO;
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

	private static final Logger logger = LoggerFactory.getLogger(SendService.class);
	
	@Autowired /** interface_if는 프론트 및 관리자단에서 ezwel 프레임워크 표준인  Autowired를 사용한다. (interface_if_server는 Autowired보다 빠른 스프링 컨텍스트의 getBean을 사용함) */
	private SmsSender smsSender;
	
	@Autowired /** interface_if는 프론트 및 관리자단에서 ezwel 프레임워크 표준인  Autowired를 사용한다. (interface_if_server는 Autowired보다 빠른 스프링 컨텍스트의 getBean을 사용함) */
	private MailSender mailSender;
	
	@Autowired /** interface_if는 프론트 및 관리자단에서 ezwel 프레임워크 표준인  Autowired를 사용한다. (interface_if_server는 Autowired보다 빠른 스프링 컨텍스트의 getBean을 사용함) */
	private FaxSender faxSender;
	
	@Autowired /** interface_if는 프론트 및 관리자단에서 ezwel 프레임워크 표준인  Autowired를 사용한다. (interface_if_server는 Autowired보다 빠른 스프링 컨텍스트의 getBean을 사용함) */
	private FaxReader faxReader;
	
	public SendService() {
		
		if(smsSender == null) {
			smsSender = new SmsSender();
		}
		
		if(mailSender == null) {
			mailSender = new MailSender();
		}
		
		if(faxSender == null) {
			faxSender = new FaxSender();
		}
	}
	
	@APIOperation(description="문자발송 인터페이스")
	public SmsSenderOutSDO callSmsSender(SmsSenderInSDO smsSenderInSDO) {
		return callSmsSender(smsSenderInSDO, false);
	}
	
	@APIOperation(description="문자발송 인터페이스")
	public SmsSenderOutSDO callSmsSender(SmsSenderInSDO smsSenderInSDO, boolean isEzwelInsideInterface) {
		
		smsSender = (SmsSender) LApplicationContext.getBean(smsSender, SmsSender.class);
		
		SmsSenderOutSDO out = new SmsSenderOutSDO();
		
		try {
			
			out = (SmsSenderOutSDO) smsSender.callSmsSender(smsSenderInSDO);
			
		} 
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9900, "문자발송 인터페이스 장애발생.", e);
		}
			
		return out;
	}
	
	@APIOperation(description="메일발송 인터페이스")
	public MailSenderOutSDO callMailSender(MailSenderInSDO mailSenderInSDO) {
		return callMailSender(mailSenderInSDO, false);
	}
	
	@APIOperation(description="메일발송 인터페이스")
	public MailSenderOutSDO callMailSender(MailSenderInSDO mailSenderInSDO, boolean isEzwelInsideInterface) {
		
		mailSender = (MailSender) LApplicationContext.getBean(mailSender, MailSender.class);
		
		MailSenderOutSDO out = new MailSenderOutSDO();
		
		try {
			
			out = (MailSenderOutSDO) mailSender.callMailSender(mailSenderInSDO);
		} 
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9901, "메일발송 인터페이스 장애발생.", e);
		}
			
		return out;
	}
	
	@APIOperation(description="팩스발송 인터페이스")
	public FaxSenderOutSDO callFaxSender(FaxSenderInSDO faxSenderInSDO) {
		return callFaxSender(faxSenderInSDO, false);
	}
	
	@APIOperation(description="팩스발송 인터페이스")
	public FaxSenderOutSDO callFaxSender(FaxSenderInSDO faxSenderInSDO, boolean isEzwelInsideInterface) {
		
		faxSender = (FaxSender) LApplicationContext.getBean(faxSender, FaxSender.class);
		
		FaxSenderOutSDO out = new FaxSenderOutSDO();
		
		try {
			
			out = (FaxSenderOutSDO) faxSender.callFaxSender(faxSenderInSDO);
			
		} 
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9902, "팩스발송 인터페이스 장애발생.", e);
		}
			
		return out;
	}
	
	@APIOperation(description="팩스수신 인터페이스")
	public FaxReaderOutSDO callFaxReader(String reservNum) {
		return callFaxReader(reservNum, false);
	}
	
	@APIOperation(description="팩스수신 인터페이스")
	public FaxReaderOutSDO callFaxReader(String reservNum, boolean isEzwelInsideInterface) {
		
		faxReader = (FaxReader) LApplicationContext.getBean(faxReader, FaxReader.class);
		
		FaxReaderOutSDO out = new FaxReaderOutSDO();
		
		try {
			
			out = (FaxReaderOutSDO) faxReader.callFaxReader(reservNum);
			
		} 
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9902, "팩스수신 인터페이스 장애발생.", e);
		}
			
		return out;
	}
	
}
