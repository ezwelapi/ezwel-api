package com.ezwel.htl.interfaces.server.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.configure.data.ApiLogReportRecevConfig;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.sdo.ApiBatcLogSDO;
import com.ezwel.htl.interfaces.commons.sdo.IfLogSDO;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractDataAccessObject;
import com.ezwel.htl.interfaces.server.commons.constants.CodeDataConstants;
import com.ezwel.htl.interfaces.server.commons.send.MailSender;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.entities.EzcApiBatcLog;
import com.ezwel.htl.interfaces.server.entities.EzcIfLog;
import com.ezwel.htl.interfaces.service.data.send.MailSenderInSDO;
import com.ezwel.htl.interfaces.service.data.send.MailSenderOutSDO;

/**
 * <pre>
 * 인터페이스 로그 데이터 핸들링
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2019. 01. 14.
 */
@Repository
@APIType(description="인터페이스 로그 데이터 핸들링")
public class LogRepository extends AbstractDataAccessObject {

	private static final Logger logger = LoggerFactory.getLogger(LogRepository.class);
	
	private PropertyUtil propertyUtil;
	
	private MailSender mailSender;
	
	@APIOperation(description="인터페이스 실행 로그 목록 입력")
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class, SQLException.class, APIException.class})
	public void insertInterfaceLog(List<IfLogSDO> inInterfaceLogSDO) {
		if(inInterfaceLogSDO != null) {
			
			String inptDt = APIUtil.getFastDate();
			for(IfLogSDO item : inInterfaceLogSDO) {
				item.setInptDt(inptDt);
				insertInterfaceLog(item);
			}
		}
	}
	
	
	@APIOperation(description="인터페이스 실행 로그 입력")
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class, SQLException.class, APIException.class})
	public void insertInterfaceLog(IfLogSDO inInterfaceLogSDO) {
		logger.debug("[START] insertInterfaceLog [FINAL-LOG-DATA] "/*, inInterfaceLogSDO*/);
		
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		
		Integer out = OperateConstants.INTEGER_ZERO_VALUE;
		EzcIfLog ezcIfLog = null;
		ExecutorService executorService = null;
		
		try {
			
			if(inInterfaceLogSDO != null) {
				
				ezcIfLog = (EzcIfLog) propertyUtil.copySameProperty(inInterfaceLogSDO, EzcIfLog.class);
				ezcIfLog.setIfExecCd(APIUtil.getId());
				if(APIUtil.isEmpty(ezcIfLog.getInptDt())) {
					ezcIfLog.setInptDt(APIUtil.getFastDate());
				}
				//logger.debug("# EzcIfLog : {}", ezcIfLog);
				out = sqlSession.insert(getNamespace("IF_LOG_MAPPER", "insertEzcIfLog"), ezcIfLog);
				logger.debug("[LOG-SAVED] txSuccess : {}", out);
				
				if(out > 0 && APIUtil.NVL(ezcIfLog.getSuccYn(), CodeDataConstants.CD_N).equals(CodeDataConstants.CD_N)) { // 로그 정보 이메일 발송
					
					executorService = Executors.newCachedThreadPool();
					final EzcIfLog mailEzcIfLog = (EzcIfLog) propertyUtil.copySameProperty(ezcIfLog, EzcIfLog.class);
					Runnable runnable = new Runnable() {
						@Override
						public void run() {
							sendInterfaceLog(mailEzcIfLog);
						}
					};
					// 스레드풀에게 작업 처리 요청
					executorService.execute(runnable);
				}
				
			}
		}
		catch(Exception e) {
			
			//None API runtimeException
			logger.error(APIUtil.formatMessage("- 인터페이스 로그 입력 장애발생 {}", ezcIfLog), e);
		}
		finally {
			
			if(inInterfaceLogSDO != null) {
				inInterfaceLogSDO = null;
			}
			if(ezcIfLog != null) {
				ezcIfLog = null;
			}
			if(executorService != null) {
				executorService.shutdown();
			}
		}
		logger.debug("[END] insertInterfaceLog");
	}

	@APIOperation(description="인터페이스 실행 로그 건수 조회")
	public Integer countListEzcIfLog(EzcIfLog ezcIfLog) {
			
		Integer out = null;
		
		try {
			out = sqlSession.selectOne(getNamespace("IF_LOG_MAPPER", "selectCountEzcIfLog"), ezcIfLog);
		}
		catch(APIException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "인터페이스 실행 로그 목록 조회 장애발생.", e);
		}
			
		return out;
	}
	
	
	@APIOperation(description="인터페이스 실행 로그 목록 조회")
	public List<EzcIfLog> selectListEzcIfLog(EzcIfLog ezcIfLog) {
			
		List<EzcIfLog> out = null;
		
		try {
			out = sqlSession.selectList(getNamespace("IF_LOG_MAPPER", "selectListEzcIfLog"), ezcIfLog);
		}
		catch(APIException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "인터페이스 실행 로그 목록 조회 장애발생.", e);
		}
			
		return out;
	}
	
	
	
	@APIOperation(description="인터페이스 실행 로그 단건 조회")
	public EzcIfLog selectEzcIfLog(EzcIfLog ezcIfLog) {
			
		EzcIfLog out = null;
		
		try {
			out = sqlSession.selectOne(getNamespace("IF_LOG_MAPPER", "selectListEzcIfLog"), ezcIfLog);
			if(out == null) {
				throw new APIException(MessageConstants.RESPONSE_CODE_9502, "인터페이스 실행 코드에 해당하는 로그 정보가 존재하지 않습니다.");
			}
		}
		catch(APIException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "인터페이스 실행 로그 목록 조회 장애발생.", e);
		}
			
		return out;
	}
	
	

	@APIOperation(description="API 배치 로그 입력")
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class, SQLException.class, APIException.class})
	public void insertEzcApiBatcLog(List<ApiBatcLogSDO> inApiBatcLogList) {
		logger.debug("[START] insertEzcApiBatcLog [FINAL-LOG-DATA] size : {}", (inApiBatcLogList != null ? inApiBatcLogList.size() : 0));
		
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		Integer out = OperateConstants.INTEGER_ZERO_VALUE;
		EzcApiBatcLog ezcApiBatcLog = null;
		ExecutorService executorService = null;
		Runnable runnable = null;
		try {
			
			if(inApiBatcLogList != null) {
				
				String inptDt = APIUtil.getFastDate();
				for(ApiBatcLogSDO inApiBatcLog : inApiBatcLogList) {
					
					ezcApiBatcLog = (EzcApiBatcLog) propertyUtil.copySameProperty(inApiBatcLog, EzcApiBatcLog.class);
					ezcApiBatcLog.setBatcExecCd(APIUtil.getId());
					ezcApiBatcLog.setInptDt(inptDt);
					//logger.debug("# EzcApiBatcLog : {}", ezcApiBatcLog);
					out += sqlSession.insert(getNamespace("API_BATC_LOG_MAPPER", "insertEzcApiBatcLog"), ezcApiBatcLog);

					// APIUtil.NVL(ezcApiBatcLog.getBatcLogType()).equals("IV")
					if(out > 0) { // 로그 정보 이메일 발송
						executorService = Executors.newCachedThreadPool();
						final EzcApiBatcLog mailApiBatcLog = (EzcApiBatcLog) propertyUtil.copySameProperty(ezcApiBatcLog, EzcApiBatcLog.class);
						runnable = new Runnable() {
							@Override
							public void run() {
								logger.debug("mailApiBatcLog : {}", mailApiBatcLog);
								sendApiBatchLog(mailApiBatcLog);
							}
						};
						
						// 스레드풀에게 작업 처리 요청
						executorService.execute(runnable);
					}
					
				}
				
				logger.debug("[LOG-SAVED] txSuccess : {}", out);
			}
		}
		catch(Exception e) {
			
			//None API runtimeException
			logger.error(APIUtil.formatMessage("- API 배치 로그 입력 장애발생 {}", ezcApiBatcLog), e);
		}
		finally {
			
			if(inApiBatcLogList != null) {
				inApiBatcLogList.clear();
			}
			if(ezcApiBatcLog != null) {
				ezcApiBatcLog = null;
			}
			if(executorService != null) {
				executorService.shutdown();
			}			
		}
		logger.debug("[END] insertEzcApiBatcLog");
	}

	@APIOperation(description="API 배치 로그 건수 조회")
	public Integer countListEzcApiBatcLog(EzcApiBatcLog ezcApiBatcLog) {
			
		Integer out = null;
		
		try {
			out = sqlSession.selectOne(getNamespace("API_BATC_LOG_MAPPER", "selectCountEzcApiBatcLog"), ezcApiBatcLog);
		}
		catch(APIException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "API 배치 로그 목록 조회 장애발생.", e);
		}
			
		return out;
	}
	
	
	@APIOperation(description="API 배치 로그 목록 조회")
	public List<EzcApiBatcLog> selectListEzcApiBatcLog(EzcApiBatcLog ezcApiBatcLog) {
			
		List<EzcApiBatcLog> out = null;
		
		try {
			out = sqlSession.selectList(getNamespace("API_BATC_LOG_MAPPER", "selectListEzcApiBatcLog"), ezcApiBatcLog);
		}
		catch(APIException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "API 배치 로그 목록 조회 장애발생.", e);
		}
			
		return out;
	}
	
	
	
	@APIOperation(description="API 배치 로그 단건 조회")
	public EzcApiBatcLog selectEzcApiBatcLog(EzcApiBatcLog ezcApiBatcLog) {
			
		EzcApiBatcLog out = null;
		
		try {
			out = sqlSession.selectOne(getNamespace("API_BATC_LOG_MAPPER", "selectListEzcApiBatcLog"), ezcApiBatcLog);
			if(out == null) {
				throw new APIException(MessageConstants.RESPONSE_CODE_9502, "배치 실행 코드에 해당하는 로그 정보가 존재하지 않습니다.");
			}
		}
		catch(APIException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "API 배치 로그 목록 조회 장애발생.", e);
		}
			
		return out;
	}
	
	@APIOperation(description="인터페이스 메일 로그 레포트 메일발송")
	private boolean sendInterfaceLog(EzcIfLog ezcIfLog) {
		
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
			contentBuffer.append(OperateConstants.LINE_SEPARATOR);
			
			contentBuffer.append("- 인터페이스 메시지 : ");
			contentBuffer.append(ezcIfLog.getExecMsg());
			contentBuffer.append(OperateConstants.LINE_SEPARATOR);
			contentBuffer.append(OperateConstants.LINE_SEPARATOR);
			
			if(APIUtil.isNotEmpty(ezcIfLog.getInptTelg())) {
				contentBuffer.append("[입력(Request) 전문] 사이즈 : ");
				contentBuffer.append(ezcIfLog.getInptTelgSize());
				contentBuffer.append(" Bytes(length) ");
				contentBuffer.append(OperateConstants.LINE_SEPARATOR);
				
				contentBuffer.append(ezcIfLog.getInptTelg());
				contentBuffer.append(OperateConstants.LINE_SEPARATOR);
				contentBuffer.append(OperateConstants.LINE_SEPARATOR);
			}
			
			if(APIUtil.isNotEmpty(ezcIfLog.getOutpTelg())) {
				contentBuffer.append("[출력(Response) 전문] 사이즈 : ");
				contentBuffer.append(ezcIfLog.getOutpTelgSize());
				contentBuffer.append(" Bytes(length) ");
				contentBuffer.append(OperateConstants.LINE_SEPARATOR);
				
				contentBuffer.append(ezcIfLog.getOutpTelg());
				contentBuffer.append(OperateConstants.LINE_SEPARATOR);
				contentBuffer.append(OperateConstants.LINE_SEPARATOR);
			}
			
			if(APIUtil.isNotEmpty(ezcIfLog.getErrCont())) {
				contentBuffer.append("[장애 내용]");
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
		
		logger.debug("■■■■■■■■■ 인터페이스 로그 레포트 메일발송 성공 여부 : {} ■■■■■■■■■", out);
		return out;
	}
	
	
	@APIOperation(description="API 배치 메일 로그 레포트 메일발송")
	private boolean sendApiBatchLog(EzcApiBatcLog mailApiBatcLog) {
		
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
			
			contentBuffer.append("- 전체 실행 시간(초) : ");
			contentBuffer.append(APIUtil.getTimeMillisToSecond(mailApiBatcLog.getExecEndMlisSecd()));
			contentBuffer.append(OperateConstants.LINE_SEPARATOR);			
			
			contentBuffer.append("- 로그 입력 시각 : ");
			contentBuffer.append(mailApiBatcLog.getInptDt());
			contentBuffer.append(OperateConstants.LINE_SEPARATOR);
			contentBuffer.append(OperateConstants.LINE_SEPARATOR);
			
			contentBuffer.append("- 배치 실행 메시지 : ");
			contentBuffer.append(mailApiBatcLog.getErrMsg());
			contentBuffer.append(OperateConstants.LINE_SEPARATOR);
			contentBuffer.append(OperateConstants.LINE_SEPARATOR);
			
			if(APIUtil.isNotEmpty(mailApiBatcLog.getErrCont())) {
				contentBuffer.append("[장애 내용]");
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
		
		logger.debug("■■■■■■■■■ API 배치 로그 레포트 메일발송 성공 여부 : {} ■■■■■■■■■", out);

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
