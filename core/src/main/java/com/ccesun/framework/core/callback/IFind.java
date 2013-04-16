/**
 * 
 * Apr 16, 2011 10:06:29 AM
 */
package com.ccesun.framework.core.callback;

/**
 * <p>
 * 版权所有：长春易申软件有限公司
 * <p>
 * 未经本公司许可，不得以任何方式复制或使用本程序任何部分，
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
 * DATE: Apr 16, 2011 10:06:29 AM
 * <p>
 * HISTORY: 1.0
 * 
 * @version 1.0
 * @author mawm
 * @since java jdk1.6.0_11<br>
 * @beanid <br>
 * @springxml .xml
 * @struts2xml .xml <br>
 * @url / /{*}.s2
 * 
 * <br>
 * 功能描述:<br>
 * 查找对象的回调接口
 * 
 */
public interface IFind<K, V> {
	/**
	 * @param v
	 * @return
	 * @author mawm at Apr 16, 2011 10:06:54 AM
	 */
	public V find(K v);
}
