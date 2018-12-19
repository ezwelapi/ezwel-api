package com.ezwel.htl.interfaces.server.commons.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.Charsets;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
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
import com.ezwel.htl.interfaces.commons.http.HttpInterfaceExecutor;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigSDO;
import com.ezwel.htl.interfaces.commons.sdo.ImageSDO;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.commons.utils.StackTraceUtil;
import com.ezwel.htl.interfaces.server.commons.morpheme.ko.KoreanAnalyzer;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.entities.EzcDetailCd;
import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;
/**
 * <pre>
 * if server 공통 유틸
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 21.
 */
@APIType
@Component
public class CommonUtil {

	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	
	private WebApplicationContext context;
	
	private PropertyUtil propertyUtil;
	
	private StackTraceUtil stackTraceUtil;
	
	public final static int DEFAULT_FLAGS; 
	
	static {
		DEFAULT_FLAGS = Pattern.CASE_INSENSITIVE | Pattern.DOTALL;
	}
	
	@APIOperation(description="요청 파라메터 바디를 BufferReader를 이용하여 읽고 내용을 문자열로 리턴합니다.", isExecTest=true)
	public String readReqeustBodyWithBufferedReader() {
		return readReqeustBodyWithBufferedReader(null);
	}
	
	@APIOperation(description="요청 파라메터 바디를 BufferReader를 이용하여 읽고 내용을 문자열로 리턴합니다.", isExecTest=true)
    public String readReqeustBodyWithBufferedReader(HttpServletRequest request) {
    	
		HttpServletRequest currentRequest = null;
		if(request == null) {
			currentRequest = LApplicationContext.getRequest();
		}
		else {
			currentRequest = request; 
		}
    	StringBuilder builder = null;
    	
    	
    	try {

    		if(currentRequest.getMethod().equals(OperateConstants.HTTP_METHOD_POST)) {
    			
    			logger.debug("[InputStream] : {}", currentRequest.getInputStream());
        		if(currentRequest.getInputStream() != null) {
        			
        			builder = new StringBuilder();
        			//BufferedReader input = currentRequest.getReader();
        			BufferedReader input = new BufferedReader(new InputStreamReader(currentRequest.getInputStream(), OperateConstants.DEFAULT_ENCODING));
        			String buffer = null;
        			while ((buffer = input.readLine()) != null) {
        				//if (builder.length() > 0) {
        				//	builder.append(OperateConstants.LINE_SEPARATOR);
        				//}
        				builder.append(buffer);
        			}
        		}	
        	}
    		else if(currentRequest.getMethod().equals(OperateConstants.HTTP_METHOD_GET)) {
    		    			
    		}
    		
		} catch (IOException e) {
			throw new APIException(e);
		}
		return (builder != null ? builder.toString().trim() : null);
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
			certifications = new HttpInterfaceExecutor().getCert(in);
			
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
	 * URL 이미지를 저장할 디렉토리를 만들고 FS에 저장할 실제 파일경로를 포함한 Object를 리턴합니다. (다운로드는 하지 않습니다.)
	 * @param imageSDO
	 * @param verbose
	 * @return
	 */
	@APIOperation(description="URL 이미지 URL의 패턴을 검증하고 FS에 저장할 실제 파일경로를 포함한 Object를 리턴합니다. (다운로드는 하지 않습니다.)")
	public ImageSDO getImageDownload(ImageSDO imageSDO, boolean verbose) {
		if(imageSDO == null) {
			throw new APIException("[getSaveImagePath] Input Prameter ImageSDO is Null...");
		}
		logger.debug("[START] getSaveImagePath URL : {}", imageSDO.getImageURL());
		
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		stackTraceUtil = (StackTraceUtil) LApplicationContext.getBean(stackTraceUtil, StackTraceUtil.class);
		
		String imageRootPath = InterfaceFactory.getImageRootPath();
		String childPath = null;
		
		if(APIUtil.isNotEmpty(imageSDO.getChildPath())) {
			// 에이젼트ID / 도시코드 / 지역코드
			childPath = imageSDO.getChildPath();
		}
		else {
			// 저장 경로 파라메터가 없을경우 년 / 월 / 일
			childPath = new StringBuffer().append(APIUtil.getFastDate("yyyy")).append(File.separator)
					.append(APIUtil.getFastDate("MM")).append(File.separator)
					.append(APIUtil.getFastDate("dd")).toString();
		}
		
		File outputFile = null;
		URL url = null;
		BufferedImage bufferedImage = null;
		String fileExt = null;
		String chngFileName = null;
		String orgFileName = null;
		String lowerImageURL = null;
		String imageURL = (imageSDO.getImageURL() != null ? imageSDO.getImageURL().trim() : null);
		
		ImageSDO namedSDO = new ImageSDO();
		try {
			
			if( APIUtil.isEmpty(imageURL) ) {
				logger.error("이미지 URL이 존재하지 않습니다.");
			}
			else {
				if(verbose) {
					logger.debug("Starting image url download...");
				}
				
				//Directory Setting
				outputFile = new File(imageRootPath, childPath);
				imageSDO.setDirectoryPath(outputFile.getPath());

				url = new URL(imageURL);

				lowerImageURL = imageURL.toLowerCase();
				if (lowerImageURL.startsWith(OperateConstants.DATA_IMAGE_PREFIX) && lowerImageURL.contains(OperateConstants.STR_BASE64)) {
					logger.warn("- base64 data:image 제휴사에서 이미지URL을 데이터:이미지 base64 URL로 리턴할경우 추가작업 필요. 현재(20181129) 전용필차장과 의논해본결과 리턴URL 패턴 파악이 불가능하다고함.");
				} 
				else if (url.toURI().parseServerAuthority() != null) /* URL 패턴 검증 */ {

					if (doDirectoryMake(outputFile, true)) {
						// 이미지 확장자를 찾음
						for (Entry<String, String> entry : OperateConstants.IMAGE_EXT.entrySet()) {
							lowerImageURL = imageURL.toLowerCase();

							if (lowerImageURL.indexOf("?") > -1) {
								lowerImageURL = lowerImageURL.substring(0, lowerImageURL.indexOf("?"));
							}

							if (lowerImageURL.endsWith(OperateConstants.STR_DOT.concat(entry.getValue()))) {
								// URL 이미지 확장자
								fileExt = entry.getValue();
								break;
							}
						}

						if (fileExt == null) {
							if (verbose) {
								logger.debug("image file extension is not found. set default image extension.");
							}
							fileExt = OperateConstants.DEF_IMAGE_EXTENSION;
						}

						// URL 이미지 실제 이름
						orgFileName = imageURL.substring(imageURL.lastIndexOf(OperateConstants.STR_SLASH) + OperateConstants.STR_SLASH.length());
						
						if (orgFileName.contains(OperateConstants.STR_DOT)) {
							orgFileName = orgFileName.substring(0, orgFileName.indexOf(OperateConstants.STR_DOT));
						}

						// 파일시스템에 저장 할 파일명
						chngFileName = APIUtil.getMD5HashString(orgFileName).concat(OperateConstants.STR_DOT).concat(fileExt);
						// 파일시스템에 저장 할 파일
						outputFile = new File(outputFile, chngFileName);

						if (verbose) {
							logger.debug("- 이미지 파일명 : {}", orgFileName);
							logger.debug("- 이미지 FS저장 파일명 : {}", chngFileName);
							logger.debug("- 이미지 확장자 : {}", fileExt);
							logger.debug("- 저장할 파일 전체 경로 : {}", outputFile.getPath());
						}

						namedSDO = (ImageSDO) propertyUtil.copySameProperty(imageSDO, ImageSDO.class);
						// URL 이미지 읽음
						bufferedImage = ImageIO.read(url);
						// URL 이미지 작성
						namedSDO.setSave(ImageIO.write(bufferedImage, fileExt, outputFile));
						// 저장소 루트를 제외한 경로
						namedSDO.setFileExt(fileExt);
						namedSDO.setFileSize(outputFile.length());
						namedSDO.setChngFileName(chngFileName);
						namedSDO.setOrgFileName(orgFileName);
						namedSDO.setCanonicalPath(outputFile.getCanonicalPath());
						namedSDO.setRelativePath(byteSubstring(outputFile.getCanonicalPath(),
															   imageRootPath.getBytes(OperateConstants.DEFAULT_ENCODING).length,
															   OperateConstants.DEFAULT_ENCODING));
					}
				}
				else {
					logger.warn("[WARN] 이미지 URL이 잘못되었습니다. => {}", imageURL);
				}
			}
		} catch (IOException e) {
			namedSDO.setDescription("[ERROR] ".concat(e.getMessage()));
			logger.error("[getSaveImagePath] IOException : {} {}", imageURL, stackTraceUtil.getStackTrace(e));
		} catch (Exception e) {
			namedSDO.setDescription("[ERROR] ".concat(e.getMessage()));
			logger.error("[getSaveImagePath] Exception : {} {}", imageURL, stackTraceUtil.getStackTrace(e));
		}
		
		logger.debug("[END] getSaveImagePath {} : {}", imageRootPath, namedSDO.getRelativePath());
		return namedSDO;
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
	        	logger.debug("..디렉토리 존재여부 : {}" , dir.isDirectory());
	        }
	        
			if(dir.isFile()) {
				if(verbose) {
	        		logger.debug("..디렉토리 생성 대상 경로가 이미 존재하는 파일입니다. 잘못된 경로가 전달되었습니다.");
	        	}
				out = false;
			}
			else if(!dir.isDirectory()){
	        	
	        	if(verbose) {
	        		logger.debug("..디렉토리가 존재하지 않음으로 디렉토리를 생성합니다. {}", directory.getPath());
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
    public static String byteSubstring(String str, int sPoint, String encoding) {
    	return byteSubstring(str, sPoint, -1, encoding);
    }
    
    @APIOperation
	public static String byteSubstring(String userSentence, int userStartPoint, int userEndPoint, String userEncoding) {
	    String out = null;

	    try {
	    	String sentence = userSentence; 
	    	int startPoint = userStartPoint;
	    	int endPoint = userEndPoint;
	    	String encoding = userEncoding;
	    	
			byte[] bytes = sentence.getBytes(encoding);

	    	if(endPoint < 0) {
	    		endPoint = bytes.length - startPoint;
	    	}
	    	
			byte[] value = new byte[endPoint];
			
			if(bytes.length < startPoint + endPoint){
				logger.warn("[VALIDATE] byteSubstring => Length of bytes is less. length : {}, startPoint : {}, endPoint : {}", bytes.length, startPoint, endPoint);
				return sentence;
			}
			
			for(int i = 0; i < endPoint; i++){
				value[i] = bytes[startPoint + i];
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
	
	/**
	 * 파일 생성
	 * @param fileDir	: 생성할파일 이름을 포함한 전체경로
	 * @param fileName	: 생성할파일 이름을 포함한 전체경로
	 * @param contents	: 생성한 파일에 쓰기할 내용
	 * @param encoding	: 파일인코딩
	 * @param inheritfile	: 파일이 존재할 경우 파일안에 내용을 추가할것인지 여부
	 * @param verbose	: 파일 생성 로깅을 할것인지 여부
	 * @return
	 */
    @APIOperation
    public File mkfile(String fileDir, String fileName, String contents, String encoding, boolean inheritfile, boolean verbose) {

    	if (logger.isDebugEnabled() && verbose) {
    		logger.debug("[START] mkfile\n mkfile fileDir : {}\n mkfile fileName : {}\n mkfile fileContents : {}" , fileDir , fileName , contents);
    		logger.debug("..wget() mkfile start.. ");
    	}

    	String mkfileName = "";		// 파일 이름

    	if(fileDir != null) {
    		//mkdir(fileDir, verbose);
    		doDirectoryMake(fileDir, true); // 디렉토리를 채크하고 없으면 생성한다.
    		mkfileName = fileDir.concat(OperateConstants.STR_SLASH).concat(fileName);	// 경로를 포함한 파일 이름
    	}else{
    		mkfileName = fileName;	// 경로를 포함한 파일 이름
    	}

        File file = new File(mkfileName);	// 파일 생성
        OutputStreamWriter out = null;
        String fileContent = APIUtil.NVL(contents);

        if (logger.isDebugEnabled() && verbose) {
        	logger.debug("..new file() will save to {}", mkfileName);
        }
		try {
			out = new OutputStreamWriter(new FileOutputStream(file, inheritfile), encoding); // 파일에 문자를 적을 스트림 생성
			if (logger.isDebugEnabled() && verbose) {
				logger.debug ("..new file() make stream ");
			}
			
			out.write(fileContent); // 파일에 쓰기
			if (logger.isDebugEnabled() && verbose) {
				logger.debug ("..new file() write contents. ");
			}
			
			out.flush(); // 파일 에 문자열전달
			if (logger.isDebugEnabled() && verbose) {
				logger.debug ("..new file() flush ");
			}
		}
		catch (UnsupportedEncodingException e) {
			throw new APIException(e);
		}
		catch (FileNotFoundException e) {
			throw new APIException(e);
		}
		catch (IOException e) {
			throw new APIException(e);
		}
		finally {
	        try {
				if(out != null) {
					out.close(); // 스트림 닫기
					if (logger.isDebugEnabled() && verbose) {
						logger.debug ("..new file() close ");
					}
				}
			} catch (IOException e) {
				throw new APIException(e);
			}
		}
		if (logger.isDebugEnabled() && verbose) {
			logger.debug ("..wget() end ");
		}
		
        return file;
    }
    
    public String getKoreanMorphologicalAnalysis(String sentence, String divisionString) {
    	logger.debug("[START] KoreanMorphologicalAnalysis INPUT : {}", sentence);
		if(APIUtil.isEmpty(sentence)) {
			return null;
		}    	
    	
		StringBuilder out = new StringBuilder();
    	KoreanAnalyzer korean = null;
		TokenStream ts = null;
		CharTermAttribute termAtt = null;
		
		try {
	    	korean = new KoreanAnalyzer();
	    	korean.setQueryMode(false);
	    	
			ts = korean.tokenStream("bogus", sentence);
		    termAtt = ts.addAttribute(CharTermAttribute.class);
		    ts.reset();
		    
		    while (ts.incrementToken()) {
		    	if(termAtt.toString().equals(divisionString)) {
		    		continue;
		    	}
		    	out.append(termAtt.toString());
		    	out.append(divisionString);
		    }
		}
		catch(IOException e) {
			logger.error("[IOException] 형태소 분석도중 장애 발생", e);
		}
		catch(Exception e) {
			logger.error("[Exception] 형태소 분석도중 장애 발생", e);
		}
		finally {
			if(ts != null) {
			    try {
					ts.end();
					ts.close();
				} catch (IOException e) {
					logger.error("[IOException] 형태소 분석기 tokenStream을 닫는도중 장애 발생", e);
				}
			}
		}
		
		logger.debug("[END] KoreanMorphologicalAnalysis OUTPUT : {}", out);
		return out.toString();
    }
    
}