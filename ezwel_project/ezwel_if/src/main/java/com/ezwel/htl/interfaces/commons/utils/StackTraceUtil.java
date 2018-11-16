package com.ezwel.htl.interfaces.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIService;
import com.ezwel.htl.interfaces.commons.constants.OperateCode;

/**
 * <pre>
 * StackTrace에서 필요한 정보를 취득하는 유틸 클래스
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */
@APIService
public class StackTraceUtil {

	protected static final Logger logger = LoggerFactory.getLogger(StackTraceUtil.class);
	
    public String getSystemOutFormat(StackTraceElement stackTraceElement) {
    	
    	StackTraceElement beforeStack = stackTraceElement;
    	
    	if( beforeStack == null) {
    		throw new RuntimeException( " StackTraceElement is Null !!" );
    	}
    	
    	StringBuffer message = new StringBuffer();
    	
    	//message.append("sysout");
    	message.append(" [");    	
    	message.append( APIUtil.getFastDate("HH:mm:ss") );
    	message.append("] [");
    	message.append(beforeStack.getClassName());
    	message.append(".");
    	message.append(beforeStack.getMethodName());
    	message.append(":");
    	message.append(beforeStack.getLineNumber());
    	message.append("]");
    	
    	return message.toString();
    }
    
    public String getStackTrace(Throwable cause){

    	StackTraceElement[] stackTrace = cause.getStackTrace();
    	
    	if( stackTrace == null ) {
    		throw new RuntimeException( " StackTraceElement[] is Null !!" );
    	}
    	
    	StringBuffer message = new StringBuffer();
    	if(cause.getMessage() == null) {
    		message.append(cause.toString());
    		message.append(OperateCode.LINE_SEPARATOR);
    	}
    	
    	if(cause.getMessage() != null) {
    		message.append("Message : ");
    		message.append(cause.getMessage());
    		message.append(OperateCode.LINE_SEPARATOR);
    	} 
    	    	
    	if(stackTrace.length > 0) { 
    		message.append("Trace : ");
	    	for(StackTraceElement stack : stackTrace){
	    		message.append(stack.toString());
	    		message.append(OperateCode.LINE_SEPARATOR);
	    	}
    	}
    	
    	if(cause.getCause() != null && cause.getCause().getStackTrace().length > 0) {
    		message.append("Caused by : ");
	    	for(StackTraceElement stack : cause.getCause().getStackTrace()){
	    		message.append(stack.toString());
	    		message.append(OperateCode.LINE_SEPARATOR);
	    	}
    	}
    	
    	if(!message.toString().endsWith(OperateCode.LINE_SEPARATOR) && !message.toString().endsWith("\n")) {
    		message.append(OperateCode.LINE_SEPARATOR);
    	}
    	
    	return message.toString();
    }
    
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
}
