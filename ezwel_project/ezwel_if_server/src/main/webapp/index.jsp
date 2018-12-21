<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ezwel api test</title>
<style>
	body, ul, li {
		margin:0px;
		padding:0px 14px 0px 10px;
	}
	h2, span {
		color : #ffffff
	}
</style>
</head>
<body>

<script src="//code.jquery.com/jquery.js"></script>

<script type="text/javascript">
var testAssets = {
	color : {
		// testAssets.color.metro_palette
		metro_palette : {
			1:"black",
			2:"lime",
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
			13:"yellow",
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
		// testAssets.color.metroColor
		metroColor: function(){
			var randomNumber = Math.ceil(Math.random() * Object.keys(testAssets.color.metro_palette).length ); // ceil 올림
			var color = testAssets.color.metro_palette[ randomNumber ];
			console.debug("color : " + color);
			$("body").css("background-color", color);
		}		
	}
	,contextPath : "/API1.0" // testAssets.contextPath
	,datas : {
		"IN-신규시설등록수정" : {
			 url : "/{httpAgentId}/facl/record"
			,input : {
				"dataUrl" : "http://ezc-api.dev.ezwel.com/API1.0/10000496/facl/record"
			}
		},
		"IN-시설판매중지설정" : {
			  url : "/{httpAgentId}/facl/saleStop"
			 ,input : {
				"pdtNo": "1",
				"sellFlag": "N"
			 }
		},
		"IN-예약내역조회" : {
			 url : "/{httpAgentId}/facl/view"
			,input : {
				"rsvNo": "123456789",
				"startDate": "",
				"endDate": "",
				"dateType": ""
			}
		},
		"IN-시설바우처번호등록" : {
			 url : "/{httpAgentId}/facl/voucherReg"
			,input : {
				"rsvNo": "123456789",
				"voucherNo": "1234"
			}
		},
		"IN-주문대사(제휴사)" : {
			 url : "/{httpAgentId}/facl/agentJob"
			,input : {
				"rsvNo": "123456789",
				"rsvDateStart": "20181201",
				"rsvDateEnd": "20181215"
			}
		},
		
		"OUT-전체시설일괄등록 인터페이스" : {
			 url : "/service/allReg"
			,input : {
				"httpAgentType": "{setClientUserData}",
				"httpChannelCd": "{setClientUserData}",
				"httpClientId": "{setClientUserData}",
				"httpRequestId": "{setClientUserData}",
				"connTimeout": 1000,	/* {setClientUserData} */
				"readTimeout": 100000	/* {setClientUserData} */
			}
		},
		"OUT-시설검색 인터페이스" : {
			 url : "/service/callFaclSearch"
			,input : {
				userAgentSDO : {
					"httpAgentGroupId": "multiFaclSearchGroup",	/* PIX DATA */
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
					"sidoCode": "11"	/* {setClientUserData} */
				}
			}
		},
		"OUT-당일특가검색 인터페이스" : {
			 url : "/service/callSddSearch"
			,input : {
				"httpAgentGroupId": "sddSearch-chanGroup",
				"httpAgentType": "{setClientUserData}",
				"httpChannelCd": "{setClientUserData}",
				"httpClientId": "{setClientUserData}",
				"httpRequestId": "{setClientUserData}",
				"connTimeout": 1000,	/* {setClientUserData} */
				"readTimeout": 100000	/* {setClientUserData} */,
			}
		},
		"OUT-객실정보조회 인터페이스" : {
			 url : "/service/callRoomRead"
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
					"pdtNo": "KRSEL402",	/* {setClientUserData} */
					"checkInDate": "20190101",	/* {setClientUserData} */
					"checkOutDate": "20190102",	/* {setClientUserData} */
					"roomCnt": 1,	/* {setClientUserData} */
					"adultCnt": 2,	/* {setClientUserData} */
					"childCnt": 0	/* {setClientUserData} */
				}
			}
		},
		"OUT-취소수수료규정 인터페이스" : {
			 url : "/service/callCancelFeePsrc"
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
			 url : "/service/callRsvHistSend"
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
			 url : "/service/callCancelFeeAmt"
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
			 url : "/service/callOrderCancelReq"
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
			 url : "/service/callOmiNumIdn"
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
			 url : "/service/callEzwelJob"
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
			 url : "/service/allReg/imageDownload"
		},
		"인터페이스 API KEY 발급" : {
			 url : "/agent/apiKey"
			,input : {
				 "agentName" : ""
				,"httpAgentId" : ""
			}
		},
		"한글형태소분석" : {
			url : "/morp/korean"
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
		}
		
	},
	requestHeader : {
		"http-client-id" : "",
		"http-request-id" : "",
		"http-channel-cd" : "",
		"http-agent-id" : "",
		"http-agent-type" : "",
	}
	,send : function( httpAgentId, restURL, jsonString ) {
		
		console.info(arguments);
		
		var inputJson = null;
		var headerJson = null;
		var passAgentIdURI = new Array();
		passAgentIdURI.push("/agent/apiKey");
		passAgentIdURI.push("/morp/korean");
		passAgentIdURI.push("/service/allReg/imageDownload");
		passAgentIdURI.push("/service/allReg");
		
		try {
			if(!jsonString || $.trim(jsonString) === "") {
				inputJson = JSON.parse("{}");
			}
			else {
				inputJson = JSON.parse(jsonString);
			}
			
			if( passAgentIdURI.indexOf(restURL) === -1 && inputJson.httpAgentId && inputJson.httpAgentId !== "" ) {
				inputJson.httpAgentId = httpAgentId;
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
			
			headerJson["Accept"] = "application/json";
			headerJson["Content-Type"] = "application/json; charset=UTF-8";			
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
			url : testAssets.contextPath + restURL, 
			data: JSON.stringify(inputJson),
			headers : headerJson,
			dataType: "json", 
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
				$('#outputJson').text(JSON.stringify(data, undefined, 4));
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

				$('#outputJson').text(output);
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
			
			var userHeader = testAssets.requestHeader;
			var datas = testAssets.datas;
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

			if(restURL.indexOf("/{httpAgentId}/") > -1 || restURL.indexOf("/service/") > -1) {
				if(restURL.indexOf("{httpAgentId}") > -1 && (!httpAgentId || httpAgentId == "")) {
					alert("에이전트 아이디를 선택하세요");
					return false;
				}			
			}
			restURL = restURL.replace("{httpAgentId}", httpAgentId);
			testAssets.send( httpAgentId, restURL, inputJson );
		});
	}
	,init : function() {

		testAssets.color.metroColor();
		
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
	testAssets.init();
});
</script>

<h2>Interface Test Site</h2>
<div style="padding-bottom:4px;">
	<span>Input Data Setup</span>
	<span>
		<select id="restURL"></select>
	</span>
	<span style="padding-left:2px;width:10%;">
		<select id="httpAgentId" name="httpAgentId">
			<option value="">:: 선택 ::</option>
			<option value="10000495">호텔엔조이 메이트아이 10000495</option>
			<option value="10000496">펜션라이프 플레이스엠 10000496</option>
			<option value="10002654">인터파크투어 10002654</option>
			<option value="10008365">펀앤비즈 샬레코리아 10008365</option>
			<option value="10030653">우리펜션(옐로트래블랩스) 10030653</option>
			<option value="10053461">인터치투어 10053461</option>
			<option value="10054233">웹투어 10054233</option>
			<option value="10055550">호텔패스글로벌 10055550</option>
			<option value="10056562">(주)위드이노베이션_여기어때 10056562</option>
			<option value="99999999">직영/숙박 99999999</option>
		</select>
	</span>
	<span style="padding-left:2px;"><button id="sendBtn">SEND</button></span>
</div>
<div>
<textarea placeholder="API 입력 파라메터(JSON)" id="inputJson" name="inputJson" style="float:left;width:69%;height:200px;"></textarea>
<textarea placeholder="API 요청 헤더(JSON)" id="inputHeader" name="inputHeader" style="float:right;width:30%;height:200px;"></textarea>
</div>
<span>Output Data</span>
<div> 
	<textarea placeholder="API 응답 결과(JSON)" id="outputJson" name="outputJson" style="width:100%;height:200px;" readonly></textarea>
</div>
</body>
</html>