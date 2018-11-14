package com.ezwel.htl.interfaces.service.dto.cancelFeePsrc;

import java.util.List;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 13.
 */
public class CancelFeePsrcDataOutDTO extends AbstractEntity {

	private String cancelFeeText;
	private List<CancelFeePsrcPenaltyOutDTO> penalty = null;

	public String getCancelFeeText() {
		return cancelFeeText;
	}

	public void setCancelFeeText(String cancelFeeText) {
		this.cancelFeeText = cancelFeeText;
	}

	public List<CancelFeePsrcPenaltyOutDTO> getPenalty() {
		return penalty;
	}

	public void setPenalty(List<CancelFeePsrcPenaltyOutDTO> penalty) {
		this.penalty = penalty;
	}
}
