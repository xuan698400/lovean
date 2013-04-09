package com.xuan.lovean.anDb.helper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对应的是一个类
 * 
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2013-4-8 下午5:45:58 $
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ManyToOne {
    public String column() default "";
}
