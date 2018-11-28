<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ezwel api test</title>
<style>
	body, ul, li {
		margin:0px;
		padding:0px;
	}
</style>
</head>
<body>

<script src="//code.jquery.com/jquery.js"></script>

<script type="text/javascript">
function createData() { 
	
	// 1. 자바스크립트 객체 형태로 전달 
	//var sendData = {name:$('#dataUrl').val()}; 
	
	// 2. jQuery serialize함수를 사용해서 전달 
	//var sendData = $('#AjaxForm').serialize(); 
	var sendData = $('#inputJson').val();
	
	console.log("-----------------"); 
	console.log(sendData); 
	console.log("-----------------");
	
	return sendData; 
	
	// 3. 객체를 json 문자열로 만들어서 전달 
	//var sendData = JSON.stringify({name:$('#name').val(), email:$('#email').val()}); 
	//console.log(sendData);
	//return {"data" : sendDta}; 
}

function AjaxCall() { 
	
	var url = $("#restURL").val();
	
	$.ajax({ 
		type: "POST", 
		url : url, 
		data: createData(), 
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


</script>

<h2>Interface Test Site</h2>
<span>Input Data Setup</span>
<div>
	<span><input id="restURL" type="text" value="/API1.0/10000496/facl/record" style="width:30%;"/></span>
	<span style="padding-left:10px;"><input type="button" value="POST" onclick="AjaxCall();" /></span>
</div>
<div>
	<textarea id="inputJson" style="width:98%;height:200px;">
{
  "dataUrl" : "http://ezcheckin.jyp.ezwel.com:8123/API1.0/10000496/facl/record"
}	
	</textarea>
</div>
<span>Output Data</span>
<div>
	<textarea id="outputJson" style="width:98%;height:200px;"></textarea>
</div>
</body>
</html>