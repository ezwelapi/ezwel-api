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
 * 0.0.1      CodeSkeleton         2018-11-23 18:46:12                신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:46:12
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
@Data
@Alias("ezcPrmComment")
@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="프로모션 댓글", description="프로모션 댓글 ( EZC_PRM_COMMENT )", modelTypes="TABLE")
public class EzcPrmComment extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "프로모션 댓글 일련번호", maxLength=10, required=true, constraints="EZC_PRM_COMMENT_PK(P),SYS_C0011545(C) EZC_PRM_COMMENT_PK(UNIQUE)")
	private BigDecimal prmCommentSeq;

	@APIFields(description = "프로모션 코드", maxLength=10, required=true, constraints="FK_EZC_PRM_MNG_EZC_PRM_COMMENT(R),SYS_C0011546(C) EZC_PRM_COMMENT_IF01(NONUNIQUE)")
	private String prmCd;

	@APIFields(description = "내용", maxLength=1000)
	private String content;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011547(C)")
	private String regId;

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, required=true, constraints="SYS_C0011548(C)")
	private String regDt;


	
	public BigDecimal getPrmCommentSeq() {
		return prmCommentSeq;
	}

	public void setPrmCommentSeq(BigDecimal prmCommentSeq) {
		this.prmCommentSeq = prmCommentSeq;
	}

	public String getPrmCd() {
		return prmCd;
	}

	public void setPrmCd(String prmCd) {
		this.prmCd = prmCd;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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


}	