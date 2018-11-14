package com.ezwel.htl.interfaces.commons.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * <pre>
 * request/response character encoding filter
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 14.
 */
public class CharacterEncodingFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(CharacterEncodingFilter.class);
	
	private String encoding;
	private boolean forceEncoding = false;
	private boolean cors = false;
	
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void setForceEncoding(boolean forceEncoding) {
		this.forceEncoding = forceEncoding;
	}

	public void setCors(boolean cors) {
		this.cors = cors;
	}

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		/** CORS(Cross Origin Resources Sharing) 웹 클라이언트가 다른 DOMAIN의 URL에게 AJAX로 리소스 요청시 허가 */
		if(this.cors) {
			logger.debug(" [Cross Origin Resources Sharing] ");
	        response.setHeader("Access-Control-Allow-Origin", "*");
	        response.setHeader("Access-Control-Allow-Methods", "GET, POST, HEAD, OPTIONS, DELETE");
	        response.setHeader("Access-Control-Allow-Credentials","true");
	        response.setHeader("Access-Control-Max-Age", "3600");
	        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
	        response.setHeader("Vary", "Origin");
		}
		
		
		if ((this.encoding != null) && (((this.forceEncoding) || (request.getCharacterEncoding() == null)))) {
			
			request.setCharacterEncoding(this.encoding);
			if (this.forceEncoding) {
				response.setCharacterEncoding(this.encoding);
			}
		}

		filterChain.doFilter(request, response);
	}
}
