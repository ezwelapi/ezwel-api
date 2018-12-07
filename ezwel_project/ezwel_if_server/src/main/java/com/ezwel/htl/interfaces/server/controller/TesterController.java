package com.ezwel.htl.interfaces.server.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;

@Controller
public class TesterController {
	
	private static final Logger logger = LoggerFactory.getLogger(TesterController.class);
	
	@APIOperation(description="테스트 JSP Forward Operation")
	@RequestMapping(value="/test/{fileName}")
	public String forward(@PathVariable("fileName") String fileName, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("[FORWARD] {}", fileName);
		return "/test/".concat(fileName);
	}
	
}
