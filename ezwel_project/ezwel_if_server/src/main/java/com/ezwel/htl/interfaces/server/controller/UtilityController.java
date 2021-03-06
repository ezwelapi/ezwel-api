package com.ezwel.htl.interfaces.server.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigSDO;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.RegexUtil;
import com.ezwel.htl.interfaces.server.commons.interfaces.RequestNamespace;
import com.ezwel.htl.interfaces.server.commons.morpheme.cm.MorphemeUtil;
import com.ezwel.htl.interfaces.server.commons.morpheme.en.EnglishAnalyzers;
import com.ezwel.htl.interfaces.server.commons.morpheme.ko.KoreanAnalyzers;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.commons.utils.FileUtil;
import com.ezwel.htl.interfaces.server.commons.utils.ResponseUtil;
import com.ezwel.htl.interfaces.server.sdo.AgentApiKeySDO;
import com.ezwel.htl.interfaces.server.sdo.MorphemeSDO;
import com.ezwel.htl.interfaces.server.service.SendService;
import com.ezwel.htl.interfaces.service.data.send.FaxReaderOutSDO;
import com.ezwel.htl.interfaces.service.data.send.FaxSenderInSDO;
import com.ezwel.htl.interfaces.service.data.send.FaxSenderOutSDO;
import com.ezwel.htl.interfaces.service.data.send.MailSenderInSDO;
import com.ezwel.htl.interfaces.service.data.send.MailSenderOutSDO;
import com.ezwel.htl.interfaces.service.data.send.SmsSenderInSDO;
import com.ezwel.htl.interfaces.service.data.send.SmsSenderOutSDO;

@Controller
@RequestMapping(value = RequestNamespace.NAME_SPACE)
@APIType(description = "부가기능 컨트롤러")
public class UtilityController {
	
	private static final Logger logger = LoggerFactory.getLogger(UtilityController.class);
	
	private APIUtil apiUtil;
	private FileUtil fileUtil;
	private KoreanAnalyzers koreanAnalyzer;
	private EnglishAnalyzers englishAnalayzer;
	private RegexUtil regexUtil;
	private ResponseUtil responseUtil;
	private SendService sendService;
	
	@APIOperation(description="테스트 JSP Forward Operation")
	@RequestMapping(value="/{fileName}")
	public String forward(@PathVariable("fileName") String fileName, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("[FORWARD] {}", fileName);
		return RequestNamespace.NAME_SPACE.concat("/").concat(fileName);
	}

	
	@APIOperation(description="에이젼트 키 발급", isOutputJsonMarshall=true, returnType=AgentApiKeySDO.class)
	@RequestMapping(value="/agent/apiKey")
	public Object agentApiKey(AgentApiKeySDO in) {
		logger.debug("[START] agentApiKey \n{}", in);
		
		apiUtil = (APIUtil) LApplicationContext.getBean(apiUtil, APIUtil.class);
		
		AgentApiKeySDO out = new AgentApiKeySDO();
		
		out.setAgentName(in.getAgentName());
		out.setHttpAgentId(in.getHttpAgentId());
		// inside api key
		out.setInsideKey(apiUtil.createApiKey("i", in.getAgentName(), in.getHttpAgentId()));
		
		// outside api key
		out.setOutsideKey(apiUtil.createApiKey("o", in.getAgentName(), in.getHttpAgentId()));
		
		logger.debug("[END] agentApiKey \n{}", out);
		return out;
	}
	

	@RequestMapping(value = "/getHttpSignature")
	@APIOperation(description = "시그니처 발급", isOutputJsonMarshall=true, returnType=HttpConfigSDO.class)
	public Object getHttpSignature(HttpConfigSDO httpConfigSDO) {
		logger.debug("[START] getHttpSignature {}", httpConfigSDO);
		
		HttpConfigSDO out = httpConfigSDO;
		
		out.setHttpApiSignature(APIUtil.getHttpSignature(out.getHttpAgentId(), out.getHttpApiKey(), out.getHttpApiTimestamp()));
		
		logger.debug("[END] getHttpSignature {}", out);
		return out;
	}
	
	@APIOperation(description="한글 형태소 분석", isOutputJsonMarshall=true, returnType=MorphemeSDO.class)
	@RequestMapping(value="/morp/korean")
	public Object morpKorean(MorphemeSDO morphemeSDO) {
		logger.debug("[START] morpKorean {}", morphemeSDO);

		MorphemeSDO out = new MorphemeSDO();
		
		koreanAnalyzer = (KoreanAnalyzers) LApplicationContext.getBean(koreanAnalyzer, KoreanAnalyzers.class);
		englishAnalayzer = (EnglishAnalyzers) LApplicationContext.getBean(englishAnalayzer, EnglishAnalyzers.class);
		regexUtil = (RegexUtil) LApplicationContext.getBean(regexUtil, RegexUtil.class);
		apiUtil = (APIUtil) LApplicationContext.getBean(apiUtil, APIUtil.class);
		
		String conversion = null;
		
		List<List<String>> korMorphemeList = new ArrayList<List<String>>();
		for(String input : morphemeSDO.getSentenceList()) {
			if(input == null || input.isEmpty()) {
				continue;
			}
			
			if(regexUtil.testPattern(input, MorphemeUtil.PATTERN_KOREAN_SENTENCE)) {
				conversion = apiUtil.toHalfChar(input).replaceAll("(?i)".concat(MorphemeUtil.PATTERN_CORP_SIGN).concat("|").concat(MorphemeUtil.PATTERN_SPEC_CHAR).concat("|").concat(MorphemeUtil.PATTERN_ENGLISH_SENTENCE), OperateConstants.STR_BLANK).trim();
				korMorphemeList.add(new ArrayList<String>(koreanAnalyzer.getKoreanMorphologicalAnalysis(conversion)));
			}
		}		
		out.setKorMorphemeList(korMorphemeList);
		
		List<List<String>> engMorphemeList = new ArrayList<List<String>>();
		for(String input : morphemeSDO.getSentenceList()) {
			if(input == null || input.isEmpty()) {
				continue;
			}
			
			if(regexUtil.testPattern(input, MorphemeUtil.PATTERN_ENGLISH_SENTENCE)) {
				conversion = apiUtil.toHalfChar(input).replaceAll("(?i)".concat(MorphemeUtil.PATTERN_SPEC_CHAR).concat("|").concat(MorphemeUtil.PATTERN_KOREAN_SENTENCE), OperateConstants.STR_BLANK).trim();
				engMorphemeList.add(new ArrayList<String>(englishAnalayzer.getEnglishMorphologicalAnalysis(conversion)));
			}
		}		
		out.setEngMorphemeList(engMorphemeList);
		
		logger.debug("[END] morpKorean ");
		return out;
	}	 
	
	@APIOperation(description="인터페이스 환경설정파일 XML")
	@RequestMapping(value="/configXML")
	public ResponseEntity<String> getInterfaceConfigXML(HttpServletRequest request) {
		logger.debug("[START] getInterfaceConfigXML");
		
		if(!InterfaceFactory.isMasterServer()) {
			throw new APIException("요청하신 서버는 인터페이스 마스터 서버가 아닙니다. 인터페이스 설정정보 요청은 인터페이스 마스터 서버에게만 가능합니다.");
		}
		fileUtil = (FileUtil) LApplicationContext.getBean(fileUtil, FileUtil.class);
		responseUtil = (ResponseUtil) LApplicationContext.getBean(responseUtil, ResponseUtil.class);
		
		ResponseEntity<String> out = null;
		String configXml = null;
		URL configFileURL = null;
		BufferedReader bufferedReader = null;
		InputStream inputStream = null;
		
		try {
			configFileURL = InterfaceFactory.getConfigXmlFileURL();
			logger.debug("*- configFileURL : {}", configFileURL);
			
			if(configFileURL == null) {
				throw new APIException("인터페이스 마스터 서버에 환경 설정 파일 경로가 캐쉬되지 않았습니다.");
			}

			if(configFileURL.getPath().indexOf(OperateConstants.JAR_SEPARATOR) > -1) {
	            configXml = fileUtil.getInnerJarTextResource(configFileURL);
			}
			else {
				configXml = fileUtil.getTextFileContent(new File(configFileURL.getPath()));
			}
			
			logger.debug("*- XML Contents : {}", configXml);
			out = responseUtil.getResponseEntity(configXml, OperateConstants.DATA_TYPE_XML);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9301, e);
		}
		finally {
			if(bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					logger.error("[IOException] bufferedReader.close", e);
				}
			}
			
			if(inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("[IOException] inputStream.close", e);
				};
			}
		}
		
		logger.debug("[END] getInterfaceConfigXML");
		return out;
	}
	
	@APIOperation(description="문자발송 인터페이스", isOutputJsonMarshall=true, returnType=SmsSenderOutSDO.class)
	@RequestMapping(value="/callSmsSender")
	public Object callSmsSender(SmsSenderInSDO smsSenderInSDO) {
		
		sendService = (SendService) LApplicationContext.getBean(sendService, SendService.class);
		
		SmsSenderOutSDO out = new SmsSenderOutSDO();
		out = (SmsSenderOutSDO) sendService.callSmsSender(smsSenderInSDO);
		
		return out;
		
	}
	
	@APIOperation(description="메일발송 인터페이스", isOutputJsonMarshall=true, returnType=MailSenderOutSDO.class)
	@RequestMapping(value="/callMailSender")
	public Object callMailSender(MailSenderInSDO mailSenderInSDO) {
		
		sendService = (SendService) LApplicationContext.getBean(sendService, SendService.class);
		
		MailSenderOutSDO out = new MailSenderOutSDO();
		out = (MailSenderOutSDO) sendService.callMailSender(mailSenderInSDO);
		
		return out;
		
	}
	
	@APIOperation(description="팩스발송 인터페이스", isOutputJsonMarshall=true, returnType=FaxSenderOutSDO.class)
	@RequestMapping(value="/callFaxSender")
	public Object callFaxSender(FaxSenderInSDO faxSenderInSDO) {
		
		sendService = (SendService) LApplicationContext.getBean(sendService, SendService.class);
		
		FaxSenderOutSDO out = new FaxSenderOutSDO();
		out = (FaxSenderOutSDO) sendService.callFaxSender(faxSenderInSDO);
		
		return out;
		
	}
	
	@APIOperation(description="팩스리더 인터페이스", isOutputJsonMarshall=true, returnType=FaxSenderOutSDO.class)
	@RequestMapping(value="/callFaxReader")
	public Object callFaxReader(String reservNum) {
		
		sendService = (SendService) LApplicationContext.getBean(sendService, SendService.class);
		
		FaxReaderOutSDO out = new FaxReaderOutSDO();
		out = (FaxReaderOutSDO) sendService.callFaxReader(reservNum);
		
		return out;
		
	}
	
}
