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
		ezcFacls.add(1);
		ezcFacls.add(2);
		ezcFacls.add(3);
		ezcFacls.add(4);
		ezcFacls.add(5);
		ezcFacls.add(6);
		ezcFacls.add(7);
		ezcFacls.add(8);
		ezcFacls.add(9);
		ezcFacls.add(10);
		ezcFacls.add(11);
		ezcFacls.add(12);
		ezcFacls.add(13);
		
		Integer fromIndex = 10;
		Integer toIndex = fromIndex + 5;
		
		if(toIndex > ezcFacls.size()) {
			toIndex = ezcFacls.size();
		}
		logger.debug("{} ~ {}", fromIndex, toIndex);
		
		List<Integer> saveFaclRegDatas = ezcFacls.subList(fromIndex, toIndex);
		
		logger.debug("saveFaclRegDatas : {}",  saveFaclRegDatas.size());
		logger.debug("saveFaclRegDatas : {}",  saveFaclRegDatas);
		
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
