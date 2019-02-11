package ezwel_if_server.test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.ImageReader;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

public class QrCodeTest {
	
	private static final Logger logger = LoggerFactory.getLogger(QrCodeTest.class);
	
	@Test
	public void main() {
		try {
			QrCodeWriter();
			QrCodeReader();
		} catch (WriterException | IOException | NotFoundException | ChecksumException | FormatException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Generate QRCode PNG image.
	 *
	 * @throws WriterException the writer exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void QrCodeWriter() throws WriterException, IOException {
			
		try {
			
			String contents = new String("123456789".getBytes("UTF-8"), "ISO-8859-1");
			
			Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, Charset.forName("UTF-8").toString());
			
			BitMatrix matrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, 200, 200, hints);
					
	        MatrixToImageWriter.writeToPath(matrix, "PNG", Paths.get("D:/qrtest/123456789.png"));
	        
	        logger.debug( "QrCodeWriter Text : {}" , contents);
	        
		} catch (Exception e) {
        	e.printStackTrace();
    	}
	}

	private static void QrCodeReader() throws IOException, NotFoundException, ChecksumException, FormatException {
		
		try {
			
			BufferedImage image = ImageReader.readImage(Paths.get("d:/qrtest/123456789.png").toUri());
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