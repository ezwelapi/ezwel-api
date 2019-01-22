package images;

import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class ImageTest5 implements Callable<Integer> {

	private static final Logger logger = LoggerFactory.getLogger(ImageTest5.class);
	
	ImagesDTO item;
	
	ImageAPI imageAPI;
	public ImageTest5(ImagesDTO item) {
		imageAPI = new ImageAPI();
		this.item = item;
	}
	
	@Override
	public Integer call() throws Exception {
		int out = 0;
		
			// case 5
			out += imageAPI.saveUrl1(item.getFilePath(), item.getUrlString(), item.getSecsConnectTimeout(), item.getSecsReadTimeout());
		

		logger.debug("ImageTest5 result : {} ", out);
		return out;
	}
	
}
