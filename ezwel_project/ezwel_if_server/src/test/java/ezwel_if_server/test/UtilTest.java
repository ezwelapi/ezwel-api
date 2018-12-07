package ezwel_if_server.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.server.commons.utils.CommonUtil;
import com.ezwel.htl.interfaces.server.commons.utils.data.ImageSDO;
import com.ezwel.htl.interfaces.server.entities.EzcFacl;

public class UtilTest {

	private static final Logger logger = LoggerFactory.getLogger(UtilTest.class);
	/*
	public UtilTest() {
		InterfaceFactory factory = new InterfaceFactory();
		factory.setConfigXmlPath("/interfaces/interface-configure.xml");
		factory.initFactory();
	}
	*/
	
	@Test
	public void test() {
		
		List<Integer> ezcFacls = new ArrayList<Integer>();
		
		for(int i = 0; i < 2251; i++) {
			ezcFacls.add(i);
			logger.debug("{}", i);
		}
		
		testSubList(ezcFacls, 0);
	}
	
	public void testSubList(List<Integer> ezcFacls, int fromIndex) {
		
		Integer toIndex = fromIndex + 50;
		
		List<Integer> saveFaclRegDatas = null;
		if(toIndex > ezcFacls.size()) {
			toIndex = ezcFacls.size();
		}
		
		
		saveFaclRegDatas = ezcFacls.subList(fromIndex, toIndex);
		
		logger.debug("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		logger.debug("{} ~ {}", fromIndex, toIndex);
		logger.debug("saveFaclRegDatas : {}",  saveFaclRegDatas.size());
		logger.debug("saveFaclRegDatas : {}",  saveFaclRegDatas);
		
		if(ezcFacls != null && ezcFacls.size() > toIndex) {
			testSubList(ezcFacls/* 제휴사 별 시설 목록 */, toIndex);
		}
		
		if(saveFaclRegDatas != null) {
			saveFaclRegDatas.removeAll(saveFaclRegDatas);
		}
	}
	
	//@Test
	public void imageDownload() {
		CommonUtil comm = new CommonUtil();
		ImageSDO imageSDO = new ImageSDO();
		/*다운로드 할 이미지 URL*/
		imageSDO.setImageURL("http://tourimage.interpark.com/Product/Housing/Hotel/17000047/17000047_01bb.jpg?ver=20181129");
		ImageSDO out = comm.getImage(imageSDO, true);
		logger.debug("ImageSDO out : {}", out);
	}
}
