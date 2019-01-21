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
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigSDO;
import com.ezwel.htl.interfaces.commons.send.SmsSender;
import com.ezwel.htl.interfaces.commons.send.data.SmsSenderSDO;

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
	private SmsSender smsSender;
	
	public SendIFService() {
		
		if(smsSender == null) {
			smsSender = new SmsSender();
		}
	}	
	
	@APIOperation(description="문자발송 인터페이스", isOutputJsonMarshall=true, returnType=SmsSenderSDO.class)
	public boolean callSmsSender(SmsSenderSDO smsSenderSDO) {
		return callSmsSender(smsSenderSDO, false);
	}
	
	@APIOperation(description="문자발송 인터페이스", isOutputJsonMarshall=true, returnType=SmsSenderSDO.class)
	public boolean callSmsSender(SmsSenderSDO smsSenderSDO, boolean isEzwelInsideInterface) {
				
//		String callTo = "01037440698";
//		String callFrom = "0232820579";
//		String mmsSubject = "테스트";
//		String smsTxt = "대기예약 확정이 가능합니다. 예약 확정은 2019-01-15 18:00 시간 내에 홈페이지에서 해주셔야 하며, 이후 자동 취소 됩니다.  - 시설 : 부산파라다이스호텔 - 일시 : 2019-01-17 이지웰 복지몰 서비스를 이용해 주셔서 감사합니다.";
//		String svcType = "1008";
//		String smsUseYn = "N";
//		String templateCode = "10052";
//		
//		//Input parameter
//		SmsSenderSDO smsSenderSDO = new SmsSenderSDO();
//		smsSenderSDO.setCallTo(callTo); 			// 필수
//		smsSenderSDO.setCallFrom(callFrom); 		// 선택
//		smsSenderSDO.setMmsSubject(mmsSubject); 	// 선택
//		smsSenderSDO.setSmsText(smsTxt); 			// 필수
//		smsSenderSDO.setTemplateCode(templateCode); // 선택 ( 카카오톡메세지일경우 필수 )
		
		boolean out = true;
		
		try {
			
			HttpConfigSDO httpConfigSDO = new HttpConfigSDO();
			httpConfigSDO.setRestURI(InterfaceFactory.getOptionalApps().getSmsConfig().getRestURI());
			httpConfigSDO.setEzwelInsideInterface(isEzwelInsideInterface);
					
			smsSender.requestUrl(httpConfigSDO, smsSenderSDO);
			
			logger.debug("[SMS START] : {}", out);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "문자발송 인터페이스 장애발생.", e);
		}
			
		return out;
	}
	
//	@APIOperation(description="메일발송 인터페이스")
//	public boolean callMailSender(MailSenderSDO mailSenderSDO) {
//		return callMailSender(mailSenderSDO, false);
//	}
//	
//	@APIOperation(description="메일발송 인터페이스")
//	public boolean callMailSender(MailSenderSDO mailSenderSDO, boolean isEzwelInsideInterface) {
//		
//		boolean out = true;
//		
//		/*
//		String recipient = "jyp0698@gmail.com"; 
//		String subject = "메일 제목 테스트";
//		String body = "메일 내용 테스트";
//		
//		mailSenderSDO.setRecipient(recipient); 필수
//		mailSenderSDO.setSubject(subject); 필수
//		mailSenderSDO.setBody(body); 필수
//		
//		mailSenderSDO.setFrom(from); 옵션
//		mailSenderSDO.setFromName(fromName);옵션
//		
//		*/
//		
//		if(StringUtils.isEmpty(mailSenderSDO.getRecipient()) || StringUtils.isEmpty(mailSenderSDO.getSubject()) || StringUtils.isEmpty(mailSenderSDO.getBody())) {
//			return false;
//		}
//		
//		try {
//			
//			
//			MailSenderSDO out = (MailSenderSDO) mailSender.callMailSender();
//			
//			
//		}
//		catch(Exception e) {
//			return false;
//		}
//			
//		return out;		
//	}

}
