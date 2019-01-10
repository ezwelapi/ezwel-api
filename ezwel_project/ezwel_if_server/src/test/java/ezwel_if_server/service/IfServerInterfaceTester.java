package ezwel_if_server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
import com.ezwel.htl.interfaces.server.service.OutsideService;
import com.ezwel.htl.interfaces.service.data.faclSearch.FaclSearchInSDO;

import junit.framework.TestCase;

public class IfServerInterfaceTester extends TestCase {

	private static Logger logger = LoggerFactory.getLogger(IfServerInterfaceTester.class);
	
	OutsideService outsideService;
	
	public IfServerInterfaceTester() {
		InterfaceFactory.initLocalTestInterfaceFactory();
		
		outsideService = new OutsideService();
	}
	
	/**
	 * 시설 검색(최저가 정보)
	 */
	public void testCallSddSearch() {
		
		UserAgentSDO userAgentDTO = new UserAgentSDO();
		
		//전체 제휴사 조회 (읽기전용)
		//userAgentDTO.setReadOnly(true); 
		//호텔패스 1개 제휴사만 조회 (읽기전용)
		//userAgentDTO.setHttpAgentId("10055550"); 
		
		userAgentDTO.setHttpAgentGroupId("multiFaclSearch"); 
		userAgentDTO.setHttpAgentType("AP02PO");
		userAgentDTO.setHttpChannelCd("1");
		userAgentDTO.setHttpClientId("ez1");
		userAgentDTO.setHttpRequestId("test");
		
		FaclSearchInSDO faclSearchDTO = new FaclSearchInSDO();

		//필수
		faclSearchDTO.setGunguCode("11"); //서울
		faclSearchDTO.setCheckInDate("20180101");
		faclSearchDTO.setCheckOutDate("20190110");
		//옵션
		faclSearchDTO.setSidoCode("11230");
		
		outsideService.callFaclSearch(userAgentDTO, faclSearchDTO);
		
		
		
	}
	
	
}
