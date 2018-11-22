package com.ezwel.htl.interfaces.service.data.cancelFeePsrc;

import java.util.List;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractDTO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 13.
 */
@APIModel
@Data
@EqualsAndHashCode(callSuper=true)
public class CancelFeePsrcDataOutSDO extends AbstractDTO {

	@APIFields(description = "취소수수료규정 output 대체텍스트", maxLength=2000)
	private String cancelFeeText;
	
	@APIFields(description = "취소수수료규정 output penalty")
	private List<CancelFeePsrcPenaltyOutSDO> penalty = null;

	
	public String getCancelFeeText() {
		return cancelFeeText;
	}

	public void setCancelFeeText(String cancelFeeText) {
		this.cancelFeeText = cancelFeeText;
	}

	public List<CancelFeePsrcPenaltyOutSDO> getPenalty() {
		return penalty;
	}

	public void setPenalty(List<CancelFeePsrcPenaltyOutSDO> penalty) {
		this.penalty = penalty;
	}
}
