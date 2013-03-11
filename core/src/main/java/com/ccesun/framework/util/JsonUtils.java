package com.ccesun.framework.util;

import java.util.Collection;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtils {
	
	public static final ObjectMapper JACKSON_MAPPER = new ObjectMapper();
	
	
	public static final String jacksonToJson(Object obj){
		String json = StringUtils.EMPTY;
		if(obj != null){
			try {
				json = JACKSON_MAPPER.writeValueAsString(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return json;
	}
	
	public static final String toJson(Object obj){
		String json = StringUtils.EMPTY;
		if(obj != null){
			if(obj instanceof Collection){
				json = JSONArray.fromObject(obj).toString();
			}else{
				json = JSONObject.fromObject(obj).toString();
			}
		}
		return json;
	}
	
	@SuppressWarnings("unchecked")
	public static final <T> T fromJson(String jsonStr, Class<T> clazz){
		T result = null;
		JSONObject json = JSONObject.fromObject(jsonStr);
		try{
			result = (T) JSONObject.toBean(json, clazz);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

}
