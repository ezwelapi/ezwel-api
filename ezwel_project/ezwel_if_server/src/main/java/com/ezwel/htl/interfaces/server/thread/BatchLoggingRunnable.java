package com.ezwel.htl.interfaces.server.thread;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.sdo.ApiBatcLogSDO;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractComponent;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.repository.LogRepository;


@APIType(description="배치 로그 저장 Runnable")
public class BatchLoggingRunnable extends AbstractComponent implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(BatchLoggingRunnable.class);
	
	private static final boolean IS_LOGGING = false;
	
	private List<ApiBatcLogSDO> apiBatcLogList;
	
	private LogRepository logginRepository;
	
	public BatchLoggingRunnable(List<ApiBatcLogSDO> apiBatcLogList) {
		this.apiBatcLogList = apiBatcLogList;
	}
	
	@Override
	public void run() { 
		logger.debug("[BatchLoggingRunnable-START] {}", Thread.currentThread().getName());
		
		logginRepository = (LogRepository) LApplicationContext.getBean(logginRepository, LogRepository.class);
		logginRepository.insertEzcApiBatcLog(apiBatcLogList);
	}

}
