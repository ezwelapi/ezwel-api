package com.ezwel.htl.interfaces.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.sdo.InterfaceLogSDO;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractComponent;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.repository.InterfaceLogRepository;


@APIType(description="로그 저장 Runnable")
public class LoggingRunnableService extends AbstractComponent implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(LoggingRunnableService.class);
	
	private static final boolean IS_LOGGING = false;
	
	private InterfaceLogSDO inInterfaceLogSDO;
	
	private InterfaceLogRepository interfaceLogRepository;
	
	public LoggingRunnableService(InterfaceLogSDO inInterfaceLogSDO) {
		this.inInterfaceLogSDO = inInterfaceLogSDO;
	}
	
	@Override
	public void run() {
		
		interfaceLogRepository = (InterfaceLogRepository) LApplicationContext.getBean(interfaceLogRepository, InterfaceLogRepository.class);
		interfaceLogRepository.insertInterfaceLog(inInterfaceLogSDO);
	}

}