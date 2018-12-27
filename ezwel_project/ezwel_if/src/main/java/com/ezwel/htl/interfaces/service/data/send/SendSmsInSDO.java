package com.ezwel.htl.interfaces.service.data.send;


import java.util.Date;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author ypjeon@ebsolution.co.kr
 * @date 2018. 12. 13.
 */

@APIModel
@Data
@EqualsAndHashCode(callSuper=true)
public class SendSmsInSDO extends AbstractSDO {
	
	@APIFields(description = "문자발송 Input 수신번호", required=true, maxLength=12)
	private String callTo;
	
	@APIFields(description = "문자발송 Input 발신번호", required=true, maxLength=12)
	private String callFrom;
	
	@APIFields(description = "문자발송 Input 발송타입", maxLength=3)
	private String msgType;
	
	@APIFields(description = "문자발송 Input 메세지 제목", maxLength=200)
	private String mmsSubject;
	
	@APIFields(description = "문자발송 Input 메세지 내용", required=true, maxLength=200)
	private String smsText;
	
	@APIFields(description = "문자발송 Input 서비스 구분코드", required=true, maxLength=4)
	private String svcType;
	
	@APIFields(description = "문자발송 Input 발송 예약일시")
	private String reqDate;
	
	@APIFields(description = "문자발송 Input 출력형식", maxLength=10)
	private String __ezwel_framework_view_type__;
	
	@APIFields(description = "문자발송 Input 첨부이미지 URL 또는 서버 파일경로", maxLength=200)
	private String attachedUrl;
	
	@APIFields(description = "문자발송 Input 사용자식별번호(복지관유저키)")
	private Integer userData;
	
	@APIFields(description = "문자발송 Input 카카오 알림톡 사용여부")
	private String smsUseYn;
	
	@APIFields(description = "문자발송 Input 카카오 알림톡 템플릿 번호")
	private String templateCode;

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

	public String getSmsText() {
		return smsText;
	}

	public void setSmsText(String smsText) {
		this.smsText = smsText;
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

}
