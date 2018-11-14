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
	"input": {
		"rsvNo": "123456789",
		"startDate": "",
		"endDate": "",
		"dateType": ""
	},

	"output": {
		"code": "1000",
		"message": "정상적으로 처리되었습니다",
		"data" : [
			{
				"rsvNo": "123456789",
				"rsvDatetime": "20181113152332",
				"rsvPrice": 200000,
				"rsvStat": "r02",
				"rsvPdtName": "",
				"rsvPdtNo": "",
				"pdtNo": "1",
				"pdtName": "서울 프라자 호텔",
				"roomNo": "1",
				"roomName": "디럭스",
				"roomCnt": 1,
				"checkInDate": "20181201",
				"checkOutDate": "20181202",
				"otaRsvNo": "000000123",
				"voucherNo": "1234",
				"memKey": "EZ0001",
				"memName": "홍길동",
				"memPhone": "01012341234",
				"memEmail": "test@test.com",
				"userName": "홍길동",
				"userMobile": "01012341234",
				"userEmail": "test@test.com",
				"userCmt": "전망 좋은 방으로 요청 드립니다",
				"adultCnt": 2,
				"childCnt": 1,
				"options" : [
					{
						"rsvOptNo": "",
						"optNo": "1",
						"optName": "조식",
						"optPrice": 20000,
						"optCountMax": 2
					}
				]
			}
		]
	}
}