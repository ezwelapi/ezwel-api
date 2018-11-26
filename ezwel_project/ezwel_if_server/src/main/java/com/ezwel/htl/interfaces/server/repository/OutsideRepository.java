package com.ezwel.htl.interfaces.server.repository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.commons.utils.DataAccessObjectUtil;
import com.ezwel.htl.interfaces.server.entities.EzcFacl;
import com.ezwel.htl.interfaces.server.entities.EzcFaclAment;
import com.ezwel.htl.interfaces.server.entities.EzcFaclImg;
import com.ezwel.htl.interfaces.service.data.allReg.AllRegDataOutSDO;
import com.ezwel.htl.interfaces.service.data.allReg.AllRegSubImagesOutSDO;
import com.ezwel.htl.interfaces.service.data.faclSearch.FaclSearchOutSDO;
import com.ezwel.htl.interfaces.service.data.sddSearch.SddSearchOutSDO;

/**
 * <pre>
 * Outside 인터페이스 서비스
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
@Repository
@APIType(description="내부 > 외부 인터페이스 데이터 핸들링 서비스")
public class OutsideRepository extends DataAccessObjectUtil {

	private static final Logger logger = LoggerFactory.getLogger(OutsideRepository.class);
	
	private PropertyUtil propertyUtil = (PropertyUtil) LApplicationContext.getBean(PropertyUtil.class);
	
	/**
	 * 맵핑 시설 : EZC_FACL, EZC_FACL_IMG, EZC_FACL_AMENT ( 1 : N : N ), 데이터 적제 
	 * 요청(입력) 파라메터 없음
	 */
	@Transactional
	@APIOperation(description="전체시설일괄등록 인터페이스")
	public Integer insertAllReg(List<AllRegDataOutSDO> saveFaclRegDatas) {
		
		Integer txCount = 0;
		Integer txSuccess = 0;
		List<AllRegSubImagesOutSDO> subImages = null;
		List<String> serviceCodes = null;
		EzcFaclAment ezcFaclAment = null;
		BigDecimal faclCdSeq = null;
		EzcFacl ezcFacl = null;
		EzcFaclImg ezcFaclImg = null;
		AllRegDataOutSDO telegramItem = null;
		
		try {
			
			for(Integer i = 0; i < saveFaclRegDatas.size(); i++) {
				telegramItem = saveFaclRegDatas.get(i);
				
				ezcFacl = (EzcFacl) propertyUtil.copySameProperty(telegramItem, EzcFacl.class);
				/**
				 * 인터페이스 전문 필드명과 DB 테이블 컬럼 엔티티명 죄다 다름 아우 -_- 필드 별 일일이 확인 필요
				 */
				
				/** 0. 시설 코드 (Number) Sequnce */
				faclCdSeq = sqlSession.selectOne(OUTSIDE_MAPPER_NAMESPACE.concat("sequnceEzcFacl"));
				ezcFacl.setFaclCd(faclCdSeq);
				ezcFacl.setRegId(Local.commonHeader().getSystemUserId());
				ezcFacl.setRegDt(APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis()));
				
				/** 1. EZC_FACL 1건 저장 */
				txSuccess = sqlSession.insert(OUTSIDE_MAPPER_NAMESPACE.concat("insertEzcFacl"), ezcFacl);
				if(txSuccess > 0) {
					txCount++;
					
					/** 2. EZC_FACL_IMG N건 저장 */
					subImages = telegramItem.getSubImages();
					if(subImages != null) {
						for(AllRegSubImagesOutSDO subImage : subImages) {
							ezcFaclImg = (EzcFaclImg) propertyUtil.copySameProperty(subImage, EzcFaclImg.class);
							ezcFaclImg.setRegId(Local.commonHeader().getSystemUserId());
							ezcFaclImg.setRegDt(APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis()));							
							/**
							 * 인터페이스 전문 필드명과 DB 테이블 컬럼 엔티티명 죄다 다름 아우 -_- 필드 별 일일이 확인 필요
							 */
							
							txCount += sqlSession.insert(OUTSIDE_MAPPER_NAMESPACE.concat("insertEzcFaclImg"), ezcFaclImg);
						}
					}
					
					/** 3. EZC_FACL_AMENT N건 저장 */
					if(APIUtil.isNotEmpty(telegramItem.getServiceCodes())) {
						serviceCodes = Arrays.asList(telegramItem.getServiceCodes().split(OperateConstants.STR_COMA));
						
						for(String serviceCode : serviceCodes) {
							if(APIUtil.isNotEmpty(serviceCode)) {
								
								ezcFaclAment = new EzcFaclAment();
								ezcFaclAment.setFaclCd(ezcFacl.getFaclCd());
								ezcFaclAment.setAmentType(serviceCode.trim());
								ezcFaclAment.setRegId(Local.commonHeader().getSystemUserId());
								ezcFaclAment.setRegDt(APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis()));
								
								txCount += sqlSession.insert(OUTSIDE_MAPPER_NAMESPACE.concat("insertEzcFaclImg"), ezcFaclAment);
							}
						}
					}
				}
			}
		}
		catch(Exception e) {
			//에러 발생 레코드 errorItems에 저장후 runtimeException 없이 로깅후 종료
			Local.commonHeader().addErrorItems(telegramItem);
			e.printStackTrace();
		}
		
		return txCount;
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
	public FaclSearchOutSDO callFaclSearch(List<FaclSearchOutSDO> assets) {
			
		FaclSearchOutSDO out = null;
		
		try {
			
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
	public SddSearchOutSDO callSddSearch(List<SddSearchOutSDO> assets) {
		
		SddSearchOutSDO out = null;
		
		try {
		
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "시설검색 인터페이스 장애발생.", e);
		}
		
		return out;
	}
	

}
