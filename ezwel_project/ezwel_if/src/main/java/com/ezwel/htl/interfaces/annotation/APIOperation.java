package com.ezwel.htl.interfaces.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * API Annotation for Operation
 * </pre>
 * @author swkim@ebsolution.co.kr
 * @date 2018. 11. 5.
 * @serviceType API
 */
@Target({ java.lang.annotation.ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface APIOperation {
    /**
     * API Operation id
     * @return
     */
    public abstract String id() default "";
    
    /**
     * API Operation thread별 id
     * @return
     */
    public abstract String threadId() default "";
    
    /**
     * API Operation 설명
     * @return
     */
    public abstract String description() default "api operation";
    
    public abstract boolean isExecTest() default false;
}
