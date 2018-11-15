package com.ezwel.htl.interfaces.commons.http.dto;

import com.ezwel.htl.interfaces.commons.abstracts.APIObject;
import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.constants.IOperateCode;

/**
 * <pre>
 * Http 인터페이스 API용 DTO  
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */
@APIModel
public class MultiHttpConfigDTO extends APIObject {
	//<T extends APIObject> 
	
	@APIFields(description = "HTTP 통신 설정 DTO", required=true)
	private HttpConfigDTO httpConfigDTO;
	
	@APIFields(description = "HTTP 통신 요청 파라메터 DTO", required=true)
	private AbstractEntity inputDTO;
	
	@APIFields(description = "HTTP 통신 응답 결과타입 클래스", required=true)
	private Class<? extends AbstractEntity> outputType;
	
	@APIFields(description = "HTTP 인터페이스 API 총 실행시간")
	private Long lapTimeMillis;
	
	public MultiHttpConfigDTO() {
		this.reset();
	}
	
	private void reset() {
		httpConfigDTO = null;
		inputDTO = null;
		outputType = null;
		lapTimeMillis = IOperateCode.LONG_ZERO_VALUE;
	}

	public HttpConfigDTO getHttpConfigDTO() {
		return httpConfigDTO;
	}

	public void setHttpConfigDTO(HttpConfigDTO httpConfigDTO) {
		this.httpConfigDTO = httpConfigDTO;
	}

	public AbstractEntity getInputDTO() {
		return inputDTO;
	}

	public void setInputDTO(AbstractEntity inputDTO) {
		this.inputDTO = inputDTO;
	}

	public Class<? extends AbstractEntity> getOutputType() {
		return outputType;
	}

	public void setOutputType(Class<? extends AbstractEntity> outputType) {
		this.outputType = outputType;
	}

	public Long getLapTimeMillis() {
		return lapTimeMillis;
	}

	public void setLapTimeMillis(Long lapTimeMillis) {
		this.lapTimeMillis = lapTimeMillis;
	}

	


	
	
}
