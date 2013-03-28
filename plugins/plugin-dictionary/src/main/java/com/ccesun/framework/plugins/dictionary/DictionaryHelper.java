package com.ccesun.framework.plugins.dictionary;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.ccesun.framework.core.dao.support.Page;
import com.ccesun.framework.core.dao.support.QCriteria;
import com.ccesun.framework.plugins.dictionary.domain.Dictionary;
import com.ccesun.framework.plugins.dictionary.service.DictionaryService;
import com.ccesun.framework.util.BeanUtils;
import com.ccesun.framework.util.StringUtils;

@Component
@Lazy
public class DictionaryHelper {
	
	@Autowired
	private DictionaryService dictionaryService;
	
	private Map<String, Map<String, Dictionary>> dictionaries = new TreeMap<String, Map<String, Dictionary>>();

	public Map<String, Map<String, Dictionary>> getDictionaries() {
		return dictionaries;
	}
	
	@PostConstruct
	public void init() {
			
		List<Dictionary> dicList = dictionaryService.find(new QCriteria());

		for (Dictionary dict : dicList) {
			String type = dict.getDictType();
			Map<String, Dictionary> tmpMap = dictionaries.get(type);
			if (tmpMap == null) {
				tmpMap = new HashMap<String, Dictionary>();
				dictionaries.put(type, tmpMap);
			}
			tmpMap.put(dict.getDictKey(), dict);
		}

	}

	public void update() {
		clear();
		init();
	}

	public void clear() {
		dictionaries.clear();
	}

	public Object convert(Object bean, String type, String fieldName) {

		String oldValue = null;
		String newValue = null;
		try {
			oldValue = BeanUtils.getProperty(bean, fieldName);
		} catch (Exception e) {
		}
		newValue = lookupDictValue0(type, oldValue);

		try {
			BeanUtils.copyProperty(bean, fieldName, newValue);
		} catch (Exception e) {
		}

		return bean;
	}

	public Dictionary lookupDict(String type, String key) {
		if (StringUtils.isBlank(type) || StringUtils.isBlank(key))
			return null;
		Map<String, Dictionary> typeMap = dictionaries.get(type);
		return typeMap == null ? null : typeMap.get(key);
	}
	
	/**
	 * 从缓存中获得指定type, 多个key的字典列表
	 * 
	 * @param type 字典类型
	 * @param key 逗号分隔的key串
	 * @return
	 */
	public Dictionary[] lookupDicts(String type, String csvKey) {
		if (StringUtils.isBlank(type) || StringUtils.isBlank(csvKey))
			return null;
		
		Map<String, Dictionary> typeMap = dictionaries.get(type);
		List<Dictionary> result = new ArrayList<Dictionary>();
		if (typeMap != null) {
			String[] temp = StringUtils.split(csvKey, ",");
			for (int i = 0; i < temp.length; i++) {
				temp[i] = temp[i].trim();
				if (typeMap.get(temp[i]) != null)
					result.add(typeMap.get(temp[i]));
			}
		}
		return result.toArray(new Dictionary[0]);
	}
	
	
	public String lookupDictValue0(String type, String key) {
		Dictionary[] dicts = lookupDicts(type, key);
		if (dicts == null)
			return StringUtils.EMPTY;
		String[] result = new String[dicts.length];
		for (int i = 0; i < dicts.length; i++) {
			result[i] = dicts[i].getDictValue0();
		}
		return StringUtils.join(result, ",");
	}
	
	public String lookupDictValue1(String type, String key) {
		Dictionary[] dicts = lookupDicts(type, key);
		if (dicts == null)
			return StringUtils.EMPTY;
		String[] result = new String[dicts.length];
		for (int i = 0; i < dicts.length; i++) {
			result[i] = dicts[i].getDictValue1();
		}
		return StringUtils.join(result, ",");
	}
	
	public String lookupDictValue2(String type, String key) {
		Dictionary[] dicts = lookupDicts(type, key);
		if (dicts == null)
			return StringUtils.EMPTY;
		String[] result = new String[dicts.length];
		for (int i = 0; i < dicts.length; i++) {
			result[i] = dicts[i].getDictValue2();
		}
		return StringUtils.join(result, ",");
	}
	
	public String lookupDictValue3(String type, String key) {
		Dictionary[] dicts = lookupDicts(type, key);
		if (dicts == null)
			return StringUtils.EMPTY;
		String[] result = new String[dicts.length];
		for (int i = 0; i < dicts.length; i++) {
			result[i] = dicts[i].getDictValue3();
		}
		return StringUtils.join(result, ",");
	}

	public Map<String, Dictionary> getDictionariesByType(String type) {
		return getDictionariesByTypeAndParent(type, null);
	}
	
	public Map<String, Dictionary> getDictionariesByTypeAndParent(String type, String parentKey) {
		
		Map<String, Dictionary> result = new HashMap<String, Dictionary>();
		if (StringUtils.isBlank(parentKey))
			result = dictionaries.get(type);
		
		else {
			Map<String, Dictionary> tmpResult = dictionaries.get(type);
			Set<Map.Entry<String, Dictionary>> entrys = tmpResult.entrySet();
			for (Map.Entry<String, Dictionary> entry : entrys) {
				if (parentKey.equals(entry.getValue().getParentKey()))
					result.put(entry.getKey(), entry.getValue());
			}
		}
		
		return result;
	}
	
	public List<Map<String, String>> decodeBeansToMaps(List<?> beans) {

		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		
		for (Object bean : beans) {
			Map<String, String> entry = decodeBeanToMap(bean);
			result.add(entry);
		}
		
		return result;
	}

	public Map<String, String> decodeBeanToMap(Object bean) {
		Map<String, String> result = new HashMap<String, String>();
		
		Field[] fields = bean.getClass().getDeclaredFields();
		for (Field field : fields) {
			DictValue dictValue = field.getAnnotation(DictValue.class);
			field.setAccessible(true);
			
			String fieldValue = StringUtils.EMPTY;
			try {
				fieldValue = BeanUtils.getProperty(bean, field.getName());
			} catch (Exception e) {
			}
			
			if (dictValue != null) {
				
				String decodeValue = StringUtils.EMPTY;
				
				if (StringUtils.isBlank(dictValue.value()) || StringUtils.equalsIgnoreCase(dictValue.value(), "value0")) {
					decodeValue = lookupDictValue0(dictValue.type(), fieldValue);
				}
				if (StringUtils.equalsIgnoreCase(dictValue.value(), "value1")) {
					decodeValue = lookupDictValue1(dictValue.type(), fieldValue);
				}
				if (StringUtils.equalsIgnoreCase(dictValue.value(), "value2")) {
					decodeValue = lookupDictValue2(dictValue.type(), fieldValue);
				}
				if (StringUtils.equalsIgnoreCase(dictValue.value(), "value3")) {
					decodeValue = lookupDictValue3(dictValue.type(), fieldValue);
				}
				
				result.put(field.getName(), decodeValue);
			} else {
				result.put(field.getName(), fieldValue);
			}
		}
		return result;
	}
	
	public <T> void decodePage(Page<T> page) {

		page.setContent(decodeBeans(page.getContent()));
		
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> decodeBeans(List<T> beans) {

		List<T> result = new ArrayList<T>();
		
		for (Object bean : beans) {
			T entry = (T) decodeBean(bean);
			result.add(entry);
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	public <T> T decodeBean(T source) {
		T result = source;
		try {
			result = ((Class<T>) source.getClass()).newInstance();
		} catch (Exception e) {
		}
		
		Field[] fields = source.getClass().getDeclaredFields();
		for (Field field : fields) {
			DictValue dictValue = field.getAnnotation(DictValue.class);
			field.setAccessible(true);
			
			String fieldValue = StringUtils.EMPTY;
			try {
				fieldValue = BeanUtils.getProperty(source, field.getName());
			} catch (Exception e) {
			}
			
			if (dictValue != null) {
				
				String decodeValue = StringUtils.EMPTY;
				
				if (StringUtils.isBlank(dictValue.value()) || StringUtils.equalsIgnoreCase(dictValue.value(), "value0")) {
					decodeValue = lookupDictValue0(dictValue.type(), fieldValue);
				}
				if (StringUtils.equalsIgnoreCase(dictValue.value(), "value1")) {
					decodeValue = lookupDictValue1(dictValue.type(), fieldValue);
				}
				if (StringUtils.equalsIgnoreCase(dictValue.value(), "value2")) {
					decodeValue = lookupDictValue2(dictValue.type(), fieldValue);
				}
				if (StringUtils.equalsIgnoreCase(dictValue.value(), "value3")) {
					decodeValue = lookupDictValue3(dictValue.type(), fieldValue);
				}
				
				try {
					BeanUtils.setProperty(result, field.getName(), decodeValue);
				} catch (Exception e) {
				}
			} else {
				try {
					BeanUtils.setProperty(result, field.getName(), fieldValue);
				} catch (Exception e) {
				}
			}
		}
		return result;
	}
}
