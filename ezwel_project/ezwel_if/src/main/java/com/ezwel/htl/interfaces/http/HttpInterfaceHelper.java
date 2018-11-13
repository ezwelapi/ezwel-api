package com.ezwel.htl.interfaces.http;

import java.util.Date;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.abstracts.AbstractEntity;
import com.ezwel.htl.interfaces.annotation.APIService;
import com.ezwel.htl.interfaces.http.dto.MultiHttpConfigDTO;



/**
 * <pre>
 *  HTTP 프로토콜을 이용한 싱글 요청 인터페이스와 다중 요청 인터페이스 유틸
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */
@APIService
public class HttpInterfaceHelper implements Callable<AbstractEntity> {

	private static final Logger logger = LoggerFactory.getLogger(HttpInterfaceHelper.class);

	private MultiHttpConfigDTO multiHttpConfigDTO;
	
	private HttpInterfaceService httpInterface;
	
	public HttpInterfaceHelper(MultiHttpConfigDTO multiHttpConfigDTO) {
		if(httpInterface == null) {
			this.httpInterface = new HttpInterfaceService();
		}
		this.multiHttpConfigDTO = multiHttpConfigDTO;
	}
	
	@Override
	public AbstractEntity call() throws Exception {

		logger.debug("[START] Time : {}, Thread Name : {}, httpConfig : {}",  new Date(), Thread.currentThread().getName(), multiHttpConfigDTO);
		//인터페이스 실행
		AbstractEntity out = (AbstractEntity) httpInterface.sendPostJSON(multiHttpConfigDTO.getHttpConfigDTO(), multiHttpConfigDTO.getInputDTO(), multiHttpConfigDTO.getOutputType());
		//결과 리턴
		return out;
	}

}
