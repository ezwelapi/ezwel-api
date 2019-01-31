package com.ezwel.htl.interfaces.server.commons.send;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractDataAccessObject;
import com.ezwel.htl.interfaces.server.commons.intercepter.HandlerInterceptor;
import com.ezwel.htl.interfaces.service.data.send.FaxReaderOutSDO;

/**
 * <pre>
 * 팩스발송
 * </pre>
 * @author ypjeon@ebsolution.co.kr
 * @date   2019. 01. 22.
 */
@Repository
@APIType(description="팩스수신")
public class FaxReader extends AbstractDataAccessObject {
	
	private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);
	
	@APIOperation(description="팩스수신 인터페이스 DB Insert")
	public FaxReaderOutSDO callFaxReader(String reservNum) {
		
		FaxReaderOutSDO faxReaderOutSDO = new FaxReaderOutSDO();
		faxReaderOutSDO.setSuccess(true);
			
		return faxReaderOutSDO;
	}
	
}
