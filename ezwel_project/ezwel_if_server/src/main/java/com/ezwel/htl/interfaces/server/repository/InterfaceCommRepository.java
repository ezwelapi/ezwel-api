package com.ezwel.htl.interfaces.server.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.server.commons.constants.NamespaceConstants;
import com.ezwel.htl.interfaces.server.commons.mybatis.DataAccessObjectUtil;
import com.ezwel.htl.interfaces.server.entities.EzcDetailCd;

@Repository
@APIType(description="인터페이스 공통 데이터 조회 DAO")
public class InterfaceCommRepository extends DataAccessObjectUtil {

	private static final Logger logger = LoggerFactory.getLogger(InterfaceCommRepository.class);
	
	/**
	 * 공통 코드 조회
	 * @return
	 */
	public List<EzcDetailCd> getEzcPartnerList() {
		
		// IF_COMM_MAPPER  
		List<EzcDetailCd> out = sqlSession.selectList(NamespaceConstants.IF_COMM_MAPPER.concat("selectListEzcDetailCd"));
		
		return out;
	}
	
	
}
