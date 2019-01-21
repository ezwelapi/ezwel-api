package ezwel_if_server.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

import ezwelcrypto.EzwelCrypto;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;



public class EzwelCryptoTest{
	
	//TEST대칭키 입니다. EZWEL과 연동 전 EZWEL에 문의하여 대칭키를 받으셔야 합니다.(16자리 고정)
	private String key = "EzwelCrytpoTest1";
	
	
	private String getKey() {
		return key;
	}
	private void setKey(String cspCd){

		this.key = cspCd;
	}
	
	public String encode(String strEncode) throws UnsupportedEncodingException {
		
		
		EzwelCrypto ezwelCrypto = new EzwelCrypto();
		BASE64Encoder encoder = new BASE64Encoder();
		
		String encryptText = "";

		if(!strEncode.equals("") && strEncode != null){
			encryptText = encoder.encode(ezwelCrypto.encrypt(strEncode, getKey().getBytes(), "UTF-8"));
		}
		return encryptText;
	}
	public String decode(String strDecode) throws IOException{
		
		EzwelCrypto ezwelCrypto = new EzwelCrypto();
		BASE64Decoder decoder = new BASE64Decoder();
		
		String decryptText = "";
			if(!strDecode.equals("") && strDecode != null){
				byte[] encryptbytes = decoder.decodeBuffer(strDecode);
				decryptText = ezwelCrypto.decryptAsString(encryptbytes, getKey().getBytes(), "UTF-8");
			}
		
		return decryptText;
	}
	
	
	
	@Test
	public void main() throws Exception {
		String text = "암호화 Test=====";
		System.out.println("평문 : "+text);
	
		EzwelCryptoTest ezwelCryptoTest = new EzwelCryptoTest();

		String encryptText = ezwelCryptoTest.encode(text);
		System.out.println("암호화 : "+encryptText);

		
		String decryptText = ezwelCryptoTest.decode(encryptText);
		System.out.println("복호화 : "+decryptText);

	}
}