<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ezwel api test</title>
<link rel="stylesheet" id="common-css"  href="./resources/common/css/common.css" type="text/css" media="all" />
<script type="text/javascript" src="//code.jquery.com/jquery.js" charset="UTF-8"></script>
<script type="text/javascript" src="./resources/common/js/index.js" charset="UTF-8"></script>
</head>
<body>
<div>
	<ul>
		<li style="width:100%;"><h2>Interface Test Site</h2></li>
	</ul>
	<ul class="titleContainer">
		<li><span class="title">Input Data Setup</span></li>
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

		<li><button id="sendBtn" >SEND</button></li>
		<li><button id="getXmlConfigBtn" >IF설정파일조회</button></li>
	</ul>
	<ul>
		<li style="width:65%;"><textarea placeholder="API 입력 파라메터(JSON)" id="inputJson" name="inputJson" style="width:99%;height:200px;"></textarea></li>
		<li style="width:35%"><textarea placeholder="API 요청 헤더(JSON)" id="inputHeader" name="inputHeader" style="width:99%;height:200px;"></textarea></li>
	</ul>
	<ul class="titleContainer">
		<li style="width:100%;"><span class="title">Output Data</span></li>
	</ul>
	<ul>
		<li style="width:100%;">
			<div placeholder="API 응답 결과(JSON)" id="outputJson" name="outputJson" style="width:99.5%;height:580px;background:#ffffff;overflow:auto;"></div>
		</li>
	</ul>
</div>
</body>
</html>