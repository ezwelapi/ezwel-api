package com.ezwel.htl.interfaces.service.data.ezwelJob;


import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
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
@APIModel(description="주문대사(이지웰)")
@Data
@EqualsAndHashCode(callSuper=true)
public class EzwelJobInSDO extends AbstractSDO {

	@APIFields(description = "주문대사(이지웰) 제휴사아이디", required=true, maxLength=100)
	private String otaId;
	
	@APIFields(description = "주문대사(이지웰) 주문번호", required=false, maxLength=100)
	private String rsvNo;
	
	@APIFields(description = "주문대사(이지웰) 주문시작일", required=false, maxLength=8)
	private String rsvDateStart;
	
	@APIFields(description = "주문대사(이지웰) 주문종료일", required=false, maxLength=8)
	private String rsvDateEnd;

	public String getOtaId() {
		return otaId;
	}

	public void setOtaId(String otaId) {
		this.otaId = otaId;
	}
	
	public String getRsvNo() {
		return rsvNo;
	}

	public void setRsvNo(String rsvNo) {
		this.rsvNo = rsvNo;
	}

	public String getRsvDateStart() {
		return rsvDateStart;
	}

	public void setRsvDateStart(String rsvDateStart) {
		this.rsvDateStart = rsvDateStart;
	}

	public String getRsvDateEnd() {
		return rsvDateEnd;
	}

	public void setRsvDateEnd(String rsvDateEnd) {
		this.rsvDateEnd = rsvDateEnd;
	}

}
