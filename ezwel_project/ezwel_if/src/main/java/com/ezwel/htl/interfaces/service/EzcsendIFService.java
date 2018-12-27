package com.ezwel.htl.interfaces.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.configure.ConfigureHelper;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.HttpInterfaceExecutor;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigSDO;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.service.data.mock.MocKUpOutSDO;
import com.ezwel.htl.interfaces.service.data.send.SendSmsInSDO;
import com.ezwel.htl.interfaces.service.data.send.SendSmsOutSDO;

/**
 * <pre>
 * 인터페이스 서비스
 * </pre>
 * @author ypjeon@ebsolution.co.kr
 * @date   2018. 12. 13.
 */
@Service
@APIType
public class EzcsendIFService {

	private static final Logger logger = LoggerFactory.getLogger(EzcsendIFService.class);

	@Autowired /** interface_if는 프론트 및 관리자단에서 ezwel 프레임워크 표준인  Autowired를 사용한다. (interface_if_server는 Autowired보다 빠른 스프링 컨텍스트의 getBean을 사용함) */
	private HttpInterfaceExecutor inteface;
	
	@Autowired /** interface_if는 프론트 및 관리자단에서 ezwel 프레임워크 표준인  Autowired를 사용한다. (interface_if_server는 Autowired보다 빠른 스프링 컨텍스트의 getBean을 사용함) */
	private ConfigureHelper configureHelper; 
	
	@Autowired /** interface_if는 프론트 및 관리자단에서 ezwel 프레임워크 표준인  Autowired를 사용한다. (interface_if_server는 Autowired보다 빠른 스프링 컨텍스트의 getBean을 사용함) */
	private PropertyUtil propertyUtil;
	
	public EzcsendIFService() {
		
		if(propertyUtil == null) {
			propertyUtil = new PropertyUtil();
		}
		if(inteface == null) {
			inteface = new HttpInterfaceExecutor();
		}
		if(configureHelper == null) {
			configureHelper = new ConfigureHelper();
		}
	}
	
	
	@APIOperation(description="문자발송 인터페이스")
	public SendSmsOutSDO callSendSms(UserAgentSDO userAgentSDO, SendSmsInSDO sendSmsSDO) {
		return callSendSms(userAgentSDO, sendSmsSDO, false);
	}
	
	@APIOperation(description="문자발송 인터페이스")
	public SendSmsOutSDO callSendSms(UserAgentSDO userAgentSDO, SendSmsInSDO sendSmsSDO, boolean isEzwelInsideInterface) {
		
		SendSmsOutSDO out = null;
		
		try {
			
			HttpConfigSDO httpConfigSDO = InterfaceFactory.getChannel("sendSms", userAgentSDO.getHttpAgentId());
			httpConfigSDO.setEzwelInsideInterface(isEzwelInsideInterface);
			configureHelper.setupUserAgentInfo(userAgentSDO, httpConfigSDO);
			
			/** execute interface */
			if(inteface.isHttpConnect(httpConfigSDO)) {
				out = (SendSmsOutSDO) inteface.sendJSON(httpConfigSDO, sendSmsSDO, SendSmsOutSDO.class);
			}
			else {
				out = MocKUpOutSDO.getSendSmsOut();
			}
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "문자발송 인터페이스 장애발생.", e);
		}
			
		return out;		
	}
	
}
