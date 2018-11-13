<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="javax.servlet.ServletInputStream,javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpUtils"%>
<%@page import="java.io.BufferedReader,java.io.InputStreamReader,java.io.ByteArrayOutputStream"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.net.URL,java.net.HttpURLConnection"%>
<%@page import="net.sf.json.JSONException"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONSerializer"%>
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
		JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON( sbf.toString() );
		
		System.out.println("request jsonObject : \n" + jsonObject.toString(3));		
	}

} catch (Exception e) {
	throw new RuntimeException("JSON Telegram Exception", e);
}	
%>
{
	"menuList" : [
		{
			 "menuCd": "NB001"
			,"menuCdPrnt": "ROOT"
			,"menuNm": "시스템 관리"
			,"menuDesc": "UBMS 시스템 관리그룹"
			,"menuIcon": "./ux/common/image/ico/ico_01.png"
			,"cdMenuType": "F" 
			,"cdEvntType": "F" 
			,"sortOrdr": 1 
			,"moduUrl": "" 
			,"moduParm": "" 
			,"level": 1
		}
		,{
			 "menuCd": "NB002"
			,"menuCdPrnt": "NB001"
			,"menuNm": "시스템 운영환경"
			,"menuDesc": "UBMS 시스템 운영환경 관리그룹"
			,"menuIcon": "./ux/common/image/ico/ico_01.png"
			,"cdMenuType": "F" 
			,"cdEvntType": "F" 
			,"sortOrdr": 1 
			,"moduUrl": "" 
			,"moduParm": "" 
			,"level": 2
		}
		,{
			 "menuCd": "NB003"
			,"menuCdPrnt": "NB002"
			,"menuNm": "시스템 환경 목록"
			,"menuDesc": "UBMS 시스템 환경 목록"
			,"menuIcon": "./ux/common/image/ico/ico_01.png"
			,"cdMenuType": "G" 
			,"cdEvntType": "G" 
			,"sortOrdr": 1 
			,"moduUrl": "./ux/view/temp/content01.html" 
			,"moduParm": "" 
			,"level": 3
		}
		,{
			 "menuCd": "NB004"
			,"menuCdPrnt": "NB002"
			,"menuNm": "시스템 환경 관리"
			,"menuDesc": "UBMS 시스템 환경 관리"
			,"menuIcon": "./ux/common/image/ico/ico_02.png"
			,"cdMenuType": "G" 
			,"cdEvntType": "G" 
			,"sortOrdr": 2 
			,"moduUrl": "./ux/view/temp/content02.html" 
			,"moduParm": "" 
			,"level": 3
		}
		,{
			 "menuCd": "NB005"
			,"menuCdPrnt": "NB002"
			,"menuNm": "시스템 APP 파일 목록"
			,"menuDesc": "UBMS 시스템 APP 파일 목록"
			,"menuIcon": "./ux/common/image/ico/ico_03.png"
			,"cdMenuType": "G" 
			,"cdEvntType": "G" 
			,"sortOrdr": 3 
			,"moduUrl": "./ux/view/temp/content03.html" 
			,"moduParm": "" 
			,"level": 3
		}
		,{
			 "menuCd": "NB006"
			,"menuCdPrnt": "NB001"
			,"menuNm": "시스템 JVM"
			,"menuDesc": "시스템 JVM 관리그룹"
			,"menuIcon": "./ux/common/image/ico/ico_01.png"
			,"cdMenuType": "F" 
			,"cdEvntType": "F" 
			,"sortOrdr": 2 
			,"moduUrl": "" 
			,"moduParm": "" 
			,"level": 2
		}
		,{
			 "menuCd": "NB007"
			,"menuCdPrnt": "NB006"
			,"menuNm": "시스템 JVM CLASS 조회"
			,"menuDesc": "UBMS 시스템 JVM CLASS 조회"
			,"menuIcon": "./ux/common/image/ico/ico_04.png"
			,"cdMenuType": "G" 
			,"cdEvntType": "G" 
			,"sortOrdr": 1 
			,"moduUrl": "./ux/view/temp/content04.html" 
			,"moduParm": "" 
			,"level": 3
		}
		,{
			 "menuCd": "NB008"
			,"menuCdPrnt": "NB001"
			,"menuNm": "시스템 코드"
			,"menuDesc": "시스템 코드 관리그룹"
			,"menuIcon": "./ux/common/image/ico/ico_01.png"
			,"cdMenuType": "F" 
			,"cdEvntType": "F" 
			,"sortOrdr": 3 
			,"moduUrl": "" 
			,"moduParm": ""
			,"level": 2
		}
		,{
			 "menuCd": "NB009"
			,"menuCdPrnt": "NB008"
			,"menuNm": "시스템 코드 목록"
			,"menuDesc": "UBMS 시스템 코드 목록"
			,"menuIcon": "./ux/common/image/ico/ico_05.png"
			,"cdMenuType": "G" 
			,"cdEvntType": "G" 
			,"sortOrdr": 1 
			,"moduUrl": "./ux/view/temp/content05.html" 
			,"moduParm": ""
			,"level": 3
		}
		,{
			 "menuCd": "NB010"		
			,"menuCdPrnt": "NB008"	
			,"menuNm": "시스템 코드 관리"	
			,"menuDesc": "UBMS 시스템 코드 관리"	
			,"menuIcon": "./ux/common/image/ico/ico_01.png"	
			,"cdMenuType": "G" 
			,"cdEvntType": "G" 
			,"sortOrdr": 2 
			,"moduUrl": "./ux/view/temp/content01.html" 
			,"moduParm": "" 
			,"level": 3
		}
	]
}