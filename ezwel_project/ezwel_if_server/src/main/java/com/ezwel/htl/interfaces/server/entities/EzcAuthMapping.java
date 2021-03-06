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
 * 0.0.1      iCodeManager         2018-11-23 18:58:44      신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:58:44
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
//@Data
@Alias("ezcAuthMapping")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="권한 맵핑", description="권한 맵핑 ( EZC_AUTH_MAPPING )", modelTypes="TABLE")
public class EzcAuthMapping extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "권한 그룹 코드", maxLength=10, required=true, constraints="EZC_AUTH_MAPPING_PK(P),FK_EZC_AUTH_GRP_EZC_AUTH_MAPPI(R),SYS_C0011220(C) EZC_AUTH_MAPPING_PK(UNIQUE),EZC_AUTH_MAPPING_IF01(NONUNIQUE)")
	private String authGrpCd;

	@APIFields(description = "운영자 ID", maxLength=20, required=true, constraints="EZC_AUTH_MAPPING_PK(P),FK_EZC_MGR_EZC_AUTH_MAPPING(R),SYS_C0011221(C) EZC_AUTH_MAPPING_IF02(NONUNIQUE),EZC_AUTH_MAPPING_PK(UNIQUE)")
	private String mgrId;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011222(C)")
	private String regId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyyMMddHHmmss", required=true, constraints="SYS_C0011223(C)")
	private String regDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());


	
	public String getAuthGrpCd() {
		return authGrpCd;
	}

	public void setAuthGrpCd(String authGrpCd) {
		this.authGrpCd = authGrpCd;
	}

	public String getMgrId() {
		return mgrId;
	}

	public void setMgrId(String mgrId) {
		this.mgrId = mgrId;
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
