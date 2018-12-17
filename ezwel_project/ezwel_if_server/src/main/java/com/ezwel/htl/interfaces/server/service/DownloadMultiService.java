package com.ezwel.htl.interfaces.server.service;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.sdo.ImageSDO;
import com.ezwel.htl.interfaces.commons.utils.StackTraceUtil;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractComponent;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.commons.utils.CommonUtil;
import com.ezwel.htl.interfaces.server.entities.EzcFaclImg;

@APIType(description="시설 이미지 멀티쓰레드 다운로드 서비스")
public class DownloadMultiService extends AbstractComponent implements Callable<ImageSDO> {

	private static final Logger logger = LoggerFactory.getLogger(DownloadMultiService.class);
	
	private CommonUtil commonUtil;
	
	private ImageSDO imageParam;
	
	private StackTraceUtil stackTraceUtil;
	
	public DownloadMultiService(ImageSDO inImageParam, Integer count) {
		/** 필요 한 지역변수 세팅 */
		commonUtil = (CommonUtil) LApplicationContext.getBean(commonUtil, CommonUtil.class);
		logger.debug("- DownloadService Initialized : {}", count);
		this.imageParam = inImageParam;
	}
	
	/**
	 * 멀티쓰레드에서 수행할 작업 (call)
	 */
	@Override
	public ImageSDO call() throws Exception {
		ImageSDO out = null;
		
		try {
			logger.debug("[DOWNLOAD-START] BUILD IMAGE URL : {}", imageParam.getImageURL());
			out = commonUtil.getImageDownload(imageParam, true);
			out.setDummy(imageParam.getDummy());
			logger.debug("[DOWNLOAD-END] BUILD IMAGE DOWNLOAD : {}", out.isSave());
		}
		catch(APIException e) {
			//이미지 다운로드중 발생한 익셉션 무시
			logger.error("[APIException] Build Image Download : {}", getCause(e));
		}
		catch(Exception e) {
			//이미지 다운로드중 발생한 익셉션 무시
			logger.error("[Exception] Build Image Download : {}", getCause(e));
		}
		
		return out;
	}
	

}
