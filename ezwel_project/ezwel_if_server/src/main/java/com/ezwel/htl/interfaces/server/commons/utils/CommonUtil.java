package com.ezwel.htl.interfaces.server.commons.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map.Entry;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.HttpInterfaceExecutorService;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigSDO;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;
/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 21.
 */
@APIType
@Component
public class CommonUtil {

	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	
	@APIOperation(description="요청 파라메터 바디를 BufferReader를 이용하여 읽고 내용을 문자열로 리턴합니다.", isExecTest=true)
    public String readReqeustBodyWithBufferedReader(HttpServletRequest request) {
    	
    	StringBuilder builder = null;
    	
    	try {
    		if(request.getInputStream() != null) {
    			
    			builder = new StringBuilder();
    			BufferedReader input = new BufferedReader(new InputStreamReader(request.getInputStream(), OperateConstants.DEFAULT_ENCODING));
    			String buffer = null;
    			while ((buffer = input.readLine()) != null) {
    				if (builder.length() > 0) {
    					builder.append(OperateConstants.LINE_SEPARATOR);
    				}
    				builder.append(buffer);
    			}
    		}
		} catch (IOException e) {
			throw new APIException(e);
		}		
		return (builder != null ? builder.toString() : null);
    }
 
    
	@APIOperation(description="요청 파라메터 바디를 ByteStreams를 이용하여 읽고 내용을 문자열로 리턴합니다.", isExecTest=true)
    public String readReqestBodyWithByteStreams(HttpServletRequest request) {
    	String out = null;
    	try {
    		if(request.getInputStream() != null) {
    			out = ByteSource.wrap(ByteStreams.toByteArray(request.getInputStream())).asCharSource(Charsets.UTF_8).read();
    		}
		} catch (IOException e) {
			throw new APIException(e);
		}
    	return out;
    }
    	

	
	/**
	 * <pre>
	 * [메서드 설명]
	 * HttpServletRequest의 content-type을 리턴
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @param request
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 20.
	 */
	@APIOperation(description="HttpServletRequest의 content-type을 리턴합니다.", isExecTest=true)
	public String getRequestContentType(HttpServletRequest request) {
		
		String out = null;
		
		String contentType = APIUtil.NVL(request.getContentType(),"").toLowerCase();
		
		if(contentType.indexOf("multipart/form-data; boundary=") > -1) {
			out = contentType.substring(0, OperateConstants.CONTENT_TYPE_MULTIPART_FORM_DATA.length());
		}
		else {
			out = contentType;
		}
		return out;
	}    
    

	/**
	 * <pre>
	 * [메서드 설명]
	 * 	클라이언트 IP를 리턴합니다.
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @param request
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 14.
	 */
	@APIOperation(description="클라이언트 IP를 리턴합니다.", isExecTest=true)
	public String getClientAddress(HttpServletRequest request) {
		
		String clientAddress  = request.getHeader("X-FORWARDED-FOR"); 
		if(clientAddress == null) 
		{ 
			clientAddress = request.getRemoteAddr(); 
		} 
		
		return clientAddress;
	}
	

	/**
	 * <pre>
	 * [메서드 설명]
	 * 	바인드된 객체의 타입@메소드 문자열을 리턴합니다.
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @param handler
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 14.
	 */
	@APIOperation(description="바인드된 객체의 타입@메소드 문자열을 리턴합니다.", isExecTest=true)
	public String getMethodInfo(Object handler){
		String out = null;
		
		if(getControllerType(handler).equals(OperateConstants.SPRING_CONTROLLER)) {
			HandlerMethod method = (HandlerMethod) handler;
			out = method.getBeanType().getCanonicalName().concat("@").concat(method.getMethod().getName());
		}
		else {
			out = handler.getClass().getCanonicalName();
		}
		
		return out;
	}

	/**
	 * <pre>
	 * [메서드 설명]
	 * 	handler 타입을 리턴 (HandlerMethod 또는 DefaultServlet를 리턴)
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @param handler
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 14.
	 */
	@APIOperation(description="handler 타입을 리턴 (HandlerMethod 또는 DefaultServlet를 리턴)", isExecTest=true)
	public String getControllerType(Object handlerParam){
		String out = null;
		Object handler = handlerParam;
		if(handler instanceof HandlerMethod){ 
			out = OperateConstants.SPRING_CONTROLLER;
		}
		else {
			out = OperateConstants.DEFAULT_SERVLET;
		}
		
		return out;
	}
	

	
	/**
	 * <pre>
	 * [메서드 설명]
	 * 	바인드된 객체가 HandlerMethod일 경우 HandlerMethod로 캐스팅하여 리턴합니다.
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @param handler
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 14.
	 */
	@APIOperation(description="바인드된 객체가 HandlerMethod일 경우 HandlerMethod로 캐스팅하여 리턴합니다.", isExecTest=true)
	public HandlerMethod getHandlerMethod(Object handler){
		HandlerMethod method = null;
		if(handler instanceof HandlerMethod){ 
			method = (HandlerMethod) handler;
		}
		return method;
	}
	
	
	@APIOperation(description="핸들링 패스 필드인지 여부를 리턴합니다.", isExecTest=true)
	public boolean isPassField(Field field) {
		boolean out = false;

		if (!Modifier.isPublic(field.getModifiers())
				|| Modifier.isTransient(field.getModifiers())
				|| Modifier.isFinal(field.getModifiers())
				|| Modifier.isStatic(field.getModifiers())) {
			if (logger.isDebugEnabled()) {
				logger.debug("[ field name : " + field.getName()+ " ] modifiers \"" + field.getModifiers()+ "\" is pass");
			}
			out = true;
		}

		return out;
	}	
	
	@APIOperation(description="문자열의 첫번째 문자를 소문자로 변환하여 리턴합니다.", isExecTest=true)
	public static String getFirstCharLowerCase(String strWord) {
		
		String out = null;
		
		String word = APIUtil.NVL(strWord, "").trim();
		if(word.length() > 1) {
			out = word.substring(0,1).toLowerCase() + word.substring(1);
		}
		else {
			out = word.toLowerCase();
		}
		
		return out;
	}
	
	
	/**
	 * 컨트롤러 응답결과 테스트용 유틸
	 * @param in
	 * @param response
	 */
	@APIOperation(description="컨트롤러 응답결과 테스트용 유틸 response header 정보 세팅", isExecTest=true)
	public void setResponseHeader(HttpConfigSDO in, HttpServletResponse response) {
		if(in == null) {
			throw new APIException("에이전트 정보가 존재하지 않습니다. ");
		}
		logger.debug("[컨트롤러 응답결과 테스트용 유틸] in {}", in);
		
		Properties certifications = null;
		
		try {
			
			//Certifications Property
			certifications = new HttpInterfaceExecutorService().getCert(in);
			
			if(in.getRequestProperty() == null) in.setRequestProperty(new Properties());
			in.getRequestProperty().putAll(certifications);
			
			for(Entry<Object, Object> entry : in.getRequestProperty().entrySet()) {
				response.setHeader((String) entry.getKey(), (String) entry.getValue());
			}
			
			/** 강제 유지 프로퍼티 */
			response.setHeader("Content-Type", APIUtil.addString("application/json; charset=", in.getEncoding()));
			response.setHeader("Accept", "application/json");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Content-Length", "1" );
			
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
	
	/**
	 * URL 이미지를 바인드된 경로에 다운로드 하고 저장된 전체경로를 리턴합니다.
	 * @param imageHttpURL 다운로드 할 이미지 URL
	 * @param saveFilePath 저장할 경로
	 * @return
	 */
	@APIOperation(description="URL 이미지를 바인드된 경로에 다운로드 하고 저장된 전체경로를 리턴합니다.")
	public String getImage(String imageHttpURL, String saveDirPath, boolean verbose) {
		logger.debug("[START] getImage imageHttpURL : {}, saveDirPath : {}, verbose : {}", imageHttpURL, saveDirPath, verbose);
		
		File outputFile = new File(saveDirPath);
		
		URL url = null;
		BufferedImage bufferedImage = null;
		String fileExt = null;
		String out = null;
		String fileName = null;
		try {
						
			if(doDirectoryMake(outputFile, true)) {
				
				if(imageHttpURL.startsWith(OperateConstants.DATA_IMAGE_PREFIX) && imageHttpURL.contains(OperateConstants.STR_BASE64)) {
					if(verbose) {
						logger.debug("- base64 data:image");
					}
				}
				else {
					for(Entry<String, String> entry : OperateConstants.IMAGE_EXT.entrySet()) {
						if(imageHttpURL.toLowerCase().endsWith(OperateConstants.STR_DOT.concat(entry.getValue()))) {
							fileExt = entry.getValue();
							fileName = imageHttpURL.substring(imageHttpURL.lastIndexOf(OperateConstants.STR_DOT) + OperateConstants.STR_DOT.length());
							break;
						}
					}
					
					if(fileExt == null) {
						if(verbose) {
							logger.debug("image file extension is not found");
						}
						fileExt = OperateConstants.DEF_IMAGE_EXTENSION;
					}
					
					outputFile = new File(outputFile, fileName);
					url = new URL(imageHttpURL);
					bufferedImage = ImageIO.read(url);
					
					if(verbose) {
						logger.debug("- 이미지 URL : {}", url);
						logger.debug("- 이미지 bufferedImage : {}", bufferedImage);
						logger.debug("- 이미지 파일 확장자 : {}", fileExt);
						logger.debug("- 저장할 파일명 전체 경로를 생성합니다. {}", outputFile.getCanonicalPath());
					}
					
				}
			}
			
		 
		} catch (MalformedURLException e) {
			throw new APIException(e);
		} catch (IOException e) {
			throw new APIException(e);
		} catch (Exception e) {
			throw new APIException(e);
		}
		
		logger.debug("[END] getImage out : {}", out);
		return out;
	}
	
	
    /**
     * FS의 디렉토리 존재여부를 판단하고 존재하지 않는다면 생성합니다.
     * @param directory
     * @param verbose
     */
	public boolean doDirectoryMake(String targetDir, boolean verbose) {
		return doDirectoryMake(new File(targetDir), verbose);
	}
	
    /**
     * FS의 디렉토리 존재여부를 판단하고 존재하지 않는다면 생성합니다.
     * @param directory
     * @param verbose
     */
    public boolean doDirectoryMake(File directory, boolean verbose) {
    	logger.debug("[START] doDirectoryMake directory : {}, verbose : {}", directory, verbose);
    	
    	boolean out = true;
    	
    	try {
    		
    		File dir = directory;

	        if(verbose) {
	        	logger.debug("..디렉토리 존재여부 : " , dir.isDirectory());
	        }
	        
			if(dir.isFile()) {
				if(verbose) {
	        		logger.debug("..디렉토리 생성 대상 경로가 이미 존재하는 파일입니다. 잘못된 경로가 전달되었습니다.");
	        	}
				out = false;
			}
			else if(!dir.isDirectory()){
	        	
	        	if(verbose) {
	        		logger.debug("..디렉토리가 존재하지 않음으로 디렉토리를 생성합니다.");
	        	}
	        	
	            if(!dir.mkdirs()){
	            	
	            	if(verbose) {
	            		logger.debug("..디렉토리 생성 실패.");
	            	}
	                out = false;
	            }else{
	            	out = true;
	            }
	        }
		} catch (Exception e) {
			out = false;
			/** ignore exception "no runtime exception" */
			e.printStackTrace();
		}

    	logger.debug("[END] doDirectoryMake out : {}", out);
        return out;
    }
}

