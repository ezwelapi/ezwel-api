package com.ezwel.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.service.OutsideInterfaceService;
import com.ezwel.htl.interfaces.service.dto.agentJob.AgentJobInDTO;
import com.ezwel.htl.interfaces.service.dto.agentJob.AgentJobOutDTO;

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
		
		AgentJobInDTO agentJobDTO = new AgentJobInDTO();
		
		agentJobDTO.setRsvDateEnd("");
		agentJobDTO.setRsvDateStart("");
		agentJobDTO.setRsvNo("");
		AgentJobOutDTO output = (AgentJobOutDTO) interfaceService.callAgentJob(agentJobDTO);
		
	}
	
}
