package com.ezwel.htl.interfaces.server.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ezwel.htl.interfaces.commons.annotation.APIType;

@Repository
@APIType(description="시설정보(EZC_FACL)테이블의 시설정보 매핑 서비스 (매핑 데이터 적제 : EZC_MAPPING_FACL:매핑 시설, EZC_MAPPING_GRP_FACL:매핑 그룹 시설) 이 서비스에서 형태소 분석 필요")
public class FaclMappingRepository {

		private static final Logger logger = LoggerFactory.getLogger(FaclMappingRepository.class);
		
		
		
		
}