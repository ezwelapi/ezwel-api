package images;

import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.thread.Local;



public class ImageTest3 implements Callable<Integer> {

	private static final Logger logger = LoggerFactory.getLogger(ImageTest3.class);
	
	ImagesDTO item;
	int count;
	ImageAPI imageAPI;
	public ImageTest3(ImagesDTO item, int count) {
		//Local.commonHeader();
		
		imageAPI = new ImageAPI();
		this.item = item;
		this.count = count;
	}
	
	@Override
	public Integer call() throws Exception {
		logger.debug("ImageTest3 call( {} )", count);
		int out = 0;
		try {
			
			// case 3
			out += imageAPI.downloadBuffer(item.getUrlString(), item.getFilePath(), count, 0);
		}
		finally {
			//Local.remove();
			
			//Thread.sleep(500);
		}
		
		//logger.debug("ImageTest3 result : {} ", out);
		return out;
	}
	
}
