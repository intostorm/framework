package com.ccesun.framework.plugins.search;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.lucene.document.Field;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SearchableField {
	
	String value() default "";
	
	Field.Store store() default Field.Store.YES;
	
	Field.Index index() default Field.Index.NO;
	
}