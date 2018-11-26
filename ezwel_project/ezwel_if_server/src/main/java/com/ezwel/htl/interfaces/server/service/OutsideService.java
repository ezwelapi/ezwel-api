package com.ezwel.htl.interfaces.server.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.configure.ConfigureHelper;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.HttpInterfaceExecutorService;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigSDO;
import com.ezwel.htl.interfaces.commons.http.data.MultiHttpConfigSDO;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.service.data.allReg.AllRegOutSDO;
import com.ezwel.htl.interfaces.service.data.faclSearch.FaclSearchInSDO;
import com.ezwel.htl.interfaces.service.data.faclSearch.FaclSearchOutSDO;
import com.ezwel.htl.interfaces.service.data.sddSearch.SddSearchOutSDO;

/**
 * <pre>
 * 인터페이스 서비스
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
@Service
@APIType(description="외부 인터페이스 데이터 적제 서비스")
public class OutsideService {

	private static final Logger logger = LoggerFactory.getLogger(OutsideService.class);

	private HttpInterfaceExecutorService inteface = (HttpInterfaceExecutorService) LApplicationContext.getBean(HttpInterfaceExecutorService.class);
	
	private ConfigureHelper configureHelper = (ConfigureHelper) LApplicationContext.getBean(ConfigureHelper.class);
	
	private PropertyUtil propertyUtil = (PropertyUtil) LApplicationContext.getBean(PropertyUtil.class);
	
	public OutsideService() {
		
		if(propertyUtil == null) {
			propertyUtil = new PropertyUtil();
		}
		if(inteface == null) {
			inteface = new HttpInterfaceExecutorService();
		}
		if(configureHelper == null) {
			configureHelper = new ConfigureHelper();
		}		
	}
	

	/**
	 * 맵핑 시설 : EZC_FACL, EZC_FACL_IMG, EZC_FACL_AMENT ( 1 : N : N ), 데이터 적제 
	 * 요청(입력) 파라메터 없음
	 */
	@APIOperation(description="전체시설일괄등록 인터페이스")
	public AllRegOutSDO callAllReg(UserAgentSDO userAgentDTO) {
		
		List<AllRegOutSDO> out = null;
		MultiHttpConfigSDO multi = null;
		List<HttpConfigSDO> channelList = null;
		List<MultiHttpConfigSDO> multiHttpConfigList = null;
		
		try {
			multiHttpConfigList = new ArrayList<MultiHttpConfigSDO>();
			
			channelList = InterfaceFactory.getChannelGroup("allReg", userAgentDTO.getHttpAgentGroupId());
			if(channelList != null) {
				for(HttpConfigSDO httpConfigDTO : channelList) {
					multi = new MultiHttpConfigSDO();
					configureHelper.setupUserAgentInfo(httpConfigDTO, userAgentDTO);
					//no input 
					httpConfigDTO.setDoOutput(false);					
					//config
					multi.setHttpConfigDTO(httpConfigDTO);
					//output
					multi.setOutputType(FaclSearchOutSDO.class);
					multiHttpConfigList.add(multi);
				}
			}
			
			/** execute interface */
			//멀티 쓰레드 인터페이스 실행
			out = inteface.sendMultiPostJSON(multiHttpConfigList);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "시설검색 인터페이스 장애발생.", e);
		}
		
		
		try {
			HttpConfigSDO httpConfigDTO = InterfaceFactory.getChannel("allReg", userAgentDTO.getHttpAgentId());
			configureHelper.setupUserAgentInfo(httpConfigDTO, userAgentDTO);
			/** execute interface */
			out = (AllRegOutSDO) inteface.sendPostJSON(httpConfigDTO, AllRegOutSDO.class);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "전체시설일괄등록 인터페이스 장애발생.", e);
		}
		
		return out;
	}	
	
	
	/**
	 * 멀티쓰레드
	 * EZC_CACHE_MIN_AMT 데이터 적제
	 * input = 
	 * @param userAgentDTO
	 * @param faclSearchDTO
	 * @return
	 */
	@APIOperation(description="시설검색 인터페이스")
	public List<FaclSearchOutSDO> callFaclSearch(UserAgentSDO userAgentDTO, FaclSearchInSDO faclSearchDTO) {
			
		List<FaclSearchOutSDO> out = null;
		MultiHttpConfigSDO multi = null;
		List<HttpConfigSDO> channelList = null;
		List<MultiHttpConfigSDO> multiHttpConfigList = null;
		
		try {
			multiHttpConfigList = new ArrayList<MultiHttpConfigSDO>();
			
			channelList = InterfaceFactory.getChannelGroup("faclSearch", userAgentDTO.getHttpAgentGroupId());
			if(channelList != null) {
				for(HttpConfigSDO httpConfigDTO : channelList) {
					multi = new MultiHttpConfigSDO();
					configureHelper.setupUserAgentInfo(httpConfigDTO, userAgentDTO);
					//config
					multi.setHttpConfigDTO(httpConfigDTO);
					//input
					multi.setInputDTO(faclSearchDTO);
					//output
					multi.setOutputType(FaclSearchOutSDO.class);
					multiHttpConfigList.add(multi);
				}
			}
			
			/** execute interface */
			//멀티 쓰레드 인터페이스 실행
			out = inteface.sendMultiPostJSON(multiHttpConfigList);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "시설검색 인터페이스 장애발생.", e);
		}
			
		return out;
	}
	
	/**
	 * EZC_CACHE_DAY_PRICE 데이터 적제  ( 맵핑시설 테이블에 존재하는 시설의 당일특가만 적제 가능 )
	 * @param userAgentDTO
	 * @return 
	 */
	@APIOperation(description="당일특가검색 인터페이스")
	public List<SddSearchOutSDO> callSddSearch(UserAgentSDO userAgentDTO) {
		
		List<SddSearchOutSDO> out = null;
		MultiHttpConfigSDO multi = null;
		List<MultiHttpConfigSDO> multiHttpConfigList = null;
		List<HttpConfigSDO> channelList = null;
		
		try {
			multiHttpConfigList = new ArrayList<MultiHttpConfigSDO>();
			
			channelList = InterfaceFactory.getChannelGroup("sddSearch", userAgentDTO.getHttpAgentGroupId());
			if(channelList != null) {
				for(HttpConfigSDO httpConfigDTO : channelList) {
					multi = new MultiHttpConfigSDO();
					configureHelper.setupUserAgentInfo(httpConfigDTO, userAgentDTO);
					//no input 
					httpConfigDTO.setDoOutput(false);
					//config
					multi.setHttpConfigDTO(httpConfigDTO);
					//output
					multi.setOutputType(SddSearchOutSDO.class);
					multiHttpConfigList.add(multi);
				}
			}
			
			/** execute interface */
			//멀티 쓰레드 인터페이스 실행
			out = inteface.sendMultiPostJSON(multiHttpConfigList);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "시설검색 인터페이스 장애발생.", e);
		}
		
		return out;
	}
	

}
