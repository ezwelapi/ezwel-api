package com.ezwel.htl.interfaces.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;

/**
 * <pre>
 * StackTrace에서 필요한 정보를 취득하는 유틸 클래스
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */
@Component
@APIType(description="오퍼레이션 실행 추적 및 발생한 문제의 문자열 레포트 생성")
public class StackTraceUtil {

	protected static final Logger logger = LoggerFactory.getLogger(StackTraceUtil.class);
	
    @APIOperation(description="이전 caller의 StackTraceElement를 리턴합니다.", isExecTest=true)
	public static StackTraceElement getCurrentStack() {
    	return getCurrentStack(null);
    }
    
	@APIOperation(description="바인드된 클래스의 이전의 StackTraceElement를 리턴합니다.", isExecTest=true)
	public static StackTraceElement getCurrentStack(Class<?> filterClass) {
		StackTraceElement out = null;
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        Class<?> filteredClass = null;
        if(filterClass == null) {
        	filteredClass = StackTraceUtil.class;
        }
        else {
        	filteredClass = filterClass;
        }
        boolean isCurrent = false;
        int point = 0;
        for(StackTraceElement stack : stacktrace) {
        	//logger.debug("stack.getClassName : {}", stack.getClassName());
        	if(filteredClass.getCanonicalName().equals(stack.getClassName())) {
        		isCurrent = true;
        	}
        	if(isCurrent && !filteredClass.getCanonicalName().equals(stack.getClassName())) {
        		out = stacktrace[point];
        		break;
    		}
        	point++;
        }
        
        return out;
	}
	
	@APIOperation(description="발생한 Throwable(문제)의 문자열 레포트 리턴")
    public String getStackTrace(Throwable cause){

    	StackTraceElement[] stackTrace = cause.getStackTrace();
    	
    	if( stackTrace == null ) {
    		throw new RuntimeException( " StackTraceElement[] is Null !!" );
    	}
    	
    	StringBuffer message = new StringBuffer();

    	
    	if(cause.getMessage() != null) {
    		message.append(cause.getMessage());
    		message.append(OperateConstants.LINE_SEPARATOR);
    	} 
    	else {
        	message.append(cause.toString());
        	message.append(OperateConstants.LINE_SEPARATOR);    		
    	}
    	
    	if(cause.getCause() != null) {
    		message.append("Cause : ");
	    	message.append(cause.getCause());
	    	message.append(OperateConstants.LINE_SEPARATOR);
    	}
    	
    	if(stackTrace.length > 0) { 
    		message.append("Trace : ");
	    	for(StackTraceElement stack : stackTrace){
	    		message.append(stack.toString());
	    		message.append(OperateConstants.LINE_SEPARATOR);
	    	}
    	}
    	
    	return message.toString();
    }	
}
