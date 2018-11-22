package com.ezwel.htl.interfaces.service.data.record;


import com.ezwel.htl.interfaces.commons.abstracts.AbstractDTO;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import lombok.Data;
import lombok.EqualsAndHashCode;



import com.ezwel.htl.interfaces.commons.annotation.APIFields;

/**
 * <pre>
 * 
 * </pre>
 * @author ypjeon@ebsolution.co.kr
 * @date   2018. 11. 13.
 */

@APIModel
@Data
@EqualsAndHashCode(callSuper=true)
public class RecordInSDO extends AbstractDTO {



	
	@APIFields(description = "시설신규등록수정 Input 시설정보연동URL", required=true, maxLength=300)
	private String dataUrl;

	public String getDataUrl() {
		return dataUrl;
	}

	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}

}
