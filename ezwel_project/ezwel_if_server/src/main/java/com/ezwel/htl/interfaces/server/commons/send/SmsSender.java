package com.ezwel.htl.interfaces.server.commons.send;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.configure.ConfigureHelper;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.HttpInterfaceExecutor;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigSDO;
import com.ezwel.htl.interfaces.server.commons.intercepter.HandlerInterceptor;
import com.ezwel.htl.interfaces.server.commons.send.data.SmsSenderInSDO;
import com.ezwel.htl.interfaces.server.commons.send.data.SmsSenderOutSDO;

@Component
@APIType
public class SmsSender {
	
	private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);
	
	@Autowired /** interface_if는 프론트 및 관리자단에서 ezwel 프레임워크 표준인  Autowired를 사용한다. (interface_if_server는 Autowired보다 빠른 스프링 컨텍스트의 getBean을 사용함) */
	private HttpInterfaceExecutor inteface;
	
	@Autowired /** interface_if는 프론트 및 관리자단에서 ezwel 프레임워크 표준인  Autowired를 사용한다. (interface_if_server는 Autowired보다 빠른 스프링 컨텍스트의 getBean을 사용함) */
	private ConfigureHelper configureHelper;
	
	public SmsSender() {		
		if(inteface == null) {
			inteface = new HttpInterfaceExecutor();
		}
		if(configureHelper == null) {
			configureHelper = new ConfigureHelper();
		}
	}
	
	@APIOperation(description="문자발송 인터페이스")
	public SmsSenderOutSDO callSmsSender(SmsSenderInSDO smsSenderSDO) {
		
		logger.debug("errorCode : {}", smsSenderSDO.getCallTo());
		
		return callSmsSender(smsSenderSDO, false);
	}
	
	@APIOperation(description="문자발송 인터페이스")
	public SmsSenderOutSDO callSmsSender(SmsSenderInSDO smsSenderSDO, boolean isEzwelInsideInterface) {
		
		SmsSenderOutSDO out = null;
		
		try {
			
			HttpConfigSDO httpConfigSDO = new HttpConfigSDO();
			httpConfigSDO.setRestURI(InterfaceFactory.getOptionalApps().getSmsConfig().getRestURI());
			httpConfigSDO.setEzwelInsideInterface(isEzwelInsideInterface);
			
			/** execute interface */
			out = (SmsSenderOutSDO) inteface.sendJSON(httpConfigSDO, smsSenderSDO, SmsSenderOutSDO.class);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "문자발송 인터페이스 장애발생.", e);
		}
			
		return out;		
	}
}
