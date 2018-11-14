package com.ezwel.htl.interfaces.commons.intercepter;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.entity.CommonHeader;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.filter.CharacterEncodingFilter;
import com.ezwel.htl.interfaces.commons.spring.ApplicationContext;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;


public class HandlerInterceptor  extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);
	
	private APIUtil apiUtil = (APIUtil) ApplicationContext.getBean(APIUtil.class);
	
	private static final boolean isLogging = false;
	
	public HandlerInterceptor() {
		if(this.apiUtil == null) {
			this.apiUtil = new APIUtil();
		}
	}
	
	/**
	 * 요청된 데이터 콘텐츠 타입이 멀티파트폼데이터인지 체크하여 줍니다.
	 * @param request
	 * @return
	 */
	public boolean isMultipartRequest(HttpServletRequest request) {
		
		boolean result = false;
		
		String contentType = APIUtil.NVL(request.getContentType(),"").toLowerCase();
		
		if(contentType.indexOf("multipart/form-data; boundary=") > -1) {
			result = true;
			if(isLogging) {
				logger.debug("[HANDLER] - isMultipartRequest : " + contentType);
			}
		}
		else {
			if(isLogging) {
				logger.debug("[HANDLER] - isNormalRequest : " + contentType);
			}
		}
		return result;
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
		if(isLogging) {
			logger.debug("[HANDLER] RequestURI() : " + requestURI);
		}

		//1.ThreadLocal 초기화
		CommonHeader header = Local.commonHeader();
		//2.서플릿 컨트롤러 타입 저장
		header.setControllerType(APIUtil.getControllerType(handler));
		//3.Request Type Check
		header.setMultipartRequest(isMultipartRequest(request));
		//기타 정보 저장
		header.setEncoding(request.getCharacterEncoding());
		header.setClientAddress(apiUtil.getClientAddress(request));
		header.setRequest(request);
		header.setResponse(response);
		
		HandlerMethod method = apiUtil.getHandlerMethod(handler);
		APIOperation operationAnno = method.getMethodAnnotation(APIOperation.class);

		if(operationAnno == null) {
			throw new APIException("■■ 유효하지 않은 API 오퍼레이션 APIOperation어노테이션이 존재하지 않습니다. '{}'", typeMethodName);
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
