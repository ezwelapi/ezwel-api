<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="javax.servlet.ServletInputStream,javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpUtils"%>
<%@page import="java.io.BufferedReader,java.io.InputStreamReader,java.io.ByteArrayOutputStream"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.net.URL,java.net.HttpURLConnection"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%
try {
	System.out.println("■■■■■■■■■ [Response JSP Reqeust Header] ■■■■■■■■■");
	Enumeration<?> headerNames = request.getHeaderNames();
	String paramName = null;
	while (headerNames.hasMoreElements()) {
		paramName = (String) headerNames.nextElement();
		System.out.println(paramName + " : " + request.getHeader(paramName));
	} 

	System.out.println("■■■■■■■■■ [Response JSP Input Stream Data (JSON)] ■■■■■■■■■");
	
	BufferedReader input = new BufferedReader(new InputStreamReader(request.getInputStream()));
	StringBuffer sbf = new StringBuffer();
	String buffer;
	while ((buffer = input.readLine()) != null) {
		if (sbf.length() > 0) {
			sbf.append("\n");
		}
		sbf.append(buffer);
	}
	System.out.println("requestOrgin : '" + sbf.toString() + "'");
	
	if( !sbf.toString().isEmpty() ) {
		/** 시작 : 변경 부분 */
		ObjectMapper mapper = new ObjectMapper();
		String inputJson = mapper.writeValueAsString(sbf.toString());
		
		System.out.println("request jsonObject : \n" + inputJson);
		/** 종료 : 변경 부분 */
	}

} catch (Exception e) {
	throw new RuntimeException("JSON Telegram Exception", e);
}	
%>

{
	"code": "1000",
	"message": "정상적으로 처리되었습니다",
	"reserves" : [
		{
			"rsvNo": "123456789",
			"rsvPdtNo": "",
			"rsvPrice": 200000,
			"pdtNo": "1",
			"otaRsvNo": "000000123",
			"rsvStat": "o01"
		}
	]
}