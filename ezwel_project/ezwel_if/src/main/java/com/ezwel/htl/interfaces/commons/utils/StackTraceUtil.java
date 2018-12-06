package com.ezwel.htl.interfaces.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;

/**
 * <pre>
 * StackTrace에서 필요한 정보를 취득하는 유틸 클래스
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */
@APIType
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
}
