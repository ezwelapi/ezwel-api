package com.ezwel.htl.interfaces.commons.http;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.http.data.MultiHttpConfigSDO;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;



/**
 * <pre>
 *  HTTP 프로토콜을 이용한 싱글 요청 인터페이스와 다중 요청 인터페이스 유틸
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */

@APIType(description="HTTP 인터페이스 멀티쓰레드 Callable")
public class HttpInterfaceHelper implements Callable<AbstractSDO> {

	private static final Logger logger = LoggerFactory.getLogger(HttpInterfaceHelper.class);

	private MultiHttpConfigSDO multiHttpConfigDTO;
	
	@Autowired
	private HttpInterfaceExecutor httpInterface;
	
	@Autowired
	private PropertyUtil propertyUtil;
	
	public HttpInterfaceHelper(MultiHttpConfigSDO multiHttpConfigDTO) {
		//ThreadLocal 초기화
		Local.commonHeader();
		
		if(httpInterface == null) {
			this.httpInterface = new HttpInterfaceExecutor();
		}
		if(propertyUtil == null) {
			this.propertyUtil = new PropertyUtil();
		}
		this.multiHttpConfigDTO = multiHttpConfigDTO;
		
		//logger.debug("- HttpInterfaceHelper : {}", multiHttpConfigDTO);
	}
	
	@Override
	public AbstractSDO call() throws Exception {
		logger.debug("[HttpInterfaceHelper-START] ThreadName : {}", Thread.currentThread().getName());
		//logger.debug("[START] Time : {}, Thread Name : {}, httpConfig : {}",  new Date(), Thread.currentThread().getName(), multiHttpConfigDTO);
		AbstractSDO out = null;
		try {
			
			//인터페이스 실행
			multiHttpConfigDTO.getHttpConfigDTO().setMultiThread(true);
			Object output = httpInterface.sendJSON(multiHttpConfigDTO.getHttpConfigDTO(), multiHttpConfigDTO.getInputDTO(), multiHttpConfigDTO.getOutputType());

			if(output != null) {
				//logger.debug("$output : {}", output);
				//setup httpAgentId
				propertyUtil.setProperty(output, OperateConstants.FIELD_HTTP_AGENT_ID, multiHttpConfigDTO.getHttpConfigDTO().getHttpAgentId());
				//setup httpAgentDesc
				propertyUtil.setProperty(output, OperateConstants.FIELD_HTTP_AGENT_DESC, multiHttpConfigDTO.getHttpConfigDTO().getDescription());
				//setup patnCdType
				propertyUtil.setProperty(output, OperateConstants.FIELD_PATN_CD_TYPE, multiHttpConfigDTO.getHttpConfigDTO().getPatnCdType());
			}
			//setup output 
			out = (AbstractSDO) output;
			//결과 리턴
		}
		catch(Exception e) {
			logger.error(APIUtil.NVL(Local.commonHeader().getMessage(), "멀티쓰레드 인터페이스 장애 발생"), APIUtil.ONVL(Local.commonHeader().getThrowable(), e));
		}		
		finally {
			
			//ThreadLocal 종료
			Local.remove();
		}
		
		logger.debug("[HttpInterfaceHelper-END] ThreadName : {}", Thread.currentThread().getName());
		return out;
	}

}
