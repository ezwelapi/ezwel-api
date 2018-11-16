package com.ezwel.htl.interfaces.http;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractDTO;
import com.ezwel.htl.interfaces.commons.constants.OperateCode;
import com.ezwel.htl.interfaces.commons.http.HttpInterfaceExecutorService;
import com.ezwel.htl.interfaces.commons.http.dto.UserAgentDTO;
import com.ezwel.htl.interfaces.commons.http.dto.HttpConfigDTO;
import com.ezwel.htl.interfaces.commons.http.dto.MultiHttpConfigDTO;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.http.dto.InputDTO;
import com.ezwel.htl.interfaces.http.dto.InputDTOSub01;
import com.ezwel.htl.interfaces.http.dto.InputDTOSub02;
import com.ezwel.htl.interfaces.http.dto.OutputDTO;
import com.ezwel.htl.interfaces.service.OutsideInterfaceService;
import com.ezwel.htl.interfaces.service.dto.cancelFeeAmt.CancelFeeAmtInDTO;
import com.ezwel.htl.interfaces.service.dto.cancelFeeAmt.CancelFeeAmtOutDTO;



public class HttpInterfaceExecutorTest  {

	private static Logger logger = LoggerFactory.getLogger(HttpInterfaceExecutorTest.class);
	
	private HttpConfigDTO config;
	private HttpInterfaceExecutorService http;
	private InputDTO inputDTO;
	private List<String> restURIList;
	
	public HttpInterfaceExecutorTest() {
		http = new HttpInterfaceExecutorService();
		config = new HttpConfigDTO();
		inputDTO = new InputDTO();
		this.setHttpConfigDTO();
		this.setInputDTO();
		this.setRestURI();
	}
	
	@Test
	public void sendPostJSONTest() {
		
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
		
		OutputDTO singleOut = (OutputDTO) http.sendPostJSON(config, inputDTO, OutputDTO.class);
		logger.debug("singleOut : {}", singleOut);
		
		
		OutsideInterfaceService interfaceService = new OutsideInterfaceService();
		CancelFeeAmtInDTO cancelFeeAmtIn = new CancelFeeAmtInDTO();
		cancelFeeAmtIn.setRsvNo("rsvNo");

		UserAgentDTO agentInfoDTO = new UserAgentDTO();
		agentInfoDTO.setHttpAgentId("outside-07");
			
//		CancelFeeAmtOutDTO output = (CancelFeeAmtOutDTO) interfaceService.callCancelFeeAmt(agentInfoDTO, cancelFeeAmtIn);
//		logger.debug("#callCancelFeeAmt interface output : {}", output);
	}
	
	//@Test
	public void sendMultiJSONTest() {
		
		MultiHttpConfigDTO multi = null;
		HttpConfigDTO httpConfigDTO = null;
		
		List<MultiHttpConfigDTO> multiHttpConfigList = new ArrayList<MultiHttpConfigDTO>();
		int testCnt = 0;
		for(String restURI : restURIList) {
			
			//멀티 시그니처DTO
			multi = new MultiHttpConfigDTO();
			
			//설정 DTO
			httpConfigDTO = new HttpConfigDTO();
			httpConfigDTO.setHttpAgentId("CUSTOMER-A");
			httpConfigDTO.setHttpApiKey(APIUtil.getRandomUUID());
			httpConfigDTO.setHttpApiSignature("signature");
			httpConfigDTO.setHttpApiTimestamp(Long.toString(APIUtil.currentTimeMillis()));
			httpConfigDTO.setDoInput(true);
			httpConfigDTO.setDoOutput(true);
			httpConfigDTO.addRequestProperty("header01", "value01");
			httpConfigDTO.addRequestProperty("header02", "value02");
			httpConfigDTO.addRequestProperty("header03", "value03");
			httpConfigDTO.setEncoding(OperateCode.DEFAULT_ENCODING);
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
		List<AbstractDTO> multiOut = http.sendMultiPostJSON(multiHttpConfigList);
		
		int cnt = 0;
		for(AbstractDTO out : multiOut) {
			logger.debug("IF-Result({}) : resultClass : {}, resultValue : {}", cnt, out.getClass(), out);
			cnt++;
		}
		
	}
	
	private void setHttpConfigDTO() {

		config = new HttpConfigDTO();
		config.setRestURI("http://localhost:9123/ezwel_if_demo/service/index.jsp");
		config.setHttpAgentId("CUSTOMER-A");
		config.setHttpApiKey(APIUtil.getRandomUUID());
		config.setHttpApiSignature("signature");
		config.setHttpApiTimestamp(Long.toString(APIUtil.currentTimeMillis()));
		config.setDoInput(true);
		config.setDoOutput(true);
		config.addRequestProperty("header01", "value01");
		config.addRequestProperty("header02", "value02");
		config.addRequestProperty("header03", "value03");
		config.setEncoding(OperateCode.DEFAULT_ENCODING);
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
