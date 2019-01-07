package com.ezwel.htl.interfaces.server.commons.utils;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;

@APIType(description="로케일 핸들링 유틸")
public class LocaleUtil {

	private static final Logger logger = LoggerFactory.getLogger(LocaleUtil.class);
	
	private static final String DEFAULT_USER_LOCALE = OperateConstants.SYSTEM_LOCALE;
	
	private static Map<String, Locale> availableLocales = null;
	
	private static final boolean IS_LOCALE_LOG = false;
	
	public static Locale getSystemLocale() {
		return parseStrict(null, false);
	}
	
	public static Locale parseStrict(String str) {
		return parseStrict(str, false);
	}
	
	public static Locale parseStrict(String str, boolean newLocale) {
		
		Locale out = null;
    	String localeStr = null;
    	String language = null;
        String country = null;
        String variant = null;
        
    	if(APIUtil.isNotEmpty(str)) {
    		localeStr = str;
    	} else {
    		localeStr = DEFAULT_USER_LOCALE;
    	}
    	
        int underBarIndex = localeStr.indexOf(OperateConstants.STR_UNDERBAR);
        int dotIndex = localeStr.indexOf(OperateConstants.STR_DOT);
    	int underbarLenght = OperateConstants.STR_UNDERBAR.length();
    	int dotLenght = OperateConstants.STR_DOT.length();
    	
        if(dotIndex > -1 && underBarIndex > dotIndex) {
        	if(logger.isDebugEnabled()) {
        		logger.debug(APIUtil.addString(new Object[]{" underBarIndex : " , underBarIndex , ", dotIndex : " , dotIndex}) );
        	}
    		throw new RuntimeException(APIUtil.addString(localeStr , " is Invalid locale pattern. "));
    	}
        else if(underBarIndex == -1 && dotIndex == -1) {
        	language = localeStr;
        	country = "";
        	variant = "";
        }
        else if(underBarIndex > -1 && dotIndex > -1) {
        	language = localeStr.substring(0, underBarIndex);
        	country = localeStr.substring(underBarIndex + underbarLenght, dotIndex);
        	variant = localeStr.substring(dotIndex + dotLenght);
        }
        else if(underBarIndex > -1) {
        	language = localeStr.substring(0, underBarIndex);
        	country = localeStr.substring(underBarIndex + underbarLenght);
        	variant = "";
        }
        else if(dotIndex > -1) {
        	language = localeStr.substring(0, dotIndex);
        	country = "";
        	variant = localeStr.substring(dotIndex + dotLenght);
        }
        
        if(logger.isDebugEnabled() && IS_LOCALE_LOG) {
        	logger.debug(APIUtil.addString("language : " , language , ", country : " , country , ", variant : " , variant));
        }
       	
        if( newLocale ) {
        
        	if(getAvailableLocale(language) != null) {
            	out = new Locale(language, country, variant);
            }
        }
        else {
        	
        	if(APIUtil.isNotEmpty(language)) {
            	if(APIUtil.isNotEmpty(country)) {
            		language = language.concat(OperateConstants.STR_UNDERBAR).concat(country);
            	}
            	
            	out = getAvailableLocale(language);
            }
        }
        
        if(out == null) {
        	throw new RuntimeException(APIUtil.addString("사용자 언어가 잘못되거나 지원되지 않는 언어입니다. locale : ", localeStr));
        }
        
		return out;
	}
	
	   /**
     * 지원 가능한 언어인지 여부를 JAVA의 Available Locales 에서 확인하여 줍니다.
     * @param language
     * @return
     */
    public static Locale getAvailableLocale(String language){

    	Locale out = getLocaleMap().get(language);
    	
    	return out;
    }
    
    /**
     * 캐쉬 해놓은 Available Locales Map 을 리턴하거나 캐쉬 정보가 없으면 캐쉬하여 데이터 를 리턴합니다.
     * @return
     */
    public static Map<String, Locale> getLocaleMap(){
    	
    	if(availableLocales != null) {
    		if(logger.isDebugEnabled()) {
    			if(IS_LOCALE_LOG) {
    				logger.debug(APIUtil.addString("Available Locale Data load to cashed ... "));
    			}
    		}
    		return availableLocales;
    	}
    	else {
    		if(logger.isDebugEnabled()) {
    			logger.debug(APIUtil.addString("Available Locale Data load to execute saveAvailableLocales... "));
    		}
   			return saveAvailableLocales();
    	}
    }
    

    /**
     * availableLocales 이 null 일경우 Java 에서 지원하는 모든 Locale 정보를 availableLocales (static map) 에 담아줍니다. 
     */
    public static Map<String, Locale> saveAvailableLocales() {
    	
    	try {
    		availableLocales = new HashMap<String, Locale>();

        	Locale[] list = DateFormat.getAvailableLocales();

    		for (int i = 0; i < list.length; i++) {
    			availableLocales.put(list[i].toString(), list[i]);
    			//if(logger.isDebugEnabled()) {
    			//	logger.debug("support locale : {}", list[i]);
    			//}
    		}	
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return availableLocales;
    }
    
}
