package com.ezwel.htl.interfaces.commons.utils.crypt;

import org.apache.commons.codec.binary.Base64;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;

/**
* <p>Title: Base64Codec</p>
* <p>Description:
* Apache codec 의 base64 를 이용하여 문자열의 base64 인코딩,디코딩을 담당한다.
* </p>
* <p>Copyright: Copyright (c) 2011</p>
* <p>Company: mvc</p>
* @since 2011. 07. 04
* @author ksw
* @version 1.0
*/
@APIType(description="BASE64 EN/DE Coder")
public class Base64Codec {

	private static final Base64Codec INSTANCE = new Base64Codec();

	@APIOperation(description="Base64Codec 인스턴스를 리턴합니다.")
    public static Base64Codec getInstance()
    {
        return INSTANCE;
    }

	/**
	 * <pre>
	 * [메서드 설명]
	 * 	바인드된 일반 문자열을 Base64로 인코딩합니다.
	 * [사용방법 설명]
	 *  encode(문자열)
	 * </pre>
	 * 
	 * @param value
	 * @return
	 */
	@APIOperation(description="바인드된 일반 문자열을 Base64로 인코딩합니다.")
	public String encode(String value)
    {
        String result = "";
        try
        {
            byte plainText[] = null;
            plainText = value.getBytes();
            result = Base64.encodeBase64URLSafeString(plainText);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

	@APIOperation(description="Base64로 인코딩된 문자열을 decode합니다.")
    public String decode(String value)
    {
        String result = "";
        try
        {
        	Base64 decoder = new Base64();
            byte plainText[] = null;
            plainText = decoder.decode(value);
            result = new String(plainText);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }




}
