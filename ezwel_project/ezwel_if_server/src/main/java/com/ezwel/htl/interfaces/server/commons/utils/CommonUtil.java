package com.ezwel.htl.interfaces.server.commons.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.util.WebUtils;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.HttpInterfaceExecutorService;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigSDO;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.commons.utils.data.ImageSDO;
import com.ezwel.htl.interfaces.server.entities.EzcDetailCd;
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
	
	private WebApplicationContext context;
	
	public final static int DEFAULT_FLAGS; 
	
	static {
		DEFAULT_FLAGS = Pattern.CASE_INSENSITIVE | Pattern.DOTALL;
	}
	
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
	
	@APIOperation(description="서버 IP 대역")
	public static String getServerAddress() {
		String out = null;
		String prodServerIpRange = InterfaceFactory.getServerAddress().getProdServerIpRange(); 
		String devServerIpRange = InterfaceFactory.getServerAddress().getDevServerIpRange();
		
		if(APIUtil.isNotEmpty(prodServerIpRange)) {
			
			if(prodServerIpRange.endsWith(".*")) {
				prodServerIpRange = prodServerIpRange.substring(0, prodServerIpRange.indexOf(".*"));
				
				if(InterfaceFactory.LOCAL_HOST_ADDRESS.startsWith(prodServerIpRange)) {
					out = OperateConstants.CURRENT_PROD_SERVER;
				}
			}
			else if(InterfaceFactory.LOCAL_HOST_ADDRESS.equals(prodServerIpRange)) {
				out = OperateConstants.CURRENT_PROD_SERVER;
			}
		}
		else if(APIUtil.isNotEmpty(devServerIpRange)) {
			
			if(devServerIpRange.endsWith(".*")) {
				devServerIpRange = devServerIpRange.substring(0, devServerIpRange.indexOf(".*"));
				
				if(InterfaceFactory.LOCAL_HOST_ADDRESS.startsWith(devServerIpRange)) {
					out = OperateConstants.CURRENT_DEV_SERVER;
				}
			}
			else if(InterfaceFactory.LOCAL_HOST_ADDRESS.equals(devServerIpRange)) {
				out = OperateConstants.CURRENT_DEV_SERVER;
			}
		}
		else {
			out = OperateConstants.CURRENT_PC_SERVER;
		}

		return out;
	}
	
	public static String getImageRootPath() {
		String out = null;
		
		if(getServerAddress() == null) {
			throw new APIException("인터페이스 환경파일에 설정된 개발 또는 운영서버의 IP또는 IP대역과 현제 서버의 IP가 일치하지 않습니다.");
		}
		else if(getServerAddress().equals(OperateConstants.CURRENT_PROD_SERVER)) {
			// prod server
			out = InterfaceFactory.getFileRepository().getBuildImage().getProdRootPath();
		}
		else if(getServerAddress().equals(OperateConstants.CURRENT_DEV_SERVER)) {
			// dev server
			out = InterfaceFactory.getFileRepository().getBuildImage().getDevRootPath();
		}
		else {
			// developer local pc server
			out = InterfaceFactory.getFileRepository().getBuildImage().getLocalRootPath();
		}
		
		return out;
	}
	/**
	 * URL 이미지를 바인드된 경로에 다운로드 하고 저장된 전체경로를 리턴합니다.
	 * @param imageSDO.getImageURL() 다운로드 할 이미지 URL
	 * @param saveFilePath 저장할 경로
	 * @return
	 */
	@APIOperation(description="URL 이미지를 바인드된 경로에 다운로드 하고 저장된 전체경로를 리턴합니다.")
	public ImageSDO getImage(ImageSDO imageSDO, boolean verbose) {
		
		String imageRootPath = getImageRootPath();
		
		String toDate = APIUtil.getFastDate("yyyyMMdd");
		File outputFile = new File(imageRootPath, toDate);
		
		URL url = null;
		BufferedImage bufferedImage = null;
		String fileExt = null;
		String canonicalPath = null;
		String chngFileName = null;
		String orgFileName = null;
		String tempImageURL = null;
		try {
						
			if(doDirectoryMake(outputFile, true)) {
				
				if(imageSDO.getImageURL().startsWith(OperateConstants.DATA_IMAGE_PREFIX) && imageSDO.getImageURL().contains(OperateConstants.STR_BASE64)) {
					if(verbose) {
						logger.debug("- base64 data:image 제휴사에서 데이터 이미지URL을 리턴할경우 추가작업 현재(20181129) 전용필차장과 의논해본결과 리턴URL 패턴 파악이 불가능함");
					}
				}
				else {
					for(Entry<String, String> entry : OperateConstants.IMAGE_EXT.entrySet()) {
						tempImageURL = imageSDO.getImageURL().toLowerCase(); 
						if(tempImageURL.indexOf("?") > -1) {
							tempImageURL = tempImageURL.substring(0, tempImageURL.indexOf("?"));
						}
						if(tempImageURL.endsWith(OperateConstants.STR_DOT.concat(entry.getValue()))) {
							fileExt = entry.getValue();
							orgFileName = APIUtil.NVL(tempImageURL.substring(tempImageURL.lastIndexOf(OperateConstants.STR_SLASH) + OperateConstants.STR_SLASH.length()), "PROGRAM-URL");
							break;
						}
					}
					
					if(fileExt == null) {
						if(verbose) {
							logger.debug("image file extension is not found");
						}
						fileExt = OperateConstants.DEF_IMAGE_EXTENSION;
					}
					
					chngFileName = APIUtil.getId().concat(OperateConstants.STR_DOT).concat(fileExt);
					
					if(verbose) {
						
						logger.debug("- 이미지 orgFileName : {}", orgFileName);
						logger.debug("- 이미지 chngFileName : {}", chngFileName);
					}
					
					outputFile = new File(outputFile, chngFileName);
					url = new URL(imageSDO.getImageURL());
					bufferedImage = ImageIO.read(url);
					
					if(verbose) {
						logger.debug("- 이미지 URL : '{}'", url);
						logger.debug("- 이미지 bufferedImage : {}", bufferedImage);
						logger.debug("- 이미지 파일 확장자 : {}", fileExt);
						logger.debug("- 저장할 파일명 전체 경로를 생성합니다. {}", outputFile.getCanonicalPath());
					}
					
					if(ImageIO.write(bufferedImage, fileExt, outputFile)) {
						canonicalPath = outputFile.getCanonicalPath();
						imageSDO.setSave(true);
						if(verbose) {
							logger.debug("- 이미지 다운로드 성공...");
						}
					}
					
					imageSDO.setFileExt(fileExt);
					imageSDO.setChngFileName(chngFileName);
					imageSDO.setOrgFileName(orgFileName);
					imageSDO.setCanonicalPath(canonicalPath);
				}
			}
			
		 
		} catch (MalformedURLException e) {
			throw new APIException(e);
		} catch (IOException e) {
			throw new APIException(e);
		} catch (Exception e) {
			throw new APIException(e);
		}
		
		return imageSDO;
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

        return out;
    }
    
    @APIOperation(description="공통코드 목록에서 상세 코드에 해당하는 레코드를 리턴합니다.")
	public EzcDetailCd getEzcDetailCdForCodeList(List<EzcDetailCd> detailCdList, String detailCd) {
		EzcDetailCd out = null;
		
		if(detailCdList != null) {
			for(EzcDetailCd detailCdItem : detailCdList) {
				if(detailCdItem != null && detailCdItem.getDetailCd().equals(detailCd)) {
					out = detailCdItem;
					break;
				}
			}
		}
		
		return out;
	}
    
    @APIOperation(description="공통코드 목록에서 상세 코드에 해당하는 마스터 코드문자열을 리턴합니다.")
	public String getMasterCdForCodeList(List<EzcDetailCd> detailCdList, String detailCd) {
		String out = null;
		
		EzcDetailCd outEzcDetailCd = getEzcDetailCdForCodeList(detailCdList, detailCd);
		if(outEzcDetailCd != null) {
			out = outEzcDetailCd.getMasterCd();
		}
		return out;
	}
    
    

    /**
     * 파일명을 제외한 파일 확장자를 반환
     * @param fileName
     * @return
     */
    @APIOperation
    public static String getExt(String canonicalPath) {
        int idx;
        String fileName = canonicalPath;
        if (fileName == null || (idx = fileName.lastIndexOf(OperateConstants.EXT_DELIMETER)) == -1)
            return "";
        else
            return fileName.substring(idx + 1);
    }
    
    /**
     * WebApplicationContext 의 ContextPath 를 구합니다.
     * @return
     * @throws FileNotFoundException
     */
    @APIOperation
    public String getContextPath() {
    	return getContextRealPath(OperateConstants.STR_BLANK);
    }

	/**
	 * 컨텍스트 패스 아래 파일에 대한 실제 경로를 얻기위한 명령은 
	 * getRealPath 만을 사용하도록한다.
	 * @param filePath
	 * @return
	 */
    @APIOperation
    public String getRealPath(String filePath) {
    	
    	if(context != null) {
    		context = LApplicationContext.getWebApplicationContext();
    	}
    	
    	if(context == null && logger.isErrorEnabled()) {
    		logger.error(" WebApplicationContext is null...");
    	}
    	return (context != null ? getContextRealPath(filePath) : filePath);
    }

    @APIOperation
	public File getRealFile(String filePath){
		
		File out = null; 
	
		String canonicalPath = getRealPath(filePath);
		
		if(logger.isDebugEnabled()) {
			logger.debug(APIUtil.addString("canonicalPath : ", canonicalPath));
		}
	
		out = new File(canonicalPath);
	
		if(!out.exists()) {  
			throw new APIException(APIUtil.addString(OperateConstants.STR_DOUBLE_QUOTATION, canonicalPath, "\" file path does not exist."));
		}

		return out;
	}    
    
    /**
     * WebApplicationContext 의 ContextPath 에 문자열 Path 를 연결하여 반환합니다.
     * @param path
     * @return
     * @throws FileNotFoundException
     */
    @APIOperation
    private String getContextRealPath(String currentPath) {
    	
    	String out = null;
    	
    	if(context != null) {
    		context = LApplicationContext.getWebApplicationContext();
    	}
    	
    	try {
    		String current = APIUtil.NVL(currentPath, OperateConstants.STR_BLANK).trim();
        	current = (current.equals(OperateConstants.STR_SLASH) ? OperateConstants.STR_BLANK : current);
        	boolean isCanonicalPath = false;
        	
        	if(OperateConstants.FILE_SEPARATOR.equals(OperateConstants.STR_SLASH) && current.startsWith(OperateConstants.STR_SLASH)) {
        		//linux, unix ( SystemUtil.FILE_SEPARATOR is / )
        		isCanonicalPath = new File(currentPath).exists();
        		if(logger.isDebugEnabled()) {
    	    		logger.debug(" Linux, Unix path : " + isCanonicalPath);
    	    	}
        	}
        	else {
        		//window NT ( SystemUtil.FILE_SEPARATOR is (a-zA-Z):\\ ) 
        		if( testPattern(currentPath, "^[a-zA-Z]:", DEFAULT_FLAGS) ) {
        			isCanonicalPath = true;
        	    	if(logger.isDebugEnabled()) {
        	    		logger.debug(" Window NT path : " + isCanonicalPath);
        	    	}
        		}
        	}
        	
    		if(!isCanonicalPath) {
    			//WebUtils.getRealPath
    			out = WebUtils.getRealPath(context.getServletContext(), OperateConstants.STR_SLASH.concat(current));	
    		}
    		else {
    			//isCanonicalPath
    			out = currentPath;
    		}
			
		} catch (FileNotFoundException e) {
			throw new APIException(e);
		} catch (Exception e) {
			throw new APIException(e);
		}
    	
    	return out; 
    }
    
    @APIOperation
	public boolean testPattern(String objectStr, String patternStr, int flags) {
		
		//if(logger.isDebugEnabled()) {
		//	logger.debug(CommonUtil.mergeObjectString(new Object[]{" *- matcherPatternFind -* objectStr :  " , objectStr , ", patternStr : " , patternStr}));
		//}
		
		boolean findMatcher = false;
		if(APIUtil.isNotEmptyStringArray(new String[]{objectStr, patternStr})){
			Matcher matcher = match(objectStr, patternStr, flags);
			findMatcher = matcher.find();
		}
		return findMatcher;
	}
	
	/**
	 * 주어진 패턴컴파일, Matcher 실행 및 Matcher 리턴
	 * @param contents
	 * @param pattern
	 * @return
	 */
    @APIOperation
	public Matcher match(String contents , String pattern, int flags) {
		String contentStr = APIUtil.NVL(contents);
		String patternStr = APIUtil.NVL(pattern);
		Pattern regex = null;
		if(flags == -1) {
			regex = Pattern.compile(patternStr);
		}
		else {
			regex = Pattern.compile(patternStr, flags);
		}
		return regex.matcher(contentStr);
	}
	
    @APIOperation
	public static boolean existsClass(String... classType) {
		boolean out = false;
		try {
			String[] clazzTypes = classType;
			if( clazzTypes != null ) {
				for(String clazzType : clazzTypes) {
					Class.forName(clazzType);
				}
				out = true;
			}
		} catch (ClassNotFoundException e) {
			if(logger.isDebugEnabled()) {
				logger.debug("ClassNotFound : ".concat(e.getMessage()));
			}
		}
		
		return out;
	}
	
    @APIOperation
	public static String byteSubstring(String str, int sPoint, int length, String encoding) {
	    String out = null;

	    try {
			
			byte[] bytes = str.getBytes(encoding);
			byte[] value = new byte[length];
			
			if(bytes.length < sPoint + length){
				logger.warn("[VALIDATE] byteSubstring => Length of bytes is less. length : {}, sPoint : {}, length : {}", bytes.length, sPoint, length);
				return str;
			}
			
			for(int i = 0; i < length; i++){
				value[i] = bytes[sPoint + i];
			}
			
			/* 
			    logger.debug("utf-8 -> euc-kr        : " + new String(word.getBytes("utf-8"), "euc-kr"));
			    logger.debug("utf-8 -> ksc5601       : " + new String(word.getBytes("utf-8"), "ksc5601"));
			    logger.debug("utf-8 -> x-windows-949 : " + new String(word.getBytes("utf-8"), "x-windows-949"));
			    logger.debug("utf-8 -> iso-8859-1    : " + new String(word.getBytes("utf-8"), "iso-8859-1"));
			    logger.debug("iso-8859-1 -> euc-kr        : " + new String(word.getBytes("iso-8859-1"), "euc-kr"));
			    logger.debug("iso-8859-1 -> ksc5601       : " + new String(word.getBytes("iso-8859-1"), "ksc5601"));
			    logger.debug("iso-8859-1 -> x-windows-949 : " + new String(word.getBytes("iso-8859-1"), "x-windows-949"));
			    logger.debug("iso-8859-1 -> utf-8         : " + new String(word.getBytes("iso-8859-1"), "utf-8"));
			    logger.debug("euc-kr -> utf-8         : " + new String(word.getBytes("euc-kr"), "utf-8"));
			    logger.debug("euc-kr -> ksc5601       : " + new String(word.getBytes("euc-kr"), "ksc5601"));
			    logger.debug("euc-kr -> x-windows-949 : " + new String(word.getBytes("euc-kr"), "x-windows-949"));
			    logger.debug("euc-kr -> iso-8859-1    : " + new String(word.getBytes("euc-kr"), "iso-8859-1"));
			    logger.debug("ksc5601 -> euc-kr        : " + new String(word.getBytes("ksc5601"), "euc-kr"));
			    logger.debug("ksc5601 -> utf-8         : " + new String(word.getBytes("ksc5601"), "utf-8"));
			    logger.debug("ksc5601 -> x-windows-949 : " + new String(word.getBytes("ksc5601"), "x-windows-949"));
			    logger.debug("ksc5601 -> iso-8859-1    : " + new String(word.getBytes("ksc5601"), "iso-8859-1"));
			    logger.debug("x-windows-949 -> euc-kr     : " + new String(word.getBytes("x-windows-949"), "euc-kr"));
			    logger.debug("x-windows-949 -> utf-8      : " + new String(word.getBytes("x-windows-949"), "utf-8"));
			    logger.debug("x-windows-949 -> ksc5601    : " + new String(word.getBytes("x-windows-949"), "ksc5601"));
			    logger.debug("x-windows-949 -> iso-8859-1 : " + new String(word.getBytes("x-windows-949"), "iso-8859-1"));
			*/
			
			out = new String(value, encoding).trim();
		}
	    catch (UnsupportedEncodingException e) {
			throw new APIException(e);
		}
		catch(Exception e) {
			throw new APIException(e);
		} 
	    
	    return out;
	}
	
}

