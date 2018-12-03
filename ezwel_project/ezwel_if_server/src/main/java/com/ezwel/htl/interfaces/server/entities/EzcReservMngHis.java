package com.ezwel.htl.interfaces.server.entities;

//import lombok.Data;
//import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;
import com.ezwel.htl.interfaces.commons.thread.Local;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.server.commons.abstracts.AbstractEntity;
import java.math.BigDecimal;


/**
 * <b>History : Generated Code Skeleton iCodeManager Made by KSW</b>
 * <pre>
 * 버전              성명                                일자                                       변경내용
 * -------    ----------------     -------------------		-----------------
 * 0.0.1      iCodeManager         2018-11-23 18:58:46      신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:58:46
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
//@Data
@Alias("ezcReservMngHis")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="예약 관리 이력", description="예약 관리 이력 ( EZC_RESERV_MNG_HIS )", modelTypes="TABLE")
public class EzcReservMngHis extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "관리이력 일련번호", maxLength=10, required=true, constraints="EZC_RESERV_MNG_HIS_PK(P),SYS_C0011625(C) EZC_RESERV_MNG_HIS_PK(UNIQUE)")
	private BigDecimal mnghisSeq;

	@APIFields(description = "예약 번호", maxLength=10, required=true, constraints="SYS_C0011626(C) EZC_RESERV_MNG_HIS_IF01(NONUNIQUE)")
	private BigDecimal reservNum;

	@APIFields(description = "예약 관리 이력 구분", maxLength=8, required=true, constraints="SYS_C0011627(C)")
	private String reservMngHisDiv;

	@APIFields(description = "내용", maxLength=4000)
	private String content;

	@APIFields(description = "발송 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011628(C)")
	private String sendDt;

	@APIFields(description = "수신 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011629(C)")
	private String recvDt;

	@APIFields(description = "자동 발송 여부", maxLength=1, required=true, constraints="SYS_C0011630(C)")
	private String autoSendYn;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011631(C)")
	private String regId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011632(C)")
	private String regDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss")
	private String modiDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());


	
	public BigDecimal getMnghisSeq() {
		return mnghisSeq;
	}

	public void setMnghisSeq(BigDecimal mnghisSeq) {
		this.mnghisSeq = mnghisSeq;
	}

	public BigDecimal getReservNum() {
		return reservNum;
	}

	public void setReservNum(BigDecimal reservNum) {
		this.reservNum = reservNum;
	}

	public String getReservMngHisDiv() {
		return reservMngHisDiv;
	}

	public void setReservMngHisDiv(String reservMngHisDiv) {
		this.reservMngHisDiv = reservMngHisDiv;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSendDt() {
		return sendDt;
	}

	public void setSendDt(String sendDt) {
		this.sendDt = sendDt;
	}

	public String getRecvDt() {
		return recvDt;
	}

	public void setRecvDt(String recvDt) {
		this.recvDt = recvDt;
	}

	public String getAutoSendYn() {
		return autoSendYn;
	}

	public void setAutoSendYn(String autoSendYn) {
		this.autoSendYn = autoSendYn;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getModiId() {
		return modiId;
	}

	public void setModiId(String modiId) {
		this.modiId = modiId;
	}

	public String getModiDt() {
		return modiDt;
	}

	public void setModiDt(String modiDt) {
		this.modiDt = modiDt;
	}


}	
