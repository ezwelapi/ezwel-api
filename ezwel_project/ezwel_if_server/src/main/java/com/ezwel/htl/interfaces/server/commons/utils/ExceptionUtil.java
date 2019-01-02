package com.ezwel.htl.interfaces.server.commons.utils;


import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;

@Component
@APIType(description="slf4j로그를 제외한 특수한 경우의 로그 메시지 작성")
public class ExceptionUtil {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionUtil.class);
	
	private CommonUtil commonUtil;
	
	private FileUtil fileUtil;
	
	private APIUtil apiUtil;
	
	@APIOperation(description="전체시설일괄등록 인터페이스 배치 에러로그 작성")
	public boolean writeBatchErrorLog(String userMessages, Object[] messageArguments, String fileName, Exception e) {
		logger.debug("[START] writeBatchErrorLog fileName : {}", fileName);
		
		apiUtil = (APIUtil) LApplicationContext.getBean(apiUtil, APIUtil.class);
		commonUtil = (CommonUtil) LApplicationContext.getBean(commonUtil, CommonUtil.class);
		fileUtil = (FileUtil) LApplicationContext.getBean(fileUtil, FileUtil.class);
		
		boolean out = false;
		
		try {
			if(APIUtil.isEmpty(userMessages)) {
				throw new APIException("[Validate] writeBatchErrorLog 의  메시지가 존재하지 않습니다.");
			}
			if(APIUtil.isEmpty(fileName)) {
				throw new APIException("[Validate] writeBatchErrorLog 의 로그 파일 명이 존재하지 않습니다.");
			}
			if(e == null) {
				throw new APIException("[Validate] writeBatchErrorLog 의 Exception이 존재하지 않습니다.");
			}
			
			String messages = userMessages;
			Object[] argumets = messageArguments;
			String filePath = fileName;
			
			if(argumets != null && argumets.length > 0) {
				messages = APIUtil.formatMessage(messages, argumets);
			}
			
			if(!messages.endsWith("\n") && !messages.endsWith(OperateConstants.LINE_SEPARATOR)) {
				messages = messages.concat(OperateConstants.LINE_SEPARATOR);
			}
			
			messages = messages.concat(APIUtil.formatMessage("Exception Time : {}\nException Message : {}\nException Cause : {}\n\n", 
					 APIUtil.getFastDate(OperateConstants.GENERAL_DATE_FORMAT)
					,e.getMessage()
					,e.getStackTrace()));

			if(filePath.indexOf(OperateConstants.STR_DOT) == -1) {
				filePath = filePath.concat(".log");
			}
			
			/** 에러 발생 레코드 interface batch error log file에 저장후 RuntimeException 없이 로깅후 종료 */
			File file = fileUtil.mkfile(InterfaceFactory.getInterfaceBatchErrorLogPath(), filePath, messages, OperateConstants.DEFAULT_ENCODING, true, true);
			
			if(file.exists()) {
				out = true;
			}
		}
		catch(Exception error) {
			throw new APIException("배치 장애 로그 파일 작성중 장애 발생. 관리자에게 문의하세요.", error);
		}
		
		logger.debug("[END] writeBatchErrorLog result : {}", out);
		return out;
	}
}
