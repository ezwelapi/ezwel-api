package com.ezwel.htl.interfaces.commons.http;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigDTO;
import com.ezwel.htl.interfaces.commons.http.data.MultiHttpConfigDTO;
import com.ezwel.htl.interfaces.commons.marshaller.BeanMarshaller;
import com.ezwel.htl.interfaces.commons.thread.CallableExecutor;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;



/**
 * <pre>
 *  HTTP 프로토콜을 이용한 싱글 요청 인터페이스와 다중 요청 인터페이스 유틸
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */
@Service 
@APIType
public class HttpInterfaceExecutorService {

	private static final Logger logger = LoggerFactory.getLogger(HttpInterfaceExecutorService.class);

	/**
	 * Default URL Connection TimeOut 3 Second
	 */
	private final int urlConnTimeout = 3000;

	/**
	 * Default Read Time Out 3 Second
	 */
	private final int urlReadTimeout = 3000;
	
	private APIUtil util;
	
	private BeanMarshaller beanConvert;
	
	private PropertyUtil propertyUtil;
	
	public HttpInterfaceExecutorService() {
		this.reset();
	}

	private void reset() {
		if(util == null) {
			util = new APIUtil();
		}
		if(beanConvert == null) {
			beanConvert = new BeanMarshaller(); 
		}
		if(propertyUtil == null) {
			propertyUtil = new PropertyUtil();
		}
	}
	
	@APIOperation(description="Getting Http URL Connection", isExecTest=true)
	public HttpURLConnection getOpenHttpURLConnection(HttpConfigDTO in) {
		
		HttpURLConnection conn = null;
		URL url = null;
		
		int httpConnTimeout = (in.getConnTimeout() > -1 ? in.getConnTimeout() : urlConnTimeout);
		int httpReadTimeout = (in.getReadTimeout() > -1 ? in.getReadTimeout() : urlReadTimeout);
		
		try {
			url = new URL(in.getRestURI());
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(in.isDoInput());	//HTTP 응답 결과 수신 여부
			conn.setDoOutput(in.isDoOutput()); //HTTP 요청 파라메터 송신 여부
			conn.setUseCaches(false);
			conn.setDefaultUseCaches(false);
			conn.setRequestMethod(OperateConstants.HTTP_METHOD_POST);
			conn.setConnectTimeout(httpConnTimeout);
			conn.setReadTimeout(httpReadTimeout);	
			
		} catch (ProtocolException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9000, "■ 연결이 불가능한 주소입니다.", e);
		} catch (MalformedURLException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9000, "■ 프로토콜이 잘못되었습니다.", e);
		} catch (IOException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "■ 통신 장애 발생.", e);
		}

		return conn;
	}
	
	void setJsonRequestHeader(HttpConfigDTO in, HttpURLConnection conn, String inJsonParam) {
		
		Properties certifications = null;
		
		try {
			
			//Certifications Property
			certifications = getCert(in);
			
			if(in.getRequestProperty() == null) in.setRequestProperty(new Properties());
			in.getRequestProperty().putAll(certifications);
			
			for(Entry<Object, Object> entry : in.getRequestProperty().entrySet()) {
				conn.setRequestProperty((String) entry.getKey(), (String) entry.getValue());
			}
			
			/** 강제 유지 프로퍼티 */
			conn.setRequestProperty("Content-Type", APIUtil.addString("application/json; charset=", in.getEncoding()));
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Cache-Control", "no-cache");
			
			if(in.isDoOutput() && APIUtil.isNotEmpty(inJsonParam)) {
				conn.setRequestProperty("Content-Length", Integer.toString(util.getBytesLength(inJsonParam, in.getEncoding())) );
			}
			
			logger.debug("\n■ RequestProperties : \n{}", beanConvert.toJSONString( conn.getRequestProperties()));
		} catch(APIException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9000, "■ 인터페이스 요청 헤더 작성중 장애발생.", e);
		} finally {
			if(certifications != null) {
				certifications.clear();
			}
			if(in.getRequestProperty() != null) {
				in.getRequestProperty().clear();
			}			
		}
	}
	
	String getInputStreamToString(HttpConfigDTO in, HttpURLConnection conn) {
		
		String out = null; 
		InputStream is = null;
		
		try {
			is = new BufferedInputStream(conn.getInputStream());
			out = IOUtils.toString(is, in.getEncoding());
		} catch (IOException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9000, "■ 입력스트림 변환과정에 장애발생", e);
		} finally {
			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {
					throw new APIException(e);
				}
			}
		}
		
		return out;
	}
	
	
	<T extends AbstractSDO> String inputBeanToJSON(T inputObject) {
		
		String out = null;

		try {
		
			out = beanConvert.toJSONString(inputObject);
			logger.debug("\n■ input parameter : \n{}", out);	
		}
		catch(APIException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9000, "■ 입력 DTO를 JSON으로 변환과정에 장애발생", e);
		}
		
		return out;
	}
	
	void wrtieInputJSON(HttpConfigDTO in, HttpURLConnection conn, String inJsonParam) {
		
		OutputStream os = null;
		try {
			os = conn.getOutputStream();
			os.write(inJsonParam.getBytes(in.getEncoding()));
			os.flush();			
		} catch (IOException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "■ JSON 파라메터 전송중 장애발생", e);
		} finally {
			if(os != null) {
				try {
					os.close();
				} catch (IOException e) {
					throw new APIException(e);
				}
			}
		}
	}
	
	@APIOperation(description="Http URL Communication API (output only)", isExecTest=true)
	public <T extends AbstractSDO> T sendPostJSON(HttpConfigDTO in, Class<T> outputObject) {
		if(in == null) {
			throw new APIException(MessageConstants.RESPONSE_CODE_2000, "인터페이스 필수 입력 객체가 존재하지 않습니다.");
		}
		
		/** 요청 파라메터 여부 */
		in.setDoOutput(false); 
		return sendPostJSON(in, null, outputObject);
	}
	
	@APIOperation(description="Http URL Communication API (input only)", isExecTest=true)
	public <T extends AbstractSDO> T sendPostJSON(HttpConfigDTO in, T inputObject) {
		if(in == null) {
			throw new APIException(MessageConstants.RESPONSE_CODE_2000, "인터페이스 필수 입력 객체가 존재하지 않습니다.");
		}
		
		/** 응답 결과 수신 여부 */
		in.setDoInput(false);
		return sendPostJSON(in, inputObject, null);
	}
	
	
	/**
	 * Http URL Communication API
	 * @param in
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "finally" })
	@APIOperation(description="Http URL Communication API", isExecTest=true)
	public <T1 extends AbstractSDO, T2 extends AbstractSDO> T2 sendPostJSON(HttpConfigDTO in, T1 inputObject, Class<T2> outputType) {
		logger.debug("[START] sendJSON\n[CHANNEL-INFO] {}\n[USER-INPUT] {}\n[USER-OUTPUT] {}", in, inputObject, outputType);
		
		//return value
		T2 out = null;
		//HttpURLConn
		HttpURLConnection conn = null;
		//doOutput parameter
		String inJsonParam = null;
		//response original value 
		String responseOrgin = null;
		
		try {
			
			if(in == null) {
				throw new APIException(MessageConstants.RESPONSE_CODE_2000, "■ 인터페이스 필수 입력 객체가 존재하지 않습니다.");
			}
			
			/** input bean to json */
			if(in.isDoOutput() && inputObject != null) {
				inJsonParam = inputBeanToJSON(inputObject);
			}

			/** getOpenHttpURLConnection */
			conn = getOpenHttpURLConnection(in);

			/** Set Request Header */
			setJsonRequestHeader(in, conn, inJsonParam);
			
			/** send request json parameter */
			if(in.isDoOutput() && inputObject != null) {
				wrtieInputJSON(in, conn, inJsonParam);
			}
			
			/** 응답 코드 채크 */
			logger.debug("■ responseCode : {}", conn.getResponseCode());
			if(conn.getResponseCode() != 200) {
				/** 서버측 에러 발생시 에러메시지 세팅 */
				logger.error("■ HttpServer Exception : {}", (conn.getErrorStream() != null ? IOUtils.toString(new BufferedInputStream(conn.getErrorStream()), in.getEncoding()) : ""));
				throw new APIException(MessageConstants.RESPONSE_CODE_9200, "■ HTTP 통신 장애 발생 원격 서버 에러");
	    	}
			else {
				/** 응답 수신 및 리턴타입 빈으로 변환 */
				if(in.isDoInput()) {

					/** 응답을 읽음 */
					responseOrgin = getInputStreamToString(in, conn); 
					
					if(responseOrgin != null) {
						responseOrgin = responseOrgin.trim();
					} 
					
					if(APIUtil.isNotEmpty(responseOrgin)) {
						
						if(outputType == null) {
							throw new APIException(MessageConstants.RESPONSE_CODE_9000, "■ 인터페이스 응답 결과를 담을 CLASS정보가 존재하지 않습니다. HttpDTO의 outputType class를 설정하세요.");
						}

						/** execute unmarshall */
						logger.debug("■ outputType : {}", outputType);
						out = (T2) beanConvert.fromJSONString/*fromJSON*/(responseOrgin, outputType);						
					}
					else {
						logger.debug("■ 인터페이스 응답 내용이 존재하지 않습니다.");
					}
				}
			}
			
			
		} catch (APIException e) {
			
			if(outputType != null) {
				e.printStackTrace();
				
				out = outputType.newInstance();
				propertyUtil.setProperty(out, MessageConstants.RESPONSE_CODE_FIELD_NAME, e.getResultCode());
				propertyUtil.setProperty(out, MessageConstants.RESPONSE_MESSAGE_FIELD_NAME, e.getMessage());
			}
			else {
				throw new APIException(MessageConstants.RESPONSE_CODE_9100, "■ 통신 장애 발생.", e);
			}
		}  finally {
			if(conn != null) {
				conn.disconnect();
			}
			
			logger.debug("[END] sendJSON");
			return out;			
		}
	}
	
	/**
	 * Create Http URL Communication Certification Properties
	 * @param request
	 * @return
	 */
	@APIOperation(description="Create Http URL Communication Certification Properties", isExecTest=true)
	private Properties getCert(HttpConfigDTO request) {
		
		Properties out = null;
		Field[] field = null;
		Object fieldValue = null;
		APIFields fieldAnno = null;
		
		try {
			out = new Properties();
			field = request.getClass().getDeclaredFields();
			
			for(Field item : field) {
				item.setAccessible(true);
				fieldAnno = item.getAnnotation(APIFields.class);
			
				fieldValue = item.get(request);
				
				if( fieldAnno == null ) {
					throw new APIException(APIUtil.addString("'", item.getName(), "'APIFields Annotation is not found!"));
				}
				if(fieldAnno.required() && fieldValue == null) {
					throw new APIException(APIUtil.addString("필수 입력 필드 '", fieldAnno.description(), "'가(이) 입력되지 않았습니다."));
				}
				if(fieldAnno.httpHeader() && fieldValue != null) {
					out.setProperty(fieldAnno.headerName(), (String) fieldValue);
				}				
			}
		} catch (IllegalArgumentException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9000, e);
		} catch (IllegalAccessException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9000, "■ HttpConfigDTO필드에 접근할수 없습니다.", e);
		}
		
		return out;
	}
	
	
	@SuppressWarnings({ "unchecked", "finally" })
	@APIOperation(description="Http URL Multi Communication API", isExecTest=true)
	public <T extends AbstractSDO> List<T> sendMultiPostJSON(List<MultiHttpConfigDTO> in) {
		logger.debug("[START] sendMultiPostJSON\nInput Signature : {}", in);
		
		List<T> out = null;
		CallableExecutor executor = null;
		
		try {
			if(in == null || in.size() == 0) {
				throw new APIException(MessageConstants.RESPONSE_CODE_2000, "■ 멀티쓰레드 URL통신에 필요한 설정 목록이 존재하지 않습니다.");
			}
			
			executor = new CallableExecutor();
			out = new ArrayList<T>();
			
			//multiHttpConfigList 사이즈만큼 쓰레드를 가진 쓰레드풀 생성
			executor.initThreadPool(in.size());
			
			for(MultiHttpConfigDTO multiHttpConfig : in) {
				Callable<AbstractSDO> callable = new HttpInterfaceHelper(multiHttpConfig);
				//logger.debug("[before] executor.addCall : {}", in);
				// 생성된 callable들을 threadpool에서 수행시키고 결과는 Future 목록에 담는다
				executor.addCall(callable);
			}
			
			List<Future<?>> futures = executor.getResult();
			if(futures != null) {
				logger.debug("- futures.size() : {}", futures.size());
				
				for(Future<?> future : futures) {
					out.add((T) future.get());
				}
			}
			
		} catch (APIException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "■ 다중 인터페이스 장애 발생", e);
		}
		finally {
			if(executor != null) {
				executor.clear();
			}

			logger.debug("[END] sendMultiPostJSON");		
			return out;
		}
	}
}
