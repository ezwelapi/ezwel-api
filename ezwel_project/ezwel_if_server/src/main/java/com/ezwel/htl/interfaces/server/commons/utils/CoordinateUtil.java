package com.ezwel.htl.interfaces.server.commons.utils;

import java.math.BigDecimal;
import java.math.MathContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;

@Component
@APIType(description="좌표(위도/경도) 차이 계산 유틸")
public class CoordinateUtil {

	private static final Logger logger = LoggerFactory.getLogger(CoordinateUtil.class);
	
	private OracleFuncUtil oracleFuncUtil;
	
	@APIOperation
	public BigDecimal getCoordDiffer(BigDecimal calcFrontX, BigDecimal calcFrontY, BigDecimal calcBackendX, BigDecimal calcBackendY) {
		
		oracleFuncUtil = (OracleFuncUtil) LApplicationContext.getBean(oracleFuncUtil, OracleFuncUtil.class);
		
		BigDecimal out = OperateConstants.BIGDECIMAL_ZERO_VALUE;
		
		BigDecimal pLat1 = calcFrontX;
		BigDecimal pLon1 = calcFrontY;
		BigDecimal pLat2 = calcBackendX;
		BigDecimal pLon2 = calcBackendY;
		
		//cSpherRad CONSTANT NUMBER := 6367;
		final BigDecimal cSpherRad = new BigDecimal(6367);

		/*
		 * Most computers require the arguments of trigonometric functions to be
		 * expressed in radians. To convert lon1, lat1 and lon2,lat2 from
		 * degrees,minutes, seconds to radians, first convert them to decimal degrees.
		 * To convert decimal degrees to radians, multiply the number of degrees by
		 * pi/180 = 0.017453293 radians/degrees.
		 */
		
		BigDecimal multiplyValue = new BigDecimal(0.017453293);
		
		BigDecimal vLat1Rad = pLat1.multiply(multiplyValue);
		BigDecimal vLat2Rad = pLat2.multiply(multiplyValue);
		BigDecimal vLon1Rad = pLon1.multiply(multiplyValue);
		BigDecimal vLon2Rad = pLon2.multiply(multiplyValue);

		BigDecimal vLon = vLon2Rad.subtract(vLon1Rad);
		BigDecimal vLat = vLat2Rad.subtract(vLat1Rad);
		
		// a := POWER(SIN(vLat/2),2) + ((COS(vLat1Rad) * COS(vLat2Rad)) * POWER(SIN(vLon/2),2));
		BigDecimal a = oracleFuncUtil.getPower(
								oracleFuncUtil.getSin(vLat.divide(OperateConstants.BIGDECIMAL_TWO_VALUE, MathContext.DECIMAL32)), OperateConstants.BIGDECIMAL_TWO_VALUE
							).add(
								((oracleFuncUtil.getCos(vLat1Rad)).multiply(
										(oracleFuncUtil.getCos(vLat2Rad))
									)).multiply(
											oracleFuncUtil.getPower(
												oracleFuncUtil.getSin(vLon.divide(OperateConstants.BIGDECIMAL_TWO_VALUE, MathContext.DECIMAL32)), OperateConstants.BIGDECIMAL_TWO_VALUE
											)
									));

		
		/*
		 * The intermediate result c is the great circle distance in radians. Inverse
		 * trigonometric functions return results expressed in radians. To express c in
		 * decimal degrees, multiply the number of radians by 180/pi = 57.295780
		 * degrees/radian. The great circle distance d will be in the same units as r.
		 */

		//out = ROUND(cSpherRad * 2 * ATAN2(SQRT(a), SQRT(1-a)),1);
		out = oracleFuncUtil.getRound( cSpherRad.multiply(OperateConstants.BIGDECIMAL_TWO_VALUE).multiply((oracleFuncUtil.getAtan2( oracleFuncUtil.getSqrt(a), oracleFuncUtil.getSqrt(OperateConstants.BIGDECIMAL_ONE_VALUE.subtract(a) ) ))), OperateConstants.BIGDECIMAL_ONE_VALUE );
		
		return out;
	}



}
