package com.ezwel.htl.interfaces.service.data.allReg;


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
public class AllRegSubImagesOutSDO extends AbstractDTO {

	@APIFields(description = "전체시설일괄등록 output 이미지URL", maxLength=500)
	private String image;
	
	@APIFields(description = "전체시설일괄등록 output 이미지설명", maxLength=200)
	private String desc;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
