package com.ccesun.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ClassUtils;

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
	
	@SuppressWarnings("unchecked")
	public static Map<String, Field> getAllFieldMap(Class<?> resultClass) {
        List<Class<?>> allSuperclasses = ClassUtils.getAllSuperclasses(resultClass);

        Map<String, Field> m = new HashMap<String, Field>();
        for (Class<?> one : allSuperclasses) {
                 Field[] fields = one.getDeclaredFields();
                 for (Field o : fields) {
                      m.put(o.getName(), o);
                 }
        }
        Field[] fields = resultClass.getDeclaredFields();
        for (Field o : fields) {
        	m.put(o.getName(), o);
        }

        return m;
	}
	
	
}
