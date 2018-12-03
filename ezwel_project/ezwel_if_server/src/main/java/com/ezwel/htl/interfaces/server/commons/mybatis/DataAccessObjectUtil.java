package com.ezwel.htl.interfaces.server.commons.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;

import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.server.commons.constants.NamespaceConstants;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;

public class DataAccessObjectUtil {

	protected SqlSession sqlSession = (SqlSession) LApplicationContext.getBean(SqlSession.class);
	
	//@Qualifier("sqlSession")
	protected SqlSessionTemplate sqlSessionTemplate;

	protected TransactionManager getTransactionManager() {
		if(sqlSessionTemplate == null) {
			sqlSessionTemplate = (SqlSessionTemplate) LApplicationContext.getBean(SqlSessionTemplate.class); 
		}
		return (TransactionManager) LApplicationContext.getBean(TransactionManager.class);
	}
	
	protected String getNamespace(String namespace, String sqlId) {
		if( NamespaceConstants.getNamespace() == null || NamespaceConstants.getNamespace().size() == 0 ) {
			throw new APIException(MessageConstants.RESPONSE_CODE_2001, "네임스페이스가 설정되지 않습니다.");
		}
		if(APIUtil.isEmpty(sqlId) ) {
			throw new APIException(MessageConstants.RESPONSE_CODE_2001, "SQL-ID가 존재하지 않습니다.");
		}
		return NamespaceConstants.getNamespace(namespace).concat(OperateConstants.STR_DOT).concat(sqlId);
	}
}
