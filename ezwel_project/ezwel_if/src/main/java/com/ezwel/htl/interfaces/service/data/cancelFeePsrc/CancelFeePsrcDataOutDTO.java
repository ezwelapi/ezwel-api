package com.ezwel.htl.interfaces.service.data.cancelFeePsrc;

import java.util.List;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractDTO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 13.
 */
@APIModel
public class CancelFeePsrcDataOutDTO extends AbstractDTO {

	@APIFields(description = "취소수수료규정 output 대체텍스트", maxLength=2000)
	private String cancelFeeText;
	
	@APIFields(description = "취소수수료규정 output penalty")
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
