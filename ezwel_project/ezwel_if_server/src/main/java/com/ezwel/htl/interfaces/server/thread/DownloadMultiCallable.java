package com.ezwel.htl.interfaces.server.thread;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.sdo.ImageSDO;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractComponent;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.commons.utils.FileUtil;


@APIType(description="시설 이미지 멀티쓰레드 다운로드 서비스")
public class DownloadMultiCallable extends AbstractComponent implements Callable<ImageSDO> {

	private static final Logger logger = LoggerFactory.getLogger(DownloadMultiCallable.class);
	
	private static final boolean IS_LOGGING = false;
	
	private FileUtil fileUtil;
	
	private ImageSDO imageParam;
	
	/**
	 * Constructor
	 * @param inImageParam
	 * @param count
	 */
	public DownloadMultiCallable(ImageSDO inImageParam, Integer count) {
		//ThreadLocal 초기화
		Local.commonHeader();
		
		logger.debug("- DownloadService Initialized : {}, URL : {}", count, inImageParam.getImageURL());
		/** 필요 한 지역변수 세팅 */
		fileUtil = (FileUtil) LApplicationContext.getBean(fileUtil, FileUtil.class);
		this.imageParam = inImageParam;
	}
	
	/**
	 * 멀티쓰레드에서 수행할 작업 (call)
	 */
	@Override
	public ImageSDO call() throws Exception {
		ImageSDO out = null;
		
		try {
			if(IS_LOGGING) {
				logger.debug("[DOWNLOAD-START] BUILD IMAGE URL : {}", imageParam.getImageURL());
			}
			out = fileUtil.getImageDownload(imageParam, true);
			out.setDummy(imageParam.getDummy());
			if(IS_LOGGING) {
				logger.debug("[DOWNLOAD-END] BUILD IMAGE DOWNLOAD : {}", out.isSave());
			}
		}
		catch(APIException e) {
			//이미지 다운로드중 발생한 익셉션 무시
			logger.error("[APIException] Build Image Download : {}", getCause(e));
		}
		catch(Exception e) {
			//이미지 다운로드중 발생한 익셉션 무시
			logger.error("[Exception] Build Image Download : {}", getCause(e));
		}
		finally {
			//ThreadLocal 종료
			Local.remove();
		}
		
		return out;
	}
	

}
