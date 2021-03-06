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
import com.ezwel.htl.interfaces.commons.sdo.IfLogSDO;
import com.ezwel.htl.interfaces.commons.thread.CallableExecutor;
import com.ezwel.htl.interfaces.commons.thread.Local;
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
@APIType(description="HTTP 인터페이스 실행기")
public class HttpInterfaceExecutor {

	private static final Logger logger = LoggerFactory.getLogger(HttpInterfaceExecutor.class);

	@Autowired /** interface_if는 프론트 및 관리자단에서 ezwel 프레임워크 표준인  Autowired를 사용한다. (interface_if_server는 Autowired보다 빠른 스프링 컨텍스트의 getBean을 사용함) */
	private APIUtil util;
	
	@Autowired /** interface_if는 프론트 및 관리자단에서 ezwel 프레임워크 표준인  Autowired를 사용한다. (interface_if_server는 Autowired보다 빠른 스프링 컨텍스트의 getBean을 사용함) */
	private BeanMarshaller beanConvert;
	
	@Autowired /** interface_if는 프론트 및 관리자단에서 ezwel 프레임워크 표준인  Autowired를 사용한다. (interface_if_server는 Autowired보다 빠른 스프링 컨텍스트의 getBean을 사용함) */
	private PropertyUtil propertyUtil;
	
	@Autowired /** interface_if는 프론트 및 관리자단에서 ezwel 프레임워크 표준인  Autowired를 사용한다. (interface_if_server는 Autowired보다 빠른 스프링 컨텍스트의 getBean을 사용함) */
	private StackTraceUtil stackTraceUtil;
	
	/**
	 * Default URL Connection TimeOut 3 Second
	 */
	private final static int URL_CONN_TIMEOUT;
	
	/**
	 * Default Read Time Out 10 Second
	 */
	private final static int URL_READ_TIMEOUT;
	
	/**
	 * 인터페이스 실패시 재시도 횟수
	 */
	private final static int RETRY_COUNT;
	
	/**
	 * 인터페이스 실패시 재시도 간격(텀) 밀리세컨
	 */
	private final static int RETRY_INTERVAL_MILLISECOND;
	
	static {
		
		//default 3 Second
		URL_CONN_TIMEOUT = 3000;
		//default 10 Second
		URL_READ_TIMEOUT = 10000;
		//retry count 2
		RETRY_COUNT = 2;
		//retry interval millisecond 5
		RETRY_INTERVAL_MILLISECOND = 5000;
	}
	
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
		
		int httpConnTimeout = (in.getConnTimeout() != null ? in.getConnTimeout() : URL_CONN_TIMEOUT);
		int httpReadTimeout = (in.getReadTimeout() != null ? in.getReadTimeout() : URL_READ_TIMEOUT);
		logger.debug("[OpenHttpURLConnection] httpConnTimeout : {}, httpReadTimeout : {}", httpConnTimeout, httpReadTimeout);
		
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
		} catch (Exception e) {
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
				in.addRequestProperties(header.getKey(), header.getValue().get(0));
			}
			
			//conn.connect();
		} catch(Exception e) {
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
		logger.debug("[START] inputBeanToJSON");
		
		String out = null;
		try {
			
			if(config.isEzwelInsideInterface() || config.isInsideOnly()) {
				//★ Front or BackOffice 에서 인터페이스 서버를 경유할때 또는 내부 전용인터페이스 일경우
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
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9000, "■ 입력 SDO를 JSON으로 변환과정에 장애발생", e);
		}
		
		logger.debug("[END] inputBeanToJSON");
		return out;
	}
	
	void wrtieInputJSON(HttpConfigSDO in, HttpURLConnection conn, String inJsonParam) {
		logger.debug("[START] wrtieInputJSON");
		
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
		logger.debug("[END] wrtieInputJSON");
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
	@APIOperation(description="Http URL Communication API", isOutsideInterfaceAPI=true)
	public <T1 extends AbstractSDO, T2 extends AbstractSDO> T2 sendJSON(HttpConfigSDO in, T1 inputObject, Class<T2> outputType) {
		logger.debug("[START] sendJSON {} \n[USER-INPUT] {} \n[USER-OUTPUT] {} \n[CHANNEL-INFO] {} ", in.getRestURI(), inputObject, outputType, in);

		if(in == null) {
			throw new APIException(MessageConstants.RESPONSE_CODE_2000, "■ 인터페이스 필수 입력 객체가 존재하지 않습니다.");
		}
		
		/***************************
		 * [START] LOG DATA SETTING 
		 ***************************/
		Local.commonHeader().initInterfaceReqeustLogData(in, APIUtil.currentTimeMillis());
		/***************************
		 * [END]   LOG DATA SETTING 
		 ***************************/
		
		//return value
		T2 out = null;
		//HttpURLConn
		HttpURLConnection conn = null;
		//doOutput parameter
		String inJsonParam = null;
		//response original value 
		String responseOrgin = null;
		//data length
		int contentLength = 0;
		//error contents
		String errContents = null;
		
		try {
			
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
			
			contentLength = APIUtil.NVL(inJsonParam).getBytes(in.getEncoding()).length;
			
			/** getOpenHttpURLConnection */
			conn = getOpenHttpURLConnection(in, contentLength);
			
			/** Set Request Header */
			setRequestHeader(in, conn, contentLength);
			
			/***************************
			 * [START] LOG DATA SETTING 
			 ***************************/
			Local.commonHeader().setInterfaceInputTelegram(in, inJsonParam);
			/***************************
			 * [END]   LOG DATA SETTING 
			 ***************************/
			
			logger.debug("■ request isDoOutput : {}", in.isDoOutput());
			/** send request json parameter */
			if(in.isDoOutput()) {
				wrtieInputJSON(in, conn, inJsonParam);
			}
			
			/** 응답 코드 채크 */
			logger.debug("■ responseCode : {}, ResponseMessage : {}, URI : {}", conn.getResponseCode(), conn.getResponseMessage(), in.getRestURI());
			//200 : ok, 201 : created
			if(conn.getResponseCode() != 200 && conn.getResponseCode() != 201) {
				
				errContents = APIUtil.formatMessage("■ 제휴사 서버 통신 장애 발생({}){}■ 제휴사 서버 에러 내용 : {}", in.getRestURI(), OperateConstants.LINE_SEPARATOR, (conn.getErrorStream() != null ? IOUtils.toString(new BufferedInputStream(conn.getErrorStream()), in.getEncoding()) : ""));
				
				/***************************
				 * [START] LOG DATA SETTING 
				 ***************************/
				setIfLogData(in, out, responseOrgin);
				/***************************
				 * [END]   LOG DATA SETTING 
				 ***************************/
				
				/** 서버측 에러 발생시 에러메시지 세팅 */
				//logger.error("■ HttpServer Exception '{}'\n{}", in.getRestURI(), (conn.getErrorStream() != null ? IOUtils.toString(new BufferedInputStream(conn.getErrorStream()), in.getEncoding()) : ""));
				throw new APIException(MessageConstants.RESPONSE_CODE_9200, errContents);
	    	}
			else {
				/** 응답 수신 및 리턴타입 빈으로 변환 */
				if(in.isDoInput()) {

					/** 응답을 읽음 */
					responseOrgin = getInputStreamToString(in, conn); 
					
					if(responseOrgin != null) {
						responseOrgin = responseOrgin.trim();
					} 
					
					logger.debug("■ responseOrgin url : {}", in.getRestURI()/*, responseOrgin*/);
					
					if(APIUtil.isNotEmpty(responseOrgin)) {
						
						if(outputType == null) {
							throw new APIException(MessageConstants.RESPONSE_CODE_9000, "■ 인터페이스 응답 결과를 담을 CLASS정보가 존재하지 않습니다. HttpConfigSDO의 outputType class를 설정하세요.");
						}

						/** execute unmarshall */
						logger.debug("■ outputType : {}", outputType);
						out = (T2) beanConvert.fromJSONString(responseOrgin, outputType, in.getRestURI());
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
			
			if(in.getCallCount() > (RETRY_COUNT - 1)) {
				out = setSendJSONException(e, in, outputType);
			}
			else {
				
				logger.debug("* 고객 요구사항 Connection/Read Timeout 발생시 5초후 재시도 2회");
				Thread.sleep(RETRY_INTERVAL_MILLISECOND);
				
				in.setCallCount(in.getCallCount() + 1);   
				out = sendJSON(in, inputObject, outputType);
			}
			
		} catch (APIException e) { 
			out = setSendJSONException(e, in, outputType);
		} catch (Exception e) { 
			out = setSendJSONException(e, in, outputType);
		} finally {

			try {
				
				if(conn != null && (conn.getResponseCode() == 200 || conn.getResponseCode() == 201)) {
					
					/***************************
					 * [START] LOG DATA SETTING 
					 ***************************/
					setIfLogData(in, out, responseOrgin);
					/***************************
					 * [END]   LOG DATA SETTING 
					 ***************************/
				}
			} catch (Exception e) {
				logger.error("[Exception-sendJSON] Check ResponseCode 장애 발생", e);
			}

			
			if(conn != null) {
				conn.disconnect();
			}
			
			logger.debug("[END] sendJSON {}", in.getRestURI());
			return out;			
		}
	}
	
	@APIOperation
	private <T extends AbstractSDO> void setIfLogData(HttpConfigSDO in, T out, String responseOrgin) {
		
		//logger.debug("[InterfaceResultLogData] output : {}", out);
		Local.commonHeader().setInterfaceResultLogData(propertyUtil.getProperty(out, MessageConstants.RESPONSE_CODE_FIELD_NAME), responseOrgin, APIUtil.currentTimeMillis());
		
		if(in.isMultiThread() && Local.commonHeader().getIfLogSDO() != null) {
			//스프링 빈이 아닌 callable의 멀티쓰레드로 실행중인경우 로그 세팅
			propertyUtil.setProperty(out, MessageConstants.IF_LOG_FIELD_NAME, Local.commonHeader().getIfLogSDO());
		}
	}
	
	
	@APIOperation(description="통신 장에 내용 세팅", isExecTest=true)
	private <T extends AbstractSDO> T setSendJSONException(Exception e, HttpConfigSDO in, Class<T> outputType) {
		//logger.debug("# setSendJSONException e class : {}", e.getClass().getCanonicalName());
		
		T out = null;
		if(outputType != null) {
			
			try {
				
				out = outputType.newInstance();
				
				Integer code = null;
				String message = null;
				String errCont = null;
				
				if(APIException.class.isAssignableFrom(e.getClass())) {
					code = ((APIException) e).getResultCode();
					message = ((APIException) e).getMessages();
				}
				else if(SocketTimeoutException.class.isAssignableFrom(e.getClass())) {
					code = MessageConstants.RESPONSE_CODE_9102;
					message = ((SocketTimeoutException) e).getMessage();							
				}
				else {
					code = MessageConstants.RESPONSE_CODE_9100;
					message = ((Exception) e).getMessage();				
				}

				message = new StringBuffer().append("■ ").append(message).append(OperateConstants.LINE_SEPARATOR).append("■ RestURI : ").append(in.getRestURI()).toString();
				propertyUtil.setProperty(out, MessageConstants.RESPONSE_CODE_FIELD_NAME, Integer.toString(code));
				propertyUtil.setProperty(out, MessageConstants.RESPONSE_MESSAGE_FIELD_NAME, message);
				
				/***************************
				 * [START] LOG DATA SETTING 
				 ***************************/
				if(Local.commonHeader().getIfLogSDO() != null) {
					
					logger.debug("{}", new StringBuffer().append(OperateConstants.LINE_SEPARATOR)
											 	.append("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ 에러 정보 세팅 ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■").append(OperateConstants.LINE_SEPARATOR)
												.toString());
					
					errCont = message.concat(OperateConstants.LINE_SEPARATOR).concat(APIUtil.formatMessage("■ Trace : {}", new Object[]{stackTraceUtil.getStackTrace(e)}));
					logger.debug("#errCont# {}", errCont);
					Local.commonHeader().getIfLogSDO().setExecMsg(MessageConstants.getMessage(code)); //MessageConstants의 에러 유형 메시지
					Local.commonHeader().getIfLogSDO().setErrCont(errCont);
				}
				/***************************
				 * [END]    LOG DATA SETTING 
				 ***************************/
				logger.error(APIUtil.formatMessage("■ SendJSONException : {}", new Object[]{ message }));
				
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
		logger.debug("[START] getCert"/*, request*/);
		
		Properties out = null;
		Field[] field = null;
		Object fieldValue = null;
		APIFields fieldAnno = null;
		
		try {
			out = new Properties();
			field = request.getClass().getDeclaredFields();
			
			for(Field item : field) {
				
				//필드의 Modifier에 따른 패스 여부 판단 또는 영구 패스 필드일경우 continue
				if(util.isPassField(item) || ParamValidate.PASS_FIELDS.contains(item.getName())) {
					continue;
				}
				
				//내부인터페이스일 때 유효성검사 패스필드로 등록된필드이면 continue
				if(request.isEzwelInsideInterface() && OperateConstants.EZWEL_INSIDE_EXCLUDE_FIELDS.contains(item.getName())) {
					continue;
				}
				
				item.setAccessible(true);
				//APIFields annotation
				fieldAnno = item.getAnnotation(APIFields.class);
				//fieldValue
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
		
		T value = null;
		List<T> out = null;
		IfLogSDO ifLogSDO = null;
		CallableExecutor executor = null;
		Callable<AbstractSDO> callable = null;
		
		try {
		
			if(in == null || in.size() == 0) {
				logger.warn("■ 멀티쓰레드 URL통신에 필요한 설정 목록이 존재하지 않습니다.");
				return out;
			}
			
			executor = new CallableExecutor();
			out = new ArrayList<T>();
			
			//multiHttpConfigList 사이즈만큼 쓰레드를 가진 쓰레드풀 생성
			executor.initThreadPool(in.size());
			
			for(MultiHttpConfigSDO multiHttpConfig : in) {
				//마더 쓰레드 GUID 전달
				multiHttpConfig.setGuid(Local.getId());
				callable = new HttpInterfaceHelper(multiHttpConfig);
				//logger.debug("[before] executor.addCall : {}", in);
				// 생성된 callable들을 threadpool에서 수행시키고 결과는 Future 목록에 담는다
				executor.addCall(callable);
			}
			
			List<Future<?>> futures = executor.getResult();
			if(futures != null) {
				logger.debug("- futures.size() : {}", futures.size());
				
				for(Future<?> future : futures) {
					if(future.get() != null) {
						value = (T) future.get();
						ifLogSDO = value.getIfLog();
						if(ifLogSDO != null) {
							Local.commonHeader().addMultiIfLogList(ifLogSDO);
						}
						
						out.add(value);
					}
				}
			}
			
		} catch (Exception e) {
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
		
		if(!in.isConfirmConnect()) {
			// confirmConnect가 false면 connection check 하지 않음
			return true;
		}
		
		boolean out = false;
		HttpURLConnection conn = null;
		URL url = null;
		
		int httpConnTimeout = (in.getConnTimeout() != null ? in.getConnTimeout() : URL_CONN_TIMEOUT);
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
			logger.error("■ 연결이 불가능한 주소입니다. {}", e.getMessage()); 
		} catch (MalformedURLException e) {
			logger.error("■ 프로토콜이 잘못되었습니다. {}", e.getMessage());
		} catch (IOException e) {
			logger.error("■ 통신 장애 발생. {}", e.getMessage());
		} finally {
			if(conn != null) {
				conn.disconnect();
			}
			
			return out;
		}
	}
	
	
}
