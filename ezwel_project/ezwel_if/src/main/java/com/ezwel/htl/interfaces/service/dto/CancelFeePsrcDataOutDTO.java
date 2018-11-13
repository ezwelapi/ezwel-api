package com.ezwel.htl.interfaces.service.dto;

import java.util.List;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 13.
 */
public class CancelFeePsrcDataOutDTO {

	private String cancelFeeText;
	private List<Penalty> penalty = null;

	public String getCancelFeeText() {
		return cancelFeeText;
	}

	public void setCancelFeeText(String cancelFeeText) {
		this.cancelFeeText = cancelFeeText;
	}

	public List<Penalty> getPenalty() {
		return penalty;
	}

	public void setPenalty(List<Penalty> penalty) {
		this.penalty = penalty;
	}

}
