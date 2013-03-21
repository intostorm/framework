package com.ccesun.framework.core.dao.support;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.ccesun.framework.util.BeanUtils;

public class EntityUtils {
	
	public static boolean isNew(Serializable pk) {
		
		if (pk == null)
			return true;
		
		if (pk instanceof Integer) {
			return ((Integer) pk).intValue() == 0; 
		}
		
		if (pk instanceof Long) {
			return ((Long) pk).intValue() == 0; 
		}
		
		return false;
	}
	
	/**
	 * 按模式加载延迟的对象
	 * <p>用法：<br>
	 * EntityUtils.touch(user, "dept");<br> 
	 * dept 是user对象的字段<br>
	 * EntityUtils.touch(item, "sale.inputUser");<br>
	 * sale 是item对象的字段<br>
	 * inputUser 是sale对象的字段<br>
	 * @param source 含有延迟加截的对象
	 * @param pattern 模式
	 */
	public static void touch(Object source, String pattern) {
		
		String[] patternFrags = StringUtils.split(pattern, ".");
		Object obj = source;
		for (String patternFrag : patternFrags) {
			try {
				if(obj != null){
					Object value = BeanUtils.getProperty(obj, patternFrag);
					BeanUtils.setProperty(obj, patternFrag, value);
					obj = value;
				}else{
					break;
				}
			} catch (Exception e) {
				obj = null;
				break;
			}
		}
	}

}
