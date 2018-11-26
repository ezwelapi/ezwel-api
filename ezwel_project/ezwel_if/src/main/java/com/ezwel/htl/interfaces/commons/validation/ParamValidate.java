package com.ezwel.htl.interfaces.commons.validation;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ezwel.htl.interfaces.commons.annotation.APIFields;
import com.ezwel.htl.interfaces.commons.annotation.APIModel;
import com.ezwel.htl.interfaces.commons.annotation.APIOperation;
import com.ezwel.htl.interfaces.commons.annotation.APIType;
import com.ezwel.htl.interfaces.commons.constants.MessageConstants;
import com.ezwel.htl.interfaces.commons.constants.OperateConstants;
import com.ezwel.htl.interfaces.commons.exception.APIException;
import com.ezwel.htl.interfaces.commons.utils.APIUtil;
import com.ezwel.htl.interfaces.commons.utils.PropertyUtil;
import com.ezwel.htl.interfaces.commons.utils.RegexUtil;
import com.ezwel.htl.interfaces.commons.utils.TypeUtil;
import com.ezwel.htl.interfaces.commons.validation.data.ParamValidateSDO;


@APIType
public class ParamValidate {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RegexUtil regexUtil;
	
	@Autowired
	private TypeUtil typeUtil;
	
	@Autowired
	private PropertyUtil propertyUtil;
	
	@Autowired
	private APIUtil apiUtil;
	
	private List<ParamValidateSDO> params;
	
	private final String VP_REQUIRED = "required";
	
	private final String VP_REGEX = "regex:";
	
	private final String VP_TYPE = "type:";
	
	private final String VP_SIZE = "size:";
	
	private final String VP_LENGTH = "length:";
	
	private String encoding = OperateConstants.DEFAULT_ENCODING;
	
	public ParamValidate(){
		this.reset();
		//ParamValidate를 @Autowired하지 않고 new ParamValidate() 하였을경우
		if(regexUtil == null) this.regexUtil = new RegexUtil();
		if(typeUtil == null) this.typeUtil = new TypeUtil();
		if(propertyUtil == null) this.propertyUtil = new PropertyUtil();
		if(apiUtil == null) this.apiUtil = new APIUtil();
	}
	
	@APIOperation(description="필드 밸류 리셋(초기화)")
	private void reset(){
		params = new ArrayList<ParamValidateSDO>();
	}

	@APIOperation(description="파라메터 유효성 검사 목록 DATA OBJECT를 리턴합니다.")
	public List<ParamValidateSDO> getParams() {
		return params;
	}

	@APIOperation(description="파라메터 유효성 검사 목록 DATA OBJECT를 세팅합니다.")
	public void setParams(List<ParamValidateSDO> params) {
		this.params = params;
	}

	@APIOperation(description="파라메터 유효성 검사 DATA OBJECT를 목록에 추가합니다.")
	public void addParam(ParamValidateSDO param) {
		if(this.params == null) this.params = new ArrayList<ParamValidateSDO>();
		this.params.add(param);
	}
	
	@APIOperation(description="파라메터 유효성 검사를 실행합니다.")
	public ParamValidateSDO validation(){
		return validation(null);
	}
	
	@APIOperation(description="파라메터 유효성 검사를 바인드된 인코딩을 반영하여 실행합니다.")
	public ParamValidateSDO validation(String encoding){
		
		if(this.params == null) return new ParamValidateSDO();
		
		if(encoding != null) {
			this.encoding = encoding;
		}
		
		ParamValidateSDO inValidate = execution();
		
		if(inValidate == null) inValidate = new ParamValidateSDO();
		
		return inValidate;
	}
	
	
	@APIOperation(description="파라메터 유효성 검사를 실행합니다.")
	public ParamValidateSDO execute(){
		return execute(null, false);
	}
	
	@APIOperation(description="파라메터 유효성 검사를 실행하고 유효성검사에 실패한 인증 필드 객체의 리턴 여부를 지정합니다.")
	public ParamValidateSDO execute(boolean returnInvalid){
		return execute(null, returnInvalid);
	}
	
	
	@APIOperation(description="파라메터 유효성 검사를 바인드된 인코딩을 반영하여 실행하고 유효성검사에 실패한 인증 필드 객체의 리턴 여부를 지정합니다.")
	public ParamValidateSDO execute(String encoding, boolean returnInvalid){

		if(this.params == null) return null;
		
		if(encoding != null) {
			this.encoding = encoding;
		}
		
		ParamValidateSDO inValidate = execution();
		
		if(!returnInvalid && inValidate != null) {
			throw new APIException(MessageConstants.RESPONSE_CODE_2001, inValidate.getMessage());
		}
		
		return inValidate;
	}
	
	@APIOperation(description="파라메터 유효성 검사를 설정된 정보에 의거하여 실행합니다.")
	private ParamValidateSDO execution(){
		
		ParamValidateSDO inValidate = null;
		try {
			int validateCnt = 1;
			for(ParamValidateSDO validate : this.params) {
				
		    	if(logger.isDebugEnabled()) {
		    		logger.debug(APIUtil.addString("START Validate No.", validateCnt));
		    	}

		    	validate = this.run(validate);
		    	
	    		if(!validate.isValidation()) {
	    			inValidate = validate; 
	    			break;
	    		}
	    		else {
	    			if(logger.isDebugEnabled()) {
	    	    		logger.debug(APIUtil.addString("PASS Validate No.", validateCnt, " END"));
	    	    	}
	    		}
	    		validateCnt++;
			}
			
			if(this.params != null) {
				this.params.clear();
			}
		}
		catch(Exception e){
			throw new APIException(MessageConstants.RESPONSE_CODE_2001, e);
		}
		
		return inValidate;
	}
	
	/**
	 * 바인드된 ParamValidateDTO설정값 으로 벨리데이션 실행 
	 * @Name : run 
	 * @Description : 
	 * @LastUpdated : 2015. 10. 17. 오후 11:14:41
	 * @param paramValidate
	 * @return
	 */
	@APIOperation(description="바인드된 ParamValidateSDO설정값으로 유효성검사를 실행 합니다.")
    public ParamValidateSDO run(ParamValidateSDO paramValidate){
    	
    	if( logger.isDebugEnabled() ) {
	    	logger.debug("유효성 검증 필드 : {}", paramValidate);
	    }
    	
    	/**
    	 * paramValidate.getValidateType()으로 벨리데이션 유형을 판단한다. 
    	 */
    	
    	if(paramValidate.getValidateType() == OperateConstants.VALIDATE_DTO_FULL_FIELD_ANNO) {
    		//DTO 오브젝트에 있는 필드의 @Field 어노테이션을 기준으로 모든 필드 벨리데이션 설정
    		//@APIModel 어노테이션타입에만 해당
    		//@APIFields 어노테이션필드에만 해당
    		return validateModel(paramValidate);
    	}
    	else if(paramValidate.getValidateType() == OperateConstants.VALIDATE_DTO_SINGLE_FIELD_ANNO) {
    		//DTO 오브젝트에 있는 fieldName의 @Field 어노테이션을 기준으로 단일 필드 벨리데이션 설정
    		//@APIModel 어노테이션타입에만 해당
    		//@APIFields 어노테이션필드에만 해당
    		return validateModel(paramValidate);    		
    	}
    	else if(paramValidate.getValidateType() == OperateConstants.VALIDATE_SINGLE_VALUE_PATTERN) {
    		//fieldValue를 petterns 옵션으로 벨리데이션 설정
    		return validate(paramValidate);
    	}
		else if(paramValidate.getValidateType() == OperateConstants.VALIDATE_DTO_SINGLE_FIELD_PATTERN) {
			//DTO 오브젝트의 fieldName의 fieldValue값을 petterns 옵션으로 벨리데이션 설정 (추가작업필요함)
			return validate(paramValidate);
		}
		else {
			throw new APIException(MessageConstants.RESPONSE_CODE_2001, "유효성검사유형이 잘못되었습니다.");
		}
    }
    
	@APIOperation(description="바인드된 ParamValidateSDO설정값 중 설정된 모델의 필드를 유효성검사합니다.")
    private boolean validateField(ParamValidateSDO paramValidate, Object model, Field field) {
    	
    	APIFields fieldAnno = field.getAnnotation(APIFields.class);
    	Collection<?> collection = typeUtil.getCollectionType(field.getType());
		//@APIFields 어노테이션이있는 필드만 벨리데이션한다. 없는 필드는 패스 한다. 
		if(fieldAnno != null) {
			// field.getType()
			try {
				Object value = propertyUtil.getProperty(model, field.getName());
				
				/** required */
				//필수 값 존재 여부채크
				if( fieldAnno.required() ) {
					if(value == null || (field.getType().isAssignableFrom(String.class) && APIUtil.isEmpty((String)value))) {
						paramValidate.setMessage("'".concat(fieldAnno.description()).concat("'는/은 필수 입력 사항합니다."));
						paramValidate.setValidation(false);
						return false;
					}
				}
				
				/** maxLength */
				//필드 타입이 문자열일때 문자열의 byte(UTF-8기준 한글3바이트,나머지 한글2바이트) 최대길이를 넘어서는지 채크 
				if( field.getType().isAssignableFrom(String.class) && fieldAnno.maxLength() > 0 && value != null ) {
					//((String)field)
					int byteLength = apiUtil.getBytesLength((String)value, this.encoding);
					if(byteLength > fieldAnno.maxLength()) {
						paramValidate.setMessage(APIUtil.addString("'", fieldAnno.description(), "' 필드 데이터는 ", Integer.toString(fieldAnno.maxLength()), " 바이트를 넘을수 없습니다."));
						paramValidate.setValidation(false);
						return false;	
					}
				}
				
				/** minLength */
				//필드 타입이 문자열일때 문자열의 byte(UTF-8기준 한글3바이트,나머지 한글2바이트) 최대길이를 넘어서는지 채크 
				if( field.getType().isAssignableFrom(String.class) && fieldAnno.minLength() > 0 && value != null ) {
					//((String)field)
					int byteLength = apiUtil.getBytesLength((String)value, this.encoding);
					if(byteLength < fieldAnno.minLength()) {
						paramValidate.setMessage(APIUtil.addString("'", fieldAnno.description(), "' 필드 데이터는 ", Integer.toString(fieldAnno.minLength()), " 바이트보다 길게 입력되야 합니다."));
						paramValidate.setValidation(false);
						return false;	
					}
				}
				
				/** regex pattern */
				if( field.getType().isAssignableFrom(String.class) && APIUtil.isNotEmpty(fieldAnno.pattern()) && value != null ) {
					
					if(!regexUtil.testPattern((String) value, fieldAnno.pattern())) {
						paramValidate.setMessage(APIUtil.addString("'", fieldAnno.description(), "' 필드 데이터가 유효하지 않습니다. 검증식은 '",fieldAnno.pattern() ,"' 입니다."));
						paramValidate.setValidation(false);
						return false;
					}
				}
				
				/** isDate */
				if( field.getType().isAssignableFrom(String.class) && fieldAnno.isDate() && value != null ) {
					
					if(!apiUtil.isValidDate((String) value)) {
						paramValidate.setMessage(APIUtil.addString("'", fieldAnno.description(), "' 필드 날짜 데이터가 유효하지 않습니다."));
						paramValidate.setValidation(false);
						return false;
					}
				}

				/** collection or dto, vo 등의 User Object */
				if(value != null && (collection != null || !typeUtil.isGeneralType(field.getType()) && typeUtil.isSupportedReferenceType(value.getClass().getCanonicalName()))) {
					validateModel(new ParamValidateSDO(value));
				}
			}
			catch (Exception e){
				throw new APIException(MessageConstants.RESPONSE_CODE_2001, APIUtil.addString("유효성검사대상 필드 '", field.getName(), "'가 잘못되었습니다."), e);
			}
		}
		return true;
    }
    
	
	@APIOperation(description="바인드된 ParamValidateSDO설정값 중 설정된 모델의 모든 필드를 유효성검사합니다.")
    private ParamValidateSDO validateModel(ParamValidateSDO paramValidate) {
    	
    	Object model = paramValidate.getModel();
		
    	if(model != null) {

        	Class<?> modelClass = null;
    		APIModel modelAnno = null;
    		
    		if(Collection.class.isAssignableFrom(model.getClass()) && paramValidate.getValidateType() == OperateConstants.VALIDATE_DTO_FULL_FIELD_ANNO){
    			//DeclaredFields field validate
				for(Object items : (Collection<?>) model) {
					
					modelClass = items.getClass();
		    		modelAnno = modelClass.getAnnotation(APIModel.class);
		    		
		    		//@APIModel 어노테이션이있는 DTO만 벨리데이션한다.
	        		if(modelAnno != null) {
	        			
	        			for(Field field : modelClass.getDeclaredFields()) {
	    					if(logger.isDebugEnabled()) {
	    						logger.debug("[VALIDATE] DeclaredFields Name : " + field.getName());
	    					}
	    					if(!validateField(paramValidate, items, field)) {
	    						return paramValidate;
	    					}
	        			}	
	        		}
	        		else {
	        			throw new APIException(MessageConstants.RESPONSE_CODE_2001, APIUtil.addString("유효성검사대상 클래스가 잘못되었습니다. @APIModel어노테이션이 설정된 모델만 가능합니다."));
	        		}
				}
    		}
    		else {
    			modelClass = model.getClass();
        		modelAnno = modelClass.getAnnotation(APIModel.class);
        		
        		//@APIModel 어노테이션이있는 DTO만 벨리데이션한다.
        		if(modelAnno != null) {
        			
        			if(paramValidate.getValidateType() == OperateConstants.VALIDATE_DTO_FULL_FIELD_ANNO) {
        				//DeclaredFields field validate
        				for(Field field : modelClass.getDeclaredFields()) {
        					if(logger.isDebugEnabled()) {
        						logger.debug("[VALIDATE] DeclaredFields Name : {}", field.getName());
        					}
        					if(!validateField(paramValidate, model, field)){
        						return paramValidate;
        					}
            			}
        			}
        			else if(paramValidate.getValidateType() == OperateConstants.VALIDATE_DTO_SINGLE_FIELD_ANNO) {
        				//Single field validate
        				
        				//validate field 
        				String fieldName = paramValidate.getFieldName();
        				boolean isValidatedField = false;
        				if(logger.isDebugEnabled()) {
        					logger.debug("[VALIDATE] SuperClass Field Name : {}", fieldName);
        				}
        				
        				if(APIUtil.isNotEmpty(fieldName)) {
    					    Class<?> modeClass = model.getClass();
    					    
    					    while (modeClass != null) {
    					        //fieldList에 벨리데이션 대상 필드가있는지 loop 하여 있으면 벨리데이션하고 없으면 superClass 의 DeclaredFields 에서 다시 찾는다. 
    					        //찾는 필드가 없으면 나올때까지 superClass 를 계속 탐색한다. (그래도 없으면 필드가 없는것임)
    					        isValidatedField = false;
    					        for(Field field : modeClass.getDeclaredFields()){
    					        	if(field.getName().equals(fieldName)) {
    					        		
    					        		if(logger.isDebugEnabled()) {
    					        			logger.debug("[VALIDATE] Find Field Name : {}", fieldName);
    					        		}
    					        		if(!validateField(paramValidate, model, field)){
    					        			if(logger.isDebugEnabled()) {
    					        				logger.debug("[VALIDATE] Field validation Fail!! : {}", fieldName);
    					        			}
    		        						return paramValidate;
    		        					}
    					        		if(logger.isDebugEnabled()) {
    					        			logger.debug("[VALIDATE] Field validation OK!! : {}", fieldName);
    					        		}
    					        		
    					        		isValidatedField = true;
    					        		break;
    					        	}
    					        }
    					        
    					        if(isValidatedField) {
    					        	break;
    					        }
    					        modeClass = modeClass.getSuperclass();
    					    }
    					    
    					    if(!isValidatedField) {
    	    					//해당 필드가 없음
    	    					throw new APIException(APIUtil.addString("유효성검사대상 필드 '", fieldName, "'가 존재하지 않습니다."));
    	    				}
        				}
        				else {
        					throw new APIException(APIUtil.addString("유효성검사대상 필드명이 잘못되었습니다."));
        				}
        				/*
        				if(APIUtil.isNotEmpty(fieldName)) {
            				try {
            					logger.debug("[VALIDATE] SuperClass Field Name : " + fieldName);
        						Field field = modelClass.getField(fieldName);
        						
        						if(!validateField(paramValidate, model, field)){
            						return paramValidate;
            					}
        					} catch (NoSuchFieldException | SecurityException e) {
        						throw new APIException(APIUtil.addString("ISNOT_EXIST_PARAMS", "유효성검사대상 @필드{"+fieldName+"}가"), e);
        					}
            			}
        				else {
        					throw new APIException(APIUtil.addString("INVALID_PARAMS", "유효성검사대상 @필드명이"));
        				}
        				*/
        			}
        		}
        		else {
        			throw new APIException(APIUtil.addString("유효성검사대상 클래스는 @APIModel어노테이션 클래스만 가능합니다."));
        		}
    		}
    	}
    	else{
    		throw new APIException(APIUtil.addString("유효성검사대상 DTO가 존재하지 않습니다."));
    	}
    	
    	return paramValidate;
    }
    
    
	@APIOperation(description="바인드된 ParamValidateSDO설정값으로 유효성 검사를 실행합니다.")
    private ParamValidateSDO validate(ParamValidateSDO paramValidate){
    	
    	Object values = paramValidate.getFieldValue();
    	
    	if( paramValidate.isRequired() && ( values == null || values.toString().isEmpty()) ) {
			//required
			if(logger.isDebugEnabled()) {
    			logger.debug(" - Required Validate");
    		}
			paramValidate.setValidation(false);
			return paramValidate;
    	}
    	
    	if(values != null) {

    		Class<?> valueClass = values.getClass();
        	String vPattern = null; 
        	long sizeLen = 0;
        	
    		//regex pattern test
    		for(String pattern : paramValidate.getPatterns()) {
    			
    			if( pattern.equalsIgnoreCase(VP_REQUIRED) ) {
    				continue;
    			}
    			else if( pattern.toLowerCase().startsWith(VP_REGEX) ) {
    				if(logger.isDebugEnabled()) {
    	    			logger.debug(" - Regex Validate");
    	    		}
    				//regex
    				vPattern = pattern.substring(pattern.toLowerCase().indexOf(VP_REGEX) + VP_REGEX.length()).trim();
					
					if(logger.isDebugEnabled()) {
		    			logger.debug(APIUtil.addString( OperateConstants.LINE_SEPARATOR, " regexp : ", vPattern ,OperateConstants.LINE_SEPARATOR, " values : ", values.toString()));
		    		}

					if(!regexUtil.testPattern(values.toString(), vPattern)) {
						paramValidate.setValidation(false);
						return paramValidate;
					}
    			}
    			else if( pattern.toLowerCase().startsWith(VP_TYPE) ) {
    				if(logger.isDebugEnabled()) {
    	    			logger.debug(" - Type Validate");
    	    		}
    				//type
    				vPattern = pattern.substring(pattern.toLowerCase().indexOf(VP_TYPE) + VP_TYPE.length()).trim();
    				
    				if(logger.isDebugEnabled()) {
    	    			logger.debug(APIUtil.addString( OperateConstants.LINE_SEPARATOR, " value type : ", valueClass.getCanonicalName() ,OperateConstants.LINE_SEPARATOR, " validate type : ", vPattern));
    	    		}
    				
    				if(!valueClass.getCanonicalName().equalsIgnoreCase(vPattern) && !valueClass.getSimpleName().equalsIgnoreCase(vPattern)) {
    					paramValidate.setValidation(false);
    					return paramValidate;
    				}
    			}
    			else if( pattern.toLowerCase().startsWith(VP_SIZE) ) {
    				if(logger.isDebugEnabled()) {
    	    			logger.debug(" - Size Validate");
    	    		}
    				//size
					sizeLen = Long.parseLong( pattern.substring(pattern.toLowerCase().indexOf(VP_SIZE) + VP_SIZE.length()).trim() );
					
    				if( typeUtil.getCollectionType(valueClass) != null ) {
    					//Collection
    					if(((Collection<?>) values).size() != sizeLen) {
    						paramValidate.setValidation(false);
    						return paramValidate;
    					}
    				}
    				else if( typeUtil.getMapType(valueClass) != null ) {
    					//Map
    					if(((Map<?,?>) values).size() != sizeLen) {
    						paramValidate.setValidation(false);
    						return paramValidate;
    					}
    				}
    				else {
    					throw new APIException("SIZE 유효성채크 대상은 Collection, Map 타입만 가능합니다.");
    				}
    			}
    			else if( pattern.toLowerCase().startsWith(VP_LENGTH) ) {
    				if(logger.isDebugEnabled()) {
    	    			logger.debug(" - Length Validate");
    	    		}
    				//length
    				sizeLen = Long.parseLong( pattern.substring(pattern.toLowerCase().indexOf(VP_LENGTH) + VP_LENGTH.length()).trim() );
    				
    				if( valueClass.isAssignableFrom(Array.class) ) {
    					//Array
    					if(Array.getLength(values) != sizeLen) {
    						paramValidate.setValidation(false);
    						return paramValidate;
    					}
    				}
    				else {
    					//toString length
    					if(values.toString().length() != sizeLen) {
    						paramValidate.setValidation(false);
    						return paramValidate;
    					}
    				}
    			}
    			else {
    				//잘못된 유효성 검사 패턴
    				throw new APIException("유효성검사 유형이 잘못되었습니다.");
    			}
    		}
    	}
    	
		return paramValidate;
    }
}
