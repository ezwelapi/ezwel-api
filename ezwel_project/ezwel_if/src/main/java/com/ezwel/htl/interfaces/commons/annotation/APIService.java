package com.ezwel.htl.interfaces.commons.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * API Annotation for Service(Type)
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */
@Target({ java.lang.annotation.ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface APIService {

    
    /**
     * api service id 
     * @return
     */
    public abstract String id() default "";
    
    /**
     * api service description
     * @return
     */
    public abstract String description() default "";
    
}
