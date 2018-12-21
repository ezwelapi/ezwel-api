package com.ezwel.htl.interfaces.server.repository;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractDataAccessObject;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.commons.utils.ExceptionUtil;
import com.ezwel.htl.interfaces.server.entities.EzcFaclImg;

/**
 * <pre>
 * Outside 인터페이스 서비스
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
@Repository
@APIType(description="내부 > 외부 인터페이스 데이터 핸들링 서비스")
public class MultiBuildImageUpdateRepository extends AbstractDataAccessObject implements Callable<Integer> {

	private static final Logger logger = LoggerFactory.getLogger(MultiBuildImageUpdateRepository.class);
	
	private ExceptionUtil exceptionUtil;
	
	private EzcFaclImg ezcFaclImg;
	
	private boolean isErrorPassed;
	
	public MultiBuildImageUpdateRepository(EzcFaclImg ezcFaclImg, boolean isErrorPassed) {
		logger.info("- OutsideBuildImageRepository Initialized : {}\nisErrorPassed : {}", ezcFaclImg, isErrorPassed);

		this.ezcFaclImg = ezcFaclImg;
		this.isErrorPassed = isErrorPassed;
	}
	
	
	@APIOperation(description="전체시설 이미지 다운로드 경로 단건 저장")
	public Integer call() {
		
		Integer txCount = OperateConstants.INTEGER_ZERO_VALUE;
		try {
		
			txCount = sqlSession.update(getNamespace("FACL_IMG_MAPPER", "updateEzcFaclImgDownload"), ezcFaclImg);
		}
		catch(Exception e) {
			
			//에러 발생 레코드 errorItems에 저장후 runtimeException 없이 로깅후 종료
			if(isErrorPassed) {
				
				exceptionUtil = (ExceptionUtil) LApplicationContext.getBean(exceptionUtil, ExceptionUtil.class);
				
				/** 에러 발생 레코드 interface batch error log file에 저장후 RuntimeException 없이 로깅후 종료 */
				exceptionUtil.writeBatchErrorLog("{}\n{}@{}\n에러 발생 객체 : {}", 
						new Object[] {"[전체시설 이미지 다운로드 경로 저장 장애발생]", this.getClass().getCanonicalName(), "updateBuildImage", ezcFaclImg},
						new StringBuffer().append(this.getClass().getSimpleName()).append(OperateConstants.STR_AT).append("updateBuildImage-").append(APIUtil.getFastDate(OperateConstants.DEF_DAY_FORMAT)).toString(), 
						e);
				
				logger.error("Code : {}", MessageConstants.RESPONSE_CODE_9500);
				logger.error("Message : {}", e.getMessage());
				e.getStackTrace();
				
			}
			else {
				throw new APIException("이미지 다운르드 경로 DB 저장 실패 {}", new Object[] {e.getMessage()}, e) ; 
			}
		}

		return txCount;
	}
	
	
}
