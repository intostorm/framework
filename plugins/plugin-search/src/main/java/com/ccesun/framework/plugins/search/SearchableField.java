package com.ccesun.framework.plugins.search;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.lucene.document.Field;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SearchableField {
	
	/**
	 * 名称，默认与字段名一致 
	 * @return
	 */
	String value() default "";
	
	/**
	 * 是否存储此字段
	 * @return
	 */
	Field.Store store() default Field.Store.YES;
	
	/**
	 * 是否将此字段加入到索引中（可用作检索条件） 
	 * @return
	 */
	Field.Index index() default Field.Index.NO;
	
}