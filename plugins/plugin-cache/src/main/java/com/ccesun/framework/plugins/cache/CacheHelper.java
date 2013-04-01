package com.ccesun.framework.plugins.cache;

import java.io.Serializable;
import java.net.URL;

import javax.annotation.PostConstruct;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class CacheHelper {
	
	private CacheManager manager;
	
	@PostConstruct
	public void init() {
		URL url = getClass().getResource("/ehcache.xml");
		manager = CacheManager.newInstance(url);
	}
	
	private void put(Cache cache, Serializable key, Serializable value) {
		Element element = new Element(key, value);
		cache.put(element);
	}
	
	public void put(String cacheName, Serializable key, Serializable value) {
		Cache cache = manager.getCache(cacheName);
		put(cache, key, value);
	}
	
	public void put(Serializable key, Serializable value) {
		Cache cache = manager.getCache("defaultCache");
		put(cache, key, value);
	}
	
	private Object get(Cache cache, Serializable key) {
		Element element = cache.get("key");
		return element.getObjectValue();
	}
	
	public Object get(Serializable key) {
		Cache cache = manager.getCache("defaultCache");
		return get(cache, key);
	}
	
	public Object get(String cacheName, Serializable key) {
		Cache cache = manager.getCache(cacheName);
		return get(cache, key);
	}
	
	private Object remove(Cache cache, Serializable key) {
		return cache.remove(key);
	}
	
	public Object remove(Serializable key) {
		Cache cache = manager.getCache("defaultCache");
		return remove(cache, key);
	}
	
	public Object remove(String cacheName, Serializable key) {
		Cache cache = manager.getCache(cacheName);
		return remove(cache, key);
	}
}
