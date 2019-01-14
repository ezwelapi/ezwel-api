package com.ezwel.htl.interfaces.commons.abstracts;

import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;

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
