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
 * 0.0.1      iCodeManager         2018-11-23 18:58:45      신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:58:45
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
//@Data
@Alias("ezcDisp")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="전시", description="전시 ( EZC_DISP )", modelTypes="TABLE")
public class EzcDisp extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "전시 코드", maxLength=10, required=true, constraints="EZC_DISP_PK(P),SYS_C0011388(C) EZC_DISP_PK(UNIQUE)")
	private BigDecimal dispCd;

	@APIFields(description = "전시 명", maxLength=100, required=true, constraints="SYS_C0011389(C)")
	private String dispNm;

	@APIFields(description = "전시 설명", maxLength=4000)
	private String dispDesc;

	@APIFields(description = "이미지 경로", maxLength=100)
	private String imgPath;

	@APIFields(description = "이미지 시스템 파일명", maxLength=100)
	private String imgSysFilenm;

	@APIFields(description = "이미지 파일명", maxLength=100)
	private String imgFilenm;

	@APIFields(description = "URL", maxLength=200)
	private String url;

	@APIFields(description = "이미지 HTML", maxLength=4000)
	private String imgHtml;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011390(C)")
	private String regId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011391(C)")
	private String regDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss")
	private String modiDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());


	
	public BigDecimal getDispCd() {
		return dispCd;
	}

	public void setDispCd(BigDecimal dispCd) {
		this.dispCd = dispCd;
	}

	public String getDispNm() {
		return dispNm;
	}

	public void setDispNm(String dispNm) {
		this.dispNm = dispNm;
	}

	public String getDispDesc() {
		return dispDesc;
	}

	public void setDispDesc(String dispDesc) {
		this.dispDesc = dispDesc;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getImgSysFilenm() {
		return imgSysFilenm;
	}

	public void setImgSysFilenm(String imgSysFilenm) {
		this.imgSysFilenm = imgSysFilenm;
	}

	public String getImgFilenm() {
		return imgFilenm;
	}

	public void setImgFilenm(String imgFilenm) {
		this.imgFilenm = imgFilenm;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImgHtml() {
		return imgHtml;
	}

	public void setImgHtml(String imgHtml) {
		this.imgHtml = imgHtml;
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
