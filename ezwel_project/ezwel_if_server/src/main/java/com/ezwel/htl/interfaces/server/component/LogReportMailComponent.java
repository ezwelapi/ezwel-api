package com.ezwel.htl.interfaces.server.component;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.configure.data.ApiLogReportRecevConfig;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.server.commons.constants.CodeDataConstants;
import com.ezwel.htl.interfaces.server.commons.send.MailSender;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.entities.EzcApiBatcLog;
import com.ezwel.htl.interfaces.server.entities.EzcIfLog;
import com.ezwel.htl.interfaces.service.data.send.MailSenderInSDO;
import com.ezwel.htl.interfaces.service.data.send.MailSenderOutSDO;

@Repository
@APIType(description="로그 레포트 메일 발송 컴포넌트")
public class LogReportMailComponent {

	private static final Logger logger = LoggerFactory.getLogger(LogReportMailComponent.class);
	
	private MailSender mailSender;
	
	@APIOperation(description="인터페이스 메일 로그 레포트 메일발송")
	public boolean sendInterfaceLog(EzcIfLog ezcIfLog) {
		
		boolean out = false; 
		
		if(ezcIfLog == null) {
			logger.warn("인터페이스 로그 객체가 존재하지 않습니다.");
			return out;
		}
		
		mailSender = (MailSender) LApplicationContext.getBean(mailSender, MailSender.class);
		
		Integer sendCount = OperateConstants.INTEGER_ONE_VALUE;
		List<MailSenderInSDO> mailSenderInList = null; 
		List<ApiLogReportRecevConfig> receiverList = null; 
		List<MailSenderOutSDO> mailSenderOutList = null; 
		MailSenderInSDO mailSenderIn = null;
		StringBuffer subjectBuffer = null;
		StringBuffer contentBuffer = null;
		
		try {
			receiverList = InterfaceFactory.getApiLogReport().getReceiverList();
			//등록된 수신자가 존재하지 않음
			if(receiverList == null) {
				return false;
			}
			
			//제목 
			subjectBuffer = new StringBuffer();
			subjectBuffer.append("[EZC-API-");
			if(ezcIfLog.getIfReqtDirt().equals("O")) {
				subjectBuffer.append("OUTSIDE ");	
			}
			else {
				subjectBuffer.append("INSIDE ");
			}
			
			subjectBuffer.append("인터페이스로그 레포트] ");
			subjectBuffer.append(ezcIfLog.getIfDesc());
			subjectBuffer.append("(");
			subjectBuffer.append(ezcIfLog.getPartAgentId());
			subjectBuffer.append(") ");
			if(ezcIfLog.getSuccYn().equals(CodeDataConstants.CD_Y)) {
				subjectBuffer.append("성공!!");	
			}
			else {
				subjectBuffer.append("실패..");
			}
			
			
			//내용
			contentBuffer = new StringBuffer();
			contentBuffer.append("<b>");
			contentBuffer.append(subjectBuffer.toString()
				.replace("성공!!", "<span style=\"color:blue;\">성공!!</style>")
				.replace("실패..", "<span style=\"color:red;\">실패..</style>")
			);
			contentBuffer.append("</b>");
			contentBuffer.append(OperateConstants.LINE_SEPARATOR);
			
			contentBuffer.append("- 인터페이스 실행 코드: ");
			contentBuffer.append(ezcIfLog.getIfExecCd());
			contentBuffer.append(OperateConstants.LINE_SEPARATOR);
			
			contentBuffer.append("- 인터페이스 요청자 ID : ");
			contentBuffer.append(ezcIfLog.getIfReqtId());
			contentBuffer.append(OperateConstants.LINE_SEPARATOR);

			contentBuffer.append("- 인터페이스 요청자 IP : ");
			contentBuffer.append(ezcIfLog.getIfReqtIp());
			contentBuffer.append(OperateConstants.LINE_SEPARATOR);
			
			contentBuffer.append("- 실행 시각 : ");
			contentBuffer.append(APIUtil.getTimeMillisToDateString(ezcIfLog.getExecStrtMlisSecd()));
			contentBuffer.append(OperateConstants.LINE_SEPARATOR);
			
			contentBuffer.append("- 종료 시각 : ");
			contentBuffer.append(APIUtil.getTimeMillisToDateString(ezcIfLog.getExecEndMlisSecd()));
			contentBuffer.append(OperateConstants.LINE_SEPARATOR);
			
			contentBuffer.append("- 전체 실행 시간(초) : ");
			contentBuffer.append(APIUtil.getTimeMillisToSecond(ezcIfLog.getExecEndMlisSecd()));
			contentBuffer.append(OperateConstants.LINE_SEPARATOR);			
			
			contentBuffer.append("- 로그 입력 시각 : ");
			contentBuffer.append(ezcIfLog.getInptDt());
			contentBuffer.append(OperateConstants.LINE_SEPARATOR);
			
			contentBuffer.append("- 인터페이스 메시지 : ");
			contentBuffer.append(ezcIfLog.getExecMsg());
			contentBuffer.append(OperateConstants.LINE_SEPARATOR);
			contentBuffer.append(OperateConstants.LINE_SEPARATOR);
			
			if(APIUtil.isNotEmpty(ezcIfLog.getInptTelg())) {
				contentBuffer.append("<b>[입력(Request) 전문]</b> 사이즈 : ");
				contentBuffer.append(ezcIfLog.getInptTelgSize());
				contentBuffer.append(" Bytes(length) ");
				contentBuffer.append(OperateConstants.LINE_SEPARATOR);
				
				contentBuffer.append(ezcIfLog.getInptTelg());
				contentBuffer.append(OperateConstants.LINE_SEPARATOR);
				contentBuffer.append(OperateConstants.LINE_SEPARATOR);
			}
			
			if(APIUtil.isNotEmpty(ezcIfLog.getOutpTelg())) {
				contentBuffer.append("<b>[출력(Response) 전문]</b> 사이즈 : ");
				contentBuffer.append(ezcIfLog.getOutpTelgSize());
				contentBuffer.append(" Bytes(length) ");
				contentBuffer.append(OperateConstants.LINE_SEPARATOR);
				
				contentBuffer.append(ezcIfLog.getOutpTelg());
				contentBuffer.append(OperateConstants.LINE_SEPARATOR);
				contentBuffer.append(OperateConstants.LINE_SEPARATOR);
			}
			
			if(APIUtil.isNotEmpty(ezcIfLog.getErrCont())) {
				contentBuffer.append("<b>[장애 내용]</b>");
				contentBuffer.append(OperateConstants.LINE_SEPARATOR);
				contentBuffer.append(ezcIfLog.getErrCont());
				contentBuffer.append(OperateConstants.LINE_SEPARATOR);
				contentBuffer.append(OperateConstants.LINE_SEPARATOR);
			}
			
			
			mailSenderInList = new ArrayList<MailSenderInSDO>();
			for(ApiLogReportRecevConfig receiver : receiverList) { 
				//메일 정보 설정
				mailSenderIn = new MailSenderInSDO();
				mailSenderIn.setSubject(subjectBuffer.toString());
				mailSenderIn.setRecipient(receiver.getEmailAddr());
				mailSenderIn.setBody(convertGeneralCharToHTML(contentBuffer.toString()));
				mailSenderInList.add(mailSenderIn);
			}
			
			mailSenderOutList = mailSender.callMailSender(mailSenderInList);
			if(mailSenderOutList != null) {
				
				for(MailSenderOutSDO result : mailSenderOutList) {
					logger.debug("# isApiBatch-SendMail : ", result.isSuccess());
					if(result.isSuccess()) {
						sendCount++;
					}
				}
			}
			
			if(sendCount == receiverList.size()) {
				out = true;
			}
		}
		catch(Exception e) {
			logger.error("인터페이스 로그 레포트 이메일 발송중 에러 발생. {}", e.getMessage());
		}
		
		logger.debug("■■■■■■■■■ 인터페이스 로그 레포트 메일발송 성공 개수 : {} ■■■■■■■■■", sendCount);
		return out;
	}
	
	
	@APIOperation(description="API 배치 메일 로그 레포트 메일발송")
	public boolean sendApiBatchLog(EzcApiBatcLog mailApiBatcLog) {
		
		boolean out = false; 
		
		if(mailApiBatcLog == null) {
			logger.warn("API 배치 로그 객체가 존재하지 않습니다.");
			return out;
		}
		
		mailSender = (MailSender) LApplicationContext.getBean(mailSender, MailSender.class);
		
		Integer sendCount = OperateConstants.INTEGER_ONE_VALUE;
		List<MailSenderInSDO> mailSenderInList = null; 
		List<ApiLogReportRecevConfig> receiverList = null; 
		List<MailSenderOutSDO> mailSenderOutList = null; 
		MailSenderInSDO mailSenderIn = null;
		StringBuffer subjectBuffer = null;
		StringBuffer contentBuffer = null;
		
		try {
			receiverList = InterfaceFactory.getApiLogReport().getReceiverList();
			//등록된 수신자가 존재하지 않음
			if(receiverList == null) {
				return false;
			}
			
			//제목 
			subjectBuffer = new StringBuffer();
			subjectBuffer.append("[EZC-API-BATCH 레포트] ");
			subjectBuffer.append(mailApiBatcLog.getBatcDesc());
			
			if(mailApiBatcLog.getBatcLogType().equals("TM")) {
				subjectBuffer.append(" 전체 실행 시간");	
			}
			else if(mailApiBatcLog.getBatcLogType().equals("IV")) {
				subjectBuffer.append(" 유효성 검사 실패..");
			}
			else {
				subjectBuffer.append(" 시스템 에러..");
			}
			
			//내용
			contentBuffer = new StringBuffer();
			contentBuffer.append("<b>");
			contentBuffer.append(subjectBuffer.toString()
				.replace("전체 실행 시간", "<span style=\"color:blue;\">전체 실행 시간</style>")
				.replace("유효성 검사 실패..", "<span style=\"color:orange;\">유효성 검사 실패..</style>")
				.replace("시스템 에러..", "<span style=\"color:red;\">시스템 에러..</style>")
			);
			contentBuffer.append(OperateConstants.LINE_SEPARATOR);
			contentBuffer.append(" 프로그램 : ");
			contentBuffer.append(mailApiBatcLog.getBatcProgType());
			contentBuffer.append("</b>");
			contentBuffer.append(OperateConstants.LINE_SEPARATOR);
			
			contentBuffer.append("- 배치 실행 코드: ");
			contentBuffer.append(mailApiBatcLog.getBatcExecCd());
			contentBuffer.append(OperateConstants.LINE_SEPARATOR);
			
			contentBuffer.append("- 인터페이스 요청자 ID : ");
			contentBuffer.append(mailApiBatcLog.getBatcReqtId());
			contentBuffer.append(OperateConstants.LINE_SEPARATOR);

			contentBuffer.append("- 인터페이스 요청자 IP : ");
			contentBuffer.append(mailApiBatcLog.getBatcReqtIp());
			contentBuffer.append(OperateConstants.LINE_SEPARATOR);
			
			contentBuffer.append("- 실행 시각 : ");
			contentBuffer.append(APIUtil.getTimeMillisToDateString(mailApiBatcLog.getExecStrtMlisSecd()));
			contentBuffer.append(OperateConstants.LINE_SEPARATOR);
			
			contentBuffer.append("- 종료 시각 : ");
			contentBuffer.append(APIUtil.getTimeMillisToDateString(mailApiBatcLog.getExecEndMlisSecd()));
			contentBuffer.append(OperateConstants.LINE_SEPARATOR);
			
			contentBuffer.append("- <b>전체 실행 시간(초) :</b> ");
			contentBuffer.append(APIUtil.getTimeMillisToSecond(mailApiBatcLog.getExecEndMlisSecd()));
			contentBuffer.append(OperateConstants.LINE_SEPARATOR);			
			
			contentBuffer.append("- 로그 입력 시각 : ");
			contentBuffer.append(mailApiBatcLog.getInptDt());
			contentBuffer.append(OperateConstants.LINE_SEPARATOR);
			contentBuffer.append(OperateConstants.LINE_SEPARATOR);
			
			contentBuffer.append("- <b>배치 실행 메시지 :</b> ");
			contentBuffer.append(mailApiBatcLog.getErrMsg());
			contentBuffer.append(OperateConstants.LINE_SEPARATOR);
			contentBuffer.append(OperateConstants.LINE_SEPARATOR);
			
			if(APIUtil.isNotEmpty(mailApiBatcLog.getErrCont())) {
				contentBuffer.append("<b>[장애 내용]</b>");
				contentBuffer.append(OperateConstants.LINE_SEPARATOR);
				contentBuffer.append(mailApiBatcLog.getErrCont());
				contentBuffer.append(OperateConstants.LINE_SEPARATOR);
				contentBuffer.append(OperateConstants.LINE_SEPARATOR);
			}
			
			
			mailSenderInList = new ArrayList<MailSenderInSDO>();
			for(ApiLogReportRecevConfig receiver : receiverList) { 
				//메일 정보 설정
				mailSenderIn = new MailSenderInSDO();
				mailSenderIn.setSubject(subjectBuffer.toString());
				mailSenderIn.setRecipient(receiver.getEmailAddr());
				mailSenderIn.setBody(convertGeneralCharToHTML(contentBuffer.toString()));
				mailSenderInList.add(mailSenderIn);
			}
			
			mailSenderOutList = mailSender.callMailSender(mailSenderInList);
			if(mailSenderOutList != null) {
				
				for(MailSenderOutSDO result : mailSenderOutList) {
					logger.debug("# isIfReport-SendMail : ", result.isSuccess());
					if(result.isSuccess()) {
						sendCount++;
					}
				}
			}
		}
		catch(Exception e) {
			logger.error("인터페이스 로그 레포트 이메일 발송중 에러 발생. {}", e.getMessage());
		}
		
		logger.debug("■■■■■■■■■ API 배치 로그 레포트 메일발송 성공 개수 : {} ■■■■■■■■■", sendCount);

		return out;
	}
	
	private String convertGeneralCharToHTML(String contents) {
		String out = null;
		
		if(contents == null ) {
			return out;
		}
		
		out = contents
				.replace(OperateConstants.LINE_SEPARATOR, "<br>")
				.replace(OperateConstants.STR_WHITE_SPACE, "&nbsp;")
				.replace(OperateConstants.STR_TAB, "&nbsp;&nbsp;&nbsp;&nbsp;");
		
		return out;
	}
}
