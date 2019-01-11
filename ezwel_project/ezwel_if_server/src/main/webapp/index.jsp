<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ezwel api test</title>
<style>
@import url(https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css);
@import url(https://fonts.googleapis.com/css?family=Lato:300,400,700);

body {
	margin: 0px;
	padding: 0px 14px 0px 10px;
	font-family: 'Lato', sans-serif;
}

h2, span {
	color: #ffffff
}

ul {
	list-style: none;
	margin: 0;
	padding: 0;
}

li {
	margin: 0 0 0 0;
	padding: 0 0 0 0;
	border: 0;
	float: left;
}

.jumbotron h1 {
	color: #353535;
}

footer {
	margin-bottom: 0 !important;
	margin-top: 80px;
}

footer p {
	margin: 0;
	padding: 0;
}

span.icon {
	margin: 0 5px;
	color: #D64541;
}

h2 {
	color: #BDC3C7;
	text-transform: uppercase;
	letter-spacing: 1px;
}

.mrng-60-top {
	margin-top: 60px;
}
/* Global Button Styles */
a.animated-button:link, a.animated-button:visited {
	position: relative;
	display: block;
	margin: 30px auto 0;
	padding: 14px 15px;
	color: #fff;
	font-size: 14px;
	font-weight: bold;
	text-align: center;
	text-decoration: none;
	text-transform: uppercase;
	overflow: hidden;
	letter-spacing: .08em;
	border-radius: 0;
	text-shadow: 0 0 1px rgba(0, 0, 0, 0.2), 0 1px 0 rgba(0, 0, 0, 0.2);
	-webkit-transition: all 1s ease;
	-moz-transition: all 1s ease;
	-o-transition: all 1s ease;
	transition: all 1s ease;
}

a.animated-button:link:after, a.animated-button:visited:after {
	content: "";
	position: absolute;
	height: 0%;
	left: 50%;
	top: 50%;
	width: 150%;
	z-index: -1;
	-webkit-transition: all 0.75s ease 0s;
	-moz-transition: all 0.75s ease 0s;
	-o-transition: all 0.75s ease 0s;
	transition: all 0.75s ease 0s;
}

a.animated-button:link:hover, a.animated-button:visited:hover {
	color: #FFF;
	text-shadow: none;
}

a.animated-button:link:hover:after, a.animated-button:visited:hover:after
	{
	height: 450%;
}

a.animated-button:link, a.animated-button:visited {
	position: relative;
	display: block;
	margin: 30px auto 0;
	padding: 14px 15px;
	color: #fff;
	font-size: 14px;
	border-radius: 0;
	font-weight: bold;
	text-align: center;
	text-decoration: none;
	text-transform: uppercase;
	overflow: hidden;
	letter-spacing: .08em;
	text-shadow: 0 0 1px rgba(0, 0, 0, 0.2), 0 1px 0 rgba(0, 0, 0, 0.2);
	-webkit-transition: all 1s ease;
	-moz-transition: all 1s ease;
	-o-transition: all 1s ease;
	transition: all 1s ease;
}

/* Victoria Buttons */
a.animated-button.victoria-one {
	border: 2px solid #D24D57;
}

a.animated-button.victoria-one:after {
	background: #D24D57;
	-moz-transform: translateX(-50%) translateY(-50%) rotate(-25deg);
	-ms-transform: translateX(-50%) translateY(-50%) rotate(-25deg);
	-webkit-transform: translateX(-50%) translateY(-50%) rotate(-25deg);
	transform: translateX(-50%) translateY(-50%) rotate(-25deg);
}

a.animated-button.victoria-two {
	border: 2px solid #D24D57;
}

a.animated-button.victoria-two:after {
	background: #D24D57;
	-moz-transform: translateX(-50%) translateY(-50%) rotate(25deg);
	-ms-transform: translateX(-50%) translateY(-50%) rotate(25deg);
	-webkit-transform: translateX(-50%) translateY(-50%) rotate(25deg);
	transform: translateX(-50%) translateY(-50%) rotate(25deg);
}

a.animated-button.victoria-three {
	border: 2px solid #D24D57;
}

a.animated-button.victoria-three:after {
	background: #D24D57;
	opacity: .5;
	-moz-transform: translateX(-50%) translateY(-50%);
	-ms-transform: translateX(-50%) translateY(-50%);
	-webkit-transform: translateX(-50%) translateY(-50%);
	transform: translateX(-50%) translateY(-50%);
}

a.animated-button.victoria-three:hover:after {
	height: 140%;
	opacity: 1;
}

a.animated-button.victoria-four {
	border: 2px solid #D24D57;
}

a.animated-button.victoria-four:after {
	background: #D24D57;
	opacity: .5;
	-moz-transform: translateY(-50%) translateX(-50%) rotate(90deg);
	-ms-transform: translateY(-50%) translateX(-50%) rotate(90deg);
	-webkit-transform: translateY(-50%) translateX(-50%) rotate(90deg);
	transform: translateY(-50%) translateX(-50%) rotate(90deg);
}

a.animated-button.victoria-four:hover:after {
	opacity: 1;
	height: 600% !important;
}
/* Sandy Buttons */
a.animated-button.sandy-one {
	border: 2px solid #AEA8D3;
	color: #FFF;
}

a.animated-button.sandy-one:after {
	border: 3px solid #AEA8D3;
	opacity: 0;
	-moz-transform: translateX(-50%) translateY(-50%);
	-ms-transform: translateX(-50%) translateY(-50%);
	-webkit-transform: translateX(-50%) translateY(-50%);
	transform: translateX(-50%) translateY(-50%);
}

a.animated-button.sandy-one:hover:after {
	height: 120% !important;
	opacity: 1;
	color: #FFF;
}

a.animated-button.sandy-two {
	border: 2px solid #AEA8D3;
	color: #FFF;
}

a.animated-button.sandy-two:after {
	border: 3px solid #AEA8D3;
	opacity: 0;
	-moz-transform: translateY(-50%) translateX(-50%) rotate(90deg);
	-ms-transform: translateY(-50%) translateX(-50%) rotate(90deg);
	-webkit-transform: translateY(-50%) translateX(-50%) rotate(90deg);
	transform: translateY(-50%) translateX(-50%) rotate(90deg);
}

a.animated-button.sandy-two:hover:after {
	height: 600% !important;
	opacity: 1;
	color: #FFF;
}

a.animated-button.sandy-three {
	border: 2px solid #AEA8D3;
	color: #FFF;
}

a.animated-button.sandy-three:after {
	border: 3px solid #AEA8D3;
	opacity: 0;
	-moz-transform: translateX(-50%) translateY(-50%) rotate(-25deg);
	-ms-transform: translateX(-50%) translateY(-50%) rotate(-25deg);
	-webkit-transform: translateX(-50%) translateY(-50%) rotate(-25deg);
	transform: translateX(-50%) translateY(-50%) rotate(-25deg);
}

a.animated-button.sandy-three:hover:after {
	height: 400% !important;
	opacity: 1;
	color: #FFF;
}

a.animated-button.sandy-four {
	border: 2px solid #AEA8D3;
	color: #FFF;
}

a.animated-button.sandy-four:after {
	border: 3px solid #AEA8D3;
	opacity: 0;
	-moz-transform: translateY(-50%) translateX(-50%) rotate(25deg);
	-ms-transform: translateY(-50%) translateX(-50%) rotate(25deg);
	-webkit-transform: translateY(-50%) translateX(-50%) rotate(25deg);
	transform: translateY(-50%) translateX(-50%) rotate(25deg);
}

a.animated-button.sandy-four:hover:after {
	height: 400% !important;
	opacity: 1;
	color: #FFF;
}
/* Gibson Buttons */
a.animated-button.gibson-one {
	border: 2px solid #65b37a;
	color: #FFF;
}

a.animated-button.gibson-one:after {
	opacity: 0;
	background-image: -webkit-linear-gradient(transparent 50%, rgba(101, 179, 122, 0.2)
		50%);
	background-image: -moz-linear-gradient(transparent 50%, rgba(101, 179, 122, 0.2)
		50%);
	background-size: 10px 10px;
	-moz-transform: translateX(-50%) translateY(-50%) rotate(25deg);
	-ms-transform: translateX(-50%) translateY(-50%) rotate(25deg);
	-webkit-transform: translateX(-50%) translateY(-50%) rotate(25deg);
	transform: translateX(-50%) translateY(-50%) rotate(25deg);
}

a.animated-button.gibson-one:hover:after {
	height: 600% !important;
	opacity: 1;
	color: #FFF;
}

a.animated-button.gibson-two {
	border: 2px solid #65b37a;
	color: #FFF;
}

a.animated-button.gibson-two:after {
	opacity: 0;
	background-image: -webkit-linear-gradient(transparent 50%, rgba(101, 179, 122, 0.2)
		50%);
	background-image: -moz-linear-gradient(transparent 50%, rgba(101, 179, 122, 0.2)
		50%);
	background-size: 10px 10px;
	-moz-transform: translateX(-50%) translateY(-50%) rotate(-25deg);
	-ms-transform: translateX(-50%) translateY(-50%) rotate(-25deg);
	-webkit-transform: translateX(-50%) translateY(-50%) rotate(-25deg);
	transform: translateX(-50%) translateY(-50%) rotate(-25deg);
}

a.animated-button.gibson-two:hover:after {
	height: 600% !important;
	opacity: 1;
	color: #FFF;
}

a.animated-button.gibson-three {
	border: 2px solid #65b37a;
	color: #FFF;
}

a.animated-button.gibson-three:after {
	opacity: 0;
	background-image: -webkit-linear-gradient(transparent 50%, rgba(101, 179, 122, 0.2)
		50%);
	background-image: -moz-linear-gradient(transparent 50%, rgba(101, 179, 122, 0.2)
		50%);
	background-size: 10px 10px;
	-moz-transform: translateX(-50%) translateY(-50%) rotate(90deg);
	-ms-transform: translateX(-50%) translateY(-50%) rotate(90deg);
	-webkit-transform: translateX(-50%) translateY(-50%) rotate(90deg);
	transform: translateX(-50%) translateY(-50%) rotate(90deg);
}

a.animated-button.gibson-three:hover:after {
	height: 600% !important;
	opacity: 1;
	color: #FFF;
}

a.animated-button.gibson-four {
	border: 2px solid #65b37a;
	color: #FFF;
}

a.animated-button.gibson-four:after {
	opacity: 0;
	background-image: -webkit-linear-gradient(transparent 50%, rgba(101, 179, 122, 0.2)
		50%);
	background-image: -moz-linear-gradient(transparent 50%, rgba(101, 179, 122, 0.2)
		50%);
	background-size: 10px 10px;
	-moz-transform: translateX(-50%) translateY(-50%);
	-ms-transform: translateX(-50%) translateY(-50%));
	-webkit-transform: translateX(-50%) translateY(-50%);
	transform: translateX(-50%) translateY(-50%);
}

a.animated-button.gibson-four:hover:after {
	height: 600% !important;
	opacity: 1;
	color: #FFF;
}
/* Thar Buttons */
a.animated-button.thar-one {
	color: #fff;
	cursor: pointer;
	display: block;
	position: relative;
	border: 2px solid #F7CA18;
	transition: all 0.4s cubic-bezier(0.215, 0.61, 0.355, 1) 0s;
}

a.animated-button.thar-one:hover {
	color: #000 !important;
	background-color: transparent;
	text-shadow: none;
}

a.animated-button.thar-one:hover:before {
	bottom: 0%;
	top: auto;
	height: 100%;
}

a.animated-button.thar-one:before {
	display: block;
	position: absolute;
	left: 0px;
	top: 0px;
	height: 0px;
	width: 100%;
	z-index: -1;
	content: '';
	color: #000 !important;
	background: #F7CA18;
	transition: all 0.4s cubic-bezier(0.215, 0.61, 0.355, 1) 0s;
}

a.animated-button.thar-two {
	color: #fff;
	cursor: pointer;
	display: block;
	position: relative;
	border: 2px solid #F7CA18;
	transition: all 0.4s cubic-bezier(0.215, 0.61, 0.355, 1) 0s;
}

a.animated-button.thar-two:hover {
	color: #000 !important;
	background-color: transparent;
	text-shadow: ntwo;
}

a.animated-button.thar-two:hover:before {
	top: 0%;
	bottom: auto;
	height: 100%;
}

a.animated-button.thar-two:before {
	display: block;
	position: absolute;
	left: 0px;
	bottom: 0px;
	height: 0px;
	width: 100%;
	z-index: -1;
	content: '';
	color: #000 !important;
	background: #F7CA18;
	transition: all 0.4s cubic-bezier(0.215, 0.61, 0.355, 1) 0s;
}

a.animated-button.thar-three {
	color: #fff;
	cursor: pointer;
	display: block;
	position: relative;
	border: 2px solid #F7CA18;
	transition: all 0.4s cubic-bezier(0.42, 0, 0.58, 1);
	0
	s;
}

a.animated-button.thar-three:hover {
	color: #000 !important;
	background-color: transparent;
	text-shadow: nthree;
}

a.animated-button.thar-three:hover:before {
	left: 0%;
	right: auto;
	width: 100%;
}

a.animated-button.thar-three:before {
	display: block;
	position: absolute;
	top: 0px;
	right: 0px;
	height: 100%;
	width: 0px;
	z-index: -1;
	content: '';
	color: #000 !important;
	background: #F7CA18;
	transition: all 0.4s cubic-bezier(0.42, 0, 0.58, 1);
	0
	s;
}

a.animated-button.thar-four {
	color: #fff;
	cursor: pointer;
	display: block;
	position: relative;
	border: 2px solid #F7CA18;
	transition: all 0.4s cubic-bezier(0.42, 0, 0.58, 1);
	0
	s;
}

a.animated-button.thar-four:hover {
	color: #000 !important;
	background-color: transparent;
	text-shadow: nfour;
}

a.animated-button.thar-four:hover:before {
	right: 0%;
	left: auto;
	width: 100%;
}

a.animated-button.thar-four:before {
	display: block;
	position: absolute;
	top: 0px;
	left: 0px;
	height: 100%;
	width: 0px;
	z-index: -1;
	content: '';
	color: #000 !important;
	background: #F7CA18;
	transition: all 0.4s cubic-bezier(0.42, 0, 0.58, 1);
	0
	s;
}

</style>

<link rel="stylesheet" id="common-css"  href="./resources/common/css/common.css" type="text/css" media="all" />

</head>
<body>

<script src="//code.jquery.com/jquery.js"></script>

<script type="text/javascript">

var $interfaceTester = {
	color : {
		// $interfaceTester.color.metro_palette
		metro_palette : {
			1:"black",
			2:"violet",/* "lime", */
			3:"green",
			4:"teal",
			5:"blue",
			6:"indigo",
			7:"violet",
			8:"pink",
			9:"magenta",
			10:"crimson",
			11:"red",
			12:"orange",
			13:"orange",/* "yellow", */
			14:"brown",
			15:"olive",
			16:"red",
			17:"gray",
			18:"orange",
			19:"darkMagenta",
			20:"brown",
			21:"darkGreen",
			22:"darkOrange",
			23:"darkRed",
			24:"darkBlue"
		},
		// $interfaceTester.color.metroColor
		metroColor: function(){
			var randomNumber = Math.ceil(Math.random() * Object.keys($interfaceTester.color.metro_palette).length ); // ceil 올림
			var color = $interfaceTester.color.metro_palette[ randomNumber ];
			console.debug("color : " + color);
			$("body").css("background-color", color);
		}		
	}
	,contextPath : "/API1.0" // $interfaceTester.contextPath
	,requestNamespance : "/service"   // $interfaceTester.requestNamespance
	,datas : {  // $interfaceTester.datas
		"IN-신규시설등록수정" : {
			 url : $interfaceTester.requestNamespance + "/facl/record"
			,input : {
				"dataUrl" : "http://ezc-api.dev.ezwel.com/API1.0/10000496/facl/record"
			}
		},
		"IN-시설판매중지설정" : {
			  url : $interfaceTester.requestNamespance + "/facl/saleStop"
			 ,input : {
				"pdtNo": "1",
				"sellFlag": "N"
			 }
		},
		"IN-예약내역조회" : {
			 url : $interfaceTester.requestNamespance + "/facl/view"
			,input : {
				"rsvNo": "123456789",
				"startDate": "",
				"endDate": "",
				"dateType": ""
			}
		},
		"IN-시설바우처번호등록" : {
			 url : $interfaceTester.requestNamespance + "/facl/voucherReg"
			,input : {
				"rsvNo": "123456789",
				"voucherNo": "1234"
			}
		},
		"IN-주문대사(제휴사)" : {
			 url : $interfaceTester.requestNamespance + "/facl/agentJob"
			,input : {
				"rsvNo": "123456789",
				"rsvDateStart": "20181201",
				"rsvDateEnd": "20181215"
			}
		},
		
		"OUT-전체시설일괄등록 인터페이스" : {
			 url : $interfaceTester.requestNamespance + "/allReg"
			,input : {
				"httpAgentGroupId": "multiAllReg",	/* PIX DATA */
				"httpAgentType": "{setClientUserData}",
				"httpChannelCd": "{setClientUserData}",
				"httpClientId": "{setClientUserData}",
				"httpRequestId": "{setClientUserData}",
				"connTimeout": 1000,	/* {setClientUserData} */
				"readTimeout": 100000	/* {setClientUserData} */
			}
		},
		"OUT-시설검색 인터페이스" : {
			 url : $interfaceTester.requestNamespance + "/callFaclSearch"
			,input : {
				userAgentSDO : {
					"httpAgentGroupId": "multiFaclSearch",	/* PIX DATA */
					"httpAgentType": "{setClientUserData}",
					"httpChannelCd": "{setClientUserData}",
					"httpClientId": "{setClientUserData}",
					"httpRequestId": "{setClientUserData}",
					"connTimeout": 1000,	/* {setClientUserData} */
					"readTimeout": 100000	/* {setClientUserData} */
				},
				faclSearchInSDO : {
					"checkInDate": "20190101",	/* {setClientUserData} */
					"checkOutDate": "20190102",	/* {setClientUserData} */
					"sidoCode": "11",	/* {setClientUserData} */
					"gunguCode": ""	/* {setClientUserData} */
				}
			}
		},
		"OUT-당일특가검색 인터페이스" : {
			 url : $interfaceTester.requestNamespance + "/callSddSearch"
			,input : {
				"httpAgentGroupId": "multiSddSearch",
				"httpAgentType": "{setClientUserData}",
				"httpChannelCd": "{setClientUserData}",
				"httpClientId": "{setClientUserData}",
				"httpRequestId": "{setClientUserData}",
				"connTimeout": 1000,	/* {setClientUserData} */
				"readTimeout": 100000	/* {setClientUserData} */,
			}
		},
		"OUT-객실정보조회 인터페이스" : {
			 url : $interfaceTester.requestNamespance + "/callRoomRead"
			,input : {
				userAgentSDO : {
					"httpAgentId": ":: 우측상단 콤보박스 선택값 자동 대입 ::",
					"httpAgentType": "{setClientUserData}",
					"httpChannelCd": "{setClientUserData}",
					"httpClientId": "{setClientUserData}",
					"httpRequestId": "{setClientUserData}",
					"connTimeout": 1000,	/* {setClientUserData} */
					"readTimeout": 100000	/* {setClientUserData} */
				},
				roomReadInSDO : {
					"pdtNo": "KRSEL340",	/* {setClientUserData} */
					"checkInDate": "20190101",	/* {setClientUserData} */
					"checkOutDate": "20190102",	/* {setClientUserData} */
					"roomCnt": 1,	/* {setClientUserData} */
					"adultCnt": 2,	/* {setClientUserData} */
					"childCnt": 0	/* {setClientUserData} */
				}
			}
		},
		"OUT-시설 별 최저가목록조회 인터페이스" : {
			 url : $interfaceTester.requestNamespance + "/callRoomRead"
			,input : {
				userAgentSDO : {
					"httpAgentType": "{setClientUserData}",
					"httpChannelCd": "{setClientUserData}",
					"httpClientId": "{setClientUserData}",
					"httpRequestId": "{setClientUserData}",
					"connTimeout": 1000,	/* {setClientUserData} */
					"readTimeout": 100000	/* {setClientUserData} */
				},
				roomReadInSDO : {
					"grpFaclCd": "64512",
					"checkInDate": "20190101",	/* {setClientUserData} */
					"checkOutDate": "20190102",	/* {setClientUserData} */
					"roomCnt": 1,	/* {setClientUserData} */
					"adultCnt": 2,	/* {setClientUserData} */
					"childCnt": 0	/* {setClientUserData} */
				}
			}
		},		
		"OUT-취소수수료규정 인터페이스" : {
			 url : $interfaceTester.requestNamespance + "/callCancelFeePsrc"
			,input : {
				userAgentSDO : {
					"httpAgentId": ":: 우측상단 콤보박스 선택값 자동 대입 ::",
					"httpAgentType": "{setClientUserData}",
					"httpChannelCd": "{setClientUserData}",
					"httpClientId": "{setClientUserData}",
					"httpRequestId": "{setClientUserData}",
					"connTimeout": 1000,	/* {setClientUserData} */
					"readTimeout": 100000	/* {setClientUserData} */
				},
				cancelFeePsrcInSDO : {
					"pdtNo": "KRSEL402",	/* {setClientUserData} */
					"roomNo": "ZyM^JZUuHL^IGl8A4YyHevULP9zS8K2ev1C8NgVQnc3lQabQXTwtxa52D2tOFZB2tN7ujSDtk3otQJI/mC1K8WBmiQtdoKdpQmCmqKh8no3FwuqqmGtyuccVc6rLbyAndR69WtV9Q7yJANWNZvF^pDUf/WJPWCOUtlau0aEKEWKybat7rYTTc4ZK8Lr2bmY99lX5X^ECCR7G3B5mSVU639vOOl3l^EG2qfYwiFx1hRw=",	/* {setClientUserData} */
					"checkInDate": "20190101",	/* {setClientUserData} */
					"checkOutDate": "20190102",	/* {setClientUserData} */
					"roomCnt": 1	/* {setClientUserData} */
				}
			}
		},
		"OUT-결재완료내역전송 인터페이스" : {
			 url : $interfaceTester.requestNamespance + "/callRsvHistSend"
			,input : {
				userAgentSDO : {
					"httpAgentId": ":: 우측상단 콤보박스 선택값 자동 대입 ::",
					"httpAgentType": "{setClientUserData}",
					"httpChannelCd": "{setClientUserData}",
					"httpClientId": "{setClientUserData}",
					"httpRequestId": "{setClientUserData}",
					"connTimeout": 1000,	/* {setClientUserData} */
					"readTimeout": 100000	/* {setClientUserData} */
				},
				rsvHistSendInSDO : {
					data : {
						"rsvNo": "E000000003",	/* {setClientUserData} */
						"rsvDatetime": "20181220153034",	/* {setClientUserData} */
						"rsvPrice": 56000,	/* {setClientUserData} */
						"rsvStat": "r02",	/* {setClientUserData} */
						"rsvPdtName": "더 리센츠 동대문 호텔",	/* {setClientUserData} */
						"rsvPdtNo": "P000000003",	/* {setClientUserData} */
						"pdtNo": "KRSEL402",	/* {setClientUserData} */
						"pdtName": "더 리센츠 동대문 호텔",	/* {setClientUserData} */
						"roomNo": "ZyM^JZUuHL^IGl8A4YyHevULP9zS8K2ev1C8NgVQnc3lQabQXTwtxa52D2tOFZB2tN7ujSDtk3otQJI/mC1K8WBmiQtdoKdpQmCmqKh8no3FwuqqmGtyuccVc6rLbyAndR69WtV9Q7yJANWNZvF^pDUf/WJPWCOUtlau0aEKEWKybat7rYTTc4ZK8Lr2bmY99lX5X^ECCR7G3B5mSVU639vOOl3l^EG2qfYwiFx1hRw=",	/* {setClientUserData} */
						"roomName": "Standard Double Room (Latex Mattress)(1 double bed request)",	/* {setClientUserData} */
						"roomCnt": 1,	/* {setClientUserData} */
						"checkInDate": "20190101",	/* {setClientUserData} */
						"checkOutDate": "20190102",	/* {setClientUserData} */
						"memKey": "EZ0001",	/* {setClientUserData} */
						"memName": "홍길동",	/* {setClientUserData} */
						"memPhone": "01012341234",	/* {setClientUserData} */
						"memEmail": "test@test.com",	/* {setClientUserData} */
						"userName": "홍길동",	/* {setClientUserData} */
						"userMobile": "01012341234",	/* {setClientUserData} */
						"userEmail": "test@test.com",	/* {setClientUserData} */
						"userCmt": "전망 좋은 방으로 요청 드립니다",	/* {setClientUserData} */
						"adultCnt": 2,	/* {setClientUserData} */
						"childCnt": 0	/* {setClientUserData} */
					}
				}
			}
		},
		"OUT-취소수수료계산 인터페이스" : {
			 url : $interfaceTester.requestNamespance + "/callCancelFeeAmt"
			,input : {
				userAgentSDO : {
					"httpAgentId": ":: 우측상단 콤보박스 선택값 자동 대입 ::",
					"httpAgentType": "{setClientUserData}",
					"httpChannelCd": "{setClientUserData}",
					"httpClientId": "{setClientUserData}",
					"httpRequestId": "{setClientUserData}",
					"connTimeout": 1000,	/* {setClientUserData} */
					"readTimeout": 100000	/* {setClientUserData} */
				},
				cancelFeeAmtInSDO : {
					"rsvNo": "E000000003"
				}
			}
		},
		"OUT-주문취소요청 인터페이스" : {
			 url : $interfaceTester.requestNamespance + "/callOrderCancelReq"
			,input : {
				userAgentSDO : {
					"httpAgentId": ":: 우측상단 콤보박스 선택값 자동 대입 ::",
					"httpAgentType": "{setClientUserData}",
					"httpChannelCd": "{setClientUserData}",
					"httpClientId": "{setClientUserData}",
					"httpRequestId": "{setClientUserData}",
					"connTimeout": 1000,	/* {setClientUserData} */
					"readTimeout": 100000	/* {setClientUserData} */
				},
				orderCancelReqInSDO : {
					"rsvNo": "E000000003",	/* {setClientUserData} */
					"otaRsvNo": "1812200852-1",	/* {setClientUserData} */
					"rsvPrice": 0,	/* {setClientUserData} */
					"cancelCharge": 0	/* {setClientUserData} */
				}
			}
		},
		"OUT-누락건확인 인터페이스" : {
			 url : $interfaceTester.requestNamespance + "/callOmiNumIdn"
			,input : {
				userAgentSDO : {
					"httpAgentId": ":: 우측상단 콤보박스 선택값 자동 대입 ::",
					"httpAgentType": "{setClientUserData}",
					"httpChannelCd": "{setClientUserData}",
					"httpClientId": "{setClientUserData}",
					"httpRequestId": "{setClientUserData}",
					"connTimeout": 1000,	/* {setClientUserData} */
					"readTimeout": 100000	/* {setClientUserData} */
				},
				omiNumIdnInSDO : {				
					"rsvNo": "E000000003",	/* {setClientUserData} */
					"rsvStat": "r02"	/* {setClientUserData} */
				}
			}
		},
		"OUT-주문대사(이지웰) 인터페이스" : {
			 url : $interfaceTester.requestNamespance + "/callEzwelJob"
			,input : {
				userAgentSDO : {
					"httpAgentId": ":: 우측상단 콤보박스 선택값 자동 대입 ::",	/* {setClientUserData} */
					"httpAgentType": "{setClientUserData}",
					"httpChannelCd": "{setClientUserData}",
					"httpClientId": "{setClientUserData}",
					"httpRequestId": "{setClientUserData}",
					"connTimeout": 1000,	/* {setClientUserData} */
					"readTimeout": 100000	/* {setClientUserData} */
				},
				ezwelJobInSDO : {
					"rsvNo": "E000000003",	/* {setClientUserData} */
					"rsvDateStart": "20181201",	/* {setClientUserData} */
					"rsvDateEnd": "20181220"	/* {setClientUserData} */
				}
			}
		},
		"시설이미지 전체다운로드 인터페이스" : {
			 url : $interfaceTester.requestNamespance + "/allReg/imageDownload"
		},
		"인터페이스 API KEY 발급" : {
			 url : $interfaceTester.requestNamespance + "/agent/apiKey"
			,input : {
				 "agentName" : ""
				,"httpAgentId" : ""
			}
		},
		"한글형태소분석" : {
			url : $interfaceTester.requestNamespance + "/morp/korean"
			,input : {
				sentenceList : [
					 "(주)신라호텔"
					,"신라 호텔 (주)"
					,"입실 및 퇴실시간은 반드시 지켜주세요."
					,"[펜션라이프] 국내숙박 연동 개발완료 확인"
					,"정보화용역사업"
					,"실라스테이서초"
					,"켄싱턴리조트제주"
					,"더플라자호텔"
					,"하이원리조트"
					,"인터컨티넨탈호텔"
					,"세종호텔"
					,"오라카이 인사동스위츠"
					,"더케이호텔서울"
					,"CS Premier Hotel"
					,"쉐라톤 서울 디큐브시티"
					,"신림 호텔 어반(Sillim Hotel Urban)"
					,"베스트 웨스턴 프리미어 구로 호텔"
					,"라마다 서울"
					,"서울해군호텔"
					,"강남아르누보씨티"
					,"베스트 웨스턴 프리미어 서울가든호텔"
					,"알티호텔"
					,"新罗首尔"					
				]
			}
		},
		"시설 매핑 실행" : {
			 url : $interfaceTester.requestNamespance + "/execFaclMapping"
			,input : {
				 "cityCd" : ""
				,"areaCd" : ""
				,"roomType" : ""
				,"roomClass" : ""
				,"faclDiv" : ""
			}
		}, 
		"InterfaceConfigXml" : {
			url : $interfaceTester.requestNamespance + "/configXML"
		}
	},
	requestHeader : {
		"http-client-id" : "",
		"http-request-id" : "",
		"http-channel-cd" : "",
		"http-agent-id" : "",
		"http-agent-type" : ""
	}
	,util : {
		removeTag : function( html ) {
			return html.replace(/(<([^>]+)>)/gi, "");
		}		
	}
	,send : function( httpAgentId, restURL, jsonString, dataType ) {
		
		console.info(arguments);
		
		var inputJson = null;
		var headerJson = null;
		var passAgentIdURI = new Array();
		passAgentIdURI.push($interfaceTester.requestNamespance + "/agent/apiKey");
		passAgentIdURI.push($interfaceTester.requestNamespance + "/morp/korean");
		passAgentIdURI.push($interfaceTester.requestNamespance + "/allReg/imageDownload");
		passAgentIdURI.push($interfaceTester.requestNamespance + "/allReg");
		passAgentIdURI.push($interfaceTester.requestNamespance + "/execFaclMapping");
		passAgentIdURI.push($interfaceTester.requestNamespance + "/configXML");
		
		if(!dataType) {
			dataType = "json";	
		}
		
		try {
			
			if(!jsonString || $.trim(jsonString) === "") {
				inputJson = JSON.parse("{}");
			}
			else {
				inputJson = JSON.parse(jsonString);
			}
			
			if( passAgentIdURI.indexOf(restURL) === -1 && inputJson["userAgentSDO"] && inputJson["userAgentSDO"]["httpAgentId"] ) {
				if(!httpAgentId || $.trim(httpAgentId) === "") {
					alert("제휴사를 선택하세요.");
					return false;
				}
				
				inputJson.userAgentSDO.httpAgentId = httpAgentId;
			}
			else {
				$("#httpAgentId").val("");
			}
			
			headerJson = $("#inputHeader").val();
			if(!headerJson || $.trim(headerJson) === "") {
				headerJson = JSON.parse("{}");
			}
			else {
				headerJson = JSON.parse(headerJson);
			}
			
			if( passAgentIdURI.indexOf(restURL) === -1 && ((headerJson["http-agent-id"] && headerJson["http-agent-id"] !== "") || !headerJson["http-agent-id"]) ) {
				headerJson["http-agent-id"] = httpAgentId;
			}	
			
			if(passAgentIdURI.indexOf(restURL) > -1) {
				$("#httpAgentId").val("");
			}
			
			if(dataType === "json") {
				headerJson["Accept"] = "application/json";
				headerJson["Content-Type"] = "application/json; charset=UTF-8";				
			}
		}
		catch( e ) {
			alert("입력 파라메터 필드의 JSON 문자열이 잘못되었습니다.\n" + e.message);
			return false;
		}

		console.info("Request Header");
		console.info(headerJson);
		console.info("Input Parameter");
		console.info(inputJson);
		
		$.ajax({ 
			type: "POST", 
			url : $interfaceTester.contextPath + restURL, 
			data: JSON.stringify(inputJson),
			headers : headerJson,
			dataType: dataType, 
			async: true,
			cache: false,
			processData: true,
			beforeSend: function ( xhr ) {
				console.info( "[TRANSACTION.AJAX BEFORESEND]");
				console.warn(xhr);
			},			
			timeout : function(){
				console.error( "[TRANSACTION TIMEOUT]");
			},
			success : function(data, status, xhr) {
				console.info("[SUCCESS]");
				console.info(data);
				
				if(dataType === "json") {
					$('#outputJson').text(JSON.stringify(data, undefined, 4));
				}
				else if(dataType === "xml") {
					$('#outputJson').text(new XMLSerializer().serializeToString(data));
				}
				else {
					$('#outputJson').text(data);
				}
			}, 
			error: function(jqXHR, textStatus, errorThrown) {
				console.error("[ERROR]");
				
				var output = "";
				output += JSON.stringify(jqXHR, undefined, 4);
				output += "\n";
				output += JSON.stringify(textStatus, undefined, 4);
				output += "\n";
				output += JSON.stringify(errorThrown, undefined, 4);
				console.error(jqXHR);
				console.error(textStatus);
				console.error(errorThrown);

				$('#outputJson').text($interfaceTester.util.removeTag(output.split("<br>").join("\n")));
			},
			statusCode : {
				404 : function() {
					console.error("*- transaction statusCode 404 page not found 페이지가 존재하지않습니다.");
				},
				403 : function(){
					console.error("*- transaction statusCode 403 Forbidden 접근권한없습니다.");
				},
				500 : function(){
					console.error("*- transaction statusCode 500 Internal Server Error 소프트웨어 장애가 발생하였습니다.");
				},
				503 : function(){
					console.error("*- transaction statusCode 503 Overhead Service Maintenance 접속량초과입니다 잠시후 다시 시도하세요.");
				}
			},
			complete : function(){
				console.debug( "[TRANSACTION COMPLETE]" );
			}			
		}); 
	}
	,bind : function() {
		
		$("#restURL").on("change", function( e ) {
			var selectText = $("#restURL option:selected").text();
			console.debug("text : " + selectText);
			
			var userHeader = $interfaceTester.requestHeader;
			var datas = $interfaceTester.datas;
			var input = datas[selectText].input;
			$("#inputJson").val(JSON.stringify(input, undefined, 4));
			$("#inputHeader").val(JSON.stringify(userHeader, undefined, 4));
		});
		
		$("#sendBtn").on("click", function(e) {
			var restURL = $("#restURL").val();
			if(!restURL || restURL == "") {
				alert("선택박스에서 인터페이스를 선택하세요.");
				return false;
			}
			
			var httpAgentId = $.trim($("#httpAgentId").val());
			var inputJson = $("#inputJson").val();

			$interfaceTester.send( httpAgentId, restURL, inputJson );
		});
		
		$("#getXmlConfigBtn").on("click", function(e){
			$("#inputJson").val("");
			$interfaceTester.send( null, $interfaceTester.datas["InterfaceConfigXml"].url, null, "xml" );
		});
	}
	,init : function() {

		$interfaceTester.color.metroColor();
		
		var datas = this.datas;
		var keys = Object.keys(datas);
		var $select = $("#restURL");
		$select.append(new Option(":: 선택 ::", ""));
		$.each(keys, function(idx, optionText) {
			console.debug("optionText["+idx+"] : " + optionText);
			$select.append(new Option(optionText, datas[optionText].url));
		});
		
		this.bind();
	}
};

$(document).ready(function() {
	$interfaceTester.init();
});
</script>


<div style="padding-bottom:4px;">

</div>
<div>
	<ul>
		<li style="width:100%;"><h2>Interface Test Site</h2></li>
	</ul>
	<ul class="row">
		<li><span>Input Data Setup</span></li>
		<li><select id="restURL"></select></li>
		<li>
			<select id="httpAgentId" name="httpAgentId">
				<option value="">:: 선택 ::</option>
				<option value="10055550">호텔패스글로벌 10055550</option>
				<option value="10000495">호텔엔조이 메이트아이 10000495</option>
				<option value="10000496">펜션라이프 플레이스엠 10000496</option>
				<option value="10002654">인터파크투어 10002654</option>
				<option value="10008365">펀앤비즈 샬레코리아 10008365</option>
				<option value="10030653">우리펜션(옐로트래블랩스) 10030653</option>
				<option value="10053461">인터치투어 10053461</option>
				<option value="10054233">웹투어 10054233</option>  
				<option value="10056562">(주)위드이노베이션_여기어때 10056562</option>
				<option value="99999999">직영/숙박 99999999</option>
			</select>
		</li>
		<!-- 
		<div class="row">
			<div class="col-md-3 col-sm-3 col-xs-6"> <a href="#" class="btn btn-sm animated-button victoria-one">Sign up</a> </div>
			<div class="col-md-3 col-sm-3 col-xs-6"> <a href="#" class="btn btn-sm animated-button victoria-two">Login</a> </div>
			<div class="col-md-3 col-sm-3 col-xs-6"> <a href="#" class="btn btn-sm animated-button victoria-three">Register</a> </div>
			<div class="col-md-3 col-sm-3 col-xs-6"> <a href="#" class="btn btn-sm animated-button victoria-four">Learn more</a> </div>
		</div>
  		 -->
		<li class="col-md-3 col-sm-3 col-xs-6"><button id="sendBtn" class="btn btn-sm animated-button victoria-one">SEND</button></li>
		<li><button id="getXmlConfigBtn">IF설정파일조회</button></li>
	</ul>
	<ul>
		<li style="width:65%;"><textarea placeholder="API 입력 파라메터(JSON)" id="inputJson" name="inputJson" style="width:99%;height:200px;"></textarea></li>
		<li style="width:35%"><textarea placeholder="API 요청 헤더(JSON)" id="inputHeader" name="inputHeader" style="width:99%;height:200px;"></textarea></li>
	</ul>
	<ul>
		<li style="width:100%;"><span>Output Data</span></li>
	</ul>
	<ul>
		<li style="width:100%;"><textarea placeholder="API 응답 결과(JSON)" id="outputJson" name="outputJson" style="width:99.5%;height:580px;" readonly></textarea></li>
	</ul>
</div>
</body>
</html>