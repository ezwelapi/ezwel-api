package com.ezwel.htl.interfaces.commons.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.utils.crypt.Base64Codec;
import com.ezwel.htl.interfaces.commons.utils.crypt.MD5;
import com.ezwel.htl.interfaces.commons.utils.ezwelCrypt.EzwelCrypto;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@APIType(description="암호화/인코딩/디코딩 유틸")
public class CryptUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(CryptUtil.class);

	@APIOperation
	public static String getEncodeBase64(String word) {
		if(APIUtil.isEmpty(word)) {
			logger.warn("EncodeBase64 input parameter is empty or null...");
			return word;
		}
		
		String out = Base64Codec.getInstance().encode(word);
		return out;
	}
	
	@APIOperation
	public static String getDecodeBase64(String word) {
		if(APIUtil.isEmpty(word)) {
			logger.warn("DecodeBase64 input parameter is empty or null...");
			return word;
		}
		
		String out = Base64Codec.getInstance().decode(word);
		return out;
	}
	
	@APIOperation
	public static String getMD5HashString(String word) {
		if(APIUtil.isEmpty(word)) {
			logger.warn("MD5HashString input parameter is empty or null...");
			return word;
		}
		
		String out = MD5.getInstance().getHashString(word);
		return out;
	}
	
	@APIOperation
	public static byte[] getMD5Hash(String word) {
		if(APIUtil.isEmpty(word)) {
			logger.warn("MD5Hash input parameter is empty or null...");
			return null;
		}
		
		byte[] out = MD5.getInstance().getHash(word);
		return out;
	}
	
	@APIOperation
	public static byte[] getSHA256(String word) {
		if(APIUtil.isEmpty(word)) {
			logger.warn("SHA256 input parameter is empty or null...");
			return null;
		}
		
		byte[] out = DigestUtils.sha256(word);
		return out;
	}
	
	@APIOperation
	public static String getSHA256Hex(String word) {
		if(APIUtil.isEmpty(word)) {
			logger.warn("SHA256Hex input parameter is empty or null...");
			return word;
		}
		
		String out = DigestUtils.sha256Hex(word);
		return out;
	}
	
	@APIOperation
	public String ezwelCryptoEncode(String cryptoKey, String strEncode) {
		return ezwelCryptoEncode(cryptoKey, strEncode, OperateConstants.DEFAULT_ENCODING);
	}
	
	@APIOperation
	@SuppressWarnings("restriction")
	public String ezwelCryptoEncode(String cryptoKey, String strEncode, String encoding) {
		logger.debug("[START] ezwelCryptoEncode => cryptoKey : {}, strEncode : {}, encoding : {}", cryptoKey, strEncode, encoding);
		
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
		logger.debug("[END] ezwelCryptoEncode : {}", encryptText);
		return encryptText;
	}
	
	@APIOperation
	public String ezwelCryptoDecode(String cryptoKey, String strDecode) {
		return ezwelCryptoDecode(cryptoKey, strDecode, OperateConstants.DEFAULT_ENCODING);
	}
	
	@APIOperation
	@SuppressWarnings("restriction")
	public String ezwelCryptoDecode(String cryptoKey, String strDecode, String encoding) {
		logger.debug("[START] ezwelCryptoDecode => cryptoKey : {}, strEncode : {}, encoding : {}", cryptoKey, strDecode, encoding);
		
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
		
		logger.debug("[END] ezwelCryptoDecode : {}", decryptText);
		return decryptText;
	}
	
}
