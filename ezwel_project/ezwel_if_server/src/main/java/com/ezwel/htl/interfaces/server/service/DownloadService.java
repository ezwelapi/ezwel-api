package com.ezwel.htl.interfaces.server.service;

import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.thread.CallableExecutor;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.commons.utils.CommonUtil;
import com.ezwel.htl.interfaces.server.commons.utils.data.ImageSDO;

@Service
@APIType(description="시설 이미지 멀티쓰레드 다운로드 서비스")
public class DownloadService implements Callable<ImageSDO> {

	private static final Logger logger = LoggerFactory.getLogger(DownloadService.class);
	
	private CommonUtil commonUtil;
	
	private ImageSDO imageParam;
	
	public DownloadService(ImageSDO inImageParam) {
		/** 필요 한 지역변수 세팅 */
		commonUtil = (CommonUtil) LApplicationContext.getBean(commonUtil, CommonUtil.class);
		
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
	
	
	@APIOperation(description="Http URL Image Download Multi Communication API", isExecTest=true)
	public void downloadMultiImage(List<ImageSDO> imageList) {
		logger.debug("[START] downloadMultiImage\nInput Signature : {}", imageList);
		
		CallableExecutor executor = null;
		
		try {
			if(imageList == null || imageList.size() == 0) {
				throw new APIException(MessageConstants.RESPONSE_CODE_2000, "■ 멀티쓰레드 다운로드 대상 이미지 목록이 존재하지 않습니다.");
			}
			
			executor = new CallableExecutor();
			executor.initThreadPool(30);
			
			for(ImageSDO imageConfig : imageList) {
				//실존하는 이미지로 확인된 URL만 다운로드를 실행한다.
				if(APIUtil.isNotEmpty(imageConfig.getCanonicalPath())) {
					Callable<ImageSDO> callable = new DownloadService(imageConfig);
					//설정된 멀티쓰레드 개수만큼 반복 실행
					executor.addCall(callable);
				}
			}
			
			//현재(20181211)는 결과를 받아 사용하지 않음.
			
		} catch (APIException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "■ 이미지 다운로드 다중 인터페이스 장애 발생", e);
		}
		finally {
			if(executor != null) {
				executor.clear();
			}
		}
		
		logger.debug("[END] downloadMultiImage");		
	}
	
}
