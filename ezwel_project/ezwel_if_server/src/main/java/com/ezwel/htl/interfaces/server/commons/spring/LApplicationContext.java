package com.ezwel.htl.interfaces.server.commons.spring;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.exception.APIException;

/**
 * <pre>
 * 스프링프레임워크에서 필요한 Bean, Request/Response, Session 등의 객체를 
 * 컨텍스트 로더, 컨텍스트 홀더 등에서 직접 취득하는 클래스
 * ps: 스프링프레임워크의 @Autowired(주입) 어노테이션은 시스템내 객체가 많아질수록 속도(성능)저하를 발생시키기떄문에
 *	   스프링 컨텍스트에서 스프링Bean을 직접꺼내어 사용하는 방식으로 속도(성능)을 개선할수 있는 방안으로 개발된 Bean
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 7.
 * @serviceType API
 */
@APIType
public class LApplicationContext implements ApplicationContextAware, BeanNameAware {
	
	private static final Logger logger = LoggerFactory.getLogger(LApplicationContext.class);
	
	private static ApplicationContext applicationContext;
	
	public LApplicationContext() { 
		logger.info("# Init Interface ApplicationContext"); 
	}

	@Autowired(required=true)
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		logger.info("# setApplicationContext : {}", context); 
		LApplicationContext.applicationContext = context;
	}

	/**
	 * <pre>
	 * [메서드 설명]
	 * 바인드된 빈 클래스에 해당하는 빈을 직접 얻습니다.
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @param beans
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 21.
	 */
	public static Object getBean(Class<?> beanName) {
		return applicationContext.getBean(beanName);
	}
	
	/**
	 * <pre>
	 * [메서드 설명]
	 * 바인드된 빈 이르ㅁ에 해당하는 빈을 직접 얻습니다.
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @param beans
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 21.
	 */
	public static Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}

	/**
	 * <pre>
	 * [메서드 설명]
	 * 바인드된 빈이름과 빈클래스에 해당하는 빈을 직접 얻습니다.
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @param beans
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 21.
	 */
	public static <T> T getBean(String beanName, Class<T> requiredType) {
		return applicationContext.getBean(beanName, requiredType);
	}

	/**
	 * <pre>
	 * [메서드 설명]
	 * 빈을 직접 얻습니다.
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @param beans
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 21.
	 */
    public static Object getBeanRootWAC(Class<?> beans) {
    	if(beans == null) {
    		throw new APIException("BEAN CLASS가 존재하지 않습니다.");
    	}
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        
        if(context != null) {
        	return context.getBean(beans);
        }
        else {
        	return null;
        }
    }

	public static Object getBean(final HttpServletRequest request, final String beanId) throws Exception {

		Object beanObject = null;
		ServletContext sc = null;
		HttpSession hs = null;
		WebApplicationContext webApplicationContext = null;
		
		try {
			
			// DispatcherServlet으로 로딩된 context를 가져 온다.
			webApplicationContext = RequestContextUtils.getWebApplicationContext(request);
			// 빈을 검색해서 해당 빈 오브젝트를 가져 온다.
			if (webApplicationContext.containsBean(beanId)) {
				beanObject = webApplicationContext.getBean(beanId);
				return beanObject;
			}

			hs = request.getSession();
			sc = hs.getServletContext();
			// ContextLoaderListener으로 로딩된 context를 가져 온다.
			webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(sc);

			if (webApplicationContext.containsBean(beanId)) {
				beanObject = webApplicationContext.getBean(beanId);
				return beanObject;
			}
		}
		catch(Exception e) {
			throw new APIException(e);
		}
		
		return beanObject;
	}
    		
    /**
     * <pre>
     * [메서드 설명]
     * HttpServletReqeust 객체를 직접 얻습니다.
     * [사용방법 설명]
     * 
     * </pre>
     * @return
     * @author swkim@ebsolution.co.kr
     * @since  2018. 11. 21.
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
        return attr.getRequest();
    }

    /**
     * <pre>
     * [메서드 설명]
     * HttpServletResponse 객체를 직접 얻습니다.
     * [사용방법 설명]
     * 
     * </pre>
     * @return
     * @author swkim@ebsolution.co.kr
     * @since  2018. 11. 21.
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
        return attr.getResponse();
    }

    /**
     * <pre>
     * [메서드 설명]
     * REQUEST 영역에서 객체획득
     * [사용방법 설명]
     * 
     * </pre>
     * @param key
     * @return
     * @author swkim@ebsolution.co.kr
     * @since  2018. 11. 21.
     */
    public static Object getAttrFromRequest(String key) {
        ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
        return attr.getAttribute(key, ServletRequestAttributes.SCOPE_REQUEST);
    }

    /**
     * <pre>
     * [메서드 설명]
     * REQUEST 영역에 객체 저장
     * [사용방법 설명]
     * 
     * </pre>
     * @param key
     * @param obj
     * @author swkim@ebsolution.co.kr
     * @since  2018. 11. 21.
     */
    public static void setAttrToRequest(String key, Object obj) {
        ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
        attr.setAttribute(key, obj, ServletRequestAttributes.SCOPE_REQUEST);
    }

	public void setBeanName(String name) {
		logger.debug("# BeanNameAware : {}", name);
	}


}
