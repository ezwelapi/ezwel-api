package images;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;

public class ImagesDTO {

	int secsConnectTimeout = 1000;
	int secsReadTimeout = 10000;	
	String filePath = null;
	String urlString = null;
	
	public int getSecsConnectTimeout() {
		return secsConnectTimeout;
	}
	public void setSecsConnectTimeout(int secsConnectTimeout) {
		this.secsConnectTimeout = secsConnectTimeout;
	}
	public int getSecsReadTimeout() {
		return secsReadTimeout;
	}
	public void setSecsReadTimeout(int secsReadTimeout) {
		this.secsReadTimeout = secsReadTimeout;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getUrlString() {
		return urlString;
	}
	public void setUrlString(String urlString) {
		this.urlString = urlString;
	}
	
}
