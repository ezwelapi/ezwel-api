package com.ezwel.htl.interfaces.server.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 16.
 */
@Component
public class ResponseUtil {

	private static final Logger logger = LoggerFactory.getLogger(ResponseUtil.class);
	
	@APIOperation(description="ResponseEntity JSON String")
	public ResponseEntity<String> getResponseEntity(String jsonString) {
		return getResponseEntity(jsonString, null);
	}
	
	@APIOperation(description="ResponseEntity JSON String")
	public ResponseEntity<String> getResponseEntity(String jsonString, String userCharset) {
		logger.debug("[START] responseEntity\n{}", jsonString);
		
		String charset = null;
		if(APIUtil.isEmpty(userCharset)) {
			charset = OperateConstants.DEFAULT_ENCODING;
		}
		else {
			charset = userCharset;
		}
		HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json;charset=".concat(charset));
		
        logger.debug("[END] responseEntity");
		return new ResponseEntity<String>(jsonString, responseHeaders, HttpStatus.CREATED);
	}
	
}
