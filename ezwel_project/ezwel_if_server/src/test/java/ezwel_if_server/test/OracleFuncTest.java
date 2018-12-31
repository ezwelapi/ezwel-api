package ezwel_if_server.test;

import java.math.BigDecimal;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.server.commons.utils.CoordinateUtil;
import com.ezwel.htl.interfaces.server.commons.utils.OracleFuncUtil;

public class OracleFuncTest {

	private static final Logger logger = LoggerFactory.getLogger(OracleFuncTest.class);
	
	OracleFuncUtil func = new OracleFuncUtil();
	CoordinateUtil coord = new CoordinateUtil();
	
	@Test
	public void test() {
		
		logger.debug( "POWER : {}" , func.getPower(3D, 3D) );
		//27
		
		logger.debug( "SIN : {}" , func.getSin(3D) );
		//0.141120008059867
		
		logger.debug( "COS : {}" , func.getCos(3D) );
		//-0.989992496600445
		
		logger.debug( "ATAN2 - 1: {}" , func.getAtan2(3D, 2D) );		
		//0.982793723247329

		logger.debug( "SQRT : {}" , func.getSqrt(12D) );
		//3.46410161513775
		
		logger.debug( "ROUND : {}" , func.getRound(12.553D, 1) );
		//12.6

		/*
		 * distanceMile : 0.6038846790309288 
		 * distanceMeter : 971.8581848903511
		 * distanceKiloMeter : 0.9718581848903511
		 */
		
		// 마일(Mile) 단위
		logger.debug( "distanceMile : {}", coord.getCoordDistance(37.504198, 127.047967, 37.501025, 127.037701, CoordinateUtil.CALC_UNIT_MILE));
		// 미터(Meter) 단위
		logger.debug( "distanceMeter : {}", coord.getCoordDistance(37.504198, 127.047967, 37.501025, 127.037701, CoordinateUtil.CALC_UNIT_METER));
		// 킬로미터(Kilo Meter) 단위
		logger.debug( "distanceKiloMeter : {}", coord.getCoordDistance(37.504198, 127.047967, 37.501025, 127.037701, CoordinateUtil.CALC_UNIT_KILOMETER));
		
		BigDecimal testVal = new BigDecimal(99.782D);
		
		logger.debug("testVal : {}", testVal);
		
		logger.debug("testVal : {}", new BigDecimal(Double.toString(99.782D)));
	}
}
