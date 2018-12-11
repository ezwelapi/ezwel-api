package com.ezwel.htl.interfaces.server.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.server.commons.morpheme.ko.KoreanAnalyzer;
import com.ezwel.htl.interfaces.server.commons.sdo.MorphemeSDO;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;

@Controller
public class UtilityController {
	
	private static final Logger logger = LoggerFactory.getLogger(UtilityController.class);
	
	private APIUtil apiUtil;
	
	@APIOperation(description="테스트 JSP Forward Operation")
	@RequestMapping(value="/test/{fileName}")
	public String forward(@PathVariable("fileName") String fileName, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("[FORWARD] {}", fileName);
		return "/test/".concat(fileName);
	}

	
	@APIOperation(description="에이젼트 키 발급", isOutputJsonMarshall=true, returnType=Map.class)
	@RequestMapping(value="/agent/apiKey")
	public Object agentApiKey(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("[START] agentApiKey \n{}", request.getParameterMap());
		
		apiUtil = (APIUtil) LApplicationContext.getBean(apiUtil, APIUtil.class);
		
		Map<String, String[]> requestMap = request.getParameterMap();
		
		String agentName = APIUtil.NVL(requestMap.get("agentName")[0]);
		String httpAgentId = APIUtil.NVL(requestMap.get("httpAgentId")[0]);
		
		Map<String, String> out = new LinkedHashMap<String, String>();
		
		if(!agentName.isEmpty() && !httpAgentId.isEmpty()) {
			// inside api key
			out.put("inside", apiUtil.createApiKey("i", agentName, httpAgentId));
			logger.debug("{}({}) : {}", out.get("inside").length(), out);
			
			// outside api key
			out.put("outside", apiUtil.createApiKey("o", agentName, httpAgentId));
			logger.debug("{}({}) : {}", agentName, out.get("outside").length(), out);
		}
		logger.debug("[END] agentApiKey \n{}", out);
		return out;
	}
	
	
	@APIOperation(description="한글 형태소 분석", isOutputJsonMarshall=true, returnType=MorphemeSDO.class)
	@RequestMapping(value="/morp/korean")
	public Object morpKorean(MorphemeSDO morphemeSDO, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("[START] morpKorean {}", morphemeSDO);
		
		MorphemeSDO out = new MorphemeSDO();
		
		KoreanAnalyzer korean = new KoreanAnalyzer();
		logger.debug("korean : {}", korean);
		korean.setQueryMode(false);
		StringBuilder actual = null;
		TokenStream ts = null;
		CharTermAttribute termAtt = null;
		List<String> morphemeList = new ArrayList<String>();
		for(String input : morphemeSDO.getSentenceList()) {
		
			actual = new StringBuilder();
			try {
				
				ts = korean.tokenStream("bogus", input);
			    termAtt = ts.addAttribute(CharTermAttribute.class);
			    ts.reset();
			    
			    while (ts.incrementToken()) {
			      actual.append(termAtt.toString());
			      actual.append(", ");
			    }
			    
			    logger.debug("{}", actual);
			    morphemeList.add(actual.toString());
			    
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				if(ts != null) {
				    try {
						ts.end();
						ts.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}		
		
		logger.debug("[END] morpKorean ");
		return out;
	}	
}
