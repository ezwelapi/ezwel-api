package com.ezwel.htl.interfaces.server.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;

/**
 * <pre>
 * 유니코드 문자열 encode/decode 유틸
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 12. 11.
 */
public class UnicodeUtil {

	private static final Logger logger = LoggerFactory.getLogger(UnicodeUtil.class);

	@APIOperation(description="문자열을 Unicode로 encoding")
    public static String encode(String unicode)throws Exception {
        StringBuffer str = new StringBuffer();

        for (int i = 0; i < unicode.length(); i++) {
            if(((int) unicode.charAt(i) == 32)) {
                str.append(" ");
                continue;
            }
            str.append("\\u");
            str.append(Integer.toHexString((int) unicode.charAt(i)));
        }

        return str.toString();
    }


	@APIOperation(description="Unicode를 일반 문자열로 decoding")
    public static String decode(String unicode)throws Exception {
        StringBuffer str = new StringBuffer();

        char ch = 0;
        for( int i= unicode.indexOf("\\u"); i > -1; i = unicode.indexOf("\\u") ){
            ch = (char)Integer.parseInt( unicode.substring( i + 2, i + 6 ) ,16);
            str.append( unicode.substring(0, i) );
            str.append( String.valueOf(ch) );
            unicode = unicode.substring(i + 6);
        }
        str.append( unicode );

        return str.toString();
    }
	
}
