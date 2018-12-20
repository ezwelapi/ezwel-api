package com.ezwel.htl.interfaces.server.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.RegexUtil;
import com.ezwel.htl.interfaces.server.commons.morpheme.cm.MorphemeUtil;
import com.ezwel.htl.interfaces.server.commons.morpheme.en.EnglishAnalyzers;
import com.ezwel.htl.interfaces.server.commons.morpheme.ko.KoreanAnalyzers;
import com.ezwel.htl.interfaces.server.commons.sdo.AgentApiKeySDO;
import com.ezwel.htl.interfaces.server.commons.sdo.MorphemeSDO;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.commons.utils.CommonUtil;

@Controller
public class UtilityController {
	
	private static final Logger logger = LoggerFactory.getLogger(UtilityController.class);
	
	private APIUtil apiUtil;
	private CommonUtil commonUtil;
	private KoreanAnalyzers koreanAnalyzer;
	private EnglishAnalyzers englishAnalayzer;
	private RegexUtil regexUtil;
	
	@APIOperation(description="테스트 JSP Forward Operation")
	@RequestMapping(value="/test/{fileName}")
	public String forward(@PathVariable("fileName") String fileName, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("[FORWARD] {}", fileName);
		return "/test/".concat(fileName);
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
	
	
	@APIOperation(description="한글 형태소 분석", isOutputJsonMarshall=true, returnType=MorphemeSDO.class)
	@RequestMapping(value="/morp/korean")
	public Object morpKorean(MorphemeSDO morphemeSDO) {
		logger.debug("[START] morpKorean {}", morphemeSDO);

		MorphemeSDO out = new MorphemeSDO();
		
		koreanAnalyzer = (KoreanAnalyzers) LApplicationContext.getBean(koreanAnalyzer, KoreanAnalyzers.class);
		englishAnalayzer = (EnglishAnalyzers) LApplicationContext.getBean(englishAnalayzer, EnglishAnalyzers.class);
		regexUtil = (RegexUtil) LApplicationContext.getBeanInstance(regexUtil, RegexUtil.class);
		apiUtil = (APIUtil) LApplicationContext.getBeanInstance(apiUtil, APIUtil.class);
		
		List<List<String>> korMorphemeList = new ArrayList<List<String>>();
		for(String input : morphemeSDO.getSentenceList()) {
			if(input == null || input.isEmpty()) {
				continue;
			}
			
			if(regexUtil.testPattern(input, MorphemeUtil.PATTERN_KOREAN_SENTENCE)) {
				input = apiUtil.toHalfChar(input).replaceAll("(?i)".concat("\\(([	 주]+)\\)|").concat(MorphemeUtil.PATTERN_ENGLISH_SENTENCE), OperateConstants.STR_BLANK).trim();
				korMorphemeList.add(new ArrayList<String>(koreanAnalyzer.getKoreanMorphologicalAnalysis(input)));
			}
		}		
		out.setKorMorphemeList(korMorphemeList);
		
		List<List<String>> engMorphemeList = new ArrayList<List<String>>();
		for(String input : morphemeSDO.getSentenceList()) {
			if(input == null || input.isEmpty()) {
				continue;
			}
			
			if(regexUtil.testPattern(input, MorphemeUtil.PATTERN_ENGLISH_SENTENCE)) {
				input = apiUtil.toHalfChar(input).replaceAll("(?i)".concat(MorphemeUtil.PATTERN_KOREAN_SENTENCE), OperateConstants.STR_BLANK).trim();
				engMorphemeList.add(new ArrayList<String>(englishAnalayzer.getEnglishMorphologicalAnalysis(input)));
			}
		}		
		out.setEngMorphemeList(engMorphemeList);
		
		logger.debug("[END] morpKorean ");
		return out;
	}	
}
