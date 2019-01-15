package com.ezwel.htl.interfaces.server.commons.send;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.server.commons.intercepter.HandlerInterceptor;
import com.ezwel.htl.interfaces.server.commons.sdo.FaxSenderSDO;

@Component
@APIType(description="팩스발송 인터페이스")
public class FaxSender {
	
	private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);
	
	/**
	 * 팩스발송
	 * @param trTitle 발송제목
	 * @param trSendName 발신자이름
	 * @param trSendFaxNum 발신자팩스번호
	 * @param trDocName 발송파일정보
	 * @param toName 수신자명
	 * @param toPhone 수신자팩스번호
	 * @throws NoSuchAlgorithmException
	 */
	@APIOperation(description="팩스발송 인터페이스 DB Insert")
	public void FaxSender(String trTitle, String trSendName, String trSendFaxNum, String trDocName, String toName, String toPhone) throws NoSuchAlgorithmException {
		
		// 팩스발송등록 파라미터
		FaxSenderSDO faxSenderSDO = new FaxSenderSDO();		
		faxSenderSDO.setTrSendDate(new Timestamp(new java.util.Date().getTime())); // 발송일시
		faxSenderSDO.setTrTitle(trTitle);					// 발송제목
		faxSenderSDO.setTrSendName(trSendName); 			// 발신자이름
		faxSenderSDO.setTrSendFaxNum(trSendFaxNum); 		// 발신자팩스번호
		faxSenderSDO.setTrMsgCount(1); 						// 동보건수
		faxSenderSDO.setTrDocName(trDocName); 				// 발송파일정보
		faxSenderSDO.setTrSendStat("0"); 					// 발송상태값(0:발송대기,1:발송중,2:발송완료)

		// 팩스발송내용등록	
		//this.sendManagerDAO.insertFaxSendMeta(faxSenderSDO);
		
//		<!-- 팩스발송내용등록 -->
//	    <insert id="insertFaxSendMeta" parameterType="com.ezwel.htl.interfaces.server.commons.sdo">
//
//	        <selectKey keyProperty="trBatchId" resultType="long" order="BEFORE">
//	            SELECT FC_BATCHID.NEXTVAL FROM DUAL
//	        </selectKey>
//
//	        INSERT INTO FC_META_TRAN (             /* TABLE-팩스전송내용 */
//	               TR_BATCHID                      /* 발송번호 */
//	             , TR_SENDDATE                     /* 발송일시 */
//	             , TR_TITLE                        /* 발송제목 */
//	             , TR_MSGCOUNT                     /* 동보건수 */
//	             , TR_SENDNAME                     /* 발신자이름 */
//	             , TR_SENDFAXNUM                   /* 발신자팩스번호 */
//	             , TR_DOCNAME                      /* 발송파일정보 */
//	             , TR_SENDSTAT                     /* 발송상태값(0:발송대기,1:발송중,2:발송완료) */
//	             , TR_ID                           /* 참조번호 */
//	             , TR_ETC1                         /* 기타필드1 */
//	             , TR_ETC2                         /* 기타필드2(파일경로) */
//	        ) VALUES (
//	               #{trBatchId}                    /* 발송번호 */
//	             , #{trSendDate}                   /* 발송일시 */
//	             , #{trTitle}                      /* 발송제목 */
//	             , #{trMsgCount}                   /* 동보건수 */
//	             , #{trSendName}                   /* 발신자이름 */
//	             , #{trSendFaxNum}                 /* 발신자팩스번호 */
//	             , #{trDocName}                    /* 발송파일정보 */
//	             , #{trSendStat}                   /* 발송상태값(0:발송대기,1:발송중,2:발송완료) */
//	             , #{refrnNo}                      /* 참조번호 */
//	             , #{trEtc1,jdbcType=VARCHAR}      /* 기타필드1 */
//	             , #{trEtc2,jdbcType=VARCHAR}      /* 기타필드2(파일경로) */
//	        )
//	    </insert>

		faxSenderSDO.setTrSerialNo(1); 						// 발송순번
		faxSenderSDO.setTrName(toName); 					// 수신자이름
		faxSenderSDO.setTrPhone(toPhone); 					// 수신자팩스번호
		
		// 팩스발송수신자등록
		//this.sendManagerDAO.insertFaxSendMsg(faxSenderSDO);
		
//		<!-- 팩스발송수신자등록 -->
//	    <insert id="insertFaxSendMsg" parameterType="com.ezwel.htl.interfaces.server.commons.sdo">
//
//	        INSERT INTO FC_MSG_TRAN (              /* TABLE-팩스발송기본내용 */
//	               TR_BATCHID                      /* 발송번호 */
//	             , TR_SERIALNO                     /* 발송순번 */
//	             , TR_SENDDATE                     /* 발송일시 */
//	             , TR_NAME                         /* 수신자이름 */
//	             , TR_PHONE                        /* 수신팩스번호 */
//	             , TR_SENDSTAT                     /* 발송상태값(0:발송대기,1:발송중,2:발송완료) */
//	        ) VALUES (
//	               #{trBatchId}                    /* 발송번호 */
//	             , #{trSerialNo}                   /* 발송순번 */
//	             , #{trSendDate}                   /* 발송일시 */
//	             , #{trName}                       /* 수신자이름 */
//	             , #{trPhone}                      /* 수신팩스번호 */
//	             , #{trSendStat}                   /* 발송상태값(0:발송대기,1:발송중,2:발송완료) */
//	        )
//	    </insert>
		
	}
	
//	@APIOperation(description="현제 Timestamp를 리턴합니다.", isExecTest=true)
//	public static Timestamp getTimestamp(){
//		return new Timestamp(new java.util.Date().getTime());	
//	}
	
}
