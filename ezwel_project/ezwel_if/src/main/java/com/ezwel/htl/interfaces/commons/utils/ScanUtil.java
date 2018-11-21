package com.ezwel.htl.interfaces.commons.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.exception.APIException;
/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 20.
 */
@APIType
public class ScanUtil {

	private static final Logger logger = LoggerFactory.getLogger(ScanUtil.class);
	
	private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];
	private static final Throwable CVF_FAILURE, CR_FAILURE; // set in <clinit>
	private static final Field CLASSES_VECTOR_FIELD; // set in <clinit> [can be null]
	private static final CallerResolver CALLER_RESOLVER; // set in <clinit> [can be null]
	
	
	@Test
	public void testOperation() {
		scanClassesDir("com.ezwel.htl.interfaces.commons.http.dto");
		
		
		scanClassesDir("java.io", this.getClass().getName());
		
	}

	
	static {
		Throwable failure = null;

		Field tempf = null;
		try {
			// this can fail if this is not a Sun-compatible JVM
			// or if the security is too tight:

			tempf = ClassLoader.class.getDeclaredField("classes");
			if (tempf.getType() != Vector.class) {
				throw new RuntimeException("not of type java.util.Vector: " + tempf.getType().getName());
			}

			tempf.setAccessible(true);
		} catch (Throwable t) {
			failure = t;
		}
		CLASSES_VECTOR_FIELD = tempf;
		CVF_FAILURE = failure;

		failure = null;
		CallerResolver tempcr = null;
		try {
			// this can fail if the current SecurityManager does not allow
			// RuntimePermission ("createSecurityManager"):

			tempcr = new CallerResolver();
		} catch (Throwable t) {
			failure = t;
		}
		CALLER_RESOLVER = tempcr;
		CR_FAILURE = failure;
	}
	
	/**
	 * <pre>
	 * [메서드 설명]
	 * 주어진 현재 클래스 로더 경로로 부터 
	 * 로딩된 classes 아래의 class들에서 검색 대상 파일을 찾음 
	 * [사용방법 설명]
	 * 
	 * </pre>
	 * @return
	 * @author swkim@ebsolution.co.kr
	 * @since  2018. 11. 20.
	 */
	public List<Object> scanClassesDir(String packagesParam) {
		return scanClassesDir(packagesParam, null);
	}
	
	
	public List<Object> scanClassesDir(String packagesParam, String fileNameParam) {
		
		List<Object> asset = null;
		URL resourceURL = null;
		File resourceFile = null;
		String packages = null;
		String fileName = null;
		
		try {
			//start 
			ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
			ClassLoader currentClassLoader = ScanUtil.class.getClassLoader();
			
			ClassLoader[] loaders = new ClassLoader[] { systemClassLoader, currentClassLoader };
			final Class<?>[] classes = getLoadedClasses(loaders);			
			
			for (Class<?> cls : classes) {
				
				String className = cls.getName();
				
			}
			//end
			asset = new ArrayList<Object>();
			packages = "/".concat(packagesParam.replace(".", "/"));
			if( fileNameParam != null ) {
				fileName = fileNameParam;
				packages = packages.concat("/").concat(fileName);
			}
			logger.debug("# scan target packages : {}", packages);
			
			resourceURL = this.getClass().getResource(packages);
			if(resourceURL != null) {
				resourceFile = APIUtil.getFileFromURI(resourceURL.toURI());
				logger.debug("# CanonicalPath : {}", resourceFile.getCanonicalPath());
			}
			else {
				logger.warn("# classes에 존재하지 않는 Type. Jar Scanning 시작 '{}'", packages);
				File parentFile = APIUtil.getFileFromURI(getClass().getResource(".").toURI()).getParentFile();
				
			}
			
			
		} catch (URISyntaxException e) {
			throw new APIException(e);
		} catch (IOException e) {
			throw new APIException(e);
		}
		
		return asset;
	}
	
	public static Class<?>[] getLoadedClasses(final ClassLoader[] loaders) {
		if (loaders == null) {
			throw new IllegalArgumentException("null input: loaders");
		}

		final List<Class<?>> resultList = new LinkedList<Class<?>>();

		for (int l = 0; l < loaders.length; ++l) {
			final ClassLoader loader = loaders[l];
			if (loader != null) {
				final Class<?>[] classes = getLoadedClasses(loaders[l]);

				resultList.addAll(Arrays.asList(classes));
			}
		}

		final Class<?>[] result = new Class<?>[resultList.size()];
		resultList.toArray(result);

		return result;
	}
	
	private static final class CallerResolver extends SecurityManager {
		protected Class<?>[] getClassContext() {
			return super.getClassContext();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Class<?>[] getLoadedClasses(final ClassLoader loader) {
		if (loader == null) {
			throw new IllegalArgumentException("null input: loader");
		}
		if (CLASSES_VECTOR_FIELD == null) {
			throw new RuntimeException("ClassScope::getLoadedClasses() cannot be used in this JRE", CVF_FAILURE);
		}

		try {
			final Vector<Class<?>> classes = (Vector<Class<?>>) CLASSES_VECTOR_FIELD.get(loader);
			if (classes == null) {
				return EMPTY_CLASS_ARRAY;
			}
			
			final Class<?>[] result;

			// note: Vector is synchronized in Java 2, which helps us make
			// the following into a safe critical section:

			synchronized (classes) {
				result = new Class<?>[classes.size()];
				classes.toArray(result);
			}

			return result;
		}
		// this should not happen if <clinit> was successful:
		catch (IllegalAccessException e) {
			e.printStackTrace(System.out);

			return EMPTY_CLASS_ARRAY;
		}
	}
	
}

