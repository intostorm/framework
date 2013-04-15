package com.ccesun.framework.plugins.search;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SearchableBean {
	/**
	 * @return 存储的目录,默认取SearchUtils.getSearchIndexDir(),也就是配置的plugin.search.
	 *         searchIndexDir指向的目录
	 * @author mawm at 2013-4-15 下午3:56:55
	 */
	String dir() default "";
}