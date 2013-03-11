package com.ccesun.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {
	
	public static void mergeProperties(Object dest, Object orig) {
	    
		Class<?> c = orig.getClass();
		Field[] fileds = c.getDeclaredFields();
		
		for (Field field : fileds) {
			field.setAccessible(true);
			try {
				Object value = field.get(orig);
				mergeProperty(dest, field.getName(), value);
			} catch (Exception e) {
			}
			
		}
		
    }
	
	public static void mergeProperty(Object bean, String name, Object value) {

		if (value != null) {
			try {
				copyProperty(bean, name, value);
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
		}
    }
	
	
	
}
