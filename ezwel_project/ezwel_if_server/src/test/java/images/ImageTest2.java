package images;

import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezwel.htl.interfaces.commons.thread.Local;



public class ImageTest2 implements Callable<Integer> {

	private static final Logger logger = LoggerFactory.getLogger(ImageTest2.class);
	
	ImagesDTO item;
	int count;
	ImageAPI imageAPI;
	public ImageTest2(ImagesDTO item, int count) {
		Local.commonHeader();
		//logger.debug("ImageTest2( {} )", count);
		imageAPI = new ImageAPI();
		this.item = item;
		this.count = count;
	}
	
	@Override
	public Integer call() throws Exception {
		int out = 0;
		try {
			// case 2
			out += imageAPI.downloadFileFromURL(item.getUrlString(), item.getFilePath(), count);
		}
		finally {
			Local.remove();
		}
		//logger.debug("ImageTest2 result : {} ", out);
		return out;
	}
	
}
