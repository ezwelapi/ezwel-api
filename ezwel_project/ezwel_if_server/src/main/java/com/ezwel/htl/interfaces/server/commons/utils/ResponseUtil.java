package com.ezwel.htl.interfaces.server.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ezwel.htl.interfaces.commons.constants.OperateConstants;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 16.
 */
public class ResponseUtil {

	private static final Logger logger = LoggerFactory.getLogger(ResponseUtil.class);
	
	public ResponseEntity<String> responseEntity(String jsonString) {
		logger.debug("[START] responseEntity\n{}", jsonString);
		
		HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json;charset=".concat(OperateConstants.DEFAULT_ENCODING));
		
        logger.debug("[END] responseEntity");
		return new ResponseEntity<String>(jsonString, responseHeaders, HttpStatus.CREATED);
	}
	
}
