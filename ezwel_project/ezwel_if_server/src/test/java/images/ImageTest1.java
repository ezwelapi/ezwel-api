package images;

import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class ImageTest1 implements Callable<Integer> {

	private static final Logger logger = LoggerFactory.getLogger(ImageTest1.class);
	
	ImagesDTO item;
	
	ImageAPI imageAPI;
	public ImageTest1(ImagesDTO item) {
		imageAPI = new ImageAPI();
		this.item = item;
	}
	
	@Override
	public Integer call() throws Exception {
		int out = 0;
		
			// case 1
			out += imageAPI.saveUrl(item.getFilePath(), item.getUrlString());
		
		//logger.debug("ImageTest1 result : {} ", out);
		return out;
	}
	
}
