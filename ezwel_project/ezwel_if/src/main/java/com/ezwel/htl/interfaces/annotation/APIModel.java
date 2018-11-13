package com.ezwel.htl.interfaces.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * API Annotation for Model
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */
@Target({ java.lang.annotation.ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface APIModel {

    
    /**
     * Database Table Name 
     * @return
     */
    public abstract String modelNames() default "";
    
    /**
     * Database Table Comment
     * @return
     */
    public abstract String description() default "";
    
    
    /**
     * Database Table Type
     * @return
     */
    public abstract String modelTypes() default "";
    
}
