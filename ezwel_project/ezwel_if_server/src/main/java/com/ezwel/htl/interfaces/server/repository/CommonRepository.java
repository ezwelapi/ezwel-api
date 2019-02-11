package com.ezwel.htl.interfaces.server.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractDataAccessObject;
import com.ezwel.htl.interfaces.server.commons.constants.CodeDataConstants;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.entities.EzcCityCd;
import com.ezwel.htl.interfaces.server.entities.EzcDetailCd;

/**
 * <pre>
 * Outside 인터페이스 서비스
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
@Repository
@APIType(description="공통 데이터 조회")
public class CommonRepository extends AbstractDataAccessObject {

	private static final Logger logger = LoggerFactory.getLogger(CommonRepository.class);
	
	private PropertyUtil propertyUtil = (PropertyUtil) LApplicationContext.getBean(PropertyUtil.class);
	
	/**
	 * 맵핑 시설 : EZC_FACL, EZC_FACL_IMG, EZC_FACL_AMENT ( 1 : N : N ), 데이터 적제 
	 * 요청(입력) 파라메터 없음
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	@APIOperation(description="공통코드조회(별도커넥션사용)")
	public List<EzcDetailCd> selectListCommonCodeRequired(EzcDetailCd inEzcDetailCd) {
		return selectListCommonCode(inEzcDetailCd);
	}	
	
	@APIOperation(description="공통코드 목록조회")
	public List<EzcDetailCd> selectListCommonCode(EzcDetailCd inEzcDetailCd) {
		if(inEzcDetailCd != null) {
			inEzcDetailCd.setUseYn(CodeDataConstants.CD_Y);
		}
		List<EzcDetailCd> out = sqlSession.selectList(getNamespace("DETAIL_CD_MAPPER", "selectListEzcDetailCd"), inEzcDetailCd);
		return out;
	}	
	
	@APIOperation(description="시도코드 목록조회") // EZC_CITY_CD
	public List<EzcCityCd> selectListSidoCode(EzcCityCd inEzcCityCd) {
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		
		if(inEzcCityCd == null) {
			inEzcCityCd = new EzcCityCd();
		}
		
		propertyUtil.removeDefaulFieldData(inEzcCityCd);
		inEzcCityCd.setUseYn(CodeDataConstants.CD_Y);
		
		List<EzcCityCd> out = sqlSession.selectList(getNamespace("CITY_CD_MAPPER", "selectListEzcCityCd"), inEzcCityCd);
		return out;
	}	
	
}
