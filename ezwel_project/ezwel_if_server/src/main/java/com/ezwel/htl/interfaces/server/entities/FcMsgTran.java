package com.ezwel.htl.interfaces.server.entities;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractEntity;


/**
 * <b>History : Generated Code Skeleton iCodeManager Made by KSW</b>
 * <pre>
 * 버전              성명                                일자                                       변경내용
 * -------    ----------------     -------------------		-----------------
 * 0.0.1      iCodeManager         2018-11-23 18:58:45      신규자동생성 
 * </pre>
 * 
 * @author ypjeon@ebsolution.co.kr
 * @since 2018-11-23 18:58:45
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
//@Data
@Alias("fcMsgTran")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="팩스발송 정보", description="팩스발송 정보 ( FC_MSG_TRAN )", modelTypes="TABLE")
public class FcMsgTran extends AbstractEntity {

	@APIFields(description = "고유번호(발송고유번호)")
	private BigDecimal trBatchId;
	
	@APIFields(description = "발송순번", required=true)
	private Integer trSerialNo;
	
	@APIFields(description = "발송일시")
	private Date trSendDate;	
	
	@APIFields(description = "수신자이름", required=true, maxLength=50)
	private String trName;
	
	@APIFields(description = "수신팩스번호", required=true, maxLength=20)
	private String trPhone;
	
	/** 발송상태값(0:발송대기,1:발송중,2:발송완료) */
	@APIFields(description = "발송상태값", required=true, maxLength=1)
	private String trSendStat;

	public BigDecimal getTrBatchId() {
		return trBatchId;
	}

	public void setTrBatchId(BigDecimal trBatchId) {
		this.trBatchId = trBatchId;
	}

	public Integer getTrSerialNo() {
		return trSerialNo;
	}

	public void setTrSerialNo(Integer trSerialNo) {
		this.trSerialNo = trSerialNo;
	}

	public Date getTrSendDate() {
		return trSendDate;
	}

	public void setTrSendDate(Date trSendDate) {
		this.trSendDate = trSendDate;
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

	public String getTrSendStat() {
		return trSendStat;
	}

	public void setTrSendStat(String trSendStat) {
		this.trSendStat = trSendStat;
	}
	
}	