package com.ezwel.htl.interfaces.commons.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * API Annotation for Field
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */
@Target({ java.lang.annotation.ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface APIFields {

	/**
	 * 파라메터 필수 filed 여부 
	 * @return 
	 */
    public abstract boolean required() default false;
    
    public abstract boolean httpHeader() default false;
    
    public abstract String headerName() default "";
    
    public abstract String description() default "";
    
    public abstract String attributeName() default "";
    
    public abstract String attributeType() default "";
    
    public abstract String constraints() default "";
    
    public abstract int attributeLength() default -1;
    
    public abstract String groupName() default "";
    
    public abstract String[] marshalType() default "";
    
    public abstract int ioType() default -1;
    
    /**
     * Json 또는 XML 마샬대상 필드 여부
     * @return
     */
    public abstract boolean isMarshalField() default false;
    
    /**
     * Collection 필드의 경우 Collection 사이즈 의존 필드 이름
     * @return
     */
    public abstract String dependentField() default "";
    
    /**
     * field 의 write 형식 ( normal, json, xml ) 
     * @return
     */
    public abstract String writeFormat() default "normal";
    
    /**
     * field 의 read 형식 ( normal, json, xml )
     * @return
     */
    public abstract String readFormat() default "normal";
    
    /**
     * Collection Type Field 의 경우 Collection reference wrapper type 
     * @return
     */
    public abstract Class<?> referenceType() default java.lang.Object.class;
    
    /**
     * Map Type Field 의 경우 Map 의 key type
     * @return
     */
    public abstract Class<?> mapKey() default java.lang.Object.class;

    /**
     * Map Type Field 의 경우 Map 의 value type
     * @return
     */
    public abstract Class<?> mapValue() default java.lang.Object.class;

}
