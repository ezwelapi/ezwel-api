package com.ezwel.htl.interfaces.service.data.cancelFeePsrc;


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
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 13.
 */

@APIModel
@Data
@EqualsAndHashCode(callSuper=true)
public class CancelFeePsrcInSDO extends AbstractSDO {
	
	@APIFields(description = "취소수수료규정 Input 상품코드", required=true, maxLength=100)
	private String pdtNo;
	
	@APIFields(description = "취소수수료규정 Input 객실코드", required=true, maxLength=500)
	private String roomNo;
	
	@APIFields(description = "취소수수료규정 Input 체크인", required=true, maxLength=8)
	private String checkInDate;
	
	@APIFields(description = "취소수수료규정 Input 체크아웃", required=true, maxLength=8)
	private String checkOutDate;
	
	@APIFields(description = "취소수수료규정 Input 객실수", required=true, maxLength=1)
	private Integer roomCnt;
	
	public String getPdtNo() {
		return pdtNo;
	}

	public void setPdtNo(String pdtNo) {
		this.pdtNo = pdtNo;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}

	public String getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public Integer getRoomCnt() {
		return roomCnt;
	}

	public void setRoomCnt(Integer roomCnt) {
		this.roomCnt = roomCnt;
	}


}
