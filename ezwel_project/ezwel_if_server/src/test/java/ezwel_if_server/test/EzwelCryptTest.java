package ezwel_if_server.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.CryptUtil;

import ezwelcrypto.EzwelCrypto;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EzwelCryptTest {

	private static final Logger logger = LoggerFactory.getLogger(EzwelCryptTest.class);
	
	private static int GetB0(int A) {
		return A & 0x7FFFFFFF;
	}
	
	@Test
	public void test() {
		
		CryptUtil cryptUtil = new CryptUtil();
		
		logger.debug("GetB0 : {}", GetB0(-999999));
		
		// 1. 콘도 24
		String key = "EzwelCrytpoTest1";
		String testVal =  "암호화 Test=====";
		
		logger.debug("# key : {}, testVal : {}", key, testVal);

		String enTestVal = encode(key, testVal);
		logger.debug("# enTestVal : {}", enTestVal);
		
		String deTestVal = decode(key, enTestVal);
		logger.debug("# deTestVal : {}", deTestVal);
		
		//decompile
		enTestVal = cryptUtil.ezwelCryptoEncode(key, testVal);
		logger.debug("# ezwelCryptoEncode enTestVal : {}", enTestVal);
		
		deTestVal = cryptUtil.ezwelCryptoDecode(key, enTestVal);
		logger.debug("# ezwelCryptoDecode deTestVal : {}", deTestVal);
		
		
	
	}

	

	/**********************************************************
	 * 					   EZWEL Encode/Decode
	 **********************************************************/
	
	public String encode(String cryptoKey, String strEncode) {
		return encode(cryptoKey, strEncode, OperateConstants.DEFAULT_ENCODING);
	}
	
	@SuppressWarnings("restriction")
	public String encode(String cryptoKey, String strEncode, String encoding) {
		logger.debug("[START] encode => cryptoKey : {}, strEncode : {}, encoding : {}", cryptoKey, strEncode, encoding);
		
		EzwelCrypto ezwelCrypto = null;
		BASE64Encoder encoder = null;
		String encryptText = null;
		
		try {
			
			ezwelCrypto = new EzwelCrypto();
			encoder = new BASE64Encoder();
			if(APIUtil.isNotEmpty(cryptoKey) && APIUtil.isNotEmpty(strEncode) && APIUtil.isNotEmpty(encoding)) {
				encryptText = encoder.encode(ezwelCrypto.encrypt(strEncode, cryptoKey.getBytes(), encoding));
			}
		}
		catch(UnsupportedEncodingException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9800, e);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9800, e);
		}
		logger.debug("[END] encode : {}", encryptText);
		return encryptText;
	}
	
	
	public String decode(String cryptoKey, String strDecode) {
		return decode(cryptoKey, strDecode, OperateConstants.DEFAULT_ENCODING);
	}
	
	
	@SuppressWarnings("restriction")
	public String decode(String cryptoKey, String strDecode, String encoding) {
		logger.debug("[START] decode => cryptoKey : {}, strEncode : {}, encoding : {}", cryptoKey, strDecode, encoding);
		
		EzwelCrypto ezwelCrypto = null;
		BASE64Decoder decoder = null;
		String decryptText = null;
		byte[] encryptbytes = null;
		
		try {
			ezwelCrypto = new EzwelCrypto();
			decoder = new BASE64Decoder();
			
			if(APIUtil.isNotEmpty(cryptoKey) && APIUtil.isNotEmpty(strDecode) && APIUtil.isNotEmpty(encoding)) {
				encryptbytes = decoder.decodeBuffer(strDecode);
				decryptText = ezwelCrypto.decryptAsString(encryptbytes, cryptoKey.getBytes(), encoding);
			}
		}
		catch(IOException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9800, e);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9800, e);
		}
		
		logger.debug("[END] decode : {}", decryptText);
		return decryptText;
	}
}
