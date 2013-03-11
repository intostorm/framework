package com.ccesun.framework.core.requesthistory;

import java.util.Map;

public class RequestHistoryEntry {

	private String name;
	private String requestURI;
	private Map<String, Object> parameterMap;

	public RequestHistoryEntry() {
	}

	public RequestHistoryEntry(String name, String requestURI,
			Map<String, Object> parameterMap) {
		super();
		this.name = name;
		this.requestURI = requestURI;
		this.parameterMap = parameterMap;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRequestURI() {
		return requestURI;
	}

	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}

	public Map<String, Object> getParameterMap() {
		return parameterMap;
	}

	public void setParameterMap(Map<String, Object> parameterMap) {
		this.parameterMap = parameterMap;
	}
}
