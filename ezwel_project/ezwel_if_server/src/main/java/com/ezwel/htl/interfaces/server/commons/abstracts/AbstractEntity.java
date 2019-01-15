package com.ezwel.htl.interfaces.server.commons.abstracts;

import com.ezwel.htl.interfaces.commons.abstracts.APIObject;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;

/**
 * <pre>
 *  AbstractEntity for *Entity
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */
@APIModel(modelNames="API Entity Super Class")
public abstract class AbstractEntity extends APIObject {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private long totalCount = OperateConstants.LONG_ZERO_VALUE;
	
	private long pageNum = OperateConstants.LONG_ZERO_VALUE;
	
	private long pageCount = OperateConstants.LONG_MAX_VALUE;

	
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
	
	
}
