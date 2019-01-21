package ezwel_if_server.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.server.commons.utils.CryptoUtil;

public class EzwelCryptTest {

	private static final Logger logger = LoggerFactory.getLogger(EzwelCryptTest.class);
	
	private static int GetB0(int A) {
		return A & 0x7FFFFFFF;
	}
	
	@Test
	public void test() {
		
		CryptoUtil cryptoUtil = new CryptoUtil();
		
		logger.debug("GetB0 : {}", GetB0(-999999));
		
		// 1. 콘도 24
		String key = "EzwelCrytpoTest1";
		String testVal =  "암호화 Test=====";
		
		logger.debug("# key : {}, testVal : {}", key, testVal);

		//decompile
		String enTestVal = cryptoUtil.encode(key, testVal);
		logger.debug("# encode enTestVal : {}", enTestVal);
		
		String deTestVal = cryptoUtil.decode(key, enTestVal);
		logger.debug("# decode deTestVal : {}", deTestVal);
	
	}
}
