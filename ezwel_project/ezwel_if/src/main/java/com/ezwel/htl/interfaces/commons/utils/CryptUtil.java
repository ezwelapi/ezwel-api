package com.ezwel.htl.interfaces.commons.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.utils.crypt.Base64Codec;
import com.ezwel.htl.interfaces.commons.utils.crypt.MD5;

@APIType(description="암호화/인코딩/디코딩 유틸")
public class CryptUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(CryptUtil.class);

	@APIOperation
	public static String getEncodeBase64(String word) {
		if(APIUtil.isEmpty(word)) {
			logger.warn("EncodeBase64 input parameter is empty or null...");
			return word;
		}
		
		return Base64Codec.getInstance().encode(word);
	}
	
	@APIOperation
	public static String getDecodeBase64(String word) {
		if(APIUtil.isEmpty(word)) {
			logger.warn("DecodeBase64 input parameter is empty or null...");
			return word;
		}
		
		return Base64Codec.getInstance().decode(word);
	}
	
	@APIOperation
	public static String getMD5HashString(String word) {
		if(APIUtil.isEmpty(word)) {
			logger.warn("MD5HashString input parameter is empty or null...");
			return word;
		}
		
		return MD5.getInstance().getHashString(word);
	}
	
	@APIOperation
	public static byte[] getMD5Hash(String word) {
		if(APIUtil.isEmpty(word)) {
			logger.warn("MD5Hash input parameter is empty or null...");
			return null;
		}
		
		return MD5.getInstance().getHash(word);
	}
	
	@APIOperation
	public static byte[] getSHA256(String word) {
		if(APIUtil.isEmpty(word)) {
			logger.warn("SHA256 input parameter is empty or null...");
			return null;
		}
		
		return DigestUtils.sha256(word);
	}
	
	@APIOperation
	public static String getSHA256Hex(String word) {
		if(APIUtil.isEmpty(word)) {
			logger.warn("SHA256Hex input parameter is empty or null...");
			return word;
		}
		
		return DigestUtils.sha256Hex(word);
	}
}
