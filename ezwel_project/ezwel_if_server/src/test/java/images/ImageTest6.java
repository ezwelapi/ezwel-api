package images;

import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.thread.Local;



public class ImageTest6 implements Callable<Integer> {

	private static final Logger logger = LoggerFactory.getLogger(ImageTest6.class);
	
	ImagesDTO item;
	int count;
	ImageAPI imageAPI;
	public ImageTest6(ImagesDTO item, int count) {
		Local.commonHeader();
		//logger.debug("ImageTest6( {} )", count);
		imageAPI = new ImageAPI();
		this.item = item;
		this.count = count;
	}
	
	@Override
	public Integer call() throws Exception {
		int out = 0;
		try {
			// case 6
			out += imageAPI.saveUrl2(item.getFilePath(), item.getUrlString(), item.getSecsConnectTimeout(), item.getSecsReadTimeout(), count);
		}
		finally {
			Local.remove();
		}

		logger.debug("ImageTest6 result : {} ", out);
		return out;
	}
	
}
