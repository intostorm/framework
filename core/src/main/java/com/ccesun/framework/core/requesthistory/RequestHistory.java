package com.ccesun.framework.core.requesthistory;

import java.util.HashMap;
import java.util.Map;

public class RequestHistory {
	
	//public static final String REQUESTHISTORY_KEY = "com.ccesun.framework.core.requesthistory.REQUESTHISTORY_KEY";
	
	Map<String, RequestHistoryEntry> entris = new HashMap<String, RequestHistoryEntry>();
	
	private static final RequestHistory instance = new RequestHistory();
	
	private RequestHistory () {};
	
	public static RequestHistory getInstance() {
		return instance;
	}
	
	public Map<String, RequestHistoryEntry> getEntris() {
		return entris;
	}

	public void setEntris(Map<String, RequestHistoryEntry> entris) {
		this.entris = entris;
	}

	public void addEntry(String name, String requestURI, Map<String, Object> parameterMap) {
		entris.put(name, new RequestHistoryEntry(name, requestURI, parameterMap));
	};

	public RequestHistoryEntry getEntry(String name) {
		return entris.get(name);
	}
}
