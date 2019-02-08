package com.ezwel.htl.interfaces.server.commons.send;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractDataAccessObject;
import com.ezwel.htl.interfaces.server.commons.intercepter.HandlerInterceptor;
import com.ezwel.htl.interfaces.server.entities.FcMetaTran;
import com.ezwel.htl.interfaces.server.entities.FcMsgTran;
import com.ezwel.htl.interfaces.service.data.send.FaxSenderInSDO;
import com.ezwel.htl.interfaces.service.data.send.FaxSenderOutSDO;

/**
 * <pre>
 * 팩스발송
 * </pre>
 * @author ypjeon@ebsolution.co.kr
 * @date   2019. 01. 22.
 */
@Repository
@APIType(description="팩스발송")
public class FaxSender extends AbstractDataAccessObject {
	
	private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);
	
	static final String TR_SENDNAME = "이지웰";		//발신자이름
	static final String TR_NAME = "이지웰";			//수신자이름
	static final String TR_PHONE = "0269191002";	//수신팩스번호
	
	@APIOperation(description="팩스발송 인터페이스 DB Insert")
	public FaxSenderOutSDO callFaxSender(FaxSenderInSDO faxSenderInSDO) {
		
		FcMetaTran fcMetaTran = new FcMetaTran();
		FcMsgTran fcMsgTran = new FcMsgTran();
		
		BigDecimal trBatchId = null;
		Date trSendDate = this.getTimestamp();
		Integer fcMetaSuccess = 0;
		Integer fcMsgSuccess = 0;
		
		FaxSenderOutSDO faxSenderOutSDO = new FaxSenderOutSDO();
		
		try {
			
			trBatchId = sqlSession.selectOne(getNamespace("SEQUNCE_MAPPER", "selectTrBatchIdSeq"));
			fcMetaTran.setTrBatchId(trBatchId);								//고유번호
			fcMetaTran.setTrSendDate(trSendDate);							//발송일시
			fcMetaTran.setTrTitle(faxSenderInSDO.getTrTitle());				//발송제목
			fcMetaTran.setTrSendName(TR_SENDNAME);							//발신자이름
			fcMetaTran.setTrSendFaxNum(faxSenderInSDO.getTrSendFaxNum());	//발신자번호
			fcMetaTran.setTrMsgCount(1);									//동보건수
			fcMetaTran.setTrDocName(faxSenderInSDO.getTrDocName());			//발송파일정보
			fcMetaTran.setTrSendStat("0");									//발송상태값(0:발송대기,1:발송중,2:발송완료)
			
			//Insert
			fcMetaSuccess = sqlSession.insert(getNamespace("FC_META_TRAN", "insertFcMetaTran"), fcMetaTran);			
			
			fcMsgTran.setTrBatchId(trBatchId);		//고유번호
			fcMsgTran.setTrSerialNo(1);				//발송순번
			fcMsgTran.setTrSendDate(trSendDate);	//발송일시
			fcMsgTran.setTrName(TR_NAME);			//수신자이름
			fcMsgTran.setTrPhone(TR_PHONE);			//수신팩스번호
			fcMsgTran.setTrSendStat("0");			//발송상태값(0:발송대기,1:발송중,2:발송완료)
			
			//Insert
			fcMsgSuccess = sqlSession.insert(getNamespace("FC_MSG_TRAN", "insertFcMetaTran"), fcMsgTran);
			
			faxSenderOutSDO.setSuccess(true);
			
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9902, "팩스발송 인터페이스 장애발생 ()데이터 저장 실패", e);
		}
			
		return faxSenderOutSDO;
	}
	
	@APIOperation(description="현제 Timestamp를 리턴합니다.", isExecTest=true)
	public static Timestamp getTimestamp() {
		return new Timestamp(new java.util.Date().getTime());	
	}
	
}
