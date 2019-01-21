package com.ezwel.htl.interfaces.server.commons.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.server.commons.utils.crypto.Crypto;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@Component
@APIType(description = "crypto en/de")
public class CryptoUtil {

	private static final Logger logger = LoggerFactory.getLogger(CryptoUtil.class);
	
	@APIOperation
	public String encode(String cryptoKey, String strEncode) {
		return encode(cryptoKey, strEncode, OperateConstants.DEFAULT_ENCODING);
	}
	
	@APIOperation
	@SuppressWarnings("restriction")
	public String encode(String cryptoKey, String strEncode, String encoding) {
		logger.debug("[START] encode => cryptoKey : {}, strEncode : {}, encoding : {}", cryptoKey, strEncode, encoding);
		
		Crypto ezwelCrypto = null;
		BASE64Encoder encoder = null;
		String encryptText = null;
		
		try {
			
			ezwelCrypto = new Crypto();
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
	
	@APIOperation
	public String decode(String cryptoKey, String strDecode) {
		return decode(cryptoKey, strDecode, OperateConstants.DEFAULT_ENCODING);
	}
	
	@APIOperation
	@SuppressWarnings("restriction")
	public String decode(String cryptoKey, String strDecode, String encoding) {
		logger.debug("[START] decode => cryptoKey : {}, strEncode : {}, encoding : {}", cryptoKey, strDecode, encoding);
		
		Crypto ezwelCrypto = null;
		BASE64Decoder decoder = null;
		String decryptText = null;
		byte[] encryptbytes = null;
		
		try {
			
			ezwelCrypto = new Crypto();
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
	
	@APIOperation
	public <T extends AbstractSDO> void encodeAPIModel(String cryptKey, List<T> inSDO) {
		if(inSDO != null && inSDO.size() > 0) {
			for(T apiModel : inSDO) {
				encodeAPIModel(cryptKey, apiModel);
			}
		}
	}
	
	@APIOperation
	public <T extends AbstractSDO> void encodeAPIModel(String cryptKey, T inSDO) {
		
		Object value = null;
		Field[] apiFields = null;
		APIFields fieldAnno = null;
		
		try {
		
			apiFields = inSDO.getClass().getDeclaredFields();
			if(apiFields != null) {
				
				for(Field apiField : apiFields) {
					fieldAnno = apiField.getAnnotation(APIFields.class);
					if(fieldAnno != null && fieldAnno.isEzwelcrypto()) {
						apiField.setAccessible(true);
						value = apiField.get(inSDO);
						if(value != null && String.class.isAssignableFrom(value.getClass())) {
							apiField.set(inSDO, encode(cryptKey, (String) value));  
						}
					}
				}
			}
		}
		catch(Exception e) {
			throw new APIException();
		}
		
	}
	
}
