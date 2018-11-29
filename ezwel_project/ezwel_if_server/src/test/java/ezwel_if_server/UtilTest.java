package ezwel_if_server;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.server.commons.utils.CommonUtil;

public class UtilTest {

	private static final Logger logger = LoggerFactory.getLogger(UtilTest.class);
	
	@Test
	public void imageDownload() {
		CommonUtil comm = new CommonUtil();
	
		/*다운로드 할 이미지 URL*/
		String imageHttpURL = "http://finalpsd.com/wp-content/uploads/2018/03/dragon_thum.jpg"; 
		/*저장할 경로 및 파일명*/
		String saveFilePath = "D:/02.Workspace/imageDownload";
		String out = comm.getImage(imageHttpURL, saveFilePath, true);
		logger.debug("out : {}", out);
	}
}
