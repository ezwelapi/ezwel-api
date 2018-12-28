package com.ezwel.htl.interfaces.server.commons.utils;

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
	public double getPower(double value, double squareNum) {
		double out = Math.pow(value, squareNum);
		return out;
	}
	
	//SIN
	@APIOperation(description="오라클 SIN 대응 함수")
	public double getSin(double value) {
		double out = Math.sin(value);
		return out;
	}
	
	//COS
	@APIOperation(description="오라클 COS 대응 함수")
	public double getCos(double value) {
		double out = Math.cos(value);
		return out;
	}
	
	//ATAN2 결과 다름!!
	@APIOperation(description="오라클 ATAN2 대응 함수")
	public double getAtan2(double x, double y) {
		double out = Math.atan2(y, x);
		return out;
	}
	
	//SQRT
	@APIOperation(description="오라클 SQRT 대응 함수")
	public double getSqrt(double value) {
		double out = Math.sqrt(value);
		return out;
	}	
	
	//ROUND
	@APIOperation(description="오라클 ROUND 대응 함수")
	public double getRound(double value, double decimalPlaces) {
		double out = Math.round(value);
		return out;
	}	
}
