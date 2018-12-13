package com.ezwel.htl.interfaces.server.commons.utils;


import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;

@APIType(description="slf4j로그를 제외한 특수한 경우의 로그 메시지 작성")
public class ExceptionUtil {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionUtil.class);
	
	private CommonUtil commonUtil;
	
	private APIUtil apiUtil;
	
	@APIOperation(description="전체시설일괄등록 인터페이스 배치 에러로그 작성")
	public boolean writeBatchErrorLog(String userMessages, Object[] messageArguments, String fileName, Exception e) {
		
		apiUtil = (APIUtil) LApplicationContext.getBean(apiUtil, APIUtil.class);
		commonUtil = (CommonUtil) LApplicationContext.getBean(commonUtil, CommonUtil.class);
		
		boolean out = false;
		
		if(APIUtil.isEmpty(userMessages)) {
			throw new APIException("[Validate] writeBatchErrorLog 의  미시지가 존재하지 않습니다.");
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
		
		if(!messages.endsWith("\n") && !messages.endsWith(OperateConstants.LINE_SEPARATOR)) {
			messages = messages.concat(OperateConstants.LINE_SEPARATOR);
		}
		messages = messages.concat("Exception Time : {}\nException Message : {}\nException Cause : {}\n\n");
		
		if(argumets == null) {
			argumets = new Object[] {};
		}
		
		argumets[argumets.length] = APIUtil.getFastDate(OperateConstants.GENERAL_DATE_FORMAT);
		argumets[argumets.length] = e.getMessage();
		argumets[argumets.length] = e.getStackTrace();

		if(filePath.indexOf(OperateConstants.STR_DOT) == -1) {
			filePath = filePath.concat(".log");
		}
		
		/** 에러 발생 레코드 interface batch error log file에 저장후 RuntimeException 없이 로깅후 종료 */
		File file = commonUtil.mkfile(InterfaceFactory.getInterfaceBatchErrorLogPath(), filePath, messages, OperateConstants.DEFAULT_ENCODING, true, true);
		
		if(file.exists()) {
			out = true;
		}
		
		return out;
	}
}
