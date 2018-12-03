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
@Alias("ezcMgrNotice")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="운영자 공지사항", description="운영자 공지사항 ( EZC_MGR_NOTICE )", modelTypes="TABLE")
public class EzcMgrNotice extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "공지사항 일련번호", maxLength=10, required=true, constraints="EZC_MGR_NOTICE_PK(P),SYS_C0011492(C) EZC_MGR_NOTICE_PK(UNIQUE)")
	private BigDecimal noticeSeq;

	@APIFields(description = "공지 대상", maxLength=8, required=true, constraints="SYS_C0011493(C)")
	private String noticeRange;

	@APIFields(description = "제휴사 코드 유형", maxLength=8, required=true, constraints="FK_EZC_PARTNER_EZC_MGR_NOTICE(R),SYS_C0011494(C) EZC_MGR_NOTICE_IF01(NONUNIQUE)")
	private String partnerCdType;

	@APIFields(description = "제목", maxLength=100)
	private String title;

	@APIFields(description = "내용", maxLength=2000)
	private String content;

	@APIFields(description = "조회수", maxLength=4)
	private BigDecimal viewCnt;

	@APIFields(description = "작성자 ID", maxLength=20, required=true, constraints="SYS_C0011495(C)")
	private String writerId;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011496(C)")
	private String regId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011497(C)")
	private String regDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss")
	private String modiDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());


	
	public BigDecimal getNoticeSeq() {
		return noticeSeq;
	}

	public void setNoticeSeq(BigDecimal noticeSeq) {
		this.noticeSeq = noticeSeq;
	}

	public String getNoticeRange() {
		return noticeRange;
	}

	public void setNoticeRange(String noticeRange) {
		this.noticeRange = noticeRange;
	}

	public String getPartnerCdType() {
		return partnerCdType;
	}

	public void setPartnerCdType(String partnerCdType) {
		this.partnerCdType = partnerCdType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public BigDecimal getViewCnt() {
		return viewCnt;
	}

	public void setViewCnt(BigDecimal viewCnt) {
		this.viewCnt = viewCnt;
	}

	public String getWriterId() {
		return writerId;
	}

	public void setWriterId(String writerId) {
		this.writerId = writerId;
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
