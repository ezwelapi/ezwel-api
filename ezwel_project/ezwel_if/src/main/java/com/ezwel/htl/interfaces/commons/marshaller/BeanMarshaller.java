package com.ezwel.htl.interfaces.commons.marshaller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIService;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;



/**
 * <pre>
 * Bean과 JSON간의 Marshall/Unmarshall수행 클래스 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 8.
 * @serviceType API
 */
@APIService
public class BeanMarshaller {

	private static final Logger logger = LoggerFactory.getLogger(BeanMarshaller.class);
	
	
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
    public Object getProperty(Object bean, String getPropertyName) {
        if(bean == null || APIUtil.isEmpty(getPropertyName)) {
        	throw new APIException(" The parameter was null or invalid. ");
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
        
        if(logger.isWarnEnabled()) {
        	logger.warn(APIUtil.addString("[WARN] The property does not exist [bean : ", bean.getClass().getCanonicalName(), ", getPropertyName : ", getPropertyName, "]"));
        }
        return null;
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
    public boolean setProperty(Object bean, String propertyName, Object setValue) {
        if(bean == null || APIUtil.isEmpty(propertyName)) {
        	throw new APIException(" The parameter was null or invalid. ");
        }

        try {
			if( PropertyUtils.getPropertyDescriptor(bean, propertyName) != null ) {
				BeanUtils.setProperty(bean, propertyName, setValue);
				return true;
			}
		} catch (IllegalAccessException e) {
			throw new APIException(e);
		} catch (InvocationTargetException e) {
			throw new APIException(e);
		} catch (NoSuchMethodException e) {
			throw new APIException(e);
		}
        
        if(logger.isWarnEnabled()) {
        	logger.warn(APIUtil.addString("[WARN] The property does not exist [bean : ", bean.getClass().getCanonicalName(), ", property : ", propertyName, "]"));
        }
        return false;
    }
    
	
	
	@APIOperation(description="빈 오브젝트를 JSON으로 변환하여 줍니다.(결과에 따라 JSONObject or JSONArray로 캐스팅하여 사용할 수 있습니다.)", isExecTest=true)
	public String toJSONString(Object bean) {
		if(bean == null) {
			throw new APIException("JSON 변환대상 Object가 존재하지 않습니다.");
		}
		
		String out = null;
		ObjectMapper mapper = null;
		
		try {
			
			mapper = new ObjectMapper();
			out = mapper.writeValueAsString(bean);
		} catch (JsonProcessingException e) {
			throw new APIException("JSON 변환과정에 장애발생", e);
		}
			
		return out;
	}
	
	@APIOperation(description="바인드된 MAP객체를 빈 오브젝트에 담아줍니다.", isExecTest=true)
	public Object fromMap(Map<String, ? extends Object> readMap, Class<?> writeBean) {
		if (readMap == null) {
			throw new APIException("Map데이터가 존재하지 않습니다.");
		}
		if (writeBean == null) {
			throw new APIException("정보를 저장할 Bean이 존재하지 않습니다.");
		}

		Object out = null;
		try {
			if(readMap.getClass().isAssignableFrom(LinkedHashMap.class)) {
				logger.debug("순서(인덱스)를 보장하는 맵으로 구현된 Map객체입니다. ");
			}
			else {
				logger.warn("순서(인덱스)가 보장 되지 않는 맵으로 구현된 Map객체입니다. ");
			}
			out = writeBean.newInstance();
			BeanUtils.populate(out, readMap);
		} catch (IllegalAccessException e) {
			throw new APIException(e);
		} catch (InvocationTargetException e) {
			throw new APIException(e);
		} catch (InstantiationException e) {
			throw new APIException(e);
		}
		
		return out;
	}	
	

	
	@APIOperation(description="바인드된 JSON객체를 Class객체에 담아줍니다.", isExecTest=true)
	public Object fromJSON(String jsonString, Class<?> writeBean){
		
		 Object out = null;
		 ObjectMapper mapper = null;
		 Map<String, Object> jsonMap = null;
		 
		if(jsonString != null) {
			mapper = new ObjectMapper();
			try {
				jsonMap = mapper.readValue(jsonString, new TypeReference<Map<String, Object>>(){});
				out = fromMap(jsonMap, writeBean);
			} catch (JsonParseException e) {
				throw new APIException(e);
			} catch (JsonMappingException e) {
				throw new APIException(e);
			} catch (IOException e) {
				throw new APIException(e);
			}
		}
		
 		return out;
	}	
	

	/**
	 * bean의 value을 map에 넣어주는 메소드
	 * @param readBean
	 * @param writeMap
	 */
    @SuppressWarnings("unchecked")
	@APIOperation(description="바인드된 readBean을 바인드된 Map에 담아줍니다.", isExecTest=true)
	public Map<String, Object> beanToMap(Object readBean) {
		if (readBean == null) {
			throw new APIException("Bean데이터가 존재하지 않습니다.");
		}

		Map<String, Object> out = null;
		try {
			out = new LinkedHashMap<String, Object>();
			out.putAll(PropertyUtils.describe(readBean));
		} catch (IllegalAccessException e) {
			throw new APIException(e);
		} catch (InvocationTargetException e) {
			throw new APIException(e);
		} catch (NoSuchMethodException e) {
			throw new APIException(e);
		}
		logger.debug("beanToMap : {}", out);
		return out;
	}
    
}
