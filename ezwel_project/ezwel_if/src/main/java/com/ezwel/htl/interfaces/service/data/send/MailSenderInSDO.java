package com.ezwel.htl.interfaces.service.data.send;


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

@APIModel(description="메일발송 정보")
public class MailSenderInSDO extends AbstractSDO {
	
	@APIFields(description = "발송자이메일주소", required=false, maxLength=50)
	private String from;
	
	@APIFields(description = "발송자명", required=false, maxLength=50)
	private String fromName;
	
	@APIFields(description = "수신이메일주소", required=true, maxLength=50)
	private String recipient;
	
	@APIFields(description = "메일제목", required=true)
	private String subject;
	
	@APIFields(description = "메일내용", required=true)
	private String body;

	@APIFields(description = "메일발송 성공여부", required=false)
	private boolean success;
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
