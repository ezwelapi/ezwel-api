package images;

import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class ImageTest3 implements Callable<Integer> {

	private static final Logger logger = LoggerFactory.getLogger(ImageTest3.class);
	
	ImagesDTO item;
	
	ImageAPI imageAPI;
	public ImageTest3(ImagesDTO item) {
		imageAPI = new ImageAPI();
		this.item = item;
	}
	
	@Override
	public Integer call() throws Exception {
		int out = 0;
		
			// case 3
			out += imageAPI.downloadBuffer(item.getUrlString(), item.getFilePath());
		
		//logger.debug("ImageTest3 result : {} ", out);
		return out;
	}
	
}
