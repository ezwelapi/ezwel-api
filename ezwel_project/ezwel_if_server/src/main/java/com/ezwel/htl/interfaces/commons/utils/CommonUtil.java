package com.ezwel.htl.interfaces.commons.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.OperateCode;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;
/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 21.
 */
@APIType
public class CommonUtil {

	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	

	
    public String readReqeustBodyWithBufferedReader(HttpServletRequest request) {
    	
    	StringBuilder builder = null;
    	
    	try {
    		if(request.getInputStream() != null) {
    			
    			builder = new StringBuilder();
    			BufferedReader input = new BufferedReader(new InputStreamReader(request.getInputStream(), OperateCode.DEFAULT_ENCODING));
    			String buffer = null;
    			while ((buffer = input.readLine()) != null) {
    				if (builder.length() > 0) {
    					builder.append(OperateCode.LINE_SEPARATOR);
    				}
    				builder.append(buffer);
    			}
    		}
		} catch (IOException e) {
			throw new APIException(e);
		}		
		return (builder != null ? builder.toString() : null);
    }
 
    
    
    public String readReqestBodyWithByteStreams(HttpServletRequest request) {
    	String out = null;
    	try {
    		if(request.getInputStream() != null) {
    			out = ByteSource.wrap(ByteStreams.toByteArray(request.getInputStream())).asCharSource(Charsets.UTF_8).read();
    		}
		} catch (IOException e) {
			throw new APIException(e);
		}
    	return out;
    }
    	

	/**
	 * 요청된 데이터 콘텐츠 타입이 멀티파트폼데이터인지 체크하여 줍니다.
	 * @param request
	 * @return
	 */
	public boolean isMultipartRequest(HttpServletRequest request) {
		
		boolean result = false;
		
		String contentType = APIUtil.NVL(request.getContentType(),"").toLowerCase();
		
		if(contentType.indexOf("multipart/form-data; boundary=") > -1) {
			result = true;
			logger.debug("[HANDLER] - isMultipartRequest : " + contentType);
		}
		else {
			logger.debug("[HANDLER] - isNormalRequest : " + contentType);
		}
		return result;
	}
	
	
	/**
	 * <pre>
	 * [메서드 설명]
	 * HttpServletRequest의 content-type을 리턴
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @param request
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 20.
	 */
	public String getRequestContentType(HttpServletRequest request) {
		
		String out = null;
		
		String contentType = APIUtil.NVL(request.getContentType(),"");
		
		if(contentType.toLowerCase().indexOf("multipart/form-data; boundary=") > -1) {
			out = contentType.substring(0, "multipart/form-data".length());
		}
		else {
			out = contentType;
		}
		return out;
	}    
    

	/**
	 * <pre>
	 * [메서드 설명]
	 * 	클라이언트 IP를 리턴합니다.
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @param request
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 14.
	 */
	@APIOperation(description="클라이언트 IP를 리턴합니다.", isExecTest=true)
	public String getClientAddress(HttpServletRequest request) {
		
		String clientAddress  = request.getHeader("X-FORWARDED-FOR"); 
		if(clientAddress == null) 
		{ 
			clientAddress = request.getRemoteAddr(); 
		} 
		
		return clientAddress;
	}
	
}

