package com.ezwel.htl.interfaces.server.thread;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.sdo.ApiBatcLogSDO;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
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
		//ThreadLocal 초기화
		Local.commonHeader();
		
		this.apiBatcLogList = apiBatcLogList;
	}
	
	@Override
	public void run() { 
		logger.debug("[START-BatchLoggingRunnable] {}", Thread.currentThread().getName());
		
		logginRepository = (LogRepository) LApplicationContext.getBean(logginRepository, LogRepository.class);
		
		try {
			
			logginRepository.insertEzcApiBatcLog(apiBatcLogList);
		}
		catch(Exception e) {
			logger.error(APIUtil.NVL(Local.commonHeader().getMessage(), "API 배치 로그저장 장애 발생"), APIUtil.ONVL(Local.commonHeader().getThrowable(), e));
		}
		finally {
			//ThreadLocal 종료
			Local.remove();
		}
		
		logger.debug("[END-BatchLoggingRunnable] {}", Thread.currentThread().getName());
	}

}
