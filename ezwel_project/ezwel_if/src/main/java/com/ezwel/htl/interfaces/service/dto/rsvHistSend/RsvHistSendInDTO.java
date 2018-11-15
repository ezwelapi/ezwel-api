package com.ezwel.htl.interfaces.service.dto.rsvHistSend;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author ypjeon@ebsolution.co.kr
 * @date 2018. 11. 13.
 */
public class RsvHistSendInDTO extends AbstractEntity {

	private RsvHistSendDataInDTO data;

	public RsvHistSendDataInDTO getData() {
		return data;
	}

	public void setData(RsvHistSendDataInDTO data) {
		this.data = data;
	}

}