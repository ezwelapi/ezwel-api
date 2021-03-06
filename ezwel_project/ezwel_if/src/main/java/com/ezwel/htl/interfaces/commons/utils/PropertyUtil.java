package com.ezwel.htl.interfaces.commons.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.exception.APIException;



/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 8.
 * @serviceType API
 */
@Component
@APIType(description="자바 프로퍼티 핸들링 유틸")
public class PropertyUtil {

	private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);
	
	private static final List<Class<?>> EXCLUDE_PROPERTY_TYPES;
	
	@Autowired
	private APIUtil apiUtil;
	
	static {
		EXCLUDE_PROPERTY_TYPES = new ArrayList<Class<?>>();
		EXCLUDE_PROPERTY_TYPES.add(java.lang.Class.class);
	}
	
	public PropertyUtil() {
		if(apiUtil == null) {
			apiUtil = new APIUtil();
		}
	}
	/**
	 * 주어진 bean 에 propertyName 이 존재한다면 propertyName 의 value 를 반환
	 * 업다면 null을 반환합니다.
	 * @param bean
	 * @param propertyName
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@APIOperation(description="주어진 bean 에 propertyName 이 존재한다면 propertyName 의 value 를 반환합니다.")
    public Object getProperty(Object bean, String getPropertyName) {
		if(bean == null) {
			logger.warn("Bean Object is null.");
			return null;
		}
		
        if(APIUtil.isEmpty(getPropertyName)) {
        	throw new APIException(" The propertyName was null or invalid. ");
        }
        
        try {
			if( PropertyUtils.getPropertyDescriptor(bean, getPropertyName) != null ) {
				return BeanUtils.getProperty(bean, getPropertyName);
			}
		} catch (IllegalAccessException e) {
			throw new APIException(e);
		} catch (InvocationTargetException e) {
			throw new APIException(e);
		} catch (NoSuchMethodException e) {
			throw new APIException(e);
		}
        
       	logger.info(APIUtil.addString("[WARN] The property does not exist [bean : ", bean.getClass().getCanonicalName(), ", getPropertyName : ", getPropertyName, "]"));
        return null;
    }

	
	/**
	 * 주어진 bean에 바인드된 필드들의 값을 null로 세팅합니다.
	 * @param bean
	 * @param propertyNames
	 * @return
	 */
	@APIOperation(description="주어진 bean에 바인드된 필드들의 값을 null로 세팅합니다.")
	public int removeFieldData(Object bean, String... propertyNames) {
		int count = 0;
		
		if(propertyNames != null) {
			for(String propertyName : propertyNames) {
				if(setProperty(bean, propertyName, null)) {
					count++;
				}
			}
		}
		
		return count;
	}
	
	
	@APIOperation(description="주어진 bean에 바인드된 기본 공통필드들의 값을 null로 세팅합니다.")
	public int removeDefaulFieldData(Object bean) {
		return removeFieldData(bean, "regId", "regDt", "modiId", "modiDt");
	}
	
    /**
     * 주어진 bean 에 propertyName 이 존재한다면 propertyName 에 result 를 setting
     * 결과 true/false 를 반환합니다.
     * @param bean
     * @param propertyName
     * @param result
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
	@APIOperation(description="주어진 bean에 propertyName이 존재한다면 propertyName 에 result 를 setting합니다.")
    public boolean setProperty(Object bean, String propertyName, Object setValue) {
		if(bean == null) {
			logger.warn("bean Object is null.");
			return false;
		}
		
        if(APIUtil.isEmpty(propertyName)) {
        	throw new APIException(" The propertyName was null or invalid. propertyName : {}", propertyName) ;
        }        

        Field setField = null;
        try {
        	
        	setField = apiUtil.getField(bean.getClass(), propertyName);
        	
			if( PropertyUtils.getPropertyDescriptor(bean, propertyName) != null ) {
				BeanUtils.setProperty(bean, propertyName, setValue);
				return true;
			}
			
		} catch (IllegalAccessException e) {
			if(setField != null) {
				logger.error("[IllegalAccessException] Setup FieldInfo type : {}, name : {}, setting parameter type : {}", setField.getType(), setField.getName(), (setValue != null ? setValue.getClass().getCanonicalName() : null));
			}
			throw new APIException(e);
		} catch (InvocationTargetException e) {
			if(setField != null) {
				logger.error("[InvocationTargetException] Setup FieldInfo type : {}, name : {}, setting parameter type : {}", setField.getType(), setField.getName(), (setValue != null ? setValue.getClass().getCanonicalName() : null));
			}
			throw new APIException(e);
		} catch (NoSuchMethodException e) {
			if(setField != null) {
				logger.error("[NoSuchMethodException] Setup FieldInfo type : {}, name : {}, setting parameter type : {}", setField.getType(), setField.getName(), (setValue != null ? setValue.getClass().getCanonicalName() : null));
			}
			throw new APIException(e);
		} catch (Exception e) {
			if(setField != null) {
				logger.error("[Exception] Setup FieldInfo type : {}, name : {}, setting parameter type : {}", setField.getType(), setField.getName(), (setValue != null ? setValue.getClass().getCanonicalName() : null));
			}
			throw new APIException(e);
		}
        
       	logger.info(APIUtil.addString("[WARN] The property does not exist [bean : ", bean.getClass().getCanonicalName(), ", property : ", propertyName, "]"));
        return false;
    }
    
	@APIOperation(description="readBean의 프로퍼티 명과 이름이 일치하는 writeBean의 프로퍼티에 값을 담아줍니다.")
    public Object copySameProperty(Object readBean, Class<?> writeBean) {
    	return copySameProperty(readBean, writeBean, new String[] {});
    }
	
	@APIOperation(description="readBean의 프로퍼티 명과 이름이 일치하는 writeBean의 프로퍼티에 값을 담아줍니다.")
    public Object copySameProperty(Object readBean, Class<?> writeBean, String... excludeFields) {
		Object out = null;
    	try {
    		out = copySameProperty(readBean, writeBean.newInstance(), excludeFields, false);
		} catch (InstantiationException e) {
			throw new APIException(e);
		} catch (IllegalAccessException e) {
			throw new APIException(e);
		}
    	return out;
    }
	
	@APIOperation(description="readBean의 프로퍼티 명과 이름이 일치하는 writeBean의 프로퍼티에 값을 담아줍니다.")
    public Object copySameProperty(Object readBean, Class<?> writeBean, boolean isLogging) {
		Object out = null;
    	try {
    		out = copySameProperty(readBean, writeBean.newInstance(), new String[] {}, isLogging);
		} catch (InstantiationException e) {
			throw new APIException(e);
		} catch (IllegalAccessException e) {
			throw new APIException(e);
		}
    	return out;
    }
	
	@APIOperation(description="readBean의 프로퍼티 명과 이름이 일치하는 writeBean의 프로퍼티에 값을 담아줍니다.")
    public boolean copySameProperty(Object readBean, Object writeBean) {
		Object out = copySameProperty(readBean, writeBean, new String[] {}, false);
    	return (out != null ? true : false);
    }
	
	
	@APIOperation(description="readBean의 프로퍼티 명과 이름이 일치하는 writeBean의 프로퍼티에 값을 담아줍니다.")
    public boolean copySameProperty(Object readBean, Object writeBean, boolean isLogging) {
		Object out = copySameProperty(readBean, writeBean, new String[] {}, isLogging);
    	return (out != null ? true : false);
	}

    
	@APIOperation(description="readBean의 프로퍼티 명과 이름이 일치하는 writeBean의 프로퍼티에 값을 담아줍니다.")
    public boolean copySameProperty(Object readBean, Object writeBean, String... excludeFields) {
		Object out = copySameProperty(readBean, writeBean, excludeFields, false);
    	return (out != null ? true : false);
    }
	
    /**
     * readBean의 프로퍼티 명과 이름이 일치하는 writeBean의 프로퍼티에 값을 담아줌. 
     * @param readBean
     * @param writeBean
     * @param copyTarget
     * @return
     */
	@APIOperation(description="readBean의 프로퍼티 명과 이름이 일치하는 writeBean의 프로퍼티에 값을 담아줍니다.(세팅 로깅여부를 설정합니다)")
    public Object copySameProperty(Object readBean, Object writeBean, String[] excludeFields, boolean isLogging){
    	
    	String readName = null;
    	Class<?> readType = null;
    	Object readValue = null;
    	List<String> excludeList = null;
    	try {
    		PropertyDescriptor[] readProperties  = PropertyUtils.getPropertyDescriptors(readBean);
    		PropertyDescriptor[] writeProperties = PropertyUtils.getPropertyDescriptors(writeBean);
    		
    		if(excludeFields != null && excludeFields.length > 0) {
    			excludeList = Arrays.asList(excludeFields);
    		}
    		
    		for (PropertyDescriptor read : readProperties) {
    			readName = read.getName();
    			readType = read.getPropertyType();
    			
    			if(excludeList != null && excludeList.contains(readName)) {
    				continue;
    			}
    			
    			if(EXCLUDE_PROPERTY_TYPES.contains(readType)) {
    				continue;
    			}
    			
    			for (PropertyDescriptor write : writeProperties) {
    			
    				if(readName.equals(write.getName()) && readType.isAssignableFrom(write.getPropertyType())) {
    					
    					readValue = BeanUtils.getProperty(readBean, readName);
    					//readValue = getProperty(readBean, readName);
    					
    					if(isLogging) {
	    					logger.debug(new StringBuilder().append("\n [Copy Property Infomation]")
	    						.append("\n readName : ").append(readName)
	    						.append("\n readValue : ").append(readValue)
	    						.append("\n readType : ").append(readType.getCanonicalName()) 
	    						.append("\n isInterface : ").append(readType.isInterface())
	    						.append("\n isAnnotation : ").append(readType.isAnnotation())
	    						.append("\n isArray : ").append(readType.isArray())
	    						.append("\n isEnum : ").append(readType.isEnum())
	    						.append("\n").toString()
	    					);
    					}
    					
    					if( !Map.class.isAssignableFrom(readType) && readValue != null ) {
    						BeanUtils.setProperty(writeBean, readName, readValue);
    						//setProperty(writeBean, readName, readValue);
    					}
    					break;
    				}
    			}
    		}
    		
		} catch (IllegalAccessException e) {
			throw new APIException(e);
		} catch (InvocationTargetException e) {
			throw new APIException(e);
		} catch (NoSuchMethodException e) {
			throw new APIException(e);
		}

    	return writeBean;
    }
    
}
