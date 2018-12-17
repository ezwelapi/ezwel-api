package com.ezwel.htl.interfaces.commons.configure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigSDO;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;

@Component
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
		/** conntime, readtime, httpAgentType, httpChannelCd, httpClientId, httpRequestId  */
		logger.debug("[START] setupUserAgentInfo userAgentDTO : {}, httpConfigSDO : {}", userAgentDTO, httpConfigSDO);
		propertyUtil.copySameProperty(userAgentDTO, httpConfigSDO, false);
		
		/** setup httpApiSignature */
		if(!httpConfigSDO.isEzwelInsideInterface()) {
			httpConfigSDO.setHttpApiSignature(APIUtil.getHttpSignature(httpConfigSDO.getHttpAgentId(), httpConfigSDO.getHttpApiKey(), httpConfigSDO.getHttpApiTimestamp()));
		}
		else if(httpConfigSDO.isEzwelInsideInterface() && APIUtil.isNotEmpty(httpConfigSDO.getReceiverRestURI())){
			// isEzwel InsideInterface Receiver URI
			httpConfigSDO.setRestURI(InterfaceFactory.getServerHttpDomainURI().concat(httpConfigSDO.getReceiverRestURI()));
			logger.debug("[httpConfigSDO.getRestURI()] : {}", httpConfigSDO.getRestURI());
		}
		
		return httpConfigSDO;
	}
	
	
}
