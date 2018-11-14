package com.ezwel.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.service.OutsideInterfaceService;
import com.ezwel.htl.interfaces.service.dto.agentJob.AgentJobOutDTO;
import com.ezwel.htl.interfaces.service.dto.cancelFeeAmt.CancelFeeAmtInDTO;
import com.ezwel.htl.interfaces.service.dto.cancelFeeAmt.CancelFeeAmtOutDTO;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 14.
 */
public class InterfaceTester {

	private static final Logger logger = LoggerFactory.getLogger(InterfaceTester.class);
	
	private OutsideInterfaceService interfaceService;
	
	public void test() {
		
		CancelFeeAmtInDTO cancelFeeAmtIn = new CancelFeeAmtInDTO();
		
		cancelFeeAmtIn.setRsvNo("rsvNo");
		CancelFeeAmtOutDTO output = (CancelFeeAmtOutDTO) interfaceService.callCancelFeeAmt(cancelFeeAmtIn);
		logger.debug("#callCancelFeeAmt interface output : {}", output);
	}
	
}
