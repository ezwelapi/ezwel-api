package com.ezwel.htl.interfaces.commons.configure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigSDO;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;

@Component
@APIType(description="인터페이스 설정 부가기능")
public class ConfigureHelper {

	private static final Logger logger = LoggerFactory.getLogger(ConfigureHelper.class);
	
	@Autowired /** interface_if는 프론트 및 관리자단에서 ezwel 프레임워크 표준인  Autowired를 사용한다. (interface_if_server는 Autowired보다 빠른 스프링 컨텍스트의 getBean을 사용함) */
	private PropertyUtil propertyUtil;
	
	public ConfigureHelper() {
		
		if(propertyUtil == null) {
			propertyUtil = new PropertyUtil();
		}
	}
	
	@APIOperation(description="인터페이스 사용 유저 설정 정보 세팅")
	public HttpConfigSDO setupUserAgentInfo(UserAgentSDO userAgentDTO, HttpConfigSDO httpConfigSDO) {
		return setupUserAgentInfo(userAgentDTO, httpConfigSDO, false);
	}
	
	
	@APIOperation(description="인터페이스 사용 유저 설정 정보 세팅")
	public HttpConfigSDO setupUserAgentInfo(UserAgentSDO userAgentDTO, HttpConfigSDO httpConfigSDO, boolean isEzwelInsideInterface) {
		/** conntime, readtime, httpAgentType, httpChannelCd, httpClientId, httpRequestId  */
		logger.debug("[START] setupUserAgentInfo userAgentDTO : {}, httpConfigSDO : {}", userAgentDTO, httpConfigSDO);
		propertyUtil.copySameProperty(userAgentDTO, httpConfigSDO, false);
		
		//인터페이스 서버를 거처가는 인터페이스 여부 설정
		httpConfigSDO.setEzwelInsideInterface(isEzwelInsideInterface);
		//인터페이스 서버를 거처가는 인터페이스일 경우 커넥션 테스트 여부 설정
		httpConfigSDO.setConfirmConnect(httpConfigSDO.isEzwelInsideInterface());
		logger.debug("# httpConfigSDO : {}", httpConfigSDO);
		
		/** setup httpApiSignature */
		if(!httpConfigSDO.isEzwelInsideInterface()) {
			
			httpConfigSDO.setHttpApiSignature(APIUtil.getHttpSignature(httpConfigSDO.getHttpAgentId(), httpConfigSDO.getHttpApiKey(), httpConfigSDO.getHttpApiTimestamp()));
		}
		else if(httpConfigSDO.isEzwelInsideInterface() && APIUtil.isNotEmpty(httpConfigSDO.getReceiverRestURI())){
			
			// isEzwel InsideInterface Receiver URI (개발서버 또는 운영서버 컨트롤러 URL세팅)
			httpConfigSDO.setRestURI(InterfaceFactory.getServerHttpDomainURI().concat(httpConfigSDO.getReceiverRestURI()));
			logger.debug("[httpConfigSDO.getRestURI()] : {}", httpConfigSDO.getRestURI());
		}
		
		//스케쥴러에의한 실행이면 HttpRequestId를 시스템 아이디로 설정
		logger.debug("- InitThreadName : {}", Local.commonHeader().getInitThreadName());
		if(Local.commonHeader().getInitThreadName().indexOf(OperateConstants.TASK_SCHEDULER_THREAD_NAME) > -1) {
			httpConfigSDO.setHttpRequestId(OperateConstants.SYSTEM_ID);
		}
		return httpConfigSDO;
	}
	
	@APIOperation(description="체널정보가 없을경우 결과 타입에 메시지세팅")
	public <T extends AbstractSDO> T getChannelNotFoundMessage(Class<T> returnObject) {
		
		T value = null;
		try {
			value = (T) returnObject.newInstance();
			
			//code
			propertyUtil.setProperty(value, MessageConstants.RESPONSE_CODE_FIELD_NAME, Integer.toString(MessageConstants.RESPONSE_CODE_9103));
			//message
			propertyUtil.setProperty(value, MessageConstants.RESPONSE_MESSAGE_FIELD_NAME, MessageConstants.getMessage(MessageConstants.RESPONSE_CODE_9103));
			
		} catch (InstantiationException e) {
			logger.error("[setChannelNotFoundMessage-InstantiationException]", e);
		} catch (IllegalAccessException e) {
			logger.error("[setChannelNotFoundMessage-IllegalAccessException]", e);
		} catch (Exception e) {
			logger.error("[setChannelNotFoundMessage-Exception]", e);
		}
		
		return value;
	}
}
