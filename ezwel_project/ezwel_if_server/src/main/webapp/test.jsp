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
</style>
</head>
<body>

<script src="//code.jquery.com/jquery.js"></script>

<script type="text/javascript">
var testAssets = {
	 contextPath : "/API1.0" // testAssets.contextPath
	,datas : {
		"IN-신규시설등록수정" : {
			 url : "/{httpAgentId}/facl/record"
			,input : {
				  "dataUrl" : "http://ezcheckin.jyp.ezwel.com:8123/API1.0/10000496/facl/record"
			}
		},
		"IN-시설판매중지설정" : {
			  url : "/{httpAgentId}/facl/saleStop"
			 ,input : {
				 
			 }
		},
		"IN-예약내역조회" : {
			 url : "/{httpAgentId}/facl/view"
			,input : {
				
			}
		}
	}
	,send : function( restURL, inputJson ) {
		
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
			testAssets.send( restURL, inputJson );
		});
	}
	,init : function() {
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
	<span style="padding-left:2px;"><input id="httpAgentId" type="text" value="" style="width:10%;"/></span>
	<span style="padding-left:2px;"><button id="sendBtn">SEND</button></span>
</div>
<div>
<textarea id="inputJson" style="width:100%;height:200px;"></textarea>
</div>
<span>Output Data</span>
<div>
	<textarea id="outputJson" style="width:100%;height:200px;"></textarea>
</div>
</body>
</html>