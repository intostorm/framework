package com.ccesun.framework.core;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * <p>代表全局配置，以单例的方式使用
 * <p>此类在加载时从类路径下加载/config.properties文件，不管/config.properties存不存在，AppContext都能正常使用
 * <p>AppContext内部维护一个Map<String, Object> contextMap，用来保存配置项
 * <p>配置值的来源有两个，一是从/config.properties在初始化时加载，二是在运行期动态存入
 * <p>
 * <p>使用方法：1 2 34
 * <p>
 * <p>AppContext apptext = AppContext.getInstance();
 * <p>String charset = apptext.getString("app.Charset"); //写在/config.properties内的配置
 * <p>int pageSize = apptext.getString("app.pageSize"); //写在/config.properties内的配置
 * <p>
 * <p>apptext.put("app.test1", "test1Value");
 * <p>String value = apptext.getString("app.test1");
 * <p>
 * @author Jaron
 *
 */
public class AppContext {

	private static Log log = LogFactory.getLog(AppContext.class);
	private static final String CONFIGUATION_FILE = "/config.properties";
	
    private static final AppContext context = new AppContext();
    
    private ServletContext servletContext;
    
    private ApplicationContext applicationContext;
    static {
    	try {
            InputStream in = AppContext.class.getResourceAsStream(CONFIGUATION_FILE);
            Properties p = new Properties(); 
            p.load(in);

            for (Iterator<Object> iterator = p.keySet().iterator(); iterator.hasNext();) {
                String key = (String) iterator.next();
                AppContext.getInstance().put(key, p.getProperty(key)); 
            }
            
        } catch (Exception e) {
            log.warn("Can not init properties, cause by reading configuation.properties failed");
        }
    }

    private Map<String, Object> contextMap = new HashMap<String, Object>();

    private AppContext() {
    }

    public static AppContext getInstance() {
        return context;
    }

    public Object get(String key) {
        return this.contextMap.get(key);
    }

    public void put(String key, Object value) {
        this.contextMap.put(key, value);
    }

    public String getString(String key) {
        return String.valueOf(this.contextMap.get(key));
    }

    public int getInt(String key) {
        return Integer.parseInt(getString(key));
    }
    
    public Map<String, Object> getContextMap() {
    	return Collections.unmodifiableMap(contextMap);
    } 

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public ApplicationContext getApplicationContext() {
		if(this.servletContext == null){
			return null;
		}
		if(this.applicationContext == null){
			this.applicationContext = WebApplicationContextUtils.getWebApplicationContext(this.servletContext);
		}
		return applicationContext;
	}

}