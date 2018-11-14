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

import com.ezwel.htl.interfaces.commons.entity.CommonHeader;
import com.ezwel.htl.interfaces.commons.filter.CharacterEncodingFilter;
import com.ezwel.htl.interfaces.commons.spring.ApplicationContext;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;


public class HandlerInterceptor  extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);
	
	private APIUtil apiUtil = (APIUtil) ApplicationContext.getBean("APIUtil");
	
	private static final boolean isLogging = false;
	
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
			if(logger.isDebugEnabled()) {
				logger.debug("[HANDLER] - isMultipartRequest : " + contentType);
			}
		}
		else {
			if(logger.isDebugEnabled()) {
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
		if(logger.isDebugEnabled()) {
			logger.debug("[HANDLER START] preHandle type info : {}", APIUtil.getMethodInfo(handler));
		}
		
		boolean out = true;
		String requestURI = request.getRequestURI();
		if(logger.isDebugEnabled()) {
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
		header.setClientAddress(APIUtil.getClientAddress(request));
		header.setRequest(request);
		header.setResponse(response);
		
		
		if(interCeptorUtil.isNotIndexForwardMethod(handler)) {
		
			
			
			try {
				

	
				
				//4.공통 메타 데이터 및 매개 변수를 ThreadLocal에 저장한다. 
				if( isMultipartRequest(request) ) {
					//Multipart Request일 경우
					header.initSearchParam((MultipartHttpServletRequest) request);
				}
				else {
					//Normal Request일 경우
					header.initSearchParam(request);
				}

				if(logger.isDebugEnabled()) {
					logger.debug("[HANDLER] Local.searchParam({})\n{}\n", interCeptorUtil.getMethodInfo(handler), Local.searchParam().getParamMap());
				}
				

				
				if(logger.isDebugEnabled()) {
					logger.debug("[HANDLER] client address : " + header.getClientAddress());
				}
				
				//HttpSession이 존재하면
				if(Local.sessionDTO().isSessionOn()) {

					if(header.getControllerType().equals(IOperateCode.SPRING_CONTROLLER)) {
						
						MenuInfoDTO menu = null;
						MenuInfoDTO popupMenu = null;
						HandlerMethod method = interCeptorUtil.getHandlerMethod(handler);
						ParamValidate validate = new ParamValidate();
						
						if( logger.isDebugEnabled() ) {
							logger.debug("[HANDLER] HandlerMethod : " + ( method != null ? method.getMethod().getName() : null ) );
						}
						
						Operation OperationAnno = method.getMethodAnnotation(Operation.class);
							
							
						
						}
						else {
							
							//@Index
							if(IndexAnno != null) {
								
								if(IndexAnno.value().equals(IOperateCode.INDEX_BUSINESS)) {
									//메뉴 권한 및 코드 조회 (개발완료후 이곳으로 인덱스 비즈니스 메소드의 코드조회 이동한다)
									
									//# 파라메터 Validate 
									validate.addParam(new ParamValidateDTO(Local.searchParam().get(IOperateCode.SEARCH_KEY_MENU_CD), new String[]{"required"}, MessageHelper.message("ISNOT_EXIST_PARAMS", "["+requestURI+"] 메뉴코드가")));
									validate.execute();
									
									//# 메뉴 정보와 사용자 권한을 조회한다. 
									menu = controlBean.findFastMenu();
									
									//# Set Data Object In UI Model 
									//> header.setMenu(menu);
									
									//# 코드를 조회하도록 설정되어 있으면
									if(IndexAnno.findCode()) {
										//# 화면에서 사용하는 코드를 조회한다. 
										Map<String, List<ComCdDatDTO>> codes = controlBean.findFastCodes(menu, method.getMethod());
										if(logger.isDebugEnabled()) {
											logger.debug("[HANDLER] codes size : " + (codes != null ? codes.size() : 0));
										}
										
										//# Set Data Object In UI Model 
										//> header.setCodes(codes);
									}
								}
								else if(IndexAnno.value().equals(IOperateCode.INDEX_POPUP)) {
									//메뉴 권한 및 팝업화면코드 조회 (개발완료후 이곳으로 인덱스 팝업 메소드의 코드조회 이동한다)
									
									//# 파라메터 Validate 
									validate.addParam(new ParamValidateDTO(Local.searchParam().get(IOperateCode.SEARCH_KEY_MENU_CD), new String[]{"required"}, MessageHelper.message("ISNOT_EXIST_PARAMS", "["+requestURI+"] 메뉴코드가")));
									validate.addParam(new ParamValidateDTO(Local.searchParam().get(IOperateCode.SEARCH_KEY_CD_MST_MENU_CD), new String[]{"required"}, MessageHelper.message("ISNOT_EXIST_PARAMS", "["+requestURI+"] 코드마스터 조회용 메뉴코드가")));
									validate.execute();
									
									//# 메뉴 정보와 사용자 권한을 조회한다.(사용자권한조회용) 
									menu = controlBean.findFastMenu();
									
									//# 메뉴 정보와 사용자 권한을 조회한다.(팝업화면코드조회용) 
									popupMenu = controlBean.findFastMenu(Local.searchParam().getString(IOperateCode.SEARCH_KEY_CD_MST_MENU_CD));
									
									//# 화면에서 사용하는 코드를 조회한다. 
									Map<String, List<ComCdDatDTO>> codes = controlBean.findFastCodes(popupMenu, method.getMethod());
									if(logger.isDebugEnabled()) {
										logger.debug("[HANDLER] popup codes size : " + (codes != null ? codes.size() : 0));
									}
									
									//# Set Data Object In UI Model 
									//> header.setCodes(codes);
									//> header.setMenu(menu);
									//> header.setPopupMenu(popupMenu);
								}
								else if(IndexAnno.value().equals(IOperateCode.INDEX_MAIN)) {
									
									//# 메뉴 정보와 사용자 권한을 조회한다. 
									menu = controlBean.findFastMenu("PrjDashBord");
									
									//# Set Data Object In UI Model 
									//> header.setMenu(menu);
									
									//# 코드를 조회하도록 설정되어 있으면
									if(IndexAnno.findCode()) {
										//# 화면에서 사용하는 코드를 조회한다. 
										Map<String, List<ComCdDatDTO>> codes = controlBean.findFastCodes(menu, method.getMethod());
										if(logger.isDebugEnabled()) {
											logger.debug("[HANDLER] codes size : " + (codes != null ? codes.size() : 0));
										}
										
										//# Set Data Object In UI Model 
										//> header.setCodes(codes);
									}
								}
							}
							else if( OperationAnno != null ) {
								//# 파라메터 Validate 
								validate.addParam(new ParamValidateDTO(Local.searchParam().get(IOperateCode.SEARCH_KEY_MENU_CD), new String[]{"required"}, MessageHelper.message("ISNOT_EXIST_PARAMS", "["+requestURI+"] 메뉴코드가")));
								validate.execute();
								
								//# 메뉴 정보와 사용자 권한을 조회한다. 
								menu = controlBean.findFastMenu();
								
								//# Set Data Object In UI Model 
								//> header.setMenu(menu);
								
								if( OperationAnno.findCode() ) {
									//# 코드를 조회한다. 
									Map<String, List<ComCdDatDTO>> codes = controlBean.findFastCodes(menu, method.getMethod());
									if(logger.isDebugEnabled()) {
										logger.debug("[HANDLER] codes size : " + (codes != null ? codes.size() : 0));
									}
									
									//# Set Data Object In UI Model 
									//> header.setCodes(codes);
								}
							}
						}
					}

					out = true;
				}
				else {
					if(logger.isDebugEnabled()) {
						logger.debug("[HANDLER] ".concat(MessageHelper.message("ISNOT_EXIST_HTTP_SESSION", "사용자")));
						logger.debug("[HANDLER] ControllerType : " + header.getControllerType());
					}
					//세션이 존재하지 않고 DWR컨트롤러이면 false (로그인처리 컨트롤러가 아닐경우만 해당)
					if(header.getControllerType().equals(IOperateCode.DWR_CONTROLLER)) {
						
						//로그인 화면일경우는 제외
						if(requestURI.indexOf(PCUserUnifLogn.class.getSimpleName().substring(2)) == -1 && requestURI.indexOf("__System.generateId") == -1) {
							out = false;
						}
					}
				}
				
				header.setRunController(out);
				
			} catch (Exception e) {
				
				header.setRunController(out);
				throw new ApplicationException("프로세서 인터셉터 장애발생 관리자에게 문의하세요.", e);
			}
		}
		else {
			out = true;
			if(logger.isDebugEnabled()) {
				logger.debug("[HANDLER] isIndexForwardMethod");
			}			
		}
		
		if(logger.isDebugEnabled()) {
			logger.debug("[HANDLER END] preHandle Controller type info : {}", interCeptorUtil.getMethodInfo(handler));
		}
		return out;
	}
	
	
	/**
	 * This implementation is empty.
	 * postHandle - controller 호출 후 view 페이지 출력전
	 */
	public void postHandle( HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if(logger.isDebugEnabled()) {
			logger.debug("[HANDLER START] postHandle type info : {}", interCeptorUtil.getMethodInfo(handler));
		}
		
		if(interCeptorUtil.isNotIndexForwardMethod(handler)) {
			
			//스프링 컨트롤러 의 모델 맵이 존재할경우 추가로 필요한 객체를 담는다. DWR컨트롤러일경우 해당사항 없음.
			if(logger.isDebugEnabled()) {
				logger.debug("[HANDLER] postHandle modelAndView : {}", modelAndView);
			}
			ModelMap modelMap = null;
			if(modelAndView != null) {
				modelMap = modelAndView.getModelMap();

				modelMap.addAttribute(sessionManager.getSessionName(), Local.sessionDTO()); 
				modelMap.addAttribute(IOperateCode.PARAM_META_OPERATE_CODE, new OperateSetup());
				modelMap.addAttribute(IOperateCode.PARAM_META_SEARCH_PARAM, Local.searchParam());
				//> modelMap.addAttribute(IOperateCode.PARAM_META_MENU, Local.menu());
				//> modelMap.addAttribute(IOperateCode.PARAM_META_POPUP_MENU, Local.popupMenu());
				//> modelMap.addAttribute(IOperateCode.PARAM_META_CODES, Local.codes());	
				
				//model에 담긴 객체를 받는다.
				//SearchParam searchParam = (SearchParam) modelAndView.getModel().get(IOperateCode.PARAM_META_SEARCH_PARAM);
				
				if( !Local.sessionDTO().isSessionOn() ){
					HandlerMethod method = interCeptorUtil.getHandlerMethod(handler);
					Index IndexAnno = method.getMethodAnnotation(Index.class);
					if( IndexAnno != null ) {
						if( IndexAnno.value().equals(IOperateCode.INDEX_MAIN)) {
							if(logger.isDebugEnabled()) {
								logger.debug("[HANDLER] [(메인)세션이 만료 로그인페이지로 이동]");
							}
							//메인 화면 접근시 세션이 없을경우 로그인화면으로 돌려보낸다.
							modelAndView.setViewName("/ubms/common/info/indexInvalidSession");
						}
						else if( IndexAnno.value().equals(IOperateCode.INDEX_LOGIN)) {
							//로그인 페이지
							if(logger.isDebugEnabled()) {
								logger.debug("[HANDLER] [로그인 페이지 접속]");
							}
						}
						else {
							if(logger.isDebugEnabled()) {
								logger.debug("[HANDLER] [(업무화면)세션 만료]");
							}
							// 세션이 존재하지 않을경우 화면에 세션만료 메시지팝업을띠우고 로그인화면으로 돌려보낸다.
							modelAndView.setViewName("/ubms/common/info/invalidSession");
						}
					}
					
				}
			}
			else {
				//DWR인경우 처리
				
				if(!Local.sessionDTO().isSessionOn()) {

					//DWR인 경우 세션이 없을때...
					if(logger.isDebugEnabled()) {
						logger.debug("[HANDLER] MethodInfo info : {}", interCeptorUtil.getMethodInfo(handler));
						logger.debug("[HANDLER] DWR Contoller 세션 만료");
					}
					//response.sendRedirect(request.getContextPath() + "/process/Main/index"+IOperateCode.REQUEST_EXTENSION);

				}
				
			}
		}
		if(logger.isDebugEnabled()) {
			logger.debug("[HANDLER END] postHandle type info : {}", interCeptorUtil.getMethodInfo(handler));
		}		
	}

	/**
	 * This implementation is empty.
	 * afterCompletion - controller + view 페이지 모두 출력 후
	 * Exception ex : afterCompletion에 전달되는 Exception은 ExceptionResolver 가 처리하지 않는 예외,
	 * 				  Intercepter와 Handler에서 발생하는 예외가 아닌 그 둘을 실행하는 DispatcherServlet이나 그외 부분에서 발생하는 예외를 담고있음.
	 */
	public void afterCompletion( HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		if(logger.isDebugEnabled()) {
			logger.debug("[HANDLER START] afterCompletion type info : {}", interCeptorUtil.getMethodInfo(handler));
		}
		if(interCeptorUtil.isNotIndexForwardMethod(handler)) {
			//1.모든처리를 완료하였음 으로 ThreadLocal을 삭제한다. 
			Local.remove();
		}
		if(logger.isDebugEnabled()) {
			logger.debug("[HANDLER END] afterCompletion type info : {}", interCeptorUtil.getMethodInfo(handler));
		}	
	}

	/**
	 * This implementation is empty.
	 * afterConcurrentHandlingStarted - 동시에 핸들링 해주는 메서드
	 */
	public void afterConcurrentHandlingStarted( HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(logger.isDebugEnabled()) {
			logger.debug("[HANDLER START] afterConcurrentHandlingStarted type info : {}", interCeptorUtil.getMethodInfo(handler));
		}
		
		super.afterConcurrentHandlingStarted(request, response, handler);
		
		if(logger.isDebugEnabled()) {
			logger.debug("[HANDLER END] afterConcurrentHandlingStarted type info : {}", interCeptorUtil.getMethodInfo(handler));
		}
	}
	
	
	
}
