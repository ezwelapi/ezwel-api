package images;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path; 
import java.nio.file.Paths; 
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.thread.CallableExecutor;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;



public class ImageAPI {

	private static final Logger logger = LoggerFactory.getLogger(ImageAPI.class);
	
	static String SWITCH_DIR_NAME = "_____SWITCH_____"; 
	
	private List<ImagesDTO> createData() {
		
		String testFileRoot = "D:".concat(File.separator).concat("ezwel-repository").concat(File.separator).concat("ImagesDownload").concat(File.separator);
		String date = APIUtil.getFastDate("yyyyMMdd");
		ImagesDTO item = null;
		List<ImagesDTO> imageList = new ArrayList<ImagesDTO>();
		Random randomGenerator = new Random();
		
		List<String> imageURL = new ArrayList<String>();
		imageURL.add("http://blogfiles.naver.net/data43/2008/12/21/159/200812212_%282%29_qhaltkfkd919.jpg");
		imageURL.add("http://blogfiles.naver.net/20101216_262/viva1143_1292499811258ismmQ_JPEG/16.jpg");
		imageURL.add("http://blogfiles.naver.net/20120513_197/nyc77777_1336874303236frgMc_JPEG/%C7%B338_11.JPG");
		imageURL.add("http://blogfiles.naver.net/20101004_245/ban123_1286125303832IQo3X_jpg/dsc_3549%BF%F8%B4%E7_ban123.jpg");
		imageURL.add("http://blogfiles.naver.net/data44/2009/6/12/273/4a23213a8b994_anampp.jpg");
		imageURL.add("http://blogfiles.naver.net/data6/2005/5/1/53/z14%5B20031001205456%5D-kong4tang.jpg");
		imageURL.add("http://blogfiles.naver.net/20140302_73/dfgiyo_1393763339117FHAYo_JPEG/daum_net_20140302_211541.jpg");
		imageURL.add("http://blogfiles.naver.net/20100309_147/eli20_1268127307860zI20T_jpg/%BA%BD4_eli20.jpg");
		imageURL.add("http://cafefiles.naver.net/data31/2008/5/20/271/%BF%C0%BD%BA%C6%AE%B8%AE%BE%C6_%C7%B3%B0%E6_5_2327woo.jpg");
		imageURL.add("http://blogfiles.naver.net/MjAxNzAyMTlfMTQ4/MDAxNDg3NDg5OTQyMDUy.RNFa_LwlUepNzh-L4xKtRELnF9aZMauDWlIaqQmXqCAg.yZNnm7lpZaBN1KyfZWg-vA12Vy2Y11i6IgLzsy0AfOIg.GIF.jeung7702/%B0%DC%BF%EF%C7%B3%B0%E6_%2824%29.gif");
		
		for(int i = 0; i < 10000; i++) {
			
			item = new ImagesDTO();
			//item.setFilePath(testFileRoot.concat(SWITCH_DIR_NAME).concat(File.separator).concat(Long.toString(APIUtil.currentTimeMillis())).concat("_").concat(getRandomString()).concat("_").concat(APIUtil.getId())); //Path
			item.setFilePath(testFileRoot.concat(SWITCH_DIR_NAME).concat(File.separator).concat(Integer.toString(i)));
			item.setUrlString(imageURL.get(randomGenerator.nextInt(10))); //url string			
			
			imageList.add(item);
		}
		logger.debug("imageList size : {}", imageList.size());
		return imageList; 
	}
	
	
	private String getRandomString() {
		
		StringBuffer temp = new StringBuffer();
		Random rnd = new Random();
		
		for (int i = 0; i < 20; i++) {
		    int rIndex = rnd.nextInt(3);
		    switch (rIndex) {
		    case 0:
		        // a-z
		        temp.append((char) ((int) (rnd.nextInt(26)) + 97));
		        break;
		    case 1:
		        // A-Z
		        temp.append((char) ((int) (rnd.nextInt(26)) + 65));
		        break;
		    case 2:
		        // 0-9
		        temp.append((rnd.nextInt(10)));
		        break;
		    }
		}

		return temp.toString();
	}
	
	@Test
	public void realTest() {
		
		List<ImagesDTO> imageList = createData();
		//imageList.size();
		int threadPoolCount = Runtime.getRuntime().availableProcessors();
		threadPoolCount = imageList.size();
		
		//59
		Callable<Integer> callable1 = null;
		CallableExecutor executor1 = new CallableExecutor();
		executor1.initThreadPool(threadPoolCount);
		//0
		Callable<Integer> callable2 = null;
		CallableExecutor executor2 = new CallableExecutor();
		executor2.initThreadPool(threadPoolCount);
		//9472
		Callable<Integer> callable3 = null;
		CallableExecutor executor3 = new CallableExecutor();
		executor3.initThreadPool(threadPoolCount);
		//0
		Callable<Integer> callable4 = null;
		CallableExecutor executor4 = new CallableExecutor();
		executor4.initThreadPool(threadPoolCount);
		//56
		Callable<Integer> callable5 = null;
		CallableExecutor executor5 = new CallableExecutor();
		executor5.initThreadPool(threadPoolCount);
		//0
		Callable<Integer> callable6 = null;
		CallableExecutor executor6 = new CallableExecutor();
		executor6.initThreadPool(threadPoolCount);
		//0
		Callable<Integer> callable7 = null;
		CallableExecutor executor7 = new CallableExecutor();
		executor7.initThreadPool(threadPoolCount);
		//0
		Callable<Integer> callable8 = null;
		CallableExecutor executor8 = new CallableExecutor();
		executor8.initThreadPool(threadPoolCount);
		
		
		
		int count = 0; 
		/*
		//141
		for(ImagesDTO item : imageList) {
			callable1 = new ImageTest1(item, count);
			executor1.addCall(callable1);
			count++;
		}
		*/
/*
		count = 0; //141
		for(ImagesDTO item : imageList) {
			callable2 = new ImageTest2(item, count);
			executor2.addCall(callable2);
			count++;
		}
*/
	
		count = 0; // 9067
		for(ImagesDTO item : imageList) {
			callable3 = new ImageTest3(item, count);
			executor3.addCall(callable3, 10L);
			count++;
		}
		executor3.clear();
	
/*
		count = 0;
		for(ImagesDTO item : imageList) {
			callable4 = new ImageTest4(item, count);
			executor4.addCall(callable4);
			count++;
		}
		executor4.clear();
		*/
/*
		count = 0;
		for(ImagesDTO item : imageList) {
			callable5 = new ImageTest5(item, count);
			executor5.addCall(callable5);
			count++;
		}
		*/
/*
		count = 0;
		for(ImagesDTO item : imageList) {
			callable6 = new ImageTest6(item, count);
			executor6.addCall(callable6);
			count++;
		}
		*/
		
		/*
		count = 0;
		for(ImagesDTO item : imageList) {
			callable7 = new ImageTest7(item, count);
			executor7.addCall(callable7);
			count++;
		}
		*/
		/*
		// no multithread
		count = 0;
		for(ImagesDTO item : imageList) {
			saveUrl3(item.getFilePath(), item.getUrlString(), item.getSecsConnectTimeout(), item.getSecsReadTimeout(), count);
			count++;
		}
		*/
		/*
		count = 0;
		for(ImagesDTO item : imageList) {
			callable8 = new ImageTest8(item, count);
			executor8.addCall(callable8);
			count++;
		}
		executor8.clear();
		*/
/*
		// no multithread
		count = 0;
		for(ImagesDTO item : imageList) {
			//callable8 = new ImageTest8(item, count);
			//executor8.addCall(callable8);
			getImageIO(item.getUrlString(), item.getFilePath(), count);
			count++;
		}
*/
	}
	
	
	//@Test
	public void test() throws MalformedURLException, IOException {

		//input parameter
		List<String> imageUrlList = new ArrayList<String>();
		imageUrlList.add("http://url/image.gif");

		Path file = null;
		URL url = null;
		int secsConnectTimeout = 1000;
		int secsReadTimeout = 1000;		

		String filePath = "";
		String urlString = "";
		
		int count = 1;
		// case 1
		saveUrl(filePath, urlString, count);

		// case 2
		downloadFileFromURL(urlString, filePath, count);

		// case 3
		downloadBuffer(urlString, filePath, count, 0);

		// case 4
		saveUrl(filePath, urlString, secsConnectTimeout, secsReadTimeout, count);

		// case 5
		saveUrl1(filePath, urlString, secsConnectTimeout, secsReadTimeout, count);
		
		// case 6
		saveUrl2(filePath, urlString, secsConnectTimeout, secsReadTimeout, count);
		
		// case 7
		saveUrl3(filePath, urlString, secsConnectTimeout, secsReadTimeout, count);
		
		
	}

	/*********************************************************************************/
	
    @APIOperation
    public static String getExt(String canonicalPath) {
        int idx;
        String fileName = canonicalPath;
        if (fileName == null || (idx = fileName.lastIndexOf(OperateConstants.EXT_DELIMETER)) == -1)
            return "";
        else
            return fileName.substring(idx);
    }
    
	public int saveUrl(String filePath, String urlString, int eCount) throws MalformedURLException, IOException {
		logger.debug("[CASE1-START] : {}", eCount);
		
		BufferedInputStream in = null;
		FileOutputStream fout = null;
		boolean isDownload = false;
		try {
			String path = filePath.replace(SWITCH_DIR_NAME, "case1") + getExt(urlString);
			logger.debug("path : {}", path);
			File dirPath = new File(new File(path).getParent());
			boolean confirm = dirPath.mkdirs();			
			logger.debug("# case1 confirm : {}, exists : {}", confirm, dirPath.exists());
			
			if(confirm || dirPath.exists()) {
				
				in = new BufferedInputStream(new URL(urlString).openStream());
				fout = new FileOutputStream(path);
				
				byte data[] = new byte[1024];
				int count;
				while ((count = in.read(data, 0, 1024)) != -1) {
					fout.write(data, 0, count);
				}
				isDownload = new File(path).exists();
				return 1;
			}
		} 
		catch(Exception e) {
			logger.error("[CASE1-ERROR]", e);
			e.printStackTrace();
		}
		finally {
			if (in != null) {
				in.close();
			}
			if (fout != null) {
				fout.close();
			}
			logger.debug("[CASE1-FINALLY] : {}", isDownload);
		}
		
		logger.debug("case1 out : {}, url : {}", isDownload, urlString);
		return 0;
	}

	/*********************************************************************************/
	
	public int downloadFileFromURL(String urlString, String filePath, int eCount) {
		logger.debug("[CASE2-START] : {}", eCount);
		
		FileOutputStream fos = null;
		ReadableByteChannel rbc = null;
		URL website = null;
		File file = null;
		File dirFile = null;
		boolean isCreate = false;
		boolean isDownload = false;
		try {
			file = new File(filePath.replace(SWITCH_DIR_NAME, "case2") + getExt(urlString));
			dirFile = new File(file.getParent());
			isCreate = dirFile.mkdirs();
			
			if(isCreate || dirFile.exists()) {
				website = new URL(urlString);
				rbc = Channels.newChannel(website.openStream());
				fos = new FileOutputStream(file);
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				
				isDownload = file.exists();
				return 1;
			}
			
		} catch (Exception e) {
			logger.error("[CASE2-ERROR]", e);
		}
		finally {
			try {
				if(fos != null) {
					fos.close();
				}
				if(rbc != null) {
					rbc.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			logger.debug("[CASE2-FINALLY] : {}, url : {}", isDownload, urlString);
		}
		
		return 0;
	}

	/*********************************************************************************/
	

	public int downloadBuffer(String urlString, String localFileName, int eCount, int callCount) {
		
		OutputStream out = null;
		URLConnection conn = null;
		InputStream in = null;
		boolean isDownload = false;
		String path = null;
		
		try {
			
			URL url = new URL(urlString);
			path = localFileName.replace(SWITCH_DIR_NAME, "case3") + getExt(urlString);
			File dirFile = new File(new File(path).getParent());
			boolean confirm = dirFile.mkdirs();
			
			if(confirm || dirFile.exists()) {
				//logger.debug("# 존재여부 : {}", new File(path).exists());
				out = new BufferedOutputStream(new FileOutputStream(path));
				conn = url.openConnection();
				conn.setConnectTimeout(10000);
				//conn.setReadTimeout(180000);
				in = conn.getInputStream();
				byte[] buffer = new byte[8192];
				
				int numRead;
				long numWritten = 0;
				
				while ((numRead = in.read(buffer)) != -1) {
					out.write(buffer, 0, numRead);
					numWritten += numRead;
				}
				logger.debug("# numWritten : {}, path : {}", numWritten, path);
				isDownload = new File(path).exists();
				//logger.debug("[CASE3-PROC] eCount : {}, urlString : {}, path : {}, isDownload : {}, numWritten : {}", eCount, urlString, path, isDownload, numWritten);
				
				return 1;
			}
			else {
				//logger.debug("[CASE3-ELSE] eCount : {}, urlString : {}, path : {}, isDownload : {}", eCount, urlString, path, isDownload);
			}
			
		} catch (SocketTimeoutException e) {
			// Connect | Read timed out 이후 5초후 1 회 다시 호출
			if(callCount > (2 - 1)) {
				logger.error(APIUtil.formatMessage("[SocketTimeoutException] 호스트에 접속할 수 없습니다. 재호출({})회", (callCount + 1)), e);	
			}
			else {
				try {
					logger.debug("* 고객 요구사항 Connection/Read Timeout 발생시 5초후 재시도 2회 {}", urlString);
					Thread.sleep(5000);
				} catch (InterruptedException ie) {
					logger.debug("[InterruptedException] 타임아웃으로 다시 호출 시도 인터벌중 장애발생", ie);
				}
 
				return downloadBuffer(urlString, localFileName, eCount, (callCount + 1));
			}
		} catch (UnknownHostException e) {
			logger.error("[UnknownHostException] 호스트를 찾을수 없습니다.", e);
		} catch (Exception e) {
			logger.error("[CASE3-ERROR]", e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			
			if(conn != null) {
				((HttpURLConnection) conn).disconnect();
			}
			logger.debug("[CASE3-FINALLY] eCount : {}, urlString : {}, path : {}, isDownload : {}, numWritten : {}", eCount, urlString, path, isDownload);
		}
		return 0;
	}


	/***********************************************************************************/

	public int saveUrl(String filePath, String urlString, int secsConnectTimeout, int secsReadTimeout, int eCount)
			throws IOException {
		logger.debug("[CASE4-START] : {}", eCount);
		
		URLConnection conn = null;
		int ret = 0;
		boolean somethingRead = false;
		InputStream is = null;
		BufferedInputStream in = null;
		OutputStream fout = null;
		boolean isDownload = false;
		try {
			Path file = Paths.get(filePath.replace(SWITCH_DIR_NAME, "case4") + getExt(urlString));
			URL url = new URL(urlString);
			//디렉토리생성(상위DIR까지) jdk1.7부터 이전은 mkdirs
			Files.createDirectories(file.getParent()); // make sure parent dir exists , this can throw exception
			
			conn = url.openConnection(); // can throw exception if bad url
			if (secsConnectTimeout > 0) {
				conn.setConnectTimeout(secsConnectTimeout * 1000);
			}
				
			if (secsReadTimeout > 0) {
				conn.setReadTimeout(secsReadTimeout * 1000);
			}
			
			is = conn.getInputStream();
			in = new BufferedInputStream(is); 
			fout = Files.newOutputStream(file);
			
			byte data[] = new byte[8192];
			int count;
			while ((count = in.read(data)) > 0) {
				somethingRead = true;
				fout.write(data, 0, count);
			}
			
			isDownload = file.toFile().exists();
			return 1;
		} catch (java.io.IOException e) {
			logger.error("[CASE4-ERROR]", e);
			
			int httpcode = 999;
			
			try {
				httpcode = ((HttpURLConnection) conn).getResponseCode();
			} catch (Exception ee) {
				ee.printStackTrace();
			}
			
			if (somethingRead && e instanceof java.net.SocketTimeoutException)
				ret = 10;
			else if (e instanceof FileNotFoundException && httpcode >= 400 && httpcode < 500)
				ret = 20;
			else if (httpcode >= 400 && httpcode < 600)
				ret = 30;
			else if (e instanceof java.net.SocketTimeoutException)
				ret = 40;
			else if (e instanceof java.net.ConnectException)
				ret = 50;
			else if (e instanceof java.net.UnknownHostException)
				ret = 60;
			else
				throw e;
			
			logger.debug("download fail issue : {}", ret);
		}
		finally {
			logger.debug("[CASE4-FINALLY] : {}, url : {}", isDownload, urlString);
		}
		return 0;
	}

	
	/*********************************************************************************/
	
	public int saveUrl1(String filePath, String urlString, int secsConnectTimeout, int secsReadTimeout, int eCount) throws MalformedURLException, IOException {
		logger.debug("[CASE5-START] : {}", eCount);
		
		// Files.createDirectories(file.getParent()); // optional, make sure parent dir exists
		BufferedInputStream in = null;
		OutputStream fout = null;
	    HttpURLConnection conn = null;
	    InputStream is = null;
	    boolean isDownload = false;
		try {
			Path file = Paths.get(filePath.replace(SWITCH_DIR_NAME, "case5") + getExt(urlString));
			URL url = new URL(urlString);
	    	Files.createDirectories(file.getParent());
	    	
	    	conn = (HttpURLConnection) streamFromUrl(url, secsConnectTimeout,secsReadTimeout);
	    	is = conn.getInputStream();
	    	in = new BufferedInputStream(is);
	    	fout = Files.newOutputStream(file);
	        byte data[] = new byte[8192];
	        int count;
	        while((count = in.read(data)) > 0) {
	        	fout.write(data, 0, count);
	        }
	        
	        isDownload = file.toFile().exists();
	        return 1;
	    }
	    catch(Exception e) {
	    	logger.error("[CASE5-ERROR]", e);
	    }
	    finally {
	    	if(in != null) {
	    		in.close();
	    	}
	    	if(is != null) {
	    		is.close();
	    	}	    	
	    	if(fout != null) {
	    		fout.close();
	    	}
	    	if(conn != null) {
	    		conn.disconnect();
	    	}
	    	logger.debug("[CASE5-FINALLY] : {}, url : {}", isDownload, urlString);
	    }
	    return 0;
	}

	public int saveUrl2(String filePath, String urlString, int secsConnectTimeout, int secsReadTimeout, int eCount) throws MalformedURLException, IOException {
		logger.debug("[CASE6-START] : {}", eCount);
		// Files.createDirectories(file.getParent()); // optional, make sure parent dir
		// exists
		ReadableByteChannel rbc = null;
		FileChannel channel = null;
	    HttpURLConnection conn = null;
	    InputStream is = null;
	    boolean isDownload = false;
		try {
			Path file = Paths.get(filePath.replace(SWITCH_DIR_NAME, "case6") + getExt(urlString));
			URL url = new URL(urlString);
			Files.createDirectories(file.getParent());
			
			conn = (HttpURLConnection) streamFromUrl(url, secsConnectTimeout, secsReadTimeout);
			is = conn.getInputStream();
			rbc = Channels.newChannel(is);
			channel = FileChannel.open(file, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
			channel.transferFrom(rbc, 0, Long.MAX_VALUE);
			isDownload = file.toFile().exists();
			
			return 1;
		} catch(Exception e) {
			logger.error("[CASE6-ERROR]", e);
		}
		finally {
			if(rbc != null) {
				rbc.close();
			}
			if(channel != null) {
				channel.close();
			}
			if(conn != null) {
				conn.disconnect();
			}
			if(is != null) {
				is.close();
			}
			logger.debug("[CASE6-FINALLY] : {}, url : {}", isDownload, urlString);
		}
		
		return 0;
	}

	public int saveUrl3(String filePath, String urlString, int secsConnectTimeout, int secsReadTimeout, int eCount) {
		logger.debug("[CASE7-START] : {}", eCount);
			    // Files.createDirectories(file.getParent()); // optional, make sure parent dir exists
		boolean isDownload = false;
		InputStream in = null;
		HttpURLConnection conn = null;
		
		try {
			Path file = Paths.get(filePath.replace(SWITCH_DIR_NAME, "case7") + getExt(urlString));
			URL url = new URL(urlString);
			Files.createDirectories(file.getParent());
			
			conn = (HttpURLConnection) streamFromUrl(url, secsConnectTimeout,secsReadTimeout);
			in = conn.getInputStream();
			Files.copy(in, file, StandardCopyOption.REPLACE_EXISTING);	
			
			isDownload = file.toFile().exists();
			return 1;
		}
		catch(Exception e) {
			logger.error("[CASE7-ERROR]", e);
			return 0;
		}
		finally {
			if(conn != null) {
				conn.disconnect();
			}			
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			logger.debug("[CASE7-FINALLY] : {}, url : {}", isDownload, urlString);
		}
	}

	public URLConnection streamFromUrl(URL url, int secsConnectTimeout, int secsReadTimeout) throws IOException {
		URLConnection conn = url.openConnection();
		
		if (secsConnectTimeout > 0) {
			conn.setConnectTimeout(secsConnectTimeout * 1000);
		}
		if (secsReadTimeout > 0) {
			conn.setReadTimeout(secsReadTimeout * 1000);
		}
		return conn;
	}

	/**********************************************************************************/
	
	public int getImageIO(String urlString, String filePath, int eCount) {
		//logger.debug("[CASE8-START] : {}", eCount);
		
		boolean isDownload = false;
		boolean isCreate = false;
		String path = null;
		try {
			path = filePath.replace(SWITCH_DIR_NAME, "case8") + getExt(urlString);
			// 저장 파일
			File outputFile = new File(path);
						
			File dirFile = new File(outputFile.getParent());
			isCreate = dirFile.mkdirs();
			
			if(isCreate || dirFile.exists()) {
				// URL
				URL url = new URL(urlString);
				// URL 이미지 읽음
				BufferedImage bufferedImage = ImageIO.read(url);
				// 이미지 작성
				isDownload = ImageIO.write(bufferedImage, getExt(urlString).substring(1), outputFile);
			}
			//logger.debug("[CASE8-END] getImageIO");
			return 1;
		} catch (Exception e) {
			logger.error("[CASE8-ERROR] getImageIO", e);
			return 0;
		}
		finally {
			logger.debug("[CASE8-FINALLY] eCount : {}, urlString : {}, path : {}, isDownload : {}, numWritten : {}", eCount, urlString, path, isDownload);
		}
		
		
	}

}
