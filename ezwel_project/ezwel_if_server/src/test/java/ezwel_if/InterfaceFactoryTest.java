package ezwel_if;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 15.
 */
public class InterfaceFactoryTest {

	private static final Logger logger = LoggerFactory.getLogger(InterfaceFactoryTest.class);
	
	@Test
	public void test() {
		
		InterfaceFactory factory = new InterfaceFactory();
		
		factory.setConfigXmlPath("/interfaces/interface-configure.xml");
		factory.initFactory();
		//logger.debug("###################################");
		//logger.debug(">> {}", InterfaceFactory.getInterfaceChannels());
		//logger.debug("###################################");
		//logger.debug("{}", InterfaceFactory.getChannel("ezwelJob", "10000496"));
		/*
		logger.debug("###################################");
		logger.debug("###################################");
		logger.debug("###################################");
		List<HttpConfigDTO> item = InterfaceFactory.getChannelGroup("faclSearch", "chanGroup-01");
		logger.debug("size : {}", item.size());
		for(HttpConfigDTO http : item) {
			logger.debug("http : {}", http);
		}
		*/
	}
}
