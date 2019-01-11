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
import com.ezwel.htl.interfaces.commons.send.data.SmsSenderInSDO;
import com.ezwel.htl.interfaces.commons.send.data.SmsSenderOutSDO;

/**
 * <pre>
 * 인터페이스 서비스
 * </pre>
 * @author ypjeon@ebsolution.co.kr
 * @date   2018. 12. 26.
 */
@Service
@APIType
public class SendIFService {

	private static final Logger logger = LoggerFactory.getLogger(OutsideIFService.class);
	
	@Autowired /** interface_if는 프론트 및 관리자단에서 ezwel 프레임워크 표준인  Autowired를 사용한다. (interface_if_server는 Autowired보다 빠른 스프링 컨텍스트의 getBean을 사용함) */
	private SmsSender smsSender;
	
	public SendIFService() {
		
		if(smsSender == null) {
			smsSender = new SmsSender();
		}
	}
	
	
	@APIOperation(description="문자발송 인터페이스")
	public SmsSenderOutSDO callSmsSender(SmsSenderInSDO smsSenderSDO) {
		return callSmsSender(smsSenderSDO, false);
	}
	
	@APIOperation(description="문자발송 인터페이스")
	public SmsSenderOutSDO callSmsSender(SmsSenderInSDO smsSenderSDO, boolean isEzwelInsideInterface) {
		
		SmsSenderOutSDO out = null;
		
		try {
			
			HttpConfigSDO httpConfigSDO = new HttpConfigSDO();
			httpConfigSDO.setRestURI(InterfaceFactory.getOptionalApps().getSmsConfig().getRestURI());
			httpConfigSDO.setEzwelInsideInterface(isEzwelInsideInterface);
			
			out = (SmsSenderOutSDO) smsSender.callSmsSender(smsSenderSDO);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "문자발송 인터페이스 장애발생.", e);
		}
			
		return out;		
	}

}
