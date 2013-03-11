package com.ccesun.framework.plugins.dictionary;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.FIELD)
@Retention(RUNTIME)
public @interface DictValue {
	
	/** required */
	String type();

	String value() default "value0";
	
}
