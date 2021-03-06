package com.ezwel.htl.interfaces.server.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.configure.data.ServerManagedConfig;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.server.commons.spring.LApplicationContext;
import com.ezwel.htl.interfaces.server.commons.utils.FileUtil;
import com.ezwel.htl.interfaces.server.sdo.ManagerSDO;
import com.ezwel.htl.interfaces.server.sdo.StoreInfoSDO;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 15.
 */
@Service
@APIType(description="외부 > 내부 인터페이스 서비스")
public class ManagerService {

	private static final Logger logger = LoggerFactory.getLogger(ManagerService.class);
	
	private PropertyUtil propertyUtil;
	
	private FileUtil fileUtil;
	
	private final static String CONFIG_FILE_EXT;
	
	static {
		CONFIG_FILE_EXT = "xml";
	}
	
	private String getAppBasePath() throws Exception {
		
		fileUtil = (FileUtil) LApplicationContext.getBean(fileUtil, FileUtil.class);
		
		ServerManagedConfig managedConfig = InterfaceFactory.getServerConfig();
		
		String appBasePath = "";
		
		if(managedConfig != null && managedConfig.isServerRelativePath()) {
			//상대경로
			appBasePath = fileUtil.getContextPath();
			logger.debug("# appBase Path : {}", appBasePath);
		}
		
		return appBasePath;
	}
	
	/**
	 * <pre>
	 * [메서드 설명]
	 * 인터페이스 설정 XML내용 조회
	 * 
	 * </pre>
	 * 
	 * @param inManagerSDO
	 * @return
	 * @throws APIException
	 * @throws Exception
	 * @author swkim@ebsolution.co.kr
	 * @since  2019. 02. 11. 
	 */
	@APIOperation(description="인터페이스 설정 XML내용 조회")
	public ManagerSDO findXML(ManagerSDO inManagerSDO) throws Exception {
		logger.debug("[START] findXML {}", inManagerSDO);
		if(inManagerSDO == null) {
			throw new APIException(MessageConstants.RESPONSE_CODE_2000, "입력값이 존재하지 않습니다.");
		}
		
		ManagerSDO out = null;
		
		if(APIUtil.isNotEmpty(inManagerSDO.getStoreFileDir()) && APIUtil.isEmpty(inManagerSDO.getStoreFileName())) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9302, "임시저장소 파일경로는 존재하나 파일명이 존재하지 않습니다.");
		}
		else if(APIUtil.isEmpty(inManagerSDO.getStoreFileDir()) && APIUtil.isNotEmpty(inManagerSDO.getStoreFileName())) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9302, "임시저장소 파일명은 존재하나 파일경로가 존재하지 않습니다.");
		}
		
		fileUtil = (FileUtil) LApplicationContext.getBean(fileUtil, FileUtil.class);
		
		ServerManagedConfig managedConfig = InterfaceFactory.getServerConfig();
		
		String appBasePath = getAppBasePath();
		String filePath = null;
		String tempDir = null;
		File dirPath = null;
		List<StoreInfoSDO> dirList = new ArrayList<StoreInfoSDO>();
		
		if(!inManagerSDO.isManagedXml() && APIUtil.isNotEmpty(inManagerSDO.getStoreFileDir()) && APIUtil.isNotEmpty(inManagerSDO.getStoreFileName())) {
			//임시저장소 XML 파일 조회
			
			filePath = new StringBuffer().append(appBasePath)
						.append(managedConfig.getTemporaryXmlStorageRoot()).append(File.separator)
						.append(inManagerSDO.getStoreFileDir()).append(File.separator)
						.append(inManagerSDO.getStoreFileName()).toString();
		}
		else {
			if(inManagerSDO.isManagedXml()) {
				//현재 반영중인 메인 XML 파일 조회
				filePath = new StringBuffer().append(appBasePath).append(managedConfig.getXmlConfigRealPath()).toString();
			}
		}		
		
		//임시저장소 루트 경로
		tempDir = new StringBuffer().append(appBasePath).append(managedConfig.getTemporaryXmlStorageRoot()).toString();
		dirPath = new File(tempDir);
		
	  	StoreInfoSDO storeInfoSDO = null;
		
		if(dirPath.exists() && dirPath.isDirectory()) {
			
			if(dirPath.listFiles() != null) {
				
				for(File userDir : dirPath.listFiles()) {
					storeInfoSDO = new StoreInfoSDO();
					storeInfoSDO.setName(userDir.getName());
					dirList.add(storeInfoSDO);
				}
			}
		}
		else {
			logger.warn("임시저장소 루트 디렉토리가 존재하지 않습니다.");
		}
		
		logger.debug("* filePath : {}", filePath);
		out = new ManagerSDO();
		out.setXmlCont(fileUtil.getTextFileContent(new File(filePath)));
		out.setFileList(dirList);
		
		logger.debug("[END] findXML {}", out);
		return out;
	}
	
	/**
	 * <pre>
	 * [메서드 설명]
	 * 인터페이스 설정 XML내용 저장
	 * 
	 * </pre>
	 * 
	 * @param inManagerSDO
	 * @return
	 * @throws APIException
	 * @throws Exception
	 * @author swkim@ebsolution.co.kr
	 * @since  2019. 02. 11. 
	 */
	@APIOperation(description="인터페이스 설정 XML내용 저장")
	public ManagerSDO saveXML(ManagerSDO inManagerSDO) throws Exception {
		logger.debug("[START] saveXML {}", inManagerSDO);
		
		ManagerSDO out = null;
		
		if(inManagerSDO == null) {
			throw new APIException(MessageConstants.RESPONSE_CODE_2000, "입력값이 존재하지 않습니다.");
		}
		
		if(APIUtil.isEmpty(inManagerSDO.getStoreFileDir())) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9302, "임시저장소 경로가 존재하지 않습니다.");
		}
		if(APIUtil.isEmpty(inManagerSDO.getXmlCont())) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9302, "XML 내용이 존재하지 않습니다.");
		}
		
		fileUtil = (FileUtil) LApplicationContext.getBean(fileUtil, FileUtil.class);
		
		ServerManagedConfig managedConfig = InterfaceFactory.getServerConfig();
		
		String fileDir = new StringBuffer().append(getAppBasePath())
				.append(managedConfig.getTemporaryXmlStorageRoot()).append(File.separator)
				.append(inManagerSDO.getStoreFileDir()).append(File.separator).toString();
		
		String fileName = new StringBuffer()/*.append(inManagerSDO.getStoreFileDir().toUpperCase())*/
				.append(APIUtil.getFastDate(OperateConstants.DEF_DATE_MILLISECOND_FORMAT)).append(OperateConstants.STR_DOT)
				.append(CONFIG_FILE_EXT).toString();
		
		File newFile = fileUtil.mkfile(fileDir, fileName, inManagerSDO.getXmlCont(), OperateConstants.DEFAULT_ENCODING, false, true);
		
		out = new ManagerSDO();
		
		if(newFile != null && newFile.exists() && newFile.isFile()) {
			out.setSaved(true);
			out.setStoreFileName(newFile.getName());
		}
		else {
			out.setSaved(false);
		}
		
		logger.debug("[END] saveXML {}", out);
		return out;
	}

	
	
	/**
	 * <pre>
	 * [메서드 설명]
	 * 인터페이스 설정 XML내용 반영
	 * 
	 * </pre>
	 * 
	 * @param inManagerSDO
	 * @return
	 * @throws APIException
	 * @throws Exception
	 * @author swkim@ebsolution.co.kr
	 * @since  2019. 02. 11. 
	 */
	@APIOperation(description="인터페이스 설정 XML내용 반영")
	public ManagerSDO applyXML(ManagerSDO inManagerSDO) throws Exception {
		logger.debug("[START] applyXML {}", inManagerSDO);
		
		ManagerSDO out = null;
		
		if(inManagerSDO == null) {
			throw new APIException(MessageConstants.RESPONSE_CODE_2000, "입력값이 존재하지 않습니다.");
		}

		if(APIUtil.isNotEmpty(inManagerSDO.getStoreFileDir()) && APIUtil.isEmpty(inManagerSDO.getStoreFileName())) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9302, "임시저장소 파일경로는 존재하나 파일명이 존재하지 않습니다.");
		}
		else if(APIUtil.isEmpty(inManagerSDO.getStoreFileDir()) && APIUtil.isNotEmpty(inManagerSDO.getStoreFileName())) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9302, "임시저장소 파일명은 존재하나 파일경로가 존재하지 않습니다.");
		}
		else if(APIUtil.isEmpty(inManagerSDO.getStoreFileDir()) && APIUtil.isEmpty(inManagerSDO.getStoreFileName())) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9302, "반영할 파일의 디렉토리와 파일명이 존재하지 않습니다.");
		}
		
		fileUtil = (FileUtil) LApplicationContext.getBean(fileUtil, FileUtil.class);
		StringBuffer logContents = null;
		String guid = APIUtil.getId();
		
		/**
		 * 임시로 저장한XML환경 파일을 반영할때 
		 * 1. 적용중인 파일을 백업합니다.
		 * 2. 백업이 완료되면 로그파일에 백업 완료 메시지를 기록합니다.
		 * 3. 신규로 적용하려는 파일을 읽습니다.
		 * 4. XML파일을 실제 운영 파일에 반영합니다.
		 * 5. 반영 완료 메시지를 로그에 기록합니다.
		 */
		ServerManagedConfig managedConfig = InterfaceFactory.getServerConfig();
		String realConfigPath = new StringBuffer().append(getAppBasePath()).append(managedConfig.getXmlConfigRealPath()).toString();
		logger.debug("- realConfigPath : {}", realConfigPath);
		File confXML = new File(realConfigPath);
		if(!confXML.exists()) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9302, "인터페이스 운영환경XML 파일이 존재하지 않습니다. 대상경로 : {}", realConfigPath);
		}
		
		String currentTime = APIUtil.getFastDate(OperateConstants.DEF_DATE_MILLISECOND_FORMAT);
		
		String fileExt = OperateConstants.STR_DOT.concat(CONFIG_FILE_EXT);
		logger.debug("- fileExt : {}", fileExt);
		String backupConfigPath = new StringBuffer().append(getAppBasePath()).append(managedConfig.getXmlConfigFileBackupRoot()).toString();
		logger.debug("- backupConfigPath : {}", backupConfigPath);
		String backupFileName = confXML.getName().substring(0, confXML.getName().lastIndexOf(fileExt));
		backupFileName = new StringBuffer().append(backupFileName).append(OperateConstants.STR_UNDERBAR).append(inManagerSDO.getUserId()).append(OperateConstants.STR_UNDERBAR).append(currentTime).append(fileExt).toString();
		backupConfigPath = backupConfigPath.concat(File.separator).concat(backupFileName);
		//원본 백업
		boolean isCopy = fileUtil.copy(realConfigPath, backupConfigPath);
		logger.debug("- isCopy : {}", isCopy);
		
		//반영할 신규 설정 XML
		File newConfigureFilePath = new File(new StringBuffer().append(getAppBasePath())
				.append(managedConfig.getTemporaryXmlStorageRoot()).append(File.separator)
				.append(inManagerSDO.getStoreFileDir()).append(File.separator)
				.append(inManagerSDO.getStoreFileName()).toString());
		
		String logPathDir = new StringBuffer().append(getAppBasePath()).append(managedConfig.getApplyConfigLogRoot()).toString();
		
		logContents = new StringBuffer(); 
		logContents.append(OperateConstants.LINE_SEPARATOR);
		logContents.append("[");
		logContents.append(guid);
		logContents.append("] TARGETING! USER_ID: ");
		logContents.append(inManagerSDO.getUserId());
		logContents.append(" APPLY XML FILE: ");
		logContents.append(newConfigureFilePath.getPath());
		logContents.append(" [");
		logContents.append(currentTime);
		logContents.append("]");		
		
		//원본 백업 후 어떤유저가 설정파일을 변경 반영하는지 로깅한다.
		File logFile = fileUtil.mkfile(logPathDir, "ApplyXML_".concat(APIUtil.getFastDate(OperateConstants.DEF_DAY_FORMAT)).concat(".log"), logContents.toString(), OperateConstants.DEFAULT_ENCODING, true, true);
		logger.debug("- logFile : {}, file length : {}", logFile.exists(), logFile.length());
		
		if(!newConfigureFilePath.exists()) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9302, "신규 설정으로 반영할 XML파일이 존재하지 않습니다. 대상경로 : {}", newConfigureFilePath.getPath());
		}
		
		//신규파일 내용
		String fileContents = fileUtil.getTextFileContent(newConfigureFilePath);
		//반영중인 파일 
		File configPath = new File(new StringBuffer().append(getAppBasePath()).append(managedConfig.getXmlConfigRealPath()).toString());
		//반영중인 파일을 신규 환경파일로 교채(반영)함
		File newConfigFile = fileUtil.mkfile(configPath.getParent(), configPath.getName(), fileContents, OperateConstants.DEFAULT_ENCODING, false, true);
		if(!newConfigFile.exists()) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9302, "운영 설정파일이 존재하지 않거나 잘못된 경로입니다. 대상경로 : {}", newConfigureFilePath.getPath());
		}
		else {
			//반영 완료 로깅
			logContents = new StringBuffer(); 
			logContents.append(OperateConstants.LINE_SEPARATOR);
			logContents.append("[");
			logContents.append(guid);
			logContents.append("] APPLY COMPLETE! USER_ID: ");
			logContents.append(inManagerSDO.getUserId());
			logContents.append(" APPLY XML FILE: ");
			logContents.append(newConfigureFilePath.getPath());
			logContents.append(" [");
			logContents.append(APIUtil.getFastDate(OperateConstants.DEF_DATE_MILLISECOND_FORMAT));
			logContents.append("]");
			
			logFile = fileUtil.mkfile(logPathDir, "ApplyXML_".concat(APIUtil.getFastDate(OperateConstants.DEF_DAY_FORMAT)).concat(".log"), logContents.toString(), OperateConstants.DEFAULT_ENCODING, true, true);
			
			logger.debug("- logFile : {}, file length : {}", logFile.exists(), logFile.length());
			
			if(!newConfigureFilePath.exists()) {
				throw new APIException(MessageConstants.RESPONSE_CODE_9302, "신규 설정으로 반영할 XML파일이 존재하지 않습니다. 대상경로 : {}", newConfigureFilePath.getPath());
			}			
		}
		
		logger.debug("[END] applyXML {}", out);
		return out;
	}
	
	
	/**
	 * <pre>
	 * [메서드 설명]
	 * 인터페이스 설정 XML임시 저장내용 목록 조회
	 * 
	 * </pre>
	 * 
	 * @param inManagerSDO
	 * @return
	 * @throws APIException
	 * @throws Exception
	 * @author swkim@ebsolution.co.kr
	 * @since  2019. 02. 11. 
	 */
	@APIOperation(description="인터페이스 설정 XML임시 저장내용 목록 조회")
	public ManagerSDO storeList(ManagerSDO inManagerSDO) throws Exception {
		logger.debug("[START] storeList {}", inManagerSDO);
		
		ManagerSDO out = null;
		
		if(inManagerSDO == null) {
			throw new APIException(MessageConstants.RESPONSE_CODE_2000, "입력값이 존재하지 않습니다.");
		}
		
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		fileUtil = (FileUtil) LApplicationContext.getBean(fileUtil, FileUtil.class);
		
		if(APIUtil.isEmpty(inManagerSDO.getStoreType())) {
	  		throw new APIException(MessageConstants.RESPONSE_CODE_9302, "조회 형식 파라메터가 존재하지 않습니다.");
	  	}
		
	  	ServerManagedConfig managedConfig = InterfaceFactory.getServerConfig();
	  	
	  	String userFileDir = new StringBuffer().append(getAppBasePath()).append(managedConfig.getTemporaryXmlStorageRoot()).toString();	  	
	  	
	  	
	  	
	  	if(!"root".equals(inManagerSDO.getStoreType())) {
	  		
			if(APIUtil.isEmpty(inManagerSDO.getStoreFileDir())) {
		  		throw new APIException(MessageConstants.RESPONSE_CODE_9302, "임시저장경로 디렉토리 이름이 존재하지 않습니다.");
		  	}
			
			userFileDir = new StringBuffer(userFileDir).append(File.separator).append(inManagerSDO.getStoreFileDir()).toString();
	  	}
	  	
	  	logger.debug("userFileDir : {}", userFileDir);
	  	
	  	File dirFile = new File(userFileDir);
	  	List<StoreInfoSDO> fileList = new ArrayList<StoreInfoSDO>();
	  	StoreInfoSDO storeInfoSDO = null;
	  	
	  	out = new ManagerSDO();
	  	if(dirFile != null && dirFile.exists() && dirFile.isDirectory()) {
	  		
	  		if(dirFile.listFiles() != null) {
	  			
	  			for(File file : dirFile.listFiles()) {
	  				storeInfoSDO = new StoreInfoSDO();
	  				storeInfoSDO.setName(file.getName());
	  				fileList.add(storeInfoSDO);
	  			}
	  		}
	  	}

	  	out.setFileList(fileList);

		logger.debug("[END] storeList {}", out);
		return out;
	}
	
	
	@APIOperation(description="인터페이스 설정 XML임시 저장내용 전체 목록 조회")
	public ManagerSDO storeAllList(ManagerSDO inManagerSDO) throws Exception {
		logger.debug("[START] storeList {}", inManagerSDO);
		
		ManagerSDO out = null;
		
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		fileUtil = (FileUtil) LApplicationContext.getBean(fileUtil, FileUtil.class);

		ServerManagedConfig managedConfig = InterfaceFactory.getServerConfig();
	  	
	  	String userFileDir = new StringBuffer().append(getAppBasePath()).append(managedConfig.getTemporaryXmlStorageRoot()).toString();	  	
	  	
	  	File fileDir = new File(userFileDir);
	  	
	  	out = new ManagerSDO();
	  	List<StoreInfoSDO> fileList = new ArrayList<StoreInfoSDO>();
	  	StoreInfoSDO storeInfoSDO = null;
	  	
	  	//실제 운영 파일 디렉토리
		storeInfoSDO = new StoreInfoSDO();
		storeInfoSDO.setName("Operating ENV XML");
		storeInfoSDO.setPath("Operating ENV XML");
		storeInfoSDO.setFileType("D");
		fileList.add(storeInfoSDO);

		//실제 운영 파일 
		storeInfoSDO = new StoreInfoSDO();
		storeInfoSDO.setName("interface-configure.xml");
		storeInfoSDO.setPath("interface-configure.xml");
		storeInfoSDO.setPrntName("Operating ENV XML");
		storeInfoSDO.setOrder(OperateConstants.INTEGER_ONE_VALUE);
		storeInfoSDO.setManagedXml(true);
		storeInfoSDO.setFileType("F");
		fileList.add(storeInfoSDO);
		
		Integer orderIdx = OperateConstants.INTEGER_TWO_VALUE;
		
	  	if(fileDir != null && fileDir.listFiles() != null) {
	  		
	  		for(File dir : fileDir.listFiles()) {
	  			
	  			if(dir.isDirectory()) {
	  				
		  			storeInfoSDO = new StoreInfoSDO();
		  			storeInfoSDO.setName(dir.getName());
		  			storeInfoSDO.setPath(dir.getPath());
		  			storeInfoSDO.setFileType("D");
		  			fileList.add(storeInfoSDO);
		  			
		  			if(dir.listFiles() != null) {
		  				
		  				for(File file : dir.listFiles()) {
		  					
		  					if(file.isFile()) {
					  			storeInfoSDO = new StoreInfoSDO();
					  			storeInfoSDO.setName(file.getName());
					  			storeInfoSDO.setPath(file.getPath());
					  			storeInfoSDO.setPrntName(dir.getName());
					  			storeInfoSDO.setOrder(orderIdx);
					  			storeInfoSDO.setFileType("F");
					  			fileList.add(storeInfoSDO);
					  			orderIdx++;
		  					}
		  				}
		  			}
	  			}
	  		}
	  	}

	  	out.setFileList(fileList);

		logger.debug("[END] storeList {}", out);
		return out;
	}

	
	@APIOperation(description="인터페이스 설정 XML임시 저장내용 전체 목록 조회")
	public ManagerSDO applyList(ManagerSDO inManagerSDO) throws Exception {
		logger.debug("[START] storeList {}", inManagerSDO);
		
		ManagerSDO out = null;
		
		propertyUtil = (PropertyUtil) LApplicationContext.getBean(propertyUtil, PropertyUtil.class);
		fileUtil = (FileUtil) LApplicationContext.getBean(fileUtil, FileUtil.class);

		ServerManagedConfig managedConfig = InterfaceFactory.getServerConfig();
	  	
		// xmlConfigFileBackupRoot 백업루트
		// applyConfigLogRoot 로그루트
		
		String storeDir = null;
		if("LOG".equals(inManagerSDO.getStoreType())) {
			storeDir = managedConfig.getApplyConfigLogRoot();
		}
		else if("BAKUP".equals(inManagerSDO.getStoreType())) {
			storeDir = managedConfig.getXmlConfigFileBackupRoot();
		}
		
	  	String fileDir = new StringBuffer().append(getAppBasePath()).append(storeDir).toString();	  	
	  	
	  	File filePath = new File(fileDir);
	  	out = new ManagerSDO();
	  	StoreInfoSDO storeInfoSDO = null;
	  	List<StoreInfoSDO> fileList = new ArrayList<StoreInfoSDO>();
	  	if(filePath != null && filePath.listFiles() != null) {
	  		
	  		for(File dir : filePath.listFiles()) {
	  			
	  			if(dir.isFile()) {
	  				
		  			storeInfoSDO = new StoreInfoSDO();
		  			storeInfoSDO.setName(dir.getName());
		  			storeInfoSDO.setPath(dir.getPath());
		  			storeInfoSDO.setFileType("F");
		  			fileList.add(storeInfoSDO);
	  			}
	  		}
	  	}

	  	out.setFileList(fileList);
	  	
	  	return out;
	}


	@APIOperation(description="XML반영 로그/백업 내용 조회")
	public ManagerSDO applyView(ManagerSDO inManagerSDO) throws Exception {
		logger.debug("[START] applyView {}", inManagerSDO);
		if(inManagerSDO == null) {
			throw new APIException(MessageConstants.RESPONSE_CODE_2000, "입력값이 존재하지 않습니다.");
		}
		
		ManagerSDO out = null;
		
		fileUtil = (FileUtil) LApplicationContext.getBean(fileUtil, FileUtil.class);
		
		ServerManagedConfig managedConfig = InterfaceFactory.getServerConfig();
		
		String storeDir = null;
		if("LOG".equals(inManagerSDO.getStoreType())) {
			storeDir = managedConfig.getApplyConfigLogRoot();
		}
		else if("BAKUP".equals(inManagerSDO.getStoreType())) {
			storeDir = managedConfig.getXmlConfigFileBackupRoot();
		}
		
	  	String filePath = new StringBuffer().append(getAppBasePath()).append(storeDir).append(File.separator).append(inManagerSDO.getStoreFileName()).toString();	  
	  	File applyFile = new File(filePath); 
	  	
	  	if(!applyFile.exists()) {
	  		throw new APIException("파일이 존재하지 않습니다.");
	  	}
		
		out = new ManagerSDO();
		out.setXmlCont(fileUtil.getTextFileContent(applyFile));
		
		logger.debug("[END] applyView {}", out);
		return out;
	}
}
