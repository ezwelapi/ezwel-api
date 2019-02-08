package ezwel_if_server.test;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QrCodeWriterTest {
	
	private static final Logger logger = LoggerFactory.getLogger(QrCodeWriterTest.class);
	
	@Test
	public void main() {
		
        try {
            
        	File file = null;
            
        	// QrCode 저장 디렉토리
            file = new File("D:\\qrtest");
            
            // QrCode File Mkdirs
            if(!file.exists()) {
                file.mkdirs();
            }
            
            // QrCode Link URL
            String qrCodeUrl = new String("123456789".getBytes("UTF-8"), "ISO-8859-1");
            
            // QrCode Color
            int qrCodeColor = 0x00000000;
            
            // QrCode BackGround Color
            int qrCodeBackColor = 0xFFFFFFFF;
             
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            
            //  QrCode width height
            BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeUrl, BarcodeFormat.QR_CODE, 200, 200);
            
            // QrCode Matrix Image
            MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrCodeColor, qrCodeBackColor);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix,matrixToImageConfig);
            
            // ImageIO QrCode Write
            ImageIO.write(bufferedImage, "png", new File("D:\\qrtest\\123456789.png"));
             
        } catch (Exception e) {
            e.printStackTrace();
        }  
	}
	
}