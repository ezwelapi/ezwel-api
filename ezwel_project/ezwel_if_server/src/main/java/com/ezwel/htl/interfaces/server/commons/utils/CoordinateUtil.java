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
	
	public double getCoordDistance(double latY1, double lonX1, double latY2, double lonX2) {
		return getCoordDistance(latY1, lonX1, latY2, lonX2, CoordinateUtil.CALC_UNIT_METER);
	}
	
    /**
     * 두 지점(좌표)간의 거리 계산
     *
     * @param latY1 지점 1 위도
     * @param lonX1 지점 1 경도
     * @param latY2 지점 2 위도
     * @param lonX2 지점 2 경도
     * @param unit 거리 표출단위
     * @return
     */
	@APIOperation
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
     
 
    // This function converts decimal degrees to radians
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
     
    // This function converts radians to decimal degrees
    private double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

}
