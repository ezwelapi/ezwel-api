package com.ezwel.htl.interfaces.commons.entity;

import com.ezwel.htl.interfaces.commons.abstracts.APIObject;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;

/**
 * <pre>
 *  API Operation Execute Info in ThreadLocal
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */
@APIModel
public class RuntimeHeader extends APIObject {

	private long startTimeMillis;
	
	private long endTimeMillis;
	
	private long lapTimeMillis;
	
	private String className;
	
	private String methodName;
	
	private StackTraceElement[] stackTraceElement;
	
	public RuntimeHeader() {
		startTimeMillis = OperateConstants.LONG_ZERO_VALUE;
		endTimeMillis = OperateConstants.LONG_MINUS_ONE;
		lapTimeMillis = OperateConstants.LONG_MINUS_ONE;
		className = null;
		methodName = null;
		stackTraceElement = null;
	}

	public long getStartTimeMillis() {
		return startTimeMillis;
	}

	public void setStartTimeMillis(long startTimeMillis) {
		this.startTimeMillis = startTimeMillis;
	}


	
	public long getEndTimeMillis() {
		setEndTimeMillis();	
		return endTimeMillis;
	}

	public void setEndTimeMillis() {
		if(this.endTimeMillis == OperateConstants.LONG_MINUS_ONE) {
			setEndTimeMillis(APIUtil.currentTimeMillis());
		}		
	}
	
	public void setEndTimeMillis(long endTimeMillis) {
		this.endTimeMillis = endTimeMillis;
	}

	public long getLapTimeMillis() {
		if(this.lapTimeMillis == OperateConstants.LONG_MINUS_ONE) this.lapTimeMillis = getEndTimeMillis() - getStartTimeMillis();
		return lapTimeMillis;
	}

	public void setLapTimeMillis(long lapTimeMillis) {
		this.lapTimeMillis = lapTimeMillis;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public StackTraceElement[] getStackTraceElement() {
		return stackTraceElement;
	}

	public void setStackTraceElement(StackTraceElement[] stackTraceElement) {
		this.stackTraceElement = stackTraceElement;
	}
	
	
}
