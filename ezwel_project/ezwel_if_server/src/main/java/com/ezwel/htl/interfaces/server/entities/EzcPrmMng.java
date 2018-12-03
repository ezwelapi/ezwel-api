package com.ezwel.htl.interfaces.server.entities;

//import lombok.Data;
//import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;
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
@Alias("ezcPrmMng")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="프로모션 관리", description="프로모션 관리 ( EZC_PRM_MNG )", modelTypes="TABLE")
public class EzcPrmMng extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "프로모션 코드", maxLength=10, required=true, constraints="EZC_PRM_MNG_PK(P),SYS_C0011560(C) EZC_PRM_MNG_PK(UNIQUE)")
	private String prmCd;

	@APIFields(description = "프로모션 유형", maxLength=8, required=true, constraints="SYS_C0011561(C)")
	private String prmType;

	@APIFields(description = "프로모션 구성 구분", maxLength=8, required=true, constraints="SYS_C0011562(C)")
	private String prmConfigDiv;

	@APIFields(description = "제목", maxLength=100, required=true, constraints="SYS_C0011563(C)")
	private String title;

	@APIFields(description = "프로모션 시작 일자", maxLength=8, required=true, constraints="SYS_C0011564(C)")
	private String prmStartDd;

	@APIFields(description = "프로모션 종료 일자", maxLength=8, required=true, constraints="SYS_C0011565(C)")
	private String prmEndDd;

	@APIFields(description = "프로모션 시작 시간", maxLength=4)
	private String prmStartTm;

	@APIFields(description = "프로모션 종료 시간", maxLength=4)
	private String prmEndTm;

	@APIFields(description = "프로모션 사용 여부", maxLength=1, required=true, constraints="SYS_C0011566(C)")
	private String prmUseYn;

	@APIFields(description = "디바이스 유형", maxLength=8)
	private String deviceType;

	@APIFields(description = "가족회원 접근 여부", maxLength=1, required=true, constraints="SYS_C0011567(C)")
	private String famuserAccessYn;

	@APIFields(description = "목록 이미지", maxLength=200)
	private String listImg;

	@APIFields(description = "상세 HTML", maxLength=4000)
	private String detailHtml;

	@APIFields(description = "유의사항", maxLength=2000)
	private String notice;

	@APIFields(description = "좋아요 사용 여부", maxLength=1, required=true, constraints="SYS_C0011568(C)")
	private String goodUseYn;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011569(C)")
	private String regId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011570(C)")
	private String regDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss")
	private String modiDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());


	
	public String getPrmCd() {
		return prmCd;
	}

	public void setPrmCd(String prmCd) {
		this.prmCd = prmCd;
	}

	public String getPrmType() {
		return prmType;
	}

	public void setPrmType(String prmType) {
		this.prmType = prmType;
	}

	public String getPrmConfigDiv() {
		return prmConfigDiv;
	}

	public void setPrmConfigDiv(String prmConfigDiv) {
		this.prmConfigDiv = prmConfigDiv;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrmStartDd() {
		return prmStartDd;
	}

	public void setPrmStartDd(String prmStartDd) {
		this.prmStartDd = prmStartDd;
	}

	public String getPrmEndDd() {
		return prmEndDd;
	}

	public void setPrmEndDd(String prmEndDd) {
		this.prmEndDd = prmEndDd;
	}

	public String getPrmStartTm() {
		return prmStartTm;
	}

	public void setPrmStartTm(String prmStartTm) {
		this.prmStartTm = prmStartTm;
	}

	public String getPrmEndTm() {
		return prmEndTm;
	}

	public void setPrmEndTm(String prmEndTm) {
		this.prmEndTm = prmEndTm;
	}

	public String getPrmUseYn() {
		return prmUseYn;
	}

	public void setPrmUseYn(String prmUseYn) {
		this.prmUseYn = prmUseYn;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getFamuserAccessYn() {
		return famuserAccessYn;
	}

	public void setFamuserAccessYn(String famuserAccessYn) {
		this.famuserAccessYn = famuserAccessYn;
	}

	public String getListImg() {
		return listImg;
	}

	public void setListImg(String listImg) {
		this.listImg = listImg;
	}

	public String getDetailHtml() {
		return detailHtml;
	}

	public void setDetailHtml(String detailHtml) {
		this.detailHtml = detailHtml;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getGoodUseYn() {
		return goodUseYn;
	}

	public void setGoodUseYn(String goodUseYn) {
		this.goodUseYn = goodUseYn;
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
