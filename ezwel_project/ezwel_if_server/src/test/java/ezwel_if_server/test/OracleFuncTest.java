package ezwel_if_server.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.server.commons.utils.OracleFuncUtil;

public class OracleFuncTest {

	private static final Logger logger = LoggerFactory.getLogger(OracleFuncTest.class);
	
	OracleFuncUtil func = new OracleFuncUtil();
	
	@Test
	public void test() {
		
		logger.debug( "POWER : {}" , func.getPower(3D, 3D) );
		//27
		
		logger.debug( "SIN : {}" , func.getSin(3D) );
		//0.141120008059867
		
		logger.debug( "COS : {}" , func.getCos(3D) );
		//-0.989992496600445
		
		logger.debug( "ATAN2 : {}" , func.getAtan2(3D, 2D) );		
		//0.982793723247329
		
		logger.debug( "SQRT : {}" , func.getSqrt(12D) );
		//3.46410161513775
		
		logger.debug( "ROUND : {}" , func.getRound(12.523D) );
		//13
	}
}
