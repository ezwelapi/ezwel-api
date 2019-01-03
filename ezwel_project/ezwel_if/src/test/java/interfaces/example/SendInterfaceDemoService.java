package interfaces.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
import com.ezwel.htl.interfaces.service.EzcsendIFService;
import com.ezwel.htl.interfaces.service.data.send.SendSmsInSDO;
import com.ezwel.htl.interfaces.service.data.send.SendSmsOutSDO;

import junit.framework.TestCase;

/**
 * <pre>
 * 
 * </pre>
 * @author ypjeon@ebsolution.co.kr
 * @date   2018. 12. 19.
 */
public class SendInterfaceDemoService extends TestCase {

	private static final Logger logger = LoggerFactory.getLogger(SendInterfaceDemoService.class);
	
	private EzcsendIFService ezcIfService; // if ezc
	
	/**
	 * 아레 컨트스럭터는 로컬테스트 떄만 사용한다.
	 * InterfaceFactory는 스프링 어플리케이션 초기화시 스프링 빈으로 초기화된다.
	 */
	public SendInterfaceDemoService()  throws Exception {
		InterfaceFactory.initLocalTestInterfaceFactory();
		
		ezcIfService = new EzcsendIFService();
	}


	// 문자발송
	public void testSendSms()  throws Exception {
		logger.debug("[START] testSendSms");
		
		UserAgentSDO userAgentDTO = new UserAgentSDO();
		
		userAgentDTO.setHttpAgentId("99999999"); //직영숙박
		
		//Input parameter
		SendSmsInSDO sendSmsSDO = new SendSmsInSDO();
		
		sendSmsSDO.setCallTo("01037440698");
		sendSmsSDO.setCallFrom("0232820579");
		sendSmsSDO.setMsgType("SMS");
		sendSmsSDO.setMmsSubject("test");
		sendSmsSDO.setSmsText("test");
		sendSmsSDO.setSvcType("1008");
		sendSmsSDO.setSmsUseYn("Y");
		sendSmsSDO.setTemplateCode("10013");
		sendSmsSDO.setServiceSeqno("1710002752");
		
		//interface api call
		SendSmsOutSDO out = ezcIfService.callSendSms(userAgentDTO, sendSmsSDO);
		
		logger.debug("errorCode : {}", out.getErrorCode());
		logger.debug("errorMessage : {}", out.getErrorMessage());
		logger.debug("data : {}", out.getData());
		
		logger.debug("[END] testSendSms");
	}
	
}
