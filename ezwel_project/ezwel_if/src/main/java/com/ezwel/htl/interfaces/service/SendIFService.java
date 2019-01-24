package com.ezwel.htl.interfaces.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.HttpInterfaceExecutor;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigSDO;
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
@APIType(description="문자발송 인터페이스")
public class SendIFService {

	private static final Logger logger = LoggerFactory.getLogger(OutsideIFService.class);
	
	@Autowired /** interface_if는 프론트 및 관리자단에서 ezwel 프레임워크 표준인  Autowired를 사용한다. (interface_if_server는 Autowired보다 빠른 스프링 컨텍스트의 getBean을 사용함) */
	private HttpInterfaceExecutor inteface;	
	
	public SendIFService() {
		
		if(inteface == null) {
			inteface = new HttpInterfaceExecutor();
		}
	}

	@APIOperation(description="문자발송 인터페이스 (외부로직접나감)")
	public SmsSenderOutSDO callSmsSender(SmsSenderInSDO smsSenderInSDO) {
		return callSmsSender(smsSenderInSDO, false);
	}
	
	@APIOperation(description="문자발송 인터페이스")
	public SmsSenderOutSDO callSmsSender(SmsSenderInSDO smsSenderInSDO, boolean isEzwelInsideInterface) {
		
		SmsSenderOutSDO out = new SmsSenderOutSDO();
		
		try {
			
			HttpConfigSDO httpConfigSDO = new HttpConfigSDO();
			httpConfigSDO.setHttpAgentId("00000000");
			httpConfigSDO.setHttpApiKey("");
			httpConfigSDO.setHttpApiSignature("");
			httpConfigSDO.getHttpApiTimestamp();
			httpConfigSDO.setRestURI(InterfaceFactory.getServerHttpDomainURI().concat(InterfaceFactory.getOptionalApps().getSmsConfig().getRestURI()));
			
			/** execute interface */
			out = (SmsSenderOutSDO) inteface.sendJSON(httpConfigSDO, smsSenderInSDO, SmsSenderOutSDO.class);
			
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9900, "문자발송 인터페이스 장애발생.", e);
		}
		
		return out;
	}
	
	@APIOperation(description="메일발송 인터페이스 (외부로직접나감)")
	public MailSenderOutSDO callMailSender(MailSenderInSDO mailSenderInSDO) {
		return callMailSender(mailSenderInSDO, false);
	}
	
	@APIOperation(description="메일발송 인터페이스")
	public MailSenderOutSDO callMailSender(MailSenderInSDO mailSenderInSDO, boolean isEzwelInsideInterface) {
		
		MailSenderOutSDO out = new MailSenderOutSDO();
		
		try {
			
			HttpConfigSDO httpConfigSDO = new HttpConfigSDO();
			httpConfigSDO.setHttpAgentId("00000000");
			httpConfigSDO.setHttpApiKey("");
			httpConfigSDO.setHttpApiSignature("");
			httpConfigSDO.getHttpApiTimestamp();
			httpConfigSDO.setRestURI(InterfaceFactory.getServerHttpDomainURI().concat(InterfaceFactory.getOptionalApps().getMailConfig().getRestURI()));
			
			/** execute interface */
			out = (MailSenderOutSDO) inteface.sendJSON(httpConfigSDO, mailSenderInSDO, MailSenderOutSDO.class);
			
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9901, "메일발송 인터페이스 장애발생.", e);
		}
			
		return out;		
	}
	
	@APIOperation(description="팩스발송 인터페이스 (외부로직접나감)")
	public FaxSenderOutSDO callFaxSender(FaxSenderInSDO faxSenderInSDO) {
		return callFaxSender(faxSenderInSDO, false);
	}
	
	@APIOperation(description="팩스발송 인터페이스")
	public FaxSenderOutSDO callFaxSender(FaxSenderInSDO faxSenderInSDO, boolean isEzwelInsideInterface) {
		
		FaxSenderOutSDO out = new FaxSenderOutSDO();
		
		try {
			
			HttpConfigSDO httpConfigSDO = new HttpConfigSDO();
			httpConfigSDO.setHttpAgentId("00000000");
			httpConfigSDO.setHttpApiKey("");
			httpConfigSDO.setHttpApiSignature("");
			httpConfigSDO.getHttpApiTimestamp();
			httpConfigSDO.setRestURI(InterfaceFactory.getServerHttpDomainURI().concat(InterfaceFactory.getOptionalApps().getFaxConfig().getRestURI()));
			
			/** execute interface */
			out = (FaxSenderOutSDO) inteface.sendJSON(httpConfigSDO, faxSenderInSDO, FaxSenderOutSDO.class);
			
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9902, "팩스발송 인터페이스 장애발생.", e);
		}
			
		return out;		
	}

}
