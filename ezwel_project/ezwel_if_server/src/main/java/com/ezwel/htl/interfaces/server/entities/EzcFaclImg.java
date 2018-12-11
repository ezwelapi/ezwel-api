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
 * 0.0.1      iCodeManager         2018-11-23 18:58:45      신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:58:45
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
//@Data
@Alias("ezcFaclImg")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="시설 이미지", description="시설 이미지 ( EZC_FACL_IMG )", modelTypes="TABLE")
public class EzcFaclImg extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "시설 이미지 일련번호", maxLength=10, required=true, constraints="EZC_FACL_IMG_PK(P),SYS_C0011106(C) EZC_FACL_IMG_PK(UNIQUE)")
	private BigDecimal faclImgSeq;

	@APIFields(description = "시설 코드", maxLength=10, required=true, constraints="FK_EZC_FACL_EZC_FACL_IMG(R),SYS_C0011107(C) EZC_FACL_IMG_IF01(NONUNIQUE)")
	private BigDecimal faclCd;

	@APIFields(description = "시설 이미지 유형", maxLength=8, required=true, constraints="SYS_C0011108(C)")
	private String faclImgType;

	@APIFields(description = "대표 이미지 여부", maxLength=1, required=true, constraints="SYS_C0011109(C)")
	private String mainImgYn;

	@APIFields(description = "이미지 경로", maxLength=200, required=false, constraints="")
	private String imgUrl;

	@APIFields(description = "이미지 URL", maxLength=500, required=true, constraints="SYS_C0011110(C)")
	private String partnerImgUrl;
	
	@APIFields(description = "이미지 설명", maxLength=1000)
	private String imgDesc;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011111(C)")
	private String regId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyyMMddHHmmss", required=true, constraints="SYS_C0011112(C)")
	private String regDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyyMMddHHmmss")
	private String modiDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());


	
	public BigDecimal getFaclImgSeq() {
		return faclImgSeq;
	}

	public void setFaclImgSeq(BigDecimal faclImgSeq) {
		this.faclImgSeq = faclImgSeq;
	}

	public BigDecimal getFaclCd() {
		return faclCd;
	}

	public void setFaclCd(BigDecimal faclCd) {
		this.faclCd = faclCd;
	}

	public String getFaclImgType() {
		return faclImgType;
	}

	public void setFaclImgType(String faclImgType) {
		this.faclImgType = faclImgType;
	}

	public String getMainImgYn() {
		return mainImgYn;
	}

	public void setMainImgYn(String mainImgYn) {
		this.mainImgYn = mainImgYn;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public String getPartnerImgUrl() {
		return partnerImgUrl;
	}

	public void setPartnerImgUrl(String partnerImgUrl) {
		this.partnerImgUrl = partnerImgUrl;
	}

	public String getImgDesc() {
		return imgDesc;
	}

	public void setImgDesc(String imgDesc) {
		this.imgDesc = imgDesc;
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
