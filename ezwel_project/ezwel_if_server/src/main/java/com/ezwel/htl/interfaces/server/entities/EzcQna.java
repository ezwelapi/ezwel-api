package com.ezwel.htl.interfaces.server.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.abstracts.AbstractEntity;
import java.math.BigDecimal;


/**
 * <b>History : Generated Code Skeleton Made by KSW</b>
 * <pre>
 * 버전              성명                                일자                                       변경내용
 * -------    ----------------     -------------------		-----------------
 * 0.0.1      CodeSkeleton         2018-11-23 18:55:43                신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:55:43
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
@Data
@Alias("ezcQna")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="시설 문의사항", description="시설 문의사항 ( EZC_QNA )", modelTypes="TABLE")
public class EzcQna extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "문의사항 일련번호", maxLength=10, required=true, constraints="EZC_QNA_PK(P),SYS_C0011592(C) EZC_QNA_PK(UNIQUE)")
	private BigDecimal qnaSeq;

	@APIFields(description = "문의사항 그룹", maxLength=10, required=true, constraints="SYS_C0011593(C)")
	private BigDecimal qnaGrp;

	@APIFields(description = "문의사항 그룹 순서", maxLength=4)
	private BigDecimal qnaGrpOrd;

	@APIFields(description = "깊이", maxLength=4)
	private BigDecimal depth;

	@APIFields(description = "제목", maxLength=100)
	private String title;

	@APIFields(description = "내용", maxLength=4000)
	private String content;

	@APIFields(description = "비밀 여부", maxLength=1, required=true, constraints="SYS_C0011594(C)")
	private String passYn;

	@APIFields(description = "문의사항 상태", maxLength=8, required=true, constraints="SYS_C0011595(C)")
	private String qnaStatus;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011596(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011597(C)")
	private String regDt;

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId;

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss")
	private String modiDt;

	@APIFields(description = "시설 코드", maxLength=10, constraints="EZC_QNA_IF01(NONUNIQUE)")
	private BigDecimal faclCd;


	
	public BigDecimal getQnaSeq() {
		return qnaSeq;
	}

	public void setQnaSeq(BigDecimal qnaSeq) {
		this.qnaSeq = qnaSeq;
	}

	public BigDecimal getQnaGrp() {
		return qnaGrp;
	}

	public void setQnaGrp(BigDecimal qnaGrp) {
		this.qnaGrp = qnaGrp;
	}

	public BigDecimal getQnaGrpOrd() {
		return qnaGrpOrd;
	}

	public void setQnaGrpOrd(BigDecimal qnaGrpOrd) {
		this.qnaGrpOrd = qnaGrpOrd;
	}

	public BigDecimal getDepth() {
		return depth;
	}

	public void setDepth(BigDecimal depth) {
		this.depth = depth;
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

	public String getPassYn() {
		return passYn;
	}

	public void setPassYn(String passYn) {
		this.passYn = passYn;
	}

	public String getQnaStatus() {
		return qnaStatus;
	}

	public void setQnaStatus(String qnaStatus) {
		this.qnaStatus = qnaStatus;
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

	public BigDecimal getFaclCd() {
		return faclCd;
	}

	public void setFaclCd(BigDecimal faclCd) {
		this.faclCd = faclCd;
	}


}	
