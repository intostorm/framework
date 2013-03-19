package com.ccesun.framework.util;

import java.util.Collection;
import java.util.Date;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtils {

	public static final ObjectMapper JACKSON_MAPPER = new ObjectMapper();

	public static final String jacksonToJson(Object obj) {
		String json = StringUtils.EMPTY;
		if (obj != null) {
			try {
				json = JACKSON_MAPPER.writeValueAsString(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return json;
	}

	public static final String toJson(Object obj) {
		String json = StringUtils.EMPTY;
		if (obj != null) {
			if (obj instanceof Collection) {
				json = JSONArray.fromObject(obj).toString();
			} else {
				json = JSONObject.fromObject(obj).toString();
			}
		}
		return json;
	}

	@SuppressWarnings("unchecked")
	public static final <T> T fromJson(String jsonStr, Class<T> clazz) {
		T result = null;
		JSONObject json = JSONObject.fromObject(jsonStr);
		try {
			result = (T) JSONObject.toBean(json, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**把对象转换为json对象,忽略嵌套
	 * @param obj
	 * @return
	 * @author mawm  at 2013-3-19 下午1:05:15
	 */
	public static JSON parseJson(Object obj) {
		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false);
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

		config.registerJsonValueProcessor(Date.class,
				new DateJsonValueProcessor("yyyyMMdd hh:mm:ss"));
		JSON json = JSONSerializer.toJSON(obj, config);
		return json;
	}

}
