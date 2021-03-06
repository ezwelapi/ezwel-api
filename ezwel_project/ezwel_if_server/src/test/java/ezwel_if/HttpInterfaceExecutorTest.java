package ezwel_if;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.http.HttpInterfaceExecutor;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigSDO;
import com.ezwel.htl.interfaces.commons.http.data.MultiHttpConfigSDO;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.service.OutsideIFService;
import com.ezwel.htl.interfaces.service.data.cancelFeeAmt.CancelFeeAmtInSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeeAmt.CancelFeeAmtOutSDO;
import com.ezwel.htl.interfaces.service.data.cancelFeePsrc.CancelFeePsrcOutSDO;
import com.ezwel.htl.interfaces.service.data.roomRead.RoomReadOutSDO;

import ezwel_if.data.InputDTO;
import ezwel_if.data.InputDTOSub01;
import ezwel_if.data.InputDTOSub02;
import ezwel_if.data.OutputDTO;



public class HttpInterfaceExecutorTest  {

	private static Logger logger = LoggerFactory.getLogger(HttpInterfaceExecutorTest.class);
	
	private HttpConfigSDO config;
	private HttpInterfaceExecutor http;
	private InputDTO inputDTO;
	private List<String> restURIList;
	
	public HttpInterfaceExecutorTest() {
		http = new HttpInterfaceExecutor();
		config = new HttpConfigSDO();
		inputDTO = new InputDTO();
		this.setHttpConfigDTO();
		this.setInputDTO();
		this.setRestURI();
		this.interfaceInit();
	}
	
	private void interfaceInit() {
		InterfaceFactory.initLocalTestInterfaceFactory();		
	}
	
	
	@Test
	public void testConnect() {
		
		HttpConfigSDO in = new HttpConfigSDO();
		in.setRestURI("http://naver.xcom");
		logger.debug("isHttpConnect : {}", http.isHttpConnect(in));
		
		String test = "[java.net.AbstractPlainSocketImpl.connect(AbstractPlainSocketImpl.java:178), java.net.PlainSocketImpl.connect(PlainSocketImpl.java:172), java.net.SocksSocketImpl.connect(SocksSocketImpl.java:392), java.net.Socket.connect(Socket.java:579), sun.net.NetworkClient.doConnect(NetworkClient.java:175), sun.net.www.http.HttpClient.openServer(HttpClient.java:432), sun.net.www.http.HttpClient.openServer(HttpClient.java:527), sun.net.www.http.HttpClient.<init>(HttpClient.java:211), sun.net.www.http.HttpClient.New(HttpClient.java:308), sun.net.www.http.HttpClient.New(HttpClient.java:326), sun.net.www.protocol.http.HttpURLConnection.getNewHttpClient(HttpURLConnection.java:997), sun.net.www.protocol.http.HttpURLConnection.plainConnect(HttpURLConnection.java:933), sun.net.www.protocol.http.HttpURLConnection.connect(HttpURLConnection.java:851), com.ezwel.htl.interfaces.commons.http.HttpInterfaceExecutor.isHttpConnect(HttpInterfaceExecutor.java:579), com.ezwel.htl.interfaces.http.HttpInterfaceExecutorTest.testConnect(HttpInterfaceExecutorTest.java:61), sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method), sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57), sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43), java.lang.reflect.Method.invoke(Method.java:606), org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:47), org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12), org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:44), org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17), org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:271), org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:70), org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:50), org.junit.runners.ParentRunner$3.run(ParentRunner.java:238), org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:63), org.junit.runners.ParentRunner.runChildren(ParentRunner.java:236), org.junit.runners.ParentRunner.access$000(ParentRunner.java:53), org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:229), org.junit.runners.ParentRunner.run(ParentRunner.java:309), org.eclipse.jdt.internal.junit4.runner.JUnit4TestReference.run(JUnit4TestReference.java:86), org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:38), org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:538), org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:760), org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:460), org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:206)]";
		
	}
	
	
	//@Test
	public void receiveInsideInterfaceTest() {
		
		InterfaceDemoService service = new InterfaceDemoService();
		
		RoomReadOutSDO roomReadOutSDO = service.callRoomRead();
		
		logger.debug("[roomReadOutSDO]");
		logger.debug("Code : {}", roomReadOutSDO.getCode());
		logger.debug("Message : {}", roomReadOutSDO.getMessage());
		logger.debug("Data : {}", roomReadOutSDO.getData());
		
		
		CancelFeePsrcOutSDO cancelFeePsrcOutSDO = service.callCancelFeePsrc();
		
		logger.debug("[roomReadOutSDO]");
		logger.debug("Code : {}", cancelFeePsrcOutSDO.getCode());
		logger.debug("Message : {}", cancelFeePsrcOutSDO.getMessage());
		logger.debug("Data : {}", cancelFeePsrcOutSDO.getData());
		
		
		CancelFeeAmtOutSDO cancelFeeAmtOutSDO = service.callCancelFeeAmt();

		logger.debug("[cancelFeeAmtOutSDO]");
		logger.debug("Code : {}", cancelFeeAmtOutSDO.getCode());
		logger.debug("Message : {}", cancelFeeAmtOutSDO.getMessage());
		logger.debug("Data : {}", cancelFeeAmtOutSDO.getData());
	}
	
	
			
	//@Test
	public void sendJSONTest() {
		
		/*
		JSON out = JSONSerializer.toJSON( inputDTO );
		
		logger.debug("json : {}", out.toString(3));
		
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        int point = 0;
        for(StackTraceElement stack : stacktrace) {
        	logger.debug("({}) : {}", point,  stacktrace[point]);
        	point++;
        }
        */
		
		RoomReadOutSDO singleOut = (RoomReadOutSDO) http.sendJSON(config, inputDTO, RoomReadOutSDO.class);
		logger.debug("###RoomReadOutDTO : {}", singleOut);
		
		
		if(true) {
			return;
		}
		
		OutsideIFService interfaceService = new OutsideIFService();
		CancelFeeAmtInSDO cancelFeeAmtIn = new CancelFeeAmtInSDO();
		cancelFeeAmtIn.setRsvNo("rsvNo");

		UserAgentSDO agentInfoDTO = new UserAgentSDO();
		agentInfoDTO.setHttpAgentId("outside-07");
			
//		CancelFeeAmtOutDTO output = (CancelFeeAmtOutDTO) interfaceService.callCancelFeeAmt(agentInfoDTO, cancelFeeAmtIn);
//		logger.debug("#callCancelFeeAmt interface output : {}", output);
	}
	
	//@Test
	public void sendMultiJSONTest() {
		
		MultiHttpConfigSDO multi = null;
		HttpConfigSDO httpConfigDTO = null;
		
		List<MultiHttpConfigSDO> multiHttpConfigList = new ArrayList<MultiHttpConfigSDO>();
		int testCnt = 0;
		for(String restURI : restURIList) {
			
			//멀티 시그니처DTO
			multi = new MultiHttpConfigSDO();
			
			//설정 DTO
			httpConfigDTO = new HttpConfigSDO();
			httpConfigDTO.setHttpAgentId("CUSTOMER-A");
			httpConfigDTO.setHttpApiKey(APIUtil.getRandomUUID());
			httpConfigDTO.setHttpApiSignature("signature");
			httpConfigDTO.setHttpApiTimestamp(Long.toString(APIUtil.currentTimeMillis()));
			httpConfigDTO.setDoInput(true);
			httpConfigDTO.setDoOutput(true);
			httpConfigDTO.addRequestProperty("header01", "value01");
			httpConfigDTO.addRequestProperty("header02", "value02");
			httpConfigDTO.addRequestProperty("header03", "value03");
			httpConfigDTO.setEncoding(OperateConstants.DEFAULT_ENCODING);
			httpConfigDTO.setRestURI(restURI);
			httpConfigDTO.setHttpApiKey("(" + testCnt + ") " + APIUtil.getId());
			multi.setHttpConfigDTO(httpConfigDTO);
			
			//입력 DTO
			multi.setInputDTO(inputDTO);

			//결과 DTO Class
			multi.setOutputType(OutputDTO.class);
			
			logger.debug("-restURI : {}", multi.getHttpConfigDTO().getRestURI());
			
			//리스트 세팅
			multiHttpConfigList.add(multi);
			
			testCnt++;
		}
		
		//멀티 쓰레드 인터페이스 실행
		List<AbstractSDO> multiOut = http.sendMultiJSON(multiHttpConfigList);
		
		int cnt = 0;
		for(AbstractSDO out : multiOut) {
			logger.debug("IF-Result({}) : resultClass : {}, resultValue : {}", cnt, out.getClass(), out);
			cnt++;
		}
		
	}
	
	private void setHttpConfigDTO() {

		config = new HttpConfigSDO();
		config.setChanId("roomRead");
		config.setRestURI("http://localhost:9123/ezwel_if_demo/service/roomRead.jsp");
		config.setHttpAgentId("CUSTOMER-A");
		config.setHttpApiKey(APIUtil.getRandomUUID());
		config.setHttpApiSignature("signature");
		config.setHttpApiTimestamp(Long.toString(APIUtil.currentTimeMillis()));
		config.setDoInput(true);
		config.setDoOutput(true);
		config.addRequestProperty("header01", "value01");
		config.addRequestProperty("header02", "value02");
		config.addRequestProperty("header03", "value03");
		config.setEncoding(OperateConstants.DEFAULT_ENCODING);
		
		config.setHttpAgentType("httpAgentType-httpAgentType");
		config.setHttpChannelCd("httpChannelCd-httpChannelCd");
		config.setHttpClientId("httpClientId-httpClientId");
		config.setHttpRequestId("httpRequestId-httpRequestId");
	}
	
	public void setInputDTO() {
		inputDTO.setKey01("TEST VALUE key01");
		
		InputDTOSub01 key02 = null;
		List<InputDTOSub01> key02List = new ArrayList<InputDTOSub01>();
		
		key02 = new InputDTOSub01();
		key02.setKey02Sub01("subVal01");
		key02.setKey02Sub02("subVal02");
		key02.setKey02Sub03("subVal03");	
		key02List.add(key02);

		key02.setKey02Sub01("subVal04");
		key02.setKey02Sub02("subVal05");
		key02.setKey02Sub03("subVal06");	
		key02List.add(key02);
		
		key02.setKey02Sub01("subVal07");
		key02.setKey02Sub02("subVal08");
		key02.setKey02Sub03("subVal09");	
		key02List.add(key02);
		
		inputDTO.setKey02(key02List);
		
		inputDTO.setKey03("TEST VALUE key03");
		
		InputDTOSub02 key04 = new InputDTOSub02();
		key04.setSub01("subVal01");
		key04.setSub02("subVal02");
		key04.setSub03("subVal03");	
		inputDTO.setKey04(key04);
		
		inputDTO.setKey05("TEST VALUE key05");
	}
	
	public void setRestURI() {
		
		restURIList = new ArrayList<String>();
		restURIList.add("http://localhost:9123/ezwel_if_demo/service/agentJob.jsp");
		restURIList.add("http://localhost:9123/ezwel_if_demo/service/allReg.jsp");
		restURIList.add("http://localhost:9123/ezwel_if_demo/service/cancelFeeAmt.jsp");
		restURIList.add("http://localhost:9123/ezwel_if_demo/service/cancelFeePsrc.jsp");
		restURIList.add("http://localhost:9123/ezwel_if_demo/service/ezwelJob.jsp");
		restURIList.add("http://localhost:9123/ezwel_if_demo/service/faclSearch.jsp");
		restURIList.add("http://localhost:9123/ezwel_if_demo/service/omiNumIdn.jsp");
		restURIList.add("http://localhost:9123/ezwel_if_demo/service/orderCancelReq.jsp");
		restURIList.add("http://localhost:9123/ezwel_if_demo/service/record.jsp");
		restURIList.add("http://localhost:9123/ezwel_if_demo/service/roomRead.jsp");
		restURIList.add("http://localhost:9123/ezwel_if_demo/service/rsvHistSend.jsp");
		restURIList.add("http://localhost:9123/ezwel_if_demo/service/saleStop.jsp");
		restURIList.add("http://localhost:9123/ezwel_if_demo/service/sddSearch.jsp");
		restURIList.add("http://localhost:9123/ezwel_if_demo/service/view.jsp");
		restURIList.add("http://localhost:9123/ezwel_if_demo/service/voucherReg.jsp");
	}
	
	
	
}
