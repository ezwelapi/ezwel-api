package com.ezwel.htl.interfaces.commons.send;

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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.HttpHeaderConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.http.data.HttpConfigSDO;
import com.ezwel.htl.interfaces.commons.send.data.SmsSenderSDO;

@Component
@APIType(description="문자발송 인터페이스")
public class SmsSender {
	
	private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);
	
	@APIOperation(description="문자발송 인터페이스 HttpURLConnection")
	public static String requestUrl(HttpConfigSDO httpConfigSDO, SmsSenderSDO smsSenderSDO) throws Exception {
		
		String params = null;
		
		// 발송정보생성
		String callTo = smsSenderSDO.getCallTo();		
		String callFrom = "";
		if (StringUtils.isEmpty(smsSenderSDO.getCallFrom())) {
			callFrom = "0232820579";
		}		
		String smsTxt = URLEncoder.encode(smsSenderSDO.getSmsTxt(), "euc-kr");
		String svcType = "1008";
		String templateCode = smsSenderSDO.getTemplateCode();		
		String smsUseYn = "N";
		if (StringUtils.isEmpty(templateCode)) {
			smsUseYn = "Y";
		}
		
		params = "callTo="+callTo+"&callFrom="+callFrom+"&smsTxt="+smsTxt+"&svcType="+svcType+"&smsUseYn="+smsUseYn+"&templateCode="+templateCode;			
		
		// HttpURLConnection
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
		
//		SmsSenderOutSDO smsSenderOutSDO = new SmsSenderOutSDO();
//		
//		if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {					
//			smsSenderOutSDO.setData(streamToString(urlConnection.getInputStream()));
//		} else {
//			smsSenderOutSDO.setData(streamToString(urlConnection.getInputStream()));	
//		}
		
		String streamOut = null;
		streamOut = streamToString(urlConnection.getInputStream());
		
		return streamOut;
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
