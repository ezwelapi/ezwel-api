package com.ezwel.htl.interfaces.server.service;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.commons.utils.CommonUtil;
import com.ezwel.htl.interfaces.server.commons.utils.data.ImageSDO;

@APIType(description="시설 이미지 멀티쓰레드 다운로드 서비스")
public class DownloadService implements Callable<ImageSDO> {

	private static final Logger logger = LoggerFactory.getLogger(DownloadService.class);
	
	private CommonUtil commonUtil;
	
	private ImageSDO imageParam;
	
	public DownloadService(ImageSDO inImageParam) {
		/** 필요 한 지역변수 세팅 */
		commonUtil = (CommonUtil) LApplicationContext.getBean(commonUtil, CommonUtil.class);
		logger.debug("- DownloadService Constructor : {}", commonUtil);
		this.imageParam = inImageParam;
	}
	
	/**
	 * 멀티쓰레드에서 수행할 작업 (call)
	 */
	@Override
	public ImageSDO call() throws Exception {
		ImageSDO out = null;
		
		try {
			out = commonUtil.getImageDownload(imageParam, true);
			logger.debug("★ download execute : {}", out);
		}
		catch(APIException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9401, MessageConstants.getMessage(MessageConstants.RESPONSE_CODE_9401), e);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9401, MessageConstants.getMessage(MessageConstants.RESPONSE_CODE_9401), e);
		}
		
		return out;
	}
	
	public void test() { 
		
	}
}
