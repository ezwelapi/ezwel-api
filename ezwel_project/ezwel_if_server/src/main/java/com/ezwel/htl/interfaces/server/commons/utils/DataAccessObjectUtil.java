package com.ezwel.htl.interfaces.server.commons.utils;

import org.apache.ibatis.session.SqlSession;

import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;

public class DataAccessObjectUtil {

	protected SqlSession sqlSession = (SqlSession) LApplicationContext.getBean(SqlSession.class);
	
	protected final static String OUTSIDE_MAPPER_NAMESPACE = "com.ezwel.htl.interfaces.server.repository.outsideMapper";
	
}
