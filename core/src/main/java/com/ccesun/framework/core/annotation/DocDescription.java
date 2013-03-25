/**
 * 
 */
package com.ccesun.framework.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 版权所有：长春易申软件有限公司
 * <p>
 * 侵权者将受到法律追究。
 * <p>
 * DERIVED FROM: NONE
 * <p>
 * PURPOSE:
 * <p>
 * DESCRIPTION:
 * <p>
 * UPDATE: mawm
 * <p>
 * DATE: 2013-3-21 下午9:41:27
 * <p>
 * HISTORY: 1.0
 * 
 * @version 1.0
 * @author mawm
 * 
 * <br>
 *         功能描述:<br>
 * 
 *         对象的描述,对应"<description>描述的内容</description>"
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD })
@Documented
public @interface DocDescription {
	/**
	 * @return 描述的内容
	 * @author mawm at 2013-3-21 下午10:39:51
	 */
	String value() default "";
}
