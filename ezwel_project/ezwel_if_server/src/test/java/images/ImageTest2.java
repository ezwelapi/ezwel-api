package images;

import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class ImageTest2 implements Callable<Integer> {

	private static final Logger logger = LoggerFactory.getLogger(ImageTest2.class);
	
	ImagesDTO item;
	
	ImageAPI imageAPI;
	public ImageTest2(ImagesDTO item) {
		imageAPI = new ImageAPI();
		this.item = item;
	}
	
	@Override
	public Integer call() throws Exception {
		int out = 0;
		
			// case 2
			out += imageAPI.downloadFileFromURL(item.getUrlString(), item.getFilePath());
		
		//logger.debug("ImageTest2 result : {} ", out);
		return out;
	}
	
}
