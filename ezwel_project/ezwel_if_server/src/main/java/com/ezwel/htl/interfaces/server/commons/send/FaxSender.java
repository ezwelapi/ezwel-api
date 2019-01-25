package com.ezwel.htl.interfaces.server.commons.send;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractDataAccessObject;
import com.ezwel.htl.interfaces.server.commons.intercepter.HandlerInterceptor;
import com.ezwel.htl.interfaces.service.data.send.FaxSenderInSDO;
import com.ezwel.htl.interfaces.service.data.send.FaxSenderOutSDO;

/**
 * <pre>
 * 팩스발송
 * </pre>
 * @author ypjeon@ebsolution.co.kr
 * @date   2019. 01. 22.
 */
@Component
@APIType(description="팩스발송")
public class FaxSender extends AbstractDataAccessObject {
	
	private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);
	
	@APIOperation(description="팩스발송 인터페이스 DB Insert")
	public FaxSenderOutSDO callFaxSender(FaxSenderInSDO faxSenderInSDO) {
		
		Long trBatchId = null;
		Integer txSuccess = 0;
		
		FaxSenderOutSDO faxSenderOutSDO = new FaxSenderOutSDO();
		
		try {
			
			trBatchId = sqlSession.selectOne(getNamespace("SEQUNCE_MAPPER", "insertFcMsgTran"));
			faxSenderInSDO.setTrBatchId(trBatchId);
			
			//Insert
			txSuccess = sqlSession.insert(getNamespace("FC_META_TRAN", "insertFcMetaTran"), faxSenderInSDO);
			
			faxSenderOutSDO.setSuccess(true);
			
		}
		catch(Exception e) {
			throw new APIException("팩스발송 인터페이스 데이터 저장 실패 {}", new Object[] {e.getMessage()}, e) ;
		}
			
		return faxSenderOutSDO;
	}
	
	@APIOperation(description="현제 Timestamp를 리턴합니다.", isExecTest=true)
	public static Timestamp getTimestamp() {
		return new Timestamp(new java.util.Date().getTime());	
	}
	
}
