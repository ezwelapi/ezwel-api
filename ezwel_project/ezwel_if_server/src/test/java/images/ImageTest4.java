package images;

import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class ImageTest4 implements Callable<Integer> {

	private static final Logger logger = LoggerFactory.getLogger(ImageTest4.class);
	
	ImagesDTO item;
	
	ImageAPI imageAPI;
	public ImageTest4(ImagesDTO item) {
		imageAPI = new ImageAPI();
		this.item = item;
	}
	
	@Override
	public Integer call() throws Exception {
		int out = 0;
		
			// case 4
			out += imageAPI.saveUrl(item.getFilePath(), item.getUrlString(), item.getSecsConnectTimeout(), item.getSecsReadTimeout());
		
		logger.debug("ImageTest4 result : {} ", out);
		return out;
	}
	
}
