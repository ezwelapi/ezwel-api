package com.ezwel.htl.interfaces.server.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;

@Component
@APIType(description="좌표(위도/경도) 차이 계산 유틸")
public class CoordinateUtil {

	private static final Logger logger = LoggerFactory.getLogger(CoordinateUtil.class);
	
	private OracleFuncUtil oracleFuncUtil;
	
	public final static int CALC_UNIT_MILE;
	public final static int CALC_UNIT_METER;
	public final static int CALC_UNIT_KILOMETER;
	
	static {
		
		CALC_UNIT_MILE = 0;			// CoordinateUtil.CALC_UNIT_MILE
		CALC_UNIT_METER = 1;			// CoordinateUtil.CALC_UNIT_METER
		CALC_UNIT_KILOMETER = 3;	// CoordinateUtil.CALC_UNIT_KILOMETER
	}
	
	public CoordinateUtil() {
		if(oracleFuncUtil == null) {
			oracleFuncUtil = new OracleFuncUtil();
		}
	}
	
	/**
	 * 두 좌표간의 거리(METER) 계산
	 * @param latY1
	 * @param lonX1
	 * @param latY2
	 * @param lonX2
	 * @return
	 */
	@APIOperation(description="두 좌표간의 거리(METER) 계산")
	public double getCoordDistance(double latY1, double lonX1, double latY2, double lonX2) {
		return getCoordDistance(latY1, lonX1, latY2, lonX2, CoordinateUtil.CALC_UNIT_METER);
	}
	
    /**
     * 두 좌표간의 거리(MILE/METER/KILOMETER) 계산합니다
     * @param latY1 지점 1 위도
     * @param lonX1 지점 1 경도
     * @param latY2 지점 2 위도
     * @param lonX2 지점 2 경도
     * @param unit 거리 표출단위
     * @return
     */
	@APIOperation(description="두 좌표간의 거리(MILE/METER/KILOMETER) 계산합니다")
    public double getCoordDistance(double latY1, double lonX1, double latY2, double lonX2, int unit) {
        
		double out = 0.0;
		
		try {
			
	        double theta = lonX1 - lonX2;
	        double dist = Math.sin(deg2rad(latY1)) * Math.sin(deg2rad(latY2)) + Math.cos(deg2rad(latY1)) * Math.cos(deg2rad(latY2)) * Math.cos(deg2rad(theta));
	         
	        dist = Math.acos(dist);
	        dist = rad2deg(dist);
	        dist = dist * 60 * 1.1515;
	         
	        if (unit == CoordinateUtil.CALC_UNIT_KILOMETER) {
	            dist = dist * 1.609344;
	        } else if(unit == CoordinateUtil.CALC_UNIT_METER) {
	            dist = dist * 1609.344;
	        }
	 
	        out = oracleFuncUtil.getRound(dist, 2);
		}
		catch(Exception e) {
			logger.error("두 지점(좌표)간의 거리 계산 문제 발생 : {}", e.getMessage());
		}
        
        return out;
    }
     
 
    /**
     * 십진수도를 라디안으로 변환합니다.
     * @param deg
     * @return
     */
	@APIOperation(description="십진수를 라디안으로 변환합니다.")
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
     
    /**
     * 라디안을 십진수로 변환합니다.
     * @param rad
     * @return
     */
    @APIOperation(description="라디안을 십진수로 변환합니다.")
    private double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

}
