package com.ezwel.htl.interfaces.commons.annotation;

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
     * API Operation 설명
     * @return
     */
    public abstract String description() default "api operation";
    
    /**
     * <pre>
     * [메서드 설명]
     * 테스트 통과 여부
     * [사용방법 설명]
     * @APIOperation(isExecTest=true)
     * </pre>
     * @return
     * @author swkim@ebsolution.co.kr
     * @since  2018. 11. 14.
     */
    public abstract boolean isExecTest() default false;
}
