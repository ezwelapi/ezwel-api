package com.ezwel.htl.interfaces.server.commons.utils.data;

import com.ezwel.htl.interfaces.commons.abstracts.AbstractSDO;
import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;

@APIModel
public class MessageSDO extends AbstractSDO {

	private static final long serialVersionUID = 1L;

	@APIFields(description = "Exception 객체", required=true, maxLength=4)
	private Exception exception;

	@APIFields(description = "원본 메시지", maxLength=100)
	private Object[] message;
	
	@APIFields(description = "메시지 Arguments", maxLength=100)
	private Object[] arguments;
	
	@APIFields(description = "파일 명", maxLength=100)
	private String fileName;

	

	
}
