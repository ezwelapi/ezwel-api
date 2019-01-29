package com.ezwel.htl.interfaces.commons.abstracts;

import java.util.ArrayList;
import java.util.List;

import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.sdo.IfLogSDO;
import com.ezwel.htl.interfaces.commons.sdo.MultiThreadResult;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <pre>
 *  AbstractSDO for *SDO
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */
@Data
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="API SDO Super Class")
public abstract class AbstractSDO extends APIObject {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "전체 데이터 개수")
	private long totalCount = OperateConstants.LONG_ZERO_VALUE;
	
	@APIFields(description = "페이지 번호")
	private long pageNum = OperateConstants.LONG_ZERO_VALUE;
	
	@APIFields(description = "페이지 목록 데이터 개수")
	private long pageCount = OperateConstants.LONG_MAX_VALUE;

	@APIFields(description = "인터페이스 로그")
	private IfLogSDO ifLog;
	
	@APIFields(description = "개인정보 암호화 여부")
	private boolean isEncPrvtInfo;
	
	@APIFields(description = "멀티쓰레드 인터페이스 결과 코드/메시지")
	private List<MultiThreadResult> multiThreadResults;
	
	@APIFields(description = "상세 메시지")
	private String detailMessage;
	
	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public long getPageNum() {
		return pageNum;
	}

	public void setPageNum(long pageNum) {
		this.pageNum = pageNum;
	}

	public long getPageCount() {
		return pageCount;
	}

	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}

	public IfLogSDO getIfLog() {
		return ifLog;
	}

	public void setIfLog(IfLogSDO ifLog) {
		this.ifLog = ifLog;
	}

	public boolean isEncPrvtInfo() {
		return isEncPrvtInfo;
	}

	public void setEncPrvtInfo(boolean isEncPrvtInfo) {
		this.isEncPrvtInfo = isEncPrvtInfo;
	}

	public List<MultiThreadResult> getMultiThreadResults() {
		return multiThreadResults;
	}

	public void setMultiThreadResults(List<MultiThreadResult> multiThreadResults) {
		this.multiThreadResults = multiThreadResults;
	}
	
	public void addMultiThreadResults(String code, String message, String restURI) {
		if(this.multiThreadResults == null) {
			this.multiThreadResults = new ArrayList<MultiThreadResult>();
		}
		MultiThreadResult result = new MultiThreadResult();
		result.setCode(code);
		result.setMessage(message);
		result.setRestURI(restURI);
		this.multiThreadResults.add(result);
	}

	public String getDetailMessage() {
		return detailMessage;
	}

	public void setDetailMessage(String detailMessage) {
		this.detailMessage = detailMessage;
	}
	
	
}
