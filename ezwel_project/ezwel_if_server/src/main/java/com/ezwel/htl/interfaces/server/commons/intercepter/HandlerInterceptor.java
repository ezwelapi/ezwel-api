package com.ezwel.htl.interfaces.server.commons.intercepter;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.entity.CommonHeader;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.marshaller.BeanMarshaller;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.commons.utils.CommonUtil;


public class HandlerInterceptor  extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);
	
	private static final boolean isLogging = true;

	private APIUtil apiUtil;
	
	private CommonUtil commonUtil;
	
	private BeanMarshaller marshaller;
	
	public HandlerInterceptor() {
		logger.debug("[HandlerInterceptor Init]");
	}
	
	/**
	 * This implementation always returns {@code true}.
	 * preHandle - controller 이벤트 호출전
	 */
	@APIOperation(description="preHandle")
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){

		apiUtil = (APIUtil) LApplicationContext.getBean(apiUtil, APIUtil.class);
		marshaller = (BeanMarshaller) LApplicationContext.getBean(marshaller, BeanMarshaller.class);
		commonUtil = (CommonUtil) LApplicationContext.getBean(commonUtil, CommonUtil.class);
		
		String typeMethodName = commonUtil.getMethodInfo(handler);
		if(isLogging) {
			logger.debug("[HANDLER START] 'preHandle' controller typeMethodName : {}", typeMethodName);
		}
		
		boolean out = false;
		String requestURI = request.getRequestURI();
		String headerReferer = request.getHeader("referer");
		
		if(isLogging) {
			logger.debug("[HANDLER] RequestURI() : {}, Referer : {}", requestURI, headerReferer);
		}
		
		try {
				
			//1.ThreadLocal 초기화
			CommonHeader header = Local.commonHeader();
			//2.서플릿 컨트롤러 타입 저장
			header.setControllerType(commonUtil.getControllerType(handler));
			//3.Content Type 
			header.setContentType(commonUtil.getRequestContentType(request));
			//4.요청 인코딩
			header.setEncoding(request.getCharacterEncoding());
			//5.접속자 IP
			header.setClientAddress(commonUtil.getClientAddress(request));
			//6.Request Header 
			if(request.getHeaderNames() != null) {
				String headerName = null;
				String headerValue = null;
				Enumeration<String> headerNames = request.getHeaderNames();
		        while(headerNames.hasMoreElements()){
		            headerName = (String) headerNames.nextElement();
		            headerValue = request.getHeader(headerName);
		            logger.debug("- Intercepter RequestHeader ■ {} : {}", headerName, headerValue);
		            header.addProperties(headerName, headerValue);
		        }
			}
			
			HandlerMethod handlerMethod = commonUtil.getHandlerMethod(handler);
	
			APIOperation operationAnno = handlerMethod.getMethodAnnotation(APIOperation.class);
	
			if(operationAnno == null) {
				throw new APIException("■■ 유효하지 않은 API 오퍼레이션 APIOperation어노테이션이 존재하지 않습니다. '{}'", typeMethodName);
			}
			
			/*
			ParamValidate paramValidator = null;
			APIModel apiModelAnno = null;
			AbstractSDO inputParamObject = null;
			String inputStreamData = null;
			if(header.getContentType().equals(OperateConstants.CONTENT_TYPE_APPLICATION_JSON)) {
				inputStreamData = commonUtil.readReqeustBodyWithBufferedReader(request);
			
				if(inputStreamData != null) {
					
					//List<AbstractDTO> inputParameters = new ArrayList<AbstractDTO>();
					MethodParameter[] methodParameter = handlerMethod.getMethodParameters();
					if(methodParameter.length > 0) {
						//parameter validator
						paramValidator = new ParamValidate(); 
						for(MethodParameter input : methodParameter) {
							
							if(AbstractSDO.class.isAssignableFrom(input.getParameterType())) {
								//밸리데이션 대상 클래스이면
								apiModelAnno = input.getParameterType().getAnnotation(APIModel.class);
								if(apiModelAnno != null) {
									
									inputParamObject = (AbstractSDO) marshaller.fromJSON(inputStreamData, input.getParameterType());
									//inputParameters.add(inputParamObject);
									//validation inputParamObject
									paramValidator.addParam(new ParamValidateSDO(inputParamObject));
								}
							}
						}

						if(paramValidator.getParams() != null && paramValidator.getParams().size() > 0) {
							//execute validator
							paramValidator.execute();
						}
					}
				}
			}
			*/
			
			out = true;
		} catch (Exception e) {
			throw new APIException("인터페이스 인터셉터 장애발생", e);
		}
		
		if(isLogging) {
			logger.debug("[HANDLER END] 'preHandle' controller typeMethodName : {}", typeMethodName);
		}
		return out;
	}
	
	
	/**
	 * This implementation is empty.
	 * postHandle - controller 호출 후 view 페이지 출력전
	 */
	@APIOperation(description="postHandle")
	public void postHandle( HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		commonUtil = (CommonUtil) LApplicationContext.getBean(commonUtil, CommonUtil.class);
		
		String typeMethodName = commonUtil.getMethodInfo(handler);
		if(isLogging) {
			logger.debug("[HANDLER START] 'postHandle' controller typeMethodName : {}", typeMethodName);
		}
		
		/** ezwel api prj에서 로직 필요시 추가 */
		
		if(isLogging) {
			logger.debug("[HANDLER END] 'postHandle' controller typeMethodName : {}", typeMethodName);
		}
	}

	/**
	 * This implementation is empty.
	 * afterCompletion - controller + view 페이지 모두 출력 후
	 * Exception ex : afterCompletion에 전달되는 Exception은 ExceptionResolver 가 처리하지 않는 예외,
	 * 				  Intercepter와 Handler에서 발생하는 예외가 아닌 그 둘을 실행하는 DispatcherServlet이나 그외 부분에서 발생하는 예외를 담고있음.
	 */
	@APIOperation(description="afterCompletion")
	public void afterCompletion( HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		commonUtil = (CommonUtil) LApplicationContext.getBean(commonUtil, CommonUtil.class);
		
		String typeMethodName = commonUtil.getMethodInfo(handler);
		if(isLogging) {
			logger.debug("[HANDLER START] 'afterCompletion' controller typeMethodName : {}, Exception : {}", typeMethodName, ex);
		}
		
		/** 모든처리를 완료하였음 으로 ThreadLocal을 삭제한다. */ 
		Local.commonHeader().setHandlerInterceptorComplete(true);
		
		if(isLogging) {
			logger.debug("[HANDLER END] 'afterCompletion' controller typeMethodName : {}, Exception : {}", typeMethodName, ex);
		}
	}

	/**
	 * This implementation is empty.
	 * afterConcurrentHandlingStarted - 동시에 핸들링 해주는 메서드
	 */
	@APIOperation(description="afterConcurrentHandlingStarted")
	public void afterConcurrentHandlingStarted( HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		commonUtil = (CommonUtil) LApplicationContext.getBean(commonUtil, CommonUtil.class);
		
		String typeMethodName = commonUtil.getMethodInfo(handler);
		if(isLogging) {
			logger.debug("[HANDLER START] 'afterConcurrentHandlingStarted' controller typeMethodName : {}", typeMethodName);
		}
		
		super.afterConcurrentHandlingStarted(request, response, handler);
		
		if(isLogging) {
			logger.debug("[HANDLER END] 'afterConcurrentHandlingStarted' controller typeMethodName : {}", typeMethodName);
		}
	}
	
	
	
}
