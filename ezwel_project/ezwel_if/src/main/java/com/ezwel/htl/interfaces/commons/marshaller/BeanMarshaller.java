package com.ezwel.htl.interfaces.commons.marshaller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.fasterxml.jackson.core.JsonGenerationException;
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
@Component
@APIType(description="Bean과 JSON간의 Marshall/Unmarshall수행 클래스 ")
public class BeanMarshaller {

	private static final Logger logger = LoggerFactory.getLogger(BeanMarshaller.class);
	
	
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
			throw new APIException("BEAN을 JSON으로 변환과정에 장애발생", e);
		} catch (Exception e) {
			throw new APIException("BEAN을 JSON으로 변환과정에 장애발생", e);
		}
			
		return out;
	}
	
	
	@APIOperation(description="바인드된 MAP객체를 빈 오브젝트에 담아줍니다.", isExecTest=true)
	public Object mapToBean(Map<String, ? extends Object> readMap, Class<?> writeBean) {
		if (readMap == null) {
			throw new APIException("Map데이터가 존재하지 않습니다.");
		}
		if (writeBean == null) {
			throw new APIException("정보를 저장할 Bean이 존재하지 않습니다.");
		}

		Object out = null;
		String jsonString = null;
		
		try {
			jsonString = toJSONString(readMap);
			logger.debug("-mapToBean : {}", jsonString);
			
			out = fromJSONString(jsonString, writeBean);
			
		} catch(Exception e) {
			throw new APIException("맵을 빈으로 변환하는과정에 장애발생.\n{}", new Object[] {jsonString}, e);
		}
		return out;
	}
	
	
	
	@APIOperation(description="바인드된 JSON 문자열을 Class객체에 담아줍니다.", isExecTest=true)
	public Object fromJSONString(String jsonString, Class<?> writeBean) {

		ObjectMapper mapper = new ObjectMapper();
		Object out = null;
		try {
			// Convert JSON string to Object
			out = mapper.readValue(jsonString, writeBean);
			
		} catch (JsonParseException e) {
			throw new APIException("(JSON)전문 분석 장애 발생. 제휴사 서버의 응답(JSON)전문 데이터 표현이 잘못되었습니다.\n{}", new Object[] {jsonString}, e);
		} catch (JsonGenerationException e) {
			throw new APIException("제휴사 서버의 응답(JSON)전문 언마샬과정에 JsonGenerationException발생.\n{}", new Object[] {jsonString}, e);
		} catch (JsonMappingException e) {
			throw new APIException("(JSON)전문의 EZWEL(BEAN)에 언마샬과정에 장애 발생. 제휴사 서버의 응답(JSON)전문 데이터 표현이 잘못되었습니다.\n{}", new Object[] {jsonString}, e) ;
		} catch (IOException e) {
			throw new APIException("제휴사 서버의 응답(JSON)전문 언마샬과정에 IOException발생.\n{}", new Object[] {jsonString}, e);
		} catch (Exception e) {
			throw new APIException("제휴사 서버의 응답(JSON)전문 데이터 표현이 잘못되었습니다.\n{}", new Object[] {jsonString}, e);
		}
		
		return out;
	}
	
	@APIOperation(description="바인드된 JSON 문자열을 Map객체에 담아줍니다.", isExecTest=true)
	public Map<String, Object> fromJSONStringToMap(String jsonString) {

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> out = null;
		try {

			// convert JSON string to LinkedHashMap
			out = mapper.readValue(jsonString, new TypeReference<LinkedHashMap<String, Object>>(){});

		} catch (JsonParseException e) {
			throw new APIException("(JSON)전문 분석 장애 발생. 전문 데이터 표현이 잘못되었습니다. {}", new Object[] {jsonString}, e);
		} catch (JsonGenerationException e) {
			throw new APIException("(JSON)전문 언마샬과정에 JsonGenerationException발생. {}", new Object[] {jsonString}, e);
		} catch (JsonMappingException e) {
			throw new APIException("(JSON)전문의 EZWEL(BEAN)에 언마샬과정에 장애 발생. (JSON)전문 데이터 표현이 잘못되었습니다.\n{}", new Object[] {jsonString}, e) ;
		} catch (IOException e) {
			throw new APIException("(JSON)전문 언마샬과정에 IOException발생.\n{}", new Object[] {jsonString}, e);
		} catch (Exception e) {
			throw new APIException("(JSON)전문 데이터 표현이 잘못되었습니다.\n{}", new Object[] {jsonString}, e);
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
