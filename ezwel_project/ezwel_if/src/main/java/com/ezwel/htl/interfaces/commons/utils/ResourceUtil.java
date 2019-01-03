package com.ezwel.htl.interfaces.commons.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;

@Component
@APIType(description="프로퍼티 핸들링 유틸")
public class ResourceUtil {

	private static final Logger logger = LoggerFactory.getLogger(ResourceUtil.class);

	private static Map<String, Map<String, Object>> resourceMap = null; 
	
	private final String RESOURCE_EXT = ".properties";
	
	private static void initResourceMap(){
		if(resourceMap == null) {
			resourceMap = new HashMap<String, Map<String, Object>>();
		}
	}
	
	private static void setResourceMap(String key, Map<String, Object> value){
		initResourceMap();
		resourceMap.put(key, value);
		
		logger.debug("- entered after resource cache size : {}", resourceMap.size());
	}
	
	private static Map<String, Object> getResourceMap(String key){
		initResourceMap();
		return resourceMap.get(key);
	}
	
	@APIOperation
	public void out(Properties properties){
		if(logger.isDebugEnabled()) {
			StackTraceElement[] stacks = new Throwable().getStackTrace();
        	StackTraceElement beforeStack = stacks[ 1 ];    
        	
        	logger.debug("[Properties Infomation] ".concat(beforeStack.getClassName()).concat("@").concat(beforeStack.getMethodName()).concat(":").concat(Integer.toString(beforeStack.getLineNumber())));
			for(Entry<?, ?> entry : properties.entrySet()){
				logger.debug(entry.getKey() + " : " + entry.getValue());
			}
		}
	}
	
	@APIOperation
	public Properties load(String filePath) {
		logger.debug("[load] filePath : {}", filePath);

		if(filePath == null) {
			return null;
		}
		return load(new File(filePath));
	}
	
	@APIOperation
	public Properties load(File file) {
		logger.debug("[load] file : {}", file);

		if(file.exists()) {
			Map<String, Object> loadedCache = getLoadedCache(file);
			if(loadedCache != null) {
				return (Properties) loadedCache.get(OperateConstants.RESOURCE_PROPERTIES_KEY);
			}
			else {
				//캐쉬 실행
				return enter(file);
			}
		}
		else {
			return null;
		}		
	}
	
	@APIOperation
	public Properties load(URL fileURL) {
		return load(fileURL, OperateConstants.DEFAULT_ENCODING);
	}
	
	
	@APIOperation
	public Properties load(URL fileURL, String encoding) {
		logger.debug("[load] fileURL : {}", fileURL);
		
		if(fileURL == null) {
			return null;
		}
		
		Map<String, Object> loadedCache = null;
		File urlFile = new File(fileURL.getPath());
		
		if(urlFile.getPath().indexOf(OperateConstants.JAR_SEPARATOR) > -1) {
			//JAR 내부 파일 URL
			loadedCache = getLoadedCache(urlFile);
			if(loadedCache != null) {
				return (Properties) loadedCache.get(OperateConstants.RESOURCE_PROPERTIES_KEY);
			}
			else {
				
				try {
					String absolutePath = fileURL.getPath();
					absolutePath = absolutePath.substring(absolutePath.lastIndexOf(OperateConstants.JAR_SEPARATOR) + OperateConstants.JAR_SEPARATOR.length());
					logger.debug("- in jar absolutePath : {}", absolutePath);
					
					InputStream inputStream = getClass().getClassLoader().getResourceAsStream(absolutePath);
					logger.debug("- in jar inputStream : {}", inputStream);
					
					return enter(inputStream, urlFile, encoding);
				} catch (Exception e) {
					throw new APIException(MessageConstants.RESPONSE_CODE_9000, "JAR파일 내부 텍스트기반 데이터를 추출하고 캐쉬하는 과정에 장애 발생", e);
				}
			}
		}
		else {
			return load(urlFile);
		}
	}
	

	@APIOperation
	private Map<String, Object> getLoadedCache(File propertiesFile) {
		logger.debug("[START] ResourceUtil.getLoadedCache ");
		
		Map<String, Object> out = null;
		String propertyPath = null;
		File file = propertiesFile;
		long resourceLastmodified = -1;
		
		try {
			propertyPath = file.getPath();
			
			if(!propertyPath.toLowerCase().endsWith(RESOURCE_EXT)) {
				throw new APIException(MessageConstants.RESPONSE_CODE_2001, "프로퍼티 파일이 아닙니다.");
			}
			
			out = getResourceMap(propertyPath);
			
			if(out != null && out.get(OperateConstants.RESOURCE_LASTMODIFIED_KEY) != null) {
				resourceLastmodified = (Long) out.get(OperateConstants.RESOURCE_LASTMODIFIED_KEY);
			}
			
			logger.debug(" file : {}, resourceLastmodified : {}", file, resourceLastmodified);
	
			if( file.lastModified() != resourceLastmodified ) {
				out = null;
			}
			
		} catch (Exception e) {
            throw new APIException("이미 캐쉬된 프로퍼티인지 확인하는 과정에 장애 발생", e);
        }
		
        return out;
	}
	
	@APIOperation
	private Properties enter(File file) {
		
		Properties out = null;
		BufferedInputStream bis = null;
		String propertyPath = null;
		try {
			
			propertyPath = file.getPath();
        	bis = new BufferedInputStream(new FileInputStream(file));
        	out = new Properties();
        	out.load(bis);
        	
        	enter(propertyPath, out, file, null);
        	
    		logger.debug(APIUtil.addString("new Loading properties file... ", propertyPath, ", resourceMap size : ", resourceMap.size()));
        	
        } catch (Exception e) {
            throw new APIException("일반경로의 프로퍼티 파일을 로딩하고 캐쉬하는 과정에 장애 발생", e);
        } finally {
        	if(bis != null) {
        		try {
					bis.close();
				} catch (IOException e) {
					logger.error("[IOException] bufferedInputStream.close", e);
				}
        	}
        }
		
		return out;
	}
	
	@APIOperation
	private Properties enter(InputStream inputStream, File urlFile, String encoding) {
		logger.debug("- [enter] InputStream is, File urlFile, String encoding => {}", inputStream);
		if(inputStream == null) {
			throw new APIException("Properties InputStream 파라메터가 존재하지 않습니다.");
		}
		
		Properties out = null;
		BufferedReader bufferedReader = null;
		String propertyPath = null;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			
			propertyPath = urlFile.getPath();
        	out = new Properties();
        	out.load(bufferedReader);
        	
        	enter(propertyPath, out, null, encoding);
        	
    		logger.debug(APIUtil.addString("new Loading properties file... ", propertyPath, ", resourceMap size : ", resourceMap.size()));
        	
        } catch (Exception e) {
            throw new APIException("InputStream 데이터를 읽어 프로퍼티로 변환하는 과정에 장애 발생", e);
        } finally {
        	if(bufferedReader != null) {
        		try {
        			bufferedReader.close();
				} catch (IOException e) {
					logger.error("[IOException] bufferedReader.close", e);
				}
        	}
        	
        	if(inputStream != null) {
        		try {
        			inputStream.close();
				} catch (IOException e) {
					logger.error("[IOException] inputStream.close", e);
				}
        	}        	
        }
		
		return out;
	}
	
	@APIOperation
	private void enter(String propertyPath, Properties out, File file, String encoding) { 
		logger.debug("- [enter] String propertyPath, Properties out, File file, String encoding ");
		
		Map<String, Object> propsValues = new HashMap<String, Object>();
		try {
			
			if(file != null) {
				propsValues.put(OperateConstants.RESOURCE_LASTMODIFIED_KEY, file.lastModified());
			}
			else {
				int keyValueHashSum = 0;
				for(Entry<Object, Object> entry : out.entrySet()) {
					keyValueHashSum += entry.getKey().hashCode();
					keyValueHashSum += entry.getValue().hashCode();
				}

				// InputStream으로 부터 추출한 프로퍼티는 키,밸류들의 해쉬코드의 합을 long으로 변환한 값으로 파일의 LASTMODIFIED 값을 대처한다.
				long inputStreamPropertiesSize = Long.valueOf(keyValueHashSum);
				
				propsValues.put(OperateConstants.RESOURCE_LASTMODIFIED_KEY, inputStreamPropertiesSize);
			}
			
	    	propsValues.put(OperateConstants.RESOURCE_PROPERTIES_KEY, out);
	    	
	    	setResourceMap(propertyPath, propsValues);
		} catch (Exception e) {
			throw new APIException("프로퍼티의 InputStream 데이터를 캐쉬하는 과정에 장애 발생", e);
		}
	}
	
    
}
