package ezwel_if_server.test;

import java.math.BigDecimal;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.server.commons.utils.OracleFuncUtil;

public class OracleFuncTest {

	private static final Logger logger = LoggerFactory.getLogger(OracleFuncTest.class);
	
	OracleFuncUtil func = new OracleFuncUtil();
	
	@Test
	public void test() {
		
		logger.debug( "POWER : {}" , func.getPower(new BigDecimal(3), new BigDecimal(3)) );
		//27
		
		logger.debug( "SIN : {}" , func.getSin(new BigDecimal(3)) );
		//0.141120008059867
		
		logger.debug( "COS : {}" , func.getCos(new BigDecimal(3)) );
		//-0.989992496600445
		
		logger.debug( "ATAN2 : {}" , func.getAtan2(new BigDecimal(3), new BigDecimal(2)) );		
		//0.982793723247329
		
		logger.debug( "SQRT : {}" , func.getSqrt(new BigDecimal(12)) );
		//3.46410161513775
		
		logger.debug( "ROUND : {}" , func.getRound(new BigDecimal(12.523), new BigDecimal(1)) );
		//13
		
	}
}
