package com.ezwel.htl.interfaces.commons.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.utils.ezwelCrypt.EzwelCrypto;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@Component
@APIType(description="이지웰 계인정보 인/디코딩 유틸")
public class EzwelCryptoUtil {

	/**********************************************************
	 * 					   EZWEL Encode/Decode
	 **********************************************************/
	
	public String encode(String cryptoKey, String strEncode) throws IOException {
		return decode(cryptoKey, strEncode, OperateConstants.DEFAULT_ENCODING);
	}
	
	@SuppressWarnings("restriction")
	public String encode(String cryptoKey, String strEncode, String encoding) throws UnsupportedEncodingException {
		
		EzwelCrypto ezwelCrypto = null;
		BASE64Encoder encoder = null;
		String encryptText = null;
		
		try {
			
			ezwelCrypto = new EzwelCrypto();
			encoder = new BASE64Encoder();
			if(APIUtil.isNotEmpty(strEncode)) {
				encryptText = encoder.encode(ezwelCrypto.encrypt(strEncode, cryptoKey.getBytes(encoding), encoding));
			}
		}
		catch(UnsupportedEncodingException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9800, e);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9800, e);
		}
		
		return encryptText;
	}
	
	
	public String decode(String cryptoKey, String strDecode) throws IOException {
		return decode(cryptoKey, strDecode, OperateConstants.DEFAULT_ENCODING);
	}
	
	
	@SuppressWarnings("restriction")
	public String decode(String cryptoKey, String strDecode, String encoding) throws IOException {
		
		EzwelCrypto ezwelCrypto = null;
		BASE64Decoder decoder = null;
		String decryptText = null;
		byte[] encryptbytes = null;
		
		try {
			ezwelCrypto = new EzwelCrypto();
			decoder = new BASE64Decoder();
			
			if(APIUtil.isNotEmpty(strDecode)) {
				encryptbytes = decoder.decodeBuffer(strDecode);
				decryptText = ezwelCrypto.decryptAsString(encryptbytes, cryptoKey.getBytes(encoding), encoding);
			}
		}
		catch(IOException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9800, e);
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9800, e);
		}
		
		return decryptText;
	}
}
