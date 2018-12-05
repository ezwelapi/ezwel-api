package com.ezwel.htl.interfaces.commons.configure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigSDO;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;

@Component
public class ConfigureHelper {

	private static final Logger logger = LoggerFactory.getLogger(ConfigureHelper.class);
	
	@Autowired
	private PropertyUtil propertyUtil;
	
	public ConfigureHelper() {
		
		if(propertyUtil == null) {
			propertyUtil = new PropertyUtil();
		}
	}
	
	@APIOperation(description="인터페이스 사용 유저 설정 정보 세팅")
	public HttpConfigSDO setupUserAgentInfo(UserAgentSDO userAgentDTO, HttpConfigSDO config) {
		/** conntime, readtime, httpAgentType, httpChannelCd, httpClientId, httpRequestId  */
		propertyUtil.copySameProperty(userAgentDTO, config);
		/** setup httpApiSignature */
		config.setHttpApiSignature(APIUtil.getHttpSignature(config.getHttpAgentId(), config.getHttpApiKey(), config.getHttpApiTimestamp()));
		return config;
	}
	
	
}
