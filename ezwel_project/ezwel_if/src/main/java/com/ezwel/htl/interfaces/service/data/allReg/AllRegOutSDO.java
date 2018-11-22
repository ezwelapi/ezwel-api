package com.ezwel.htl.interfaces.service.data.allReg;

import java.util.List;


import com.ezwel.htl.interfaces.commons.abstracts.AbstractDTO;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import lombok.Data;
import lombok.EqualsAndHashCode;



import com.ezwel.htl.interfaces.commons.annotation.APIFields;

/**
 * <pre>
 * 
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date   2018. 11. 13.
 */
@APIModel
@Data
@EqualsAndHashCode(callSuper=true)
public class AllRegOutSDO extends AbstractDTO {

	@APIFields(description = "전체시설일괄등록 output code", required=true, maxLength=4)
	private String code;
	
	@APIFields(description = "전체시설일괄등록 output message", maxLength=100)
	private String message;
	
	@APIFields(description = "전체시설일괄등록 output data")
	private List<AllRegDataOutSDO> data = null;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<AllRegDataOutSDO> getData() {
		return data;
	}

	public void setData(List<AllRegDataOutSDO> data) {
		this.data = data;
	}

}
