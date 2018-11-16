package com.ezwel.htl.interfaces.service.dto.rsvHistSend;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractDTO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author ypjeon@ebsolution.co.kr
 * @date 2018. 11. 13.
 */
public class RsvHistSendInDTO extends AbstractDTO {

	@APIFields(description = "결제완료내역전송 Input data")
	private RsvHistSendDataInDTO data;

	public RsvHistSendDataInDTO getData() {
		return data;
	}

	public void setData(RsvHistSendDataInDTO data) {
		this.data = data;
	}

}