package com.ezwel.htl.interfaces.commons.http.data;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;

/**
 * <pre>
 * Http 인터페이스 API용 DTO  
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */
@APIModel
public class MultiHttpConfigSDO extends AbstractSDO {
	//<T extends APIObject> 
	
	@APIFields(description = "HTTP 통신 설정 DTO", required=true)
	private HttpConfigSDO httpConfigDTO;
	
	@APIFields(description = "HTTP 통신 요청 파라메터 DTO", required=true)
	private AbstractSDO inputDTO;
	
	@APIFields(description = "HTTP 통신 응답 결과타입 클래스", required=true)
	private Class<? extends AbstractSDO> outputType;
	
	@APIFields(description = "HTTP 인터페이스 API 총 실행시간")
	private Long lapTimeMillis;
	
	public MultiHttpConfigSDO() {
		this.reset();
	}
	
	private void reset() {
		httpConfigDTO = null;
		inputDTO = null;
		outputType = null;
		lapTimeMillis = OperateConstants.LONG_ZERO_VALUE;
	}

	public HttpConfigSDO getHttpConfigDTO() {
		return httpConfigDTO;
	}

	public void setHttpConfigDTO(HttpConfigSDO httpConfigDTO) {
		this.httpConfigDTO = httpConfigDTO;
	}

	public AbstractSDO getInputDTO() {
		return inputDTO;
	}

	public void setInputDTO(AbstractSDO inputDTO) {
		this.inputDTO = inputDTO;
	}

	public Class<? extends AbstractSDO> getOutputType() {
		return outputType;
	}

	public void setOutputType(Class<? extends AbstractSDO> outputType) {
		this.outputType = outputType;
	}

	public Long getLapTimeMillis() {
		return lapTimeMillis;
	}

	public void setLapTimeMillis(Long lapTimeMillis) {
		this.lapTimeMillis = lapTimeMillis;
	}

	


	
	
}
