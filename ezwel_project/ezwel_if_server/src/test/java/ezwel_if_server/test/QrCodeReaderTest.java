package ezwel_if_server.test;

import java.awt.image.BufferedImage;
import java.nio.file.Paths;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.ImageReader;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

public class QrCodeReaderTest {
	
	private static final Logger logger = LoggerFactory.getLogger(QrCodeReaderTest.class);
	
	@Test
	public void main() {
		
		try {
			
			BufferedImage image = ImageReader.readImage(Paths.get("D:\\qrtest\\123456789.png").toUri());
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer bin = new HybridBinarizer(source);
			BinaryBitmap bitmap = new BinaryBitmap(bin);
			Result result = new QRCodeReader().decode(bitmap);
			
			logger.debug( "QRCodeReader Text : {}" , result.toString());
			
		} catch (Exception e) {
            e.printStackTrace();
        }
		
	}
	
}