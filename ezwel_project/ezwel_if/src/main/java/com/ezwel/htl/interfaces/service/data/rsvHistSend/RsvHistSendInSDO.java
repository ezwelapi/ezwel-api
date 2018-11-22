package com.ezwel.htl.interfaces.service.data.rsvHistSend;


import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import lombok.Data;
import lombok.EqualsAndHashCode;



import com.ezwel.htl.interfaces.commons.annotation.APIFields;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author ypjeon@ebsolution.co.kr
 * @date 2018. 11. 13.
 */
@APIModel
@Data
@EqualsAndHashCode(callSuper=true)
public class RsvHistSendInSDO extends AbstractSDO {

	@APIFields(description = "결제완료내역전송 Input data")
	private RsvHistSendDataInSDO data;

	public RsvHistSendDataInSDO getData() {
		return data;
	}

	public void setData(RsvHistSendDataInSDO data) {
		this.data = data;
	}

}