package com.joker.jcache.annoation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Cache {
	public enum OperateType{INSERT,UPDATE,DELETE,SELECT};
	String[] value() default {};
	OperateType operateType();
}
