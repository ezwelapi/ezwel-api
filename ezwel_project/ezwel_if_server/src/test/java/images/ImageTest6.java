package images;

import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class ImageTest6 implements Callable<Integer> {

	private static final Logger logger = LoggerFactory.getLogger(ImageTest6.class);
	
	ImagesDTO item;
	
	ImageAPI imageAPI;
	public ImageTest6(ImagesDTO item) {
		imageAPI = new ImageAPI();
		this.item = item;
	}
	
	@Override
	public Integer call() throws Exception {
		int out = 0;
		
			// case 6
			out += imageAPI.saveUrl2(item.getFilePath(), item.getUrlString(), item.getSecsConnectTimeout(), item.getSecsReadTimeout());
		

		logger.debug("ImageTest6 result : {} ", out);
		return out;
	}
	
}
