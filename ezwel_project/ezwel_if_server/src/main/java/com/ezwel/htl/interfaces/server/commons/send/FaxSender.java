package com.ezwel.htl.interfaces.server.commons.send;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ezwel.htl.interfaces.commons.http.HttpInterfaceExecutor;
import com.ezwel.htl.interfaces.server.commons.intercepter.HandlerInterceptor;

public class FaxSender {
	
	private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);
	
	@Autowired /** interface_if는 프론트 및 관리자단에서 ezwel 프레임워크 표준인  Autowired를 사용한다. (interface_if_server는 Autowired보다 빠른 스프링 컨텍스트의 getBean을 사용함) */
	private HttpInterfaceExecutor inteface;
	
	public FaxSender() {		
		if(inteface == null) {
			inteface = new HttpInterfaceExecutor();
		}
	}
	
}
