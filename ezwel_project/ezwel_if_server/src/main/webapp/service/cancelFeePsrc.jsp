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
	"data" : {
		"cancelFeeText": "18시 이후 취소규정은 익일 취소규정으로 적용 되어집니다.",
		"penalty": [
			{
				"applyBgnDate": "20181128",
				"cancelFeeRate": 0,
				"cancelFeePrice": 0
			},
			{
				"applyBgnDate": "20181129",
				"cancelFeeRate": 20,
				"cancelFeePrice": 40000
			},
			{
				"applyBgnDate": "20181130",
				"cancelFeeRate": 50,
				"cancelFeePrice": 100000
			},
			{
				"applyBgnDate": "20181201",
				"cancelFeeRate": 100,
				"cancelFeePrice": 200000
			}
		]
	}
}