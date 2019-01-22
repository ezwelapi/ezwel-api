package images;

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
import java.net.URL;
import java.net.URLConnection;
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
		
		for(int i = 0; i < 10000000; i++) {
			
			item = new ImagesDTO();
			item.setFilePath(testFileRoot.concat(SWITCH_DIR_NAME).concat(File.separator).concat(Long.toString(APIUtil.currentTimeMillis())).concat(File.separator).concat(APIUtil.getId())); //Path
			item.setUrlString(imageURL.get(randomGenerator.nextInt(10))); //url string			
			
			imageList.add(item);
		}
		
		return imageList; 
	}
	
	@Test
	public void realTest() {
		
		Callable<Integer> callable1 = null;
		CallableExecutor executor1 = new CallableExecutor();
		executor1.initThreadPool(20);
		
		Callable<Integer> callable2 = null;
		CallableExecutor executor2 = new CallableExecutor();
		executor2.initThreadPool(20);
		
		Callable<Integer> callable3 = null;
		CallableExecutor executor3 = new CallableExecutor();
		executor3.initThreadPool(20);
		
		Callable<Integer> callable4 = null;
		CallableExecutor executor4 = new CallableExecutor();
		executor4.initThreadPool(20);
		
		Callable<Integer> callable5 = null;
		CallableExecutor executor5 = new CallableExecutor();
		executor5.initThreadPool(20);
		
		Callable<Integer> callable6 = null;
		CallableExecutor executor6 = new CallableExecutor();
		executor6.initThreadPool(20);
		
		Callable<Integer> callable7 = null;
		CallableExecutor executor7 = new CallableExecutor();
		executor7.initThreadPool(20);
		
		List<ImagesDTO> imageList = createData();
		
		for(ImagesDTO item : imageList) {
			callable1 = new ImageTest1(item);
			executor1.addCall(callable1);
		}
		
		for(ImagesDTO item : imageList) {
			callable2 = new ImageTest2(item);
			executor2.addCall(callable2);
		}
		
		for(ImagesDTO item : imageList) {
			callable3 = new ImageTest3(item);
			executor3.addCall(callable3);
		}
		
		for(ImagesDTO item : imageList) {
			callable4 = new ImageTest4(item);
			executor4.addCall(callable4);
		}
		
		for(ImagesDTO item : imageList) {
			callable5 = new ImageTest5(item);
			executor5.addCall(callable5);
		}
		
		for(ImagesDTO item : imageList) {
			callable6 = new ImageTest6(item);
			executor6.addCall(callable6);
		}
		
		for(ImagesDTO item : imageList) {
			callable7 = new ImageTest7(item);
			executor7.addCall(callable7);
		}		
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
		
		
		// case 1
		saveUrl(filePath, urlString);

		// case 2
		downloadFileFromURL(urlString, filePath);

		// case 3
		downloadBuffer(urlString, filePath);

		// case 4
		saveUrl(filePath, urlString, secsConnectTimeout, secsReadTimeout);

		// case 5
		saveUrl1(filePath, urlString, secsConnectTimeout, secsReadTimeout);
		
		// case 6
		saveUrl2(filePath, urlString, secsConnectTimeout, secsReadTimeout);
		
		// case 7
		saveUrl3(filePath, urlString, secsConnectTimeout, secsReadTimeout);
		
		
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
    
	public int saveUrl(String filePath, String urlString) throws MalformedURLException, IOException {
		BufferedInputStream in = null;
		FileOutputStream fout = null;
		int out = 0;
		try {
			String path = filePath.replace(SWITCH_DIR_NAME, "case1") + getExt(urlString);
			boolean confirm = new File(new File(path).getParent()).mkdirs();			
			
			if(confirm) {
				
				in = new BufferedInputStream(new URL(urlString).openStream());
				fout = new FileOutputStream(path);
				
				byte data[] = new byte[1024];
				int count;
				while ((count = in.read(data, 0, 1024)) != -1) {
					fout.write(data, 0, count);
				}
				out = 1;
			}
		} 
		catch(Exception e) {
			logger.error("[CASE1-ERROR]", e);
		}
		finally {
			if (in != null) {
				in.close();
			}
			if (fout != null) {
				fout.close();
			}
		}
		return out;
	}

	/*********************************************************************************/
	
	public int downloadFileFromURL(String urlString, String filePath) {
		
		FileOutputStream fos = null;
		ReadableByteChannel rbc = null;
		URL website = null;
		File file = null;
		boolean isCreate = false;
		try {
			file = new File(filePath.replace(SWITCH_DIR_NAME, "case2") + getExt(urlString));
			isCreate = new File(file.getParent()).mkdirs();
			if(isCreate) {
				website = new URL(urlString);
				rbc = Channels.newChannel(website.openStream());
				fos = new FileOutputStream(file);
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
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
		}
		
		return 0;
	}

	/*********************************************************************************/
	

	public int downloadBuffer(String urlString, String localFileName) {
		OutputStream out = null;
		URLConnection conn = null;
		InputStream in = null;

		try {
			URL url = new URL(urlString);
			String path = localFileName.replace(SWITCH_DIR_NAME, "case3") + getExt(urlString);
			boolean confirm = new File(new File(path).getParent()).mkdirs();
			
			if(confirm) {
				
				out = new BufferedOutputStream(new FileOutputStream(path));
				conn = url.openConnection();
				in = conn.getInputStream();
				byte[] buffer = new byte[1024];
				
				int numRead;
				long numWritten = 0;
				
				while ((numRead = in.read(buffer)) != -1) {
					out.write(buffer, 0, numRead);
					numWritten += numRead;
				}
				
				logger.debug("localFileName : {} => numWritten : {}", localFileName, numWritten);
				return 1;
			}
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
		}
		return 0;
	}


	/***********************************************************************************/

	public int saveUrl(String filePath, String urlString, int secsConnectTimeout, int secsReadTimeout)
			throws IOException {
		
		
		URLConnection conn = null;
		int ret = 0;
		boolean somethingRead = false;
		InputStream is = null;
		BufferedInputStream in = null;
		OutputStream fout = null;
		
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
		return 0;
	}

	
	/*********************************************************************************/
	
	public int saveUrl1(String filePath, String urlString, int secsConnectTimeout, int secsReadTimeout) throws MalformedURLException, IOException {
		// Files.createDirectories(file.getParent()); // optional, make sure parent dir exists
		BufferedInputStream in = null;
		OutputStream fout = null;
	    HttpURLConnection conn = null;
	    InputStream is = null;
	    
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
	    }
	    return 0;
	}

	public int saveUrl2(String filePath, String urlString, int secsConnectTimeout, int secsReadTimeout) throws MalformedURLException, IOException {
		// Files.createDirectories(file.getParent()); // optional, make sure parent dir
		// exists
		ReadableByteChannel rbc = null;
		FileChannel channel = null;
	    HttpURLConnection conn = null;
	    InputStream is = null;
	    
		try {
			Path file = Paths.get(filePath.replace(SWITCH_DIR_NAME, "case6") + getExt(urlString));
			URL url = new URL(urlString);
			Files.createDirectories(file.getParent());
			
			conn = (HttpURLConnection) streamFromUrl(url, secsConnectTimeout, secsReadTimeout);
			is = conn.getInputStream();
			rbc = Channels.newChannel(is);
			channel = FileChannel.open(file, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
			channel.transferFrom(rbc, 0, Long.MAX_VALUE);
			
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
		}
		
		return 0;
	}

	public int saveUrl3(String filePath, String urlString, int secsConnectTimeout, int secsReadTimeout) throws MalformedURLException, IOException {
			    // Files.createDirectories(file.getParent()); // optional, make sure parent dir exists
		
		InputStream in = null;
		HttpURLConnection conn = null;
		
		try {
			Path file = Paths.get(filePath.replace(SWITCH_DIR_NAME, "case7") + getExt(urlString));
			URL url = new URL(urlString);
			Files.createDirectories(file.getParent());
			
			conn = (HttpURLConnection) streamFromUrl(url, secsConnectTimeout,secsReadTimeout);
			in = conn.getInputStream();
			Files.copy(in, file, StandardCopyOption.REPLACE_EXISTING);	
			return 1;
		}
		catch(Exception e) {
			logger.error("[CASE7-ERROR]", e);
		}
		finally {
			if(in != null) {
				in.close();
			}
			if(conn != null) {
				conn.disconnect();
			}
		}
		return 0;
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



}
