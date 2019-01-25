package com.ezwel.htl.interfaces.server.commons.send;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.constants.HttpHeaderConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigSDO;
import com.ezwel.htl.interfaces.service.data.send.SmsSenderInSDO;
import com.ezwel.htl.interfaces.service.data.send.SmsSenderOutSDO;

/**
 * <pre>
 * 문자발송
 * </pre>
 * @author jyp@ebsolution.co.kr
 * @date   2019. 01. 22.
 */
@Component
@APIType(description="문자발송 인터페이스")
public class SmsSender {
	
	private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);
	
	static final String CALLFROM = "0232820579"; //수신번호
	static final String SVC_TYPE = "1008";		 //서비스구분코드(숙박)
	
	@APIOperation(description="문자발송 목록 인터페이스")
	public List<SmsSenderOutSDO> callSmsSender(List<SmsSenderInSDO> smsSenderInList) throws Exception {
		
		List<SmsSenderOutSDO> out = null;
		
		if(smsSenderInList != null) {
			out = new ArrayList<SmsSenderOutSDO>();
			for(SmsSenderInSDO sms : smsSenderInList) {
				out.add(callSmsSender(sms));
			}
		}
		
		return out;
	}
	
	@APIOperation(description="문자발송 인터페이스 HttpURLConnection")
	public SmsSenderOutSDO callSmsSender(SmsSenderInSDO smsSenderInSDO) throws Exception {
		
		String params = null;
		
		// 발송정보생성
		String callTo = smsSenderInSDO.getCallTo();		
		String callFrom = "";
		if (StringUtils.isEmpty(smsSenderInSDO.getCallFrom())) {
			callFrom = CALLFROM;
		}		
		String smsTxt = URLEncoder.encode(smsSenderInSDO.getSmsTxt(), "euc-kr");
		String svcType = SVC_TYPE;
		String templateCode = smsSenderInSDO.getTemplateCode();		
		String smsUseYn = "N";
		if (StringUtils.isEmpty(templateCode)) {
			smsUseYn = "Y";
		}
		
		params = "callTo="+callTo+"&callFrom="+callFrom+"&smsTxt="+smsTxt+"&svcType="+svcType+"&smsUseYn="+smsUseYn+"&templateCode="+templateCode;			
		
		HttpConfigSDO httpConfigSDO = new HttpConfigSDO();
		// HttpURLConnection
		URL url = new URL(InterfaceFactory.getOptionalApps().getSmsConfig().getSmsURI());
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setRequestMethod(OperateConstants.HTTP_METHOD_POST);
		urlConnection.setConnectTimeout(httpConfigSDO.getConnTimeout());
		urlConnection.setReadTimeout(httpConfigSDO.getReadTimeout());
		urlConnection.setDoOutput(true);
		urlConnection.addRequestProperty("Content-type", HttpHeaderConstants.CONTENT_TYPE_WWW_FORM + httpConfigSDO.getEncoding());
		urlConnection.addRequestProperty("Accept", HttpHeaderConstants.ACCEPT_APP_JSON);
		
		OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream(),"UTF-8");
		out.write(params);
		out.close();
		
		Map<String, List<String>> headers = urlConnection.getHeaderFields();
		Iterator<String> it = headers.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			List<String> values = headers.get(key);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < values.size(); i++) {
				sb.append(";" + values.get(i));
			}
		}
		
		SmsSenderOutSDO smsSenderOutSDO = new SmsSenderOutSDO();
		
		if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			smsSenderOutSDO.setData(streamToString(urlConnection.getInputStream()));
			smsSenderOutSDO.setSuccess(true);
		} else {
			if(urlConnection.getErrorStream() != null) {
				smsSenderOutSDO.setData(streamToString(urlConnection.getErrorStream()));
			} else {
				smsSenderOutSDO.setData(streamToString(urlConnection.getInputStream()));
			}
		}
		
		return smsSenderOutSDO;
	}
	
	@APIOperation(description="inputStream To String return")
    public static String streamToString(InputStream inputStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
        StringBuffer sb = new StringBuffer();
        try {
	        String stdLine="";
	        while((stdLine=in.readLine()) != null){
	        	sb.append(stdLine);
	        }
        } finally {
        	if(in != null){
        		in.close();
        	}
        }
        return sb.toString();
    }
}
