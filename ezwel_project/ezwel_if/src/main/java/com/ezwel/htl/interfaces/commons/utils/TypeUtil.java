package com.ezwel.htl.interfaces.commons.utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;

@Component
public class TypeUtil {

	private static final Logger logger = LoggerFactory.getLogger(RegexUtil.class);
	
	private ConvertUtilsBean coverter = BeanUtilsBean.getInstance().getConvertUtils();

	private static Set<Class<?>> primitiveWrapper = null;
	
	private static Map<String, String> dataBaseType4j = new HashMap<String, String>();
	
	static {
		
		primitiveWrapper = new HashSet<Class<?>>();
		primitiveWrapper.add(java.math.BigDecimal.class);
		primitiveWrapper.add(java.math.BigInteger.class);
		primitiveWrapper.add(Boolean.TYPE);
		primitiveWrapper.add(java.lang.Boolean.class);
		primitiveWrapper.add(Byte.TYPE);
		primitiveWrapper.add(java.lang.Byte.class);
		primitiveWrapper.add(Character.TYPE);
		primitiveWrapper.add(java.lang.Character.class);
		primitiveWrapper.add(java.lang.Class.class);
		primitiveWrapper.add(Double.TYPE);
		primitiveWrapper.add(java.lang.Double.class);
		primitiveWrapper.add(Float.TYPE);
		primitiveWrapper.add(java.lang.Float.class);
		primitiveWrapper.add(Integer.TYPE);
		primitiveWrapper.add(java.lang.Integer.class);
		primitiveWrapper.add(Long.TYPE);
		primitiveWrapper.add(java.lang.Long.class);
		primitiveWrapper.add(Short.TYPE);
		primitiveWrapper.add(java.lang.Short.class);
		primitiveWrapper.add(java.lang.String.class);
		primitiveWrapper.add(java.sql.Date.class);
		primitiveWrapper.add(java.sql.Time.class);
		primitiveWrapper.add(java.sql.Timestamp.class);
		//primitiveWrapper.add(java.io.File.class);
		//primitiveWrapper.add(java.net.URL.class);
	}
	
	static {
		dataBaseType4j.put("NUMBER", "long");
		dataBaseType4j.put("INTEGER", "int");
		dataBaseType4j.put("FLOAT", "float");
		dataBaseType4j.put("DOUBLE", "double");
		dataBaseType4j.put("NVARCHAR2", "String");
		dataBaseType4j.put("VARCHAR2", "String");
		dataBaseType4j.put("CHAR", "String");
		dataBaseType4j.put("LONG", "String");
		dataBaseType4j.put("CLOB", "String");
		dataBaseType4j.put("BLOB", "Blob");
		//dataBaseType4j.put("DATE", "Date");
		dataBaseType4j.put("DATE", "String"); //pms
	}
	

	@APIOperation(description="바인드된 클래스의 Collection Object를 리턴합니다.")
    public Collection<?> getCollectionType(Class<?> propertyType){
    	
    	Collection<?> coll = null;
    	
    	try {
			if(propertyType.isAssignableFrom(SortedSet.class)) {
				coll = new TreeSet<Object>();	
			}
			else if(propertyType.isAssignableFrom(Set.class)) {
				coll = new HashSet<Object>();
			}
			else if(propertyType.isAssignableFrom(List.class)
					|| propertyType.isAssignableFrom(Collection.class)) {
				coll = new ArrayList<Object>();
			}
			/*
			else {
				coll = (Collection<?>) propertyType.newInstance();
			}*/
    	} catch (Exception e) {
			throw new APIException(e);
		}
    	
    	return coll;
    }
 
	@APIOperation(description="바인드된 클래스의 Map Object를 리턴합니다.")
    public Map<?, ?> getMapType(Class<?> propertyType){
    	
    	Map<?, ?> map = null;
    	try {
	    
	    	if(propertyType.isAssignableFrom(SortedMap.class)) {
	    		map = new TreeMap<Object, Object>();	
	    	}
	    	else if(propertyType.isAssignableFrom(Map.class)) {
	    		map = new HashMap<Object, Object>();	
	    	}
	    	else {
				map = (Map<?, ?>) propertyType.newInstance();
	    	}
    	} catch (InstantiationException e) {
			throw new APIException(e);
		} catch (IllegalAccessException e) {
			throw new APIException(e);
		}
    	
    	return map;
    }
    
    /**
     * primitive or primitive wrapper class ( Contains an array ) true/false
     * @param propertyType
     * @return
     */
	@APIOperation(description="바인드된 클래스가 일반 적인 타입인지 여부를 리턴합니다.")
    public boolean isGeneralType(Class<?> propertyType){
    	return (propertyType.isPrimitive() || isConvertibleRequest(propertyType) || propertyType.isArray());
    }
    
	@APIOperation(description="바인드된 패키지가 서포트 가능한 리퍼런스타입인지 여부를 리턴합니다.")	
    public boolean isSupportedReferenceType(String packages){
    	return isSupportedReferenceType(packages, null, null);
    }

	@APIOperation(description="바인드된 패키지가 서포트 가능한 includePackage인지 여부를 리턴합니다.")
    public boolean isSupportedIncludeReferenceType(String packages, String[] includePackages){
    	return isSupportedReferenceType(packages, includePackages, null);
    }
    
	@APIOperation(description="바인드된 패키지가 서포트를 제외한 excludePackage인지 여부를 리턴합니다.")
    public boolean isSupportedExcludeReferenceType(String packages, String[] excludePackages){
    	return isSupportedReferenceType(packages, null, excludePackages);
    }
    
	@APIOperation(description="바인드된 패키지가 서포트 가능한 includePackage인지, 서포트를 제외한 excludePackage인지 여부를 리턴합니다.")
    public boolean isSupportedReferenceType(String packages, String[] includePackages, String[] excludePackages){
    	
    	boolean out = true;
    	if(packages == null) return false;
    	
    	String[] excludes = null;
    	if( excludePackages == null || excludePackages.length == 0) {
    		excludes = (String[]) OperateConstants.EXCLUDE_PACKAGE_STARTS.toArray(); //{"java.","javax."}
    	}
    	else {
    		excludes = excludePackages;
    	}
    	
    	if(includePackages != null) {
    		for(String include : includePackages){
        		if(packages.startsWith(include)) {
        			out = true;
        			break;
        		}else {
        			out = false;
        		}
        	}
    	}
    	
    	for(String exclude : excludes){
    		if(packages.startsWith(exclude)) {
    			out = false;
    			break;
    		}
    	}
    	
    	if(!out) {
	    	if(logger.isDebugEnabled()) {
	    		logger.debug(" isSupportedReferenceType : " + out + ", type is : " + packages);
	    	}
    	}
    	return out;
    }
 
    
	@APIOperation(description="바인드된 클래스가 PRIMITIVE 타입이거나 일반적인 REFERENCE WRAPTYPE인지를 리턴합니다.")
    public boolean isPrimitiveWrapType(Class<?> propertyType) {
    	return primitiveWrapper.contains(propertyType);
    }
    
   
    
	/**
	 * <pre>
	 * [메서드 설명]
	 * 바인드된 클래스가 beanutils 의 ConvertUtils 가 converting 가능한 primitive reference type 인지 체크하여줌
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @param clazz
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 21.
	 */
	@APIOperation(description="바인드된 클래스가 beanutils 의 ConvertUtils 가 converting 가능한 primitive reference type 인지 체크하여줍니다.")
    public boolean isConvertibleRequest(Class<?> clazz) {
    	
    	if(clazz == null || clazz.isAssignableFrom(Class.class)) {
    		return false;
    	}
    	
    	return (coverter.lookup(clazz) != null);
    }
    
}
