package com.ezwel.htl.interfaces.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.utils.crypt.Base64Codec;
import com.ezwel.htl.interfaces.commons.utils.crypt.MD5;

@APIType(description="암호화/인코딩/디코딩 유틸")
public class CryptUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(CryptUtil.class);

	public static String getEncodeBase64(String word) {
		String out = Base64Codec.getInstance().encode(word);
		return out;
	}
	
	public static String getDecodeBase64(String word) {
		String out = Base64Codec.getInstance().decode(word);
		return out;
	}
	
	public static String getMD5HashString(String word) {
		String out = MD5.getInstance().getHashString(word);
		return out;
	}
	
	public static byte[] getMD5Hash(String word) {
		byte[] out = MD5.getInstance().getHash(word);
		return out;
	}
	
	
	
}
