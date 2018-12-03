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
 * 0.0.1      iCodeManager         2018-11-23 18:58:44      신규자동생성 
 * </pre>
 * 
 * @author swkim@ebsolution.co.kr
 * @since 2018-11-23 18:58:44
 * @version 0.0.1
 * @see "EZWEL Entity"
 */
//@Data
@Alias("ezcAuth")
//@EqualsAndHashCode(callSuper=true)
@APIModel(modelNames="권한 정보", description="권한 정보 ( EZC_AUTH )", modelTypes="TABLE")
public class EzcAuth extends AbstractEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@APIFields(description = "메뉴 ID", maxLength=6, required=true, constraints="EZC_AUTH_PK(P),FK_EZC_MENU_EZC_AUTH(R),SYS_C0011203(C) EZC_AUTH_PK(UNIQUE),EZC_AUTH_IF01(NONUNIQUE)")
	private String menuId;

	@APIFields(description = "권한 그룹 코드", maxLength=10, required=true, constraints="EZC_AUTH_PK(P),FK_EZC_AUTH_GRP_EZC_AUTH(R),SYS_C0011204(C) EZC_AUTH_IF02(NONUNIQUE),EZC_AUTH_PK(UNIQUE)")
	private String authGrpCd;

	@APIFields(description = "권한 C", maxLength=1, required=true, constraints="SYS_C0011205(C)")
	private String authC;

	@APIFields(description = "권한 R", maxLength=1, required=true, constraints="SYS_C0011206(C)")
	private String authR;

	@APIFields(description = "권한 U", maxLength=1, required=true, constraints="SYS_C0011207(C)")
	private String authU;

	@APIFields(description = "권한 D", maxLength=1, required=true, constraints="SYS_C0011208(C)")
	private String authD;

	@APIFields(description = "권한 X", maxLength=1, required=true, constraints="SYS_C0011209(C)")
	private String authX;

	@APIFields(description = "권한 P", maxLength=1, required=true, constraints="SYS_C0011210(C)")
	private String authP;

	@APIFields(description = "등록자 ID", maxLength=20, required=true, constraints="SYS_C0011211(C)")
	private String regId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "등록 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss", required=true, constraints="SYS_C0011212(C)")
	private String regDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());

	@APIFields(description = "수정자 ID", maxLength=20)
	private String modiId = Local.commonHeader().getSystemUserId();

	@APIFields(description = "수정 일시", maxLength=14, isDate=true, dateFormat="yyyy-MM-dd HH24:mi:ss")
	private String modiDt = APIUtil.getTimeMillisToDate(Local.commonHeader().getStartTimeMillis());


	
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getAuthGrpCd() {
		return authGrpCd;
	}

	public void setAuthGrpCd(String authGrpCd) {
		this.authGrpCd = authGrpCd;
	}

	public String getAuthC() {
		return authC;
	}

	public void setAuthC(String authC) {
		this.authC = authC;
	}

	public String getAuthR() {
		return authR;
	}

	public void setAuthR(String authR) {
		this.authR = authR;
	}

	public String getAuthU() {
		return authU;
	}

	public void setAuthU(String authU) {
		this.authU = authU;
	}

	public String getAuthD() {
		return authD;
	}

	public void setAuthD(String authD) {
		this.authD = authD;
	}

	public String getAuthX() {
		return authX;
	}

	public void setAuthX(String authX) {
		this.authX = authX;
	}

	public String getAuthP() {
		return authP;
	}

	public void setAuthP(String authP) {
		this.authP = authP;
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
