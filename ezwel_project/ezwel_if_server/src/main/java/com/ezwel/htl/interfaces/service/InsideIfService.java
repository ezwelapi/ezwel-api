package com.ezwel.htl.interfaces.service;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.HttpInterfaceExecutorService;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigDTO;
import com.ezwel.htl.interfaces.commons.spring.ApplicationContext;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.service.data.agentJob.AgentJobInDTO;
import com.ezwel.htl.interfaces.service.data.agentJob.AgentJobOutDTO;
import com.ezwel.htl.interfaces.service.data.record.RecordInDTO;
import com.ezwel.htl.interfaces.service.data.record.RecordOutDTO;
import com.ezwel.htl.interfaces.service.data.saleStop.SaleStopInDTO;
import com.ezwel.htl.interfaces.service.data.saleStop.SaleStopOutDTO;
import com.ezwel.htl.interfaces.service.data.view.ViewInDTO;
import com.ezwel.htl.interfaces.service.data.view.ViewOutDTO;
import com.ezwel.htl.interfaces.service.data.voucherReg.VoucherRegInDTO;
import com.ezwel.htl.interfaces.service.data.voucherReg.VoucherRegOutDTO;

/**
 * <pre>
 * 인터페이스 서비스
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
@Service
@APIType
public class InsideIfService {

	private static final Logger logger = LoggerFactory.getLogger(InsideIfService.class);

	private HttpInterfaceExecutorService inteface = (HttpInterfaceExecutorService) ApplicationContext.getBean(HttpInterfaceExecutorService.class);
	
	private PropertyUtil propertyUtil;
	
	public InsideIfService() {
		if(propertyUtil == null) {
			propertyUtil = new PropertyUtil();
		}
	}
	
	@APIOperation(description="신규시설등록수정 인터페이스")
	public RecordOutDTO callRecord(RecordInDTO recordDTO) {
		
		RecordOutDTO out = null;
		
		try {
			
			HttpConfigDTO config = doProcessRequestHeader();
			/** execute interface */
			out = (RecordOutDTO) inteface.sendPostJSON(config, recordDTO, RecordOutDTO.class);
		}
		catch(Exception e) {
			throw new APIException("시설판매중지설정 인터페이스 장애발생.", e);
		}
		
		return out;
	}
	
	@APIOperation(description="시설판매중지설정 인터페이스")
	public SaleStopOutDTO callSaleStop(SaleStopInDTO saleStopDTO) {
		
		SaleStopOutDTO out = null;
		
		try {
			
			HttpConfigDTO config = doProcessRequestHeader();
			/** execute interface */
			out = (SaleStopOutDTO) inteface.sendPostJSON(config, saleStopDTO, SaleStopOutDTO.class);
		}
		catch(Exception e) {
			throw new APIException("시설판매중지설정 인터페이스 장애발생.", e);
		}
		
		return out;
	}
	
	@APIOperation(description="시설바우처번호등록 인터페이스")
	public VoucherRegOutDTO callVoucherReg(VoucherRegInDTO voucherRegDTO) {
		
		VoucherRegOutDTO out = null;
		
		try {
			
			HttpConfigDTO config = doProcessRequestHeader();
			/** execute interface */
			out = (VoucherRegOutDTO) inteface.sendPostJSON(config, voucherRegDTO, VoucherRegOutDTO.class);
		}
		catch(Exception e) {
			throw new APIException("시설바우처번호등록 인터페이스 장애발생.", e);
		}
		
		return out;
	}
	
	@APIOperation(description="예약내역조회 인터페이스")
	public ViewOutDTO callView(ViewInDTO viewDTO) {
		
		ViewOutDTO out = null;
		
		try {
			
			HttpConfigDTO config = doProcessRequestHeader();
			/** execute interface */
			out = (ViewOutDTO) inteface.sendPostJSON(config, viewDTO, ViewOutDTO.class);
		}
		catch(Exception e) {
			throw new APIException("예약내역조회 인터페이스 장애발생.", e);
		}
		
		return out;
	}
	
	@APIOperation(description="주문대사(제휴사) 인터페이스")
	public AgentJobOutDTO callAgentJob(AgentJobInDTO agentJobDTO) {
		
		AgentJobOutDTO out = null;
		
		try {
			
			HttpConfigDTO config = doProcessRequestHeader();
			/** execute interface */
			out = (AgentJobOutDTO) inteface.sendPostJSON(config, agentJobDTO, AgentJobOutDTO.class);
		}
		catch(Exception e) {
			throw new APIException("주문대사(제휴사) 인터페이스 장애발생.", e);
		}
		
		return out;
	}	
	
	/**
	 * <pre>
	 * [메서드 설명]
	 * 인터 페이스 요청에 해당하는 헤더 정보 조회 및 확인 
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 21.
	 */
	@APIOperation(description="인터페이스 헤더 정보 세팅")
	private HttpConfigDTO doProcessRequestHeader() {
		HttpConfigDTO out = new HttpConfigDTO();
		HttpServletRequest request = ApplicationContext.getRequest();
		request.getHeader("");
		/** setup httpApiSignature */
		//config.setHttpApiSignature(APIUtil.getHttpSignature(config.getHttpAgentId(), config.getHttpApiKey(), config.getHttpApiTimestamp()));
		return out;
	}
	
}
