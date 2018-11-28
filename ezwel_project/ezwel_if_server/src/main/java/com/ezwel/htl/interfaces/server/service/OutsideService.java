package com.ezwel.htl.interfaces.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.configure.ConfigureHelper;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.HttpInterfaceExecutorService;
import com.ezwel.htl.interfaces.commons.http.data.AgentInfoSDO;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigSDO;
import com.ezwel.htl.interfaces.commons.http.data.MultiHttpConfigSDO;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.repository.OutsideRepository;
import com.ezwel.htl.interfaces.service.data.allReg.AllRegDataOutSDO;
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
	
	private OutsideRepository outsideRepository = (OutsideRepository) LApplicationContext.getBean(OutsideRepository.class);
	
	/** 제휴사 별 시설 정보 transaction commit 건수 */
	private static final Integer FACL_REG_DATA_TX_COUNT = 50;
	
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
		
		AllRegOutSDO out = null;
		MultiHttpConfigSDO multi = null;
		List<MultiHttpConfigSDO> multiHttpConfigList = null;
		
		try {
			multiHttpConfigList = new ArrayList<MultiHttpConfigSDO>();
			HttpConfigSDO httpConfigSDO = null;
			Map<String, AgentInfoSDO> interfaceAgents = InterfaceFactory.getInterfaceAgents();
			
			for(Entry<String, AgentInfoSDO> entry : interfaceAgents.entrySet()) {
				
				multi = new MultiHttpConfigSDO();
				httpConfigSDO = InterfaceFactory.getChannel("allReg".concat(OperateConstants.STR_HYPHEN).concat(entry.getValue().getHttpAgentId()), entry.getValue().getHttpAgentId());
				configureHelper.setupUserAgentInfo(httpConfigSDO, userAgentDTO);
				//no input 
				httpConfigSDO.setDoOutput(false);	
				//config
				multi.setHttpConfigDTO(httpConfigSDO);
				//output
				multi.setOutputType(AllRegOutSDO.class);
				multiHttpConfigList.add(multi);
			}
			
			/** execute multi thread interface */
			List<AllRegOutSDO> assets = inteface.sendMultiPostJSON(multiHttpConfigList);
			
			if(assets != null && assets.size() > 0) {
				/** execute transaction */ 
				out = insertAllFacl(assets, new AllRegOutSDO(), 0);
			}
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "시설검색 인터페이스 장애발생.", e);
		}
		
		
		return out;
	}	
	
	@APIOperation(description="전체시설일괄등록 인터페이스")
	private AllRegOutSDO insertAllFacl(List<AllRegOutSDO> assets, AllRegOutSDO allFacl, Integer faclIndex) {
		if(assets == null) {
			throw new APIException("시설 목록이 존재하지 않거나 잘못되었습니다.");
		}
		
		Integer txCount = 0;
		/**
		 * 1. 제휴사 별 TX 실행
		 * 2. 제휴사 내 100개씩  commit 되도록 operation 구성
		 */

		AllRegOutSDO allReg = assets.get(faclIndex);
		List<AllRegDataOutSDO> faclDatas = null;
		/** 제휴사 1건 별 이하 프로세스 실행 */
		if(allReg != null && allReg.getData() != null && allReg.getData().size() > 0) {
			/** 제휴사 별 시설 목록 */
			faclDatas = allReg.getData();
			/** 제휴사 별 시설 데이터 입력 실행 */
			txCount = insertFaclRegData(faclDatas, 0);
			/** 저장 개수가 존재하면 */
			if(txCount > 0) {
				if(allFacl.getData() == null) {
					allFacl.setData(new ArrayList<AllRegDataOutSDO>());
				}
				/** 제휴사의 시설 데이터를 output sdo 의 목록에 저장 */
				allFacl.getData().addAll(faclDatas);
				/** 트렌젝션 성공 개수 */
				allFacl.setTxCount(allFacl.getTxCount() + txCount);
			}
		}
		
		Integer nextIndex = faclIndex + 1;
		if(assets != null && assets.size() > nextIndex) {
			insertAllFacl(assets, allFacl, nextIndex);
		}
		
		return allFacl;
	}
	
	
	
	@APIOperation(description="전체시설일괄등록 인터페이스")
	private Integer insertFaclRegData(List<AllRegDataOutSDO> faclDatas/* 제휴사 별 시설 목록 */, Integer fromIndex) {
		/**
		 * 시설 50개씩 connection 끊어서 돌려야함.
		 */
		Integer txCount = 0;
		Integer toIndex = fromIndex + FACL_REG_DATA_TX_COUNT;
		List<AllRegDataOutSDO> saveFaclRegDatas = null;
		if(toIndex >= faclDatas.size()) {
			toIndex = faclDatas.size() - 1;
		}

		saveFaclRegDatas = faclDatas.subList(fromIndex, toIndex);
		
		txCount = outsideRepository.insertAllReg(saveFaclRegDatas);
		
		Integer nextIndex = toIndex + 1;
		if(faclDatas != null && faclDatas.size() > nextIndex) {
			insertFaclRegData(faclDatas/* 제휴사 별 시설 목록 */, nextIndex) ;
		}
		
		return txCount;
	}
	
	
	
	/**
	 * EZC_CACHE_DAY_PRICE, EZC_CACHE_DAY_PRICE_LOG 데이터 적제  ( 맵핑시설 테이블에 존재하는 시설의 당일특가만 적제 가능 )
	 * @param userAgentDTO
	 * @return 
	 */
	@APIOperation(description="당일특가검색 인터페이스")
	public SddSearchOutSDO callSddSearch(UserAgentSDO userAgentDTO) {
		
		SddSearchOutSDO out = null;
		MultiHttpConfigSDO multi = null;
		List<MultiHttpConfigSDO> multiHttpConfigList = null;
		List<HttpConfigSDO> channelList = null;
		
		try {
			multiHttpConfigList = new ArrayList<MultiHttpConfigSDO>();
			
			channelList = InterfaceFactory.getChannelGroup("sddSearch", userAgentDTO.getHttpAgentGroupId());
			if(channelList != null) {
				for(HttpConfigSDO httpConfigSDO : channelList) {
					multi = new MultiHttpConfigSDO();
					configureHelper.setupUserAgentInfo(httpConfigSDO, userAgentDTO);
					//no input 
					httpConfigSDO.setDoOutput(false);
					//config
					multi.setHttpConfigDTO(httpConfigSDO);
					//output
					multi.setOutputType(SddSearchOutSDO.class);
					multiHttpConfigList.add(multi);
				}
			}
			
			/** execute interface */
			//멀티 쓰레드 인터페이스 실행
			List<SddSearchOutSDO> assets = inteface.sendMultiPostJSON(multiHttpConfigList);
			
			/** execute dbio */
			out = outsideRepository.callSddSearch(assets);			
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "시설검색 인터페이스 장애발생.", e);
		}
		
		return out;
	}
	

	/**
	 * 멀티쓰레드
	 * EZC_CACHE_MIN_AMT, EZC_CACHE_SEARCH_LOG 데이터 적제
	 * input = 
	 * @param userAgentDTO
	 * @param faclSearchDTO
	 * @return
	 */
	@APIOperation(description="시설검색 인터페이스")
	public FaclSearchOutSDO callFaclSearch(UserAgentSDO userAgentDTO, FaclSearchInSDO faclSearchDTO) {
			
		FaclSearchOutSDO out = null;
		MultiHttpConfigSDO multi = null;
		List<HttpConfigSDO> channelList = null;
		List<MultiHttpConfigSDO> multiHttpConfigList = null;
		
		try {
			multiHttpConfigList = new ArrayList<MultiHttpConfigSDO>();
			
			channelList = InterfaceFactory.getChannelGroup("faclSearch", userAgentDTO.getHttpAgentGroupId());
			if(channelList != null) {
				for(HttpConfigSDO httpConfigSDO : channelList) {
					multi = new MultiHttpConfigSDO();
					configureHelper.setupUserAgentInfo(httpConfigSDO, userAgentDTO);
					//config
					multi.setHttpConfigDTO(httpConfigSDO);
					//input
					multi.setInputDTO(faclSearchDTO);
					//output
					multi.setOutputType(FaclSearchOutSDO.class);
					multiHttpConfigList.add(multi);
				}
			}
			
			/** execute interface */
			//멀티 쓰레드 인터페이스 실행
			List<FaclSearchOutSDO> assets = inteface.sendMultiPostJSON(multiHttpConfigList);
			
			/** execute dbio */
			out = outsideRepository.callFaclSearch(assets);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "시설검색 인터페이스 장애발생.", e);
		}
			
		return out;
	}
}
