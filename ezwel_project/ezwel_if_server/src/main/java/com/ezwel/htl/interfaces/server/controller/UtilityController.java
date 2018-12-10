package com.ezwel.htl.interfaces.server.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
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
	public Object agentApiKey(Model model, HttpServletRequest request, HttpServletResponse response) {
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
	
}
