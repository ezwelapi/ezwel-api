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
	"code": "1000",
	"message": "정상적으로 처리되었습니다",
	"data" : [
		{
			"pdtName": "서울 프라자 호텔",
			"pdtNameEng": "The Plaza",
			"pdtNo": "1",
			"tripadvisorId": "",
			"mainImage": "http://tourimage.interpark.com/Product/Hotel/Thumbnail/00161/20180117/1001565129/1001565129_01_b288x208.jpg",
			"changeImage": "N",
			"subImages" : [
				{
					"image": "http://tourimage.interpark.com/Product/Hotel/CAVAEAAEAK/500/1001565129_2_54210.jpg",
					"desc": "로비"
				},
				{
					"image": "http://tourimage.interpark.com/Product/Hotel/CAVAEAAEAK/500/1001565129_4_18803.jpg",
					"desc": "객실"
				}
			],
			"descHTML": "아름답게 설계된 더 플라자 서울, 오토그래프 컬렉션은 서울 중심부에 위치한 대표적인 부티크 호텔로 무료 WiFi와 실내 수영장을 자랑합니다. 도보로 2분이 소요되는 시청역을 통해 도심으로 쉽게 이동하실 수 있습니다.더 플라자 서울, 오토그래프 컬렉션의 객실은 현대적인 디자인 요소와 부드러운 색감으로 멋지게 꾸며져 있으며 에어컨, 자동 커튼과 대형 평면 TV를 갖추고 있습니다. 각 객실에는 차/커피와 실내 슬리퍼도 구비되어 있습니다.각종 시설이 설치된 피트니스 센터에서 운동을 하거나 스파에서 편안한 마사지를 받으며 피로를 풀어보십시오. 또한, 호텔에는 무료 주차장, 비즈니스 센터 및 세탁 서비스가 마련되어 있습니다.도원 레스토랑은 건강한 오일 프리 중국 요리를 선보이며, 세븐 스퀘어에서는 지중해풍 요리로 구성된 뷔페를 즐기실 수 있습니다. 또한, 호텔에는 일본 별미를 맛보실 수 있는 무라사키, 이탈리아 요리를 제공하는 투스카니, 갓 구운 신선한 빵이 가득한 에릭 케제르 등 여러 레스토랑도 자리해 있습니다.더 플라자 서울, 오토그래프 컬렉션에서 인천국제공항은 택시로 약 1시간이 소요되며 쇼핑가인 남대문과 명동은 1km 이내의 거리에 있습니다.",
			"descMobile": "",
			"sellStartDate": "20181113",
			"sellEndDate": "20190228",
			"sellPrice": "121000",
			"sido": "서울",
			"sidoCode": "11",
			"gungu": "중구",
			"gunguCode": "11020",
			"address": "서울 중구 소공로 119",
			"addressType": "B",
			"zipCode": "04525",
			"telephone": "027712200",
			"checkInTime": "14:00",
			"checkOutTime": "11:00",
			"typeCode": "t01",
			"gradeCode": "g01",
			"mapX": "",
			"mapY": "",
			"serviceCodes": ""
		}
	]
}