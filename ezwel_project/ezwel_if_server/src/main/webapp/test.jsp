<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ezwel api test</title>
</head>
<body>

<script src="//code.jquery.com/jquery.js"></script>
<script type="text/javascript">
function createData() { 
	
	// 1. 자바스크립트 객체 형태로 전달 
	//var sendData = {name:$('#dataUrl').val()}; 
	
	// 2. jQuery serialize함수를 사용해서 전달 
	var sendData = $('#AjaxForm').serialize(); 
	
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
	
	var url = null;
	if(location.href.indexOf("localhost:8282/ezwel_if_server") > -1) {
		url = "http://http://localhost:8282/ezwel_if_server/API1.0/10000496/facl/record";
	}
	else {
		url = "http://ezc-api.dev.ezwel.com/API1.0/10000496/facl/record";
	}
	
	$.ajax({ 
		type: "POST", 
		url : url, 
		data: createData(), 
		dataType:"json", 
		success : function(data, status, xhr) {
			console.log("success");
			console.log(data);
		}, 
		error: function(jqXHR, textStatus, errorThrown) {
			console.log("error");
			console.log(jqXHR);
			console.log(textStatus);
			console.log(errorThrown);
		}
	}); 
}


</script>

<form name="AjaxForm" id="AjaxForm"> 
<label for="name">dataUrl</label> 
<input type="text" name="dataUrl" id="dataUrl" />
</form> 
<input type="button" value="POST" onclick="AjaxCall();" />

</body>
</html>