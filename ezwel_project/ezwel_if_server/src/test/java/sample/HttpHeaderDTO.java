package sample;

import java.io.Serializable;

import com.ezwel.htl.interfaces.commons.utils.APIUtil;

/**
 * <pre>
 * Http 인터페이스 API용 DTO  
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */
public class HttpHeaderDTO implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 5754987302124785681L;

	//(description = "에이전트 아이디", required=true, httpHeader=true, headerName="http-agent-id")
	private String httpAgentId;
	
	//(description = "API 키(ezwel발급)", required=true, httpHeader=true, headerName="http-api-key")
	private String httpApiKey; 

	//(description = "API 시그니처", required=true, httpHeader=true, headerName="http-api-signature")
	private String httpApiSignature; 

	//(description = "요정시간(타임스탬프)", required=true, httpHeader=true, headerName="http-api-timestamp")
	private String httpApiTimestamp;
	
	
	public HttpHeaderDTO() {
		this.reset();
	}
	
	private void reset() {
		httpApiKey = null;
		httpApiSignature = null;
		httpApiTimestamp = APIUtil.getTimeStamp();
		httpAgentId = null;
	}

	public String getHttpAgentId() {
		return httpAgentId;
	}

	public void setHttpAgentId(String httpAgentId) {
		this.httpAgentId = httpAgentId;
	}

	public String getHttpApiKey() {
		return httpApiKey;
	}

	public void setHttpApiKey(String httpApiKey) {
		this.httpApiKey = httpApiKey;
	}

	public String getHttpApiSignature() {
		return httpApiSignature;
	}

	public void setHttpApiSignature(String httpApiSignature) {
		this.httpApiSignature = httpApiSignature;
	}

	public String getHttpApiTimestamp() {
		return httpApiTimestamp;
	}

	public void setHttpApiTimestamp(String httpApiTimestamp) {
		this.httpApiTimestamp = httpApiTimestamp;
	}
	
	
}
