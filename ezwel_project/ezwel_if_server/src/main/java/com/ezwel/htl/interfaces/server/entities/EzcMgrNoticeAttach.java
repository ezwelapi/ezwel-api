package com.ezwel.htl.interfaces.server.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;
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
@Data
@Alias("ezcMgrNoticeAttach")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="운영자 공지사항 첨부파일", description="운영자 공지사항 첨부파일 ( EZC_MGR_NOTICE_ATTACH )", modelTypes="TABLE")
public class EzcMgrNoticeAttach extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "공지사항 첨부파일 일련번호", maxLength=10, required=true, constraints="EZC_MGR_NOTICE_ATTACH_PK(P),SYS_C0011499(C) EZC_MGR_NOTICE_ATTACH_PK(UNIQUE)")
	private BigDecimal noticeAttachSeq;

	@APIFields(description = "공지사항 일련번호", maxLength=10, required=true, constraints="FK_EZC_MGR_NOTICE_EZC_MGR_NOTI(R),SYS_C0011500(C) EZC_MGR_NOTICE_ATTACH_IF01(NONUNIQUE)")
	private BigDecimal noticeSeq;

	@APIFields(description = "파일 경로", maxLength=100, required=true, constraints="SYS_C0011501(C)")
	private String filePath;

	@APIFields(description = "시스템 파일명", maxLength=100, required=true, constraints="SYS_C0011502(C)")
	private String sysFilenm;

	@APIFields(description = "파일명", maxLength=100, required=true, constraints="SYS_C0011503(C)")
	private String filenm;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011504(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011505(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss")
	private String modiDt;


	
	public BigDecimal getNoticeAttachSeq() {
		return noticeAttachSeq;
	}

	public void setNoticeAttachSeq(BigDecimal noticeAttachSeq) {
		this.noticeAttachSeq = noticeAttachSeq;
	}

	public BigDecimal getNoticeSeq() {
		return noticeSeq;
	}

	public void setNoticeSeq(BigDecimal noticeSeq) {
		this.noticeSeq = noticeSeq;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getSysFilenm() {
		return sysFilenm;
	}

	public void setSysFilenm(String sysFilenm) {
		this.sysFilenm = sysFilenm;
	}

	public String getFilenm() {
		return filenm;
	}

	public void setFilenm(String filenm) {
		this.filenm = filenm;
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
