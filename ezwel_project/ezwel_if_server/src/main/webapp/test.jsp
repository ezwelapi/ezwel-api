<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<!-- Ajax 사용을 위한 jquery -->
<script src="//code.jquery.com/jquery.js"></script>
 
<!-- JavaScript -->
<script type="text/javascript">
function createData() { 
	
	// 1. 자바스크립트 객체 형태로 전달 
	//var sendData = {name:$('#dataUrl').val()}; 
	
	// 2. jQuery serialize함수를 사용해서 전달 
	var sendData = $('#AjaxForm').serialize(); 
	
	console.log(sendData); 
	
	return sendData; 
	
	// 3. 객체를 json 문자열로 만들어서 전달 
	//var sendData = JSON.stringify({name:$('#name').val(), email:$('#email').val()}); 
	//console.log(sendData);
	//return {"data" : sendDta}; 
}

function AjaxCall() { 
	$.ajax({ 
		type: "POST", 
		url : "http://ezcheckin.jyp.ezwel.com:8123/API1.0/10000496/facl/record", 
		data: createData(), 
		dataType:"json", 
		success : function(data, status, xhr) { 
			console.log(data); 
		}, 
		error: function(jqXHR, textStatus, errorThrown) { 
			console.log(jqXHR.responseText); 
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