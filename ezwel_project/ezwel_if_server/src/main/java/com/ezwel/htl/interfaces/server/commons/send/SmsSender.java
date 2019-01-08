package com.ezwel.htl.interfaces.server.commons.send;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.configure.InterfaceFactory;
import com.ezwel.htl.interfaces.commons.constants.HttpHeaderConstants;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigSDO;
import com.ezwel.htl.interfaces.server.commons.intercepter.HandlerInterceptor;
import com.ezwel.htl.interfaces.server.commons.send.data.SmsSenderInSDO;
import com.ezwel.htl.interfaces.server.commons.send.data.SmsSenderOutSDO;

@Component
@APIType(description="문자발송 인터페이스")
public class SmsSender {
	
	private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);
	
	@APIOperation(description="문자발송 인터페이스")
	public SmsSenderOutSDO callSmsSender(SmsSenderInSDO smsSenderSDO) {		
		return callSmsSender(smsSenderSDO, false);
	}
	
	@APIOperation(description="문자발송 인터페이스")
	public SmsSenderOutSDO callSmsSender(SmsSenderInSDO smsSenderSDO, boolean isEzwelInsideInterface) {
		
		SmsSenderOutSDO out = null;
		
		try {
			
			HttpConfigSDO httpConfigSDO = new HttpConfigSDO();
			httpConfigSDO.setRestURI(InterfaceFactory.getOptionalApps().getSmsConfig().getRestURI());
			httpConfigSDO.setEzwelInsideInterface(isEzwelInsideInterface);
			
			out = (SmsSenderOutSDO) requestUrl(httpConfigSDO, smsSenderSDO);
			
		}
		catch(Exception e) {
			throw new APIException(MessageConstants.RESPONSE_CODE_9100, "문자발송 인터페이스 장애발생.", e);
		}
		
		return out;		
	}
	
	@APIOperation(description="문자발송 인터페이스 HttpURLConnection")
	public static SmsSenderOutSDO requestUrl(HttpConfigSDO httpConfigSDO, SmsSenderInSDO smsSenderSDO) throws Exception {
		
		String params = null;
		
		String callTo = smsSenderSDO.getCallTo();
		String callFrom = smsSenderSDO.getCallFrom();
		String mmsSubject = URLEncoder.encode(smsSenderSDO.getMmsSubject(), "euc-kr");
		String smsTxt = URLEncoder.encode(smsSenderSDO.getSmsTxt(), "euc-kr");
		String svcType = smsSenderSDO.getSvcType();
		String smsUseYn = smsSenderSDO.getSmsUseYn();
		String templateCode = smsSenderSDO.getTemplateCode();
		
		params = "callTo="+callTo+"&callFrom="+callFrom+"&mmsSubject="+mmsSubject+"&smsTxt="+smsTxt+"&svcType="+svcType+"&smsUseYn="+smsUseYn+"&templateCode="+templateCode;			
		
		URL url = new URL(httpConfigSDO.getRestURI());
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
		
		smsSenderOutSDO.setErrorCode(urlConnection.getResponseCode());
		smsSenderOutSDO.setErrorMessage(urlConnection.getResponseMessage());
		smsSenderOutSDO.setData(streamToString(urlConnection.getInputStream()));
		
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
