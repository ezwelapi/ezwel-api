<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>EZC-API INTERFACE COLLECTION</title>
<link rel="stylesheet" id="common-css"  href="./resources/common/css/common.css" type="text/css" media="all" />
<script type="text/javascript" src="./resources/common/js/jquery.js" charset="UTF-8"></script>
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
				<option value="10055550" httpApiKey="f5831137b0aa322fc2af1a37d6ecf8cei">호텔패스글로벌 10055550</option>
				<option value="10000495" httpApiKey="d099b5ed2d8d352d6bb539febb4b46aai">호텔엔조이 메이트아이 10000495</option>
				<option value="10000496" httpApiKey="dcf82e9fc9dafb753075c6c244f6699fi">펜션라이프 플레이스엠 10000496</option>
				<option value="10002654" httpApiKey="2abcbc439f7d84747b370776de4aa3d1i">인터파크투어 10002654</option>
				<option value="10008365" httpApiKey="aadde1e6ac23fdbb04c7b9a067ee7c1ai">펀앤비즈 샬레코리아 10008365</option>
				<option value="10030653" httpApiKey="4a2022d9a3b0da0231b701de6778eb15i">우리펜션(옐로트래블랩스) 10030653</option>
				<option value="10053461" httpApiKey="173a7146e68477b8b3b3e3017646aa6ci">인터치투어 10053461</option>
				<option value="10054233" httpApiKey="b6380f208fd90378ff70786a02d92aa5i">웹투어 10054233</option>  
				<option value="10056562" httpApiKey="e54d735ff4ab6b93308c31d99cd683d4i">(주)위드이노베이션_여기어때 10056562</option>
				<option value="-" httpApiKey="-">콘도24</option>
				<option value="99999999" httpApiKey="e54d735ffzab6b93308c31d99cd683d4i">직영/숙박 99999999</option>
			</select>
		</li>
		<li><button id="sendBtn" >실행</button></li>
		<li><button id="getXmlConfigBtn" >IF설정파일조회</button></li>
		<li><button id="mailContentsDecode" >Decode</button></li>
	</ul>
	<ul>
		<li style="width:55%;height:200px;"><textarea placeholder="API 입력 파라메터(JSON)" id="inputJson" name="inputJson" style="width:99%;height:200px;"></textarea></li>
		<li style="width:45%;height:200px;float:right;">
			<div>
				<textarea placeholder="API 요청 헤더(JSON)" id="inputHeader" name="inputHeader" style="width:98%;height:150px;"></textarea>
			</div>
			<div>
				<textarea placeholder="body" id="inputMailContents" name="inputMailContents" style="width:98%;height:40px;"></textarea>
			</div>			
		</li>
	</ul>
	<ul class="titleContainer">
		<li style="width:100%;padding-top:8px;"><span class="title">Output Data</span></li>
	</ul>
	<ul>
		<li style="width:100%;">
			<div placeholder="API 응답 결과(JSON)" id="outputJson" name="outputJson" style="width:99.5%;height:580px;background:#ffffff;overflow:auto;"></div>
		</li>
	</ul>
</div>
</body>
</html>