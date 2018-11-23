package com.ezwel.interfaces.demo;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.http.HttpInterfaceExecutorService;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigDTO;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentDTO;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.service.data.ezwelJob.EzwelJobOutSDO;
import com.ezwel.htl.interfaces.service.data.record.RecordInSDO;
import com.ezwel.htl.interfaces.service.data.record.RecordOutSDO;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 19.
 */
public class InsideInterfaceDemo {

	private static final Logger logger = LoggerFactory.getLogger(InterfaceDemoService.class);

	private PropertyUtil propertyUtil;
	
	private HttpInterfaceExecutorService inteface;
	
	public InsideInterfaceDemo() {
		InterfaceFactory factory = new InterfaceFactory();
		factory.setConfigXmlPath("/interfaces/interface-configure.xml");
		factory.initFactory();
		
		propertyUtil = new PropertyUtil();
		
		inteface = new HttpInterfaceExecutorService();
	}
	
	@APIOperation(description="인터페이스 사용 유저 설정 정보 세팅")
	private void setupUserAgentInfo(HttpConfigDTO config, UserAgentDTO userAgentDTO) {
		/** conntime, readtime, httpAgentType, httpChannelCd, httpClientId, httpRequestId  */
		propertyUtil.copySameProperty(userAgentDTO, config);
		/** setup httpApiSignature */
		config.setHttpApiSignature(APIUtil.getHttpSignature(config.getHttpAgentId(), config.getHttpApiKey(), config.getHttpApiTimestamp()));
	}
	
	@Test
	public void callRecord() {

		UserAgentDTO userAgentDTO = new UserAgentDTO();
		userAgentDTO.setHttpAgentId("10000495");
		userAgentDTO.setHttpAgentType("에이전트유형");
		userAgentDTO.setHttpChannelCd("체널코드");
		userAgentDTO.setHttpRequestId("요청자ID");

		HttpConfigDTO httpConfigDTO = InterfaceFactory.getChannel("record", userAgentDTO.getHttpAgentId());
		
		setupUserAgentInfo(httpConfigDTO, userAgentDTO);
		httpConfigDTO.setRestURI("http://localhost:8282/ezwel_if_server/API1.0/"+userAgentDTO.getHttpAgentId()+"/facl/record");
		
		RecordInSDO in = new RecordInSDO();
		in.setDataUrl("http://dataUrl");
		
		RecordOutSDO out = (RecordOutSDO) inteface.sendPostJSON(httpConfigDTO, in, RecordOutSDO.class);
		
		logger.debug("callRecord : {}", out);
	}
	
}
