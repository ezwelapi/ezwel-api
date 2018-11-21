package com.ezwel.htl.interfaces.commons.intercepter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractDTO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.constants.OperateCode;
import com.ezwel.htl.interfaces.commons.entity.CommonHeader;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.marshaller.BeanMarshaller;
import com.ezwel.htl.interfaces.commons.spring.ApplicationContext;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;


public class HandlerInterceptor  extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);
	
	private APIUtil apiUtil = (APIUtil) ApplicationContext.getBean(APIUtil.class);
	
	private BeanMarshaller marshaller = (BeanMarshaller) ApplicationContext.getBean(BeanMarshaller.class);
	
	private static final boolean isLogging = false;
	
	public HandlerInterceptor() {
		if(this.apiUtil == null) {
			this.apiUtil = new APIUtil();
		}
		if(this.marshaller == null) {
			this.marshaller = new BeanMarshaller();
		}
	}

	
	/**
	 * This implementation always returns {@code true}.
	 * preHandle - controller 이벤트 호출전
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){

		String typeMethodName = apiUtil.getMethodInfo(handler);
		if(isLogging) {
			logger.debug("[HANDLER START] 'preHandle' controller typeMethodName : {}", typeMethodName);
		}
		
		boolean out = true;
		String requestURI = request.getRequestURI();
		String headerReferer = request.getHeader("referer");
		
		if(isLogging) {
			logger.debug("[HANDLER] RequestURI() : " + requestURI);
		}
		try {
				
			//1.ThreadLocal 초기화
			CommonHeader header = Local.commonHeader();
			//2.서플릿 컨트롤러 타입 저장
			header.setControllerType(apiUtil.getControllerType(handler));
			//3.Request Type Check
			header.setMultipartRequest(apiUtil.isMultipartRequest(request));
			//4.Content Type 
			header.setContentType(apiUtil.getRequestContentType(request));
			//기타 정보 저장
			header.setEncoding(request.getCharacterEncoding());
			header.setClientAddress(apiUtil.getClientAddress(request));
			header.setRequest(request);
			header.setResponse(response);
			
			HandlerMethod handlerMethod = apiUtil.getHandlerMethod(handler);
	
			APIOperation operationAnno = handlerMethod.getMethodAnnotation(APIOperation.class);
	
			if(operationAnno == null) {
				throw new APIException("■■ 유효하지 않은 API 오퍼레이션 APIOperation어노테이션이 존재하지 않습니다. '{}'", typeMethodName);
			}

			APIModel apiModelAnno = null;
			APIFields apiFieldsAnno = null;
			Field[] fields = null; 
			boolean required = false;
			Object fieldValue = null;
			Class<?> fieldType = null;
			AbstractDTO inputParamObject = null;
			String inputParam = apiUtil.readReqeustBodyWithBufferedReader(request);
			
			if(inputParam != null) {
				
				List<AbstractDTO> inputParameters = new ArrayList<AbstractDTO>();
				MethodParameter[] methodParameter = handlerMethod.getMethodParameters();
				for(MethodParameter input : methodParameter) {
					
					if(AbstractDTO.class.isAssignableFrom(input.getParameterType())) {
						//밸리데이션 대상 클래스이면
						apiModelAnno = input.getParameterType().getAnnotation(APIModel.class);
						if(apiModelAnno != null) {
							
							inputParamObject = (AbstractDTO) marshaller.fromJSON(inputParam, input.getParameterType());
							inputParameters.add(inputParamObject);
							
							fields = inputParamObject.getClass().getDeclaredFields();
							for(Field field : fields) {
								if(!apiUtil.isPassField(field)) {
									apiFieldsAnno = field.getAnnotation(APIFields.class);
									fieldValue = field.get(inputParamObject);
									fieldType = field.getType();
									logger.debug("■ Field description : {}", apiFieldsAnno.description());
									
									if(fieldType.isPrimitive()) {
									
										if(apiFieldsAnno.required()) {
											//필수 입력
											if(fieldValue == null) {
												
											}
										}
										if(apiFieldsAnno.maxLength() > 0) {
											//최대 입력 길이 채크
											
										}
										if(apiFieldsAnno.minLength() > 0) {
											//최소 입력 길이 채크
											
										}
										if(APIUtil.isNotEmpty(apiFieldsAnno.pattern())) {
											//정규식 채크
											
										}
									}
									else if(fieldType.isArray() || Collection.class.isAssignableFrom(fieldType)) {
										
									}
									else if(Map.class.isAssignableFrom(fieldType)) {
										
									}
								}
							}
						}
					}
				}
			}
			
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
	public void postHandle( HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
		String typeMethodName = apiUtil.getMethodInfo(handler);
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
	public void afterCompletion( HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
		String typeMethodName = apiUtil.getMethodInfo(handler);
		if(isLogging) {
			logger.debug("[HANDLER START] 'afterCompletion' controller typeMethodName : {}", typeMethodName);
		}
		
		/** 모든처리를 완료하였음 으로 ThreadLocal을 삭제한다. */ 
		Local.remove();
		
		if(isLogging) {
			logger.debug("[HANDLER END] 'afterCompletion' controller typeMethodName : {}", typeMethodName);
		}
	}

	/**
	 * This implementation is empty.
	 * afterConcurrentHandlingStarted - 동시에 핸들링 해주는 메서드
	 */
	public void afterConcurrentHandlingStarted( HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String typeMethodName = apiUtil.getMethodInfo(handler);
		if(isLogging) {
			logger.debug("[HANDLER START] 'afterConcurrentHandlingStarted' controller typeMethodName : {}", typeMethodName);
		}
		
		super.afterConcurrentHandlingStarted(request, response, handler);
		
		if(isLogging) {
			logger.debug("[HANDLER END] 'afterConcurrentHandlingStarted' controller typeMethodName : {}", typeMethodName);
		}
	}
	
	
	
}
