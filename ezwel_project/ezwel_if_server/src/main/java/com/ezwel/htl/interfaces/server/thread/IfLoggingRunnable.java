package com.ezwel.htl.interfaces.server.thread;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.sdo.IfLogSDO;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractComponent;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.repository.LogRepository;


@APIType(description="인터페이스 로그 저장 Runnable")
public class IfLoggingRunnable extends AbstractComponent implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(IfLoggingRunnable.class);
	
	private static final boolean IS_LOGGING = false;
	
	private IfLogSDO inInterfaceLogSDO;
	
	private List<IfLogSDO> inInterfaceLogList;
	
	private LogRepository loggerRepository;
	
	public IfLoggingRunnable(IfLogSDO inInterfaceLogSDO) {
		//ThreadLocal 초기화
		Local.commonHeader();
		
		this.inInterfaceLogSDO = inInterfaceLogSDO;
	}
	
	public IfLoggingRunnable(List<IfLogSDO> inInterfaceLogList) {
		//ThreadLocal 초기화
		Local.commonHeader();
		
		this.inInterfaceLogList = inInterfaceLogList;
	}
	
	@Override
	public void run() {
		logger.debug("[IfLoggingRunnable-START] {}", Thread.currentThread().getName());
		
		loggerRepository = (LogRepository) LApplicationContext.getBean(loggerRepository, LogRepository.class);
		
		try {
			if(inInterfaceLogSDO != null) {
				loggerRepository.insertInterfaceLog(inInterfaceLogSDO);
			} 
			else if(inInterfaceLogList != null) {
				loggerRepository.insertInterfaceLog(inInterfaceLogList);
			}
		}finally {
			Local.remove();
		}
	}

}
