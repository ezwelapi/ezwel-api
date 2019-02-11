package com.ezwel.htl.interfaces.server.commons.send;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractDataAccessObject;
import com.ezwel.htl.interfaces.server.commons.intercepter.HandlerInterceptor;
import com.ezwel.htl.interfaces.service.data.send.FaxReaderOutSDO;

/**
 * <pre>
 * 팩스발송
 * </pre>
 * @author ypjeon@ebsolution.co.kr
 * @date   2019. 01. 22.
 */
@Repository
@APIType(description="팩스수신")
public class FaxReader extends AbstractDataAccessObject {
	
	private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);
	
	@APIOperation(description="팩스수신 인터페이스 DB Update")
	public FaxReaderOutSDO callFaxReader(String reservNum) {
		
//		try {
//			
//			BufferedImage image = ImageReader.readImage(Paths.get("D:\\qrtest\\123456789.png").toUri());
//			LuminanceSource source = new BufferedImageLuminanceSource(image);
//			Binarizer bin = new HybridBinarizer(source);
//			BinaryBitmap bitmap = new BinaryBitmap(bin);
//			Result result = new QRCodeReader().decode(bitmap);
//			
//			logger.debug( "QRCodeReader Text : {}" , result.toString());
//			
//		} catch (Exception e) {
//            e.printStackTrace();
//        }
		
		FaxReaderOutSDO faxReaderOutSDO = new FaxReaderOutSDO();
		faxReaderOutSDO.setSuccess(true);
			
		return faxReaderOutSDO;
	}
	
}
