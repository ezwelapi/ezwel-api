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

	private int pageNum = OperateConstants.INTEGER_ZERO_VALUE;
	
	private int pageCount = OperateConstants.INTEGER_ZERO_VALUE;

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	
}
