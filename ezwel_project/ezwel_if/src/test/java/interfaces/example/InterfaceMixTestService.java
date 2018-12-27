package interfaces.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.adapter.OutsideIFAdapter;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
import com.ezwel.htl.interfaces.service.EzcsendIFService;
import com.ezwel.htl.interfaces.service.OutsideIFService;
import com.ezwel.htl.interfaces.service.data.send.SendSmsInSDO;
import com.ezwel.htl.interfaces.service.data.send.SendSmsOutSDO;

import junit.framework.TestCase;

/**
 * <pre>
 * 테스트 도중 문제발생 케이스 디버깅용 테스트클래스
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 19.
 */
public class InterfaceMixTestService extends TestCase {

	private static final Logger logger = LoggerFactory.getLogger(InterfaceMixTestService.class);
	
	private OutsideIFAdapter outIfAdapter; // if Adapter
	
	private OutsideIFService outIfService; // if Service
	
	private EzcsendIFService ezcIfService; // if Service
	
	/**
	 * 아레 컨트스럭터는 로컬테스트 떄만 사용한다.
	 * InterfaceFactory는 스프링 어플리케이션 초기화시 스프링 빈으로 초기화된다.
	 */
	public InterfaceMixTestService()  throws Exception {
		InterfaceFactory factory = new InterfaceFactory();
		factory.setConfigXmlPath("/interfaces/interface-configure.xml");
		factory.initFactory();
		
		outIfAdapter = new OutsideIFAdapter();
		outIfService = new OutsideIFService();
		ezcIfService = new EzcsendIFService();
	}

	public void testSendSms()  throws Exception {
		logger.debug("[START] testSendSms");
		
		UserAgentSDO userAgentDTO = new UserAgentSDO();
		
		userAgentDTO.setHttpAgentId("99999999"); //직영숙박
		
		//Input parameter
		SendSmsInSDO sendSmsSDO = new SendSmsInSDO();
		
		sendSmsSDO.setCallTo("01037440698");
		sendSmsSDO.setCallFrom("0232820579");
		sendSmsSDO.setMmsSubject("테스트");
		sendSmsSDO.setSmsText("테스트");
		sendSmsSDO.setSvcType("1008");
		sendSmsSDO.setSmsUseYn("N");
		sendSmsSDO.setTemplateCode("N");
		
		//interface api call
		SendSmsOutSDO out = ezcIfService.callSendSms(userAgentDTO, sendSmsSDO);
		
		logger.debug("errorCode : {}", out.getErrorCode());
		logger.debug("errorMessage : {}", out.getErrorMessage());
		logger.debug("data : {}", out.getData());
		
		logger.debug("[END] testSendSms");
	}
	
}
