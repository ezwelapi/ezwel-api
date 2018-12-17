package com.ezwel.htl.interfaces.commons.http;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.HttpHeaderConstants;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigSDO;
import com.ezwel.htl.interfaces.commons.http.data.MultiHttpConfigSDO;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
import com.ezwel.htl.interfaces.commons.marshaller.BeanMarshaller;
import com.ezwel.htl.interfaces.commons.thread.CallableExecutor;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.commons.utils.StackTraceUtil;
import com.ezwel.htl.interfaces.commons.validation.ParamValidate;



/**
 * <pre>
 *  HTTP 프로토콜을 이용한 싱글 요청 인터페이스와 다중 요청 인터페이스 유틸
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */
@Component
@APIType(description="http url connection executor")
public class HttpInterfaceExecutor {

	private static final Logger logger = LoggerFactory.getLogger(HttpInterfaceExecutor.class);

	/**
	 * Default URL Connection TimeOut 3 Second
	 */
	private final int urlConnTimeout = 3000;

	/**
	 * Default Read Time Out 10 Second
	 */
	private final int urlReadTimeout = 10000;
	
	
	@Autowired /** interface_if는 프론트 및 관리자단에서 ezwel 프레임워크 표준인  Autowired를 사용한다. (interface_if_server는 Autowired보다 빠른 스프링 컨텍스트의 getBean을 사용함) */
	private APIUtil util;
	
	@Autowired /** interface_if는 프론트 및 관리자단에서 ezwel 프레임워크 표준인  Autowired를 사용한다. (interface_if_server는 Autowired보다 빠른 스프링 컨텍스트의 getBean을 사용함) */
	private BeanMarshaller beanConvert;
	
	@Autowired /** interface_if는 프론트 및 관리자단에서 ezwel 프레임워크 표준인  Autowired를 사용한다. (interface_if_server는 Autowired보다 빠른 스프링 컨텍스트의 getBean을 사용함) */
	private PropertyUtil propertyUtil;
	
	@Autowired /** interface_if는 프론트 및 관리자단에서 ezwel 프레임워크 표준인  Autowired를 사용한다. (interface_if_server는 Autowired보다 빠른 스프링 컨텍스트의 getBean을 사용함) */
	private StackTraceUtil stackTraceUtil;
	
	public HttpInterfaceExecutor() {
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
		if(stackTraceUtil == null) {
			stackTraceUtil = new StackTraceUtil();
		}
	}
	
	@APIOperation(description="Getting Http URL Connection", isExecTest=true)
	public HttpURLConnection getOpenHttpURLConnection(HttpConfigSDO in, int contentLength) {
				
		HttpURLConnection conn = null;
		URL url = null;
		
		int httpConnTimeout = (in.getConnTimeout() != null ? in.getConnTimeout() : urlConnTimeout);
		int httpReadTimeout = (in.getReadTimeout() != null ? in.getReadTimeout() : urlReadTimeout);
		logger.debug("\n# httpConnTimeout : {}\n# httpReadTimeout : {}", httpConnTimeout, httpReadTimeout);
		
		try {
			url = new URL(in.getRestURI());
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(in.isDoInput());	//HTTP 응답 결과 수신 여부
			conn.setDoOutput(in.isDoOutput()); //HTTP 요청 파라메터 송신 여부
			conn.setUseCaches(false);
			conn.setDefaultUseCaches(false);
			if(in.isDoOutput() && contentLength > 0) {
				conn.setRequestMethod(OperateConstants.HTTP_METHOD_POST);
			}
			else {
				conn.setRequestMethod(OperateConstants.HTTP_METHOD_GET);
			}
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
	
	@APIOperation(description="Setting Request Header", isExecTest=true)
	void setRequestHeader(HttpConfigSDO in, HttpURLConnection conn, int contentLength) {
		logger.debug("[START] setRequestHeader {}", in.getRestURI());
		
		Properties certifications = null;
		
		try {
			
			//Certifications Property
			certifications = getCert(in);
			for(Entry<Object, Object> prop : certifications.entrySet()) {
				logger.debug("- certifications : {}", prop);
			}
			
			if(in.getRequestProperty() == null) in.setRequestProperty(new Properties());
			in.getRequestProperty().putAll(certifications);
			
			for(Entry<Object, Object> entry : in.getRequestProperty().entrySet()) {
				conn.setRequestProperty((String) entry.getKey(), (String) entry.getValue());
			}
			
			/** 강제 유지 프로퍼티 */
			conn.setRequestProperty(HttpHeaderConstants.CONTENT_TYPE, APIUtil.addString(HttpHeaderConstants.CONTENT_TYPE_APP_JSON, in.getEncoding()));
			conn.setRequestProperty(HttpHeaderConstants.ACCEPT, HttpHeaderConstants.ACCEPT_APP_JSON);
			conn.setRequestProperty(HttpHeaderConstants.ACCEPT_CHARSET, in.getEncoding());
			conn.setRequestProperty(HttpHeaderConstants.CHARSET, in.getEncoding());
			conn.setRequestProperty(HttpHeaderConstants.DATE, APIUtil.getFastDate());
			conn.setRequestProperty(HttpHeaderConstants.CACHE_CONTROL, HttpHeaderConstants.CACHE_CONTROL_NO_CACHE);
			conn.setRequestProperty(HttpHeaderConstants.HTTP_REST_URI, in.getRestURI());
			conn.setRequestProperty(HttpHeaderConstants.USER_AGENT, HttpHeaderConstants.USER_AGENT_VALUE);

			if(contentLength > 0) {
				//in case that the content is not empty
				conn.setRequestProperty(HttpHeaderConstants.CONTENT_LENGTH, Integer.toString(contentLength));
		    } else {
		    	conn.addRequestProperty(HttpHeaderConstants.CONTENT_LENGTH, "0");
			}
			
			for(Entry<String, List<String>> header : conn.getRequestProperties().entrySet()) {
				logger.debug("★ RequestHeader : {}", header);
			}
			
			//conn.connect();
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
		
		//for(Entry<String, List<String>> header : conn.getRequestProperties().entrySet()) {
		//	logger.debug("[HEADER] {} : {}", header.getKey(), header.getValue());
		//}
		
		logger.debug("[END] setRequestHeader");
	}
	
	@APIOperation(description="InputStream To String", isExecTest=true)
	String getInputStreamToString(HttpConfigSDO in, HttpURLConnection conn) {
		
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
	
	
	@APIOperation(description="InputBean To JSON", isExecTest=true)
	<T extends AbstractSDO> String inputBeanToJSON(HttpConfigSDO config, T inputObject) {
		
		String out = null;
		try {

			if(config.isEzwelInsideInterface()) {
				UserAgentSDO userAgentSDO = new UserAgentSDO();
				propertyUtil.copySameProperty(config, userAgentSDO, false);
				
				Map<String, Object> insideObject = new LinkedHashMap<String, Object>();
				insideObject.put(APIUtil.getFirstCharLowerCase(userAgentSDO.getClass().getSimpleName()), userAgentSDO);
				insideObject.put(APIUtil.getFirstCharLowerCase(inputObject.getClass().getSimpleName()), inputObject);   
				
				out = beanConvert.toJSONString(insideObject);
				//logger.debug("\n■ input parameter : \n{}", out);
			}
			else {
				out = beanConvert.toJSONString(inputObject);
				//logger.debug("\n■ input parameter : \n{}", out);
			}
		}
		catch(APIException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9000, "■ 입력 SDO를 JSON으로 변환과정에 장애발생", e);
		}
		
		return out;
	}
	
	void wrtieInputJSON(HttpConfigSDO in, HttpURLConnection conn, String inJsonParam) {
		
		OutputStream os = null;
		try {
			os = conn.getOutputStream();
			os.write(inJsonParam.getBytes(in.getEncoding()));
			os.flush();	
			logger.debug("[wrtieInputJSON] OutputStream flush!\n{}", inJsonParam);
		} catch (IOException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "■ JSON Write 장애발생", e);
		} catch (APIException e) {
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
	public <T extends AbstractSDO> T sendJSON(HttpConfigSDO in, Class<T> outputObject) {
		if(in == null) {
			throw new APIException(MessageConstants.RESPONSE_CODE_2000, "인터페이스 필수 입력 객체가 존재하지 않습니다.");
		}
		
		/** 요청 파라메터 여부 */
		in.setDoOutput(false); 
		return sendJSON(in, null, outputObject);
	}
	
	@APIOperation(description="Http URL Communication API (input only)", isExecTest=true)
	public <T extends AbstractSDO> T sendJSON(HttpConfigSDO in, T inputObject) {
		if(in == null) {
			throw new APIException(MessageConstants.RESPONSE_CODE_2000, "인터페이스 필수 입력 객체가 존재하지 않습니다.");
		}
		
		/** 응답 결과 수신 여부 */
		in.setDoInput(false);
		return sendJSON(in, inputObject, null);
	}
	
	
	/**
	 * Http URL Communication API
	 * @param in
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "finally", "unused" })
	@APIOperation(description="Http URL Communication API", isExecTest=true)
	public <T1 extends AbstractSDO, T2 extends AbstractSDO> T2 sendJSON(HttpConfigSDO in, T1 inputObject, Class<T2> outputType) {
		logger.debug("[START] sendJSON {}\n[CHANNEL-INFO] {}\n[USER-INPUT] {}\n[USER-OUTPUT] {}", in.getRestURI(), in, inputObject, outputType);

		if(in == null) {
			throw new APIException(MessageConstants.RESPONSE_CODE_2000, "■ 인터페이스 필수 입력 객체가 존재하지 않습니다.");
		}
		
		//return value
		T2 out = null;
		//HttpURLConn
		HttpURLConnection conn = null;
		//doOutput parameter
		String inJsonParam = null;
		//response original value 
		String responseOrgin = null;
		
		try {
			// 에러 테스트 용
			//in.setRestURI("https://translate.google.com/?hl=ko");
			
			if(in.isDoOutput()) {
				if(inputObject == null) {
					throw new APIException(MessageConstants.RESPONSE_CODE_2000, "■ HttpURLConnection의 isDoOutput가 true일때 inputObject 파라메터가 null일수 없습니다.");
				}
				/** input bean to json */	
				inJsonParam = inputBeanToJSON(in, inputObject);
			}
			else {
				inJsonParam = OperateConstants.STR_BLANK;
			}
			
			int contentLength = APIUtil.NVL(inJsonParam).getBytes(in.getEncoding()).length;
			
			/** getOpenHttpURLConnection */
			conn = getOpenHttpURLConnection(in, contentLength);
			
			/** Set Request Header */
			setRequestHeader(in, conn, contentLength);
			
			logger.debug("■ request isDoOutput : {}", in.isDoOutput());
			/** send request json parameter */
			if(in.isDoOutput()) {
				wrtieInputJSON(in, conn, inJsonParam);
			}
			
			/** 응답 코드 채크 */
			logger.debug("■ responseCode : {}, URI : {}", conn.getResponseCode(), in.getRestURI());
			//200 : ok, 201 : created
			if(conn.getResponseCode() != 200 && conn.getResponseCode() != 201) {
				/** 서버측 에러 발생시 에러메시지 세팅 */
				//logger.error("■ HttpServer Exception '{}'\n{}", in.getRestURI(), (conn.getErrorStream() != null ? IOUtils.toString(new BufferedInputStream(conn.getErrorStream()), in.getEncoding()) : ""));
				throw new APIException(MessageConstants.RESPONSE_CODE_9200, "■ 원격 서버 통신 장애 발생({})\n{}", in.getRestURI(), (conn.getErrorStream() != null ? IOUtils.toString(new BufferedInputStream(conn.getErrorStream()), in.getEncoding()) : ""));
	    	}
			else {
				/** 응답 수신 및 리턴타입 빈으로 변환 */
				if(in.isDoInput()) {

					/** 응답을 읽음 */
					responseOrgin = getInputStreamToString(in, conn); 
					
					if(responseOrgin != null) {
						responseOrgin = responseOrgin.trim();
					} 
					
					logger.debug("■ responseOrgin : {}\n{}", in.getRestURI(), responseOrgin);
					
					if(APIUtil.isNotEmpty(responseOrgin)) {
						
						if(outputType == null) {
							throw new APIException(MessageConstants.RESPONSE_CODE_9000, "■ 인터페이스 응답 결과를 담을 CLASS정보가 존재하지 않습니다. HttpSDO의 outputType class를 설정하세요.");
						}

						/** execute unmarshall */
						logger.debug("■ outputType : {}", outputType);
						out = (T2) beanConvert.fromJSONString/*fromJSON*/(responseOrgin, outputType);
						propertyUtil.setProperty(out, "restURI", in.getRestURI());
						//logger.debug("■ outputType result before : {}", out);
						
						if(APIUtil.isEmpty((String) propertyUtil.getProperty(out, MessageConstants.RESPONSE_CODE_FIELD_NAME))) {
							propertyUtil.setProperty(out, MessageConstants.RESPONSE_CODE_FIELD_NAME, Integer.toString(MessageConstants.RESPONSE_CODE_1000));
						}
						if(APIUtil.isEmpty((String) propertyUtil.getProperty(out, MessageConstants.RESPONSE_MESSAGE_FIELD_NAME))) {
							propertyUtil.setProperty(out, MessageConstants.RESPONSE_MESSAGE_FIELD_NAME, MessageConstants.getMessage(MessageConstants.RESPONSE_CODE_1000).concat(", ").concat(in.getRestURI()));						
						}
						//logger.debug("■ outputType result after : {}", out);
					}
					else {
						logger.debug("■ 인터페이스 응답 내용이 존재하지 않습니다.");
					}
				}
			}
		} catch (SocketTimeoutException e) {
			// Connect | Read timed out 이후 5초후 1 회 다시 호출
			logger.debug("* callCount : {}", in.getCallCount());
			
			if(in.getCallCount() > 0) {
				out = setSendJSONException(e, in, outputType);
			}
			else {
				logger.debug("* 고객 요청으로 인하여 Connection/Read Timeout 발생시 5초후 1회 재시도 ");
				Thread.sleep(5000);
				
				in.setCallCount(1);
				sendJSON(in, inputObject, outputType);
				
				out = setSendJSONException(e, in, outputType);
			}
		} catch (APIException e) {
			out = setSendJSONException(e, in, outputType);
		} catch (Exception e) {
			out = setSendJSONException(e, in, outputType);
		} finally {
			if(conn != null) {
				conn.disconnect();
			}

			logger.debug("[END] sendJSON {}", in.getRestURI());
			return out;			
		}
	}
	
	private <T extends AbstractSDO> T setSendJSONException(Exception e, HttpConfigSDO in, Class<T> outputType) {
		//logger.debug("# setSendJSONException e class : {}", e.getClass().getCanonicalName());
		
		T out = null;
		if(outputType != null) {
			try {
				out = outputType.newInstance();
				
				Integer code = null;
				String message = null;
				if(APIException.class.isAssignableFrom(e.getClass())) {
					code = ((APIException) e).getResultCode();
					message = ((APIException) e).getMessages();
				}
				else {
					code = MessageConstants.RESPONSE_CODE_9100;
					message = ((Exception) e).getMessage();				
				}

				propertyUtil.setProperty(out, MessageConstants.RESPONSE_CODE_FIELD_NAME, Integer.toString(code));
				propertyUtil.setProperty(out, MessageConstants.RESPONSE_MESSAGE_FIELD_NAME, message.concat(", ").concat(in.getRestURI()));
				
				logger.error("■ URL Exception {}", stackTraceUtil.getStackTrace(e));
				e.printStackTrace();
				
			} catch (InstantiationException e1) {
				throw new APIException(MessageConstants.RESPONSE_CODE_9100, "■ 통신 장애 발생.", e);
			} catch (IllegalAccessException e1) {
				throw new APIException(MessageConstants.RESPONSE_CODE_9100, "■ 통신 장애 발생.", e);
			}
		}
		else {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "■ 통신 장애 발생.", e);
		}
		return out;
	}
	
	/**
	 * Create Http URL Communication Certification Properties
	 * @param request
	 * @return
	 */
	@APIOperation(description="Create Http URL Communication Certification Properties", isExecTest=true)
	public Properties getCert(HttpConfigSDO request) {
		logger.debug("[START] getCert : {}", request);
		
		Properties out = null;
		Field[] field = null;
		Object fieldValue = null;
		APIFields fieldAnno = null;
		
		try {
			out = new Properties();
			field = request.getClass().getDeclaredFields();
			
			for(Field item : field) {
				
				if(util.isPassField(item) || ParamValidate.PASS_FIELDS.contains(item.getName())) {
					continue;
				}
				
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
		} catch (IllegalAccessException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9000, "■ HttpConfigSDO필드에 접근할수 없습니다.", e);
		} 
		
		logger.debug("[END] getCert");
		return out;
	}
	
	
	@SuppressWarnings({ "unchecked", "finally" })
	@APIOperation(description="Http URL Multi Communication API", isExecTest=true)
	public <T extends AbstractSDO> List<T> sendMultiJSON(List<MultiHttpConfigSDO> in) {
		logger.debug("[START] sendMultiJSON\nInput Signature : {}", in);
		
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
			
			for(MultiHttpConfigSDO multiHttpConfig : in) {
				Callable<AbstractSDO> callable = new HttpInterfaceHelper(multiHttpConfig);
				//logger.debug("[before] executor.addCall : {}", in);
				// 생성된 callable들을 threadpool에서 수행시키고 결과는 Future 목록에 담는다
				executor.addCall(callable);
			}
			
			List<Future<?>> futures = executor.getResult();
			if(futures != null) {
				logger.debug("- futures.size() : {}", futures.size());
				
				for(Future<?> future : futures) {
					if(future.get() != null) {
						out.add((T) future.get());
					}
				}
			}
			
		} catch (APIException e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "■ 다중 인터페이스 장애 발생", e);
		}
		finally {
			if(executor != null) {
				executor.clear();
			}

			logger.debug("[END] sendMultiJSON");		
			return out;
		}
	}
	
	
	@SuppressWarnings("finally")
	@APIOperation(description="HTTP URL 커넥션 체크(커넥션 체크전용)", isExecTest=true)
	public boolean isHttpConnect(HttpConfigSDO in) {
		
		boolean out = false;
		HttpURLConnection conn = null;
		URL url = null;
		
		//int httpConnTimeout = (in.getConnTimeout() != null ? in.getConnTimeout() : urlConnTimeout);
		int httpConnTimeout = 1000; //커넥트 테스트는 1초로 함
		logger.debug("# isHttpConnect : {}", httpConnTimeout);
		
		try {
			url = new URL(in.getRestURI());
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(false);	//HTTP 응답 결과 수신 여부
			conn.setDoOutput(false); //HTTP 요청 파라메터 송신 여부
			conn.setUseCaches(false);
			conn.setDefaultUseCaches(false);
			conn.setRequestMethod(OperateConstants.HTTP_METHOD_GET);
			conn.setConnectTimeout(httpConnTimeout);
			
			conn.connect();
			out = true;
		} catch (ProtocolException e) {
			logger.error("■ 연결이 불가능한 주소입니다. {}", stackTraceUtil.getStackTrace(e)); 
		} catch (MalformedURLException e) {
			logger.error("■ 프로토콜이 잘못되었습니다. {}", stackTraceUtil.getStackTrace(e));
		} catch (IOException e) {
			logger.error("■ 통신 장애 발생. {}", stackTraceUtil.getStackTrace(e));
		} finally {
			if(conn != null) {
				conn.disconnect();
			}
			
			return out;
		}
	}
	
	
}
