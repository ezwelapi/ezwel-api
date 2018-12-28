package com.ezwel.htl.interfaces.server.commons.utils;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;

@Component
@APIType(description="오라클 내장함수 > 자바 구현 유틸")
public class OracleFuncUtil {

	private static final Logger logger = LoggerFactory.getLogger(OracleFuncUtil.class);
	
	//POWER
	@APIOperation(description="오라클 POWER 대응 함수")
	public BigDecimal getPower(BigDecimal value, BigDecimal squareNum) {
		BigDecimal out = new BigDecimal(Math.pow(value.doubleValue(), squareNum.doubleValue()));
		return out;
	}
	
	//SIN
	@APIOperation(description="오라클 SIN 대응 함수")
	public BigDecimal getSin(BigDecimal value) {
		BigDecimal out = new BigDecimal(Math.sin(value.doubleValue()));
		return out;
	}
	
	//COS
	@APIOperation(description="오라클 COS 대응 함수")
	public BigDecimal getCos(BigDecimal value) {
		BigDecimal out = new BigDecimal(Math.cos(value.doubleValue()));
		return out;
	}
	
	//ATAN2 결과 다름!! ( 직접 구현 필요 )
	@APIOperation(description="오라클 ATAN2 대응 함수")
	public BigDecimal getAtan2(BigDecimal x, BigDecimal y) {
		BigDecimal out = new BigDecimal(Math.atan2(y.doubleValue(), x.doubleValue()));
		return out;
	}
	
	//SQRT
	@APIOperation(description="오라클 SQRT 대응 함수")
	public BigDecimal getSqrt(BigDecimal value) {
		BigDecimal out = new BigDecimal(Math.sqrt(value.doubleValue()));
		return out;
	}	
	
	//ROUND 소수점 자리수 설정할수 없음!! ( 직접 구현 필요 )
	@APIOperation(description="오라클 ROUND 대응 함수")
	public BigDecimal getRound(BigDecimal value, BigDecimal decimalPlaces) {
		BigDecimal out = new BigDecimal(Math.round(value.doubleValue()));
		return out;
	}	
}
