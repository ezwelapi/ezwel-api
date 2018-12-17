package com.ezwel.htl.interfaces.server.commons.abstracts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.utils.StackTraceUtil;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;

public abstract class AbstractComponent {

	private static final Logger logger = LoggerFactory.getLogger(AbstractComponent.class);

	protected StackTraceUtil stackTraceUtil;
	
	protected String getCause(Throwable e) {
		stackTraceUtil = (StackTraceUtil) LApplicationContext.getBean(stackTraceUtil, StackTraceUtil.class);
		
		return stackTraceUtil.getStackTrace(e);
	}
	
}
