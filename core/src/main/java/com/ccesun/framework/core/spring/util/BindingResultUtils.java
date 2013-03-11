package com.ccesun.framework.core.spring.util;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class BindingResultUtils {

	public static final String exportError(BindingResult bindingResult, String split){
		StringBuilder sb = new StringBuilder();
		if(split == null){
			split = StringUtils.EMPTY;
		}
		if(bindingResult != null && bindingResult.hasErrors()){
			List<FieldError> errors = bindingResult.getFieldErrors();
			for(FieldError error : errors){
				sb.append('[').append(bindingResult.getFieldErrors())
					.append("]").append(split).append(error.getDefaultMessage()).append("\r\n");
			}
		}
		return sb.toString();
	}
	
	public static final String translateErrors(String error, Map<String,String> propertyNames){
		StringBuffer sb = new StringBuffer();
		Pattern pattern = Pattern.compile("\\[(\\w+)\\]");
		Matcher matcher = pattern.matcher(error);
		while(matcher.find()){
			String key = matcher.group(1);
			if(propertyNames.containsKey(key)){
				matcher.appendReplacement(sb, propertyNames.get(key));
			}else{
				matcher.appendReplacement(sb, matcher.group());
			}
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
}
