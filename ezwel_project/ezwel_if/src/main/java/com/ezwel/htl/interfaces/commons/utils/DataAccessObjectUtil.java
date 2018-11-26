package com.ezwel.htl.interfaces.commons.utils;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

public class DataAccessObjectUtil {

	@Autowired
	protected SqlSession sqlSession;
}
