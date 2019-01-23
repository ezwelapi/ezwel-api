package com.ezwel.htl.interfaces.server.commons.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException; 
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.WebUtils;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.sdo.ImageSDO;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.CryptUtil;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.commons.utils.RegexUtil;
import com.ezwel.htl.interfaces.commons.utils.StackTraceUtil;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
/**
 * <pre>
 * if server 공통 유틸
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 21.
 */
@Component
@APIType(description="파일 핸들링 유틸")
public class FileUtil {

	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	private final static int URL_CONNECT_TIMEOUT = 5000;
	
	private final static int RETRY_INTERVAL_MILLISECOND = 5000;
	
	private final static int RETRY_COUNT = 2;
	
	private WebApplicationContext context;
	
	private PropertyUtil propertyUtil;
	
	private StackTraceUtil stackTraceUtil;
	
	private RegexUtil regexUtil;
	
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
		logger.info("[START] getSaveImagePath URL : {}", imageSDO.getImageURL());
		
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
		String fileExt = null;
		String chngFileName = null;
		String orgFileName = null;
		String lowerImageURL = null;
		String imageURL = (imageSDO.getImageURL() != null ? imageSDO.getImageURL().trim() : null);
		boolean isDeleted = false;
		
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
					logger.info("- base64 data:image 제휴사에서 이미지URL을 데이터:이미지 base64 URL로 리턴할경우 추가작업 필요. 현재(20181129) 전용필차장과 의논해본결과 리턴URL 패턴 파악이 불가능하다고함.");
				} 
				else if (url.toURI().parseServerAuthority() != null) /* URL 패턴 검증 */ {

					if (doDirectoryMake(outputFile, true)) {
						// 이미지 확장자를 찾음
						for (Entry<String, String> entry : OperateConstants.IMAGE_EXT.entrySet()) {
							lowerImageURL = imageURL.toLowerCase();

							if (lowerImageURL.indexOf(OperateConstants.STR_QUESTION_MARK) > -1) {
								lowerImageURL = lowerImageURL.substring(0, lowerImageURL.indexOf(OperateConstants.STR_QUESTION_MARK));
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
						chngFileName = CryptUtil.getMD5HashString(orgFileName).concat(OperateConstants.STR_DOT).concat(fileExt);
						// 파일시스템에 저장 할 파일
						outputFile = new File(outputFile, chngFileName);
						
						if (verbose) {
							logger.info("\n[★☆★☆★☆ 저장 대상 이미지 정보 ★☆★☆★☆]\n- 이미지 파일명 : {}\n- 이미지 FS저장 파일명 : {}\n- 이미지 확장자 : {}\n- 이미지 파일 존재여부 : {}, 저장경로 : {}", orgFileName, chngFileName, fileExt, outputFile.exists(), outputFile.getPath());
						}
						
						if(outputFile.exists()) {
							//파일이 존재하면 삭제
							isDeleted = outputFile.delete();
							if (verbose) {
								logger.debug("- Delete Target File isDeleted : {}, Path : {}", isDeleted, outputFile.getPath());
							}
						}

						namedSDO = (ImageSDO) propertyUtil.copySameProperty(imageSDO, ImageSDO.class);
						
						// URL 이미지 다운로드 version.1
						//namedSDO.setSave(isDownloadImageIO(url, fileExt, outputFile));
						
						// URL 이미지 다운로드 version.2
						namedSDO.setSave(isDownloadURLStream(url, outputFile, 0));
						
						// 저장소 루트를 제외한 경로
						namedSDO.setFileExt(fileExt);
						namedSDO.setFileSize(outputFile.length());
						namedSDO.setChngFileName(chngFileName);
						namedSDO.setOrgFileName(orgFileName);
						namedSDO.setCanonicalPath(outputFile.getCanonicalPath());
						namedSDO.setRelativePath(CommonUtil.byteSubstring(outputFile.getCanonicalPath(),
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
			logger.error("[getSaveImagePath] IOException : {}\n{}", imageURL, stackTraceUtil.getStackTrace(e));
		} catch (Exception e) {
			namedSDO.setDescription("[ERROR] ".concat(e.getMessage()));
			logger.error("[getSaveImagePath] Exception : {}\n{}", imageURL, stackTraceUtil.getStackTrace(e));
		}
		
		logger.debug("[END] getSaveImagePath {} : {}", imageRootPath, namedSDO.getRelativePath());
		return namedSDO;
	}
	
	
	/**
	 * ImageIO 사용
	 * URL 이미지 다운로드 version.1
	 * @param url
	 * @param fileExt
	 * @param outputFile
	 * @return
	 */
	public boolean isDownloadImageIO(URL url, String fileExt, File outputFile) {
		boolean out = false;
		BufferedImage bufferedImage = null;

		try {
			// URL 이미지 읽음
			bufferedImage = ImageIO.read(url);
			// 이미지 write
			out = ImageIO.write(bufferedImage, fileExt, outputFile);
		}
		catch(Exception e) {
			logger.error("[isDownloadImageIO] Exception : {}\n{}", url, stackTraceUtil.getStackTrace(e));
		}
		
		return out;
	}
	
	
	public boolean isDownloadURLStream(URL url, File outputFile, int callCount) {
		
		boolean out = false;

		URLConnection conn = null;
		OutputStream os = null;
		InputStream in = null;
		byte[] buffer = null;
		int numRead = OperateConstants.INTEGER_ZERO_VALUE;
		long numWritten = OperateConstants.LONG_ZERO_VALUE;
		
		try {
			
			os = new BufferedOutputStream(new FileOutputStream(outputFile));
			conn = url.openConnection();
			conn.setConnectTimeout(URL_CONNECT_TIMEOUT);
			in = conn.getInputStream();
			
			buffer = new byte[8192];
			while ((numRead = in.read(buffer)) != -1) {
				os.write(buffer, 0, numRead);
				numWritten += numRead;
			}
			
			if(numWritten > OperateConstants.LONG_ZERO_VALUE && outputFile.exists()) {
				logger.debug("[SUCCESS] isDownloadURLStream Written : {}\nURL : {}\nFilePath : {}", numWritten, url, outputFile.getPath());
				out = true;	
			}
			else {
				logger.debug("[FAIL] isDownloadURLStream Failed to download URL file. {}", url);
			}
		} catch (SocketTimeoutException e) {
			// Connect | Read timed out 이후 5초후 1 회 다시 호출
			if(callCount > (RETRY_COUNT - 1)) {
				logger.error(APIUtil.formatMessage("[SocketTimeoutException] 호스트에 접속할 수 없습니다. 재호출({})회", (callCount + 1)), e);	
			}
			else {
				try {
					logger.debug("* 고객 요구사항 Connection/Read Timeout 발생시 5초후 재시도 2회 {}", url);
					Thread.sleep(RETRY_INTERVAL_MILLISECOND);
				} catch (InterruptedException ie) {
					logger.debug("[InterruptedException] 타임아웃으로 다시 호출 시도 인터벌중 장애발생", ie);
				}
 
				out = isDownloadURLStream(url, outputFile, (callCount + 1));
			}
		} catch (UnknownHostException e) {
			logger.error("[UnknownHostException] 호스트를 찾을수 없습니다.", e);
		} catch (Exception e) {
			logger.error(APIUtil.formatMessage("[Exception] isDownloadURLStream URL : {}", url), e);
		} finally {
			
			try {
				
				if (in != null) {
					in.close();
				}
				if (os != null) {
					os.close();
				}
			} catch (IOException ioe) {
				logger.error(APIUtil.formatMessage("[isDownloadURLStream] stream not closed URL : {}", url), ioe);
			}
			
			if(conn != null) {
				((HttpURLConnection) conn).disconnect();
			}
		}
		
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
    	regexUtil = (RegexUtil) LApplicationContext.getBean(regexUtil, RegexUtil.class);
    	
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
        		if( regexUtil.testPattern(currentPath, "^[a-zA-Z]:") ) {
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

    	if (verbose) {
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

        if (verbose) {
        	logger.debug("..new file() will save to {}", mkfileName);
        }
		try {
			out = new OutputStreamWriter(new FileOutputStream(file, inheritfile), encoding); // 파일에 문자를 적을 스트림 생성
			if (verbose) {
				logger.debug ("..new file() make stream ");
			}
			
			out.write(fileContent); // 파일에 쓰기
			if (verbose) {
				logger.debug ("..new file() write contents. ");
			}
			
			out.flush(); // 파일 에 문자열전달
			if (verbose) {
				logger.debug ("..new file() flush ");
			}
		}
		catch (UnsupportedEncodingException e) {
			throw new APIException("지원하지 않는 파일 인코딩 입니다.", e);
		}
		catch (FileNotFoundException e) {
			throw new APIException("작성할 파일이 존재하지 않습니다.", e);
		}
		catch (IOException e) {
			throw new APIException("파일에 내용 작성도중 장애 발생.", e);
		}
		catch (Exception e) {
			throw new APIException("디렉토리 및 파일 생성 또는 파일에 내용 작성중 장애 발생.", e);
		}
		finally {
	        try {
				if(out != null) {
					out.close(); // 스트림 닫기
					if (verbose) {
						logger.debug ("..new file() close ");
					}
				}
			} catch (IOException e) {
				logger.error("[IOException] mkfile", e);
			}
		}
		if (verbose) {
			logger.debug ("..wget() end ");
		}
		
        return file;
    }
    
	
    @APIOperation
    public String getTextFileContent(File userFile) {
    	if(userFile == null) {
    		logger.error("[getTextFileContent] 텍스트 파일 경로가 존재하지 않습니다.");
    		return null;
    	}
    	
        StringBuffer contents = null;
        FileReader fileReader = null;
        BufferedReader in = null;
        
        try {
        	
        	logger.debug(" +- getTextFileContent filePath : {}", userFile.getPath());

        	File target = userFile;
            if(!target.exists()) {
            	throw new FileNotFoundException(target.getPath().concat(" 파일이 존재하지 않습니다."));
            }

            fileReader = new FileReader(target);
            in = new BufferedReader(fileReader);

            String read;
            contents = new StringBuffer();
            while((read = in.readLine()) != null) {
            	contents.append((new StringBuilder(String.valueOf(read))).append("\n").toString());
            }

        }
        catch(IOException io) {
        	logger.error("[IOException] getTextFileContent Message : ", io);
        }
        catch(Exception e) {
            logger.error("[Exception] getTextFileContent Message : ", e);
        }
        finally {
        	if(in != null) {
        		try {
					in.close();
				} catch (IOException e) {
					logger.error("[IOException] Close BufferedReader Error Message : ", e);
				}
        	}
        }
        
        return (contents != null ? contents.toString() : null);
    }
    
    @APIOperation
    public String getInnerJarTextResource(URL configFileURL) {

		if(configFileURL == null) {
			throw new APIException(MessageConstants.RESPONSE_CODE_2001, "- [InnerJarTextResource] 파라메터 URL이 존재하지 않습니다. ");
		}
		if(configFileURL.getPath().indexOf(OperateConstants.JAR_SEPARATOR) == -1) {
			throw new APIException(MessageConstants.RESPONSE_CODE_2001, "- [InnerJarTextResource] 바인드된 URL은 jar 내부의 파일경로를 포함하는 URL이 아닙니다. URL : {}", configFileURL.getPath());
		}
		
		String out = null;
    	String absolutePath = null;
		BufferedReader bufferedReader = null;
		InputStream inputStream = null;
		StringBuffer contents = null;
		String line = null;
		
    	try {
    		contents = new StringBuffer(); 

    		absolutePath = configFileURL.getPath();
			absolutePath = absolutePath.substring(absolutePath.lastIndexOf(OperateConstants.JAR_SEPARATOR) + OperateConstants.JAR_SEPARATOR.length());
			logger.debug("- in jar absolutePath : {}", absolutePath);
			
			inputStream = getClass().getClassLoader().getResourceAsStream(absolutePath);
	        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	        while ((line = bufferedReader.readLine()) != null) {
	        	contents.append(line);   
	        	contents.append(OperateConstants.LINE_SEPARATOR);
	        }
	        
	        out = contents.toString();
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_2001, "JAR파일 내부의 텍스트기반 파일 내용 추출 장애 발생", e);
		}
		finally {
			if(bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					logger.error("[IOException] bufferedReader.close", e);
				}
			}
			
			if(inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("[IOException] inputStream.close", e);
				};
			}
		}
    	
    	return out;
    }
    
}