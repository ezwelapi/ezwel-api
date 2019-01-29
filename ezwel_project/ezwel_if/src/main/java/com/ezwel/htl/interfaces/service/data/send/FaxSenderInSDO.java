package com.ezwel.htl.interfaces.service.data.send;

import java.math.BigDecimal;
import java.util.Date;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author ypjeon@ebsolution.co.kr
 * @date 2019.01.07
 */

@APIModel(description="팩스발송 정보")
public class FaxSenderInSDO extends AbstractSDO {
	
	@APIFields(description = "고유번호(발송고유번호)")
	private BigDecimal trBatchId;
	
	@APIFields(description = "발송일시")
	private Date trSendDate;
	
	@APIFields(description = "발송제목", required=true, maxLength=128)
	private String trTitle;
	
	@APIFields(description = "발신자이름", required=true, maxLength=50)
	private String trSendName;
	
	@APIFields(description = "발신자번호", required=true, maxLength=20)
	private String trSendFaxNum;
	
	@APIFields(description = "동보건수", required=false, maxLength=1)
	private Integer trMsgCount;
	
	@APIFields(description = "발송파일정보", required=true, maxLength=255)
	private String trDocName;
	
	/** 발송상태값(0:발송대기,1:발송중,2:발송완료) */
	@APIFields(description = "발송상태값", required=false, maxLength=1)
	private String trSendStat;
	
	@APIFields(description = "발송순번", required=false)
	private Integer trSerialNo;
	
	@APIFields(description = "수신자이름", required=false, maxLength=50)
	private String trName;
	
	@APIFields(description = "수신팩스번호", required=false, maxLength=20)
	private String trPhone;

	public BigDecimal getTrBatchId() {
		return trBatchId;
	}

	public void setTrBatchId(BigDecimal trBatchId) {
		this.trBatchId = trBatchId;
	}

	public Date getTrSendDate() {
		return trSendDate;
	}

	public void setTrSendDate(Date trSendDate) {
		this.trSendDate = trSendDate;
	}

	public String getTrTitle() {
		return trTitle;
	}

	public void setTrTitle(String trTitle) {
		this.trTitle = trTitle;
	}

	public String getTrSendName() {
		return trSendName;
	}

	public void setTrSendName(String trSendName) {
		this.trSendName = trSendName;
	}

	public String getTrSendFaxNum() {
		return trSendFaxNum;
	}

	public void setTrSendFaxNum(String trSendFaxNum) {
		this.trSendFaxNum = trSendFaxNum;
	}

	public Integer getTrMsgCount() {
		return trMsgCount;
	}

	public void setTrMsgCount(Integer trMsgCount) {
		this.trMsgCount = trMsgCount;
	}

	public String getTrDocName() {
		return trDocName;
	}

	public void setTrDocName(String trDocName) {
		this.trDocName = trDocName;
	}

	public String getTrSendStat() {
		return trSendStat;
	}

	public void setTrSendStat(String trSendStat) {
		this.trSendStat = trSendStat;
	}

	public Integer getTrSerialNo() {
		return trSerialNo;
	}

	public void setTrSerialNo(Integer trSerialNo) {
		this.trSerialNo = trSerialNo;
	}

	public String getTrName() {
		return trName;
	}

	public void setTrName(String trName) {
		this.trName = trName;
	}

	public String getTrPhone() {
		return trPhone;
	}

	public void setTrPhone(String trPhone) {
		this.trPhone = trPhone;
	}
	
	
}