package com.ezwel.htl.interfaces.commons.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.utils.CommonUtil;

/**
 * <pre>
 * 스프링프레임워크에서 필요한 Bean, Request/Response, Session 등의 객체를 
 * 컨텍스트 로더, 컨텍스트 홀더 등에서 직접 취득하는 클래스
 * ps: 스프링프레임워크의 @Autowired(주입) 어노테이션은 시스템내 객체가 많아질수록 속도(성능)저하를 발생시키기떄문에
 *	   스프링 컨텍스트에서 스프링Bean을 직접꺼내어 사용하는 방식으로 속도(성능)을 개선할수 있는 방안으로 개발된 클래스 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 7.
 * @serviceType API
 */
@APIType
public class ApplicationContext {
	

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
    public static Object getBean(Class<?> beans) {
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

}
