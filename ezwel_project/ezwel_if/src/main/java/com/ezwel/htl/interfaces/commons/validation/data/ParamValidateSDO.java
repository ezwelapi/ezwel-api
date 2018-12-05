package com.ezwel.htl.interfaces.commons.validation.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;

@APIModel(description = "유효성검증정보")
public class ParamValidateSDO {

	@APIFields(required = false, description = "DTO")
	private Object model;
	
	@APIFields(required = false, description = "필드명")
	private String fieldName;

	@APIFields(required = false, description = "필드값")
	private Object fieldValue;
	
	@APIFields(required = false, description = "메세지")
	private String message;
	
	@APIFields(required = false, description = "정규식패턴")
	private String[] patterns;
	
	@APIFields(required = false, description = "필수여부")
	private boolean required;

	@APIFields(required = false, description = "유효성검사")
	private boolean validation;	
	
	@APIFields(required = false, description = "유효성검사유형")
	private int validateType;	

	@APIFields(required = false, description = "유효성검사제외필드목록")
	private List<String> excludeFieldList;
	
	public ParamValidateSDO() {
		this.reset();
	}

	/**
	 * DTO 오브젝트에 있는 필드의 @Field 어노테이션을 기준으로 모든 필드 벨리데이션 설정
	 * @param model
	 */
	public ParamValidateSDO(Object model) {
		this.validateType = OperateConstants.VALIDATE_DTO_FULL_FIELD_ANNO;
		initialize(model, null, null, null, null);
	}
	
	/**
	 * DTO 오브젝트에 있는 필드의 @Field 어노테이션을 기준으로 모든 필드 벨리데이션 설정
	 * @param model
	 */
	public ParamValidateSDO(Object model, String[] excludeField) {
		this.validateType = OperateConstants.VALIDATE_DTO_FULL_FIELD_ANNO;
		
		if(excludeField != null && excludeField.length > 0) {
			setExcludeFieldList(Arrays.asList(excludeField));
		}
		
		initialize(model, null, null, null, null);
	}
	
	/**
	 * DTO 오브젝트에 있는 fieldName의 @Field 어노테이션을 기준으로 단일 필드 벨리데이션 설정
	 * @param model
	 */
	public ParamValidateSDO(Object model, String fieldName) {
		this.validateType = OperateConstants.VALIDATE_DTO_SINGLE_FIELD_ANNO;
		initialize(model, fieldName, null, null, null);
	}
	
	/**
	 * fieldValue를 petterns 옵션으로 벨리데이션 설정
	 * @param fieldValue
	 * @param patterns
	 * @param message
	 */
	public ParamValidateSDO(Object fieldValue, String[] patterns, String message) {
		this.validateType = OperateConstants.VALIDATE_SINGLE_VALUE_PATTERN;
		initialize(null, null, fieldValue, patterns, message);
	}

	/**
	 * DTO 오브젝트의 fieldName의 fieldValue값을 petterns 옵션으로 벨리데이션 설정
	 * @param model
	 * @param fieldName
	 * @param fieldValue
	 * @param patterns
	 * @param message
	 */
	public ParamValidateSDO(Object model, String fieldName, Object fieldValue, String[] patterns, String message) {
		this.validateType = OperateConstants.VALIDATE_DTO_SINGLE_FIELD_PATTERN;
		initialize(model, fieldName, fieldValue, patterns, message);
	}
	
	public List<String> getExcludeFieldList() {
		return excludeFieldList;
	}

	public void setExcludeFieldList(List<String> excludeFieldList) {
		this.excludeFieldList = excludeFieldList;
	}

	public void addExcludeFieldList(String excludeField) {
		if(this.excludeFieldList == null) {
			this.excludeFieldList = new ArrayList<String>();
		}
		this.excludeFieldList.add(excludeField);
	}
	
	/**
	 * 바인드된 값으로 벨리데이션 설정 DTO 초기화
	 * @Name : initialize 
	 * @Description : 
	 * @LastUpdated : 2015. 10. 17. 오후 11:12:01
	 * @param model
	 * @param fieldName
	 * @param fieldValue
	 * @param patterns
	 * @param message
	 */
	private void initialize(Object model, String fieldName, Object fieldValue, String[] patterns, String message){
		this.reset();
		this.model = model;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		this.patterns = patterns;
		this.message = message;
		
		if(this.patterns != null) {
			
			for(String pattern : this.patterns) {
				if( pattern instanceof String ) {
					if(patterns != null && pattern.toString().equalsIgnoreCase("required")) {
						this.required = true;
						break;
					}
				}
			}
		}
	}
	
	private void reset(){
		
		fieldName = "";
		fieldValue = null;
		message = "";
		patterns = null;
		required = false;
		validation = true;
	}
	
	
	public boolean isValidation() {
		return validation;
	}

	public void setValidation(boolean validation) {
		this.validation = validation;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Object getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String[] getPatterns() {
		return patterns;
	}

	public void setPatterns(String[] patterns) {
		this.patterns = patterns;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public Object getModel() {
		return model;
	}

	public int getValidateType() {
		return validateType;
	}

	public void setModel(Object model) {
		this.model = model;
	}

	public void setValidateType(int validateType) {
		this.validateType = validateType;
	}

}
