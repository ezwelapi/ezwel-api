package com.ezwel.htl.interfaces.server.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.sdo.IfLogSDO;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractComponent;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.repository.ProcessLogRepository;


@APIType(description="인터페이스 로그 저장 Runnable")
public class IfLoggingRunnable extends AbstractComponent implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(IfLoggingRunnable.class);
	
	private static final boolean IS_LOGGING = false;
	
	private IfLogSDO inInterfaceLogSDO;
	
	private ProcessLogRepository loggerRepository;
	
	public IfLoggingRunnable(IfLogSDO inInterfaceLogSDO) {
		this.inInterfaceLogSDO = inInterfaceLogSDO;
	}
	
	@Override
	public void run() {
		
		loggerRepository = (ProcessLogRepository) LApplicationContext.getBean(loggerRepository, ProcessLogRepository.class);
		loggerRepository.insertInterfaceLog(inInterfaceLogSDO);
	}

}
