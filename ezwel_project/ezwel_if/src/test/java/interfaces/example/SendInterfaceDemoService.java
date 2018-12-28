package interfaces.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.http.data.UserAgentSDO;
import com.ezwel.htl.interfaces.commons.utils.CryptUtil;
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
		InterfaceFactory factory = new InterfaceFactory();
		factory.setConfigXmlPath("/interfaces/interface-configure.xml");
		factory.initFactory();
		
		ezcIfService = new EzcsendIFService();
	}


	// 문자발송
	public void testSendSms()  throws Exception {
		logger.debug("[START] testSendSms");
		
		UserAgentSDO userAgentDTO = new UserAgentSDO();
		
		userAgentDTO.setHttpAgentId("99999999"); //직영숙박
		
		//request value
		String callTo = "01037440698";
		String callFrom = "0232820579";
		String msgType = "SMS";
		String mmsSubject = "테스트";
		String smsText = "API 발송 테스트";
		String svcType = "1008";
		String smsUseYn = "Y";
		String templateCode = "10013";
		String serviceSeqno = "1710002752";
		
		String ebcallTo = CryptUtil.getEncodeBase64(callTo);
		String ebcallFrom = CryptUtil.getEncodeBase64(callFrom);
		String ebmsgType = CryptUtil.getEncodeBase64(msgType);
		String ebmmsSubject = CryptUtil.getEncodeBase64(mmsSubject);
		String ebsmsText = CryptUtil.getEncodeBase64(smsText);
		String ebsvcType = CryptUtil.getEncodeBase64(svcType);
		String ebsmsUseYn = CryptUtil.getEncodeBase64(smsUseYn);
		String ebtemplateCode = CryptUtil.getEncodeBase64(templateCode);
		String ebserviceSeqno = CryptUtil.getEncodeBase64(serviceSeqno);
		
		//Input parameter
		SendSmsInSDO sendSmsSDO = new SendSmsInSDO();
		
		sendSmsSDO.setCallTo(ebcallTo);
		sendSmsSDO.setCallFrom(ebcallFrom);
		sendSmsSDO.setMsgType(ebmsgType);
		sendSmsSDO.setMmsSubject(ebmmsSubject);
		sendSmsSDO.setSmsText(ebsmsText);
		sendSmsSDO.setSvcType(ebsvcType);
		sendSmsSDO.setSmsUseYn(ebsmsUseYn);
		sendSmsSDO.setTemplateCode(ebtemplateCode);
		sendSmsSDO.setServiceSeqno(ebserviceSeqno);
		
		//interface api call
		SendSmsOutSDO out = ezcIfService.callSendSms(userAgentDTO, sendSmsSDO);
		
		logger.debug("ebcallTo getDecodeBase64 : {}", CryptUtil.getDecodeBase64(ebcallTo));
		logger.debug("ebcallFrom getDecodeBase64 : {}", CryptUtil.getDecodeBase64(ebcallFrom));
		logger.debug("ebmsgType getDecodeBase64 : {}", CryptUtil.getDecodeBase64(ebmsgType));
		logger.debug("ebmmsSubject getDecodeBase64 : {}", CryptUtil.getDecodeBase64(ebmmsSubject));
		logger.debug("ebsmsText getDecodeBase64 : {}", CryptUtil.getDecodeBase64(ebsmsText));
		logger.debug("ebsvcType getDecodeBase64 : {}", CryptUtil.getDecodeBase64(ebsvcType));
		logger.debug("ebsmsUseYn getDecodeBase64 : {}", CryptUtil.getDecodeBase64(ebsmsUseYn));
		logger.debug("ebtemplateCode getDecodeBase64 : {}", CryptUtil.getDecodeBase64(ebtemplateCode));
		logger.debug("ebserviceSeqno getDecodeBase64 : {}", CryptUtil.getDecodeBase64(ebserviceSeqno));
		
		logger.debug("errorCode : {}", out.getErrorCode());
		logger.debug("errorMessage : {}", out.getErrorMessage());
		logger.debug("data : {}", out.getData());
		
		logger.debug("[END] testSendSms");
	}
	
}
