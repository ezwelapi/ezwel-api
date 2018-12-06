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
				"rsvDateStart": "",
				"rsvDateEnd": ""
			}
		},
		
		"OUT-전체시설일괄등록 인터페이스" : {
			 url : "/service/allReg"
			,input : {
				"httpAgentType": "TEST VALUE",
				"httpChannelCd": "TEST VALUE",
				"httpClientId": "TEST VALUE",
				"httpRequestId": "TEST VALUE",
				"connTimeout": 1000,
				"readTimeout": 100000
			}
		},
		"OUT-시설검색 인터페이스" : {
			 url : "/service/callFaclSearch"
			,input : {
				"httpAgentGroupId": "faclSearch-chanGroup",
				"httpAgentType": "TEST VALUE",
				"httpChannelCd": "TEST VALUE",
				"httpClientId": "TEST VALUE",
				"httpRequestId": "TEST VALUE",
				"connTimeout": 1000,
				"readTimeout": 100000
			}
		},
		"OUT-당일특가검색 인터페이스" : {
			 url : "/service/callSddSearch"
			,input : {
				"httpAgentGroupId": "sddSearch-chanGroup",
				"httpAgentType": "TEST VALUE",
				"httpChannelCd": "TEST VALUE",
				"httpClientId": "TEST VALUE",
				"httpRequestId": "TEST VALUE",
				"connTimeout": 1000,
				"readTimeout": 100000
			}
		}
		
		,
		"OUT-객실정보조회 인터페이스" : {
			 url : "/service/callRoomRead"
			,input : {
				"httpAgentId": "",
				"httpAgentType": "TEST VALUE",
				"httpChannelCd": "TEST VALUE",
				"httpClientId": "TEST VALUE",
				"httpRequestId": "TEST VALUE",
				"connTimeout": 1000,
				"readTimeout": 100000
				/* 실제 입력 필드 추가 */
			}
		},
		"OUT-취소수수규정 인터페이스" : {
			 url : "/service/callCancelFeePsrc"
			,input : {
				"httpAgentId": "",
				"httpAgentType": "TEST VALUE",
				"httpChannelCd": "TEST VALUE",
				"httpClientId": "TEST VALUE",
				"httpRequestId": "TEST VALUE",
				"connTimeout": 1000,
				"readTimeout": 100000
				/* 실제 입력 필드 추가 */
			}
		},
		"OUT-결재완료내역전송 인터페이스" : {
			 url : "/service/callRsvHistSend"
			,input : {
				"httpAgentId": "",
				"httpAgentType": "TEST VALUE",
				"httpChannelCd": "TEST VALUE",
				"httpClientId": "TEST VALUE",
				"httpRequestId": "TEST VALUE",
				"connTimeout": 1000,
				"readTimeout": 100000
				/* 실제 입력 필드 추가 */
			}
		},
		"OUT-취소수수료계산 인터페이스" : {
			 url : "/service/callCancelFeeAmt"
			,input : {
				"httpAgentId": "",
				"httpAgentType": "TEST VALUE",
				"httpChannelCd": "TEST VALUE",
				"httpClientId": "TEST VALUE",
				"httpRequestId": "TEST VALUE",
				"connTimeout": 1000,
				"readTimeout": 100000
				/* 실제 입력 필드 추가 */
			}
		},
		"OUT-주문취소요청 인터페이스" : {
			 url : "/service/callOrderCancelReq"
			,input : {
				"httpAgentId": "",
				"httpAgentType": "TEST VALUE",
				"httpChannelCd": "TEST VALUE",
				"httpClientId": "TEST VALUE",
				"httpRequestId": "TEST VALUE",
				"connTimeout": 1000,
				"readTimeout": 100000
				/* 실제 입력 필드 추가 */
			}
		},
		"OUT-누락건확인 인터페이스" : {
			 url : "/service/callOmiNumIdn"
			,input : {
				"httpAgentId": "",
				"httpAgentType": "TEST VALUE",
				"httpChannelCd": "TEST VALUE",
				"httpClientId": "TEST VALUE",
				"httpRequestId": "TEST VALUE",
				"connTimeout": 1000,
				"readTimeout": 100000
				/* 실제 입력 필드 추가 */
			}
		},
		"OUT-주문대사(이지웰) 인터페이스" : {
			 url : "/service/callEzwelJob"
			,input : {
				"httpAgentId": "",
				"httpAgentType": "TEST VALUE",
				"httpChannelCd": "TEST VALUE",
				"httpClientId": "TEST VALUE",
				"httpRequestId": "TEST VALUE",
				"connTimeout": 1000,
				"readTimeout": 100000
				/* 실제 입력 필드 추가 */
			}
		}
		
	}
	,send : function( httpAgentId, restURL, jsonString ) {
		
		var inputJson = null;
		try {
			if(!jsonString || $.trim(jsonString) === "") {
				inputJson = JSON.parse("{}");
			}
			else {
				inputJson = JSON.parse(jsonString);
			}
			
			if( inputJson.httpAgentId && inputJson.httpAgentId !== "" ) {
				inputJson.httpAgentId = httpAgentId;
			}
		}
		catch( e ) {
			alert("입력 파라메터 필드의 JSON 문자열이 잘못되었습니다.\n" + e.message);
			return false;
		}
		
		$.ajax({ 
			type: "POST", 
			url : testAssets.contextPath + restURL, 
			data: inputJson, 
			dataType:"json", 
			success : function(data, status, xhr) {
				console.log("success");
				console.log(data);
				$('#outputJson').text(JSON.stringify(data, undefined, 4));
			}, 
			error: function(jqXHR, textStatus, errorThrown) {
				console.log("error");
				var output = "";
				output += JSON.stringify(jqXHR, undefined, 4);
				output += "\n";
				output += JSON.stringify(textStatus, undefined, 4);
				output += "\n";
				output += JSON.stringify(errorThrown, undefined, 4);
				console.log(jqXHR);
				console.log(textStatus);
				console.log(errorThrown);

				$('#outputJson').text(output);
			}
		}); 
	}
	,bind : function() {
		
		$("#restURL").on("change", function( e ) {
			var selectText = $("#restURL option:selected").text();
			console.debug("text : " + selectText);

			var datas = testAssets.datas;
			var input = datas[selectText].input;
			$("#inputJson").val(JSON.stringify(input, undefined, 4));
		});
		
		$("#sendBtn").on("click", function(e) {
			var restURL = $("#restURL").val();
			if(!restURL || restURL == "") {
				alert("선택박스에서 인터페이스를 선택하세요.");
				return false;
			}
			
			var httpAgentId = $.trim($("#httpAgentId").val());
			var inputJson = $("#inputJson").val();
			
			if(restURL.indexOf("{httpAgentId}") > -1 && (!httpAgentId || httpAgentId == "")) {
				alert("에이전트 아이디를 입력하세요");
				return false;
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
<textarea id="inputJson" name="inputJson" style="width:100%;height:200px;"></textarea>
</div>
<span>Output Data</span>
<div>
	<textarea id="outputJson" name="outputJson" style="width:100%;height:200px;"></textarea>
</div>
</body>
</html>