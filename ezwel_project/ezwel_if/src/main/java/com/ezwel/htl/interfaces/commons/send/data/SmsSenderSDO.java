package com.ezwel.htl.interfaces.commons.send.data;


import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author ypjeon@ebsolution.co.kr
 * @date 2018. 12. 13.
 */

@APIModel
public class SmsSenderSDO extends AbstractSDO {
	
	@APIFields(description = "문자발송 수신번호", required=true, maxLength=12)
	private String callTo;
	
	@APIFields(description = "문자발송 발신번호", required=true, maxLength=12)
	private String callFrom;
	
	@APIFields(description = "문자발송 발송타입", maxLength=3)
	private String msgType;
	
	@APIFields(description = "문자발송 메세지 제목", maxLength=200)
	private String mmsSubject;
	
	@APIFields(description = "문자발송 메세지 내용", required=true, maxLength=200)
	private String smsTxt;
	
	@APIFields(description = "문자발송 서비스 구분코드", required=true, maxLength=4)
	private String svcType;
	
	@APIFields(description = "문자발송 발송 예약일시")
	private String reqDate;
	
	@APIFields(description = "문자발송 출력형식", maxLength=10)
	private String __ezwel_framework_view_type__;
	
	@APIFields(description = "문자발송 첨부이미지 URL 또는 서버 파일경로", maxLength=200)
	private String attachedUrl;
	
	@APIFields(description = "문자발송 사용자식별번호(복지관유저키)")
	private Integer userData;
	
	@APIFields(description = "문자발송 카카오 알림톡 사용여부")
	private String smsUseYn;
	
	@APIFields(description = "문자발송 카카오 알림톡 템플릿 번호")
	private String templateCode;
	
	@APIFields(description = "errorCode")
	private String errorCode;

	@APIFields(description = "errorMessage")
	private String errorMessage;
	
	@APIFields(description = "data")
	private String data;
	
	@APIFields(description = "send result", required=false)
	private boolean result;
	
	public String getCallTo() {
		return callTo;
	}

	public void setCallTo(String callTo) {
		this.callTo = callTo;
	}

	public String getCallFrom() {
		return callFrom;
	}

	public void setCallFrom(String callFrom) {
		this.callFrom = callFrom;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getMmsSubject() {
		return mmsSubject;
	}

	public void setMmsSubject(String mmsSubject) {
		this.mmsSubject = mmsSubject;
	}

	public String getSmsTxt() {
		return smsTxt;
	}

	public void setSmsText(String smsTxt) {
		this.smsTxt = smsTxt;
	}

	public String getSvcType() {
		return svcType;
	}

	public void setSvcType(String svcType) {
		this.svcType = svcType;
	}

	public String getReqDate() {
		return reqDate;
	}

	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}

	public String get__ezwel_framework_view_type__() {
		return __ezwel_framework_view_type__;
	}

	public void set__ezwel_framework_view_type__(String __ezwel_framework_view_type__) {
		this.__ezwel_framework_view_type__ = __ezwel_framework_view_type__;
	}

	public String getAttachedUrl() {
		return attachedUrl;
	}

	public void setAttachedUrl(String attachedUrl) {
		this.attachedUrl = attachedUrl;
	}

	public Integer getUserData() {
		return userData;
	}

	public void setUserData(Integer userData) {
		this.userData = userData;
	}

	public String getSmsUseYn() {
		return smsUseYn;
	}

	public void setSmsUseYn(String smsUseYn) {
		this.smsUseYn = smsUseYn;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
	
}
